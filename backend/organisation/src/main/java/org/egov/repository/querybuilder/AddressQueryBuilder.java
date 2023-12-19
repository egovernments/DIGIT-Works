package org.egov.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class AddressQueryBuilder {
    private static final String FETCH_ADDRESS_QUERY = "SELECT addr.id as address_id, addr.tenant_id as address_tenantId, " +
            "addr.org_id as address_orgId, addr.door_no as address_doorNo, addr.plot_no as address_plotNo, " +
            "addr.landmark as address_landmark, addr.city as address_city, addr.pin_code as address_pinCode, " +
            "addr.district as address_district, addr.region as address_region, addr.state as address_state, " +
            "addr.country as address_country, addr.boundary_code as address_boundaryCode, addr.boundary_type as address_boundaryType, " +
            "addr.building_name as address_buildingName, addr.street as address_street, addr.additional_details as address_additionalDetails, " +
            "addrGeoLoc.id as addressGeoLocation_Id, addrGeoLoc.address_id as addressGeoLocation_addressId, " +
            "addrGeoLoc.latitude as addressGeoLocation_latitude, addrGeoLoc.longitude as addressGeoLocation_longitude, " +
            "addrGeoLoc.additional_details as addressGeoLocation_additionalDetails " +
            "FROM eg_org_address addr " +
            "LEFT JOIN eg_org_address_geo_location addrGeoLoc ON addr.id = addrGeoLoc.address_id";

    /* Constructs address search query based on organisation Ids */
    public String getAddressSearchQuery(Set<String> organisationIds, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = null;
        queryBuilder = new StringBuilder(FETCH_ADDRESS_QUERY);

        if (organisationIds != null && !organisationIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" addr.org_id IN (").append(createQuery(organisationIds)).append(")");
            addToPreparedStatement(preparedStmtList, organisationIds);
        }

        return queryBuilder.toString();
    }

    public String getAddressSearchQueryBasedOnCriteria(String boundaryCode, String tenantId, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = null;
        queryBuilder = new StringBuilder(FETCH_ADDRESS_QUERY);

        if (StringUtils.isNotBlank(tenantId)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" addr.tenant_id=? ");
            preparedStmtList.add(tenantId);
        }

        if (StringUtils.isNotBlank(boundaryCode)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" addr.boundary_code=? ");
            preparedStmtList.add(boundaryCode);
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
        ids.forEach(preparedStmtList::add);
    }

}
