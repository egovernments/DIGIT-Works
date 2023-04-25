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

	@Value("${egov.idgen.supervision.bill.number}")
	private String idGenSupervisionBillFormat;

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

	@Value("${egov.works.expense.wage.lineitem.type}")
	private String wageLineItemType;

	@Value("${egov.works.expense.payer.type}")
	private String payerType;

	//KAFKA topic
	@Value("${expense.calculator.create.topic}")
	private String calculatorCreateTopic;

}
