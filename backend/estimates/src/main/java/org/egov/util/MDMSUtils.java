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

import java.util.*;

import static org.egov.util.EstimateServiceConstant.*;

@Component
@Slf4j
public class MDMSUtils {

    public static final String PLACEHOLDER_CODE = "{code}";
    public static final String tenantFilterCode = "$.[?(@.code =='{code}')].code";
    public static final String filterWorksModuleCode = "$.[?(@.active==true && @.code=='{code}')]";
    public static final String codeFilter = "$.*.code";
    public static final String activeCodeFilter = "$.[?(@.active==true)].code";
    private static final String sorFilterCode = "@.id=='%s'";
    private static final String orAdditionalFilter = " || ";
    private static final String filterStart = "[?(";
    private static final String filterEnd = ")]";
    private static final String ratesFilterCode = "@.sorId=='%s'";

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
     * Calls MDMS v2 service to fetch works master data
     *
     * @param request
     * @param tenantId
     * @return
     */
    public Object mdmsCallV2ForSor(EstimateRequest request, String tenantId, Set<String> sorIds, boolean isRate){
        log.info("MDMSUtils::mDMSCallV2");
        RequestInfo requestInfo =request.getRequestInfo();
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequestV2(requestInfo,tenantId,request, sorIds, isRate);
        Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrlV2(), mdmsCriteriaReq);
        return result;
    }


    public Object mdmsCallV2(EstimateRequest request, String tenantId, String masterName,String moduleName){
        log.info("MDMSUtils::mDMSCallV2");
        RequestInfo requestInfo =request.getRequestInfo();
        MasterDetail masterDetail = MasterDetail.builder().name(masterName).build();
        ModuleDetail moduleDetail = ModuleDetail.builder().masterDetails(Collections.singletonList(masterDetail)).moduleName(moduleName).build();
        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(Collections.singletonList(moduleDetail)).tenantId(tenantId).build();
        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().requestInfo(requestInfo).mdmsCriteria(mdmsCriteria).build();
        Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrlV2(), mdmsCriteriaReq);
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
        ModuleDetail estimateCategoryModuleDetail = getCategoryModuleRequestData(request);

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(estimateTenantModuleDetail);
        moduleDetails.add(estimateDepartmentModuleDetail);
        moduleDetails.add(estimateCategoryModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();

        log.info("MDMSUtils::search MDMS request -> {}", mdmsCriteriaReq != null ? mdmsCriteriaReq.toString() : null);
        return mdmsCriteriaReq;
    }

    /**
     * Returns mdms v2 search criteria based on the tenantId and mdms search criteria
     * @return
     */

    public MdmsCriteriaReq getMDMSRequestV2(RequestInfo requestInfo , String  tenantId , EstimateRequest request,Set<String>sorIds, boolean isRate){
        log.info("MDMSUtils::getMDMSRequestV2");
        ModuleDetail estimateSorIdModuleDetail = getSorIdModuleRequestData(request, sorIds, isRate);
        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(Collections.singletonList(estimateSorIdModuleDetail)).tenantId(tenantId).build();
        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().requestInfo(requestInfo).mdmsCriteria(mdmsCriteria).build();
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


    private ModuleDetail getSorIdModuleRequestData(EstimateRequest request, Set<String> sorIds, Boolean isRate) {
        log.info("MDMSUtils::getSorIdModuleRequestData");
        List<MasterDetail> estimateSorIdMasterDetails = new ArrayList<>();
        MasterDetail departmentMasterDetails;
        StringBuilder ratesStringBuilder = new StringBuilder();
        Iterator ratesIterator = sorIds.iterator();
        while (ratesIterator.hasNext()) {
            String sorIdRateFilter = String.format(isRate? ratesFilterCode:sorFilterCode, ratesIterator.next());
            ratesStringBuilder.append(sorIdRateFilter);
            if(ratesIterator.hasNext()){
                ratesStringBuilder.append(orAdditionalFilter);
            }
        }
        String ratesFilter =  filterStart + ratesStringBuilder + filterEnd;
        departmentMasterDetails = MasterDetail.builder().name(isRate?MDMS_RATES_MASTER_NAME:MDMS_SOR_MASTER_NAME)
                .filter(ratesFilter).build();

        estimateSorIdMasterDetails.add(departmentMasterDetails);

        ModuleDetail estimateSorIdModuleDetail = ModuleDetail.builder().masterDetails(estimateSorIdMasterDetails)
                .moduleName(config.getSorSearchModuleName()).build();

        return estimateSorIdModuleDetail;
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

    /**
     * Returns the url for mdms search v2 endpoint
     *
     * @return url for mdms search v2 endpoint
     */
    public StringBuilder getMdmsSearchUrlV2() {
        return new StringBuilder().append(config.getMdmsHostV2()).append(config.getMdmsEndPointV2());
    }

}
