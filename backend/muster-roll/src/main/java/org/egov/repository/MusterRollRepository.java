package org.egov.repository;

import org.egov.repository.querybuilder.MusterRollQueryBuilder;
import org.egov.repository.rowmapper.MusterRollRowMapper;
import org.egov.web.models.MusterRoll;
import org.egov.web.models.MusterRollSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MusterRollRepository {

    private final MusterRollRowMapper rowMapper;

    private final MusterRollQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MusterRollRepository(MusterRollRowMapper rowMapper, MusterRollQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate) {
        this.rowMapper = rowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     * Fetch the record from DB based on the search criteria
     * @param searchCriteria
     * @return
     */
    public List<MusterRoll> getMusterRoll(MusterRollSearchCriteria searchCriteria,List<String> registerIds) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getMusterSearchQuery(searchCriteria, preparedStmtList, registerIds);
        return jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
    }
}
