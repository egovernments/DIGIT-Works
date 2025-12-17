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

	// MusterRoll
	@Value("${egov.musterroll.host}")
	private String musterRollHost;

	@Value("${egov.musterroll.search.endpoint}")
	private String musterRollEndPoint;

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

	@Value("${notification.sms.enabled}")
	private boolean isSMSEnabled;

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

	@Value("${egov.mdms.v2.host}")
	private String mdmsV2Host;

	@Value("${egov.mdms.v2.search.endpoint}")
	private String mdmsV2EndPoint;

        @Value("${works.estimate.host}")
	private String estimateHost;

	@Value("${works.estimate.search.endpoint}")
	private String estimateEndpoint;

	@Value("${expense.billing.bill.index}")
	private String billIndexTopic;

       @Value("${kafka.topics.works.notification.sms.name}")
	private String muktaNotificationTopic;
	@Value("${sms.isAdditonalFieldRequired}")
	private boolean isAdditonalFieldRequired;


}
