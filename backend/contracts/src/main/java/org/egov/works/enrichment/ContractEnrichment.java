package org.egov.works.enrichment;


import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.util.*;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.egov.works.web.models.ContractCriteria;
import org.egov.works.web.models.Pagination;

import static org.egov.works.util.ContractServiceConstants.JSON_PATH_FOR_CONTRACT_TYPE_VERIFICATION;
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
    private AttendanceUtils attendanceUtils;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ObjectMapper mapper;

    public void enrichContractOnCreate(ContractRequest contractRequest){
        Object mdmsForEnrichment = fetchMDMSDataForEnrichment(contractRequest);
        // Enrich LineItems
        enrichContractLineItems(contractRequest,mdmsForEnrichment);
        // Enrich Contract Number
        enrichContractNumber(contractRequest);
        // Enrich UUID and AuditDetails
        enrichIdsAgreementDateAndAuditDetailsOnCreate(contractRequest);

    }

    public void enrichContractOnUpdate(ContractRequest contractRequest){
        Contract contract = contractRequest.getContract();
        String action = contractRequest.getWorkflow().getAction();
        log.info("Update:: Enrich contract create request. ContractId ["+contract.getId()+"], action ["+action+"]");
        Object mdmsForEnrichment = fetchMDMSDataForEnrichment(contractRequest);
        // Enrich LineItems
        enrichContractLineItems(contractRequest,mdmsForEnrichment);
        // Enrich UUID and AuditDetails
        enrichIdsAndAuditDetailsOnUpdate(contractRequest);
        //Enrich contract issue date on workflow approve action and start date and end date on workflow accept action
        enrichContractDates(contractRequest);
        //mark contract and its components as INACTIVE when workflow has REJECT action
        enrichContractComponents(contractRequest);
        //Create register as soon as contract is accepted by contractor
        enrichRegister(contractRequest,mdmsForEnrichment);
    }

    private Object fetchMDMSDataForEnrichment(ContractRequest contractRequest) {
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String tenantId = contract.getTenantId();
        String rootTenantId = tenantId.split("\\.")[0];
        String contractType = contract.getContractType();
        Object mdmsData = mdmsUtils.fetchMDMSForEnrichment(requestInfo, rootTenantId, contractType);
        log.info("MDMS data fetched for enrichment. ContractId ["+contract.getId()+"]");
        return mdmsData;
    }

    private void enrichRegister(ContractRequest contractRequest,Object mdmsData) {
        Contract contract = contractRequest.getContract();
        Workflow workflow=contractRequest.getWorkflow();
        if("ACCEPT".equalsIgnoreCase(workflow.getAction())  && shouldCreateRegister(mdmsData)){
            log.info("Create register for Contract ["+contract.getId()+"]");
            final String attendanceRegisterNumber = attendanceUtils.createAttendanceRegister(contractRequest);
            final Object additionalDetails = contractRequest.getContract().getAdditionalDetails();
            try {
                JsonNode node = mapper.readTree(mapper.writeValueAsString(additionalDetails));
                ((ObjectNode)node).put("attendanceRegisterNumber",attendanceRegisterNumber);
                contractRequest.getContract().setAdditionalDetails(mapper.readValue(node.toString(), Object.class));
            }
            catch (Exception e){
                log.error("Error while parsing additionalDetails object. Contract ["+contract.getId()+"]");
                throw new CustomException("PARSE_ERROR","Error while parsing");
            }
            log.info("Register created for Contract ["+contract.getId()+"]");
        }
    }

    private boolean shouldCreateRegister(Object mdmsData) {
        List<Object> contractTypeRes = commonUtil.readJSONPathValue(mdmsData,JSON_PATH_FOR_CONTRACT_TYPE_VERIFICATION);
        if(!contractTypeRes.isEmpty())
            return true;
        return false;
    }

    private void enrichContractComponents(ContractRequest contractRequest){
        Workflow workflow=contractRequest.getWorkflow();
        Contract contract = contractRequest.getContract();
        if("REJECT".equalsIgnoreCase(workflow.getAction())){
            log.info("Enriching contract components as INACTIVE on workflow 'REJECT' action. Contract Id ["+contract.getId()+"]");
            markContractAndDocumentsAsInactive(contractRequest);
            markLineItemsAndAmountBreakupsAsInactive(contractRequest);
            log.info("Contract components are marked as INACTIVE on workflow 'REJECT' action. Contract Id ["+contract.getId()+"]");
        }
    }

    private void markContractAndDocumentsAsInactive(ContractRequest contractRequest){
        Contract contract=contractRequest.getContract();
        List<Document> documents=contractRequest.getContract().getDocuments();
        contract.setStatus(Status.INACTIVE);
        // Check if documents exists then only update the status
        if (documents != null && !documents.isEmpty()) {
            for(Document document:documents){
                document.setStatus(Status.INACTIVE);
            }
        }
    }

    private void markLineItemsAndAmountBreakupsAsInactive(ContractRequest contractRequest){
        List<LineItems> lineItems=contractRequest.getContract().getLineItems();
        for(LineItems lineItem:lineItems){
            lineItem.setStatus(Status.INACTIVE);
            List<AmountBreakup> amountBreakups=lineItem.getAmountBreakups();
            for(AmountBreakup amountBreakup:amountBreakups){
                amountBreakup.setStatus(Status.INACTIVE);
            }
        }
    }

    private void enrichContractDates(ContractRequest contractRequest){
        Workflow workflow=contractRequest.getWorkflow();
        Contract contract=contractRequest.getContract();
        if("APPROVE".equalsIgnoreCase(workflow.getAction())){
            log.info("Update :: Enriching contract issueDate on workflow 'APPROVE' action. ContractId: ["+contract.getId()+"]");
            long currentTime = Instant.now().toEpochMilli();
            contract.setIssueDate(new BigDecimal(currentTime));
        }
        if("ACCEPT".equalsIgnoreCase(workflow.getAction())){
            log.info("Update :: Enriching contract startDate endDate on workflow 'ACCEPT' action. ContractId: ["+contract.getId()+"]");
            LocalDate localDate = LocalDate.now();
            // Contract start date will be MID time of today's date
            long startTime = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long localDateTimeMIN = localDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long endDate=localDateTimeMIN+TimeUnit.DAYS.toMillis(contract.getCompletionPeriod());
            contract.setStartDate(new BigDecimal(startTime));
            contract.setEndDate(new BigDecimal(endDate));
        }
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
            // Check if documents are available then do UUID generation
            if (documents != null && !documents.isEmpty()) {
                for (Document document : documents) {
                    if(document.getId() == null) {
                        document.setId(String.valueOf(UUID.randomUUID()));
                    }
                }
            }
        }
        log.info("Update :: audit details are enriched for contractId ["+contract.getId()+"]");
    }

    private void enrichContractLineItems(ContractRequest contractRequest,Object mdms) {
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String tenantId = contract.getTenantId();
        List<LineItems> providedLineItems = contract.getLineItems();
        Set<String> providedEstimateIds = providedLineItems.stream().map(e -> e.getEstimateId()).collect(Collectors.toSet());

        log.info("Fetch active estimates for provided estimate Ids ["+providedEstimateIds+"]");
        List<Estimate> fetchedActiveEstimates = fetchActiveEstimates(requestInfo,tenantId,providedEstimateIds);
        log.info("Active estimates are fetched for provided estimate Ids ["+providedEstimateIds+"]");
        log.info("Fetch allowed overheads from mdms");
        List<Object> objects = fetchAllowedOverheardsFromMDMS(mdms);
        log.info("Allowed overheads are: "+objects);

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
        log.info("LineItem enrichment is done for");
    }

    private List<Estimate> fetchActiveEstimates(RequestInfo requestInfo, String tenantId, Set<String> providedEstimateIds) {
        return estimateServiceUtil.fetchActiveEstimates(requestInfo,tenantId,providedEstimateIds);
    }

    private List<Object> fetchAllowedOverheardsFromMDMS(Object mdms) {
        return commonUtil.readJSONPathValue(mdms, JSON_PATH_FOR_OVER_HEADS_VERIFICATION);
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

        log.info("Create :: Contract id, agreementDate, audit details enrichment is done. Contract Id ["+contract.getId()+"]");
    }

    private void enrichContractNumber(ContractRequest contractRequest) {
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String rootTenantId = contract.getTenantId().split("\\.")[0];
        List<String> idList = idgenUtil.getIdList(requestInfo, rootTenantId, config.getIdgenContractNumberName(), "", 1);
        String generatedContractNumber = idList.get(0);
        contract.setContractNumber(generatedContractNumber);
        log.info("Contract Number enrichment is done. Generated Contract Number["+generatedContractNumber+"]");
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
