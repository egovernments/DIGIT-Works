package org.egov.digit.expense.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.digit.expense.repository.querybuilder.BillApprovalQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.BillApprovalRowMapper;
import org.egov.digit.expense.web.models.BillApproval;
import org.egov.digit.expense.web.models.BillApprovalSearchCriteria;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.egov.digit.expense.config.Constants.INVALID_TENANT_ID_ERR_CODE;

@Repository
@Slf4j
public class BillApprovalRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BillApprovalQueryBuilder queryBuilder;
    private final BillApprovalRowMapper rowMapper;
    private final MultiStateInstanceUtil multiStateInstanceUtil;

    @Autowired
    public BillApprovalRepository(JdbcTemplate jdbcTemplate,
                                  BillApprovalQueryBuilder queryBuilder,
                                  BillApprovalRowMapper rowMapper,
                                  MultiStateInstanceUtil multiStateInstanceUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryBuilder = queryBuilder;
        this.rowMapper = rowMapper;
        this.multiStateInstanceUtil = multiStateInstanceUtil;
    }

    public List<BillApproval> search(BillApprovalSearchCriteria criteria) {
        log.info("BillApprovalRepository::search");
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = queryBuilder.getSearchQuery(criteria, preparedStatementValues);
        try {
            query = multiStateInstanceUtil.replaceSchemaPlaceholder(query, criteria.getTenantId());
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
        log.debug("Search query: {}", query);
        return jdbcTemplate.query(query, preparedStatementValues.toArray(), rowMapper);
    }
}
