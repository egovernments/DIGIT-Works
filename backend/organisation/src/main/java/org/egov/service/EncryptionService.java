package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.util.EncryptionDecryptionUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EncryptionService {
    @Autowired
    private EncryptionDecryptionUtil encryptionDecryptionUtil;
    public OrgRequest encryptDetails(OrgRequest orgRequest,String key){
        List<Organisation> organisationList = orgRequest.getOrganisations();
        String stateLevelTenantId = organisationList.get(0).getTenantId().split("\\.")[0];
        for(Organisation organisation: organisationList){
            if (!CollectionUtils.isEmpty(organisation.getContactDetails())) {
                List<ContactDetails> encryptedContactDetails = (List<ContactDetails>) encryptionDecryptionUtil
                        .encryptObject(organisation.getContactDetails(), stateLevelTenantId, key, ContactDetails.class);
                organisation.setContactDetails(encryptedContactDetails);
            }
        }
        return orgRequest;
    }

    public OrgSearchRequest encryptDetails(OrgSearchRequest orgSearchRequest,String key){
        OrgSearchCriteria searchCriteria = orgSearchRequest.getSearchCriteria();
        if (StringUtils.isNotBlank(searchCriteria.getTenantId())){
            String stateLevelTenantId = searchCriteria.getTenantId().split("\\.")[0];
            OrgSearchCriteria encryptedSearchCrteria = (OrgSearchCriteria) encryptionDecryptionUtil
                    .encryptObject(searchCriteria, stateLevelTenantId, key, OrgSearchCriteria.class);

            orgSearchRequest.setSearchCriteria(encryptedSearchCrteria);
        }
        return orgSearchRequest;
    }

    public List<Organisation> decrypt(List<Organisation> organisationList, String key,OrgSearchRequest orgSearchRequest){
        for(Organisation organisation: organisationList){
            List<ContactDetails> contactDetailsList = organisation.getContactDetails();
            List<ContactDetails> decryptContactDetails = (List<ContactDetails>) encryptionDecryptionUtil.decryptObject(contactDetailsList,key,Organisation.class,orgSearchRequest.getRequestInfo());
            organisation.setContactDetails(decryptContactDetails);
        }
        return organisationList;
    }

}
