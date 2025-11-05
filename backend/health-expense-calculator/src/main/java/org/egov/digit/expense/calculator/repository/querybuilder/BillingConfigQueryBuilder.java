package org.egov.digit.expense.calculator.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.digit.expense.calculator.web.models.BillingConfigSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * BillingConfigQueryBuilder
 *
 * Builds SQL queries for billing configuration and period searches.
 * Follows DIGIT Works query builder pattern with prepared statements.
 *
 * @author DIGIT-Works
 */
@Component
@Slf4j
public class BillingConfigQueryBuilder {

    private static final String BILLING_CONFIG_BASE_QUERY =
        "SELECT bc.id, bc.tenant_id, bc.campaign_number, bc.billing_frequency, " +
        "bc.custom_frequency_days, bc.project_start_date, bc.project_end_date, " +
        "bc.status, bc.created_by, bc.created_time, bc.last_modified_by, " +
        "bc.last_modified_time, bc.additional_details " +
        "FROM eg_expense_billing_config bc ";

    private static final String BILLING_PERIOD_BASE_QUERY =
        "SELECT bp.id, bp.tenant_id, bp.campaign_number, bp.billing_config_id, " +
        "bp.period_number, bp.period_start_date, bp.period_end_date, " +
        "bp.billing_frequency, bp.period_type, bp.status, bp.bill_id, " +
        "bp.total_amount, bp.beneficiary_count, bp.register_count, " +
        "bp.muster_roll_count, bp.created_by, bp.created_time, " +
        "bp.last_modified_by, bp.last_modified_time, bp.additional_details " +
        "FROM eg_wms_billing_period bp ";

    /**
     * Builds query for searching billing configurations.
     *
     * @param criteria Search criteria
     * @param preparedStmtList List to store prepared statement parameters
     * @return SQL query string
     */
    public String buildBillingConfigSearchQuery(BillingConfigSearchCriteria criteria,
                                               List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(BILLING_CONFIG_BASE_QUERY);

        // Tenant ID is mandatory
        queryBuilder.append(" WHERE bc.tenant_id = ? ");
        preparedStmtList.add(criteria.getTenantId());

        // Filter by IDs
        if (criteria.getIds() != null && !criteria.getIds().isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bc.id IN (")
                       .append(createQuery(criteria.getIds()))
                       .append(") ");
            addToPreparedStatement(preparedStmtList, criteria.getIds());
        }

        // Filter by campaign number
        if (StringUtils.isNotBlank(criteria.getCampaignNumber())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bc.campaign_number = ? ");
            preparedStmtList.add(criteria.getCampaignNumber());
        }

        // Filter by campaign numbers
        if (criteria.getCampaignNumbers() != null && !criteria.getCampaignNumbers().isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bc.campaign_number IN (")
                       .append(createQuery(criteria.getCampaignNumbers()))
                       .append(") ");
            addToPreparedStatement(preparedStmtList, criteria.getCampaignNumbers());
        }

        // Filter by billing frequency
        if (criteria.getBillingFrequency() != null) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bc.billing_frequency = ? ");
            preparedStmtList.add(criteria.getBillingFrequency().getValue());
        }

        // Filter by status
        if (StringUtils.isNotBlank(criteria.getStatus())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bc.status = ? ");
            preparedStmtList.add(criteria.getStatus());
        }

        // Filter by created date range
        if (criteria.getCreatedFrom() != null) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bc.created_time >= ? ");
            preparedStmtList.add(criteria.getCreatedFrom());
        }

        if (criteria.getCreatedTo() != null) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bc.created_time <= ? ");
            preparedStmtList.add(criteria.getCreatedTo());
        }

        // Add ordering
        queryBuilder.append(" ORDER BY bc.created_time DESC ");

