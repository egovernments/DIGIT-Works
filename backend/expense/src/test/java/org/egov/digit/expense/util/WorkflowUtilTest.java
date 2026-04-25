package org.egov.digit.expense.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
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
}
