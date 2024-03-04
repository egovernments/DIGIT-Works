package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.jit.Allotment;
import org.egov.web.models.jit.FundsSummary;
import org.egov.web.models.jit.SanctionDetail;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Slf4j
public class SanctionDetailRowMapper implements ResultSetExtractor<List<SanctionDetail>> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<SanctionDetail> extractData(ResultSet rs) throws SQLException, DataAccessException {

        Map<String, SanctionDetail> sanctionDetailMap = new LinkedHashMap<>();

        while (rs.next()) {
            String id = rs.getString("snacDetailsId");
            String tenantId = rs.getString("snacDetailsTenantId");
            String hoaCode = rs.getString("snacDetailsHoaCode");
            String ddoCode = rs.getString("snacDetailsDdoCode");
            String masterAllotmentId = rs.getString("snacDetailsMasterAllotmentId");
            BigDecimal sanctionedAmount = rs.getBigDecimal("snacDetailsSanctionedAmount");
            String financialYear = rs.getString("snacDetailsFinancialYear");
            String createdby = rs.getString("snacDetailsCreatedBy");
            String lastmodifiedby = rs.getString("snacDetailsLastModifiedBy");
            Long createdtime = rs.getLong("snacDetailsCreatedTime");
            Long lastmodifiedtime = rs.getLong("snacDetailsLastModifiedTime");

            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("snacDetailsAdditionalDetails", rs);

            SanctionDetail sanctionDetail = SanctionDetail.builder()
                    .id(id)
                    .tenantId(tenantId)
                    .hoaCode(hoaCode)
                    .ddoCode(ddoCode)
                    .masterAllotmentId(masterAllotmentId)
                    .sanctionedAmount(sanctionedAmount)
                    .financialYear(financialYear)
                    .additionalDetails(additionalDetails)
                    .auditDetails(auditDetails)
                    .build();

            if (!sanctionDetailMap.containsKey(id)) {
                sanctionDetailMap.put(id, sanctionDetail);
            }

            addFundsSummary(rs, sanctionDetailMap.get(id));
            addAllotmentDetails(rs, sanctionDetailMap.get(id));
        }
        return new ArrayList<>(sanctionDetailMap.values());
    }

    private void addFundsSummary(ResultSet rs, SanctionDetail sanctionDetail) throws SQLException {
        String fundsSummaryId = rs.getString("fundsSummaryId");
        String tenantId = rs.getString("fundsSummaryTenantId");
        String sanctionId = rs.getString("fundsSummarySanctionId");
        BigDecimal allottedAmount = rs.getBigDecimal("fundsSummaryAllottedAmount");
        BigDecimal availableAmount = rs.getBigDecimal("fundsSummaryAvailableAmount");
        String createdby = rs.getString("fundsSummaryCreatedBy");
        String lastmodifiedby = rs.getString("fundsSummaryLastModifiedBy");
        Long createdtime = rs.getLong("fundsSummaryCreatedTime");
        Long lastmodifiedtime = rs.getLong("fundsSummaryLastModifiedTime");
        JsonNode additionalDetails = getAdditionalDetail("fundsSummaryAdditionalDetails", rs);

        if (StringUtils.isNotBlank(fundsSummaryId) && sanctionId.equalsIgnoreCase(sanctionDetail.getId().toString())) {
            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            FundsSummary fundsSummary = FundsSummary.builder()
                    .id(fundsSummaryId)
                    .tenantId(tenantId)
                    .sanctionId(sanctionId)
                    .allottedAmount(allottedAmount)
                    .availableAmount(availableAmount)
                    .additionalDetails(additionalDetails)
                    .auditDetails(auditDetails)
                    .build();

            sanctionDetail.setFundsSummary(fundsSummary);
        }
    }

    private void addAllotmentDetails(ResultSet rs, SanctionDetail sanctionDetail) throws SQLException {
        String allotmentId = rs.getString("allotmentDetailsId");
        String tenantId = rs.getString("allotmentDetailsTenantId");
        String sanctionId = rs.getString("allotmentDetailsSanctionId");
        Integer allotmentSerialNo = rs.getInt("allotmentDetailsAllotmentSerialNo");
        String ssuAllotmentId = rs.getString("allotmentDetailsSsuAllotmentId");
        String allotmentTransactionType = rs.getString("allotmentDetailsAllotmentTransactionType");
        BigDecimal allottedAmount = rs.getBigDecimal("allotmentDetailsAllotmentAmount");
        BigDecimal sanctionBalance = rs.getBigDecimal("allotmentDetailsSanctionBalance");
        Long allotmentDate = rs.getLong("allotmentDetailsAllotmentDate");
        String createdby = rs.getString("allotmentDetailsCreatedBy");
        String lastmodifiedby = rs.getString("allotmentDetailsLastModifiedBy");
        Long createdtime = rs.getLong("allotmentDetailsCreatedTime");
        Long lastmodifiedtime = rs.getLong("allotmentDetailsLastModifiedTime");
        JsonNode additionalDetails = getAdditionalDetail("allotmentDetailsAdditionalDetails", rs);

        if (StringUtils.isNotBlank(allotmentId) && sanctionId.equalsIgnoreCase(sanctionDetail.getId().toString())) {
            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            Allotment allotment = Allotment.builder()
                    .id(allotmentId)
                    .tenantId(tenantId)
                    .sanctionId(sanctionId)
                    .allotmentSerialNo(allotmentSerialNo)
                    .ssuAllotmentId(ssuAllotmentId)
                    .allotmentTxnType(allotmentTransactionType)
                    .decimalAllottedAmount(allottedAmount)
                    .decimalSanctionBalance(sanctionBalance)
                    .allotmentDateTimeStamp(allotmentDate)
                    .additionalDetails(additionalDetails)
                    .auditDetails(auditDetails)
                    .build();
            if (sanctionDetail.getAllotmentDetails() == null) {
                List<Allotment> allotments = new ArrayList<>();
                allotments.add(allotment);
                sanctionDetail.setAllotmentDetails(allotments);
            } else {
                sanctionDetail.getAllotmentDetails().add(allotment);
            }
        }
    }


    private JsonNode getAdditionalDetail(String columnName, ResultSet rs) throws SQLException {
        JsonNode additionalDetails = null;
        try {
            PGobject obj = (PGobject) rs.getObject(columnName);
            if (obj != null) {
                additionalDetails = objectMapper.readTree(obj.getValue());
            }
        } catch (IOException e) {
            log.error("Failed to parse additionalDetail object");
            throw new CustomException("PARSING_ERROR", "Failed to parse additionalDetail object");
        }
        if (additionalDetails == null) {
            JsonNode emptyObject = objectMapper.createObjectNode();
            additionalDetails = emptyObject;
        }
        return additionalDetails;
    }
}
