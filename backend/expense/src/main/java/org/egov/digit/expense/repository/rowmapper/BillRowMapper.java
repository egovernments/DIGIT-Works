package org.egov.digit.expense.repository.rowmapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.LineItem;
import org.egov.digit.expense.web.models.Party;
import org.egov.digit.expense.web.models.enums.LineItemType;
import org.egov.digit.expense.web.models.enums.PaymentStatus;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BillRowMapper implements ResultSetExtractor<List<Bill>>{
	
	private final ObjectMapper mapper;

	@Autowired
	public BillRowMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<Bill> extractData(ResultSet rs) throws SQLException {
		
		Map<String, Bill> billMap = new LinkedHashMap<>();
		Map<String, BillDetail> billDetailMap = new HashMap<>();

		while (rs.next()) {
			String billId = rs.getString("b_id");
			Bill bill = billMap.get(billId);

			if (bill == null) {
				
				billDetailMap.clear();
				
				
				Party payer = getPayer(rs);
				
				AuditDetails billAuditDetails = getAuditDetailsForKey(rs,
						"b_createdby",
						"b_createdtime",
						"b_lastmodifiedby",
						"b_lastmodifiedtime");
				
				bill = Bill.builder()
					.paymentStatus(PaymentStatus.fromValue(rs.getString("b_paymentstatus")))
					.additionalDetails(getadditionalDetail(rs, "b_additionalDetails"))
					.totalPaidAmount(rs.getBigDecimal("b_totalpaidamount"))
					.status(Status.fromValue(rs.getString("b_status")))
					.businessService(rs.getString("businessservice"))
					.totalAmount(rs.getBigDecimal("b_totalamount"))
					.referenceId(rs.getString("b_referenceid"))
					.billNumber(rs.getString("billnumber"))
					.fromPeriod(rs.getLong("b_fromperiod"))
					.tenantId(rs.getString("b_tenantid"))
					.toPeriod(rs.getLong("b_toperiod"))
					.billDate(rs.getLong("billdate"))
					.dueDate(rs.getLong("duedate"))
					.auditDetails(billAuditDetails)
					.payer(payer)
					.id(billId)
					.build();

				billMap.put(bill.getId(), bill);
			}

			/*
			 * bill details 
			 */
			String detailId = rs.getString("bd_id");
			BillDetail billDetail = billDetailMap.get(detailId);

			if (billDetail == null) {
				
				Party payee = getPayee(rs);
				
				AuditDetails bDAuditDetails =  getAuditDetailsForKey(rs,
						"bd_createdby",
						"bd_createdtime",
						"bd_lastmodifiedby",
						"bd_lastmodifiedtime");
				
				billDetail = BillDetail.builder()
					.paymentStatus(PaymentStatus.fromValue(rs.getString("bd_paymentstatus")))
					.additionalDetails(getadditionalDetail(rs, "bd_additionalDetails"))
					.totalPaidAmount(rs.getBigDecimal("bd_totalpaidamount"))
					.status(Status.fromValue(rs.getString("bd_status")))
					.totalAmount(rs.getBigDecimal("bd_totalamount"))
					.referenceId(rs.getString("bd_referenceid"))
					.fromPeriod(rs.getLong("bd_fromperiod"))
					.tenantId(rs.getString("bd_tenantid"))
					.toPeriod(rs.getLong("bd_toperiod"))
					.billId(rs.getString("billid"))
					.auditDetails(bDAuditDetails)
					.id(detailId)
					.payee(payee)
					.build();
		
				billDetailMap.put(billDetail.getId(), billDetail);

				if (bill.getId().equals(billDetail.getBillId()))
					bill.addBillDetailsItem(billDetail);
			}
			
			/*
			 * Line items details
			 */
			Boolean isLineItemPayable = rs.getBoolean("islineitemPayable"); 
			String lineTiemBillDetailId = rs.getString("line_billdetailid");
			
			AuditDetails auditDetails =AuditDetails.builder()
					.createdBy(rs.getString("line_createdby"))
					.createdTime((Long) rs.getObject("line_createdtime"))
					.lastModifiedBy(rs.getString("line_lastmodifiedby"))
					.lastModifiedTime((Long) rs.getObject("line_lastmodifiedtime"))
					.build();

			LineItem lineItem = LineItem.builder()
					.auditDetails(auditDetails)
					.id(rs.getString("line_id"))
					.tenantId(rs.getString("line_tenantid"))
					.status(Status.fromValue(rs.getString("line_status")))
					.headCode(rs.getString("headcode"))
					.amount(rs.getBigDecimal("amount"))
					.paidAmount(rs.getBigDecimal("paidamount"))
					.paymentStatus(PaymentStatus.fromValue(rs.getString("li_paymentstatus")))
					.type(LineItemType.fromValue(rs.getString("line_type")))
					.additionalDetails(getadditionalDetail(rs, "line_additionalDetails"))
					.build();
			
			if(lineTiemBillDetailId.equals(detailId)) {
				if(Boolean.TRUE.equals(isLineItemPayable))
					billDetail.addPayableLineItems(lineItem);
				else 
					billDetail.addLineItems(lineItem);
			}
					
		}
		log.debug("converting map to list object ::: " + billMap.values());
		return new ArrayList<>(billMap.values());
	}


	/**
	 * Fetch payer details from result set
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Party getPayer(ResultSet rs) throws SQLException {
		AuditDetails payerAuditDetails = getAuditDetailsForKey(rs, 
				"payer_createdby",
				"payer_createdtime",
				"payer_lastmodifiedby",
				"payer_lastmodifiedtime");

		return Party.builder()
				.status(Status.fromValue(rs.getString("payer_status")))
				.identifier(rs.getString("payer_identifier"))
				.tenantId(rs.getString("payer_tenantid"))
				.type(rs.getString("payer_type"))
				.auditDetails(payerAuditDetails)
				.id(rs.getString("payer_id"))
				.build();
	}
	

	/**
	 * Fetch payee details from result set
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Party getPayee(ResultSet rs) throws SQLException {
		
		AuditDetails payeeAuditDetails = getAuditDetailsForKey(rs, 
				"payee_createdby",
				"payee_createdtime",
				"payee_lastmodifiedby",
				"payee_lastmodifiedtime");

		return Party.builder()
				.status(Status.fromValue(rs.getString("payee_status")))
				.identifier(rs.getString("payee_identifier"))
				.tenantId(rs.getString("payee_tenantid"))
				.type(rs.getString("payee_type"))
				.auditDetails(payeeAuditDetails)
				.additionalDetails(getadditionalDetail(rs,"payee_additionaldetails"))
				.id(rs.getString("payee_id"))
				.build();
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

		if(additionalDetails != null && additionalDetails.isEmpty())
			additionalDetails = null;
		
		return additionalDetails;

	}
}
