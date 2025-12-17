package org.egov.works.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.repository.querybuilder.AmountBreakupQueryBuilder;
import org.egov.works.repository.rowmapper.AmountBreakupRowMapper;
import org.egov.works.web.models.AmountBreakup;
import org.egov.works.web.models.ContractCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class AmountBreakupRepository {
    private final AmountBreakupRowMapper rowMapper;

    private final AmountBreakupQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AmountBreakupRepository(AmountBreakupRowMapper rowMapper, AmountBreakupQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate) {
        this.rowMapper = rowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AmountBreakup> getAmountBreakups(ContractCriteria contractCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getAmountBreakupSearchQuery(contractCriteria, preparedStmtList);
        return jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
    }
}
