package org.egov.digit.expense.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.digit.expense.repository.querybuilder.BillDetailQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.BillDetailRowMapper;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.egov.digit.expense.config.Constants.INVALID_TENANT_ID_ERR_CODE;

@Repository
@Slf4j
public class BillDetailRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BillDetailQueryBuilder queryBuilder;
    private final BillDetailRowMapper rowMapper;
    private final MultiStateInstanceUtil multiStateInstanceUtil;

    @Autowired
    public BillDetailRepository(JdbcTemplate jdbcTemplate,
                                 BillDetailQueryBuilder queryBuilder,
                                 BillDetailRowMapper rowMapper,
                                 MultiStateInstanceUtil multiStateInstanceUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryBuilder = queryBuilder;
        this.rowMapper = rowMapper;
        this.multiStateInstanceUtil = multiStateInstanceUtil;
    }

    public List<BillDetail> searchByBillIds(List<String> billIds, String tenantId) {
        if (billIds == null || billIds.isEmpty()) return Collections.emptyList();
        List<Object> params = new ArrayList<>();
        String query = queryBuilder.getQueryByBillIds(billIds, tenantId, params);
        try {
            query = multiStateInstanceUtil.replaceSchemaPlaceholder(query, tenantId);
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
        List<BillDetail> result = jdbcTemplate.query(query, params.toArray(), rowMapper);
        log.debug("searchByBillIds: tenantId={} billIds={} found={}", tenantId, billIds, result != null ? result.size() : 0);
        return result != null ? result : Collections.emptyList();
    }

    public List<BillDetail> searchByDetailIds(List<String> detailIds, String tenantId) {
        if (detailIds == null || detailIds.isEmpty()) return Collections.emptyList();
        List<Object> params = new ArrayList<>();
        String query = queryBuilder.getQueryByDetailIds(detailIds, tenantId, params);
        try {
            query = multiStateInstanceUtil.replaceSchemaPlaceholder(query, tenantId);
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
        List<BillDetail> result = jdbcTemplate.query(query, params.toArray(), rowMapper);
        log.debug("searchByDetailIds: tenantId={} detailIds={} found={}", tenantId, detailIds, result != null ? result.size() : 0);
        return result != null ? result : Collections.emptyList();
    }
}
