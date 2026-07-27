package org.egov.enrichment;

import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.util.AttendanceServiceUtil;
import org.egov.web.models.FaceAuthEvent;
import org.egov.web.models.FaceAuthEventRequest;
import org.egov.web.models.FaceAuthEventSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class FaceAuthEventEnrichment {

    private final AttendanceServiceUtil attendanceServiceUtil;

    @Autowired
    public FaceAuthEventEnrichment(AttendanceServiceUtil attendanceServiceUtil) {
        this.attendanceServiceUtil = attendanceServiceUtil;
    }

    /**
     * API-stage enrichment: id and auditDetails are server-owned, so they are ALWAYS overwritten —
     * a client-supplied id (spoofable PK / duplicate-key poison) or auditDetails (spoofable audit
     * trail) is never trusted.
     */
    public void enrichCreateRequest(FaceAuthEventRequest request) {
        log.info("Enriching face auth event create request");
        String byUser = request.getRequestInfo().getUserInfo().getUuid();
        AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(byUser, null, true);

        List<FaceAuthEvent> events = request.getFaceAuthEvents();
        for (FaceAuthEvent event : events) {
            event.setId(String.valueOf(UUID.randomUUID()));
            event.setAuditDetails(auditDetails);
        }
        log.info("Enriched {} face auth events for create", events.size());
    }

    /**
     * Consumer-stage enrichment: fills id/auditDetails only when absent — a no-op for events that
     * already passed API-stage enrichment, and a safety net for events produced straight to the bulk
     * topic. Never regenerates an id that is already assigned (idempotent under redelivery).
     */
    public void enrichMissingIdentifiers(FaceAuthEventRequest request) {
        String byUser = request.getRequestInfo().getUserInfo().getUuid();
        AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(byUser, null, true);

        for (FaceAuthEvent event : request.getFaceAuthEvents()) {
            if (event.getId() == null || event.getId().isEmpty()) {
                event.setId(String.valueOf(UUID.randomUUID()));
            }
            if (event.getAuditDetails() == null) {
                event.setAuditDetails(auditDetails);
            }
        }
    }

    public void enrichSearchRequest(FaceAuthEventSearchCriteria searchCriteria) {
        if (searchCriteria.getLimit() == null)
            searchCriteria.setLimit(100);

        if (searchCriteria.getOffset() == null)
            searchCriteria.setOffset(0);

        if (searchCriteria.getLimit() > 1000)
            searchCriteria.setLimit(1000);
    }
}
