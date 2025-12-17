package org.egov.digit.expense.calculator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.*;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.contract.Contract;
import org.egov.works.services.common.models.contract.ContractResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;

@Slf4j
@Component
public class PurchaseBillGeneratorService {

    private final ObjectMapper mapper;

    private final CommonUtil commonUtil;

    private final ExpenseCalculatorConfiguration configs;
    
    private final IdgenUtil idgenUtil;
    
    private final MdmsUtils mdmsUtil;

    private final ContractUtils contractUtils;

    private final ExpenseCalculatorConfiguration config;
    private static final String SERVICE_PLACEHOLDER = "] and service [";
    private static final String INVALID_HEAD_CODE = "INVALID_HEAD_CODE";
    private static final String INVALID_HEAD_CODE_PLACEHOLDER = "Invalid head code [";
    private static final String FOR_SERVICE_PLACEHOLDER = "] for service [";
    private static final String CATEGORY_MISSING = "CATEGORY_MISSING";


    @Autowired
    public PurchaseBillGeneratorService(ObjectMapper mapper, CommonUtil commonUtil, ExpenseCalculatorConfiguration configs, IdgenUtil idgenUtil, MdmsUtils mdmsUtil, ContractUtils contractUtils, ExpenseCalculatorConfiguration config) {
        this.mapper = mapper;
        this.commonUtil = commonUtil;
        this.configs = configs;
        this.idgenUtil = idgenUtil;
        this.mdmsUtil = mdmsUtil;
        this.contractUtils = contractUtils;
        this.config = config;
    }

    public Bill createPurchaseBill(RequestInfo requestInfo,PurchaseBill providedPurchaseBill , List<HeadCode> headCodes , List<ApplicableCharge> applicableCharges,Map<String, String> metaInfo) {
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
        log.info("Create purchase bill for referenceId ["+referenceId+"]");
        Bill purchaseBill = Bill.builder()
                                .tenantId(tenantId)
                                .billDate(providedPurchaseBill.getInvoiceDate())
                                .referenceId(referenceId)
                                .businessService(configs.getPurchaseBusinessService())
                                .fromPeriod(contract.getStartDate().longValue())
                                .toPeriod(contract.getEndDate().longValue())
                                .status(providedPurchaseBill.getStatus())
                                .totalAmount(netPayableAmount)
                                //.paymentStatus("PENDING")
                                .payer(payer)
                                .billDetails(providedPurchaseBill.getBillDetails())
                                .additionalDetails(providedPurchaseBill.getAdditionalDetails())
                                .build();

        log.info("Purchase bill created");
        return purchaseBill;
    }

    public Bill updatePurchaseBill(RequestInfo requestInfo,PurchaseBill providedPurchaseBill , List<HeadCode> headCodes , List<ApplicableCharge> applicableCharges,Map<String, String> metaInfo) {
        // Get Id
        String id = providedPurchaseBill.getId();
        // Get TenantId
        String tenantId = providedPurchaseBill.getTenantId();
        // Get documents
        List<Document> documents = providedPurchaseBill.getDocuments();
        // Get contract number
        String contractNumber = providedPurchaseBill.getContractNumber();
        //Generate referenceId
        String referenceId = providedPurchaseBill.getReferenceId();
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

        // Get the payer from providedPurchaseBill
        Party payer = providedPurchaseBill.getParty();

        // If payer is null then Build payer
        if (payer == null) {
            payer = buildParty(requestInfo, configs.getPayerType(), tenantId);
        }
        // Populate additional details object with documents
        populateBillAdditionalDetails(providedPurchaseBill,DOCUMENTS_CONSTANT, documents);
        // Generate the bill
        log.info("Update purchase bill for referenceId ["+referenceId+"]");

        return Bill.builder()
                .tenantId(tenantId)
                .id(id)
                .billDate(providedPurchaseBill.getInvoiceDate())
                .referenceId(referenceId)
                .businessService(configs.getPurchaseBusinessService())
                .fromPeriod(contract.getStartDate().longValue())
                .toPeriod(contract.getEndDate().longValue())
                .status(providedPurchaseBill.getStatus())
                .totalAmount(netPayableAmount)
                //.paymentStatus("PENDING")
                .billNumber(providedPurchaseBill.getBillNumber())
                .billDate(providedPurchaseBill.getBillDate())
                .payer(payer)
                .billDetails(providedPurchaseBill.getBillDetails())
                .additionalDetails(providedPurchaseBill.getAdditionalDetails())
                .build();
    }

