package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.works.config.ContractServiceConfiguration;
import digit.models.coremodels.IdGenerationRequest;
import digit.models.coremodels.IdGenerationResponse;
import digit.models.coremodels.IdRequest;
import digit.models.coremodels.IdResponse;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IdgenUtil {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ServiceRequestRepository restRepo;

    @Autowired
    private ContractServiceConfiguration configs;

    public List<String> getIdList(RequestInfo requestInfo, String tenantId, String idName, String idformat, Integer count) {
        List<IdRequest> reqList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            reqList.add(IdRequest.builder().idName(idName).format(idformat).tenantId(tenantId).build());
        }

        IdGenerationRequest request = IdGenerationRequest.builder().idRequests(reqList).requestInfo(requestInfo).build();
        StringBuilder uri = new StringBuilder(configs.getIdGenHost()).append(configs.getIdGenPath());
        IdGenerationResponse response = mapper.convertValue(restRepo.fetchResult(uri, request), IdGenerationResponse.class);

        List<IdResponse> idResponses = response.getIdResponses();

        if (CollectionUtils.isEmpty(idResponses))
            throw new CustomException("IDGEN_ERROR", "No ids returned from idgen Service");

        return idResponses.stream().map(IdResponse::getId).collect(Collectors.toList());
    }
}