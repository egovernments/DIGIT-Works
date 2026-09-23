package org.egov.repository.querybuilder;

import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.web.models.StaffSearchCriteria;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StaffQueryBuilderTest {

    private static final String INDIVIDUAL_ID = "b46a0b3b-32cd-4ce1-bd5d-7ed625b2d6cb";
    private static final String REGISTER_ID = "261e8359-376b-4627-80a3-28b487d89f68";
    private static final String TENANT_ID = "bo";

    private final StaffQueryBuilder queryBuilder =
            new StaffQueryBuilder(new MultiStateInstanceUtil(1, true, 0));

    private StaffSearchCriteria criteria() {
        return StaffSearchCriteria.builder()
                .individualIds(Collections.singletonList(INDIVIDUAL_ID))
                .registerIds(Collections.singletonList(REGISTER_ID))
                .tenantId(TENANT_ID)
                .build();
    }

    @Test
    void activeStaffQueryTreatsDeEnrolmentAsPointInTime() throws InvalidTenantIdException {
        List<Object> params = new ArrayList<>();

        String query = queryBuilder.getActiveAttendanceStaffSearchQuery(criteria(), params);

        assertTrue(query.contains("stf.deenrollment_date is null OR stf.deenrollment_date > ?"),
                "active staff must include future-dated de-enrolments, query was: " + query);
        assertFalse(query.matches(".*deenrollment_date is null\\s*$"),
                "bare null check would lock out staff with a scheduled de-enrolment");
    }

    @Test
    void activeStaffQueryBindsCurrentTimeAsLastParameter() throws InvalidTenantIdException {
        List<Object> params = new ArrayList<>();
        long before = System.currentTimeMillis();

        queryBuilder.getActiveAttendanceStaffSearchQuery(criteria(), params);

        long after = System.currentTimeMillis();
        Object bound = params.get(params.size() - 1);

        assertTrue(bound instanceof Long, "de-enrolment cutoff must be bound as epoch millis");
        long cutoff = (Long) bound;
        assertTrue(cutoff >= before && cutoff <= after,
                "cutoff " + cutoff + " must be the current time in [" + before + ", " + after + "]");
    }

    @Test
    void activeStaffQueryKeepsExistingPredicatesAndSchema() throws InvalidTenantIdException {
        List<Object> params = new ArrayList<>();

        String query = queryBuilder.getActiveAttendanceStaffSearchQuery(criteria(), params);

        assertTrue(query.contains("bo.eg_wms_attendance_staff"), query);
        assertTrue(query.contains("stf.individual_id IN"), query);
        assertTrue(query.contains("stf.register_id IN"), query);
        assertTrue(query.contains("stf.tenantid IN"), query);
        assertTrue(query.contains("status = ? AND isdeleted = ?"), query);
        assertEquals(INDIVIDUAL_ID, params.get(0));
        assertEquals(REGISTER_ID, params.get(1));
        assertEquals(TENANT_ID, params.get(2));
        assertEquals("ACTIVE", params.get(3));
        assertEquals(false, params.get(4));
    }
}
