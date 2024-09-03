package org.egov.kafka;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.egov.common.models.project.ProjectRequest;
import org.egov.service.AttendanceRegisterService;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AttendanceRegisterConsumer {
    private final AttendanceRegisterService attendanceRegisterService;

    private final ObjectMapper objectMapper;

    @Autowired
    public AttendanceRegisterConsumer(AttendanceRegisterService attendanceRegisterService, ObjectMapper objectMapper) {
        this.attendanceRegisterService = attendanceRegisterService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${project.management.system.kafka.update.topic}")
    public void projectUpdate(Map<String, Object> consumerRecord,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            log.info("Attendance Register Consumer Started for project update.");
            ProjectRequest projectRequest = objectMapper.convertValue(consumerRecord, ProjectRequest.class);
            attendanceRegisterService.updateAttendanceRegister(RequestInfoWrapper.builder().requestInfo(projectRequest.getRequestInfo()).build(), projectRequest.getProjects());
        } catch (Exception exception) {
            log.error("Error in Attendance Register consumer update", exception);
            log.error("Exception trace: ", ExceptionUtils.getStackTrace(exception));
            throw new CustomException("HCM_ATTENDANCE_REGISTER_UPDATE", exception.getMessage());
        }
    }
}
