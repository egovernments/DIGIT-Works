package org.egov.works.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.AmountBreakup;
import org.egov.works.web.models.Status;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AmountBreakupRowMapper implements ResultSetExtractor<List<AmountBreakup>> {

    private final ObjectMapper mapper;

    @Autowired
    public AmountBreakupRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<AmountBreakup> extractData(ResultSet rs) throws SQLException, DataAccessException {

        Map<String, AmountBreakup> amountBreakupMap = new LinkedHashMap<>();

        while (rs.next()) {
            String id = rs.getString("id");
            String estimateAmountBreakupId = rs.getString("estimateAmountBreakupId");
            String lineItemId = rs.getString("lineItemId");
            Double amount = rs.getDouble("amount");
            String status = rs.getString("status");
            String createdby = rs.getString("createdby");
            String lastmodifiedby = rs.getString("lastModifiedBy");
            Long createdtime = rs.getLong("createdTime");
            Long lastmodifiedtime = rs.getLong("lastModifiedTime");

            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("additionalDetails", rs);

            AmountBreakup document = AmountBreakup.builder()
                    .id(id)
                    .estimateAmountBreakupId(estimateAmountBreakupId)
                    .lineItemId(lineItemId)
                    .amount(amount)
                    .status(Status.fromValue(status))
                    .additionalDetails(additionalDetails)
                    .auditDetails(auditDetails)
                    .build();

            if (!amountBreakupMap.containsKey(id)) {
                amountBreakupMap.put(id, document);
            }
        }
        return new ArrayList<>(amountBreakupMap.values());
    }

    private JsonNode getAdditionalDetail(String columnName, ResultSet rs) throws SQLException {
        JsonNode additionalDetails = null;
        try {
            PGobject obj = (PGobject) rs.getObject(columnName);
            if (obj != null) {
                additionalDetails = mapper.readTree(obj.getValue());
            }
        } catch (IOException e) {
            log.error("Failed to parse additionalDetail object");
            throw new CustomException("PARSING_ERROR", "Failed to parse additionalDetail object");
        }
        if (additionalDetails.isEmpty())
            additionalDetails = null;
        return additionalDetails;
    }
}
