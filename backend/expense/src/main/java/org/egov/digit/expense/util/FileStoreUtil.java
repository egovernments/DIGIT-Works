package org.egov.digit.expense.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.config.Configuration;
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

import static org.egov.digit.expense.config.Constants.*;

@Slf4j
@Component
public class FileStoreUtil {

    private final RestTemplate restTemplate;
    private final Configuration config;
    private final ObjectMapper objectMapper;

    @Autowired
    public FileStoreUtil(RestTemplate restTemplate,ObjectMapper objectMapper, Configuration config) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.config = config;
    }

    public String uploadFileAndGetFileStoreId(String tenantId, Resource resource){
        HttpHeaders headers = new HttpHeaders();
        Object response = null;
        StringBuilder uri = new StringBuilder();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        uri.append(config.getFileStoreHost()).append(config.getFileStoreEndpoint());
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(TENANT_ID, tenantId);
        body.add(FILE, resource);
        body.add(MODULE, BILL_TRANSACTION_REPORT_MODULE);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            log.info("inside fs try");
            response = restTemplate.postForObject(uri.toString(),requestEntity, Object.class);
        }catch (Exception e){
            log.error("Exception while uploading file to filestore: ", e);
            throw new RuntimeException(e.getMessage());
        }
        return getFileStoreId(response);
    }

    private String getFileStoreId(Object response) {
        log.info("inside fs getFileStoreId");
        Map<String,Object> res = objectMapper.convertValue(response, Map.class);
        List<Map<String,String>> files = (List<Map<String, String>>) res.get("files");
        if(files.isEmpty())
            throw new RuntimeException("File not uploaded to filestore");
        return files.get(0).get("fileStoreId");
    }

}

