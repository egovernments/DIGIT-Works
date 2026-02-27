package org.egov.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.worker.IndividualWorker;
import org.egov.web.models.worker.WorkerResponse;
import org.egov.web.models.worker.WorkerSearch;
import org.egov.web.models.worker.WorkerSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class WorkerRegistryUtil {

    @Value("${egov.worker.registry.enabled:true}")
    private Boolean enabled;

    @Value("${egov.worker.registry.host}")
    private String host;

    @Value("${egov.worker.registry.search.path}")
    private String searchPath;

    private final ObjectMapper mapper;

    private final ServiceRequestRepository restRepo;

    @Autowired
    public WorkerRegistryUtil(ObjectMapper mapper, ServiceRequestRepository restRepo) {
        this.mapper = mapper;
        this.restRepo = restRepo;
    }

    public Map<String, IndividualWorker> getWorkers(RequestInfo requestInfo, String tenantId, List<String> individualIds) {
        Map<String, IndividualWorker> individualWorkersMap = new HashMap<>();
        if(!enabled || CollectionUtils.isEmpty(individualIds)) return individualWorkersMap;
        WorkerSearch workerSearch = WorkerSearch.builder().tenantId(tenantId).individualId(individualIds).build();

        WorkerSearchRequest request = WorkerSearchRequest.builder().workerSearch(workerSearch).requestInfo(requestInfo).build();
        StringBuilder uri = new StringBuilder(host).append(searchPath).append("?tenantId=").append(tenantId);
        WorkerResponse response = mapper.convertValue(restRepo.fetchResult(uri, request), WorkerResponse.class);

        List<IndividualWorker> workers = Optional.ofNullable(response.getWorkers()).orElse(new ArrayList<>());

        return workers.stream().collect(Collectors.toMap(IndividualWorker::getIndividualId, worker -> worker));
    }
}