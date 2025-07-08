package org.egov.digit.expense.util;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.digit.expense.config.Constants.*;

@Component
@Slf4j
public class HRMSUtils {
    private final ServiceRequestRepository serviceRequestRepository;

    private final Configuration config;

    @Autowired
    public HRMSUtils(ServiceRequestRepository serviceRequestRepository, Configuration config) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.config = config;
    }


    public Map<String, String> getEmployeeDetailsByUuid(RequestInfo requestInfo, String tenantId, String uuid) {
        StringBuilder url = getHRMSURIWithUUid(tenantId, uuid);

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        Object res = serviceRequestRepository.fetchResult(url, requestInfoWrapper);

        Map<String, String> userDetailsForSMS = new HashMap<>();
        List<String> userNames = null;
        List<String> mobileNumbers = null;

        try {
            userNames = JsonPath.read(res, HRMS_USER_USERNAME_CODE);
            mobileNumbers = JsonPath.read(res, HRMS_USER_MOBILE_NO);

        } catch (Exception e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse HRMS response");
        }

        userDetailsForSMS.put("userName", userNames.get(0));
        userDetailsForSMS.put("mobileNumber", mobileNumbers.get(0));

        return userDetailsForSMS;
    }

    private StringBuilder getHRMSURIWithUUid(String tenantId, String employeeUuid) {

        StringBuilder builder = new StringBuilder(config.getHrmsHost());
        builder.append(config.getHrmsEndPoint());
        builder.append("?tenantId=");
        builder.append(tenantId);
        builder.append("&uuids=");
        builder.append(employeeUuid);

        return builder;
    }

}



