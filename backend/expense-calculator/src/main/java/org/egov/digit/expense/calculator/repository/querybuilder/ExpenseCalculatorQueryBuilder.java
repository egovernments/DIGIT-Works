package org.egov.digit.expense.calculator.repository.querybuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.egov.digit.expense.calculator.web.models.CalculatorSearchCriteria;
import org.egov.digit.expense.calculator.web.models.CalculatorSearchRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExpenseCalculatorQueryBuilder {


    private static final String FETCH_MUSTER_NUM_QUERY = "SELECT musterroll_number FROM eg_works_calculation ";

    private static final String FETCH_BILL_ID_QUERY = "SELECT bill_id FROM eg_works_calculation ";

    private static final String FETCH_CALCULATE_BILL_IDS_QUERY = "SELECT bill_id,contract_number,musterroll_number," +
            "project_number,org_id FROM eg_works_calculation ";




    public String getMusterRollsOfContract(String contractId, String billType, String tenantId, List<String> billIds, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(FETCH_MUSTER_NUM_QUERY);

        if (StringUtils.isNotBlank(contractId)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" contract_number=? ");
            preparedStmtList.add(contractId);
        }

        if (StringUtils.isNotBlank(tenantId)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" tenant_id=? ");
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
            queryBuilder.append(" tenant_id=? ");
            preparedStmtList.add(tenantId);
        }
        return queryBuilder.toString();
    }

    public String getBillIds(CalculatorSearchRequest calculatorSearchRequest, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(FETCH_CALCULATE_BILL_IDS_QUERY);

        CalculatorSearchCriteria calculatorSearchCriteria=calculatorSearchRequest.getSearchCriteria();

        if (StringUtils.isNotBlank(calculatorSearchCriteria.getTenantId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" tenant_id=? ");
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
