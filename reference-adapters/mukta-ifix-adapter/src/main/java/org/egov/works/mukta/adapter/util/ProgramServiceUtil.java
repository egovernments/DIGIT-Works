package org.egov.works.mukta.adapter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.constants.Error;
import org.egov.works.mukta.adapter.enrichment.PaymentInstructionEnrichment;
import org.egov.works.mukta.adapter.web.models.DisbursementCreateRequest;
import org.egov.works.mukta.adapter.web.models.DisbursementRequest;
import org.egov.works.mukta.adapter.web.models.MsgHeader;
import org.egov.works.mukta.adapter.web.models.enums.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.egov.works.mukta.adapter.config.Constants.*;

@Slf4j
@Component
public class ProgramServiceUtil {
    private final RestTemplate restTemplate;

    private final MuktaAdaptorConfig config;
    private final PaymentInstructionEnrichment paymentInstructionEnrichment;
    private final MdmsUtil mdmsUtil;

    @Autowired
    public ProgramServiceUtil(RestTemplate restTemplate, PaymentInstructionEnrichment paymentInstructionEnrichment, MuktaAdaptorConfig config, MdmsUtil mdmsUtil) {
        this.restTemplate = restTemplate;
        this.config = config;
        this.paymentInstructionEnrichment = paymentInstructionEnrichment;
        this.mdmsUtil = mdmsUtil;
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
            throw new CustomException(Error.PROGRAM_SERVICE_ERROR,"Error while calling program service for disbursement");
        }
        log.info("Response from program service for disbursement: " + response);
    }

    public MsgHeader getMessageCallbackHeader(RequestInfo requestInfo, String locationCode) {
        String idFormat = "program@{URI}";
        Map<String, Map<String, JSONArray>> exchangeServers = mdmsUtil.fetchExchangeServers(requestInfo,locationCode);
        JSONArray exchangeServer = exchangeServers.get(MDMS_EXCHANGE_MODULE_NAME).get(MDMS_EXCHANGE_SERVER_MASTER);
        String senderId = null;
        String receiverId = null;
        for (Object o : exchangeServer) {
            Map<String, String> exchangeServerMap = (Map<String, String>) o;
            if (exchangeServerMap.get("code").equals("IFMS")) {
                receiverId = exchangeServerMap.get("hostUrl");
            }
            if (exchangeServerMap.get("code").equals("MUKTA")) {
                senderId = exchangeServerMap.get("hostUrl");
            }
        }

        return MsgHeader.builder()
                .messageId(UUID.randomUUID().toString())
                .senderId(idFormat.replace("{URI}", Objects.requireNonNull(senderId)))
                .receiverId(idFormat.replace("{URI}", Objects.requireNonNull(receiverId)))
                .senderUri(senderId)
                .build();
    }
}
