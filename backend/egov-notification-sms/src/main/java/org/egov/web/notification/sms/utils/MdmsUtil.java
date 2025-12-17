package org.egov.web.notification.sms.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.mdms.model.*;
import org.egov.web.notification.sms.config.Configuration;

//import org.egov.mdms.model.*;
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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Configuration configs;




    public Map<String, Map<String, JSONArray>> fetchMdmsData(RequestInfo requestInfo, String tenantId, String moduleName,
                                                                                List<String> masterNameList) {
        StringBuilder uri = new StringBuilder();
        uri.append(configs.getMdmsHost()).append(configs.getMdmsEndPoint());
        MdmsCriteriaReq mdmsCriteriaReq = prepareMdMsRequest(requestInfo, tenantId, moduleName, masterNameList);
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
        //log.info(ulbToCategoryListMap.toString());
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
		masterNames.forEach(name -> {
			masterDetails.add(MasterDetail.builder().name(name).build());
		});

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