package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.ServiceRequestRepository;
import org.egov.digit.expense.web.models.WorkerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.egov.digit.expense.config.Constants.*;

@Component
@Slf4j
public class WorkerRegistryUtil {

    private final Configuration config;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ObjectMapper mapper;

    @Autowired
    public WorkerRegistryUtil(Configuration config,
                              ServiceRequestRepository serviceRequestRepository,
                              ObjectMapper mapper) {
        this.config = config;
        this.serviceRequestRepository = serviceRequestRepository;
        this.mapper = mapper;
    }

    /**
     * Fetches worker details from Worker Registry for a batch of individualIds,
     * paging through results in chunks of {@code works.worker.registry.search.page.size} (default 100).
     * Returns a Map keyed by individualId to WorkerDetails.
     * On failure, logs a warning and returns an empty map (graceful degradation).
     */
    public Map<String, WorkerDetails> fetchWorkersByIndividualIds(RequestInfo requestInfo,
                                                                   String tenantId,
                                                                   List<String> individualIds) {
        if (individualIds == null || individualIds.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, WorkerDetails> result = new HashMap<>();
        int pageSize = config.getWorkerRegistrySearchPageSize();
        int total = individualIds.size();

        for (int offset = 0; offset < total; offset += pageSize) {
            List<String> page = individualIds.subList(offset, Math.min(offset + pageSize, total));
            try {
                Map<String, WorkerDetails> pageResult = fetchPage(requestInfo, tenantId, page);
                result.putAll(pageResult);
            } catch (Exception e) {
                log.warn("Failed to fetch workers from Worker Registry for tenantId={}, offset={}, pageSize={}. " +
                        "Payment fields will remain null for this page. Error: {}", tenantId, offset, pageSize, e.getMessage());
            }
        }
        return result;
    }

    private Map<String, WorkerDetails> fetchPage(RequestInfo requestInfo, String tenantId,
                                                  List<String> pageIds) {
        StringBuilder uri = new StringBuilder()
                .append(config.getWorkerRegistryHost())
                .append(config.getWorkerRegistryEndpoint());

        ObjectNode requestBody = mapper.createObjectNode();
        requestBody.putPOJO(REQUEST_INFO, requestInfo);

        ObjectNode workerSearch = mapper.createObjectNode();
        workerSearch.put(TENANT_ID, tenantId);
        ArrayNode ids = mapper.createArrayNode();
        pageIds.forEach(ids::add);
        workerSearch.set(INDIVIDUAL_ID, ids);
        requestBody.set(WORKER_SEARCH, workerSearch);

        Object response = serviceRequestRepository.fetchResult(uri, requestBody);
        return parseWorkerResponse(response);
    }

    @SuppressWarnings("unchecked")
    private Map<String, WorkerDetails> parseWorkerResponse(Object response) {
        Map<String, WorkerDetails> result = new HashMap<>();
        try {
            List<Map<String, Object>> workers = JsonPath.read(response, WORKERS_RESPONSE_PATH);
            if (workers == null) return result;

            for (Map<String, Object> worker : workers) {
                WorkerDetails details = WorkerDetails.builder()
                        .workerId((String) worker.get("id"))
                        .paymentProvider((String) worker.get("paymentProvider"))
                        .payeeName((String) worker.get("payeeName"))
                        .payeePhoneNumber((String) worker.get("payeePhoneNumber"))
                        .bankAccount((String) worker.get("bankAccount"))
                        .bankCode((String) worker.get("bankCode"))
                        .beneficiaryCode((String) worker.get("beneficiaryCode"))
                        .build();

                List<String> individualIds = (List<String>) worker.get("individualIds");
                if (individualIds != null) {
                    for (String indId : individualIds) {
                        result.put(indId, details);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Failed to parse Worker Registry response: {}", e.getMessage());
        }
        return result;
    }
}
