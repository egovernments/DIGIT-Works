package org.egov.digit.expense.calculator.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.util.MdmsUtils;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.digit.expense.calculator.util.ExpenseCalculatorUtil;
import org.egov.digit.expense.calculator.web.models.CalculationRequest;
import org.egov.digit.expense.calculator.web.models.Contract;
import org.egov.digit.expense.calculator.web.models.Criteria;
import org.egov.digit.expense.calculator.web.models.MusterRoll;
import org.egov.digit.expense.calculator.web.models.MusterRollRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.JSON_PATH_FOR_BUSINESS_SERVICE_VERIFICATION;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.JSON_PATH_FOR_TENANTS_VERIFICATION;

@Component
@Slf4j
public class ExpenseCalculatorServiceValidator {
    @Autowired
    private MdmsUtils mdmsUtils;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExpenseCalculatorUtil expenseCalculatorUtil;

    @Autowired
    private ExpenseCalculatorConfiguration configs;


    public void validateCalculatorEstimateRequest(CalculationRequest calculationRequest){
        validateCommonCalculatorRequest(calculationRequest);

        // Validate musterRollIds if given against muster roll service
        validateMusterRollIdAgainstService(calculationRequest,false);

        // Validate contractId if given against contract service
        validateContractIdAgainstService(calculationRequest);

    }

    public void validateCalculatorCalculateRequest(CalculationRequest calculationRequest){
        validateCommonCalculatorRequest(calculationRequest);

        //Validate muster roll Ids against muster roll service
        validateMusterRollIdAgainstService(calculationRequest,true);

        // Validate contractId if given against contract service
        validateContractIdAgainstService(calculationRequest);
    }

    public void validateWageBillCreateForMusterRollRequest(MusterRollRequest musterRollRequest){
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        // Validate the Request Info object
        validateRequestInfo(requestInfo);
        // Validate the required params
        validateRequiredParametersForMusterRollRequest(musterRollRequest);
        //Validate request against MDMS
        validateRequestAgainstMDMS(requestInfo,musterRoll.getTenantId(), configs.getWageBusinessService());
        //Validate musterRollId against service
        validateMusterRollIdAgainstService(musterRollRequest);
        log.info("Validation done for muster roll number ["+musterRoll.getMusterRollNumber()+"]");
    }

    public void validateCreatePurchaseRequest(PurchaseBillRequest purchaseBillRequest) {
        RequestInfo requestInfo = purchaseBillRequest.getRequestInfo();
        PurchaseBill bill = purchaseBillRequest.getBill();

        // Validate the Request Info object
        validateRequestInfo(requestInfo);
        // Validate purchase request parameters
        validateCreatePurchaseRequestParameters(purchaseBillRequest);
        //Validate request against MDMS
        validateRequestAgainstMDMS(requestInfo,bill.getTenantId(),configs.getPurchaseBusinessService());
    }

    public void validateUpdatePurchaseRequest(PurchaseBillRequest purchaseBillRequest) {
        RequestInfo requestInfo = purchaseBillRequest.getRequestInfo();
        PurchaseBill bill = purchaseBillRequest.getBill();

        // Update payableLineItem in request
        updatedBillPayableLineItemFromDB(requestInfo, purchaseBillRequest);
        // Validate the Request Info object
        validateRequestInfo(requestInfo);
        // Validate purchase request parameters
        validateUpdatePurchaseRequestParameters(purchaseBillRequest);
        //Validate request against MDMS
        validateRequestAgainstMDMS(requestInfo,bill.getTenantId(),configs.getPurchaseBusinessService());
    }

