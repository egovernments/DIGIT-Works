package org.egov.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.TimeZone;

@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor
public class EstimateServiceConfiguration {

    @Value("${app.timezone}")
    private String timeZone;

    @Value("${is.caching.enabled}")
    private Boolean isCachingEnabled;

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

    //MDMS V2
    @Value("${egov.mdms.host.v2}")
    private String mdmsHostV2;
    @Value("${egov.mdms.search.endpoint.v2}")
    private String mdmsEndPointV2;

    //Schema Code
    @Value("${estimate.sorSearch.schemacode}")
    private String sorSearchSchemaCode;

    @Value("${estimate.rateSearch.schemacode}")
    private  String rateSearchSchenaCode;

    @Value("${estimate.sorSearch.moduleName}")
    private String sorSearchModuleName;
    //Topic
    @Value("${estimate.kafka.create.topic}")
    private String saveEstimateTopic;
    @Value("${estimate.kafka.update.topic}")
    private String updateEstimateTopic;

    @Value("${estimate.kafka.enrich.topic}")
    private String enrichEstimateTopic;

    //id format
    @Value("${egov.idgen.estimate.number.name}")
    private String idgenEstimateNumberName;
    @Value("${egov.idgen.estimate.number.format}")
    private String idgenEstimateNumberFormat;
    @Value("${egov.idgen.revisionEstimate.number.name}")
    private String idgenRevisionEstimateNumberName;
    @Value("${egov.idgen.revisionEstimate.number.format}")
    private String idgenRevisionEstimateNumberFormat;

    //search config
    @Value("${estimate.default.offset}")
    private Integer defaultOffset;
    @Value("${estimate.default.limit}")
    private Integer defaultLimit;
    @Value("${estimate.search.max.limit}")
    private Integer maxLimit;
    //Workflow Config
    @Value("${egov.workflow.host}")
    private String wfHost;
    @Value("${egov.workflow.transition.path}")
    private String wfTransitionPath;
    @Value("${egov.workflow.businessservice.search.path}")
    private String wfBusinessServiceSearchPath;
    @Value("${egov.workflow.processinstance.search.path}")
    private String wfProcessInstanceSearchPath;
    @Value("${estimate.workflow.business.service}")
    private String estimateWFBusinessService;
    @Value("${estimate.workflow.module.name}")
    private String estimateWFModuleName;

    //HRMS
    @Value("${egov.hrms.host}")
    private String hrmsHost;

    @Value("${egov.hrms.search.endpoint}")
    private String hrmsEndPoint;


    //Works -Project management system Config
    @Value("${works.project.service.host}")
    private String worksProjectManagementSystemHost;

    @Value("${works.project.service.path}")
    private String worksProjectManagementSystemPath;

    //Location Service
    @Value("${egov.location.host}")
    private String locationHost;

    @Value("${egov.location.context.path}")
    private String locationContextPath;

    @Value("${egov.location.endpoint}")
    private String locationEndpoint;

    // Contracts
    @Value("${egov.contract.host}")
    private String contractHost;

    @Value("${egov.contract.search.endpoint}")
    private String contractSearchEndpoint;

    //Measurement Book
    @Value("${egov.measurementService.host}")
    private String measurementBookHost;
    @Value("${egov.measurementService.search.endpoint}")
    private String measurementBookSearchEndpoint;

    //Notification
    @Value("${notification.sms.enabled}")
    private Boolean isSMSEnabled;

    @Value("${kafka.topics.notification.sms}")
    private String smsNotifTopic;

    @Value("${kafka.topics.works.notification.sms.name}")
    private String muktaNotificationTopic;
    //Localization
    @Value("${egov.localization.host}")
    private String localizationHost;

    @Value("${egov.localization.context.path}")
    private String localizationContextPath;

    @Value("${egov.localization.search.endpoint}")
    private String localizationSearchEndpoint;

    @Value("${egov.localization.statelevel}")
    private Boolean isLocalizationStateLevel;

    // RevisionEstimate
    @Value("${estimate.revisionEstimate.buisnessService}")
    private String revisionEstimateBusinessService;

    @Value("${estimate.revisionEstimate.measurementValidation}")
    private  Boolean revisionEstimateMeasurementValidation;

    @Value("${estimate.revisionEstimate.maxLimit}")
    private BigDecimal revisionEstimateMaxLimit;

    @Value("${sms.isAdditonalFieldRequired}")
    private boolean isAdditonalFieldRequired;


    @PostConstruct
    public void initialize() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

}

