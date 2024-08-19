package org.egov.works.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.web.models.ContractCriteria;
import org.egov.works.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Component
@Slf4j
public class ContractQueryBuilder {

    private static final  String CONTRACT_SELECT_QUERY = " SELECT " +
            "contract.id AS contractId, " +
            "contract.contract_number AS contractNumber, " +
            "contract.supplement_number AS supplementNumber, " +
            "contract.version_number AS versionNumber, " +
            "contract.old_uuid AS oldUuid, " +
            "contract.business_service AS businessService, " +
            "contract.tenant_id AS contractTenantId, " +
            "contract.wf_status AS wfStatus, " +
            "contract.executing_authority AS executingAuthority, " +
            "contract.contract_type AS contractType, " +
            "contract.total_contracted_amount AS totalContractedAmount, " +
            "contract.security_deposit AS securityDeposit, " +
            "contract.agreement_date AS agreementDate, " +
            "contract.defect_liability_period AS defectLiabilityPeriod, " +
            "contract.org_id AS orgId, " +
            "contract.start_date AS startDate, " +
            "contract.end_date AS endDate, " +
            "contract.status AS contractStatus, " +
            "contract.additional_details AS contractAdditionalDetails, " +
            "contract.created_by AS contractCreatedBy, " +
            "contract.last_modified_by AS contractLastModifiedBy, " +
            "contract.created_time AS contractCreatedTime, " +
            "contract.last_modified_time AS contractLastModifiedTime, " +
            "contract.issue_date AS issueDate, " +
            "contract.completion_period AS completionPeriod, " +

            "document.id AS docId, " +
            "document.filestore_id AS docFileStoreId, " +
            "document.document_type AS docDocumentType, " +
            "document.document_uid AS docDocumentUid, " +
            "document.status AS docStatus, " +
            "document.contract_id AS docContractId, " +
            "document.additional_details AS docAdditionalDetails, " +
            "document.created_by AS docCreatedBy, " +
            "document.last_modified_by AS docLastModifiedBy, " +
            "document.created_time AS docCreatedTime, " +
            "document.last_modified_time AS docLastModifiedTime, " +

            "lineItems.id AS lineItemId, " +
            "lineItems.estimate_id AS estimateId, " +
            "lineItems.estimate_line_item_id AS estimateLineItemId, " +
            "lineItems.contract_line_item_ref AS contractLineItemRef, " +
            "lineItems.contract_id AS lineItemContractId, " +
            "lineItems.tenant_id AS lineItemTenantId, " +
            "lineItems.unit_rate AS unitRate, " +
            "lineItems.no_of_unit AS noOfUnit, " +
            "lineItems.status AS lineItemStatus, " +
            "lineItems.additional_details AS lineItemAdditionalDetails, " +
            "lineItems.created_by AS lineItemCreatedBy, " +
            "lineItems.last_modified_by AS lineItemLastModifiedBy, " +
            "lineItems.created_time AS lineItemCreatedTime, " +
            "lineItems.last_modified_time AS lineItemLastModifiedTime, " +


            "amountBreakups.id AS amtId, " +
            "amountBreakups.estimate_amount_breakup_id AS amtEstimateAmountBreakupId, " +
            "amountBreakups.line_item_id AS amtLineItemId, " +
            "amountBreakups.amount AS amtAmount, " +
            "amountBreakups.status AS amtStatus, " +
            "amountBreakups.additional_details AS amtAdditionalDetails, " +
            "amountBreakups.created_by AS amtCreatedBy, " +
            "amountBreakups.last_modified_by AS amtLastModifiedBy, " +
            "amountBreakups.created_time AS amtCreatedTime, " +
            "amountBreakups.last_modified_time AS amtLastModifiedTime " +

            "FROM eg_wms_contract AS contract " +
            "LEFT JOIN eg_wms_contract_documents AS document " +
            "ON contract.id = document.contract_id " +
            "LEFT JOIN eg_wms_contract_line_items AS lineItems " +
            "ON contract.id = lineItems.contract_id " +
            "LEFT JOIN " +
            "eg_wms_contract_amount_breakups as amountBreakups " +
            "ON lineItems.id=amountBreakups.line_item_id ";

