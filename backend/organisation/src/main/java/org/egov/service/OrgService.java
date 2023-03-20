package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.web.models.OrgSearchCriteria;
import org.egov.web.models.OrgServiceRequest;
import org.egov.web.models.Organisation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class OrgService {


    public OrgServiceRequest createOrganisationWithWorkFlow(OrgServiceRequest orgServiceRequest) {

        return orgServiceRequest;
    }

    public OrgServiceRequest updateOrganisationWithWorkFlow(OrgServiceRequest orgServiceRequest) {

        return orgServiceRequest;
    }

    public List<Organisation> searchOrganisation(RequestInfo requestInfo, OrgSearchCriteria searchCriteria) {

        return Collections.emptyList();
    }

    public Integer countAllOrganisations(OrgSearchCriteria searchCriteria) {

        return 0;
    }
}
