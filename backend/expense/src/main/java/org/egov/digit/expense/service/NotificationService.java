package org.egov.digit.expense.service;

import org.egov.digit.expense.web.models.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.util.HRMSUtils;
import org.egov.digit.expense.util.LocalizationUtil;
import org.egov.digit.expense.util.NotificationUtil;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.WorksSmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.egov.digit.expense.config.Constants.*;

@Service
@Slf4j
public class NotificationService {
    private final ExpenseProducer expenseProducer;
    private final NotificationUtil notificationUtil;
    private final LocalizationUtil localizationUtil;
    private final Configuration config;
    private final HRMSUtils hrmsUtils;

    @Autowired
    public NotificationService(ExpenseProducer expenseProducer, NotificationUtil notificationUtil, LocalizationUtil localizationUtil, Configuration config, HRMSUtils hrmsUtils) {
        this.expenseProducer = expenseProducer;
        this.notificationUtil = notificationUtil;
        this.localizationUtil = localizationUtil;
        this.config = config;
        this.hrmsUtils = hrmsUtils;
    }


    public void sendNotificationForPurchaseBill(BillRequest billRequest){

        if(config.isSMSEnabled()) {
            log.info("Notification is enabled for this service");

            String action = billRequest.getWorkflow().getAction();
            if (action.equalsIgnoreCase(APPROVE_CODE) || action.equalsIgnoreCase(REJECT_CODE)) {
                String amount = String.valueOf(billRequest.getBill().getTotalAmount());
                String billNumber = billRequest.getBill().getBillNumber();
                String message = null;
                String contactMobileNumber = null;
                Map<String, Object> addtionalFields = new HashMap<>();
                if (action.equalsIgnoreCase(APPROVE_CODE)) {
                    Map<String, String> cboDetails = notificationUtil.getVendorContactPersonDetails(billRequest.getRequestInfo(),
                            billRequest.getBill().getTenantId(),
                            billRequest.getBill().getBillDetails().get(0).getPayee().getIdentifier());
                    message = getMessage(billRequest.getRequestInfo(), billRequest.getBill().getTenantId(), PURCHASE_BILL_APPROVE_TO_VENDOR_LOCALIZATION_CODE, addtionalFields);
                    contactMobileNumber = cboDetails.get(CONTACT_MOBILE_NUMBER);

                } else if (action.equalsIgnoreCase(REJECT_CODE)) {

                    Map<String, String> employeeDetails = hrmsUtils.getEmployeeDetailsByUuid(billRequest.getRequestInfo(), billRequest.getBill()
                            .getTenantId(), billRequest.getBill().getAuditDetails().getCreatedBy());
                    contactMobileNumber = employeeDetails.get(MOBILE_NUMBER_CODE);
                    message = getMessage(billRequest.getRequestInfo(), billRequest.getBill().getTenantId(), PURCHASE_BILL_REJECT_TO_CREATOR_LOCALIZATION_CODE, addtionalFields);

                }
                String customizedMessage = buildMessageReplaceVariables(message, billNumber, amount);
                checkAdditionalFieldAndPushONSmsTopic(customizedMessage, addtionalFields, contactMobileNumber);
            }
        }else{
            log.info("Notification is not enabled for this service");
        }
    }

    public void sendNotificationForSupervisionBill(BillRequest billRequest){

        if(config.isSMSEnabled()) {
            log.info("Notification is enabled for this service");

            Map<String, String> cboDetails = notificationUtil.getCBOContactPersonDetails(billRequest);
            String amount = String.valueOf(billRequest.getBill().getTotalAmount());
            String billNumber = billRequest.getBill().getBillNumber();
            Map<String, Object> addtionalFields = new HashMap<>();
            String message = getMessage(billRequest.getRequestInfo(), billRequest.getBill().getTenantId(), SUPERVISION_BILL_APPROVE_ON_CREATE_TO_CBO_LOCALIZATION_CODE, addtionalFields);
            String contactMobileNumber = cboDetails.get(CONTACT_MOBILE_NUMBER);
            String customizedMessage = buildMessageReplaceVariables(message, billNumber, amount);
            checkAdditionalFieldAndPushONSmsTopic(customizedMessage, addtionalFields, contactMobileNumber);
        }else{
            log.info("Notification is not enabled for this service");
        }
    }

    public String getMessage(RequestInfo requestInfo, String tenantId, String msgCode, Map<String,Object> addtionalFields){
        String locale = "en_IN";
        String rootTenantId = tenantId.split("\\.")[0];
        if(requestInfo.getMsgId().split("\\|").length > 1)
            locale = requestInfo.getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = localizationUtil.getLocalisedMessages(requestInfo, rootTenantId,
                locale, EXPENSE_CALCULATOR_MODULE_CODE);
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
            expenseProducer.push(config.getMuktaNotificationTopic(), smsRequest);

        }else{
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(mobileNumber).message(customizedMessage).build();
            log.info("SMS message without additonalFields:::::" + smsRequest.toString());
            expenseProducer.push(config.getSmsNotificationTopic(), smsRequest);
        }
    }
}
