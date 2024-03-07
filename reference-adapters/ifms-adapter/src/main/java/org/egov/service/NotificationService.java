package org.egov.service;


import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.producer.NotificationProducer;
import org.egov.utils.NotificationUtil;
import org.egov.web.models.WorksSmsRequest;
import org.egov.web.models.enums.BeneficiaryPaymentStatus;
import org.egov.web.models.jit.Beneficiary;
import org.egov.web.models.jit.PaymentInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static org.egov.config.Constants.*;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private NotificationUtil notificationUtil;

    @Autowired
    private IfmsAdapterConfig ifmsAdapterConfig;

    @Autowired
    private NotificationProducer notificationProducer;

    public void sendSmsNotification(RequestInfo requestInfo, PaymentInstruction paymentInstruction){

//        Getting details of each beneficiary and sending SMS
        for(Beneficiary beneficiary : paymentInstruction.getBeneficiaryDetails()){
            String utrNo = beneficiary.getUtrNo();
            String amount = beneficiary.getBenfAmount();
            String mobileNumber = beneficiary.getBenfMobileNo();
            String paymentStatus = beneficiary.getPaymentStatus().toString();
            String tenantId = paymentInstruction.getTenantId();
            sendNotification(requestInfo, utrNo, amount, mobileNumber, paymentStatus, tenantId);
        }

    }

    public void sendNotification(RequestInfo requestInfo, String utrNo, String amount, String mobileNumber, String paymentStatus, String tenantId){

        String localizationCode = null;

//        Set localization code based on the payment status
        if(paymentStatus.equals(BeneficiaryPaymentStatus.SUCCESS)){
            localizationCode = JIT_PAYMENT_SUCCESSFUL_LOCALIZATION_CODE;
        }else if(paymentStatus.equals(BeneficiaryPaymentStatus.FAILED)){
            localizationCode = JIT_PAYMENT_FAILED_LOCALIZATION_CODE;
        }

//        Get message from Localization
        String message = getMessage(requestInfo, localizationCode, tenantId);

        if (StringUtils.isEmpty(message)) {
            log.info("message not configured for this case");
            return;
        }

        message = getCustomMessage(message, utrNo, amount);


        Map<String, Object> additionalField=new HashMap<>();

//        Set additional field if required
        if(ifmsAdapterConfig.isAdditonalFieldRequired()){
            setAdditionalFields(requestInfo,localizationCode, additionalField);
        }
        Map<String, String> smsDetails = new HashMap<>();
        smsDetails.put("mobileNumber", mobileNumber);

//      Create Sms request and push to the kafka topic based on additional field
        checkAdditionalFieldAndPushONSmsTopic(message,additionalField,smsDetails);


    }

    private void setAdditionalFields(RequestInfo requestInfo, String localizationCode,Map<String, Object> additionalField ){
        additionalField.put("templateCode",localizationCode);
        additionalField.put("requestInfo",requestInfo);
        additionalField.put("tenantId",requestInfo);

    }

    private void checkAdditionalFieldAndPushONSmsTopic( String customizedMessage , Map<String, Object> additionalField,Map<String,String> smsDetails){

        if(!additionalField.isEmpty()){
            WorksSmsRequest smsRequest=WorksSmsRequest.builder().message(customizedMessage).additionalFields(additionalField)
                    .mobileNumber(smsDetails.get("mobileNumber")).build();
            log.info("SMS message with additonal fields:::::" + smsRequest.toString());
            notificationProducer.push(ifmsAdapterConfig.getMuktaNotificationTopic(), smsRequest);

        }else{
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(smsDetails.get("mobileNumber")).message(customizedMessage).build();
            log.info("SMS message without Additonal Fields:::::" + smsRequest.toString());
            notificationProducer.push(ifmsAdapterConfig.getSmsNotifTopic(), smsRequest);
        }
    }


    private String getCustomMessage(String message, String utrNo, String amount) {
        message = message.replace("{amount}", amount).replace("{UTRNO}", utrNo);
        return message;
    }


    public String getMessage(RequestInfo requestInfo, String msgCode, String tenantId) {
        String rootTenantId = tenantId.split("\\.")[0];
        String locale = "en_IN";
        if(requestInfo.getMsgId().split("\\|").length > 1)
            locale = requestInfo.getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = notificationUtil.getLocalisedMessages(requestInfo, rootTenantId,
                locale, JIT_LOCALIZATION_MODULE_CODE);
        return localizedMessageMap.get(locale + "|" + rootTenantId).get(msgCode);
    }

}