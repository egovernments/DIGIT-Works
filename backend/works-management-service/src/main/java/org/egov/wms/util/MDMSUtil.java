package org.egov.wms.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.wms.web.model.WMSSearchRequest;
import org.egov.wms.web.model.V2.SearchQueryConfiguration;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.wms.util.WMSConstants.*;


@Component
public class MDMSUtil {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MultiStateInstanceUtil multiStateInstanceUtil;

    @Value("${egov.mdms.host}")
    private String mdmsHost;

    @Value("${egov.mdms.search.endpoint}")
    private String mdmsUrl;

    @Autowired
    private ObjectMapper objectMapper;

    @Cacheable(value="inboxConfiguration")
    public SearchQueryConfiguration getConfigFromMDMS(WMSSearchRequest wmsSearchRequest, String module) {

        RequestInfo requestInfo = wmsSearchRequest.getRequestInfo();
        String tenantId = wmsSearchRequest.getInbox().getTenantId();

        StringBuilder uri = new StringBuilder();
        uri.append(mdmsHost).append(mdmsUrl);
        MdmsCriteriaReq mdmsCriteriaReq = getMdmsRequestForInboxQueryConfiguration(requestInfo, tenantId);
        Object response = new HashMap<>();
        List<Map> configs;
        try {
            response = restTemplate.postForObject(uri.toString(), mdmsCriteriaReq, Map.class);
            String jsonpath = MDMS_RESPONSE_JSONPATH.replace(MODULE_PLACEHOLDER, module);
            configs = JsonPath.read(response, jsonpath);
        }catch(Exception e) {
            throw new CustomException("CONFIG_ERROR","Error in fetching search query configuration from MDMS for: "+module);
        }

        if (CollectionUtils.isEmpty(configs))
            throw new CustomException("CONFIG_ERROR","Search Query Configuration not found in MDMS response for: "+module);

        SearchQueryConfiguration configuration = objectMapper.convertValue(configs.get(0), SearchQueryConfiguration.class);

        return configuration;
    }

    private MdmsCriteriaReq getMdmsRequestForInboxQueryConfiguration(RequestInfo requestInfo, String tenantId) {
        MasterDetail masterDetail = new MasterDetail();
        masterDetail.setName(INBOX_QUERY_CONFIG_NAME);
        List<MasterDetail> masterDetailList = new ArrayList<>();
        masterDetailList.add(masterDetail);

        ModuleDetail moduleDetail = new ModuleDetail();
        moduleDetail.setMasterDetails(masterDetailList);
        moduleDetail.setModuleName(INBOX_MODULE_CODE);
        List<ModuleDetail> moduleDetailList = new ArrayList<>();
        moduleDetailList.add(moduleDetail);

        MdmsCriteria mdmsCriteria = new MdmsCriteria();
        mdmsCriteria.setTenantId(multiStateInstanceUtil.getStateLevelTenant(tenantId));
        mdmsCriteria.setModuleDetails(moduleDetailList);

        MdmsCriteriaReq mdmsCriteriaReq = new MdmsCriteriaReq();
        mdmsCriteriaReq.setMdmsCriteria(mdmsCriteria);
        mdmsCriteriaReq.setRequestInfo(requestInfo);

        return mdmsCriteriaReq;
    }
}

