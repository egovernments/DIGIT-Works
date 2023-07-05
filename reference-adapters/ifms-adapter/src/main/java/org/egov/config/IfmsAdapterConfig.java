package org.egov.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class IfmsAdapterConfig {

    @Value("${payment.status.kafka.topic}")
    private String paymentStatusTopic;

    @Value("${ifms.jit.hostname}")
    private String ifmsJitHostName;

    @Value("${ifms.jit.authenticate.endpoint}")
    private String ifmsJitAuthEndpoint;

    @Value("${ifms.jit.request.data.endpoint}")
    private String ifmsJitRequestEndpoint;

    @Value("${ifms.jit.client.id}")
    private String ifmsJitClientId;

    @Value("${ifms.jit.client.secret}")
    private String ifmsJitClientSecret;

    @Value("${ifms.jit.public.key}")
    private String ifmsJitPublic;

    @Value("${ifms.jit.public.key.path}")
    private String ifmsJitPublicKeyPath;

    @Value("${ifms.jit.public.key.file}")
    private String ifmsJitPublicKeyFile;

    @Value("${billing.payment.create}")
    private String paymentCreateTopic;

    @Value("${billing.payment.update}")
    private String paymentUpdateTopic;

    @Value("${billing.payment.enrich}")
    private String paymentEnrichTopic;

    @Value("${state.level.tenant.id}")
    private String stateLevelTenantId;

    // MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;

    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;

    // bill
    @Value("${egov.bill.host}")
    private String billHost;

    @Value("${egov.bill.search.endpoint}")
    private String billSearchEndPoint;

    // Bank account
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

    // idgen
    @Value("${egov.idgen.host}")
    private String idgenHost;

    @Value("${egov.idgen.path}")
    private String idGenPath;

    @Value("${egov.idgen.ifms.bill.reference.number}")
    private String paymentInstructionNumberFormat;

}
