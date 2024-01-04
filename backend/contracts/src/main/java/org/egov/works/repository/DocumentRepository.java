package org.egov.works.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.repository.querybuilder.DocumentsQueryBuilder;
import org.egov.works.repository.rowmapper.DocumentRowMapper;
import org.egov.works.web.models.ContractCriteria;
import org.egov.works.web.models.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class DocumentRepository {
    @Autowired
    private DocumentRowMapper rowMapper;

    @Autowired
    private DocumentsQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Document> getDocuments(ContractCriteria contractCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getDocumentSearchQuery(contractCriteria, preparedStmtList);
        return jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
    }
}
