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
    //Workflow Config
    @Value("${egov.workflow.host}")
    private String wfHost;
    @Value("${loi.workflow.business.service}")
    private String loiWFBusinessService;
    @Value("${egov.workflow.transition.path}")
    private String wfTransitionPath;
    @Value("${loi.workflow.module.name}")
    private String loiWFModuleName;

    //Search Configs
    @Value("${loi.default.limit}")
    private Integer defaultLimit;
    @Value("${loi.default.offset}")
    private Integer defaultOffset;
    @Value("${loi.search.max.limit}")
    private Integer maxLimit;

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

    @Value("${egov.workflow.host}")
    private String workflowHost;
    @Value("${egov.workflow.transition.path}")
    private String workflowTransitionPath;
    @Value("${egov.workflow.processinstance.search.path}")
    private String wfProcessInstanceSearchPath;
    @Value("${egov.workflow.businessservice.search.path}")
    private String wfBusinessServiceSearchPath;

    @Value("${workflow.loi.business.service.name}")
    private String workflowLOIBusinessServiceName;
    @Value("${workflow.loi.module.name}")
    private String workflowLOIModuleName;


}
