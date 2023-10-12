package org.egov.service;


import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.OrganisationRepository;
import org.egov.config.Configuration;
import org.egov.kafka.Producer;
import org.egov.util.EncryptionDecryptionUtil;
import org.egov.validator.OrganisationServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class OrganisationService {
    public static final String ORGANISATION_ENCRYPT_KEY = "Organisation";
    @Autowired
    private OrganisationServiceValidator organisationServiceValidator;
    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private OrganisationEnrichmentService organisationEnrichmentService;

    @Autowired
    private Producer producer;

    @Autowired
    private Configuration configuration;

    @Autowired
    private UserService userService;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private EncryptionService encryptionService;


    /**
     *
     * @param orgRequest
     * @return
     */
    public OrgRequest createOrganisationWithoutWorkFlow(OrgRequest orgRequest) {
        log.info("OrganisationService::createOrganisationWithoutWorkFlow");
        organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest);
        organisationEnrichmentService.enrichCreateOrgRegistryWithoutWorkFlow(orgRequest);
        //userService.createUser(orgRequest);
        individualService.createIndividual(orgRequest);
//        OrgRequest clonedOrgRequest = new OrgRequest();
//
//        clonedOrgRequest.setOrganisations(orgRequest.getOrganisations());
//        clonedOrgRequest.setRequestInfo(orgRequest.getRequestInfo());
//
//        List<Organisation> organisationList = orgRequest.getOrganisations();
//        List<Organisation> clonedOrganisations = new ArrayList<>();
//        for(Organisation organisation: organisationList){
//            Organisation clonedOrg = new Organisation();
//            List<ContactDetails> contactDetailsList = organisation.getContactDetails();
//            List<ContactDetails> CopyContactDetails = new ArrayList<>();
//            for(ContactDetails originalContact: contactDetailsList){
//                ContactDetails clonedContact = new ContactDetails();
//                clonedContact.setContactName(originalContact.getContactName());
//                clonedContact.setContactMobileNumber(originalContact.getContactMobileNumber());
//                clonedContact.setId(originalContact.getId());
//                clonedContact.setContactEmail(originalContact.getContactEmail());
//
//                CopyContactDetails.add(clonedContact);
//            }
//            clonedOrg.setContactDetails(CopyContactDetails);
//            clonedOrganisations.add(clonedOrg);
//            clonedOrgRequest.getOrganisations().get(0).setContactDetails(CopyContactDetails);
//        }
//
//        clonedOrgRequest.setOrganisations(clonedOrganisations);


//        OrgRequest orgRequest1 = new OrgRequest();
//        orgRequest1.setOrganisations(orgRequest.getOrganisations());
//        orgRequest1.setRequestInfo(orgRequest.getRequestInfo());

            // Create a new OrgRequest for the deep copy
            OrgRequest clonedOrgRequest = new OrgRequest();

            // Set the RequestInfo
            clonedOrgRequest.setRequestInfo(orgRequest.getRequestInfo());

            // Create a new list for cloned Organisations
            List<Organisation> clonedOrganisations = new ArrayList<>();

            // Iterate through the Organisations in orgRequest
            for (Organisation originalOrg : orgRequest.getOrganisations()) {
                // Create a new Organisation for the deep copy
                Organisation clonedOrg = new Organisation();

                // Copy primitive properties from the original Organisation
                clonedOrg.setTenantId(originalOrg.getTenantId());
                clonedOrg.setName(originalOrg.getName());
                clonedOrg.setApplicationStatus(originalOrg.getApplicationStatus());
                clonedOrg.setDateOfIncorporation(originalOrg.getDateOfIncorporation());

                // Create a new list for cloned ContactDetails
                List<ContactDetails> clonedContactDetails = new ArrayList<>();

                // Iterate through the ContactDetails in the original Organisation
                for (ContactDetails originalContact : originalOrg.getContactDetails()) {
                    // Create a new ContactDetails for the deep copy
                    ContactDetails clonedContact = new ContactDetails();

                    // Copy properties from the original ContactDetails
                    clonedContact.setContactName(originalContact.getContactName());
                    clonedContact.setContactMobileNumber(originalContact.getContactMobileNumber());
                    clonedContact.setContactEmail(originalContact.getContactEmail());
                    clonedContact.setId(originalContact.getId());

                    // Add the cloned ContactDetails to the list
                    clonedContactDetails.add(clonedContact);
                }

                // Set the cloned ContactDetails list in the cloned Organisation
                clonedOrg.setContactDetails(clonedContactDetails);

                // Copy the remaining arrays from the original Organisation without deep copying
                clonedOrg.setOrgAddress(originalOrg.getOrgAddress());
                clonedOrg.setAdditionalDetails(originalOrg.getAdditionalDetails());
                clonedOrg.setFunctions(originalOrg.getFunctions());
                clonedOrg.setIdentifiers(originalOrg.getIdentifiers());

                // Add the cloned Organisation to the list of cloned Organisations
                clonedOrganisations.add(clonedOrg);
            }

            // Set the list of cloned Organisations in the cloned OrgRequest
            clonedOrgRequest.setOrganisations(clonedOrganisations);




        encryptionService.encryptDetails(clonedOrgRequest,ORGANISATION_ENCRYPT_KEY);
        producer.push(configuration.getOrgKafkaCreateTopic(), clonedOrgRequest);
        try {
            notificationService.sendNotification(orgRequest, true);
        }catch (Exception e) {
            log.error("Exception while sending notification: " + e);
        }
        return orgRequest;
    }

    /**
     *
     * @param orgRequest
     * @return
     */
    public OrgRequest updateOrganisationWithoutWorkFlow(OrgRequest orgRequest) {
        log.info("OrganisationService::updateOrganisationWithoutWorkFlow");
        organisationServiceValidator.validateUpdateOrgRegistryWithoutWorkFlow(orgRequest);
        organisationEnrichmentService.enrichUpdateOrgRegistryWithoutWorkFlow(orgRequest);
        //userService.updateUser(orgRequest);
        individualService.updateIndividual(orgRequest);
        try {
            notificationService.sendNotification(orgRequest,false);
        }catch (Exception e){
            log.error("Exception while sending notification: " + e);
        }
        encryptionService.encryptDetails(orgRequest,ORGANISATION_ENCRYPT_KEY);
        producer.push(configuration.getOrgKafkaUpdateTopic(), orgRequest);
        return orgRequest;
    }

    /**
     *
     * @param orgSearchRequest
     * @return
     */
    public List<Organisation> searchOrganisation(OrgSearchRequest orgSearchRequest) {
        log.info("OrganisationService::searchOrganisationWithoutWorkFlow");
        organisationServiceValidator.validateSearchOrganisationRequest(orgSearchRequest);
        encryptionService.encryptDetails(orgSearchRequest,ORGANISATION_ENCRYPT_KEY);
        List<Organisation> organisations = organisationRepository.getOrganisations(orgSearchRequest);
        encryptionService.decrypt(organisations,ORGANISATION_ENCRYPT_KEY,orgSearchRequest);
        return organisations;
    }

    /**
     *
     * @param orgSearchRequest
     * @return
     */
    public Integer countAllOrganisations(OrgSearchRequest orgSearchRequest) {
        return organisationRepository.getOrganisationsCount(orgSearchRequest);
    }
}



