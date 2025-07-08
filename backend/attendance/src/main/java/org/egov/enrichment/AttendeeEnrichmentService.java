package org.egov.enrichment;

import digit.models.coremodels.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.util.AttendanceServiceUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AttendeeEnrichmentService {

    private final AttendanceServiceUtil attendanceServiceUtil;

    @Autowired
    public AttendeeEnrichmentService(AttendanceServiceUtil attendanceServiceUtil) {
        this.attendanceServiceUtil = attendanceServiceUtil;
    }

    public void enrichAttendeeOnCreate(AttendeeCreateRequest attendeeCreateRequest) {
        RequestInfo requestInfo = attendeeCreateRequest.getRequestInfo();
        List<IndividualEntry> attendeeListFromRequest = attendeeCreateRequest.getAttendees();

        for (IndividualEntry attendee : attendeeListFromRequest) {
            AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), attendee.getAuditDetails(), true);
            attendee.setAuditDetails(auditDetails);
            attendee.setId(UUID.randomUUID().toString());
            attendee.setDenrollmentDate(null);
            if (attendee.getEnrollmentDate() == null) {
                BigDecimal enrollmentDate = new BigDecimal(System.currentTimeMillis());
                attendee.setEnrollmentDate(enrollmentDate);
            }

        }
    }

    /**
     * Enriches the attendees for tag update by preserving original fields from the database
     * and updating audit details.
     *
     * @param attendeeUpdateTagRequest The request containing attendees to be updated.
     * @param attendeesFromDB          The list of attendees fetched from the database.
     */
    public void enrichAttendeesForTagUpdate (AttendeeUpdateTagRequest attendeeUpdateTagRequest, List<IndividualEntry> attendeesFromDB) {

        RequestInfo requestInfo =  attendeeUpdateTagRequest.getRequestInfo();
        List<IndividualEntry> attendeesListFromRequest =  attendeeUpdateTagRequest.getAttendees();

        // Create a map for quick lookup of attendees by their UUID
        Map<String, IndividualEntry> uuidToAttendeeMap = attendeesFromDB.stream()
                .collect(Collectors.toMap(IndividualEntry::getId, Function.identity()));

        // Validate that all attendees in the request exist in the database
        for (IndividualEntry attendee : attendeesListFromRequest) {
            IndividualEntry dbAttendee = uuidToAttendeeMap.get(attendee.getId());

            if (dbAttendee == null) {
                throw new CustomException("INVALID_ID", "Attendee not found: " + attendee.getId());
            }

            // Preserve original fields from DB
            attendee.setRegisterId(dbAttendee.getRegisterId());
            attendee.setIndividualId(dbAttendee.getIndividualId());
            attendee.setTenantId(dbAttendee.getTenantId());
            attendee.setEnrollmentDate(dbAttendee.getEnrollmentDate());
            attendee.setDenrollmentDate(dbAttendee.getDenrollmentDate());
            attendee.setAdditionalDetails(dbAttendee.getAdditionalDetails());

            // Enrich audit details (lastModifiedBy and lastModifiedTime)
            AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(
                    requestInfo.getUserInfo().getUuid(),
                    dbAttendee.getAuditDetails(),
                    false // isCreate = false since this is an update
            );
            // Set the enriched audit details back to the attendee
            attendee.setAuditDetails(auditDetails);
        }
    }

    public void enrichAttendeeOnDelete(AttendeeDeleteRequest attendeeDeleteRequest, List<IndividualEntry> attendeesFromDB) {
        RequestInfo requestInfo = attendeeDeleteRequest.getRequestInfo();
        List<IndividualEntry> attendeesListFromRequest = attendeeDeleteRequest.getAttendees();

        for (IndividualEntry attendee : attendeesListFromRequest) {
            for (IndividualEntry attendeeFromDB : attendeesFromDB) {
                if (attendeeFromDB.getIndividualId().equals(attendee.getIndividualId())
                        && attendeeFromDB.getRegisterId().equals(attendee.getRegisterId())) {

                    attendee.setId(attendeeFromDB.getId());
                    attendee.setEnrollmentDate(attendeeFromDB.getEnrollmentDate());
                    attendee.setTag(attendeeFromDB.getTag());

                    AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), attendeeFromDB.getAuditDetails(), false);
                    attendee.setAuditDetails(auditDetails);


                    if (attendee.getDenrollmentDate() == null) {
                        BigDecimal deEnrollmentDate = new BigDecimal(System.currentTimeMillis());
                        attendee.setDenrollmentDate(deEnrollmentDate);
                    }
                }
            }
        }


    }
}
