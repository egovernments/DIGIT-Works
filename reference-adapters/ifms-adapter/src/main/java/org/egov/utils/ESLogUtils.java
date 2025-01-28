package org.egov.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.Constants;
import org.egov.config.IfmsAdapterConfig;
import org.egov.config.JITAuthValues;
import org.egov.enc.SymmetricEncryptionService;
import org.egov.kafka.IfmsAdapterProducer;
import org.egov.web.models.ErrorRes;
import org.egov.web.models.jit.JITErrorRequestLog;
import org.egov.web.models.jit.JITRequest;
import org.egov.web.models.jit.JITRequestLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class ESLogUtils {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IfmsAdapterConfig config;

    @Autowired
    private JITAuthValues jitAuthValues;

    @Autowired
    private IfmsAdapterProducer ifmsAdapterProducer;

    public @Valid JITRequestLog saveAuthenticateRequest(String request, String authResponse) {
        JITRequestLog jitRequestLog = null;
        try {
            if (config.getIfmsRequestLogEnabled()) {
                log.info("Creating log for jit auth request in ES");
                // Convert the JSON object to a string
                jitRequestLog = JITRequestLog.builder()
                        .id(UUID.randomUUID().toString())
                        .serviceId(null)
                        .jitBillNo("AUTH")
                        .encRequest(request)
                        .encResponse(authResponse)
                        .decryptionRek(null)
                        .createdtime(System.currentTimeMillis())
                        .build();
                String endPoint = Constants.DOC_INSERT_PATH + "/" + jitRequestLog.getId();
                StringBuilder uri = getURI(config.getIfmsRequestLogIndex(), endPoint);
                executeQuery(uri, jitRequestLog);
                log.info("Request logged of jit request and response in ES");
            }
        } catch (Exception e) {
            log.info("Exception in saveJitRequestLogsToES : "+ e.getMessage());
            ErrorRes errorRes = ErrorRes.builder().message(e.getMessage()).objects(Collections.singletonList(jitRequestLog)).build();
            ifmsAdapterProducer.push(config.getIfixAdapterESErrorQueueTopic(), errorRes);
        }
        return jitRequestLog;
    }

    public @Valid JITErrorRequestLog saveAuthenticateFailureRequest(String request, Exception ex, Integer statusCode, JITRequestLog jitRequestLog) {
        JITErrorRequestLog jitErrorRequestLog = null;
        try {
            if (config.getIfmsRequestLogEnabled()) {
                log.info("Creating log for jit auth request in ES");
                // Convert the JSON object to a string
                jitErrorRequestLog = JITErrorRequestLog.builder()
                        .id(UUID.randomUUID().toString())
                        .requestId(jitRequestLog != null ? jitRequestLog.getId() : null)
                        .serviceId(null)
                        .jitEncRequest(request)
                        .errorMsg(ex.toString())
                        .statusCode(statusCode)
                        .createdtime(System.currentTimeMillis())
                        .build();
                String endPoint = Constants.DOC_INSERT_PATH + "/" + jitErrorRequestLog.getId();
                StringBuilder uri = getURI(config.getIfmsErrorLogIndex(), endPoint);
                executeQuery(uri, jitErrorRequestLog);
                log.info("Request logged of jit request and response in ES");
            }
        } catch (Exception e) {
            log.error("Exception in saveJitRequestLogsToES : "+ e.getMessage());
            ErrorRes errorRes = ErrorRes.builder().message(e.getMessage()).objects(Collections.singletonList(jitErrorRequestLog)).build();
            ifmsAdapterProducer.push(config.getIfixAdapterESErrorQueueTopic(), errorRes);
        }
        return jitErrorRequestLog;
    }

	public @Valid JITRequestLog saveJitRequestLogsToES(JITRequest jitRequest, String encResponse, String decryptionRek) {
		JITRequestLog jitRequestLog = null;
        try {
            if (config.getIfmsRequestLogEnabled()) {
                log.info("Creating log for jit request and response in ES");
                // Convert the JSON object to a string
                String requestBody = objectMapper.writeValueAsString(jitRequest);
                String encryptedRequest = SymmetricEncryptionService.encryptRequest(requestBody, config.getIfmsRequestEncSecret());
                String jitBillNo = getJitBillNoFromJitRequest(jitRequest);
                jitRequestLog = JITRequestLog.builder()
                        .id(UUID.randomUUID().toString())
                        .serviceId(jitRequest.getServiceId())
                        .jitBillNo(jitBillNo)
                        .encRequest(encryptedRequest)
                        .encResponse(encResponse)
                        .decryptionRek(decryptionRek)
                        .createdtime(System.currentTimeMillis())
                        .build();
                String endPoint = Constants.DOC_INSERT_PATH + "/" + jitRequestLog.getId();
                StringBuilder uri = getURI(config.getIfmsRequestLogIndex(), endPoint);
                executeQuery(uri, jitRequestLog);
                log.info("Request logged of jit request and response in ES");
            }
        } catch (Exception e) {
            log.error("Exception in saveJitRequestLogsToES : "+ e.getMessage());
            ErrorRes errorRes = ErrorRes.builder().message(e.getMessage()).objects(Collections.singletonList(jitRequestLog)).build();
            ifmsAdapterProducer.push(config.getIfixAdapterESErrorQueueTopic(), errorRes);
        }
        return jitRequestLog;
	}

    public @Valid JITErrorRequestLog saveErrorResponseLogsToES(JITRequestLog jitRequestLog, JITRequest jitRequest, Map<String, String> payload, Exception ex, Integer statusCode) {
        JITErrorRequestLog jitErrorRequestLog = null;
        try {
            if (config.getIfmsErrorLogEnabled()) {
                log.info("Creating error log for jit in ES");
                // Convert the JSON object to a string
                String requestBody = payload.get("encryptedPayload");
                String decryptionRek = payload.get("decryptionRek");
                String encryptionRek = payload.get("encryptionRek");
                String encryptedRequest = SymmetricEncryptionService.encryptRequest(requestBody, config.getIfmsRequestEncSecret());
                jitErrorRequestLog = JITErrorRequestLog.builder()
                        .id(UUID.randomUUID().toString())
                        .serviceId(jitRequest != null ? jitRequest.getServiceId() : null)
                        .requestId(jitRequestLog != null ?jitRequestLog.getId() : null)
                        .statusCode(statusCode)
                        .jitEncRequest(encryptedRequest)
                        .decryptionRek(decryptionRek)
                        .encryptionRek(encryptionRek)
                        .authToken(jitAuthValues.getAuthToken())
                        .sekString(jitAuthValues.getSekString())
                        .errorMsg(ex.toString())
                        .createdtime(System.currentTimeMillis())
                        .build();
                String endPoint = Constants.DOC_INSERT_PATH + "/" + jitErrorRequestLog.getId();
                StringBuilder uri = getURI(config.getIfmsErrorLogIndex(), endPoint);
                executeQuery(uri, jitErrorRequestLog);
                log.info("Error loges saved of jit in ES");
            }

        } catch (Exception e) {
            log.error("Exception in saveErrorResponseLogsToES : "+ e.getMessage());
            ErrorRes errorRes = ErrorRes.builder().message(e.getMessage()).objects(Collections.singletonList(jitErrorRequestLog)).build();
            ifmsAdapterProducer.push(config.getIfixAdapterESErrorQueueTopic(), errorRes);
        }
        return jitErrorRequestLog;
    }

    private String getJitBillNoFromJitRequest(JITRequest jitRequest) {
        String jitBillNo = null;
        try {
            if (jitRequest != null && jitRequest.getParams() != null) {
                JsonNode node = objectMapper.valueToTree(jitRequest.getParams());
                switch (jitRequest.getServiceId()){
                    case PI:
                    case PIS:
                        jitBillNo = node.get("jitBillNo").asText();
                        break;
                    case PAG:
                        jitBillNo = node.get("billNo").asText();
                        break;
                    case COR:
                    case FTPS:
                    case FTFPS:
                        jitBillNo = node.get("jitCorBillNo").asText();
                        break;
                }
            }
        } catch (Exception e) {
            log.info("Exception in getJitBillNoFromJitRequest : "+ e.getMessage());
        }
        return jitBillNo;
    }

    private Object executeQuery(StringBuilder uri, Object request) {

        Object response = new HashMap<>();
        try {
            response = restTemplate.postForObject(uri.toString(), request, Map.class);
            log.info("Elasticsearch query executed." + response);
        } catch (Exception e) {
            log.error("Exception occurred while executing query in indexer : ", e);
            throw e;
        }
        return response;
    }


    private StringBuilder getURI(String indexName, String endpoint){
        StringBuilder uri = new StringBuilder(config.getEsIndexerHost());
        uri.append(indexName);
        uri.append(endpoint);
        return uri;
    }

}
