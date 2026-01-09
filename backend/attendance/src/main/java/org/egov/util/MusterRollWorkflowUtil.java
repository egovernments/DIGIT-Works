package org.egov.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.workflow.BusinessService;
import org.egov.common.contract.workflow.BusinessServiceResponse;
import org.egov.common.contract.workflow.State;
import org.egov.config.AttendanceServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MusterRollWorkflowUtil
 *
 * Utility class to fetch muster roll workflow states dynamically from the workflow service.
 * This eliminates the need to hardcode workflow states in the attendance service.
 *
 * WORKFLOW STATES:
 * ----------------
 * The muster roll workflow (HCMMUSTERROLL) typically has states like:
 *   - APPROVAL_PENDING (or similar intermediate states)
 *   - APPROVED (terminal state - billing ready)
 *   - REJECTED, SENT_BACK (optional states based on workflow config)
 *
 * The terminal state (isTerminateState=true) is considered "APPROVED" for billing purposes.
 * All other states are treated as "PENDING" for V1 compatibility.
 *
 * CACHING:
 * --------
 * Workflow states are cached per tenant to avoid repeated API calls.
 * Cache is populated on first request per tenant.
 */
@Component
@Slf4j
public class MusterRollWorkflowUtil {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final AttendanceServiceConfiguration config;

    // Cache: tenantId -> Set of terminal state names (typically just "APPROVED")
    private final ConcurrentHashMap<String, Set<String>> terminalStatesCache = new ConcurrentHashMap<>();

    // Cache: tenantId -> Set of all workflow state names
    private final ConcurrentHashMap<String, Set<String>> allStatesCache = new ConcurrentHashMap<>();

