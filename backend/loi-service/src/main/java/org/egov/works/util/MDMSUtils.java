package org.egov.works.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
import org.egov.works.config.LOIConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class MDMSUtils {

    private LOIConfiguration config;

    private ServiceRequestRepository serviceRequestRepository;

    public static final String PLACEHOLDER_CODE = "{code}";
    public final String filterWorksModuleCode = "$.[?(@.active==true && @.code=='{code}')]";

    public static final String filterCode = "$.*.code";
    public final String filterSubSchemeModuleCode = "$.[?(@.active==true)].subSchemes.[?(@.active==true && @.code=='{code}')]]";
    public final String filterSubTypeModuleCode = "$.[?(@.active==true)].subTypes.[?(@.active==true && @.code=='{code}')]]";


    @Autowired
    public MDMSUtils(LOIConfiguration config, ServiceRequestRepository serviceRequestRepository) {
        this.config = config;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    /**
     * Calls MDMS service to fetch works master data
     *
     * @param request
     * @param tenantId
     * @return
     */
    public Object mDMSCall(LetterOfIndentRequest request, String tenantId) {
        RequestInfo requestInfo = request.getRequestInfo();
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequest(requestInfo, tenantId, request);
        Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
        return result;
    }




    /**
     * Returns mdms search criteria based on the tenantId
     *
     * @param requestInfo
     * @param tenantId
     * @param request
     * @return
     */
    public MdmsCriteriaReq getMDMSRequest(RequestInfo requestInfo, String tenantId, LetterOfIndentRequest request) {

        ModuleDetail estimateWorksModuleDetail = getWorksModuleRequestData(request);
        ModuleDetail estimateFinanceModuleDetail = getFinanceModuleRequestData(request);
        ModuleDetail estimateTenantModuleDetail = getTenantModuleRequestData(request);

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(estimateWorksModuleDetail);
        moduleDetails.add(estimateFinanceModuleDetail);
        moduleDetails.add(estimateTenantModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();
        return mdmsCriteriaReq;
    }

    private ModuleDetail getTenantModuleRequestData(LetterOfIndentRequest request) {



        ModuleDetail estimateTenantModuleDetail = null;

        return estimateTenantModuleDetail;
    }

    private ModuleDetail getFinanceModuleRequestData(LetterOfIndentRequest request) {


        ModuleDetail estimateFinanceModuleDetail = null;

        return estimateFinanceModuleDetail;
    }

    private ModuleDetail getWorksModuleRequestData(LetterOfIndentRequest request) {


        ModuleDetail estimateWorksModuleDetail = null;

        return estimateWorksModuleDetail;
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
