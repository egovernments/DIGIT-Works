package org.egov.works.mukta.adapter.config;

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
public class MuktaAdaptorConfig {

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
    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;

    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;

    //SMSNotification
    @Value("${egov.sms.notification.topic}")
    private String smsNotificationTopic;

    //Expense Service
    @Value("${egov.bill.host}")
    private String billHost;
    @Value("${egov.bill.search.endpoint}")
    private String billSearchEndpoint;
    @Value("${egov.bill.search.limit}")
    private Integer billSearchLimit;
    @Value("${egov.payment.search.endpoint}")
    private String paymentSearchEndpoint;
    @Value("${egov.payment.create.endpoint}")
    private String paymentCreateEndpoint;
    @Value("${egov.payment.update.endpoint}")
    private String paymentUpdateEndpoint;

    //Expense Calculator Service
    @Value("${egov.bill.calculator.host}")
    private String billCalculatorHost;
    @Value("${egov.bill.calculator.search.endpoint}")
    private String billCalculatorSearchEndpoint;

    @Value("${egov.bank.account.host}")
    private String bankAccountHost;

    @Value("${egov.bank.account.search.endpoint}")
    private String bankAccountSearchEndPoint;
    // individual
    @Value("${egov.individual.host}")
    private String individualHost;

    @Value("${egov.individual.search.endpoint}")
    private String individualSearchEndPoint;
    // organisation
    @Value("${egov.organisation.host}")
    private String organisationHost;

    @Value("${egov.organisation.search.endpoint}")
    private String organisationSearchEndPoint;
    @Value("${ifms.pi.index.enrich.topic}")
    private String ifmsPiEnrichmentTopic;

    //State Level Tenant I'd
    @Value("${state.level.tenant.id}")
    private String stateLevelTenantId;

    // es log configurations
    @Value("${egov.es.indexer.host}")
    private String esIndexerHost;

    @Value("${ifms.request.log.index}")
    private String ifmsRequestLogIndex;

    @Value("${ifms.error.log.index}")
    private String ifmsErrorLogIndex;

    @Value("${ifms.request.log.enabled}")
    private Boolean ifmsRequestLogEnabled;

    @Value("${ifms.error.log.enabled}")
    private Boolean ifmsErrorLogEnabled;

    @Value("${ifms.request.enc.secret}")
    private String ifmsRequestEncSecret;
    @Value("${ifms.jit.hostname}")
    private String ifmsJitHostName;

    @Value("${ifms.jit.authenticate.endpoint}")
    private String ifmsJitAuthEndpoint;

    @Value("${ifms.jit.service.endpoint}")
    private String ifmsJitRequestEndpoint;

    @Value("${ifms.jit.client.id}")
    private String ifmsJitClientId;

    @Value("${ifms.jit.client.secret}")
    private String ifmsJitClientSecret;

    @Value("${ifms.jit.public.key.filepath}")
    private String ifmsJitPublicKeyFilePath;

    @Value("${payment.create.topic}")
    private String paymentCreateTopic;

    @Value("${egov.program.service.host}")
    private String programServiceHost;
    @Value("${egov.program.service.disbursement.endpoint}")
    private String programServiceDisbursementEndpoint;

    // Kafka Topics
    @Value("${disburse.create.topic}")
    private String disburseCreateTopic;
}
