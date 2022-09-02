package org.egov.works.service;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdResponse;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.repository.IdGenRepository;
import org.egov.works.util.EstimateServiceUtil;
import org.egov.works.web.models.Estimate;
import org.egov.works.web.models.EstimateDetail;
import org.egov.works.web.models.EstimateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnrichmentService {

    private EstimateServiceUtil estimateServiceUtil;
    private IdGenRepository idGenRepository;

    @Autowired
    public EnrichmentService(EstimateServiceUtil estimateServiceUtil,IdGenRepository idGenRepository) {
        this.estimateServiceUtil = estimateServiceUtil;
        this.idGenRepository = idGenRepository;
    }

    public void enrichCreateEstimate(EstimateRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        Estimate estimate = request.getEstimate();
        List<EstimateDetail> estimateDetails = estimate.getEstimateDetails();

        AuditDetails auditDetails = estimateServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), estimate, true);
        estimate.setAuditDetails(auditDetails);
        estimate.setId(UUID.randomUUID());

        if (estimateDetails != null && !estimateDetails.isEmpty()) {
            for (EstimateDetail estimateDetail : estimateDetails) {
                estimateDetail.setId(UUID.randomUUID());
            }
        }
    }

    /**
     * Returns a list of numbers generated from idgen
     *
     * @param requestInfo RequestInfo from the request
     * @param tenantId    tenantId of the city
     * @param idKey       code of the field defined in application properties for which ids are generated for
     * @param idformat    format in which ids are to be generated
     * @param count       Number of ids to be generated
     * @return List of ids generated using idGen service
     */
    private List<String> getIdList(RequestInfo requestInfo, String tenantId, String idKey,
                                   String idformat, int count) {
        List<IdResponse> idResponses = idGenRepository.getId(requestInfo, tenantId, idKey, idformat, count).getIdResponses();

        if (CollectionUtils.isEmpty(idResponses))
            throw new CustomException("IDGEN ERROR", "No ids returned from idgen Service");

        return idResponses.stream()
                .map(IdResponse::getId).collect(Collectors.toList());
    }

}
