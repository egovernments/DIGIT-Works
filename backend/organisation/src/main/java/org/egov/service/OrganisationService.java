package org.egov.service;


import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.OrganisationRepository;
import org.egov.validator.OrganisationServiceValidator;
import org.egov.web.models.OrgRequest;
import org.egov.web.models.OrgSearchCriteria;
import org.egov.web.models.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class OrganisationService {

    @Autowired
    OrganisationServiceValidator organisationServiceValidator;

    @Autowired
    OrganisationRepository organisationRepository;

    public OrgRequest createOrganisationWithoutWorkFlow(OrgRequest orgRequest) {

        return orgRequest;
    }

    public OrgRequest updateOrganisationWithoutWorkFlow(OrgRequest orgRequest) {

        return orgRequest;
    }

    public List<Organisation> searchOrganisation(RequestInfo requestInfo, OrgSearchCriteria searchCriteria) {
        organisationServiceValidator.validateSearchOrganisationRequest(requestInfo, searchCriteria);
        List<Organisation> organisations = organisationRepository.getOrganisations(requestInfo, searchCriteria);
        return organisations;
    }

    public Integer countAllOrganisations(OrgSearchCriteria searchCriteria) {

        return 0;
    }
}
