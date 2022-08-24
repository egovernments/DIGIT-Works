package org.egov.works.util;

import org.egov.common.contract.request.RequestInfo;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
import org.egov.works.config.LOIConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.LetterOfIndent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.egov.works.util.LOIConstants.MDMS_MASTER;
import static org.egov.works.util.LOIConstants.MDMS_MODULE_NAME;

@Component
public class MDMSUtils {


    private LOIConfiguration config;

    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    public MDMSUtils(LOIConfiguration config, ServiceRequestRepository serviceRequestRepository) {
        this.config = config;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    /**
     * Calls MDMS service to fetch loi master data
     *
     * @param request
     * @return
     */
    public Object mDMSCall(LetterOfIndent request) {
        RequestInfo requestInfo = new RequestInfo();
        String tenantId = request.getTenantId();
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequest(requestInfo, tenantId);
        Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
        return result;
    }


    /**
     * Returns mdms search criteria based on the tenantId
     *
     * @param requestInfo
     * @param tenantId
     * @return
     */
    public MdmsCriteriaReq getMDMSRequest(RequestInfo requestInfo, String tenantId) {
        List<ModuleDetail> pgrModuleRequest = getLOIModuleRequest();

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.addAll(pgrModuleRequest);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();
        return mdmsCriteriaReq;
    }


    /**
     * Creates request to search serviceDef from MDMS
     *
     * @return request to search UOM from MDMS
     */
    private List<ModuleDetail> getLOIModuleRequest() {

        // master details for TL module
        List<MasterDetail> loiMasterDetails = new ArrayList<>();

        // filter to only get code field from master data
        final String filterCode = "$.*.code";

        loiMasterDetails.add(MasterDetail.builder().name(MDMS_MASTER).filter(filterCode).build());

        ModuleDetail loiModuleDtls = ModuleDetail.builder().masterDetails(loiMasterDetails)
                .moduleName(MDMS_MODULE_NAME).build();


        return Collections.singletonList(loiModuleDtls);

    }


    /**
     * Returns the url for mdms search endpoint
     *
     * @return url for mdms search endpoint
     */
    public StringBuilder getMdmsSearchUrl() {
        return new StringBuilder().append(config.getMdmsHost()).append(config.getMdmsEndPoint());
    }
}
