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
     * @param mdmsCriteriaReq
     * @param tenantId
     * @return
     */
    public Object mDMSCall(MdmsCriteriaReq mdmsCriteriaReq, String tenantId) {
        log.info("MDMSUtil::mDMSCall");
        Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
        return result;
    }


    public MdmsCriteriaReq getOrgFunctionMDMSRequest(RequestInfo requestInfo, String tenantId, List<Organisation> organisationList) {
        log.info("MDMSUtil::getOrgFunctionMDMSRequest");
        ModuleDetail orgFunctionModuleDetail = getOrgFunctionModuleRequestData(organisationList);

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(orgFunctionModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();

        log.info("MDMSUtil::search Org Function MDMS request -> {}", mdmsCriteriaReq != null ? mdmsCriteriaReq.toString() : null);
        return mdmsCriteriaReq;
    }

    public MdmsCriteriaReq getOrgTaxIdentifierMDMSRequest(RequestInfo requestInfo, String tenantId, List<Organisation> organisationList) {
        log.info("MDMSUtil::getOrgTaxIdentifierMDMSRequest");
        ModuleDetail orgTaxIdentifierModuleDetail = getOrgTaxIdentifierModuleRequestData(organisationList);

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(orgTaxIdentifierModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();

        log.info("MDMSUtil::search Org Tax Identifier MDMS request -> {}", mdmsCriteriaReq != null ? mdmsCriteriaReq.toString() : null);
        return mdmsCriteriaReq;
    }

    public MdmsCriteriaReq getOrgFunCategoryMDMSRequest(RequestInfo requestInfo, String tenantId, List<Organisation> organisationList) {
        log.info("MDMSUtil::getOrgFunCategoryMDMSRequest");
        ModuleDetail orgFuncCategoryModuleDetail = getOrgFunCategoryModuleRequestData(organisationList);

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(orgFuncCategoryModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();

        log.info("MDMSUtil::search Org Function Category MDMSRequest MDMS request -> {}", mdmsCriteriaReq != null ? mdmsCriteriaReq.toString() : null);
        return mdmsCriteriaReq;
    }

    public MdmsCriteriaReq getTenantMDMSRequest(RequestInfo requestInfo, String tenantId, List<Organisation> organisationList) {
        log.info("MDMSUtil::getTenantMDMSRequest");
        ModuleDetail orgTenantModuleDetail = getTenantModuleRequestData(organisationList);

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(orgTenantModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();

        log.info("MDMSUtil::search tenant MDMS request -> {}", mdmsCriteriaReq != null ? mdmsCriteriaReq.toString() : null);
        return mdmsCriteriaReq;
    }

    public MdmsCriteriaReq getOrgTypeMDMSRequest(RequestInfo requestInfo, String tenantId, List<Organisation> organisationList) {
        log.info("MDMSUtil::getOrgTypeMDMSRequest");
        ModuleDetail orgTypeModuleDetail = getOrgTypeModuleRequestData(organisationList);

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(orgTypeModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();

        log.info("MDMSUtil::search OrgType MDMS request -> {}", mdmsCriteriaReq != null ? mdmsCriteriaReq.toString() : null);
        return mdmsCriteriaReq;
    }

    private ModuleDetail getOrgTypeModuleRequestData(List<Organisation> organisationList) {
        log.info("MDMSUtils::getOrgTypeModuleRequestData");

        List<MasterDetail> orgTypeMasterDetails = new ArrayList<>();

        MasterDetail orgTypeMaster = MasterDetail.builder().name(MASTER_ORG_TYPE)
                .filter(activeCodeFilter).build();

        orgTypeMasterDetails.add(orgTypeMaster);

        ModuleDetail orgTypeModuleDetail = ModuleDetail.builder().masterDetails(orgTypeMasterDetails)
                .moduleName(MDMS_COMMON_MASTERS_MODULE_NAME).build();

        return orgTypeModuleDetail;
    }

    private ModuleDetail getOrgFunCategoryModuleRequestData(List<Organisation> organisationList) {
        log.info("MDMSUtils::getOrgFunCategoryModuleRequestData");

        List<MasterDetail> orgFunCategoryMasterDetails = new ArrayList<>();

        MasterDetail orgFunCategoryMaster = MasterDetail.builder().name(MASTER_ORG_FUNC_CATEGORY)
                .filter(activeCodeFilter).build();

        orgFunCategoryMasterDetails.add(orgFunCategoryMaster);

        ModuleDetail orgFunCategoryModuleDetail = ModuleDetail.builder().masterDetails(orgFunCategoryMasterDetails)
                .moduleName(MDMS_COMMON_MASTERS_MODULE_NAME).build();

        return orgFunCategoryModuleDetail;
    }

//    private ModuleDetail getOrgTransferModuleRequestData(List<Organisation> organisationList) {
//        log.info("MDMSUtils::getOrgTransferModuleRequestData");
//
//        List<MasterDetail> orgTransferMasterDetails = new ArrayList<>();
//
//        MasterDetail orgTransferMaster = MasterDetail.builder().name(MASTER_ORG_TRANSFER_CODE)
//                .filter(activeCodeFilter).build();
//
//        orgTransferMasterDetails.add(orgTransferMaster);
//
//        ModuleDetail orgTransferModuleDetail = ModuleDetail.builder().masterDetails(orgTransferMasterDetails)
//                .moduleName(MDMS_COMMON_MASTERS_MODULE_NAME).build();
//
//        return orgTransferModuleDetail;
//    }


    private ModuleDetail getOrgTaxIdentifierModuleRequestData(List<Organisation> organisationList) {
        log.info("MDMSUtils::getOrgTaxIdentifierModuleRequestData");

        List<MasterDetail> orgTaxIdentifierMasterDetails = new ArrayList<>();

        MasterDetail orgTaxIdentifierMaster = MasterDetail.builder().name(MASTER_ORG_TAX_IDENTIFIER)
                .filter(activeCodeFilter).build();

        orgTaxIdentifierMasterDetails.add(orgTaxIdentifierMaster);

        ModuleDetail orgTaxIdentifierModuleDetail = ModuleDetail.builder().masterDetails(orgTaxIdentifierMasterDetails)
                .moduleName(MDMS_COMMON_MASTERS_MODULE_NAME).build();

        return orgTaxIdentifierModuleDetail;
    }

    private ModuleDetail getOrgFunctionModuleRequestData(List<Organisation> organisationList) {
        log.info("MDMSUtils::getOrgFunctionModuleRequestData");

        List<MasterDetail> orgFuncMasterDetails = new ArrayList<>();

        MasterDetail orgFuncMaster = MasterDetail.builder().name(MASTER_ORG_FUNC_CLASS)
                .filter(activeCodeFilter).build();

        orgFuncMasterDetails.add(orgFuncMaster);

        ModuleDetail orgFuncModuleDetail = ModuleDetail.builder().masterDetails(orgFuncMasterDetails)
                .moduleName(MDMS_COMMON_MASTERS_MODULE_NAME).build();

        return orgFuncModuleDetail;
    }

    private ModuleDetail getTenantModuleRequestData(List<Organisation> organisationList) {
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