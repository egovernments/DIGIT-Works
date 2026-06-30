package org.egov.digit.expense.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.web.models.BillDetail;
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

import java.util.List;
import java.util.Optional;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillDetailCacheServiceTest {

    @Mock private RedisTemplate<String, String> redisTemplate;
    @Mock private ValueOperations<String, String> valueOps;

    private BillDetailCacheService cacheService;
    private ObjectMapper objectMapper;

    private static final String EXPECTED_DETAIL_KEY =
            TENANT_ID + ":bill:" + BILL_ID + ":billDetail:" + DETAIL_ID_1;
    private static final String EXPECTED_IDS_KEY =
            TENANT_ID + ":bill:" + BILL_ID + ":billDetailIds";

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        cacheService = new BillDetailCacheService(redisTemplate, objectMapper);
        ReflectionTestUtils.setField(cacheService, "detailTtlSeconds", 300L);
        ReflectionTestUtils.setField(cacheService, "detailIdsTtlSeconds", 3600L);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
    }

    // ── putDetail ──────────────────────────────────────────────────────────────

    @Test
    public void putDetail_validDetail_writesToRedisWithCorrectKeyAndTtl() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);

        cacheService.putDetail(detail, TENANT_ID);

        verify(valueOps).set(eq(EXPECTED_DETAIL_KEY), anyString(), eq(300L), any());
    }

    @Test
    public void putDetail_nullDetail_silentlySkips() {
        cacheService.putDetail(null, TENANT_ID);
        verify(valueOps, never()).set(any(), any(), anyLong(), any());
    }

    @Test
    public void putDetail_nullTenantId_silentlySkips() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        cacheService.putDetail(detail, null);
        verify(valueOps, never()).set(any(), any(), anyLong(), any());
    }

    @Test
    public void putDetail_redisError_doesNotThrow() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        doThrow(new RuntimeException("Redis down")).when(valueOps).set(any(), any(), anyLong(), any());
        assertDoesNotThrow(() -> cacheService.putDetail(detail, TENANT_ID));
    }

    // ── getDetail ──────────────────────────────────────────────────────────────

    @Test
    public void getDetail_hit_deserializesAndReturnsDetail() throws Exception {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        String json = objectMapper.writeValueAsString(detail);
        when(valueOps.get(EXPECTED_DETAIL_KEY)).thenReturn(json);

        Optional<BillDetail> result = cacheService.getDetail(BILL_ID, DETAIL_ID_1, TENANT_ID);

        assertTrue(result.isPresent());
        assertEquals(DETAIL_ID_1, result.get().getId());
    }

    @Test
    public void getDetail_miss_returnsEmpty() {
        when(valueOps.get(anyString())).thenReturn(null);
        assertTrue(cacheService.getDetail(BILL_ID, DETAIL_ID_1, TENANT_ID).isEmpty());
    }

    @Test
    public void getDetail_corruptJson_returnsEmpty() {
        when(valueOps.get(anyString())).thenReturn("NOT_VALID{{{");
        assertTrue(cacheService.getDetail(BILL_ID, DETAIL_ID_1, TENANT_ID).isEmpty());
    }

    // ── putDetailIds ───────────────────────────────────────────────────────────

    @Test
    public void putDetailIds_validIds_writesToRedisWithCorrectKeyAndTtl() {
        cacheService.putDetailIds(BILL_ID, TENANT_ID, List.of(DETAIL_ID_1, DETAIL_ID_2));
        verify(valueOps).set(eq(EXPECTED_IDS_KEY), anyString(), eq(3600L), any());
    }

    @Test
    public void putDetailIds_emptyList_stillWrites() {
        cacheService.putDetailIds(BILL_ID, TENANT_ID, List.of());
        verify(valueOps).set(eq(EXPECTED_IDS_KEY), anyString(), eq(3600L), any());
    }

    // ── getDetailIds ───────────────────────────────────────────────────────────

    @Test
    public void getDetailIds_hit_deserializesAndReturnsList() throws Exception {
        List<String> ids = List.of(DETAIL_ID_1, DETAIL_ID_2);
        when(valueOps.get(EXPECTED_IDS_KEY)).thenReturn(objectMapper.writeValueAsString(ids));

        Optional<List<String>> result = cacheService.getDetailIds(BILL_ID, TENANT_ID);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        assertTrue(result.get().contains(DETAIL_ID_1));
    }

    @Test
    public void getDetailIds_miss_returnsEmpty() {
        when(valueOps.get(anyString())).thenReturn(null);
        assertTrue(cacheService.getDetailIds(BILL_ID, TENANT_ID).isEmpty());
    }

    // helper
    private BillDetail buildDetail(String detailId, Status status) {
        return BillDetail.builder()
                .id(detailId)
                .tenantId(TENANT_ID)
                .billId(BILL_ID)
                .status(status)
                .build();
    }
}
