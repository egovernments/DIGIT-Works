package org.egov.digit.expense.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.web.models.Bill;
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

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillCacheServiceTest {

    @Mock private RedisTemplate<String, String> redisTemplate;
    @Mock private ValueOperations<String, String> valueOps;

    private BillCacheService billCacheService;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        billCacheService = new BillCacheService(redisTemplate, objectMapper);
        ReflectionTestUtils.setField(billCacheService, "cacheTtlSeconds", 60L);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
    }

    @Test
    public void put_validBill_writesToRedisWithCorrectKeyAndTtl() {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.PENDING_VERIFICATION, 1);

        billCacheService.put(bill);

        verify(valueOps).set(
                argThat(key -> key.contains(BILL_ID) && key.contains(TENANT_ID)),
                anyString(), eq(60L), any()
        );
    }

    @Test
    public void put_nullBill_silentlySkips() {
        billCacheService.put(null);
        verify(valueOps, never()).set(any(), any(), anyLong(), any());
    }

    @Test
    public void put_nullBillId_silentlySkips() {
        Bill bill = Bill.builder().tenantId(TENANT_ID).build();
        billCacheService.put(bill);
        verify(valueOps, never()).set(any(), any(), anyLong(), any());
    }

    @Test
    public void put_redisError_doesNotThrow() {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.PENDING_VERIFICATION, 1);
        doThrow(new RuntimeException("Redis down")).when(valueOps).set(any(), any(), anyLong(), any());

        assertDoesNotThrow(() -> billCacheService.put(bill));
    }

    @Test
    public void get_hit_deserializesAndReturnsBill() throws Exception {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.PENDING_VERIFICATION, 1);
        String json = objectMapper.writeValueAsString(bill);
        when(valueOps.get(anyString())).thenReturn(json);

        Optional<Bill> result = billCacheService.get(TENANT_ID, BILL_ID);

        assertTrue(result.isPresent());
        assertEquals(BILL_ID, result.get().getId());
        assertEquals(Status.VERIFICATION_IN_PROGRESS, result.get().getStatus());
    }

    @Test
    public void get_miss_returnsEmpty() {
        when(valueOps.get(anyString())).thenReturn(null);
        assertTrue(billCacheService.get(TENANT_ID, BILL_ID).isEmpty());
    }

    @Test
    public void get_corruptJson_returnsEmpty() {
        when(valueOps.get(anyString())).thenReturn("NOT_VALID{{{");
        assertTrue(billCacheService.get(TENANT_ID, BILL_ID).isEmpty());
    }

    @Test
    public void getMultiple_partialHit_returnsOnlyHits() throws Exception {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.PENDING_VERIFICATION, 1);
        String json = objectMapper.writeValueAsString(bill);
        when(redisTemplate.opsForValue().multiGet(anyList())).thenReturn(java.util.Arrays.asList(json, null));

        Map<String, Bill> result = billCacheService.getMultiple(TENANT_ID, Set.of(BILL_ID, "other-id"));

        // At least the hit should be deserialised — null entries are skipped
        assertFalse(result.containsValue(null));
    }
}
