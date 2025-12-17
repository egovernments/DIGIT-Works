package org.egov.works.service;

import com.jayway.jsonpath.JsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;

import org.egov.tracer.model.CustomException;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.util.*;

import org.egov.works.config.NotificationServiceConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.egov.works.kafka.Producer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.works.config.Constants.*;

import  org.egov.works.models.*;

@Service
@Slf4j
public class EstimateNotificationService {

    @Autowired
    private Producer producer;
    @Autowired
    private NotificationServiceConfiguration config;
    @Autowired
    private HRMSUtils hrmsUtils;

    @Autowired
    private ProjectServiceUtil projectServiceUtil;

    @Autowired
    private LocationServiceUtil locationServiceUtil;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private LocalizationUtil localizationUtil;

    public void process(final String record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){

        try {
            EstimateRequest estimateRequest= mapper.readValue(record,EstimateRequest.class);
            sendNotification(estimateRequest);
        } catch (Exception e) {
            log.error("Exception while sending notification: ", e);
        }
    }

    /**
     * Sends notification by putting the sms content onto the core-sms topic
     *
     * @param request
     */
    public void sendNotification(EstimateRequest request) {
        Workflow workflow = request.getWorkflow();

        if ("REJECT".equalsIgnoreCase(workflow.getAction())) {
            pushNotificationToCreatorForRejectAction(request);
        } else if ("APPROVE".equalsIgnoreCase(workflow.getAction())) {
            pushNotificationToCreatorForApproveAction(request);
        }
    }

    private void pushNotificationToCreatorForRejectAction(EstimateRequest request) {
        Estimate estimate = request.getEstimate();
        String createdByUuid = request.getEstimate().getAuditDetails().getCreatedBy();

        log.info("get message template for reject action");
        String message = getMessage(request);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }

