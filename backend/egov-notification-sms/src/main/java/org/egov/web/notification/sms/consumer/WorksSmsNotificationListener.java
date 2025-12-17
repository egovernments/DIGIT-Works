package org.egov.web.notification.sms.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.kafka.CustomKafkaTemplate;
import org.egov.web.notification.sms.consumer.contract.SMSRequest;
import org.egov.web.notification.sms.consumer.contract.WorksSMSRequest;
import org.egov.web.notification.sms.models.Category;
import org.egov.web.notification.sms.models.RequestContext;
import org.egov.web.notification.sms.service.SMSService;
import org.egov.web.notification.sms.service.impl.CDACSMSServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.UUID;

@Slf4j
@Service
public class WorksSmsNotificationListener {

    private final ApplicationContext context;
    private CDACSMSServiceImpl smsService;
    private CustomKafkaTemplate<String, WorksSMSRequest> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${kafka.topics.expiry.sms}")
    String expiredSmsTopic;

    @Value("${kafka.topics.backup.sms}")
    String backupSmsTopic;

    @Value("${kafka.topics.error.sms}")
    String errorSmsTopic;

    @Value("${sms.enabled}")
    Boolean smsEnable;


    @Autowired
    public WorksSmsNotificationListener(
            ApplicationContext context,
            CDACSMSServiceImpl smsService,
                                   CustomKafkaTemplate<String, WorksSMSRequest> kafkaTemplate) {
        this.smsService = smsService;
        this.context = context;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(
            topics = "${kafka.topics.mukta.notification.sms.name}"
    )
    public void process(HashMap<String, Object> consumerRecord) {
        RequestContext.setId(UUID.randomUUID().toString());
        WorksSMSRequest request = null;
        try {
            if(!smsEnable){
                log.info("Sms service is disable to enable the notification service set the value of sms.enable flag as true");
            }
            else{
                request = objectMapper.convertValue(consumerRecord, WorksSMSRequest.class);
                if (request.getExpiryTime() != null &&request.getCategory()!=null&& request.getCategory() == Category.OTP) {
                    Long expiryTime = request.getExpiryTime();
                    Long currentTime = System.currentTimeMillis();
                    if (expiryTime < currentTime) {
                        log.info("OTP Expired");
                        if (!StringUtils.isEmpty(expiredSmsTopic))
                            kafkaTemplate.send(expiredSmsTopic, request);
                    } else {
                        smsService.sendSMS(request.toDomain());
                    }
                } else {
                    smsService.sendSMS(request.toDomain());
                }
            }

        } catch (RestClientException rx) {
            log.info("Going to backup SMS Service", rx);
            if (!StringUtils.isEmpty(backupSmsTopic))
                kafkaTemplate.send(backupSmsTopic, request);
            else if (!StringUtils.isEmpty(errorSmsTopic)) {
                kafkaTemplate.send(errorSmsTopic, request);
            } else {
                throw rx;
            }
        } catch (Exception ex) {
            log.error("Sms service failed", ex);
            if (!StringUtils.isEmpty(errorSmsTopic)) {
                kafkaTemplate.send(errorSmsTopic, request);
            } else {
                throw ex;
            }
        }

    }

}
