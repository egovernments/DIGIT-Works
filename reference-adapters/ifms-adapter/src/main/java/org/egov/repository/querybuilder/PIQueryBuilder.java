package org.egov.repository.querybuilder;

import org.springframework.stereotype.Component;

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
            + "(id, tenantId, muktaReferenceId, piId, beneficiaryId, beneficiaryType, bankAccountId, amount, voucherNumber, voucherDate, utrNo, utrDate, endToEndId, challanNumber, "
            + "challanDate, paymentStatus, paymentStatusMessage, additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :tenantId, :muktaReferenceId, :piId, :beneficiaryId, :beneficiaryType, :bankAccountId, :amount, :voucherNumber, :voucherDate, :utrNo, :utrDate, :endToEndId, :challanNumber, "
            + " :challanDate, :paymentStatus, :paymentStatusMessage, :additionalDetails, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";

    public static final String TRANSACTION_DETAILS_INSERT_QUERY = "INSERT INTO jit_transaction_details "
            + "(id, tenantId, sanctionId, paymentInstId, transactionAmount, transactionDate, transactionType, "
            + "additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :tenantId, :sanctionId, :paymentInstId, :transactionAmount, :transactionDate, :transactionType, "
            + " :additionalDetails, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";

    public static final String BENEFICIARY_LINEITEM_INSERT_QUERY = "INSERT INTO jit_beneficiary_lineitems "
            + "(id, beneficiaryId, lineItemId, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :beneficiaryId, :lineItemId, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";


}
