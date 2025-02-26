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
public class StatementConfiguration {


    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;

    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;





    @Value("${state.level.tenant.id}")
    private String stateLevelTenantId;

    //Estimate service
    @Value("${works.estimate.host}")
    private String estimateHost;

    @Value("${works.estimate.search.endpoint}")
    private String estimateSearchEndpoint;

    @Value("${egov.mdms.v2.host}")
    private String mdmsV2Host;

    @Value("${egov.mdms.v2.search.endpoint}")
    private String mdmsV2EndPoint;

    @Value("${egov.mdmsV2.search.endpoint}")
    private String mdmsSearchEndPoint;

    @Value("${works.sor.type}")
    private String worksSorType;

    @Value("${statement.sorComposition.moduleName}")
    private String sorCompositionSearchModuleName;
    @Value("${save.analysis.statement.topic}")
    private String saveAnalysisStatementTopic;
    @Value("${update.analysis.statement.topic}")
    private String updateAnalysisStatementTopic;

    // Measurement Service Host
    @Value("${works.measurement.host}")
    private String measurementHost;

    @Value("${works.measurement.search.endpoint}")
    private String measurementSearchEndpoint;

    //Contract Service Host
    @Value("${works.contract.host}")
    private String contractHost;

    @Value("${works.contract.search.endpoint}")
    private String contractSearchEndpoint;

    //Workflow Service Host
    @Value("${egov.workflow.host}")
    private String wfHost;

    @Value("${egov.workflow.processinstance.search.path}")
    private String wfProcessInstanceSearchPath;

    @Value("${estimate.workflow.business.service}")
    private String estimateWFBusinessService;

    @Value("${estimate.workflow.module.name}")
    private String estimateWFModuleName;

    @Value("${utilization.error.topic}")
    private String utilizationErrorTopic;

    @Value("${estimate.kafka.create.topic}")
    private String estimateCreateTopic;
    @Value("${estimate.kafka.update.topic}")
    private String estimateUpdateTopic;
    @Value("${analysis.statement.error.topic}")
    private String analysisStatementErrorTopic;

}
