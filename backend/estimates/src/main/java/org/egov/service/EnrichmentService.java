package org.egov.service;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdGenerationResponse;
import digit.models.coremodels.IdResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.util.EstimateServiceConstant.*;

@Service
@Slf4j
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
     *
     * @param request
     */
    public void enrichEstimateOnCreate(EstimateRequest request) {
        log.info("EnrichmentService::enrichEstimateOnCreate");
        RequestInfo requestInfo = request.getRequestInfo();
        Estimate estimate = request.getEstimate();
        String tenantId = estimate.getTenantId();
        Estimate estimateForRevision = null;
        List<EstimateDetail> estimateDetails = estimate.getEstimateDetails();
        Address address = estimate.getAddress();

        enrichNoOfUnit(estimateDetails);

        AuditDetails auditDetails = estimateServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), estimate, true);
        estimate.setAuditDetails(auditDetails);
        estimate.setId(UUID.randomUUID().toString());
        //TODO -check with FE ?
        Date currentDT = new Date();
        BigDecimal proposalDate = new BigDecimal(currentDT.getTime());
        estimate.setProposalDate(proposalDate);
        if(estimate.getBusinessService().equals(config.getRevisionEstimateBusinessService()) && config.getRevisionEstimateActiveStatus()){
            EstimateSearchCriteria estimateSearchCriteria = EstimateSearchCriteria.builder().tenantId(estimate.getTenantId()).estimateNumber(estimate.getEstimateNumber()).sortOrder(EstimateSearchCriteria.SortOrder.DESC).sortBy(
                    EstimateSearchCriteria.SortBy.createdTime).build();
            List<Estimate> estimateList = estimateRepository.getEstimate(estimateSearchCriteria);
            estimateForRevision = estimateList.get(0);
            for(Estimate estimate1: estimateList){
                if(estimate1.getWfStatus().equals(ESTIMATE_APPROVED_STATUS)){
                    estimate.setOldUuid(estimate1.getId());
                    break;
                }
            }
            estimate.setVersionNumber(estimateForRevision.getVersionNumber().add(BigDecimal.valueOf(1)));
            estimate.setRevisionNumber(estimateForRevision.getEstimateNumber() + "/RE-" + (estimate.getVersionNumber().subtract(BigDecimal.valueOf(1))));
        }else{
            List<String> estimateNumbers = getIdList(requestInfo, tenantId
                    , config.getIdgenEstimateNumberName(), config.getIdgenEstimateNumberFormat(), 1);

            if (estimateNumbers != null && !estimateNumbers.isEmpty()) {
                String estimateNumber = estimateNumbers.get(0);
                estimate.setEstimateNumber(estimateNumber);
                estimate.setVersionNumber(BigDecimal.valueOf(1));
            }
        }

        address.setId(UUID.randomUUID().toString());
        //enrich estimate detail & amount detail - id(s)
        for (EstimateDetail estimateDetail : estimateDetails) {
            estimateDetail.setId(UUID.randomUUID().toString());
            List<AmountDetail> amountDetails = estimateDetail.getAmountDetail();
            for (AmountDetail amountDetail : amountDetails) {
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
        log.info("EnrichmentService::getIdList");
        List<IdResponse> idResponses = new ArrayList<>();
        IdGenerationResponse idGenerationResponse = idGenRepository.getId(requestInfo, tenantId, idKey, idformat, count);

        if (idGenerationResponse != null && CollectionUtils.isEmpty(idGenerationResponse.getIdResponses())) {
            throw new CustomException("IDGEN ERROR", "No ids returned from idgen Service");
        }

        if (idGenerationResponse != null && !CollectionUtils.isEmpty(idGenerationResponse.getIdResponses())) {
            idResponses = idGenerationResponse.getIdResponses();
        }

        return idResponses.stream()
                .map(IdResponse::getId).collect(Collectors.toList());
    }

    /**
     * enrich the search estimate criteria with default values in case the
     * values are not passed as part of request
     *
     * @param requestInfo
     * @param searchCriteria
     */
    public void enrichEstimateOnSearch(RequestInfo requestInfo, EstimateSearchCriteria searchCriteria) {
        log.info("EnrichmentService::enrichEstimateOnSearch");
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
     *
     * @param request
     */
    public void enrichEstimateOnUpdate(EstimateRequest request) {
        log.info("EnrichmentService::enrichEstimateOnUpdate");
        RequestInfo requestInfo = request.getRequestInfo();
        Estimate estimate = request.getEstimate();

        List<String> ids = new ArrayList<>();
        ids.add(estimate.getId());
        EstimateSearchCriteria searchCriteria = EstimateSearchCriteria.builder().ids(ids).tenantId(estimate.getTenantId()).build();

        //Existing estimate
        List<Estimate> estimateList = estimateRepository.getEstimate(searchCriteria);

        estimate.setAuditDetails(estimateList.get(0).getAuditDetails());

        AuditDetails auditDetails = estimateServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), estimate, false);

        estimate.setAuditDetails(auditDetails);

        //upsert line item and amount detail
        List<EstimateDetail> lineItemsFromReq = estimate.getEstimateDetails();
        enrichNoOfUnit(lineItemsFromReq);
        //check ids are there in the request or not, if not then its a new record that has to be inserted
        for (EstimateDetail lineItem : lineItemsFromReq) {
            if (StringUtils.isBlank(lineItem.getId())) {
                lineItem.setId(UUID.randomUUID().toString());
            }
            List<AmountDetail> amountDetails = lineItem.getAmountDetail();
            for (AmountDetail amountDetail : amountDetails) {
                if (StringUtils.isBlank(amountDetail.getId())) {
                    amountDetail.setId(UUID.randomUUID().toString());
                }
            }
        }

    }

    /**
     * If the workflow action is 'REJECT' then assignee will be updated
     * with user id that is having role as 'EST_CREATOR'  (i.e the 'auditDetails.createdBy')
     *
     * @param request
     */
    private void enrichUpdateEstimateWorkFlowForActionReject(EstimateRequest request) {
        log.info("EnrichmentService::enrichUpdateEstimateWorkFlowForActionReject");
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
     *
     * @param requestInfo
     * @return
     */
    private boolean enrichEstimateBasedOnRole(RequestInfo requestInfo) {
        log.info("EnrichmentService::enrichEstimateBasedOnRole");
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

    public void enrichNoOfUnit(List<EstimateDetail> estimateDetails){
        for(int i =0;i<estimateDetails.size();i++){
            EstimateDetail estimateDetail = estimateDetails.get(i);
            if(estimateDetail.getNoOfunit()==null){
                BigDecimal total =new BigDecimal(1);
                boolean allNull =true;
                if(estimateDetail.getLength()!=null){
                    total =total.multiply(estimateDetail.getLength());
                    allNull=false;
                }
                if(estimateDetail.getWidth()!=null){
                    total =total.multiply(estimateDetail.getWidth());
                    allNull=false;
                }
                if(estimateDetail.getHeight()!=null){
                    total =total.multiply(estimateDetail.getHeight());
                    allNull=false;
                }
                if(estimateDetail.getQuantity()!=null){
                    total =total.multiply(estimateDetail.getQuantity());
                    allNull=false;
                }
                if(allNull){
                    continue;
                }
                double totalNew = total.doubleValue();
                estimateDetail.setNoOfunit(totalNew);
            }
        }
    }
}
