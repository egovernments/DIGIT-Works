package org.egov.works.enrichment;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.egov.common.contract.workflow.ProcessInstance;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.kafka.ContractProducer;
import org.egov.works.repository.ContractRepository;
import org.egov.works.service.WorkflowService;
import org.egov.works.services.common.models.estimate.AmountDetail;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.estimate.EstimateDetail;
import org.egov.works.util.*;
import org.egov.works.validator.ContractServiceValidator;
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

import static org.egov.works.util.ContractServiceConstants.*;

@Component
@Slf4j
public class ContractEnrichment {

    private final IdgenUtil idgenUtil;

    private final ContractServiceConfiguration config;

    private final ContractRepository contractRepository;

    private final EstimateServiceUtil estimateServiceUtil;

    private final MDMSUtils mdmsUtils;

    private final AttendanceUtils attendanceUtils;

    private final CommonUtil commonUtil;

    private final ObjectMapper mapper;

    private final ContractProducer contractProducer;

    private final WorkflowService workflowService;

    private final ContractServiceValidator contractServiceValidator;

    @Autowired
    public ContractEnrichment(IdgenUtil idgenUtil, ContractServiceConfiguration config, ContractRepository contractRepository, EstimateServiceUtil estimateServiceUtil, MDMSUtils mdmsUtils, AttendanceUtils attendanceUtils, CommonUtil commonUtil, ObjectMapper mapper, ContractProducer contractProducer, WorkflowService workflowService, ContractServiceValidator contractServiceValidator) {
        this.contractRepository = contractRepository;
        this.idgenUtil = idgenUtil;
        this.config = config;
        this.estimateServiceUtil = estimateServiceUtil;
        this.mdmsUtils = mdmsUtils;
        this.attendanceUtils = attendanceUtils;
        this.commonUtil = commonUtil;
        this.mapper = mapper;
        this.contractProducer = contractProducer;
        this.workflowService = workflowService;
        this.contractServiceValidator = contractServiceValidator;
    }

    public void enrichContractOnCreate(ContractRequest contractRequest){
        Object mdmsForEnrichment = fetchMDMSDataForEnrichment(contractRequest);
        // Enrich Contract business service
        enrichContractBusinessService(contractRequest);
        // Enrich LineItems
        enrichContractLineItems(contractRequest,mdmsForEnrichment);
        // Enrich UUID and AuditDetails
        enrichIdsAgreementDateAndAuditDetailsOnCreate(contractRequest);
        // Enrich Supplement number and mark contracts and document status as in-workflow
        if (contractRequest.getContract().getBusinessService() != null) {

            switch(contractRequest.getContract().getBusinessService()){
                case CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE:
                {
                    enrichSupplementNumber(contractRequest);
                    markContractAndDocumentsStatus(contractRequest, Status.INWORKFLOW);
                    markLineItemsAndAmountBreakupsStatus(contractRequest, Status.INWORKFLOW);

                    break;
                }
                case CONTRACT_REVISION_ESTIMATE:
                {
                    enrichSupplementNumber(contractRequest);
                    markContractAndDocumentsStatus(contractRequest, Status.ACTIVE);
                    markLineItemsAndAmountBreakupsStatus(contractRequest, Status.ACTIVE);
                    enrichPreviousContractLineItems(contractRequest);
                    break;
                }
                default:
                {
                    log.info("Request Triggered for buisness service CONTRACT ");
                    enrichContractNumber(contractRequest);

                }
            }
            // Enrich Supplement Number


        }
    }

    public void enrichContractOnUpdate(ContractRequest contractRequest){
        Contract contract = contractRequest.getContract();
        String action = contractRequest.getWorkflow().getAction();
        log.info("Update:: Enrich contract create request. ContractId ["+contract.getId()+"], action ["+action+"]");
        Object mdmsForEnrichment = fetchMDMSDataForEnrichment(contractRequest);
        // Enrich Contract business service
        enrichContractBusinessService(contractRequest);
        // Enrich LineItems
        enrichContractLineItems(contractRequest,mdmsForEnrichment);
        // Enrich UUID and AuditDetails
        enrichIdsAndAuditDetailsOnUpdate(contractRequest);
        //Enrich contract issue date on workflow approve action and start date and end date on workflow accept action
        enrichContractDates(contractRequest);
        //mark contract and its components as INACTIVE when workflow has REJECT action
        enrichContractComponents(contractRequest);
        if (contract.getBusinessService() == null || contract.getBusinessService().equalsIgnoreCase(CONTRACT_BUSINESS_SERVICE)) {
            //Create register as soon as contract is accepted by contractor
            enrichRegister(contractRequest, mdmsForEnrichment);
        }
    }

