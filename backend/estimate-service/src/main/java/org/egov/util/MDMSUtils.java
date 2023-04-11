package org.egov.util;

import digit.models.coremodels.mdms.MasterDetail;
import digit.models.coremodels.mdms.MdmsCriteria;
import digit.models.coremodels.mdms.MdmsCriteriaReq;
import digit.models.coremodels.mdms.ModuleDetail;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.egov.util.EstimateServiceConstant.*;

@Component
@Slf4j
public class MDMSUtils {

    public static final String PLACEHOLDER_CODE = "{code}";
    public static final String tenantFilterCode = "$.[?(@.code =='{code}')].code";
    public static final String filterWorksModuleCode = "$.[?(@.active==true && @.code=='{code}')]";
    public static final String codeFilter = "$.*.code";
    public static final String activeCodeFilter = "$.[?(@.active==true)].code";

    @Autowired
    private EstimateServiceConfiguration config;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    /**
     * Calls MDMS service to fetch works master data
     *
     * @param request
     * @param tenantId
     * @return
     */
    public Object mDMSCall(EstimateRequest request, String tenantId) {
        log.info("MDMSUtils::mDMSCall");
        RequestInfo requestInfo = request.getRequestInfo();
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequest(requestInfo, tenantId, request);
        Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
        return result;
    }


    /**
     * Calls MDMS service to fetch overhead category
     *
     * @param request
     * @param tenantId
     * @return
     */
    public Object mDMSCallForOverHeadCategory(EstimateRequest request, String tenantId) {
        log.info("MDMSUtils::mDMSCallForOverHeadCategory");
        RequestInfo requestInfo = request.getRequestInfo();

        ModuleDetail estimateOverheadModuleDetail = getOverHeadModuleRequestData(request);
        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(estimateOverheadModuleDetail);
        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();

        log.info("MDMSUtils::search MDMS request for overhead -> {}", mdmsCriteriaReq != null ? mdmsCriteriaReq.toString() : null);

        Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
        return result;
    }

    /**
     * Returns mdms search criteria based on the tenantId
     *
     * @param requestInfo
     * @param tenantId
     * @param request
     * @return
     */
    public MdmsCriteriaReq getMDMSRequest(RequestInfo requestInfo, String tenantId, EstimateRequest request) {
        log.info("MDMSUtils::getMDMSRequest");
        ModuleDetail estimateDepartmentModuleDetail = getDepartmentModuleRequestData(request);
        ModuleDetail estimateTenantModuleDetail = getTenantModuleRequestData(request);
        ModuleDetail estimateSorIdModuleDetail = getSorIdModuleRequestData(request);
        ModuleDetail estimateCategoryModuleDetail = getCategoryModuleRequestData(request);

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(estimateTenantModuleDetail);
        moduleDetails.add(estimateDepartmentModuleDetail);
        moduleDetails.add(estimateSorIdModuleDetail);
        moduleDetails.add(estimateCategoryModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();

        log.info("MDMSUtils::search MDMS request -> {}", mdmsCriteriaReq != null ? mdmsCriteriaReq.toString() : null);
        return mdmsCriteriaReq;
    }

    private ModuleDetail getOverHeadModuleRequestData(EstimateRequest request) {
        log.info("MDMSUtils::getOverHeadModuleRequestData");

        List<MasterDetail> estimateOverheadMasterDetails = new ArrayList<>();

        MasterDetail overheadMasterDetails = MasterDetail.builder().name(MASTER_OVERHEAD)
                .filter(activeCodeFilter).build();

        estimateOverheadMasterDetails.add(overheadMasterDetails);

        ModuleDetail estimateOverHeadModuleDetail = ModuleDetail.builder().masterDetails(estimateOverheadMasterDetails)
                .moduleName(MDMS_WORKS_MODULE_NAME).build();

        return estimateOverHeadModuleDetail;
    }

    private ModuleDetail getCategoryModuleRequestData(EstimateRequest request) {
        log.info("MDMSUtils::getCategoryModuleRequestData");

        List<MasterDetail> estimateCategoryMasterDetails = new ArrayList<>();

        MasterDetail categoryMasterDetails = MasterDetail.builder().name(MASTER_CATEGORY)
                .filter(activeCodeFilter).build();

        estimateCategoryMasterDetails.add(categoryMasterDetails);

        ModuleDetail estimateCategoryModuleDetail = ModuleDetail.builder().masterDetails(estimateCategoryMasterDetails)
                .moduleName(MDMS_WORKS_MODULE_NAME).build();

        return estimateCategoryModuleDetail;
    }


    private ModuleDetail getSorIdModuleRequestData(EstimateRequest request) {
        log.info("MDMSUtils::getSorIdModuleRequestData");
        List<MasterDetail> estimateSorIdMasterDetails = new ArrayList<>();

        MasterDetail departmentMasterDetails = MasterDetail.builder().name(MASTER_SOR_ID)
                .filter(codeFilter).build();

        estimateSorIdMasterDetails.add(departmentMasterDetails);

        ModuleDetail estimateDepartmentModuleDetail = ModuleDetail.builder().masterDetails(estimateSorIdMasterDetails)
                .moduleName(MDMS_WORKS_MODULE_NAME).build();

        return estimateDepartmentModuleDetail;
    }

    private ModuleDetail getDepartmentModuleRequestData(EstimateRequest request) {
        log.info("MDMSUtils::getDepartmentModuleRequestData");
        Estimate estimate = request.getEstimate();
        List<MasterDetail> estimateDepartmentMasterDetails = new ArrayList<>();

        MasterDetail departmentMasterDetails = MasterDetail.builder().name(MASTER_DEPARTMENT)
                .filter(filterWorksModuleCode.replace(PLACEHOLDER_CODE, estimate.getExecutingDepartment())).build();

        estimateDepartmentMasterDetails.add(departmentMasterDetails);

        ModuleDetail estimateDepartmentModuleDetail = ModuleDetail.builder().masterDetails(estimateDepartmentMasterDetails)
                .moduleName(MDMS_COMMON_MASTERS_MODULE_NAME).build();

        return estimateDepartmentModuleDetail;
    }

    private ModuleDetail getTenantModuleRequestData(EstimateRequest request) {
        log.info("MDMSUtils::getTenantModuleRequestData");
        Estimate estimate = request.getEstimate();
        List<MasterDetail> estimateTenantMasterDetails = new ArrayList<>();

        MasterDetail tenantMasterDetails = MasterDetail.builder().name(MASTER_TENANTS)
                .filter(tenantFilterCode.replace(PLACEHOLDER_CODE, estimate.getTenantId())).build();

        estimateTenantMasterDetails.add(tenantMasterDetails);

        ModuleDetail estimateTenantModuleDetail = ModuleDetail.builder().masterDetails(estimateTenantMasterDetails)
                .moduleName(MDMS_TENANT_MODULE_NAME).build();

        return estimateTenantModuleDetail;
    }

    /**
     * Returns the url for mdms search endpoint
     *
     * @return url for mdms search endpoint
     */
    public StringBuilder getMdmsSearchUrl() {
        return new StringBuilder().append(config.getMdmsHost()).append(config.getMdmsEndPoint());
    }

}
