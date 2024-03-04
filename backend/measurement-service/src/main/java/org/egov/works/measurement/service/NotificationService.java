package org.egov.works.measurement.service;

import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.measurement.config.MBServiceConfiguration;
import org.egov.works.measurement.config.ServiceConstants;
import org.egov.works.measurement.kafka.MBServiceProducer;
import org.egov.works.measurement.util.NotificationUtil;
import org.egov.works.measurement.web.models.MeasurementServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Map;

import static org.egov.works.measurement.config.ServiceConstants.APPROVE_LOCALISATION_CODE;
import static org.egov.works.measurement.config.ServiceConstants.REJECT_LOCALISATION_CODE;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private MBServiceProducer producer;
    @Autowired
    private MBServiceConfiguration config;
    @Autowired
    private NotificationUtil notificationUtil;

    public void sendNotification(MeasurementServiceRequest request) {
        String localisationCode = null;
        if (request.getMeasurements().get(0).getWorkflow().getAction().equalsIgnoreCase("APPROVE")) {
            localisationCode = APPROVE_LOCALISATION_CODE;
        } else if (request.getMeasurements().get(0).getWorkflow().getAction().equalsIgnoreCase("REJECT")) {
            localisationCode = REJECT_LOCALISATION_CODE;
        } else {
            return;
        }
        String message = getMessage(request, localisationCode);

        if (StringUtils.isEmpty(message)) {
            log.info("message not configured for this case");
            return;
        }
        String projectNumber = notificationUtil.getProjectNumber(request.getRequestInfo(), request.getMeasurements().get(0).getTenantId(),
                request.getMeasurements().get(0).getReferenceId());
        String mobileNumber = notificationUtil.getEmployeeMobileNumber(request.getRequestInfo(), request.getMeasurements().get(0).getTenantId()
        , request.getMeasurements().get(0).getAuditDetails().getCreatedBy());
        message = getCustomMessage(message, request.getMeasurements().get(0).getMeasurementNumber(),projectNumber);
        SMSRequest smsRequest = SMSRequest.builder().message(message).mobileNumber(mobileNumber).build();

        log.info("Sending notification for action in measurement book state");
        producer.push(config.getSmsNotifTopic(), smsRequest);
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
