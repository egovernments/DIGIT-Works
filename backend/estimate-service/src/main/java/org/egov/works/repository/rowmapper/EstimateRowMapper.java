package org.egov.works.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.Estimate;
import org.egov.works.web.models.EstimateDetail;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class EstimateRowMapper implements ResultSetExtractor<List<Estimate>> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<Estimate> extractData(ResultSet rs) throws SQLException, DataAccessException {

        Map<String, Estimate> estimateMap = new LinkedHashMap<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String tenantId = rs.getString("tenantId");
            String estimateNumber = rs.getString("estimate_number");
            BigDecimal proposalDate = rs.getBigDecimal("proposal_date");
            String adminSanctionNumber = rs.getString("admin_sanction_number");
            String status = rs.getString("status");
            String estimateStatus = rs.getString("estimate_status");
            String typeOfWork = rs.getString("type_of_work");
            String subject = rs.getString("subject");
            String natureOfWork = rs.getString("nature_of_work");
            String requirementNumber = rs.getString("requirement_number");
            String beneficiaryType = rs.getString("beneficiary_type");
            String description = rs.getString("description");
            String workCategory = rs.getString("work_category");
            String department = rs.getString("department");
            String location = rs.getString("location");
            String subtypeOfWork = rs.getString("subtype_of_work");
            String entrustmentMode = rs.getString("entrustment_mode");
            String fund = rs.getString("fund");
            String function = rs.getString("function");
            String budgetHead = rs.getString("budget_head");
            String scheme = rs.getString("scheme");
            String subScheme = rs.getString("sub_scheme");
            String createdby = rs.getString("createdby");
            String lastmodifiedby = rs.getString("lastmodifiedby");
            BigDecimal totalAmount = rs.getBigDecimal("total_amount");
            Long createdtime = rs.getLong("createdtime");
            Long lastmodifiedtime = rs.getLong("lastmodifiedtime");

            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("additionaldetails", rs);

            Estimate estimate = Estimate.builder().estimateNumber(estimateNumber).id(UUID.fromString(id))
                    .estimateStatus(estimateStatus).status(Estimate.StatusEnum.fromValue(status)).adminSanctionNumber(adminSanctionNumber)
                    .totalAmount(totalAmount).additionalDetails(additionalDetails).beneficiaryType(beneficiaryType).budgetHead(budgetHead)
                    .description(description).entrustmentMode(entrustmentMode).function(function).fund(fund).location(location)
                    .natureOfWork(natureOfWork).requirementNumber(requirementNumber).proposalDate(proposalDate).scheme(scheme).subject(subject)
                    .subScheme(subScheme).subTypeOfWork(subtypeOfWork).typeOfWork(typeOfWork).tenantId(tenantId).department(department)
                    .workCategory(workCategory).auditDetails(auditDetails).build();


            addEstimateDetails(rs, estimate, estimateMap);
            estimateMap.put(id, estimate);
        }
        return new ArrayList<>(estimateMap.values());
    }

    private void addEstimateDetails(ResultSet rs, Estimate estimate, Map<String, Estimate> estimateMap) throws SQLException {
        String estDetailsId = rs.getString("estDetailId");
        if (StringUtils.isNotBlank(estDetailsId)) {

            EstimateDetail estimateDetail = EstimateDetail.builder()
                    .id(UUID.fromString(estDetailsId))
                    .estimateDetailNumber(rs.getString("estimate_detail_number"))
                    .name(rs.getString("name"))
                    .amount(rs.getBigDecimal("amount"))
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("estDetailAdditional", rs);
            estimateDetail.setAdditionalDetails(additionalDetails);

            if (estimateMap.containsKey(estDetailsId)) {
                estimateMap.get(estDetailsId).getEstimateDetails().add(estimateDetail);
            } else {
                List<EstimateDetail> estimateDetailList = new LinkedList<>();
                estimateDetailList.add(estimateDetail);

                estimate.setEstimateDetails(estimateDetailList);
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
            throw new CustomException("PARSING ERROR", "Failed to parse additionalDetail object");
        }
        if (additionalDetails.isEmpty())
            additionalDetails = null;
        return additionalDetails;
    }
}
