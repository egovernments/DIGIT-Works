package org.egov.digit.expense.calculator.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.util.MdmsUtils;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private void validateContractIdAgainstService(CalculationRequest calculationRequest) {
        final Criteria criteria = calculationRequest.getCriteria();
        // Validate contractId if given against contract service
        if(StringUtils.isNotBlank(criteria.getContractId())) {
            List<Contract> contracts = expenseCalculatorUtil.fetchContract(calculationRequest.getRequestInfo(), criteria.getTenantId(), criteria.getContractId());
            if (CollectionUtils.isEmpty(contracts)) {
                log.error("ExpenseCalculatorServiceValidator:No matched contract found for contractId - "+criteria.getContractId());
                throw new CustomException("INVALID_CONTRACT_ID", "Contract not found");
            }
        }
    }

    public void validateCommonCalculatorRequest(CalculationRequest calculationRequest){
        // Validate the Request Info object
        validateRequestInfo(calculationRequest.getRequestInfo());

        //Validate required parameters for calculator estimate
        validateRequiredParametersForCalculatorRequest(calculationRequest);

        //Validate request against MDMS
        validateCalculatorEstimateReqeuestAgainstMDMS(calculationRequest);

    }

    public void validateWageBillCreateForMusterRollRequest(MusterRollRequest musterRollRequest){
        // Validate the Request Info object
        validateRequestInfo(musterRollRequest.getRequestInfo());
        // Validate the required params
        validateRequiredParametersForMusterRollRequest(musterRollRequest);
        //Validate request against MDMS
        validateMusterRollRequestAgainstMDMS(musterRollRequest);
        //Validate musterRollId against service
        validateMusterRollIdAgainstService(musterRollRequest);
        log.info("Validation done for muster roll number ["+musterRollRequest.getMusterRoll().getMusterRollNumber()+"]");
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

    private void validateMusterRollRequestAgainstMDMS(MusterRollRequest musterRollRequest) {
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        String tenantId = musterRoll.getTenantId();
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        //Fetch MDMS data
        Object mdmsData = fetchMDMSDataForValidation(requestInfo,tenantId);
        // Validate tenantId against MDMS data
        validateTenantIdAgainstMDMS(mdmsData, tenantId);

        log.info("MDMD validation done");
    }

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


        log.info("Required request parameter validation done for Calculator Estimate service");
    }

    private void validateCalculatorEstimateReqeuestAgainstMDMS(CalculationRequest calculationRequest) {
        Criteria criteria = calculationRequest.getCriteria();
        String tenantId = criteria.getTenantId();
        RequestInfo requestInfo = calculationRequest.getRequestInfo();
        //Fetch MDMS data
        Object mdmsData = fetchMDMSDataForValidation(requestInfo,tenantId);
        // Validate tenantId against MDMS data
        validateTenantIdAgainstMDMS(mdmsData, tenantId);
    }

    private Object fetchMDMSDataForValidation(RequestInfo requestInfo, String tenantId){
        String rootTenantId = tenantId.split("\\.")[0];
        return mdmsUtils.fetchMDMSForValidation(requestInfo, rootTenantId);

    }

    private void validateTenantIdAgainstMDMS(Object mdmsData,String tenantId) {
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

}
