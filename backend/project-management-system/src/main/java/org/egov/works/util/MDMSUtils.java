package org.egov.works.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
import org.egov.works.config.ProjectConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.Project;
import org.egov.works.web.models.ProjectRequest;
import org.egov.works.web.models.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.egov.works.util.ProjectConstants.*;

@Component
@Slf4j
public class MDMSUtils {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ProjectConfiguration config;

    public static final String filterCode = "$.*.code";

    public static final String filterActiveTrue = "$.[?(@.active==true)]";

    public Object mDMSCall(ProjectRequest request, String tenantId) {
        RequestInfo requestInfo = request.getRequestInfo();
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequest(requestInfo, tenantId, request);
        Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
        return result;
    }

    public MdmsCriteriaReq getMDMSRequest(RequestInfo requestInfo, String tenantId, ProjectRequest request) {

        ModuleDetail projectMDMSModuleDetail = getMDMSModuleRequestData(request);
        ModuleDetail projectDepartmentModuleDetail = getDepartmentModuleRequestData(request);
        ModuleDetail projectTenantModuleDetail = getTenantModuleRequestData(request);

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(projectMDMSModuleDetail);
        moduleDetails.add(projectDepartmentModuleDetail);
        moduleDetails.add(projectTenantModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();
        return mdmsCriteriaReq;
    }

    private ModuleDetail getMDMSModuleRequestData(ProjectRequest request) {
        List<MasterDetail> projectMDMSMasterDetails = new ArrayList<>();

        MasterDetail projectTypeMasterDetails = MasterDetail.builder().name(MASTER_PROJECTTYPE)
                .filter(filterActiveTrue)
                .build();
        MasterDetail natureOfWorkMasterDetails = MasterDetail.builder().name(MASTER_NATUREOFWORK)
                .filter(filterActiveTrue)
                .build();
        projectMDMSMasterDetails.add(projectTypeMasterDetails);
        projectMDMSMasterDetails.add(natureOfWorkMasterDetails);


        ModuleDetail projectMDMSModuleDetail = ModuleDetail.builder().masterDetails(projectMDMSMasterDetails)
                .moduleName(config.getMdmsModule()).build();

        return projectMDMSModuleDetail;
    }

    private ModuleDetail getDepartmentModuleRequestData(ProjectRequest request) {
        List<Project> projects = request.getProjects();
        List<MasterDetail> projectDepartmentMasterDetails = new ArrayList<>();

        MasterDetail departmentMasterDetails = MasterDetail.builder().name(MASTER_DEPARTMENT)
                .filter(filterActiveTrue).build();
        projectDepartmentMasterDetails.add(departmentMasterDetails);

        ModuleDetail projectDepartmentModuleDetail = ModuleDetail.builder().masterDetails(projectDepartmentMasterDetails)
                .moduleName(MDMS_COMMON_MASTERS_MODULE_NAME).build();

        return projectDepartmentModuleDetail;
    }

    public StringBuilder getMdmsSearchUrl() {
        return new StringBuilder().append(config.getMdmsHost()).append(config.getMdmsEndPoint());
    }

    private ModuleDetail getTenantModuleRequestData(ProjectRequest request) {
        List<MasterDetail> tenantMasterDetails = new ArrayList<>();

        MasterDetail tenantMasterDetail = MasterDetail.builder().name(MASTER_TENANTS)
                .filter(filterCode).build();

        tenantMasterDetails.add(tenantMasterDetail);

        ModuleDetail tenantModuleDetail = ModuleDetail.builder().masterDetails(tenantMasterDetails)
                .moduleName(MDMS_TENANT_MODULE_NAME).build();

        return tenantModuleDetail;
    }

}
