package org.egov.digit.expense.repository.rowmapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.egov.digit.expense.web.models.Payment;
import org.egov.digit.expense.web.models.PaymentBill;
import org.egov.digit.expense.web.models.PaymentBillDetail;
import org.egov.digit.expense.web.models.PaymentLineItem;
import org.egov.digit.expense.web.models.enums.PaymentStatus;
import org.egov.digit.expense.web.models.enums.ReferenceStatus;
import org.egov.tracer.model.CustomException;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PaymentRowMapper implements ResultSetExtractor<List<Payment>>{
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public List<Payment> extractData(ResultSet rs) throws SQLException {
		
		Map<String, Payment> paymentMap = new LinkedHashMap<>();
		Map<String, PaymentBill> paymentBillMap = new HashMap<>();
		Map<String, PaymentBillDetail> paymentBillDtlMap = new HashMap<>();

		while (rs.next()) {

			String paymentId = rs.getString("p_id");
			Payment payment = paymentMap.get(paymentId);

			if (payment == null) {
				
				paymentBillMap.clear();
				paymentBillDtlMap.clear();
				
				AuditDetails auditDetails = getAuditDetailsForKey(rs,
						"p_createdby",
						"p_createdTime",
						"p_lastmodifiedby",
						"p_lastmodifiedTime");
						
				payment = Payment.builder()
					.additionalDetails(getadditionalDetail(rs, "p_additionalDetails"))
					.status(PaymentStatus.fromValue(rs.getString("p_status")))
					.referenceStatus(ReferenceStatus.fromValue(rs.getString("p_referencestatus")))
					.netPayableAmount(rs.getBigDecimal("netpayableamount"))
					.netPaidAmount(rs.getBigDecimal("netpaidamount"))
					.paymentNumber(rs.getString("paymentnumber"))
					.tenantId(rs.getString("p_tenantid"))
					.auditDetails(auditDetails)
					.id(paymentId)
					.build();

				paymentMap.put(payment.getId(), payment);
			}
			
			String paymentBillId = rs.getString("b_id");
			PaymentBill bill = paymentBillMap.get(paymentBillId);
			
			if(null == bill) {
				
				AuditDetails billAuditDetails = getAuditDetailsForKey(rs,
						"b_createdby",
						"b_createdTime",
						"b_lastmodifiedby",
						"b_lastmodifiedTime");
				
				 bill = PaymentBill.builder()
						.status(PaymentStatus.fromValue(rs.getString("b_status")))
						.totalPaidAmount(rs.getBigDecimal("b_totalpaidAmount"))
						.totalAmount(rs.getBigDecimal("b_totalamount"))
			     		.tenantId(rs.getString("b_tenantId"))
						.billId(rs.getString("billid"))
						.auditDetails(billAuditDetails)
						.id(paymentBillId)
						.build();
				 
				 paymentBillMap.put(paymentBillId, bill);
				 payment.addBillItem(bill);
			}
			
			String paymentBillDetailId = rs.getString("bd_id");
			PaymentBillDetail billDetail = paymentBillDtlMap.get(paymentBillDetailId);
			
			if(null == billDetail) {
				
				AuditDetails bdAuditDetails = getAuditDetailsForKey(rs,
						"bd_createdby",
						"bd_createdTime",
						"bd_lastmodifiedby",
						"bd_lastmodifiedTime");
				
				billDetail = PaymentBillDetail.builder()
						.status(PaymentStatus.fromValue(rs.getString("bd_status")))
						.totalPaidAmount(rs.getBigDecimal("bd_totalpaidAmount"))
						.totalAmount(rs.getBigDecimal("bd_totalamount"))
						.billDetailId(rs.getString("billDetailId"))
						.tenantId(rs.getString("bd_tenantid"))
						.auditDetails(bdAuditDetails)
						.id(paymentBillDetailId)
						.build();

				paymentBillDtlMap.put(paymentBillDetailId, billDetail);
				bill.addPaymentBillDetailItem(billDetail);
			}
			
			AuditDetails liAuditDetails = getAuditDetailsForKey(rs,
					"li_createdby",
					"li_createdTime",
					"li_lastmodifiedby",
					"li_lastmodifiedTime");
			
			PaymentLineItem lineItem = PaymentLineItem.builder()
					.status(PaymentStatus.fromValue(rs.getString("li_status")))
					.lineItemId(rs.getString("lineitemid"))
					.paidAmount(rs.getBigDecimal("paidamount"))
					.tenantId(rs.getString("li_tenantid"))
					.auditDetails(liAuditDetails)
					.id(rs.getString("li_id"))
					.build();
			
				billDetail.addLineItem(lineItem);
		}
		log.debug("converting map to list object ::: " + paymentMap.values());
		return new ArrayList<>(paymentMap.values());
	}
	
	/**
	 * Fetch audit details from result set for the given keys
	 * 
	 * @param rs
	 * @param createdBy
	 * @param createdTime
	 * @param modifiedBy
	 * @param modifiedTime
	 * @return
	 * @throws SQLException
	 */
	private AuditDetails getAuditDetailsForKey (ResultSet rs, String createdBy, String createdTime, String modifiedBy, String modifiedTime) throws SQLException {
		
		return AuditDetails.builder()
			.lastModifiedTime(rs.getLong(modifiedTime))
			.createdTime((Long) rs.getObject(createdTime))
			.lastModifiedBy(rs.getString(modifiedBy))
			.createdBy(rs.getString(createdBy))
			.build();
	}
	
	
	/**
	 * method parses the PGobject and returns the JSON node
	 * 
	 * @param rs
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	private JsonNode getadditionalDetail(ResultSet rs, String key) throws SQLException {

		JsonNode additionalDetails = null;

		try {

			PGobject obj = (PGobject) rs.getObject(key);
			if (obj != null) {
				additionalDetails = mapper.readTree(obj.getValue());
			}

		} catch (IOException e) {
			throw new CustomException("PARSING ERROR", "The propertyAdditionalDetail json cannot be parsed");
		}

		if(additionalDetails.isEmpty())
			additionalDetails = null;
		
		return additionalDetails;

	}

}
