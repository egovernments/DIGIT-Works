package org.egov.digit.expense.service;

import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillDetailRepository;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillDetailMessage;
import org.egov.digit.expense.web.models.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillDetailServiceTest {

    @Mock private BillDetailRepository repository;
    @Mock private BillDetailCacheService cacheService;
    @Mock private ExpenseProducer producer;
    @Mock private Configuration config;

    private BillDetailService service;

    private static final String DETAIL_TOPIC_CREATE = "expense-bill-detail-create";
    private static final String DETAIL_TOPIC_UPDATE = "expense-bill-detail-update";

    @BeforeEach
    public void setUp() {
        service = new BillDetailService(repository, cacheService, producer, config);
        when(config.getBillDetailCreateTopic()).thenReturn(DETAIL_TOPIC_CREATE);
        when(config.getBillDetailUpdateTopic()).thenReturn(DETAIL_TOPIC_UPDATE);
    }

    private BillDetail buildDetail(String id, Status status) {
        return BillDetail.builder()
                .id(id)
                .tenantId(TENANT_ID)
                .billId(BILL_ID)
                .status(status)
                .build();
    }

    // ── create ────────────────────────────────────────────────────────────────

    @Test
    public void create_multipleDetails_pushesEachToCreateTopic() {
        BillDetail d1 = buildDetail(DETAIL_ID_1, Status.ACTIVE);
        BillDetail d2 = buildDetail(DETAIL_ID_2, Status.ACTIVE);

        service.create(List.of(d1, d2), TENANT_ID);

        verify(producer, times(2)).push(eq(TENANT_ID), eq(DETAIL_TOPIC_CREATE), any(BillDetailMessage.class));
    }

    @Test
    public void create_emptyList_noPush() {
        service.create(Collections.emptyList(), TENANT_ID);
        verify(producer, never()).push(any(), any(), any());
    }

    @Test
    public void create_null_noPush() {
        service.create(null, TENANT_ID);
        verify(producer, never()).push(any(), any(), any());
    }

    @Test
    public void create_singleDetail_messageHasCreateAction() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.ACTIVE);
        ArgumentCaptor<BillDetailMessage> captor = ArgumentCaptor.forClass(BillDetailMessage.class);

        service.create(List.of(detail), TENANT_ID);

        verify(producer).push(eq(TENANT_ID), eq(DETAIL_TOPIC_CREATE), captor.capture());
        assertEquals("CREATE", captor.getValue().getAction());
        assertEquals(DETAIL_ID_1, captor.getValue().getBillDetail().getId());
    }

    // ── update ────────────────────────────────────────────────────────────────

    @Test
    public void update_multipleDetails_cachesAndPushesEach() {
        BillDetail d1 = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        BillDetail d2 = buildDetail(DETAIL_ID_2, Status.VERIFICATION_FAILED);

        service.update(List.of(d1, d2), TENANT_ID);

        verify(cacheService, times(2)).putDetail(any(BillDetail.class), eq(TENANT_ID));
        verify(producer, times(2)).push(eq(TENANT_ID), eq(DETAIL_TOPIC_UPDATE), any(BillDetailMessage.class));
    }

    @Test
    public void update_singleDetail_messageHasUpdateAction() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        ArgumentCaptor<BillDetailMessage> captor = ArgumentCaptor.forClass(BillDetailMessage.class);

        service.update(List.of(detail), TENANT_ID);

        verify(producer).push(eq(TENANT_ID), eq(DETAIL_TOPIC_UPDATE), captor.capture());
        assertEquals("UPDATE", captor.getValue().getAction());
    }

    // ── searchByBillIds — DB fallback ─────────────────────────────────────────

    @Test
    public void searchByBillIds_cacheMiss_fetchesFromDb_populatesCache() {
        BillDetail d1 = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        when(cacheService.getDetailIds(BILL_ID, TENANT_ID)).thenReturn(Optional.empty());
        when(repository.searchByBillIds(anyList(), eq(TENANT_ID))).thenReturn(List.of(d1));

        List<BillDetail> result = service.searchByBillIds(List.of(BILL_ID), TENANT_ID);

        assertFalse(result.isEmpty());
        assertEquals(DETAIL_ID_1, result.get(0).getId());
        verify(cacheService).putDetail(d1, TENANT_ID);
        verify(cacheService).putDetailIds(eq(BILL_ID), eq(TENANT_ID), anyList());
    }

    @Test
    public void searchByBillIds_cacheHitAllDetails_doesNotHitDb() {
        BillDetail d1 = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        when(cacheService.getDetailIds(BILL_ID, TENANT_ID)).thenReturn(Optional.of(List.of(DETAIL_ID_1)));
        when(cacheService.getDetail(BILL_ID, DETAIL_ID_1, TENANT_ID)).thenReturn(Optional.of(d1));

        List<BillDetail> result = service.searchByBillIds(List.of(BILL_ID), TENANT_ID);

        assertEquals(1, result.size());
        assertEquals(DETAIL_ID_1, result.get(0).getId());
        verify(repository, never()).searchByBillIds(any(), any());
    }

    @Test
    public void searchByBillIds_partialCacheHit_fetchesMissingFromDb() {
        BillDetail d2 = buildDetail(DETAIL_ID_2, Status.PENDING_VERIFICATION);
        when(cacheService.getDetailIds(BILL_ID, TENANT_ID))
                .thenReturn(Optional.of(List.of(DETAIL_ID_1, DETAIL_ID_2)));
        when(cacheService.getDetail(BILL_ID, DETAIL_ID_1, TENANT_ID)).thenReturn(Optional.empty());
        when(cacheService.getDetail(BILL_ID, DETAIL_ID_2, TENANT_ID)).thenReturn(Optional.of(d2));
        when(repository.searchByDetailIds(anyList(), eq(TENANT_ID))).thenReturn(List.of(
                buildDetail(DETAIL_ID_1, Status.VERIFIED)));

        List<BillDetail> result = service.searchByBillIds(List.of(BILL_ID), TENANT_ID);

        assertEquals(2, result.size());
        verify(repository).searchByDetailIds(List.of(DETAIL_ID_1), TENANT_ID);
    }

    @Test
    public void searchByBillIds_emptyList_returnsEmpty() {
        assertTrue(service.searchByBillIds(Collections.emptyList(), TENANT_ID).isEmpty());
        verify(repository, never()).searchByBillIds(any(), any());
    }

    // ── enrichBillsWithDetails ────────────────────────────────────────────────

    @Test
    public void enrichBillsWithDetails_enrichesBillWithDetails() {
        Bill bill = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 0);
        BillDetail d1 = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        when(cacheService.getDetailIds(BILL_ID, TENANT_ID)).thenReturn(Optional.empty());
        when(repository.searchByBillIds(anyList(), eq(TENANT_ID))).thenReturn(List.of(d1));

        service.enrichBillsWithDetails(List.of(bill), TENANT_ID);

        assertNotNull(bill.getBillDetails());
        assertEquals(1, bill.getBillDetails().size());
        assertEquals(DETAIL_ID_1, bill.getBillDetails().get(0).getId());
    }
}
