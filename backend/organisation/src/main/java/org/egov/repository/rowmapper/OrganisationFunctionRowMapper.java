package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.models.AuditDetails;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.ApplicationStatus;
import org.egov.web.models.Function;
import org.egov.web.models.Organisation;
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
public class OrganisationFunctionRowMapper implements ResultSetExtractor<List<Organisation>> {

    private final ObjectMapper mapper;

    @Autowired
    public OrganisationFunctionRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Organisation> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<String, Organisation> organisationMap = new LinkedHashMap<>();
        while (resultSet.next()) {
            String orgId = resultSet.getString("organisation_Id");

            if (!organisationMap.containsKey(orgId)) {
                organisationMap.put(orgId, createOrganisationObj(resultSet));
            }
            else {
                addFunctionToOrganisation(organisationMap.get(orgId), resultSet);
            }
        }

        return new ArrayList<>(organisationMap.values());
    }

    private Organisation createOrganisationObj(ResultSet rs) throws SQLException, DataAccessException {

        Function function = createFunctionObjFromResultSet(rs);
        List<Function> functions = new ArrayList<>();
        functions.add(function);

        String organisationId = rs.getString("organisation_Id");
        String organisationTenantId = rs.getString("organisation_tenantId");
        String organisationApplicationNumber = rs.getString("organisation_applicationNumber");
        String organisationName = rs.getString("organisation_name");
        String organisationOrgNumber = rs.getString("organisation_orgNumber");
        String organisationExternalRefNumber = rs.getString("organisation_externalRefNumber");
        BigDecimal organisationDateOfIncorporation = rs.getBigDecimal("organisation_dateOfIncorporation");
        String organisationApplicationStatus = rs.getString("organisation_applicationStatus");
        Boolean organisationIsActive = rs.getBoolean("organisation_isActive");
        JsonNode organisationAdditionalDetails = getAdditionalDetail("organisation_additionalDetails", rs);
        String organisationCreatedBy = rs.getString("organisation_createdBy");
        String organisationLastModifiedBy = rs.getString("organisation_lastModifiedBy");
        Long organisationCreatedTime = rs.getLong("organisation_createdTime");
        Long organisationLastModifiedTime = rs.getLong("organisation_lastModifiedTime");

        AuditDetails organisationAuditDetails = AuditDetails.builder().createdBy(organisationCreatedBy).createdTime(organisationCreatedTime)
                .lastModifiedBy(organisationLastModifiedBy).lastModifiedTime(organisationLastModifiedTime)
                .build();

        return Organisation.builder()
                .id(organisationId)
                .tenantId(organisationTenantId)
                .applicationNumber(organisationApplicationNumber)
                .name(organisationName)
                .orgNumber(organisationOrgNumber)
                .externalRefNumber(organisationExternalRefNumber)
                .dateOfIncorporation(organisationDateOfIncorporation)
                .applicationStatus(ApplicationStatus.fromValue(organisationApplicationStatus))
                .isActive(organisationIsActive)
                .functions(functions)
                .additionalDetails(organisationAdditionalDetails)
                .auditDetails(organisationAuditDetails)
                .build();
    }

    private Function createFunctionObjFromResultSet(ResultSet rs) throws SQLException  {
        String organisationFunctionId = rs.getString("organisationFunction_Id");
        String organisationFunctionOrgId = rs.getString("organisationFunction_OrgId");
        String organisationFunctionApplicationNumber = rs.getString("organisationFunction_applicationNumber");
        String organisationFunctionType = rs.getString("organisationFunction_type");
        String organisationFunctionCategory = rs.getString("organisationFunction_category");
        String organisationFunctionClass = rs.getString("organisationFunction_class");
        BigDecimal organisationFunctionValidFrom = rs.getBigDecimal("organisationFunction_valid_from");
        BigDecimal organisationFunctionValidTo = rs.getBigDecimal("organisationFunction_validTo");
        String organisationFunctionApplicationStatus = rs.getString("organisationFunction_applicationStatus");
        String organisationFunctionWfStatus = rs.getString("organisationFunction_wfStatus");
        Boolean organisationFunctionIsActive = rs.getBoolean("organisationFunction_isActive");
        JsonNode organisationFunctionAdditionalDetails = getAdditionalDetail("organisationFunction_additionalDetails", rs);
        String organisationFunctionCreatedBy = rs.getString("organisationFunction_createdBy");
        String organisationFunctionLastModifiedBy = rs.getString("organisationFunction_lastModifiedBy");
        Long organisationFunctionCreatedTime = rs.getLong("organisationFunction_createdTime");
        Long organisationFunctionLastModifiedTime = rs.getLong("organisationFunction_lastModifiedTime");

        AuditDetails organisationFunctionAuditDetails = AuditDetails.builder().createdBy(organisationFunctionCreatedBy).createdTime(organisationFunctionCreatedTime)
                .lastModifiedBy(organisationFunctionLastModifiedBy).lastModifiedTime(organisationFunctionLastModifiedTime)
                .build();

        return Function.builder()
                .id(organisationFunctionId)
                .orgId(organisationFunctionOrgId)
                .applicationNumber(organisationFunctionApplicationNumber)
                .type(organisationFunctionType)
                .category(organisationFunctionCategory)
                .propertyClass(organisationFunctionClass)
                .validFrom(organisationFunctionValidFrom)
                .validTo(organisationFunctionValidTo)
                .applicationStatus(ApplicationStatus.fromValue(organisationFunctionApplicationStatus))
                .wfStatus(organisationFunctionWfStatus)
                .isActive(organisationFunctionIsActive)
                .additionalDetails(organisationFunctionAdditionalDetails)
                .auditDetails(organisationFunctionAuditDetails)
                .build();
    }

    private void addFunctionToOrganisation(Organisation organisation, ResultSet resultSet) throws SQLException {
        Function function = createFunctionObjFromResultSet(resultSet);
        organisation.addFunctionsItem(function);
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
        if (additionalDetails != null && additionalDetails.isEmpty())
            additionalDetails = null;
        return additionalDetails;
    }

}
