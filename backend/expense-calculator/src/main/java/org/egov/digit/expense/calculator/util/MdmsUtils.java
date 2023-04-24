package org.egov.digit.expense.calculator.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;

@Component
@Slf4j
public class MdmsUtils {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ExpenseCalculatorConfiguration config;

    public Object fetchMDMSForValidation(RequestInfo requestInfo, String tenantId) {
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSValidationRequest(requestInfo, tenantId);
        return serviceRequestRepository.fetchResult(getMDMSSearchUrl(), mdmsCriteriaReq);
    }
    private MdmsCriteriaReq getMDMSValidationRequest(RequestInfo requestInfo, String tenantId) {
        ModuleDetail tenantModuleDetail = getTenantModuleDetail();
        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(tenantModuleDetail);
        return prepareMDMSCriteria(requestInfo,moduleDetails,tenantId);
    }

    private MdmsCriteriaReq prepareMDMSCriteria(RequestInfo requestInfo,List<ModuleDetail> moduleDetails, String tenantId){
        MdmsCriteria mdmsCriteria = MdmsCriteria.builder()
                                                .moduleDetails(moduleDetails)
                                                .tenantId(tenantId)
                                                .build();
        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder()
                                                         .mdmsCriteria(mdmsCriteria)
                                                         .requestInfo(requestInfo)
                                                         .build();
        return mdmsCriteriaReq;
    }

    private ModuleDetail getTenantModuleDetail() {
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail masterDetail = getMasterDetailForSubModule(MASTER_TENANTS,TENANT_FILTER_CODE);
        masterDetails.add(masterDetail);
        ModuleDetail tenantModuleDetail = ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(MDMS_TENANT_MODULE_NAME).build();
        return tenantModuleDetail;
    }
    private MasterDetail getMasterDetailForSubModule(String masterDetailName, String filter){
      return MasterDetail.builder().name(masterDetailName)
              .filter(filter).build();
    }

    private ModuleDetail getWageSeekerSkillsModuleDetails() {
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail overHeadsMasterDetail = getMasterDetailForSubModule(WAGE_SEEKER_SKILLS, MDMS_COMMON_ACTIVE_FILTER);
        masterDetails.add(overHeadsMasterDetail);

        return ModuleDetail.builder()
                            .masterDetails(masterDetails)
                            .moduleName(MDMS_COMMON_MASTERS)
                            .build();
    }
    
    //TODO: This doesn't filter based on type. Need to add that in.
    private ModuleDetail getPayerListModuleDetails() {
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail payerListMasterDetail = getMasterDetailForSubModule(PAYER_MASTER, MDMS_COMMON_ACTIVE_FILTER);
        masterDetails.add(payerListMasterDetail);

        return ModuleDetail.builder()
                            .masterDetails(masterDetails)
                            .moduleName(EXPENSE_MODULE)
                            .build();
    }

    public StringBuilder getMDMSSearchUrl() {
        return new StringBuilder().append(config.getMdmsHost()).append(config.getMdmsEndPoint());
    }

    public Object fetchMDMSDataForWageSeekersSkills(RequestInfo requestInfo, String rootTenantId) {
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequestForWageSeekersSkills(requestInfo, rootTenantId);
        Object result = serviceRequestRepository.fetchResult(getMDMSSearchUrl(), mdmsCriteriaReq);
        return result;
    }
    
    public Object fetchMDMSDataForPayerList(RequestInfo requestInfo, String rootTenantId) {
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequestForPayerList(requestInfo, rootTenantId);
        Object result = serviceRequestRepository.fetchResult(getMDMSSearchUrl(), mdmsCriteriaReq);
        return result;
    }

    private MdmsCriteriaReq getMDMSRequestForWageSeekersSkills(RequestInfo requestInfo, String tenantId) {
        ModuleDetail wageSeekerSkillsModuleDetail = getWageSeekerSkillsModuleDetails();
        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(wageSeekerSkillsModuleDetail);
        return prepareMDMSCriteria(requestInfo,moduleDetails,tenantId);
    }
    
    private MdmsCriteriaReq getMDMSRequestForPayerList(RequestInfo requestInfo, String tenantId) {
        ModuleDetail payerListModuleDetails = getPayerListModuleDetails();
        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(payerListModuleDetails);
        return prepareMDMSCriteria(requestInfo,moduleDetails,tenantId);
    }
}
