package org.egov.works.repository.querybuilder;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Component
public class LocalityQueryBuilder {

    private static final String FETCH_LOCALITY_QUERY = "select loc.id as locality_id, loc.address_id as locality_addressid, loc.parent_id as locality_parentid, " +
            " loc.code as locality_code, loc.name as locality_name, loc.latitude as locality_latitude, loc.longitude as locality_longitude, loc.materializedpath as locality_materializedpath " +
            " from eg_pms_locality loc ";

    public String getLocalitySearchQuery(HashSet<String> addressIds, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = null;
        queryBuilder = new StringBuilder(FETCH_LOCALITY_QUERY);

        if (addressIds != null && !addressIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" loc.address_id IN (").append(createQuery(addressIds)).append(")");
            addToPreparedStatement(preparedStmtList, addressIds);
        }

        return queryBuilder.toString();
    }

    private static void addClauseIfRequired(List<Object> values, StringBuilder queryString) {
        if (values.isEmpty())
            queryString.append(" WHERE ");
        else {
            queryString.append(" AND");
        }
    }

    private String createQuery(Collection<String> ids) {
        StringBuilder builder = new StringBuilder();
        int length = ids.size();
        for (int i = 0; i < length; i++) {
            builder.append(" ? ");
            if (i != length - 1) builder.append(",");
        }
        return builder.toString();
    }

    private void addToPreparedStatement(List<Object> preparedStmtList, Collection<String> ids) {
        ids.forEach(id -> {
            preparedStmtList.add(id);
        });
    }
}
