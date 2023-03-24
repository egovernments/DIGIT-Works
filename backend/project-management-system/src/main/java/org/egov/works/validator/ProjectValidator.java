package org.egov.works.validator;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ProjectConfiguration;
import org.egov.works.util.BoundaryUtil;
import org.egov.works.util.MDMSUtils;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.print.Doc;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.util.ProjectConstants.*;

@Component
@Slf4j
public class ProjectValidator {

    @Autowired
    MDMSUtils mdmsUtils;

    @Autowired
    BoundaryUtil boundaryUtil;

    @Autowired
    ProjectConfiguration config;

    /* Validates create Project request body */
    public void validateCreateProjectRequest(ProjectRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        RequestInfo requestInfo = request.getRequestInfo();

        //Verify if RequestInfo and UserInfo is present
        validateRequestInfo(requestInfo);
        //Verify if project request and mandatory fields are present
        validateProjectRequest(request.getProjects());
        //Verify if project request have multiple tenant Ids
        validateMultipleTenantIds(request);

        String tenantId = request.getProjects().get(0).getTenantId();
        //Verify MDMS Data
        validateRequestMDMSData(request, tenantId, errorMap);

        //Get boundaries in list from all Projects in request body for validation
        Map<String, List<String>> boundariesForValidation = getBoundaryForValidation(request.getProjects());
        validateBoundary(boundariesForValidation, tenantId, requestInfo, errorMap);
        log.info("Boundaries in request validated with Location Service");

        // Verify provided documentIds are valid.
        validateDocumentIds(request);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    /* Validates search Project request body and parameters*/
    public void validateSearchProjectRequest(ProjectRequest project, Integer limit, Integer offset, String tenantId, Long createdFrom, Long createdTo) {
        Map<String, String> errorMap = new HashMap<>();
        RequestInfo requestInfo = project.getRequestInfo();

        //Verify if RequestInfo and UserInfo is present
        validateRequestInfo(requestInfo);
        //Verify if search project request parameters are valid
        validateSearchProjectRequestParams(limit, offset, tenantId, createdFrom, createdTo);
        //Verify if search project request is valid
        validateSearchProjectRequest(project.getProjects(), tenantId, createdFrom);
        //Verify if project request have multiple tenant Ids
        validateMultipleTenantIds(project);
        //Verify MDMS Data
        validateRequestMDMSData(project, tenantId, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    /* Validates Update Project request body */
    public void validateUpdateProjectRequest(ProjectRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        RequestInfo requestInfo = request.getRequestInfo();

        //Verify if RequestInfo and UserInfo is present
        validateRequestInfo(requestInfo);
        //Verify Project request and if mandatory fields are present
        validateProjectRequest(request.getProjects());
        //Verify if project request have multiple tenant Ids
        validateMultipleTenantIds(request);

        //Verify if Project id is present
        for (Project project: request.getProjects()) {
            if (StringUtils.isBlank(project.getId())) {
                log.error("Project Id is mandatory");
                throw new CustomException("UPDATE_PROJECT", "Project Id is mandatory");
            }
        }

        String tenantId = request.getProjects().get(0).getTenantId();
        //Verify MDMS Data
        validateRequestMDMSData(request, tenantId, errorMap);

        //Get boundaries in list from all Projects in request body for validation
        Map<String, List<String>> boundariesForValidation = getBoundaryForValidation(request.getProjects());
        validateBoundary(boundariesForValidation, tenantId, requestInfo, errorMap);
        log.info("Boundaries in request validated with Location Service");

        // Verify provided documentIds are valid.
        validateDocumentIds(request);


        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    /* Validates if search Project request parameters are valid */
    private void validateSearchProjectRequestParams(Integer limit, Integer offset, String tenantId, Long createdFrom, Long createdTo) {
        if (limit == null) {
            log.error("limit is mandatory parameter in Project search");
            throw new CustomException("SEARCH_PROJECT.LIMIT", "limit is mandatory for Project Search");
        }

        if (offset == null) {
            log.error("offset is mandatory parameter in Project search");
            throw new CustomException("SEARCH_PROJECT.OFFSET", "offset is mandatory for Project Search");
        }

        if (StringUtils.isBlank(tenantId)) {
            log.error("tenantId is mandatory parameter in Project search");
            throw new CustomException("SEARCH_PROJECT.TENANT_ID", "tenantId is mandatory for Project Search");
        }

        if ((createdFrom == null || createdFrom == 0) && (createdTo != null && createdTo != 0)) {
            log.error("Created From date is required if Created To date is given");
            throw new CustomException("INVALID_DATE_PARAM", "Created From date is required if Created To date is given");
        }

        if ((createdFrom != null && createdTo != null  && createdTo != 0) && (createdFrom.compareTo(createdTo) > 0)) {
            log.error("Created From in Project search parameters should be less than Created To");
            throw new CustomException("INVALID_DATE", "Created From should be less than Created To");
        }
    }

    /* Validates Project request body for create and update apis */
    private void validateProjectRequest(List<Project> projects) {
        Map<String, String> errorMap = new HashMap<>();

        if (projects == null || projects.size() == 0) {
            log.error("Project list is empty. Projects is mandatory");
            throw new CustomException("PROJECT", "Projects are mandatory");
        }

        for (Project project: projects) {
            if (project == null) {
                log.error("Project is mandatory in Projects");
                throw new CustomException("PROJECT", "Project is mandatory");
            }
            if (StringUtils.isBlank(project.getTenantId())) {
                log.error("Tenant ID is mandatory in Project request body");
                errorMap.put("TENANT_ID", "Tenant ID is mandatory");
            }
            if ((project.getStartDate() != null && project.getEndDate() != null  && project.getEndDate() != 0) && (project.getStartDate().compareTo(project.getEndDate()) > 0)) {
                log.error("Start date should be less than end date");
                errorMap.put("INVALID_DATE", "Start date should be less than end date");
            }
            if (project.getAddress() != null && StringUtils.isNotBlank(project.getAddress().getBoundary()) && StringUtils.isBlank(project.getAddress().getBoundaryType()) ) {
                log.error("Boundary Type is mandatory if boundary is present  in Project request body");
                errorMap.put("BOUNDARY", "Boundary Type is mandatory if boundary is present in Project request body");
            }
            if (project.getDocuments() != null && !project.getDocuments().isEmpty()) {
                for (Document document: project.getDocuments()) {
                    if (StringUtils.isBlank(document.getFileStore())) {
                        log.error("Filestore Id is mandatory for document");
                        errorMap.put("FILESTORE_ID", "FileStore Id must be present for document");
                    }
                }
            }
        }

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    /* Validates Search Project Request body */
    private void validateSearchProjectRequest(List<Project> projects, String tenantId, Long createdFrom) {
        if (projects == null || projects.size() == 0) {
            log.error("Project list is empty. Projects is mandatory");
            throw new CustomException("PROJECT", "Projects are mandatory");
        }

        for (Project project: projects) {
            if (project == null) {
                log.error("Project is mandatory in Projects");
                throw new CustomException("PROJECT", "Project is mandatory");
            }
            if (StringUtils.isBlank(project.getTenantId())) {
                log.error("Tenant ID is mandatory in Project request body");
                throw new CustomException("TENANT_ID", "Tenant ID is mandatory");
            }
            if (StringUtils.isBlank(project.getId()) && StringUtils.isBlank(project.getProjectType())
                    && StringUtils.isBlank(project.getName()) && StringUtils.isBlank(project.getProjectNumber())
                    && StringUtils.isBlank(project.getProjectSubType())
                    && (project.getStartDate() == null || project.getStartDate() == 0)
                    && (project.getEndDate() == null || project.getEndDate() == 0)
                    && (createdFrom == null || createdFrom == 0)
                    && (project.getAddress() == null || StringUtils.isBlank(project.getAddress().getBoundary()))) {
                log.error("Any one project search field is required for Project Search");
                throw new CustomException("PROJECT_SEARCH_FIELDS", "Any one project search field is required");
            }

            if (!project.getTenantId().equals(tenantId)) {
                log.error("Tenant Id must be same in URL param as well as project request body");
                throw new CustomException("MULTIPLE_TENANTS", "Tenant Id must be same in URL param and project request");
            }

            if ((project.getStartDate() != null && project.getEndDate() != null && project.getEndDate() != 0) && (project.getStartDate().compareTo(project.getEndDate()) > 0)) {
                log.error("Start date should be less than end date");
                throw new CustomException("INVALID_DATE", "Start date should be less than end date");
            }

            if ((project.getStartDate() == null || project.getStartDate() == 0) && (project.getEndDate() != null && project.getEndDate() != 0)) {
                log.error("Start date is required if end date is passed");
                throw new CustomException("INVALID_DATE", "Start date is required if end date is passed");
            }

        }
    }

    /* Validates Request Info and User Info */
    private void validateRequestInfo(RequestInfo requestInfo) {
        if (requestInfo == null) {
            log.error("Request info is mandatory");
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            log.error("UserInfo is mandatory in RequestInfo");
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            log.error("UUID is mandatory in UserInfo");
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }
    }

    /* Validates the request data against MDMS data */
    private void  validateMDMSData(List<Project> projects, Object mdmsData, Map<String, String> errorMap) {
        final String jsonPathForMDMSTypeOfProjectList = "$.MdmsRes." + config.getMdmsModule() + "." + MASTER_PROJECTTYPE + ".[?(@.active==true)].code";
        final String jsonPathForMDMSNatureOfWorkList = "$.MdmsRes." + config.getMdmsModule() + "." + MASTER_NATUREOFWORK + ".[?(@.active==true)].code";
        final String jsonPathForDepartment = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_DEPARTMENT + ".*.code";
        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";

        List<Object> deptRes = null;
        List<Object> typeOfProjectRes = null;
        List<Object> tenantRes = null;
        List<Object> natureOfWorkRes = null;
        try {
            deptRes = JsonPath.read(mdmsData, jsonPathForDepartment);
            typeOfProjectRes = JsonPath.read(mdmsData, jsonPathForMDMSTypeOfProjectList);
            tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);
            if (projects.stream().anyMatch(p -> StringUtils.isNotBlank(p.getNatureOfWork()))) {
                natureOfWorkRes = JsonPath.read(mdmsData, jsonPathForMDMSNatureOfWorkList);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        for (Project project: projects) {
            log.info("Validate Project type with MDMS");
            if (!StringUtils.isBlank(project.getProjectType()) && !typeOfProjectRes.contains(project.getProjectType())) {
                log.error("The project type: " + project.getProjectType() + " is not present in MDMS");
                errorMap.put("INVALID_PROJECT_TYPE", "The project type: " + project.getProjectType() + " is not present in MDMS");
            }
            log.info("Validate Tenant Id with MDMS");
            if (!StringUtils.isBlank(project.getTenantId()) && !tenantRes.contains(project.getTenantId())) {
                log.error("The tenant: " + project.getTenantId() + " is not present in MDMS");
                errorMap.put("INVALID_TENANT", "The tenant: " + project.getTenantId() + " is not present in MDMS");
            }
            log.info("Validate Department with MDMS");
            if (!StringUtils.isBlank(project.getDepartment()) && !deptRes.contains(project.getDepartment())) {
                log.error("The department code: " + project.getDepartment() + " is not present in MDMS");
                errorMap.put("INVALID_DEPARTMENT_CODE", "The department code: " + project.getDepartment() + " is not present in MDMS");
            }
            log.info("Validate Nature of Work with MDMS");
            if (!StringUtils.isBlank(project.getNatureOfWork()) && natureOfWorkRes != null && !natureOfWorkRes.contains(project.getNatureOfWork())) {
                log.error("The nature of work: " + project.getNatureOfWork() + " is not present in MDMS");
                errorMap.put("INVALID_NATURE_OF_WORK", "The nature of work: " + project.getNatureOfWork() + " is not present in MDMS");
            }
        }
    }

    /* Validate Project Request MDMS data */
    private void validateRequestMDMSData(ProjectRequest request, String tenantId, Map<String, String> errorMap) {
        String rootTenantId = tenantId.split("\\.")[0];

        //Get MDMS data using create project request and tenantId
        Object mdmsData = mdmsUtils.mDMSCall(request, rootTenantId);

        validateMDMSData(request.getProjects(), mdmsData,  errorMap);
        log.info("Request data validated with MDMS");
    }

    /* Returns boundaries map for all Projects in request body with key boundaryType and value as list of all boundaries corresponding to boundaryType*/
    private Map<String, List<String>> getBoundaryForValidation(List<Project> projects) {
        Map<String, List<String>> boundariesMap = new HashMap<>();
        for (Project project: projects) {

            if (project.getAddress() != null && StringUtils.isNotBlank(project.getAddress().getBoundary())) {
                String boundaryType = project.getAddress().getBoundaryType();
                String boundary = project.getAddress().getBoundary();

                // If the boundary type already exists in the map, add the boundary to the existing list
                if (boundariesMap.containsKey(boundaryType)) {
                    boundariesMap.get(boundaryType).add(boundary);
                }
                // If the boundary type does not exist in the map, create a new list and add the boundary to it
                else {
                    List<String> boundaries = new ArrayList<>();
                    boundaries.add(boundary);
                    boundariesMap.put(boundaryType, boundaries);
                }
            }
        }
        return boundariesMap;
    }

    /* Validates Boundary data with location service */
    private void validateBoundary(Map<String, List<String>> boundaries, String tenantId, RequestInfo requestInfo, Map<String, String> errorMap) {
        if (!boundaries.isEmpty()) {
            boundaryUtil.validateBoundaryDetails(boundaries, tenantId, requestInfo, config.getLocationHierarchyType());
        }
    }

    /* Validates projects data in update request against projects data fetched from database */
    public void validateUpdateAgainstDB(List<Project> projectsFromRequest, List<Project> projectsFromDB) {
        if (CollectionUtils.isEmpty(projectsFromDB)) {
            log.error("The project records that you are trying to update does not exists in the system");
            throw new CustomException("INVALID_PROJECT_MODIFY", "The records that you are trying to update does not exists in the system");
        }

        for (Project project: projectsFromRequest) {
            Project projectFromDB = projectsFromDB.stream().filter(p -> p.getId().equals(project.getId())).findFirst().orElse(null);

            if (projectFromDB == null) {
                log.error("The project id " + project.getId() + " that you are trying to update does not exists for the project");
                throw new CustomException("INVALID_PROJECT_MODIFY", "The project id " + project.getId() + " that you are trying to update does not exists for the project");
            }

            if (projectFromDB.getTargets() != null) {
                Set<String> targetIdsFromDB = projectFromDB.getTargets().stream().filter(t -> !t.getIsDeleted()).map(Target :: getId).collect(Collectors.toSet());
                if (project.getTargets() != null) {
                    for (Target target: project.getTargets()) {
                        if (StringUtils.isNotBlank(target.getId()) && !targetIdsFromDB.contains(target.getId())) {
                            log.error("The target id " + target.getId() + " that you are trying to update does not exists for the project " + projectFromDB.getProjectNumber());
                            throw new CustomException("INVALID_PROJECT_MODIFY.TARGET", "The target id " + target.getId() + " that you are trying to update does not exists for the project " + projectFromDB.getProjectNumber());
                        }
                    }
                }
            } else {
                if (project.getTargets() != null && !project.getTargets().isEmpty()) {
                    for (Target target: project.getTargets()) {
                        if (StringUtils.isNotBlank(target.getId())) {
                            log.error("The target id " + target.getId() + " that you are trying to update does not exists for the project " + projectFromDB.getProjectNumber());
                            throw new CustomException("INVALID_PROJECT_MODIFY.TARGET", "The target id " + target.getId() + " that you are trying to update does not exists for the project " + projectFromDB.getProjectNumber());
                        }
                    }
                }
            }

            if (projectFromDB.getDocuments() != null) {
                Set<String> documentIdsFromDB = projectFromDB.getDocuments().stream().map(Document:: getId).collect(Collectors.toSet());
                if (project.getDocuments() != null) {
                    for (Document document: project.getDocuments()) {
                        if (StringUtils.isNotBlank(document.getId()) && !documentIdsFromDB.contains(document.getId())) {
                            log.error("The document id " + document.getId() + " that you are trying to update does not exists for the project " + projectFromDB.getProjectNumber());
                            throw new CustomException("INVALID_PROJECT_MODIFY.DOCUMENT", "The document id " + document.getId() + " that you are trying to update does not exists for the project " + projectFromDB.getProjectNumber());
                        }
                    }
                }
            } else {
                if (project.getDocuments() != null && project.getDocuments().size() > 0) {
                    for (Document document: project.getDocuments()) {
                        if (StringUtils.isNotBlank(document.getId())) {
                            log.error("The document id " + document.getId() + " that you are trying to update does not exists for the project " + projectFromDB.getProjectNumber());
                            throw new CustomException("INVALID_PROJECT_MODIFY.DOCUMENT", "The document id " + document.getId() + " that you are trying to update does not exists for the project " + projectFromDB.getProjectNumber());
                        }
                    }
                }
            }


            //Checks for a project if address already present in DB
            if ((projectFromDB.getAddress() != null && projectFromDB.getAddress().getId() != null) && project.getAddress() != null && StringUtils.isBlank(project.getAddress().getId())) {
                log.error("The address with id " + projectFromDB.getAddress().getId() + " already exists for the project");
                throw new CustomException("INVALID_PROJECT_MODIFY.ADDRESS", "The address with id " + projectFromDB.getAddress().getId() + " already exists for the project " + projectFromDB.getProjectNumber());
            }

            if ( project.getAddress() != null
                    && StringUtils.isNotBlank(project.getAddress().getId())
                    && (projectFromDB.getAddress() == null || StringUtils.isBlank(projectFromDB.getAddress().getId()) || !projectFromDB.getAddress().getId().equals(project.getAddress().getId())) ) {
                log.error("The address id " + project.getAddress().getId() + " that you are trying to update does not exists for the project");
                throw new CustomException("INVALID_PROJECT_MODIFY.ADDRESS", "The address id " + project.getAddress().getId() + " that you are trying to update does not exists for the project " + projectFromDB.getProjectNumber());
            }

        }

    }

    /* Validates if all Projects have same tenant Id */
    private void validateMultipleTenantIds(ProjectRequest projectRequest) {
        List<Project> projects = projectRequest.getProjects();
        String firstTenantId = projects.get(0).getTenantId();
        if (projects.stream().anyMatch(p -> !p.getTenantId().equals(firstTenantId))) {
            log.error("All projects in Project request must have same tenant Id");
            throw new CustomException("MULTIPLE_TENANTS", "All projects must have same tenant Id. Please create new request for different tentant id");
        }
    }

    /* Validate document Ids */
    private void validateDocumentIds(ProjectRequest projectRequest) {
        if ("TRUE".equalsIgnoreCase(config.getDocumentIdVerificationRequired())) {
            //TODO
            // For now throwing exception. Later implementation will be done
            log.error("Document service not integrated yet");
            throw new CustomException("SERVICE_UNAVAILABLE", "Service not integrated yet");
        }
    }

    /* Validates parent data in create request against projects data fetched from database */
    public void validateParentAgainstDB(List<Project> projects, List<Project> parentProjects) {
        Set<String> parentProjectIds = parentProjects.stream().map(Project :: getId).collect(Collectors.toSet());
        for (Project project: projects) {
            if (StringUtils.isNotBlank(project.getParent()) && !parentProjectIds.contains(project.getParent())) {
                log.error("The parent project with id " + project.getParent() + " does not exists in the system");
                throw new CustomException("INVALID_PARENT_PROJECT", "The parent project with id " + project.getParent() + " does not exists in the system");
            }
        }
        log.info("Parent projects validated against DB");
    }
}
