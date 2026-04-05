package org.egov.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class FileStoreUtil {

    @Value("${egov.filestore.host:http://filestore:8080}")
    private String fileStoreHost;

    @Value("${egov.filestore.endpoint:/filestore/v1/files}")
    private String fileStoreEndpoint;

    @Value("${egov.filestore.download.endpoint:/filestore/v1/files/id}")
    private String fileStoreDownloadEndpoint;

    private final RestTemplate restTemplate;

    public FileStoreUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String uploadFile(byte[] fileContent, String fileName, String tenantId, String module) {
        try {
            String url = fileStoreHost + fileStoreEndpoint;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("tenantId", tenantId);
            body.add("module", module);
            body.add("file", new ByteArrayResource(fileContent) {
                @Override
                public String getFilename() {
                    return fileName;
                }
            });

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

            Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);

            if (response != null && response.containsKey("files")) {
                List<Map<String, Object>> files = (List<Map<String, Object>>) response.get("files");
                if (files != null && !files.isEmpty()) {
                    Map<String, Object> fileInfo = files.get(0);
                    String fileStoreId = (String) fileInfo.get("fileStoreId");
                    log.info("File uploaded successfully with fileStoreId: {}", fileStoreId);
                    return fileStoreId;
                }
            }

            log.error("Failed to upload file - no fileStoreId in response");
            return null;

        } catch (RestClientException e) {
            log.error("Error uploading file to filestore: {}", e.getMessage(), e);
            return null;
        } catch (Exception e) {
            log.error("Unexpected error uploading file to filestore: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Downloads a file from the filestore service by fileStoreId.
     * Returns null on failure so the caller can render "NA" in its place.
     */
    public byte[] downloadFileBytes(String fileStoreId, String tenantId) {
        try {
            String url = fileStoreHost + fileStoreDownloadEndpoint
                    + "?tenantId=" + tenantId + "&fileStoreId=" + fileStoreId;
            byte[] bytes = restTemplate.getForObject(url, byte[].class);
            if (bytes == null || bytes.length == 0) {
                log.warn("Empty response downloading fileStoreId: {}", fileStoreId);
                return null;
            }
            return bytes;
        } catch (RestClientException e) {
            log.warn("Failed to download file from filestore, fileStoreId={}: {}", fileStoreId, e.getMessage());
            return null;
        } catch (Exception e) {
            log.warn("Unexpected error downloading file from filestore, fileStoreId={}: {}", fileStoreId, e.getMessage());
            return null;
        }
    }
}
