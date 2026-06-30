package org.egov.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.data.query.builder.SelectQueryBuilder;
import org.egov.common.data.repository.GenericRepository;
import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.producer.Producer;
import org.egov.repository.querybuilder.FaceAuthEventQueryBuilder;
import org.egov.repository.rowmapper.FaceAuthEventRowMapper;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.FaceAuthEvent;
import org.egov.web.models.FaceAuthEventSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static org.egov.Constants.INVALID_TENANT_ID;

@Repository
@Slf4j
public class FaceAuthEventRepository extends GenericRepository<FaceAuthEvent> {
    private final FaceAuthEventRowMapper rowMapper;
    private final FaceAuthEventQueryBuilder queryBuilder;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    protected FaceAuthEventRepository(
            Producer producer,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            RedisTemplate<String, Object> redisTemplate,
            SelectQueryBuilder selectQueryBuilder,
            FaceAuthEventRowMapper rowMapper,
            JdbcTemplate jdbcTemplate,
            FaceAuthEventQueryBuilder queryBuilder) {
        super(producer, namedParameterJdbcTemplate, redisTemplate, selectQueryBuilder, null, Optional.of("abc"));
        this.rowMapper = rowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<FaceAuthEvent> getFaceAuthEvents(FaceAuthEventSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String tenantId = searchCriteria.getTenantId();
        String query;
        try {
            query = queryBuilder.getFaceAuthEventSearchQuery(tenantId, searchCriteria, preparedStmtList);
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID, e.getMessage());
        }
        log.info("Face auth event query built successfully");
        List<FaceAuthEvent> faceAuthEvents = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        log.info("Fetched {} face auth events", faceAuthEvents.size());
        return faceAuthEvents;
    }
}
