package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.querybuilder.OrganisationFunctionQueryBuilder;
import org.egov.repository.rowmapper.OrganisationFunctionRowMapper;
import org.egov.web.models.OrgSearchCriteria;
import org.egov.web.models.OrgSearchRequest;
import org.egov.web.models.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class OrganisationRepository {

    @Autowired
    private OrganisationFunctionQueryBuilder organisationFunctionQueryBuilder;

    @Autowired
    private OrganisationFunctionRowMapper organisationFunctionRowMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Organisation> getOrganisations(OrgSearchRequest orgSearchRequest) {

        //Fetch Organisations based on search criteria
        List<Organisation> organisations = getOrganisationsBasedOnSearchCriteria(orgSearchRequest);

        Set<String> organisationIds = organisations.stream().map(Organisation :: getId).collect(Collectors.toSet());

        return organisations;
    }

    private List<Organisation> getOrganisationsBasedOnSearchCriteria(OrgSearchRequest orgSearchRequest) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = organisationFunctionQueryBuilder.getOrganisationSearchQuery(orgSearchRequest, preparedStmtList);
        List<Organisation> organisations = jdbcTemplate.query(query, organisationFunctionRowMapper, preparedStmtList.toArray());

        log.info("Fetched organisations list based on given search criteria");
        return organisations;
    }
}
