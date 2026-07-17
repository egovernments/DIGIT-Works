package org.egov.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        List<String> uniqueIndividualIds = individualIds.stream().distinct().toList();
        WorkerSearch workerSearch = WorkerSearch.builder().tenantId(tenantId).individualId(uniqueIndividualIds).build();

        WorkerSearchRequest request = WorkerSearchRequest.builder().workerSearch(workerSearch).requestInfo(requestInfo).build();
        List<IndividualWorker> workers = new ArrayList<>();

        try {
            StringBuilder uri = new StringBuilder(host).append(searchPath).append("?tenantId=").append(tenantId);
            WorkerResponse response = mapper.convertValue(restRepo.fetchResult(uri, request), WorkerResponse.class);
            if (response != null && !CollectionUtils.isEmpty(response.getWorkers())) {
                workers.addAll(response.getWorkers());
            }
        }
        catch (Exception e) {
            log.error("Error while fetching workers from worker registry: ", e);
        }

        return workers.stream()
                .filter(worker -> !CollectionUtils.isEmpty(worker.getIndividualIds()))
                .collect(Collectors.toMap(
                        worker -> worker.getIndividualIds().stream().findFirst().get(),
                        worker -> worker, (w1, w2) -> w1)
                );
    }
}