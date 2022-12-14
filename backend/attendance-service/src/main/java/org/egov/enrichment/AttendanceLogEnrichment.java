package org.egov.enrichment;

import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.util.AttendanceServiceUtil;
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
    private AttendanceServiceUtil attendanceServiceUtil;
    @Autowired
    private AttendanceServiceConfiguration config;
    public void enrichAttendanceLogCreateRequest(AttendanceLogRequest attendanceLogRequest) {
        List<AttendanceLog> attendanceLog = attendanceLogRequest.getAttendance();
        String byUser = attendanceLogRequest.getRequestInfo().getUserInfo().getUuid();
        AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(byUser,null,true);
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
        String byUser = attendanceLogRequest.getRequestInfo().getUserInfo().getUuid();
        attendanceLog.forEach(e -> {
            AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(byUser,e.getAuditDetails(),false);
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

    public void enrichAttendanceLogSearchRequest(RequestInfo requestInfo, AttendanceLogSearchCriteria searchCriteria) {

        if (searchCriteria.getLimit() == null)
            searchCriteria.setLimit(config.getAttendanceLogDefaultLimit());

        if (searchCriteria.getOffset() == null)
            searchCriteria.setOffset(config.getAttendanceLogDefaultOffset());

        if (searchCriteria.getLimit() != null && searchCriteria.getLimit() > config.getAttendanceLogMaxLimit())
            searchCriteria.setLimit(config.getAttendanceLogMaxLimit());

    }
}
