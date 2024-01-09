package org.egov.works.mukta.adapter.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.mukta.adapter.repository.queryBuilder.PaymentQueryBuilder;
import org.egov.works.mukta.adapter.repository.rowMapper.PaymentRowMapper;
import org.egov.works.mukta.adapter.web.models.MuktaPayment;
import org.egov.works.mukta.adapter.web.models.PaymentSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PaymentRepository {
    private final PaymentQueryBuilder paymentQueryBuilder;
    private final PaymentRowMapper paymentRowMapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PaymentRepository(PaymentQueryBuilder paymentQueryBuilder, PaymentRowMapper paymentRowMapper, JdbcTemplate jdbcTemplate) {
        this.paymentQueryBuilder = paymentQueryBuilder;
        this.paymentRowMapper = paymentRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MuktaPayment> searchPayment(PaymentSearchCriteria paymentSearchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = paymentQueryBuilder.getPaymentSearchQuery(paymentSearchCriteria, preparedStmtList);
        return jdbcTemplate.query(query, preparedStmtList.toArray(), paymentRowMapper);
    }
}