    private void calculateAndSetNetLineItemAmount(BillDetail billDetail) {
        List<LineItem> payableLineItems = billDetail.getPayableLineItems();
        BigDecimal netLineItemAmount = BigDecimal.ZERO;
        if (payableLineItems != null && !payableLineItems.isEmpty()) {
            for(LineItem lineItem : payableLineItems) {
                // Add netLineItemAmount only if status is not INACTIVE
                if (!LINEITEM_STATUS_INACTIVE.equalsIgnoreCase(lineItem.getStatus())) {
                    netLineItemAmount = netLineItemAmount.add(lineItem.getAmount().setScale(0, RoundingMode.HALF_UP));
                }
            }
        }
        billDetail.setNetLineItemAmount(netLineItemAmount);
    }

    private void calculateAndSetPayableLineItems(BillDetail billDetail, List<HeadCode> headCodes, List<ApplicableCharge> applicableCharges) {
        List<LineItem> lineItems = billDetail.getLineItems();
        String tenantId = billDetail.getTenantId();

        BigDecimal expense = calculateTotalExpense(lineItems, headCodes);
        BigDecimal materialCost = calculateMaterialCost(lineItems, headCodes);
        setLineItemsInactive(billDetail);
        BigDecimal deduction = calculateTotalDeduction(lineItems, headCodes, applicableCharges, tenantId, materialCost,billDetail);

        // If bill amount is less then equal to zero then do not generate bill
        if (expense.subtract(deduction).compareTo(BigDecimal.ZERO) <= 0) {
            log.error("INVALID_PURCHASE_BILL_AMOUNT", "Purchase bill amount is not grater then ZERO.");
            throw new CustomException("INVALID_PURCHASE_BILL_AMOUNT", "Purchase bill amount is not grater then ZERO.");
        }
        billDetail.addPayableLineItems(buildPayableLineItem(expense.subtract(deduction),tenantId,"PURCHASE"));
    }

    private BigDecimal calculateMaterialCost(List<LineItem> lineItems, List<HeadCode> headCodes) {
        BigDecimal materialCost = BigDecimal.ZERO;
        List<LineItem> lineItemWithZeroAmount = new ArrayList<>();
        for(LineItem lineItem : lineItems) {
            String headCode = lineItem.getHeadCode();
            BigDecimal amount = lineItem.getAmount().setScale(0, RoundingMode.HALF_UP);
            lineItem.setAmount(amount);
            String category = getHeadCodeCategory(headCode,headCodes);

            if (category != null && category.equalsIgnoreCase(EXPENSE_CONSTANT) &&
                    lineItem.getStatus().equals(LINEITEM_STATUS_ACTIVE) && headCode.equals("MC")) {
                materialCost = materialCost.add(amount);
            }

            if(amount.compareTo(BigDecimal.ZERO) <= 0){
                lineItemWithZeroAmount.add(lineItem);
            }
        }
        //Removing the line items which have amount as 0
        if(!lineItemWithZeroAmount.isEmpty()){
            lineItems.removeAll(lineItemWithZeroAmount);
        }
        return materialCost;
    }


