package org.egov.works.enrichment;

import digit.models.coremodels.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.util.ProjectServiceUtil;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
                }
            }
        }

    }
}
