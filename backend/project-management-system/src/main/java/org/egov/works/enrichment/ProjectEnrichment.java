package org.egov.works.enrichment;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ProjectConfiguration;
import org.egov.works.repository.IdGenRepository;
import org.egov.works.util.ProjectServiceUtil;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectEnrichment {

    @Autowired
    private ProjectServiceUtil projectServiceUtil;

    @Autowired
    private IdGenRepository idGenRepository;

    @Autowired
    private ProjectConfiguration config;

    /* Enrich Project on Create Request */
    public void enrichProjectOnCreate(ProjectRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        List<Project> projects = request.getProjects();

        String rootTenantId = projects.get(0).getTenantId().split("\\.")[0];

        //Get Project Ids from Idgen Service for Number of projects present in Projects
        List<String> projectNumbers = getIdList(requestInfo, rootTenantId
                , config.getIdgenProjectNumberName(), config.getIdgenProjectNumberFormat(), projects.size());

        for (int i = 0; i < projects.size(); i++) {

            if (projectNumbers != null && !projectNumbers.isEmpty()) {
                projects.get(i).setProjectNumber(projectNumbers.get(i));
            }

            //Enrich Project id and audit details
            enrichProjectRequest(projects.get(i), null, requestInfo);

            //Enrich Address id and audit details
            enrichProjectAddress(projects.get(i), null, requestInfo);

            //Enrich target id and audit details
            enrichProjectTarget(projects.get(i), null, requestInfo);

            //Enrich document id and audit details
            enrichProjectDocument(projects.get(i), null, requestInfo);
        }

    }

    /* Enrich Project on Update Request */
    public void enrichProjectOnUpdate(ProjectRequest request, List<Project> projectsFromDB) {
        RequestInfo requestInfo = request.getRequestInfo();
        List<Project> projectsFromRequest = request.getProjects();

        for (Project project : projectsFromRequest) {
            String projectId = String.valueOf(project.getId());
            Project projectFromDB = projectsFromDB.stream().filter(p -> projectId.equals(String.valueOf(p.getId()))).findFirst().orElse(null);

            //Updating lastModifiedTime and lastModifiedBy for Project
            enrichProjectRequest(project, projectFromDB, requestInfo);

            //Add address if id is empty or update lastModifiedTime and lastModifiedBy if id exists
            enrichProjectAddress(project, projectFromDB, requestInfo);

            //Add new target if id is empty or update lastModifiedTime and lastModifiedBy if id exists
            enrichProjectTarget(project, projectFromDB, requestInfo);

            //Add new document if id is empty or update lastModifiedTime and lastModifiedBy if id exists
            enrichProjectDocument(project, projectFromDB, requestInfo);
        }

    }

    /* Enrich Project with id and audit details if project id is empty or if project already present, update last modified by and last modified time */
    private void enrichProjectRequest(Project projectRequest, Project projectFromDB, RequestInfo requestInfo) {
        if (StringUtils.isBlank(projectRequest.getId())) {
            projectRequest.setId(UUID.randomUUID().toString());
            AuditDetails auditDetails = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), null, true);
            projectRequest.setAuditDetails(auditDetails);
        } else if (projectFromDB != null) {
            projectRequest.setAuditDetails(projectFromDB.getAuditDetails());
            AuditDetails auditDetails = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), projectFromDB.getAuditDetails(), false);
            projectRequest.setAuditDetails(auditDetails);
        }
    }

    /* Enrich Address with id and audit details if address id is empty or if address already present, update last modified by and last modified time */
    private void enrichProjectAddress(Project projectFromRequest, Project projectFromDB, RequestInfo requestInfo) {
        if (projectFromRequest.getAddress() != null) {
            if (StringUtils.isBlank(projectFromRequest.getAddress().getId())) {
                projectFromRequest.getAddress().setId(UUID.randomUUID().toString());
                AuditDetails auditDetails = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), null, true);
                projectFromRequest.getAddress().setAuditDetails(auditDetails);
            } else if (projectFromDB != null) {
                projectFromRequest.getAddress().setAuditDetails(projectFromDB.getAddress().getAuditDetails());
                AuditDetails auditDetailsAddress = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), projectFromDB.getAddress().getAuditDetails(), false);
                projectFromRequest.getAddress().setAuditDetails(auditDetailsAddress);
            }
        }
    }

    /* Enrich Target with id and audit details if target id is empty or if target already present, update last modified by and last modified time */
    private void enrichProjectTarget(Project projectFromRequest, Project projectFromDB, RequestInfo requestInfo) {
        if (projectFromRequest.getTargets() != null) {
            for (Target target: projectFromRequest.getTargets()) {
                if (StringUtils.isBlank(target.getId())) {
                    target.setId(UUID.randomUUID().toString());
                    AuditDetails auditDetailsForAdd = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), null, true);
                    target.setAuditDetails(auditDetailsForAdd);
                } else if (projectFromDB != null) {
                    String targetId = String.valueOf(target.getId());
                    Target targetFromDB = projectFromDB.getTargets().stream().filter(t -> targetId.equals(String.valueOf(t.getId()))).findFirst().orElse(null);
                    target.setAuditDetails(targetFromDB.getAuditDetails());
                    AuditDetails auditDetailsTarget = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), targetFromDB.getAuditDetails(), false);
                    target.setAuditDetails(auditDetailsTarget);
                }
            }
        }
    }

    /* Enrich Document with id and audit details if document id is empty or if document already present, update last modified by and last modified time */
    private void enrichProjectDocument(Project projectFromRequest, Project projectFromDB, RequestInfo requestInfo) {
        if (projectFromRequest.getDocuments() != null) {
            for (Document document: projectFromRequest.getDocuments()) {
                if (StringUtils.isBlank(document.getId())) {
                    document.setId(UUID.randomUUID().toString());
                    AuditDetails auditDetailsForAdd = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), null, true);
                    document.setAuditDetails(auditDetailsForAdd);
                } else if (projectFromDB != null) {
                    String documentId = String.valueOf(document.getId());
                    Document documentFromDB = projectFromDB.getDocuments().stream().filter(d -> documentId.equals(String.valueOf(d.getId()))).findFirst().orElse(null);
                    document.setAuditDetails(documentFromDB.getAuditDetails());
                    AuditDetails auditDetailsDocument = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), documentFromDB.getAuditDetails(), false);
                    document.setAuditDetails(auditDetailsDocument);
                }
            }
        }
    }

    /* Get Project Number list from IdGen service */
    private List<String> getIdList(RequestInfo requestInfo, String tenantId, String idKey,
                                   String idformat, int count) {
        List<IdResponse> idResponses = idGenRepository.getId(requestInfo, tenantId, idKey, idformat, count).getIdResponses();

        if (CollectionUtils.isEmpty(idResponses)) {
            log.error("No ids returned from idgen Service");
            throw new CustomException("IDGEN ERROR", "No ids returned from idgen Service");
        }

        return idResponses.stream()
                .map(IdResponse::getId).collect(Collectors.toList());
    }
}
