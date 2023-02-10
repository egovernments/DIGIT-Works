package org.egov.works.enrichment;

import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.util.ContractServiceUtil;
import org.egov.works.util.EstimateServiceUtil;
import org.egov.works.util.IdgenUtil;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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

    public void enrichContractOnCreate(ContractRequest contractRequest){
        // Enrich LineItems
        enrichContractLineItems(contractRequest);
        // Enrich Contract Number
        enrichContractNumber(contractRequest);
        // Enrich UUID and AuditDetails
        enrichIdsAndAuditDetails(contractRequest);

    }

    private void enrichContractLineItems(ContractRequest contractRequest) {
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String tenantId = contract.getTenantId();
        List<LineItems> providedLineItems = contract.getLineItems();

        Set<String> providedEstimateIds = providedLineItems.stream().map(e -> e.getEstimateId()).collect(Collectors.toSet());
        List<Estimate> fetchedEstimates = estimateServiceUtil.fetchEstimates(requestInfo,tenantId,providedEstimateIds);

        Map<String, List<LineItems>> providedLineItemsListMap = providedLineItems.stream().collect(Collectors.groupingBy(LineItems::getEstimateId));
        Map<String, List<Estimate>> fetchedEstimateListMap = fetchedEstimates.stream().collect(Collectors.groupingBy(Estimate::getId));

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
                                .tenantId(providedLineItem.getTenantId())
                                .additionalDetails(fetchedEstimateDetail.getAdditionalDetails())
                                .build();

                        List<AmountDetail> amountDetails = fetchedEstimateDetail.getAmountDetail();

                        for (AmountDetail amountDetail : amountDetails) {
                            AmountBreakup amountBreakup = AmountBreakup.builder()
                                    .estimateAmountBreakupId(amountDetail.getId())
                                    .amount(amountDetail.getAmount())
                                    .additionalDetails(amountDetail.getAdditionalDetails())
                                    .build();
                            lineItem.addAmountBreakupsItem(amountBreakup);
                        }
                        refinedLineItems.add(lineItem);
                    }
                } else {
                    refinedLineItems.add(providedLineItem);
                }
            }
        }

        contract.getLineItems().clear();
        contract.setLineItems(refinedLineItems);
    }

    private void enrichIdsAndAuditDetails(ContractRequest contractRequest) {
        Contract contract = contractRequest.getContract();
        contract.setId(String.valueOf(UUID.randomUUID()));
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
            }
        }
    }

    private void enrichContractNumber(ContractRequest contractRequest) {
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String rootTenantId = contract.getTenantId().split("\\.")[0];
        List<String> idList = idgenUtil.getIdList(requestInfo, rootTenantId, config.getIdgenContractNumberName(), "", 1);
        contract.setContractNumber(idList.get(0));
    }
}
