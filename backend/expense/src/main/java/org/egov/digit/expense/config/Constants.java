package org.egov.digit.expense.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {

	public static final String TENANT_MODULE_NAME = "tenant";

	public static final String TENANT_CODE_FILTER = "$.*.code";

	public static final String TENANT_MASTERNAME = "tenants";
	
	
	public static final String MODULE_NAME = "works";
	
	public static final String HEADCODE_CODE_FILTER = "$.*.code";
	
	public static final String HEADCODE_MASTERNAME = "ExpenseHeads";
	
	public static final List<String> MDMS_MASTER_NAMES = Collections
			.unmodifiableList(Arrays.asList(HEADCODE_MASTERNAME));

	public static final List<String> TENANT_MDMS_MASTER_NAMES = Collections
			.unmodifiableList(Arrays.asList(TENANT_MASTERNAME));
	private static final String INNER_JOIN = "INNER JOIN";
	private static final String WHERE = " WHERE ";
	
	public static final String PAYMENT_QUERY = "SELECT "
			
			+ " payment.id as p_id, payment.tenantid as p_tenantid, netpayableamount, netpaidamount,"
			+ " payment.status, payment.createdby as p_createdby, payment.createdtime as p_createdtime,"
			+ " payment.lastmodifiedby as p_lastmodifiedby, payment.lastmodifiedtime as lastmodifiedtime,"
			+ " payment.additionaldetails as p_additionaldetails, "
			
			+ " billpayment.paymentid,billpayment.billid,billpayment.tenantid as billpayment_tenantid "

			
			+ " FROM eg_expense_payment payment "
			
			+  INNER_JOIN 
			
			+ " eg_expense_bill_payment billpayment "
			
			+ "ON payment.id=billpayment.paymentid AND payment.tenantid=billpayment.tenantid "
			
			+ WHERE;
}
