package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.producer.Producer;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.web.models.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceDocumentEventService {

    private final Producer producer;
    private final AttendanceServiceConfiguration config;
    private final ObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    public void processFirstSignatureEvents(AttendanceLogRequest request) {
        if (CollectionUtils.isEmpty(request.getAttendance())) return;

        for (AttendanceLog log : request.getAttendance()) {
            Object additionalDetails = log.getAdditionalDetails();
            if (additionalDetails == null) continue;

            Map<String, Object> detailsMap;
            if (additionalDetails instanceof Map) {
                detailsMap = (Map<String, Object>) additionalDetails;
            } else {
                detailsMap = objectMapper.convertValue(additionalDetails, LinkedHashMap.class);
            }

            String fileStoreId = (String) detailsMap.get("signatureFileStoreId");
            if (fileStoreId == null || fileStoreId.isEmpty()) continue;

            if (!isFirstSignature(detailsMap)) continue;

            AttendanceDocumentEventRequest event = AttendanceDocumentEventRequest.builder()
                    .requestInfo(request.getRequestInfo())
                    .attendanceDocumentEvent(AttendanceDocumentEvent.builder()
                            .individualId(log.getIndividualId())
                            .tenantId(log.getTenantId())
                            .fileStore(fileStoreId)
                            .type("SIGNATURE")
                            .clientAuditDetails(log.getClientAuditDetails())
                            .build())
                    .build();

            producer.push(log.getTenantId(), config.getFirstAttendanceLogTopic(), event);
        }
    }

    private boolean isFirstSignature(Map<String, Object> detailsMap) {
        Object flag = detailsMap.get("isFirstSignature");
        if (flag == null) return false;
        return Boolean.TRUE.equals(flag) || "true".equals(String.valueOf(flag));
    }
}
