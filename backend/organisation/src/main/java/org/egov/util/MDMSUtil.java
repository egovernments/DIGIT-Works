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

    public static final String codeFilter = "$.*.code";
    public static final String activeCodeFilter = "$.[?(@.active==true)].code";

    @Autowired
    private Configuration config;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

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
        Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
        return result;
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
        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();
        return mdmsCriteriaReq;
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
                .filter(activeCodeFilter).build();
        MasterDetail orgTaxIdentifierMaster = MasterDetail.builder().name(MASTER_ORG_TAX_IDENTIFIER)
                .filter(activeCodeFilter).build();
        MasterDetail orgFunCategoryMaster = MasterDetail.builder().name(MASTER_ORG_FUNC_CATEGORY)
                .filter(activeCodeFilter).build();
        MasterDetail orgTypeMaster = MasterDetail.builder().name(MASTER_ORG_TYPE)
                .filter(activeCodeFilter).build();
        commonMasterModulesDetails.add(orgFuncMaster);
        commonMasterModulesDetails.add(orgTaxIdentifierMaster);
        commonMasterModulesDetails.add(orgFunCategoryMaster);
        commonMasterModulesDetails.add(orgTypeMaster);
        ModuleDetail commonMasterModuleDetail = ModuleDetail.builder().masterDetails(commonMasterModulesDetails)
                .moduleName(MDMS_COMMON_MASTERS_MODULE_NAME).build();
        return commonMasterModuleDetail;
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
                .filter(codeFilter).build();

        orgTenantMasterDetails.add(tenantMasterDetails);

        ModuleDetail orgTenantModuleDetail = ModuleDetail.builder().masterDetails(orgTenantMasterDetails)
                .moduleName(MDMS_TENANT_MODULE_NAME).build();

        return orgTenantModuleDetail;
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