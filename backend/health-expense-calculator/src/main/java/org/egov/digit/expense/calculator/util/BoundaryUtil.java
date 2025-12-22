package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.Boundary;
import org.egov.digit.expense.calculator.web.models.BoundaryResponse;
import org.egov.digit.expense.calculator.web.models.TenantBoundary;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class BoundaryUtil {

    private final ServiceRequestRepository restRepo;
    private final ExpenseCalculatorConfiguration configuration;
    private final ObjectMapper mapper;

    @Autowired
    public BoundaryUtil(ServiceRequestRepository restRepo, ExpenseCalculatorConfiguration configuration, ObjectMapper mapper) {
        this.restRepo = restRepo;
        this.configuration = configuration;
        this.mapper = mapper;
    }

    /**
     * Fetches boundaries from boundary service (backward compatible - without hierarchyType).
     * This method is kept for backward compatibility with existing callers.
     * It first tries without hierarchyType, and if empty, falls back to trying with hierarchyType.
     *
     * @param request         RequestInfoWrapper containing request info
     * @param localityCode    The locality/boundary code to search for
     * @param tenantId        The tenant ID
     * @param includeChildren Whether to include child boundaries
     * @return List of TenantBoundary objects
     */
    public List<TenantBoundary> fetchBoundary(RequestInfoWrapper request, String localityCode, String tenantId, boolean includeChildren) {
        return fetchBoundary(request, localityCode, tenantId, includeChildren, null);
    }

    /**
     * Fetches boundaries from boundary service with explicit hierarchyType support.
     * Uses a backward-compatible approach:
     * 1. First tries to call WITHOUT hierarchyType parameter (primary approach)
     * 2. If response is null/empty, falls back to trying WITH hierarchyType
     *
     * @param request         RequestInfoWrapper containing request info
     * @param localityCode    The locality/boundary code to search for
     * @param tenantId        The tenant ID
     * @param includeChildren Whether to include child boundaries
     * @param hierarchyType   The hierarchy type (e.g., "MICROPLAN", "ADMIN"). Used as fallback if provided.
     * @return List of TenantBoundary objects
     */
    public List<TenantBoundary> fetchBoundary(RequestInfoWrapper request, String localityCode, String tenantId, boolean includeChildren, String hierarchyType) {
        try {
            // Primary approach: Try without hierarchyType first (backward compatible with master)
            log.info("Fetching boundary without hierarchyType for localityCode: {}", localityCode);
            List<TenantBoundary> boundaries = fetchBoundaryWithoutHierarchyType(request, localityCode, tenantId, includeChildren);

            // Fallback: If response is null/empty, try with hierarchyType
            if (CollectionUtils.isEmpty(boundaries)) {
                String effectiveHierarchyType;

                if (StringUtils.isNotBlank(hierarchyType)) {
                    // Use the explicitly provided hierarchyType
                    effectiveHierarchyType = hierarchyType;
                    log.info("Boundary search without hierarchyType returned empty. Using provided hierarchyType: {}", effectiveHierarchyType);
                } else {
                    // Extract hierarchyType from localityCode (first part before underscore)
                    // e.g., MICROPLAN_MO_01_01_MOSSURILEE -> MICROPLAN
                    // This maintains backward compatibility with master branch logic
                    effectiveHierarchyType = localityCode.contains("_")
                            ? localityCode.substring(0, localityCode.indexOf("_"))
                            : localityCode;
                    log.info("Boundary search without hierarchyType returned empty. Extracted hierarchyType '{}' from localityCode '{}'", effectiveHierarchyType, localityCode);
                }

                boundaries = fetchBoundaryWithHierarchyType(request, localityCode, tenantId, includeChildren, effectiveHierarchyType);
            }

            return boundaries;
        } catch (Exception e) {
            log.error("Exception while searching boundaries for localityCode '{}': {}", localityCode, e.getMessage());
            throw new CustomException("BOUNDARY_SERVICE_SEARCH_ERROR", "Error while fetching boundaries from Boundary Service: " + e.getMessage());
        }
    }

    /**
     * Fetches boundaries without hierarchyType parameter (primary approach - backward compatible).
     */
    private List<TenantBoundary> fetchBoundaryWithoutHierarchyType(RequestInfoWrapper request, String localityCode, String tenantId, boolean includeChildren) {
        try {
            Object boundarySearchResponse = restRepo.fetchResult(
                    new StringBuilder(configuration.getBoundaryServiceHost()
                            + configuration.getBoundarySearchUrl()
                            + "?limit=" + configuration.getDefaultLimit()
                            + "&offset=0&tenantId=" + tenantId
                            + "&codes=" + localityCode
                            + "&includeChildren=" + includeChildren),
                    request
            );

            BoundaryResponse boundaryResponse = mapper.convertValue(boundarySearchResponse, BoundaryResponse.class);
            return boundaryResponse != null ? boundaryResponse.getBoundary() : null;
        } catch (Exception e) {
            log.warn("Boundary search without hierarchyType failed for localityCode '{}': {}", localityCode, e.getMessage());
            return null;
        }
    }

    /**
     * Fetches boundaries with explicit hierarchyType parameter (fallback approach).
     *
     * @param hierarchyType The hierarchy type (e.g., "MICROPLAN", "ADMIN", "MICROPLAN_MO")
     */
    private List<TenantBoundary> fetchBoundaryWithHierarchyType(RequestInfoWrapper request, String localityCode, String tenantId, boolean includeChildren, String hierarchyType) {
        try {
            log.info("Fetching boundary with hierarchyType '{}' for localityCode '{}'", hierarchyType, localityCode);

            Object boundarySearchResponse = restRepo.fetchResult(
                    new StringBuilder(configuration.getBoundaryServiceHost()
                            + configuration.getBoundarySearchUrl()
                            + "?limit=" + configuration.getDefaultLimit()
                            + "&offset=0&tenantId=" + tenantId
                            + "&codes=" + localityCode
                            + "&includeChildren=" + includeChildren
                            + "&hierarchyType=" + hierarchyType),
                    request
            );

            BoundaryResponse boundaryResponse = mapper.convertValue(boundarySearchResponse, BoundaryResponse.class);
            return boundaryResponse != null ? boundaryResponse.getBoundary() : null;
        } catch (Exception e) {
            log.warn("Boundary search with hierarchyType '{}' failed for localityCode '{}': {}", hierarchyType, localityCode, e.getMessage());
            return null;
        }
    }

}
