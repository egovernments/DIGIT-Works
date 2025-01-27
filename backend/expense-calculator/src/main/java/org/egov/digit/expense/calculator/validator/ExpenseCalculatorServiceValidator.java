package org.egov.digit.expense.calculator.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.util.MdmsUtils;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.digit.expense.calculator.util.ExpenseCalculatorUtil;
import org.egov.digit.expense.calculator.web.models.CalculationRequest;
import org.egov.digit.expense.calculator.web.models.Criteria;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.contract.Contract;
import org.egov.works.services.common.models.musterroll.MusterRoll;
import org.egov.works.services.common.models.musterroll.MusterRollRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.JSON_PATH_FOR_TENANTS_VERIFICATION;

@Component
@Slf4j
public class ExpenseCalculatorServiceValidator {
    private final MdmsUtils mdmsUtils;

    private final CommonUtil commonUtil;

    private final ExpenseCalculatorUtil expenseCalculatorUtil;
    private static final String PURCHASEBILL_IS_MANDATORY ="PurchaseBill is mandatory";
    private static final String BILLDETAILS_IS_MANDATORY= "BillDetails is mandatory";
    private static final String PAYEE_IS_MANDATORY = "Payee is mandatory";
    private static final String PAYEE_TYPE_IS_MANDATORY = "Payee type is mandatory";
    private static final String PAYEE_IDENTIFIER_IS_MANDATORY = "Payee identifier is mandatory";
    private static final String TENANT_ID_IS_MANDATORY = "TenantId is mandatory";
    @Autowired
    public ExpenseCalculatorServiceValidator(MdmsUtils mdmsUtils, CommonUtil commonUtil, ExpenseCalculatorUtil expenseCalculatorUtil) {
        this.mdmsUtils = mdmsUtils;
        this.commonUtil = commonUtil;
        this.expenseCalculatorUtil = expenseCalculatorUtil;
    }


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
        validateRequestAgainstMDMS(requestInfo,musterRoll.getTenantId());
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
        validateRequestAgainstMDMS(requestInfo,bill.getTenantId());
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
        validateRequestAgainstMDMS(requestInfo,bill.getTenantId());
    }

    private void validateUpdatePurchaseRequestParameters(PurchaseBillRequest purchaseBillRequest) {
        Map<String, String> errorMap = new HashMap<>();
        PurchaseBill purchaseBill = purchaseBillRequest.getBill();

        if (purchaseBill == null) {
            log.error(PURCHASEBILL_IS_MANDATORY);
            throw new CustomException("PURCHASE_BILL", PURCHASEBILL_IS_MANDATORY);
        }

        if(StringUtils.isBlank(purchaseBill.getReferenceId()) ) {
            log.error("ReferenceId is mandatory");
            errorMap.put("PURCHASE_BILL.REFERENCE_ID", "ReferenceId is mandatory");
        }


        List<BillDetail> billDetails = purchaseBill.getBillDetails();
        if(billDetails == null || billDetails.isEmpty()) {
            log.error(BILLDETAILS_IS_MANDATORY);
            throw new CustomException("PURCHASE_BILL.BILL_DETAILS", BILLDETAILS_IS_MANDATORY);
        }

        for(BillDetail billDetail : billDetails) {
            Party payee = billDetail.getPayee();
            if(payee == null) {
                log.error(PAYEE_IS_MANDATORY);
                throw new CustomException("PURCHASE_BILL.BILL_DETAILS.PAYEE", PAYEE_IS_MANDATORY);
            }

            if(StringUtils.isBlank(payee.getType()) ) {
                log.error(PAYEE_TYPE_IS_MANDATORY);
                errorMap.put("PURCHASE_BILL.BILL_DETAILS.PAYEE.TYPE", PAYEE_TYPE_IS_MANDATORY);
            }

            if(StringUtils.isBlank(payee.getIdentifier())) {
                log.error(PAYEE_IDENTIFIER_IS_MANDATORY);
                errorMap.put("PURCHASE_BILL.BILL_DETAILS.PAYEE.IDENTIFIER", PAYEE_IDENTIFIER_IS_MANDATORY);
            }
        }

        if (StringUtils.isBlank(purchaseBill.getTenantId())) {
            log.error(TENANT_ID_IS_MANDATORY);
            errorMap.put("PURCHASE_BILL.TENANTID", TENANT_ID_IS_MANDATORY);
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
            log.error(PURCHASEBILL_IS_MANDATORY);
            throw new CustomException("PURCHASE_BILL", PURCHASEBILL_IS_MANDATORY);
        }

        List<BillDetail> billDetails = purchaseBill.getBillDetails();
        if(billDetails == null || billDetails.isEmpty()) {
            log.error(BILLDETAILS_IS_MANDATORY);
            throw new CustomException("PURCHASE_BILL.BILL_DETAILS", BILLDETAILS_IS_MANDATORY);
        }

        for(BillDetail billDetail : billDetails) {
            Party payee = billDetail.getPayee();
            if(payee == null) {
                log.error(PAYEE_IS_MANDATORY);
                throw new CustomException("PURCHASE_BILL.BILL_DETAILS.PAYEE", PAYEE_IS_MANDATORY);
            }

            if(StringUtils.isBlank(payee.getType()) ) {
                log.error(PAYEE_TYPE_IS_MANDATORY);
                errorMap.put("PURCHASE_BILL.BILL_DETAILS.PAYEE.TYPE", PAYEE_TYPE_IS_MANDATORY);
            }

            if(StringUtils.isBlank(payee.getIdentifier())) {
                log.error(PAYEE_IDENTIFIER_IS_MANDATORY);
                errorMap.put("PURCHASE_BILL.BILL_DETAILS.PAYEE.IDENTIFIER", PAYEE_IDENTIFIER_IS_MANDATORY);
            }
        }

        if (StringUtils.isBlank(purchaseBill.getTenantId())) {
            log.error(TENANT_ID_IS_MANDATORY);
            errorMap.put("PURCHASE_BILL.TENANTID", TENANT_ID_IS_MANDATORY);
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
        List<Bill> bills;
        List<String> billIds = new ArrayList<>(Arrays.asList(billRequest.getId()));
        bills = expenseCalculatorUtil.fetchBillsWithBillIds(requestInfo, billRequest.getTenantId(), billIds);
        if (bills != null && !bills.isEmpty()) {
            Bill billFromDB = bills.get((0));
            // Set payableLineItems ones
            Map<String, BillDetail> billDetailMap = new HashMap<>();
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

        validateRequestAgainstMDMS(requestInfo,criteria.getTenantId());
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


    private void validateRequiredParametersForMusterRollRequest(MusterRollRequest musterRollRequest) {
         MusterRoll musterRoll = musterRollRequest.getMusterRoll();
         Map<String, String> errorMap = new HashMap<>();

         if (musterRoll == null) {
            log.error("MusterRoll is mandatory");
            throw new CustomException("MUSTERROLL","MusterRoll is mandatory");
         }

        if (StringUtils.isBlank(musterRoll.getTenantId())) {
            log.error(TENANT_ID_IS_MANDATORY);
            errorMap.put("MUSTERROLL.TENANTID", TENANT_ID_IS_MANDATORY);
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
            log.error(TENANT_ID_IS_MANDATORY);
            errorMap.put("CRITERIA.TENANTID", TENANT_ID_IS_MANDATORY);
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

    private void validateRequestAgainstMDMS(RequestInfo requestInfo, String tenantId) {
        //Fetch MDMS data
        Object mdmsData = fetchMDMSDataForValidation(requestInfo,tenantId);
        
        log.info("MDMS Data response:" + mdmsData.toString());        // Validate tenantId against MDMS data
        validateTenantIdAgainstMDMS(mdmsData, tenantId);
    }


    private Object fetchMDMSDataForValidation(RequestInfo requestInfo, String tenantId){
        return mdmsUtils.fetchMDMSForValidation(requestInfo, tenantId);

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
            throw new CustomException("TENANT_ID",TENANT_ID_IS_MANDATORY);
        }

        if(StringUtils.isNotBlank(tenantId) && (!CollectionUtils.isEmpty(searchCriteria.getProjectNumbers())
                    || !CollectionUtils.isEmpty(searchCriteria.getOrgNumbers())
                    || !CollectionUtils.isEmpty(searchCriteria.getMusterRollNumbers())
                    || !CollectionUtils.isEmpty(searchCriteria.getContractNumbers())
                    || !CollectionUtils.isEmpty(searchCriteria.getBillNumbers())
                    || !CollectionUtils.isEmpty(searchCriteria.getBillReferenceIds())
                    || searchCriteria.getProjectName()!=null
                    || searchCriteria.getBoundary()!=null
                    )){
                isValidRequest=true;


        }

        if(!isValidRequest)
            throw new CustomException("INVALID_SEARCH_CRITERIA","Search with only [tenantId] or [billTypes and tenantId] not allowed");
    }

}
