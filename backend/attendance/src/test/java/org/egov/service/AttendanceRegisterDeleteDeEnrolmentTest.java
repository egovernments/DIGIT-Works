package org.egov.service;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.producer.Producer;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.enrichment.AttendeeEnrichmentService;
import org.egov.enrichment.RegisterEnrichment;
import org.egov.enrichment.StaffEnrichmentService;
import org.egov.helper.AuditDetailsTestBuilder;
import org.egov.helper.RequestInfoTestBuilder;
import org.egov.repository.AttendeeRepository;
import org.egov.repository.RegisterRepository;
import org.egov.repository.StaffRepository;
import org.egov.util.IndividualServiceUtil;
import org.egov.util.MusterRollWorkflowUtil;
import org.egov.util.ProjectServiceUtil;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.AttendanceServiceValidator;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterDeleteRequest;
import org.egov.web.models.AttendeeDeleteRequest;
import org.egov.web.models.IndividualEntry;
import org.egov.web.models.StaffPermission;
import org.egov.web.models.StaffPermissionRequest;
import org.egov.web.models.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * An enrolment in a register that no longer exists still blocks that person from being enrolled
 * anywhere else, so deleting a register has to end the enrolments it holds.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AttendanceRegisterDeleteDeEnrolmentTest {

    private static final String TENANT_ID = "dev";
    private static final String REGISTER_ID = "97ed7da3-753e-426a-b0b0-95dd61029785";
    private static final String DELETE_TOPIC = "delete-attendance-register";
    private static final String UPDATE_ATTENDEE_TOPIC = "update-attendee";
    private static final String UPDATE_STAFF_TOPIC = "update-staff";

    @Mock private AttendanceServiceValidator attendanceServiceValidator;
    @Mock private ResponseInfoFactory responseInfoFactory;
    @Mock private Producer producer;
    @Mock private AttendanceServiceConfiguration attendanceServiceConfiguration;
    @Mock private RegisterEnrichment registerEnrichment;
    @Mock private StaffRepository staffRepository;
    @Mock private RegisterRepository registerRepository;
    @Mock private AttendeeRepository attendeeRepository;
    @Mock private StaffEnrichmentService staffEnrichmentService;
    @Mock private IndividualServiceUtil individualServiceUtil;
    @Mock private ProjectServiceUtil projectServiceUtil;
    @Mock private RegisterPeriodEnrichmentService registerPeriodEnrichmentService;
    @Mock private MusterRollWorkflowUtil musterRollWorkflowUtil;
    @Mock private AttendeeEnrichmentService attendeeEnrichmentService;

    @Spy
    @InjectMocks
    private AttendanceRegisterService attendanceRegisterService;

    private RequestInfo requestInfo;

    @BeforeEach
    void setUp() {
        requestInfo = RequestInfoTestBuilder.builder().withCompleteRequestInfo().build();
        when(attendanceServiceConfiguration.getDeleteAttendanceRegisterTopic()).thenReturn(DELETE_TOPIC);
        when(attendanceServiceConfiguration.getUpdateAttendeeTopic()).thenReturn(UPDATE_ATTENDEE_TOPIC);
        when(attendanceServiceConfiguration.getUpdateStaffTopic()).thenReturn(UPDATE_STAFF_TOPIC);

        AttendanceRegister activeRegister = AttendanceRegister.builder()
                .id(REGISTER_ID).tenantId(TENANT_ID).status(Status.ACTIVE)
                .auditDetails(AuditDetailsTestBuilder.builder().withAuditDetails().build()).build();
        doReturn(Collections.singletonList(activeRegister))
                .when(attendanceRegisterService).getAttendanceRegisters(any(), any(), any());
    }

    private AttendanceRegisterDeleteRequest deleteRequest() {
        return AttendanceRegisterDeleteRequest.builder()
                .requestInfo(requestInfo)
                .attendanceRegister(Collections.singletonList(AttendanceRegister.builder()
                        .id(REGISTER_ID).tenantId(TENANT_ID).build()))
                .build();
    }

    private IndividualEntry attendee(String individualId, BigDecimal denrollmentDate) {
        return IndividualEntry.builder()
                .id("attendee-" + individualId)
                .tenantId(TENANT_ID)
                .registerId(REGISTER_ID)
                .individualId(individualId)
                .enrollmentDate(new BigDecimal("1755000000000"))
                .denrollmentDate(denrollmentDate)
                .build();
    }

    @Test
    @DisplayName("de-enrols the attendees still enrolled in a deleted register")
    void deEnrolsAttendeesOfDeletedRegister() {
        when(attendeeRepository.getAttendees(eq(TENANT_ID), any()))
                .thenReturn(List.of(attendee("ind-1", null)));
        when(staffRepository.getActiveStaff(any())).thenReturn(Collections.emptyList());

        attendanceRegisterService.deleteAttendanceRegister(deleteRequest());

        ArgumentCaptor<Object> payload = ArgumentCaptor.forClass(Object.class);
        verify(producer).push(eq(TENANT_ID), eq(UPDATE_ATTENDEE_TOPIC), payload.capture());
        AttendeeDeleteRequest pushed = (AttendeeDeleteRequest) payload.getValue();
        assertEquals(1, pushed.getAttendees().size());
        assertEquals("ind-1", pushed.getAttendees().get(0).getIndividualId());
        assertEquals(REGISTER_ID, pushed.getAttendees().get(0).getRegisterId());
        // The date and audit stamping is the same enrichment the attendee delete API uses
        verify(attendeeEnrichmentService).enrichAttendeeOnDelete(eq(pushed), any());
    }

    @Test
    @DisplayName("leaves an already de-enrolled attendee alone")
    void skipsAttendeesAlreadyDeEnrolled() {
        when(attendeeRepository.getAttendees(eq(TENANT_ID), any())).thenReturn(List.of(
                attendee("ind-1", new BigDecimal("1755100000000")),
                attendee("ind-2", BigDecimal.ZERO),
                attendee("ind-3", null)));
        when(staffRepository.getActiveStaff(any())).thenReturn(Collections.emptyList());

        attendanceRegisterService.deleteAttendanceRegister(deleteRequest());

        ArgumentCaptor<Object> payload = ArgumentCaptor.forClass(Object.class);
        verify(producer).push(eq(TENANT_ID), eq(UPDATE_ATTENDEE_TOPIC), payload.capture());
        List<IndividualEntry> pushed = ((AttendeeDeleteRequest) payload.getValue()).getAttendees();
        // A zero date means the same as no date, so ind-2 is still enrolled and ind-1 is not
        assertEquals(2, pushed.size());
        assertTrue(pushed.stream().anyMatch(a -> "ind-2".equals(a.getIndividualId())));
        assertTrue(pushed.stream().anyMatch(a -> "ind-3".equals(a.getIndividualId())));
    }

    @Test
    @DisplayName("de-enrols the staff of a deleted register too")
    void deEnrolsStaffOfDeletedRegister() {
        when(attendeeRepository.getAttendees(eq(TENANT_ID), any())).thenReturn(Collections.emptyList());
        when(staffRepository.getActiveStaff(any())).thenReturn(List.of(StaffPermission.builder()
                .id("staff-1").tenantId(TENANT_ID).registerId(REGISTER_ID).userId("usr-1").build()));

        attendanceRegisterService.deleteAttendanceRegister(deleteRequest());

        ArgumentCaptor<Object> payload = ArgumentCaptor.forClass(Object.class);
        verify(producer).push(eq(TENANT_ID), eq(UPDATE_STAFF_TOPIC), payload.capture());
        StaffPermissionRequest pushed = (StaffPermissionRequest) payload.getValue();
        assertEquals(1, pushed.getStaff().size());
        assertEquals("usr-1", pushed.getStaff().get(0).getUserId());
        verify(staffEnrichmentService).enrichStaffPermissionOnDelete(eq(pushed), any());
    }

    @Test
    @DisplayName("still deletes a register nobody is enrolled in")
    void pushesNothingWhenTheRegisterIsEmpty() {
        when(attendeeRepository.getAttendees(eq(TENANT_ID), any())).thenReturn(Collections.emptyList());
        when(staffRepository.getActiveStaff(any())).thenReturn(Collections.emptyList());

        attendanceRegisterService.deleteAttendanceRegister(deleteRequest());

        verify(producer).push(eq(TENANT_ID), eq(DELETE_TOPIC), any());
        verify(producer, never()).push(eq(TENANT_ID), eq(UPDATE_ATTENDEE_TOPIC), any());
        verify(producer, never()).push(eq(TENANT_ID), eq(UPDATE_STAFF_TOPIC), any());
    }
}
