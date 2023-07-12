package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.enums.BeneficiaryPaymentStatus;
import org.egov.web.models.enums.BeneficiaryType;
import org.egov.web.models.enums.PIStatus;
import org.egov.web.models.enums.TransactionType;
import org.egov.web.models.jit.*;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Slf4j
public class PIRowMapper implements ResultSetExtractor<List<PaymentInstruction>> {

    @Autowired
    ObjectMapper mapper;

    public List<PaymentInstruction> extractData(ResultSet rs) throws SQLException, DataAccessException{
        Map<String, PaymentInstruction> paymentInstructionMap = new LinkedHashMap<>();

        while(rs.next()) {
            String id = rs.getString("jpiId");
            PaymentInstruction paymentInstruction = paymentInstructionMap.get(id);

            if(paymentInstruction == null){

                String tenantId = rs.getString("jpiTenantId");
                String jitBillNo = rs.getString("jpiPiNumber");
                String parentPiNumber = rs.getString("jpiParentPiNumber");
                String muktaReferenceId = rs.getString("jpiMuktaReferenceId");
                Integer numBeneficiaries = rs.getInt("jpiNumBeneficiaries");
                BigDecimal grossAmount = rs.getBigDecimal("jpiGrossAmount");
                BigDecimal netAmount = rs.getBigDecimal("jpiNetAmount");
                PIStatus piStatus = PIStatus.fromValue(rs.getString("jpiPiStatus"));
                String piSuccessCode = rs.getString("jpiPiSuccessCode");
                String piSuccessDesc = rs.getString("jpiPiSuccessDesc");
                String piApprovedId = rs.getString("jpiPiApprovedId");
                String piApprovalDate = rs.getString("jpiPiApprovalDate");
                String piErrorResp = rs.getString("jpiPiErrorResp");
                Object additionalDetails = getAdditionalDetail(rs, "jpiAdditionalDetails");

                AuditDetails auditDetails = getAuditDetailsForKey(rs, "jpiCreatedBy","jpiCreatedTime",
                        "jpiLastModifiedBy","jpiLastModifiedTime") ;

                paymentInstruction = PaymentInstruction.builder()
                        .id(id)
                        .tenantId(tenantId)
                        .jitBillNo(jitBillNo)
                        .parentPiNumber(parentPiNumber)
                        .muktaReferenceId(muktaReferenceId)
                        .numBeneficiaries(numBeneficiaries)
                        .grossAmount(grossAmount)
                        .netAmount(netAmount)
                        .piStatus(piStatus)
                        .piSuccessCode(piSuccessCode)
                        .piSuccessDesc(piSuccessDesc)
                        .piApprovedId(piApprovedId)
                        .piApprovalDate(piApprovalDate)
                        .piErrorResp(piErrorResp)
                        .additionalDetails(additionalDetails)
                        .auditDetails(auditDetails)
                        .build();

                paymentInstructionMap.put(paymentInstruction.getId(), paymentInstruction);
            }

            addTransactionDetails(rs, paymentInstruction);
            addPADetails(rs, paymentInstruction);
            addBenificiaryDetails(rs, paymentInstruction);
        }
        log.debug("converting map to list object ::: " + paymentInstructionMap.values());
        return new ArrayList<>(paymentInstructionMap.values());
    }

    private void addTransactionDetails(ResultSet rs, PaymentInstruction paymentInstruction) throws SQLException{
        if(paymentInstruction.getTransactionDetails() == null) {
            paymentInstruction.setTransactionDetails(new ArrayList<>());
        }
        TransactionDetails transactionDetail = null;
        String transactionDetailId = rs.getString("jtdId");
        for(TransactionDetails transactionDetails1 : paymentInstruction.getTransactionDetails()) {
            if(transactionDetails1.getId().equalsIgnoreCase(transactionDetailId)) {
                transactionDetail = transactionDetails1;
                break;
            }
        }

        if(transactionDetail == null) {
            String transactionDetailTenantId = rs.getString("jtdTenantId");
            String sanctionId = rs.getString("jtdSanctionId");
            String paymentInstId = rs.getString("jtdPaymentInstId");
            BigDecimal transactionAmount = rs.getBigDecimal("jtdTransactionAmount");
            Long transactionDate = rs.getLong("jtdTransactionDate");
            TransactionType transactionType = TransactionType.fromValue(rs.getString("jtdTransactionType"));
            Object transactionAdditionalDetails = rs.getObject("jtdAdditionalDetails");

            AuditDetails auditDetails = getAuditDetailsForKey(rs, "jtdCreatedBy","jtdCreatedTime",
                    "jtdLastModifiedBy","jtdLastModifiedTime") ;

            if(StringUtils.isNotEmpty(transactionDetailId) && paymentInstruction.getId().equalsIgnoreCase(paymentInstId)) {

                transactionDetail = TransactionDetails.builder()
                        .id(transactionDetailId)
                        .tenantId(transactionDetailTenantId)
                        .sanctionId(sanctionId)
                        .paymentInstId(paymentInstId)
                        .transactionAmount(transactionAmount)
                        .transactionDate(transactionDate)
                        .transactionType(transactionType)
                        .additionalDetails(transactionAdditionalDetails)
                        .auditDetails(auditDetails)
                        .build();

                paymentInstruction.getTransactionDetails().add(transactionDetail);
            }
        }
    }

