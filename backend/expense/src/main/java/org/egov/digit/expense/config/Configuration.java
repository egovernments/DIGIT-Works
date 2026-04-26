package org.egov.digit.expense.config;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Data
@Import({ TracerConfiguration.class })
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Configuration {

	// service kafka topics

	@Value("${expense.billing.bill.create}")
	private String billCreateTopic;

	@Value("${expense.billing.bill.update}")
	private String billUpdateTopic;

	@Value("${expense.billing.payment.create}")
	private String paymentCreateTopic;

	@Value("${expense.billing.payment.update}")
	private String paymentUpdateTopic;

	// User Config
	@Value("${egov.user.host}")
	private String userHost;

	@Value("${egov.user.context.path}")
	private String userContextPath;

	@Value("${egov.user.create.path}")
	private String userCreateEndpoint;

	@Value("${egov.user.search.path}")
	private String userSearchEndpoint;

	@Value("${egov.user.update.path}")
	private String userUpdateEndpoint;

	// Idgen Config
	@Value("${egov.idgen.host}")
	private String idGenHost;

	@Value("${egov.idgen.path}")
	private String idGenPath;

	@Value("${egov.idgen.works.wage.bill.number.name}")
	private String wageBillNumberName;

	@Value("${egov.idgen.works.wage.bill.number.format}")
	private String wageBillNumberFormat;

	@Value("${egov.idgen.works.purchase.bill.number.name}")
	private String purchaseBillNumberName;

	@Value("${egov.idgen.works.purchase.bill.number.format}")
	private String purchaseBillNumberFormat;

	@Value("${egov.idgen.works.supervision.bill.number.name}")
	private String supervisionBillNumberName;

	@Value("${egov.idgen.works.supervision.bill.number.format}")
	private String supervisionBillNumberFormat;

	// Workflow Config
	@Value("${egov.workflow.host}")
	private String wfHost;

	@Value("${egov.workflow.transition.path}")
	private String wfTransitionPath;

	@Value("${egov.workflow.businessservice.search.path}")
	private String wfBusinessServiceSearchPath;

	@Value("${egov.workflow.processinstance.search.path}")
	private String wfProcessInstanceSearchPath;
	
	@Value("#{${business.workflow.status.map}}")
	private Map<String, Boolean> businessServiceWorkflowStatusMap;
	
	@Value("${expense.workflow.module.name}")
	private String expenseWorkflowModuleName;

	// MDMS
	@Value("${egov.mdms.host}")
	private String mdmsHost;

	@Value("${egov.mdms.search.endpoint}")
	private String mdmsEndPoint;

	//MDMS V2
	@Value("${egov.mdms.v2.host}")
	private String mdmsV2Host;

	@Value("${egov.mdms.v2.search.endpoint}")
	private String mdmsV2EndPoint;

	// HRMS
	@Value("${egov.hrms.host}")
	private String hrmsHost;

	@Value("${egov.hrms.search.endpoint}")
	private String hrmsEndPoint;

	// URLShortening
	@Value("${egov.url.shortner.host}")
	private String urlShortnerHost;

	@Value("${egov.url.shortner.endpoint}")
	private String urlShortnerEndpoint;

	// SMSNotification
	@Value("${egov.sms.notification.topic}")
	private String smsNotificationTopic;

	// Email Notification
	@Value("${expense.email.notification.topic:egov.core.notification.email}")
	private String emailNotificationTopic;
	
	// bill search configs
    @Value("${expense.billing.default.limit}")
    private Integer defaultLimit;

    @Value("${expense.billing.default.offset}")
    private Integer defaultOffset;
    
    @Value("${expense.billing.search.max.limit}")
    private Integer maxSearchLimit;
    
    @Value("${expense.payment.default.status}")
	public String defaultPaymentStatus;

	@Value("${expense.reference.default.status}")
	public String defaultReferenceStatus;

	// Project Service
	@Value("${works.project.host}")
	private String projectServiceHost;

	@Value("${works.project.staff.endpoint:/project/staff/v1/_search}")
	private String projectStaffEndpoint;

	//Organisation Service
	@Value("${works.organisation.host}")
	private String organisationServiceHost;

	@Value("${works.organisation.endpoint}")
	private String organisationServiceEndpoint;

	//Localization Config
	@Value("${egov.localization.host}")
	private String localizationServiceHost;

	@Value("${egov.localization.context.path}")
	private String localizationServiceContextPath;

	@Value("${egov.localization.search.endpoint}")
	private String localizationServiceEndpoint;

	@Value("${localization.default.locale:en_MZ}")
	private String localizationDefaultLocale;

	@Value("${localization.module.expense.template:expense}")
	private String templateLocalizationModule;

	@Value("${localization.module.notification:rainmaker-common-masters}")
	private String notificationLocalizationModule;

	@Value("${localization.module.email.notification:expense-notification}")
	private String emailNotificationLocalizationModule;

	@Value("${localization.module.payment.advisory:expense}")
	private String paymentAdvisoryLocalizationModule;

	@Value("${excel.sheet.protect.password:readonly}")
	private String excelSheetProtectPassword;

	@Value("${filestore.module:expense}")
	private String filestoreModule;

	//Contract Service
	@Value("${works.contract.host}")
	private String contractServiceHost;

	@Value("${works.contract.endpoint}")
	private String contractServiceEndpoint;

	//Individual Service
	@Value("${works.individual.host}")
	private String individualServiceHost;

	@Value("${works.individual.endpoint}")
	private String individualServiceEndpoint;

	@Value("${is.health.context.enabled}")
	private boolean isHealthContextEnabled;

	//Worker Registry Service
	@Value("${works.worker.registry.host}")
	private String workerRegistryHost;

	@Value("${works.worker.registry.endpoint}")
	private String workerRegistryEndpoint;

	@Value("${works.worker.registry.search.page.size:100}")
	private int workerRegistrySearchPageSize;

	@Value("${expense.v2.periodic.billing.enabled:true}")
	private boolean isV2PeriodicBillingEnabled;

	@Value("${bill.persistence.breakdown.enabled:true}")
	private boolean isBillBreakdownEnabled;

	@Value("${bill.persistence.breakdown.size:200}")
	private Integer billBreakdownSize;

	@Value("${mtn.base.url}")
	private String baseUrlMTN;

	@Value("${mtn.token.endpoint}")
	private String tokenEndpointMTN;

	@Value("${mtn.subscription.key}")
	private String subscriptionKeyMTN;

	@Value("${mtn.basic.authorization}")
	private String authorizationMTN;

	@Value("${mtn.target.environment}")
	private String targetEnvironmentMTN;

	@Value("${mtn.account.active.endpoint}")
	private String accountEndpointMTN;

	@Value("${mtn.user.info.endpoint}")
	private String basicUserInfoEndpointMTN;

	@Value("${expense.bill.task}")
	private String billTaskTopic;

	@Value("${expense.bill.task.details}")
	private String billTaskDetailsTopic;

	@Value("${expense.task.status.update}")
	private String taskUpdateTopic;

	@Value("${expense.task.details.update}")
	private String taskDetailsUpdateTopic;

	@Value("${mtn.transfer.endpoint}")
	private String transferEndpointMTN;

	@Value("${mtn.transfer.status.endpoint}")
	private String transferStatusEndpointMTN;

	@Value("${mtn.amount.balance.endpoint}")
	private String amountBalanceEndpointMTN;

	@Value("${mtn.payment.currency}")
	private String paymentCurrency;

	@Value("${mtn.payment.partyIdType}")
	private String partyIdType;

	@Value("${mtn.payment.token.expiry.interval.millisec}")
	private String tokenExpiryInterval;

	@Value("${mtn.payment.phone.code.prefix}")
	private String phoneCodePrefix;

	@Value("${mtn.api.mock.enabled:false}")
	private boolean mtnApiMockEnabled;

	@Value("${bill.business.service}")
	private String billBusinessService;

	@Value("${bill.detail.business.service}")
	private String billDetailBusinessService;


	@Value("${expense.calculator.host}")
	private String calculatorHost;

	@Value("${expense.calculator.calculate.endpoint}")
	private String calculatePath;

	@Value("${expense.payment.additional.percent}")
	private BigDecimal additionalAmountPercent;

	// Bill Report Topics
	@Value("${expense.bill.report.save}")
	private String billReportSaveTopic;

	@Value("${expense.bill.report.update}")
	private String billReportUpdateTopic;

	// Filestore
	@Value("${egov.filestore.host}")
	private String filestoreHost;

	@Value("${egov.filestore.upload.endpoint:/filestore/v1/files}")
	private String filestoreUploadEndpoint;

	@Value("${egov.filestore.download.endpoint:/filestore/v1/files/id}")
	private String filestoreDownloadEndpoint;

	// Report Regeneration Trigger — published on bill updates for health-expense-calculator
	@Value("${report.generation.trigger.topic}")
	private String reportRegenerationTriggerTopic;

	// Generic Scheduler Configuration
	@Value("${task.scheduler.batch.size:100}")
	private int schedulerBatchSize;

	@Value("${task.scheduler.max.attempts:200}")
	private int schedulerMaxAttempts;

	@Value("${task.scheduler.min.interval.ms:5000}")
	private long schedulerMinIntervalMs;

	@Value("${task.scheduler.max.interval.ms:30000}")
	private long schedulerMaxIntervalMs;

	@Value("${task.scheduler.initial.delay.ms:10000}")
	private long schedulerInitialDelayMs;

	@Value("${task.scheduler.max.duration.ms:86400000}")
	private long schedulerMaxDurationMs;

	@Value("${task.scheduler.stuck.threshold.ms:180000}")
	private long schedulerStuckThresholdMs;

	@Value("${task.scheduler.cleanup.after.ms:604800000}")
	private long schedulerCleanupAfterMs;

	@Value("${task.scheduler.recovery.interval.ms:120000}")
	private long schedulerRecoveryIntervalMs;

	@Value("${task.scheduler.cleanup.interval.ms:3600000}")
	private long schedulerCleanupIntervalMs;

	@Value("${task.scheduler.backoff.base.ms:30000}")
	private long schedulerBackoffBaseMs;

	@Value("${task.scheduler.backoff.multiplier:1.5}")
	private double schedulerBackoffMultiplier;

	@Value("${task.scheduler.backoff.max.ms:300000}")
	private long schedulerBackoffMaxMs;

	/**
	 * Comma-separated list of tenant IDs to pre-register in the scheduler registry at startup.
	 * Ensures PENDING jobs in DB are picked up after a full cluster restart, without waiting
	 * for a new transfer request to re-register the tenant.
	 * Example: ng.narayi,ng.gombe
	 */
	@Value("${task.scheduler.bootstrap.tenants:}")
	private String schedulerBootstrapTenantsRaw;

	@Value("${wf.transition.retry.max.attempts:3}")
	private int wfTransitionRetryMaxAttempts;

	@Value("${wf.transition.retry.initial.delay.ms:200}")
	private long wfTransitionRetryInitialDelayMs;

	/**
	 * Delay before a safety-net BILL_STATUS_POLL job fires.
	 * Should be long enough for all per-detail jobs + any external API calls to complete.
	 * Default: 60 seconds.
	 */
	@Value("${task.scheduler.safety.net.delay.ms:60000}")
	private long schedulerSafetyNetDelayMs;

public List<String> getSchedulerBootstrapTenants() {
		if (schedulerBootstrapTenantsRaw == null || schedulerBootstrapTenantsRaw.isBlank()) {
			return Collections.emptyList();
		}
		return List.of(schedulerBootstrapTenantsRaw.split(","))
				.stream()
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.toList();
	}

}
