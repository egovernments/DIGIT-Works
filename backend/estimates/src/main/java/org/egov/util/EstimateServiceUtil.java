package org.egov.util;


import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateRequest;
import org.egov.web.models.EstimateSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class EstimateServiceUtil {

    private final EstimateServiceConfiguration config;
    @Autowired
    public EstimateServiceUtil(EstimateServiceConfiguration config) {
        this.config = config;
    }
    /**
     * Method to return auditDetails for create/update flows
     *
     * @param by
     * @param isCreate
     * @return AuditDetails
     */
    public AuditDetails getAuditDetails(String by, Estimate estimate, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        if (Boolean.TRUE.equals(isCreate))
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(estimate.getAuditDetails().getCreatedBy()).lastModifiedBy(by)
                    .createdTime(estimate.getAuditDetails().getCreatedTime()).lastModifiedTime(time).build();
    }
    public Boolean isRevisionEstimate(EstimateRequest request){
        log.info("EstimateServiceValidator::isRevisionEstimate");
        Estimate estimate = request.getEstimate();
        return estimate.getBusinessService() != null && estimate.getBusinessService().equalsIgnoreCase(config.getRevisionEstimateBusinessService());
    }

    public boolean isCacheSearchRequired(EstimateSearchCriteria searchCriteria) {
        return config.getIsCachingEnabled() && !CollectionUtils.isEmpty(searchCriteria.getIds()) &&
                StringUtils.isEmpty(searchCriteria.getEstimateNumber()) &&
                StringUtils.isEmpty(searchCriteria.getExecutingDepartment()) &&
                StringUtils.isEmpty(searchCriteria.getBusinessService()) &&
                StringUtils.isEmpty(searchCriteria.getProjectId()) &&
                StringUtils.isEmpty(searchCriteria.getReferenceNumber()) &&
                StringUtils.isEmpty(searchCriteria.getOldUuid()) &&
                StringUtils.isEmpty(searchCriteria.getRevisionNumber()) &&
                StringUtils.isEmpty(searchCriteria.getWfStatus());
    }
}
