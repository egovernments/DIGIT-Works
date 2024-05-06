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
            String id = rs.getString("pymtInstId");
            PaymentInstruction paymentInstruction = paymentInstructionMap.get(id);

            if(paymentInstruction == null){

                String tenantId = rs.getString("pymtInstTenantId");
                String programCode = rs.getString("pymtInstProgramCode");
                String jitBillNo = rs.getString("pymtInstPiNumber");
                String parentPiNumber = rs.getString("pymtInstParentPiNumber");
                String muktaReferenceId = rs.getString("pymtInstMuktaReferenceId");
                Integer numBeneficiaries = rs.getInt("pymtInstNumBeneficiaries");
                BigDecimal grossAmount = rs.getBigDecimal("pymtInstGrossAmount");
                BigDecimal netAmount = rs.getBigDecimal("pymtInstNetAmount");
                PIStatus piStatus = PIStatus.fromValue(rs.getString("pymtInstPiStatus"));
                Boolean piIsActive = rs.getBoolean("pymtInstIsActive");
                String piSuccessCode = rs.getString("pymtInstPiSuccessCode");
                String piSuccessDesc = rs.getString("pymtInstPiSuccessDesc");
                String piApprovedId = rs.getString("pymtInstPiApprovedId");
                String piApprovalDate = rs.getString("pymtInstPiApprovalDate");
                String piErrorResp = rs.getString("pymtInstPiErrorResp");
                Object additionalDetails = getAdditionalDetail(rs, "pymtInstAdditionalDetails");

                AuditDetails auditDetails = getAuditDetailsForKey(rs, "pymtInstCreatedBy","pymtInstCreatedTime",
                        "pymtInstLastModifiedBy","pymtInstLastModifiedTime") ;

                paymentInstruction = PaymentInstruction.builder()
                        .id(id)
                        .tenantId(tenantId)
                        .programCode(programCode)
                        .jitBillNo(jitBillNo)
                        .parentPiNumber(parentPiNumber)
                        .muktaReferenceId(muktaReferenceId)
                        .numBeneficiaries(numBeneficiaries)
                        .grossAmount(grossAmount)
                        .netAmount(netAmount)
                        .piStatus(piStatus)
                        .isActive(piIsActive)
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
        String transactionDetailId = rs.getString("transDetailId");
        for(TransactionDetails transactionDetails1 : paymentInstruction.getTransactionDetails()) {
            if(transactionDetails1.getId().equalsIgnoreCase(transactionDetailId)) {
                transactionDetail = transactionDetails1;
                break;
            }
        }

        if(transactionDetail == null) {
            String transactionDetailTenantId = rs.getString("transDetailTenantId");
            String sanctionId = rs.getString("transDetailSanctionId");
            String paymentInstId = rs.getString("transDetailPaymentInstId");
            BigDecimal transactionAmount = rs.getBigDecimal("transDetailTransactionAmount");
            Long transactionDate = rs.getLong("transDetailTransactionDate");
            TransactionType transactionType = TransactionType.fromValue(rs.getString("transDetailTransactionType"));
            Object transactionAdditionalDetails = rs.getObject("transDetailAdditionalDetails");

            AuditDetails auditDetails = getAuditDetailsForKey(rs, "transDetailCreatedBy","transDetailCreatedTime",
                    "transDetailLastModifiedBy","transDetailLastModifiedTime") ;

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
        String jpaId = rs.getString("pymtAdvDtlId");
        PADetails paDetail = null;
        for(PADetails paDetails1 : paymentInstruction.getPaDetails()) {
            if(paDetails1.getId().equalsIgnoreCase(jpaId)) {
                paDetail = paDetails1;
                break;
            }
        }
        if(paDetail == null) {
            String jpaTenantId = rs.getString("pymtAdvDtlTenantId");
            String jpaMuktaReferenceId = rs.getString("pymtAdvDtlMuktaReferenceId");
            String jpaPiId = rs.getString("pymtAdvDtlPiId");
            String jpaPaBillRefNumber = rs.getString("pymtAdvDtlPaBillRefNumber");
            String jpaPaFinYear = rs.getString("pymtAdvDtlPaFinYear");
            String jpaPaAdviceId = rs.getString("pymtAdvDtlPaAdviceId");
            String jpaPaAdviceDate = rs.getString("pymtAdvDtlPaAdviceDate");
            String jpaPaTokenNumber = rs.getString("pymtAdvDtlPaTokenNumber");
            String jpaPaTokenDate = rs.getString("pymtAdvDtlPaTokenDate");
            String jpaPaErrorMsg = rs.getString("pymtAdvDtlPaErrorMsg");
            Object jpaAdditionalDetails = getAdditionalDetail(rs, "pymtAdvDtlAdditionalDetails");

            AuditDetails auditDetails = getAuditDetailsForKey(rs, "pymtAdvDtlCreatedBy","pymtAdvDtlCreatedTime",
                    "pymtAdvDtlLastModifiedBy","pymtAdvDtlLastModifiedTime") ;

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
        String jbdId = rs.getString("benfDetailId");
        Beneficiary beneficiary = null;
        for (Beneficiary beneficiary1 : paymentInstruction.getBeneficiaryDetails()) {
            if(beneficiary1.getId().equalsIgnoreCase(jbdId)) {
                beneficiary = beneficiary1;
                break;
            }
        }

        if(beneficiary == null) {
            String jbdTenantId = rs.getString("benfDetailTenantId");
            String jbdMuktaReferenceId = rs.getString("benfDetailMuktaReferenceId");
            String jbdPiId = rs.getString("benfDetailPiId");
            String jbdBeneficiaryId = rs.getString("benfDetailBeneficiaryId");
            BeneficiaryType jbdBeneficiaryType = BeneficiaryType.fromValue(rs.getString("benfDetailBeneficiaryType"));
            String jbdBeneficiaryNumber = rs.getString("benfDetailBeneficiaryNumber");
            String jbdBankAccountId = rs.getString("benfDetailBankAccountId");
            BigDecimal jbdAmount = rs.getBigDecimal("benfDetailAmount");
            String jbdVoucherNumber = rs.getString("benfDetailVoucherNumber");
            Long jbdVoucherDate = rs.getLong("benfDetailVoucherDate");
            if (jbdVoucherDate.equals(0L))
                jbdVoucherDate = null;
            String jbdUtrNo = rs.getString("benfDetailUtrNo");
            String jbdUtrDate = rs.getString("benfDetailUtrDate");
            String jbdEndToEndId = rs.getString("benfDetailEndToEndId");
            String jbdChallanNumber = rs.getString("benfDetailChallanNumber");
            String jbdChallanDate = rs.getString("benfDetailChallanDate");
            BeneficiaryPaymentStatus jbdPaymentStatus = BeneficiaryPaymentStatus.fromValue(rs.getString("benfDetailPaymentStatus"));
            String jbdPaymentStatusMessage = rs.getString("benfDetailPaymentStatusMessage");
            Object jbdAdditionalDetails = getAdditionalDetail(rs, "benfDetailAdditionalDetails") ;

            AuditDetails auditDetails = getAuditDetailsForKey(rs, "benfDetailCreatedBy","benfDetailCreatedTime",
                    "benfDetailLastModifiedBy","benfDetailLastModifiedTime") ;

            if(StringUtils.isNotEmpty(jbdId) && jbdPiId.equalsIgnoreCase(paymentInstruction.getId())) {
                beneficiary = Beneficiary.builder()
                        .id(jbdId)
                        .tenantId(jbdTenantId)
                        .muktaReferenceId(jbdMuktaReferenceId)
                        .piId(jbdPiId)
                        .beneficiaryId(jbdBeneficiaryId)
                        .beneficiaryType(jbdBeneficiaryType)
                        .beneficiaryNumber(jbdBeneficiaryNumber)
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
        String jblId = rs.getString("benfLineItemId");

        BenfLineItems benfLineItem = null;
        for(BenfLineItems benfLineItems1 : beneficiary.getBenfLineItems()) {
            if(benfLineItems1.getId().equalsIgnoreCase(jblId)) {
                benfLineItem = benfLineItems1;
                break;
            }
        }

        if(benfLineItem == null) {
            String jblBeneficiaryId = rs.getString("benfLineItemBeneficiaryId");
            String jblLineItemId = rs.getString("benfLineItemLineItemId");

            AuditDetails auditDetails = getAuditDetailsForKey(rs, "benfLineItemCreatedBy","benfLineItemCreatedTime",
                    "benfLineItemLastModifiedBy","benfLineItemLastModifiedTime") ;

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
