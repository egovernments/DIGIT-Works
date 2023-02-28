package org.egov.works.enrichment;

import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.util.*;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import org.egov.works.web.models.ContractCriteria;
import org.egov.works.web.models.Pagination;

import static org.egov.works.util.ContractServiceConstants.JSON_PATH_FOR_OVER_HEADS_VERIFICATION;

@Component
@Slf4j
public class ContractEnrichment {

    @Autowired
    private IdgenUtil idgenUtil;

    @Autowired
    private ContractServiceConfiguration config;

    @Autowired
    private ContractServiceUtil contractServiceUtil;

    @Autowired
    private EstimateServiceUtil estimateServiceUtil;

    @Autowired
    private MDMSUtils mdmsUtils;

    @Autowired
    private MDMSDataParser mdmsDataParser;


    public void enrichContractOnCreate(ContractRequest contractRequest){
        // Enrich LineItems
        enrichContractLineItems(contractRequest);
        // Enrich Contract Number
        enrichContractNumber(contractRequest);
        // Enrich UUID and AuditDetails
        enrichIdsAgreementDateAndAuditDetailsOnCreate(contractRequest);

    }

    public void enrichContractOnUpdate(ContractRequest contractRequest){
        // Enrich LineItems
        enrichContractLineItems(contractRequest);
        // Enrich UUID and AuditDetails
        enrichIdsAndAuditDetailsOnUpdate(contractRequest);
    }

    private void enrichIdsAndAuditDetailsOnUpdate(ContractRequest contractRequest) {
        Contract contract = contractRequest.getContract();
        AuditDetails providedAuditDetails = contractRequest.getContract().getAuditDetails();
        AuditDetails auditDetails = contractServiceUtil.getAuditDetails(contractRequest.getRequestInfo().getUserInfo().getUuid(), providedAuditDetails, false);
        contract.setAuditDetails(auditDetails);
        for(LineItems lineItem : contract.getLineItems()){
            if(lineItem.getId() == null) {
                lineItem.setId(String.valueOf(UUID.randomUUID()));
            }
            lineItem.setAuditDetails(auditDetails);
            for(AmountBreakup amountBreakup : lineItem.getAmountBreakups()){
                if(amountBreakup.getId() == null) {
                    amountBreakup.setId(String.valueOf(UUID.randomUUID()));
                }
            }
            List<Document> documents = contract.getDocuments();
            for (Document document : documents) {
                if(document.getId() == null) {
                    document.setId(String.valueOf(UUID.randomUUID()));
                }
            }
        }
    }

    private void enrichContractLineItems(ContractRequest contractRequest) {
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String tenantId = contract.getTenantId();
        List<LineItems> providedLineItems = contract.getLineItems();
        Set<String> providedEstimateIds = providedLineItems.stream().map(e -> e.getEstimateId()).collect(Collectors.toSet());

        List<Estimate> fetchedActiveEstimates = fetchActiveEstimates(requestInfo,tenantId,providedEstimateIds);
        List<Object> objects = fetchAllowedOverheardsFromMDMS(requestInfo,tenantId);

        Map<String, List<LineItems>> providedLineItemsListMap = providedLineItems.stream().collect(Collectors.groupingBy(LineItems::getEstimateId));
        Map<String, List<Estimate>> fetchedEstimateListMap = fetchedActiveEstimates.stream().collect(Collectors.groupingBy(Estimate::getId));

        List<LineItems> refinedLineItems = new ArrayList<>();
        for(String providedEstimateId : providedEstimateIds) {
            for (LineItems providedLineItem : providedLineItemsListMap.get(providedEstimateId)){
                if (providedLineItem.getEstimateLineItemId() == null) {
                    Estimate fetchedEstimate = fetchedEstimateListMap.get(providedEstimateId).get(0);
                    for (EstimateDetail fetchedEstimateDetail : fetchedEstimate.getEstimateDetails()) {

                        LineItems lineItem = LineItems.builder()
                                .estimateId(providedEstimateId)
                                .estimateLineItemId(fetchedEstimateDetail.getId())
                                .noOfunit(fetchedEstimateDetail.getNoOfunit())
                                .unitRate(fetchedEstimateDetail.getUnitRate())
                                .name(fetchedEstimateDetail.getName())
                                .category(fetchedEstimateDetail.getCategory())
                                .tenantId(fetchedEstimate.getTenantId())
                                .status(fetchedEstimate.getStatus())
                                .additionalDetails(fetchedEstimateDetail.getAdditionalDetails())
                                .build();

                        List<AmountDetail> amountDetails = fetchedEstimateDetail.getAmountDetail();

                        for (AmountDetail amountDetail : amountDetails) {
                            AmountBreakup amountBreakup = AmountBreakup.builder()
                                    .estimateAmountBreakupId(amountDetail.getId())
                                    .amount(amountDetail.getAmount())
                                    .additionalDetails(amountDetail.getAdditionalDetails())
                                    .status(Status.ACTIVE)
                                    .build();
                            lineItem.addAmountBreakupsItem(amountBreakup);
                        }

                        //refinedLineItems.add(lineItem);
                        addLineItem(refinedLineItems,lineItem,objects);
                    }
                } else {
                    addLineItem(refinedLineItems,providedLineItem,objects);
                    //refinedLineItems.add(providedLineItem);
                }
            }
        }

        contract.getLineItems().clear();
        contract.setLineItems(refinedLineItems);
        log.info("LintItem enrichment is done");
    }

