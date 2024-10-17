package org.egov.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
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
    public static final String TENANT_FILTER_CODE = "$.[?(@.code =='{code}')].code";
    public static final String FILTER_WORKS_MODULE_CODE = "$.[?(@.active==true && @.code=='{code}')]";
    public static final String ACTIVE_FILTER_CODE = "$.[?(@.active==true)].code";
    private static final String SOR_FILTER_CODE = "@.id=='%s'";
    private static final String OR_ADDITIONAL_FILTER = " || ";
    private static final String FILTER_START = "[?(";
    private static final String FILTER_END = ")]";
    private static final String RATES_FILTER_CODE = "@.sorId=='%s'";

    private final EstimateServiceConfiguration config;

    private final ServiceRequestRepository serviceRequestRepository;

    @Autowired
    public MDMSUtils(EstimateServiceConfiguration config, ServiceRequestRepository serviceRequestRepository) {
        this.config = config;
        this.serviceRequestRepository = serviceRequestRepository;
    }

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
        return serviceRequestRepository.fetchResult(getMdmsSearchUrlV2(), mdmsCriteriaReq);
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
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequestV2(requestInfo,tenantId, sorIds, isRate);
        return serviceRequestRepository.fetchResult(getMdmsSearchUrlV2(), mdmsCriteriaReq);
    }

    /**
     * Calls MDMSV2 service to fetch works master data
     *
     * @param request
     * @param tenantId
     * @param masterName
     * @param moduleName
     * @return
     */
    public Object mdmsCallV2(EstimateRequest request, String tenantId, String masterName,String moduleName){
        log.info("MDMSUtils::mDMSCallV2");
        RequestInfo requestInfo =request.getRequestInfo();
        MasterDetail masterDetail = MasterDetail.builder().name(masterName).build();
        ModuleDetail moduleDetail = ModuleDetail.builder().masterDetails(Collections.singletonList(masterDetail)).moduleName(moduleName).build();
        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(Collections.singletonList(moduleDetail)).tenantId(tenantId).build();
        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().requestInfo(requestInfo).mdmsCriteria(mdmsCriteria).build();
        return serviceRequestRepository.fetchResult(getMdmsSearchUrlV2(), mdmsCriteriaReq);
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

        ModuleDetail estimateOverheadModuleDetail = getOverHeadModuleRequestData();
        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(estimateOverheadModuleDetail);
        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();

        log.info("MDMSUtils::search MDMS request for overhead -> {}", mdmsCriteriaReq != null ? mdmsCriteriaReq.toString() : null);

        return serviceRequestRepository.fetchResult(getMdmsSearchUrlV2(), mdmsCriteriaReq);
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
        ModuleDetail estimateCategoryModuleDetail = getCategoryModuleRequestData();

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

    public MdmsCriteriaReq getMDMSRequestV2(RequestInfo requestInfo , String  tenantId ,Set<String>sorIds, boolean isRate){
        log.info("MDMSUtils::getMDMSRequestV2");
        ModuleDetail estimateSorIdModuleDetail = getSorIdModuleRequestData(sorIds, isRate);
        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(Collections.singletonList(estimateSorIdModuleDetail)).tenantId(tenantId).build();
        return MdmsCriteriaReq.builder().requestInfo(requestInfo).mdmsCriteria(mdmsCriteria).build();
    }

    private ModuleDetail getOverHeadModuleRequestData() {
        log.info("MDMSUtils::getOverHeadModuleRequestData");

        List<MasterDetail> estimateOverheadMasterDetails = new ArrayList<>();

        MasterDetail overheadMasterDetails = MasterDetail.builder().name(MASTER_OVERHEAD)
                .filter(ACTIVE_FILTER_CODE).build();

        estimateOverheadMasterDetails.add(overheadMasterDetails);

        return ModuleDetail.builder().masterDetails(estimateOverheadMasterDetails)
                .moduleName(MDMS_WORKS_MODULE_NAME).build();
    }

    private ModuleDetail getCategoryModuleRequestData() {
        log.info("MDMSUtils::getCategoryModuleRequestData");

        List<MasterDetail> estimateCategoryMasterDetails = new ArrayList<>();

        MasterDetail categoryMasterDetails = MasterDetail.builder().name(MASTER_CATEGORY)
                .filter(ACTIVE_FILTER_CODE).build();

        estimateCategoryMasterDetails.add(categoryMasterDetails);

        return ModuleDetail.builder().masterDetails(estimateCategoryMasterDetails)
                .moduleName(MDMS_WORKS_MODULE_NAME).build();
    }

    /**
     * Method to create SorId module with required filters for SOR and Rates for fetching master data
     * @param sorIds
     * @param isRate
     * @return
     */
    private ModuleDetail getSorIdModuleRequestData(Set<String> sorIds, Boolean isRate) {
        log.info("MDMSUtils::getSorIdModuleRequestData");
        List<MasterDetail> estimateSorIdMasterDetails = new ArrayList<>();
        MasterDetail departmentMasterDetails;
        StringBuilder ratesStringBuilder = new StringBuilder();
        Iterator<String> ratesIterator = sorIds.iterator();
        while (ratesIterator.hasNext()) {
            String sorIdRateFilter = String.format(Boolean.TRUE.equals(isRate)? RATES_FILTER_CODE:SOR_FILTER_CODE, ratesIterator.next());
            ratesStringBuilder.append(sorIdRateFilter);
            if(ratesIterator.hasNext()){
                ratesStringBuilder.append(OR_ADDITIONAL_FILTER);
            }
        }
        String ratesFilter =  FILTER_START + ratesStringBuilder + FILTER_END;
        departmentMasterDetails = MasterDetail.builder().name(Boolean.TRUE.equals(isRate)?MDMS_RATES_MASTER_NAME:MDMS_SOR_MASTER_NAME)
                .filter(ratesFilter).build();

        estimateSorIdMasterDetails.add(departmentMasterDetails);

        return ModuleDetail.builder().masterDetails(estimateSorIdMasterDetails)
                .moduleName(config.getSorSearchModuleName()).build();
    }

    private ModuleDetail getDepartmentModuleRequestData(EstimateRequest request) {
        log.info("MDMSUtils::getDepartmentModuleRequestData");
        Estimate estimate = request.getEstimate();
        List<MasterDetail> estimateDepartmentMasterDetails = new ArrayList<>();

        MasterDetail departmentMasterDetails = MasterDetail.builder().name(MASTER_DEPARTMENT)
                .filter(FILTER_WORKS_MODULE_CODE.replace(PLACEHOLDER_CODE, estimate.getExecutingDepartment())).build();

        estimateDepartmentMasterDetails.add(departmentMasterDetails);

        return ModuleDetail.builder().masterDetails(estimateDepartmentMasterDetails)
                .moduleName(MDMS_COMMON_MASTERS_MODULE_NAME).build();
    }

    private ModuleDetail getTenantModuleRequestData(EstimateRequest request) {
        log.info("MDMSUtils::getTenantModuleRequestData");
        Estimate estimate = request.getEstimate();
        List<MasterDetail> estimateTenantMasterDetails = new ArrayList<>();

        MasterDetail tenantMasterDetails = MasterDetail.builder().name(MASTER_TENANTS)
                .filter(TENANT_FILTER_CODE.replace(PLACEHOLDER_CODE, estimate.getTenantId())).build();

        estimateTenantMasterDetails.add(tenantMasterDetails);

        return ModuleDetail.builder().masterDetails(estimateTenantMasterDetails)
                .moduleName(MDMS_TENANT_MODULE_NAME).build();
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