    private BigDecimal calculateTotalExpense(List<LineItem> lineItems, List<HeadCode> headCodes) {
        BigDecimal expense = BigDecimal.ZERO;
        List<LineItem> lineItemWithZeroAmount = new ArrayList<>();
        for(LineItem lineItem : lineItems) {
            String headCode = lineItem.getHeadCode();
            BigDecimal amount = lineItem.getAmount().setScale(0, RoundingMode.HALF_UP);
            lineItem.setAmount(amount);
            String category = getHeadCodeCategory(headCode,headCodes);
            if(category != null && category.equalsIgnoreCase(EXPENSE_CONSTANT) && lineItem.getStatus().equals(LINEITEM_STATUS_ACTIVE)) {
                expense = expense.add(amount);
            }
            if(amount.compareTo(BigDecimal.ZERO) <= 0){
                lineItemWithZeroAmount.add(lineItem);
            }
        }
        //Removing the line items which have amount as 0
        if(!lineItemWithZeroAmount.isEmpty()){
            lineItems.removeAll(lineItemWithZeroAmount);
        }
        return expense;
    }

    private void setLineItemsInactive(BillDetail billDetail) {
        // If PayableLineItems is available in bill details then set each line item as INACTIVE
        if (billDetail.getPayableLineItems() != null && !billDetail.getPayableLineItems().isEmpty()) {
            List<LineItem> payableLineItems = billDetail.getPayableLineItems();
            payableLineItems.forEach(p -> p.setStatus(LINEITEM_STATUS_INACTIVE));
            billDetail.setPayableLineItems(payableLineItems);
        }
    }

    private BigDecimal calculateTotalDeduction(List<LineItem> lineItems, List<HeadCode> headCodes, List<ApplicableCharge> applicableCharges, String tenantId, BigDecimal materialCost,BillDetail billDetail) {
        BigDecimal deduction = BigDecimal.ZERO;
        for(LineItem lineItem : lineItems) {
            String headCode = lineItem.getHeadCode();
            String category = getHeadCodeCategory(headCode,headCodes);
            BigDecimal tempDeduction;
            // Generate PayableLineItem only if status is ACTIVE and headCode category type is deduction
            if(DEDUCTION_CONSTANT.equalsIgnoreCase(category) && LINEITEM_STATUS_ACTIVE.equalsIgnoreCase(lineItem.getStatus())) {
                String calculationType = getCalculationType(headCode,applicableCharges);
                String value = getDeductionValue(headCode,applicableCharges);
                tempDeduction = calculateDeduction(materialCost, lineItem, calculationType, value);
                if (tempDeduction.compareTo(BigDecimal.ZERO) <= 0)
                    continue;
                deduction = deduction.add(tempDeduction);
                billDetail.addPayableLineItems(buildPayableLineItem(tempDeduction,tenantId,headCode));
            }
        }
        return deduction;
    }

    private BigDecimal calculateDeduction(BigDecimal materialCost, LineItem lineItem, String calculationType, String value) {
        BigDecimal tempDeduction;
        if(PERCENTAGE_CONSTANT.equalsIgnoreCase(calculationType) && (value == null || "null".equalsIgnoreCase(value))) {
            log.error("INVALID_CALCULATION_TYPE_VALUE", "For calculationType [" + calculationType +"] value is null");
            throw new CustomException("INVALID_CALCULATION_TYPE_VALUE", "For calculationType [" + calculationType +"] field value is null");
        } else if (PERCENTAGE_CONSTANT.equalsIgnoreCase(calculationType) && value != null && !"null".equalsIgnoreCase(value) ) {
            tempDeduction = materialCost.multiply(new BigDecimal(value)).divide(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP) ;
        } else if (LUMPSUM_CONSTANT.equalsIgnoreCase(calculationType) && (value == null || "null".equalsIgnoreCase(value)))  {
            tempDeduction = lineItem.getAmount().setScale(0, RoundingMode.HALF_UP);
        } else if (LUMPSUM_CONSTANT.equalsIgnoreCase(calculationType) && value != null && !"null".equalsIgnoreCase(value) ) {
            tempDeduction = new BigDecimal(value).setScale(0, RoundingMode.HALF_UP);
        } else {
            log.error("INVALID_HEADCODE_CALCULATION_TYPE", "Head Code calculation type [" + calculationType +"] is not supported");
            throw new CustomException("INVALID_HEADCODE_CALCULATION_TYPE", "Head Code calculation type [" + calculationType +"] is not supported");
        }
        return tempDeduction;
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
        log.info("Purchase Bill additional details populated with given key value");
    }

