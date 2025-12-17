package org.egov.web.notification.sms.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.web.notification.sms.consumer.contract.WorksSMSRequest;
import org.egov.web.notification.sms.models.Sms;
import org.egov.web.notification.sms.models.WorksSms;
import org.egov.web.notification.sms.service.BaseSMSService;
import org.egov.web.notification.sms.utils.MdmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@Service
@Slf4j
@ConditionalOnProperty(value = "sms.provider.class", matchIfMissing = true, havingValue = "CDAC")
public class CDACSMSServiceImpl extends BaseSMSService {

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected ObjectMapper objectMapper;

    private static final String SMS_RESPONSE_NOT_SUCCESSFUL = "Sms response not successful";

    private static final String SMS_REQUEST_BODY_VALIDATION_ERROR = " Required sms attributes are not passed in the request.";

    @Autowired
    private MdmsUtil mdmsUtil;

    private static final String WORKS_NOTIFICATION_MODULE_NAME = "muktaNotification";
    public static final String NOTIFICATION_MASTERNAME = "NotificationTemplateAndActionMapping";
    public static final List<String> NOTIFICATION_MDMS_MASTER_NAME = Collections
            .unmodifiableList(Arrays.asList(NOTIFICATION_MASTERNAME));


    public void sendSMS(WorksSms worksSms) {
        if (!worksSms.isValid()) {
            log.error(String.format("Sms %s is not valid", worksSms));
            return;
        }

        if (smsProperties.isNumberBlacklisted(worksSms.getMobileNumber())) {
            log.error(String.format("Sms to %s is blacklisted", worksSms.getMobileNumber()));
            return;
        }

        if (!smsProperties.isNumberWhitelisted(worksSms.getMobileNumber())) {
            log.error(String.format("Sms to %s is not in whitelist", worksSms.getMobileNumber()));
            return;
        }
        submitToExternalSmsService(worksSms);
    }