        // Add pagination
        return addPaginationWrapper(queryBuilder.toString(), criteria, preparedStmtList);
    }

    /**
     * Builds query for searching billing periods.
     *
     * @param campaignNumber Campaign identifier
     * @param tenantId Tenant identifier
     * @param preparedStmtList List to store prepared statement parameters
     * @return SQL query string
     */
    public String buildBillingPeriodSearchQuery(String campaignNumber, String tenantId,
                                               List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(BILLING_PERIOD_BASE_QUERY);

        queryBuilder.append(" WHERE bp.tenant_id = ? ");
        preparedStmtList.add(tenantId);

        queryBuilder.append(" AND bp.campaign_number = ? ");
        preparedStmtList.add(campaignNumber);

        // Order by period number
        queryBuilder.append(" ORDER BY bp.period_number ASC ");

        log.debug("Billing period search query: {}", queryBuilder);
        return queryBuilder.toString();
    }

    /**
     * Builds query for searching billing periods by billing config ID.
     *
     * @param billingConfigId Billing configuration identifier
     * @param preparedStmtList List to store prepared statement parameters
     * @return SQL query string
     */
    public String buildBillingPeriodByConfigIdQuery(String billingConfigId,
                                                   List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(BILLING_PERIOD_BASE_QUERY);

        queryBuilder.append(" WHERE bp.billing_config_id = ? ");
        preparedStmtList.add(billingConfigId);

        // Order by period number
        queryBuilder.append(" ORDER BY bp.period_number ASC ");

        log.debug("Billing period by config ID query: {}", queryBuilder);
        return queryBuilder.toString();
    }

    /**
     * Builds query to find billing config by campaign number.
     *
     * @param campaignNumber Campaign identifier
     * @param tenantId Tenant identifier
     * @param preparedStmtList List to store prepared statement parameters
     * @return SQL query string
     */
    public String buildFindByCampaignNumberQuery(String campaignNumber, String tenantId,
                                           List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(BILLING_CONFIG_BASE_QUERY);

        queryBuilder.append(" WHERE bc.tenant_id = ? ");
        preparedStmtList.add(tenantId);

        queryBuilder.append(" AND bc.campaign_number = ? ");
        preparedStmtList.add(campaignNumber);

        queryBuilder.append(" LIMIT 1 ");

        log.debug("Find by campaign number query: {}", queryBuilder);
        return queryBuilder.toString();
    }

    /**
     * Adds AND clause if required based on existing prepared statement values.
     *
     * @param values List of prepared statement values
     * @param queryString Query string builder
     */
    private void addClauseIfRequired(List<Object> values, StringBuilder queryString) {
        if (!values.isEmpty()) {
            queryString.append(" AND ");
        }
    }

    /**
     * Creates placeholders for IN clause.
     *
     * @param ids List of identifiers
     * @return Comma-separated placeholders
     */
    private String createQuery(List<String> ids) {
        StringBuilder builder = new StringBuilder();
        int length = ids.size();
        for (int i = 0; i < length; i++) {
            builder.append("?");
            if (i != length - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    /**
     * Adds list values to prepared statement.
     *
     * @param preparedStmtList List to store prepared statement parameters
     * @param ids List of identifiers
     */
    private void addToPreparedStatement(List<Object> preparedStmtList, List<String> ids) {
        preparedStmtList.addAll(ids);
    }

    /**
     * Adds pagination wrapper to query.
     *
     * @param query Base query
     * @param criteria Search criteria
     * @param preparedStmtList List to store prepared statement parameters
     * @return Query with pagination
     */
    private String addPaginationWrapper(String query, BillingConfigSearchCriteria criteria,
                                       List<Object> preparedStmtList) {
        StringBuilder paginatedQuery = new StringBuilder(query);

        // Add limit
        Integer limit = criteria.getLimitOrDefault();
        paginatedQuery.append(" LIMIT ? ");
        preparedStmtList.add(limit);

        // Add offset
        Integer offset = criteria.getOffsetOrDefault();
        paginatedQuery.append(" OFFSET ? ");
        preparedStmtList.add(offset);

        log.debug("Paginated query: {}", paginatedQuery);
        return paginatedQuery.toString();
    }
}
