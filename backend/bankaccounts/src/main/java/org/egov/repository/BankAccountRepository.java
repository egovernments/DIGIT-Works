package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.repository.rowmapper.BankAccountQueryBuilder;
import org.egov.repository.rowmapper.BankAccountRowMapper;
import org.egov.web.models.BankAccount;
import org.egov.web.models.BankAccountSearchCriteria;
import org.egov.web.models.BankAccountSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class BankAccountRepository {

    private final JdbcTemplate jdbcTemplate;

    private final BankAccountQueryBuilder queryBuilder;

    private final BankAccountRowMapper rowMapper;

    @Autowired
    public BankAccountRepository(JdbcTemplate jdbcTemplate, BankAccountQueryBuilder queryBuilder, BankAccountRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryBuilder = queryBuilder;
        this.rowMapper = rowMapper;
    }

    /**
     * @param searchRequest
     * @return
     */
    public List<BankAccount> getBankAccount(BankAccountSearchRequest searchRequest) {
        log.info("BankAccountRepository::getBankAccount");
        BankAccountSearchCriteria searchCriteria = searchRequest.getBankAccountDetails();
        List<Object> preparedStmtList = new ArrayList<>();
        if (searchCriteria.getIsCountNeeded() == null) {
            searchCriteria.setIsCountNeeded(Boolean.FALSE);
        }
        String query = queryBuilder.getBankAccountQuery(searchRequest, preparedStmtList);
        List<BankAccount> bankAccountList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return bankAccountList;
    }

    /**
     * Get the count of bank accounts based on the given search criteria (using dynamic
     * query build at the run time)
     *
     * @param searchRequest
     * @return
     */
    public Integer getBankAccountCount(BankAccountSearchRequest searchRequest) {
        log.info("BankAccountRepository::getBankAccountCount");
        List<Object> preparedStatement = new ArrayList<>();
        String query = queryBuilder.getSearchCountQueryString(searchRequest, preparedStatement);

        if (query == null)
            return 0;

        Integer count = jdbcTemplate.queryForObject(query, preparedStatement.toArray(), Integer.class);
        return count;
    }
}
