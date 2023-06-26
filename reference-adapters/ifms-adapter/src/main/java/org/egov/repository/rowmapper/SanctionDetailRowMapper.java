package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
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
            String id = rs.getString("jsdId");
            String tenantId = rs.getString("jsdTenantId");
            String hoaCode = rs.getString("jsdHoaCode");
            String ddoCode = rs.getString("jsdDdoCode");
            String masterAllotmentId = rs.getString("jsdMasterAllotmentId");
            BigDecimal sanctionedAmount = rs.getBigDecimal("jsdSanctionedAmount");
            String financialYear = rs.getString("jsdFinancialYear");
            String createdby = rs.getString("jsdCreatedBy");
            String lastmodifiedby = rs.getString("jsdLastModifiedBy");
            Long createdtime = rs.getLong("jsdCreatedTime");
            Long lastmodifiedtime = rs.getLong("jsdLastModifiedTime");

            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("jsdAdditionalDetails", rs);

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
        }
        return new ArrayList<>(sanctionDetailMap.values());
    }

    private void addFundsSummary(ResultSet rs, SanctionDetail sanctionDetail) throws SQLException {
        String fundsSummaryId = rs.getString("jfsId");
        String tenantId = rs.getString("jfsTenantId");
        String sanctionId = rs.getString("jfsSanctionId");
        BigDecimal allottedAmount = rs.getBigDecimal("jfsAllottedAmount");
        BigDecimal availableAmount = rs.getBigDecimal("jfsAvailableAmount");
        String createdby = rs.getString("jfsCreatedBy");
        String lastmodifiedby = rs.getString("jfsLastModifiedBy");
        Long createdtime = rs.getLong("jfsCreatedTime");
        Long lastmodifiedtime = rs.getLong("jfsLastModifiedTime");
        JsonNode additionalDetails = getAdditionalDetail("jfsAdditionalDetails", rs);

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
