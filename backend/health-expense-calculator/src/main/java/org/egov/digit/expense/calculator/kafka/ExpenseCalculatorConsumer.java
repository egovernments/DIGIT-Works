package org.egov.digit.expense.calculator.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.models.project.ProjectRequest;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.service.BillingConfigurationService;
import org.egov.digit.expense.calculator.service.ExpenseCalculatorService;
import org.egov.digit.expense.calculator.service.HealthBillReportGenerator;
import org.egov.digit.expense.calculator.service.RedisService;
import org.egov.digit.expense.calculator.util.ExpenseCalculatorUtil;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillRequest;
import org.egov.digit.expense.calculator.web.models.CalculationRequest;
import org.egov.digit.expense.calculator.web.models.ReportGenerationTrigger;
import org.egov.tracer.model.CustomException;
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

	private static final int MAX_REPORT_GENERATION_RETRIES = 3;

	private final ExpenseCalculatorConfiguration configs;
	private final ExpenseCalculatorService expenseCalculatorService;
	private final ObjectMapper objectMapper;
	private final ExpenseCalculatorProducer producer;
	private final HealthBillReportGenerator healthBillReportGenerator;
	private final RedisService redisService;
	private final ExpenseCalculatorUtil expenseCalculatorUtil;
	private final BillingConfigurationService billingConfigurationService;

	@Autowired
	public ExpenseCalculatorConsumer(ExpenseCalculatorConfiguration configs, ExpenseCalculatorService expenseCalculatorService,
									 ObjectMapper objectMapper, ExpenseCalculatorProducer producer, HealthBillReportGenerator healthBillReportGenerator,
									 RedisService redisService, ExpenseCalculatorUtil expenseCalculatorUtil,
									 BillingConfigurationService billingConfigurationService) {
		this.configs = configs;
		this.expenseCalculatorService = expenseCalculatorService;
		this.objectMapper = objectMapper;
		this.producer = producer;
        this.healthBillReportGenerator = healthBillReportGenerator;
        this.redisService = redisService;
        this.expenseCalculatorUtil = expenseCalculatorUtil;
        this.billingConfigurationService = billingConfigurationService;
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

		String processingBillId = null;
		ReportGenerationTrigger trigger = null;
		try {
			trigger = objectMapper.readValue(consumerRecord, ReportGenerationTrigger.class);
			long triggerAge = System.currentTimeMillis() - trigger.getCreatedTime();
			log.info("Processing report trigger for billId: {}, tenantId: {}, numberOfBillDetails: {}, triggerCreatedTime: {}, triggerAge: {} seconds",
				trigger.getBillId(), trigger.getTenantId(), trigger.getNumberOfBillDetails(),
				trigger.getCreatedTime(), triggerAge / 1000);

			log.info("Searching for bill with ID: {} in expense service", trigger.getBillId());
			List<Bill> bills =	expenseCalculatorUtil.fetchBillsWithBillIds(trigger.getRequestInfo(), trigger.getTenantId(), Collections.singletonList(trigger.getBillId()));
			log.info("Search completed for bill ID: {}. Found {} bills", trigger.getBillId(), bills != null ? bills.size() : 0);

			// Validate that bill exists in the system, because of async possible that it's not persisted while consuming the record.
			if (!CollectionUtils.isEmpty(bills) && bills.get(0).getBillDetails().size() == trigger.getNumberOfBillDetails()) {
				Bill bill = bills.get(0);

				// Check if report generation is already in progress for a previous update
				// If INITIATED and forceRegenerate=true, wait for the prior run to finish before retrying
				String currentReportStatus = extractReportStatus(bill);
				if ("INITIATED".equals(currentReportStatus) && !Boolean.TRUE.equals(trigger.getForceRegenerate())) {
					log.info("Report generation already INITIATED for bill {}. Waiting for prior run to complete.", bill.getId());
					Thread.sleep(15 * 1000);
					producer.push(configs.getReportGenerationRetryTriggerTopic(), trigger);
					return;
				}

				log.info("Bill found with matching details count. BillId: {}, billNumber: {}, actualBillDetails: {}, expectedBillDetails: {}",
						bill.getId(), bill.getBillNumber(),
						bill.getBillDetails().size(), trigger.getNumberOfBillDetails());

				// Check if this is a V2 bill by looking at additionalDetails
				boolean isV2Bill = false;
				if (bill.getAdditionalDetails() != null) {
					try {
						Map<String, Object> additionalDetails = objectMapper.convertValue(
							bill.getAdditionalDetails(),
							objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class)
						);
						isV2Bill = additionalDetails.containsKey("billingPeriodId") ||
								   (additionalDetails.containsKey("billingType") &&
								   "INTERMEDIATE".equals(additionalDetails.get("billingType")));

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

				// Ensure bill has tenantId set (in case it's missing from expense service response)
				if (bill.getTenantId() == null || bill.getTenantId().isEmpty()) {
					log.warn("Bill {} has null/empty tenantId, setting from trigger: {}", bill.getId(), trigger.getTenantId());
					bill.setTenantId(trigger.getTenantId());
				}

				BillRequest request = BillRequest.builder().requestInfo(trigger.getRequestInfo()).bill(bill).build();
				log.info("Bill exists and validated for bill id: {} (V2: {}), tenantId: {}", bill.getId(), isV2Bill, bill.getTenantId());

				// If force regeneration requested (bill was updated), evict cache to allow re-processing
				if (Boolean.TRUE.equals(trigger.getForceRegenerate())) {
					log.info("Force regeneration requested for billId: {} — evicting Redis cache", bill.getId());
					redisService.evict(bill.getId());
				}

				// Atomic operation to prevent concurrent duplicate processing
				if (!redisService.setCacheIfAbsent(bill.getId())) {
					log.info("Bill {} already in cache, skipping report generation to prevent duplicate", bill.getId());
					return;
				}

				processingBillId = bill.getId();
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
				throw new CustomException("BILL_NOT_FOUND", errorMsg);
			}
		} catch (Exception exception) {
			log.error("Error occurred while processing the report from topic: {} for record: {}",
				topic, consumerRecord, exception);
			// Evict Redis cache so the trigger can be reprocessed on retry
			if (processingBillId != null) {
				redisService.evict(processingBillId);
				log.info("Evicted Redis cache for bill {} to allow retry", processingBillId);
			}
			// Retry the trigger up to MAX_REPORT_GENERATION_RETRIES times
			if (trigger != null && trigger.getRetryCount() < MAX_REPORT_GENERATION_RETRIES) {
				ReportGenerationTrigger retryTrigger = ReportGenerationTrigger.builder()
						.requestInfo(trigger.getRequestInfo())
						.tenantId(trigger.getTenantId())
						.billId(trigger.getBillId())
						.createdTime(trigger.getCreatedTime())
						.numberOfBillDetails(trigger.getNumberOfBillDetails())
						.forceRegenerate(trigger.getForceRegenerate())
						.retryCount(trigger.getRetryCount() + 1)
						.build();
				log.info("Scheduling report generation retry {}/{} for billId: {}",
						retryTrigger.getRetryCount(), MAX_REPORT_GENERATION_RETRIES, trigger.getBillId());
				producer.push(configs.getReportGenerationRetryTriggerTopic(), retryTrigger);
			} else {
				String errorDetail = exception instanceof CustomException
					? ((CustomException) exception).getCode() + ": " + exception.getMessage()
					: exception.getMessage();
				log.error("Report generation exhausted {} retries for billId: {}. Sending to error queue.",
						MAX_REPORT_GENERATION_RETRIES, trigger != null ? trigger.getBillId() : "unknown");
				producer.push(configs.getReportErrorQueueTopic(), errorDetail + " : " + consumerRecord);
			}
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

	/**
	 * Listens to the project update topic (same as attendance service) to sync
	 * BillingConfig and BillingPeriod records when campaign dates change.
	 *
	 * Only root projects (parent == null) carry campaign-level date changes.
	 * Child projects (per locality) are handled by the attendance service.
	 */
	@KafkaListener(topicPattern = "${project.management.system.kafka.update.topic}")
	public void listenProjectUpdate(final Map<String, Object> consumerRecord,
									@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		log.info("ExpenseCalculatorConsumer:listenProjectUpdate from topic: {}", topic);
		try {
			ProjectRequest projectRequest = objectMapper.convertValue(consumerRecord, ProjectRequest.class);
			if (projectRequest.getProjects() == null || projectRequest.getProjects().isEmpty()) {
				log.warn("Received empty project list on topic: {}", topic);
				return;
			}
			String userId = extractUserId(projectRequest);
			projectRequest.getProjects().stream()
				.filter(p -> p.getParent() == null)
				.forEach(rootProject -> {
					try {
						billingConfigurationService.handleCampaignDateUpdate(rootProject, userId);
					} catch (CustomException e) {
						log.error("Campaign date update blocked for project {}: {} — {}",
							rootProject.getId(), e.getCode(), e.getMessage());
					} catch (Exception e) {
						log.error("Error handling campaign date update for project {}", rootProject.getId(), e);
					}
				});
		} catch (Exception exception) {
			log.error("Error processing project update event from topic: {}", topic, exception);
		}
	}

	@SuppressWarnings("unchecked")
	private String extractReportStatus(Bill bill) {
		try {
			if (bill.getAdditionalDetails() == null) return null;
			Map<String, Object> details = objectMapper.convertValue(
					bill.getAdditionalDetails(),
					objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class));
			Object reportDetailsObj = details.get("reportDetails");
			if (reportDetailsObj == null) return null;
			Map<String, Object> reportDetails = objectMapper.convertValue(
					reportDetailsObj,
					objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class));
			Object status = reportDetails.get("status");
			return status != null ? status.toString() : null;
		} catch (Exception e) {
			log.warn("Could not extract report status from bill {}", bill.getId());
			return null;
		}
	}

	private String extractUserId(ProjectRequest projectRequest) {
		try {
			if (projectRequest.getRequestInfo() != null
					&& projectRequest.getRequestInfo().getUserInfo() != null
					&& projectRequest.getRequestInfo().getUserInfo().getUuid() != null) {
				return projectRequest.getRequestInfo().getUserInfo().getUuid();
			}
		} catch (Exception e) {
			log.warn("Could not extract userId from ProjectRequest, falling back to SYSTEM", e);
		}
		return "SYSTEM";
	}
}
