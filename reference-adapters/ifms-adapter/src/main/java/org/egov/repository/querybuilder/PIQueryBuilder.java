package org.egov.repository.querybuilder;

import org.apache.commons.lang3.StringUtils;
import org.egov.web.models.bill.PISearchCriteria;
import org.egov.web.models.jit.SanctionDetailsSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class PIQueryBuilder {

    public static final String PAYMENT_INSTRUCTION_INSERT_QUERY = "INSERT INTO jit_payment_inst_details "
            + "(id, tenantId, piNumber, parentPiNumber, muktaReferenceId, numBeneficiaries, grossAmount, netAmount, piStatus, piSuccessCode, piSuccessDesc, "
            + "piApprovedId, piApprovalDate, piErrorResp, additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :tenantId, :piNumber, :parentPiNumber, :muktaReferenceId, :numBeneficiaries, :grossAmount, :netAmount, :piStatus, :piSuccessCode, :piSuccessDesc, "
            + " :piApprovedId, :piApprovalDate, :piErrorResp, :additionalDetails, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";

    public static final String PAYMENT_ADVICE_DETAILS_INSERT_QUERY = "INSERT INTO jit_payment_advice_details "
            + "(id, tenantId, muktaReferenceId, piId, paBillRefNumber, paFinYear, paAdviceId, paAdviceDate, paTokenNumber, paTokenDate,"
            + "paErrorMsg, additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :tenantId, :muktaReferenceId, :piId, :paBillRefNumber, :paFinYear, :paAdviceId, :paAdviceDate, :paTokenNumber, :paTokenDate,"
            + " :paErrorMsg, :additionalDetails, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";
    public static final String BENEFICIARY_DETAILS_INSERT_QUERY = "INSERT INTO jit_beneficiary_details "
            + "(id, tenantId, muktaReferenceId, piId, beneficiaryId, amount, voucherNumber, voucherDate, utrNo, utrDate, endToEndId, challanNumber, "
            + "challanDate, paymentStatus, paymentStatusMessage, additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :tenantId, :muktaReferenceId, :piId, :beneficiaryId, :amount, :voucherNumber, :voucherDate, :utrNo, :utrDate, :endToEndId, :challanNumber, "
            + " :challanDate, :paymentStatus, :paymentStatusMessage, :additionalDetails, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";

    public static final String TRANSACTION_DETAILS_INSERT_QUERY = "INSERT INTO jit_transaction_details "
            + "(id, tenantId, sanctionId, paymentInstId, transactionAmount, transactionDate, transactionType, "
            + "additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :tenantId, :sanctionId, :paymentInstId, :transactionAmount, :transactionDate, :transactionType, "
            + " :additionalDetails, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";

    public static final String BENEFICIARY_LINEITEM_INSERT_QUERY = "INSERT INTO jit_beneficiary_lineitems "
            + "(id, beneficiaryId, lineItemId, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :beneficiaryId, :lineItemId, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";

    public static final String PAYMENT_INSTRUCTION_SELECT_QUERY = " SELECT jpi.id as jpiId, " +
            "jpi.tenantId as jpiTenantId, " +
            "jpi.piNumber as jpiPiNumber, " +
            "jpi.parentPiNumber as jpiParentPiNumber, " +
            "jpi.muktaReferenceId as jpiMuktaReferenceId, " +
            "jpi.numBeneficiaries as jpiNumBeneficiaries, " +
            "jpi.grossAmount as jpiGrossAmount, " +
            "jpi.netAmount as jpiNetAmount, " +
            "jpi.piStatus as jpiPiStatus, " +
            "jpi.piSuccessCode as jpiPiSuccessCode, " +
            "jpi.piSuccessDesc as jpiPiSuccessDesc, " +
            "jpi.piApprovedId as jpiPiApprovedId, " +
            "jpi.piApprovalDate as jpiPiApprovalDate, " +
            "jpi.piErrorResp as jpiPiErrorResp, " +
            "jpi.additionalDetails as jpiAdditionalDetails, " +
            "jpi.createdtime as jpiCreatedTime, " +
            "jpi.createdby as jpiCreatedBy, " +
            "jpi.lastmodifiedtime as jpiLastModifiedTime, " +
            "jpi.lastmodifiedby as jpiLastModifiedBy, " +
            "jpa.id as jpaId, " +
            "jpa.tenantId as jpaTenantId, " +
            "jpa.muktaReferenceId as jpaMuktaReferenceId, " +
            "jpa.piId as jpaPiId, " +
            "jpa.paBillRefNumber as jpaPaBillRefNumber, " +
            "jpa.paFinYear as jpaPaFinYear, " +
            "jpa.paAdviceId as jpaPaAdviceId, " +
            "jpa.paAdviceDate as jpaPaAdviceDate, " +
            "jpa.paTokenNumber as jpaPaTokenNumber, " +
            "jpa.paTokenDate as jpaPaTokenDate, " +
            "jpa.paErrorMsg as jpaPaErrorMsg, " +
            "jpa.additionalDetails as jpaAdditionalDetails, " +
            "jpa.createdtime as jpaCreatedTime, " +
            "jpa.createdby as jpaCreatedBy, " +
            "jpa.lastmodifiedtime as jpaLastModifiedTime, " +
            "jpa.lastmodifiedby as jpaLastModifiedBy, " +
            "jbd.id as jbdId, " +
            "jbd.tenantId as jbdTenantId, " +
            "jbd.muktaReferenceId as jbdMuktaReferenceId, " +
            "jbd.piId as jbdPiId, " +
            "jbd.beneficiaryId as jbdBeneficiaryId, " +
            "jbd.amount as jbdAmount, " +
            "jbd.voucherNumber as jbdVoucherNumber, " +
            "jbd.voucherDate as jbdVoucherDate, " +
            "jbd.utrNo as jbdUtrNo, " +
            "jbd.utrDate as jbdUtrDate, " +
            "jbd.endToEndId as jbdEndToEndId, " +
            "jbd.challanNumber as jbdChallanNumber, " +
            "jbd.challanDate as jbdChallanDate, " +
            "jbd.paymentStatus as jbdPaymentStatus, " +
            "jbd.paymentStatusMessage as jbdPaymentStatusMessage, " +
            "jbd.additionalDetails as jbdAdditionalDetails, " +
            "jbd.createdtime as jbdCreatedTime, " +
            "jbd.createdby as jbdCreatedBy, " +
            "jbd.lastmodifiedtime as jbdLastModifiedTime, " +
            "jbd.lastmodifiedby as jbdLastModifiedBy, " +
            "jtd.id as jtdId, " +
            "jtd.tenantId as jtdTenantId, " +
            "jtd.sanctionId as jtdSanctionId, " +
            "jtd.paymentInstId as jtdPaymentInstId, " +
            "jtd.transactionAmount as jtdTransactionAmount, " +
            "jtd.transactionDate as jtdTransactionDate, " +
            "jtd.transactionType as jtdTransactionType, " +
            "jtd.additionalDetails as jtdAdditionalDetails, " +
            "jtd.createdtime as jtdCreatedTime, " +
            "jtd.createdby as jtdCreatedBy, " +
            "jtd.lastmodifiedtime as jtdLastModifiedTime, " +
            "jtd.lastmodifiedby as jtdLastModifiedBy, " +
            "jbl.id as jblId, " +
            "jbl.beneficiaryId as jblBeneficiaryId, " +
            "jbl.lineItemId as jblLineItemId, " +
            "jbl.createdtime as jblCreatedTime, " +
            "jbl.createdby as jblCreatedBy, " +
            "jbl.lastmodifiedtime as jblLastModifiedTime, " +
            "jbl.lastmodifiedby as jblLastModifiedBy, " +
            ;

    public String getPaymentInstructionSearchQuery(PISearchCriteria criteria, List<Object> preparedStmtList){
        StringBuilder query = new StringBuilder(PAYMENT_INSTRUCTION_SELECT_QUERY);

        List<String> ids = criteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" jsd.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        if (StringUtils.isNotBlank(criteria.getTenantId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" jsd.tenantId=? ");
            preparedStmtList.add(criteria.getTenantId());
        }

        if (StringUtils.isNotBlank(criteria.getHoaCode())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" jsd.hoaCode=? ");
            preparedStmtList.add(criteria.getHoaCode());
        }
        if (StringUtils.isNotBlank(criteria.getDdoCode())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" jsd.ddoCode=? ");
            preparedStmtList.add(criteria.getDdoCode());
        }
        if (StringUtils.isNotBlank(criteria.getMasterAllotmentId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" jsd.masterAllotmentId=? ");
            preparedStmtList.add(criteria.getMasterAllotmentId());
        }


        addOrderByClause(query, criteria);

        addLimitAndOffset(query, criteria, preparedStmtList);

        return query.toString();
    }

    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList) {
        if (preparedStmtList.isEmpty()) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }
    }
    private void addLimitAndOffset(StringBuilder queryBuilder, SanctionDetailsSearchCriteria criteria, List<Object> preparedStmtList) {
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
    private void addOrderByClause(StringBuilder queryBuilder, SanctionDetailsSearchCriteria criteria) {

        //default
        if (criteria.getSortBy() == null || StringUtils.isEmpty(criteria.getSortBy().name())) {
            queryBuilder.append(" ORDER BY jsd.lastmodifiedtime ");
        }

        if (criteria.getSortOrder() == SanctionDetailsSearchCriteria.SortOrder.ASC)
            queryBuilder.append(" ASC ");
        else queryBuilder.append(" DESC ");
    }
}
