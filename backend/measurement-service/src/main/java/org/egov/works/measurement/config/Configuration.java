package org.egov.works.measurement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.egov.tracer.config.TracerConfiguration;
import org.egov.works.measurement.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Configuration {

    @Bean
    public Pagination pagination() {
        return new Pagination(); // Initialize Pagination bean as needed
    }



    @Value("${measurement-service.default.offset}")
    private Integer defaultOffset;

    @Value("${measurement-service.default.limit}")
    private Integer defaultLimit;

    //Idgen Config
    @Value("${egov.idgen.host}")
    private String idGenHost;

    @Value("${egov.idgen.path}")
    private String idGenPath;


    //Workflow Config
    @Value("${egov.workflow.host}")
    private String wfHost;

    @Value("${egov.workflow.transition.path}")
    private String wfTransitionPath;

    @Value("${egov.workflow.businessservice.search.path}")
    private String wfBusinessServiceSearchPath;

    @Value("${egov.workflow.processinstance.search.path}")
    private String wfProcessInstanceSearchPath;


    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;

    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;

    // Kafka topics
    @Value("${measurement.kafka.create.topic}")
    private String createMeasurementTopic;

    @Value("${measurement.kafka.update.topic}")
    private String updateTopic;

    @Value("${measurement-service.kafka.update.topic}")
    private String serviceUpdateTopic;

    @Value("${measurement-service.kafka.create.topic}")
    private String measurementServiceCreateTopic;

    @Value("${measurement.kafka.enrich.create.topic}")
    private String enrichMeasurementTopic;

    @Value("${measurement.idgen.name}")
    private String idName;

    @Value("${measurement.idgen.format}")
    private String idFormat;

    // contract service
    @Value("${egov.contract.host}")
    private String contractHost;

    @Value("${egov.contract.path}")
    private String contractPath;

    // estimate service
    @Value("${egov.estimate.host}")
    private String estimateHost;

    @Value("${egov.estimate.path}")
    private String estimatePath;

    @Value("${egov.workflow.bussinessServiceCode}")
    private String bussinessServiceCode;

    @Value("${egov.workflow.moduleName}")
    private String wfModuleName;
}