    private void validateUpdatePurchaseRequestParameters(PurchaseBillRequest purchaseBillRequest) {
        Map<String, String> errorMap = new HashMap<>();
        PurchaseBill purchaseBill = purchaseBillRequest.getBill();

        if (purchaseBill == null) {
            log.error("PurchaseBill is mandatory");
            throw new CustomException("PURCHASE_BILL", "PurchaseBill is mandatory");
        }

        if(StringUtils.isBlank(purchaseBill.getReferenceId()) ) {
            log.error("ReferenceId is mandatory");
            errorMap.put("PURCHASE_BILL.REFERENCE_ID", "ReferenceId is mandatory");
        }


        List<BillDetail> billDetails = purchaseBill.getBillDetails();
        if(billDetails == null || billDetails.isEmpty()) {
            log.error("BillDetails is mandatory");
            throw new CustomException("PURCHASE_BILL.BILL_DETAILS", "BillDetails is mandatory");
        }

        for(BillDetail billDetail : billDetails) {
            Party payee = billDetail.getPayee();
            if(payee == null) {
                log.error("Payee is mandatory");
                throw new CustomException("PURCHASE_BILL.BILL_DETAILS.PAYEE", "Payee is mandatory");
            }

            if(StringUtils.isBlank(payee.getType()) ) {
                log.error("Payee type is mandatory");
                errorMap.put("PURCHASE_BILL.BILL_DETAILS.PAYEE.TYPE", "Payee type is mandatory");
            }

            if(StringUtils.isBlank(payee.getIdentifier())) {
                log.error("Payee identifier is mandatory");
                errorMap.put("PURCHASE_BILL.BILL_DETAILS.PAYEE.IDENTIFIER", "Payee identifier is mandatory");
            }
        }

        if (StringUtils.isBlank(purchaseBill.getTenantId())) {
            log.error("TenantId is mandatory");
            errorMap.put("PURCHASE_BILL.TENANTID", "TenantId is mandatory");
        }

        if (!errorMap.isEmpty()) {
            log.error("Purchase bill validation failed");
            throw new CustomException(errorMap);
        }
        log.info("Required request parameter validation done for purchase bill");
    }

    private void validateCreatePurchaseRequestParameters(PurchaseBillRequest purchaseBillRequest) {
        Map<String, String> errorMap = new HashMap<>();
        PurchaseBill purchaseBill = purchaseBillRequest.getBill();
        if (purchaseBill == null) {
            log.error("PurchaseBill is mandatory");
            throw new CustomException("PURCHASE_BILL", "PurchaseBill is mandatory");
        }

        List<BillDetail> billDetails = purchaseBill.getBillDetails();
        if(billDetails == null || billDetails.isEmpty()) {
            log.error("BillDetails is mandatory");
            throw new CustomException("PURCHASE_BILL.BILL_DETAILS", "BillDetails is mandatory");
        }

        for(BillDetail billDetail : billDetails) {
            Party payee = billDetail.getPayee();
            if(payee == null) {
                log.error("Payee is mandatory");
                throw new CustomException("PURCHASE_BILL.BILL_DETAILS.PAYEE", "Payee is mandatory");
            }

            if(StringUtils.isBlank(payee.getType()) ) {
                log.error("Payee type is mandatory");
                errorMap.put("PURCHASE_BILL.BILL_DETAILS.PAYEE.TYPE", "Payee type is mandatory");
            }

            if(StringUtils.isBlank(payee.getIdentifier())) {
                log.error("Payee identifier is mandatory");
                errorMap.put("PURCHASE_BILL.BILL_DETAILS.PAYEE.IDENTIFIER", "Payee identifier is mandatory");
            }
        }

        if (StringUtils.isBlank(purchaseBill.getTenantId())) {
            log.error("TenantId is mandatory");
            errorMap.put("PURCHASE_BILL.TENANTID", "TenantId is mandatory");
        }

        if (!errorMap.isEmpty()) {
            log.error("Purchase bill validation failed");
            throw new CustomException(errorMap);
        }
        log.info("Required request parameter validation done for purchase bill");
    }

