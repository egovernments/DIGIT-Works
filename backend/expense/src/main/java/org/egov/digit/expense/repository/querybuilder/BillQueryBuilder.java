package org.egov.digit.expense.repository.querybuilder;

import org.apache.commons.lang3.StringUtils;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BillQueryBuilder {

    @Autowired
    private Configuration config;

    private static final String BILL_SELECT_QUERY = "SELECT bill.id as bill_id, "+
            "bill.tenantid as bill_tenantid, "+
            "bill.billdate as bill_billdate, " +
            "bill.duedate as bill_duedate, " +
            "bill.netpayableamount as bill_netpayableamount, "+
            "bill.netpaidamount as bill_netpaidamount, " +
            "bill.businessservice as bill_businessservice, "+
            "bill.referenceid as bill_referenceid, "+
            "bill.fromperiod as bill_fromperiod, "+
            "bill.toperiod as bill_toperiod, "+
            "bill.status as bill_status, "+
            "bill.paymentstatus as bill_paymentstatus, "+
            "bill.billNumber as bill_billnumber, "+
            "bill.createdby as bill_createdby, "+
            "bill.createdtime as bill_createdtime, "+
            "bill.lastmodifiedby as bill_lastmodifiedby, "+
            " bill.lastmodifiedtime as bill_lastmodifiedtime, "+
            "bill.additionaldetails as bill_additionaldetails, "+ //--bill

            " bd.id as bd_id, "+
            "bd.referenceid as bd_referenceid, "+
            "bd.tenantid as bd_tenantid, "+
            "bd.billid as bd_billid, "+
            "bd.paymentstatus as bd_paymentstatus, "+
            "bd.fromperiod as bd_fromperiod, "+
            "bd.toperiod as bd_toperiod, "+
            "bd.createdby as bd_createdby, "+
            "bd.createdtime as bd_createdtime, "+
            "bd.lastmodifiedby as bd_lastmodifiedby, "+
            "bd.lastmodifiedtime as bd_lastmodifiedtime, "+
            "bd.additionaldetails as bd_additionaldetails, "+ //-- bill details

            "li.id as li_id, "+
            "li.billdetailid as li_billdetailid, "+
            "li.tenantid as li_tenantid, "+
            "li.headcode as li_headcode, "+
            "li.amount as li_amount, "+
            "li.paidamount as li_paidamount, "+
            "li.type as li_type, "+
            "li.status as li_status, "+
            "li.islineitempayable as li_islineitempayable, "+
            "li.createdby as li_createdby, "+
            "li.createdtime as li_createdtime, "+
            "li.lastmodifiedby as li_lastmodifiedby, "+
            "li.lastmodifiedtime as li_lastmodifiedtime, "+
            "li.additionaldetails as li_additionaldetails, "+ //-- line_items

            "payer.id as payer_id, "+
            "payer.tenantid as payer_tenantid, "+
            "payer.type as payer_type, "+
            "payer.status as payer_status, "+
            "payer.identifier as payer_identifier, "+
            "payer.parentid as payer_parentid, "+
            "payer.createdby as payer_createdby, "+
            "payer.createdtime as payer_createdtime, "+
            "payer.lastmodifiedby as payer_lastmodifiedby, "+
            "payer.lastmodifiedtime as payer_lastmodifiedtime, "+
            "payer.additionaldetails as payer_additionaldetails, "+ //-- eg_expense_party_payer

            "payee.id as payee_id, "+
            "payee.tenantid as payee_tenantid, "+
            "payee.type as payee_type, "+
            "payee.status as payee_status, "+
            "payee.identifier as payee_identifier, "+
            "payee.parentid as payee_parentid, "+
            "payee.createdby as payee_createdby, "+
            "payee.createdtime as payee_createdtime, "+
            "payee.lastmodifiedby as payee_lastmodifiedby, "+
            "payee.lastmodifiedtime as payee_lastmodifiedtime, "+
            "payee.additionaldetails as payee_additionaldetails "+ //-- eg_expense_party_payee

            "FROM eg_expense_bill as bill "+

            "LEFT JOIN eg_expense_billdetail as bd ON bill.id = bd.billid "+

            "LEFT JOIN eg_expense_lineitem as li ON bd.id = li.billdetailid "+

            "LEFT JOIN eg_expense_party_payer as payer ON bill.id = payer.parentid "+

            "LEFT JOIN eg_expense_party_payee as payee ON bd.billid = payee.parentid ";


    public String getBillQuery(BillSearchRequest billSearchRequest, List<Object> preparedStmtList) {
        BillCriteria criteria=billSearchRequest.getBillCriteria();
        StringBuilder query = new StringBuilder(BILL_SELECT_QUERY);

        List<String> ids = new ArrayList<>(criteria.getIds());
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        List<String> referenceIds = new ArrayList<>(criteria.getReferenceIds());
        if (referenceIds != null && !referenceIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.referenceid IN (").append(createQuery(referenceIds)).append(")");
            addToPreparedStatement(preparedStmtList, referenceIds);
        }

        if (StringUtils.isNotBlank(criteria.getTenantId()) && criteria.getTenantId()!=null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.tenantid=? ");
            preparedStmtList.add(criteria.getTenantId());
        }

        if (StringUtils.isNotBlank(criteria.getBusinessService()) && criteria.getBusinessService()!=null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.businessservice=? ");
            preparedStmtList.add(criteria.getBusinessService());
        }

        if (StringUtils.isNotBlank(criteria.getStatus()) && criteria.getStatus()!=null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.status=? ");
            preparedStmtList.add(criteria.getTenantId());
        }

        addOrderByClause(query, criteria, preparedStmtList);

        addLimitAndOffset(query, criteria, preparedStmtList);

        return query.toString();
    }

    private void addOrderByClause(StringBuilder queryBuilder, BillCriteria criteria, List<Object> preparedStmtList) {

        Pagination pagination = criteria.getPagination();


        Set<String> sortableColumns=new HashSet<>(Arrays.asList("bill_id","bill_billdate","bill_duedate","bill_paymentstatus"
                ,"bill_status"));


        if (pagination.getSortBy() != null && !pagination.getSortBy().isEmpty() && sortableColumns.contains(pagination.getSortBy())) {
            queryBuilder.append(" ORDER BY ");
            queryBuilder.append(pagination.getSortBy()).append(" ");
        }
        else{
            queryBuilder.append(" ORDER BY bill_billdate ");
        }

        if (pagination.getOrder() != null && Pagination.OrderEnum.fromValue(pagination.getOrder().toString()) != null) {
            queryBuilder.append(pagination.getOrder().name());
        }
        else{
            criteria.getPagination().setOrder(Pagination.OrderEnum.ASC);
            queryBuilder.append(Pagination.OrderEnum.ASC);
        }
    }

    private void addLimitAndOffset(StringBuilder queryBuilder, BillCriteria criteria, List<Object> preparedStmtList) {

        int limit = criteria.getPagination().getLimit();
        int offset = criteria.getPagination().getOffSet();

        queryBuilder.append(" LIMIT ?");
        preparedStmtList.add(limit);

        queryBuilder.append(" OFFSET ? ");
        preparedStmtList.add(offset);

    }

    private String createQuery2(Collection<String> ids) {
    	//select referenceId from table where referenceId like 'id%'
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT referenceId FROM eg_expense_bill WHERE referenceid ");        
        int length = ids.size();
        String[] referenceIds = (String[]) ids.toArray();
        for (int i = 0; i < length; i++) {
        	builder.append("LIKE " + referenceIds[i] + "%");
        	if (i != length - 1) builder.append(" OR referenceid ");
        }
        return builder.toString();
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

    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList) {
        if (preparedStmtList.isEmpty()) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }
    }

    private void addToPreparedStatement(List<Object> preparedStmtList, Collection<String> ids) {
        preparedStmtList.addAll(ids);
    }
}
