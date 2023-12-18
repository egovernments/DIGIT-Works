package org.egov.digit.expense.calculator.service;

import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.kafka.ExpenseCalculatorProducer;
import org.egov.digit.expense.calculator.util.HRMSUtils;
import org.egov.digit.expense.calculator.util.LocalizationUtil;
import org.egov.digit.expense.calculator.util.NotificationUtil;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.Calculation;
import org.egov.digit.expense.calculator.web.models.Criteria;
import org.egov.digit.expense.calculator.web.models.PurchaseBillRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            if (action.equalsIgnoreCase("APPROVE")) {
                Map<String, String> cbODetails = notificationUtil.getCBOContactPersonDetails(purchaseBillRequest.getRequestInfo(),
                        purchaseBillRequest.getBill().getTenantId(), purchaseBillRequest.getBill().getContractNumber());
                message = getMessage(purchaseBillRequest.getRequestInfo(), purchaseBillRequest.getBill().getTenantId(), "PURCHASE_BILL_APPROVE_TO_VENDOR");
                contactMobileNumber = cbODetails.get(CONTACT_MOBILE_NUMBER);

            } else if (action.equalsIgnoreCase("REJECT")) {

                Map<String , String> employeeDetails=hrmsUtils.getEmployeeDetailsByUuid(purchaseBillRequest.getRequestInfo(),purchaseBillRequest.getBill()
                        .getTenantId(),purchaseBillRequest.getBill().getAuditDetails().getCreatedBy());
                contactMobileNumber = employeeDetails.get("mobileNumber");
                message = getMessage(purchaseBillRequest.getRequestInfo(), purchaseBillRequest.getBill().getTenantId(), "PURCHASE_BILL_REJECT_TO_CREATOR");

            }
            String customizedMessage = buildMessageReplaceVariables(message, billNumber, amount);
            SMSRequest smsRequest = SMSRequest.builder().mobileNumber(contactMobileNumber).message(customizedMessage).build();
            producer.push(config.getSmsNotificationTopic(), smsRequest);
        }
    }

    public void sendNotificationForSupervisionBill(RequestInfo requestInfo, Criteria criteria, Calculation calculation, List<Bill> bills){
        Map<String, String> cboDetails = notificationUtil.getCBOContactPersonDetails(requestInfo, criteria.getTenantId(), criteria.getContractId());
        String amount = String.valueOf(calculation.getTotalAmount());
        String billNumber = bills.get(0).getBillNumber();
        String message = getMessage(requestInfo, criteria.getTenantId(), "SUPERVISION_BILL_APPROVE_ON_CREATE_TO_CBO");
        String contactMobileNumber = cboDetails.get(CONTACT_MOBILE_NUMBER);
        String customizedMessage = buildMessageReplaceVariables(message, billNumber, amount);
        SMSRequest smsRequest = SMSRequest.builder().mobileNumber(contactMobileNumber).message(customizedMessage).build();
        producer.push(config.getSmsNotificationTopic(), smsRequest);
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
