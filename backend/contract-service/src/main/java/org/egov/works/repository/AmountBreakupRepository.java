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
    @Autowired
    private AmountBreakupRowMapper rowMapper;

    @Autowired
    private AmountBreakupQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AmountBreakup> getAmountBreakups(ContractCriteria contractCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getAmountBreakupSearchQuery(contractCriteria, preparedStmtList);
        List<AmountBreakup> amountBreakups = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return amountBreakups;
    }
}
