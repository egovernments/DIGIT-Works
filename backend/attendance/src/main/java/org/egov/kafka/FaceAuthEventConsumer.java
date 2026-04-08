package org.egov.kafka;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.egov.service.FaceAuthEventService;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.FaceAuthEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FaceAuthEventConsumer {

    private final FaceAuthEventService faceAuthEventService;
    private final ObjectMapper objectMapper;

    @Autowired
    public FaceAuthEventConsumer(FaceAuthEventService faceAuthEventService, ObjectMapper objectMapper) {
        this.faceAuthEventService = faceAuthEventService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${attendance.face.auth.kafka.consumer.bulk.create.topic}")
    public void bulkCreate(Map<String, Object> consumerRecord,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            FaceAuthEventRequest request = objectMapper.convertValue(consumerRecord, FaceAuthEventRequest.class);
            faceAuthEventService.createFaceAuthEvents(request);
        } catch (Exception exception) {
            log.error("Error in Face Auth Event consumer bulk create", exception);
            log.error("Exception trace: ", ExceptionUtils.getStackTrace(exception));
            throw new CustomException("HCM_FACE_AUTH_EVENT_CREATE", exception.getMessage());
        }
    }
}
