package org.egov.service;


import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.OrganisationRepository;
import org.egov.config.Configuration;
import org.egov.kafka.Producer;
import org.egov.validator.OrganisationServiceValidator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class OrganisationService {

    private final OrganisationServiceValidator organisationServiceValidator;

    private final OrganisationRepository organisationRepository;

    private final OrganisationEnrichmentService organisationEnrichmentService;

    private final Producer producer;

    private final Configuration configuration;


    private final IndividualService individualService;

    private final NotificationService notificationService;

    @Autowired
    public OrganisationService(OrganisationServiceValidator organisationServiceValidator, OrganisationRepository organisationRepository, OrganisationEnrichmentService organisationEnrichmentService, Producer producer, Configuration configuration, IndividualService individualService, NotificationService notificationService) {
        this.organisationServiceValidator = organisationServiceValidator;
        this.organisationRepository = organisationRepository;
        this.organisationEnrichmentService = organisationEnrichmentService;
        this.producer = producer;
        this.configuration = configuration;
        this.individualService = individualService;
        this.notificationService = notificationService;
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
        producer.push(configuration.getOrgKafkaCreateTopic(), orgRequest);
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
        try {
            notificationService.sendNotification(orgRequest,false);
        }catch (Exception e){
            log.error("Exception while sending notification: " + e);
        }
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
