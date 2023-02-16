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

    public Object mDMSCall(RequestInfo requestInfo, String tenantId) {
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequest(requestInfo, tenantId);
        Object result = serviceRequestRepository.fetchResult(getMDMSSearchUrl(), mdmsCriteriaReq);
        return result;
    }

    public MdmsCriteriaReq getMDMSRequest(RequestInfo requestInfo, String tenantId) {
        ModuleDetail tenantModuleDetail = getRequestDataForTenantModule();
        ModuleDetail worksModuleDetail = getRequestDataForWorksModule();

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(tenantModuleDetail);
        moduleDetails.add(worksModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();
        return mdmsCriteriaReq;
    }

    private ModuleDetail getRequestDataForWorksModule() {
        List<MasterDetail> masterDetails = new ArrayList<>();

        MasterDetail executingAuthorityMasterDetail = getMasterDetail(MASTER_EXECUTING_AUTHORITY, COMMON_ACTIVE_FILTER);
        MasterDetail documentTypeMasterDetail = getMasterDetail(MASTER_DOCUMENT_TYPE, COMMON_ACTIVE_FILTER);
        MasterDetail contractTypeMasterDetail = getMasterDetail(MASTER_CONTRACT_TYPE,COMMON_ACTIVE_FILTER);

        masterDetails.add(executingAuthorityMasterDetail);
        masterDetails.add(documentTypeMasterDetail);
        masterDetails.add(contractTypeMasterDetail);

        ModuleDetail worksModuleDetail = ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(MDMS_WORKS_MODULE_NAME).build();
        return worksModuleDetail;
    }
    private ModuleDetail getRequestDataForTenantModule() {
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail masterDetail = getMasterDetail(MASTER_TENANTS,TENANT_FILTER_CODE);
        masterDetails.add(masterDetail);
        ModuleDetail tenantModuleDetail = ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(MDMS_TENANT_MODULE_NAME).build();
        return tenantModuleDetail;
    }
    private MasterDetail getMasterDetail(String masterDetailName,String filter){
      return MasterDetail.builder().name(masterDetailName)
              .filter(filter).build();
    }
    public StringBuilder getMDMSSearchUrl() {
        return new StringBuilder().append(config.getMdmsHost()).append(config.getMdmsEndPoint());
    }
}
