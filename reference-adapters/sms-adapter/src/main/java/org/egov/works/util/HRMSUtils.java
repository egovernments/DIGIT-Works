package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.NotificationServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.works.config.Constants.*;

@Component
@Slf4j
public class HRMSUtils {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private NotificationServiceConfiguration config;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    public List<String> getRoleCodesByEmployeeId(RequestInfo requestInfo, String tenantId, List<String> employeeIds) {
        StringBuilder url = getHRMSURI(tenantId, employeeIds);

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        Object res = serviceRequestRepository.fetchResult(url, requestInfoWrapper);

        List<String> roles = null;

        try {
            roles = JsonPath.read(res, HRMS_USER_ROLES_CODE);
        } catch (Exception e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse HRMS response");
        }

        if (CollectionUtils.isEmpty(roles))
            throw new CustomException("ROLE_CODE_NOT_FOUND", "For employee: " + employeeIds.toString() + " role code not found");

        return roles;
    }

    public Map<String, String> getEmployeeDetailsByUuid(RequestInfo requestInfo, String tenantId, String uuid) {
        StringBuilder url = getHRMSURIWithUUid(tenantId, uuid);

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        Object res = serviceRequestRepository.fetchResult(url, requestInfoWrapper);

        Map<String, String> userDetailsForSMS = new HashMap<>();
        List<String> userNames = null;
        List<String> mobileNumbers = null;
        List<String> designations=null;

        try {
            designations = JsonPath.read(res, HRMS_USER_DESIGNATION);
            userNames = JsonPath.read(res, HRMS_USER_USERNAME_CODE);
            mobileNumbers = JsonPath.read(res, HRMS_USER_MOBILE_NO);

        } catch (Exception e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse HRMS response");
        }

        userDetailsForSMS.put("userName", userNames.get(0));
        userDetailsForSMS.put("mobileNumber", mobileNumbers.get(0));
        userDetailsForSMS.put("designation", designations.get(0));

        return userDetailsForSMS;
    }

    public Map<String, String> getEmployeeDetailsByCode(RequestInfo requestInfo, String tenantId, String code) {
        StringBuilder url = getHRMSURIWithCode(tenantId,code);

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        Object res = serviceRequestRepository.fetchResult(url, requestInfoWrapper);

        Map<String, String> userDetailsForSMS = new HashMap<>();
        List<String> userNames = null;
        List<String> mobileNumbers = null;
        List<String> designations=null;

        try {
            designations = JsonPath.read(res, HRMS_USER_DESIGNATION);
            userNames = JsonPath.read(res, HRMS_USER_USERNAME_CODE);
            mobileNumbers = JsonPath.read(res, HRMS_USER_MOBILE_NO);

        } catch (Exception e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse HRMS response");
        }

        userDetailsForSMS.put("userName", userNames.get(0));
        userDetailsForSMS.put("mobileNumber", mobileNumbers.get(0));
        userDetailsForSMS.put("designation", designations.get(0));

        return userDetailsForSMS;
    }

    private StringBuilder getHRMSURIWithCode(String tenantId, String code) {

        StringBuilder builder = new StringBuilder(config.getHrmsHost());
        builder.append(config.getHrmsEndPoint());
        builder.append("?tenantId=");
        builder.append(tenantId);
        builder.append("&codes=");
        builder.append(code);

        return builder;
    }

    private StringBuilder getHRMSURI(String tenantId, List<String> employeeIds) {

        StringBuilder builder = new StringBuilder(config.getHrmsHost());
        builder.append(config.getHrmsEndPoint());
        builder.append("?tenantId=");
        builder.append(tenantId);
        builder.append("&codes=");
        builder.append(StringUtils.join(employeeIds, ","));

        return builder;
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
