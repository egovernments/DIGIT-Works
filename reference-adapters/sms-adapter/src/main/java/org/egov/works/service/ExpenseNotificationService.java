package org.egov.works.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;

import org.egov.works.config.Constants;
import org.egov.works.config.NotificationServiceConfiguration;
import org.egov.works.kafka.Producer;
import org.egov.works.models.*;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.egov.works.config.Constants.*;

@Service
@Slf4j
public class ExpenseNotificationService {

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


    public void process(final String record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){

        try {
            BillRequest billRequest= mapper.readValue(record,BillRequest.class);
            if (billRequest.getBill().getBusinessService().equalsIgnoreCase("EXPENSE.SUPERVISION")){
                sendNotificationForSupervisionBill(billRequest);
            }else{
                sendNotificationForPurchaseBill(billRequest);
            }
        } catch (Exception e) {
            log.error("Exception while sending notification: ", e);
        }

    }

    public void sendNotificationForPurchaseBill(BillRequest billRequest){

        String action = billRequest.getWorkflow().getAction();
        if(action.equalsIgnoreCase(APPROVE_ACTION) || action.equalsIgnoreCase(REJECT_ACTION)) {
            String amount = String.valueOf(billRequest.getBill().getTotalAmount());
            String billNumber = billRequest.getBill().getBillNumber();
            String message = null;
            String contactMobileNumber = null;
            Map<String,Object> addtionalFields= new HashMap<>();
            if (action.equalsIgnoreCase(APPROVE_ACTION)) {
                Map<String, String> cboDetails = notificationUtil.getVendorContactPersonDetails(billRequest.getRequestInfo(),
                        billRequest.getBill().getTenantId(),
                        billRequest.getBill().getBillDetails().get(0).getPayee().getIdentifier());
                message = getMessage(billRequest.getRequestInfo(), billRequest.getBill().getTenantId(), PURCHASE_BILL_APPROVE_TO_VENDOR_LOCALIZATION_CODE,addtionalFields);
                contactMobileNumber = cboDetails.get(CONTACT_MOBILE_NUMBER);

            } else if (action.equalsIgnoreCase(REJECT_ACTION)) {

                Map<String , String> employeeDetails=hrmsUtils.getEmployeeDetailsByUuid(billRequest.getRequestInfo(),billRequest.getBill()
                        .getTenantId(),billRequest.getBill().getAuditDetails().getCreatedBy());
                contactMobileNumber = employeeDetails.get(MOBILE_NUMBER);
                message = getMessage(billRequest.getRequestInfo(), billRequest.getBill().getTenantId(), PURCHASE_BILL_REJECT_TO_CREATOR_LOCALIZATION_CODE,addtionalFields);

            }
            String customizedMessage = buildMessageReplaceVariables(message, billNumber, amount);
            checkAdditionalFieldAndPushONSmsTopic(customizedMessage,addtionalFields,contactMobileNumber);
        }
    }

    public void sendNotificationForSupervisionBill(BillRequest billRequest){
        Map<String, String> cboDetails = notificationUtil.getCBOContactPersonDetails(billRequest);
        String amount = String.valueOf(billRequest.getBill().getTotalAmount());
        String billNumber = billRequest.getBill().getBillNumber();
        Map<String,Object> addtionalFields= new HashMap<>();
        String message = getMessage(billRequest.getRequestInfo(), billRequest.getBill().getTenantId(), SUPERVISION_BILL_APPROVE_ON_CREATE_TO_CBO_LOCALIZATION_CODE,addtionalFields);
        String contactMobileNumber = cboDetails.get(CONTACT_MOBILE_NUMBER);
        String customizedMessage = buildMessageReplaceVariables(message, billNumber, amount);
        checkAdditionalFieldAndPushONSmsTopic(customizedMessage,addtionalFields,contactMobileNumber);
    }

    public String getMessage(RequestInfo requestInfo, String tenantId, String msgCode, Map<String,Object> addtionalFields){
        String locale = "en_IN";
        String rootTenantId = tenantId.split("\\.")[0];
        if(requestInfo.getMsgId().split("\\|").length > 1)
            locale = requestInfo.getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = localizationUtil.getLocalisedMessages(requestInfo, rootTenantId,
                locale, RAINMAKER_COMMON_MODULE_CODE, MSGS_JSONPATH,LOCALIZATION_CODES_JSONPATH);
        if(config.isAdditonalFieldRequired()){
            setAdditionalFields(requestInfo,rootTenantId,msgCode,addtionalFields);
        }
        return localizedMessageMap.get(locale + "|" + rootTenantId).get(msgCode);
    }

    public String buildMessageReplaceVariables(String message, String billNumber, String amount){
        message = message.replace("{billnumber}", billNumber)
                .replace("{amount}", amount);
        return message;
    }

    /**
     * Sets additional field if the additonal field required flag is true
     * @param requestInfo
     * @param tenantId
     * @param localizationCode
     * @param addtionalFields
     */
    private void setAdditionalFields(RequestInfo requestInfo,String tenantId, String localizationCode, Map<String,Object> addtionalFields){
        addtionalFields.put("templateCode",localizationCode);
        addtionalFields.put("requestInfo",requestInfo);
        addtionalFields.put("tenantId",tenantId);
    }

    /**
     * Checks if the additonal field is empty or not
     * if !Empty then sends sms request on the existing topic
     * if Empty then send the sms request on the new topic with additional fields
     * @param customizedMessage
     * @param addtionalFields
     * @param mobileNumber
     */
    private void checkAdditionalFieldAndPushONSmsTopic( String customizedMessage , Map<String, Object> addtionalFields,String mobileNumber){
        if(!addtionalFields.isEmpty()){
            WorksSmsRequest smsRequest= WorksSmsRequest.builder().message(customizedMessage).additionalFields(addtionalFields)
                    .mobileNumber(mobileNumber).build();
            log.info("SMS message with additonal Fields:::::" + smsRequest.toString());
            producer.push(config.getMuktaNotificationTopic(), smsRequest);

        }else{
            digit.models.coremodels.SMSRequest smsRequest = SMSRequest.builder().mobileNumber(mobileNumber).message(customizedMessage).build();
            log.info("SMS message without additonalFields:::::" + smsRequest.toString());
            producer.push(config.getSmsNotifTopic(), smsRequest);
        }
    }
}
