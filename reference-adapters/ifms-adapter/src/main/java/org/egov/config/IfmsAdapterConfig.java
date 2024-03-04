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

    @Value("${ifms.pi.index.enrich.topic}")
    private String ifmsPiEnrichmentTopic;

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

    @Value("${egov.payment.update.endpoint}")
    private String paymentUpdateEndPoint;

    @Value("${egov.payment.search.endpoint}")
    private String paymentSearchEndPoint;

    @Value("${egov.payment.create.endpoint}")
    private String paymentCreateEndpoint;

    @Value("${egov.bill.search.limit}")
    private Integer billSearchLimit;

    // bill calculator
    @Value("${egov.bill.calculator.host}")
    private String billCalculatorHost;

    @Value("${egov.bill.calculator.search.endpoint}")
    private String billCalculatorSearchEndPoint;

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

    @Value("${egov.idgen.ifms.pi.reference.number}")
    private String paymentInstructionNumberFormat;

    @Value("${egov.idgen.ifms.benf.reference.number}")
    private String piBenefInstructionNumberFormat;

    // audit log
    @Value("${egov.audit.log.host}")
    private String auditLogHost;

    @Value("${egov.audit.log.search.endpoint}")
    private String auditLogSearchEndPoint;

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
}
