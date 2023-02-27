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

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ContractServiceConfiguration config;

    public Object fetchMDMSForValidation(RequestInfo requestInfo, String tenantId) {
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSValidationRequest(requestInfo, tenantId);
        Object result = serviceRequestRepository.fetchResult(getMDMSSearchUrl(), mdmsCriteriaReq);
        return result;
    }

    public Object fetchMDMSForEnrichment(RequestInfo requestInfo, String tenantId) {
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSEnrichmentRequest(requestInfo, tenantId);
        Object result = serviceRequestRepository.fetchResult(getMDMSSearchUrl(), mdmsCriteriaReq);
        return result;
    }

    private MdmsCriteriaReq getMDMSEnrichmentRequest(RequestInfo requestInfo, String tenantId) {
        ModuleDetail worksModuleDetail =  getRequestDataForWorksModuleEnrichment();
        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(worksModuleDetail);
        return prepareMDMSCriteria(requestInfo,moduleDetails,tenantId);
    }
    private MdmsCriteriaReq getMDMSValidationRequest(RequestInfo requestInfo, String tenantId) {
        ModuleDetail tenantModuleDetail = getRequestDataForTenantModule();
        ModuleDetail worksModuleDetail =  getRequestDataForWorksModuleValidate();

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(tenantModuleDetail);
        moduleDetails.add(worksModuleDetail);

        return prepareMDMSCriteria(requestInfo,moduleDetails,tenantId);
    }

    private MdmsCriteriaReq prepareMDMSCriteria(RequestInfo requestInfo,List<ModuleDetail> moduleDetails, String tenantId){
        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();
        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();
        return mdmsCriteriaReq;
    }

    private ModuleDetail getRequestDataForWorksModuleValidate() {
        List<MasterDetail> masterDetails = new ArrayList<>();

        //MasterDetail documentTypeMasterDetail = getMasterDetail(MASTER_DOCUMENT_TYPE, COMMON_ACTIVE_WITH_NAME_FILTER);
        MasterDetail executingAuthorityMasterDetail = getMasterDetailForSubModule(MASTER_CBO_ROLES, COMMON_ACTIVE_WITH_CODE_FILTER);
        MasterDetail contractTypeMasterDetail = getMasterDetailForSubModule(MASTER_CONTRACT_TYPE, COMMON_ACTIVE_WITH_CODE_FILTER);
        MasterDetail officerInChargeRolesMasterDetail = getMasterDetailForSubModule(MASTER_OIC_ROLES, COMMON_ACTIVE_WITH_CODE_FILTER);

        //masterDetails.add(documentTypeMasterDetail);
        masterDetails.add(executingAuthorityMasterDetail);
        masterDetails.add(contractTypeMasterDetail);
        masterDetails.add(officerInChargeRolesMasterDetail);

        ModuleDetail worksModuleDetail = ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(MDMS_WORKS_MODULE_NAME).build();
        return worksModuleDetail;
    }
    private ModuleDetail getRequestDataForTenantModule() {
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail masterDetail = getMasterDetailForSubModule(MASTER_TENANTS,TENANT_FILTER_CODE);
        masterDetails.add(masterDetail);
        ModuleDetail tenantModuleDetail = ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(MDMS_TENANT_MODULE_NAME).build();
        return tenantModuleDetail;
    }

    private ModuleDetail getRequestDataForWorksModuleEnrichment() {
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail executingAuthorityMasterDetail = getMasterDetailForSubModule(MASTER_OVER_HEADS, COMMON_ACTIVE_WITH_WORK_ORDER_VALUE);
        masterDetails.add(executingAuthorityMasterDetail);
        ModuleDetail worksModuleDetail = ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(MDMS_WORKS_MODULE_NAME).build();
        return worksModuleDetail;
    }

    private MasterDetail getMasterDetailForSubModule(String masterDetailName, String filter){
      return MasterDetail.builder().name(masterDetailName)
              .filter(filter).build();
    }
    public StringBuilder getMDMSSearchUrl() {
        return new StringBuilder().append(config.getMdmsHost()).append(config.getMdmsEndPoint());
    }
}
