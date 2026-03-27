package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.producer.Producer;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.web.models.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceDocumentEventService {

    private final Producer producer;
    private final AttendanceServiceConfiguration config;
    private final ObjectMapper objectMapper;

    public void processFirstSignatureEvents(AttendanceLogRequest request) {
        if (CollectionUtils.isEmpty(request.getAttendance())) return;

        for (AttendanceLog log : request.getAttendance()) {
            if (CollectionUtils.isEmpty(log.getDocumentIds())) continue;

            for (Document doc : log.getDocumentIds()) {
                if (!isFirstSignature(doc)) continue;

                AttendanceDocumentEventRequest event = AttendanceDocumentEventRequest.builder()
                        .requestInfo(request.getRequestInfo())
                        .attendanceDocumentEvent(AttendanceDocumentEvent.builder()
                                .individualId(log.getIndividualId())
                                .tenantId(log.getTenantId())
                                .fileStore(doc.getFileStore())
                                .type("SIGNATURE")
                                .clientAuditDetails(log.getClientAuditDetails())
                                .build())
                        .build();

                producer.push(log.getTenantId(), config.getFirstAttendanceLogTopic(), event);
            }
        }
    }

    private boolean isFirstSignature(Document doc) {
        if (!"SIGNATURE".equals(doc.getDocumentType())) return false;
        if (doc.getAdditionalDetails() == null) return false;
        try {
            Map<String, Object> details = objectMapper.convertValue(doc.getAdditionalDetails(), Map.class);
            Object flag = details.get("isFirstSignature");
            return Boolean.TRUE.equals(flag) || "true".equals(String.valueOf(flag));
        } catch (Exception e) {
            log.warn("Could not parse additionalDetails for document {}", doc.getId());
            return false;
        }
    }
}
