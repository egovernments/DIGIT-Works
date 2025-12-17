package org.egov.digit.expense.calculator.util;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.MdmsResponse;
import org.egov.mdms.model.ModuleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;

@Component
@Slf4j
public class MdmsUtils {
    private final ServiceRequestRepository serviceRequestRepository;
    
    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final ExpenseCalculatorConfiguration config;

    @Autowired
    public MdmsUtils(ServiceRequestRepository serviceRequestRepository, RestTemplate restTemplate, ObjectMapper mapper, ExpenseCalculatorConfiguration config) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.config = config;
    }

    public Map<String, Map<String, JSONArray>> fetchMdmsData(RequestInfo requestInfo, String tenantId,
			String moduleName, List<String> masterNameList) {
		StringBuilder uri = new StringBuilder();
		uri.append(config.getMdmsV2Host()).append(config.getMdmsV2EndPoint());
		MdmsCriteriaReq mdmsCriteriaReq = prepareMdMsRequest(requestInfo, tenantId, moduleName, masterNameList);
		Object response = new HashMap<>();
		MdmsResponse mdmsResponse = new MdmsResponse();
		try {
			response = restTemplate.postForObject(uri.toString(), mdmsCriteriaReq, Map.class);
			mdmsResponse = mapper.convertValue(response, MdmsResponse.class);
		} catch (Exception e) {
			log.error("Exception occurred while fetching category lists from mdms: ", e);
		}

		log.info(mdmsResponse.toString());
		return mdmsResponse.getMdmsRes();
	}

	/**
	 * prepares Master Data request
	 * 
	 * @param tenantId
	 * @param moduleName
	 * @param masterNames
	 * @param requestInfo
	 * @return
	 */
	public MdmsCriteriaReq prepareMdMsRequest(RequestInfo requestInfo, String tenantId, String moduleName,
			List<String> masterNames) {

		List<MasterDetail> masterDetails = new ArrayList<>();
		masterNames.forEach(name ->
			masterDetails.add(MasterDetail.builder().name(name).build())
		);

		ModuleDetail moduleDetail = ModuleDetail.builder()
				.moduleName(moduleName)
				.masterDetails(masterDetails)
				.build();
		
		List<ModuleDetail> moduleDetails = new ArrayList<>();
		moduleDetails.add(moduleDetail);
		
		MdmsCriteria mdmsCriteria = MdmsCriteria.builder()
				.tenantId(tenantId)
				.moduleDetails(moduleDetails)
				.build();

		return MdmsCriteriaReq.builder()
				.requestInfo(requestInfo)
				.mdmsCriteria(mdmsCriteria)
				.build();
	}
    public Object fetchMDMSForValidation(RequestInfo requestInfo, String tenantId) {
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSValidationRequest(requestInfo, tenantId);
        return serviceRequestRepository.fetchResult(getMDMSSearchUrl(), mdmsCriteriaReq);
    }

    public Object getPayersForTypeFromMDMS(RequestInfo requestInfo, String type, String tenantId){
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail masterDetail = getMasterDetailForSubModuleAndFilter(MDMS_PAYER_LIST, "$.[?(@.active==true && @.type=='"+type+"')]");
        masterDetails.add(masterDetail);
        ModuleDetail expenseModuleDetail = ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(MDMS_EXPENSE_MASTERS).build();

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(expenseModuleDetail);
        MdmsCriteriaReq mdmsCriteriaReq = prepareMDMSCriteria(requestInfo, moduleDetails, tenantId);

        return serviceRequestRepository.fetchResult(getMDMSSearchUrl(), mdmsCriteriaReq);
    }

    public Object getExpenseFromMDMSForSubmoduleWithFilter(RequestInfo requestInfo, String tenantId, String subModule){
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail masterDetail = getMasterDetailForSubModuleAndFilter(subModule,MDMS_COMMON_ACTIVE_FILTER );
        masterDetails.add(masterDetail);
        ModuleDetail expenseModuleDetail = ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(MDMS_EXPENSE_MASTERS).build();

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(expenseModuleDetail);
        MdmsCriteriaReq mdmsCriteriaReq = prepareMDMSCriteria(requestInfo, moduleDetails, tenantId);

        return serviceRequestRepository.fetchResult(getMDMSSearchUrl(), mdmsCriteriaReq);
    }

    public Object getExpenseFromMDMSForSubmodule(RequestInfo requestInfo, String tenantId, String subModule){
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail masterDetail = getMasterDetailForSubModule(subModule);
        masterDetails.add(masterDetail);
        ModuleDetail expenseModuleDetail = ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(MDMS_EXPENSE_MASTERS).build();

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(expenseModuleDetail);
        MdmsCriteriaReq mdmsCriteriaReq = prepareMDMSCriteria(requestInfo, moduleDetails, tenantId);

        return serviceRequestRepository.fetchResult(getMDMSSearchUrl(), mdmsCriteriaReq);
    }

    private MdmsCriteriaReq getMDMSValidationRequest(RequestInfo requestInfo, String tenantId) {
        ModuleDetail tenantModuleDetail = getTenantModuleDetail();
        ModuleDetail expenseModuleDetail = getExpenseModuleDetail();

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(tenantModuleDetail);
        moduleDetails.add(expenseModuleDetail);
        return prepareMDMSCriteria(requestInfo,moduleDetails,tenantId);
    }

    private ModuleDetail getExpenseModuleDetail() {
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail businessServiceMasterDetail = getMasterDetailForSubModuleAndFilter(MDMS_BUSINESS_SERVICE,JSON_PATH_FOR_MDMS_BUSINESS_SERVICE);
        MasterDetail headCodesMasterDetail = getMasterDetailForSubModuleAndFilter(MDMS_HEAD_CODES,FILTER_CODE);
        masterDetails.add(businessServiceMasterDetail);
        masterDetails.add(headCodesMasterDetail);
        return ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(EXPENSE_MODULE).build();
    }

    private ModuleDetail getTenantModuleDetail() {
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail masterDetail = getMasterDetailForSubModuleAndFilter(MASTER_TENANTS, FILTER_CODE);
        masterDetails.add(masterDetail);
        return ModuleDetail.builder().masterDetails(masterDetails)
                .moduleName(MDMS_TENANT_MODULE_NAME).build();
    }

    private MdmsCriteriaReq prepareMDMSCriteria(RequestInfo requestInfo,List<ModuleDetail> moduleDetails, String tenantId){
        MdmsCriteria mdmsCriteria = MdmsCriteria.builder()
                                                .moduleDetails(moduleDetails)
                                                .tenantId(tenantId)
                                                .build();
        return MdmsCriteriaReq.builder()
                .mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo)
                .build();
    }
    private MasterDetail getMasterDetailForSubModuleAndFilter(String masterDetailName, String filter){
      return MasterDetail.builder().name(masterDetailName)
              .filter(filter).build();
    }

    private MasterDetail getMasterDetailForSubModule(String masterDetailName){
        return MasterDetail.builder().name(masterDetailName)
                .build();
    }


    private ModuleDetail getLabourChargesModuleDetails() {
        List<MasterDetail> masterDetails = new ArrayList<>();
        MasterDetail overHeadsMasterDetail = getMasterDetailForSubModule(MDMS_LABOUR_CHARGES);
        masterDetails.add(overHeadsMasterDetail);

        return ModuleDetail.builder()
                            .masterDetails(masterDetails)
                            .moduleName(MDMS_EXPENSE_MASTERS)
                            .build();
    }


    public StringBuilder getMDMSSearchUrl() {
        return new StringBuilder().append(config.getMdmsV2Host()).append(config.getMdmsV2EndPoint());
    }

    public Object fetchMDMSDataForLabourCharges(RequestInfo requestInfo, String rootTenantId) {
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequestForLabourChanges(requestInfo, rootTenantId);
        return serviceRequestRepository.fetchResult(getMDMSSearchUrl(), mdmsCriteriaReq);
    }

    private MdmsCriteriaReq getMDMSRequestForLabourChanges(RequestInfo requestInfo, String tenantId) {
        ModuleDetail wageSeekerSkillsModuleDetail = getLabourChargesModuleDetails();
        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(wageSeekerSkillsModuleDetail);
        return prepareMDMSCriteria(requestInfo,moduleDetails,tenantId);
    }

    public Object getLabourSorFromMDMSV2(RequestInfo requestInfo, String tenantId, List<String> sorList, Boolean isRate) {
        String filter = getFilterFromSorList(sorList, isRate);

        MdmsCriteria mdmsCriteria = getMdmsCriteria(requestInfo, isRate ? RATES_CONSTANT : SOR_CONSTANT, tenantId, filter);

        return serviceRequestRepository.fetchResult(getMDMSV2SearchUrl(), MdmsCriteriaReq.builder().requestInfo(requestInfo).mdmsCriteria(mdmsCriteria).build());
    }


    private MdmsCriteria getMdmsCriteria (RequestInfo requestInfo, String masterName, String tenantId, String filter) {
        return MdmsCriteria.builder().tenantId(tenantId)
                .moduleDetails(Collections.singletonList(ModuleDetail.builder()
                        .moduleName(WORKS_SOR_CONSTANT)
                        .masterDetails(Collections.singletonList(MasterDetail.builder()
                                .name(masterName)
                                .filter(filter)
                                .build()))
                        .build()))
                .build();

    }

    String getFilterFromSorList(List<String> sorList, Boolean isRate) {
        String idConstant = isRate ? ID_SEARCH_CONSTANT_RATE : ID_SEARCH_CONSTANT;
        StringBuilder filterString = new StringBuilder(FILTER_START).append(idConstant).append(sorList.get(0));
        for (int i = 1; i < sorList.size(); i++) {
            filterString.append(FILTER_OR_CONSTANT);
            filterString.append(idConstant).append(sorList.get(i));
        }
        filterString.append(FILTER_END);
        return filterString.toString();
    }

    public StringBuilder getMDMSV2SearchUrl() {
        return new StringBuilder().append(config.getMdmsV2Host()).append(config.getMdmsV2EndPoint());
    }

}
