package org.egov.digit.expense.calculator.repository.querybuilder;

import org.apache.commons.lang3.StringUtils;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.web.models.CalculatorSearchCriteria;
import org.egov.digit.expense.calculator.web.models.CalculatorSearchRequest;
import org.egov.digit.expense.calculator.web.models.Order;
import org.egov.digit.expense.calculator.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static org.egov.common.utils.MultiStateInstanceUtil.SCHEMA_REPLACE_STRING;

@Component
public class ExpenseCalculatorQueryBuilder {

    private final ExpenseCalculatorConfiguration config;


    private static final String FETCH_MUSTER_NUM_QUERY = "SELECT musterroll_number FROM " + SCHEMA_REPLACE_STRING + ".eg_works_calculation ";

    private static final String FETCH_BILL_ID_QUERY = "SELECT bill_id FROM " + SCHEMA_REPLACE_STRING + ".eg_works_calculation ";

    private static final String FETCH_PROJECT_ID_QUERY = "SELECT distinct project_number FROM " + SCHEMA_REPLACE_STRING + ".eg_works_calculation ";

    private static final String FETCH_CALCULATE_BILL_IDS_QUERY = "SELECT bill_id,contract_number,musterroll_number," +
            "project_number,org_id FROM " + SCHEMA_REPLACE_STRING + ".eg_works_calculation ";