    /**
     * Update payableLineItem in bill details of request to resolve issue of duplicate payableLineItem
     * @param requestInfo
     * @param purchaseBillRequest
     */
    private void updatedBillPayableLineItemFromDB(RequestInfo requestInfo, PurchaseBillRequest purchaseBillRequest) {
        // Get purchase bill from db and update payableLineItem into the request
        PurchaseBill billRequest = purchaseBillRequest.getBill();
        List<Bill> bills=new ArrayList<>();
        List<String> billIds = new ArrayList<>(Arrays.asList(billRequest.getId()));
        bills = expenseCalculatorUtil.fetchBillsWithBillIds(requestInfo, billRequest.getTenantId(), billIds);
        if (bills != null && !bills.isEmpty()) {
            Bill billFromDB = bills.get((0));
            // Set payableLineItems ones
            Map<String, BillDetail> billDetailMap = new HashMap<String, BillDetail>();
            if (billFromDB != null && !billFromDB.getBillDetails().isEmpty() && !purchaseBillRequest.getBill().getBillDetails().isEmpty()) {
                List<BillDetail> billDetailsFromDB = billFromDB.getBillDetails();
                for (int idx = 0; idx < billDetailsFromDB.size(); idx++) {
                    billDetailMap.put(billDetailsFromDB.get(idx).getId(), billDetailsFromDB.get(idx));
                }

                for (int idx = 0; idx < billRequest.getBillDetails().size(); idx++) {
                    BillDetail billDetail = billRequest.getBillDetails().get(idx);
                    if (billDetailMap.containsKey(billDetail.getId())) {
                        billRequest.getBillDetails().get(idx).setPayableLineItems(billDetailMap.get(billDetail.getId()).getPayableLineItems());
                    }
                }
            }
            // Update purchase bill request with updated request
            purchaseBillRequest.setBill(billRequest);
        }
    }

    private void validateContractIdAgainstService(CalculationRequest calculationRequest) {
        final Criteria criteria = calculationRequest.getCriteria();
        // Validate contractId if given against contract service
        if(StringUtils.isNotBlank(criteria.getContractId())) {
            List<Contract> contracts = expenseCalculatorUtil.fetchContract(calculationRequest.getRequestInfo(), criteria.getTenantId(), criteria.getContractId());
            if (CollectionUtils.isEmpty(contracts)) {
                log.error("ExpenseCalculatorServiceValidator:No matched contract found for contractId - "+criteria.getContractId());
                throw new CustomException("INVALID_CONTRACT_ID", "Contract not found");
            }
            log.info("ContractId validated against service");
        }
    }

    public void validateCommonCalculatorRequest(CalculationRequest calculationRequest){
        RequestInfo requestInfo = calculationRequest.getRequestInfo();
        Criteria criteria = calculationRequest.getCriteria();
        // Validate the Request Info object
        validateRequestInfo(requestInfo);
        //Validate required parameters for calculator estimate
        validateRequiredParametersForCalculatorRequest(calculationRequest);

        String businessServiceToValidate = null;
        if(criteria.getMusterRollId() != null && !criteria.getMusterRollId().isEmpty())
            businessServiceToValidate = configs.getWageBusinessService();
        else
            businessServiceToValidate = configs.getSupervisionBusinessService();

        validateRequestAgainstMDMS(requestInfo,criteria.getTenantId(),businessServiceToValidate);
    }

    private void validateMusterRollIdAgainstService(CalculationRequest calculationRequest, boolean onlyApproved) {
        // Validate musterRollIds if given against muster roll service
        Criteria criteria = calculationRequest.getCriteria();
        List<String> musterRollIds = criteria.getMusterRollId();

        if(musterRollIds != null && !musterRollIds.isEmpty()) {
            //Validate requested musterRollIds against musterroll service
            String tenantId = criteria.getTenantId();
            RequestInfo requestInfo = calculationRequest.getRequestInfo();
            List<String> fetchedMusterRollIds = fetchListOfMusterRollIdsForGivenIds(requestInfo, tenantId, musterRollIds, onlyApproved);
            validateMusterRollIds(musterRollIds,fetchedMusterRollIds);
            log.info("MusterRollId validated against service");
        }
    }
    private void validateMusterRollIdAgainstService(MusterRollRequest musterRollRequest) {
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        String musterRollId = musterRoll.getId();
        String tenantId = musterRoll.getTenantId();
        List<String> fetchedMusterRollIds = fetchListOfMusterRollIdsForGivenIds(requestInfo, tenantId, Collections.singletonList(musterRollId), true);
        validateMusterRollIds( Collections.singletonList(musterRollId),fetchedMusterRollIds);
        log.info("Muster roll validated against muster roll service ["+musterRollId+"]");
    }

//    private void validateMusterRollRequestAgainstMDMS(MusterRollRequest musterRollRequest) {
//        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
//        String tenantId = musterRoll.getTenantId();
//        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
//        //Fetch MDMS data
//        Object mdmsData = fetchMDMSDataForValidation(requestInfo,tenantId);
//        // Validate tenantId against MDMS data
//        validateTenantIdAgainstMDMS(mdmsData, tenantId);
//
//        log.info("MDMD validation done");
//    }

