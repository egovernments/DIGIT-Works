package org.egov.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ESAuthUtil {


    @Value("${egov.es.username}")
    private String esUserName;
    @Value("${egov.es.password}")
    private String esPassword;

    public String getESEncodedCredentials() {
        String credentials = esUserName + ":" + esPassword;
        byte[] credentialsBytes = credentials.getBytes();
        byte[] base64CredentialsBytes = Base64.getEncoder().encode(credentialsBytes);
        return "Basic " + new String(base64CredentialsBytes);
    }
}