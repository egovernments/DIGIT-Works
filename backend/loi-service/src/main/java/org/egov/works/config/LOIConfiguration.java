package org.egov.works.config;

import lombok.*;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Import({TracerConfiguration.class})
@Component
public class LOIConfiguration {

    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;

    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;

    //Idgen Config
    @Value("${egov.idgen.host}")
    private String idGenHost;

    @Value("${egov.idgen.path}")
    private String idGenPath;

    //Id Format
    @Value("${egov.idgen.loi.number.name}")
    private String idGenLOINumberName;

    @Value("${egov.idgen.loi.number.format}")
    private String idGenLOINumberFormat;

    @Value("${loi.kafka.create.topic}")
    private String loiSaveTopic;

    @Value("${loi.kafka.update.topic}")
    private String loiUpdateTopic;

}