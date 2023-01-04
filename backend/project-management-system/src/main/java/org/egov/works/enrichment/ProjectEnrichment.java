package org.egov.works.enrichment;

import digit.models.coremodels.AuditDetails;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.util.ProjectServiceUtil;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectEnrichment {

    @Autowired
    private ProjectServiceUtil projectServiceUtil;

    public void enrichCreateProject(ProjectRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        List<Project> projects = request.getProjects();

        for (Project project: projects) {

            AuditDetails auditDetails = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), null, true);
            project.setAuditDetails(auditDetails);
            project.setId(UUID.randomUUID().toString());

            for (Target target: project.getTargets()) {
                target.setId(UUID.randomUUID().toString());
                target.setAuditDetails(auditDetails);
            }

            for (Document document: project.getDocuments()) {
                document.setId(UUID.randomUUID().toString());
                document.setAuditDetails(auditDetails);
            }

            project.getAddress().setAuditDetails(auditDetails);
            project.getAddress().setId(UUID.randomUUID().toString());

            if (project.getAddress().getLocality() != null) {
                project.getAddress().getLocality().setId(UUID.randomUUID().toString());

                List<Boundary> children = project.getAddress().getLocality().getChildren();
                for (Boundary child: children) {
                    child.setId(UUID.randomUUID().toString());
                    child.setParentid(project.getAddress().getLocality().getId());
                    child.setAddressid(project.getAddress().getId());
                }
            }
        }

    }

    public void enrichUpdateProject(ProjectRequest request, List<Project> projectsFromDB) {
        RequestInfo requestInfo = request.getRequestInfo();
        List<Project> projectsFromRequest = request.getProjects();
        AuditDetails auditDetailsForAdd = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), null, true);

        for (Project project : projectsFromRequest) {
            String projectId = String.valueOf(project.getId());

            Project projectFromDB = projectsFromDB.stream().filter(p -> projectId.equals(String.valueOf(p.getId()))).findFirst().orElse(null);

            project.setAuditDetails(projectFromDB.getAuditDetails());
            AuditDetails auditDetails = projectServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), projectFromDB.getAuditDetails(), false);
            project.setAuditDetails(auditDetails);

            if (project.getAddress() != null && StringUtils.isBlank(project.getAddress().getId())) {
                project.getAddress().setId(UUID.randomUUID().toString());
                project.setAuditDetails(auditDetailsForAdd);
            }

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

            if (project.getAddress().getLocality() != null) {
                if (StringUtils.isBlank(project.getAddress().getLocality().getId())) {
                    project.getAddress().getLocality().setId(UUID.randomUUID().toString());
                }
                for (Boundary child: project.getAddress().getLocality().getChildren()) {
                    if (StringUtils.isBlank(child.getId())) {
                        child.setId(UUID.randomUUID().toString());
                    }
                    child.setParentid(project.getAddress().getLocality().getId());
                    child.setAddressid(project.getAddress().getId());
                }
            }
        }

    }
}
