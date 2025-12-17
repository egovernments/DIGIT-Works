package org.egov.digit.expense.calculator.service;

import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.kafka.ExpenseCalculatorProducer;
import org.egov.digit.expense.calculator.util.HRMSUtils;
import org.egov.digit.expense.calculator.util.LocalizationUtil;
import org.egov.digit.expense.calculator.util.NotificationUtil;
import org.egov.digit.expense.calculator.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class NotificationService {
    private final ExpenseCalculatorProducer producer;
    private final NotificationUtil notificationUtil;
    private final LocalizationUtil localizationUtil;
    private final ExpenseCalculatorConfiguration config;
    private final HRMSUtils hrmsUtils;

    public static final String CONTACT_MOBILE_NUMBER = "contactMobileNumber";
    public static final String EXPENSE_CALCULATOR_MODULE_CODE = "rainmaker-common-masters";

    @Autowired
    public NotificationService(ExpenseCalculatorProducer producer, NotificationUtil notificationUtil, LocalizationUtil localizationUtil, ExpenseCalculatorConfiguration config, HRMSUtils hrmsUtils) {
        this.producer = producer;
        this.notificationUtil = notificationUtil;
        this.localizationUtil = localizationUtil;
        this.config = config;
        this.hrmsUtils = hrmsUtils;
    }

    public void sendNotificationForPurchaseBill(PurchaseBillRequest purchaseBillRequest){

        String action = purchaseBillRequest.getWorkflow().getAction();
        if(action.equalsIgnoreCase("APPROVE") || action.equalsIgnoreCase("REJECT")) {
            String amount = String.valueOf(purchaseBillRequest.getBill().getTotalAmount());
            String billNumber = purchaseBillRequest.getBill().getBillNumber();
            String message = null;
            String contactMobileNumber = null;
            Map<String,Object> addtionalFields= new HashMap<>();
            if (action.equalsIgnoreCase("APPROVE")) {
                Map<String, String> CBODetails = notificationUtil.getCBOContactPersonDetails(purchaseBillRequest.getRequestInfo(),
                        purchaseBillRequest.getBill().getTenantId(), purchaseBillRequest.getBill().getContractNumber());
                message = getMessage(purchaseBillRequest.getRequestInfo(), purchaseBillRequest.getBill().getTenantId(), "PURCHASE_BILL_APPROVE_TO_VENDOR",addtionalFields);
                contactMobileNumber = CBODetails.get(CONTACT_MOBILE_NUMBER);

            } else if (action.equalsIgnoreCase("REJECT")) {

                Map<String , String> employeeDetails=hrmsUtils.getEmployeeDetailsByUuid(purchaseBillRequest.getRequestInfo(),purchaseBillRequest.getBill()
                        .getTenantId(),purchaseBillRequest.getBill().getAuditDetails().getCreatedBy());
                contactMobileNumber = employeeDetails.get("mobileNumber");
                message = getMessage(purchaseBillRequest.getRequestInfo(), purchaseBillRequest.getBill().getTenantId(), "PURCHASE_BILL_REJECT_TO_CREATOR",addtionalFields);

            }
            String customizedMessage = buildMessageReplaceVariables(message, billNumber, amount);
            checkAdditionalFieldAndPushONSmsTopic(customizedMessage,addtionalFields,contactMobileNumber);
        }
    }

    public void sendNotificationForSupervisionBill(RequestInfo requestInfo, Criteria criteria, Calculation calculation, List<Bill> bills){
        Map<String, String> cboDetails = notificationUtil.getCBOContactPersonDetails(requestInfo, criteria.getTenantId(), criteria.getContractId());
        String amount = String.valueOf(calculation.getTotalAmount());
        String billNumber = bills.get(0).getBillNumber();
        Map<String,Object> addtionalFields= new HashMap<>();
        String message = getMessage(requestInfo, criteria.getTenantId(), "SUPERVISION_BILL_APPROVE_ON_CREATE_TO_CBO",addtionalFields);
        String contactMobileNumber = cboDetails.get(CONTACT_MOBILE_NUMBER);
        String customizedMessage = buildMessageReplaceVariables(message, billNumber, amount);
        checkAdditionalFieldAndPushONSmsTopic(customizedMessage,addtionalFields,contactMobileNumber);
    }

    public String getMessage(RequestInfo requestInfo, String tenantId, String msgCode, Map<String,Object> addtionalFields){
        String rootTenantId = tenantId.split("\\.")[0];
        String locale = "en_IN";
        if(requestInfo.getMsgId().split("\\|").length > 1)
            locale = requestInfo.getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = localizationUtil.getLocalisedMessages(requestInfo, rootTenantId,
                locale, EXPENSE_CALCULATOR_MODULE_CODE);
        if(config.isAdditonalFieldRequired()){
            setAdditionalFields(requestInfo,tenantId,msgCode,addtionalFields);
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
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(mobileNumber).message(customizedMessage).build();
            log.info("SMS message without additonalFields:::::" + smsRequest.toString());
            producer.push(config.getSmsNotificationTopic(), smsRequest);
        }
    }

}
