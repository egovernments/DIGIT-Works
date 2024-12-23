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
    @Autowired
    private LineItemsRowMapper rowMapper;

    @Autowired
    private LineItemsQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<LineItems> getLineItems(ContractCriteria contractCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getLineItemsSearchQuery(contractCriteria, preparedStmtList);
        return jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
    }
}
