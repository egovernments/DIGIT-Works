package org.egov.works.measurement.config;

import lombok.*;
import org.egov.tracer.config.TracerConfiguration;
import org.egov.works.measurement.web.models.Pagination;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;


@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MBRegistryConfiguration {

    @Bean
    public Pagination pagination() {
        return new Pagination(); // Initialize Pagination bean as needed
    }

    @Value("${mb.default.offset}")
    private Integer defaultOffset;

    @Value("${mb.default.limit}")
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

    // MDMS V2
    @Value("${egov.mdms.V2.host}")
    private String mdmsV2Host;

    @Value("${egov.mdms.search.V2.endpoint}")
    private String mdmsV2EndPoint;

    @Value("${state.level.tenant.id}")
    private String stateLevelTenantId;

    // Kafka topics
    @Value("${measurement.kafka.create.topic}")
    private String createMeasurementTopic;

    @Value("${measurement.kafka.update.topic}")
    private String updateTopic;

    @Value("${measurement.kafka.enrich.create.topic}")
    private String enrichMeasurementTopic;

    @Value("${measurement.idgen.name}")
    private String idName;

    @Value("${measurement.idgen.format}")
    private String idFormat;

    @Value("${egov.workflow.bussinessServiceCode}")
    private String bussinessServiceCode;

    @Value("${egov.workflow.moduleName}")
    private String wfModuleName;

    // filestore
    @Value("${egov.filestore.host}")
    private String baseFilestoreUrl;

    @Value("${egov.filestore.endpoint}")
    private String baseFilestoreEndpoint;

}
