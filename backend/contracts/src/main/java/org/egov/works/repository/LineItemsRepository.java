package org.egov.works.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.repository.querybuilder.LineItemsQueryBuilder;
import org.egov.works.repository.rowmapper.LineItemsRowMapper;
import org.egov.works.web.models.ContractCriteria;
import org.egov.works.web.models.LineItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class LineItemsRepository {
    private final LineItemsRowMapper rowMapper;

    private final LineItemsQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LineItemsRepository(LineItemsRowMapper rowMapper, LineItemsQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate) {
        this.rowMapper = rowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<LineItems> getLineItems(ContractCriteria contractCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getLineItemsSearchQuery(contractCriteria, preparedStmtList);
        return jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
    }
}
