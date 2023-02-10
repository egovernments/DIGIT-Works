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

    @Value("${egov.idgen.contract.number.name}")
    private String idgenContractNumberName;

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

    // kafka topics
    @Value("${contract.kafka.create.topic}")
    private String createContractTopic;

    @Value("${contract.kafka.update.topic}")
    private String updateContractTopic;

    //SMSNotification
//    @Value("${egov.sms.notification.topic}")
//    private String smsNotificationTopic;

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

    @Value("${contract.document.id.verification.required}")
    private String documentIdVerificationRequired;

    @Value("${contract.org.id.verification.required}")
    private String orgIdVerificationRequired;

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
