package org.egov.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
import org.egov.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.egov.util.OrganisationConstant.*;

@Slf4j
@Component
public class MDMSUtil {

    public static final String CODE_FILTER = "$.*.code";
    public static final String ACTIVE_CODE_FILTER = "$.[?(@.active==true)].code";

    private final Configuration config;

    private final ServiceRequestRepository serviceRequestRepository;

    @Autowired
    public MDMSUtil(Configuration config, ServiceRequestRepository serviceRequestRepository) {
        this.config = config;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    /**
     * Calls MDMS service to fetch works master data
     *
     * @param requestInfo
     * @param tenantId
     * @return
     */
    public Object mDMSCall(RequestInfo requestInfo, String tenantId) {
        log.info("MDMSUtil::mDMSCall");
        MdmsCriteriaReq mdmsCriteriaReq = prepareMDMSRequest(requestInfo, tenantId);
        return serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
    }

    /**
     * Prepares the mdms search request
     * @param requestInfo
     * @param tenantId
     * @return the mdms search request
     */
    public MdmsCriteriaReq prepareMDMSRequest(RequestInfo requestInfo, String tenantId) {
        log.info("MDMSUtil::prepareMDMSRequest");
        ModuleDetail commonMasterModuleDetails = prepareCommonMasterModuleDetails();
        ModuleDetail tenantModuleDetail = getTenantModuleRequestData();

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(commonMasterModuleDetails);
        moduleDetails.add(tenantModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();
        return MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();
    }

    /**
     * Prepares the mdms search request for common master module
     *
     * @return the mdms search request for common master module
     */
    private ModuleDetail prepareCommonMasterModuleDetails(){
        log.info("MDMSUtil::prepareCommonMasterModuleDetails");
        List<MasterDetail> commonMasterModulesDetails = new ArrayList<>();
        MasterDetail orgFuncMaster = MasterDetail.builder().name(MASTER_ORG_FUNC_CLASS)
                .filter(ACTIVE_CODE_FILTER).build();
        MasterDetail orgTaxIdentifierMaster = MasterDetail.builder().name(MASTER_ORG_TAX_IDENTIFIER)
                .filter(ACTIVE_CODE_FILTER).build();
        MasterDetail orgFunCategoryMaster = MasterDetail.builder().name(MASTER_ORG_FUNC_CATEGORY)
                .filter(ACTIVE_CODE_FILTER).build();
        MasterDetail orgTypeMaster = MasterDetail.builder().name(MASTER_ORG_TYPE)
                .filter(ACTIVE_CODE_FILTER).build();
        commonMasterModulesDetails.add(orgFuncMaster);
        commonMasterModulesDetails.add(orgTaxIdentifierMaster);
        commonMasterModulesDetails.add(orgFunCategoryMaster);
        commonMasterModulesDetails.add(orgTypeMaster);
        return ModuleDetail.builder().masterDetails(commonMasterModulesDetails)
                .moduleName(MDMS_COMMON_MASTERS_MODULE_NAME).build();
    }

    /**
     * Prepares the mdms search request for tenant module
     *
     * @return the mdms search request for tenant module
     */
    private ModuleDetail getTenantModuleRequestData() {
        log.info("MDMSUtil::getTenantModuleRequestData");
        List<MasterDetail> orgTenantMasterDetails = new ArrayList<>();

        MasterDetail tenantMasterDetails = MasterDetail.builder().name(MASTER_TENANTS)
                .filter(CODE_FILTER).build();

        orgTenantMasterDetails.add(tenantMasterDetails);

        return ModuleDetail.builder().masterDetails(orgTenantMasterDetails)
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

}
