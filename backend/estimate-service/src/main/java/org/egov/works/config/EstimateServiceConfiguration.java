package org.egov.works.config;

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
public class EstimateServiceConfiguration {

    @Value("${app.timezone}")
    private String timeZone;

    @PostConstruct
    public void initialize() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

    //Idgen Config
    @Value("${egov.idgen.host}")
    private String idGenHost;

    @Value("${egov.idgen.path}")
    private String idGenPath;

    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;

    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;

    //Topic
    @Value("${estimate.kafka.create.topic}")
    private String saveEstimateTopic;

    @Value("${estimate.kafka.update.topic}")
    private String updateEstimateTopic;


    //id format
    @Value("${egov.idgen.estimate.number.name}")
    private String idgenEstimateNumberName;

    @Value("${egov.idgen.estimate.number.format}")
    private String idgenEstimateNumberFormat;

    @Value("${egov.idgen.estimate.detail.number.name}")
    private String idgenSubEstimateNumberName;

    @Value("${egov.idgen.estimate.detail.number.format}")
    private String idgenSubEstimateNumberFormat;
}

