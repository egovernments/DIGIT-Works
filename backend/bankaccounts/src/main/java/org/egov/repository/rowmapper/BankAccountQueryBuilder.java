package org.egov.repository.rowmapper;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.config.Configuration;
import org.egov.web.models.*;
import org.egov.works.services.common.models.bankaccounts.Order;
import org.egov.works.services.common.models.bankaccounts.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class BankAccountQueryBuilder {

    private final Configuration config;


    private static final String FETCH_BANK_ACCOUNT_QUERY = "SELECT bankAcct.*," +
            "bankAcctDoc.*,bankAcctIdntfr.*,bankAcctDetail.*, bankAcctDetail.id as bankAcctDetailId,bankAcct.id as bankAcctId, bankAcct.last_modified_time as bankAcctLastModifiedTime, bankAcctDoc.id AS bankAcctDocId, " +
            "bankAcctDoc.additional_details AS bankAcctDocAdditional,bankAcctDetail.additional_details AS bankAcctDetailAdditional," +
            "bankAcctIdntfr.additional_details AS bankAcctIdntfrAdditional," +
            "bankAcctDetail.created_by AS bankAcctDetailCreatedBy,bankAcctDetail.last_modified_by AS bankAcctDetailLastModifiedBy," +
            "bankAcctDetail.created_time AS bankAcctDetailCreatedTime,bankAcctDetail.last_modified_time AS bankAcctDetailLastModifiedTime,bankAcctDetail.created_time AS bankAcctDetailCreatedTime," +
            "bankAcctIdntfr.id AS bankAcctIdntfrId,bankAcctDoc.bank_account_detail_id AS bankAcctDocAcctId " +
            "FROM eg_bank_account AS bankAcct " +
            "LEFT JOIN " +
            "eg_bank_account_detail AS bankAcctDetail " +
            "ON (bankAcct.id=bankAcctDetail.bank_account_id) " +
            "LEFT JOIN " +
            "eg_bank_accounts_doc AS bankAcctDoc " +
            "ON (bankAcctDetail.id=bankAcctDoc.bank_account_detail_id) " +
            "LEFT JOIN " +
            "eg_bank_branch_identifier AS bankAcctIdntfr " +
            "ON (bankAcctDetail.id=bankAcctIdntfr.bank_account_detail_id) ";

    private static final String BANK_ACCOUNT_COUNT_QUERY = "SELECT distinct(bankAcct.id) " +
            "FROM eg_bank_account AS bankAcct " +
            "LEFT JOIN " +
            "eg_bank_account_detail AS bankAcctDetail " +
            "ON (bankAcct.id=bankAcctDetail.bank_account_id) " +
            "LEFT JOIN " +
            "eg_bank_accounts_doc AS bankAcctDoc " +
            "ON (bankAcctDetail.id=bankAcctDoc.bank_account_detail_id) " +
            "LEFT JOIN " +
            "eg_bank_branch_identifier AS bankAcctIdntfr " +
            "ON (bankAcctDetail.id=bankAcctIdntfr.bank_account_detail_id) ";

    private final String paginationWrapper = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY bankAcctLastModifiedTime DESC , bankAcctId) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";

    private static final String COUNT_WRAPPER = " SELECT COUNT(*) FROM ({INTERNAL_QUERY}) AS count ";

    @Autowired
    public BankAccountQueryBuilder(Configuration config) {
        this.config = config;
    }

    private static void addClauseIfRequired(List<Object> values, StringBuilder queryString) {
        if (values.isEmpty())
            queryString.append(" WHERE ");
        else {
            queryString.append(" AND");
        }
    }

    /**
     * @param bankAccountDetails
     * @param preparedStmtList
     * @return
     */
    public String getBankAccountQuery(BankAccountSearchRequest bankAccountSearchRequest, List<Object> preparedStmtList) {
        log.info("BankAccountQueryBuilder::getBankAccountQuery");
        StringBuilder queryBuilder = null;
        BankAccountSearchCriteria bankAccountDetails = bankAccountSearchRequest.getBankAccountDetails();
        if (!bankAccountDetails.getIsCountNeeded())
            queryBuilder = new StringBuilder(FETCH_BANK_ACCOUNT_QUERY);
        else
            queryBuilder = new StringBuilder(BANK_ACCOUNT_COUNT_QUERY);

        List<String> ids = bankAccountDetails.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bankAcct.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        if (StringUtils.isNotBlank(bankAccountDetails.getTenantId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bankAcct.tenant_id=? ");
            preparedStmtList.add(bankAccountDetails.getTenantId());
        }

        if (!CollectionUtils.isEmpty(bankAccountDetails.getAccountNumber())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bankAcctDetail.account_number IN (").append(createQuery(bankAccountDetails.getAccountNumber())).append(")");
            addToPreparedStatement(preparedStmtList, bankAccountDetails.getAccountNumber());
        }

        if (StringUtils.isNotBlank(bankAccountDetails.getAccountHolderName())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bankAcctDetail.account_holder_name=? ");
            preparedStmtList.add(bankAccountDetails.getAccountHolderName());
        }

        if (StringUtils.isNotBlank(bankAccountDetails.getServiceCode())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bankAcct.service_code=? ");
            preparedStmtList.add(bankAccountDetails.getServiceCode());
        }

        if (!CollectionUtils.isEmpty(bankAccountDetails.getReferenceId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bankAcct.reference_id IN (").append(createQuery(bankAccountDetails.getReferenceId())).append(")");
            addToPreparedStatement(preparedStmtList, bankAccountDetails.getReferenceId());
        }

        BankBranchIdentifier bankBranchIdentifierCode = bankAccountDetails.getBankBranchIdentifierCode();
        if (bankBranchIdentifierCode != null) {
            if (StringUtils.isNotBlank(bankBranchIdentifierCode.getCode())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" bankAcctIdntfr.code=? ");
                preparedStmtList.add(bankBranchIdentifierCode.getCode());
            }
            if (StringUtils.isNotBlank(bankBranchIdentifierCode.getType())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" bankAcctIdntfr.type=? ");
                preparedStmtList.add(bankBranchIdentifierCode.getType());
            }
            //TODO-check
            if (ObjectUtils.isNotEmpty(bankBranchIdentifierCode.getAdditionalDetails())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" bankAcctIdntfrAdditional=? ");
                preparedStmtList.add(bankBranchIdentifierCode.getAdditionalDetails());
            }
        }

        //Default active is 'TRUE'
        if (bankAccountDetails.getIsActive() == null) {
            bankAccountDetails.setIsActive(Boolean.TRUE);
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bankAcctDetail.is_active=? ");
            preparedStmtList.add(bankAccountDetails.getIsActive());
        } else {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bankAcctDetail.is_active=? ");
            preparedStmtList.add(bankAccountDetails.getIsActive());
        }

        //default primary is 'TRUE'
        if (bankAccountDetails.getIsPrimary() == null) {
            bankAccountDetails.setIsPrimary(Boolean.TRUE);
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bankAcctDetail.is_primary=? ");
            preparedStmtList.add(bankAccountDetails.getIsPrimary());
        } else {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bankAcctDetail.is_primary=? ");
            preparedStmtList.add(bankAccountDetails.getIsPrimary());
        }

        if (!bankAccountDetails.getIsCountNeeded()) {
            addOrderByClause(queryBuilder, bankAccountSearchRequest);
            return addPaginationWrapper(queryBuilder.toString(), preparedStmtList, bankAccountSearchRequest);
        }

        return queryBuilder.toString();
    }


    private void addOrderByClause(StringBuilder queryBuilder, BankAccountSearchRequest bankAccountSearchRequest) {
        log.info("BankAccountQueryBuilder::addOrderByClause");
        Pagination pagination = bankAccountSearchRequest.getPagination();
        //default
        if (pagination.getSortBy() == null || StringUtils.isEmpty(pagination.getSortBy().name())) {
            queryBuilder.append(" ORDER BY bankAcct.created_time ");
        } else {
            switch (Pagination.SortBy.valueOf(pagination.getSortBy().name())) {
                case accountHolderName:
                    queryBuilder.append(" ORDER BY bankAcctDetail.account_holder_name ");
                    break;
                case serviceCode:
                    queryBuilder.append(" ORDER BY bankAcct.service_code ");
                    break;
                case accountNumber:
                    queryBuilder.append(" ORDER BY bankAcctDetail.account_number ");
                    break;
                case createdTime:
                    queryBuilder.append(" ORDER BY bankAcct.created_time ");
                    break;
                case bankBranchIdentifierCode:
                    queryBuilder.append(" ORDER BY bankAcctIdntfr.code ");
                    break;
                default:
                    queryBuilder.append(" ORDER BY bankAcct.created_time ");
                    break;
            }
        }

        if (pagination.getOrder() == Order.ASC)
            queryBuilder.append(" ASC ");
        else queryBuilder.append(" DESC ");

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

    private String addPaginationWrapper(String query, List<Object> preparedStmtList,
                                        BankAccountSearchRequest bankAccountSearchRequest) {
        log.info("BankAccountQueryBuilder::addPaginationWrapper");
        Double limit = Double.valueOf(config.getDefaultLimit());
        Double offset = Double.valueOf(config.getDefaultOffset());
        String finalQuery = paginationWrapper.replace("{}", query);

        Pagination pagination = bankAccountSearchRequest.getPagination();

        if (pagination.getLimit() != null) {
            if (pagination.getLimit() <= config.getMaxLimit())
                limit = pagination.getLimit();
            else
                limit = Double.valueOf(config.getMaxLimit());
        }

        if (pagination.getOffSet() != null)
            offset = pagination.getOffSet();

        preparedStmtList.add(offset);
        preparedStmtList.add(limit + offset);

        return finalQuery;
    }

    public String getSearchCountQueryString(BankAccountSearchRequest searchRequest, List<Object> preparedStmtList) {
        log.info("BankAccountQueryBuilder::getSearchCountQueryString");
        String query = getBankAccountQuery(searchRequest, preparedStmtList);
        if (query != null)
            return COUNT_WRAPPER.replace("{INTERNAL_QUERY}", query);
        else
            return query;
    }
}
