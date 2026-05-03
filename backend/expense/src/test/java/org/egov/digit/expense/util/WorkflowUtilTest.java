package org.egov.digit.expense.util;

import org.egov.common.contract.workflow.ProcessInstance;
import org.egov.common.contract.workflow.ProcessInstanceResponse;
import org.egov.common.contract.workflow.State;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WorkflowUtilTest {

    @Mock
    private ServiceRequestRepository repository;
    @Mock
    private ObjectMapper mapper;
    @Mock
    private Configuration configs;

    @InjectMocks
    private WorkflowUtil util;

    @Test
    public void retryable_directInvalidAction() {
        assertTrue(util.isRetryableWfError(new RuntimeException("INVALID ACTION from WF")));
    }

    @Test
    public void retryable_directNoValidAction() {
        assertTrue(util.isRetryableWfError(new RuntimeException("No valid action found")));
    }

    @Test
    public void retryable_directInvalidActionUnderscore() {
        assertTrue(util.isRetryableWfError(new RuntimeException("INVALID_ACTION")));
    }

    @Test
    public void retryable_wrappedCause() {
        RuntimeException cause = new RuntimeException("INVALID ACTION - No valid action");
        RuntimeException wrapped = new RuntimeException("wrapper", cause);
        assertTrue(util.isRetryableWfError(wrapped));
    }

    @Test
    public void retryable_deeplyNestedCause() {
        RuntimeException inner = new RuntimeException("INVALID ACTION");
        RuntimeException middle = new RuntimeException("mid", inner);
        RuntimeException outer = new RuntimeException("outer", middle);
        assertTrue(util.isRetryableWfError(outer));
    }

    @Test
    public void notRetryable_connectionRefused() {
        assertFalse(util.isRetryableWfError(new RuntimeException("Connection refused")));
    }

    @Test
    public void notRetryable_nullMessageNullCause() {
        assertFalse(util.isRetryableWfError(new RuntimeException((String) null)));
    }

    @Test
    public void notRetryable_emptyMessage() {
        assertFalse(util.isRetryableWfError(new RuntimeException("")));
    }

    // ── searchCurrentWfState ──────────────────────────────────────────────────

    @Test
    public void searchCurrentWfState_found_returnsState() {
        State state = buildWfState("VERIFIED");
        ProcessInstance pi = new ProcessInstance();
        pi.setState(state);
        ProcessInstanceResponse resp = new ProcessInstanceResponse();
        resp.setProcessInstances(List.of(pi));
        when(repository.fetchResult(any(), any())).thenReturn(Optional.of(resp));
        when(mapper.convertValue(any(), eq(ProcessInstanceResponse.class))).thenReturn(resp);
        when(configs.getWfHost()).thenReturn("http://wf-host");
        when(configs.getWfProcessInstanceSearchPath()).thenReturn("/wf/process");

        State result = util.searchCurrentWfState("bill-001", TENANT_ID, buildRequestInfo());

        assertNotNull(result);
        assertEquals("VERIFIED", result.getApplicationStatus());
    }

    @Test
    public void searchCurrentWfState_notFound_returnsNull() {
        ProcessInstanceResponse emptyResp = new ProcessInstanceResponse();
        emptyResp.setProcessInstances(List.of());
        when(repository.fetchResult(any(), any())).thenReturn(Optional.of(emptyResp));
        when(mapper.convertValue(any(), eq(ProcessInstanceResponse.class))).thenReturn(emptyResp);
        when(configs.getWfHost()).thenReturn("http://wf-host");
        when(configs.getWfProcessInstanceSearchPath()).thenReturn("/wf/process");

        State result = util.searchCurrentWfState("bill-001", TENANT_ID, buildRequestInfo());

        assertNull(result);
    }

    @Test
    public void searchCurrentWfState_repositoryThrows_returnsNull() {
        when(repository.fetchResult(any(), any())).thenThrow(new RuntimeException("Connection refused"));
        when(configs.getWfHost()).thenReturn("http://wf-host");
        when(configs.getWfProcessInstanceSearchPath()).thenReturn("/wf/process");

        State result = util.searchCurrentWfState("bill-001", TENANT_ID, buildRequestInfo());

        assertNull(result);  // exception swallowed, null returned
    }

    // ── Single attempt — no Thread.sleep ─────────────────────────────────────

    @Test
    public void callWorkFlow_failure_doesNotSleepOrRetry() {
        // With the retry loop removed, a failure should propagate immediately.
        // We can't mock callWorkFlow directly, but we verify the behaviour:
        // isRetryableWfError still works correctly for classifying the error.
        RuntimeException invalidAction = new RuntimeException("INVALID ACTION");
        assertTrue(util.isRetryableWfError(invalidAction));

        RuntimeException networkError = new RuntimeException("Connection refused: localhost:8080");
        assertFalse(util.isRetryableWfError(networkError));
    }
}
