package org.egov.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.request.Role;
import org.egov.common.contract.request.User;
import org.egov.common.contract.user.UserDetailResponse;
import org.egov.common.contract.user.UserSearchRequest;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class UserUtil {

    private final ObjectMapper mapper;

    private final ServiceRequestRepository serviceRequestRepository;

    private final MusterRollServiceConfiguration config;

    private static final String LAST_MODIFIED_DATE = "lastModifiedDate";
    private static final String PWD_EXPIRY_DATE = "pwdExpiryDate";

    @Autowired
    public UserUtil(ObjectMapper mapper, ServiceRequestRepository serviceRequestRepository, MusterRollServiceConfiguration config) {
        this.mapper = mapper;
        this.serviceRequestRepository = serviceRequestRepository;
        this.config = config;
    }

    /**
     * Returns UserDetailResponse by calling user service with given uri and object
     *
     * @param userRequest Request object for user service
     * @param uri         The address of the endpoint
     * @return Response from user service as parsed as userDetailResponse
     */

    public UserDetailResponse userCall(Object userRequest, StringBuilder uri) {
        String dobFormat = null;
        if (uri.toString().contains(config.getUserSearchEndpoint()) || uri.toString().contains(config.getUserUpdateEndpoint()))
            dobFormat = "yyyy-MM-dd";
        else if (uri.toString().contains(config.getUserCreateEndpoint()))
            dobFormat = "dd/MM/yyyy";
        try {
            LinkedHashMap responseMap = (LinkedHashMap) serviceRequestRepository.fetchResult(uri, userRequest);
            parseResponse(responseMap, dobFormat);
            return mapper.convertValue(responseMap, UserDetailResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException("IllegalArgumentException", "ObjectMapper not able to convertValue in userCall");
        }
    }

    /**
      * Searches for users by their individual IDs
      *
      * @param individualIds List of individual IDs to search for
      * @return UserDetailResponse containing the matched users
      * @throws CustomException if individualIds is null or empty
     * */
    public UserDetailResponse searchUsersByIndividualIds(List<String> individualIds) {
        if (individualIds == null || individualIds.isEmpty()) {
            throw new CustomException("INVALID_INPUT", "Individual IDs list cannot be null or empty");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(config.getUserHost())
                .append(config.getUserContextPath())
                .append(config.getUserSearchEndpoint());
        UserSearchRequest userSearchRequest = new UserSearchRequest();
        userSearchRequest.setUuid(individualIds);
        return userCall(userSearchRequest, stringBuilder);
    }


    /**
     * Parses date formats to long for all users in responseMap
     *
     * @param responeMap LinkedHashMap got from user api response
     */

    public void parseResponse(LinkedHashMap responeMap, String dobFormat) {
        List<LinkedHashMap> users = (List<LinkedHashMap>) responeMap.get("user");
        String format1 = "dd-MM-yyyy HH:mm:ss";
        if (users != null) {
            users.forEach(map -> {
                        map.put("createdDate", dateTolong((String) map.get("createdDate"), format1));
                        if ((String) map.get(LAST_MODIFIED_DATE) != null)
                            map.put(LAST_MODIFIED_DATE, dateTolong((String) map.get(LAST_MODIFIED_DATE), format1));
                        if ((String) map.get("dob") != null)
                            map.put("dob", dateTolong((String) map.get("dob"), dobFormat));
                        if ((String) map.get(PWD_EXPIRY_DATE) != null)
                            map.put(PWD_EXPIRY_DATE, dateTolong((String) map.get(PWD_EXPIRY_DATE), format1));
                    }
            );
        }
    }

    /**
     * Converts date to long
     *
     * @param date   date to be parsed
     * @param format Format of the date
     * @return Long value of date
     */
    private Long dateTolong(String date, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = f.parse(date);
        } catch (ParseException e) {
            throw new CustomException("INVALID_DATE_FORMAT", "Failed to parse date format in user");
        }
        return d.getTime();
    }

    /**
     * enriches the userInfo with statelevel tenantId and other fields
     *
     * @param mobileNumber
     * @param tenantId
     * @param userInfo
     */
    public void addUserDefaultFields(String mobileNumber, String tenantId, User userInfo) {
        Role role = getCitizenRole(tenantId);
        userInfo.setRoles(Collections.singletonList(role));
        userInfo.setType("CITIZEN");
        userInfo.setUserName(mobileNumber);
        userInfo.setTenantId(getStateLevelTenant(tenantId));
    }

    /**
     * Returns role object for citizen
     *
     * @param tenantId
     * @return
     */
    private Role getCitizenRole(String tenantId) {
        Role role = new Role();
        role.setCode("CITIZEN");
        role.setName("Citizen");
        role.setTenantId(getStateLevelTenant(tenantId));
        return role;
    }

    public String getStateLevelTenant(String tenantId) {
        return tenantId.split("\\.")[0];
    }

}