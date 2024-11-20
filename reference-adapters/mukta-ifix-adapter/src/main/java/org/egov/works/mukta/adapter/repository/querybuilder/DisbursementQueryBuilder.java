package org.egov.works.mukta.adapter.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.web.models.DisbursementSearchCriteria;
import org.egov.works.mukta.adapter.web.models.DisbursementSearchRequest;
import org.egov.works.mukta.adapter.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class DisbursementQueryBuilder {

    private final MuktaAdaptorConfig config;
    public static final String DISBURSEMENT_SEARCH_QUERY = "SELECT disburse.id as disburseId,disburse.program_code as disburseProgramCode, disburse.target_id as disburseTargetId, disburse.transaction_id as disburseTransactionId,disburse.parent_id as disburseParentId, disburse.account_code as disburseAccountCode, disburse.status as disburseStatus, disburse.status_message as disburseStatusMessage,disburse.individual as disburseIndividual,disburse.net_amount as disburseNetAmount, disburse.gross_amount as disburseGrossAmount, disburse.created_time as disbursecreatedtime, disburse.created_by as disburseCreatedBy, disburse.last_modified_time as disburseLastModifiedTime, disburse.last_modified_by as disburseLastModifiedBy,\n" +
            "code.id as codeId,code.location_code as codeLocationCode, code.type as codeType, code.parent_id as codeParentId, code.function_code as codeFunctionCode, code.administration_code as codeAdministrationCode, code.program_code as codeProgramCode, code.recipient_segment_code as codeRecipientSegmentCode, code.economic_segment_code as codeEconomicSegmentCode, code.source_of_fund_code as codeSourceOfFundCode, code.target_segment_code as codeTargetSegmentCode,\n" +
            "code.additional_details as codeAdditionalDetails, code.created_time as codeCreatedTime, code.created_by as codeCreatedBy, code.last_modified_time as codeLastModifiedTime, code.last_modified_by as codeLastModifiedBy\n" +
            "FROM eg_mukta_ifms_disburse disburse LEFT JOIN eg_mukta_ifms_message_codes code ON disburse.id = code.parent_id";
    private static final String AND_CLAUSE = " AND ";
    private static final String WRAPPER_QUERY = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY disburse{sortBy} {orderBy}) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";

    @Autowired
    public DisbursementQueryBuilder(MuktaAdaptorConfig config) {
        this.config = config;
    }

    public String getDisbursementSearchQuery(DisbursementSearchRequest disbursementSearchRequest, List<Object> preparedStmtList, List<String> parentIds,boolean isPaginationRequired){
        DisbursementSearchCriteria disbursementSearchCriteria = disbursementSearchRequest.getCriteria();
        StringBuilder query = new StringBuilder(DISBURSEMENT_SEARCH_QUERY);
        if (parentIds == null || parentIds.isEmpty()){
            addClauseIfRequired(query, preparedStmtList);
            query.append(" disburse.parent_id IS NULL ");
        }else {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" disburse.parent_id IN (").append(createQuery(parentIds)).append(")");
            addToPreparedStatement(preparedStmtList, parentIds);
        }
        List<String> ids = disbursementSearchCriteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            query.append(AND_CLAUSE);
            query.append(" disburse.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        if(disbursementSearchCriteria.getPaymentNumber() != null){
            query.append(AND_CLAUSE);
            query.append(" disburse.target_id = ? ");
            preparedStmtList.add(disbursementSearchCriteria.getPaymentNumber());
        }
        if(disbursementSearchCriteria.getTransactionId() != null){
            query.append(AND_CLAUSE);
            query.append(" disburse.transaction_id = ? ");
            preparedStmtList.add(disbursementSearchCriteria.getTransactionId());
        }
        if(disbursementSearchCriteria.getStatus() != null){
            query.append(AND_CLAUSE);
            query.append(" disburse.status = ? ");
            preparedStmtList.add(disbursementSearchCriteria.getStatus());
        }
        if(disbursementSearchCriteria.getType() != null){
            query.append(AND_CLAUSE);
            query.append(" code.type = ? ");
            preparedStmtList.add(disbursementSearchCriteria.getType());
        }
        if(disbursementSearchCriteria.getTenantId() != null){
            query.append(AND_CLAUSE);
            query.append(" code.location_code = ? ");
            preparedStmtList.add(disbursementSearchCriteria.getTenantId());
        }
        if(isPaginationRequired){
            return addPaginationWrapper(query,disbursementSearchRequest.getPagination(),preparedStmtList);
        }
        return query.toString();
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
        preparedStmtList.addAll(ids);
    }
    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList) {
        if (preparedStmtList.isEmpty()) {
            query.append(" WHERE ");
        } else {
            query.append(AND_CLAUSE);
        }
    }

    public String addPaginationWrapper(StringBuilder query, Pagination pagination, List<Object> preparedStmtList) {
        String paginatedQuery = addOrderByClause(pagination);

        int limit = pagination != null && pagination.getLimit() != null ? pagination.getLimit() : config.getDefaultLimit();
        int offset = pagination != null && pagination.getOffSet() != null? pagination.getOffSet() : config.getDefaultOffset();

        String finalQuery = paginatedQuery.replace("{}", query);

        preparedStmtList.add(offset);
        preparedStmtList.add(limit + offset);

        return finalQuery;
    }


    private String addOrderByClause(Pagination pagination) {

        String paginationWrapper = WRAPPER_QUERY;
        if(pagination == null)
            return paginationWrapper.replace("{sortBy}", "createdtime").replace("{orderBy}", Pagination.OrderEnum.DESC.name());

        if ( pagination.getSortBy() != null && StringUtils.isNotBlank(pagination.getSortBy())) {
            paginationWrapper=paginationWrapper.replace("{sortBy}", pagination.getSortBy());
        }
        else{
            paginationWrapper=paginationWrapper.replace("{sortBy}", "createdtime");
        }

        if (pagination.getOrder() != null) {
            paginationWrapper=paginationWrapper.replace("{orderBy}", pagination.getOrder().name());
        }
        else{
            paginationWrapper=paginationWrapper.replace("{orderBy}", Pagination.OrderEnum.DESC.name());
        }

        return paginationWrapper;
    }
}
