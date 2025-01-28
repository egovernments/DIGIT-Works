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

import java.util.*;

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

    public Map<String,Map<String,JSONArray>> fetchExchangeServers(RequestInfo requestInfo, String tenantId) {
        StringBuilder uri = new StringBuilder();
        uri.append(configs.getMdmsV2Host()).append(configs.getMdmsV2EndPoint());
        MdmsCriteriaReq mdmsCriteriaReq = prepareMdMsRequest(requestInfo, tenantId, MDMS_EXCHANGE_MODULE_NAME, Arrays.asList(MDMS_EXCHANGE_SERVER_MASTER));
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
    public Map<String, Map<String, JSONArray>> fetchMdmsData(RequestInfo requestInfo, String tenantId) {
        StringBuilder uri = new StringBuilder();
        uri.append(configs.getMdmsV2Host()).append(configs.getMdmsV2EndPoint());
        MdmsCriteriaReq mdmsCriteriaReq = getMdmsRequest(requestInfo, tenantId);
        Object response = new HashMap<>();
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

        // Master Details for SSU
        List<MasterDetail> ssuMasterDetails = new ArrayList<>();
        MasterDetail ssuMasterDetail = MasterDetail.builder().name(MDMS_SSU_DETAILS_MASTER).build();
        ssuMasterDetails.add(ssuMasterDetail);
        ModuleDetail ssuModuleDetail = ModuleDetail.builder().masterDetails(ssuMasterDetails)
                .moduleName(MDMS_IFMS_MODULE_NAME).build();

        // Master Details for Exchange Codes
        List<MasterDetail> exchangeCodesMasterDetails = new ArrayList<>();
        MasterDetail administrativeCodesMaster = MasterDetail.builder().name(MDMS_ADMINISTRATIVE_CODES_MASTER).build();
        MasterDetail economicSegmentCodesMaster = MasterDetail.builder().name(MDMS_ECONOMIC_SEGMENT_CODES_MASTER).build();
        MasterDetail functionCodesMaster = MasterDetail.builder().name(MDMS_FUNCTION_CODES_MASTER).build();
        MasterDetail recipientSegmentCodesMaster = MasterDetail.builder().name(MDMS_RECIPIENT_SEGMENT_CODES_MASTER).build();
        MasterDetail sourceOfFundsCodesMaster = MasterDetail.builder().name(MDMS_SOURCE_OF_FUNDS_CODE_MASTER).build();
        MasterDetail targetSegmentCodesMaster = MasterDetail.builder().name(MDMS_TARGET_SEGMENT_CODES_MASTER).build();
        MasterDetail geographicSegmentCodesMaster = MasterDetail.builder().name(MDMS_GEOGRAPHIC_CODES_MASTER).build();
        exchangeCodesMasterDetails.add(administrativeCodesMaster);
        exchangeCodesMasterDetails.add(economicSegmentCodesMaster);
        exchangeCodesMasterDetails.add(functionCodesMaster);
        exchangeCodesMasterDetails.add(recipientSegmentCodesMaster);
        exchangeCodesMasterDetails.add(sourceOfFundsCodesMaster);
        exchangeCodesMasterDetails.add(targetSegmentCodesMaster);
        exchangeCodesMasterDetails.add(geographicSegmentCodesMaster);
        ModuleDetail exchangeCodesModuleDetail = ModuleDetail.builder().masterDetails(exchangeCodesMasterDetails)
                .moduleName(MDMS_SEGMENT_CODES_MODULE).build();


        List<ModuleDetail> moduleDetailList = new ArrayList<>();
        moduleDetailList.add(tenantModuleDetail);
        moduleDetailList.add(headCodesModuleDetail);
        moduleDetailList.add(ssuModuleDetail);
        moduleDetailList.add(exchangeCodesModuleDetail);

        MdmsCriteria mdmsCriteria = new MdmsCriteria();
        mdmsCriteria.setTenantId(tenantId);
        mdmsCriteria.setModuleDetails(moduleDetailList);

        MdmsCriteriaReq mdmsCriteriaReq = new MdmsCriteriaReq();
        mdmsCriteriaReq.setMdmsCriteria(mdmsCriteria);
        mdmsCriteriaReq.setRequestInfo(requestInfo);

        return mdmsCriteriaReq;
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
        masterNames.forEach(name -> masterDetails.add(MasterDetail.builder().name(name).build()));

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
}