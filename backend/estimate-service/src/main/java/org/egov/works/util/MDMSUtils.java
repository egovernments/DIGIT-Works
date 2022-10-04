package org.egov.works.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
import org.egov.works.config.EstimateServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.Estimate;
import org.egov.works.web.models.EstimateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.egov.works.util.EstimateServiceConstant.*;

@Component
@Slf4j
public class MDMSUtils {

    public static final String PLACEHOLDER_CODE = "{code}";
    public static final String filterCode = "$.*.code";
    public final String filterWorksModuleCode = "$.[?(@.active==true && @.code=='{code}')]";
    public final String filterSubSchemeModuleCode = "$.[?(@.active==true)].subSchemes.[?(@.active==true && @.code=='{code}')]]";
    public final String filterSubTypeModuleCode = "$.[?(@.active==true)].subTypes.[?(@.active==true && @.code=='{code}')]]";
    private EstimateServiceConfiguration config;
    private ServiceRequestRepository serviceRequestRepository;


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
        RequestInfo requestInfo = request.getRequestInfo();
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequest(requestInfo, tenantId, request);
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

        ModuleDetail estimateWorksModuleDetail = getWorksModuleRequestData(request);
        ModuleDetail estimateDepartmentModuleDetail = getDepartmentModuleRequestData(request);
        ModuleDetail estimateFinanceModuleDetail = getFinanceModuleRequestData(request);
        ModuleDetail estimateTenantModuleDetail = getTenantModuleRequestData(request);

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(estimateWorksModuleDetail);
        moduleDetails.add(estimateFinanceModuleDetail);
        moduleDetails.add(estimateTenantModuleDetail);
        moduleDetails.add(estimateDepartmentModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();
        return mdmsCriteriaReq;
    }

    private ModuleDetail getDepartmentModuleRequestData(EstimateRequest request) {

        Estimate estimate = request.getEstimate();
        List<MasterDetail> estimateDepartmentMasterDetails = new ArrayList<>();

        MasterDetail departmentMasterDetails = MasterDetail.builder().name(MASTER_DEPARTMENT)
                .filter(filterWorksModuleCode.replace(PLACEHOLDER_CODE, estimate.getDepartment())).build();

        estimateDepartmentMasterDetails.add(departmentMasterDetails);

        ModuleDetail estimateDepartmentModuleDetail = ModuleDetail.builder().masterDetails(estimateDepartmentMasterDetails)
                .moduleName(MDMS_COMMON_MASTERS_MODULE_NAME).build();

        return estimateDepartmentModuleDetail;
    }

    private ModuleDetail getTenantModuleRequestData(EstimateRequest request) {
        Estimate estimate = request.getEstimate();
        List<MasterDetail> estimateTenantMasterDetails = new ArrayList<>();

        MasterDetail tenantMasterDetails = MasterDetail.builder().name(MASTER_TENANTS)
                .filter(filterCode).build();

        estimateTenantMasterDetails.add(tenantMasterDetails);

        ModuleDetail estimateTenantModuleDetail = ModuleDetail.builder().masterDetails(estimateTenantMasterDetails)
                .moduleName(MDMS_TENANT_MODULE_NAME).build();

        return estimateTenantModuleDetail;
    }

    private ModuleDetail getFinanceModuleRequestData(EstimateRequest request) {
        Estimate estimate = request.getEstimate();
        List<MasterDetail> estimateWorksMasterDetails = new ArrayList<>();

        MasterDetail fundMasterDetails = MasterDetail.builder().name(MASTER_FUND)
                .filter(filterWorksModuleCode.replace(PLACEHOLDER_CODE, estimate.getFund())).build();

        MasterDetail functionsMasterDetails = MasterDetail.builder().name(MASTER_FUNCTIONS)
                .filter(filterWorksModuleCode.replace(PLACEHOLDER_CODE, estimate.getFunction())).build();

        MasterDetail budgetHeadMasterDetails = MasterDetail.builder().name(MASTER_BUDGET_HEAD)
                .filter(filterWorksModuleCode.replace(PLACEHOLDER_CODE, estimate.getBudgetHead())).build();

        MasterDetail schemeMasterDetails = MasterDetail.builder().name(MASTER_SCHEME)
                .filter(filterWorksModuleCode.replace(PLACEHOLDER_CODE, estimate.getScheme() != null ? estimate.getScheme() : "")).build();

        MasterDetail subSchemeMasterDetails = MasterDetail.builder().name(MASTER_SCHEME)
                .filter(filterSubSchemeModuleCode.replace(PLACEHOLDER_CODE, estimate.getSubScheme() != null ? estimate.getSubScheme() : "")).build();


        estimateWorksMasterDetails.add(fundMasterDetails);
        estimateWorksMasterDetails.add(functionsMasterDetails);
        estimateWorksMasterDetails.add(schemeMasterDetails);
        estimateWorksMasterDetails.add(subSchemeMasterDetails);
        estimateWorksMasterDetails.add(budgetHeadMasterDetails);

        ModuleDetail estimateFinanceModuleDetail = ModuleDetail.builder().masterDetails(estimateWorksMasterDetails)
                .moduleName(MDMS_FINANCE_MODULE_NAME).build();

        return estimateFinanceModuleDetail;
    }

    private ModuleDetail getWorksModuleRequestData(EstimateRequest request) {
        Estimate estimate = request.getEstimate();
        List<MasterDetail> estimateWorksMasterDetails = new ArrayList<>();

        MasterDetail beneficiaryMasterDetails = MasterDetail.builder().name(MASTER_BENEFICIART_TYPE)
                .filter(filterWorksModuleCode.replace(PLACEHOLDER_CODE, estimate.getBeneficiaryType())).build();

        MasterDetail entrustmentMasterDetails = MasterDetail.builder().name(MASTER_ENTRUSTMENTMODE)
                .filter(filterWorksModuleCode.replace(PLACEHOLDER_CODE, estimate.getEntrustmentMode())).build();

        MasterDetail typeOfWorkMasterDetails = MasterDetail.builder().name(MASTER_TYPEOFWORK)
                .filter(filterWorksModuleCode.replace(PLACEHOLDER_CODE, estimate.getTypeOfWork())).build();

        MasterDetail subTypeOfWorkMasterDetails = MasterDetail.builder().name(MASTER_TYPEOFWORK)
                .filter(filterSubTypeModuleCode.replace(PLACEHOLDER_CODE, (estimate.getSubTypeOfWork() != null ? estimate.getSubTypeOfWork() : ""))).build();

        MasterDetail natureOfWorkMasterDetails = MasterDetail.builder().name(MASTER_NATUREOFWORK)
                .filter(filterWorksModuleCode.replace(PLACEHOLDER_CODE, estimate.getNatureOfWork())).build();

        estimateWorksMasterDetails.add(beneficiaryMasterDetails);
        estimateWorksMasterDetails.add(entrustmentMasterDetails);
        estimateWorksMasterDetails.add(typeOfWorkMasterDetails);
        estimateWorksMasterDetails.add(subTypeOfWorkMasterDetails);
        estimateWorksMasterDetails.add(natureOfWorkMasterDetails);

        ModuleDetail estimateWorksModuleDetail = ModuleDetail.builder().masterDetails(estimateWorksMasterDetails)
                .moduleName(MDMS_WORKS_MODULE_NAME).build();

        return estimateWorksModuleDetail;
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
