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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class NotificationServiceConfiguration {


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

    //Localization
    @Value("${egov.localization.host}")
    private String localizationHost;

    @Value("${egov.localization.context.path}")
    private String localizationContextPath;

    @Value("${egov.localization.search.endpoint}")
    private String localizationSearchEndpoint;

    @Value("${egov.localization.statelevel}")
    private Boolean isLocalizationStateLevel;



    //SMS notification
    @Value("${notification.sms.enabled}")
    private Boolean isSMSEnabled;

    @Value("${kafka.topics.notification.sms}")
    private String smsNotifTopic;

//    @Value("${kafka.topics.mukta.notification.sms.name}")
//    private String muktaSmsNotifTopic;

    @Value("${kafka.topics.works.notification.sms.name}")
    private String muktaNotificationTopic;


    @Value("${sms.isAdditonalFieldRequired}")
    private boolean isAdditonalFieldRequired;


    //URL shortner
    @Value("${egov.url.shortner.host}")
    private String urlShortnerHost;

    @Value("${egov.url.shortner.endpoint}")
    private String urlShortnerEndpoint;

    @Value("${estimate.kafka.update.topic}")
    private String estimateUpdateTopic;

    @Value("${expense.billing.bill.create}")
    private String billCreateTopic;

    @Value("${expense.billing.bill.update}")
    private String billUpdateTopic;

    //Organisation Service
    @Value("${works.organisation.host}")
    private String organisationServiceHost;

    @Value("${works.organisation.endpoint}")
    private String organisationServiceEndpoint;

    //Expense Service
    @Value("${works.expense.calculator.host}")
    private String expenseCalculatorServiceHost;
    @Value("${works.expense.calculator.endpoint}")
    private String expenseCalculatorServiceEndpoint;

    @Value("${musterroll.kafka.update.topic}")
    private String updateMusterRollTopic;

    @Value("${measurement-service.kafka.update.topic}")
    private String updateMeasurementServiceKafkaTopic;

    @Value("${org.kafka.create.topic}")
    private String orgKafkaCreateTopic;

    @Value("${org.kafka.update.topic}")
    private String orgKafkaUpdateTopic;

    @Value("${individual.producer.save.topic}")
    private String saveIndividualTopic;

    @Value("${individual.producer.update.topic}")
    private String updateIndividualTopic;








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