    private static final String PAGINATION_WRAPPER = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY ORDERBYCOLUMN [] , contractId) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";

    @Autowired
    private ContractServiceConfiguration config;

    public String getContractSearchQuery(ContractCriteria criteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(CONTRACT_SELECT_QUERY);

        List<String> ids = criteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" contract.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }
        if (StringUtils.isNotBlank(criteria.getContractNumber())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" contract.contract_number=? ");
            preparedStmtList.add(criteria.getContractNumber());
        }

        if (StringUtils.isNotBlank(criteria.getSupplementNumber())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" contract.supplement_number=? ");
            preparedStmtList.add(criteria.getSupplementNumber());
        }

        if (StringUtils.isNotBlank(criteria.getBusinessService())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" contract.business_service=? ");
            preparedStmtList.add(criteria.getBusinessService());
        }

        if (StringUtils.isNotBlank(criteria.getStatus())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" contract.status=? ");
            preparedStmtList.add(criteria.getStatus());
        }

        List<String> wfStatus = criteria.getWfStatus();
        if (wfStatus != null && !wfStatus.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" contract.wf_status IN (").append(createQuery(wfStatus)).append(")");
            addToPreparedStatement(preparedStmtList, wfStatus);
        }

        List<String> orgIds = criteria.getOrgIds();
        if (orgIds != null && !orgIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" contract.org_id IN (").append(createQuery(orgIds)).append(")");
            addToPreparedStatement(preparedStmtList, orgIds);
        }

        List<String> estimateIds = criteria.getEstimateIds();
        if (estimateIds != null && !estimateIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" lineItems.estimate_id IN (").append(createQuery(estimateIds)).append(")");
            addToPreparedStatement(preparedStmtList, estimateIds);
        }


        if (StringUtils.isNotBlank(criteria.getContractType()) && criteria.getContractType()!=null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" contract.contract_type=? ");
            preparedStmtList.add(criteria.getContractType());
        }

        if (StringUtils.isNotBlank(criteria.getTenantId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" contract.tenant_id=? ");
            preparedStmtList.add(criteria.getTenantId());
        }

        if (criteria.getFromDate() != null) {
            addClauseIfRequired(query, preparedStmtList);

            //If user does not specify toDate, take today's date as toDate by default.
            if (criteria.getToDate() == null) {
                criteria.setToDate(BigDecimal.valueOf(Instant.now().toEpochMilli()));
            }

            query.append(" contract.start_date BETWEEN ? AND ?");
            preparedStmtList.add(criteria.getFromDate());
            preparedStmtList.add(criteria.getToDate());

        } else {
            //if only toDate is provided as parameter without fromDate parameter, throw an exception.
            if (criteria.getToDate() != null) {
                throw new CustomException("INVALID_SEARCH_PARAM", "Cannot specify toDate without a fromDate");
            }
        }

        return addPaginationWrapper(query.toString(), preparedStmtList, criteria);
    }

    private void addOrderByClause(StringBuilder queryBuilder, ContractCriteria criteria) {

        Pagination pagination = criteria.getPagination();


        Set<String> sortableColumns=new HashSet<>(Arrays.asList("id","contractNumber","totalContractedamount","securityDeposit"
                ,"agreementDate","defectLiabilityPeriod","startDate","endDate","createdTime","lastModifiedTime"));


        if (pagination.getSortBy() != null && !pagination.getSortBy().isEmpty() && sortableColumns.contains(pagination.getSortBy())) {
            queryBuilder.append(" ORDER BY ");
            queryBuilder.append(pagination.getSortBy()).append(" ");
        }
        else{
            queryBuilder.append(" ORDER BY startDate ");
        }

        if (pagination.getOrder() != null && Pagination.OrderEnum.fromValue(pagination.getOrder().toString()) != null) {
            queryBuilder.append(pagination.getOrder().name());
        }
        else{
            criteria.getPagination().setOrder(Pagination.OrderEnum.DESC);
            queryBuilder.append(Pagination.OrderEnum.DESC);
        }
    }

    private String addPaginationWrapper(String query, List<Object> preparedStmtList,
                                       ContractCriteria criteria) {
        log.info("ContractQueryBuilder::addPaginationWrapper");
        int limit = config.getContractDefaultLimit();
        int offset = config.getContractDefaultOffset();
        String wrapperQuery = PAGINATION_WRAPPER;
        Pagination pagination = criteria.getPagination();
        Set<String> sortableColumns = new HashSet<>(Arrays.asList("id","contractNumber","totalContractedamount","securityDeposit"
                ,"agreementDate","defectLiabilityPeriod","startDate","endDate","createdTime","lastModifiedTime"));

        if (pagination.getSortBy() != null && !pagination.getSortBy().isEmpty() && sortableColumns.contains(pagination.getSortBy())) {
            if (pagination.getSortBy().equals("id")) {
                wrapperQuery = wrapperQuery.replace("ORDERBYCOLUMN", "contractId");

            } else if (pagination.getSortBy().equals("createdTime")) {
                wrapperQuery = wrapperQuery.replace("ORDERBYCOLUMN", "contractCreatedTime");
            } else if (pagination.getSortBy().equals("lastModifiedTime")) {
                wrapperQuery = wrapperQuery.replace("ORDERBYCOLUMN", "contractLastModifiedTime");
            } else {
                wrapperQuery = wrapperQuery.replace( "ORDERBYCOLUMN", pagination.getSortBy());
            }
        } else {
            wrapperQuery = wrapperQuery.replace("ORDERBYCOLUMN", "startDate");
        }


        if (criteria.getPagination().getOrder() == Pagination.OrderEnum.ASC)
            wrapperQuery = wrapperQuery.replace("[]", "ASC");
        else
            wrapperQuery = wrapperQuery.replace("[]", "DESC");

        String finalQuery = wrapperQuery.replace("{}", query);

        if (criteria.getPagination().getLimit() != null) {
            if (pagination.getLimit() <= config.getContractMaxLimit())
                limit = pagination.getLimit();
            else
                limit = config.getContractMaxLimit();
        }

        if (pagination.getOffSet() != null)
            offset = pagination.getOffSet();

        preparedStmtList.add(offset);
        preparedStmtList.add(limit + offset);
        return finalQuery;
    }

    private void addLimitAndOffset(StringBuilder queryBuilder, ContractCriteria criteria, List<Object> preparedStmtList) {

        int limit = criteria.getPagination().getLimit();
        int offset = criteria.getPagination().getOffSet();

        queryBuilder.append(" LIMIT ?");
        preparedStmtList.add(limit);

        queryBuilder.append(" OFFSET ? ");
        preparedStmtList.add(offset);

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
