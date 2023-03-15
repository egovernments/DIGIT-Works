package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.util.IdgenUtil;
import org.egov.util.OrganisationUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

            List<Address> orgAddressList = organisation.getOrgAddress();
            List<ContactDetails> contactDetailsList = organisation.getContactDetails();
            List<Document> documentList = organisation.getDocuments();
            List<Identifier> identifierList = organisation.getIdentifiers();
            List<Function> functionList = organisation.getFunctions();

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
                }
            }

            //org tax identifier
            if (!CollectionUtils.isEmpty(identifierList)) {
                for (Identifier identifier : identifierList) {
                    identifier.setId(UUID.randomUUID().toString());
                }
            }

            //set id, audit details, application number for function
            if (!CollectionUtils.isEmpty(functionList)) {

                organisationUtil.setAuditDetailsForFunction(requestInfo.getUserInfo().getUuid(), functionList, Boolean.TRUE);

                for (Function function : functionList) {
                    function.setId(UUID.randomUUID().toString());
                    function.setApplicationNumber(orgFunctionApplicationNumbers.get(funcAppNumIdFormatIndex));

                    List<Document> documents = function.getDocuments();
                    if (!CollectionUtils.isEmpty(documents)) {
                        for (Document funcDocument : documents) {
                            funcDocument.setId(UUID.randomUUID().toString());
                        }
                    }
                    funcAppNumIdFormatIndex++;

                }
            }

            orgAppNumIdFormatIndex++;
        }


    }

    /**
     * Enrich the update organisation registry with audit details
     *
     * @param orgRequest
     */
    public void enrichUpdateOrgRegistryWithoutWorkFlow(OrgRequest orgRequest) {
        log.info("OrganisationEnrichmentService::enrichUpdateOrgRegistryWithoutWorkFlow");
        RequestInfo requestInfo = orgRequest.getRequestInfo();
        List<Organisation> organisationList = orgRequest.getOrganisations();

        //set the audit details for organisation
        organisationUtil.setAuditDetailsForOrganisation(requestInfo.getUserInfo().getUuid(), organisationList, Boolean.FALSE);

        for (Organisation organisation : organisationList) {
            List<Function> functionList = organisation.getFunctions();
            if (!CollectionUtils.isEmpty(functionList)) {
                //set the audit details for function
                organisationUtil.setAuditDetailsForFunction(requestInfo.getUserInfo().getUuid(), functionList, Boolean.FALSE);
            }
        }

    }
}
