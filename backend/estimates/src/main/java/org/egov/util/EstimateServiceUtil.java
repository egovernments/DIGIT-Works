package org.egov.util;


import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
