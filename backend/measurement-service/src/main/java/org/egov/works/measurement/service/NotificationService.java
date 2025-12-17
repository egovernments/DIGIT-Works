package org.egov.works.measurement.service;

import org.egov.works.services.common.models.estimate.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.measurement.config.MBServiceConfiguration;
import org.egov.works.measurement.config.ServiceConstants;
import org.egov.works.measurement.kafka.MBServiceProducer;
import org.egov.works.measurement.util.NotificationUtil;
import org.egov.works.measurement.web.models.MeasurementServiceRequest;
import org.egov.works.measurement.web.models.WorksSmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static org.egov.works.measurement.config.ServiceConstants.*;

@Service
@Slf4j
public class NotificationService {

    private final MBServiceProducer producer;
    private final MBServiceConfiguration config;
    private final NotificationUtil notificationUtil;

    @Autowired
    public NotificationService(MBServiceProducer producer, MBServiceConfiguration config, NotificationUtil notificationUtil) {
        this.producer = producer;
        this.config = config;
        this.notificationUtil = notificationUtil;
    }

    public void sendNotification(MeasurementServiceRequest request) {

        if(config.isSMSEnabled()) {
            log.info("Notification is enabled for this service");

//        Get RequestInfo and Workflow from request
            RequestInfo requestInfo = request.getRequestInfo();

            Workflow workflow = request.getMeasurements().get(0).getWorkflow();

            String localisationCode = null;

//        Set localization code based on the action
            if (workflow.getAction().equalsIgnoreCase(APPROVE_ACTION)) {
                localisationCode = APPROVE_LOCALISATION_CODE;
            } else if (workflow.getAction().equalsIgnoreCase(REJECT_ACTION)) {
                localisationCode = REJECT_LOCALISATION_CODE;
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
            String projectNumber = notificationUtil.getProjectNumber(requestInfo, request.getMeasurements().get(0).getTenantId(),
                    request.getMeasurements().get(0).getReferenceId());
            String mobileNumber = notificationUtil.getEmployeeMobileNumber(requestInfo, request.getMeasurements().get(0).getTenantId()
                    , request.getMeasurements().get(0).getAuditDetails().getCreatedBy());

//        Set reference number and proejct id in the message template
            message = getCustomMessage(message, request.getMeasurements().get(0).getMeasurementNumber(), projectNumber);


            Map<String, Object> additionalField = new HashMap<>();

//        Set additional field if required
            if (config.isAdditonalFieldRequired()) {
                setAdditionalFields(request, localisationCode, additionalField);
            }
            Map<String, String> smsDetails = new HashMap<>();
            smsDetails.put("mobileNumber", mobileNumber);

//      Create Sms request and push to the kafka topic based on additional field
            checkAdditionalFieldAndPushONSmsTopic(message, additionalField, smsDetails);
        }else{
            log.info("Notification is not enabled for this service");
        }

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
            log.info("SMS message with additonal fields:::::" + smsRequest.toString());
            producer.push(config.getMuktaNotificationTopic(), smsRequest);

        }else{
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(customizedMessage).build();
            log.info("SMS message without Additonal Fields:::::" + smsRequest.toString());
            producer.push(config.getSmsNotifTopic(), smsRequest);
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
        Map<String, Map<String, String>> localizedMessageMap = notificationUtil.getLocalisedMessages(requestInfo, rootTenantId,
                locale, ServiceConstants.MB_LOCALIZATION_MODULE_CODE);
        return localizedMessageMap.get(locale + "|" + rootTenantId).get(msgCode);
    }


}
