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


    private static final String FETCH_CALCULATE_QUERY = "SELECT musterroll_num FROM eg_works_calculation ";

    private static final String FETCH_CALCULATE_BILL_IDS_QUERY = "SELECT bill_id FROM eg_works_calculation ";



    public String getMusterRollsOfContract(String contractId, String billType, List<String> billIds, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(FETCH_CALCULATE_QUERY);

        if (StringUtils.isNotBlank(contractId)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append("contract_id=? ");
            preparedStmtList.add(contractId);
        }

        if (StringUtils.isNotBlank(billType)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append("bill_type=? ");
            preparedStmtList.add(billType);
        }

        if (billIds != null && !billIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append("bill_id IN (").append(createQuery(billIds)).append(")");
            addToPreparedStatement(preparedStmtList, billIds);
        }

        return queryBuilder.toString();
    }

    public String getBillIds(CalculatorSearchRequest calculatorSearchRequest, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(FETCH_CALCULATE_BILL_IDS_QUERY);

        List<String> projectNumbers=new ArrayList<>();
        List<String> contractNumbers=new ArrayList<>();
        List<String> orgNumbers=new ArrayList<>();
        List<String> musterRollNumbers=new ArrayList<>();
        List<String> billNumbers=new ArrayList<>();
        List<String> billTypes=new ArrayList<>();
        List<String> tenantIds=new ArrayList<>();
        List<String> billRefIds=new ArrayList<>();



        if(!CollectionUtils.isEmpty(calculatorSearchRequest.getSearchCriterias())){
            for(CalculatorSearchCriteria calculatorSearchCriteria: calculatorSearchRequest.getSearchCriterias()){
                if(StringUtils.isNotBlank(calculatorSearchCriteria.getProjectNumber())){
                    projectNumbers.add(calculatorSearchCriteria.getProjectNumber());
                }

                if(StringUtils.isNotBlank(calculatorSearchCriteria.getContractNumber())){
                    contractNumbers.add(calculatorSearchCriteria.getContractNumber());
                }

                if(StringUtils.isNotBlank(calculatorSearchCriteria.getOrgNumber())){
                    orgNumbers.add(calculatorSearchCriteria.getOrgNumber());
                }

                if(StringUtils.isNotBlank(calculatorSearchCriteria.getMusterRollNumber())){
                    musterRollNumbers.add(calculatorSearchCriteria.getMusterRollNumber());
                }

                if(StringUtils.isNotBlank(calculatorSearchCriteria.getBillNumber())){
                    billNumbers.add(calculatorSearchCriteria.getBillNumber());
                }

                if(StringUtils.isNotBlank(calculatorSearchCriteria.getBillType())){
                    billTypes.add(calculatorSearchCriteria.getBillType());
                }

                if(StringUtils.isNotBlank(calculatorSearchCriteria.getTenantId())){
                    tenantIds.add(calculatorSearchCriteria.getTenantId());
                }

                if(StringUtils.isNotBlank(calculatorSearchCriteria.getBillReferenceId())){
                    billRefIds.add(calculatorSearchCriteria.getBillReferenceId());
                }
            }
        }

        if (!tenantIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" tenant_id IN (").append(createQuery(tenantIds)).append(")");
            addToPreparedStatement(preparedStmtList,tenantIds);
        }

        if (!projectNumbers.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" project_number IN (").append(createQuery(projectNumbers)).append(")");
            addToPreparedStatement(preparedStmtList,projectNumbers);
        }

        if (!contractNumbers.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" contract_number IN (").append(createQuery(contractNumbers)).append(")");
            addToPreparedStatement(preparedStmtList,contractNumbers);
        }

        if (!orgNumbers.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" org_id IN (").append(createQuery(orgNumbers)).append(")");
            addToPreparedStatement(preparedStmtList,orgNumbers);
        }

        if (!musterRollNumbers.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" musterroll_number IN (").append(createQuery(musterRollNumbers)).append(")");
            addToPreparedStatement(preparedStmtList,musterRollNumbers);
        }

        if (!billNumbers.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bill_number IN (").append(createQuery(billNumbers)).append(")");
            addToPreparedStatement(preparedStmtList,billNumbers);
        }

        if (!billTypes.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" business_service IN (").append(createQuery(billTypes)).append(")");
            addToPreparedStatement(preparedStmtList,billTypes);
        }

        if (!billRefIds.isEmpty()) {
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
