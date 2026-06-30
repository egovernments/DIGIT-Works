package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class FileStoreUtil {

    private final RestTemplate restTemplate;
    private final ExpenseCalculatorConfiguration config;
    private final ObjectMapper objectMapper;

    @Autowired
    public FileStoreUtil(RestTemplate restTemplate,ObjectMapper objectMapper, ExpenseCalculatorConfiguration config) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.config = config;
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

    private String getFileStoreId(Object response) {
        Map<String,Object> res = objectMapper.convertValue(response, Map.class);
        List<Map<String,String>> files = (List<Map<String, String>>) res.get("files");
        if(files.isEmpty())
            throw new RuntimeException("File not uploaded to filestore");
        return files.get(0).get("fileStoreId");
    }

}