    private void validateRequiredParametersForMusterRollRequest(MusterRollRequest musterRollRequest) {
         MusterRoll musterRoll = musterRollRequest.getMusterRoll();
         Map<String, String> errorMap = new HashMap<>();

         if (musterRoll == null) {
            log.error("MusterRoll is mandatory");
            throw new CustomException("MUSTERROLL","MusterRoll is mandatory");
         }

        if (StringUtils.isBlank(musterRoll.getTenantId())) {
            log.error("TenantId is mandatory");
            errorMap.put("MUSTERROLL.TENANTID", "TenantId is mandatory");
        }

        String musterRollId = musterRoll.getId();

        if(StringUtils.isBlank(musterRollId)) {
            log.error("musterRollId is mandatory");
            errorMap.put("MUSTERROLL.MUSTER_ROLL_ID", "MusterRollId is mandatory");
        }

        if (!errorMap.isEmpty()) {
            log.error("Calculator Estimate validation failed");
            throw new CustomException(errorMap);
        }
        log.info("Required request parameter validation done for Calculator calculate service");
    }
    private void validateRequestInfo(RequestInfo requestInfo) {
        if (requestInfo == null) {
            log.error("Request info is mandatory");
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            log.error("UserInfo is mandatory");
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            log.error("UUID is mandatory");
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }

        log.info("Request Info object validation done");
    }

    private void validateRequiredParametersForCalculatorRequest(CalculationRequest calculationRequest) {
        Map<String, String> errorMap = new HashMap<>();

        Criteria criteria = calculationRequest.getCriteria();

        if (criteria == null) {
            log.error("Criteria is mandatory");
            throw new CustomException("CRITERIA", "criteria is mandatory");
        }

        if (StringUtils.isBlank(criteria.getTenantId())) {
            log.error("TenantId is mandatory");
            errorMap.put("CRITERIA.TENANTID", "TenantId is mandatory");
        }

        List<String> musterRollId = criteria.getMusterRollId();
        String contractId = criteria.getContractId();

        if((musterRollId == null || musterRollId.isEmpty()) && (StringUtils.isBlank(contractId))) {
            log.error("MusterRollId or ContractId is mandatory");
            errorMap.put("CRITERIA.MUSTER_ROLL_OR_CONTRACT_ID", "MusterRollId or ContractId is mandatory");
        }

        if (!errorMap.isEmpty()){
            log.error("Calculator Estimate validation failed");
            throw new CustomException(errorMap);
        }


        log.info("Required request parameter validation done for Calculator");
    }

    private void validateRequestAgainstMDMS(RequestInfo requestInfo, String tenantId, String businessService) {
        //Fetch MDMS data
        Object mdmsData = fetchMDMSDataForValidation(requestInfo,tenantId);
        
        log.info("MDMS Data response:" + mdmsData.toString());        // Validate tenantId against MDMS data
        validateTenantIdAgainstMDMS(mdmsData, tenantId);
        // Validate head code against MDMS data
        //validateHeadCodeAgainstMDMS(mdmsData, businessService);
        // Validate business service against MDMS data
        //TODO: Commenting this out since this is problematic
        //validateBusinessServiceAgainstMDMS(mdmsData, businessService);
    }

