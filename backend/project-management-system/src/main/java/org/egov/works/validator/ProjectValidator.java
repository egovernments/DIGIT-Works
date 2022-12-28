package org.egov.works.validator;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.util.MDMSUtils;
import org.egov.works.web.models.Project;
import org.egov.works.web.models.ProjectRequest;
import org.egov.works.web.models.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.works.util.ProjectConstants.*;

@Component
@Slf4j
public class ProjectValidator {

    @Autowired
    MDMSUtils mdmsUtils;

    public void validateCreateProjectRequest(ProjectRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        List<Project> projects = request.getProjects();
        RequestInfo requestInfo = request.getRequestInfo();

        validateRequestInfo(requestInfo, errorMap);
        validateProjectRequest(projects, errorMap, true);

        String tenantId = projects.get(0).getTenantId();
        String rootTenantId = tenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(request, rootTenantId);

        validateMDMSData(projects, mdmsData,  errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    public void valiateSearchProject(ProjectRequest project, Integer limit, Integer offset, String tenantId) {
        Map<String, String> errorMap = new HashMap<>();
        List<Project> projects = project.getProjects();
        RequestInfo requestInfo = project.getRequestInfo();

        validateRequestInfo(requestInfo, errorMap);
        validateProjectRequest(projects, errorMap, false);
        validateProjectRequestParams(limit, offset, tenantId);

        Object mdmsData = mdmsUtils.mDMSCall(project, tenantId.split("\\.")[0]);

        validateMDMSData(projects, mdmsData,  errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    private void validateProjectRequestParams(Integer limit, Integer offset, String tenantId) {
        if (limit == null) {
            throw new CustomException("CREATE_PROJECT", "limit is mandatory");
        }

        if (offset == null) {
            throw new CustomException("CREATE_PROJECT", "offset is mandatory");
        }

        if (StringUtils.isBlank(tenantId)) {
            throw new CustomException("CREATE_PROJECT", "tenantId is mandatory");
        }
    }

    private void validateProjectRequest(List<Project> projects, Map<String, String> errorMap, Boolean isCreate) {
        if (projects == null || projects.size() == 0) {
            throw new CustomException("CREATE_PROJECT", "Projects are mandatory");
        }

        for (Project project: projects) {
            if (project == null) {
                throw new CustomException("PROJECT", "Project is mandatory");
            }
            if (isCreate && project.getTenantId() == null) {
                throw new CustomException("TENANT_ID", "Tenant ID is mandatory");
            }
            if (!isCreate && StringUtils.isBlank(project.getId()) && StringUtils.isBlank(project.getProjectType()) && StringUtils.isBlank(project.getProjectSubType()) && StringUtils.isBlank(project.getReferenceID())
                    && project.getStartDate() == 0 && project.getEndDate() == 0 && StringUtils.isBlank(project.getDepartment())) {
                throw new CustomException("PROJECT_SEARCH_FIELDS", "Any one project search field is required");
            }
            if (StringUtils.isBlank(project.getProjectType()) && StringUtils.isNotBlank(project.getProjectSubType())) {
                errorMap.put("PROJECT", "Project Type must be present for Project sub type");
            }
        }
    }

    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
        if (requestInfo == null) {
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }
    }

    private void validateMDMSData(List<Project> projects, Object mdmsData, Map<String, String> errorMap) {
        final String jsonPathForWorksTypeOfProjectList = "$.MdmsRes." + MDMS_WORKS_MODULE_NAME + "." + MASTER_PROJECTTYPE + ".*.code";
        String jsonPathForWorksSubTypeOfProject = "$.MdmsRes." + MDMS_WORKS_MODULE_NAME + "." + MASTER_PROJECTTYPE + ".[?(@.active==true && @.code == '{code}')].subTypes.[?(@.active==true && @.code=='{subCode}')]";
        final String jsonPathForWorksDepartment = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_DEPARTMENT + ".*.code";
        final String jsonPathForWorksBeneficiaryType = "$.MdmsRes." + MDMS_WORKS_MODULE_NAME + "." + MASTER_BENEFICIART_TYPE + ".*.code";
        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";
        final String PLACEHOLDER_CODE = "{code}";
        final String PLACEHOLDER_SUB_CODE = "{subCode}";

        List<Object> deptRes = null;
        List<Object> beneficiaryTypeRes = null;
        List<Object> typeOfProjectRes = null;
        List<Object> tenantRes = null;
        List<Object> projectSubTypeRes = null;
        try {
            deptRes = JsonPath.read(mdmsData, jsonPathForWorksDepartment);
            beneficiaryTypeRes = JsonPath.read(mdmsData, jsonPathForWorksBeneficiaryType);
            typeOfProjectRes = JsonPath.read(mdmsData, jsonPathForWorksTypeOfProjectList);
            tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        for (Project project: projects) {
            boolean isProjectPresentInMDMS = true;
            if (!StringUtils.isBlank(project.getProjectType()) && !typeOfProjectRes.contains(project.getProjectType())) {
                isProjectPresentInMDMS = false;
                errorMap.put("INVALID_PROJECT_TYPE", "The project type: " + project.getProjectType() + " is not present in MDMS");
            }
            if (!StringUtils.isBlank(project.getTenantId()) && !tenantRes.contains(project.getTenantId()))
                errorMap.put("INVALID_TENANT", "The tenant: " + project.getTenantId() + " is not present in MDMS");
            if (!StringUtils.isBlank(project.getDepartment()) && !deptRes.contains(project.getDepartment()))
                errorMap.put("INVALID_DEPARTMENT_CODE", "The department code: " + project.getDepartment() + " is not present in MDMS");

            if (isProjectPresentInMDMS && !StringUtils.isBlank(project.getProjectType())) {
                jsonPathForWorksSubTypeOfProject = jsonPathForWorksSubTypeOfProject.replace(PLACEHOLDER_CODE, project.getProjectType()).replace(PLACEHOLDER_SUB_CODE, project.getProjectSubType());
                projectSubTypeRes = JsonPath.read(mdmsData, jsonPathForWorksSubTypeOfProject);

                if (!StringUtils.isBlank(project.getProjectSubType()) && CollectionUtils.isEmpty(projectSubTypeRes))
                    errorMap.put("INVALID_PROJECT_SUB_TYPE", "The project subtype : " + project.getProjectSubType() + " is not present in MDMS for project type : " + project.getProjectType());
            }

            for (Target target: project.getTargets()) {
                if (!StringUtils.isBlank(target.getBeneficiaryType()) && !beneficiaryTypeRes.contains(target.getBeneficiaryType()))
                    errorMap.put("INVALID_BENEFICIARY_TYPE", "The beneficiary Type: " + target.getBeneficiaryType() + " is not present in MDMS");
            }
        }
    }
}
