package org.egov.service;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdResponse;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.repository.EstimateRepository;
import org.egov.repository.IdGenRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.EstimateServiceUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.util.EstimateServiceConstant.ACTION_REJECT;
import static org.egov.util.EstimateServiceConstant.ALLOW_EDITING_ROLES;

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


    /**
     * Enrich create estimate with - audit details, estimate number from idGen service,
     * id for the estimate, estimateDetail, address, amountDetail
     * @param request
     */
    public void enrichCreateEstimate(EstimateRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        Estimate estimate = request.getEstimate();
        List<EstimateDetail> estimateDetails = estimate.getEstimateDetails();
        Address address = estimate.getAddress();

        AuditDetails auditDetails = estimateServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), estimate, true);
        estimate.setAuditDetails(auditDetails);
        estimate.setId(UUID.randomUUID().toString());
        Date currentDT = new Date();
        BigDecimal proposalDate = new BigDecimal(currentDT.getTime());
        estimate.setProposalDate(proposalDate);

        String rootTenantId = estimate.getTenantId().split("\\.")[0];

        List<String> estimateNumbers = getIdList(requestInfo, rootTenantId
                , config.getIdgenEstimateNumberName(), config.getIdgenEstimateNumberFormat(), 1);

        if (estimateNumbers != null && !estimateNumbers.isEmpty()) {
            String estimateNumber = estimateNumbers.get(0);
            estimate.setEstimateNumber(estimateNumber);
        }


        address.setId(UUID.randomUUID().toString());
        //enrich estimate detail & amount detail - id(s)
        for(EstimateDetail estimateDetail : estimateDetails){
            estimateDetail.setId(UUID.randomUUID().toString());
            List<AmountDetail> amountDetails = estimateDetail.getAmountDetail();
            for(AmountDetail amountDetail : amountDetails){
                amountDetail.setId(UUID.randomUUID().toString());
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
     * enrich the search estimate criteria with default values in case the
     * values are not passed as part of request
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

    /**
     * enrich the update estimate request with - audit details,
     * workflow's action, & based on user's roles
     * @param request
     */
    public void enrichUpdateEstimate(EstimateRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        Estimate estimate = request.getEstimate();

        List<String> ids = new ArrayList<>();
        ids.add(estimate.getId());
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

        enrichUpdateEstimateWorkFlowForActionReject(request);
    }

    /**
     * If the workflow action is 'REJECT' then assignee will be updated
     * with user id that is having role as 'EST_CREATOR'  (i.e the 'auditDetails.createdBy')
     *
     * @param request
     */
    private void enrichUpdateEstimateWorkFlowForActionReject(EstimateRequest request) {
        Workflow workflow = request.getWorkflow();
        AuditDetails auditDetails = request.getEstimate().getAuditDetails();
        List<String> updatedAssignees = new ArrayList<>();
        updatedAssignees.add(auditDetails.getCreatedBy());
        if (workflow.getAction().equals(ACTION_REJECT)) {
            workflow.setAssignees(updatedAssignees);
        }
    }

    /**
     * Check the user has edit('or' update) roles
     * @param requestInfo
     * @return
     */
    private boolean enrichEstimateBasedOnRole(RequestInfo requestInfo) {
        User userInfo = requestInfo.getUserInfo();
        boolean rolePresent = false;
        if (userInfo.getRoles() != null && !userInfo.getRoles().isEmpty()) {
            List<org.egov.common.contract.request.Role> roles = userInfo.getRoles();
            List<String> updateRoles = Arrays.asList(ALLOW_EDITING_ROLES.split(","));

            rolePresent = roles.stream().anyMatch(role -> {
                return updateRoles.contains(role.getCode());
            });

        }
        return rolePresent;
    }
}
