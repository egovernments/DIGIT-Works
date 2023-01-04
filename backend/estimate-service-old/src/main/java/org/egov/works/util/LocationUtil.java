package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

    public static final String PLACEHOLDER_CODE = "{code}";
    public static final String filterHierarchyType = "$.[?(@.hierarchyType.code=='{code}')].boundary";
    public static final String filterChildrenCode = "[?(@.code=='{code}')].children";
    public static final String CODE = "code";
    @Autowired
    private EstimateServiceConfiguration serviceConfiguration;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private ObjectMapper mapper;


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

        StringBuilder filterBuilder = new StringBuilder();
        filterBuilder.append(filterHierarchyType.replace(PLACEHOLDER_CODE, hierarchyType));

        for (int i = 2; i < locArr.length; i++) {
            filterBuilder.append(DOT);
            filterBuilder.append(filterChildrenCode.replace(PLACEHOLDER_CODE, locArr[i]));
        }
        String finalFilter = filterBuilder.substring(0, filterBuilder.lastIndexOf(".children"));
        finalFilter = finalFilter.concat(DOT).concat(CODE);


        List<MasterDetail> locationMasterDetails = new ArrayList<>();

        MasterDetail locationMasterDetail = MasterDetail.builder().name(MASTER_BOUNDARY_LOCATION)
                .filter(finalFilter).build();

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
