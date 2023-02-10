package org.egov.works.repository.querybuilder;

import org.apache.commons.lang3.StringUtils;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.web.models.ContractCriteria;
import org.egov.works.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ContractQueryBuilder {

    private static final String CONTRACT_SELECT_QUERY = " SELECT contract.id AS id, " +
            "contract.contract_number AS contractNumber, " +
            "contract.tenant_id AS tenantId, " +
            "contract.wf_status AS wfStatus, " +
            "contract.executing_authority AS executingAuthority, " +
            "contract.contract_type AS contractType, " +
            "contract.total_contracted_amount AS totalContractedamount, " +
            "contract.security_deposit AS securityDeposit, " +
            "contract.agreement_date AS agreementDate, " +
            "contract.defect_liability_period AS defectLiabilityPeriod, " +
            "contract.org_id AS orgId, " +
            "contract.start_date AS startDate, " +
            "contract.end_date AS endDate, " +
            "contract.status AS status, " +
            "contract.additional_details AS additionalDetails, " +
            "contract.created_by AS createdBy, " +
            "contract.last_modified_by AS lastModifiedBy, " +
            "contract.created_time AS createdTime, " +
            "contract.last_modified_time AS lastModifiedTime, " +

            "document.id AS docId, " +
            "document.filestore_id AS docFileStoreId, " +
            "document.document_type AS docDocumentType, " +
            "document.document_uid AS docDocumentUid, " +
            "document.status AS docStatus, " +
            "document.contract_id AS docContractIid, " +
            "document.additional_details AS docAdditionalDetails, " +
            "document.created_by AS docCreatedBy, " +
            "document.last_modified_by AS docLastModifiedBy, " +
            "document.created_time AS docCreatedTime, " +
            "document.last_modified_time AS docLastModifiedTime " +
            "FROM eg_wms_contract AS contract " +
            "LEFT JOIN " +
            "eg_wms_contract_documents as document " +
            "ON (contract.id=document.contract_id) ";
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

        if (StringUtils.isNotBlank(criteria.getTenantId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" contract.tenant_id=? ");
            preparedStmtList.add(criteria.getTenantId());
        }

        addOrderByClause(query, criteria, preparedStmtList);

        addLimitAndOffset(query, criteria, preparedStmtList);

//        return addPaginationWrapper(query.toString(),preparedStmtList,criteria);
        return query.toString();
    }

    private void addOrderByClause(StringBuilder queryBuilder, ContractCriteria criteria, List<Object> preparedStmtList) {

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
            criteria.getPagination().setOrder(Pagination.OrderEnum.ASC);
            queryBuilder.append(Pagination.OrderEnum.ASC);
        }
    }

    private void addLimitAndOffset(StringBuilder queryBuilder, ContractCriteria criteria, List<Object> preparedStmtList) {

        int limit = criteria.getPagination().getLimit();
        int offset = criteria.getPagination().getOffSet();

        queryBuilder.append(" LIMIT ?");
        preparedStmtList.add(limit);

        queryBuilder.append(" OFFSET ? ");
        preparedStmtList.add(criteria.getPagination().getOffSet());

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
