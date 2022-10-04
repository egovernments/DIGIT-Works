package org.egov.works.service;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdResponse;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.EstimateServiceConfiguration;
import org.egov.works.repository.EstimateRepository;
import org.egov.works.repository.IdGenRepository;
import org.egov.works.util.EstimateServiceUtil;
import org.egov.works.web.models.Estimate;
import org.egov.works.web.models.EstimateDetail;
import org.egov.works.web.models.EstimateRequest;
import org.egov.works.web.models.EstimateSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.util.EstimateServiceConstant.ALLOW_EDITING_ROLES;

@Service
public class EnrichmentService {

    @Autowired
    private EstimateServiceUtil estimateServiceUtil;

    @Autowired
    private IdGenRepository idGenRepository;

    @Autowired
    private EstimateServiceConfiguration config;

    @Autowired
    private EstimateRepository estimateRepository;

    public void enrichCreateEstimate(EstimateRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        Estimate estimate = request.getEstimate();
        List<EstimateDetail> estimateDetails = estimate.getEstimateDetails();

        AuditDetails auditDetails = estimateServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), estimate, true);
        estimate.setAuditDetails(auditDetails);
        estimate.setId(UUID.randomUUID());
        Date currentDT = new Date();
        BigDecimal proposalDate = new BigDecimal(currentDT.getTime());
        estimate.setProposalDate(proposalDate);

        String rootTenantId = estimate.getTenantId().split("\\.")[0];

        List<String> estimateNumbers = getIdList(requestInfo, rootTenantId
                , config.getIdgenEstimateNumberName(), config.getIdgenEstimateNumberFormat(), 1);

        String estimateNumSeq = "";
        if (estimateNumbers != null && !estimateNumbers.isEmpty()) {
            String estimateNumber = estimateNumbers.get(0);
            estimate.setEstimateNumber(estimateNumber);
            String[] estimateNumberArr = estimateNumber.split("/");
            estimateNumSeq = estimateNumberArr[estimateNumberArr.length - 1];
        }

        if (estimateDetails != null && !estimateDetails.isEmpty() && StringUtils.isNotBlank(estimateNumSeq)) {
            List<String> estimateDetailNumbers = getIdList(requestInfo, rootTenantId
                    , config.getIdgenSubEstimateNumberName(), config.getIdgenSubEstimateNumberFormat(), estimateDetails.size());
            for (int i = 0; i < estimateDetails.size(); i++) {
                estimateDetails.get(i).setId(UUID.randomUUID());
                String estimateDetailNum = estimateDetailNumbers.get(i);
                estimateDetailNum = estimateDetailNum.replace("ESTIMATE_NUM", estimateNumSeq);
                estimateDetails.get(i).setEstimateDetailNumber(estimateDetailNum);
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
     * @param requestInfo
     * @param searchCriteria
     */
    public void enrichSearchEstimate(RequestInfo requestInfo, EstimateSearchCriteria searchCriteria) {
        if (searchCriteria.getLimit() == null)
            searchCriteria.setLimit(config.getDefaultLimit());

        if (searchCriteria.getOffset() == null)
            searchCriteria.setOffset(config.getDefaultOffset());

        if (searchCriteria.getLimit() != null && searchCriteria.getLimit() > config.getMaxLimit())
            searchCriteria.setLimit(config.getMaxLimit());
    }

    public void enrichUpdateEstimate(EstimateRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        Estimate estimate = request.getEstimate();

        List<String> ids = new ArrayList<>();
        ids.add(estimate.getId().toString());
        EstimateSearchCriteria searchCriteria = EstimateSearchCriteria.builder().ids(ids).tenantId(estimate.getTenantId()).build();

        //Existing estimate
        List<Estimate> estimateList = estimateRepository.getEstimate(searchCriteria);

        if (enrichEstimateBasedOnRole(requestInfo)) {
            //set the audit details from DB
            estimate.setAuditDetails(estimateList.get(0).getAuditDetails());
        } /*Roles apart from UPDATE_ROLES, will not be able to edit/modify the existing
            record apart from estimate status field */ else {
            estimate = estimateList.get(0);
            request.setEstimate(estimate);
        }

        AuditDetails auditDetails = estimateServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), estimate, false);

        estimate.setAuditDetails(auditDetails);
    }


    private boolean enrichEstimateBasedOnRole(RequestInfo requestInfo) {
        User userInfo = requestInfo.getUserInfo();
        boolean rolePresent = false;
        if (userInfo.getRoles() == null || userInfo.getRoles().isEmpty()) {
            List<org.egov.common.contract.request.Role> roles = userInfo.getRoles();
            List<String> updateRoles = Arrays.asList(ALLOW_EDITING_ROLES.split(","));

            rolePresent = roles.stream().anyMatch(role -> {
                return updateRoles.contains(role.getCode());
            });

        }
        return rolePresent;
    }
}
