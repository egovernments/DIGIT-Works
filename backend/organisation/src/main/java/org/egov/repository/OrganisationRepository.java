package org.egov.repository;

import org.apache.commons.lang3.StringUtils;
import org.egov.repository.querybuilder.*;
import org.egov.repository.rowmapper.*;
import org.egov.service.EncryptionService;
import lombok.extern.slf4j.Slf4j;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static org.egov.util.OrganisationConstant.ORGANISATION_ENCRYPT_KEY;

@Repository
@Slf4j
public class OrganisationRepository {

    private final OrganisationFunctionQueryBuilder organisationFunctionQueryBuilder;
    private final OrganisationFunctionRowMapper organisationFunctionRowMapper;
    private final AddressQueryBuilder addressQueryBuilder;
    private final AddressRowMapper addressRowMapper;
    private final DocumentQueryBuilder documentQueryBuilder;
    private final DocumentRowMapper documentRowMapper;
    private final ContactDetailsQueryBuilder contactDetailsQueryBuilder;
    private final ContactDetailsRowMapper contactDetailsRowMapper;
    private final JurisdictionQueryBuilder jurisdictionQueryBuilder;
    private final JurisdictionRowMapper jurisdictionRowMapper;
    private final TaxIdentifierQueryBuilder taxIdentifierQueryBuilder;
    private final TaxIdentifierRowMapper taxIdentifierRowMapper;
    private final AddressOrgIdsRowMapper addressOrgIdsRowMapper;
    private final TaxIdentifierOrgIdsRowMapper taxIdentifierOrgIdsRowMapper;

    private final ContactDetailsOrgIdsRowMapper contactDetailsOrgIdsRowMapper;

    private final EncryptionService encryptionService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrganisationRepository(AddressQueryBuilder addressQueryBuilder, OrganisationFunctionQueryBuilder organisationFunctionQueryBuilder, OrganisationFunctionRowMapper organisationFunctionRowMapper, AddressOrgIdsRowMapper addressOrgIdsRowMapper, AddressRowMapper addressRowMapper, DocumentQueryBuilder documentQueryBuilder, DocumentRowMapper documentRowMapper, ContactDetailsQueryBuilder contactDetailsQueryBuilder, TaxIdentifierRowMapper taxIdentifierRowMapper, ContactDetailsRowMapper contactDetailsRowMapper, ContactDetailsOrgIdsRowMapper contactDetailsOrgIdsRowMapper, JdbcTemplate jdbcTemplate, JurisdictionQueryBuilder jurisdictionQueryBuilder, TaxIdentifierOrgIdsRowMapper taxIdentifierOrgIdsRowMapper, JurisdictionRowMapper jurisdictionRowMapper, TaxIdentifierQueryBuilder taxIdentifierQueryBuilder, EncryptionService encryptionService) {
        this.addressQueryBuilder = addressQueryBuilder;
        this.organisationFunctionQueryBuilder = organisationFunctionQueryBuilder;
        this.organisationFunctionRowMapper = organisationFunctionRowMapper;
        this.addressOrgIdsRowMapper = addressOrgIdsRowMapper;
        this.addressRowMapper = addressRowMapper;
        this.documentQueryBuilder = documentQueryBuilder;
        this.documentRowMapper = documentRowMapper;
        this.contactDetailsQueryBuilder = contactDetailsQueryBuilder;
        this.taxIdentifierRowMapper = taxIdentifierRowMapper;
        this.contactDetailsRowMapper = contactDetailsRowMapper;
        this.contactDetailsOrgIdsRowMapper = contactDetailsOrgIdsRowMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.jurisdictionQueryBuilder = jurisdictionQueryBuilder;
        this.taxIdentifierOrgIdsRowMapper = taxIdentifierOrgIdsRowMapper;
        this.jurisdictionRowMapper = jurisdictionRowMapper;
        this.taxIdentifierQueryBuilder = taxIdentifierQueryBuilder;
        this.encryptionService = encryptionService;
    }