    private void validateBusinessServiceAgainstMDMS(Object mdmsData, String businessService) {
        List<Object> businessServiceRes = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_BUSINESS_SERVICE_VERIFICATION);
        if (CollectionUtils.isEmpty(businessServiceRes) || !businessServiceRes.contains(businessService)){
            log.error("The businessService: " + businessService + " is not present in MDMS");
            throw new CustomException("INVALID_BUSINESS_SERVICE","Invalid businessService [" + businessService + "]");
        }
        log.info("BusinessService data validated against MDMS");

    }

//    private void validateHeadCodeAgainstMDMS(Object mdmsData, String businessService) {
//        List<Object> businessServiceRes = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_BUSINESS_SERVICE_VERIFICATION);
//        if (CollectionUtils.isEmpty(businessServiceRes) || !businessServiceRes.contains(businessService)){
//            log.error("The businessService: " + businessService + " is not present in MDMS");
//            throw new CustomException("INVALID_BUSINESS_SERVICE","Invalid businessService [" + businessService + "]");
//        }
//        log.info("BusinessService data validated against MDMS");
//    }

    private Object fetchMDMSDataForValidation(RequestInfo requestInfo, String tenantId){
        String rootTenantId = tenantId.split("\\.")[0];
        return mdmsUtils.fetchMDMSForValidation(requestInfo, rootTenantId);

    }

    private void validateTenantIdAgainstMDMS(Object mdmsData,String tenantId) {
    	log.info("MDMS Response for tenantID:" + mdmsData.toString());        
    	List<Object> tenantRes = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_TENANTS_VERIFICATION);
        if (CollectionUtils.isEmpty(tenantRes) || !tenantRes.contains(tenantId)){
            log.error("The tenant: " + tenantId + " is not present in MDMS");
            throw new CustomException("INVALID_TENANT","Invalid tenantId [" + tenantId + "]");
        }
        log.info("TenantId data validated against MDMS");
    }

    private List<String> fetchListOfMusterRollIdsForGivenIds(RequestInfo requestInfo, String tenantId, List<String> musterRollIds, boolean onlyApproved){
        return expenseCalculatorUtil.fetchListOfMusterRollIds(requestInfo, tenantId, musterRollIds,onlyApproved);
    }
    private void validateMusterRollIds(List<String> musterRollIds, List<String> fetchedMusterRolls) {
        for(String musterRollId : musterRollIds){
            if(!fetchedMusterRolls.contains(musterRollId)){
                log.error("INVALID_MUSTER_ROLL","Provided musterroll is invalid ["+musterRollId+"]");
                throw new CustomException("INVALID_MUSTER_ROLL","Provided musterroll is invalid ["+musterRollId+"]");
            }
        }

    }

    public void validateCalculatorSearchRequest(CalculatorSearchRequest calculatorSearchRequest) {
        // if only tenantId is passed or tenantId & billType is passed, throw error. One more criteria is mandatory
        boolean isValidRequest=false;
        String tenantId=calculatorSearchRequest.getSearchCriteria().getTenantId();
        CalculatorSearchCriteria searchCriteria=calculatorSearchRequest.getSearchCriteria();

        if(StringUtils.isBlank(tenantId)){
            throw new CustomException("TENANT_ID","TenantId is mandatory");
        }

        if(StringUtils.isNotBlank(tenantId)){
            if(!CollectionUtils.isEmpty(searchCriteria.getProjectNumbers())
                    || !CollectionUtils.isEmpty(searchCriteria.getOrgNumbers())
                    || !CollectionUtils.isEmpty(searchCriteria.getMusterRollNumbers())
                    || !CollectionUtils.isEmpty(searchCriteria.getContractNumbers())
                    || !CollectionUtils.isEmpty(searchCriteria.getBillNumbers())
                    || !CollectionUtils.isEmpty(searchCriteria.getBillReferenceIds())
                    || searchCriteria.getProjectName()!=null
                    || searchCriteria.getBoundary()!=null
                    ){
                isValidRequest=true;

            }
        }

        if(!isValidRequest)
            throw new CustomException("INVALID_SEARCH_CRITERIA","Search with only [tenantId] or [billTypes and tenantId] not allowed");
    }

}
