package org.egov.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class ContactDetailsQueryBuilder {

    private static final String FETCH_CONTACT_DETAILS_QUERY = "SELECT c.id as contactDetail_Id, c.tenant_id as contactDetail_tenantId, " +
            "c.org_id as contactDetail_orgId, c.contact_name as contactDetail_contactName, c.contact_mobile_number as contactDetail_contactMobileNumber, " +
            "c.contact_email as contactDetail_contactEmail, c.individual_id as contactDetail_contactIndividualId " +
            "FROM eg_org_contact_detail c ";
    public String getContactDetailsSearchQuery(Set<String> organisationIds, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = null;
        queryBuilder = new StringBuilder(FETCH_CONTACT_DETAILS_QUERY);

        if (organisationIds != null && !organisationIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" c.org_id IN (").append(createQuery(organisationIds)).append(")");
            addToPreparedStatement(preparedStmtList, organisationIds);
        }

        return queryBuilder.toString();
    }

    public String getContactDetailsSearchQueryBasedOnCriteria(String contactMobileNumber, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = null;
        queryBuilder = new StringBuilder(FETCH_CONTACT_DETAILS_QUERY);

        if (StringUtils.isNotBlank(contactMobileNumber)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" c.contact_mobile_number=? ");
            preparedStmtList.add(contactMobileNumber);
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
