package org.egov.service;


import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.kafka.Producer;
import org.egov.util.LocalizationUtil;
import org.egov.util.NotificationUtil;
import org.egov.web.models.MusterRollRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.egov.util.MusterRollServiceConstants.*;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private Producer producer;

    @Autowired
    private NotificationUtil notificationUtil;

    @Autowired
    private LocalizationUtil localizationUtil;

    @Autowired
    private MusterRollServiceConfiguration config;

    /**
     * Sends sms notification to CBO based on action.
     * @param musterRollRequest
     */
    public void sendNotificationToCBO(MusterRollRequest musterRollRequest){
        String action = musterRollRequest.getWorkflow().getAction();
        if(action.equalsIgnoreCase("SENDBACKTOCBO") || action.equalsIgnoreCase("RE-SUBMIT")
            || action.equalsIgnoreCase("APPROVE")) {
                Map<String, String> CBODetails = notificationUtil.getCBOContactPersonDetails(musterRollRequest);
                String amount = notificationUtil.getExpenseAmount(musterRollRequest);

                String message = null;
                String contactMobileNumber = CBODetails.get(CONTACT_MOBILE_NUMBER);
            if (musterRollRequest.getWorkflow().getAction().equalsIgnoreCase("SENDBACKTOCBO")) {
                message = getMessage(musterRollRequest, "MUSTER_ROLL_CBO_NOTIFICATION_FOR_CORRECTION");
            } else if (musterRollRequest.getWorkflow().getAction().equalsIgnoreCase("RE-SUBMIT")) {
                message = getMessage(musterRollRequest, "MUSTER_ROLL_CBO_NOTIFICATION_FOR_RE-SUBMIT");
            } else if (musterRollRequest.getWorkflow().getAction().equalsIgnoreCase("APPROVE")) {
                message = getMessage(musterRollRequest, "MUSTER_ROLL_CBO_NOTIFICATION_OF_APPROVAL");
            }
            musterRollRequest.getMusterRoll().getMusterRollNumber();

            String customizedMessage = buildMessageForSendBackToCBO(message, musterRollRequest.getMusterRoll().getMusterRollNumber(), amount);
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(contactMobileNumber).message(customizedMessage).build();

            producer.push(config.getSmsNotificationTopic(), smsRequest);
        }
    }

    /**
     * Gets the message from localization
     * @param musterRollRequest
     * @param msgCode
     * @return
     */
    public String getMessage(MusterRollRequest musterRollRequest, String msgCode){
        String rootTenantId = musterRollRequest.getMusterRoll().getTenantId().split("\\.")[0];
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        String locale = requestInfo.getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = localizationUtil.getLocalisedMessages(requestInfo, rootTenantId,
                locale, MUSTER_ROLL_MODULE_CODE);
        return localizedMessageMap.get(locale + "|" + rootTenantId).get(msgCode);
    }

    public String buildMessageForSendBackToCBO(String message, String musterRollName, String amount){
        message = message.replace("{musterrollID}", musterRollName)
                .replace("{amount}", amount);
        return message;
    }

}
