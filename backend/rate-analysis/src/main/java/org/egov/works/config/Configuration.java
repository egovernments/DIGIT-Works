package org.egov.works.config;

import lombok.*;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Configuration {


    //MDMS
    @Value("${egov.mdms.v2.host}")
    private String mdmsHost;

    @Value("${egov.mdms.v2.search.endpoint}")
    private String mdmsEndPoint;

    @Value("${egov.mdms.v2.host}")
    private String mdmsV2Host;

    @Value("${egov.mdms.v2.search.endpoint}")
    private String mdmsV2EndPoint;


    //SMSNotification
    @Value("${egov.sms.notification.topic}")
    private String smsNotificationTopic;

    // Works Sor Type
    @Value("${works.sor.type}")
    private String worksSorType;

    // Kafka Topics
    @Value("${rate.analysis.job.create.topic}")
    private String rateAnalysisJobCreateTopic;

    @Value("${rate.analysis.job.update.topic}")
    private String rateAnalysisJobUpdateTopic;

    @Value("${rate.analysis.default.offset}")
    private Integer defaultOffset;

    @Value("${rate.analysis.default.limit}")
    private Integer defaultLimit;
}
