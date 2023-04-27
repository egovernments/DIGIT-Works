package org.egov.digit.expense.repository.rowmapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.LineItem;
import org.egov.digit.expense.web.models.enums.LineItemType;
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
public class BillRowMapper implements ResultSetExtractor<List<Bill>>{
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public List<Bill> extractData(ResultSet rs) throws SQLException {
		
		Map<String, Bill> billMap = new LinkedHashMap<>();
		Map<String, BillDetail> billDetailMap = new HashMap<>();

		while (rs.next()) {

			String billId = rs.getString("b_id");
			Bill bill = billMap.get(billId);

			if (bill == null) {
				
				AuditDetails auditDetails =AuditDetails.builder()
					.createdBy(rs.getString("b_createdby"))
					.createdTime((Long) rs.getObject("b_createddate"))
					.lastModifiedBy(rs.getString("b_lastmodifiedby"))
					.lastModifiedTime((Long) rs.getObject("b_lastmodifieddate"))
					.build();

				bill = Bill.builder()
					.id(billId)
					.tenantId(rs.getString("b_tenantid"))
					.billDate(rs.getLong("billdate"))
					.dueDate(rs.getLong("duedate"))
					.netPayableAmount(rs.getBigDecimal("netpayableamount"))
					.netPaidAmount(rs.getBigDecimal("netpaidamount"))
					.businessService(rs.getString("businessservice"))
					.referenceId(rs.getString("referenceid"))
					.fromPeriod(rs.getLong("b_fromperiod"))
					.toPeriod(rs.getLong("b_toperiod"))
					.status(rs.getString("b_status"))
					.paymentStatus(rs.getString("b_paymentstatus"))
					.additionalDetails(getadditionalDetail(rs, "b_additionalDetails"))
					.auditDetails(auditDetails)
					.build();

				billMap.put(bill.getId(), bill);
			}

			/*
			 * bill details 
			 */
			String detailId = rs.getString("bd_id");
			BillDetail billDetail = billDetailMap.get(detailId);

			if (billDetail == null) {

				AuditDetails auditDetails =AuditDetails.builder()
					.createdBy(rs.getString("bd_createdby"))
					.createdTime((Long) rs.getObject("bd_createddate"))
					.lastModifiedBy(rs.getString("bd_lastmodifiedby"))
					.lastModifiedTime((Long) rs.getObject("bd_lastmodifieddate"))
					.build();
				
				
				billDetail = BillDetail.builder()
					.id(detailId)
					.billId(rs.getString("billid"))
					.referenceId(rs.getString("bd_referenceid"))
					.paymentStatus(rs.getString("bd_paymentstatus"))
					.fromPeriod(rs.getLong("bd_fromperiod"))
					.toPeriod(rs.getLong("bd_toperiod"))
					.auditDetails(auditDetails)
					.additionalDetails(getadditionalDetail(rs, "bd_additionalDetails"))
					.build();
		
				billDetailMap.put(billDetail.getId(), billDetail);

				if (bill.getId().equals(billDetail.getBillId()))
					bill.addBillDetailsItem(billDetail);
			}
			
			/*
			 * Line items details
			 */
			Boolean isLineItemPayable = rs.getBoolean("isPayable"); 
			String lineTiemBillDetailId = rs.getString("line_billdetailid");
			
			AuditDetails auditDetails =AuditDetails.builder()
					.createdBy(rs.getString("line_createdby"))
					.createdTime((Long) rs.getObject("line_createddate"))
					.lastModifiedBy(rs.getString("line_lastmodifiedby"))
					.lastModifiedTime((Long) rs.getObject("line_lastmodifieddate"))
					.build();

			LineItem lineItem = LineItem.builder()
					.auditDetails(auditDetails)
					.id(rs.getString("line_id"))
					.status(rs.getString("line_status"))
					.headCode(rs.getString("headcode"))
					.amount(rs.getBigDecimal("amount"))
					.paidAmount(rs.getBigDecimal("paidamount"))
					.type(LineItemType.fromValue(rs.getString("line_type")))
					.additionalDetails(getadditionalDetail(rs, "line_additionalDetails"))
					.build();
			
			if(lineTiemBillDetailId.equals(detailId)) {
				if(isLineItemPayable)
					billDetail.addPayableLineItems(lineItem);
				else 
					billDetail.addLineItems(lineItem);
			}
					
		}
		log.debug("converting map to list object ::: " + billMap.values());
		return new ArrayList<>(billMap.values());
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