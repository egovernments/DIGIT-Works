package org.egov.works.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.egov.works.util.ContractServiceConstants.PROJECT_NAME_CONSTANT;

@Component
@Slf4j
public class OrgUtils {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ServiceRequestRepository restRepo;

    @Autowired
    private ContractServiceConfiguration configs;

    public Object fetchOrg(RequestInfo requestInfo, String tenantId, List<String> ids){
        OrgSearchRequest orgSearchRequest = getOrgSearchRequest(requestInfo,tenantId,ids);
        StringBuilder url = getOrgRequestURL();
        return restRepo.fetchResult(url, orgSearchRequest);
    }

    private OrgSearchRequest getOrgSearchRequest(RequestInfo requestInfo, String tenantId, List<String> ids) {
        OrgSearchCriteria orgSearchCriteria = OrgSearchCriteria.builder()
                                                .id(ids)
                                                .tenantId(tenantId)
                                                .applicationStatus("ACTIVE")
                                                .build();

        return OrgSearchRequest.builder()
                .searchCriteria(orgSearchCriteria)
                .requestInfo(requestInfo)
                .build();
    }

    private StringBuilder getOrgRequestURL() {
        return new StringBuilder(configs.getOrgHost()).append(configs.getOrgSearchPath());

    }
}
