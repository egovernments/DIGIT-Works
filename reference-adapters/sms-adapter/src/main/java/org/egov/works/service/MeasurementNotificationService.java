package org.egov.works.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.NotificationServiceConfiguration;
import org.egov.works.kafka.Producer;
import org.egov.works.models.MeasurementServiceRequest;
import org.egov.works.models.Workflow;
import org.egov.works.models.WorksSmsRequest;
import org.egov.works.util.MeasurementServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import static org.egov.works.config.Constants.*;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MeasurementNotificationService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private NotificationServiceConfiguration notificationServiceConfiguration;

    @Autowired
    private MeasurementServiceUtil measurementServiceUtil;

    @Autowired
    private Producer producer;

    public void process(final String record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){

        try {
            MeasurementServiceRequest measurementServiceRequest= mapper.readValue(record, MeasurementServiceRequest.class);
            sendNotification(measurementServiceRequest);
        } catch (Exception e) {
            log.error("Exception while sending notification: ", e);
        }
    }

    public void sendNotification(MeasurementServiceRequest request) {

//        Get RequestInfo and Workflow from request
        RequestInfo requestInfo = request.getRequestInfo();

        Workflow workflow = request.getMeasurements().get(0).getWorkflow();

        String localisationCode = null;

//        Set localization code based on the action
        if (workflow.getAction().equalsIgnoreCase(APPROVE_ACTION)) {
            localisationCode = MB_APPROVE_LOCALISATION_CODE;
        } else if (workflow.getAction().equalsIgnoreCase(REJECT_ACTION)) {
            localisationCode = MB_REJECT_LOCALISATION_CODE;
        } else {
            return;
        }

//        Get message from localization based on the locale
        String message = getMessage(request, localisationCode);

        if (StringUtils.isEmpty(message)) {
            log.info("message not configured for this case");
            return;
        }

//        Get project number and mobile number
        String projectNumber = measurementServiceUtil.getProjectNumber(requestInfo, request.getMeasurements().get(0).getTenantId(),
                request.getMeasurements().get(0).getReferenceId());
        String mobileNumber = measurementServiceUtil.getEmployeeMobileNumber(requestInfo, request.getMeasurements().get(0).getTenantId()
                , request.getMeasurements().get(0).getAuditDetails().getCreatedBy());

//        Set reference number and proejct id in the message template
        message = getCustomMessage(message, request.getMeasurements().get(0).getMeasurementNumber(),projectNumber);


        Map<String, Object> additionalField=new HashMap<>();

//        Set additional field if required
        if(notificationServiceConfiguration.isAdditonalFieldRequired()){
            setAdditionalFields(request,localisationCode, additionalField);
        }
        Map<String, String> smsDetails = new HashMap<>();
        smsDetails.put("mobileNumber", mobileNumber);

//      Create Sms request and push to the kafka topic based on additional field
        checkAdditionalFieldAndPushONSmsTopic(message,additionalField,smsDetails);

    }

    private void setAdditionalFields(MeasurementServiceRequest request, String localizationCode,Map<String, Object> additionalField ){
        additionalField.put("templateCode",localizationCode);
        additionalField.put("requestInfo",request.getRequestInfo());
        additionalField.put("tenantId",request.getMeasurements().get(0).getTenantId());

    }

    private void checkAdditionalFieldAndPushONSmsTopic( String customizedMessage , Map<String, Object> additionalField,Map<String,String> smsDetails){

        if(!additionalField.isEmpty()){
            WorksSmsRequest smsRequest=WorksSmsRequest.builder().message(customizedMessage).additionalFields(additionalField)
                    .mobileNumber(smsDetails.get("mobileNumber")).build();
            log.info("SMS message with additonal fields:::::" + smsRequest);
            producer.push(notificationServiceConfiguration.getMuktaNotificationTopic(), smsRequest);

        }else{
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(customizedMessage).build();
            log.info("SMS message without Additonal Fields:::::" + smsRequest.toString());
            producer.push(notificationServiceConfiguration.getSmsNotifTopic(), smsRequest);
        }
    }


    private String getCustomMessage(String message, String measurementNumber, String projectNumber) {
        message = message.replace("{refno}", measurementNumber).replace("{projectid}", projectNumber);
        return message;
    }


    public String getMessage(MeasurementServiceRequest request, String msgCode) {
        String rootTenantId = request.getMeasurements().get(0).getTenantId().split("\\.")[0];
        RequestInfo requestInfo = request.getRequestInfo();
        String locale = "en_IN";
        if(requestInfo.getMsgId().split("\\|").length > 1)
            locale = requestInfo.getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = measurementServiceUtil.getLocalisedMessages(requestInfo, rootTenantId,
                locale, MB_LOCALIZATION_MODULE_CODE);
        return localizedMessageMap.get(locale + "|" + rootTenantId).get(msgCode);
    }


}