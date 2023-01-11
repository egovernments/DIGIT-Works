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

    public void enrichProjectOnCreate(ProjectRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        List<Project> projects = request.getProjects();

        String rootTenantId = projects.get(0).getTenantId().split("\\.")[0];

        //Get Project Ids from Idgen Service for Number of projects present in Projects
        List<String> projectNumbers = getIdList(requestInfo, rootTenantId
                , config.getIdgenProjectNumberName(), config.getIdgenProjectNumberFormat(), projects.size());

        int i = 0;
        for (Project project: projects) {

            //Get Audit details from requestInfo
            AuditDetails auditDetails = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), null, true);
            project.setAuditDetails(auditDetails);
            project.setId(UUID.randomUUID().toString());

            if (projectNumbers != null && !projectNumbers.isEmpty()) {
                projects.get(i).setProjectNumber(projectNumbers.get(i));
                i++;
            }

            for (Target target: project.getTargets()) {
                target.setId(UUID.randomUUID().toString());
                target.setAuditDetails(auditDetails);
            }

            for (Document document: project.getDocuments()) {
                document.setId(UUID.randomUUID().toString());
                document.setAuditDetails(auditDetails);
            }

            if (project.getAddress() != null) {
                project.getAddress().setAuditDetails(auditDetails);
                project.getAddress().setId(UUID.randomUUID().toString());
            }
        }

    }

    public void enrichProjectOnUpdate(ProjectRequest request, List<Project> projectsFromDB) {
        RequestInfo requestInfo = request.getRequestInfo();
        List<Project> projectsFromRequest = request.getProjects();
        //Get Audit details from requestInfo for new data
        AuditDetails auditDetailsForAdd = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), null, true);

        for (Project project : projectsFromRequest) {
            String projectId = String.valueOf(project.getId());

            Project projectFromDB = projectsFromDB.stream().filter(p -> projectId.equals(String.valueOf(p.getId()))).findFirst().orElse(null);

            //Updating lastModifiedTime and lastModifiedBy
            project.setAuditDetails(projectFromDB.getAuditDetails());
            AuditDetails auditDetails = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), projectFromDB.getAuditDetails(), false);
            project.setAuditDetails(auditDetails);

            //Add address if address is not present for existing project and address id is empty
            if (project.getAddress() != null && StringUtils.isBlank(project.getAddress().getId())) {
                project.getAddress().setId(UUID.randomUUID().toString());
                project.setAuditDetails(auditDetailsForAdd);
            }

            //Add new target to backend if id is empty or update lastModifiedTime and lastModifiedBy if id exists
            for (Target target: project.getTargets()) {
                if (StringUtils.isBlank(target.getId())) {
                    target.setId(UUID.randomUUID().toString());
                    target.setAuditDetails(auditDetailsForAdd);
                } else {
                    String targetId = String.valueOf(target.getId());
                    Target targetFromDB = project.getTargets().stream().filter(t -> targetId.equals(String.valueOf(t.getId()))).findFirst().orElse(null);
                    target.setAuditDetails(targetFromDB.getAuditDetails());
                    AuditDetails auditDetailsTarget = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), targetFromDB.getAuditDetails(), false);
                    target.setAuditDetails(auditDetailsTarget);
                }
            }

            //Add new document to backend if id is empty or update lastModifiedTime and lastModifiedBy if id exists
            for (Document document: project.getDocuments()) {
                if (StringUtils.isBlank(document.getId())) {
                    document.setId(UUID.randomUUID().toString());
                    document.setAuditDetails(auditDetailsForAdd);
                } else {
                    String documentId = String.valueOf(document.getId());
                    Document documentFromDB = project.getDocuments().stream().filter(d -> documentId.equals(String.valueOf(d.getId()))).findFirst().orElse(null);
                    document.setAuditDetails(documentFromDB.getAuditDetails());
                    AuditDetails auditDetailsDocument = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), documentFromDB.getAuditDetails(), false);
                    document.setAuditDetails(auditDetailsDocument);
                }
            }
        }

    }

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
