package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.user.enums.UserType;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.common.models.core.Role;
import org.egov.common.models.individual.UserDetails;
import org.egov.config.Configuration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.OrganisationConstant;
import org.egov.web.models.ContactDetails;
import org.egov.web.models.CreateUserRequest;
import org.egov.web.models.OrgRequest;
import org.egov.web.models.Organisation;
import org.egov.web.models.User;
import org.egov.web.models.UserDetailResponse;
import org.egov.web.models.UserRequest;
import org.egov.web.models.individual.Individual;
import org.egov.web.models.individual.IndividualBulkResponse;
import org.egov.web.models.individual.IndividualRequest;
import org.egov.web.models.individual.IndividualResponse;
import org.egov.web.models.individual.IndividualSearch;
import org.egov.web.models.individual.IndividualSearchRequest;
import org.egov.web.models.individual.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class IndividualService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private Configuration config;

    /**
     * Creates individual for the organisation - contact details, if it is not created already
     *
     * @param request OrgRequest received for creating org registry
     */
    public void createIndividual(OrgRequest request) {
        log.info("UserService::createIndividual");
        List<Organisation> organisationList = request.getOrganisations();
        String tenantId = organisationList.get(0).getTenantId();
        //String stateLevelTenantId = getStateLevelTenant(tenantId);
        RequestInfo requestInfo = request.getRequestInfo();
        Role role = getCitizenRole();

        List<ContactDetails> contactDetailsList = new ArrayList<>();
        for (Organisation organisation : organisationList) {
            if (!CollectionUtils.isEmpty(organisation.getContactDetails())) {
                contactDetailsList.addAll(organisation.getContactDetails());
            }
        }

        for (ContactDetails contactDetails : contactDetailsList) {

            Individual newUser = Individual.builder().build();
            addIndividualDefaultFields(tenantId, role, newUser, contactDetails, true, null);
            IndividualBulkResponse response = IndividualExists(contactDetails, requestInfo, Boolean.TRUE, tenantId);
            List<Individual> existingIndividualFromService = response.getIndividual();
            IndividualResponse individualResponse;

            if (CollectionUtils.isEmpty(existingIndividualFromService)) {

                contactDetails.setId(UUID.randomUUID().toString());
                individualResponse = createIndividualFromIndividualService(requestInfo, newUser, contactDetails);

            } else {
                throw new CustomException("INDIVIDUAL.MOBILE_NUMBER",
                        "Individual's mobile number : " + contactDetails.getContactMobileNumber() + " already exists in the system");
            }
            // Assigns value of fields from user got from userDetailResponse to contact detail object
            setContactFields(contactDetails, individualResponse, requestInfo);
        }
    }

    /**
     * Updates individual if present else creates new individual
     *
     * @param request OrgRequest received from update
     */
    public void updateIndividual(OrgRequest request) {
        log.info("IndividualService::updateIndividual");
        List<Organisation> organisationList = request.getOrganisations();
        RequestInfo requestInfo = request.getRequestInfo();
        String tenantId = organisationList.get(0).getTenantId();
        //String stateLevelTenantId = getStateLevelTenant(tenantId);
        Role role = getCitizenRole();

        List<ContactDetails> contactDetailsList = new ArrayList<>();
        for (Organisation organisation : organisationList) {
            if (!CollectionUtils.isEmpty(organisation.getContactDetails())) {
                contactDetailsList.addAll(organisation.getContactDetails());
            }
        }
        contactDetailsList.forEach(contactDetails -> {

            IndividualBulkResponse response = IndividualExists(contactDetails, requestInfo, Boolean.TRUE, tenantId);

            StringBuilder uri = new StringBuilder(config.getIndividualHost());

            if (!CollectionUtils.isEmpty(response.getIndividual())) {
                Individual existingIndividual = response.getIndividual().get(0);
                Individual newIndividual = Individual.builder().build();
                addIndividualDefaultFields(tenantId, role, newIndividual, contactDetails, false, existingIndividual);
                uri = uri.append(config.getIndividualUpdateEndpoint());
                IndividualRequest individualRequest = IndividualRequest.builder().requestInfo(requestInfo).individual(newIndividual).build();
                IndividualResponse individualResponse = individualUpdateCall(individualRequest,uri);
                setContactFields(contactDetails, individualResponse, requestInfo);
           } else {
              throw new CustomException("INDIVIDUAL.UUID",
                       "Individual's UUID : " + contactDetails.getId() + " doesn't exists in the system");
            }
        });

    }

    private IndividualResponse createIndividualFromIndividualService(RequestInfo requestInfo, Individual newIndividual, ContactDetails contactDetails) {
        log.info("IndividualService::createIndividualFromIndividualService");
        IndividualResponse response;
        StringBuilder uri = new StringBuilder(config.getIndividualHost())
                .append(config.getIndividualCreateEndpoint());

        IndividualRequest individualRequest = IndividualRequest.builder().requestInfo(requestInfo).individual(newIndividual).build();

        IndividualResponse individualResponse = individualCreateCall(individualRequest, uri);

        if (ObjectUtils.isEmpty(individualResponse)) {
            throw new CustomException("INVALID Individual RESPONSE",
                    "The individual create has failed for the mobileNumber : " + contactDetails.getContactMobileNumber());
        }
        return individualResponse;
    }

    /**
     * Searches if the individual is already created. Search is based on name and mobileNumber
     *
     * @param contactDetails     ContactDetails which is to be searched
     * @param requestInfo        RequestInfo from the propertyRequest
     * @return UserDetailResponse containing the user if present and the responseInfo
     */
    private IndividualBulkResponse IndividualExists(ContactDetails contactDetails, RequestInfo requestInfo, boolean isCreate, String tenantId) {
        log.info("IndividualService::Individual Exists");
        IndividualSearchRequest searchRequest = getIndividualSearchRequest(requestInfo);
        if (isCreate) {
            searchRequest.getIndividual().setMobileNumber(contactDetails.getContactMobileNumber());
            searchRequest.getIndividual().setName(new Name());
            searchRequest.getIndividual().getName().setGivenName(contactDetails.getContactName());
        } else {
            searchRequest.getIndividual().setId(Collections.singletonList(contactDetails.getId()));
        }
        StringBuilder uri = new StringBuilder(config.getIndividualHost()).append(config.getIndividualSearchEndpoint());
        return individualSearchCall(searchRequest, uri,tenantId);
    }


    /**
     * Sets the role,type,active and tenantId for a Citizen
     *
     * @param tenantId
     * @param role
     * @param individual
     * @param contactDetails
     */
    private void addIndividualDefaultFields(String tenantId, Role role, Individual individual, ContactDetails contactDetails, boolean isCreate, Individual existingIndividual) {
        log.info("IndividualService::addUserDefaultFields");
        UserDetails userDetails = UserDetails.builder().roles(Collections.singletonList(role))
                .tenantId(tenantId.split("\\.")[0]).username(contactDetails.getContactMobileNumber())
                .userType(UserType.fromValue("CITIZEN")).build();
        individual.setMobileNumber(contactDetails.getContactMobileNumber());
        individual.setEmail(contactDetails.getContactEmail());
        individual.setName(new Name());
        individual.getName().setGivenName(contactDetails.getContactName());
        individual.setTenantId(tenantId.split("\\.")[0]);
        individual.setIsSystemUser(true);
        individual.setUserDetails(userDetails);
        /*user.setType(UserType.CITIZEN);
        user.setRoles(Collections.singleton(role));
        user.setActive(Boolean.TRUE);
        user.setUsername(contactDetails.getContactMobileNumber());*/
        if (!isCreate) {
            individual.setId(existingIndividual.getId());
            individual.setRowVersion(existingIndividual.getRowVersion());
            individual.setIndividualId(existingIndividual.getIndividualId());
            individual.setIsDeleted(false);
            individual.setIdentifiers(Collections.emptyList());
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
                .code(OrganisationConstant.ORG_CITIZEN_ROLE_CODE)
                .name(OrganisationConstant.ORG_CITIZEN_ROLE_NAME)
                .build();
    }

    /**
     * provides  individual search request
     *
     * @param requestInfo
     * @return
     */
    public IndividualSearchRequest getIndividualSearchRequest(RequestInfo requestInfo) {
        log.info("IndividualService::getIndividualSearchRequest");
        IndividualSearchRequest searchRequest = new IndividualSearchRequest();
        IndividualSearch individualSearch = new IndividualSearch();
        searchRequest.setRequestInfo(requestInfo);
        searchRequest.setIndividual(individualSearch);
        return searchRequest;
    }

    /**
     * Returns IndividualResponse by calling individual service with given uri and object
     *
     * @param userRequest Request object for user service
     * @param url         The address of the endpoint
     * @return Response from user service parsed as individualResponse
     */
    private IndividualBulkResponse individualSearchCall(Object userRequest, StringBuilder url, String tenantId) {
        log.info("IndividualService::individualSearchCall");
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url.toString())
                    .queryParam("limit",100)
                    .queryParam("offset",0)
                    .queryParam("tenantId",tenantId);
            Object response = serviceRequestRepository.fetchResult(new StringBuilder(uriBuilder.toUriString()), userRequest);

            if (response != null) {
                LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) response;
                IndividualBulkResponse individualResponse = mapper.convertValue(responseMap, IndividualBulkResponse.class);
                return individualResponse;
            } else {
                return new IndividualBulkResponse(ResponseInfo.builder().build(), new ArrayList<>());
            }
        }
        catch (Exception e) {
            throw new CustomException("IllegalArgumentException", "ObjectMapper not able to convertValue in individualCall");
        }
    }

    private IndividualResponse individualCreateCall(Object userRequest, StringBuilder url) {
        log.info("IndividualService::individualCreateCall");
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url.toString())
                    .queryParam("isSystemUser","true");

            Object response = serviceRequestRepository.fetchResult(new StringBuilder(uriBuilder.toUriString()), userRequest);

            if (response != null) {
                LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) response;
                IndividualResponse individualResponse = mapper.convertValue(responseMap, IndividualResponse.class);
                return individualResponse;
            } else {
                return new IndividualResponse(ResponseInfo.builder().build(), new Individual());
            }
        }
        catch (Exception e) {
            throw new CustomException("IllegalArgumentException", "ObjectMapper not able to convertValue in individualCall");
        }
    }

    private IndividualResponse individualUpdateCall(Object userRequest, StringBuilder url) {
        log.info("IndividualService::individualUpdateCall");
        try {

            Object response = serviceRequestRepository.fetchResult(url, userRequest);

            if (response != null) {
                LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) response;
                IndividualResponse individualResponse = mapper.convertValue(responseMap, IndividualResponse.class);
                return individualResponse;
            } else {
                return new IndividualResponse(ResponseInfo.builder().build(), new Individual());
            }
        }
        catch (Exception e) {
            throw new CustomException("IllegalArgumentException", "ObjectMapper not able to convertValue in individualCall");
        }
    }

    /**
     * Set contact fields (so that the contact table can be linked to user table)
     *
     * @param contactDetails     contact details in the org whose user is created
     * @param response IndividualResponse from the individual Service corresponding to the given contact details
     */
    private void setContactFields(ContactDetails contactDetails, IndividualResponse response, RequestInfo requestInfo) {
        log.info("IndividualService::setContactFields");
        if (response != null && response.getIndividual() != null) {
            contactDetails.setId(response.getIndividual().getId());
            contactDetails.setContactName(response.getIndividual().getName().getGivenName());
            contactDetails.setCreatedBy(requestInfo.getUserInfo().getUuid());
            contactDetails.setCreatedDate(System.currentTimeMillis());
            contactDetails.setLastModifiedBy(requestInfo.getUserInfo().getUuid());
            contactDetails.setLastModifiedDate(System.currentTimeMillis());
            //contactDetails.setActive(userDetailResponse.getUser().get(0).getActive());
        }
    }


}
