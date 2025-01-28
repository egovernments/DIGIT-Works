package org.egov.works.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.models.AuditDetails;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.AmountBreakup;
import org.egov.works.web.models.LineItems;
import org.egov.works.web.models.Status;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Slf4j
public class LineItemsRowMapper implements ResultSetExtractor<List<LineItems>> {

    private final ObjectMapper mapper;

    @Autowired
    public LineItemsRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<LineItems> extractData(ResultSet rs) throws SQLException, DataAccessException {

        Map<String, LineItems> lineItemsMap = new LinkedHashMap<>();

        while (rs.next()) {
            String id = rs.getString("id");
            String estimateId = rs.getString("estimateId");
            String estimateLineItemId = rs.getString("estimateLineItemId");
            String contractLineItemRef = rs.getString("contractLineItemRef");
            String contractId = rs.getString("contractId");
            String tenantId = rs.getString("tenantId");
            Double unitRate = rs.getDouble("unitRate");
            Double noOfUnit = rs.getDouble("noOfUnit");
            String status = rs.getString("status");
            String createdby = rs.getString("createdby");
            String lastmodifiedby = rs.getString("lastModifiedBy");
            Long createdtime = rs.getLong("createdTime");
            Long lastmodifiedtime = rs.getLong("lastModifiedTime");

            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("additionalDetails", rs);

            LineItems lineItem = LineItems.builder()
                    .id(id)
                    .estimateId(estimateId)
                    .estimateLineItemId(estimateLineItemId)
                    .contractLineItemRef(contractLineItemRef)
                    .contractId(contractId)
                    .tenantId(tenantId)
                    .unitRate(unitRate)
                    .noOfunit(noOfUnit)
                    .status(Status.fromValue(status))
                    .additionalDetails(additionalDetails)
                    .auditDetails(auditDetails)
                    .build();

            if (!lineItemsMap.containsKey(id)) {
                lineItemsMap.put(id, lineItem);
            }
            addAmountBreakups(rs, lineItemsMap.get(id));
        }
        return new ArrayList<>(lineItemsMap.values());
    }


    private void addAmountBreakups(ResultSet rs, LineItems lineItem) throws SQLException {
        String amountBreakUpId = rs.getString("amtId");
        String lineItemId = rs.getString("id");
        if (StringUtils.isNotBlank(amountBreakUpId) && lineItemId.equalsIgnoreCase(lineItem.getId())) {
            AmountBreakup amountBreakup = AmountBreakup.builder()
                    .id(amountBreakUpId)
                    .estimateAmountBreakupId(rs.getString("amtEstimateAmountBreakupId"))
                    .lineItemId(rs.getString("amtLineItemId"))
                    .amount(rs.getDouble("amtAmount"))
                    .status(Status.fromValue(rs.getString("amtStatus")))
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("amtAdditionalDetails", rs);
            amountBreakup.setAdditionalDetails(additionalDetails);

            if (lineItem.getAmountBreakups() == null || lineItem.getAmountBreakups().isEmpty()) {
                List<AmountBreakup> amountBreakups = new LinkedList<>();
                amountBreakups.add(amountBreakup);
                lineItem.setAmountBreakups(amountBreakups);
            } else {
                lineItem.getAmountBreakups().add(amountBreakup);
            }
        }
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
