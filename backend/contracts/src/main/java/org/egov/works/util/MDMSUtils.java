package org.egov.works.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.config.ContractServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.egov.works.util.ContractServiceConstants.*;

@Component
@Slf4j
public class MDMSUtils {
    private final ServiceRequestRepository serviceRequestRepository;

    private final ContractServiceConfiguration config;

    @Autowired
    public MDMSUtils(ServiceRequestRepository serviceRequestRepository, ContractServiceConfiguration config) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.config = config;
    }

    public Object fetchMDMSForValidation(RequestInfo requestInfo, String tenantId) {
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSValidationRequest(requestInfo, tenantId);
        return serviceRequestRepository.fetchResult(getMDMSSearchUrl(), mdmsCriteriaReq);
    }
    private MdmsCriteriaReq getMDMSValidationRequest(RequestInfo requestInfo, String tenantId) {
        ModuleDetail tenantModuleDetail = getTenantModuleDetail();
        ModuleDetail worksModuleDetail =  getWorksModuleDetailValidate();

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(tenantModuleDetail);
        moduleDetails.add(worksModuleDetail);

        return prepareMDMSCriteria(requestInfo,moduleDetails,tenantId);
    }

    private MdmsCriteriaReq prepareMDMSCriteria(RequestInfo requestInfo,List<ModuleDetail> moduleDetails, String tenantId){
        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();
        return MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();
    }
    private ModuleDetail getWorksModuleDetailValidate() {
        List<MasterDetail> masterDetails = new ArrayList<>();

        MasterDetail executingAuthorityMasterDetail = getMasterDetailForSubModule(MASTER_CBO_ROLES, COMMON_ACTIVE_WITH_CODE_FILTER);
        MasterDetail contractTypeMasterDetail = getMasterDetailForSubModule(MASTER_CONTRACT_TYPE, COMMON_ACTIVE_WITH_CODE_FILTER);
        MasterDetail officerInChargeRolesMasterDetail = getMasterDetailForSubModule(MASTER_OIC_ROLES, COMMON_ACTIVE_WITH_CODE_FILTER);

        masterDetails.add(executingAuthorityMasterDetail);
        masterDetails.add(contractTypeMasterDetail);
        masterDetails.add(officerInChargeRolesMasterDetail);

        return ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(MDMS_WORKS_MODULE_NAME).build();
    }
    private ModuleDetail getTenantModuleDetail() {
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail masterDetail = getMasterDetailForSubModule(MASTER_TENANTS,TENANT_FILTER_CODE);
        masterDetails.add(masterDetail);
        return ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(MDMS_TENANT_MODULE_NAME).build();
    }

    public Object fetchMDMSForEnrichment(RequestInfo requestInfo, String tenantId,String contractTypeCode) {
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSEnrichmentRequest(requestInfo, tenantId,contractTypeCode);
        return serviceRequestRepository.fetchResult(getMDMSSearchUrl(), mdmsCriteriaReq);
    }
    private MdmsCriteriaReq getMDMSEnrichmentRequest(RequestInfo requestInfo, String tenantId,String contractTypeCode) {
        ModuleDetail worksModuleDetail =  getRequestDataForWorksModuleEnrichment(contractTypeCode);
        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(worksModuleDetail);
        return prepareMDMSCriteria(requestInfo,moduleDetails,tenantId);
    }
    private ModuleDetail getRequestDataForWorksModuleEnrichment(String contractTypeCode) {
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail overHeadsMasterDetail = getMasterDetailForSubModule(MASTER_OVER_HEADS, COMMON_ACTIVE_WITH_WORK_ORDER_VALUE);

        String filter = REGISTER_ACTIVE_CODE_CONSTANT + contractTypeCode + CREATE_REGISTER_CONSTANT;
        MasterDetail contractTypeMasterDetail = getMasterDetailForSubModule(MASTER_CONTRACT_TYPE, filter);

        masterDetails.add(overHeadsMasterDetail);
        masterDetails.add(contractTypeMasterDetail);
        return ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(MDMS_WORKS_MODULE_NAME).build();
    }

    private MasterDetail getMasterDetailForSubModule(String masterDetailName, String filter){
      return MasterDetail.builder().name(masterDetailName)
              .filter(filter).build();
    }
    public StringBuilder getMDMSSearchUrl() {
        return new StringBuilder().append(config.getMdmsV2Host()).append(config.getMdmsV2EndPoint());
    }
}
