package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.models.AuditDetails;
import org.egov.works.services.common.models.common.Address;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.AmountDetail;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateDetail;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Slf4j
public class EstimateRowMapper implements ResultSetExtractor<List<Estimate>> {

    private final ObjectMapper mapper;

    @Autowired
    public EstimateRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Estimate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        log.info("EstimateRowMapper::extractData");
        Map<String, Estimate> estimateMap = new LinkedHashMap<>();
        Map<String, EstimateDetail> estimateDetailMap = new LinkedHashMap<>();
        boolean isAddressAdded = false;
        while (rs.next()) {
            String id = rs.getString("id");
            String tenantId = rs.getString("tenant_id");
            String estimateNumber = rs.getString("estimate_number");
            BigDecimal proposalDate = rs.getBigDecimal("proposal_date");
            String projectId = rs.getString("project_id");
            String status = rs.getString("status");
            String wfStatus = rs.getString("wf_status");
            String name = rs.getString("name");
            String description = rs.getString("description");
            String revisionNumber = rs.getString("revision_number");
            String businessService = rs.getString("business_service");
            BigDecimal versionNumber = rs.getBigDecimal("version_number");
            String oldUuid = rs.getString("old_uuid");
            String referenceNumber = rs.getString("reference_number");
            String executingDepartment = rs.getString("executing_department");
            String createdby = rs.getString("created_by");
            String lastmodifiedby = rs.getString("last_modified_by");
            Long createdtime = rs.getLong("created_time");
            Long lastmodifiedtime = rs.getLong("last_modified_time");

            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("additional_details", rs);

            Estimate estimate = Estimate.builder().estimateNumber(estimateNumber).id(id)
                    .wfStatus(wfStatus).status(Estimate.StatusEnum.fromValue(status)).projectId(projectId)
                    .additionalDetails(additionalDetails).tenantId(tenantId).name(name)
                    .revisionNumber(revisionNumber).businessService(businessService).versionNumber(versionNumber).oldUuid(oldUuid)
                    .description(description).referenceNumber(referenceNumber).executingDepartment(executingDepartment)
                    .proposalDate(proposalDate).auditDetails(auditDetails).build();

            if (!estimateMap.containsKey(id)) {
                estimateMap.put(id, estimate);
            }
            //one-to-many mapping
            addEstimateDetails(rs, estimateDetailMap, estimateMap.get(id));

            if (!isAddressAdded) {
                Address address = getEstimateAddress(tenantId, rs);
                //one-to-one mapping
                estimate.setAddress(address);
                isAddressAdded = true;
            }
        }
        return new ArrayList<>(estimateMap.values());
    }

    private Address getEstimateAddress(String tenantId, ResultSet rs) throws SQLException {
        log.debug("EstimateRowMapper::getEstimateAddress");
        return Address.builder()
                .id(rs.getString("estAddId"))
                .tenantId(tenantId).addressLine1(rs.getString("address_line_1"))
                .addressLine2(rs.getString("address_line_2")).addressNumber(rs.getString("address_number"))
                .city(rs.getString("city")).pincode(rs.getString("pin_code"))
                .detail(rs.getString("detail"))
                .landmark(rs.getString("landmark")).latitude(rs.getDouble("latitude"))
                .longitude(rs.getDouble("longitude"))
                .build();
    }

    private void addEstimateDetails(ResultSet rs, Map<String, EstimateDetail> estimateDetailMap, Estimate estimate) throws SQLException {
        log.info("EstimateRowMapper::addEstimateDetails");
        String estDetailsId = rs.getString("estDetailId");
        EstimateDetail estimateDetail = EstimateDetail.builder()
                .id(estDetailsId)
                .previousLineItemId(rs.getString("estDetailOldUuid"))
                .sorId(rs.getString("sor_id"))
                .category(rs.getString("category"))
                .name(rs.getString("estDetailName"))
                .description(rs.getString("estDetailDescription"))
                .unitRate(rs.getDouble("unit_rate"))
                .noOfunit(rs.getDouble("no_of_unit"))
                //.totalAmount(rs.getDouble("total_amount"))
                .uomValue(rs.getDouble("uom_value"))
                .length(rs.getBigDecimal("length"))
                .width(rs.getBigDecimal("width"))
                .height(rs.getBigDecimal("height"))
                .isDeduction(rs.getBoolean("is_deduction"))
                .quantity(rs.getBigDecimal("quantity"))
                .uom(rs.getString("uom"))
                .isActive(rs.getBoolean("estDetailActive"))
                .build();

        JsonNode additionalDetails = getAdditionalDetail("estDetailAdditional", rs);
        estimateDetail.setAdditionalDetails(additionalDetails);

        if (!estimateDetailMap.containsKey(estDetailsId)) {
            estimateDetailMap.put(estDetailsId, estimateDetail);
        } else {
            estimateDetail = estimateDetailMap.get(estDetailsId);
        }

        //set the estimate detail -> amount details <one-to-many mapping> : Start
        String estimateDetailId = rs.getString("estimate_detail_id");

        AmountDetail amountDetail = AmountDetail.builder()
                .id(rs.getString("estAmtDetailId"))
                .amount(rs.getDouble("amount"))
                //.category(rs.getString("category"))
                .type(rs.getString("type"))
                .isActive(rs.getBoolean("estAmtDetailActive"))
                .build();

        JsonNode amtAdditionalDetails = getAdditionalDetail("estAmtDetailAdditional", rs);
        amountDetail.setAdditionalDetails(amtAdditionalDetails);

        if (StringUtils.isNotBlank(estimateDetailId) && estimateDetailId.equals(estDetailsId)) {
            if (estimateDetail.getAmountDetail() == null || estimateDetail.getAmountDetail().isEmpty()) {
                List<AmountDetail> amountDetails = new LinkedList<>();
                amountDetails.add(amountDetail);
                estimateDetail.setAmountDetail(amountDetails);
            } else {
                estimateDetail.getAmountDetail().add(amountDetail);
            }
        }
        //set the estimate detail -> amount details : End

        //set the estimate detail : Start
        if (estimate.getEstimateDetails() == null || estimate.getEstimateDetails().isEmpty()) {
            List<EstimateDetail> estimateDetailList = new LinkedList<>();
            estimateDetailList.add(estimateDetail);
            estimate.setEstimateDetails(estimateDetailList);
        } else {
            estimate.getEstimateDetails().add(estimateDetail);
        }
        //set the estimate detail : Start
    }


    private JsonNode getAdditionalDetail(String columnName, ResultSet rs) throws SQLException {
        log.debug("EstimateRowMapper::getAdditionalDetail");
        JsonNode additionalDetails = null;
        try {
            PGobject obj = (PGobject) rs.getObject(columnName);
            if (obj != null) {
                additionalDetails = mapper.readTree(obj.getValue());
            }
        } catch (IOException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse additionalDetail object");
        }
        if (additionalDetails != null && additionalDetails.isEmpty())
            additionalDetails = null;
        return additionalDetails;
    }
}