    private List<Estimate> fetchActiveEstimates(RequestInfo requestInfo, String tenantId, Set<String> providedEstimateIds) {
        return estimateServiceUtil.fetchActiveEstimates(requestInfo,tenantId,providedEstimateIds);
    }

    private List<Object> fetchAllowedOverheardsFromMDMS(RequestInfo requestInfo, String tenantId) {
        Object mdmsForEnrichment = mdmsUtils.fetchMDMSForEnrichment(requestInfo, tenantId.split("\\.")[0]);
        return mdmsDataParser.parseMDMSData(mdmsForEnrichment, JSON_PATH_FOR_OVER_HEADS_VERIFICATION);
    }

    private void addLineItem(List<LineItems> refinedLineItems,LineItems lineItem,List<Object> objects){
        if(!"OVERHEAD".equalsIgnoreCase(lineItem.getCategory()) || ("OVERHEAD".equalsIgnoreCase(lineItem.getCategory()) && objects.contains(lineItem.getName()))){
            refinedLineItems.add(lineItem);
        }
    }

    private void enrichIdsAgreementDateAndAuditDetailsOnCreate(ContractRequest contractRequest) {
        Contract contract = contractRequest.getContract();
        contract.setId(String.valueOf(UUID.randomUUID()));
        BigDecimal agreementDate = contract.getAgreementDate();
        if(agreementDate == null){
            agreementDate = BigDecimal.valueOf(Instant.now().toEpochMilli());
            contract.setAgreementDate(agreementDate);
        }
        AuditDetails auditDetails = contractServiceUtil.getAuditDetails(contractRequest.getRequestInfo().getUserInfo().getUuid(), null, true);
        contract.setAuditDetails(auditDetails);
        for(LineItems lineItem : contract.getLineItems()){
            lineItem.setId(String.valueOf(UUID.randomUUID()));
            lineItem.setAuditDetails(auditDetails);
            for(AmountBreakup amountBreakup : lineItem.getAmountBreakups()){
                amountBreakup.setId(String.valueOf(UUID.randomUUID()));
            }
            List<Document> documents = contract.getDocuments();
            for (Document document : documents) {
                document.setId(String.valueOf(UUID.randomUUID()));
                document.setContractId(contract.getId());
            }
        }

        log.info("Contract id, agreementDate, audit details enrichment is done ["+contract.getId()+"]");
    }

    private void enrichContractNumber(ContractRequest contractRequest) {
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String rootTenantId = contract.getTenantId().split("\\.")[0];
        List<String> idList = idgenUtil.getIdList(requestInfo, rootTenantId, config.getIdgenContractNumberName(), "", 1);
        String generatedContractNumber = idList.get(0);
        contract.setContractNumber(generatedContractNumber);
        log.info("Contract Number enrichment is done ["+generatedContractNumber+"]");
    }
    public void enrichSearchContractRequest(ContractCriteria contractCriteria) {

        Pagination pagination= getPagination(contractCriteria);

        if (pagination.getLimit() == null)
            pagination.setLimit(config.getContractDefaultLimit());

        if (pagination.getOffSet() == null)
            pagination.setOffSet(config.getContractDefaultOffset());

        if (pagination.getLimit() != null && pagination.getLimit().compareTo(config.getContractMaxLimit())>0)
            pagination.setLimit(config.getContractMaxLimit());
    }

    private Pagination getPagination(ContractCriteria contractCriteria) {
        Pagination pagination = contractCriteria.getPagination();
        if(pagination == null){
            pagination = Pagination.builder().build();
            contractCriteria.setPagination(pagination);
        }
        return pagination;
    }
}
