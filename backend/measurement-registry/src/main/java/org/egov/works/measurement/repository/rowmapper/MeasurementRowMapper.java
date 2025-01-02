package org.egov.works.measurement.repository.rowmapper;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.models.Document;
import org.egov.works.measurement.web.models.Measure;
import org.egov.works.measurement.web.models.Measurement;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class MeasurementRowMapper implements ResultSetExtractor<ArrayList<Measurement>> {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public ArrayList<Measurement> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Measurement> measurementMap = new LinkedHashMap<>();
        Map<String,Document>documentMap = new HashMap<>();
        Set<String> measuresIds=new HashSet<>();

        while (rs.next()) {
            String uuid = rs.getString("id");
            Measurement measurement = measurementMap.get(uuid);

            if (measurement == null) {
                measurement = new Measurement();
                measurement.setId(uuid);
                measurement.setTenantId(rs.getString("tenantId"));
                measurement.setMeasurementNumber(rs.getString("mbNumber"));
                measurement.setPhysicalRefNumber(rs.getString("phyRefNumber"));
                measurement.setReferenceId(rs.getString("referenceId"));
                measurement.setEntryDate(rs.getBigDecimal("entryDate"));
                measurement.setIsActive(rs.getBoolean("isActive"));

                AuditDetails auditDetails = new AuditDetails();
                auditDetails.setCreatedBy(rs.getString("createdby"));
                auditDetails.setCreatedTime(rs.getLong("createdtime"));
                auditDetails.setLastModifiedBy(rs.getString("lastmodifiedby"));
                auditDetails.setLastModifiedTime(rs.getLong("lastmodifiedtime"));
                measurement.setAuditDetails(auditDetails);

                String additionalDetailsString = rs.getString("additionalDetails");
                JsonNode additionalDetailsJson = null;
                try {
                    additionalDetailsJson = objectMapper.readTree(additionalDetailsString);
                } catch (IOException e) {
                    // Handle the JSON parsing error
                    e.printStackTrace();
                }

                // Set the parsed JSON object to the measurement or document
                measurement.setAdditionalDetails(additionalDetailsJson);
                measurement.setMeasures(new ArrayList<>());
                measurement.setDocuments(new ArrayList<>());

                measurementMap.put(uuid, measurement);
            }

            // Created a Measure object and add it to the Measurement
            Measure measure = new Measure();
            if(!measuresIds.contains(rs.getString("mmid"))){
                measuresIds.add(rs.getString("mmid"));
                measure.setId(rs.getString("mmid"));
                measure.setReferenceId(rs.getString("mdreferenceId"));
                measure.setLength(rs.getBigDecimal("mmlength"));
                measure.setBreadth(rs.getBigDecimal("mmbreadth"));
                measure.setHeight(rs.getBigDecimal("mmheight"));
                measure.setNumItems(rs.getBigDecimal("mmnumOfItems"));
                measure.setCurrentValue(rs.getBigDecimal("mmcurrentValue"));
                measure.setCumulativeValue(rs.getBigDecimal("mmcumulativeValue"));
                measure.setTargetId(rs.getString("targetId"));
                measure.setComments(rs.getString("mddescription"));
                measure.setIsActive(rs.getBoolean("mdisActive"));

                String additionalDetailsString = rs.getString("mdadditionalDetails");
                JsonNode additionalDetailsJson = null;
                try {
                    additionalDetailsJson = objectMapper.readTree(additionalDetailsString);
                } catch (IOException e) {
                    // Handle the JSON parsing error
                    e.printStackTrace();
                }

                // Set the parsed JSON object to the measurement or document
                measure.setAdditionalDetails(additionalDetailsJson);
                AuditDetails auditDetails = new AuditDetails();
                auditDetails.setCreatedBy(rs.getString("createdby"));
                auditDetails.setCreatedTime(rs.getLong("createdtime"));
                auditDetails.setLastModifiedBy(rs.getString("lastmodifiedby"));
                auditDetails.setLastModifiedTime(rs.getLong("lastmodifiedtime"));
                measure.setAuditDetails(auditDetails);
                measurement.getMeasures().add(measure);
            }

            Document document = new Document();
            document.setId(rs.getString("dcid"));

            if(document.getId()!=null && documentMap.get(document.getId())==null) {

                document.setDocumentType(rs.getString("documentType"));
                document.setFileStore(rs.getString("filestore"));
                document.setDocumentUid(rs.getString("documentuuid"));

                String additionalDetailsString = rs.getString("dcadditionalDetails");
                JsonNode additionalDetailsJson = null;
                try {
                    additionalDetailsJson = objectMapper.readTree(additionalDetailsString);
                } catch (IOException e) {
                    // Handle the JSON parsing error
                    e.printStackTrace();
                }

                // Set the parsed JSON object to the measurement or document
                document.setAdditionalDetails(additionalDetailsJson);

                document.setId(rs.getString("dcid"));
                documentMap.put(document.getId(), document);

                measurement.getDocuments().add(document);
            }
        }
        return new ArrayList<>(measurementMap.values());
    }

}