        //get project number, location, userDetails
        log.info("get project number, location, userDetails");
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);
        Map<String, Object> additionalField=new HashMap<>();
        if(config.isAdditonalFieldRequired()){
            setAdditionalFields(request,ESTIMATE_REJECT_LOCALIZATION_CODE,additionalField);
        }

        log.info("build Message For Reject Action");
        message = buildMessageForRejectAction(estimate, smsDetails, message);
        log.info("push message for REJECT Action");
        checkAdditionalFieldAndPushONSmsTopic(message,additionalField,smsDetails);

    }

    private void pushNotificationToCreatorForApproveAction(EstimateRequest request) {
        Estimate estimate = request.getEstimate();
        String createdByUuid = request.getEstimate().getAuditDetails().getCreatedBy();

        log.info("get message template of creator for approve action");
        String message = getMessage(request);

        if (StringUtils.isEmpty(message)) {
            log.info("SMS content has not been configured for this case");
            return;
        }

        //get project number, location, userDetails
        log.info("get project number, location, userDetails");
        Map<String, String> smsDetails = getDetailsForSMS(request, createdByUuid);
        Map<String, Object> additionalField=new HashMap<>();
        if(config.isAdditonalFieldRequired()){
            setAdditionalFields(request,ESTIMATE_APPROVE_LOCALIZATION_CODE,additionalField);
        }
        log.info("build Message For Approve Action for Estimate Creator");
        message = buildMessageForApproveActionCreator(estimate, smsDetails, message);
        checkAdditionalFieldAndPushONSmsTopic(message,additionalField,smsDetails);
    }



    private Map<String, String> getDetailsForSMS(EstimateRequest request, String uuid) {
        RequestInfo requestInfo = request.getRequestInfo();
        Estimate estimate = request.getEstimate();
        String tenantId = estimate.getTenantId();

        Map<String, String> smsDetails = new HashMap<>();


        //fetch the logged in user's mobileNumber, username, designation
        Map<String, String> userDetailsForSMS = hrmsUtils.
                getEmployeeDetailsByUuid(requestInfo, tenantId, uuid);


        // fetch project details - project name and location
        Map<String, String> projectDetails = projectServiceUtil.getProjectDetails(request.getRequestInfo(), request.getEstimate());


        //get location name from boundary type
        String boundaryCode = projectDetails.get("boundary");
        String boundaryType = projectDetails.get("boundaryType");
        Map<String, String> locationName = locationServiceUtil.getLocationName(tenantId, requestInfo, boundaryCode,boundaryType);

        smsDetails.putAll(userDetailsForSMS);
        smsDetails.putAll(projectDetails);
        smsDetails.putAll(locationName);

        return smsDetails;
    }

    private String getMessage(EstimateRequest request) {
        Workflow workflow = request.getWorkflow();
        String message = null;

        if ("REJECT".equalsIgnoreCase(workflow.getAction())) {
            message = getMessage(request, ESTIMATE_REJECT_LOCALIZATION_CODE);
        } else if ("APPROVE".equalsIgnoreCase(workflow.getAction())) {
            message = getMessage(request, ESTIMATE_APPROVE_LOCALIZATION_CODE);
        }

        return message;
    }

    /**
     * Gets the message from localization
     *
     * @param request
     * @param msgCode
     * @return
     */
    public String getMessage(EstimateRequest request, String msgCode) {
        String rootTenantId = request.getEstimate().getTenantId().split("\\.")[0];
        RequestInfo requestInfo = request.getRequestInfo();
        String locale = "en_IN";
        if(requestInfo.getMsgId().split("\\|").length > 1)
            locale = requestInfo.getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = getLocalisedMessages(requestInfo, rootTenantId,
                locale, ESTIMATE_MODULE_CODE);
        return localizedMessageMap.get(locale + "|" + rootTenantId).get(msgCode);
    }

    /**
     * Builds msg based on the format
     *
     * @param estimate
     * @param message
     * @param userDetailsForSMS
     * @return
     */
    public String buildMessageForRejectAction(Estimate estimate, Map<String, String> userDetailsForSMS, String message) {
        message = message.replace("{estimteno}", estimate.getEstimateNumber())
                .replace("{projectid}",userDetailsForSMS.get(PROJECT_NUMBER))
                .replace("{project_name}", userDetailsForSMS.get(PROJECT_NAME))
                .replace("{location}", userDetailsForSMS.get("locationName"))
                .replace("{username}", userDetailsForSMS.get("userName"))
                .replace("{designation}", userDetailsForSMS.get("designation"));
        return message;
    }

    public String buildMessageForApproveActionCreator(Estimate estimate, Map<String, String> userDetailsForSMS, String message) {
        message = message.replace("{estimteno}", estimate.getEstimateNumber())
                .replace("{projectid}", userDetailsForSMS.get(PROJECT_NUMBER))
                .replace("{project_name}", userDetailsForSMS.get(PROJECT_NAME))
                .replace("{location}", userDetailsForSMS.get("locationName"));
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
            codes = JsonPath.read(result, LOCALIZATION_CODES_JSONPATH);
            messages = JsonPath.read(result, MSGS_JSONPATH);
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
    private void setAdditionalFields(EstimateRequest request, String localizationCode, Map<String, Object> additionalField){
        additionalField.put("templateCode",localizationCode);
        additionalField.put("requestInfo",request.getRequestInfo());
        additionalField.put("tenantId",request.getEstimate().getTenantId());
    }

    private void checkAdditionalFieldAndPushONSmsTopic( String customizedMessage , Map<String, Object> additionalField,Map<String,String> smsDetails){


        if(!additionalField.isEmpty()){
            WorksSmsRequest smsRequest=WorksSmsRequest.builder().message(customizedMessage).additionalFields(additionalField)
                    .mobileNumber(smsDetails.get("mobileNumber")).build();
            log.info("SMS message with Additonal Fields:::::" + smsRequest.toString());
            producer.push(config.getMuktaNotificationTopic(), smsRequest);

        }else{
            digit.models.coremodels.SMSRequest smsRequest = digit.models.coremodels.SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(customizedMessage).build();
            log.info("SMS message without additional fields:::::" + smsRequest.toString());
            producer.push(config.getSmsNotifTopic(), smsRequest);
        }
    }


}
