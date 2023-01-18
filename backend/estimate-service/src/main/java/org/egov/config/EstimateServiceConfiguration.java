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
public class EstimateServiceConfiguration {

    @Value("${app.timezone}")
    private String timeZone;
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


    //Works -Project management system Config
    @Value("${works.project.service.host}")
    private String worksProjectManagementSystemHost;

    @Value("${works.project.service.path}")
    private String worksProjectManagementSystemPath;

    @PostConstruct
    public void initialize() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

}

