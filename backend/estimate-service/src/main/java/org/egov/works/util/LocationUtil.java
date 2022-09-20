package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
import org.egov.works.config.EstimateServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.egov.works.util.EstimateServiceConstant.*;

@Component
@Slf4j
public class LocationUtil {

    @Autowired
    private EstimateServiceConfiguration serviceConfiguration;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ObjectMapper mapper;

    public static final String PLACEHOLDER_CODE = "{code}";

    public static final String filterHierarchyType = "$.[?(@.hierarchyType.code=='{code}')].boundary";
    public static final String filterChildrenCode = "[?(@.code=='{code}')].children";
    public static final String CODE = "code";

    /**
     * @param tenantId
     * @param requestInfo
     * @param hierarchyTypeCode
     * @param boundaryLabel
     * @param boundaryCode
     */

    public void getLocationFromLocationService(String tenantId, RequestInfo requestInfo, String hierarchyTypeCode, String boundaryLabel, String boundaryCode) {

        if (StringUtils.isNotBlank(tenantId)) {

            StringBuilder uri = new StringBuilder(serviceConfiguration.getLocationHost());

            uri.append(serviceConfiguration.getLocationContextPath())
                    .append(serviceConfiguration.getLocationEndpoint());
            uri.append("?").append("tenantId=").append(tenantId);

            if (StringUtils.isNotBlank(hierarchyTypeCode)) {
                uri.append("&").append("hierarchyTypeCode=").append(hierarchyTypeCode);
            }

            if (StringUtils.isNotBlank(boundaryLabel)
                    && StringUtils.isNotBlank(boundaryCode)) {
                uri.append("&").append("boundaryType=").append(boundaryLabel)
                        .append("&").append("codes=").append(boundaryCode);
            }

            Object locationRes = serviceRequestRepository.fetchResult(uri, RequestInfoWrapper.builder().requestInfo(requestInfo).build());

            //TODO
            if (locationRes != null) {

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

            // $..boundary[?(@.code=="JLC476")].area
        }


    }

    /**
     * Prepares map of propertyId to jsonpath which contains the code of the
     * property
     *
     * @param estimate PropertyRequest for create
     * @param tenantId
     * @return Map of propertyId to jsonPath with properties locality code
     */
  /*  private Map<String, String> getJsonpath(Estimate estimate) {

        Map<String, String> propertyIdToJsonPath = new LinkedHashMap<>();
        String jsonpath = "$..boundary[?(@.code==\"{}\")]";
        propertyIdToJsonPath.put(estimate.getPropertyId(),
                jsonpath.replace("{}", property.getAddress().getLocality().getCode()));
        return propertyIdToJsonPath;
    }*/
    public Object getLocationFromMDMS(String location, RequestInfo requestInfo, Map<String, String> errorMap) {
        String[] locArr = location.split(SEMICOLON);
        if (locArr.length < 2) {
            errorMap.put("INVALID_LOCATION", "Location is invalid");
            return null;
        }
        MdmsCriteriaReq mdmsCriteriaReq = getLocMDMSRequest(locArr, requestInfo);
        Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
        return result;

    }

    private MdmsCriteriaReq getLocMDMSRequest(String[] locArr, RequestInfo requestInfo) {
        String tenantId = locArr[0];
        String hierarchyType = locArr[1];

        StringBuilder finalLocFilter = new StringBuilder();
        finalLocFilter.append(filterHierarchyType.replace(PLACEHOLDER_CODE, hierarchyType));

        for (int i = 2; i < locArr.length; i++) {
            finalLocFilter.append(DOT);
            finalLocFilter.append(filterChildrenCode.replace(PLACEHOLDER_CODE, locArr[i]));
        }
        finalLocFilter.append(DOT);
        finalLocFilter.append(CODE);


        List<MasterDetail> locationMasterDetails = new ArrayList<>();

        MasterDetail locationMasterDetail = MasterDetail.builder().name(MASTER_BOUNDARY_LOCATION)
                .filter(finalLocFilter.toString()).build();

        locationMasterDetails.add(locationMasterDetail);


        ModuleDetail locationModuleDetail = ModuleDetail.builder().masterDetails(locationMasterDetails)
                .moduleName(MDMS_LOCATION_MODULE_NAME).build();

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(locationModuleDetail);


        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();
        return mdmsCriteriaReq;
    }

    /* Returns the url for mdms search endpoint
     * @return url for mdms search endpoint
     */
    public StringBuilder getMdmsSearchUrl() {
        return new StringBuilder().append(serviceConfiguration.getMdmsHost()).append(serviceConfiguration.getMdmsEndPoint());
    }
}
