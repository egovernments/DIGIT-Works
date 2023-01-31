package org.egov.works.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.works.config.ProjectConfiguration;
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
import java.util.Arrays;
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
    public List<Project> getProjects(ProjectRequest project, Integer limit, Integer offset, String tenantId, Long lastChangedSince, Boolean includeDeleted, Boolean includeAncestors) {

        //Fetch Projects based on search criteria
        List<Object> preparedStmtList = new ArrayList<>();
        List<Project> projects = getProjectsBasedOnSearchCriteria(project.getProjects(), limit, offset, tenantId, lastChangedSince, includeDeleted, preparedStmtList);
        List<Project> ancestors = null;

        //Get Project ancestors if includeAncestors flag is true
        if (includeAncestors) {
            ancestors = getProjectAncestors(projects);
        }

        List<String> projectIds = projects.stream().map(Project :: getId).collect(Collectors.toList());

        //Fetch targets based on Project Ids
        List<Object> preparedStmtListTarget = new ArrayList<>();
        List<Target> targets = getTargetsBasedOnProjectIds(projectIds, preparedStmtListTarget);

        //Fetch documents based on Project Ids
        List<Object> preparedStmtListDocument = new ArrayList<>();
        List<Document> documents = getDocumentsBasedOnProjectIds(projectIds, preparedStmtListDocument);

        //Construct Project Objects with fetched projects, targets and documents using Project id
        List<Project> result = buildProjectSearchResult(projects, targets, documents, ancestors);
        return result;
    }

    /* Fetch Projects based on search criteria */
    private List<Project> getProjectsBasedOnSearchCriteria(List<Project> projectsRequest, Integer limit, Integer offset, String tenantId, Long lastChangedSince, Boolean includeDeleted,  List<Object> preparedStmtList) {
        String query = queryBuilder.getProjectSearchQuery(projectsRequest, limit, offset, tenantId, lastChangedSince, includeDeleted, preparedStmtList);
        List<Project> projects = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        log.info("Fetched project list based on given search criteria");
        return projects;
    }

    /* Fetch Projects based on Project ids */
    private List<Project> getProjectsBasedOnProjectIds(List<String> projectIds,  List<Object> preparedStmtList) {
        String query = queryBuilder.getProjectSearchQueryBasedOnIds(projectIds, preparedStmtList);
        List<Project> projects = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        log.info("Fetched project list based on given Project Ids");
        return projects;
    }

    /* Fetch targets based on Project Ids */
    private List<Target> getTargetsBasedOnProjectIds(List<String> projectIds, List<Object> preparedStmtListTarget) {
        String queryTarget = targetQueryBuilder.getTargetSearchQuery(projectIds, preparedStmtListTarget);
        List<Target> targets = jdbcTemplate.query(queryTarget, targetRowMapper, preparedStmtListTarget.toArray());
        log.info("Fetched targets based on project Ids");
        return targets;
    }

    /* Fetch documents based on Project Ids */
    private List<Document> getDocumentsBasedOnProjectIds(List<String> projectIds, List<Object> preparedStmtListDocument) {
        String queryDocument = documentQueryBuilder.getDocumentSearchQuery(projectIds, preparedStmtListDocument);
        List<Document> documents = jdbcTemplate.query(queryDocument, documentRowMapper, preparedStmtListDocument.toArray());
        log.info("Fetched documents based on project Ids");
        return documents;
    }

    /* Seperates preceeding project ids from project hierarchy, adds them in list and fetches data using those project ids */
    private List<Project> getProjectAncestors(List<Project> projects) {
        List<String> ancestorIds = new ArrayList<>();
        List<Project> ancestors = null;

        for (Project project: projects) {
            if (StringUtils.isNotBlank(project.getProjectHierarchy())) {
                List<String> projectHierarchyIds = Arrays.asList(project.getProjectHierarchy().split("\\."));
                ancestorIds.addAll(projectHierarchyIds);
            }
        }
        if (ancestorIds.size() > 0) {
            List<Object> preparedStmtListAncestors = new ArrayList<>();
            ancestors = getProjectsBasedOnProjectIds(ancestorIds, preparedStmtListAncestors);
        }

        return ancestors;
    }

    /* Constructs Project Objects with fetched projects, targets and documents using Project id and return list of Projects */
    private List<Project> buildProjectSearchResult(List<Project> projects, List<Target> targets, List<Document> documents, List<Project> ancestors) {
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

            if (ancestors != null && StringUtils.isNotBlank(project.getParent())) {
                addAncestorsToProjectSearchResult(project, ancestors);
            }
        }
        return projects;
    }

    /* Adds ancestors to Project based on project id and Parent id  */
    private void addAncestorsToProjectSearchResult(Project project, List<Project> ancestors) {
        Project currentProject = project;
        while (StringUtils.isNotBlank(currentProject.getParent())) {
            String parentProjectId = currentProject.getParent();
            Project parentProject = ancestors.stream().filter(prj -> prj.getId().equals(parentProjectId)).findFirst().orElse(null);
            currentProject.setAncestors(parentProject);
            currentProject = currentProject.getAncestors();
        }
    }

}
