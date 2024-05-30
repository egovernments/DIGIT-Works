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


    //SMSNotification
    @Value("${egov.sms.notification.topic}")
    private String smsNotificationTopic;
}
