package org.egov.works.service;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdResponse;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.EstimateServiceConfiguration;
import org.egov.works.repository.IdGenRepository;
import org.egov.works.util.EstimateServiceUtil;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnrichmentService {

    @Autowired
    private EstimateServiceUtil estimateServiceUtil;

    @Autowired
    private IdGenRepository idGenRepository;

    @Autowired
    private EstimateServiceConfiguration config;


    public void enrichCreateEstimate(EstimateRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        Estimate estimate = request.getEstimate();
        List<EstimateDetail> estimateDetails = estimate.getEstimateDetails();

        AuditDetails auditDetails = estimateServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), estimate, true);
        estimate.setAuditDetails(auditDetails);
        estimate.setId(UUID.randomUUID());

        String tenantId = estimate.getTenantId().split("\\.")[0];

        List<String> estimateNumbers = getIdList(requestInfo, tenantId
                , config.getIdgenEstimateNumberName(), config.getIdgenEstimateNumberFormat(), 1);

        if (estimateNumbers != null && !estimateNumbers.isEmpty()) {
            estimate.setEstimateNumber(estimateNumbers.get(0));
        }

        if (estimateDetails != null && !estimateDetails.isEmpty()) {
            List<String> estimateDetailNumbers = getIdList(requestInfo, tenantId
                    , config.getIdgenSubEstimateNumberName(), config.getIdgenSubEstimateNumberFormat(), estimateDetails.size());
            for (int i = 0; i < estimateDetails.size(); i++) {
                estimateDetails.get(0).setId(UUID.randomUUID());
                estimateDetails.get(0).setEstimateDetailNumber(estimateDetailNumbers.get(0));
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

    /**
     * @param searchRequest
     */
    public void enrichSearchEstimate(EstimateSearchRequest searchRequest) {
        EstimateSearchCriteria searchCriteria = searchRequest.getSearchCriteria();

        if (searchCriteria.getLimit() == null)
            searchCriteria.setLimit(config.getDefaultLimit());

        if (searchCriteria.getOffset() == null)
            searchCriteria.setOffset(config.getDefaultOffset());

        if (searchCriteria.getLimit() != null && searchCriteria.getLimit() > config.getMaxLimit())
            searchCriteria.setLimit(config.getMaxLimit());
    }
}
