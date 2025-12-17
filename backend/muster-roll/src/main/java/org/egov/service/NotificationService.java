package org.egov.service;


import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.kafka.MusterRollProducer;
import org.egov.util.LocalizationUtil;
import org.egov.util.NotificationUtil;
import org.egov.web.models.MusterRollRequest;
import org.egov.web.models.WorksSmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.egov.util.MusterRollServiceConstants.*;

@Service
@Slf4j
public class NotificationService {

    private final MusterRollProducer musterRollProducer;

    private final NotificationUtil notificationUtil;

    private final LocalizationUtil localizationUtil;

    private final MusterRollServiceConfiguration config;

    @Autowired
    public NotificationService(MusterRollProducer musterRollProducer, NotificationUtil notificationUtil, LocalizationUtil localizationUtil, MusterRollServiceConfiguration config) {
        this.musterRollProducer = musterRollProducer;
        this.notificationUtil = notificationUtil;
        this.localizationUtil = localizationUtil;
        this.config = config;
    }

    /**
     * Sends sms notification to CBO based on action.
     * @param musterRollRequest
     */
    public void sendNotificationToCBO(MusterRollRequest musterRollRequest){

        if(config.isSMSEnabled()) {
            log.info("Notification is enabled for this service");

            String action = musterRollRequest.getWorkflow().getAction();
            if (action.equalsIgnoreCase(WF_SEND_BACK_TO_CBO_CODE) || action.equalsIgnoreCase(WF_APPROVE_CODE)) {
                Map<String, String> cboDetails = notificationUtil.getCBOContactPersonDetails(musterRollRequest);
                String amount = notificationUtil.getExpenseAmount(musterRollRequest);

                String localisationCode = null;
                String contactMobileNumber = cboDetails.get(CONTACT_MOBILE_NUMBER);
                if (musterRollRequest.getWorkflow().getAction().equalsIgnoreCase(WF_SEND_BACK_TO_CBO_CODE)) {
                    localisationCode = CBO_NOTIFICATION_FOR_CORRECTION_LOCALIZATION_CODE;
                } else if (musterRollRequest.getWorkflow().getAction().equalsIgnoreCase(WF_APPROVE_CODE)) {
                    localisationCode = CBO_NOTIFICATION_OF_APPROVAL_LOCALIZATION_CODE;
                }

                String message = getMessage(musterRollRequest, localisationCode);

                message = buildMessageReplaceVariables(message, musterRollRequest.getMusterRoll().getMusterRollNumber(), amount);

                Map<String, Object> additionalField = new HashMap<>();

//        Set additional field if required
                if (config.isAdditonalFieldRequired()) {
                    setAdditionalFields(musterRollRequest, localisationCode, additionalField);
                }
                Map<String, String> smsDetails = new HashMap<>();
                smsDetails.put("mobileNumber", contactMobileNumber);

//      Create Sms request and push to the kafka topic based on additional field
                checkAdditionalFieldAndPushONSmsTopic(message, additionalField, smsDetails);
            }
        }else{
            log.info("Notification is not enabled for this service");
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
            musterRollProducer.push(config.getMuktaNotificationTopic(), smsRequest);

        }else{
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(customizedMessage).build();
            log.info("SMS message without Additonal Fields:::::" + smsRequest.toString());
            musterRollProducer.push(config.getSmsNotificationTopic(), smsRequest);
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
                locale, MUSTER_ROLL_MODULE_CODE);
        return localizedMessageMap.get(locale + "|" + tenantId).get(msgCode);
    }

    public String buildMessageReplaceVariables(String message, String musterRollName, String amount){
        message = message.replace("{musterrollID}", musterRollName)
                .replace("{amount}", amount);
        return message;
    }

}
