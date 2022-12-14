package org.egov.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor

public class AttendanceServiceConfiguration {

    // kafka topics
    @Value("${attendance.service.log.kafka.create.topic}")
    private String createAttendanceLogTopic;

    @Value("${attendance.service.log.kafka.update.topic}")
    private String updateAttendanceLogTopic;

    // service integration config
    @Value("${attendance.service.individual.service.integration.required}")
    private String individualServiceIntegrationRequired;

    @Value("${attendance.service.staff.service.integration.required}")
    private String staffServiceIntegrationRequired;

    @Value("${attendance.service.document.id.verification.required}")
    private String documentIdVerificationRequired;

    //search config
    @Value("${attendance.service.log.default.offset}")
    private Integer attendanceLogDefaultOffset;

    @Value("${attendance.service.log.default.limit}")
    private Integer attendanceLogDefaultLimit;

    @Value("${attendance.service.log.search.max.limit}")
    private Integer attendanceLogMaxLimit;

}
