package org.egov.digit.expense.repository;

import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.digit.expense.repository.querybuilder.BillDetailQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.BillDetailRowMapper;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.List;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillDetailRepositoryTest {

    @Mock private JdbcTemplate jdbcTemplate;
    @Mock private BillDetailQueryBuilder queryBuilder;
    @Mock private BillDetailRowMapper rowMapper;
    @Mock private MultiStateInstanceUtil multiStateInstanceUtil;

    private BillDetailRepository repository;

    @BeforeEach
    public void setUp() throws Exception {
        repository = new BillDetailRepository(jdbcTemplate, queryBuilder, rowMapper, multiStateInstanceUtil);
        when(multiStateInstanceUtil.replaceSchemaPlaceholder(anyString(), anyString()))
                .thenAnswer(inv -> inv.getArgument(0));
    }

    private BillDetail buildDetail(String id) {
        return BillDetail.builder()
                .id(id)
                .tenantId(TENANT_ID)
                .billId(BILL_ID)
                .status(Status.PENDING_VERIFICATION)
                .build();
    }

    // ── searchByBillIds ───────────────────────────────────────────────────────

    @Test
    public void searchByBillIds_returnsDetails() {
        BillDetail d = buildDetail(DETAIL_ID_1);
        when(queryBuilder.getQueryByBillIds(anyList(), eq(TENANT_ID), anyList()))
                .thenReturn("SELECT * FROM eg_expense_billdetail WHERE billid IN (?)");
        when(jdbcTemplate.query(anyString(), any(Object[].class), eq(rowMapper)))
                .thenReturn(List.of(d));

        List<BillDetail> result = repository.searchByBillIds(List.of(BILL_ID), TENANT_ID);

        assertEquals(1, result.size());
        assertEquals(DETAIL_ID_1, result.get(0).getId());
    }

    @Test
    public void searchByBillIds_emptyList_returnsEmpty_noDbCall() {
        List<BillDetail> result = repository.searchByBillIds(Collections.emptyList(), TENANT_ID);
        assertTrue(result.isEmpty());
        verify(jdbcTemplate, never()).query(anyString(), any(Object[].class), eq(rowMapper));
    }

    @Test
    public void searchByBillIds_nullList_returnsEmpty_noDbCall() {
        List<BillDetail> result = repository.searchByBillIds(null, TENANT_ID);
        assertTrue(result.isEmpty());
        verify(jdbcTemplate, never()).query(anyString(), any(Object[].class), eq(rowMapper));
    }

    @Test
    public void searchByBillIds_nullResult_returnsEmpty() {
        when(queryBuilder.getQueryByBillIds(anyList(), eq(TENANT_ID), anyList())).thenReturn("SELECT 1");
        when(jdbcTemplate.query(anyString(), any(Object[].class), eq(rowMapper))).thenReturn(null);

        List<BillDetail> result = repository.searchByBillIds(List.of(BILL_ID), TENANT_ID);
        assertTrue(result.isEmpty());
    }

    // ── searchByDetailIds ─────────────────────────────────────────────────────

    @Test
    public void searchByDetailIds_returnsDetails() {
        BillDetail d = buildDetail(DETAIL_ID_1);
        when(queryBuilder.getQueryByDetailIds(anyList(), eq(TENANT_ID), anyList()))
                .thenReturn("SELECT * FROM eg_expense_billdetail WHERE id IN (?)");
        when(jdbcTemplate.query(anyString(), any(Object[].class), eq(rowMapper)))
                .thenReturn(List.of(d));

        List<BillDetail> result = repository.searchByDetailIds(List.of(DETAIL_ID_1), TENANT_ID);

        assertEquals(1, result.size());
        assertEquals(DETAIL_ID_1, result.get(0).getId());
    }

    @Test
    public void searchByDetailIds_emptyList_returnsEmpty_noDbCall() {
        List<BillDetail> result = repository.searchByDetailIds(Collections.emptyList(), TENANT_ID);
        assertTrue(result.isEmpty());
        verify(jdbcTemplate, never()).query(anyString(), any(Object[].class), eq(rowMapper));
    }
}
