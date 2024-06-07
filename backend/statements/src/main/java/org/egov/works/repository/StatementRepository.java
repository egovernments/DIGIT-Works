package org.egov.works.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.repository.rowmapper.StatementQueryBuilder;
import org.egov.works.repository.rowmapper.StatementRowMapper;
import org.egov.works.web.models.SearchCriteria;
import org.egov.works.web.models.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class StatementRepository {

    private final StatementRowMapper rowMapper;

    private final StatementQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StatementRepository(StatementRowMapper rowMapper, StatementQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate) {
        this.rowMapper = rowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Statement> getStatement(SearchCriteria searchCriteria) {
        log.info("StatementRespository::getStatement");
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getStatementQuery(searchCriteria, preparedStmtList);
        return jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
    }

}
