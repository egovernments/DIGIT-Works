package org.egov.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.FaceAuthEvent;
import org.egov.web.models.FaceAuthEventRequest;
import org.egov.web.models.FaceAuthEventSearchCriteria;
import org.egov.web.models.RequestInfoWrapper;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class FaceAuthEventValidator {

    private static final Set<String> VALID_EVENT_TYPES = Set.of("LOGIN", "CHECK_IN", "RE_VERIFY", "ENROLLMENT");
    private static final Set<String> VALID_OUTCOMES = Set.of("FACE_SUCCESS", "FACE_REJECTED", "PIN_FALLBACK", "HCM_FALLBACK", "MISSED");

    public void validateCreateRequest(FaceAuthEventRequest request) {
        log.info("Validating face auth event create request");

        validateRequestInfo(request.getRequestInfo());

        List<FaceAuthEvent> events = request.getFaceAuthEvents();
        if (events == null || events.isEmpty()) {
            throw new CustomException("FACE_AUTH_EVENTS", "Face auth events array is mandatory");
        }

        // Verify all events belong to same tenantId
        validateSingleTenantId(events);

        Map<String, String> errorMap = new HashMap<>();
        for (FaceAuthEvent event : events) {
            if (StringUtils.isBlank(event.getTenantId())) {
                errorMap.put("FACE_AUTH.TENANTID", "TenantId is mandatory");
            }
            if (StringUtils.isBlank(event.getIndividualId())) {
                errorMap.put("FACE_AUTH.INDIVIDUALID", "IndividualId is mandatory");
            }
            if (StringUtils.isBlank(event.getEventType())) {
                errorMap.put("FACE_AUTH.EVENT_TYPE", "EventType is mandatory");
            } else if (!VALID_EVENT_TYPES.contains(event.getEventType())) {
                errorMap.put("FACE_AUTH.INVALID_EVENT_TYPE", "EventType must be one of: " + VALID_EVENT_TYPES);
            }
            if (StringUtils.isBlank(event.getOutcome())) {
                errorMap.put("FACE_AUTH.OUTCOME", "Outcome is mandatory");
            } else if (!VALID_OUTCOMES.contains(event.getOutcome())) {
                errorMap.put("FACE_AUTH.INVALID_OUTCOME", "Outcome must be one of: " + VALID_OUTCOMES);
            }
            if (event.getTimestamp() == null) {
                errorMap.put("FACE_AUTH.TIMESTAMP", "Timestamp is mandatory");
            }
        }

        if (!errorMap.isEmpty()) {
            log.error("Face auth event create request validation failed");
            throw new CustomException(errorMap);
        }

        log.info("Face auth event create request validated successfully");
    }

    public void validateSearchRequest(RequestInfoWrapper requestInfoWrapper, FaceAuthEventSearchCriteria searchCriteria) {
        log.info("Validating face auth event search request");

        if (searchCriteria == null || requestInfoWrapper == null) {
            throw new CustomException("FACE_AUTH_SEARCH_REQUEST", "Search criteria and request info are mandatory");
        }

        validateRequestInfo(requestInfoWrapper.getRequestInfo());

        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            throw new CustomException("TENANT_ID", "TenantId is mandatory for face auth event search");
        }

        log.info("Face auth event search request validated successfully");
    }

    private void validateSingleTenantId(List<FaceAuthEvent> events) {
        Set<String> tenantIds = new HashSet<>();
        for (FaceAuthEvent event : events) {
            if (StringUtils.isNotBlank(event.getTenantId())) {
                tenantIds.add(event.getTenantId());
            }
        }
        if (tenantIds.size() > 1) {
            throw new CustomException("MULTIPLE_TENANTIDS", "All face auth events must belong to the same tenantId");
        }
    }

    private void validateRequestInfo(RequestInfo requestInfo) {
        if (requestInfo == null) {
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }
    }
}
