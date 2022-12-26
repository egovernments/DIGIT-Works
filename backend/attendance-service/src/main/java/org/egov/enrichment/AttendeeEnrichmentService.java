package org.egov.enrichment;

import digit.models.coremodels.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.util.AttendanceServiceUtil;
import org.egov.web.models.AttendeeCreateRequest;
import org.egov.web.models.AttendeeDeleteRequest;
import org.egov.web.models.IndividualEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AttendeeEnrichmentService {

    @Autowired
    private AttendanceServiceUtil attendanceServiceUtil;

    public void enrichCreateAttendee(AttendeeCreateRequest attendeeCreateRequest, List<IndividualEntry> attendeeListFromDB) {
        RequestInfo requestInfo = attendeeCreateRequest.getRequestInfo();
        List<IndividualEntry> attendeeListFromRequest = attendeeCreateRequest.getAttendees();

        for (IndividualEntry attendeeFromRequest : attendeeListFromRequest) {
            AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), attendeeFromRequest.getAuditDetails(), true);
            attendeeFromRequest.setAuditDetails(auditDetails);
            attendeeFromRequest.setId(UUID.randomUUID().toString());
            attendeeFromRequest.setDenrollmentDate(null);
            if (attendeeFromRequest.getEnrollmentDate() == null) {
                BigDecimal enrollmentDate = new BigDecimal(System.currentTimeMillis());
                attendeeFromRequest.setEnrollmentDate(enrollmentDate);
            }

        }
    }

    public void enrichDeleteAttendee(AttendeeDeleteRequest attendeeDeleteRequest, List<IndividualEntry> attendeesFromDB) {
        RequestInfo requestInfo = attendeeDeleteRequest.getRequestInfo();
        List<IndividualEntry> attendeesListFromRequest = attendeeDeleteRequest.getAttendees();

        for (IndividualEntry attendeeFromRequest : attendeesListFromRequest) {
            for (IndividualEntry attendeeFromDB : attendeesFromDB) {
                if (attendeeFromDB.getIndividualId().equals(attendeeFromRequest.getIndividualId())
                        && attendeeFromDB.getRegisterId().equals(attendeeFromRequest.getRegisterId())) {

                    attendeeFromRequest.setId(attendeeFromDB.getId());
                    attendeeFromRequest.setEnrollmentDate(attendeeFromDB.getEnrollmentDate());

                    AuditDetails auditDetails = attendanceServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), attendeeFromDB.getAuditDetails(), false);
                    attendeeFromRequest.setAuditDetails(auditDetails);


                    if (attendeeFromRequest.getDenrollmentDate() == null) {
                        BigDecimal deEnrollmentDate = new BigDecimal(System.currentTimeMillis());
                        attendeeFromRequest.setDenrollmentDate(deEnrollmentDate);
                    }
                }
            }
        }


    }
}
