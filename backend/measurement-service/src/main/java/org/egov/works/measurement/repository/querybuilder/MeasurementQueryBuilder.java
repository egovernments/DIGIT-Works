package org.egov.works.measurement.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.egov.works.measurement.web.models.MeasurementCriteria;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class MeasurementQueryBuilder {

//    private static final String BASE_MEASUREMENT_QUERY = "SELECT " +
//            "m.id as id, " +
//            "m.tenantId as tenantId, " +
//            "m.mbNumber as measurementNumber, " +
//            "m.phyRefNumber as physicalRefNumber, " +
//            "m.referenceId as referenceId, " +
//            "m.entryDate as entryDate, " +
//            "m.isActive as isActive, " +
//            "m.createdby as auditDetails_createdBy, " +  // Modify aliases for nested fields
//            "m.lastmodifiedby as auditDetails_lastModifiedBy, " +
//            "m.createdtime as auditDetails_createdTime, " +
//            "m.lastmodifiedtime as auditDetails_lastModifiedTime, " +
//            "m.additionalDetails as additionalDetails, " +
//            "md.id as measures_0_id, " +  // Modify aliases for nested fields
//            "md.referenceId as measures_0_referenceId, " +
//            "md.targetId as measures_0_targetId, " +
//            "md.length as measures_0_length, " +
//            "md.breadth as measures_0_breadth, " +
//            "md.height as measures_0_height, " +
//            "md.numItems as measures_0_numItems, " +
//            "md.currentValue as measures_0_currentValue, " +
//            "md.cumulativeValue as measures_0_cumulativeValue, " +
//            "md.isActive as measures_0_isActive, " +
//            "md.comments as measures_0_comments, " +
//            "md.documents as measures_0_documents, " +
//            "md.auditDetails as measures_0_auditDetails, " +
//            "md.additionalDetails as measures_0_additionalDetails " +
//            "FROM eg_mb_measurements m " +
//            "LEFT JOIN eg_mb_measurement_details md ON m.id = md.referenceId " +
//            "LEFT JOIN eg_mb_measurement_measures mm ON md.id = mm.id";



    private static final String BASE_MEASUREMENT_QUERY = "SELECT m.id as id, m.tenantId as tenantId, m.mbNumber as mbNumber, m.phyRefNumber as phyRefNumber, m.referenceId as referenceId, " +
            "m.entryDate as entryDate, m.isActive as isActive, m.createdby as createdby, m.lastmodifiedby as lastmodifiedby, " +
            "m.createdtime as createdtime, m.lastmodifiedtime as lastmodifiedtime, m.additionalDetails as additionalDetails, " +

            "md.id as mdid, md.targetId as targetId, md.mbNumber as mdmbNumber, md.isActive as mdisActive, md.description as mddescription, " +
            "md.createdby as mdcreatedby, md.lastmodifiedby as mdlastmodifiedby, md.createdtime as mdcreatedtime, md.lastmodifiedtime as mdlastmodifiedtime, " +

            "mm.id as mmid, mm.length as mmlength, mm.breadth as mmbreadth, mm.height as mmheight, mm.numOfItems as mmnumOfItems, mm.totalValue as mmtotalValue, " +

            "mm.createdby as mmcreatedby, mm.lastmodifiedby as mmlastmodifiedby, mm.createdtime as mmcreatedtime, mm.lastmodifiedtime as mmlastmodifiedtime " +
            "FROM eg_mb_measurements m " +
            "LEFT JOIN eg_mb_measurement_details md ON m.id = md.referenceId " +
            "LEFT JOIN eg_mb_measurement_measures mm ON md.id = mm.id ";


    private final String ORDER_BY_CREATED_TIME = "ORDER BY m.createdtime DESC";

    public String getMeasurementSearchQuery(MeasurementCriteria criteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(BASE_MEASUREMENT_QUERY);

        if (!ObjectUtils.isEmpty(criteria.getReferenceId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" m.referenceId = ? ");
            preparedStmtList.add(criteria.getReferenceId().get(0));
        }
        if (!ObjectUtils.isEmpty(criteria.getMeasurementNumber())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" m.mbNumber = ? ");
            preparedStmtList.add(criteria.getMeasurementNumber());
        }
        if (!CollectionUtils.isEmpty(criteria.getIds())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" m.id IN (").append(createQuery(criteria.getIds())).append(")");
            addToPreparedStatement(preparedStmtList, criteria.getIds());
        }

        // Add additional criteria as needed

        // Order measurements based on their createdtime in latest first manner
        query.append(ORDER_BY_CREATED_TIME);

        return query.toString();
    }

    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList) {
        if (preparedStmtList.isEmpty()) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }
    }

    private String createQuery(List<String> ids) {
        StringBuilder builder = new StringBuilder();
        int length = ids.size();
        for (int i = 0; i < length; i++) {
            builder.append(" ?");
            if (i != length - 1)
                builder.append(",");
        }
        return builder.toString();
    }

    private void addToPreparedStatement(List<Object> preparedStmtList, List<String> ids) {
        ids.forEach(id -> {
            preparedStmtList.add(id);
        });
    }
}


