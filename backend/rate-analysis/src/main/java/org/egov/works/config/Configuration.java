package org.egov.works.config;

import lombok.*;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Configuration {

    //Idgen Config
    @Value("${egov.idgen.host}")
    private String idGenHost;

    @Value("${egov.idgen.path}")
    private String idGenPath;

    @Value("${egov.rate.analysis.job.number.name}")
    private String rateAnalysisJobNumberName;

    //MDMS
    @Value("${egov.mdms.v2.host}")
    private String mdmsHost;

    @Value("${egov.mdms.v1.search.endpoint}")
    private String mdmsV1EndPoint;

    @Value("${egov.mdms.v2.search.endpoint}")
    private String mdmsV2EndPoint;

    @Value("${egov.mdms.v2.create.endpoint}")
    private String createEndPoint;

    @Value("${egov.mdms.v2.update.endpoint}")
    private String updateEndPoint;

    //Labour Cess Configuration
    @Value("${labour.cess.head.code}")
    private String labourCessHeadCode;

    @Value("${labour.cess.rate}")
    private BigDecimal labourCessRate;


    //SMSNotification
    @Value("${egov.sms.notification.topic}")
    private String smsNotificationTopic;

    // Works Sor Type
    @Value("${works.sor.type}")
    private String worksSorType;

    @Value("${works.mdms.data.rates.schema.code}")
    private String ratesSchemaCode;

    @Value("${works.mdms.data.composition.schema.code}")
    private String compositionSchemaCode;

    // Kafka Topics
    @Value("${rate.analysis.job.create.topic}")
    private String rateAnalysisJobCreateTopic;

    @Value("${rate.analysis.job.update.topic}")
    private String rateAnalysisJobUpdateTopic;

    @Value("${rate.analysis.default.offset}")
    private Integer defaultOffset;

    @Value("${rate.analysis.default.limit}")
    private Integer defaultLimit;

    @Value("${sor.default.limit}")
    private Integer sorDefaultLimit;

    @Value("${sor.default.offset}")
    private Integer sorDefaultOffset;

    @Value("${works.is.mdms.consumer.needed}")
    private Boolean isMdmsConsumerNeeded;
}