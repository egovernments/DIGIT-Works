package org.egov.works.repository;

import org.egov.works.repository.querybuilder.DocumentQueryBuilder;
import org.egov.works.repository.querybuilder.LocalityQueryBuilder;
import org.egov.works.repository.querybuilder.ProjectAddressQueryBuilder;
import org.egov.works.repository.querybuilder.TargetQueryBuilder;
import org.egov.works.repository.rowmapper.DocumentRowMapper;
import org.egov.works.repository.rowmapper.LocalityRowMapper;
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
public class ProjectRepository {

    @Autowired
    private ProjectAddressQueryBuilder queryBuilder;

    @Autowired
    private TargetQueryBuilder targetQueryBuilder;
    @Autowired
    private DocumentQueryBuilder documentQueryBuilder;
    @Autowired
    private LocalityQueryBuilder localityQueryBuilder;

    @Autowired
    private ProjectAddressRowMapper rowMapper;
    @Autowired
    private TargetRowMapper targetRowMapper;
    @Autowired
    private DocumentRowMapper documentRowMapper;
    @Autowired
    private LocalityRowMapper localityRowMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Project> getProjects(ProjectRequest project, Integer limit, Integer offset, String tenantId, Long lastChangedSince, Boolean includeDeleted) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getProjectSearchQuery(project, limit, offset, tenantId, lastChangedSince, includeDeleted, preparedStmtList);
        List<Project> projects = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());

        List<String> projectIds = projects.stream().map(Project :: getId).collect(Collectors.toList());

        List<Object> preparedStmtListTarget = new ArrayList<>();
        String queryTarget = targetQueryBuilder.getTargetSearchQuery(projectIds, preparedStmtListTarget);
        List<Target> targets = jdbcTemplate.query(queryTarget, targetRowMapper, preparedStmtListTarget.toArray());

        List<Object> preparedStmtListDocument = new ArrayList<>();
        String queryDocument = documentQueryBuilder.getDocumentSearchQuery(projectIds, preparedStmtListDocument);
        List<Document> documents = jdbcTemplate.query(queryDocument, documentRowMapper, preparedStmtListDocument.toArray());

        HashSet<String> addressIds = getAddressIds(projects);
        List<Object> preparedStmtListLocality = new ArrayList<>();
        String queryLocality = localityQueryBuilder.getLocalitySearchQuery(addressIds, preparedStmtListLocality);
        List<Boundary> localities = jdbcTemplate.query(queryLocality, localityRowMapper, preparedStmtListLocality.toArray());

        List<Project> result = buildProjectSearchResult(projects, targets, documents, localities);
        return result;
    }

    private List<Project> buildProjectSearchResult(List<Project> projects, List<Target> targets, List<Document> documents, List<Boundary> localities) {
        for (Project project: projects) {
            project.setTargets(new ArrayList<>());
            project.setDocuments(new ArrayList<>());
            project.getAddress().setLocality(new Boundary());


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
            for (Boundary locality: localities) {
                if (locality.getParentid() == null && locality.getAddressid().equals(project.getAddress().getId())) {
                    project.getAddress().setLocality(locality);
                }
            }
            for (Boundary children: localities) {
                if (project.getAddress().getLocality() != null && children.getParentid() != null &&
                        children.getParentid().equals(project.getAddress().getLocality().getId()) &&
                        children.getAddressid().equals(project.getAddress().getId())) {
                    if (project.getAddress().getLocality().getChildren() == null)
                        project.getAddress().getLocality().setChildren(new ArrayList<>());
                    project.getAddress().getLocality().getChildren().add(children);
                }
            }
        }
        return projects;
    }

    private HashSet<String> getAddressIds(List<Project> projects) {
        HashSet<String> projectids = new HashSet<>();
        for (Project project: projects) {
            projectids.add(project.getAddress().getId());
        }
        return projectids;
    }
}
