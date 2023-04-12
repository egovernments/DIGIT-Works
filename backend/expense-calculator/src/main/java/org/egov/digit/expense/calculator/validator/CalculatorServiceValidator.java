package org.egov.digit.expense.calculator.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.util.MdmsUtils;
import org.egov.digit.expense.calculator.util.MusterRollUtils;
import org.egov.digit.expense.calculator.web.models.CalculationRequest;
import org.egov.digit.expense.calculator.web.models.Criteria;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.JSON_PATH_FOR_TENANTS_VERIFICATION;

@Component
@Slf4j
public class CalculatorServiceValidator {
    @Autowired
    private MdmsUtils mdmsUtils;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private MusterRollUtils musterRollUtils;
    public void validateCreateCalculatorEstimateRequest (CalculationRequest calculationRequest){
        // Validate the Request Info object
        validateRequestInfo(calculationRequest.getRequestInfo());

        //Validate required parameters for calculator estimate
        validateRequiredParametersForCalculatorEstimate(calculationRequest);

        //Validate request against MDMS
        validateCalculatorEstimateReqeuestAgainstMDMS(calculationRequest);

        //Validate requested musterRollId against musterroll service
        validateMusterRollId(calculationRequest);

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

    private void validateRequiredParametersForCalculatorEstimate(CalculationRequest calculationRequest) {
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

        final List<String> musterRollId = criteria.getMusterRollId();

        if(musterRollId == null || musterRollId.isEmpty()){
            log.error("MusterRollId is mandatory");
            errorMap.put("CRITERIA.MUSTER_ROLL_ID", "MusterRollId is mandatory");
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

    private void validateMusterRollId(CalculationRequest calculationRequest) {
        Criteria criteria = calculationRequest.getCriteria();
        List<String> musterRollIds = criteria.getMusterRollId();
        String tenantId = criteria.getTenantId();
        RequestInfo requestInfo = calculationRequest.getRequestInfo();
        List<String> fetchedMusterRolls = musterRollUtils.fetchMusterRollIdsList(requestInfo, tenantId, musterRollIds);

        for(String musterRollId : musterRollIds){
            if(!fetchedMusterRolls.contains(musterRollId)){
                log.error("INVALID_MUSTER_ROLL","Provided musterroll is invalid ["+musterRollId+"]");
                throw new CustomException("INVALID_MUSTER_ROLL","Provided musterroll is invalid ["+musterRollId+"]");
            }
        }

    }
}
