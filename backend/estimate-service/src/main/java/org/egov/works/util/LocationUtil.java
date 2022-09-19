package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.EstimateServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.Estimate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@Slf4j
public class LocationUtil {

    @Autowired
    private EstimateServiceConfiguration serviceConfiguration;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ObjectMapper mapper;

    public void getLocation(Estimate estimate, RequestInfo requestInfo, String hierarchyTypeCode) {

        if (ObjectUtils.isEmpty(estimate))
            return;
        String tenantId = estimate.getTenantId();

        if (StringUtils.isNotBlank(estimate.getLocation())) {
            StringBuilder uri = new StringBuilder(serviceConfiguration.getLocationHost());
            uri.append(serviceConfiguration.getLocationContextPath())
                    .append(serviceConfiguration.getLocationEndpoint());
            uri.append("?").append("tenantId=").append(tenantId);
            if (hierarchyTypeCode != null)
                uri.append("&").append("hierarchyTypeCode=").append(hierarchyTypeCode);

            //type hardcoded
            uri.append("&").append("boundaryType=").append("Zone")
                    .append("&").append("codes=").append(estimate.getLocation());

            Object locationRes = serviceRequestRepository.fetchResult(uri, RequestInfoWrapper.builder().requestInfo(requestInfo).build());

            if(locationRes != null){

            }
            /*if (locationRes!=null) {
                LinkedHashMap responseMap = (LinkedHashMap) locationRes;
                if (CollectionUtils.isEmpty(responseMap))
                    throw new CustomException("BOUNDARY ERROR", "The response from location service is empty or null");
                String jsonString = new JSONObject(responseMap).toString();

                Map<String, String> propertyIdToJsonPath = getJsonpath(estimate);

                DocumentContext context = JsonPath.parse(jsonString);

                Object boundaryObject = context.read(propertyIdToJsonPath.get(estimate.getPropertyId()));
                if (!(boundaryObject instanceof ArrayList) || CollectionUtils.isEmpty((ArrayList) boundaryObject))
                    throw new CustomException("BOUNDARY MDMS DATA ERROR", "The boundary data was not found");

                ArrayList boundaryResponse = context.read(propertyIdToJsonPath.get(estimate.getPropertyId()));
                Locality boundary = mapper.convertValue(boundaryResponse.get(0), Locality.class);
                if (boundary.getName() == null)
                    throw new CustomException("INVALID BOUNDARY DATA", "The boundary data for the code "
                            + estimate.getAddress().getLocality().getCode() + " is not available");
                estimate.getAddress().setLocality(boundary);

            }*/
        }


        // $..boundary[?(@.code=="JLC476")].area

    }

    /**
     * Prepares map of propertyId to jsonpath which contains the code of the
     * property
     *
     * @param property PropertyRequest for create
     * @return Map of propertyId to jsonPath with properties locality code
     */
  /*  private Map<String, String> getJsonpath(Property property) {

        Map<String, String> propertyIdToJsonPath = new LinkedHashMap<>();
        String jsonpath = "$..boundary[?(@.code==\"{}\")]";
        propertyIdToJsonPath.put(property.getPropertyId(),
                jsonpath.replace("{}", property.getAddress().getLocality().getCode()));
        return propertyIdToJsonPath;
    }*/
}
