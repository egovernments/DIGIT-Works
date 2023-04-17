package org.egov.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.MusterRoll;
import org.egov.web.models.MusterRollRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.egov.util.MusterRollServiceConstants.*;

@Slf4j
@Component
public class MdmsUtil {

    @Autowired
    private MusterRollServiceConfiguration config;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public final String filterWorksModuleCode = "$.[?(@.code=='{code}')]";
    public final String PLACEHOLDER_CODE = "{code}";

    /**
     * Calls MDMS service to fetch works master data
     *
     * @param request
     * @param tenantId
     * @return
     */
    public Object mDMSCall(MusterRollRequest request, String tenantId) {
        RequestInfo requestInfo = request.getRequestInfo();
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequest(requestInfo, tenantId, request);
        Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
        return result;
    }

    /**
     * Calls MDMS service to fetch muster roll data
     *
     * @param request
     * @param tenantId
     * @return
     */
    public Object mDMSCallMuster(MusterRollRequest request, String tenantId) {
        RequestInfo requestInfo = request.getRequestInfo();
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequestMuster(requestInfo, tenantId, request);
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
    public MdmsCriteriaReq getMDMSRequest(RequestInfo requestInfo, String tenantId, MusterRollRequest request) {

        ModuleDetail tenantModuleDetail = getTenantModuleRequestData(request);

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(tenantModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();
        return mdmsCriteriaReq;
    }

    /**
     * Returns mdms search criteria based on the tenantId
     *
     * @param requestInfo
     * @param tenantId
     * @param request
     * @return
     */
    public MdmsCriteriaReq getMDMSRequestMuster(RequestInfo requestInfo, String tenantId, MusterRollRequest request) {

        ModuleDetail musterRollModuleDetail = getMusterRollModuleRequestData();

        List<ModuleDetail> moduleDetails = new LinkedList<>();
        moduleDetails.add(musterRollModuleDetail);

        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId)
                .build();

        MdmsCriteriaReq mdmsCriteriaReq = MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria)
                .requestInfo(requestInfo).build();
        return mdmsCriteriaReq;
    }

    private ModuleDetail getTenantModuleRequestData(MusterRollRequest request) {
        MusterRoll musterRoll = request.getMusterRoll();
        List<MasterDetail> musterRollTenantMasterDetails = new ArrayList<>();

        MasterDetail tenantMasterDetails = MasterDetail.builder().name(MASTER_TENANTS)
                .filter(filterCode).build();

        musterRollTenantMasterDetails.add(tenantMasterDetails);

        ModuleDetail musterRollTenantModuleDetail = ModuleDetail.builder().masterDetails(musterRollTenantMasterDetails)
                .moduleName(MDMS_TENANT_MODULE_NAME).build();

        return musterRollTenantModuleDetail;
    }

    private ModuleDetail getMusterRollModuleRequestData() {

        List<MasterDetail> musterRollMasterDetails = new ArrayList<>();

        MasterDetail musterAttendanceMasterDetails = MasterDetail.builder().name(MASTER_MUSTER_ROLL).build();
        musterRollMasterDetails.add(musterAttendanceMasterDetails);

        MasterDetail musterWageSeekerSkillsMasterDetails = MasterDetail.builder().name(MASTER_WAGER_SEEKER_SKILLS).build();
        musterRollMasterDetails.add(musterWageSeekerSkillsMasterDetails);

        ModuleDetail musterRollModuleDetail = ModuleDetail.builder().masterDetails(musterRollMasterDetails)
                .moduleName(MDMS_COMMON_MASTERS_MODULE_NAME).build();

        return musterRollModuleDetail;
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