    private void addPADetails(ResultSet rs, PaymentInstruction paymentInstruction)throws SQLException{
        if(paymentInstruction.getPaDetails() == null){
            paymentInstruction.setPaDetails(new ArrayList<>());
        }
        String jpaId = rs.getString("jpaId");
        PADetails paDetail = null;
        for(PADetails paDetails1 : paymentInstruction.getPaDetails()) {
            if(paDetails1.getId().equalsIgnoreCase(jpaId)) {
                paDetail = paDetails1;
                break;
            }
        }
        if(paDetail == null) {
            String jpaTenantId = rs.getString("jpaTenantId");
            String jpaMuktaReferenceId = rs.getString("jpaMuktaReferenceId");
            String jpaPiId = rs.getString("jpaPiId");
            String jpaPaBillRefNumber = rs.getString("jpaPaBillRefNumber");
            String jpaPaFinYear = rs.getString("jpaPaFinYear");
            String jpaPaAdviceId = rs.getString("jpaPaAdviceId");
            String jpaPaAdviceDate = rs.getString("jpaPaAdviceDate");
            String jpaPaTokenNumber = rs.getString("jpaPaTokenNumber");
            String jpaPaTokenDate = rs.getString("jpaPaTokenDate");
            String jpaPaErrorMsg = rs.getString("jpaPaErrorMsg");
            Object jpaAdditionalDetails = getAdditionalDetail(rs, "jpaAdditionalDetails");

            AuditDetails auditDetails = getAuditDetailsForKey(rs, "jpaCreatedBy","jpaCreatedTime",
                    "jpaLastModifiedBy","jpaLastModifiedTime") ;

            if(StringUtils.isNotEmpty(jpaId) && jpaPiId.equalsIgnoreCase(paymentInstruction.getId())) {
                paDetail = PADetails.builder()
                        .id(jpaId)
                        .tenantId(jpaTenantId)
                        .muktaReferenceId(jpaMuktaReferenceId)
                        .piId(jpaPiId)
                        .paBillRefNumber(jpaPaBillRefNumber)
                        .paFinYear(jpaPaFinYear)
                        .paAdviceId(jpaPaAdviceId)
                        .paAdviceDate(jpaPaAdviceDate)
                        .paTokenNumber(jpaPaTokenNumber)
                        .paTokenDate(jpaPaTokenDate)
                        .paErrorMsg(jpaPaErrorMsg)
                        .additionalDetails(jpaAdditionalDetails)
                        .auditDetails(auditDetails)
                        .build();

                paymentInstruction.getPaDetails().add(paDetail);
            }
        }
    }

