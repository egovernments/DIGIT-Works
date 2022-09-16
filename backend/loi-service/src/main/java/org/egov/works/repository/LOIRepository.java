package org.egov.works.repository;


import org.egov.works.repository.rowmapper.LOIQueryBuilder;
import org.egov.works.repository.rowmapper.LOIRowMapper;
import org.egov.works.web.models.LOISearchCriteria;
import org.egov.works.web.models.LetterOfIndent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LOIRepository {

    private LOIRowMapper rowMapper;
    private LOIQueryBuilder queryBuilder;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public LOIRepository(LOIRowMapper rowMapper, LOIQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate){
        this.rowMapper = rowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;

    }
    /**
     *
     * @param searchCriteria
     * @return
     */
    public List<LetterOfIndent> getEstimate(LOISearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getLOIQuery(searchCriteria,preparedStmtList);
        List<LetterOfIndent> loiList = jdbcTemplate.query(query,rowMapper,preparedStmtList.toArray());
        return loiList;
    }
}
