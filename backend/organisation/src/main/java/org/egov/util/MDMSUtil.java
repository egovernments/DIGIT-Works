package org.egov.util;

import digit.models.coremodels.mdms.MasterDetail;
import digit.models.coremodels.mdms.MdmsCriteria;
import digit.models.coremodels.mdms.MdmsCriteriaReq;
import digit.models.coremodels.mdms.ModuleDetail;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.OrgRequest;
import org.egov.web.models.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.egov.util.OrganisationConstant.*;

@Slf4j
@Component
public class MDMSUtil {

    public static final String PLACEHOLDER_CODE = "{code}";
    public static final String tenantFilterCode = "$.[?(@.code =='{code}')].code";
    public static final String filterWorksModuleCode = "$.[?(@.active==true && @.code=='{code}')]";
    public static final String codeFilter = "$.*.code";
    public static final String activeCodeFilter = "$.[?(@.active==true)].code";

    @Autowired
    private Configuration config;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    /**
     * Calls MDMS service to fetch works master data
     *
     * @param orgRequest
     * @param tenantId
     * @return
     */
    public Object mDMSCall(OrgRequest orgRequest, String tenantId) {
        log.info("MDMSUtil::mDMSCall");
        RequestInfo requestInfo = orgRequest.getRequestInfo();
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequest(requestInfo, tenantId, orgRequest.getOrganisations());
        Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
        return result;
    }

	/**
	 * Returns mdms search criteria based on the tenantId
	 *
	 * @param requestInfo
	 * @param tenantId
	 * @param organisationList
	 * @return
	 */
    public MdmsCriteriaReq getMDMSRequest(RequestInfo requestInfo, String tenantId, List<Organisation> organisationList) {
        log.info("MDMSUtil::getMDMSRequest");
        //ModuleDetail estimateDepartmentModuleDetail = getDepartmentModuleRequestData(request);
        ModuleDetail estimateTenantModuleDetail = getTenantModuleRequestData(organisationList);
        //ModuleDetail estimateSorIdModuleDetail = getSorIdModuleRequestData(request);
        //ModuleDetail estimateCategoryModuleDetail = getCategoryModuleRequestData(request);

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(estimateTenantModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();

        log.info("MDMSUtil::search MDMS request -> {}", mdmsCriteriaReq != null ? mdmsCriteriaReq.toString() : null);
        return mdmsCriteriaReq;
    }

//    private ModuleDetail getCategoryModuleRequestData(EstimateRequest request) {
//        log.info("MDMSUtils::getCategoryModuleRequestData");
//
//        List<MasterDetail> estimateCategoryMasterDetails = new ArrayList<>();
//
//        MasterDetail categoryMasterDetails = MasterDetail.builder().name(MASTER_CATEGORY)
//                .filter(activeCodeFilter).build();
//
//        estimateCategoryMasterDetails.add(categoryMasterDetails);
//
//        ModuleDetail estimateCategoryModuleDetail = ModuleDetail.builder().masterDetails(estimateCategoryMasterDetails)
//                .moduleName(MDMS_WORKS_MODULE_NAME).build();
//
//        return estimateCategoryModuleDetail;
//    }


    private ModuleDetail getTenantModuleRequestData(List<Organisation> organisationList) {
        log.info("MDMSUtil::getTenantModuleRequestData");
        List<MasterDetail> estimateTenantMasterDetails = new ArrayList<>();

        MasterDetail tenantMasterDetails = MasterDetail.builder().name(MASTER_TENANTS)
                .filter(codeFilter).build();

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