    private void addBenificiaryDetails(ResultSet rs, PaymentInstruction paymentInstruction)throws SQLException{
        if(paymentInstruction.getBeneficiaryDetails() == null) {
            paymentInstruction.setBeneficiaryDetails(new ArrayList<>());
        }
        String jbdId = rs.getString("jbdId");
        Beneficiary beneficiary = null;
        for (Beneficiary beneficiary1 : paymentInstruction.getBeneficiaryDetails()) {
            if(beneficiary1.getId().equalsIgnoreCase(jbdId)) {
                beneficiary = beneficiary1;
                break;
            }
        }

        if(beneficiary == null) {
            String jbdTenantId = rs.getString("jbdTenantId");
            String jbdMuktaReferenceId = rs.getString("jbdMuktaReferenceId");
            String jbdPiId = rs.getString("jbdPiId");
            String jbdBeneficiaryId = rs.getString("jbdBeneficiaryId");
            BeneficiaryType jbdBeneficiaryType = BeneficiaryType.fromValue(rs.getString("jbdBeneficiaryType"));
            String jbdBankAccountId = rs.getString("jbdBankAccountId");
            BigDecimal jbdAmount = rs.getBigDecimal("jbdAmount");
            String jbdVoucherNumber = rs.getString("jbdVoucherNumber");
            Long jbdVoucherDate = rs.getLong("jbdVoucherDate");
            if (jbdVoucherDate.equals(0L))
                jbdVoucherDate = null;
            String jbdUtrNo = rs.getString("jbdUtrNo");
            String jbdUtrDate = rs.getString("jbdUtrDate");
            String jbdEndToEndId = rs.getString("jbdEndToEndId");
            String jbdChallanNumber = rs.getString("jbdChallanNumber");
            String jbdChallanDate = rs.getString("jbdChallanDate");
            BeneficiaryPaymentStatus jbdPaymentStatus = BeneficiaryPaymentStatus.fromValue(rs.getString("jbdPaymentStatus"));
            String jbdPaymentStatusMessage = rs.getString("jbdPaymentStatusMessage");
            Object jbdAdditionalDetails = getAdditionalDetail(rs, "jbdAdditionalDetails") ;

            AuditDetails auditDetails = getAuditDetailsForKey(rs, "jbdCreatedBy","jbdCreatedTime",
                    "jbdLastModifiedBy","jbdLastModifiedTime") ;

            if(StringUtils.isNotEmpty(jbdId) && jbdPiId.equalsIgnoreCase(paymentInstruction.getId())) {
                beneficiary = Beneficiary.builder()
                        .id(jbdId)
                        .tenantId(jbdTenantId)
                        .muktaReferenceId(jbdMuktaReferenceId)
                        .piId(jbdPiId)
                        .beneficiaryId(jbdBeneficiaryId)
                        .beneficiaryType(jbdBeneficiaryType)
                        .bankAccountId(jbdBankAccountId)
                        .amount(jbdAmount)
                        .voucherNumber(jbdVoucherNumber)
                        .voucherDate(jbdVoucherDate)
                        .utrNo(jbdUtrNo)
                        .utrDate(jbdUtrDate)
                        .endToEndId(jbdEndToEndId)
                        .challanNumber(jbdChallanNumber)
                        .challanDate(jbdChallanDate)
                        .paymentStatus(jbdPaymentStatus)
                        .paymentStatusMessage(jbdPaymentStatusMessage)
                        .additionalDetails(jbdAdditionalDetails)
                        .auditDetails(auditDetails)
                        .build();

                paymentInstruction.getBeneficiaryDetails().add(beneficiary);
            }
        }
        addBeneficiaryLineItems(rs, beneficiary);
    }

    private void addBeneficiaryLineItems(ResultSet rs, Beneficiary beneficiary)throws SQLException{
        if(beneficiary.getBenfLineItems() == null) {
            beneficiary.setBenfLineItems(new ArrayList<>());
        }
        String jblId = rs.getString("jblId");

        BenfLineItems benfLineItem = null;
        for(BenfLineItems benfLineItems1 : beneficiary.getBenfLineItems()) {
            if(benfLineItems1.getId().equalsIgnoreCase(jblId)) {
                benfLineItem = benfLineItems1;
                break;
            }
        }

        if(benfLineItem == null) {
            String jblBeneficiaryId = rs.getString("jblBeneficiaryId");
            String jblLineItemId = rs.getString("jblLineItemId");

            AuditDetails auditDetails = getAuditDetailsForKey(rs, "jblCreatedBy","jblCreatedTime",
                    "jblLastModifiedBy","jblLastModifiedTime") ;

            if(StringUtils.isNotEmpty(jblId) && jblBeneficiaryId.equalsIgnoreCase(beneficiary.getId())) {
                benfLineItem = BenfLineItems.builder()
                        .id(jblId)
                        .beneficiaryId(jblBeneficiaryId)
                        .lineItemId(jblLineItemId)
                        .auditDetails(auditDetails)
                        .build();
                beneficiary.getBenfLineItems().add(benfLineItem);
            }
        }
    }

    private JsonNode getAdditionalDetail(ResultSet rs, String key) throws SQLException {

        JsonNode additionalDetails = null;

        try {

            PGobject obj = (PGobject) rs.getObject(key);
            if (obj != null) {
                additionalDetails = mapper.readTree(obj.getValue());
            }

        } catch (IOException e) {
            throw new CustomException("PARSING ERROR", "The propertyAdditionalDetail json cannot be parsed");
        }

        if(additionalDetails.isEmpty())
            additionalDetails = null;

        return additionalDetails;

    }

    private AuditDetails getAuditDetailsForKey (ResultSet rs, String createdBy, String createdTime, String modifiedBy, String modifiedTime) throws SQLException {

        return AuditDetails.builder()
                .lastModifiedTime(rs.getLong(modifiedTime))
                .createdTime((Long) rs.getObject(createdTime))
                .lastModifiedBy(rs.getString(modifiedBy))
                .createdBy(rs.getString(createdBy))
                .build();
    }

}
