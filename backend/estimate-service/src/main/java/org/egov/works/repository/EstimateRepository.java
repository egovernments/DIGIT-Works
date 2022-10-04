package org.egov.works.repository;


import org.egov.works.repository.rowmapper.EstimateQueryBuilder;
import org.egov.works.repository.rowmapper.EstimateRowMapper;
import org.egov.works.web.models.Estimate;
import org.egov.works.web.models.EstimateSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EstimateRepository {

    private EstimateRowMapper rowMapper;
    private EstimateQueryBuilder queryBuilder;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EstimateRepository(EstimateRowMapper rowMapper, EstimateQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate) {
        this.rowMapper = rowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;

    }

    /**
     * @param searchCriteria
     * @return
     */
    public List<Estimate> getEstimate(EstimateSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getEstimateQuery(searchCriteria, preparedStmtList);
        List<Estimate> estimateList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return estimateList;
    }
}
