package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.config.Configuration;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class FilestoreUtil {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Configuration config;

    @Autowired
    public FilestoreUtil(RestTemplate restTemplate, ObjectMapper objectMapper, Configuration config) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.config = config;
    }

    public String upload(byte[] fileBytes, String tenantId, String fileName) {
        log.info("FilestoreUtil::upload fileName={} tenantId={}", fileName, tenantId);

        String url = config.getFilestoreHost() + config.getFilestoreUploadEndpoint();

        ByteArrayResource fileResource = new ByteArrayResource(fileBytes) {
            @Override
            public String getFilename() {
                return fileName;
            }
        };

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("tenantId", tenantId);
        body.add("module", "expense");
        body.add("file", fileResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            Object response = restTemplate.postForObject(url, requestEntity, Object.class);
            return getFileStoreId(response, fileName);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("FILESTORE_UPLOAD_FAILED", "Failed to upload file to filestore: " + e.getMessage());
        }
    }

    private String getFileStoreId(Object response, String fileName) {
        Map<String, Object> res = objectMapper.convertValue(response, Map.class);
        List<Map<String, String>> files = (List<Map<String, String>>) res.get("files");
        if (files == null || files.isEmpty())
            throw new CustomException("FILESTORE_UPLOAD_FAILED", "No fileStoreId in filestore response for: " + fileName);
        return files.get(0).get("fileStoreId");
    }
}