    private String paginationWrapper = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY {sortBy} {orderBy} , bill_id) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";

    private static final String BILL_COUNT_QUERY = "SELECT COUNT(*) FROM " + SCHEMA_REPLACE_STRING + ".eg_works_calculation ";

    private static final String TENANT_ID_CLAUSE = " tenant_id=? ";

    @Autowired
    public ExpenseCalculatorQueryBuilder(ExpenseCalculatorConfiguration config) {
        this.config = config;
    }


    public String getMusterRollsOfContract(String contractId, String billType, String tenantId, List<String> billIds, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(FETCH_MUSTER_NUM_QUERY);

        if (StringUtils.isNotBlank(contractId)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" contract_number=? ");
            preparedStmtList.add(contractId);
        }

        if (StringUtils.isNotBlank(tenantId)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(TENANT_ID_CLAUSE);
            preparedStmtList.add(tenantId);
        }

        if (StringUtils.isNotBlank(billType)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" business_service=? ");
            preparedStmtList.add(billType);
        }

        if (billIds != null && !billIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bill_id IN (").append(createQuery(billIds)).append(")");
            addToPreparedStatement(preparedStmtList, billIds);
        }

        return queryBuilder.toString();
    }


    public String getBillsOfContract(String contractId, String tenantId, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(FETCH_BILL_ID_QUERY);

        if (StringUtils.isNotBlank(contractId)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" contract_number=? ");
            preparedStmtList.add(contractId);
        }

        if (StringUtils.isNotBlank(tenantId)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(TENANT_ID_CLAUSE);
            preparedStmtList.add(tenantId);
        }
        return queryBuilder.toString();
    }
    
    public String getUniqueProjectNumbersByTenant(String tenantId, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(FETCH_PROJECT_ID_QUERY);

        if (StringUtils.isNotBlank(tenantId)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(TENANT_ID_CLAUSE);
            preparedStmtList.add(tenantId);
        }
        
        return queryBuilder.toString();
    }
    
    /**
     * Fetches bill_ids based on project number search
     * @param tenantId
     * @param projectNumbers
     * @param preparedStmtList
     * @return
     */
    public String getBillsByProjectNumbers(String tenantId, List<String> projectNumbers, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(FETCH_BILL_ID_QUERY);

        if (StringUtils.isNotBlank(tenantId)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(TENANT_ID_CLAUSE);
            preparedStmtList.add(tenantId);
        }
        if (!CollectionUtils.isEmpty(projectNumbers)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" project_number IN (").append(createQuery(projectNumbers)).append(")");
            addToPreparedStatement(preparedStmtList,projectNumbers);
        }
        
        return queryBuilder.toString();
    }

    public String getBillIds(CalculatorSearchRequest calculatorSearchRequest, List<Object> preparedStmtList,boolean isCountQuery) {
        //This uses a ternary operator to choose between COUNT_QUERY or FETCH_QUERY based on the value of isCountQuery.
        String query = isCountQuery ? BILL_COUNT_QUERY : FETCH_CALCULATE_BILL_IDS_QUERY;
        StringBuilder queryBuilder = new StringBuilder(query);

        CalculatorSearchCriteria calculatorSearchCriteria=calculatorSearchRequest.getSearchCriteria();

        if (StringUtils.isNotBlank(calculatorSearchCriteria.getTenantId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(TENANT_ID_CLAUSE);
            preparedStmtList.add(calculatorSearchCriteria.getTenantId());
        }

        if (!CollectionUtils.isEmpty(calculatorSearchCriteria.getProjectNumbers())) {
            List<String> projectNumbers=calculatorSearchCriteria.getProjectNumbers();
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" project_number IN (").append(createQuery(projectNumbers)).append(")");
            addToPreparedStatement(preparedStmtList,projectNumbers);
        }

        if (!CollectionUtils.isEmpty(calculatorSearchCriteria.getContractNumbers())) {
            List<String> contractNumbers=calculatorSearchCriteria.getContractNumbers();
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" contract_number IN (").append(createQuery(contractNumbers)).append(")");
            addToPreparedStatement(preparedStmtList,contractNumbers);
        }

        if (!CollectionUtils.isEmpty(calculatorSearchCriteria.getOrgNumbers())) {
            List<String> orgNumbers=calculatorSearchCriteria.getOrgNumbers();
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org_id IN (").append(createQuery(orgNumbers)).append(")");
            addToPreparedStatement(preparedStmtList,orgNumbers);
        }

        if (!CollectionUtils.isEmpty(calculatorSearchCriteria.getMusterRollNumbers())) {
            List<String> musterRollNumbers=calculatorSearchCriteria.getMusterRollNumbers();
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" musterroll_number IN (").append(createQuery(musterRollNumbers)).append(")");
            addToPreparedStatement(preparedStmtList,musterRollNumbers);
        }

        if (!CollectionUtils.isEmpty(calculatorSearchCriteria.getBillNumbers())) {
            List<String> billNumbers=calculatorSearchCriteria.getBillNumbers();
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bill_number IN (").append(createQuery(billNumbers)).append(")");
            addToPreparedStatement(preparedStmtList,billNumbers);
        }

        if (!CollectionUtils.isEmpty(calculatorSearchCriteria.getBillTypes())) {
            List<String> billTypes=calculatorSearchCriteria.getBillTypes();
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" business_service IN (").append(createQuery(billTypes)).append(")");
            addToPreparedStatement(preparedStmtList,billTypes);
        }

        if (!CollectionUtils.isEmpty(calculatorSearchCriteria.getBillReferenceIds())) {
            List<String> billRefIds=calculatorSearchCriteria.getBillReferenceIds();
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bill_reference IN (").append(createQuery(billRefIds)).append(")");
            addToPreparedStatement(preparedStmtList,billRefIds);
        }

        if (isCountQuery) {
            return queryBuilder.toString();
        }

        addOrderByClause(calculatorSearchRequest);

        return addPaginationWrapper(queryBuilder,calculatorSearchRequest,preparedStmtList);
    }

    private void addOrderByClause(CalculatorSearchRequest calculatorSearchRequest) {

        Pagination pagination = calculatorSearchRequest.getPagination();


        Set<String> sortableColumns=new HashSet<>(Arrays.asList("bill_id","contract_number","project_number","musterroll_number"));


        if (pagination.getSortBy() != null && !pagination.getSortBy().isEmpty() && sortableColumns.contains(pagination.getSortBy())) {
            paginationWrapper=paginationWrapper.replace("{sortBy}", pagination.getSortBy());
        }
        else{
            paginationWrapper=paginationWrapper.replace("{sortBy}", "bill_id");
            calculatorSearchRequest.getPagination().setSortBy("billId");
        }

        if (pagination.getOrder() != null && Order.fromValue(pagination.getOrder().toString()) != null) {
            paginationWrapper=paginationWrapper.replace("{orderBy}", pagination.getOrder().name());

        }
        else{
            paginationWrapper=paginationWrapper.replace("{orderBy}", Order.ASC.name());
            calculatorSearchRequest.getPagination().setOrder(Order.ASC);

        }
    }

    private String addPaginationWrapper(StringBuilder query,CalculatorSearchRequest calculatorSearchRequest,List<Object> preparedStmtList){

        int limit = calculatorSearchRequest.getPagination().getLimit();
        int offset = calculatorSearchRequest.getPagination().getOffSet();

        if(limit>config.getMaxLimit()){
            limit=config.getMaxLimit();
            calculatorSearchRequest.getPagination().setLimit(config.getMaxLimit());
        }

        String finalQuery = paginationWrapper.replace("{}", query);

        preparedStmtList.add(offset);
        preparedStmtList.add(limit + offset);

        return finalQuery;
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

    public String getSearchCountQueryString(CalculatorSearchRequest calculatorSearchRequest, List<Object> preparedStmtList,boolean isCountQuery) {
        return getBillIds(calculatorSearchRequest,preparedStmtList,isCountQuery);
    }

}
