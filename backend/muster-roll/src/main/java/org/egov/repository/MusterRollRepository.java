package org.egov.repository;

import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.repository.querybuilder.MusterRollQueryBuilder;
import org.egov.repository.rowmapper.MusterRollRowMapper;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.MusterRoll;
import org.egov.web.models.MusterRollSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.egov.util.MusterRollServiceConstants.INVALID_TENANT_ID_ERR_CODE;

@Repository
public class MusterRollRepository {

    private final MusterRollRowMapper rowMapper;

    private final MusterRollQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;

    private final MultiStateInstanceUtil multiStateInstanceUtil;

    @Autowired
    public MusterRollRepository(MusterRollRowMapper rowMapper, MusterRollQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate, MultiStateInstanceUtil multiStateInstanceUtil) {
        this.rowMapper = rowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
        this.multiStateInstanceUtil = multiStateInstanceUtil;
    }


    /**
     * Fetch the record from DB based on the search criteria
     * @param searchCriteria
     * @return
     */
    public List<MusterRoll> getMusterRoll(MusterRollSearchCriteria searchCriteria,List<String> registerIds) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getMusterSearchQuery(searchCriteria, preparedStmtList, registerIds,false);
        try {
            // Applies schema replacement to the query string based on tenant ID
            query = multiStateInstanceUtil.replaceSchemaPlaceholder(query, searchCriteria.getTenantId());
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
        return jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
    }

    /**
     * Fetch the record count from DB based on the search criteria
     * @param searchCriteria
     * @return
     */
    public Integer getMusterRollCount(MusterRollSearchCriteria searchCriteria,List<String> registerIds) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getSearchCountQueryString(searchCriteria, preparedStmtList, registerIds);
        try {
            // Applies schema replacement to the query string based on tenant ID
            query = multiStateInstanceUtil.replaceSchemaPlaceholder(query, searchCriteria.getTenantId());
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
        return jdbcTemplate.queryForObject(query, preparedStmtList.toArray(), Integer.class);
    }
}
