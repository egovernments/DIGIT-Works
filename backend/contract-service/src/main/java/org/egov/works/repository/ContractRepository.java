package org.egov.works.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.repository.querybuilder.ContractQueryBuilder;
import org.egov.works.repository.rowmapper.ContractRowMapper;
import org.egov.works.web.models.Contract;
import org.egov.works.web.models.ContractCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ContractRepository {
    @Autowired
    private ContractRowMapper rowMapper;

    @Autowired
    private ContractQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Contract> getContracts(ContractCriteria contractCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getContractSearchQuery(contractCriteria, preparedStmtList);
        List<Contract> contracts = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return contracts;
    }
}
