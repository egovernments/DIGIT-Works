package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.config.JITAuthValues;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.egov.utils.*;
import org.egov.web.models.MsgCallbackHeader;
import org.egov.web.models.enums.Action;
import org.egov.web.models.enums.MessageType;
import org.egov.web.models.jit.JITRequest;
import org.egov.web.models.jit.JITRequestLog;
import org.egov.web.models.jit.JITResponse;
import org.egov.web.models.program.ProgramSearch;
import org.egov.web.models.program.ProgramSearchRequest;
import org.egov.web.models.program.ProgramSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.egov.config.Constants.*;


@Service
public class IfmsService {

    @Autowired
    private IfmsAdapterConfig config;

    @Autowired
    private ServiceRequestRepository requestRepository;

    @Autowired
    private AuthenticationUtils authenticationUtils;

    @Autowired
    private JitRequestUtils jitRequestUtils;

    @Autowired
    private MdmsUtils mdmsUtils;

    @Autowired
    private IfmsAdapterConfig ifmsAdapterConfig;

    @Autowired
    private JITAuthValues jitAuthValues;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ESLogUtils esLogUtils;

    @Autowired
    private MockTestService mockTestService;

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

    public JITResponse sendRequest(String tenantId, JITRequest jitRequest) {
        if (config.getIfmsJitMockEnabled() && config.getIfmsMockEnabledTenantsIds().length != 0
                && !StringUtils.isEmpty(tenantId) && Arrays.asList(config.getIfmsMockEnabledTenantsIds()).contains(tenantId)) {
            return mockTestService.getMockResponse(tenantId, jitRequest);
        } else {
            return sendRequestToIFMS(jitRequest);
        }
    }

