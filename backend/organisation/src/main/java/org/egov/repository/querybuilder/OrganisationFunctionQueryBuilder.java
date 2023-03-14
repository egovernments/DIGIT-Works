package org.egov.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.config.Configuration;
import org.egov.web.models.OrgSearchCriteria;
import org.egov.web.models.OrgSearchRequest;
import org.egov.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class OrganisationFunctionQueryBuilder {

    @Autowired
    private Configuration config;

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

    private final String paginationWrapper = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY organisation_lastModifiedTime DESC , organisation_Id) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";

    public String getOrganisationSearchQuery(OrgSearchRequest orgSearchRequest, List<Object> preparedStmtList) {
        String query = FETCH_ORGANISATION_FUNCTION_QUERY;
        StringBuilder queryBuilder = new StringBuilder(query);
        OrgSearchCriteria searchCriteria = orgSearchRequest.getSearchCriteria();

        List<String> ids = searchCriteria.getId();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        if (StringUtils.isNotBlank(searchCriteria.getTenantId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org.tenant_id=? ");
            preparedStmtList.add(searchCriteria.getTenantId());
        }

        if (StringUtils.isNotBlank(searchCriteria.getName())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org.name=? ");
            preparedStmtList.add(searchCriteria.getName());
        }

        if (!searchCriteria.getIncludeDeleted()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org.is_active=true ");
        }

        if (searchCriteria.getFunctions() != null) {

            if (StringUtils.isNotBlank(searchCriteria.getFunctions().getType())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" orgFunction.type=? ");
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

            if (searchCriteria.getFunctions().getValidFrom() != null && searchCriteria.getFunctions().getValidFrom() != 0) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" orgFunction.valid_from >= ? ");
                preparedStmtList.add(searchCriteria.getFunctions().getValidFrom());
            }

            if (searchCriteria.getFunctions().getValidTo() != null && searchCriteria.getFunctions().getValidTo() != 0) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" orgFunction.valid_to <= ? ");
                preparedStmtList.add(searchCriteria.getFunctions().getValidTo());
            }

            if (StringUtils.isNotBlank(searchCriteria.getFunctions().getWfStatus())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" orgFunction.wf_status=? ");
                preparedStmtList.add(searchCriteria.getFunctions().getWfStatus());
            }

            if (!searchCriteria.getIncludeDeleted()) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" orgFunction.is_active=true ");
            }
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
        ids.forEach(id -> {
            preparedStmtList.add(id);
        });
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

    private void addOrderByClause(StringBuilder queryBuilder, Pagination pagination) {
        log.info("OrganisationQueryBuilder::getOrganisationQuery");
        //default
        if (pagination.getSortBy() == null) {
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

        if (pagination.getOrder() == "ASC")
            queryBuilder.append(" ASC ");
        else queryBuilder.append(" DESC ");
    }

    private String addPaginationWrapper(String query, List<Object> preparedStmtList, Pagination pagination) {
        log.info("OrganisationQueryBuilder::addPaginationWrapper");
        double limit = config.getDefaultLimit();
        double offset = config.getDefaultOffset();
        String finalQuery = paginationWrapper.replace("{}", query);

        if (pagination.getLimit() != null) {
            if (pagination.getLimit() <= config.getMaxLimit())
                limit = pagination.getLimit();
            else
                limit = config.getMaxLimit();
        }

        if (pagination.getOffSet() != null)
            offset = pagination.getOffSet();

        preparedStmtList.add(offset);
        preparedStmtList.add(limit + offset);

        return finalQuery;
    }
}
