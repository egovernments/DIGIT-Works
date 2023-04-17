package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.util.IdgenUtil;
import org.egov.util.OrganisationUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrganisationEnrichmentService {

    @Autowired
    private OrganisationUtil organisationUtil;

    @Autowired
    private IdgenUtil idgenUtil;

    @Autowired
    private Configuration config;


    /**
     * Enrich the audit details, id, and custom format number
     *
     * @param orgRequest
     */
    public void enrichCreateOrgRegistryWithoutWorkFlow(OrgRequest orgRequest) {
        log.info("OrganisationEnrichmentService::enrichCreateOrgRegistryWithoutWorkFlow");
        RequestInfo requestInfo = orgRequest.getRequestInfo();
        List<Organisation> organisationList = orgRequest.getOrganisations();

        //set the audit details
        organisationUtil.setAuditDetailsForOrganisation(requestInfo.getUserInfo().getUuid(), organisationList, Boolean.TRUE);
        String rootTenantId = organisationList.get(0).getTenantId().split("\\.")[0];

        //idgen to get the list of organisation application Numbers
        List<String> orgApplicationNumbers = idgenUtil.getIdList(requestInfo, rootTenantId, config.getOrgApplicationNumberName()
                , config.getOrgApplicationNumberFormat(), organisationList.size());

        //idgen to get the list of org Numbers
        List<String> orgNumbers = idgenUtil.getIdList(requestInfo, rootTenantId, config.getOrgNumberName()
                , config.getOrgNumberFormat(), organisationList.size());

        //idgen to get the list of function application Numbers
        long idgenFuncApplicationNumberCount = organisationList.stream().mapToInt(org -> {
            if (!CollectionUtils.isEmpty(org.getFunctions())) {
                return org.getFunctions().size();
            }
            return 0;
        }).sum();

        List<String> orgFunctionApplicationNumbers = idgenUtil.getIdList(requestInfo, rootTenantId, config.getFunctionApplicationNumberName()
                , config.getFunctionApplicationNumberFormat(), ((int) idgenFuncApplicationNumberCount));

        int orgAppNumIdFormatIndex = 0;
        int funcAppNumIdFormatIndex = 0;
        for (Organisation organisation : organisationList) {
            organisation.setId(UUID.randomUUID().toString());
            organisation.setApplicationNumber(orgApplicationNumbers.get(orgAppNumIdFormatIndex));
            if (organisation.getIsActive() == null) {
                organisation.setIsActive(Boolean.TRUE);
            }

            /**
             * TODO : As of we are generating the org number from idgen and setting it to each organisation object
             * but this will be part of update org registry
             * and "idgen formatted number will be set once workflow is 'APPROVED' ".
             */
            organisation.setOrgNumber(orgNumbers.get(orgAppNumIdFormatIndex));

            List<Address> orgAddressList = organisation.getOrgAddress();
            List<ContactDetails> contactDetailsList = organisation.getContactDetails();
            List<Document> documentList = organisation.getDocuments();
            List<Identifier> identifierList = organisation.getIdentifiers();
            List<Function> functionList = organisation.getFunctions();
            List<Jurisdiction> jurisdictionList = organisation.getJurisdiction();

            //org address
            if (!CollectionUtils.isEmpty(orgAddressList)) {
                for (Address address : orgAddressList) {
                    address.setId(UUID.randomUUID().toString());
                    address.getGeoLocation().setId(UUID.randomUUID().toString());
                }
            }

            //contact detail
            if (!CollectionUtils.isEmpty(contactDetailsList)) {
                for (ContactDetails contactDetails : contactDetailsList) {
                    contactDetails.setId(UUID.randomUUID().toString());
                }
            }

            //org document
            if (!CollectionUtils.isEmpty(documentList)) {
                for (Document document : documentList) {
                    document.setId(UUID.randomUUID().toString());
                    if (document.getIsActive() == null) {
                        document.setIsActive(Boolean.TRUE);
                    }
                }
            }

            //org tax identifier
            if (!CollectionUtils.isEmpty(identifierList)) {
                for (Identifier identifier : identifierList) {
                    identifier.setId(UUID.randomUUID().toString());
                    if (identifier.getIsActive() == null) {
                        identifier.setIsActive(Boolean.TRUE);
                    }
                }
            }

            //set id, audit details, application number for function
            if (!CollectionUtils.isEmpty(functionList)) {

                organisationUtil.setAuditDetailsForFunction(requestInfo.getUserInfo().getUuid(), functionList, Boolean.TRUE);

                for (Function function : functionList) {
                    function.setId(UUID.randomUUID().toString());
                    function.setApplicationNumber(orgFunctionApplicationNumbers.get(funcAppNumIdFormatIndex));
                    if (function.getIsActive() == null) {
                        function.setIsActive(Boolean.TRUE);
                    }

                    List<Document> documents = function.getDocuments();
                    if (!CollectionUtils.isEmpty(documents)) {
                        for (Document funcDocument : documents) {
                            funcDocument.setId(UUID.randomUUID().toString());
                            if (funcDocument.getIsActive() == null) {
                                funcDocument.setIsActive(Boolean.TRUE);
                            }
                        }
                    }
                    funcAppNumIdFormatIndex++;

                }
            }

            //jurisdiction
            if (!CollectionUtils.isEmpty(jurisdictionList)) {
                for (Jurisdiction jurisdiction : jurisdictionList) {
                    jurisdiction.setId(UUID.randomUUID().toString());
                }
            }

            orgAppNumIdFormatIndex++;
        }


    }

    /**
     * Enrich the update organisation registry with ids,custom id, audit details
     *
     * @param orgRequest
     */
    public void enrichUpdateOrgRegistryWithoutWorkFlow(OrgRequest orgRequest) {
        log.info("OrganisationEnrichmentService::enrichUpdateOrgRegistryWithoutWorkFlow");
        RequestInfo requestInfo = orgRequest.getRequestInfo();
        List<Organisation> organisationList = orgRequest.getOrganisations();

        //set the audit details for organisation
        organisationUtil.setAuditDetailsForOrganisation(requestInfo.getUserInfo().getUuid(), organisationList, Boolean.FALSE);

        String rootTenantId = organisationList.get(0).getTenantId().split("\\.")[0];

        for (Organisation organisation : organisationList) {
            List<Function> functionList = organisation.getFunctions();
            List<Identifier> identifierList = organisation.getIdentifiers();
            List<Document> documentList = organisation.getDocuments();

            //upsert identifier
            upsertIdentifier(identifierList);

            //upsert org document
            upsertOrgDocument(documentList);

            //upsert function and its document
            upsertFunction(requestInfo, rootTenantId, organisation, functionList);


        }

    }

    /**
     * @param documentList
     */
    private void upsertOrgDocument(List<Document> documentList) {
        if (!CollectionUtils.isEmpty(documentList)) {
            for (Document document : documentList) {
                if (StringUtils.isBlank(document.getId())) {
                    document.setId(UUID.randomUUID().toString());
                    if (document.getIsActive() == null) {
                        document.setIsActive(Boolean.TRUE);
                    }
                }
            }
        }
    }

    /**
     * @param identifierList
     */
    private void upsertIdentifier(List<Identifier> identifierList) {
        if (!CollectionUtils.isEmpty(identifierList)) {
            for (Identifier identifier : identifierList) {
                if (StringUtils.isBlank(identifier.getId())) {
                    identifier.setId(UUID.randomUUID().toString());
                    if (identifier.getIsActive() == null) {
                        identifier.setIsActive(Boolean.TRUE);
                    }
                }
            }
        }
    }

    /**
     * @param requestInfo
     * @param rootTenantId
     * @param organisation
     * @param functionList
     */
    private void upsertFunction(RequestInfo requestInfo, String rootTenantId, Organisation organisation, List<Function> functionList) {
        List<Function> upsertFunctionList = new ArrayList<>();
        List<Function> updateFunctionList = new ArrayList<>();
        List<Function> createFunctionList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(functionList)) {
            for (Function function : functionList) {
                if (StringUtils.isBlank(function.getId())) {
                    function.setId(UUID.randomUUID().toString());
                    function.setIsActive(Boolean.TRUE);
                    createFunctionList.add(function);
                } else {
                    updateFunctionList.add(function);
                }
            }
            //set the audit details for update function
            organisationUtil.setAuditDetailsForFunction(requestInfo.getUserInfo().getUuid(), updateFunctionList, Boolean.FALSE);

            //set the audit details for create function
            organisationUtil.setAuditDetailsForFunction(requestInfo.getUserInfo().getUuid(), createFunctionList, Boolean.TRUE);

            //get the application numbers for new function from Idgen service
            List<String> orgFunctionApplicationNumbers = new ArrayList<>();
            if (createFunctionList.size() > 0) {
                orgFunctionApplicationNumbers = idgenUtil.getIdList(requestInfo, rootTenantId, config.getFunctionApplicationNumberName()
                        , config.getFunctionApplicationNumberFormat(), createFunctionList.size());
            }

            //set the application numbers to new function
            int index = 0;
            if (!CollectionUtils.isEmpty(orgFunctionApplicationNumbers)) {
                for (Function function : createFunctionList) {
                    function.setApplicationNumber(orgFunctionApplicationNumbers.get(index));
                    index++;
                }
            }

            upsertFunctionList.addAll(createFunctionList);
            upsertFunctionList.addAll(updateFunctionList);

            //check any new function doc, if yes , set a new UUID
            for (Function function : upsertFunctionList) {
                List<Document> documents = function.getDocuments();
                if (!CollectionUtils.isEmpty(documents)) {
                    for (Document document : documents) {
                        if (StringUtils.isBlank(document.getId())) {
                            document.setId(UUID.randomUUID().toString());
                            if (document.getIsActive() == null) {
                                document.setIsActive(Boolean.TRUE);
                            }
                        }
                    }
                }
            }

            organisation.setFunctions(upsertFunctionList);

        }
    }
}
