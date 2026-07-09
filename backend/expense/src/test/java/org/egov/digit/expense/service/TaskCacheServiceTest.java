package org.egov.digit.expense.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.web.models.Task;
import org.egov.digit.expense.web.models.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TaskCacheServiceTest {

    @Mock private RedisTemplate<String, String> redisTemplate;
    @Mock private ValueOperations<String, String> valueOps;

    private TaskCacheService taskCacheService;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        taskCacheService = new TaskCacheService(redisTemplate, objectMapper);
        ReflectionTestUtils.setField(taskCacheService, "cacheTtlSeconds", 120L);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
    }

    @Test
    public void put_validTask_writesToRedisWithTtl() throws Exception {
        Task task = buildTask(DETAIL_ID_1, Task.Type.Verify, Status.IN_PROGRESS);

        taskCacheService.put(task);

        verify(valueOps).set(
                argThat(key -> key.contains(DETAIL_ID_1) && key.contains("Verify")),
                anyString(),
                eq(120L),
                any()
        );
    }

    @Test
    public void put_nullTask_silentlySkips() {
        taskCacheService.put(null);
        verify(valueOps, never()).set(any(), any(), anyLong(), any());
    }

    @Test
    public void put_missingBillDetailId_silentlySkips() {
        Task task = Task.builder().tenantId(TENANT_ID).type(Task.Type.Verify).status(Status.IN_PROGRESS).build();
        taskCacheService.put(task);
        verify(valueOps, never()).set(any(), any(), anyLong(), any());
    }

    @Test
    public void put_redisThrows_doesNotPropagateException() {
        Task task = buildTask(DETAIL_ID_1, Task.Type.Verify, Status.IN_PROGRESS);
        doThrow(new RuntimeException("Redis unavailable")).when(valueOps).set(any(), any(), anyLong(), any());

        assertDoesNotThrow(() -> taskCacheService.put(task));
    }

    @Test
    public void get_hit_deserializesAndReturnsTask() throws Exception {
        Task task = buildTask(DETAIL_ID_1, Task.Type.Verify, Status.IN_PROGRESS);
        String json = objectMapper.writeValueAsString(task);
        when(valueOps.get(anyString())).thenReturn(json);

        Optional<Task> result = taskCacheService.get(TENANT_ID, DETAIL_ID_1, Task.Type.Verify);

        assertTrue(result.isPresent());
        assertEquals(DETAIL_ID_1, result.get().getBillDetailId());
    }

    @Test
    public void get_miss_returnsEmpty() {
        when(valueOps.get(anyString())).thenReturn(null);

        Optional<Task> result = taskCacheService.get(TENANT_ID, DETAIL_ID_1, Task.Type.Verify);

        assertTrue(result.isEmpty());
    }

    @Test
    public void get_corruptJson_returnsEmpty() {
        when(valueOps.get(anyString())).thenReturn("NOT_VALID_JSON{{{");

        Optional<Task> result = taskCacheService.get(TENANT_ID, DETAIL_ID_1, Task.Type.Verify);

        assertTrue(result.isEmpty());
    }

    @Test
    public void get_nullParams_returnsEmpty() {
        assertTrue(taskCacheService.get(null, DETAIL_ID_1, Task.Type.Verify).isEmpty());
        assertTrue(taskCacheService.get(TENANT_ID, null, Task.Type.Verify).isEmpty());
        assertTrue(taskCacheService.get(TENANT_ID, DETAIL_ID_1, null).isEmpty());
    }

    @Test
    public void evict_callsRedisDelete() {
        taskCacheService.evict(TENANT_ID, DETAIL_ID_1, Task.Type.Verify);
        verify(redisTemplate).delete(argThat((String key) -> key.contains(DETAIL_ID_1)));
    }

    @Test
    public void put_transferTask_usesCorrectKeyType() {
        Task task = buildTask(DETAIL_ID_1, Task.Type.Transfer, Status.IN_PROGRESS);

        taskCacheService.put(task);

        verify(valueOps).set(
                argThat(key -> key.contains("Transfer") && key.contains(DETAIL_ID_1)),
                anyString(), anyLong(), any()
        );
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private Task buildTask(String billDetailId, Task.Type type, Status status) {
        return Task.builder()
                .id(TASK_ID)
                .tenantId(TENANT_ID)
                .billId(BILL_ID)
                .billDetailId(billDetailId)
                .type(type)
                .status(status)
                .auditDetails(buildAuditDetails())
                .build();
    }
}
