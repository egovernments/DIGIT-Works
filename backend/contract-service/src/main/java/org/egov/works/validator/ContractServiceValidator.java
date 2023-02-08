package org.egov.works.validator;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.util.MdmsUtil;
import org.egov.works.web.models.ContractCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.works.util.ContractServiceConstants.MASTER_TENANTS;
import static org.egov.works.util.ContractServiceConstants.MDMS_TENANT_MODULE_NAME;

@Component
@Slf4j
public class ContractServiceValidator {

    @Autowired
    private MdmsUtil mdmsUtils;

    public void validateSearchContractRequest(RequestInfoWrapper requestInfoWrapper, ContractCriteria contractCriteria) {

        if (contractCriteria == null || requestInfoWrapper == null) {
            log.error("Contract search criteria request is mandatory");
            throw new CustomException("CONTRACT_SEARCH_CRITERIA_REQUEST", "Contract search criteria request is mandatory");
        }

        //validate request info
        RequestInfo requestInfo=requestInfoWrapper.getRequestInfo();
        validateRequestInfo(requestInfo);

        //validate request parameters
        validateSearchContractRequestParameters(contractCriteria);

        //validate tenantId with MDMS
        validateTenantIdWithMDMS(requestInfo,contractCriteria);

    }

    private void validateSearchContractRequestParameters(ContractCriteria contractCriteria){

        if (StringUtils.isBlank(contractCriteria.getTenantId())) {
            log.error("Tenant is mandatory");
            throw new CustomException("TENANT_ID", "Tenant is mandatory");
        }
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
    }

    public void validateTenantIdWithMDMS(RequestInfo requestInfo,ContractCriteria contractCriteria){

        String tenantId = contractCriteria.getTenantId();
        Map<String,String> errorMap=new HashMap<>();

        //split the tenantId
        String rootTenantId = tenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(requestInfo, rootTenantId);

        //check tenant Id
        validateMDMSData(tenantId, mdmsData,errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    private void validateMDMSData(String tenantId, Object mdmsData, Map<String, String> errorMap) {
        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";

        List<Object> tenantRes = null;
        try {
            tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        if (CollectionUtils.isEmpty(tenantRes))
            errorMap.put("INVALID_TENANT", "The tenant: " + tenantId + " is not present in MDMS");
    }
}
