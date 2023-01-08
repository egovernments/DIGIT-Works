package org.egov.works.validator;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.util.LocationUtil;
import org.egov.works.util.MDMSUtils;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.util.ProjectConstants.*;

@Component
@Slf4j
public class ProjectValidator {

    @Autowired
    MDMSUtils mdmsUtils;

    @Autowired
    LocationUtil locationUtil;

    public void validateCreateProjectRequest(ProjectRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        List<Project> projects = request.getProjects();
        RequestInfo requestInfo = request.getRequestInfo();

        validateRequestInfo(requestInfo, errorMap);
        validateProjectRequest(projects, errorMap);

        String tenantId = projects.get(0).getTenantId();
        String rootTenantId = tenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(request, rootTenantId);

        validateMDMSData(projects, mdmsData,  errorMap);

        List<String> locationsForValidation = getLocationForValidation(projects);
        if (locationsForValidation.size() > 0)
            validateLocation(locationsForValidation, tenantId, requestInfo, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    public void valiateSearchProject(ProjectRequest project, Integer limit, Integer offset, String tenantId) {
        Map<String, String> errorMap = new HashMap<>();
        List<Project> projects = project.getProjects();
        RequestInfo requestInfo = project.getRequestInfo();

        validateRequestInfo(requestInfo, errorMap);
        validateSearchProjectRequest(projects, errorMap);
        validateProjectRequestParams(limit, offset, tenantId);

        Object mdmsData = mdmsUtils.mDMSCall(project, tenantId.split("\\.")[0]);

        validateMDMSData(projects, mdmsData,  errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    public void validateUpdateProjectRequest(ProjectRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        List<Project> projects = request.getProjects();
        RequestInfo requestInfo = request.getRequestInfo();

        validateRequestInfo(requestInfo, errorMap);
        validateProjectRequest(projects, errorMap);

        String tenantId = projects.get(0).getTenantId();
        String rootTenantId = tenantId.split("\\.")[0];

        for (Project project: projects) {
            if (StringUtils.isBlank(project.getId())) {
                throw new CustomException("UPDATE_PROJECT", "Project Id is mandatory");
            }
        }

        Object mdmsData = mdmsUtils.mDMSCall(request, rootTenantId);

        validateMDMSData(projects, mdmsData,  errorMap);

        List<String> locationsForValidation = getLocationForValidation(projects);
        if (locationsForValidation.size() > 0)
            validateLocation(locationsForValidation, tenantId, requestInfo, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    private void validateProjectRequestParams(Integer limit, Integer offset, String tenantId) {
        if (limit == null) {
            throw new CustomException("SEARCH_PROJECT", "limit is mandatory");
        }

        if (offset == null) {
            throw new CustomException("SEARCH_PROJECT", "offset is mandatory");
        }

        if (StringUtils.isBlank(tenantId)) {
            throw new CustomException("SEARCH_PROJECT", "tenantId is mandatory");
        }
    }

    private void validateProjectRequest(List<Project> projects, Map<String, String> errorMap) {
        if (projects == null || projects.size() == 0) {
            throw new CustomException("PROJECT", "Projects are mandatory");
        }

        for (Project project: projects) {
            if (project == null) {
                throw new CustomException("PROJECT", "Project is mandatory");
            }
            if (StringUtils.isBlank(project.getTenantId())) {
                throw new CustomException("TENANT_ID", "Tenant ID is mandatory");
            }
            if (StringUtils.isBlank(project.getName())) {
                throw new CustomException("PROJECT_NAME", "Project Name is mandatory");
            }
            if (StringUtils.isBlank(project.getProjectType())) {
                throw new CustomException("PROJECT_TYPE", "Project Type is mandatory");
            }
            if (StringUtils.isBlank(project.getProjectType()) && StringUtils.isNotBlank(project.getProjectSubType())) {
                errorMap.put("PROJECT", "Project Type must be present for Project sub type");
            }
            if (!project.getTenantId().equals(projects.get(0).getTenantId())) {
                throw new CustomException("MULTIPLE_TENANTS", "All registers must have same tenant Id. Please create new request for different tentant id");
            }
        }
    }

    private void validateSearchProjectRequest(List<Project> projects, Map<String, String> errorMap) {
        if (projects == null || projects.size() == 0) {
            throw new CustomException("PROJECT", "Projects are mandatory");
        }

        for (Project project: projects) {
            if (project == null) {
                throw new CustomException("PROJECT", "Project is mandatory");
            }
            if (StringUtils.isBlank(project.getId()) && StringUtils.isBlank(project.getProjectType())
                    && StringUtils.isBlank(project.getName()) && StringUtils.isBlank(project.getProjectNumber())
                    && StringUtils.isBlank(project.getProjectSubType()) && StringUtils.isBlank(project.getReferenceID())
                    && project.getStartDate() == 0 && project.getEndDate() == 0 && StringUtils.isBlank(project.getDepartment())) {
                throw new CustomException("PROJECT_SEARCH_FIELDS", "Any one project search field is required");
            }
            if (StringUtils.isBlank(project.getProjectType()) && StringUtils.isNotBlank(project.getProjectSubType())) {
                errorMap.put("PROJECT", "Project Type must be present for Project sub type");
            }

            if (!project.getTenantId().equals(projects.get(0).getTenantId())) {
                throw new CustomException("MULTIPLE_TENANTS", "All registers must have same tenant Id. Please create new request for different tentant id");
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
        String jsonPathForWorksSubTypeOfProject = "$.MdmsRes." + MDMS_WORKS_MODULE_NAME + "." + MASTER_PROJECTTYPE + ".[?(@.active==true && @.code == '{code}')].projectSubType";
        final String jsonPathForWorksDepartment = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_DEPARTMENT + ".*.code";
        String jsonPathForBeneficiaryOfProject = "$.MdmsRes." + MDMS_WORKS_MODULE_NAME + "." + MASTER_PROJECTTYPE + ".[?(@.active==true && @.code == '{code}')].beneficiary";
        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";
        final String PLACEHOLDER_CODE = "{code}";

        List<Object> deptRes = null;
        List<Object> beneficiaryTypeRes = null;
        List<Object> typeOfProjectRes = null;
        List<Object> tenantRes = null;
        List<List<Object>> projectSubTypeRes = null;
        try {
            deptRes = JsonPath.read(mdmsData, jsonPathForWorksDepartment);
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
                jsonPathForWorksSubTypeOfProject = jsonPathForWorksSubTypeOfProject.replace(PLACEHOLDER_CODE, project.getProjectType());
                projectSubTypeRes = JsonPath.read(mdmsData, jsonPathForWorksSubTypeOfProject);

                if (!StringUtils.isBlank(project.getProjectSubType()) && !projectSubTypeRes.get(0).contains(project.getProjectSubType())) {
                    errorMap.put("INVALID_PROJECT_SUB_TYPE", "The project subtype : " + project.getProjectSubType() + " is not present in MDMS for project type : " + project.getProjectType());
                }
            }

            if (project.getTargets() != null) {
                for (Target target: project.getTargets()) {
                    if (isProjectPresentInMDMS && !StringUtils.isBlank(target.getBeneficiaryType())) {
                        jsonPathForBeneficiaryOfProject = jsonPathForBeneficiaryOfProject.replace(PLACEHOLDER_CODE, project.getProjectType());
                        beneficiaryTypeRes = JsonPath.read(mdmsData, jsonPathForBeneficiaryOfProject);

                        if (!StringUtils.isBlank(target.getBeneficiaryType()) && !beneficiaryTypeRes.contains(target.getBeneficiaryType())) {
                            errorMap.put("INVALID_BENEFICIARY_TYPE", "The beneficiary Type : " + target.getBeneficiaryType() + " is not present in MDMS for project type : " + project.getProjectType());
                        }
                    }
                }
            }
        }
    }

    private List<String> getLocationForValidation(List<Project> projects) {
        List<String> locations = new ArrayList<>();
        for (Project project: projects) {
            if (project.getAddress() != null && project.getAddress().getLocality() != null) {
                locations.add(project.getAddress().getLocality());
            }
        }
        return locations;
    }

    private void validateLocation(List<String> locations, String tenantId, RequestInfo requestInfo, Map<String, String> errorMap) {
        final String jsonPathForBoundryLocation = "$.MdmsRes." + MDMS_LOCATION_MODULE_NAME + "." + MASTER_BOUNDARY_LOCATION + ".*";

        if (locations.size() > 0) {
            Object locResult = locationUtil.getLocationFromMDMS(locations, tenantId, requestInfo, errorMap);
            if (locResult != null) {
                List<Object> locRes = JsonPath.read(locResult, jsonPathForBoundryLocation);
                if (CollectionUtils.isEmpty(locRes))
                    errorMap.put("LOCATION_NOT_FOUND", "Locations doesn't exist in the system");
                for (String location: locations) {
                    if(!locRes.contains(location)) {
                        errorMap.put("LOCATION_NOT_FOUND", "Location " + location + " doesn't exist in the system");
                    }
                }
            } else {
                errorMap.put("LOCATION_NOT_FOUND", "Locations doesn't exist in the system");
            }
        }
    }

    public void validateUpdateAgainstDB(List<Project> projectsFromRequest, List<Project> projectsFromDB) {
        if (CollectionUtils.isEmpty(projectsFromDB)) {
            throw new CustomException("INVALID_PROJECT_MODIFY", "The records that you are trying to update does not exists in the system");
        }

        for (Project project: projectsFromRequest) {
            Project projectFromDB = projectsFromDB.stream().filter(p -> p.getId().equals(project.getId())).findFirst().orElse(null);

            if (projectFromDB == null) {
                throw new CustomException("INVALID_PROJECT_MODIFY", "The project id " + project.getId() + " that you are trying to update does not exists for the project");
            }

            Set<String> targetIdsFromDB = projectFromDB.getTargets().stream().map(Target :: getId).collect(Collectors.toSet());
            for (Target target: project.getTargets()) {
                if (StringUtils.isNotBlank(target.getId()) && !targetIdsFromDB.contains(target.getId())) {
                    throw new CustomException("INVALID_PROJECT_MODIFY", "The target id " + target.getId() + " that you are trying to update does not exists for the project");
                }
            }

            Set<String> documentIdsFromDB = projectFromDB.getDocuments().stream().map(Document:: getId).collect(Collectors.toSet());
            for (Document document: project.getDocuments()) {
                if (StringUtils.isNotBlank(document.getId()) && !documentIdsFromDB.contains(document.getId())) {
                    throw new CustomException("INVALID_PROJECT_MODIFY", "The document id " + document.getId() + " that you are trying to update does not exists for the project");
                }
            }

            if (projectFromDB.getAddress() != null && StringUtils.isBlank(project.getAddress().getId())) {
                throw new CustomException("INVALID_PROJECT_MODIFY", "The address with id " + projectFromDB.getAddress().getId() + " already exists for the project");
            }

            if (projectFromDB.getAddress() != null && StringUtils.isNotBlank(project.getAddress().getId()) && !projectFromDB.getAddress().getId().equals(project.getAddress().getId())) {
                throw new CustomException("INVALID_PROJECT_MODIFY", "The address id " + project.getAddress().getId() + " that you are trying to update does not exists for the project");
            }

        }

    }
}
