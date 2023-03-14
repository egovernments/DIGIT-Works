package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.querybuilder.OrganisationQueryBuilder;
import org.egov.repository.rowmapper.OrganisationRowMapper;
import org.egov.web.models.OrgSearchCriteria;
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
    private OrganisationQueryBuilder organisationQueryBuilder;

    @Autowired
    private OrganisationRowMapper organisationRowMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Organisation> getOrganisations(RequestInfo requestInfo, OrgSearchCriteria searchCriteria) {

        //Fetch Organisations based on search criteria
        List<Organisation> organisations = getOrganisationsBasedOnSearchCriteria(searchCriteria);

        Set<String> organisationIds = organisations.stream().map(Organisation :: getId).collect(Collectors.toSet());

        return Collections.emptyList();
    }

    private List<Organisation> getOrganisationsBasedOnSearchCriteria(OrgSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = organisationQueryBuilder.getOrganisationSearchQuery(searchCriteria, preparedStmtList);
        List<Organisation> organisations = jdbcTemplate.query(query, organisationRowMapper, preparedStmtList.toArray());

        log.info("Fetched organisations list based on given search criteria");
        return organisations;
    }
}
