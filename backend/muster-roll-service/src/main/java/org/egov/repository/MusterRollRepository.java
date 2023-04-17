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

    @Autowired
    private MusterRollRowMapper rowMapper;

    @Autowired
    private MusterRollQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * Fetch the record from DB based on the search criteria
     * @param searchCriteria
     * @return
     */
    public List<MusterRoll> getMusterRoll(MusterRollSearchCriteria searchCriteria,List<String> registerIds) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getMusterSearchQuery(searchCriteria, preparedStmtList, registerIds);
        List<MusterRoll> musterRollList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return musterRollList;
    }
}
