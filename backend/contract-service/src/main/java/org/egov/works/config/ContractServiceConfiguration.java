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

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
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

    //Workflow Config
    @Value("${contract.workflow.module.name}")
    private String contractWFModuleName;
    @Value("${contract.workflow.business.service}")
    private String contractWFBusinessService;

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

//    @Value("${contract.document.id.verification.required}")
//    private String documentIdVerificationRequired;


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
