package org.egov.util;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Component
@Slf4j
public class BoundaryUtil {

    private final ServiceRequestRepository serviceRequestRepository;

    private final Configuration config;

    @Autowired
    public BoundaryUtil(ServiceRequestRepository serviceRequestRepository, Configuration config) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.config = config;
    }

    /**
     * Takes map of locations with boundaryType as key and list of boundaries
     * as its values, tenantId, requestInfo and hierarchyType.
     * For each boundaryType, egov-location service is called with hierarchyType,tenantId and codes as parameters.
     * The boundaries in request are validated against the egov-location response
     *
     * @param locations
     * @param tenantId
     * @param requestInfo
     * @param hierarchyTypeCode
     */
    public void validateBoundaryDetails(Map<String, List<String>> locations, String tenantId, RequestInfo requestInfo, String hierarchyTypeCode) {
        log.info("BoundaryUtil::validateBoundaryDetails");
        for (Map.Entry<String, List<String>> entry : locations.entrySet()) {
            String boundaryType = entry.getKey();
            List<String> boundaries = entry.getValue();

            log.info("Validating boundary for boundary type " + boundaryType + " with hierarchyType " + hierarchyTypeCode);
            StringBuilder uri = getUri(tenantId, hierarchyTypeCode, boundaryType, boundaries);

            Optional<Object> response = Optional.ofNullable(serviceRequestRepository.fetchResult(uri, RequestInfoWrapper.builder().requestInfo(requestInfo).build()));

            if (response.isPresent()) {
                LinkedHashMap responseMap = (LinkedHashMap) response.get();
                if (CollectionUtils.isEmpty(responseMap))
                    throw new CustomException("BOUNDARY ERROR", "The response from location service is empty or null");
                String jsonString = new JSONObject(responseMap).toString();

                for (String boundary : boundaries) {
                    String boundaryCodePath = "$..boundary[?(@.code==\"{}\")]";
                    String jsonpath = boundaryCodePath.replace("{}", boundary);
                    DocumentContext context = JsonPath.parse(jsonString);
                    Object boundaryObject = context.read(jsonpath);

                    if (!(boundaryObject instanceof ArrayList) || CollectionUtils.isEmpty((ArrayList) boundaryObject)) {
                        log.error("The boundary data for the code " + boundary + " is not available");
                        throw new CustomException("INVALID_BOUNDARY_DATA", "The boundary data for the code "
                                + boundary + " is not available");
                    }
                }
            } else {
                log.error("Error in fetching data from egov-location for boundary validation");
                throw new CustomException("EGOV_LOCATION_SERVICE_FAILED", "Error in fetching data from egov-location");
            }
            log.info("The boundaries " + StringUtils.join(boundaries, ',') + " validated for boundary type " + boundaryType + " with tenantId " + tenantId);
        }

    }

    private StringBuilder getUri(String tenantId, String hierarchyTypeCode, String boundaryType, List<String> boundaries) {
        StringBuilder uri = new StringBuilder(config.getLocationHost());
        uri.append(config.getLocationContextPath()).append(config.getLocationEndpoint());
        uri.append("?").append("tenantId=").append(tenantId);

        if (hierarchyTypeCode != null)
            uri.append("&").append("hierarchyTypeCode=").append(hierarchyTypeCode);

        uri.append("&").append("boundaryType=").append(boundaryType).append("&").append("codes=")
                .append(StringUtils.join(boundaries, ','));
        return uri;
    }

}
