package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.config.Configuration;
import org.egov.util.EncryptionDecryptionUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class EncryptionService {
    private EncryptionDecryptionUtil encryptionDecryptionUtil;
    private Configuration config;

    @Autowired
    public EncryptionService(EncryptionDecryptionUtil encryptionDecryptionUtil, Configuration config) {
        this.encryptionDecryptionUtil = encryptionDecryptionUtil;
        this.config = config;

    }

    public OrgRequest encryptDetails(OrgRequest orgRequest,String key){
        List<Organisation> organisationList = orgRequest.getOrganisations();
        for(Organisation organisation: organisationList){
            if (!CollectionUtils.isEmpty(organisation.getContactDetails())) {
                List<ContactDetails> encryptedContactDetails = (List<ContactDetails>) encryptionDecryptionUtil
                        .encryptObject(organisation.getContactDetails(), config.getStateLevelTenantId(), key, ContactDetails.class);
                organisation.setContactDetails(encryptedContactDetails);
            }
        }
        return orgRequest;
    }

    public OrgSearchRequest encryptDetails(OrgSearchRequest orgSearchRequest,String key){
        OrgSearchCriteria searchCriteria = orgSearchRequest.getSearchCriteria();
        OrgSearchCriteria encryptedSearchCriteria = encryptionDecryptionUtil
                .encryptObject(searchCriteria, config.getStateLevelTenantId(), key, OrgSearchCriteria.class);

        orgSearchRequest.setSearchCriteria(encryptedSearchCriteria);
        return orgSearchRequest;
    }

    public List<Organisation> decrypt(List<Organisation> organisationList, String key,OrgSearchRequest orgSearchRequest){
        for(Organisation organisation: organisationList){
            List<ContactDetails> contactDetailsList = organisation.getContactDetails();
            List<ContactDetails> decryptContactDetails = encryptionDecryptionUtil.decryptObject(contactDetailsList, key, ContactDetails.class, orgSearchRequest.getRequestInfo());
            organisation.setContactDetails(decryptContactDetails);
        }
        return organisationList;
    }

}