    public JITResponse sendRequestToIFMS(JITRequest jitRequest) {
        // Check if the auth token is null or the session has expired
        if (jitAuthValues.getAuthToken() == null || (System.currentTimeMillis() - jitAuthValues.getLastLogin()) >= config.getIfmsSessionTimeout()){
            getAuthDetailsFromIFMS();
        }
        JITResponse decryptedResponse = null;
        try {
            decryptedResponse = callServiceAPI(jitRequest);
//            decryptedResponse = loadCustomResponse(jitRequest.getServiceId().toString());
        } catch (Exception e) {
            String message = e.toString();
            if(message.contains(JIT_UNAUTHORIZED_REQUEST_EXCEPTION)) {
                try {
                    getAuthDetailsFromIFMS();
                    decryptedResponse = callServiceAPI(jitRequest);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                throw new RuntimeException(e);
            }
        }
        return decryptedResponse;
    }

    private JITResponse callServiceAPI(JITRequest jitRequest) throws Exception {
        JITResponse decryptedResponse = null;
        Map<String, String> payload = new HashMap<>();
        try {
            payload = (Map<String, String>) jitRequestUtils.getEncryptedRequestBody(jitAuthValues.getSekString(), jitRequest);
            String response = ifmsJITRequest(String.valueOf(jitAuthValues.getAuthToken()), payload.get("encryptedPayload"), payload.get("encryptionRek"));
            decryptedResponse = jitRequestUtils.decryptResponse(payload.get("decryptionRek"), response);
            // Log request
            esLogUtils.saveJitRequestLogsToES(jitRequest, response, payload.get("decryptionRek"));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            int statusCode = e.getRawStatusCode();
            //Log to ES
            JITRequestLog jitRequestLog = esLogUtils.saveJitRequestLogsToES(jitRequest, null, null);
            esLogUtils.saveErrorResponseLogsToES(jitRequestLog, jitRequest, payload, e, statusCode);
            throw e;
        } catch (Exception e) {
            //Log to ES
            JITRequestLog jitRequestLog = esLogUtils.saveJitRequestLogsToES(jitRequest, null, null);
            esLogUtils.saveErrorResponseLogsToES(jitRequestLog, jitRequest, payload, e, null);
            throw e;
        }
        return decryptedResponse;
    }

    private void getAuthDetailsFromIFMS() {
        Map<String, String> appKeys = new HashMap<>();
        try {
            appKeys = getKeys();
            Map<String, String> authResponse = (Map<String, String>) authenticate(appKeys.get("encodedAppKey"));
            appKeys.put("authToken", authResponse.get("authToken"));
            appKeys.put("sek", authResponse.get("sek"));
            String decryptedSek = authenticationUtils.getDecryptedSek(appKeys.get("appKey"), authResponse.get("sek"));
            appKeys.put("decryptedSek", decryptedSek);

            // Set authentication details to the request
            jitAuthValues.setAuthToken(authResponse.get("authToken"));
            jitAuthValues.setSekString(decryptedSek);
            jitAuthValues.setLastLogin(System.currentTimeMillis());
            // save auth response to ES
            esLogUtils.saveAuthenticateRequest(appKeys.toString(), authResponse.toString());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            int statusCode = e.getRawStatusCode();
            //Log to ES
            JITRequestLog jitRequestLog = esLogUtils.saveAuthenticateRequest(appKeys.toString(), null);
            esLogUtils.saveAuthenticateFailureRequest(appKeys.toString(), e, statusCode, jitRequestLog);
            throw new RuntimeException(e);
        } catch (Exception e) {
            //Log to ES
            JITRequestLog jitRequestLog = esLogUtils.saveAuthenticateRequest(appKeys.toString(), null);
            esLogUtils.saveAuthenticateFailureRequest(appKeys.toString(), e, null, jitRequestLog);
            throw new RuntimeException(e);
        }
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
        Object res = requestRepository.fetchResultWithHeader(uri, payload, headers);
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
        Object res = requestRepository.fetchResultWithHeader(uri, payload, headers);
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
        Map<String, Map<String, JSONArray>> ifmsHOAResponse = mdmsUtils.fetchMdmsDataWithActiveFilter(requestInfo, ifmsAdapterConfig.getStateLevelTenantId(), MDMS_IFMS_MODULE_NAME, ifmsMasters);
        return ifmsHOAResponse.get(MDMS_IFMS_MODULE_NAME).get(MDMS_HEAD_OF_ACCOUNT_MASTER);
    }

    public JSONArray getSchemeDetails(RequestInfo requestInfo) {
        List<String> ifmsMasters = new ArrayList<>();
        ifmsMasters.add(MDMS_SCHEMA_DETAILS_MASTER);
        Map<String, Map<String, JSONArray>> ifmsSchemaResponse = mdmsUtils.fetchMdmsData(requestInfo, ifmsAdapterConfig.getStateLevelTenantId(), MDMS_IFMS_MODULE_NAME, ifmsMasters);
        return ifmsSchemaResponse.get(MDMS_IFMS_MODULE_NAME).get(MDMS_SCHEMA_DETAILS_MASTER);
    }
    public JSONArray getSSUDetails(RequestInfo requestInfo, String tenantId) {
        List<String> ssuMasters = new ArrayList<>();
        ssuMasters.add(MDMS_SSU_DETAILS_MASTER);
        Map<String, Map<String, JSONArray>> ssuDetailsResponse = mdmsUtils.fetchMdmsDataWithActiveFilter(requestInfo, tenantId, MDMS_IFMS_MODULE_NAME, ssuMasters);
        return ssuDetailsResponse.get(MDMS_IFMS_MODULE_NAME).get(MDMS_SSU_DETAILS_MASTER);
    }

    /*
        It's for testing the multiple combination of VA response
        TODO: Remove after development
     */
    public JITResponse loadCustomResponse(String serviceId) {
        JITResponse vaResponse = null;
        String filename = "";
        switch (serviceId) {
            case "VA":
                filename = "1VAResponse.json";
                break;
            case "PI":
                filename = "2PIResponse.json";
                break;
            case "PIS":
                filename = "3PISResponse.json";
                break;
            case "PAG":
                filename = "4PAGResponse.json";
                break;
            case "PD":
                filename = "5PDResponse.json";
                break;
            case "FD":
                filename = "6FDNewResponse.json";
                break;
            case "COR":
                filename = "7CORSuccess.json";
                break;
            case "FTPS":
                filename = "8FTPSResponse.json";
                break;
            case "FTFPS":
                filename = "9FTFPSResponse.json";
                break;
        }
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sample/"+ filename);

            if (inputStream != null) {
                vaResponse = objectMapper.readValue(inputStream, JITResponse.class);
            } else {
                // Handle the case where the file is not found
                System.err.println("File not found: " + filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vaResponse;
    }

    public MsgCallbackHeader getMessageCallbackHeader(RequestInfo requestInfo,String locationCode) {
        String idFormat = "program@{URI}";
        Map<String,Map<String,JSONArray>> exchangeServers = mdmsUtils.fetchExchangeServers(requestInfo,locationCode);
        JSONArray exchangeServer = exchangeServers.get(MDMS_EXCHANGE_MODULE_NAME).get(MDMS_EXCHANGE_SERVER_MASTER);
        String senderId = null;
        String receiverId = null;
        for (Object o : exchangeServer) {
            Map<String, String> exchangeServerMap = (Map<String, String>) o;
            if (exchangeServerMap.get("code").equals("IFMS")) {
                senderId = exchangeServerMap.get("hostUrl");
            }
            if (exchangeServerMap.get("code").equals("MUKTA")) {
                receiverId = exchangeServerMap.get("hostUrl");
            }
        }

        return MsgCallbackHeader.builder()
                .messageId(UUID.randomUUID().toString())
                .senderId(idFormat.replace("{URI}", Objects.requireNonNull(extractHostUrlFromURL(senderId))))
                .receiverId(idFormat.replace("{URI}", Objects.requireNonNull(extractHostUrlFromURL(receiverId))))
                .senderUri(extractHostUrlFromURL(senderId))
                .build();
    }
    private static String extractHostUrlFromURL(String input) {
        // Regular expression pattern to match the domain with http/https
        // Pattern pattern = Pattern.compile("(https?://[a-zA-Z0-9.-]+)");
        // Regex pattern to match the domain with http/https
        String regex = "\\bhttps?://\\S+\\b";
        // Regular expression pattern to match the domain with http/https
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(); // Returns the matched domain with http/https
        } else {
            return null; // No match found
        }
    }
}