    @Autowired
    public MusterRollWorkflowUtil(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            AttendanceServiceConfiguration config) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.config = config;
    }

    /**
     * Check if the given status is a terminal (billing-ready) workflow state.
     * Terminal states are fetched dynamically from the workflow service.
     *
     * @param status The muster roll status to check
     * @param tenantId The tenant ID for workflow lookup
     * @param requestInfo Request info for API call
     * @return true if status is a terminal state (e.g., APPROVED), false otherwise
     */
    public boolean isTerminalState(String status, String tenantId, RequestInfo requestInfo) {
        if (status == null || status.isBlank()) {
            return false;
        }

        Set<String> terminalStates = getTerminalStates(tenantId, requestInfo);
        return terminalStates.contains(status.toUpperCase());
    }

    /**
     * Check if the given status is a valid workflow state.
     *
     * @param status The status to check
     * @param tenantId The tenant ID for workflow lookup
     * @param requestInfo Request info for API call
     * @return true if status is a valid workflow state, false otherwise
     */
    public boolean isValidWorkflowState(String status, String tenantId, RequestInfo requestInfo) {
        if (status == null || status.isBlank()) {
            return false;
        }

        Set<String> allStates = getAllWorkflowStates(tenantId, requestInfo);
        return allStates.contains(status.toUpperCase());
    }

    /**
     * Get all terminal (billing-ready) states from the workflow service.
     * Results are cached per tenant.
     *
     * @param tenantId The tenant ID
     * @param requestInfo Request info for API call
     * @return Set of terminal state names (uppercase)
     */
    public Set<String> getTerminalStates(String tenantId, RequestInfo requestInfo) {
        return terminalStatesCache.computeIfAbsent(
                getBaseTenantId(tenantId),
                key -> fetchTerminalStatesFromWorkflow(key, requestInfo)
        );
    }

    /**
     * Get all workflow states from the workflow service.
     * Results are cached per tenant.
     *
     * @param tenantId The tenant ID
     * @param requestInfo Request info for API call
     * @return Set of all workflow state names (uppercase)
     */
    public Set<String> getAllWorkflowStates(String tenantId, RequestInfo requestInfo) {
        return allStatesCache.computeIfAbsent(
                getBaseTenantId(tenantId),
                key -> fetchAllStatesFromWorkflow(key, requestInfo)
        );
    }

    /**
     * Fetch terminal states from workflow service.
     */
    private Set<String> fetchTerminalStatesFromWorkflow(String tenantId, RequestInfo requestInfo) {
        Set<String> terminalStates = new HashSet<>();

        try {
            BusinessService businessService = fetchBusinessService(tenantId, requestInfo);

            if (businessService != null && !CollectionUtils.isEmpty(businessService.getStates())) {
                for (State state : businessService.getStates()) {
                    if (Boolean.TRUE.equals(state.getIsTerminateState()) && state.getState() != null) {
                        terminalStates.add(state.getState().toUpperCase());
                        log.info("fetchTerminalStatesFromWorkflow::Found terminal state: {} for tenant: {}",
                                state.getState(), tenantId);
                    }
                }
            }

            if (terminalStates.isEmpty()) {
                // Fallback: If no terminal states found, default to APPROVED
                log.warn("fetchTerminalStatesFromWorkflow::No terminal states found for tenant: {}. Defaulting to APPROVED", tenantId);
                terminalStates.add("APPROVED");
            }

        } catch (Exception e) {
            log.error("fetchTerminalStatesFromWorkflow::Failed to fetch workflow states for tenant: {}. Defaulting to APPROVED. Error: {}",
                    tenantId, e.getMessage());
            terminalStates.add("APPROVED");
        }

        return terminalStates;
    }

    /**
     * Fetch all states from workflow service.
     */
    private Set<String> fetchAllStatesFromWorkflow(String tenantId, RequestInfo requestInfo) {
        Set<String> allStates = new HashSet<>();

        try {
            BusinessService businessService = fetchBusinessService(tenantId, requestInfo);

            if (businessService != null && !CollectionUtils.isEmpty(businessService.getStates())) {
                for (State state : businessService.getStates()) {
                    if (state.getState() != null) {
                        allStates.add(state.getState().toUpperCase());
                    }
                }
                log.info("fetchAllStatesFromWorkflow::Found {} workflow states for tenant: {}: {}",
                        allStates.size(), tenantId, allStates);
            }

        } catch (Exception e) {
            log.error("fetchAllStatesFromWorkflow::Failed to fetch workflow states for tenant: {}. Error: {}",
                    tenantId, e.getMessage());
        }

        return allStates;
    }

    /**
     * Fetch BusinessService from workflow service API.
     */
    private BusinessService fetchBusinessService(String tenantId, RequestInfo requestInfo) {
        try {
            String url = config.getWorkflowHost() + config.getWorkflowBusinessServiceSearchPath()
                    + "?tenantId=" + tenantId
                    + "&businessServices=" + config.getMusterRollWorkflowBusinessService();

            log.debug("fetchBusinessService::Calling workflow service: {}", url);

            Object response = restTemplate.postForObject(url, requestInfo, Object.class);
            BusinessServiceResponse businessServiceResponse = objectMapper.convertValue(response, BusinessServiceResponse.class);

            if (businessServiceResponse != null && !CollectionUtils.isEmpty(businessServiceResponse.getBusinessServices())) {
                return businessServiceResponse.getBusinessServices().get(0);
            }

        } catch (Exception e) {
            log.error("fetchBusinessService::Error calling workflow service for tenant: {} - Error: {}",
                    tenantId, e.getMessage());
        }

        return null;
    }

    /**
     * Extract base tenant ID (state level) from full tenant ID.
     * Example: "mz.district1" -> "mz"
     */
    private String getBaseTenantId(String tenantId) {
        if (tenantId == null || !tenantId.contains(".")) {
            return tenantId;
        }
        return tenantId.split("\\.")[0];
    }

    /**
     * Clear cached workflow states (for testing or refresh purposes).
     */
    public void clearCache() {
        terminalStatesCache.clear();
        allStatesCache.clear();
        log.info("clearCache::Workflow states cache cleared");
    }
}
