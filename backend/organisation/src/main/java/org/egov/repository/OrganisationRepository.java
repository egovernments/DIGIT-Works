package org.egov.repository;

import org.apache.commons.lang3.StringUtils;
import org.egov.repository.querybuilder.*;
import org.egov.repository.rowmapper.*;
import org.egov.web.models.Document;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class OrganisationRepository {

    @Autowired
    private OrganisationFunctionQueryBuilder organisationFunctionQueryBuilder;
    @Autowired
    private OrganisationFunctionRowMapper organisationFunctionRowMapper;
    @Autowired
    private AddressQueryBuilder addressQueryBuilder;
    @Autowired
    private AddressRowMapper addressRowMapper;
    @Autowired
    private DocumentQueryBuilder documentQueryBuilder;
    @Autowired
    private DocumentRowMapper documentRowMapper;
    @Autowired
    private ContactDetailsQueryBuilder contactDetailsQueryBuilder;
    @Autowired
    private ContactDetailsRowMapper contactDetailsRowMapper;
    @Autowired
    private JurisdictionQueryBuilder jurisdictionQueryBuilder;
    @Autowired
    private JurisdictionRowMapper jurisdictionRowMapper;
    @Autowired
    private TaxIdentifierQueryBuilder taxIdentifierQueryBuilder;
    @Autowired
    private TaxIdentifierRowMapper taxIdentifierRowMapper;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Organisation> getOrganisations(OrgSearchRequest orgSearchRequest) {

        //Fetch Organisations based on search criteria
        List<Organisation> organisations = getOrganisationsBasedOnSearchCriteria(orgSearchRequest);

        Set<String> organisationIds = organisations.stream().map(Organisation :: getId).collect(Collectors.toSet());

        //Fetch addresses based on organisation Ids
        List<Address> addresses = getAddressBasedOnOrganisationIds(organisationIds);

        //Fetch addresses based on organisation Ids
        List<ContactDetails> contactDetails = getContactDetailsBasedOnOrganisationIds(organisationIds);

        Set<String> functionIds = organisations.stream()
                .flatMap(org -> org.getFunctions().stream())
                .map(Function::getId)
                .collect(Collectors.toSet());
        //Fetch addresses based on organisation Ids
        List<Document> documents = getDocumentsBasedOnOrganisationIds(organisationIds, functionIds);

        //Fetch jurisdictions based on organisation Ids
        Map<String, List<String>> jurisdictions = getJurisdictionsBasedOnOrganisationIds(organisationIds);

        //Fetch identifiers based on organisation Ids
        List<Identifier> identifiers = getIdentifiersBasedOnOrganisationIds(organisationIds);

        //Construct Organisation Objects with fetched organisations, addresses, contactDetails, jurisdictions, identifiers and documents using Organisation id
        return buildOrganisationSearchResult(organisations, addresses, contactDetails, documents, jurisdictions, identifiers);
    }

    private List<Organisation> getOrganisationsBasedOnSearchCriteria(OrgSearchRequest orgSearchRequest) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = organisationFunctionQueryBuilder.getOrganisationSearchQuery(orgSearchRequest, preparedStmtList, false);
        List<Organisation> organisations = jdbcTemplate.query(query, organisationFunctionRowMapper, preparedStmtList.toArray());

        log.info("Fetched organisations list based on given search criteria");
        return organisations;
    }

    private List<Address> getAddressBasedOnOrganisationIds(Set<String> organisationIds) {
        List<Object> preparedStmtListTarget = new ArrayList<>();
        String queryAddress = addressQueryBuilder.getAddressSearchQuery(organisationIds, preparedStmtListTarget);
        List<Address> addresses = jdbcTemplate.query(queryAddress, addressRowMapper, preparedStmtListTarget.toArray());
        log.info("Fetched addresses based on organisation Ids");
        return addresses;
    }

    private List<Document> getDocumentsBasedOnOrganisationIds(Set<String> organisationIds, Set<String> functionIds) {
        List<Object> preparedStmtListTarget = new ArrayList<>();
        String queryDocument = documentQueryBuilder.getDocumentsSearchQuery(organisationIds, functionIds, preparedStmtListTarget);
        List<Document> documents = jdbcTemplate.query(queryDocument, documentRowMapper, preparedStmtListTarget.toArray());
        log.info("Fetched documents based on organisation Ids");
        return documents;
    }

    private List<ContactDetails> getContactDetailsBasedOnOrganisationIds(Set<String> organisationIds) {
        List<Object> preparedStmtListTarget = new ArrayList<>();
        String queryContactDetails = contactDetailsQueryBuilder.getContactDetailsSearchQuery(organisationIds, preparedStmtListTarget);
        List<ContactDetails> contactDetails = jdbcTemplate.query(queryContactDetails, contactDetailsRowMapper, preparedStmtListTarget.toArray());
        log.info("Fetched contactDetails based on organisation Ids");
        return contactDetails;
    }

    private List<Identifier> getIdentifiersBasedOnOrganisationIds(Set<String> organisationIds) {
        List<Object> preparedStmtListTarget = new ArrayList<>();
        String queryIdentifier = taxIdentifierQueryBuilder.getTaxIdentifierSearchQuery(organisationIds, preparedStmtListTarget);
        List<Identifier> identifiers = jdbcTemplate.query(queryIdentifier, taxIdentifierRowMapper, preparedStmtListTarget.toArray());
        log.info("Fetched identifiers based on organisation Ids");
        return identifiers;
    }

    private Map<String, List<String>> getJurisdictionsBasedOnOrganisationIds(Set<String> organisationIds) {
        List<Object> preparedStmtListTarget = new ArrayList<>();
        String queryJurisdictions = jurisdictionQueryBuilder.getJurisdictionSearchQuery(organisationIds, preparedStmtListTarget);
        Map<String, List<String>> jurisdictions = jdbcTemplate.query(queryJurisdictions, jurisdictionRowMapper, preparedStmtListTarget.toArray());
        log.info("Fetched jurisdictions based on organisation Ids");
        return jurisdictions;
    }

    private List<Organisation> buildOrganisationSearchResult(List<Organisation> organisations, List<Address> addresses, List<ContactDetails> contactDetails, List<Document> documents, Map<String, List<String>> jurisdictionsMap, List<Identifier> identifiers) {
        for (Organisation organisation: organisations) {
            log.info("Constructing organisation object for organisation " + organisation.getId());
            if (addresses != null && !addresses.isEmpty()) {
                log.info("Adding Addresses to organisation " + organisation.getId());
                addAddressToOrganisation(organisation, addresses);
            }
            if (documents != null && !documents.isEmpty()) {
                log.info("Adding Documents to organisation " + organisation.getId());
                addDocumentToOrganisation(organisation, documents);
            }
            if (contactDetails != null && !contactDetails.isEmpty()) {
                log.info("Adding contactDetails to organisation " + organisation.getId());
                addContactDetailsToOrganisation(organisation, contactDetails);
            }
            if (jurisdictionsMap != null && !jurisdictionsMap.isEmpty()) {
                log.info("Adding jurisdictions to organisation " + organisation.getId());
                addJurisdictionsToOrganisation(organisation, jurisdictionsMap);
            }
            if (identifiers != null && !identifiers.isEmpty()) {
                log.info("Adding identifiers to organisation " + organisation.getId());
                addIdentifiersToOrganisation(organisation, identifiers);
            }

            log.info("Constructed organisation object for organisation " + organisation.getId());
        }
        return organisations;
    }

    private void addIdentifiersToOrganisation(Organisation organisation, List<Identifier> identifiers) {
        organisation.setIdentifiers(new ArrayList<>());
        for (Identifier identifier: identifiers) {
            if (identifier.getOrgId().equals(organisation.getId()) && organisation.getIdentifiers().stream().noneMatch(i -> i.getId().equals(identifier.getId()))) {
                organisation.getIdentifiers().add(identifier);
            }
        }
    }

    private void addJurisdictionsToOrganisation(Organisation organisation, Map<String, List<String>> jurisdictionsMap) {
        List<String> jurisdictionsForOrg = jurisdictionsMap.get(organisation.getId());
        organisation.setJurisdiction(jurisdictionsForOrg);
    }

    private void addContactDetailsToOrganisation(Organisation organisation, List<ContactDetails> contactDetails) {
        organisation.setContactDetails(new ArrayList<>());
        for (ContactDetails contactDetail: contactDetails) {
            if (contactDetail.getOrgId().equals(organisation.getId()) && organisation.getContactDetails().stream().noneMatch(c -> c.getId().equals(contactDetail.getId()))) {
                organisation.getContactDetails().add(contactDetail);
            }
        }
    }

    private void addDocumentToOrganisation(Organisation organisation, List<Document> documents) {
        organisation.setDocuments(new ArrayList<>());
        if (organisation.getFunctions() != null) {
            organisation.getFunctions().forEach(f -> f.setDocuments(new ArrayList<>()));
        }

        for (Document document: documents) {
            if (StringUtils.isNotBlank(document.getOrgId())) {
                if (document.getOrgId().equals(organisation.getId()) && organisation.getDocuments().stream().noneMatch(d -> d.getId().equals(document.getId()))) {
                    organisation.getDocuments().add(document);
                }
            }
            if (organisation.getFunctions() != null && StringUtils.isNotBlank(document.getOrgFunctionId())) {
                for (Function function: organisation.getFunctions()) {
                    if (document.getOrgFunctionId().equals(function.getId()) && function.getDocuments().stream().noneMatch(d -> d.getId().equals(document.getId()))) {
                        organisation.getDocuments().add(document);
                    }
                }
            }
        }
    }

    private void addAddressToOrganisation(Organisation organisation, List<Address> addresses) {
        organisation.setOrgAddress(new ArrayList<>());
        for (Address address: addresses) {
            if (address.getOrgId().equals(organisation.getId()) && organisation.getOrgAddress().stream().noneMatch(a -> a.getId().equals(address.getId()))) {
                organisation.getOrgAddress().add(address);
            }
        }
    }

    public Integer getOrganisationsCount(OrgSearchRequest orgSearchRequest) {
        List<Object> preparedStatement = new ArrayList<>();
        String query = organisationFunctionQueryBuilder.getSearchCountQueryString(orgSearchRequest, preparedStatement);

        if (query == null)
            return 0;

        Integer count = jdbcTemplate.queryForObject(query, preparedStatement.toArray(), Integer.class);
        log.info("Total organisation count is : " + count);
        return count;
    }
}
