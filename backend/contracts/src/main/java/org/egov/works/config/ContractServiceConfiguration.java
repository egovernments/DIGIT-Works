package org.egov.works.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ContractServiceConfiguration {
    @Value("${app.timezone}")
    private String timeZone;

    //Idgen Config
    @Value("${egov.idgen.host}")
    private String idGenHost;

    @Value("${egov.idgen.path}")
    private String idGenPath;

    @Value("${works.contract.service.code}")
    private String serviceCode;

    // Muster-roll config
    @Value("${works.muster.roll.host}")
    private String musterRollSearchHost;
    @Value("${works.muster.roll.search.endpoint}")
    private String musterRollSearchEndpoint;

    //Attendance Config
    @Value("${egov.attendance.host}")
    private String attendanceHost;

    @Value("${egov.attendance.register.path}")
    private String attendanceRegisterPath;

    //Org Config
    @Value("${egov.org.host}")
    private String orgHost;

    @Value("${egov.org.search.endpoint}")
    private String orgSearchPath;

    @Value("${egov.idgen.contract.number.name}")
    private String idgenContractNumberName;

    @Value("${egov.idgen.supplement.number.name}")
    private String idgenSupplementNumberName;

    @Value("${egov.idgen.contract.revision.number.name}")
    private String idgenRevisionNumberName;

    //Workflow Config
    @Value("${contract.workflow.module.name}")
    private String contractWFModuleName;

    @Value("${contract.workflow.business.service}")
    private String contractWFBusinessService;

    @Value("${contract.workflow.time.extension.business.service}")
    private String contractTimeExtensionWFBusinessService;

    @Value("${contract.workflow.revision.business.service}")
    private String contractRevisionContractWFBusinessService;

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

    //MDMS V2
    @Value("${egov.mdms.v2.host}")
    private String mdmsV2Host;

    @Value("${egov.mdms.v2.search.endpoint}")
    private String mdmsV2EndPoint;

    //HRMS
    @Value("${egov.hrms.host}")
    private String hrmsHost;

    @Value("${egov.hrms.search.endpoint}")
    private String hrmsEndPoint;

    // kafka topics
    @Value("${contract.kafka.create.topic}")
    private String createContractTopic;

    @Value("${contract.kafka.update.topic}")
    private String updateContractTopic;

    @Value("${contracts.revision.topic}")
    private String updateTimeExtensionTopic;

    //attendance service register search config
    @Value("${contract.default.offset}")
    private Integer contractDefaultOffset;

    @Value("${contract.default.limit}")
    private Integer contractDefaultLimit;

    @Value("${contract.search.max.limit}")
    private Integer contractMaxLimit;

    //Estimate service
    @Value("${works.estimate.host}")
    private String estimateHost;

    @Value("${works.estimate.search.endpoint}")
    private String estimateEndpoint;

    @Value("${works.measurement.service.host}")
    private String measurementBookHost;

    @Value("${works.measurement.service.search.endpoint}")
    private String measurementBookSearchEndpoint;

    //Project Service
    @Value("${works.project.host}")
    private String worksProjectManagementSystemHost;

    @Value("${works.project.search.endpoint}")
    private String worksProjectManagementSystemPath;

    //Location Service
    @Value("${egov.location.host}")
    private String locationHost;

    @Value("${egov.location.context.path}")
    private String locationContextPath;

    @Value("${egov.location.endpoint}")
    private String locationEndpoint;

    //Contract service
    @Value("${works.contract.host}")
    private String contractHost;

    @Value("${works.contract.search.endpoint}")
    private String contractEndpoint;

    //Filestore service
    @Value("${egov.filestore.host}")
    private String fileStoreHost;

    @Value("${egov.filestore.endpoint}")
    private String fileStoreEndpoint;

    //SMS notification
    @Value("${notification.sms.enabled}")
    private Boolean isSMSEnabled;

    @Value("${kafka.topics.notification.sms}")
    private String smsNotifTopic;

    //Localization
    @Value("${egov.localization.host}")
    private String localizationHost;

    @Value("${egov.localization.context.path}")
    private String localizationContextPath;

    @Value("${egov.localization.search.endpoint}")
    private String localizationSearchEndpoint;

    @Value("${egov.localization.statelevel}")
    private Boolean isLocalizationStateLevel;

    //URL shortner
    @Value("${egov.url.shortner.host}")
    private String urlShortnerHost;

    @Value("${egov.url.shortner.endpoint}")
    private String urlShortnerEndpoint;

    //CBO urls for message template
    @Value("${works.cbo.url.host}")
    private String cboUrlHost;

    @Value("${works.cbo.url.endpoint}")
    private String cboUrlEndpoint;

    @Value("${contract.duedate.period}")
    private String contractDueDatePeriod;

    // Contract Revision configuration
    @Value("${contract.revision.max.limit}")
    private Integer contractRevisionMaxLimit;

    @Value("${contract.revision.measurement.validation}")
    private Boolean isMeasurementValidationRequired;


    @Value("${kafka.topics.works.notification.sms.name}")
    private String muktaNotificationTopic;

    @Value("${sms.isAdditonalFieldRequired}")
    private boolean isAdditonalFieldRequired;
    @Value("${is.caching.enabled}")
    private Boolean isCachingEnabled;

    @PostConstruct
    public void initialize() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

    @Bean
    @Autowired
    public MappingJackson2HttpMessageConverter jacksonConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }


}
