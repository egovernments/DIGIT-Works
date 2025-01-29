package org.egov.digit.expense.repository.querybuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.Pagination;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class BillQueryBuilder {
	
	private final Configuration configs;


    @Autowired
    public BillQueryBuilder(Configuration configs) {
        this.configs = configs;
    }


<<<<<<< HEAD
    public String getBillQuery(BillSearchRequest billSearchRequest, List<Object> preparedStmtList, boolean isCountRequired) {
=======
    public String getBillQuery(BillSearchRequest billSearchRequest, List<Object> preparedStmtList, boolean isCountRequired,
                               boolean isValidationSearch) {
>>>>>>> 504a89d592593471db1fd567ee4faf870546941e
    	
        BillCriteria criteria=billSearchRequest.getBillCriteria();
        StringBuilder query = null;

        if(isCountRequired) {
            query = new StringBuilder(Constants.BILL_COUNT_QUERY);
        } else {
            query = new StringBuilder(Constants.BILL_QUERY);
        }

        Set<String> billNumbers = criteria.getBillNumbers();
        if(!CollectionUtils.isEmpty(billNumbers)) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.billNumber IN (").append(createQuery(billNumbers)).append(")");
            addToPreparedStatement(preparedStmtList, billNumbers);
        }

        Set<String> ids = criteria.getIds();
        if (!CollectionUtils.isEmpty(ids)) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }
        /* If search is coming from validation we are using equals query as bills can be generated for multiple levels
         * and using like query will throw an error if bill is already generated for a boundary level below the current level
         */
        if (configs.isHealthContextEnabled() && !isValidationSearch) {
            Set<String> referenceIds = criteria.getReferenceIds();
            if (!CollectionUtils.isEmpty(referenceIds)) {
                addClauseIfRequired(query, preparedStmtList);
                List<String> likeClauses = new ArrayList<>();
                for (String refId : referenceIds) {
                    likeClauses.add(" bill.referenceid LIKE ?");
                    preparedStmtList.add("%" + refId + "%");  // Adding % for partial match
                }
                // Wrap the entire condition in brackets
                query.append(" (").append(String.join(" OR ", likeClauses)).append(") ");
            }
        } else {
            Set<String> referenceIds = criteria.getReferenceIds();
            if (!CollectionUtils.isEmpty(referenceIds)) {
                addClauseIfRequired(query, preparedStmtList);
                query.append(" bill.referenceid IN (").append(createQuery(referenceIds)).append(" )");
                addToPreparedStatement(preparedStmtList, referenceIds);
            }
        }

        if (criteria.getFromDate() != null) {
            addClauseIfRequired(query, preparedStmtList);

            //If user does not specify toDate, take today's date as toDate by default.
            if (criteria.getToDate() == null) {
                criteria.setToDate(Instant.now().toEpochMilli());
            }

            query.append(" bill.billdate BETWEEN ? AND ? ");
            preparedStmtList.add(criteria.getFromDate());
            preparedStmtList.add(criteria.getToDate());

        } else {
            //if only toDate is provided as parameter without fromDate parameter, throw an exception.
            if (criteria.getToDate() != null) {
                throw new CustomException("INVALID_SEARCH_PARAM", "Cannot specify toDate without a fromDate");
            }
        }

        if (StringUtils.isNotBlank(criteria.getLocalityCode())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.localitycode = ? ");
            preparedStmtList.add(criteria.getLocalityCode());
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
        if (criteria.getIsPaymentStatusNull() != null && criteria.getIsPaymentStatusNull().equals(true)) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" bill.paymentstatus IS NULL");

        }
<<<<<<< HEAD
		return isCountRequired? query.toString() : addPaginationWrapper(query, billSearchRequest.getPagination(), preparedStmtList);
=======
		return isCountRequired? query.toString() : addPaginationWrapper(query, billSearchRequest.getPagination(),
                preparedStmtList);
>>>>>>> 504a89d592593471db1fd567ee4faf870546941e
    }

    private String addOrderByClause(Pagination pagination) {

        String paginationWrapper = "SELECT * FROM " +
                "(SELECT *, DENSE_RANK() OVER (ORDER BY b_id, {sortBy} {orderBy}) offset_ FROM " +
                "({})" +
                " result) result_offset " +
                "WHERE offset_ > ? AND offset_ <= ?";

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

    public String getSearchCountQueryString(BillSearchRequest billSearchRequest, List<Object> preparedStmtList) {
<<<<<<< HEAD
        String query = getBillQuery(billSearchRequest, preparedStmtList,true);
=======
        String query = getBillQuery(billSearchRequest, preparedStmtList,true, false);
>>>>>>> 504a89d592593471db1fd567ee4faf870546941e
        if (query != null)
            return Constants.COUNT_WRAPPER.replace("{INTERNAL_QUERY}", query);
        else
            return query;
    }
}