    private Contract getContract(RequestInfo requestInfo, String tenantId, String referenceId) {
        ContractResponse contractResponse = contractUtils.fetchContract(requestInfo, tenantId, referenceId);
        return contractResponse.getContracts().get(0);
    }

    private Party buildParty(RequestInfo requestInfo, String type, String tenantId) {
        Object mdmsResp = mdmsUtil.getPayersForTypeFromMDMS(requestInfo, type, tenantId);
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
                .status("ACTIVE")
                .build();
    }

    private String getDeductionValue(String headCode, List<ApplicableCharge> applicableCharges) {
        for(ApplicableCharge applicableCharge : applicableCharges) {
            if(applicableCharge.getCode().equalsIgnoreCase(headCode)){
                return applicableCharge.getValue();
            }
        }
        log.error("MISSING_HEAD_CODE","HeadCode ["+headCode+"] missing in applicable charge MDMS");
        throw new CustomException("MISSING_HEAD_CODE","HeadCode ["+headCode+"] missing in applicable charge MDMS");
    }

    private String getCalculationType(String headCode, List<ApplicableCharge> applicableCharges) {
        for(ApplicableCharge applicableCharge : applicableCharges) {
            if(applicableCharge.getCode().equalsIgnoreCase(headCode)){
                String calculationType = applicableCharge.getCalculationType();
                if (StringUtils.isBlank(calculationType)) {
                    log.error("CALCULATION_TYPE_MISSING","MDMS::calculationType missing for head code [" + headCode +SERVICE_PLACEHOLDER+config.getPurchaseBusinessService()+"]");
                    throw new CustomException("CALCULATION_TYPE_MISSING","MDMS::calculationType missing for head code [" + headCode +SERVICE_PLACEHOLDER+config.getPurchaseBusinessService()+"]");
                } else {
                    return calculationType;
                }
            }
        }
        log.error(INVALID_HEAD_CODE,INVALID_HEAD_CODE_PLACEHOLDER + headCode +FOR_SERVICE_PLACEHOLDER+config.getPurchaseBusinessService()+"]");
        throw new CustomException(INVALID_HEAD_CODE,INVALID_HEAD_CODE_PLACEHOLDER + headCode +FOR_SERVICE_PLACEHOLDER+config.getPurchaseBusinessService()+"]");
    }

    private String getHeadCodeCategory(String headCode, List<HeadCode> headCodes) {
        for(HeadCode hCode : headCodes) {
            if(hCode.getCode().equalsIgnoreCase(headCode)){
                String category = hCode.getCategory();
                if (StringUtils.isBlank(category)) {
                    log.error(CATEGORY_MISSING,"MDMS::category missing for head code [" + headCode +SERVICE_PLACEHOLDER+config.getPurchaseBusinessService()+"]");
                    throw new CustomException(CATEGORY_MISSING,"MDMS::category missing for head code [" + headCode +SERVICE_PLACEHOLDER+config.getPurchaseBusinessService()+"]");
                } else {
                    return category;
                }
            }
        }
        log.error(INVALID_HEAD_CODE,INVALID_HEAD_CODE_PLACEHOLDER + headCode +FOR_SERVICE_PLACEHOLDER+config.getPurchaseBusinessService()+"]");
        throw new CustomException(INVALID_HEAD_CODE,INVALID_HEAD_CODE_PLACEHOLDER + headCode +FOR_SERVICE_PLACEHOLDER+config.getPurchaseBusinessService()+"]");
    }

    
    /**
     * Generates the referenceId to be set on the Bill object. This referenceId is a combination of the
     * contract number and a unique ID generated from MDMS format template. 
     * Example: CON123_PB_123
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
