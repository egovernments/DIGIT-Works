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

            "md.id as mdid, md.targetId as targetId, md.isActive as mdisActive, md.description as mddescription, md.referenceId as mdreferenceId ," +
            "md.createdby as mdcreatedby, md.lastmodifiedby as mdlastmodifiedby, md.createdtime as mdcreatedtime, md.lastmodifiedtime as mdlastmodifiedtime, " +

            "mm.id as mmid, mm.length as mmlength, mm.breadth as mmbreadth, mm.height as mmheight, mm.numOfItems as mmnumOfItems, mm.currentValue as mmcurrentValue, mm.cumulative as mmcumulativeValue, " +
            "mm.createdby as mmcreatedby, mm.lastmodifiedby as mmlastmodifiedby, mm.createdtime as mmcreatedtime, mm.lastmodifiedtime as mmlastmodifiedtime, " +

            "dc.filestore as filestore, dc.documentType as documentType, dc.documentuuid as documentuuid, dc.additionaldetails as additionaldetails, dc.id as dcid " +

            "FROM eg_mb_measurements m " +

            "INNER JOIN eg_mb_measurement_details md ON m.id = md.referenceId " +
            "INNER JOIN eg_mb_measurement_measures mm ON md.id = mm.id "+
            "INNER JOIN eg_mb_measurement_documents dc ON dc.referenceId = m.id";

    private final String ORDER_BY_CREATED_TIME = "ORDER BY m.createdtime DESC";


    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList) {
        if (preparedStmtList.isEmpty()) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }
    }

    public String getMeasurementSearchQuery(MeasurementCriteria criteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(BASE_MEASUREMENT_QUERY);

        boolean tenantIdProvided = !ObjectUtils.isEmpty(criteria.getTenantId());

        if (tenantIdProvided) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" m.tenantId = ? ");
            preparedStmtList.add(criteria.getTenantId());
        }

        if (!CollectionUtils.isEmpty(criteria.getIds())) {
            if (tenantIdProvided || !ObjectUtils.isEmpty(criteria.getMeasurementNumber()) || !CollectionUtils.isEmpty(criteria.getReferenceId())) {
                query.append(" AND "); // Add AND if tenantId, mbNumber, or referenceId is provided
            } else {
                addClauseIfRequired(query, preparedStmtList);
            }
            query.append(" m.id IN (").append(createQuery(criteria.getIds())).append(")");
            addToPreparedStatement(preparedStmtList, criteria.getIds());
        }

        if (!CollectionUtils.isEmpty(criteria.getReferenceId())) {
            if (tenantIdProvided || !ObjectUtils.isEmpty(criteria.getMeasurementNumber())) {
                query.append(" AND "); // Add AND if tenantId or mbNumber is provided
            } else {
                addClauseIfRequired(query, preparedStmtList);
            }
            query.append(" m.referenceId IN (").append(createQuery(criteria.getReferenceId())).append(")");
            addToPreparedStatement(preparedStmtList, criteria.getReferenceId());
        }

        if (!ObjectUtils.isEmpty(criteria.getMeasurementNumber())) {
            if (tenantIdProvided) {
                query.append(" AND "); // Add AND if tenantId is provided
            } else {
                addClauseIfRequired(query, preparedStmtList);
            }
            query.append(" m.mbNumber = ? ");
            preparedStmtList.add(criteria.getMeasurementNumber());
        }

        query.append(ORDER_BY_CREATED_TIME);

        return query.toString();
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