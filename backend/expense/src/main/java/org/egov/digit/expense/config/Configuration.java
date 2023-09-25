package org.egov.digit.expense.config;

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

	//Contract Service
	@Value("${works.contract.host}")
	private String contractServiceHost;

	@Value("${works.contract.endpoint}")
	private String contractServiceEndpoint;


	@Value("${kafka.topics.works.notification.sms.name}")
	private String muktaNotificationTopic;
	@Value("${sms.isAdditonalFieldRequired}")
	private boolean isAdditonalFieldRequired;

	//Individual Service
	@Value("${works.individual.host}")
	private String individualServiceHost;

	@Value("${works.individual.endpoint}")
	private String individualServiceEndpoint;
}
