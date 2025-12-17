package org.egov.works.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.BasicSor;
import org.egov.works.web.models.BasicSorDetails;
import org.egov.works.web.models.SorDetail;
import org.egov.works.web.models.Statement;
import org.postgresql.util.PGobject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Slf4j
public class StatementRowMapper implements ResultSetExtractor<List<Statement>> {
    private final ObjectMapper mapper;

    public StatementRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    @Override
      public List<Statement> extractData(ResultSet rs) throws SQLException, DataAccessException {
        log.info("StatementRowMapper::extractData");
        Map<String, Statement> statementMap = new LinkedHashMap<>();
        Map<String, SorDetail> sorDetailMap = new LinkedHashMap<>();

        while (rs.next()) {
            String id = rs.getString("id");
            String tenantId = rs.getString("tenantId");
            String targetId = rs.getString("targetId");
            String statementType = rs.getString("statementType");
            String basicSorDetails = rs.getString("basicSorDetails");
            String createdby = rs.getString("createdBy");
            String lastmodifiedby = rs.getString("lastModifiedBy");
            Long createdtime = rs.getLong("createdTime");
            Long lastmodifiedtime = rs.getLong("lastModifiedTime");

           AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("additionalDetails", rs);
            List<BasicSorDetails >basicSorDetailsList = new ArrayList<>();
            if(basicSorDetails!=null){

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    basicSorDetailsList = objectMapper.readValue(basicSorDetails, ArrayList.class);
                } catch (Exception e) {
                    throw new SQLException("Error converting JSONB to POJO", e);
                }
            }



            Statement statement =Statement.builder()
                    .id(id).tenantId(tenantId).basicSorDetails(basicSorDetailsList)
                    .statementType(Statement.StatementTypeEnum.valueOf(statementType))
                    .targetId(targetId).additionalDetails(additionalDetails).auditDetails(auditDetails).build();


            if (!statementMap.containsKey(id)) {
                statementMap.put(id, statement);
            }

            addSorDetails(rs, sorDetailMap, statementMap.get(id));


        }
        return new ArrayList<>(statementMap.values());
    }

    private void addSorDetails(ResultSet rs, Map<String, SorDetail> sorDetailMap, Statement statement) throws SQLException {
        log.info("StatementRowMapper::addSorDetails");
        Map<String, BasicSor> basicSorLineItemsMap = new LinkedHashMap<>();
        String sorDetailId = rs.getString("sorDetailsId");
        String basicSorDetails = rs.getString("sorDetailsBasicSorDetails");
        List<BasicSorDetails >basicSorDetailsList = new ArrayList<>();
        if(basicSorDetails!=null){

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                basicSorDetailsList = objectMapper.readValue(basicSorDetails, ArrayList.class);
            } catch (Exception e) {
                throw new SQLException("Error converting JSONB to POJO", e);
            }
        }
        JsonNode additionalDetails = getAdditionalDetail("sorAdditionalDetails", rs);
        SorDetail sorDetail = SorDetail.builder()
                .id(sorDetailId)
                .statementId(rs.getString("statementId"))
                .tenantId(rs.getString("sorDetailsTenantId"))
                .sorId(rs.getString("sorId"))
                .isActive(rs.getBoolean("isActive"))
                .basicSorDetails(basicSorDetailsList)
                .additionalDetails(additionalDetails)
                .build();



        if (!sorDetailMap.containsKey(sorDetailId)) {
            sorDetailMap.put(sorDetailId, sorDetail);
        } else {
            sorDetail = sorDetailMap.get(sorDetailId);
        }

        addBasicSorLineItems(rs,basicSorLineItemsMap,sorDetail);

        if (statement.getSorDetails() == null || statement.getSorDetails().isEmpty()) {
            List<SorDetail> sorDetailList = new LinkedList<>();
            sorDetailList.add(sorDetail);
            statement.setSorDetails(sorDetailList);
        } else {
            // Check if sorDetail's id is already present
            SorDetail finalSorDetail = sorDetail;
            if (sorDetail.getId() != null && statement.getSorDetails().stream().noneMatch(sd -> sd.getId().equals(finalSorDetail.getId()))) {
                statement.getSorDetails().add(sorDetail);
            }
        }
    }

    private void addBasicSorLineItems(ResultSet rs, Map<String, BasicSor> basicSorLineItemsMap,  SorDetail sorDetail) throws SQLException {
        log.info("StatementRowMapper::addBasicSorLineItems");
        String basicSorLineItemId = rs.getString("lineItemId");
        String lineItemsBasicSorDetails = rs.getString("lineItemsBasicSorDetails");

        List<BasicSorDetails >basicSorDetailsList = new ArrayList<>();
        if(lineItemsBasicSorDetails!=null){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                basicSorDetailsList = objectMapper.readValue(lineItemsBasicSorDetails, ArrayList.class);
            } catch (Exception e) {
                throw new SQLException("Error converting JSONB to POJO", e);
            }
        }
        JsonNode additionalDetails = getAdditionalDetail("lineItemAdditionalDetails", rs);
        BasicSor basicSor = BasicSor.builder()
                .id(basicSorLineItemId)
                .sorType(rs.getString("lineItemSorType"))
                .sorId(rs.getString("lineItemsSorId"))
                .referenceId(rs.getString("lineItemIdReferenceId"))
                .basicSorDetails(basicSorDetailsList)
                .additionalDetails(additionalDetails)
                .build();



        if (!basicSorLineItemsMap.containsKey(basicSorLineItemId)) {
            basicSorLineItemsMap.put(basicSorLineItemId, basicSor);
        } else {
            basicSor = basicSorLineItemsMap.get(basicSorLineItemId);
        }

        if(basicSor.getId()!=null || basicSor.getReferenceId()!=null){
          if (sorDetail.getLineItems() == null || sorDetail.getLineItems().isEmpty()) {
           // log.info("NO_LINE_ITEMS_PRESENT","For Basic Sor No line items is present");
              sorDetail.setLineItems(new ArrayList<>(Collections.singletonList(basicSor)));
        } else {
            sorDetail.getLineItems().add(basicSor);
        }
        }
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