    public List<Organisation> getOrganisations(OrgSearchRequest orgSearchRequest) {
        // Encrypt search criteria
        encryptionService.encryptDetails(orgSearchRequest, ORGANISATION_ENCRYPT_KEY);
        //Fetch organisation ids based on identifierType and identifierValue search criteria
        Set<String> orgIdsFromIdentifierSearch = getOrgIdsForIdentifiersBasedOnSearchCriteria(orgSearchRequest);
        //Fetch organisation ids based on boundaryCode in  search criteria
        Set<String> orgIdsFromBoundarySearch = getOrgIdsForAddressesBasedOnSearchCriteria(orgSearchRequest);
        //Fetch organisation ids based on contactMobileNumber in  search criteria
        Set<String> orgIdsFromContactMobileNumberSearch = getOrgIdsForContactNumberBasedOnSearchCriteria(orgSearchRequest);

        Set<String> orgIds = new HashSet<>();
        getOrgIdsForSearch(orgSearchRequest, orgIdsFromIdentifierSearch, orgIdsFromBoundarySearch, orgIdsFromContactMobileNumberSearch, orgIds);

        // If OrgIds are empty and request is present in search criteria
        if (orgIds.isEmpty() &&
                (StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getIdentifierType())
                || StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getIdentifierValue())
                || StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getBoundaryCode())
                || StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getContactMobileNumber())
                || !orgSearchRequest.getSearchCriteria().getId().isEmpty())) {
            return Collections.emptyList();
        }

        //Fetch Organisations based on search criteria
        List<Organisation> organisations = getOrganisationsBasedOnSearchCriteria(orgSearchRequest, orgIds);

        Set<String> organisationIds = organisations.stream().map(Organisation :: getId).collect(Collectors.toSet());

        //Fetch addresses based on organisation Ids
        List<Address> addresses = getAddressBasedOnOrganisationIds(organisationIds);

        //Fetch contactDetails based on organisation Ids
        List<ContactDetails> contactDetails = getContactDetailsBasedOnOrganisationIds(organisationIds);

        Set<String> functionIds = organisations.stream()
                .flatMap(org -> org.getFunctions().stream())
                .map(Function::getId)
                .collect(Collectors.toSet());
        //Fetch addresses based on organisation Ids
        List<Document> documents = getDocumentsBasedOnOrganisationIds(organisationIds, functionIds);

        //Fetch jurisdictions based on organisation Ids
        List<Jurisdiction> jurisdictions = getJurisdictionsBasedOnOrganisationIds(organisationIds);

        //Fetch identifiers based on organisation Ids
        List<Identifier> identifiers = getIdentifiersBasedOnOrganisationIds(organisationIds);

        log.info("Fetched organisation details for search request");
        //Construct Organisation Objects with fetched organisations, addresses, contactDetails, jurisdictions, identifiers and documents using Organisation id
        return encryptionService
                .decrypt(buildOrganisationSearchResult(organisations, addresses, contactDetails, documents, jurisdictions, identifiers),
                        ORGANISATION_ENCRYPT_KEY,orgSearchRequest);
    }

    private Set<String> getOrgIdsForContactNumberBasedOnSearchCriteria(OrgSearchRequest orgSearchRequest) {
        if (StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getContactMobileNumber())) {
            List<Object> preparedStmtListTarget = new ArrayList<>();
            String queryAddress = contactDetailsQueryBuilder.getContactDetailsSearchQueryBasedOnCriteria(orgSearchRequest.getSearchCriteria().getContactMobileNumber(), preparedStmtListTarget);
            Set<String> orgIds = jdbcTemplate.query(queryAddress, contactDetailsOrgIdsRowMapper, preparedStmtListTarget.toArray());
            log.info("Fetched Org Ids for contact details based on Contact Mobile Number search");
            return orgIds;
        }
        return Collections.emptySet();
    }

    /* Fetch organisation ids based on identifierType and identifierValue search criteria */
    private Set<String> getOrgIdsForIdentifiersBasedOnSearchCriteria(OrgSearchRequest orgSearchRequest) {
        if (StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getIdentifierType()) || StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getIdentifierValue())) {
            List<Object> preparedStmtListTarget = new ArrayList<>();
            String queryIdentifier = taxIdentifierQueryBuilder.getTaxIdentifierSearchQueryBasedOnCriteria(orgSearchRequest.getSearchCriteria().getIdentifierType(), orgSearchRequest.getSearchCriteria().getIdentifierValue(), preparedStmtListTarget);
            Set<String> orgIds = jdbcTemplate.query(queryIdentifier, taxIdentifierOrgIdsRowMapper, preparedStmtListTarget.toArray());
            log.info("Fetched Org Ids for identifiers based on Search criteria");
            return orgIds;
        }
        return Collections.emptySet();
    }

    /* Fetch organisation ids based on boundaryCode search criteria */
    private Set<String> getOrgIdsForAddressesBasedOnSearchCriteria(OrgSearchRequest orgSearchRequest) {
        if (StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getBoundaryCode())) {
            List<Object> preparedStmtListTarget = new ArrayList<>();
            String queryAddress = addressQueryBuilder.getAddressSearchQueryBasedOnCriteria(orgSearchRequest.getSearchCriteria().getBoundaryCode(), orgSearchRequest.getSearchCriteria().getTenantId(), preparedStmtListTarget);
            Set<String> orgIds = jdbcTemplate.query(queryAddress, addressOrgIdsRowMapper, preparedStmtListTarget.toArray());
            log.info("Fetched Org Ids for addresses based on Boundary Code search");
            return orgIds;
        }
        return Collections.emptySet();
    }

    /* Get organisations list based on search request */
    private List<Organisation> getOrganisationsBasedOnSearchCriteria(OrgSearchRequest orgSearchRequest, Set<String> orgIds) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = organisationFunctionQueryBuilder.getOrganisationSearchQuery(orgSearchRequest, orgIds, preparedStmtList, false);
        List<Organisation> organisations = jdbcTemplate.query(query, organisationFunctionRowMapper, preparedStmtList.toArray());

        log.info("Fetched organisations list based on given search criteria");
        return organisations;
    }

    /* Get OrgIds for search result from contact details, identifier and boundaryCode search results and orgIds in request */
    private void getOrgIdsForSearch(OrgSearchRequest orgSearchRequest, Set<String> orgIdsFromIdentifierSearch,Set<String> orgIdsFromBoundarySearch,Set<String> orgIdsFromContactMobileNumberSearch, Set<String> orgIds) {
        boolean isIdentifierSearchCriteriaPresent = StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getIdentifierType())
                || StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getIdentifierValue());
        boolean isBoundarySearchCriteriaPresent = StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getBoundaryCode());
        boolean isOrgIdsSearchCriteriaPresent = orgSearchRequest.getSearchCriteria().getId() != null && !orgSearchRequest.getSearchCriteria().getId().isEmpty();
        boolean isContactMobileNumberSearchCriteriaPresent = StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getContactMobileNumber());
        if (orgSearchRequest.getSearchCriteria().getId() == null) {
            orgSearchRequest.getSearchCriteria().setId(new ArrayList<>());
        }

        // If identifierType or identifierValue present in request, but the search result is empty, then return empty list
        if (orgIdsFromIdentifierSearch.isEmpty() && isIdentifierSearchCriteriaPresent) {
            return;
        }
        // If contactMobileNumber present in request, but the search result is empty, then return empty list
        if (orgIdsFromContactMobileNumberSearch.isEmpty() && isContactMobileNumberSearchCriteriaPresent) {
            return;
        }
        // If boundaryCode present in request, but the search result is empty, then return empty list
        if (orgIdsFromBoundarySearch.isEmpty() && isBoundarySearchCriteriaPresent) {
            return;
        }

        // Get common orgIds from contact mobile number search, identifier search result, boundaryCode search result and orgIds in request
        if (isContactMobileNumberSearchCriteriaPresent) {
            orgIds.addAll(orgIdsFromContactMobileNumberSearch);
            if (isIdentifierSearchCriteriaPresent) {
                //Get common orgIds of contactDetails search result and identifiers search result
                orgIds.retainAll(orgIdsFromIdentifierSearch);
            }
            if (isBoundarySearchCriteriaPresent) {
                //Get common orgIds of identifier search result and boundary search result
                orgIds.retainAll(orgIdsFromBoundarySearch);
            }
            //Get common orgIds of identifier search result and orgIds in search request
            if (isOrgIdsSearchCriteriaPresent) {
                orgIds.retainAll(orgSearchRequest.getSearchCriteria().getId());
            }
        } else if (isIdentifierSearchCriteriaPresent) {
            orgIds.addAll(orgIdsFromIdentifierSearch);
            if (isBoundarySearchCriteriaPresent) {
                //Get common orgIds of identifier search result and boundary search result
                orgIds.retainAll(orgIdsFromBoundarySearch);
            }
            //Get common orgIds of identifier search result and orgIds in search request
            if (isOrgIdsSearchCriteriaPresent) {
                orgIds.retainAll(orgSearchRequest.getSearchCriteria().getId());
            }
        } else if (isBoundarySearchCriteriaPresent) {
            orgIds.addAll(orgIdsFromBoundarySearch);
            //Get common orgIds of boundary search result and orgIds in search request
            if (isOrgIdsSearchCriteriaPresent) {
                orgIds.retainAll(orgSearchRequest.getSearchCriteria().getId());
            }
        } else {
            orgIds.addAll(orgSearchRequest.getSearchCriteria().getId());
        }

    }

    /* Get addresses list based on organisation Ids */
    private List<Address> getAddressBasedOnOrganisationIds(Set<String> organisationIds) {
        List<Object> preparedStmtListTarget = new ArrayList<>();
        String queryAddress = addressQueryBuilder.getAddressSearchQuery(organisationIds, preparedStmtListTarget);
        List<Address> addresses = jdbcTemplate.query(queryAddress, addressRowMapper, preparedStmtListTarget.toArray());
        log.info("Fetched addresses based on organisation Ids");
        return addresses;
    }

    /* Get documents list based on organisation Ids */
    private List<Document> getDocumentsBasedOnOrganisationIds(Set<String> organisationIds, Set<String> functionIds) {
        List<Object> preparedStmtListTarget = new ArrayList<>();
        String queryDocument = documentQueryBuilder.getDocumentsSearchQuery(organisationIds, functionIds, preparedStmtListTarget);
        List<Document> documents = jdbcTemplate.query(queryDocument, documentRowMapper, preparedStmtListTarget.toArray());
        log.info("Fetched documents based on organisation Ids");
        return documents;
    }

    /* Get contact details list based on organisation Ids */
    private List<ContactDetails> getContactDetailsBasedOnOrganisationIds(Set<String> organisationIds) {
        List<Object> preparedStmtListTarget = new ArrayList<>();
        String queryContactDetails = contactDetailsQueryBuilder.getContactDetailsSearchQuery(organisationIds, preparedStmtListTarget);
        List<ContactDetails> contactDetails = jdbcTemplate.query(queryContactDetails, contactDetailsRowMapper, preparedStmtListTarget.toArray());
        log.info("Fetched contactDetails based on organisation Ids");
        return contactDetails;
    }

    /* Get identifiers list based on organisation Ids */
    private List<Identifier> getIdentifiersBasedOnOrganisationIds(Set<String> organisationIds) {
        List<Object> preparedStmtListTarget = new ArrayList<>();
        String queryIdentifier = taxIdentifierQueryBuilder.getTaxIdentifierSearchQuery(organisationIds, preparedStmtListTarget);
        List<Identifier> identifiers = jdbcTemplate.query(queryIdentifier, taxIdentifierRowMapper, preparedStmtListTarget.toArray());
        log.info("Fetched identifiers based on organisation Ids");
        return identifiers;
    }

    /* Get jurisdictions list based on organisation Ids */
    private List<Jurisdiction> getJurisdictionsBasedOnOrganisationIds(Set<String> organisationIds) {
        List<Object> preparedStmtListTarget = new ArrayList<>();
        String queryJurisdictions = jurisdictionQueryBuilder.getJurisdictionSearchQuery(organisationIds, preparedStmtListTarget);
        List<Jurisdiction> jurisdictions = jdbcTemplate.query(queryJurisdictions, jurisdictionRowMapper, preparedStmtListTarget.toArray());
        log.info("Fetched jurisdictions based on organisation Ids");
        return jurisdictions;
    }

    /* Construct organisation search results based on organisations, addresses, contact details, documents, jurisdictions and identifiers*/
    private List<Organisation> buildOrganisationSearchResult(List<Organisation> organisations, List<Address> addresses, List<ContactDetails> contactDetails, List<Document> documents, List<Jurisdiction> jurisdictions, List<Identifier> identifiers) {
        for (Organisation organisation: organisations) {
            log.info("Constructing organisation object for organisation " + organisation.getId());
            constructOrganizationObject(organisation, addresses, contactDetails, documents, jurisdictions, identifiers);
            log.info("Constructed organisation object for organisation " + organisation.getId());
        }
        return organisations;
    }
    private void constructOrganizationObject(Organisation organisation, List<Address> addresses, List<ContactDetails> contactDetails, List<Document> documents, List<Jurisdiction> jurisdictions, List<Identifier> identifiers){
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
        if (jurisdictions != null && !jurisdictions.isEmpty()) {
            log.info("Adding jurisdictions to organisation " + organisation.getId());
            addJurisdictionsToOrganisation(organisation, jurisdictions);
        }
        if (identifiers != null && !identifiers.isEmpty()) {
            log.info("Adding identifiers to organisation " + organisation.getId());
            addIdentifiersToOrganisation(organisation, identifiers);
        }
    }

    private void addIdentifiersToOrganisation(Organisation organisation, List<Identifier> identifiers) {
        organisation.setIdentifiers(new ArrayList<>());
        for (Identifier identifier: identifiers) {
            if (identifier.getOrgId().equals(organisation.getId()) && organisation.getIdentifiers().stream().noneMatch(i -> i.getId().equals(identifier.getId()))) {
                organisation.getIdentifiers().add(identifier);
            }
        }
    }

    private void addJurisdictionsToOrganisation(Organisation organisation, List<Jurisdiction> jurisdictions) {
        organisation.setJurisdiction(new ArrayList<>());
        for (Jurisdiction jurisdiction: jurisdictions) {
            // Add jurisdiction from list of jurisdictions to organisation based on orgId.
            // Check if orgId in jurisdiction is equal to orgId in organisation and if organisation jurisdictions does not contain the jurisdiction to be added
            if (jurisdiction.getOrgId().equals(organisation.getId()) && organisation.getJurisdiction().stream().noneMatch(i -> i.getId().equals(jurisdiction.getId()))) {
                organisation.getJurisdiction().add(jurisdiction);
            }
        }
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
            if (StringUtils.isNotBlank(document.getOrgId()) && (document.getOrgId().equals(organisation.getId()) && organisation.getDocuments().stream().noneMatch(d -> d.getId().equals(document.getId())))) {
                    organisation.getDocuments().add(document);

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


        Set<String> orgIdsFromIdentifierSearch = getOrgIdsForIdentifiersBasedOnSearchCriteria(orgSearchRequest);
        Set<String> orgIdsFromBoundarySearch = getOrgIdsForAddressesBasedOnSearchCriteria(orgSearchRequest);
        Set<String> orgIdsFromContactMobileNumberSearch = getOrgIdsForContactNumberBasedOnSearchCriteria(orgSearchRequest);

        Set<String> orgIds = new HashSet<>();
        getOrgIdsForSearch(orgSearchRequest, orgIdsFromIdentifierSearch, orgIdsFromBoundarySearch, orgIdsFromContactMobileNumberSearch, orgIds);

        // If OrgIds are empty and request is present in search criteria
        if (orgIds.isEmpty() &&
                (StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getIdentifierType())
                        || StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getIdentifierValue())
                        || StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getBoundaryCode())
                        || StringUtils.isNotBlank(orgSearchRequest.getSearchCriteria().getContactMobileNumber())
                        || !orgSearchRequest.getSearchCriteria().getId().isEmpty())) {
            return 0;
        }
        
        String query = organisationFunctionQueryBuilder.getSearchCountQueryString(orgSearchRequest, orgIds, preparedStatement);

        if (query == null)
            return 0;

        Integer count = jdbcTemplate.queryForObject(query, preparedStatement.toArray(), Integer.class);
        log.info("Total organisation count is : " + count);
        return count;
    }
}
