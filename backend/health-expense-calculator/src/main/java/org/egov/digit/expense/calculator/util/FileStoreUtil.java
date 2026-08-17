package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class FileStoreUtil {

    private final RestTemplate restTemplate;
    private final ExpenseCalculatorConfiguration config;
    private final ObjectMapper objectMapper;

    /**
     * Used only for signature image reads. Those happen inline during report generation, and the
     * shared RestTemplate has no timeout, so an unresponsive filestore would stall the report
     * consumer. The upload path deliberately keeps the shared client: a generated voucher is a far
     * larger request and its existing behaviour must not change.
     */
    private final RestTemplate signatureRestTemplate;

    @Autowired
    public FileStoreUtil(RestTemplate restTemplate,ObjectMapper objectMapper, ExpenseCalculatorConfiguration config) {
        this(restTemplate, objectMapper, config, timeoutBoundRestTemplate(
                config.getSignatureConnectTimeoutMs(), config.getSignatureReadTimeoutMs()));
    }

    // Lets a test supply its own client for the signature reads.
    FileStoreUtil(RestTemplate restTemplate, ObjectMapper objectMapper, ExpenseCalculatorConfiguration config,
                  RestTemplate signatureRestTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.config = config;
        this.signatureRestTemplate = signatureRestTemplate;
    }

    private static RestTemplate timeoutBoundRestTemplate(int connectTimeoutMs, int readTimeoutMs) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeoutMs);
        factory.setReadTimeout(readTimeoutMs);
        return new RestTemplate(factory);
    }

    private static final int MAX_UPLOAD_RETRIES = 3;
    private static final long UPLOAD_RETRY_BASE_DELAY_MS = 5000L;

    public String uploadFileAndGetFileStoreId(String tenantId, Resource resource) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        String uri = config.getFileStoreHost() + config.getFileStoreEndpoint();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("tenantId", tenantId);
        body.add("file", resource);
        body.add("module", "BILL");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        Exception lastException = null;
        for (int attempt = 1; attempt <= MAX_UPLOAD_RETRIES; attempt++) {
            try {
                log.info("Filestore upload attempt {}/{} for tenantId: {}", attempt, MAX_UPLOAD_RETRIES, tenantId);
                Object response = restTemplate.postForObject(uri, requestEntity, Object.class);
                return getFileStoreId(response);
            } catch (Exception e) {
                lastException = e;
                log.error("Filestore upload attempt {}/{} failed: {}", attempt, MAX_UPLOAD_RETRIES, e.getMessage());
            }
        }
        throw new RuntimeException("Filestore upload failed after " + MAX_UPLOAD_RETRIES + " attempts: " + lastException.getMessage(), lastException);
    }

    /**
     * Fetches a stored file's bytes. Used to pull signature images into the generated voucher;
     * callers treat a failure as "no image available" rather than as fatal, so this throws
     * plainly and leaves the retry/skip decision to them.
     */
    public byte[] downloadFile(String tenantId, String fileStoreId) {
        String uri = UriComponentsBuilder
                .fromHttpUrl(config.getFileStoreHost() + config.getFileStoreEndpoint() + "/id")
                .queryParam("tenantId", tenantId)
                .queryParam("fileStoreId", fileStoreId)
                .toUriString();

        ResponseEntity<byte[]> response =
                signatureRestTemplate.exchange(uri, HttpMethod.GET, HttpEntity.EMPTY, byte[].class);
        byte[] body = response.getBody();
        if (body == null || body.length == 0)
            throw new RuntimeException("Filestore returned an empty file for fileStoreId=" + fileStoreId);
        return body;
    }

    private String getFileStoreId(Object response) {
        Map<String,Object> res = objectMapper.convertValue(response, Map.class);
        List<Map<String,String>> files = (List<Map<String, String>>) res.get("files");
        if(files.isEmpty())
            throw new RuntimeException("File not uploaded to filestore");
        return files.get(0).get("fileStoreId");
    }

}
