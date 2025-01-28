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

    // MDMS V2
    @Value("${egov.mdms.v2.host}")
    private String mdmsV2Host;

    @Value("${egov.mdms.v2.search.endpoint}")
    private String mdmsV2EndPoint;

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

    //State Level Tenant I'd
    @Value("${state.level.tenant.id}")
    private String stateLevelTenantId;

    @Value("${payment.create.topic}")
    private String paymentCreateTopic;
    @Value("${ifms.pi.index.enrich.topic}")
    private String ifmsPiEnrichmentTopic;
    @Value("${ifms.pi.index.internal.topic}")
    private String ifmsPiIndexInternalTopic;

    @Value("${mukta.ifix.adapter.error.queue.topic}")
    private String muktaIfixAdapterErrorQueueTopic;

    @Value("${mukta.ifix.adapter.error.queue.topic}")
    private String muktaIfixAdapterErrorQueueTopic;

    @Value("${mukta.ifix.adapter.error.queue.topic}")
    private String muktaIfixAdapterErrorQueueTopic;

    @Value("${egov.program.service.host}")
    private String programServiceHost;
    @Value("${program.service.disbursement.create.endpoint}")
    private String programServiceDisbursementEndpoint;

    // Kafka Topics
    @Value("${disburse.create.topic}")
    private String disburseCreateTopic;
    @Value("${disburse.update.topic}")
    private String disburseUpdateTopic;

    // Pagination Default Parameters
    @Value("${mukta.adapter.default.offset}")
    private Integer defaultOffset;
    @Value("${mukta.adapter.default.limit}")
    private Integer defaultLimit;
    @Value("${mukta.adapter.encryption.key}")
    private String muktaAdapterEncryptionKey;

    //System User
    @Value("${egov.system.user.username}")
    private String systemUserUsername;

    @Value("${works.estimate.host}")
    private String estimateHost;

    @Value("${works.estimate.search.endpoint}")
    private String estimateSearchEndpoint;

    // Contract service
    @Value("${egov.contract.service.host}")
    private String contractHost;

    @Value("${egov.contract.service.search.endpoint}")
    private String contractSearchEndPoint;

    // Project service
    @Value("${project.service.host}")
    private String projectHost;

    @Value("${project.search.path}")
    private String projectSearchPath;

}
