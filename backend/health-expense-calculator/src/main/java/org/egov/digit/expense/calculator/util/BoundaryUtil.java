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

            // Return the list of child boundary
            return boundaryResponse.getBoundary();
        } catch (Exception e) {
            log.error("Exception while searching boundaries for parent boundary codes");
            throw new CustomException("BOUNDARY_SERVICE_SEARCH_ERROR", "Error while fetching boundaries from Boundary Service: " + e.getMessage());
        }
    }

}
