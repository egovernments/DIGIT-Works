package org.egov.digit.expense.repository;

import org.egov.digit.expense.repository.querybuilder.BillVerificationTaskQueryBuilder;
import org.egov.digit.expense.web.models.BillVerificationTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BillVerificationTaskRepository {

    private final JdbcTemplate jdbcTemplate;

    private final BillVerificationTaskQueryBuilder queryBuilder;

    @Autowired
    public BillVerificationTaskRepository(JdbcTemplate jdbcTemplate, BillVerificationTaskQueryBuilder queryBuilder) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryBuilder = queryBuilder;
    }

    public BillVerificationTask search(String taskId){
        String queryStr = queryBuilder.getBillVerifcationTaskQuery(taskId);
        return jdbcTemplate.queryForObject(queryStr, BillVerificationTask.class);
    }
}
