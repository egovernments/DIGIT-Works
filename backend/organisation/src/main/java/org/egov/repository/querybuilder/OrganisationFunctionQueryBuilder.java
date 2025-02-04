package org.egov.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.config.Configuration;
import org.egov.web.models.OrgSearchCriteria;
import org.egov.web.models.OrgSearchRequest;
import org.egov.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class OrganisationFunctionQueryBuilder {

    private final Configuration config;

    private static final String FETCH_ORGANISATION_FUNCTION_QUERY = "SELECT org.id as organisation_Id, org.tenant_id as organisation_tenantId, " +
            "org.application_number as organisation_applicationNumber, org.name as organisation_name, org.org_number as organisation_orgNumber, " +
            "org.external_ref_number as organisation_externalRefNumber, org.date_of_incorporation as organisation_dateOfIncorporation, " +
            "org.application_status as organisation_applicationStatus, org.is_active as organisation_isActive, " +
            "org.additional_details as organisation_additionalDetails, org.created_by as organisation_createdBy, " +
            "org.last_modified_by as organisation_lastModifiedBy, org.created_time as organisation_createdTime, " +
            "org.last_modified_time as organisation_lastModifiedTime, " +
            "orgFunction.id as organisationFunction_Id, orgFunction.org_id as organisationFunction_OrgId, " +
            "orgFunction.application_number as organisationFunction_applicationNumber, orgFunction.type as organisationFunction_type, " +
            "orgFunction.category as organisationFunction_category, orgFunction.class as organisationFunction_class, " +
            "orgFunction.valid_from as organisationFunction_valid_from, orgFunction.valid_to as organisationFunction_validTo, " +
            "orgFunction.application_status as organisationFunction_applicationStatus, orgFunction.wf_status as organisationFunction_wfStatus, " +
            "orgFunction.is_active as organisationFunction_isActive, orgFunction.additional_details as organisationFunction_additionalDetails, " +
            "orgFunction.created_by as organisationFunction_createdBy, orgFunction.last_modified_by as organisationFunction_lastModifiedBy, " +
            "orgFunction.created_time as organisationFunction_createdTime, orgFunction.last_modified_time as organisationFunction_lastModifiedTime " +
            "FROM eg_org org " +
            "LEFT JOIN eg_org_function orgFunction ON org.id = orgFunction.org_id";

    private static final String PAGINATION_WRAPPER = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY organisation_lastModifiedTime DESC , organisation_Id) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";

    private static final String ORGANISATIONS_COUNT_QUERY = "SELECT DISTINCT(org.id) from eg_org org " +
            "LEFT JOIN eg_org_function orgFunction ON org.id = orgFunction.org_id";

    private static final String COUNT_WRAPPER = "SELECT COUNT(*) FROM ({INTERNAL_QUERY}) as count";

    @Autowired
    public OrganisationFunctionQueryBuilder(Configuration config) {
        this.config = config;
    }

    public String getOrganisationSearchQuery(OrgSearchRequest orgSearchRequest, Set<String> orgIds, List<Object> preparedStmtList, Boolean isCountQuery) {
        String query = Boolean.TRUE.equals(isCountQuery) ? ORGANISATIONS_COUNT_QUERY : FETCH_ORGANISATION_FUNCTION_QUERY;
        StringBuilder queryBuilder = new StringBuilder(query);
        OrgSearchCriteria searchCriteria = orgSearchRequest.getSearchCriteria();

        if (orgIds != null && !orgIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org.id IN (").append(createQuery(orgIds)).append(")");
            addToPreparedStatement(preparedStmtList, orgIds);
        }

        if (StringUtils.isNotBlank(searchCriteria.getTenantId()) && searchCriteria.getTenantId().contains(config.getStateLevelTenantId()+".")) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org.tenant_id=? ");
            preparedStmtList.add(searchCriteria.getTenantId());
        }

        if (StringUtils.isNotBlank(searchCriteria.getName())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org.name LIKE ? ");
            preparedStmtList.add('%' + searchCriteria.getName() + '%');
        }

        if (StringUtils.isNotBlank(searchCriteria.getApplicationNumber())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org.application_number=? ");
            preparedStmtList.add(searchCriteria.getApplicationNumber());
        }

        if (StringUtils.isNotBlank(searchCriteria.getOrgNumber())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org.org_number=? ");
            preparedStmtList.add(searchCriteria.getOrgNumber());
        }

        if (StringUtils.isNotBlank(searchCriteria.getApplicationStatus())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org.application_status=? ");
            preparedStmtList.add(searchCriteria.getApplicationStatus());
        }

        if (searchCriteria.getCreatedFrom() != null && searchCriteria.getCreatedFrom() != 0) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org.created_time >= ? ");
            preparedStmtList.add(searchCriteria.getCreatedFrom());
        }

        if (searchCriteria.getCreatedTo() != null && searchCriteria.getCreatedTo() != 0) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org.created_time <= ? ");
            preparedStmtList.add(searchCriteria.getCreatedTo());
        }


        if (searchCriteria.getIncludeDeleted() == null || !searchCriteria.getIncludeDeleted()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org.is_active=true ");
        }

        if (searchCriteria.getFunctions() != null) {

            // This search matches with only organisation type which is the first part of the '.' separated value as well as
            // exact value in database. i.e. OrgType along with organisation subtype which is '.' separated value
            if (StringUtils.isNotBlank(searchCriteria.getFunctions().getType())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                //This query checks first part of the type field in db
                queryBuilder.append("( LEFT(orgFunction.type, POSITION('.' in orgFunction.type)-1) = ? ");
                //If the type doesn't have '.' i.e. the organisation doesn't have subtype
                queryBuilder.append(" OR orgFunction.type = ?  )");
                preparedStmtList.add(searchCriteria.getFunctions().getType());
                preparedStmtList.add(searchCriteria.getFunctions().getType());
            }

            if (StringUtils.isNotBlank(searchCriteria.getFunctions().getCategory())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" orgFunction.category=? ");
                preparedStmtList.add(searchCriteria.getFunctions().getCategory());
            }

            if (StringUtils.isNotBlank(searchCriteria.getFunctions().getPropertyClass())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" orgFunction.class=? ");
                preparedStmtList.add(searchCriteria.getFunctions().getPropertyClass());
            }

            if (searchCriteria.getFunctions().getValidFrom() != null && BigDecimal.ZERO.compareTo(searchCriteria.getFunctions().getValidFrom()) != 0) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" orgFunction.valid_from >= ? ");
                preparedStmtList.add(searchCriteria.getFunctions().getValidFrom());
            }

            if (searchCriteria.getFunctions().getValidTo() != null && BigDecimal.ZERO.compareTo(searchCriteria.getFunctions().getValidTo()) != 0) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" orgFunction.valid_to <= ? ");
                preparedStmtList.add(searchCriteria.getFunctions().getValidTo());
            }

            if (StringUtils.isNotBlank(searchCriteria.getFunctions().getWfStatus())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" orgFunction.wf_status=? ");
                preparedStmtList.add(searchCriteria.getFunctions().getWfStatus());
            }

            if (searchCriteria.getIncludeDeleted() == null || !searchCriteria.getIncludeDeleted()) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" orgFunction.is_active=true ");
            }
        }

        if (Boolean.TRUE.equals(isCountQuery)) {
            return queryBuilder.toString();
        }

        addOrderByClause(queryBuilder, orgSearchRequest.getPagination());
        return addPaginationWrapper(queryBuilder.toString(), preparedStmtList, orgSearchRequest.getPagination());
    }

    private static void addClauseIfRequired(List<Object> values, StringBuilder queryString) {
        if (values.isEmpty())
            queryString.append(" WHERE ");
        else {
            queryString.append(" AND");
        }
    }

    private void addToPreparedStatement(List<Object> preparedStmtList, Collection<String> ids) {
        preparedStmtList.addAll(ids);
    }

    private String  createQuery(Collection<String> ids) {
        StringBuilder builder = new StringBuilder();
        int length = ids.size();
        for (int i = 0; i < length; i++) {
            builder.append(" ? ");
            if (i != length - 1) builder.append(",");
        }
        return builder.toString();
    }

    private void addOrderByClause(StringBuilder queryBuilder, Pagination pagination) {
        log.info("OrganisationQueryBuilder::getOrganisationQuery");
        //default
        if (pagination == null || pagination.getSortBy() == null) {
            queryBuilder.append(" ORDER BY org.created_time ");
        } else {
            switch (pagination.getSortBy()) {
                case "name":
                    queryBuilder.append(" ORDER BY org.name ");
                    break;
                case "type":
                    queryBuilder.append(" ORDER BY orgFunction.type ");
                    break;
                default:
                    queryBuilder.append(" ORDER BY est.created_time ");
                    break;
            }
        }

        if (pagination != null && pagination.getOrder() == "ASC")
            queryBuilder.append(" ASC ");
        else queryBuilder.append(" DESC ");
    }

    private String addPaginationWrapper(String query, List<Object> preparedStmtList, Pagination pagination) {
        log.info("OrganisationQueryBuilder::addPaginationWrapper");
        double limit = config.getDefaultLimit();
        double offset = config.getDefaultOffset();
        String finalQuery = PAGINATION_WRAPPER.replace("{}", query);

        if (pagination != null && pagination.getLimit() != null) {
            if (pagination.getLimit() <= config.getMaxLimit())
                limit = pagination.getLimit();
            else
                limit = config.getMaxLimit();
        }

        if (pagination != null && pagination.getOffset() != null)
            offset = pagination.getOffset();

        preparedStmtList.add(offset);
        preparedStmtList.add(limit + offset);

        return finalQuery;
    }

    public String getSearchCountQueryString(OrgSearchRequest orgSearchRequest, Set<String> orgIdsFromIdentifierAndBoundarySearch, List<Object> preparedStmtList) {
        log.info("OrganisationSearchQueryBuilder::getSearchCountQueryString");
        String query = getOrganisationSearchQuery(orgSearchRequest, orgIdsFromIdentifierAndBoundarySearch, preparedStmtList, true);
        if (query != null)
            return COUNT_WRAPPER.replace("{INTERNAL_QUERY}", query);
        else
            return query;
    }
}
