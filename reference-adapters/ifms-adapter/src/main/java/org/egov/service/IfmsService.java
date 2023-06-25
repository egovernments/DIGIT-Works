package org.egov.service;

import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.ServiceRequestRepository;
import org.egov.utils.AuthenticationUtils;
import org.egov.utils.JitRequestUtils;
import org.egov.utils.MdmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.config.Constants.*;


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

    @Autowired
    MdmsUtils mdmsUtils;

    @Autowired
    IfmsAdapterConfig ifmsAdapterConfig;

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

    public JSONArray getHeadOfAccounts(RequestInfo requestInfo) {
        List<String> ifmsMasters = new ArrayList<>();
        ifmsMasters.add(MDMS_HEAD_OF_ACCOUNT_MASTER);
        Map<String, Map<String, JSONArray>> ifmsHOAResponse = mdmsUtils.fetchMdmsData(requestInfo, ifmsAdapterConfig.getStateLevelTenantId(), MDMS_IFMS_MODULE_NAME, ifmsMasters);
        System.out.println(ifmsHOAResponse);
        return ifmsHOAResponse.get(MDMS_IFMS_MODULE_NAME).get(MDMS_HEAD_OF_ACCOUNT_MASTER);
    }

    public JSONArray getSchemeDetails(RequestInfo requestInfo) {
        List<String> ifmsMasters = new ArrayList<>();
        ifmsMasters.add(MDMS_SCHEMA_DETAILS_MASTER);
        Map<String, Map<String, JSONArray>> ifmsSchemaResponse = mdmsUtils.fetchMdmsData(requestInfo, ifmsAdapterConfig.getStateLevelTenantId(), MDMS_IFMS_MODULE_NAME, ifmsMasters);
        System.out.println(ifmsSchemaResponse);
        return ifmsSchemaResponse.get(MDMS_IFMS_MODULE_NAME).get(MDMS_SCHEMA_DETAILS_MASTER);
    }



}
