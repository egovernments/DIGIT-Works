package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.repository.querybuilder.PIQueryBuilder;
import org.egov.repository.rowmapper.PIRowMapper;
import org.egov.utils.HelperUtil;
import org.egov.web.models.Pagination;
import org.egov.web.models.enums.PIStatus;
import org.egov.web.models.enums.PaymentStatus;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.egov.repository.querybuilder.PIQueryBuilder.*;

@Repository
@Slf4j
public class PIRepository {
    @Autowired
    HelperUtil util;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    private PIRowMapper piRowMapper;

    @Autowired
    private PIQueryBuilder piQueryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    SanctionDetailsRepository sanctionDetailsRepository;
    @Transactional
    public void save(List<PaymentInstruction> piRequests, FundsSummary fundsSummary, PaymentStatus paymentStatus) {

        log.info("PIRepository save");
        List<MapSqlParameterSource> piRequestSqlParameterSources = getSqlParameterListForPIRequest(piRequests);
        List<MapSqlParameterSource> paDetailsSqlParameterSources = getSqlParameterListForPADetails(piRequests);
        List<MapSqlParameterSource> transactionSqlParameterSources = getSqlParameterListForTransactionDetails(piRequests);
        List<MapSqlParameterSource> beneficiarySqlParameterSources = getSqlParameterListForBenefDetails(piRequests);
        List<MapSqlParameterSource> beneficiaryLineItemSqlParameterSources = getSqlParameterListForBeneficiaryLineItem(piRequests);

        namedJdbcTemplate.batchUpdate(PIQueryBuilder.PAYMENT_INSTRUCTION_INSERT_QUERY, piRequestSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        namedJdbcTemplate.batchUpdate(PIQueryBuilder.PAYMENT_ADVICE_DETAILS_INSERT_QUERY, paDetailsSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        namedJdbcTemplate.batchUpdate(PIQueryBuilder.BENEFICIARY_DETAILS_INSERT_QUERY, beneficiarySqlParameterSources.toArray(new MapSqlParameterSource[0]));
        namedJdbcTemplate.batchUpdate(PIQueryBuilder.BENEFICIARY_LINEITEM_INSERT_QUERY, beneficiaryLineItemSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        if (!transactionSqlParameterSources.isEmpty()) {
            namedJdbcTemplate.batchUpdate(PIQueryBuilder.TRANSACTION_DETAILS_INSERT_QUERY, transactionSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        }
        
        if (paymentStatus.equals(PaymentStatus.INITIATED) && fundsSummary != null) {
            sanctionDetailsRepository.updateFundsSummary(Collections.singletonList(fundsSummary));
        }
    }
    private List<MapSqlParameterSource> getSqlParameterListForPIRequest(List<PaymentInstruction> piRequests) {

        List<MapSqlParameterSource> piDetailParamMapList = new ArrayList<>();
        for (PaymentInstruction piRequest: piRequests) {

            MapSqlParameterSource piRequestParamMap = new MapSqlParameterSource();
            piRequestParamMap.addValue("id", piRequest.getId());
            piRequestParamMap.addValue("tenantId", piRequest.getTenantId());
            piRequestParamMap.addValue("programCode",piRequest.getProgramCode());
            piRequestParamMap.addValue("piNumber", piRequest.getJitBillNo());
            piRequestParamMap.addValue("parentPiNumber", piRequest.getParentPiNumber());
            piRequestParamMap.addValue("muktaReferenceId", piRequest.getMuktaReferenceId());
            piRequestParamMap.addValue("numBeneficiaries", piRequest.getNumBeneficiaries());
            piRequestParamMap.addValue("grossAmount", piRequest.getGrossAmount());
            piRequestParamMap.addValue("netAmount", piRequest.getNetAmount());
            piRequestParamMap.addValue("piStatus", piRequest.getPiStatus().toString());
            piRequestParamMap.addValue("isActive", piRequest.getIsActive());
            piRequestParamMap.addValue("piSuccessCode", piRequest.getPiSuccessCode());
            piRequestParamMap.addValue("piSuccessDesc", piRequest.getPiSuccessDesc());
            piRequestParamMap.addValue("piApprovedId", piRequest.getPiApprovedId());
            piRequestParamMap.addValue("piApprovalDate", piRequest.getPiApprovalDate());
            piRequestParamMap.addValue("piErrorResp", piRequest.getPiErrorResp());
            piRequestParamMap.addValue("additionalDetails", util.getPGObject(piRequest.getAdditionalDetails()));
            piRequestParamMap.addValue("createdby", piRequest.getAuditDetails().getCreatedBy());
            piRequestParamMap.addValue("createdtime", piRequest.getAuditDetails().getCreatedTime());
            piRequestParamMap.addValue("lastmodifiedby", piRequest.getAuditDetails().getLastModifiedBy());
            piRequestParamMap.addValue("lastmodifiedtime", piRequest.getAuditDetails().getLastModifiedTime());
            piDetailParamMapList.add(piRequestParamMap);
        }
        return piDetailParamMapList;
    }

    private List<MapSqlParameterSource> getSqlParameterListForPADetails(List<PaymentInstruction> piRequests) {

        List<MapSqlParameterSource> paDetailParamMapList = new ArrayList<>();
        for (PaymentInstruction piRequest: piRequests) {
            for (PADetails paDetails: piRequest.getPaDetails()) {
                MapSqlParameterSource paRequestParamMap = new MapSqlParameterSource();
                paRequestParamMap.addValue("id", paDetails.getId());
                paRequestParamMap.addValue("tenantId", paDetails.getTenantId());
                paRequestParamMap.addValue("muktaReferenceId", paDetails.getMuktaReferenceId());
                paRequestParamMap.addValue("piId", paDetails.getPiId());
                paRequestParamMap.addValue("paBillRefNumber", paDetails.getPaBillRefNumber());
                paRequestParamMap.addValue("paFinYear", paDetails.getPaFinYear());
                paRequestParamMap.addValue("paAdviceId", paDetails.getPaAdviceId());
                paRequestParamMap.addValue("paAdviceDate", paDetails.getPaAdviceDate());
                paRequestParamMap.addValue("paTokenNumber", paDetails.getPaTokenNumber());
                paRequestParamMap.addValue("paTokenDate", paDetails.getPaTokenDate());
                paRequestParamMap.addValue("paErrorMsg", paDetails.getPaErrorMsg());
                paRequestParamMap.addValue("additionalDetails", util.getPGObject(paDetails.getAdditionalDetails()));
                paRequestParamMap.addValue("createdby", paDetails.getAuditDetails().getCreatedBy());
                paRequestParamMap.addValue("createdtime", paDetails.getAuditDetails().getCreatedTime());
                paRequestParamMap.addValue("lastmodifiedby", paDetails.getAuditDetails().getLastModifiedBy());
                paRequestParamMap.addValue("lastmodifiedtime", paDetails.getAuditDetails().getLastModifiedTime());
                paDetailParamMapList.add(paRequestParamMap);
            }
        }
        return paDetailParamMapList;
    }

    private List<MapSqlParameterSource> getSqlParameterListForTransactionDetails(List<PaymentInstruction> piRequests) {

        List<MapSqlParameterSource> transactionDetailParamMapList = new ArrayList<>();
        for (PaymentInstruction piRequest: piRequests) {
            if (piRequest.getTransactionDetails() != null && !piRequest.getTransactionDetails().isEmpty()) {
                for (TransactionDetails transactionDetails: piRequest.getTransactionDetails()) {
                    MapSqlParameterSource transactionRequestParamMap = getTransactionFieldsMap(transactionDetails);
                    transactionDetailParamMapList.add(transactionRequestParamMap);
                }
            }

        }
        return transactionDetailParamMapList;
    }

    private MapSqlParameterSource getTransactionFieldsMap(TransactionDetails transactionDetails) {
        MapSqlParameterSource transactionRequestParamMap = new MapSqlParameterSource();
        transactionRequestParamMap.addValue("id", transactionDetails.getId());
        transactionRequestParamMap.addValue("tenantId", transactionDetails.getTenantId());
        transactionRequestParamMap.addValue("sanctionId", transactionDetails.getSanctionId());
        transactionRequestParamMap.addValue("paymentInstId", transactionDetails.getPaymentInstId());
        transactionRequestParamMap.addValue("transactionAmount", transactionDetails.getTransactionAmount());
        transactionRequestParamMap.addValue("transactionDate", transactionDetails.getTransactionDate());
        transactionRequestParamMap.addValue("transactionType", transactionDetails.getTransactionType().toString());
        transactionRequestParamMap.addValue("additionalDetails", util.getPGObject(transactionDetails.getAdditionalDetails()));
        transactionRequestParamMap.addValue("createdby", transactionDetails.getAuditDetails().getCreatedBy());
        transactionRequestParamMap.addValue("createdtime", transactionDetails.getAuditDetails().getCreatedTime());
        transactionRequestParamMap.addValue("lastmodifiedby", transactionDetails.getAuditDetails().getLastModifiedBy());
        transactionRequestParamMap.addValue("lastmodifiedtime", transactionDetails.getAuditDetails().getLastModifiedTime());
        return transactionRequestParamMap;
    }

    private List<MapSqlParameterSource> getSqlParameterListForBenefDetails(List<PaymentInstruction> piRequests) {

        List<MapSqlParameterSource> beneficiaryParamMapList = new ArrayList<>();
        for (PaymentInstruction piRequest: piRequests) {
            for (Beneficiary beneficiary: piRequest.getBeneficiaryDetails()) {
                MapSqlParameterSource beneficiaryParamMap = new MapSqlParameterSource();
                beneficiaryParamMap.addValue("id", beneficiary.getId());
                beneficiaryParamMap.addValue("tenantId", beneficiary.getTenantId());
                beneficiaryParamMap.addValue("muktaReferenceId", beneficiary.getMuktaReferenceId());
                beneficiaryParamMap.addValue("piId", beneficiary.getPiId());
                beneficiaryParamMap.addValue("beneficiaryId", beneficiary.getBeneficiaryId());
                beneficiaryParamMap.addValue("beneficiaryType", beneficiary.getBeneficiaryType().toString());
                beneficiaryParamMap.addValue("beneficiaryNumber", beneficiary.getBeneficiaryNumber());
                beneficiaryParamMap.addValue("bankAccountCode", beneficiary.getBankAccountId());
                beneficiaryParamMap.addValue("amount", beneficiary.getAmount());
                beneficiaryParamMap.addValue("voucherNumber", beneficiary.getVoucherNumber());
                beneficiaryParamMap.addValue("voucherDate", beneficiary.getVoucherDate());
                beneficiaryParamMap.addValue("utrNo", beneficiary.getUtrNo());
                beneficiaryParamMap.addValue("utrDate", beneficiary.getUtrDate());
                beneficiaryParamMap.addValue("endToEndId", beneficiary.getEndToEndId());
                beneficiaryParamMap.addValue("challanNumber", beneficiary.getChallanNumber());
                beneficiaryParamMap.addValue("challanDate", beneficiary.getChallanDate());
                beneficiaryParamMap.addValue("paymentStatus", beneficiary.getPaymentStatus().toString());
                beneficiaryParamMap.addValue("paymentStatusMessage", beneficiary.getPaymentStatusMessage());
                beneficiaryParamMap.addValue("additionalDetails", util.getPGObject(beneficiary.getAdditionalDetails()));
                beneficiaryParamMap.addValue("createdby", beneficiary.getAuditDetails().getCreatedBy());
                beneficiaryParamMap.addValue("createdtime", beneficiary.getAuditDetails().getCreatedTime());
                beneficiaryParamMap.addValue("lastmodifiedby", beneficiary.getAuditDetails().getLastModifiedBy());
                beneficiaryParamMap.addValue("lastmodifiedtime", beneficiary.getAuditDetails().getLastModifiedTime());
                beneficiaryParamMapList.add(beneficiaryParamMap);
            }
        }
        return beneficiaryParamMapList;
    }

    private List<MapSqlParameterSource> getSqlParameterListForBeneficiaryLineItem(List<PaymentInstruction> piRequests) {

        List<MapSqlParameterSource> benfLineItemsParamMapList = new ArrayList<>();
        for (PaymentInstruction piRequest: piRequests) {
            for (Beneficiary beneficiary: piRequest.getBeneficiaryDetails()) {
                for (BenfLineItems benfLineItems: beneficiary.getBenfLineItems()) {
                    MapSqlParameterSource benfLineItemsParamMap = new MapSqlParameterSource();
                    benfLineItemsParamMap.addValue("id", benfLineItems.getId());
                    benfLineItemsParamMap.addValue("beneficiaryId", benfLineItems.getBeneficiaryId());
                    benfLineItemsParamMap.addValue("lineItemId", benfLineItems.getLineItemId());
                    benfLineItemsParamMap.addValue("createdby", benfLineItems.getAuditDetails().getCreatedBy());
                    benfLineItemsParamMap.addValue("createdtime", benfLineItems.getAuditDetails().getCreatedTime());
                    benfLineItemsParamMap.addValue("lastmodifiedby", benfLineItems.getAuditDetails().getLastModifiedBy());
                    benfLineItemsParamMap.addValue("lastmodifiedtime", benfLineItems.getAuditDetails().getLastModifiedTime());
                    benfLineItemsParamMapList.add(benfLineItemsParamMap);
                }
            }
        }
        return benfLineItemsParamMapList;
    }

    public List<PaymentInstruction> searchPi (PISearchCriteria piSearchCriteria){
        List<Object> preparedStmtList = new ArrayList<>();
        String query = piQueryBuilder.getPaymentInstructionSearchQuery(piSearchCriteria, preparedStmtList);
        return jdbcTemplate.query(query, piRowMapper, preparedStmtList.toArray());
    }

    @Transactional
    public void update(List<PaymentInstruction> piRequests, FundsSummary fundsSummary) {

        log.info("PIRepository update, the request object : " + piRequests);
        List<MapSqlParameterSource> piRequestSqlParameterSources = getSqlParameterListForPIUpdateRequest(piRequests);
        List<MapSqlParameterSource> paDetailsSqlParameterSources = getSqlParameterListForPAUpdateDetails(piRequests);
        List<MapSqlParameterSource> beneficiarySqlParameterSources = getSqlParameterListForBenefUpdateDetails(piRequests);

        namedJdbcTemplate.batchUpdate(PIQueryBuilder.PAYMENT_INSTRUCTION_UPDATE_QUERY, piRequestSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        namedJdbcTemplate.batchUpdate(PIQueryBuilder.PAYMENT_ADVICE_DETAILS_UPDATE_QUERY, paDetailsSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        namedJdbcTemplate.batchUpdate(PIQueryBuilder.BENEFICIARY_DETAILS_UPDATE_QUERY, beneficiarySqlParameterSources.toArray(new MapSqlParameterSource[0]));

        if (fundsSummary != null) {
            sanctionDetailsRepository.updateFundsSummary(Collections.singletonList(fundsSummary));
        }
        List<MapSqlParameterSource> transactionDetailParamMapList = new ArrayList<>();

        for (PaymentInstruction pi: piRequests) {
            for (TransactionDetails tr: pi.getTransactionDetails()) {
                if (tr.getId() == null) {
                    tr.setId(UUID.randomUUID().toString());
                    MapSqlParameterSource transactionRequestParamMap = getTransactionFieldsMap(tr);
                    transactionDetailParamMapList.add(transactionRequestParamMap);
                }
            }
        }
        if (!transactionDetailParamMapList.isEmpty()) {
            namedJdbcTemplate.batchUpdate(PIQueryBuilder.TRANSACTION_DETAILS_INSERT_QUERY, transactionDetailParamMapList.toArray(new MapSqlParameterSource[0]));
        }

    }

    private List<MapSqlParameterSource> getSqlParameterListForPIUpdateRequest(List<PaymentInstruction> piRequests) {

        List<MapSqlParameterSource> piDetailParamMapList = new ArrayList<>();
        for (PaymentInstruction piRequest: piRequests) {

            MapSqlParameterSource piRequestParamMap = new MapSqlParameterSource();
            piRequestParamMap.addValue("id", piRequest.getId());
            piRequestParamMap.addValue("piStatus", piRequest.getPiStatus().toString());
            piRequestParamMap.addValue("piSuccessCode", piRequest.getPiSuccessCode());
            piRequestParamMap.addValue("piSuccessDesc", piRequest.getPiSuccessDesc());
            piRequestParamMap.addValue("piApprovedId", piRequest.getPiApprovedId());
            piRequestParamMap.addValue("piApprovalDate", piRequest.getPiApprovalDate());
            piRequestParamMap.addValue("piErrorResp", piRequest.getPiErrorResp());
            piRequestParamMap.addValue("additionalDetails", util.getPGObject(piRequest.getAdditionalDetails()));
            piRequestParamMap.addValue("lastmodifiedby", piRequest.getAuditDetails().getLastModifiedBy());
            piRequestParamMap.addValue("lastmodifiedtime", piRequest.getAuditDetails().getLastModifiedTime());
            piDetailParamMapList.add(piRequestParamMap);
        }
        return piDetailParamMapList;
    }

    private List<MapSqlParameterSource> getSqlParameterListForPAUpdateDetails(List<PaymentInstruction> piRequests) {

        List<MapSqlParameterSource> paDetailParamMapList = new ArrayList<>();
        for (PaymentInstruction piRequest: piRequests) {
            for (PADetails paDetails: piRequest.getPaDetails()) {
                MapSqlParameterSource paRequestParamMap = new MapSqlParameterSource();
                paRequestParamMap.addValue("id", paDetails.getId());
                paRequestParamMap.addValue("paBillRefNumber", paDetails.getPaBillRefNumber());
                paRequestParamMap.addValue("paFinYear", paDetails.getPaFinYear());
                paRequestParamMap.addValue("paAdviceId", paDetails.getPaAdviceId());
                paRequestParamMap.addValue("paAdviceDate", paDetails.getPaAdviceDate());
                paRequestParamMap.addValue("paTokenNumber", paDetails.getPaTokenNumber());
                paRequestParamMap.addValue("paTokenDate", paDetails.getPaTokenDate());
                paRequestParamMap.addValue("paErrorMsg", paDetails.getPaErrorMsg());
                paRequestParamMap.addValue("additionalDetails", util.getPGObject(paDetails.getAdditionalDetails()));
                paRequestParamMap.addValue("lastmodifiedby", paDetails.getAuditDetails().getLastModifiedBy());
                paRequestParamMap.addValue("lastmodifiedtime", paDetails.getAuditDetails().getLastModifiedTime());
                paDetailParamMapList.add(paRequestParamMap);
            }
        }
        return paDetailParamMapList;
    }

    private List<MapSqlParameterSource> getSqlParameterListForBenefUpdateDetails(List<PaymentInstruction> piRequests) {

        List<MapSqlParameterSource> beneficiaryParamMapList = new ArrayList<>();
        for (PaymentInstruction piRequest: piRequests) {
            for (Beneficiary beneficiary: piRequest.getBeneficiaryDetails()) {
                MapSqlParameterSource beneficiaryParamMap = new MapSqlParameterSource();
                beneficiaryParamMap.addValue("id", beneficiary.getId());
                beneficiaryParamMap.addValue("voucherNumber", beneficiary.getVoucherNumber());
                beneficiaryParamMap.addValue("voucherDate", beneficiary.getVoucherDate());
                beneficiaryParamMap.addValue("utrNo", beneficiary.getUtrNo());
                beneficiaryParamMap.addValue("utrDate", beneficiary.getUtrDate());
                beneficiaryParamMap.addValue("endToEndId", beneficiary.getEndToEndId());
                beneficiaryParamMap.addValue("challanNumber", beneficiary.getChallanNumber());
                beneficiaryParamMap.addValue("challanDate", beneficiary.getChallanDate());
                beneficiaryParamMap.addValue("paymentStatus", beneficiary.getPaymentStatus().toString());
                beneficiaryParamMap.addValue("paymentStatusMessage", beneficiary.getPaymentStatusMessage());
                beneficiaryParamMap.addValue("additionalDetails", util.getPGObject(beneficiary.getAdditionalDetails()));
                beneficiaryParamMap.addValue("lastmodifiedby", beneficiary.getAuditDetails().getLastModifiedBy());
                beneficiaryParamMap.addValue("lastmodifiedtime", beneficiary.getAuditDetails().getLastModifiedTime());
                beneficiaryParamMapList.add(beneficiaryParamMap);
            }
        }
        return beneficiaryParamMapList;
    }
}
