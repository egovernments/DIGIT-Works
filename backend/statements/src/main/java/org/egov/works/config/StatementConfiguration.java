package org.egov.works.config;


import lombok.*;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
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


//    //HRMS
//    @Value("${egov.hrms.host}")
//    private String hrmsHost;
//
//    @Value("${egov.hrms.search.endpoint}")
//    private String hrmsEndPoint;

//
//    //URLShortening
//    @Value("${egov.url.shortner.host}")
//    private String urlShortnerHost;
//
//    @Value("${egov.url.shortner.endpoint}")
//    private String urlShortnerEndpoint;


    @Value("${state.level.tenant.id}")
    private String stateLevelTenantId;

    //Estimate service
    @Value("${works.estimate.host}")
    private String estimateHost;

    @Value("${works.estimate.search.endpoint}")
    private String estimateSearchEndpoint;

    //Rate Analysis Calculate
    @Value("${works.rate-analysis.host}")
    private String rateAnalysisHost;
    @Value("${works.rate-analysis.calculate.endpoint}")
    private String rateAnalysisCalculateEndpoint;

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
}
