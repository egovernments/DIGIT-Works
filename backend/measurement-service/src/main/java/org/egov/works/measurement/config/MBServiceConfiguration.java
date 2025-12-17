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
public class MBServiceConfiguration {

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

    @Value("${state.level.tenant.id}")
    private String stateLevelTenantId;

    // MDMS V2
    @Value("${egov.mdms.v2.host}")
    private String mdmsV2Host;

    @Value("${egov.mdms.v2.search.endpoint}")
    private String mdmsV2EndPoint;

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

    @Value("${egov.measurement.registry.host}")
    public String mbRegistryHost;

    @Value("${egov.measurement.registry.create.path}")
    public String mbRegistryCreate;

    @Value("${egov.measurement.registry.update.path}")
    public String mbRegistryUpdate;

    @Value("${egov.measurement.registry.search.path}")
    public String mbRegistrySearch;

    //HRMS
    @Value("${egov.hrms.host}")
    private String hrmsHost;

    @Value("${egov.hrms.search.endpoint}")
    private String hrmsEndPoint;

    //Works -Project management system Config
    @Value("${works.project.service.host}")
    private String worksProjectServiceHost;

    @Value("${works.project.service.path}")
    private String worksProjectServicePath;
    //localization
    @Value("${egov.localization.host}")
    private String localizationHost;

    @Value("${egov.localization.context.path}")
    private String localizationContextPath;

    @Value("${egov.localization.search.endpoint}")
    private String localizationSearchEndpoint;

    @Value("${egov.localization.statelevel}")
    private Boolean isLocalizationStateLevel;

    @Value("${kafka.topics.notification.sms}")
    private String smsNotifTopic;

    @Value("${notification.sms.enabled}")
    private boolean isSMSEnabled;

    @Value("${kafka.topics.works.notification.sms.name}")
    private String muktaNotificationTopic;

    @Value("${sms.isAdditonalFieldRequired}")
    private boolean isAdditonalFieldRequired;

}
