package org.egov.digit.expense.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {
	
	
	public static final String MODULE_NAME = "ExpenseService";
	
	public static final String HEADCODE_CODE_FILTER = "$.MdmsRes.ExpenseService.HeadCodeMaster.*.code";
	
	public static final String HEADCODE_MASTERNAME = "HeadCodeMaster";
	
	public static final List<String> MDMS_MASTER_NAMES = Collections
			.unmodifiableList(Arrays.asList(HEADCODE_MASTERNAME));
	
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
	
	public static final String BILL_QUERY = "SELECT "
			
			+ " bill.id as b_id, bill.tenantid as b_tenantid, billdate, duedate, netpayableamount,"
			+ " netpaidamount, businessservice, bill.referenceid as b_referenceid, bill.fromperiod as b_from, bill.toperibill., bill.status, "
			+ " bill.paymentstatus, bill.createdby as b_createdby, bill.createdtime as b_createdtime, bill.lastmodifiedby as b_lastmodifiedby,"
			+ " bill.lastmodifiedtime as lastmodifiedtime, bill.additionaldetails as b_additionaldetails,"
			
			+ " bd.id as billdetailid, bd.tenantid as bd_tenantid, bd.referenceid as bd_referenceid, billid, bd.paymentstatus as bd_paymentstatus, bd.fromperiod as bd_fromperiod, "
			+ " bd.toperiod as bd_toperiod, bd.createdby as bd_createdby, bd.createdtime as bd_createdtime, bd.lastmodifiedby as bd_lastmodifiedby,"
			+ " bd.lastmodifiedtime as bd_lastmodifiedtime, bd.additionaldetails as bd_additionaldetails,"
			
			+ " li.id as line_id, billdetailid, li.tenantid as line_tenantid,"
			+ " headcode, amount, paidamount, type, li.status as line_status, islineitempayable, li.createdby as line_createdby, li.createdtime as line_createdtime,"
			+ " li.lastmodifiedby as line_lastmodifiedby, li.lastmodifiedtime as line_lastmodifiedtime, li.additionaldetails as line_additionaldetails "
			
			+ " payee.id as payee_id, payee.tenantid as payee_tenantid, payee.type as payee_type, payee.identifier as payee_identifier, payee.parentid as payee_parentid, "
			+ " payee.createdby as payee_createdby, payee.createdtime as payee_createdtime, payee.lastmodifiedby as payee_lastmodifiedby,"
			+ " payee.lastmodifiedtime as payee_lastmodifiedtime, payee.additionaldetails as payee_additionaldetails, "
			
			+ " payer.id as payer_id, payer.tenantid as payer_tenantid, payer.type as payer_type, payer.identifier as payer_identifier, payer.parentid as payer_parentid, "
			+ " payer.createdby as payer_createdby, payer.createdtime as payer_createdtime, payer.lastmodifiedby as payer_lastmodifiedby, "
			+ "	payer.lastmodifiedtime as payer_lastmodifiedtime, payer.additionaldetails as payer_additionaldetails "
			
			+ "	FROM eg_expense_bill bill "
			
			+ INNER_JOIN + " EG_EXPENSE_PARTY PAYER 	ON bill.id = payer.parentid AND bill.tenantid = payer.tenantid "
			
			+ INNER_JOIN + " EG_EXPENSE_BILLLDETAIL BD 	ON bill.id = bd.billid AND bd.tenantid = bill.tenantid "
			
			+ INNER_JOIN + " EG_EXPENSE_LINEITEM LI 	ON bd.id = li.billdetailid AND bd.tenantid = li.tenantid "
			
			+ INNER_JOIN + " EG_EXPENSE_PARTY PAYEE 	ON bd.id = payee.parentid AND bd.tenantid = payee.tenantid "
			
			+ WHERE;
	
}
