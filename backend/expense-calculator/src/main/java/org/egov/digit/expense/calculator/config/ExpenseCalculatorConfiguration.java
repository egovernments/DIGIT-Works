package org.egov.digit.expense.calculator.config;

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
public class ExpenseCalculatorConfiguration {

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

	@Value("${egov.idgen.supervision.reference.number}")
	private String idGenSupervisionBillFormat;

	//Localization Config
	@Value("${egov.localization.host}")
	private String localizationServiceHost;

	@Value("${egov.localization.context.path}")
	private String localizationServiceContextPath;

	@Value("${egov.localization.search.endpoint}")
	private String localizationServiceEndpoint;

	// Workflow Config
	@Value("${egov.workflow.host}")
	private String wfHost;

	@Value("${egov.workflow.transition.path}")
	private String wfTransitionPath;

	@Value("${egov.workflow.businessservice.search.path}")
	private String wfBusinessServiceSearchPath;

	@Value("${egov.workflow.processinstance.search.path}")
	private String wfProcessInstanceSearchPath;

	// MDMS
	@Value("${egov.mdms.host}")
	private String mdmsHost;

	@Value("${egov.mdms.search.endpoint}")
	private String mdmsEndPoint;

	// MDMS V2
	@Value("${egov.mdms.V2.host}")
	private String mdmsV2Host;

	@Value("${egov.mdms.search.V2.endpoint}")
	private String mdmsV2EndPoint;

	// MusterRoll
	@Value("${egov.musterroll.host}")
	private String musterRollHost;

	@Value("${egov.musterroll.search.endpoint}")
	private String musterRollEndPoint;

	@Value("${egov.musterroll.search.v2.endpoint}")
	private String musterRollEndV2Point;

	//Organisation Service
	@Value("${egov.organisation.host}")
	private String organisationServiceHost;

	@Value("${egov.organisation.endpoint}")
	private String organisationServiceEndpoint;

	// Contract service
	@Value("${egov.contract.service.host}")
	private String contractHost;

	@Value("${egov.contract.service.search.endpoint}")
	private String contractSearchEndPoint;

	// Expense bill service
	@Value("${egov.bill.host}")
	private String billHost;

	@Value("${egov.bill.create.endpoint}")
	private String billCreateEndPoint;

	@Value("${egov.bill.update.endpoint}")
	private String billUpdateEndPoint;

	@Value("${egov.expense.bill.service.search.endpoint}")
	private String expenseBillSearchEndPoint;

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

	//kafka
	@Value("${expense.calculator.error.topic}")
	private String calculatorErrorTopic;

	@Value("${expense.calculator.create.bill.topic}")
	private String calculatorCreateBillTopic;

	// SMSNotification
	@Value("${egov.sms.notification.topic}")
	private String smsNotificationTopic;

	//Expense calculator Service specific
	@Value("${egov.works.expense.wage.head.code}")
	private String wageHeadCode;

	@Value("${egov.works.expense.wage.labour.charge.unit}")
	private String wageLabourChargeUnit;

	@Value("${egov.works.expense.wage.payee.type}")
	private String wagePayeeType;

	@Value("${egov.works.expense.wage.business.service}")
	private String wageBusinessService;

	@Value("${egov.works.expense.purchase.business.service}")
	private String purchaseBusinessService;

	@Value("${egov.works.expense.supervision.business.service}")
	private String supervisionBusinessService;
	
	@Value("${works.wages.master.category}")
	private String wagesMasterCategory;

	@Value("${egov.works.expense.payer.type}")
	private String payerType;
	
	@Value("${project.service.host}")
	private String projectHost;
	
	@Value("${project.search.path}")
	private String projectSearchPath;

	//KAFKA topic
	@Value("${expense.calculator.create.topic}")
	private String calculatorCreateTopic;
	
	//Purchase bill referenceId IDGen format key
	@Value("${egov.works.expense.purchasebill.referenceId.format}")
	private String purchaseBillReferenceIdFormatKey;
	
	//Supervision bill referenceId IDGen format key
	@Value("${egov.works.expense.superbill.referenceId.format}")
	private String supervisionBillreferenceIdFormatKey;

	@Value("${egov.works.expense.wagebill.referenceId.format}")
	private String wageBillreferenceIdFormatKey;

	//search configs
	@Value("${expense.billing.default.limit}")
	private Integer defaultLimit;

	@Value("${expense.billing.default.offset}")
	private Integer defaultOffset;

	@Value("${expense.billing.search.max.limit}")
	private Integer maxLimit;

	@Value("${works.estimate.host}")
	private String estimateHost;

	@Value("${works.estimate.search.endpoint}")
	private String estimateEndpoint;

	@Value("${expense.billing.bill.index}")
	private String billIndexTopic;

	@Value("${is.health.integration.enabled}")
	private boolean isHealthIntegrationEnabled;

	//Individual
	@Value("${egov.individual.host}")
	private String individualHost;

	@Value("${egov.individual.search.endpoint}")
	private String individualSearchEndPoint;


	//Attendance service
	@Value("${works.attendance.log.host}")
	private String attendanceLogHost;
	@Value("${works.attendance.register.search.endpoint}")
	private String attendanceRegisterEndpoint;
	@Value("${works.attendance.register.search.limit}")
	private String attendanceRegisterSearchLimit;

	// File store service
	@Value("${egov.filestore.host}")
	private String fileStoreHost;

	@Value("${egov.filestore.path}")
	private String fileStoreEndpoint;

	@Value("${state.level.tenant.id}")
	private String stateLevelTenantId;

	@Value("${egov.pdf.service.host}")
	private String pdfServiceHost;

	@Value("${egov.pdf.service.create.endpoint}")
	private String pdfServiceCreateEndpoint;

	@Value("${payment.pdf.key}")
	private String paymentPdfKey;

	@Value("${report.localization.module.name}")
	private String reportLocalizationModuleName;

	@Value("${report.localization.boundary.module.name}")
	private String reportLocalizationBoundaryModuleName;

	@Value("${report.localization.locale.code}")
	private String reportLocalizationLocaleCode;

	@Value("${report.header.title}")
	private String reportHeaderTitle;

	@Value("${report.date.time.format}")
	private String reportDateTimeFormat;

	@Value("${report.date.time.zone}")
	private String reportDateTimeZone;

	@Value("${report.error.queue.topic}")
	private String reportErrorQueueTopic;
	@Value("${report.beneficiary.identifier.type}")
	private String reportBeneficiaryIdentifierType;

	@Value("${egov.boundary.host}")
	private String boundaryServiceHost;

	@Value("${egov.boundary.search.url}")
	private String boundarySearchUrl;

	@Value("${is.attendance.approval.required}")
	private boolean isAttendanceApprovalRequired;

	@Value("${register.batch.size}")
	private Integer registerBatchSize;

	@Value("${bill.generation.async.enabled}")
	private boolean isBillGenerationAsyncEnabled;

	@Value("${bill.generation.async.topic}")
	private String billGenerationAsyncTopic;

}
