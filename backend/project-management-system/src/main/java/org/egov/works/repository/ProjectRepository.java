package org.egov.works.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

import java.util.*;
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
    public List<Project> getProjects(ProjectRequest project, Integer limit, Integer offset, String tenantId, Long lastChangedSince, Boolean includeDeleted, Boolean includeAncestors, Boolean includeDescendants, Long createdFrom, Long createdTo) {

        //Fetch Projects based on search criteria
        List<Project> projects = getProjectsBasedOnSearchCriteria(project.getProjects(), limit, offset, tenantId, lastChangedSince, includeDeleted, createdFrom, createdTo);

        Set<String> projectIds = projects.stream().map(Project :: getId).collect(Collectors.toSet());

        List<Project> ancestors = null;
        List<Project> descendants = null;
        //Get Project ancestors if includeAncestors flag is true
        if (includeAncestors) {
            ancestors = getProjectAncestors(projects);
            if (ancestors != null && !ancestors.isEmpty()) {
                List<String> ancestorProjectIds = ancestors.stream().map(Project :: getId).collect(Collectors.toList());
                projectIds.addAll(ancestorProjectIds);
            }
        }
        //Get Project descendants if includeDescendants flag is true
        if (includeDescendants) {
            descendants = getProjectDescendants(projects);
            if (descendants != null && !descendants.isEmpty()) {
                List<String> descendantsProjectIds = descendants.stream().map(Project :: getId).collect(Collectors.toList());
                projectIds.addAll(descendantsProjectIds);
            }
        }

        //Fetch targets based on Project Ids
        List<Target> targets = getTargetsBasedOnProjectIds(projectIds);

        //Fetch documents based on Project Ids
        List<Document> documents = getDocumentsBasedOnProjectIds(projectIds);

        //Construct Project Objects with fetched projects, targets and documents using Project id
        return buildProjectSearchResult(projects, targets, documents, ancestors, descendants);
    }

    /* Fetch Projects based on search criteria */
    private List<Project> getProjectsBasedOnSearchCriteria(List<Project> projectsRequest, Integer limit, Integer offset, String tenantId, Long lastChangedSince, Boolean includeDeleted, Long createdFrom, Long createdTo) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getProjectSearchQuery(projectsRequest, limit, offset, tenantId, lastChangedSince, includeDeleted, createdFrom, createdTo, preparedStmtList, false);
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

    /* Fetch Project descendants based on Project ids */
    private List<Project> getProjectsDescendantsBasedOnProjectIds(List<String> projectIds, List<Object> preparedStmtListDescendants) {
        String query = queryBuilder.getProjectDescendantsSearchQueryBasedOnIds(projectIds, preparedStmtListDescendants);
        List<Project> projects = jdbcTemplate.query(query, rowMapper, preparedStmtListDescendants.toArray());
        log.info("Fetched project descendants list based on given Project Ids");
        return projects;
    }

    /* Fetch targets based on Project Ids */
    private List<Target> getTargetsBasedOnProjectIds(Set<String> projectIds) {
        List<Object> preparedStmtListTarget = new ArrayList<>();
        String queryTarget = targetQueryBuilder.getTargetSearchQuery(projectIds, preparedStmtListTarget);
        List<Target> targets = jdbcTemplate.query(queryTarget, targetRowMapper, preparedStmtListTarget.toArray());
        log.info("Fetched targets based on project Ids");
        return targets;
    }

    /* Fetch documents based on Project Ids */
    private List<Document> getDocumentsBasedOnProjectIds(Set<String> projectIds) {
        List<Object> preparedStmtListDocument = new ArrayList<>();
        String queryDocument = documentQueryBuilder.getDocumentSearchQuery(projectIds, preparedStmtListDocument);
        List<Document> documents = jdbcTemplate.query(queryDocument, documentRowMapper, preparedStmtListDocument.toArray());
        log.info("Fetched documents based on project Ids");
        return documents;
    }

    /* Separates preceding project ids from project hierarchy, adds them in list and fetches data using those project ids */
    private List<Project> getProjectAncestors(List<Project> projects) {
        List<String> ancestorIds = new ArrayList<>();
        List<Project> ancestors = null;

        // Get project Id of ancestor projects from project Hierarchy
        for (Project project: projects) {
            if (StringUtils.isNotBlank(project.getProjectHierarchy())) {
                List<String> projectHierarchyIds = Arrays.asList(project.getProjectHierarchy().split("\\."));
                ancestorIds.addAll(projectHierarchyIds);
            }
        }
        //Fetch projects based on ancestor project Ids
        if (ancestorIds.size() > 0) {
            List<Object> preparedStmtListAncestors = new ArrayList<>();
            ancestors = getProjectsBasedOnProjectIds(ancestorIds, preparedStmtListAncestors);
            log.info("Fetched ancestor projects");
        }

        return ancestors;
    }

    /* Fetch projects where project hierarchy for projects in db contains project ID of requested project. The descendant project's projectHierarchy will contain parent project id */
    private List<Project> getProjectDescendants(List<Project> projects) {
        List<String> projectIds = projects.stream().map(Project:: getId).collect(Collectors.toList());

        List<Object> preparedStmtListDescendants = new ArrayList<>();
        log.info("Fetching descendant projects");

        return getProjectsDescendantsBasedOnProjectIds(projectIds, preparedStmtListDescendants);
    }

    /* Constructs Project Objects with fetched projects, targets and documents using Project id and return list of Projects */
    private List<Project> buildProjectSearchResult(List<Project> projects, List<Target> targets, List<Document> documents, List<Project> ancestors, List<Project> descendants) {
        for (Project project: projects) {
            log.info("Constructing project object for project " + project.getId());
            if (targets != null && targets.size() > 0) {
                log.info("Adding Targets to project " + project.getId());
                addTargetToProject(project, targets);
            }
            if (documents != null && documents.size() > 0) {
                log.info("Adding Documents to project " + project.getId());
                addDocumentToProject(project, documents);
            }
            if (ancestors != null && !ancestors.isEmpty() && StringUtils.isNotBlank(project.getParent())) {
                log.info("Adding ancestors to project " + project.getId());
                addAncestorsToProjectSearchResult(project, ancestors, targets, documents);
            }
            if (descendants != null && !descendants.isEmpty()) {
                log.info("Adding descendants to project " + project.getId());
                addDescendantsToProjectSearchResult(project, descendants, targets, documents);
            }
            log.info("Constructed project object for project " + project.getId());
        }
        return projects;
    }

    /* Add Targets to projects based on projectId and targets list passed */
    private void addTargetToProject(Project project, List<Target> targets) {
        project.setTargets(new ArrayList<>());
        for (Target target: targets) {
            if (target.getProjectid().equals(project.getId()) && !target.getIsDeleted() && project.getTargets().stream().noneMatch(t -> t.getId().equals(target.getId()))) {
                project.getTargets().add(target);
            }
        }
    }

    /* Add Documents to projects based on projectId and documents list passed */
    private void addDocumentToProject(Project project, List<Document> documents) {
        project.setDocuments(new ArrayList<>());
        for (Document document: documents) {
            if (document.getProjectid().equals(project.getId())
                    && (document.getStatus() == null || document.getStatus() != null && !document.getStatus().equals("INACTIVE"))
                    && project.getDocuments().stream().noneMatch(t -> t.getId().equals(document.getId()))) {
                project.getDocuments().add(document);
            }
        }
    }


    /* Adds ancestors to Project based on project and ancestors list  */
    private void addAncestorsToProjectSearchResult(Project project, List<Project> ancestors, List<Target> targets, List<Document> documents) {
        List<Project> currentProjectAncestors = ancestors.stream().filter(a -> (project.getProjectHierarchy().contains(a.getId())
                        && !project.getId().equals(a.getId()))).collect(Collectors.toList());
        //Add target and document to ancestor projects using targets and documents list
        for (Project ancestor: currentProjectAncestors) {
            addTargetToProject(ancestor, targets);
            addDocumentToProject(ancestor, documents);
            log.info("Targets and Documents mapped to ancestor projects");
        }
        project.setAncestors(currentProjectAncestors);
        log.info("Ancestors set for project " + project.getId());

        /* The below code returns Project ancestors with tree structure. If project hierarchy A.B.C, "ancestor" field of project C will contain project B
        * "ancestor" field of project B will contain project A and so on. For this to work, change type of "ancestor" to Project instead of List<Project>.
        *  This code snippet has been tested and working as expected. */

//        Project currentProject = project;
//        while (StringUtils.isNotBlank(currentProject.getParent())) {
//            String parentProjectId = currentProject.getParent();
//            Project parentProject = ancestors.stream().filter(prj -> prj.getId().equals(parentProjectId)).findFirst().orElse(null);
//            currentProject.setAncestors(parentProject);
//            currentProject = currentProject.getAncestors();
//        }
    }

    /* Adds ancestors to Project based on project and descendants list  */
    private void addDescendantsToProjectSearchResult(Project project, List<Project> descendants, List<Target> targets, List<Document> documents) {
        List<Project> subProjects = descendants.stream().filter(d -> StringUtils.isNotBlank(d.getParent())
                    && d.getProjectHierarchy().contains(project.getId())
                    && !d.getId().equals(project.getId())).collect(Collectors.toList());
        //Add target and document to descendants projects using targets and documents list
        for (Project ancestor: subProjects) {
            addTargetToProject(ancestor, targets);
            addDocumentToProject(ancestor, documents);
            log.info("Targets and Documents mapped to descendant projects");
        }
        if (!subProjects.isEmpty()) {
            project.setDescendants(subProjects);
            log.info("Descendants set for project " + project.getId());
        }

        /* The below code returns Project descendants with tree structure. If project hierarchy A.B.C and A.D, "descendants" field of project A will contain project B and project D
         * "descendants" field of project B will contain project C, "descendants" field of project C and D will contain null  and so on.
         *  This code snippet is incomplete and not working for multiple projects, multiple subprojects */
//        for (Project descendant : descendants) {
//            addDescendants(project, descendant);
//        }
//        // Recursive method to add Descendants. This method can be taken out while implementing tree hierarchy
//        public static void addDescendants(Project parent, Project child) {
//            if (parent.getId().equals(child.getParent())) {
//                parent.addDescendant(child);
//            } else {
//                for (Project project : parent.getDescendants()) {
//                    addDescendants(project, child);
//                }
//            }
//        }
    }

    /**
     * Get the count of projects based on the given search criteria (using dynamic
     * query build at the run time)
     * @return
     */
    public Integer getProjectCount(ProjectRequest project, String tenantId, Long lastChangedSince, Boolean includeDeleted, Long createdFrom, Long createdTo) {
        List<Object> preparedStatement = new ArrayList<>();
        String query = queryBuilder.getSearchCountQueryString(project.getProjects(), tenantId, lastChangedSince, includeDeleted, createdFrom, createdTo, preparedStatement);

        if (query == null)
            return 0;

        Integer count = jdbcTemplate.queryForObject(query, preparedStatement.toArray(), Integer.class);
        log.info("Total project count is : " + count);
        return count;
    }

}
