package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.util.EncryptionDecryptionUtil;
import org.egov.web.models.ContactDetails;
import org.egov.web.models.OrgRequest;
import org.egov.web.models.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class EncryptionService {
    @Autowired
    private EncryptionDecryptionUtil encryptionDecryptionUtil;
    public OrgRequest encryptDetails(OrgRequest orgRequest,String key){
        List<Organisation> organisationList = orgRequest.getOrganisations();
        String stateLevelTenantId = orgRequest.getRequestInfo().getUserInfo().getTenantId();
        for(Organisation organisation: organisationList){
            if (!CollectionUtils.isEmpty(organisation.getContactDetails())) {
                List<ContactDetails> encryptedContactDetails = (List<ContactDetails>) encryptionDecryptionUtil
                        .encryptObject(organisation.getContactDetails(), stateLevelTenantId, key, ContactDetails.class);
                organisation.setContactDetails(encryptedContactDetails);
            }
        }
        return orgRequest;
    }

}
