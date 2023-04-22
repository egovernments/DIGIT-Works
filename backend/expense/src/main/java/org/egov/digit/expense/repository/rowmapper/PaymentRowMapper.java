package org.egov.digit.expense.repository.rowmapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.Payment;
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

		while (rs.next()) {

			String paymentId = rs.getString("p_id");
			Payment payment = paymentMap.get(paymentId);

			if (payment == null) {
				
				AuditDetails auditDetails =AuditDetails.builder()
					.createdBy(rs.getString("p_createdby"))
					.createdTime((Long) rs.getObject("p_createddate"))
					.lastModifiedBy(rs.getString("p_lastmodifiedby"))
					.lastModifiedTime((Long) rs.getObject("p_lastmodifieddate"))
					.build();

				payment = Payment.builder()
					.id(paymentId)
					.tenantId(rs.getString("p_tenantid"))
					.netPayableAmount(rs.getBigDecimal("netpayableamount"))
					.netPaidAmount(rs.getBigDecimal("netpaidamount"))
					.status(rs.getString("p_status"))
					.additionalDetails(getadditionalDetail(rs, "p_additionalDetails"))
					.auditDetails(auditDetails)
					.build();

				paymentMap.put(payment.getId(), payment);
			}

			/*
			 * bill details 
			 */
			String BillId = rs.getString("billid");
			payment.addBillDetailsItem(
					Bill.builder()
					.id(BillId)
					.build());

		}
		log.debug("converting map to list object ::: " + paymentMap.values());
		return new ArrayList<>(paymentMap.values());
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
