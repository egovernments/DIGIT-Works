package org.egov.digit.expense.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
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

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BillDetailRowMapper implements ResultSetExtractor<List<BillDetail>> {

    private final ObjectMapper mapper;

    @Autowired
    public BillDetailRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<BillDetail> extractData(ResultSet rs) throws SQLException {
        Map<String, BillDetail> detailMap = new LinkedHashMap<>();
        Map<String, LineItem> lineItemMap = new HashMap<>();

        while (rs.next()) {
            String detailId = rs.getString("bd_id");
            BillDetail detail = detailMap.get(detailId);

            if (detail == null) {
                AuditDetails auditDetails = AuditDetails.builder()
                        .createdBy(rs.getString("bd_createdby"))
                        .createdTime((Long) rs.getObject("bd_createdtime"))
                        .lastModifiedBy(rs.getString("bd_lastmodifiedby"))
                        .lastModifiedTime(rs.getLong("bd_lastmodifiedtime"))
                        .build();

                Party payee = getPayee(rs);

                detail = BillDetail.builder()
                        .id(detailId)
                        .tenantId(rs.getString("bd_tenantid"))
                        .billId(rs.getString("billid"))
                        .totalAmount(rs.getBigDecimal("bd_totalamount"))
                        .totalPaidAmount(rs.getBigDecimal("bd_totalpaidamount"))
                        .referenceId(rs.getString("bd_referenceid"))
                        .paymentStatus(PaymentStatus.fromValue(rs.getString("bd_paymentstatus")))
                        .status(Status.fromValue(rs.getString("bd_status")))
                        .fromPeriod(rs.getLong("bd_fromperiod"))
                        .toPeriod(rs.getLong("bd_toperiod"))
                        .workerId(rs.getString("bd_workerid"))
                        .totalAttendance(rs.getBigDecimal("bd_totalattendance"))
                        .additionalDetails(getAdditionalDetail(rs, "bd_additionaldetails"))
                        .auditDetails(auditDetails)
                        .payee(payee)
                        .lineItems(new ArrayList<>())
                        .payableLineItems(new ArrayList<>())
                        .build();

                detailMap.put(detailId, detail);
            }

            String lineItemDetailId = rs.getString("line_billdetailid");
            if (lineItemDetailId != null) {
                String lineItemId = rs.getString("line_id");
                if (lineItemId != null && !lineItemMap.containsKey(lineItemId)) {
                    boolean isPayable = rs.getBoolean("islineitempayable");

                    AuditDetails liAudit = AuditDetails.builder()
                            .createdBy(rs.getString("line_createdby"))
                            .createdTime((Long) rs.getObject("line_createdtime"))
                            .lastModifiedBy(rs.getString("line_lastmodifiedby"))
                            .lastModifiedTime((Long) rs.getObject("line_lastmodifiedtime"))
                            .build();

                    LineItem lineItem = LineItem.builder()
                            .id(lineItemId)
                            .tenantId(rs.getString("line_tenantid"))
                            .status(Status.fromValue(rs.getString("line_status")))
                            .headCode(rs.getString("headcode"))
                            .amount(rs.getBigDecimal("amount"))
                            .paidAmount(rs.getBigDecimal("paidamount"))
                            .paymentStatus(PaymentStatus.fromValue(rs.getString("li_paymentstatus")))
                            .type(LineItemType.fromValue(rs.getString("line_type")))
                            .additionalDetails(getAdditionalDetail(rs, "line_additionaldetails"))
                            .auditDetails(liAudit)
                            .build();

                    lineItemMap.put(lineItemId, lineItem);

                    if (lineItemDetailId.equals(detailId)) {
                        if (isPayable) {
                            detail.addPayableLineItems(lineItem);
                        } else {
                            detail.addLineItems(lineItem);
                        }
                    }
                }
            }
        }

        log.debug("BillDetailRowMapper extracted {} details", detailMap.size());
        return new ArrayList<>(detailMap.values());
    }

    private Party getPayee(ResultSet rs) throws SQLException {
        AuditDetails payeeAudit = AuditDetails.builder()
                .createdBy(rs.getString("payee_createdby"))
                .createdTime((Long) rs.getObject("payee_createdtime"))
                .lastModifiedBy(rs.getString("payee_lastmodifiedby"))
                .lastModifiedTime((Long) rs.getObject("payee_lastmodifiedtime"))
                .build();

        return Party.builder()
                .id(rs.getString("payee_id"))
                .tenantId(rs.getString("payee_tenantid"))
                .type(rs.getString("payee_type"))
                .identifier(rs.getString("payee_identifier"))
                .status(Status.fromValue(rs.getString("payee_status")))
                .paymentProvider(rs.getString("payee_paymentprovider"))
                .payeeName(rs.getString("payee_payeename"))
                .payeePhoneNumber(rs.getString("payee_payeephonenumber"))
                .bankAccount(rs.getString("payee_bankaccount"))
                .bankCode(rs.getString("payee_bankcode"))
                .beneficiaryCode(rs.getString("payee_beneficiarycode"))
                .additionalDetails(getAdditionalDetail(rs, "payee_additionaldetails"))
                .auditDetails(payeeAudit)
                .build();
    }

    private JsonNode getAdditionalDetail(ResultSet rs, String key) throws SQLException {
        try {
            PGobject obj = (PGobject) rs.getObject(key);
            if (obj == null) return null;
            JsonNode node = mapper.readTree(obj.getValue());
            return (node != null && !node.isEmpty()) ? node : null;
        } catch (IOException e) {
            throw new CustomException("PARSING_ERROR", "Cannot parse additionalDetails for key: " + key);
        }
    }
}
