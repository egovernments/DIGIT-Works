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
import java.util.List;

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
	 * @param consumerRecord
	 * @param topic
	 */
	@KafkaListener(topics = {"${report.generation.trigger.topic}", "${report.generation.retry.trigger.topic}"})
	public void listenBillForReport(final String consumerRecord, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		log.info("ExpenseCalculatorConsumer:listenBillForReport");

		try {
			ReportGenerationTrigger trigger = objectMapper.readValue(consumerRecord, ReportGenerationTrigger.class);
			List<Bill> bills =	expenseCalculatorUtil.fetchBillsWithBillIds(trigger.getRequestInfo(), trigger.getTenantId(), Collections.singletonList(trigger.getBillId()));

			// Validate that bill exists in the system, because of async possible that it's not persisted while consuming the record.
			if (!CollectionUtils.isEmpty(bills) && bills.get(0).getBillDetails().size() == trigger.getNumberOfBillDetails()) {
				/*
				 * If the bill is already present in the cache, then don't generate the report again.
				 * Because this is long-running KAFKA consumer, the same record can be consumed multiple times. This is to prevent duplicate reports from being generated.
				 */
				BillRequest request = BillRequest.builder().requestInfo(trigger.getRequestInfo()).bill(bills.get(0)).build();
				log.info("Bill exists for bill id " + request.getBill().getId());
				if (redisService.isBillIdPresentInCache(request.getBill().getId())) {
					return;
				}
				redisService.setCacheForBillReport(request.getBill().getId());
				healthBillReportGenerator.generateHealthBillReportRequest(request);
			}
			else if (System.currentTimeMillis() - trigger.getCreatedTime() < 30 * 60 * 1000) {
				// Consumer will retry till 30 minutes after the creation of the bill
				// If bill does not exist, retry for 10 seconds
				log.info("Bill does not exist, retrying for 10 seconds");
				Thread.sleep(10 * 1000);
				producer.push(configs.getReportGenerationRetryTriggerTopic(), trigger);
			} else {
				// Post 30 minutes after the creation of the bill if it's still not exists then throw exception
				throw new Exception("Bill does not exist");
			}
		} catch (Exception exception) {
			log.error("Error occurred while processing the report from topic : " + topic, exception);
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
