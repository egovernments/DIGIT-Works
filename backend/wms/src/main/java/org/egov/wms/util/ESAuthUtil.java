package org.egov.wms.util;

import org.egov.wms.config.SearchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ESAuthUtil {

    private final SearchConfiguration config;

    @Autowired
    public ESAuthUtil(SearchConfiguration config) {
        this.config = config;
    }


    public String getESEncodedCredentials() {
        String credentials = config.getEsUserName() + ":" + config.getEsPassword();
        byte[] credentialsBytes = credentials.getBytes();
        byte[] base64CredentialsBytes = Base64.getEncoder().encode(credentialsBytes);
        return "Basic " + new String(base64CredentialsBytes);
    }

}
