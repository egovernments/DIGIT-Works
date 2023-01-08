package org.egov.works.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
import org.egov.works.config.ProjectConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.egov.works.util.ProjectConstants.*;

@Component
@Slf4j
public class LocationUtil {

    public static final String PLACEHOLDER_CODE = "{code}";
    public static final String filterHierarchyType = "$.[?(@.hierarchyType.code=='{code}')].boundary";
    public static final String filterBoundaryChildrenChildren = ".children.*.children.*.code";

    @Autowired
    ProjectConfiguration projectConfiguration;

    @Autowired
    ServiceRequestRepository serviceRequestRepository;

    public Object getLocationFromMDMS(List<String> locations, String tenantId, RequestInfo requestInfo, Map<String, String> errorMap) {
        MdmsCriteriaReq mdmsCriteriaReq = getLocMDMSRequest(locations, tenantId, requestInfo);
        Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
        return result;
    }

    private MdmsCriteriaReq getLocMDMSRequest(List<String> locations, String tenantId, RequestInfo requestInfo) {

        StringBuilder filterBuilder = new StringBuilder();
        filterBuilder.append(filterHierarchyType.replace(PLACEHOLDER_CODE, BOUNDARY_ADMIN_HIERARCHY_CODE));

        List<MasterDetail> locationMasterDetails = new ArrayList<>();

        MasterDetail locationMasterDetail = MasterDetail.builder().name(MASTER_BOUNDARY_LOCATION)
                .filter(filterBoundaryChildrenChildren).build();

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
        return new StringBuilder().append(projectConfiguration.getMdmsHost()).append(projectConfiguration.getMdmsEndPoint());
    }

}
