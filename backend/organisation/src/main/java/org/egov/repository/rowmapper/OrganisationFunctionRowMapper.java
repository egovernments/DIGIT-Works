package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
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

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<Organisation> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<String, Organisation> organisationMap = new LinkedHashMap<>();
        while (resultSet.next()) {
            String org_id = resultSet.getString("organisation_Id");

            if (!organisationMap.containsKey(org_id)) {
                organisationMap.put(org_id, createOrganisationObj(resultSet));
            }
            else {
                addFunctionToOrganisation(organisationMap.get(org_id), resultSet);
            }
        }

        return new ArrayList<>(organisationMap.values());
    }

    private Organisation createOrganisationObj(ResultSet rs) throws SQLException, DataAccessException {

        Function function = createFunctionObjFromResultSet(rs);
        List<Function> functions = new ArrayList<>();
        functions.add(function);

        String organisation_Id = rs.getString("organisation_Id");
        String organisation_tenantId = rs.getString("organisation_tenantId");
        String organisation_applicationNumber = rs.getString("organisation_applicationNumber");
        String organisation_name = rs.getString("organisation_name");
        String organisation_orgNumber = rs.getString("organisation_orgNumber");
        String organisation_externalRefNumber = rs.getString("organisation_externalRefNumber");
        BigDecimal organisation_dateOfIncorporation = rs.getBigDecimal("organisation_dateOfIncorporation");;
        String organisation_applicationStatus = rs.getString("organisation_applicationStatus");
        Boolean organisation_isActive = rs.getBoolean("organisation_isActive");
        JsonNode organisation_additionalDetails = getAdditionalDetail("organisation_additionalDetails", rs);
        String organisation_createdBy = rs.getString("organisation_createdBy");
        String organisation_lastModifiedBy = rs.getString("organisation_lastModifiedBy");
        Long organisation_createdTime = rs.getLong("organisation_createdTime");
        Long organisation_lastModifiedTime = rs.getLong("organisation_lastModifiedTime");

        AuditDetails organisationAuditDetails = AuditDetails.builder().createdBy(organisation_createdBy).createdTime(organisation_createdTime)
                .lastModifiedBy(organisation_lastModifiedBy).lastModifiedTime(organisation_lastModifiedTime)
                .build();

        Organisation organisation = Organisation.builder()
                .id(organisation_Id)
                .tenantId(organisation_tenantId)
                .applicationNumber(organisation_applicationNumber)
                .name(organisation_name)
                .orgNumber(organisation_orgNumber)
                .externalRefNumber(organisation_externalRefNumber)
                .dateOfIncorporation(organisation_dateOfIncorporation)
                .applicationStatus(ApplicationStatus.fromValue(organisation_applicationStatus))
                .isActive(organisation_isActive)
                .functions(functions)
                .additionalDetails(organisation_additionalDetails)
                .auditDetails(organisationAuditDetails)
                .build();

        return organisation;
    }

    private Function createFunctionObjFromResultSet(ResultSet rs) throws SQLException  {
        String organisationFunction_Id = rs.getString("organisationFunction_Id");
        String organisationFunction_OrgId = rs.getString("organisationFunction_OrgId");
        String organisationFunction_applicationNumber = rs.getString("organisationFunction_applicationNumber");
        String organisationFunction_type = rs.getString("organisationFunction_type");
        String organisationFunction_category = rs.getString("organisationFunction_category");
        String organisationFunction_class = rs.getString("organisationFunction_class");
        BigDecimal organisationFunction_valid_from = rs.getBigDecimal("organisationFunction_valid_from");
        BigDecimal organisationFunction_validTo = rs.getBigDecimal("organisationFunction_validTo");
        String organisationFunction_applicationStatus = rs.getString("organisationFunction_applicationStatus");
        String organisationFunction_wfStatus = rs.getString("organisationFunction_wfStatus");
        Boolean organisationFunction_isActive = rs.getBoolean("organisationFunction_isActive");
        JsonNode organisationFunction_additionalDetails = getAdditionalDetail("organisationFunction_additionalDetails", rs);
        String organisationFunction_createdBy = rs.getString("organisationFunction_createdBy");
        String organisationFunction_lastModifiedBy = rs.getString("organisationFunction_lastModifiedBy");
        Long organisationFunction_createdTime = rs.getLong("organisationFunction_createdTime");
        Long organisationFunction_lastModifiedTime = rs.getLong("organisationFunction_lastModifiedTime");

        AuditDetails organisationFunctionAuditDetails = AuditDetails.builder().createdBy(organisationFunction_createdBy).createdTime(organisationFunction_createdTime)
                .lastModifiedBy(organisationFunction_lastModifiedBy).lastModifiedTime(organisationFunction_lastModifiedTime)
                .build();

        Function function = Function.builder()
                .id(organisationFunction_Id)
                .orgId(organisationFunction_OrgId)
                .applicationNumber(organisationFunction_applicationNumber)
                .type(organisationFunction_type)
                .category(organisationFunction_category)
                .propertyClass(organisationFunction_class)
                .validFrom(organisationFunction_valid_from)
                .validTo(organisationFunction_validTo)
                .applicationStatus(ApplicationStatus.fromValue(organisationFunction_applicationStatus))
                .wfStatus(organisationFunction_wfStatus)
                .isActive(organisationFunction_isActive)
                .additionalDetails(organisationFunction_additionalDetails)
                .auditDetails(organisationFunctionAuditDetails)
                .build();

        return function;
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
