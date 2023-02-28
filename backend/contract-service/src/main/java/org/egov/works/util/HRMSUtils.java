package org.egov.works.util;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.egov.works.util.ContractServiceConstants.HRMS_USER_ROLES_CODE;

@Component
@Slf4j
public class HRMSUtils {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ContractServiceConfiguration config;

    public List<String> getRoleCodesByEmployeeId(RequestInfo requestInfo, String tenantId, List<String> employeeIds) {
        StringBuilder url = getHRMSURI(tenantId,employeeIds);

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        Object res = serviceRequestRepository.fetchResult(url, requestInfoWrapper);

        List<String> roles = null;

        try {
            roles = JsonPath.read(res, HRMS_USER_ROLES_CODE);
        }
        catch (Exception e){
            throw new CustomException("PARSING_ERROR","Failed to parse HRMS response");
        }

        if(CollectionUtils.isEmpty(roles))
            throw new CustomException("ROLE_CODE_NOT_FOUND","For employee: "+employeeIds.toString()+" role code not found");

        return roles;
    }

    private StringBuilder getHRMSURI(String tenantId, List<String> employeeIds){

        StringBuilder builder = new StringBuilder(config.getHrmsHost());
        builder.append(config.getHrmsEndPoint());
        builder.append("?tenantId=");
        builder.append(tenantId);
        builder.append("&codes=");
        builder.append(StringUtils.join(employeeIds, ","));

        return builder;
    }

}
