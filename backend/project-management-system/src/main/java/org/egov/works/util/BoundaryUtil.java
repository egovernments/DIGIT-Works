package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static org.egov.works.util.ProjectConstants.*;

@Component
@Slf4j
public class BoundaryUtil {

    @Value("${egov.location.host}")
    private String locationHost;

    @Value("${egov.location.context.path}")
    private String locationContextPath;

    @Value("${egov.location.endpoint}")
    private String locationEndpoint;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public void validateBoundaryDetails(List<String> locations, String tenantId, RequestInfo requestInfo, String hierarchyTypeCode) {
        StringBuilder uri = new StringBuilder(locationHost);
        uri.append(locationContextPath).append(locationEndpoint);
        uri.append("?").append("tenantId=").append(tenantId);

        if (hierarchyTypeCode != null)
            uri.append("&").append("hierarchyTypeCode=").append(hierarchyTypeCode);

        uri.append("&").append("codes=")
                .append(StringUtils.join(locations, ','));

        Optional<Object> response = Optional.ofNullable(serviceRequestRepository.fetchResult(uri, RequestInfoWrapper.builder().requestInfo(requestInfo).build()));

        if (response.isPresent()) {
            LinkedHashMap responseMap = (LinkedHashMap) response.get();
            if (CollectionUtils.isEmpty(responseMap))
                throw new CustomException("BOUNDARY ERROR", "The response from location service is empty or null");
            String jsonString = new JSONObject(responseMap).toString();

            for (String location: locations) {

                int index = jsonString.indexOf(location);
                if (index == -1 || index < 10 || !jsonString.substring(index - 7, index - 3).equals(CODE)) {
                    log.error("The boundary data for the code " + location + " is not available");
                    throw new CustomException("INVALID_BOUNDARY_DATA", "The boundary data for the code " + location + " is not available");
                }

            }
        }
    }

}
