package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.common.contract.user.enums.UserType;
import org.egov.common.models.core.Role;
import org.egov.common.models.individual.*;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.config.Configuration;
import org.egov.kafka.OrganizationProducer;
import org.egov.repository.OrganisationRepository;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.OrganisationConstant;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IndividualService {

    private final ObjectMapper mapper;

    private final ServiceRequestRepository serviceRequestRepository;

    private final Configuration config;

    private final OrganisationRepository organisationRepository;

    private final OrganizationProducer organizationProducer;
    private final MultiStateInstanceUtil multiStateInstanceUtil;
    private static final String ILLEGAL_ARGUMENT_EXCEPTION = "IllegalArgumentException";
    private static final String OBJECTMAPPER_CONVERSION_ERROR = "ObjectMapper not able to convertValue in individualCall";

    @Autowired
    public IndividualService(ObjectMapper mapper, ServiceRequestRepository serviceRequestRepository, Configuration config, OrganisationRepository organisationRepository, OrganizationProducer organizationProducer, MultiStateInstanceUtil multiStateInstanceUtil) {
        this.mapper = mapper;
        this.serviceRequestRepository = serviceRequestRepository;
        this.config = config;
        this.organisationRepository = organisationRepository;
        this.organizationProducer = organizationProducer;
        this.multiStateInstanceUtil = multiStateInstanceUtil;
    }

    /**
     * Creates individual for the organisation - contact details, if it is not created already
     *
     * @param request OrgRequest received for creating org registry
     */
    public void createIndividual(OrgRequest request) {
        log.info("UserService::createIndividual");
        List<Organisation> organisationList = request.getOrganisations();
        StringBuilder uri = new StringBuilder(config.getIndividualHost());
        String stateLevelTenantId = multiStateInstanceUtil.getStateLevelTenant(organisationList.get(0).getTenantId());
        RequestInfo requestInfo = request.getRequestInfo();
        List<Role> role = getOrgAdminRole();

        List<ContactDetails> contactDetailsList = new ArrayList<>();
        for (Organisation organisation : organisationList) {
            if (!CollectionUtils.isEmpty(organisation.getContactDetails())) {
                contactDetailsList.addAll(organisation.getContactDetails());
            }
        }

        for (ContactDetails contactDetails : contactDetailsList) {

            Individual newUser = Individual.builder().build();
            addIndividualDefaultFields(stateLevelTenantId, role, newUser, contactDetails, true, null);
            IndividualBulkResponse response = individualExists(contactDetails, requestInfo, Boolean.TRUE, stateLevelTenantId);
            List<Individual> existingIndividualFromService = response.getIndividual();
            IndividualResponse individualResponse;
            List<String> existingRoleCode = new ArrayList<>();
            if(!CollectionUtils.isEmpty(existingIndividualFromService) && (existingIndividualFromService.get(0).getUserDetails()!=null) && !CollectionUtils.isEmpty(existingIndividualFromService.get(0).getUserDetails().getRoles()))
                existingRoleCode = existingIndividualFromService.get(0).getUserDetails().getRoles().stream().map(Role::getCode).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(existingIndividualFromService)) {

                contactDetails.setId(UUID.randomUUID().toString());
                individualResponse = createIndividualFromIndividualService(requestInfo, newUser, contactDetails);

            } else if (!existingRoleCode.contains(getOrgAdminRole().get(0).getCode())) {
                Individual newIndividual = Individual.builder().build();
                addIndividualDefaultFields(stateLevelTenantId, role, newIndividual, contactDetails, false, existingIndividualFromService.get(0));
                uri = uri.append(config.getIndividualUpdateEndpoint());
                IndividualRequest individualRequest = IndividualRequest.builder().requestInfo(requestInfo).individual(newIndividual).build();
                individualResponse = individualUpdateCall(individualRequest, uri);
            } else {
                throw new CustomException("INDIVIDUAL.MOBILE_NUMBER",
                        "Individual's mobile number : " + contactDetails.getContactMobileNumber() + " already exists in the system");
            }
            // Assigns value of fields from user got from userDetailResponse to contact detail object
            setContactFields(contactDetails, individualResponse, requestInfo);
        }
    }

    public void updateContactDetails(ContactDetails contactDetails, String tenantId, RequestInfo requestInfo, List<Role> role) {
        IndividualBulkResponse response = individualExists(contactDetails, requestInfo, Boolean.TRUE, tenantId);
        StringBuilder uri = new StringBuilder(config.getIndividualHost());
        if (!CollectionUtils.isEmpty(response.getIndividual())) {
            Individual existingIndividual = response.getIndividual().get(0);
            Individual newIndividual = Individual.builder().build();
            addIndividualDefaultFields(tenantId, role, newIndividual, contactDetails, false, existingIndividual);
            uri = uri.append(config.getIndividualUpdateEndpoint());
            IndividualRequest individualRequest = IndividualRequest.builder().requestInfo(requestInfo).individual(newIndividual).build();
            IndividualResponse individualResponse = individualUpdateCall(individualRequest, uri);
            setContactFields(contactDetails, individualResponse, requestInfo);
        } else {
            throw new CustomException("INDIVIDUAL.UUID",
                    "Individual's UUID : " + contactDetails.getIndividualId() + " doesn't exists in the system");
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
        String stateLevelTenantId = multiStateInstanceUtil.getStateLevelTenant(organisationList.get(0).getTenantId());
        List<Role> role = getOrgAdminRole();

        OrgSearchCriteria orgSearchCriteria = OrgSearchCriteria.builder()
                .id(new ArrayList<>()).tenantId(tenantId).build();

        for(Organisation organisation : organisationList) {
            orgSearchCriteria.getId().add(organisation.getId());
        }
        OrgSearchRequest orgSearch = OrgSearchRequest.builder().requestInfo(requestInfo)
                .searchCriteria(orgSearchCriteria).build();
        List<Organisation> organisationListFromDB = organisationRepository.getOrganisations(orgSearch);

        for(int i = 0; i < organisationList.size(); i++) {
            Organisation organisation = organisationList.get(i);
            Organisation organisationFromDB = organisationListFromDB.get(i);

            // Member mobiles copied from request
            Set<String> requestMembersMobiles = organisation.getContactDetails().stream().map(ContactDetails::getContactMobileNumber).collect(Collectors.toSet());
            // Member mobiles copied from organisation object from db
            Set<String> dbMembersMobiles = organisationFromDB.getContactDetails().stream().map(ContactDetails::getContactMobileNumber).collect(Collectors.toSet());

            Set<String> toBeAddedMembersMobile = new HashSet<>(requestMembersMobiles);
            toBeAddedMembersMobile.removeAll(dbMembersMobiles);

            Set<String> toBeRemovedMembersMobile = new HashSet<>(dbMembersMobiles);
            toBeRemovedMembersMobile.removeAll(requestMembersMobiles);

            // Plainly update the individuals which are not new to the org
            Set<ContactDetails> toBeUpdatedExistingMembers = organisation.getContactDetails().stream().filter(contactDetails -> dbMembersMobiles.contains(contactDetails.getContactMobileNumber())).collect(Collectors.toSet());
            for(ContactDetails contactDetails : toBeUpdatedExistingMembers) {
                updateContactDetails(contactDetails, stateLevelTenantId, requestInfo, role);
            }

            Set<ContactDetails> newMembers = organisation.getContactDetails().stream().filter(contactDetails -> toBeAddedMembersMobile.contains(contactDetails.getContactMobileNumber())).collect(Collectors.toSet());
            for(ContactDetails contactDetails : newMembers) {
                addContactAsOrgMember(contactDetails, stateLevelTenantId, requestInfo, role);
            }

            List<Role> citizenRole = getCitizenRole();
            Set<ContactDetails> toBeRemovedMembers = organisationFromDB.getContactDetails().stream().filter(contactDetails -> toBeRemovedMembersMobile.contains(contactDetails.getContactMobileNumber())).collect(Collectors.toSet());
            for(ContactDetails contactDetails : toBeRemovedMembers) {
                updateContactDetails(contactDetails, stateLevelTenantId, requestInfo, citizenRole);
            }

            if(!newMembers.isEmpty() && !toBeRemovedMembers.isEmpty()) {
                OrgContactUpdateDiff orgContactUpdateDiff = new OrgContactUpdateDiff();
                orgContactUpdateDiff.setRequestInfo(requestInfo);
                orgContactUpdateDiff.setTenantId(tenantId);
                orgContactUpdateDiff.setOrganisationId(organisation.getId());
                orgContactUpdateDiff.setOldContacts(toBeRemovedMembers);
                orgContactUpdateDiff.setNewContacts(newMembers);
                organizationProducer.push(config.getOrganisationContactDetailsUpdateTopic(), orgContactUpdateDiff);

                log.info("For Organisation Id: " + organisation.getId() + ": Number of members to be removed: " + toBeRemovedMembers.size()
                        + "\n" + "Number of members to be added: " + newMembers.size()
                        + "\nMessage pushed to kafka");
            }
        }

    }

    private void addContactAsOrgMember(ContactDetails contactDetails, String tenantId, RequestInfo requestInfo, List<Role> role) {
        IndividualBulkResponse response = individualExists(contactDetails, requestInfo, Boolean.TRUE, tenantId);
        StringBuilder uri = new StringBuilder(config.getIndividualHost());

        if (!CollectionUtils.isEmpty(response.getIndividual())) {
            Individual existingIndividual = response.getIndividual().get(0);
            List<String> existingRoleCode = new ArrayList<>();
            if((existingIndividual.getUserDetails()!=null) && !CollectionUtils.isEmpty(existingIndividual.getUserDetails().getRoles()))
                existingRoleCode = existingIndividual.getUserDetails().getRoles().stream().map(Role::getCode).collect(Collectors.toList());
            if(existingRoleCode.contains(getOrgAdminRole().get(0).getCode())){
                throw new CustomException("USER.EXISTS", "Individual contanct number: "+contactDetails.getContactMobileNumber()+" already exists in system");
            }
            else{
                Individual newIndividual = Individual.builder().build();
                addIndividualDefaultFields(tenantId, role, newIndividual, contactDetails, false, existingIndividual);
                uri = uri.append(config.getIndividualUpdateEndpoint());
                IndividualRequest individualRequest = IndividualRequest.builder().requestInfo(requestInfo).individual(newIndividual).build();
                IndividualResponse individualResponse = individualUpdateCall(individualRequest, uri);
                setContactFields(contactDetails, individualResponse, requestInfo);
            }
        }
        else{
            Individual newUser = Individual.builder().build();
            addIndividualDefaultFields(tenantId, role, newUser, contactDetails, true, null);
            IndividualResponse individualResponse = createIndividualFromIndividualService(requestInfo, newUser, contactDetails);
            setContactFields(contactDetails, individualResponse, requestInfo);
        }
    }

    private IndividualResponse createIndividualFromIndividualService(RequestInfo requestInfo, Individual newIndividual, ContactDetails contactDetails) {
        log.info("IndividualService::createIndividualFromIndividualService");
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
    private IndividualBulkResponse individualExists(ContactDetails contactDetails, RequestInfo requestInfo, boolean isCreate, String tenantId) {
        log.info("IndividualService::Individual Exists");
        IndividualSearchRequest searchRequest = getIndividualSearchRequest(requestInfo);
        if (isCreate) {
            searchRequest.getIndividual().setMobileNumber(Collections.singletonList(contactDetails.getContactMobileNumber()));
        } else {
            searchRequest.getIndividual().setId(Collections.singletonList(contactDetails.getIndividualId()));
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
    private void addIndividualDefaultFields(String tenantId, List<Role> role, Individual individual, ContactDetails contactDetails, boolean isCreate, Individual existingIndividual) {
        log.info("IndividualService::addUserDefaultFields");
        UserDetails userDetails = UserDetails.builder().roles(role)
                .tenantId(tenantId).username(contactDetails.getContactMobileNumber())
                .userType(UserType.fromValue("CITIZEN")).build();
        individual.setMobileNumber(contactDetails.getContactMobileNumber());
        individual.setEmail(contactDetails.getContactEmail());
        individual.setName(new Name());
        individual.getName().setGivenName(contactDetails.getContactName());
        individual.setTenantId(tenantId);
        individual.setIsSystemUser(true);
        individual.setUserDetails(userDetails);
        individual.setIsSystemUserActive(true);

        if (!isCreate) {
            individual.setId(existingIndividual.getId());
            individual.setRowVersion(existingIndividual.getRowVersion());
            individual.setIndividualId(existingIndividual.getIndividualId());
            individual.setIsDeleted(false);
            individual.setIdentifiers(Collections.emptyList());
            individual.setUserId(existingIndividual.getUserId());
            individual.setUserUuid(existingIndividual.getUserUuid());
        }

        contactDetails.setActive(true);
        contactDetails.setTenantId(tenantId);
        contactDetails.setRoles(role);
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
    private List<Role> getOrgAdminRole() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder()
                .code(OrganisationConstant.ORG_ADMIN_ROLE_CODE)
                .name(OrganisationConstant.ORG_ADMIN_ROLE_NAME)
                .build());

        roles.add(Role.builder()
                .code(OrganisationConstant.VIEW_ORG_UNMASKED_CODE)
                .name(OrganisationConstant.VIEW_ORG_UNMASKED_NAME)
                .build());

        roles.add(Role.builder()
                .code(OrganisationConstant.VIEW_DED_UNMASKED_CODE)
                .name(OrganisationConstant.VIEW_DED_UNMASKED_NAME)
                .build());

        roles.add(Role.builder()
                .code(OrganisationConstant.VIEW_WS_UNMASKED_CODE)
                .name(OrganisationConstant.VIEW_WS_UNMASKED_NAME)
                .build());

        return roles;
    }
    private List<Role> getCitizenRole() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder()
                .code(OrganisationConstant.ORG_CITIZEN_TYPE)
                .name(OrganisationConstant.ORG_CITIZEN_TYPE)
                .build());

        return roles;
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
            tenantId = multiStateInstanceUtil.getStateLevelTenant(tenantId);
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url.toString())
                    .queryParam("limit",100)
                    .queryParam("offset",0)
                    .queryParam("tenantId",tenantId);
            Object response = serviceRequestRepository.fetchResult(new StringBuilder(uriBuilder.toUriString()), userRequest);

            if (response != null) {
                LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) response;
                return mapper.convertValue(responseMap, IndividualBulkResponse.class);
            } else {
                return new IndividualBulkResponse(ResponseInfo.builder().build(), 0L, new ArrayList<>());
            }
        }
        catch (Exception e) {
            throw new CustomException(ILLEGAL_ARGUMENT_EXCEPTION, OBJECTMAPPER_CONVERSION_ERROR);
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
                return mapper.convertValue(responseMap, IndividualResponse.class);
            } else {
                return new IndividualResponse(ResponseInfo.builder().build(), new Individual());
            }
        }
        catch (Exception e) {
            throw new CustomException(ILLEGAL_ARGUMENT_EXCEPTION, OBJECTMAPPER_CONVERSION_ERROR);
        }
    }

    private IndividualResponse individualUpdateCall(Object userRequest, StringBuilder url) {
        log.info("IndividualService::individualUpdateCall");
        try {

            Object response = serviceRequestRepository.fetchResult(url, userRequest);

            if (response != null) {
                LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) response;
                return mapper.convertValue(responseMap, IndividualResponse.class);
            } else {
                return new IndividualResponse(ResponseInfo.builder().build(), new Individual());
            }
        }
        catch (Exception e) {
            throw new CustomException(ILLEGAL_ARGUMENT_EXCEPTION, OBJECTMAPPER_CONVERSION_ERROR);
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
            contactDetails.setIndividualId(response.getIndividual().getId());
            contactDetails.setContactName(response.getIndividual().getName().getGivenName());
            contactDetails.setCreatedBy(requestInfo.getUserInfo().getUuid());
            contactDetails.setCreatedDate(System.currentTimeMillis());
            contactDetails.setLastModifiedBy(requestInfo.getUserInfo().getUuid());
            contactDetails.setLastModifiedDate(System.currentTimeMillis());
        }
    }

}
