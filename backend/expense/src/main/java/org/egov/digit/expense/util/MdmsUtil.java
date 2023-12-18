package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.config.Configuration;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Constants;
import org.egov.mdms.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MdmsUtil {

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final Configuration configs;

	@Autowired
	public MdmsUtil(RestTemplate restTemplate, ObjectMapper mapper, Configuration configs) {
		this.restTemplate = restTemplate;
		this.mapper = mapper;
		this.configs = configs;
	}


	public Map<String, Map<String, JSONArray>> fetchMdmsData(RequestInfo requestInfo, String tenantId) {
        StringBuilder uri = new StringBuilder();
        uri.append(configs.getMdmsHost()).append(configs.getMdmsEndPoint());
        MdmsCriteriaReq mdmsCriteriaReq = prepareMdMsRequest(requestInfo, tenantId);
        Object response = new HashMap<>();
        MdmsResponse mdmsResponse = new MdmsResponse();
        try {
            response = restTemplate.postForObject(uri.toString(), mdmsCriteriaReq, Map.class);
            mdmsResponse = mapper.convertValue(response, MdmsResponse.class);
        }catch(Exception e) {
            log.error("Exception occurred while fetching category lists from mdms: ",e);
        }


		log.info(mdmsResponse.toString());
        return mdmsResponse.getMdmsRes();
    }

	/**
	 * prepares Master Data request
	 * 
	 * @param tenantId
	 * @param requestInfo
	 * @return
	 */
	public MdmsCriteriaReq prepareMdMsRequest(RequestInfo requestInfo, String tenantId) {

		// Criteria for tenant module
		List<MasterDetail> tenantMasterDetails = new ArrayList<>();
		Constants.TENANT_MDMS_MASTER_NAMES.forEach(name -> tenantMasterDetails.add(MasterDetail.builder().name(name).build()));

		ModuleDetail tenantModuleDetail = ModuleDetail.builder()
				.moduleName(Constants.TENANT_MODULE_NAME)
				.masterDetails(tenantMasterDetails)
				.build();

		// Criteria for Expense module
		List<MasterDetail> expenseMasterDetails = new ArrayList<>();
		Constants.EXPENSE_MDMS_MASTER_NAMES.forEach(name -> expenseMasterDetails.add(MasterDetail.builder().name(name).build()));
		ModuleDetail expenseModuleDetail = ModuleDetail.builder()
				.moduleName(Constants.EXPENSE_MODULE_NAME)
				.masterDetails(expenseMasterDetails)
				.build();

		List<ModuleDetail> moduleDetails = new ArrayList<>();
		moduleDetails.add(tenantModuleDetail);
		moduleDetails.add(expenseModuleDetail);
		
		MdmsCriteria mdmsCriteria = MdmsCriteria.builder()
				.tenantId(tenantId)
				.moduleDetails(moduleDetails)
				.build();

		return MdmsCriteriaReq.builder()
				.requestInfo(requestInfo)
				.mdmsCriteria(mdmsCriteria)
				.build();
	}
	
}