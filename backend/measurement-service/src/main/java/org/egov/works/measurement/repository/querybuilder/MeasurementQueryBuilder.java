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

        if (!CollectionUtils.isEmpty(criteria.getReferenceId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" m.referenceId IN (").append(createQuery(criteria.getReferenceId())).append(")");
            addToPreparedStatement(preparedStmtList, criteria.getReferenceId());
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
        if (!ObjectUtils.isEmpty(criteria.getTenantId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" m.tenantId = ? ");
            preparedStmtList.add(criteria.getTenantId());
        }
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


