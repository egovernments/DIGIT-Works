package org.egov.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.kafka.OrganizationProducer;
import org.egov.repository.OrganisationRepository;
import org.egov.config.Configuration;
import org.egov.tracer.model.CustomException;
import org.egov.validator.OrganisationServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.egov.util.OrganisationConstant.ORGANISATION_ENCRYPT_KEY;


@Service
@Slf4j
public class OrganisationService {

    private final OrganisationServiceValidator organisationServiceValidator;

    private final OrganisationRepository organisationRepository;

    private final OrganisationEnrichmentService organisationEnrichmentService;

    private final OrganizationProducer organizationProducer;

    private final Configuration configuration;


    private final IndividualService individualService;

    private final NotificationService notificationService;

    private final EncryptionService encryptionService;

    private final ObjectMapper mapper;

    @Autowired
    public OrganisationService(OrganisationServiceValidator organisationServiceValidator, OrganisationRepository organisationRepository, OrganisationEnrichmentService organisationEnrichmentService, OrganizationProducer organizationProducer, Configuration configuration, IndividualService individualService, NotificationService notificationService, EncryptionService encryptionService, ObjectMapper mapper) {
        this.organisationServiceValidator = organisationServiceValidator;
        this.organisationRepository = organisationRepository;
        this.organisationEnrichmentService = organisationEnrichmentService;
        this.organizationProducer = organizationProducer;
        this.configuration = configuration;
        this.individualService = individualService;
        this.notificationService = notificationService;
        this.encryptionService = encryptionService;
        this.mapper = mapper;
    }


    /**
     *
     * @param orgRequest
     * @return
     */
    public OrgRequest createOrganisationWithoutWorkFlow(OrgRequest orgRequest) {
        log.info("OrganisationService::createOrganisationWithoutWorkFlow");
        organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest);
        organisationEnrichmentService.enrichCreateOrgRegistryWithoutWorkFlow(orgRequest);
        individualService.createIndividual(orgRequest);
        OrgRequest clone;
        try {
            clone = mapper.readValue(mapper.writeValueAsString(orgRequest), OrgRequest.class);
        }catch (Exception e) {
            throw new CustomException("CLONING_ERROR", "Error while cloning");
        }
        encryptionService.encryptDetails(clone,ORGANISATION_ENCRYPT_KEY);
        organizationProducer.push(configuration.getOrgKafkaCreateTopic(), clone);
        try {
            notificationService.sendNotification(orgRequest, true);
        }catch (Exception e){
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
        individualService.updateIndividual(orgRequest);
        OrgRequest clone;
        try {
            clone = mapper.readValue(mapper.writeValueAsString(orgRequest), OrgRequest.class);
        }catch (Exception e) {
            throw new CustomException("CLONING_ERROR", "Error while cloning");
        }
        try {
            notificationService.sendNotification(orgRequest,false);
        }catch (Exception e){
            log.error("Exception while sending notification: " + e);
        }
        encryptionService.encryptDetails(clone,ORGANISATION_ENCRYPT_KEY);
        organizationProducer.push(configuration.getOrgKafkaUpdateTopic(), clone);
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
        return organisationRepository.getOrganisations(orgSearchRequest);
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
