package org.egov.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.idgen.IdGenerationResponse;
import org.egov.common.contract.idgen.IdResponse;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.services.common.models.common.Address;
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

    private final EstimateServiceUtil estimateServiceUtil;

    private final IdGenRepository idGenRepository;

    private final EstimateServiceConfiguration config;

    private final EstimateRepository estimateRepository;

    @Autowired
    public EnrichmentService(EstimateServiceUtil estimateServiceUtil, IdGenRepository idGenRepository, EstimateServiceConfiguration config, EstimateRepository estimateRepository) {
        this.estimateServiceUtil = estimateServiceUtil;
        this.idGenRepository = idGenRepository;
        this.config = config;
        this.estimateRepository = estimateRepository;
    }


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
        Date currentDT = new Date();
        BigDecimal proposalDate = new BigDecimal(currentDT.getTime());
        estimate.setProposalDate(proposalDate);
        if(Boolean.TRUE.equals(estimateServiceUtil.isRevisionEstimate(request))){
            EstimateSearchCriteria estimateSearchCriteria = EstimateSearchCriteria.builder().tenantId(estimate.getTenantId()).estimateNumber(estimate.getEstimateNumber()).status(ESTIMATE_ACTIVE_STATUS).sortOrder(EstimateSearchCriteria.SortOrder.DESC).sortBy(
                    EstimateSearchCriteria.SortBy.createdTime).build();
            List<Estimate> estimateList = estimateRepository.getEstimate(estimateSearchCriteria);
            estimateForRevision = estimateList.get(0);
            estimate.setOldUuid(estimateForRevision.getId());
            if(estimateForRevision.getVersionNumber() == null){
                estimate.setVersionNumber(BigDecimal.valueOf(1));
            }else {
                estimate.setVersionNumber(estimateForRevision.getVersionNumber().add(BigDecimal.valueOf(1)));
            }
            List<String> revisonEstimateNumber = getIdList(requestInfo, tenantId
                    , config.getIdgenRevisionEstimateNumberName(), config.getIdgenRevisionEstimateNumberFormat(), 1);
            if (revisonEstimateNumber != null && !revisonEstimateNumber.isEmpty()) {
                String revisionNumber = revisonEstimateNumber.get(0);
                estimate.setRevisionNumber(revisionNumber);
            }
        }else{
            List<String> estimateNumbers = getIdList(requestInfo, tenantId
                    , config.getIdgenEstimateNumberName(), config.getIdgenEstimateNumberFormat(), 1);

            if (estimateNumbers != null && !estimateNumbers.isEmpty()) {
                String estimateNumber = estimateNumbers.get(0);
                estimate.setEstimateNumber(estimateNumber);
                estimate.setVersionNumber(BigDecimal.valueOf(1));
            }
            estimate.setBusinessService(config.getEstimateWFBusinessService());
        }

        address.setId(UUID.randomUUID().toString());
        //enrich estimate detail & amount detail - id(s)
        enrichEstimateDetailsAndAmountDetails(estimateDetails);
    }
    private void enrichEstimateDetailsAndAmountDetails(List<EstimateDetail> estimateDetails){
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
    public void enrichEstimateOnSearch(EstimateSearchCriteria searchCriteria) {
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

    public void enrichNoOfUnit(List<EstimateDetail> estimateDetails){
        for (EstimateDetail estimateDetail : estimateDetails) {
            if (estimateDetail.getNoOfunit() == null) {
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
                if (allNull) {
                    continue;
                }
                double totalNew = total.doubleValue();
                estimateDetail.setNoOfunit(totalNew);
            }
        }
    }
}
