package org.egov.works.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.Address;
import org.egov.works.web.models.Boundary;
import org.egov.works.web.models.Project;
import org.egov.works.web.models.Target;
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
public class ProjectAddressRowMapper implements ResultSetExtractor<List<Project>> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<Project> extractData(ResultSet rs) throws SQLException, DataAccessException {

        Map<String, Project> projectMap = new LinkedHashMap<>();
        while (rs.next()) {
            String project_id = rs.getString("project_id");

            Project project = !projectMap.containsKey(project_id) ? createProjectObj(rs) : projectMap.get(project_id);

            if (!projectMap.containsKey(project_id)) {
                projectMap.put(project_id, project);
            }
        }

        return new ArrayList<>(projectMap.values());
    }

    private Project createProjectObj(ResultSet rs) throws SQLException, DataAccessException {
        String project_id = rs.getString("project_id");
        String project_tenantid = rs.getString("project_tenantid");
        String project_projecttype = rs.getString("project_projecttype");
        String project_projectsubtype = rs.getString("project_projectsubtype");
        String project_department = rs.getString("project_department");
        String project_description = rs.getString("project_description");
        String project_referenceid = rs.getString("project_referenceid");
        Long project_startdate = rs.getLong("project_startdate");
        Long project_enddate = rs.getLong("project_enddate");
        Boolean project_istaskenabled = rs.getBoolean("project_istaskenabled");
        String project_parent = rs.getString("project_parent");
        JsonNode project_additionaldetails = getAdditionalDetail("project_additionaldetails", rs);
        Boolean project_isdeleted = rs.getBoolean("project_isdeleted");
        Integer project_rowversion = rs.getInt("project_rowversion");
        String project_createdby = rs.getString("project_createdby");
        String project_lastmodifiedby = rs.getString("project_lastmodifiedby");
        Long project_createdtime = rs.getLong("project_createdtime");
        Long project_lastmodifiedtime = rs.getLong("project_lastmodifiedtime");

        String address_id = rs.getString("address_id");
        String address_tenantid = rs.getString("address_tenantid");
        String address_projectid = rs.getString("address_projectid");
        String address_doorno = rs.getString("address_doorno");
        Double address_latitude = rs.getDouble("address_latitude");
        Double address_longitude = rs.getDouble("address_longitude");
        Double address_locationaccuracy = rs.getDouble("address_locationaccuracy");
        String address_type = rs.getString("address_type");
        String address_addressline1 = rs.getString("address_addressline1");
        String address_addressline2 = rs.getString("address_addressline2");
        String address_landmark = rs.getString("address_landmark");
        String address_city = rs.getString("address_city");
        String address_pincode = rs.getString("address_pincode");
        String address_buildingname = rs.getString("address_buildingname");
        String address_street = rs.getString("address_street");
        String address_createdby = rs.getString("address_createdby");
        String address_lastmodifiedby = rs.getString("address_lastmodifiedby");
        Long address_createdtime = rs.getLong("address_createdtime");
        Long address_lastmodifiedtime = rs.getLong("address_lastmodifiedtime");

        AuditDetails projectAuditDetails = AuditDetails.builder().createdBy(project_createdby).createdTime(project_createdtime)
                .lastModifiedBy(project_lastmodifiedby).lastModifiedTime(project_lastmodifiedtime)
                .build();
        AuditDetails addresstAuditDetails = AuditDetails.builder().createdBy(address_createdby).createdTime(address_createdtime)
                .lastModifiedBy(address_lastmodifiedby).lastModifiedTime(address_lastmodifiedtime)
                .build();

        Address address = Address.builder()
                .id(address_id)
                .tenantId(address_tenantid)
                .doorNo(address_doorno)
                .latitude(address_latitude)
                .longitude(address_longitude)
                .locationAccuracy(address_locationaccuracy)
                .type(address_type)
                .addressLine1(address_addressline1)
                .addressLine2(address_addressline2)
                .landmark(address_landmark)
                .city(address_city)
                .pincode(address_pincode)
                .buildingName(address_buildingname)
                .street(address_street)
                .auditDetails(addresstAuditDetails)
                .build();

        Project project = Project.builder()
                .id(project_id)
                .tenantId(project_tenantid)
                .projectType(project_projecttype)
                .projectSubType(project_projectsubtype)
                .department(project_department)
                .description(project_description)
                .referenceID(project_referenceid)
                .startDate(project_startdate)
                .endDate(project_enddate)
                .isTaskEnabled(project_istaskenabled)
                .parent(project_parent)
                .additionalDetails(project_additionaldetails)
                .isDeleted(project_isdeleted)
                .rowVersion(project_rowversion)
                .address(address)
                .auditDetails(projectAuditDetails)
                .build();
        return project;
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
