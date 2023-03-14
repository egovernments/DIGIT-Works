package org.egov.service;


import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.kafka.Producer;
import org.egov.validator.OrganisationServiceValidator;
import org.egov.web.models.OrgRequest;
import org.egov.web.models.OrgSearchCriteria;
import org.egov.web.models.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class OrganisationService {

    @Autowired
    private OrganisationServiceValidator organisationServiceValidator;

    @Autowired
    private OrganisationEnrichmentService organisationEnrichmentService;

    @Autowired
    private Producer producer;

    @Autowired
    private Configuration configuration;


    public OrgRequest createOrganisationWithoutWorkFlow(OrgRequest orgRequest) {
        log.info("OrganisationService::createOrganisationWithoutWorkFlow");
        organisationServiceValidator.validateCreateOrgRegistryWithoutWorkFlow(orgRequest);
        organisationEnrichmentService.enrichCreateOrgRegistryWithoutWorkFlow(orgRequest);
        producer.push(configuration.getOrgKafkaCreateTopic(), orgRequest);
        return orgRequest;
    }

    public OrgRequest updateOrganisationWithoutWorkFlow(OrgRequest orgRequest) {
        log.info("OrganisationService::updateOrganisationWithoutWorkFlow");
        organisationServiceValidator.validateUpdateOrgRegistryWithoutWorkFlow(orgRequest);
        organisationEnrichmentService.enrichUpdateOrgRegistryWithoutWorkFlow(orgRequest);
        producer.push(configuration.getOrgKafkaUpdateTopic(), orgRequest);
        return orgRequest;
    }

    public List<Organisation> searchOrganisation(RequestInfo requestInfo, OrgSearchCriteria searchCriteria) {

        return Collections.emptyList();
    }

    public Integer countAllOrganisations(OrgSearchCriteria searchCriteria) {

        return 0;
    }
}
