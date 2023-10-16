package org.egov.works.measurement.config;

import lombok.*;
import org.egov.tracer.config.TracerConfiguration;
import org.egov.works.measurement.web.models.Pagination;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    @Value("${egov.mdms.tenantId}")
    private String mdmsTenantId;

    @Value("${egov.mdms.moduleName}")
    private String mdmsModuleName;

    @Value("${egov.mdms.masterName}")
    private String mdmsMasterName;

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

    public String rejectedStatus = "REJECTED";
    public String approvedStatus = "APPROVED";
    public String draftAction="SAVE_AS_DRAFT";
    public String submitAction="SUBMIT";
    public String editResubmitAction="EDIT/RE-SUBMIT";
    public Set<String> actionSets = new HashSet<>(Arrays.asList(draftAction, submitAction, editResubmitAction));

    // Error messages
    public static final String MEASUREMENT_DATA_NOT_EXIST = "Measurement data does not exist";
    public static final String MEASUREMENT_SERVICE_DATA_NOT_EXIST = "MeasurementRegistry data does not exist";
    public static final String MEASURES_DATA_NOT_EXIST = "Measures data does not exist";
    public static final String CUMULATIVE_ENRICHMENT_ERROR = "Error during Cumulative enrichment";
    public static final String NO_ACTIVE_CONTRACT_ID = "No active contract with the given contract id";
    public static final String DUPLICATE_TARGET_IDS = "Duplicate Target Ids received, it should be unique";
    public static final String INCOMPLETE_MEASURES = "Incomplete Measures, some active line items are missed for the given contract";
    public static final String INVALID_DOCUMENTS = "Document IDs are invalid";
    public static final String NO_VALID_ESTIMATE = "No valid Estimate found";
    public static final String IDS_AND_MB_NUMBER_MISMATCH = "Id and Measurement Number do not match";
    public static final String INVALID_ESTIMATE_ID = "Estimate Ids are invalid";
    public static final String NOT_VALID_REFERENCE_ID = "Measurement data with contract number {0} is already there in progress state.";
    public static final String API_REQUEST_FAILED = "API request failed with status code: {0}";
    public static final String REJECTED_ERROR = "Measurement with measurementNumber {0} is rejected.";
    public static final String API_REQUEST_FAILED_IOEXCEPTION = "API request failed: {0}";
    public static final String TENANT_ID_REQUIRED = "TenantId is required.";
    public static final String INVALID_CONTRACT="Invalid Contract Number.";
    public static final String LINE_ITEMS_NOT_PROVIDED = "{0} line items is not provided, it is required";
    public static final String INVALID_TARGET_ID_FOR_CONTRACT = "{0} is not a valid id for the given Contract {1}";


    // ... (Other configuration properties)
}