    protected void submitToExternalSmsService(WorksSms sms) {
        try {

            String url = smsProperties.getUrl();
            final MultiValueMap<String, String> requestBody = getSmsRequestBody(sms);
            URI final_url = UriComponentsBuilder.fromHttpUrl(url).build().encode().toUri();
            if (smsProperties.requestType.equals("POST")) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                HttpEntity<MultiValueMap<String, String>> requestEntity =
                        new HttpEntity<>(requestBody, headers);

                executeAPI(final_url, HttpMethod.POST, requestEntity, requestBody);

            } else {
                executeAPI(final_url, HttpMethod.GET, null, requestBody);
            }

        } catch (RestClientException e) {
            log.error("Error occurred while sending SMS to " + sms.getMobileNumber(), e);
            throw e;
        }
    }

    public MultiValueMap<String, String> getSmsRequestBody(WorksSms worksSms) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Map<String, Object> mdmsData = fetchMdmsData(worksSms);
        if (worksSms.getAdditionalFields().isEmpty() || worksSms.getAdditionalFields().get("templateCode") == null
                || worksSms.getAdditionalFields().get("requestInfo") == null || worksSms.getAdditionalFields().get("tenantId") == null) {

            //TODO:
            //Need to throw Custom Exception
            log.info("Required attributes templateCode,requestInfo,tenantId not passed in the additional Fields of the SMS request from the service");
            throw new RuntimeException(SMS_RESPONSE_NOT_SUCCESSFUL);

        } else {
            String smsAction = mdmsData.get("action").toString();
            String smsTemplateId = mdmsData.get("templateId").toString();
            for (String key : smsProperties.getConfigMap().keySet()) {
                String value = smsProperties.getConfigMap().get(key);
                if (value.startsWith("$")) {
                    switch (value) {
                        case "$action":
                            map.add(key, smsAction);
                            break;
                        case "$source":
                            map.add(key, smsProperties.getSenderid());
                            break;
                        case "$department_id":
                            map.add(key, smsProperties.getDepartmentId());
                            break;
                        case "$template_id":
                            map.add(key, smsTemplateId);
                            break;
                        case "$smsContent":
                            map.add(key, worksSms.getMessage());
                            break;
                        case "$phonenumber":
                            map.add(key, worksSms.getMobileNumber());
                            break;
                        default:
                            if (env.containsProperty(value.substring(1))) {
                                map.add(key, env.getProperty(value.substring(1)));
                            } else if (smsProperties.getExtraConfigMap().containsKey(value.substring(1))) {
                                map.add(key, smsProperties.getExtraConfigMap().get(value.substring(1)));
                            } else if (smsProperties.getCategoryMap().containsKey(value.substring(1))) {
                                Map<String, Map<String, String>> categoryMap = smsProperties.getCategoryMap();
                                Map<String, String> categoryValue = categoryMap.get(value.substring(1));
                                if (worksSms.getAdditionalFields().get("category") == null && categoryValue.containsKey('*')) {
                                    map.add(key, categoryValue.get('*'));
                                } else if (worksSms.getAdditionalFields().get("category") != null) {
                                    if (categoryValue.containsKey(worksSms.getAdditionalFields().get("category").toString())) {
                                        map.add(key, categoryValue.get(worksSms.getAdditionalFields().get("category").toString()));
                                    } else if (categoryValue.containsKey('*')) {
                                        map.add(key, categoryValue.get('*'));
                                    }
                                }
                            } else {
                                map.add(key, value);
                            }
                            break;
                    }
                } else {
                    map.add(key, value);
                }

            }

        }

        return map;
    }

    private Map<String, Object> fetchMdmsData(WorksSms worksSms) {
        Map<String, Object> actionAndTemplateId = new HashMap<>();

        try {
            String tenantId = (String) worksSms.getAdditionalFields().get("tenantId");
            String rootTenantId = tenantId.split("\\.")[0];
            String templateCode = (String) worksSms.getAdditionalFields().get("templateCode");

            //RequestInfo  requestInfo= (RequestInfo) worksSms.getAdditionalFields().get("requestInfo");
            RequestInfo requestInfo = objectMapper.convertValue(worksSms.getAdditionalFields().get("requestInfo"), RequestInfo.class);
            String locale = requestInfo.getMsgId().split("\\|")[1];

            Map<String, Map<String, JSONArray>> mdmsData = mdmsUtil.fetchMdmsData(requestInfo,
                    rootTenantId, WORKS_NOTIFICATION_MODULE_NAME, NOTIFICATION_MDMS_MASTER_NAME);

            List<Map<String, Object>> notificationTemplateAndActionMappingList = null;
            notificationTemplateAndActionMappingList = JsonPath.read(mdmsData.get(WORKS_NOTIFICATION_MODULE_NAME).get(NOTIFICATION_MASTERNAME), "$.*");

            for (Map<String, Object> notificationMap : notificationTemplateAndActionMappingList) {

                if (notificationMap.get("templateCode").toString().equalsIgnoreCase(templateCode) &&
                        notificationMap.get("locale").toString().equalsIgnoreCase(locale)) {
                    actionAndTemplateId.put("action", notificationMap.get("action").toString());
                    actionAndTemplateId.put("templateId", notificationMap.get("templateId").toString());

                }

            }


        } catch (Exception e) {
            log.error("Exception while fetchin the mdms data: ", e);
            log.info("MDMS data for creating the sms request body is not configured properly");
        }
        return actionAndTemplateId;
    }


    @Override
    protected void submitToExternalSmsService(Sms sms) {

    }

    /**
     * @param uri
     * @param method
     * @param requestEntity
     * @param *             @param <T>
     * @return
     */
    protected ResponseEntity<Map<String, Object>> executeAPI(URI uri, HttpMethod method, HttpEntity<?> requestEntity, MultiValueMap<String, String> requestBody) {

        if (!isRequestValidated(requestBody)) {
            throw new RuntimeException(SMS_REQUEST_BODY_VALIDATION_ERROR);
        }
        ResponseEntity<Map<String, Object>> res = restTemplate.exchange(uri, method, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {
        });

        Map<String, Object> responseMap = res.getBody();
        if (!isResponseValidated(responseMap)) {
            log.error("Response from API - " + responseMap);
            throw new RuntimeException(SMS_RESPONSE_NOT_SUCCESSFUL);
        }


        return res;
    }

    /*   {
           "success": "true",
               "responsetime": 8.653711080551147,
               "message": "Message Send Successfully",
               "status": 1
       }*/
    protected boolean isResponseValidated(Map<String, Object> responseMap) {

        if (!responseMap.isEmpty()) {
            return responseMap.get("success").toString().equalsIgnoreCase("true") && (int) responseMap.get("status") == 1;
        }

        return false;
    }

    protected boolean isRequestValidated(MultiValueMap<String, String> requestBody) {
        if (requestBody.get("department_id").isEmpty()) {
            log.info("Department Id is not passed in the request");
            return false;

        }
        if (requestBody.get("action").isEmpty()) {
            log.info("Sms Action is not passed in the request");
            return false;
        }
        if (requestBody.get("source").isEmpty()) {
            log.info("Source of the message is not passed in the request");
            return false;
        }
        if (requestBody.get("department_id").isEmpty()) {
            log.info("Department Id  is not passed in the request");
            return false;
        }
        if (requestBody.get("sms_content").isEmpty()) {
            log.info("SMS Content is not passed in the request");
            return false;
        }
        if (requestBody.get("phonenumber").isEmpty()) {
            log.info("Phone Number is not passed in the request");
            return false;
        }

        if (requestBody.get("template_id").isEmpty()) {
            log.info("Template Id of the particular message template is not passed in the request");
            return false;
        }
        return true;


    }


}

