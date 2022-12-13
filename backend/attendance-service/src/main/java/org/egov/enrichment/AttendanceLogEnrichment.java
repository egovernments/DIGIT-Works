package org.egov.enrichment;

import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.web.models.AttendanceLogSearchCriteria;
import org.egov.web.models.Document;
import org.egov.web.models.AttendanceLog;
import org.egov.web.models.AttendanceLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class AttendanceLogEnrichment {

    @Autowired
    private AttendanceServiceConfiguration config;
    public void enrichAttendanceLogCreateRequest(AttendanceLogRequest attendanceLogRequest) {
        List<AttendanceLog> attendanceLog = attendanceLogRequest.getAttendance();
        AuditDetails auditDetails = getAuditDetails(attendanceLogRequest);
        attendanceLog.forEach(e -> {
            e.setAuditDetails(auditDetails);
            UUID attendanceLogId = UUID.randomUUID();
            e.setId(attendanceLogId);
            e.getDocumentIds().forEach(d -> {
                d.setId(String.valueOf(UUID.randomUUID()));
            });
        });
    }

    public void enrichAttendanceLogUpdateRequest(AttendanceLogRequest attendanceLogRequest) {
        List<AttendanceLog> attendanceLog = attendanceLogRequest.getAttendance();
        AuditDetails auditDetails = getAuditDetails(attendanceLogRequest);
        attendanceLog.forEach(e -> {
            e.setAuditDetails(auditDetails);
            // enrich the documentId if not present
            List<Document> documentIds = e.getDocumentIds();
            documentIds.forEach(d -> {
                if(d.getId() == null){
                    d.setId(String.valueOf(UUID.randomUUID()));
                }
            });
        });
    }

    private AuditDetails getAuditDetails(AttendanceLogRequest attendanceLogRequest){
        return AuditDetails.builder()
                .createdBy(attendanceLogRequest.getRequestInfo().getUserInfo().getUuid())
                .createdTime(System.currentTimeMillis()).lastModifiedBy(attendanceLogRequest.getRequestInfo()
                        .getUserInfo().getUuid())
                .lastModifiedTime(System.currentTimeMillis())
                .build();
    }

    public void enrichAttendanceLogSearchRequest(RequestInfo requestInfo, AttendanceLogSearchCriteria searchCriteria) {

        if (searchCriteria.getLimit() == null)
            searchCriteria.setLimit(config.getDefaultLimit());

        if (searchCriteria.getOffset() == null)
            searchCriteria.setOffset(config.getDefaultOffset());

        if (searchCriteria.getLimit() != null && searchCriteria.getLimit() > config.getMaxLimit())
            searchCriteria.setLimit(config.getMaxLimit());

    }
}
