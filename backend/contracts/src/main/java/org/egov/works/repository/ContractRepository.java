package org.egov.works.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.repository.querybuilder.ContractQueryBuilder;
import org.egov.works.repository.rowmapper.ContractRowMapper;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ContractRepository {
    private final ContractRowMapper rowMapper;

    private final ContractQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;
    private final ContractServiceConfiguration contractServiceConfiguration;

    @Autowired
    public ContractRepository(ContractRowMapper rowMapper, ContractQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate, ContractServiceConfiguration contractServiceConfiguration) {
        this.rowMapper = rowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
        this.contractServiceConfiguration = contractServiceConfiguration;
    }

    public List<Contract> getContracts(ContractCriteria contractCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getContractSearchQuery(contractCriteria, preparedStmtList);
        return jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
    }
    public List<Contract> getActiveContractsFromDB(ContractRequest contractRequest) {
        Pagination pagination = Pagination.builder()
                .limit(contractServiceConfiguration.getContractMaxLimit())
                .offSet(contractServiceConfiguration.getContractDefaultOffset())
                .build();
        ContractCriteria contractCriteria = ContractCriteria.builder()
                .contractNumber(contractRequest.getContract().getContractNumber())
                .status(Status.ACTIVE.toString())
                .tenantId(contractRequest.getContract().getTenantId())
                .requestInfo(contractRequest.getRequestInfo())
                .pagination(pagination)
                .build();
        return getContracts(contractCriteria);
    }
}
