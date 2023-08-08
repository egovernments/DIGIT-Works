package org.egov.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.web.models.enums.PIType;
import org.egov.web.models.jit.PISearchCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class PIQueryBuilder {

    public static final String PAYMENT_INSTRUCTION_INSERT_QUERY = "INSERT INTO jit_payment_inst_details "
            + "(id, tenantId, piNumber, parentPiNumber, muktaReferenceId, numBeneficiaries, grossAmount, netAmount, piStatus, isActive, piSuccessCode, piSuccessDesc, "
            + "piApprovedId, piApprovalDate, piErrorResp, additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :tenantId, :piNumber, :parentPiNumber, :muktaReferenceId, :numBeneficiaries, :grossAmount, :netAmount, :piStatus, :isActive, :piSuccessCode, :piSuccessDesc, "
            + " :piApprovedId, :piApprovalDate, :piErrorResp, :additionalDetails, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";

    public static final String PAYMENT_ADVICE_DETAILS_INSERT_QUERY = "INSERT INTO jit_payment_advice_details "
            + "(id, tenantId, muktaReferenceId, piId, paBillRefNumber, paFinYear, paAdviceId, paAdviceDate, paTokenNumber, paTokenDate,"
            + "paErrorMsg, additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :tenantId, :muktaReferenceId, :piId, :paBillRefNumber, :paFinYear, :paAdviceId, :paAdviceDate, :paTokenNumber, :paTokenDate,"
            + " :paErrorMsg, :additionalDetails, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";
    public static final String BENEFICIARY_DETAILS_INSERT_QUERY = "INSERT INTO jit_beneficiary_details "
            + "(id, tenantId, muktaReferenceId, piId, beneficiaryId, beneficiaryType, beneficiaryNumber, bankAccountId, amount, voucherNumber, voucherDate, utrNo, utrDate, endToEndId, challanNumber, "
            + "challanDate, paymentStatus, paymentStatusMessage, additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :tenantId, :muktaReferenceId, :piId, :beneficiaryId, :beneficiaryType, :beneficiaryNumber, :bankAccountId, :amount, :voucherNumber, :voucherDate, :utrNo, :utrDate, :endToEndId, :challanNumber, "
            + " :challanDate, :paymentStatus, :paymentStatusMessage, :additionalDetails, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";

    public static final String TRANSACTION_DETAILS_INSERT_QUERY = "INSERT INTO jit_transaction_details "
            + "(id, tenantId, sanctionId, paymentInstId, transactionAmount, transactionDate, transactionType, "
            + "additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :tenantId, :sanctionId, :paymentInstId, :transactionAmount, :transactionDate, :transactionType, "
            + " :additionalDetails, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";

    public static final String BENEFICIARY_LINEITEM_INSERT_QUERY = "INSERT INTO jit_beneficiary_lineitems "
            + "(id, beneficiaryId, lineItemId, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :beneficiaryId, :lineItemId, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";

    public static final String PAYMENT_INSTRUCTION_UPDATE_QUERY = "UPDATE jit_payment_inst_details "
            + "SET piStatus=:piStatus, piSuccessCode=:piSuccessCode, piSuccessDesc=:piSuccessDesc, piApprovedId=:piApprovedId, piApprovalDate=:piApprovalDate, "
            +" piErrorResp=:piErrorResp, additionalDetails=:additionalDetails, lastmodifiedtime=:lastmodifiedtime, lastmodifiedby=:lastmodifiedby "
            + " WHERE id=:id;";

    public static final String PAYMENT_ADVICE_DETAILS_UPDATE_QUERY = "UPDATE jit_payment_advice_details "
            + "SET paBillRefNumber=:paBillRefNumber, paFinYear=:paFinYear, paAdviceId=:paAdviceId, paAdviceDate=:paAdviceDate, paTokenNumber=:paTokenNumber, paTokenDate=:paTokenDate,"
            + "paErrorMsg=:paErrorMsg, additionalDetails=:additionalDetails, lastmodifiedtime=:lastmodifiedtime, lastmodifiedby=:lastmodifiedby "
            + " WHERE id=:id;";
    public static final String BENEFICIARY_DETAILS_UPDATE_QUERY = "UPDATE jit_beneficiary_details "
            + "SET  voucherNumber=:voucherNumber, voucherDate=:voucherDate, utrNo=:utrNo, utrDate=:utrDate, endToEndId=:endToEndId, challanNumber=:challanNumber, "
            + "challanDate=:challanDate, paymentStatus=:paymentStatus, paymentStatusMessage=:paymentStatusMessage, additionalDetails=:additionalDetails, lastmodifiedtime=:lastmodifiedtime, lastmodifiedby=:lastmodifiedby "
            + " WHERE id=:id;";

    public static final String SEARCH_PI_QUERY = " SELECT pymtInst.id as pymtInstId, " +
            "pymtInst.tenantId as pymtInstTenantId, " +
            "pymtInst.piNumber as pymtInstPiNumber, " +
            "pymtInst.parentPiNumber as pymtInstParentPiNumber, " +
            "pymtInst.muktaReferenceId as pymtInstMuktaReferenceId, " +
            "pymtInst.numBeneficiaries as pymtInstNumBeneficiaries, " +
            "pymtInst.grossAmount as pymtInstGrossAmount, " +
            "pymtInst.netAmount as pymtInstNetAmount, " +
            "pymtInst.piStatus as pymtInstPiStatus, " +
            "pymtInst.isActive as pymtInstIsActive, " +
            "pymtInst.piSuccessCode as pymtInstPiSuccessCode, " +
            "pymtInst.piSuccessDesc as pymtInstPiSuccessDesc, " +
            "pymtInst.piApprovedId as pymtInstPiApprovedId, " +
            "pymtInst.piApprovalDate as pymtInstPiApprovalDate, " +
            "pymtInst.piErrorResp as pymtInstPiErrorResp, " +
            "pymtInst.additionalDetails as pymtInstAdditionalDetails, " +
            "pymtInst.createdtime as pymtInstCreatedTime, " +
            "pymtInst.createdby as pymtInstCreatedBy, " +
            "pymtInst.lastmodifiedtime as pymtInstLastModifiedTime, " +
            "pymtInst.lastmodifiedby as pymtInstLastModifiedBy, " +
            "pymtAdvDtl.id as pymtAdvDtlId, " +
            "pymtAdvDtl.tenantId as pymtAdvDtlTenantId, " +
            "pymtAdvDtl.muktaReferenceId as pymtAdvDtlMuktaReferenceId, " +
            "pymtAdvDtl.piId as pymtAdvDtlPiId, " +
            "pymtAdvDtl.paBillRefNumber as pymtAdvDtlPaBillRefNumber, " +
            "pymtAdvDtl.paFinYear as pymtAdvDtlPaFinYear, " +
            "pymtAdvDtl.paAdviceId as pymtAdvDtlPaAdviceId, " +
            "pymtAdvDtl.paAdviceDate as pymtAdvDtlPaAdviceDate, " +
            "pymtAdvDtl.paTokenNumber as pymtAdvDtlPaTokenNumber, " +
            "pymtAdvDtl.paTokenDate as pymtAdvDtlPaTokenDate, " +
            "pymtAdvDtl.paErrorMsg as pymtAdvDtlPaErrorMsg, " +
            "pymtAdvDtl.additionalDetails as pymtAdvDtlAdditionalDetails, " +
            "pymtAdvDtl.createdtime as pymtAdvDtlCreatedTime, " +
            "pymtAdvDtl.createdby as pymtAdvDtlCreatedBy, " +
            "pymtAdvDtl.lastmodifiedtime as pymtAdvDtlLastModifiedTime, " +
            "pymtAdvDtl.lastmodifiedby as pymtAdvDtlLastModifiedBy, " +
            "benfDetail.id as benfDetailId, " +
            "benfDetail.tenantId as benfDetailTenantId, " +
            "benfDetail.muktaReferenceId as benfDetailMuktaReferenceId, " +
            "benfDetail.piId as benfDetailPiId, " +
            "benfDetail.beneficiaryId as benfDetailBeneficiaryId, " +
            "benfDetail.beneficiaryType as benfDetailBeneficiaryType, " +
            "benfDetail.beneficiaryNumber as benfDetailBeneficiaryNumber, " +
            "benfDetail.bankAccountId as benfDetailBankAccountId, " +
            "benfDetail.amount as benfDetailAmount, " +
            "benfDetail.voucherNumber as benfDetailVoucherNumber, " +
            "benfDetail.voucherDate as benfDetailVoucherDate, " +
            "benfDetail.utrNo as benfDetailUtrNo, " +
            "benfDetail.utrDate as benfDetailUtrDate, " +
            "benfDetail.endToEndId as benfDetailEndToEndId, " +
            "benfDetail.challanNumber as benfDetailChallanNumber, " +
            "benfDetail.challanDate as benfDetailChallanDate, " +
            "benfDetail.paymentStatus as benfDetailPaymentStatus, " +
            "benfDetail.paymentStatusMessage as benfDetailPaymentStatusMessage, " +
            "benfDetail.additionalDetails as benfDetailAdditionalDetails, " +
            "benfDetail.createdtime as benfDetailCreatedTime, " +
            "benfDetail.createdby as benfDetailCreatedBy, " +
            "benfDetail.lastmodifiedtime as benfDetailLastModifiedTime, " +
            "benfDetail.lastmodifiedby as benfDetailLastModifiedBy, " +
            "transDetail.id as transDetailId, " +
            "transDetail.tenantId as transDetailTenantId, " +
            "transDetail.sanctionId as transDetailSanctionId, " +
            "transDetail.paymentInstId as transDetailPaymentInstId, " +
            "transDetail.transactionAmount as transDetailTransactionAmount, " +
            "transDetail.transactionDate as transDetailTransactionDate, " +
            "transDetail.transactionType as transDetailTransactionType, " +
            "transDetail.additionalDetails as transDetailAdditionalDetails, " +
            "transDetail.createdtime as transDetailCreatedTime, " +
            "transDetail.createdby as transDetailCreatedBy, " +
            "transDetail.lastmodifiedtime as transDetailLastModifiedTime, " +
            "transDetail.lastmodifiedby as transDetailLastModifiedBy, " +
            "benfLineItem.id as benfLineItemId, " +
            "benfLineItem.beneficiaryId as benfLineItemBeneficiaryId, " +
            "benfLineItem.lineItemId as benfLineItemLineItemId, " +
            "benfLineItem.createdtime as benfLineItemCreatedTime, " +
            "benfLineItem.createdby as benfLineItemCreatedBy, " +
            "benfLineItem.lastmodifiedtime as benfLineItemLastModifiedTime, " +
            "benfLineItem.lastmodifiedby as benfLineItemLastModifiedBy " +
            "FROM jit_payment_inst_details AS pymtInst " +
            "LEFT JOIN " +
            "jit_payment_advice_details AS pymtAdvDtl " +
            "ON (pymtInst.id=pymtAdvDtl.piId) " +
            "LEFT JOIN " +
            "jit_transaction_details AS transDetail " +
            "ON (pymtInst.id=transDetail.paymentInstId) " +
            "LEFT JOIN " +
            "jit_beneficiary_details AS benfDetail " +
            "ON (pymtInst.id=benfDetail.piId) " +
            "LEFT JOIN " +
            "jit_beneficiary_lineitems as benfLineItem " +
            "ON (benfDetail.id=benfLineItem.beneficiaryId)";


    public String getPaymentInstructionSearchQuery(PISearchCriteria criteria, List<Object> preparedStmtList){
        StringBuilder query = new StringBuilder(SEARCH_PI_QUERY);

        List<String> ids = criteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" pymtInst.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        if (StringUtils.isNotBlank(criteria.getTenantId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" pymtInst.tenantId=? ");
            preparedStmtList.add(criteria.getTenantId());
        }

        if (StringUtils.isNotBlank(criteria.getMuktaReferenceId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" pymtInst.muktaReferenceId=? ");
            preparedStmtList.add(criteria.getMuktaReferenceId());
        }
        if (criteria.getPiStatus() != null && !StringUtils.isEmpty(criteria.getPiStatus().toString())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" pymtInst.piStatus=? ");
            preparedStmtList.add(criteria.getPiStatus().toString());
        }
        if (StringUtils.isNotBlank(criteria.getJitBillNo())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" pymtInst.piNumber=? ");
            preparedStmtList.add(criteria.getJitBillNo());
        }
        if (StringUtils.isNotBlank(criteria.getParentPiNumber())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" pymtInst.parentPiNumber=? ");
            preparedStmtList.add(criteria.getParentPiNumber());
        }
        Set<String> piNumbers = criteria.getJitBillNumbers();
        if (piNumbers != null && !piNumbers.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" pymtInst.piNumber IN (").append(createQuery(piNumbers)).append(")");
            addToPreparedStatement(preparedStmtList, piNumbers);
        }
        if (criteria.getIsActive() != null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" pymtInst.isActive=? ");
            preparedStmtList.add(criteria.getIsActive());
        }
        if (criteria.getPiType() != null && criteria.getPiType().equals(PIType.ORIGINAL)) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" pymtInst.parentPiNumber IS NULL OR pymtInst.parentPiNumber= '' ");
        } else if (criteria.getPiType() != null && criteria.getPiType().equals(PIType.REVISED)) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" pymtInst.parentPiNumber IS NOT NULL AND pymtInst.parentPiNumber <> '' ");
        }

        addOrderByClause(query, criteria);

        addLimitAndOffset(query, criteria, preparedStmtList);

        log.info("executing query ::: " + query);
        return query.toString();
    }

    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList) {
        if (preparedStmtList.isEmpty()) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }
    }
    private void addLimitAndOffset(StringBuilder queryBuilder, PISearchCriteria criteria, List<Object> preparedStmtList) {
        if (criteria.getOffset() != null) {
            queryBuilder.append(" OFFSET ? ");
            preparedStmtList.add(criteria.getOffset());
        }

        if (criteria.getLimit() != null) {
            queryBuilder.append(" LIMIT ? ");
            preparedStmtList.add(criteria.getLimit());
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
        preparedStmtList.addAll(ids);
    }
    private void addOrderByClause(StringBuilder queryBuilder, PISearchCriteria criteria) {

        //default
        if (criteria.getSortBy() == null || StringUtils.isEmpty(criteria.getSortBy().name())) {
            queryBuilder.append(" ORDER BY pymtInst.createdtime ");
        } else if (criteria.getSortBy() != null && !StringUtils.isEmpty(criteria.getSortBy().name())) {
            queryBuilder.append(" ORDER BY pymtInst."+ criteria.getSortBy().toString() +" ");
        }

        if (criteria.getSortOrder() == PISearchCriteria.SortOrder.ASC)
            queryBuilder.append(" ASC ");
        else queryBuilder.append(" DESC ");
    }
}
