package org.egov.digit.expense.repository.querybuilder;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class BillQueryBuilder {
	
	@Autowired
	private Configuration configs;

   
    private static String WRAPPER_QUERY = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY b_id, {sortBy} {orderBy}) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";


    public String getBillQuery(BillSearchRequest billSearchRequest, List<Object> preparedStmtList) {
    	
        BillCriteria criteria=billSearchRequest.getBillCriteria();
        StringBuilder query = new StringBuilder(Constants.BILL_QUERY);

        Set<String> billNumbers = criteria.getBillNumbers();
        if(!CollectionUtils.isEmpty(billNumbers)) {
            if (billNumbers != null && !billNumbers.isEmpty()) {
                addClauseIfRequired(query, preparedStmtList);
                query.append(" bill.billNumber IN (").append(createQuery(billNumbers)).append(")");
                addToPreparedStatement(preparedStmtList, billNumbers);
            }
        }

        Set<String> ids = criteria.getIds();
        if (!CollectionUtils.isEmpty(ids)) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        Set<String> referenceIds = criteria.getReferenceIds();
        if (!CollectionUtils.isEmpty(referenceIds)) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.referenceid IN (").append(createQuery(referenceIds)).append(")");
            addToPreparedStatement(preparedStmtList, referenceIds);
        }

        if (StringUtils.isNotBlank(criteria.getTenantId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.tenantid = ? ");
            preparedStmtList.add(criteria.getTenantId());
        }

        if (StringUtils.isNotBlank(criteria.getBusinessService())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.businessservice = ? ");
            preparedStmtList.add(criteria.getBusinessService());
        }

        if (StringUtils.isNotBlank(criteria.getStatus())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.status = ? ");
            preparedStmtList.add(criteria.getStatus());
        }
        
        if (StringUtils.isNotBlank(criteria.getStatusNot())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.status != ? ");
            preparedStmtList.add(criteria.getStatusNot());
        }
		return addPaginationWrapper(query, billSearchRequest.getPagination(), preparedStmtList);
    }

    private String addOrderByClause(Pagination pagination) {

    	String paginationWrapper = WRAPPER_QUERY;

        if ( !StringUtils.isEmpty(pagination.getSortBy()) && Constants.SORTABLE_BILL_COLUMNS.contains(pagination.getSortBy())) {
            paginationWrapper=paginationWrapper.replace("{sortBy}", pagination.getSortBy());
        }
        else{
            paginationWrapper=paginationWrapper.replace("{sortBy}", "billdate");
        }

        if (pagination.getOrder() != null && Pagination.OrderEnum.fromValue(pagination.getOrder().toString()) != null) {
            paginationWrapper=paginationWrapper.replace("{orderBy}", pagination.getOrder().name());

        }
        else{
            paginationWrapper=paginationWrapper.replace("{orderBy}", Pagination.OrderEnum.ASC.name());
        }
        
        return paginationWrapper;
    }

    private String addPaginationWrapper(StringBuilder query,Pagination pagination,List<Object> preparedStmtList){

		String paginatedQuery = addOrderByClause(pagination);

		int limit = null != pagination.getLimit() ? pagination.getLimit() : configs.getDefaultLimit();
		int offset = null != pagination.getOffSet() ? pagination.getOffSet() : configs.getDefaultOffset();

        String finalQuery = paginatedQuery.replace("{}", query);

        preparedStmtList.add(offset);
        preparedStmtList.add(limit + offset);

        return finalQuery;
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
