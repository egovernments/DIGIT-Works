package org.egov.repository;


import lombok.extern.slf4j.Slf4j;
import org.egov.repository.rowmapper.EstimateQueryBuilder;
import org.egov.repository.rowmapper.EstimateRowMapper;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class EstimateRepository {

    @Autowired
    private EstimateRowMapper rowMapper;

    @Autowired
    private EstimateQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * Get all the estimate list based on the given search criteria (using dynamic
     * query build at the run time)
     * @param searchCriteria
     * @return
     */
    public List<Estimate> getEstimate(EstimateSearchCriteria searchCriteria) {
        log.info("EstimateRepository::getEstimate");
        List<Object> preparedStmtList = new ArrayList<>();
        if (searchCriteria.getIsCountNeeded() == null) {
            searchCriteria.setIsCountNeeded(Boolean.FALSE);
        }
        String query = queryBuilder.getEstimateQuery(searchCriteria, preparedStmtList);
        List<Estimate> estimateList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return estimateList;
    }

    /**
     * Get the count of estimates based on the given search criteria (using dynamic
     * query build at the run time)
     * @param criteria
     * @return
     */
    public Integer getEstimateCount(EstimateSearchCriteria criteria) {
        log.info("EstimateRepository::getEstimateCount");
        List<Object> preparedStatement = new ArrayList<>();
        String query = queryBuilder.getSearchCountQueryString(criteria, preparedStatement);

        if (query == null)
            return 0;

        Integer count = jdbcTemplate.queryForObject(query, preparedStatement.toArray(), Integer.class);
        return count;
    }

}
