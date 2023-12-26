package org.egov.works.mukta.adapter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.mdms.model.*;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.works.mukta.adapter.config.Constants.*;

@Slf4j
@Component
public class MdmsUtil {

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final MuktaAdaptorConfig configs;

    @Autowired
    public MdmsUtil(RestTemplate restTemplate, ObjectMapper mapper, MuktaAdaptorConfig configs) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.configs = configs;
    }


    public Map<String, Map<String, JSONArray>> fetchMdmsData(RequestInfo requestInfo, String tenantId) {
        StringBuilder uri = new StringBuilder();
        uri.append(configs.getMdmsHost()).append(configs.getMdmsEndPoint());
        MdmsCriteriaReq mdmsCriteriaReq = getMdmsRequest(requestInfo, tenantId);
        Object response = new HashMap<>();
        Integer rate = 0;
        MdmsResponse mdmsResponse = new MdmsResponse();
        try {
            response = restTemplate.postForObject(uri.toString(), mdmsCriteriaReq, Map.class);
            mdmsResponse = mapper.convertValue(response, MdmsResponse.class);
        } catch (Exception e) {
            log.error(ERROR_WHILE_FETCHING_FROM_MDMS, e);
        }

        return mdmsResponse.getMdmsRes();
    }

    private MdmsCriteriaReq getMdmsRequest(RequestInfo requestInfo, String tenantId) {
        // Master Detail for tenantId
        List<MasterDetail> tenantModuleMasterDetails = new ArrayList<>();
        MasterDetail tenantModuleMasterDetail = MasterDetail.builder().name(MDMS_TENANTS_MASTER).build();
        tenantModuleMasterDetails.add(tenantModuleMasterDetail);
        ModuleDetail tenantModuleDetail = ModuleDetail.builder().masterDetails(tenantModuleMasterDetails)
                .moduleName(MDMS_TENANT_MODULE_NAME).build();

        // Master Details for Headcodes
        List<MasterDetail> headCodesMasterDetails = new ArrayList<>();
        MasterDetail headCodesMasterDetail = MasterDetail.builder().name(MDMS_HEAD_CODES_MASTER).build();
        headCodesMasterDetails.add(headCodesMasterDetail);
        ModuleDetail headCodesModuleDetail = ModuleDetail.builder().masterDetails(headCodesMasterDetails)
                .moduleName(MDMS_EXPENSE_MODULE_NAME).build();

        List<ModuleDetail> moduleDetailList = new ArrayList<>();
        moduleDetailList.add(tenantModuleDetail);
        moduleDetailList.add(headCodesModuleDetail);

        MdmsCriteria mdmsCriteria = new MdmsCriteria();
        mdmsCriteria.setTenantId(tenantId);
        mdmsCriteria.setModuleDetails(moduleDetailList);

        MdmsCriteriaReq mdmsCriteriaReq = new MdmsCriteriaReq();
        mdmsCriteriaReq.setMdmsCriteria(mdmsCriteria);
        mdmsCriteriaReq.setRequestInfo(requestInfo);

        return mdmsCriteriaReq;
    }
}