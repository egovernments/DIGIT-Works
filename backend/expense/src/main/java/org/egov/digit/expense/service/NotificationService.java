package org.egov.digit.expense.service;

import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.util.HRMSUtils;
import org.egov.digit.expense.util.LocalizationUtil;
import org.egov.digit.expense.util.NotificationUtil;
import org.egov.digit.expense.web.models.BillRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        String action = billRequest.getWorkflow().getAction();
        if(action.equalsIgnoreCase(APPROVE_CODE) || action.equalsIgnoreCase(REJECT_CODE)) {
            String amount = String.valueOf(billRequest.getBill().getTotalAmount());
            String billNumber = billRequest.getBill().getBillNumber();
            String message = null;
            String contactMobileNumber = null;
            if (action.equalsIgnoreCase(APPROVE_CODE)) {
                Map<String, String> cboDetails = notificationUtil.getVendorContactPersonDetails(billRequest.getRequestInfo(),
                        billRequest.getBill().getTenantId(),
                        billRequest.getBill().getBillDetails().get(0).getPayee().getIdentifier());
                message = getMessage(billRequest.getRequestInfo(), billRequest.getBill().getTenantId(), PURCHASE_BILL_APPROVE_TO_VENDOR_LOCALIZATION_CODE);
                contactMobileNumber = cboDetails.get(CONTACT_MOBILE_NUMBER);

            } else if (action.equalsIgnoreCase(REJECT_CODE)) {

                Map<String , String> employeeDetails=hrmsUtils.getEmployeeDetailsByUuid(billRequest.getRequestInfo(),billRequest.getBill()
                        .getTenantId(),billRequest.getBill().getAuditDetails().getCreatedBy());
                contactMobileNumber = employeeDetails.get(MOBILE_NUMBER_CODE);
                message = getMessage(billRequest.getRequestInfo(), billRequest.getBill().getTenantId(), PURCHASE_BILL_REJECT_TO_CREATOR_LOCALIZATION_CODE);

            }
            String customizedMessage = buildMessageReplaceVariables(message, billNumber, amount);
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(contactMobileNumber).message(customizedMessage).build();
            expenseProducer.push(config.getSmsNotificationTopic(), smsRequest);
        }
    }

    public void sendNotificationForSupervisionBill(BillRequest billRequest){
        Map<String, String> cboDetails = notificationUtil.getCBOContactPersonDetails(billRequest);
        String amount = String.valueOf(billRequest.getBill().getTotalAmount());
        String billNumber = billRequest.getBill().getBillNumber();
        String message = getMessage(billRequest.getRequestInfo(), billRequest.getBill().getTenantId(), SUPERVISION_BILL_APPROVE_ON_CREATE_TO_CBO_LOCALIZATION_CODE);
        String contactMobileNumber = cboDetails.get(CONTACT_MOBILE_NUMBER);
        String customizedMessage = buildMessageReplaceVariables(message, billNumber, amount);
        SMSRequest smsRequest = SMSRequest.builder().mobileNumber(contactMobileNumber).message(customizedMessage).build();
        expenseProducer.push(config.getSmsNotificationTopic(), smsRequest);
    }

    public String getMessage(RequestInfo requestInfo, String tenantId, String msgCode){
        String locale = "en_IN";
        if(requestInfo.getMsgId().split("\\|").length > 1)
            locale = requestInfo.getMsgId().split("\\|")[1];
        Map<String, Map<String, String>> localizedMessageMap = localizationUtil.getLocalisedMessages(requestInfo, tenantId,
                locale, EXPENSE_CALCULATOR_MODULE_CODE);
        return localizedMessageMap.get(locale + "|" + tenantId).get(msgCode);
    }

    public String buildMessageReplaceVariables(String message, String billNumber, String amount){
        message = message.replace("{billnumber}", billNumber)
                .replace("{amount}", amount);
        return message;
    }

}
