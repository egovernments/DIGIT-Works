package org.egov.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceServiceConfiguration {

    @Value("${app.timezone}")
    private String timeZone;
    //Idgen Config
    @Value("${egov.idgen.host}")
    private String idGenHost;
    @Value("${egov.idgen.path}")
    private String idGenPath;
    @Value("${egov.idgen.attendance.register.number.name}")
    private String idgenAttendanceRegisterNumberName;
    @Value("${egov.idgen.attendance.register.number.format}")
    private String idgenAttendanceRegisterNumberFormat;
    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;
    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;
    //Topic
    @Value("${attendance-register.kafka.create.topic}")
    private String saveAttendanceRegisterTopic;
    @Value("${attendance-register.kafka.update.topic}")
    private String updateAttendanceRegisterTopic;


    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;
    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;

    //Topic
    @Value("${staff.kafka.create.topic}")
    private String saveStaffTopic;
    @Value("${staff.kafka.update.topic}")
    private String updateStaffTopic;


    //search config
    @Value("${staff.default.offset}")
    private Integer defaultOffset;
    @Value("${staff.default.limit}")
    private Integer defaultLimit;
    @Value("${staff.search.max.limit}")
    private Integer maxLimit;

    @PostConstruct
    public void initialize() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }
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


