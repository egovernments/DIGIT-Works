package org.egov.repository.querybuilder;

import org.egov.web.models.OrgSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganisationQueryBuilder {
    public String getOrganisationSearchQuery(OrgSearchCriteria searchCriteria, List<Object> preparedStmtList) {
        return "";
    }
}
