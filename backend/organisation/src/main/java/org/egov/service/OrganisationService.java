package org.egov.service;


import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.validator.OrganizationServiceValidator;
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
    OrganizationServiceValidator organizationServiceValidator;


    public OrgRequest createOrganisationWithoutWorkFlow(OrgRequest orgRequest) {

        return orgRequest;
    }

    public OrgRequest updateOrganisationWithoutWorkFlow(OrgRequest orgRequest) {

        return orgRequest;
    }

    public List<Organisation> searchOrganisation(RequestInfo requestInfo, OrgSearchCriteria searchCriteria) {
        organizationServiceValidator.validateSearchProjectRequest(requestInfo, searchCriteria);
        return Collections.emptyList();
    }

    public Integer countAllOrganisations(OrgSearchCriteria searchCriteria) {

        return 0;
    }
}
