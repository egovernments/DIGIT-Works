package org.egov.config;

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
public class Configuration {

    @Value("${app.timezone}")
    private String timeZone;

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

    // User Config
    @Value("${egov.user.host}")
    private String userHost;

    @Value("${egov.user.context.path}")
    private String userContextPath;

    @Value("${egov.user.create.path}")
    private String userCreateEndpoint;

    @Value("${egov.user.search.path}")
    private String userSearchEndpoint;

    @Value("${egov.user.update.path}")
    private String userUpdateEndpoint;

    // Individual config
    @Value("${works.individual.host}")
    private String individualHost;

    @Value("${works.individual.create.endpoint}")
    private String individualCreateEndpoint;

    @Value("${works.individual.search.endpoint}")
    private String individualSearchEndpoint;

    @Value("${works.individual.update.endpoint}")
    private String individualUpdateEndpoint;

    // Idgen Config
    @Value("${egov.idgen.host}")
    private String idGenHost;

    @Value("${egov.idgen.path}")
    private String idGenPath;

    @Value("${egov.idgen.organisation.application.number.name}")
    private String orgApplicationNumberName;

    @Value("${egov.idgen.organisation.application.number.format}")
    private String orgApplicationNumberFormat;

    @Value("${egov.idgen.organisation.number.name}")
    private String orgNumberName;

    @Value("${egov.idgen.organisation.number.format}")
    private String orgNumberFormat;

    @Value("${egov.idgen.function.application.number.name}")
    private String functionApplicationNumberName;

    @Value("${egov.idgen.function.application.number.format}")
    private String functionApplicationNumberFormat;

    // Workflow Config
    @Value("${egov.workflow.host}")
    private String wfHost;

    @Value("${egov.workflow.transition.path}")
    private String wfTransitionPath;

    @Value("${egov.workflow.businessservice.search.path}")
    private String wfBusinessServiceSearchPath;

    @Value("${egov.workflow.processinstance.search.path}")
    private String wfProcessInstanceSearchPath;

    // MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;

    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;

    // HRMS
    @Value("${egov.hrms.host}")
    private String hrmsHost;

    @Value("${egov.hrms.search.endpoint}")
    private String hrmsEndPoint;

    // URLShortening
    @Value("${egov.url.shortner.host}")
    private String urlShortnerHost;

    @Value("${egov.url.shortner.endpoint}")
    private String urlShortnerEndpoint;

    // SMSNotification
    @Value("${egov.sms.notification.topic}")
    private String smsNotificationTopic;

    @Value("${kafka.topics.works.notification.sms.name}")
    private String muktaNotificationTopic;

    //topic config
    @Value("${org.kafka.create.topic}")
    private String orgKafkaCreateTopic;

    @Value("${org.kafka.update.topic}")
    private String orgKafkaUpdateTopic;

    @Value("${org.contact.details.update.topic}")
    private String organisationContactDetailsUpdateTopic;

    //search config
    @Value("${org.search.max.limit}")
    private Integer maxLimit;
    @Value("${org.default.offset}")
    private Integer defaultOffset;
    @Value("${org.default.limit}")
    private Integer defaultLimit;

    //Location
    @Value("${egov.location.host}")
    private String locationHost;

    @Value("${egov.location.context.path}")
    private String locationContextPath;

    @Value("${egov.location.endpoint}")
    private String locationEndpoint;

    @Value("${egov.location.hierarchy.type}")
    private String locationHierarchyType;

    //Notification
    @Value("${notification.sms.enabled}")
    private Boolean isSMSEnabled;

    @Value("${kafka.topics.notification.sms}")
    private String smsNotifTopic;

    //CBO url for message tenplate
    @Value("${works.cbo.url.host}")
    private String cboUrlHost;

    @Value("${works.cbo.url.endpoint}")
    private String cboUrlEndpoint;

    //localization
    @Value("${egov.localization.host}")
    private String localizationHost;

    @Value("${egov.localization.context.path}")
    private String localizationContextPath;

    @Value("${egov.localization.search.endpoint}")
    private String localizationSearchEndpoint;

    @Value("${egov.localization.statelevel}")
    private Boolean isLocalizationStateLevel;

    // Encryption Config
    @Value("${egov.enc.host}")
    private String encryptionHost;

    @Value("${egov.enc.encrypt.endpoint}")
    private String encryptionEndpoint;

    @Value("${egov.enc.decrypt.endpoint}")
    private String decryptionEndpoint;

    @Value("${state.level.tenant.id}")
    private String stateLevelTenantId;
	
	@Value("${sms.isAdditonalFieldRequired}")
    private boolean isAdditonalFieldRequired;
}
