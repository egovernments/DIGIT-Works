package org.egov.works.mukta.adapter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.enrichment.PaymentInstructionEnrichment;
import org.egov.works.mukta.adapter.web.models.DisbursementCreateRequest;
import org.egov.works.mukta.adapter.web.models.DisbursementRequest;
import org.egov.works.mukta.adapter.web.models.enums.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ProgramServiceUtil {
    private final RestTemplate restTemplate;

    private final MuktaAdaptorConfig config;
    private final PaymentInstructionEnrichment paymentInstructionEnrichment;

    @Autowired
    public ProgramServiceUtil(RestTemplate restTemplate, PaymentInstructionEnrichment paymentInstructionEnrichment, MuktaAdaptorConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
        this.paymentInstructionEnrichment = paymentInstructionEnrichment;
    }

    public void callProgramServiceDisbursement(DisbursementRequest disbursement) {
        log.info("Calling program service for disbursement");
        String url = config.getProgramServiceHost() + config.getProgramServiceDisbursementEndpoint();
        log.info("URL for program service disbursement: " + url);
        Object response = null;
        try {
            response = restTemplate.postForObject(url, disbursement, Object.class);
        } catch (Exception e) {
            log.error("Error while calling program service for disbursement", e);
            paymentInstructionEnrichment.enrichDisbursementStatus(disbursement.getMessage(), StatusCode.FAILED, e.getMessage());
        }
        log.info("Response from program service for disbursement: " + response);
    }
}
