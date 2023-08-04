package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.user.enums.UserType;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.common.models.core.Role;
import org.egov.config.Configuration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.OrganisationConstant;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class UserService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private Configuration config;

    /**
     * Creates user of the organisation - contact details, if it is not created already
     *
     * @param request OrgRequest received for creating org registry
     */
    public void createUser(OrgRequest request) {
        log.info("UserService::createUser");
        List<Organisation> organisationList = request.getOrganisations();
        String tenantId = organisationList.get(0).getTenantId();
        String stateLevelTenantId = getStateLevelTenant(tenantId);
        RequestInfo requestInfo = request.getRequestInfo();
        Role role = getCitizenRole();

        List<ContactDetails> contactDetailsList = new ArrayList<>();
        for (Organisation organisation : organisationList) {
            if (!CollectionUtils.isEmpty(organisation.getContactDetails())) {
                contactDetailsList.addAll(organisation.getContactDetails());
            }
        }

        for (ContactDetails contactDetails : contactDetailsList) {

            User newUser = User.builder().build();
            addUserDefaultFields(stateLevelTenantId, role, newUser, contactDetails, true);
            UserDetailResponse userDetailResponse = userExists(contactDetails, stateLevelTenantId, requestInfo, Boolean.TRUE);
            List<UserRequest> existingUsersFromService = userDetailResponse.getUser();

            if (CollectionUtils.isEmpty(existingUsersFromService)) {

                contactDetails.setId(UUID.randomUUID().toString());
                userDetailResponse = createUserFromUserService(requestInfo, newUser, contactDetails);

            } else {
                throw new CustomException("USER.MOBILE_NUMBER",
                        "User's mobile number : " + contactDetails.getContactMobileNumber() + " already exists in the system");
            }
            // Assigns value of fields from user got from userDetailResponse to contact detail object
            setContactFields(contactDetails, userDetailResponse, requestInfo);
        }
    }


    private UserDetailResponse createUserFromUserService(RequestInfo requestInfo, User newUser, ContactDetails contactDetails) {
        log.info("UserService::createUserFromUserService");
        UserDetailResponse userDetailResponse;
        StringBuilder uri = new StringBuilder(config.getUserHost())
                .append(config.getUserContextPath())
                .append(config.getUserCreateEndpoint());

        UserRequest userRequest = new UserRequest(newUser);
        CreateUserRequest createUserRequest = new CreateUserRequest(requestInfo, userRequest);

        userDetailResponse = userCall(createUserRequest, uri);

        if (ObjectUtils.isEmpty(userDetailResponse)) {
            throw new CustomException("INVALID USER RESPONSE",
                    "The user create has failed for the mobileNumber : " + contactDetails.getContactMobileNumber());
        }
        return userDetailResponse;
    }


    /**
     * Sets the role,type,active and tenantId for a Citizen
     *
     * @param tenantId
     * @param role
     * @param user
     * @param contactDetails
     */
    private void addUserDefaultFields(String tenantId, Role role, User user, ContactDetails contactDetails, boolean isCreate) {
        log.info("UserService::addUserDefaultFields");
        user.setMobileNumber(contactDetails.getContactMobileNumber());
        user.setEmailId(contactDetails.getContactEmail());
        user.setName(contactDetails.getContactName());
        user.setTenantId(tenantId);
        user.setType(UserType.CITIZEN);
        user.setRoles(Collections.singleton(role));
        user.setActive(Boolean.TRUE);
        user.setUsername(contactDetails.getContactMobileNumber());
        if (!isCreate) {
            user.setUuid(contactDetails.getId());
        }

        contactDetails.setActive(true);
        contactDetails.setTenantId(tenantId);
        contactDetails.setRoles(Collections.singletonList(role));
        contactDetails.setType(OrganisationConstant.ORG_CITIZEN_TYPE);
        contactDetails.setCreatedDate(null);
        contactDetails.setCreatedBy(null);
        contactDetails.setLastModifiedDate(null);
        contactDetails.setLastModifiedBy(null);
    }

    /**
     * this is will be hardcoded from code level as we have fix CITIZEN role
     * @return
     */
    private Role getCitizenRole() {
        return Role.builder()
                .code(OrganisationConstant.ORG_ADMIN_ROLE_CODE)
                .name(OrganisationConstant.ORG_ADMIN_ROLE_NAME)
                .build();
    }

    /**
     * Searches if the citizen is already created. Search is based on mobileNumber
     *
     * @param contactDetails     ContactDetails which is to be searched
     * @param stateLevelTenantId
     * @param requestInfo        RequestInfo from the propertyRequest
     * @return UserDetailResponse containing the user if present and the responseInfo
     */
    private UserDetailResponse userExists(ContactDetails contactDetails, String stateLevelTenantId, RequestInfo requestInfo, boolean isCreate) {
        log.info("UserService::userExists");
        UserSearchRequest userSearchRequest = getBaseUserSearchRequest(stateLevelTenantId, requestInfo);
        if (isCreate) {
            userSearchRequest.setMobileNumber(contactDetails.getContactMobileNumber());
        } else {
            userSearchRequest.setUuid(Collections.singletonList(contactDetails.getId()));
        }
        StringBuilder uri = new StringBuilder(config.getUserHost()).append(config.getUserSearchEndpoint());
        return userCall(userSearchRequest, uri);
    }

    /**
     * Returns UserDetailResponse by calling user service with given uri and object
     *
     * @param userRequest Request object for user service
     * @param url         The address of the endpoint
     * @return Response from user service parsed as userDetailResponse
     */
    @SuppressWarnings("unchecked")
    private UserDetailResponse userCall(Object userRequest, StringBuilder url) {
        log.info("UserService::userCall");
        String dobFormat = null;
        if (url.indexOf(config.getUserSearchEndpoint()) != -1 || url.indexOf(config.getUserUpdateEndpoint()) != -1)
            dobFormat = "yyyy-MM-dd";
        else if (url.indexOf(config.getUserCreateEndpoint()) != -1)
            dobFormat = "dd/MM/yyyy";
        try {
            Object response = serviceRequestRepository.fetchResult(url, userRequest);

            if (response != null) {
                LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) response;
                parseResponse(responseMap, dobFormat);
                UserDetailResponse userDetailResponse = mapper.convertValue(responseMap, UserDetailResponse.class);
                return userDetailResponse;
            } else {
                return new UserDetailResponse(ResponseInfo.builder().build(), new ArrayList<>());
            }
        }
        // Which Exception to throw?
        catch (IllegalArgumentException e) {
            throw new CustomException("IllegalArgumentException", "ObjectMapper not able to convertValue in userCall");
        }
    }


    /**
     * Parses date formats to long for all users in responseMap
     *
     * @param responeMap LinkedHashMap got from user api response
     * @param dobFormat  dob format (required because dob is returned in different format's in search and create response in user service)
     */
    @SuppressWarnings("unchecked")
    private void parseResponse(LinkedHashMap<String, Object> responeMap, String dobFormat) {

        List<LinkedHashMap<String, Object>> users = (List<LinkedHashMap<String, Object>>) responeMap.get("user");
        String format1 = "dd-MM-yyyy HH:mm:ss";

        if (null != users) {

            users.forEach(map -> {

                        map.put("createdDate", dateTolong((String) map.get("createdDate"), format1));
                        if ((String) map.get("lastModifiedDate") != null)
                            map.put("lastModifiedDate", dateTolong((String) map.get("lastModifiedDate"), format1));
                        if ((String) map.get("dob") != null)
                            map.put("dob", dateTolong((String) map.get("dob"), dobFormat));
                        if ((String) map.get("pwdExpiryDate") != null)
                            map.put("pwdExpiryDate", dateTolong((String) map.get("pwdExpiryDate"), format1));
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
            e.printStackTrace();
        }
        return d.getTime();
    }

    /**
     * Set contact fields (so that the contact table can be linked to user table)
     *
     * @param contactDetails     contact details in the org whose user is created
     * @param userDetailResponse userDetailResponse from the user Service corresponding to the given contact details
     */
    private void setContactFields(ContactDetails contactDetails, UserDetailResponse userDetailResponse, RequestInfo requestInfo) {
        log.info("UserService::setContactFields");
        if (userDetailResponse != null && !CollectionUtils.isEmpty(userDetailResponse.getUser())) {
            contactDetails.setId(userDetailResponse.getUser().get(0).getUuid());
            contactDetails.setContactName((userDetailResponse.getUser().get(0).getName()));
            contactDetails.setCreatedBy(requestInfo.getUserInfo().getUuid());
            contactDetails.setCreatedDate(System.currentTimeMillis());
            contactDetails.setLastModifiedBy(requestInfo.getUserInfo().getUuid());
            contactDetails.setLastModifiedDate(System.currentTimeMillis());
            //contactDetails.setActive(userDetailResponse.getUser().get(0).getActive());
        }
    }


    /**
     * Updates user if present else creates new user
     *
     * @param request OrgRequest received from update
     */
    public void updateUser(OrgRequest request) {
        log.info("UserService::updateUser");
        List<Organisation> organisationList = request.getOrganisations();
        RequestInfo requestInfo = request.getRequestInfo();
        String tenantId = organisationList.get(0).getTenantId();
        String stateLevelTenantId = getStateLevelTenant(tenantId);
        Role role = getCitizenRole();

        List<ContactDetails> contactDetailsList = new ArrayList<>();
        for (Organisation organisation : organisationList) {
            if (!CollectionUtils.isEmpty(organisation.getContactDetails())) {
                contactDetailsList.addAll(organisation.getContactDetails());
            }
        }
        contactDetailsList.forEach(contactDetails -> {

            User newUser = User.builder().build();
            addUserDefaultFields(stateLevelTenantId, role, newUser, contactDetails, false);

            UserDetailResponse userDetailResponse = userExists(contactDetails, stateLevelTenantId, requestInfo, Boolean.FALSE);

            StringBuilder uri = new StringBuilder(config.getUserHost());

            if (!CollectionUtils.isEmpty(userDetailResponse.getUser())) {
                newUser.setId(userDetailResponse.getUser().get(0).getId());
                uri = uri.append(config.getUserContextPath()).append(config.getUserUpdateEndpoint());
                UserRequest userRequest = new UserRequest(newUser);
                userDetailResponse = userCall(new CreateUserRequest(requestInfo, userRequest), uri);
                setContactFields(contactDetails, userDetailResponse, requestInfo);
            } else {
                throw new CustomException("USER.UUID",
                        "User's UUID : " + contactDetails.getId() + " doesn't exists in the system");
            }
        });

    }

    /**
     * provides a user search request with basic mandatory parameters
     *
     * @param tenantId
     * @param requestInfo
     * @return
     */
    public UserSearchRequest getBaseUserSearchRequest(String tenantId, RequestInfo requestInfo) {
        log.info("UserService::getBaseUserSearchRequest");
        UserSearchRequest userSearchRequest = new UserSearchRequest();
        userSearchRequest.setRequestInfo(requestInfo);
        userSearchRequest.setUserType(OrganisationConstant.ORG_CITIZEN_TYPE);
        userSearchRequest.setTenantId(tenantId);
        userSearchRequest.setActive(Boolean.TRUE);
        return userSearchRequest;
    }

    private String getStateLevelTenant(String tenantId) {
        log.info("UserService::getStateLevelTenant");
        return tenantId.split("\\.")[0];
    }

}
