package org.egov.util;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.util.EstimateServiceConstant.LOCATION_BOUNDARY_NAME_CODE;

@Component
@Slf4j
public class LocationServiceUtil {

    private final EstimateServiceConfiguration config;

    private final ServiceRequestRepository serviceRequestRepository;

    @Autowired
    public LocationServiceUtil(EstimateServiceConfiguration config, ServiceRequestRepository serviceRequestRepository) {
        this.config = config;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public Map<String, String> getLocationName(String tenantId, RequestInfo requestInfo, String boundaryCode, String boundaryType) {
        StringBuilder uri = getLocationURI(tenantId, boundaryCode,boundaryType);

        Object response = serviceRequestRepository.fetchResult(uri, RequestInfoWrapper.builder().requestInfo(requestInfo).build());

        Map<String, String> locationDetails = new HashMap<>();
        List<String> locationNames = null;

        try {
            locationNames = JsonPath.read(response, LOCATION_BOUNDARY_NAME_CODE);

        } catch (Exception e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse HRMS response");
        }

        locationDetails.put("locationName", locationNames.get(0));

        return locationDetails;
    }

    private StringBuilder getLocationURI(String tenantId, String boundaryCode, String boundaryType) {

        StringBuilder builder = new StringBuilder(config.getLocationHost());
        builder.append(config.getLocationContextPath()).append(config.getLocationEndpoint());
        builder.append("?tenantId=");
        builder.append(tenantId);
        builder.append("&boundaryType=");
        builder.append(boundaryType);
        builder.append("&codes=");
        builder.append(boundaryCode);

        return builder;
    }

}
