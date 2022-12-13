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
    @Value("${att.svr.log.kafka.create.topic}")
    private String createAttendanceLogTopic;

    @Value("${att.svr.log.kafka.update.topic}")
    private String updateAttendanceLogTopic;

    // service integration config
    @Value("${att.svr.individual.svr.integration.required}")
    private String individualServiceIntegrationRequired;

    @Value("${att.svr.staff.svr.integration.required}")
    private String staffServiceIntegrationRequired;

    @Value("${att.svr.document.id.verification.required}")
    private String documentIdVerificationRequired;

    //search config
    @Value("${attendance.default.offset}")
    private Integer defaultOffset;

    @Value("${attendance.default.limit}")
    private Integer defaultLimit;

    @Value("${attendance.search.max.limit}")
    private Integer maxLimit;

}