    private Object fetchMDMSDataForEnrichment(ContractRequest contractRequest) {
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        String tenantId = contract.getTenantId();
        String contractType = contract.getContractType();
        Object mdmsData = mdmsUtils.fetchMDMSForEnrichment(requestInfo, tenantId, contractType);
        log.info("MDMS data fetched for enrichment. ContractId ["+contract.getId()+"]");
        return mdmsData;
    }

    private void enrichContractBusinessService(ContractRequest contractRequest){
        Contract contract = contractRequest.getContract();
        if(contract.getBusinessService() == null){
            contract.setBusinessService(CONTRACT_BUSINESS_SERVICE);
        }
    }

    private void enrichRegister(ContractRequest contractRequest,Object mdmsData) {
        Contract contract = contractRequest.getContract();
        Workflow workflow=contractRequest.getWorkflow();
        if((contractRequest.getContract().getBusinessService() == null || contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_BUSINESS_SERVICE))
                && ACCEPT_ACTION.equalsIgnoreCase(workflow.getAction())  && shouldCreateRegister(mdmsData)){
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
        return !contractTypeRes.isEmpty();
    }

    private void enrichContractComponents(ContractRequest contractRequest){
        Workflow workflow=contractRequest.getWorkflow();
        Contract contract = contractRequest.getContract();
        if(REJECT_ACTION.equalsIgnoreCase(workflow.getAction())){
            log.info("Enriching contract components as INACTIVE on workflow 'REJECT' action. Contract Id ["+contract.getId()+"]");
            markContractAndDocumentsStatus(contractRequest, Status.INACTIVE);
            markLineItemsAndAmountBreakupsStatus(contractRequest, Status.INACTIVE);
            log.info("Contract components are marked as INACTIVE on workflow 'REJECT' action. Contract Id ["+contract.getId()+"]");
        }
        if(contractRequest.getContract().getBusinessService() != null &&
                contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE)
                && APPROVE_ACTION.equalsIgnoreCase(workflow.getAction())){
            markContractAndDocumentsStatus(contractRequest, Status.ACTIVE);
            markLineItemsAndAmountBreakupsStatus(contractRequest, Status.ACTIVE);
        }
    }

    private void markContractAndDocumentsStatus(ContractRequest contractRequest, Status status){
        log.info("Setting contract and document status");
        Contract contract=contractRequest.getContract();
        List<Document> documents=contractRequest.getContract().getDocuments();
        contract.setStatus(status);
        // Check if documents exists then only update the status
        if (documents != null && !documents.isEmpty()) {
            for(Document document:documents){
                document.setStatus(status);
            }
        }
    }

    private void markLineItemsAndAmountBreakupsStatus(ContractRequest contractRequest, Status status){
        log.info("Setting line items and amount breakup status");
        List<LineItems> lineItems=contractRequest.getContract().getLineItems();
        for(LineItems lineItem:lineItems){
            lineItem.setStatus(status);
            List<AmountBreakup> amountBreakups=lineItem.getAmountBreakups();
            for(AmountBreakup amountBreakup:amountBreakups){
                amountBreakup.setStatus(status);
            }
        }
    }

    private void enrichContractDates(ContractRequest contractRequest){
        Workflow workflow=contractRequest.getWorkflow();
        Contract contract=contractRequest.getContract();
        if (contract.getBusinessService() == null || contract.getBusinessService().equalsIgnoreCase(CONTRACT_BUSINESS_SERVICE)) {
            if (APPROVE_ACTION.equalsIgnoreCase(workflow.getAction())) {
                log.info("Update :: Enriching contract issueDate on workflow 'APPROVE' action. ContractId: [" + contract.getId() + "]");
                long currentTime = Instant.now().toEpochMilli();
                contract.setIssueDate(new BigDecimal(currentTime));
            }
            if (ACCEPT_ACTION.equalsIgnoreCase(workflow.getAction())) {
                log.info("Update :: Enriching contract startDate endDate on workflow 'ACCEPT' action. ContractId: [" + contract.getId() + "]");
                LocalDate localDate = LocalDate.now();
                // Contract start date will be MID time of today's date
                long startTime = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                long localDateTimeMIN = localDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                long endDate = localDateTimeMIN + TimeUnit.DAYS.toMillis(contract.getCompletionPeriod());
                contract.setStartDate(new BigDecimal(startTime));
                contract.setEndDate(new BigDecimal(endDate));
            }
        }
    }

    private void enrichIdsAndAuditDetailsOnUpdate(ContractRequest contractRequest) {
        Contract contract = contractRequest.getContract();
        AuditDetails providedAuditDetails = contractRequest.getContract().getAuditDetails();
        AuditDetails auditDetails = getAuditDetails(contractRequest.getRequestInfo().getUserInfo().getUuid(), providedAuditDetails, false);
        contract.setAuditDetails(auditDetails);
        for(LineItems lineItem : contract.getLineItems()){
            if(lineItem.getId() == null) {
                lineItem.setId(String.valueOf(UUID.randomUUID()));
            }
            if(lineItem.getContractLineItemRef() == null) {
                lineItem.setContractLineItemRef(String.valueOf(UUID.randomUUID()));
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
        Set<String> providedEstimateIds = providedLineItems.stream().map(LineItems::getEstimateId).collect(Collectors.toSet());

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
                                .status(Status.fromValue(fetchedEstimate.getStatus().toString()))
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

                        addLineItem(refinedLineItems,lineItem,objects);
                    }
                } else {
                    addLineItem(refinedLineItems,providedLineItem,objects);
                }
            }
        }

        contract.getLineItems().clear();
        contract.setLineItems(refinedLineItems);
        setContractLineItemRef(contractRequest, fetchedActiveEstimates.get(0));
        log.info("LineItem enrichment is done");
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
        if (contract.getBusinessService() != null && (contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE)
                || contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_REVISION_ESTIMATE))) {
            List<Contract> contractsFromDB = contractRepository.getActiveContractsFromDB(contractRequest);
            contract.setOldUuid(contractsFromDB.get(0).getId());
            Long versionNumber = contractsFromDB.get(0).getVersionNumber();
            setVersionNumber(contract, versionNumber);
        } else {
            contract.setVersionNumber(1l);
        }
        contract.getLineItems().forEach(lineItems -> {
            if (lineItems.getContractLineItemRef() == null) {
                lineItems.setContractLineItemRef(UUID.randomUUID().toString());
            }
        });
        contract.setId(String.valueOf(UUID.randomUUID()));
        BigDecimal agreementDate = contract.getAgreementDate();
        if(agreementDate == null){
            agreementDate = BigDecimal.valueOf(Instant.now().toEpochMilli());
            contract.setAgreementDate(agreementDate);
        }
        AuditDetails auditDetails = getAuditDetails(contractRequest.getRequestInfo().getUserInfo().getUuid(), null, true);
        contract.setAuditDetails(auditDetails);
        for(LineItems lineItem : contract.getLineItems()){
            lineItem.setId(String.valueOf(UUID.randomUUID()));
            lineItem.setAuditDetails(auditDetails);
            for(AmountBreakup amountBreakup : lineItem.getAmountBreakups()){
                amountBreakup.setId(String.valueOf(UUID.randomUUID()));
            }
            List<Document> documents = contract.getDocuments();
            if (documents != null) {
                for (Document document : documents) {
                    document.setId(String.valueOf(UUID.randomUUID()));
                    document.setContractId(contract.getId());
                }
            }
        }

        log.info("Create :: Contract id, agreementDate, audit details enrichment is done. Contract Id ["+contract.getId()+"]");
    }
    private void setVersionNumber(Contract contract, Long versionNumber) {
        if (versionNumber == null) {
            versionNumber = 1L;
        }
        versionNumber += 1;
        contract.setVersionNumber(versionNumber);
    }

    private void enrichContractNumber(ContractRequest contractRequest) {
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        Contract contract = contractRequest.getContract();
        List<String> idList = idgenUtil.getIdList(requestInfo, contract.getTenantId(), config.getIdgenContractNumberName(), "", 1);
        String generatedContractNumber = idList.get(0);
        contract.setContractNumber(generatedContractNumber);
        log.info("Contract Number enrichment is done. Generated Contract Number["+generatedContractNumber+"]");
    }
    private void enrichSupplementNumber (ContractRequest contractRequest) {
        log.info("Generating supplement number");

        String idName = contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_REVISION_ESTIMATE)?
                config.getIdgenRevisionNumberName():config.getIdgenSupplementNumberName();

        List<String> idList = idgenUtil.getIdList(contractRequest.getRequestInfo(), contractRequest.getContract().getTenantId(),
                idName, "", 1);
        String generatedSupplementNumber = idList.get(0);
        contractRequest.getContract().setSupplementNumber(generatedSupplementNumber);
        log.info("Supplement Number enrichment is done. Generated Supplement Number ["+generatedSupplementNumber+"]");
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
    private void setContractLineItemRef(ContractRequest contractRequest, Estimate estimate) {
        if (contractRequest.getContract().getBusinessService() != null
                && (contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE)
                || contractRequest.getContract().getBusinessService().equalsIgnoreCase(CONTRACT_REVISION_ESTIMATE))) {
            // Fetch previous contract and create estimateDetailId to contractLineItemRef map
            Contract previousActiveContract = contractRepository.getActiveContractsFromDB(contractRequest).get(0);
            // Using set to filter out the duplicate contractLineItem from previousContract object
            Set<String> collectedIds = new HashSet<>();
            Map<String, String> estimateDetailIdToContractLineItemRefMap = previousActiveContract.getLineItems()
                    .stream()
                    .filter(lineItem -> collectedIds.add(lineItem.getEstimateLineItemId()))
                    .collect(Collectors.toMap(LineItems::getEstimateLineItemId, LineItems::getContractLineItemRef));
            /*Map<String, String> estimateDetailIdToContractLineItemRefMap = previousActiveContract.getLineItems()
                    .stream().collect(Collectors.toMap(LineItems::getEstimateLineItemId, LineItems::getContractLineItemRef));*/
            // Create map of estimateDetailId and prevEstimateDetailId
            Map<String, String> estimateDetailIdToPreviousEstimateDetailIdMap = estimate.getEstimateDetails()
                    .stream()
                    .collect(Collectors.toMap(EstimateDetail::getId, estimateDetail ->
                    {
                        if(contractRequest.getContract().getLineItems().get(0).getEstimateId().equalsIgnoreCase(previousActiveContract.getLineItems().get(0).getEstimateId())){
                            return estimateDetail.getId();
                        } else
                            return estimateDetail.getPreviousLineItemId() != null ? estimateDetail.getPreviousLineItemId() : "default";
                    }));
            // iterate through current contract line item estimate detail id and get the prev estimate detail id,
            // if it is not null then get the contractLineItemRef by querying the map.
            for (LineItems lineItems : contractRequest.getContract().getLineItems()) {
                String prevEstimateDetailId = estimateDetailIdToPreviousEstimateDetailIdMap.get(lineItems.getEstimateLineItemId());
                if (prevEstimateDetailId != null && !StringUtils.isEmpty(prevEstimateDetailId)) {
                    lineItems.setContractLineItemRef(estimateDetailIdToContractLineItemRefMap.get(prevEstimateDetailId));
                }
            }
            log.info("Done setting contractLineItemRef");
            contractServiceValidator.validateLineItemRef(contractRequest);
            if (Boolean.TRUE.equals(config.getIsMeasurementValidationRequired()) && !contractRequest.getWorkflow().getAction().equalsIgnoreCase(REJECT_ACTION))
                contractServiceValidator.validateMeasurement(contractRequest, estimate);

        }
    }

    public void enrichPreviousContractLineItems(ContractRequest contractRequest) {
        if (contractRequest.getContract().getBusinessService() != null
                && !CONTRACT_BUSINESS_SERVICE.equals(contractRequest.getContract().getBusinessService())) {
            log.info("Setting previous contract statuses inactive");
            Contract previousActiveContract = contractRepository.getActiveContractsFromDB(contractRequest).get(0);

            ContractRequest contractRequestFromDB = ContractRequest.builder()
                    .requestInfo(contractRequest.getRequestInfo())
                    .contract(previousActiveContract).build();
            ProcessInstance processInstance = workflowService.getProcessInstance(contractRequestFromDB);
            contractRequestFromDB.getContract().setProcessInstance(processInstance);

            switch (contractRequest.getContract().getBusinessService()) {
                case CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE: {
                    if (APPROVE_ACTION.equalsIgnoreCase(contractRequest.getWorkflow().getAction())) {
                        markContractAndDocumentsStatus(contractRequestFromDB, Status.INACTIVE);
                        markLineItemsAndAmountBreakupsStatus(contractRequestFromDB, Status.INACTIVE);
                        contractProducer.push(config.getUpdateContractTopic(), contractRequestFromDB);
                        // Push updated end date to kafka topic to update attendance register end date
                        JsonNode requestInfo = mapper.convertValue(contractRequest.getRequestInfo(), JsonNode.class);
                        JsonNode attendanceContractRevisionRequest = mapper.createObjectNode()
                                .putPOJO("RequestInfo", requestInfo)
                                .put("tenantId", contractRequest.getContract().getTenantId())
                                .put("referenceId", contractRequest.getContract().getContractNumber())
                                .put("endDate", contractRequest.getContract().getEndDate());

                        log.info("Pushing updated end date to attendance register end date update topic");
                        contractProducer.push(config.getUpdateTimeExtensionTopic(), attendanceContractRevisionRequest);
                    }
                    break;
                }
                case CONTRACT_REVISION_ESTIMATE: {
                    markContractAndDocumentsStatus(contractRequestFromDB, Status.INACTIVE);
                    markLineItemsAndAmountBreakupsStatus(contractRequestFromDB, Status.INACTIVE);
                    contractProducer.push(config.getUpdateContractTopic(), contractRequestFromDB);
                    break;
                }
                default:
                    log.info("Update Request For Original Contract");
            }
        }
    }

    public AuditDetails getAuditDetails(String by, AuditDetails auditDetails, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        if (Boolean.TRUE.equals(isCreate))
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(auditDetails.getCreatedBy()).lastModifiedBy(by)
                    .createdTime(auditDetails.getCreatedTime()).lastModifiedTime(time).build();
    }

}

