package org.egov.works.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.repository.querybuilder.DocumentQueryBuilder;
import org.egov.works.repository.querybuilder.ProjectAddressQueryBuilder;
import org.egov.works.repository.querybuilder.TargetQueryBuilder;
import org.egov.works.repository.rowmapper.DocumentRowMapper;
import org.egov.works.repository.rowmapper.ProjectAddressRowMapper;
import org.egov.works.repository.rowmapper.TargetRowMapper;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ProjectRepository {

    @Autowired
    private ProjectAddressQueryBuilder queryBuilder;

    @Autowired
    private TargetQueryBuilder targetQueryBuilder;
    @Autowired
    private DocumentQueryBuilder documentQueryBuilder;

    @Autowired
    private ProjectAddressRowMapper rowMapper;
    @Autowired
    private TargetRowMapper targetRowMapper;
    @Autowired
    private DocumentRowMapper documentRowMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /* Search projects for project request and parameters and return list of projects */
    public List<Project> getProjects(ProjectRequest project, Integer limit, Integer offset, String tenantId, Long lastChangedSince, Boolean includeDeleted) {

        //Fetch Projects based on search criteria
        List<Project> projects = getProjectsBasedOnSearchCriteria(project, limit, offset, tenantId, lastChangedSince, includeDeleted);

        List<String> projectIds = projects.stream().map(Project :: getId).collect(Collectors.toList());

        //Fetch targets based on Project Ids
        List<Target> targets = getTargetsBasedOnProjectIds(projectIds);

        //Fetch documents based on Project Ids
        List<Document> documents = getDocumentsBasedOnProjectIds(projectIds);

        //Construct Project Objects with fetched projects, targets and documents using Project id
        List<Project> result = buildProjectSearchResult(projects, targets, documents);
        return result;
    }

    /* Fetch Projects based on search criteria */
    private List<Project> getProjectsBasedOnSearchCriteria(ProjectRequest project, Integer limit, Integer offset, String tenantId, Long lastChangedSince, Boolean includeDeleted) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getProjectSearchQuery(project, limit, offset, tenantId, lastChangedSince, includeDeleted, preparedStmtList, false);
        List<Project> projects = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        /* Fetch total projects count based on project search params */
        preparedStmtList.clear();
        String queryForCount = queryBuilder.getProjectSearchQuery(project, limit, offset, tenantId, lastChangedSince, includeDeleted, preparedStmtList, true);
        Integer searchedProjectsTotalCount = jdbcTemplate.queryForObject(queryForCount, Integer.class, preparedStmtList.toArray());
        log.info("Total project count is : " + searchedProjectsTotalCount);

        log.info("Fetched project list based on given search criteria");
        return projects;
    }

    /* Fetch targets based on Project Ids */
    private List<Target> getTargetsBasedOnProjectIds(List<String> projectIds) {
        List<Object> preparedStmtListTarget = new ArrayList<>();
        String queryTarget = targetQueryBuilder.getTargetSearchQuery(projectIds, preparedStmtListTarget);
        List<Target> targets = jdbcTemplate.query(queryTarget, targetRowMapper, preparedStmtListTarget.toArray());
        log.info("Fetched targets based on project Ids");
        return targets;
    }

    /* Fetch documents based on Project Ids */
    private List<Document> getDocumentsBasedOnProjectIds(List<String> projectIds) {
        List<Object> preparedStmtListDocument = new ArrayList<>();
        String queryDocument = documentQueryBuilder.getDocumentSearchQuery(projectIds, preparedStmtListDocument);
        List<Document> documents = jdbcTemplate.query(queryDocument, documentRowMapper, preparedStmtListDocument.toArray());
        log.info("Fetched documents based on project Ids");
        return documents;
    }

    /* Constructs Project Objects with fetched projects, targets and documents using Project id and return list of Projects */
    private List<Project> buildProjectSearchResult(List<Project> projects, List<Target> targets, List<Document> documents) {
        for (Project project: projects) {
            project.setTargets(new ArrayList<>());
            project.setDocuments(new ArrayList<>());

            for (Target target: targets) {
                if (target.getProjectid().equals(project.getId())) {
                    project.getTargets().add(target);
                }
            }
            for (Document document: documents) {
                if (document.getProjectid().equals(project.getId())) {
                    project.getDocuments().add(document);
                }
            }
        }
        return projects;
    }

}
