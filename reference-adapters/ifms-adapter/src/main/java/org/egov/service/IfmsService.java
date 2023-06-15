package org.egov.service;

import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.ServiceRequestRepository;
import org.egov.utils.AuthenticationUtils;
import org.egov.utils.JitRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


@Service
public class IfmsService {

    @Autowired
    IfmsAdapterConfig config;

    @Autowired
    ServiceRequestRepository requestRepository;

    @Autowired
    AuthenticationUtils authenticationUtils;

    @Autowired
    JitRequestUtils jitRequestUtils;

    public Map<String, String> getKeys() throws NoSuchAlgorithmException {
        Map<String, String> keyMap = new HashMap<>();
        String encryptedAppKey = authenticationUtils.genEncryptedAppKey();
        System.out.println("encryptedAppKey : " + encryptedAppKey);
        keyMap.put("appKey", encryptedAppKey);
        String encAppKey = encryptedAppKey;
        String encodedAppKey = authenticationUtils.genEncodedAppKey(encAppKey);
        keyMap.put("encodedAppKey", encodedAppKey);
        System.out.println("\n\n==================keyMap : ============== \n" + keyMap);
        return  keyMap;
    }

    public Object authenticate(String appKey) {

        // Create HttpHeaders object and set the desired headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("clientId", config.getIfmsJitClientId());
        headers.set("clientSecret", config.getIfmsJitClientSecret());

        Map<String, String> payload = new HashMap<>();
        payload.put("appKey", appKey);

        StringBuilder uri = new StringBuilder(config.getIfmsJitHostName() + config.getIfmsJitAuthEndpoint());
        Object res = requestRepository.fetchResult(uri, payload, headers);
        System.out.println("res : " + res);
        return res;
    }

    public Object ifmsRequest(String authToken, String params, String rek) {

        // Create HttpHeaders object and set the desired headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authToken", authToken);

        Map<String, String> payload = new HashMap<>();
        payload.put("params", params);
        payload.put("rek", rek);

        StringBuilder uri = new StringBuilder(config.getIfmsJitHostName() + config.getIfmsJitRequestEndpoint());
        Object res = requestRepository.fetchResult(uri, payload, headers);
        System.out.println("res : " + res);
        return res;
    }

    public String ifmsJITRequest(String authToken, String params, String rek) {
        // Create HttpHeaders object and set the desired headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authToken", authToken);

        Map<String, String> payload = new HashMap<>();
        payload.put("params", params);
        payload.put("rek", rek);

        StringBuilder uri = new StringBuilder(config.getIfmsJitHostName() + config.getIfmsJitRequestEndpoint());
        String res = requestRepository.fetchResultString(uri, payload, headers);
        System.out.println("res : " + res);
        return res;
    }


}
