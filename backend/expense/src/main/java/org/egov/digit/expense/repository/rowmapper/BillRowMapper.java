package org.egov.digit.expense.repository.rowmapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.Party;
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

/**
 * Extracts Bill + Payer only from the BILL_ONLY_QUERY result set.
 * Bill details are handled separately by BillDetailRowMapper.
 */
@Component
@Slf4j
public class BillRowMapper implements ResultSetExtractor<List<Bill>> {

	private final ObjectMapper mapper;

	@Autowired
	public BillRowMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<Bill> extractData(ResultSet rs) throws SQLException {

		Map<String, Bill> billMap = new LinkedHashMap<>();

		while (rs.next()) {
			String billId = rs.getString("b_id");
			Bill bill = billMap.get(billId);

			if (bill == null) {
				Party payer = getPayer(rs);

				AuditDetails billAuditDetails = getAuditDetailsForKey(rs,
						"b_createdby",
						"b_createdtime",
						"b_lastmodifiedby",
						"b_lastmodifiedtime");

				JsonNode billAdditionalDetails = getadditionalDetail(rs, "b_additionalDetails");

				bill = Bill.builder()
					.paymentStatus(PaymentStatus.fromValue(rs.getString("b_paymentstatus")))
					.additionalDetails(billAdditionalDetails)
					.totalPaidAmount(rs.getBigDecimal("b_totalpaidamount"))
					.status(Status.fromValue(rs.getString("b_status")))
					.businessService(rs.getString("businessservice"))
					.totalAmount(rs.getBigDecimal("b_totalamount"))
					.localityCode(rs.getString("b_localitycode"))
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

				// Restore amountBreakup from additionalDetails if available (primary path)
				if (billAdditionalDetails != null) {
					JsonNode stored = billAdditionalDetails.get("amountBreakup");
					if (stored != null && stored.isObject() && !stored.isEmpty()) {
						Map<String, java.math.BigDecimal> breakup = bill.getAmountBreakup();
						stored.fields().forEachRemaining(e -> {
							if (e.getValue().isNumber()) {
								breakup.put(e.getKey(), e.getValue().decimalValue());
							}
						});
					}
				}

				billMap.put(bill.getId(), bill);
			}
		}

		log.debug("BillRowMapper extracted {} bills", billMap.size());
		return new ArrayList<>(billMap.values());
	}

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

	private AuditDetails getAuditDetailsForKey(ResultSet rs, String createdBy, String createdTime,
			String modifiedBy, String modifiedTime) throws SQLException {
		return AuditDetails.builder()
			.lastModifiedTime(rs.getLong(modifiedTime))
			.createdTime((Long) rs.getObject(createdTime))
			.lastModifiedBy(rs.getString(modifiedBy))
			.createdBy(rs.getString(createdBy))
			.build();
	}

	private JsonNode getadditionalDetail(ResultSet rs, String key) throws SQLException {
		try {
			PGobject obj = (PGobject) rs.getObject(key);
			if (obj != null) {
				JsonNode node = mapper.readTree(obj.getValue());
				if (node != null && !node.isEmpty()) return node;
			}
		} catch (IOException e) {
			throw new CustomException("PARSING ERROR", "The additionalDetails json cannot be parsed");
		}
		return null;
	}
}
