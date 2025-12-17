package org.egov.individual.service;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.core.Role;
import org.egov.common.models.individual.IndividualRequest;
import org.egov.individual.Constants;
import org.egov.individual.config.IndividualProperties;
import org.egov.individual.producer.IndividualProducer;
import org.egov.individual.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.egov.individual.web.models.WorksSmsRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.individual.Constants.*;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    IndividualProperties config;
    @Autowired
    private IndividualProducer producer;
    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Sends notification by putting the sms content onto the core-sms topic
     *
     * @param request
     */
    public void sendNotification(IndividualRequest request, boolean isCreateOperation) {
        if (isCreateOperation) {
            pushNotificationForCreate(request);
        } else {
            pushNotificationForUpdate(request);
        }
    }

    private void pushNotificationForCreate(IndividualRequest request) {
        log.info("get message template for Create action");
        List<Role> userRoles= new ArrayList<>();
        if(request.getIndividual().getUserDetails()==null){
            log.info("User details is not passed for Individual Creation ");
        }else{
             userRoles= request.getIndividual().getUserDetails().getRoles();
        }

        if(!userRoles.isEmpty()&&ORG_CITIZEN_ROLE_NAME.equals(userRoles.get(0).getName())){
            log.info("User details with Organisation Admin role is passed");
        }else{

            String message = getMessageForCreate(request);

            if (StringUtils.isEmpty(message)) {
                log.info("SMS content has not been configured for this case");
                return;
            }

            //get individual name, id, mobileNumber
            log.info("get individual name, id, mobileNumber");
            Map<String, String> smsDetails = getDetailsForSMS(request);
            Map<String, Object> additionalField = new HashMap<>();
            if (config.isAdditonalFieldRequired()) {
                setAdditionalFields(request, INDIVIDUAL_CREATE_LOCALIZATION_CODE, additionalField);
            }

            log.info("build Message For CREATE Action");
            message = buildMessageForCreate(smsDetails, message);
            log.info("push message for CREATE Action");
            checkAdditionalFieldAndPushONSmsTopic(message, additionalField, smsDetails);
        }

    }

    private void pushNotificationForUpdate(IndividualRequest request) {

        log.info("get message template of creator for approve action");
        String message = getMessageForUpdate(request);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }

        //get individual name, id, mobileNumber
        log.info("get project number, location, userDetails");
        Map<String, String> smsDetails = getDetailsForSMS(request);
        Map<String, Object> additionalField = new HashMap<>();
        if (config.isAdditonalFieldRequired()) {
            setAdditionalFields(request, INDIVIDUAL_UPDATE_LOCALIZATION_CODE, additionalField);
        }

        log.info("build Message For UPDATE action to Creator");
        message = buildMessageForUpdate(smsDetails, message);
        log.info("push Message For UPDATE action to Creator");
        checkAdditionalFieldAndPushONSmsTopic(message, additionalField, smsDetails);
    }

    private Map<String, String> getDetailsForSMS(IndividualRequest request) {
        Map<String, String> smsDetails = new HashMap<>();

        smsDetails.put("individualName", request.getIndividual().getName().getGivenName());
        smsDetails.put("registrationID", request.getIndividual().getIndividualId());
        smsDetails.put("mobileNumber", request.getIndividual().getMobileNumber());


        return smsDetails;
    }

    private String getMessageForCreate(IndividualRequest request) {
        String message = getMessage(request, INDIVIDUAL_CREATE_LOCALIZATION_CODE);
        return message;
    }

    private String getMessageForUpdate(IndividualRequest request) {
        String message = getMessage(request, INDIVIDUAL_UPDATE_LOCALIZATION_CODE);
        return message;
    }

    /**
     * Gets the message from localization
     *
     * @param request
     * @param msgCode
     * @return
     */
    public String getMessage(IndividualRequest request, String msgCode) {
        String rootTenantId = request.getIndividual().getTenantId().split("\\.")[0];
        RequestInfo requestInfo = request.getRequestInfo();
        String locale = "en_IN";
        if (requestInfo.getMsgId().split("\\|").length > 1)
            locale = requestInfo.getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = getLocalisedMessages(requestInfo, rootTenantId,
                locale, Constants.INDIVIDUAL_MODULE_CODE);
        return localizedMessageMap.get(locale + "|" + rootTenantId).get(msgCode);
    }

    /**
     * Builds msg based on the format
     *
     * @param message
     * @param userDetailsForSMS
     * @return
     */
    public String buildMessageForCreate(Map<String, String> userDetailsForSMS, String message) {
        message = message.replace("{individualName}", userDetailsForSMS.get("individualName"))
                .replace("{registrationID}", userDetailsForSMS.get("registrationID"));
        return message;
    }

    public String buildMessageForUpdate(Map<String, String> userDetailsForSMS, String message) {
        message = message.replace("{individualName}", userDetailsForSMS.get("individualName"))
                .replace("{wageseekerID}", userDetailsForSMS.get("registrationID"));
        return message;
    }

    /**
     * Creates a cache for localization that gets refreshed at every call.
     *
     * @param requestInfo
     * @param rootTenantId
     * @param locale
     * @param module
     * @return
     */
    public Map<String, Map<String, String>> getLocalisedMessages(RequestInfo requestInfo, String rootTenantId, String locale, String module) {
        Map<String, Map<String, String>> localizedMessageMap = new HashMap<>();
        Map<String, String> mapOfCodesAndMessages = new HashMap<>();
        StringBuilder uri = new StringBuilder();
        RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
        requestInfoWrapper.setRequestInfo(requestInfo);
        uri.append(config.getLocalizationHost()).append(config.getLocalizationContextPath())
                .append(config.getLocalizationSearchEndpoint()).append("?tenantId=" + rootTenantId)
                .append("&module=" + module).append("&locale=" + locale);
        List<String> codes = null;
        List<String> messages = null;
        Object result = null;
        try {
            result = repository.fetchResult(uri, requestInfoWrapper);
            codes = JsonPath.read(result, Constants.INDIVIDUAL_LOCALIZATION_CODES_JSONPATH);
            messages = JsonPath.read(result, Constants.INDIVIDUAL_LOCALIZATION_MSGS_JSONPATH);
        } catch (Exception e) {
            log.error("Exception while fetching from localization: " + e);
        }
        if (null != result) {
            for (int i = 0; i < codes.size(); i++) {
                mapOfCodesAndMessages.put(codes.get(i), messages.get(i));
            }
            localizedMessageMap.put(locale + "|" + rootTenantId, mapOfCodesAndMessages);
        }

        return localizedMessageMap;
    }

    private void setAdditionalFields(IndividualRequest request, String localizationCode, Map<String, Object> additionalField) {
        additionalField.put("templateCode", localizationCode);
        additionalField.put("requestInfo", request.getRequestInfo());
        additionalField.put("tenantId", request.getIndividual().getTenantId());
    }


    private void checkAdditionalFieldAndPushONSmsTopic(String customizedMessage, Map<String, Object> additionalField, Map<String, String> smsDetails) {


        if (!additionalField.isEmpty()) {
            WorksSmsRequest smsRequest = WorksSmsRequest.builder().message(customizedMessage).additionalFields(additionalField)
                    .mobileNumber(smsDetails.get("mobileNumber")).build();
            log.info("SMS message:::::" + smsRequest.toString());
            producer.push(config.getMuktaNotificationTopic(), smsRequest);

        } else {
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(customizedMessage).build();
            log.info("SMS message without additional fields:::::" + smsRequest.toString());
            producer.push(config.getSmsNotifTopic(), smsRequest);
        }
    }

}

