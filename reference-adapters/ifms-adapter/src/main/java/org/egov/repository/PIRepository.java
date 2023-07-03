package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.repository.querybuilder.PIQueryBuilder;
import org.egov.repository.rowmapper.PIRowMapper;
import org.egov.utils.HelperUtil;
import org.egov.web.models.bill.PISearchCriteria;
import org.egov.web.models.bill.PISearchRequest;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class PIRepository {
    @Autowired
    HelperUtil util;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    private PIQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PIRowMapper rowMapper;

    @Transactional
    public void save(List<PaymentInstruction> piRequests) {

        log.debug("PIRepository save, the request object : " + piRequests);
        List<MapSqlParameterSource> piRequestSqlParameterSources = getSqlParameterListForPIRequest(piRequests);
        List<MapSqlParameterSource> paDetailsSqlParameterSources = getSqlParameterListForPADetails(piRequests);
        List<MapSqlParameterSource> transactionSqlParameterSources = getSqlParameterListForTransactionDetails(piRequests);
        List<MapSqlParameterSource> beneficiarySqlParameterSources = getSqlParameterListForBenefDetails(piRequests);
        List<MapSqlParameterSource> beneficiaryLineItemSqlParameterSources = getSqlParameterListForBeneficiaryLineItem(piRequests);

        namedJdbcTemplate.batchUpdate(PIQueryBuilder.PAYMENT_INSTRUCTION_INSERT_QUERY, piRequestSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        namedJdbcTemplate.batchUpdate(PIQueryBuilder.PAYMENT_ADVICE_DETAILS_INSERT_QUERY, paDetailsSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        namedJdbcTemplate.batchUpdate(PIQueryBuilder.BENEFICIARY_DETAILS_INSERT_QUERY, beneficiarySqlParameterSources.toArray(new MapSqlParameterSource[0]));
        namedJdbcTemplate.batchUpdate(PIQueryBuilder.TRANSACTION_DETAILS_INSERT_QUERY, transactionSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        namedJdbcTemplate.batchUpdate(PIQueryBuilder.BENEFICIARY_LINEITEM_INSERT_QUERY, beneficiaryLineItemSqlParameterSources.toArray(new MapSqlParameterSource[0]));
    }

    private List<MapSqlParameterSource> getSqlParameterListForPIRequest(List<PaymentInstruction> piRequests) {

        List<MapSqlParameterSource> piDetailParamMapList = new ArrayList<>();
        for (PaymentInstruction piRequest: piRequests) {

            MapSqlParameterSource piRequestParamMap = new MapSqlParameterSource();
            piRequestParamMap.addValue("id", piRequest.getId());
            piRequestParamMap.addValue("tenantId", piRequest.getTenantId());
            piRequestParamMap.addValue("piNumber", piRequest.getJitBillNo());
            piRequestParamMap.addValue("parentPiNumber", piRequest.getParentPiNumber());
            piRequestParamMap.addValue("muktaReferenceId", piRequest.getMuktaReferenceId());
            piRequestParamMap.addValue("numBeneficiaries", piRequest.getNumBeneficiaries());
            piRequestParamMap.addValue("grossAmount", piRequest.getGrossAmount());
            piRequestParamMap.addValue("netAmount", piRequest.getNetAmount());
            piRequestParamMap.addValue("piStatus", piRequest.getPiStatus());
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
            for (TransactionDetails transactionDetails: piRequest.getTransactionDetails()) {
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
                transactionDetailParamMapList.add(transactionRequestParamMap);
            }
        }
        return transactionDetailParamMapList;
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
        for (PaymentInstruction piRequest : piRequests) {
            for (Beneficiary beneficiary : piRequest.getBeneficiaryDetails()) {
                for (BenfLineItems benfLineItems : beneficiary.getBenfLineItems()) {
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
        public List<PaymentInstruction> getPaymentInstructions(PISearchRequest piSearchRequest){
            log.info("PIRepository::getPaymentInstructions");
            PISearchCriteria criteria = piSearchRequest.getSearchCriteria();
            List<Object> preparedStmtList = new ArrayList<>();
            log.info("Fetching Payment instruction. tenantId ["+criteria.getTenantId()+"]");
            String query = queryBuilder.getPaymentInstructionSearchQuery(criteria, preparedStmtList);
            log.info("Query build successfully. tenantId ["+criteria.getTenantId()+"]");
            List<PaymentInstruction> paymentInstructionList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
            log.info("Fetched Payment instruction. tenantId ["+criteria.getTenantId()+"]");
            return paymentInstructionList;
    }
}
