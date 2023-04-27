package org.egov.digit.expense.calculator.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.*;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;

@Slf4j
@Component
public class PurchaseBillGeneratorService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExpenseCalculatorConfiguration configs;
    
    @Autowired
    private IdgenUtil idgenUtil;
    
    @Autowired
    private MdmsUtils mdmsUtil;

    @Autowired
    private ContractUtils contractUtils;

    @Autowired
    private ExpenseCalculatorConfiguration config;

    public Bill createPurchaseBill(RequestInfo requestInfo,PurchaseBill providedPurchaseBill,List<Payer> payers , List<HeadCode> headCodes , List<ApplicableCharge> applicableCharges,Map<String, String> metaInfo) {
        // Get TenantId
        String tenantId = providedPurchaseBill.getTenantId();
        // Get documents
        List<Document> documents = providedPurchaseBill.getDocuments();
        // Get contract number
        String contractNumber = providedPurchaseBill.getContractNumber();
        //Generate referenceId
        String referenceId = generateReferenceId(requestInfo,providedPurchaseBill, contractNumber);
        // Initialize net payable amount
        BigDecimal netPayableAmount = BigDecimal.ZERO;
        // Get provided billDetails
        List<BillDetail> billDetails = providedPurchaseBill.getBillDetails();

        // Fetch contract for provided contract number
        Contract contract = getContract(requestInfo, tenantId, contractNumber);
        // Put contact project mapping in meta
        metaInfo.put(PROJECT_ID_OF_CONSTANT+contractNumber,providedPurchaseBill.getProjectId());
        // Put OrgId in meta
        metaInfo.put(ORG_ID_CONSTANT,contract.getOrgId());
        // For each billDetails calculate different amount
        for(BillDetail billDetail : billDetails) {
            // Calculate payable line items
            calculateAndSetPayableLineItems(billDetail, headCodes,applicableCharges);
            // Calculate net line item amount
            calculateAndSetNetLineItemAmount(billDetail);
            // Calculate payable amount
            netPayableAmount = netPayableAmount.add(billDetail.getNetLineItemAmount());
        }
        // Build payer
        Party payer = buildParty(requestInfo, configs.getPayerType(), tenantId);
        // Populate additional details object with documents
        populateBillAdditionalDetails(providedPurchaseBill,DOCUMENTS_CONSTANT, documents);
        // Generate the bill
        Bill purchaseBill = Bill.builder()
                                .tenantId(tenantId)
                                .billDate(providedPurchaseBill.getInvoiceDate())
                                .referenceId(referenceId)
                                .businessService(configs.getPurchaseBusinessService())
                                .fromPeriod(contract.getStartDate().longValue())
                                .toPeriod(contract.getEndDate().longValue())
                                .status(providedPurchaseBill.getStatus())
                                .netPayableAmount(netPayableAmount)
                                .paymentStatus("PENDING")
                                .payer(payer)
                                .billDetails(providedPurchaseBill.getBillDetails())
                                .additionalDetails(providedPurchaseBill.getAdditionalDetails())
                                .build();
        return purchaseBill;
    }

    private void calculateAndSetNetLineItemAmount(BillDetail billDetail) {
        List<LineItem> payableLineItems = billDetail.getPayableLineItems();
        BigDecimal netLineItemAmount = BigDecimal.ZERO;
        for(LineItem lineItem : payableLineItems) {
            netLineItemAmount = netLineItemAmount.add(lineItem.getAmount());
        }
        billDetail.setNetLineItemAmount(netLineItemAmount);
    }

    private void calculateAndSetPayableLineItems(BillDetail billDetail, List<HeadCode> headCodes, List<ApplicableCharge> applicableCharges) {
        List<LineItem> lineItems = billDetail.getLineItems();
        String tenantId = billDetail.getTenantId();
        BigDecimal expense = BigDecimal.ZERO;
        BigDecimal deduction = BigDecimal.ZERO;
        // Calculate total expense
        for(LineItem lineItem :lineItems) {
            String headCode = lineItem.getHeadCode();
            BigDecimal amount = lineItem.getAmount();
            String category = getHeadCodeCategory(headCode,headCodes);
            if(category != null && category.equalsIgnoreCase(EXPENSE_CONSTANT)) {
                expense = expense.add(amount);
            }
        }
        // Calculate total deduction on top of expense
        for(LineItem lineItem :lineItems) {
            String headCode = lineItem.getHeadCode();
            String category = getHeadCodeCategory(headCode,headCodes);
            if(category != null && category.equalsIgnoreCase(DEDUCTION_CONSTANT)) {
                String calculationType = getCalculationType(headCode,applicableCharges);
                String value = getDeductionValue(headCode,applicableCharges);
                if(calculationType.equalsIgnoreCase(PERCENTAGE_CONSTANT)) {
                    deduction = deduction.add(expense.multiply(new BigDecimal(value)).divide(new BigDecimal(100)));
                } else if (calculationType.equalsIgnoreCase(LUMPSUM_CONSTANT)) {
                    deduction = deduction.add(new BigDecimal(value));
                } else {
                    log.error("INVALID_HEADCODE_CALCULATION_TYPE", "Head Code calculation type [" + calculationType +"] is not supported");
                    throw new CustomException("INVALID_HEADCODE_CALCULATION_TYPE", "Head Code calculation type [" + calculationType +"] is not supported");
                }
            }
        }
        billDetail.addPayableLineItems(buildPayableLineItem(expense.subtract(deduction),tenantId,"PURCHASE"));
        billDetail.addPayableLineItems(buildPayableLineItem(deduction,tenantId,"PURCHASE"));
    }

    private LineItem buildPayableLineItem(BigDecimal amount, String tenantId, String headCode) {
       return LineItem.builder()
                .amount(amount)
                .paidAmount(BigDecimal.ZERO)
                .tenantId(tenantId)
                .isLineItemPayable(true)
                .type(LineItem.TypeEnum.PAYABLE)
                .headCode(headCode)
                .build();
    }

    private void populateBillAdditionalDetails(PurchaseBill bill, String key , Object value) {
        Object additionalDetails = bill.getAdditionalDetails();
        try {
            JsonNode node = mapper.readTree(mapper.writeValueAsString(additionalDetails));
            JsonNode nodeValue = mapper.readTree(mapper.writeValueAsString(value));
            ((ObjectNode)node).put(key,nodeValue);
            bill.setAdditionalDetails(mapper.readValue(node.toString(), Object.class));
        }
        catch (Exception e){
            log.error("Error while parsing additionalDetails object.");
            throw new CustomException("PARSE_ERROR","Error while parsing additionalDetails object.");
        }
    }

    private Contract getContract(RequestInfo requestInfo, String tenantId, String referenceId) {
        ContractResponse contractResponse = contractUtils.fetchContract(requestInfo, tenantId, referenceId);
        Contract contract = contractResponse.getContracts().get(0);
        return contract;
    }

    private Party buildParty(RequestInfo requestInfo, String type, String tenantId) {
        String rootTenantId = tenantId.split("\\.")[0];
        Object mdmsResp = mdmsUtil.getPayersForTypeFromMDMS(requestInfo, type, rootTenantId);
        List<Object> payerList = commonUtil.readJSONPathValue(mdmsResp,JSON_PATH_FOR_PAYER);
        for(Object obj : payerList){
            Payer payer = mapper.convertValue(obj, Payer.class);
            if(tenantId.equals(payer.getTenantId())) {
                return buildParty(payer.getId(),payer.getCode(),tenantId);
            }
        }
        log.error("PAYER_MISSING_IN_MDMS","Payer is missing in MDMS for type : "+type + " and tenantId : "+tenantId);
        throw new CustomException("PAYER_MISSING_IN_MDMS","Payer is missing in MDMS for type : "+type + " and tenantId : "+tenantId);
    }

    private Party buildParty(String individualId, String type, String tenantId) {
        return Party.builder()
                .identifier(individualId)
                .type(type)
                .tenantId(tenantId)
                .status("STATUS")
                .build();
    }

    private String getDeductionValue(String headCode, List<ApplicableCharge> applicableCharges) {
        for(ApplicableCharge applicableCharge : applicableCharges) {
            if(applicableCharge.getCode().equalsIgnoreCase(headCode) && applicableCharge.getService().equalsIgnoreCase(config.getPurchaseBusinessService())){
                return applicableCharge.getValue();
            }
        }
        log.error("INVALID_HEAD_CODE","");
        throw new CustomException("INVALID_HEAD_CODE","");
    }

    private String getCalculationType(String headCode, List<ApplicableCharge> applicableCharges) {
        for(ApplicableCharge applicableCharge : applicableCharges) {
            if(applicableCharge.getCode().equalsIgnoreCase(headCode) && applicableCharge.getService().equalsIgnoreCase(config.getPurchaseBusinessService())){
                String calculationType = applicableCharge.getCalculationType();
                if (StringUtils.isBlank(calculationType)) {
                    log.error("CALCULATION_TYPE_MISSING","MDMS::calculationType missing for head code [" + headCode +"] and service ["+config.getPurchaseBusinessService()+"]");
                    throw new CustomException("CALCULATION_TYPE_MISSING","MDMS::calculationType missing for head code [" + headCode +"] and service ["+config.getPurchaseBusinessService()+"]");
                } else {
                    return calculationType;
                }
            }
        }
        log.error("INVALID_HEAD_CODE","Invalid head code [" + headCode +"] for service ["+config.getPurchaseBusinessService()+"]");
        throw new CustomException("INVALID_HEAD_CODE","Invalid head code [" + headCode +"] for service ["+config.getPurchaseBusinessService()+"]");
    }

    private String getHeadCodeCategory(String headCode, List<HeadCode> headCodes) {
        for(HeadCode hCode : headCodes) {
            if(hCode.getCode().equalsIgnoreCase(headCode) && hCode.getService().equalsIgnoreCase(config.getPurchaseBusinessService())){
                String category = hCode.getCategory();
                if (StringUtils.isBlank(category)) {
                    log.error("CATEGORY_MISSING","MDMS::category missing for head code [" + headCode +"] and service ["+config.getPurchaseBusinessService()+"]");
                    throw new CustomException("CATEGORY_MISSING","MDMS::category missing for head code [" + headCode +"] and service ["+config.getPurchaseBusinessService()+"]");
                } else {
                    return category;
                }
            }
        }
        log.error("INVALID_HEAD_CODE","Invalid head code [" + headCode +"] for service ["+config.getPurchaseBusinessService()+"]");
        throw new CustomException("INVALID_HEAD_CODE","Invalid head code [" + headCode +"] for service ["+config.getPurchaseBusinessService()+"]");
    }

    
    /**
     * Generates the referenceId to be set on the Bill object. This referenceId is a combination of the
     * contract number and a unique ID generated from MDMS format template. 
     * Example: CON123_PB_123
     * @param request
     * @param contractNumber
     * @return
     */
    private String generateReferenceId(RequestInfo requestInfo,PurchaseBill bill, String contractNumber) {
         String rootTenantId = bill.getTenantId().split("\\.")[0];
         List<String> idList = idgenUtil.getIdList(requestInfo, rootTenantId, configs.getPurchaseBillReferenceIdFormatKey(), "", 1);
         String generatedUniqueId = idList.get(0);
         String referenceId = contractNumber + "_" + generatedUniqueId;
         log.info("ReferenceId generated. Generated reference ID is ["+ referenceId + "]");
         return referenceId;
    }
}
