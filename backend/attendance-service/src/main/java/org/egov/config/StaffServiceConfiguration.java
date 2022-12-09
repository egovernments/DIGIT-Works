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
public class StaffServiceConfiguration {

    @Value("${app.timezone}")
    private String timeZone;


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

}

