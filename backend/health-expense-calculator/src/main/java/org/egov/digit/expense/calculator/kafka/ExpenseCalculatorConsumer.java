package org.egov.digit.expense.calculator.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.service.ExpenseCalculatorService;
import org.egov.digit.expense.calculator.service.HealthBillReportGenerator;
import org.egov.digit.expense.calculator.service.RedisService;
import org.egov.digit.expense.calculator.util.ExpenseCalculatorUtil;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillRequest;
import org.egov.digit.expense.calculator.web.models.CalculationRequest;
import org.egov.digit.expense.calculator.web.models.ReportGenerationTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ExpenseCalculatorConsumer {

	private final ExpenseCalculatorConfiguration configs;
	private final ExpenseCalculatorService expenseCalculatorService;
	private final ObjectMapper objectMapper;
	private final ExpenseCalculatorProducer producer;
	private final HealthBillReportGenerator healthBillReportGenerator;
	private final RedisService redisService;
	private final ExpenseCalculatorUtil expenseCalculatorUtil;

	@Autowired
	public ExpenseCalculatorConsumer(ExpenseCalculatorConfiguration configs, ExpenseCalculatorService expenseCalculatorService,
									 ObjectMapper objectMapper, ExpenseCalculatorProducer producer, HealthBillReportGenerator healthBillReportGenerator,
									 RedisService redisService, ExpenseCalculatorUtil expenseCalculatorUtil) {
		this.configs = configs;
		this.expenseCalculatorService = expenseCalculatorService;
		this.objectMapper = objectMapper;
		this.producer = producer;
        this.healthBillReportGenerator = healthBillReportGenerator;
        this.redisService = redisService;
        this.expenseCalculatorUtil = expenseCalculatorUtil;
    }

	// Commenting existing consumer
	/*
	@KafkaListener(topics = {"${expense.calculator.consume.topic}"})
	public void listen(final String consumerRecord, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("ExpenseCalculatorConsumer:listen");
		MusterRollRequest request = null;
		try {
			request = objectMapper.readValue(consumerRecord, MusterRollRequest.class);
			expenseCalculatorService.createAndPostWageSeekerBill(request);
		} catch (Exception exception) {
			log.error("Error occurred while processing the consumed muster record from topic : " + topic, exception);
			final MusterRollConsumerError error = MusterRollConsumerError.builder()
					.musterRollRequest(request)
					.exception(exception)
					.build();
			producer.push(configs.getCalculatorErrorTopic(),error);
		}
	}
	@KafkaListener(topics = {"${expense.billing.bill.create}", "${expense.billing.bill.update}"})
	public void listenBill(final String consumerRecord, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		log.info("ExpenseCalculatorConsumer:listenBill");
		BillRequest request = null;
		try {
			request = objectMapper.readValue(consumerRecord, BillRequest.class);
			expenseCalculatorService.processBillForAdditionalDetailsEnrichment(request);
		} catch (Exception exception) {
			log.error("Error occurred while processing the consumed muster record from topic : " + topic, exception);
		}
	}
	 */

	/**
	 * Bill Report Consumer to generate the report after generating the bill
	 * Supports both V1 and V2 bills - preserves V2 metadata while adding report details
	 *
	 * @param consumerRecord
	 * @param topic
	 */
	@KafkaListener(topics = {"${report.generation.trigger.topic}", "${report.generation.retry.trigger.topic}"})
	public void listenBillForReport(final String consumerRecord, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		log.info("ExpenseCalculatorConsumer:listenBillForReport - consuming from topic: {}", topic);

		try {
			ReportGenerationTrigger trigger = objectMapper.readValue(consumerRecord, ReportGenerationTrigger.class);
			log.info("Processing report trigger for billId: {}, tenantId: {}, numberOfBillDetails: {}",
				trigger.getBillId(), trigger.getTenantId(), trigger.getNumberOfBillDetails());

			List<Bill> bills =	expenseCalculatorUtil.fetchBillsWithBillIds(trigger.getRequestInfo(), trigger.getTenantId(), Collections.singletonList(trigger.getBillId()));

			// Validate that bill exists in the system, because of async possible that it's not persisted while consuming the record.
			if (!CollectionUtils.isEmpty(bills) && bills.get(0).getBillDetails().size() == trigger.getNumberOfBillDetails()) {
				Bill bill = bills.get(0);

				// Check if this is a V2 bill by looking at additionalDetails
				boolean isV2Bill = false;
				if (bill.getAdditionalDetails() != null) {
					try {
						Map<String, Object> additionalDetails = objectMapper.convertValue(
							bill.getAdditionalDetails(),
							objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class)
						);
						isV2Bill = additionalDetails.containsKey("billingPeriodId") ||
								   additionalDetails.containsKey("billingType") &&
								   "INTERMEDIATE".equals(additionalDetails.get("billingType"));

						if (isV2Bill) {
							log.info("Bill {} is V2 bill with period: {}, billingPeriodId: {}",
								bill.getId(),
								additionalDetails.get("periodNumber"),
								additionalDetails.get("billingPeriodId"));
						} else {
							log.info("Bill {} is V1 bill", bill.getId());
						}
					} catch (Exception e) {
						log.warn("Could not parse additionalDetails for bill {}, treating as V1", bill.getId());
					}
				}

				/*
				 * If the bill is already present in the cache, then don't generate the report again.
				 * Because this is long-running KAFKA consumer, the same record can be consumed multiple times. This is to prevent duplicate reports from being generated.
				 */
				BillRequest request = BillRequest.builder().requestInfo(trigger.getRequestInfo()).bill(bill).build();
				log.info("Bill exists for bill id: {} (V2: {})", bill.getId(), isV2Bill);

				if (redisService.isBillIdPresentInCache(bill.getId())) {
					log.info("Bill {} already in cache, skipping report generation to prevent duplicate", bill.getId());
					return;
				}

				redisService.setCacheForBillReport(bill.getId());
				log.info("Starting report generation for bill: {} (V2: {})", bill.getId(), isV2Bill);
				healthBillReportGenerator.generateHealthBillReportRequest(request);
			}
			else if (System.currentTimeMillis() - trigger.getCreatedTime() < 30 * 60 * 1000) {
				// Consumer will retry till 30 minutes after the creation of the bill
				// If bill does not exist, retry for 10 seconds
				long elapsedMinutes = (System.currentTimeMillis() - trigger.getCreatedTime()) / (60 * 1000);
				log.info("Bill {} does not exist or bill details not fully persisted (elapsed: {} minutes), retrying in 10 seconds. Expected: {} details, Found: {}",
					trigger.getBillId(),
					elapsedMinutes,
					trigger.getNumberOfBillDetails(),
					!CollectionUtils.isEmpty(bills) ? bills.get(0).getBillDetails().size() : 0);
				Thread.sleep(10 * 1000);
				producer.push(configs.getReportGenerationRetryTriggerTopic(), trigger);
			} else {
				// Post 30 minutes after the creation of the bill if it's still not exists then throw exception
				String errorMsg = String.format(
					"Bill %s does not exist or details not persisted after 30 minutes. Expected: %d details, Found: %d",
					trigger.getBillId(),
					trigger.getNumberOfBillDetails(),
					!CollectionUtils.isEmpty(bills) ? bills.get(0).getBillDetails().size() : 0
				);
				log.error(errorMsg);
				throw new Exception(errorMsg);
			}
		} catch (Exception exception) {
			log.error("Error occurred while processing the report from topic: {} for record: {}",
				topic, consumerRecord, exception);
			producer.push(configs.getReportErrorQueueTopic(), exception.getMessage() + " : " + consumerRecord);
		}
	}


	@KafkaListener(topics = {"${bill.generation.async.topic}"})
	public void listenBillGeneration(final String consumerRecord, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		log.info("ExpenseCalculatorConsumer:listen Bill Generation");
		CalculationRequest calculationRequest = null;
		try {
			calculationRequest = objectMapper.readValue(consumerRecord, CalculationRequest.class);
			expenseCalculatorService.createWageOrSupervisionBills(calculationRequest);
		} catch (Exception exception) {
			log.error("Error occurred while processing the report from topic : " + topic, exception);
			producer.push(configs.getCalculatorErrorTopic(), exception.getMessage() + " : " + consumerRecord);
		}
	}
}
