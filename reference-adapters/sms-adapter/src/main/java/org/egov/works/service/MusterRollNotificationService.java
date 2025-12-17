package org.egov.works.service;




import com.fasterxml.jackson.databind.ObjectMapper;

import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;

import org.egov.works.config.Constants;
import org.egov.works.config.NotificationServiceConfiguration;
import org.egov.works.kafka.Producer;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import org.egov.works.models.*;
import static org.egov.works.config.Constants.*;

import java.util.*;



@Service
@Slf4j
public class MusterRollNotificationService {

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
    private NotificationUtil notificationUtil;

    @Autowired
    private LocalizationUtil localizationUtil;


    public void process(final String record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        try {
            MusterRollRequest musterRollRequest = mapper.readValue(record, MusterRollRequest.class);
            sendNotificationToCBO(musterRollRequest);
        } catch (Exception e) {
            log.error("Exception while sending notification: ", e);
        }
    }

    /**
     * Sends sms notification to CBO based on action.
     * @param musterRollRequest
     */
    public void sendNotificationToCBO(MusterRollRequest musterRollRequest){
        String action = musterRollRequest.getWorkflow().getAction();
        if(action.equalsIgnoreCase(WF_SEND_BACK_TO_CBO_CODE) || action.equalsIgnoreCase(APPROVE_ACTION)) {
            Map<String, String> cboDetails = notificationUtil.getCBOContactPersonDetails(musterRollRequest);
            String amount = notificationUtil.getExpenseAmount(musterRollRequest);

            String localisationCode = null;
            String contactMobileNumber = cboDetails.get(CONTACT_MOBILE_NUMBER);
            if (musterRollRequest.getWorkflow().getAction().equalsIgnoreCase(WF_SEND_BACK_TO_CBO_CODE)) {
                localisationCode = CBO_NOTIFICATION_FOR_CORRECTION_LOCALIZATION_CODE;
            } else if (musterRollRequest.getWorkflow().getAction().equalsIgnoreCase(APPROVE_ACTION)) {
                localisationCode = CBO_NOTIFICATION_OF_APPROVAL_LOCALIZATION_CODE;
            }

            String message = getMessage(musterRollRequest, localisationCode);

            message = buildMessageReplaceVariables(message, musterRollRequest.getMusterRoll().getMusterRollNumber(), amount);

            Map<String, Object> additionalField=new HashMap<>();

//        Set additional field if required
            if(config.isAdditonalFieldRequired()){
                setAdditionalFields(musterRollRequest,localisationCode, additionalField);
            }
            Map<String, String> smsDetails = new HashMap<>();
            smsDetails.put("mobileNumber", contactMobileNumber);

//      Create Sms request and push to the kafka topic based on additional field
            checkAdditionalFieldAndPushONSmsTopic(message,additionalField,smsDetails);
        }
    }

    private void setAdditionalFields(MusterRollRequest request, String localizationCode,Map<String, Object> additionalField ){
        additionalField.put("templateCode",localizationCode);
        additionalField.put("requestInfo",request.getRequestInfo());
        additionalField.put("tenantId",request.getMusterRoll().getTenantId());

    }

    private void checkAdditionalFieldAndPushONSmsTopic( String customizedMessage , Map<String, Object> additionalField,Map<String,String> smsDetails){

        if(!additionalField.isEmpty()){
            WorksSmsRequest smsRequest=WorksSmsRequest.builder().message(customizedMessage).additionalFields(additionalField)
                    .mobileNumber(smsDetails.get("mobileNumber")).build();
            log.info("SMS message with additonal fields:::::" + smsRequest.toString());
            producer.push(config.getMuktaNotificationTopic(), smsRequest);

        }else{
            digit.models.coremodels.SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(customizedMessage).build();
            log.info("SMS message without Additonal Fields:::::" + smsRequest.toString());
            producer.push(config.getSmsNotifTopic(), smsRequest);
        }
    }

    /**
     * Gets the message from localization
     * @param musterRollRequest
     * @param msgCode
     * @return
     */
    public String getMessage(MusterRollRequest musterRollRequest, String msgCode){
        String tenantId = musterRollRequest.getMusterRoll().getTenantId().split("\\.")[0];;
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        String locale = "en_IN";
        if(requestInfo.getMsgId().split("\\|").length > 1)
            locale = requestInfo.getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = localizationUtil.getLocalisedMessages(requestInfo, tenantId,
                locale, RAINMAKER_COMMON_MODULE_CODE, MSGS_JSONPATH,LOCALIZATION_CODES_JSONPATH);
        return localizedMessageMap.get(locale + "|" + tenantId).get(msgCode);
    }

    public String buildMessageReplaceVariables(String message, String musterRollName, String amount){
        message = message.replace("{musterrollID}", musterRollName)
                .replace("{amount}", amount);
        return message;
    }

}
