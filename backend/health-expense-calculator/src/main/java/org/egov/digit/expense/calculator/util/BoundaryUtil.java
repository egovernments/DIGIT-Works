package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.Boundary;
import org.egov.digit.expense.calculator.web.models.BoundaryResponse;
import org.egov.digit.expense.calculator.web.models.TenantBoundary;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * BoundaryUtil
 *
 * ================================================================================
 * PURPOSE & BUSINESS CONTEXT
 * ================================================================================
 *
 * Utility class for fetching boundary/locality information from the Boundary Service.
 * Used during bill generation to determine project hierarchy and locality structure.
 *
 * WHY THIS CLASS EXISTS:
 * ----------------------
 * Bill generation requires understanding the boundary hierarchy:
 *   - Is this a district-level or village-level project?
 *   - What are the child boundaries (for aggregation)?
 *   - What is the locality code for register lookup?
 *
 * USAGE IN V2 INTERMEDIATE BILLING:
 * ----------------------------------
 * IntermediateBillingService calls this to:
 *   1. Check if localityCode is district level (checkIfDistrictLevel)
 *   2. Fetch child boundaries for register search
 *   3. Validate locality codes in billing request
 *
 * BOUNDARY SERVICE DEPENDENCY:
 * ----------------------------
 * Calls: {boundaryServiceHost}/boundary-service/boundary/_search
 * Parameters: tenantId, codes (locality), includeChildren
 *
 * ERROR HANDLING:
 * ---------------
 * Throws CustomException with BOUNDARY_SERVICE_SEARCH_ERROR if lookup fails.
 * This is intentional - bill generation should fail if boundary is invalid.
 *
 * ================================================================================
 */
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

    public List<TenantBoundary> fetchBoundary(RequestInfoWrapper request, String  localityCode, String tenantId, boolean includeChildren){
        try{
            // Extract hierarchyType from localityCode (first part before underscore)
            // e.g., MICROPLAN_MO_01_01_MOSSURILEE -> MICROPLAN
            String hierarchyType = localityCode.contains("_")
                    ? localityCode.substring(0, localityCode.indexOf("_"))
                    : localityCode;

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

            // Return the list of child boundary
            return boundaryResponse.getBoundary();
        } catch (Exception e) {
            log.error("Exception while searching boundaries for parent boundary codes");
            throw new CustomException("BOUNDARY_SERVICE_SEARCH_ERROR", "Error while fetching boundaries from Boundary Service: " + e.getMessage());
        }
    }

}
