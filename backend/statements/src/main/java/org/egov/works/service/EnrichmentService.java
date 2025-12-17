package org.egov.works.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.estimate.EstimateDetail;
import org.egov.works.services.common.models.estimate.EstimateResponse;
import org.egov.works.util.CommonUtil;
import org.egov.works.util.EstimateUtil;
import org.egov.works.util.MdmsUtil;
import org.egov.works.util.StatementServiceUtil;
import org.egov.works.web.models.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.config.ErrorConfiguration.*;
import static org.egov.works.config.ServiceConstants.ESTIMATE_DETAIL_SOR_CATEGORY;

@Service
@Slf4j
public class EnrichmentService {

    @Autowired
    private EstimateUtil estimateUtil;


    @Autowired
    private MdmsUtil mdmsUtil;

    @Autowired
    private StatementConfiguration configuration;
    @Autowired
    private StatementServiceUtil statementServiceUtil;
    @Autowired
    private CommonUtil commonUtil;


    private static final String OR_ADDITIONAL_FILTER = " || ";
    public static final String MDMS_SOR_MASTER_NAME = "SOR";
    private static final String SOR_FILTER_CODE = "@.id=='%s'";
    private static final String FILTER_START = "[?(";
    private static final String FILTER_END = ")]";

    public StatementPushRequest enrichStatementPushRequest(StatementCreateRequest statementCreateRequest, Estimate estimate, Boolean isCreate) {
        log.info("EnrichmentService::enrichStatementRequest");
        StatementPushRequest statementPushRequest;
        StatementRequest statementRequest = statementCreateRequest.getStatementRequest();
        RequestInfo requestInfo = statementCreateRequest.getRequestInfo();
        if (estimate == null) {
            EstimateResponse estimateResponse = estimateUtil.getEstimate(statementRequest.getId(), statementRequest.getTenantId(),
                    Statement.StatementTypeEnum.ANALYSIS.toString(), statementCreateRequest.getRequestInfo());
            if (estimateResponse == null || estimateResponse.getEstimates().isEmpty()) {
                throw new CustomException(ESTIMATE_RESPONSE_NULL_EMPTY_CODE, ESTIMATE_RESPONSE_NULL_EMPTY_MSG);
            }
            return enrichStatementPushRequestWithDetails(estimateResponse.getEstimates().get(0), requestInfo, statementRequest, isCreate);
        } else {
            return enrichStatementPushRequestWithDetails(estimate, requestInfo, statementRequest, isCreate);
        }

    }

    /**
     * This method is used to fetch the Sor Rates , Sor Composition
     * And Sor description and then create statement push request
     *
     * @param estimate
     * @param requestInfo
     * @param statementRequest
     * @return statementPusRequest
     */
    public StatementPushRequest enrichStatementPushRequestWithDetails(Estimate estimate, RequestInfo requestInfo, StatementRequest statementRequest, Boolean isCreate) {
        Map<String, List<EstimateDetail>> sorIdToEstimateDetailMap = new HashMap<>();
        Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap = new HashMap<>();
        if (null != estimate && !estimate.getEstimateDetails().isEmpty()) {
            List<EstimateDetail> filteredActiveEstimateDetails = estimate.getEstimateDetails().stream()
                    .filter(ed -> ESTIMATE_DETAIL_SOR_CATEGORY.equals(ed.getCategory()) &&
                            ed.isActive())
                    .toList();

            // If no valid details found and not in create mode, return null
            if (filteredActiveEstimateDetails.isEmpty() && !isCreate) {
                return null;
            } else if (filteredActiveEstimateDetails.isEmpty()) {
                throw new CustomException(NO_SOR_PRESENT_CODE, NO_SOR_PRESENT_MSG);
            }

            Set<String> uniqueIdentifiers = new HashSet<>();
            Set<String> basicSorIds = new HashSet<>();
            for (EstimateDetail estimateDetail : filteredActiveEstimateDetails) {
                uniqueIdentifiers.add(estimateDetail.getSorId());
                sorIdToEstimateDetailMap.computeIfAbsent(estimateDetail.getSorId(), k -> new ArrayList<>()).add(estimateDetail);
                BigDecimal quantity = estimateDetail.getIsDeduction() ? BigDecimal.valueOf(estimateDetail.getNoOfunit()).multiply(BigDecimal.valueOf(-1))
                        : BigDecimal.valueOf(estimateDetail.getNoOfunit());
                sorIdToEstimateDetailQuantityMap.merge(estimateDetail.getSorId(), quantity, BigDecimal::add);
            }
           // Map<String, Sor> sorDescriptionMapForSorMappedInEstimate = mdmsUtil.fetchSorData(requestInfo, configuration.getStateLevelTenantId(), new ArrayList<>(uniqueIdentifiers), true);
            Map<String, Rates> sorRates = mdmsUtil.fetchBasicRates(requestInfo, estimate.getTenantId(), new ArrayList<>(uniqueIdentifiers));
            Map<String, SorComposition> sorIdCompositionMap = new LinkedHashMap<>();
            Set<String> compositionIdSet = new HashSet<>();
            Long currentEpochTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000;
            for (String sorId : uniqueIdentifiers) {
                Rates rate = sorRates.get(sorId);
                Optional.ofNullable(rate.getCompositionId())
                        .ifPresent(compositionIdSet::add);
            }
      /*      // Fetch SorComposition for the current compositionIdSet
            Map<String, SorComposition> fetchedMap = mdmsUtil.fetchSorCompositionBasedOnCompositionId(requestInfo, compositionIdSet, statementRequest.getTenantId(), currentEpochTime);
            // Add fetchedMap entries to sorIdCompositionMap
            sorIdCompositionMap.putAll(fetchedMap);*/

            // Check if compositionIdSet is not empty or null
            Optional.ofNullable(compositionIdSet)
                    .filter(ids -> !ids.isEmpty())  // Check if compositionIdSet is not empty
                    .ifPresent(ids -> {
                        // Perform computation only if compositionIdSet is not empty
                        Map<String, SorComposition> fetchedMap = mdmsUtil.fetchSorCompositionBasedOnCompositionId(
                                requestInfo, ids, statementRequest.getTenantId(), currentEpochTime,false);
                        sorIdCompositionMap.putAll(fetchedMap);
                    });

            if (sorIdCompositionMap != null && !sorIdCompositionMap.isEmpty()) {
                for (SorComposition sorComposition : sorIdCompositionMap.values()) {
                    if(sorComposition!=null){
                        basicSorIds.addAll(sorComposition.getBasicSorDetails().stream().map(SorCompositionBasicSorDetail::getSorId).toList());
                        // Fetch Rates For Basic Sor present in the SorComposition
                        Map<String, Rates> basicSorRates = mdmsUtil.fetchBasicRates(requestInfo, estimate.getTenantId(), new ArrayList<>(basicSorIds));
                        // Put the basicSorRates data in sorRates map object
                        basicSorRates.forEach((key, value) -> sorRates.putIfAbsent(key, value));
                    }

                }
            } else {
                log.info("COMPOSITION_NOT_PRESENT ::: For Sor Ids mapped in the estimate detail no Sor Composition is present: {}", uniqueIdentifiers.toString());
            }
            uniqueIdentifiers.addAll(basicSorIds);

            Map<String, Sor> sorDescriptionMap = mdmsUtil.fetchSorData(requestInfo, configuration.getStateLevelTenantId(), new ArrayList<>(uniqueIdentifiers), true);

            return createStatementPushRequest(estimate, sorIdToEstimateDetailMap, requestInfo,
                    sorIdCompositionMap, sorIdToEstimateDetailQuantityMap, sorRates, sorDescriptionMap, filteredActiveEstimateDetails);


        } else {
            throw new CustomException("INVALID_ESTIMATE_DETAILS", "Estimate details is not present");
        }

    }

    /**
     * @param statementCreateRequest
     * @param existingStatement
     * @return
     */
    public StatementPushRequest enrichStatementPushRequestForUpdate(StatementCreateRequest statementCreateRequest, Statement existingStatement, Estimate estimate, Boolean isCreate) {
        log.info("EnrichmentService::enrichStatementPushRequestForUpdate");

        StatementPushRequest updatedStatementPushRequest = enrichStatementPushRequest(statementCreateRequest, estimate, isCreate);

        if (updatedStatementPushRequest == null) {
            return checkAndUpdateStatementPushRequest(existingStatement);
        }

        // Map to hold existing SorDetails by sorId and also check
        // if updatedStatementPushRequest is null then
        //Update SorDetails as inActive.
        Map<String, SorDetail> existingStatementSorDetailsMap = new HashMap<>();
        for (SorDetail sorDetail : existingStatement.getSorDetails()) {
            existingStatementSorDetailsMap.put(sorDetail.getSorId(), sorDetail);
        }

        // Set to track sorIds in the new statement
        Set<String> newStatementSorIds = new HashSet<>();
        for (SorDetail newSorDetail : updatedStatementPushRequest.getStatement().getSorDetails()) {
            newStatementSorIds.add(newSorDetail.getSorId());

            String sorId = newSorDetail.getSorId();
            if (!existingStatementSorDetailsMap.containsKey(sorId)) {
                // Add newSorDetail if it is not in the existing statement
                existingStatementSorDetailsMap.put(sorId, newSorDetail);
            } else {
                // Update existingSorDetail with newSorDetail if they differ
                SorDetail existingSorDetail = existingStatementSorDetailsMap.get(sorId);
                existingSorDetail.setAdditionalDetails(newSorDetail.getAdditionalDetails());
                existingSorDetail.setBasicSorDetails(new ArrayList<>(newSorDetail.getBasicSorDetails()));
               /* if (!areBasicSorDetailsEqual(existingSorDetail.getBasicSorDetails(), newSorDetail.getBasicSorDetails())) {
                    existingSorDetail.setBasicSorDetails(new ArrayList<>(newSorDetail.getBasicSorDetails()));
                }*/

                // Update line items if necessary
                List<BasicSor> existingBasicSorList = existingSorDetail.getLineItems();
                List<BasicSor> newBasicSorList = newSorDetail.getLineItems();
                List<BasicSor> updatedBasicSorList= new ArrayList<BasicSor>();

                if (newBasicSorList != null && !areBasicSorDetailsOfLineItemsEqual(existingBasicSorList, newBasicSorList)) {
                    log.info("Updated Basic Sor Line Items object in existing statement");
                    if (existingBasicSorList == null) {
                        existingBasicSorList = new ArrayList<>();
                        existingBasicSorList.addAll(newBasicSorList);
                        existingBasicSorList.forEach(basicSor -> basicSor.setReferenceId(existingSorDetail.getId()));
                    }


                    updatedBasicSorList.addAll(existingBasicSorList);
                    // Set updatedBasicSorList to existingSorDetail after updates
                    existingSorDetail.setLineItems(updatedBasicSorList);
                }

                existingStatementSorDetailsMap.put(sorId,existingSorDetail);
            }
        }

       // Map<String, BigDecimal> cumulativeQuantities = new HashMap<>();


        for (String sorId : existingStatementSorDetailsMap.keySet()) {
            // Track cumulative amounts for each sorType
            Map<String, BigDecimal> cumulativeAmounts = new HashMap<>();
            // Mark sorDetails in the existing statement as inactive if not present in the new statement
            if (!newStatementSorIds.contains(sorId)) {
                SorDetail existingSorDetail = existingStatementSorDetailsMap.get(sorId);
                existingSorDetail.setIsActive(Boolean.FALSE);
                existingStatementSorDetailsMap.put(sorId,existingSorDetail);
            }

            if(existingStatementSorDetailsMap.get(sorId).getLineItems()!=null) {

                ObjectMapper objectMapper = new ObjectMapper();

                List<BasicSorDetails> basicSorDetailsList = objectMapper.convertValue(
                        existingStatementSorDetailsMap.get(sorId).getBasicSorDetails(),
                        new TypeReference<List<BasicSorDetails>>() {
                        }
                );

                for (BasicSorDetails detail : basicSorDetailsList) {
                    String type = detail.getType();
                    cumulativeAmounts.put(type, cumulativeAmounts.getOrDefault(type, BigDecimal.ZERO).add(detail.getAmount()));

                }
                // Update cumulative amounts and quantities
                List<BasicSorDetails> basicSorDetails = new ArrayList<>();
                for (Map.Entry<String, BigDecimal> entry : cumulativeAmounts.entrySet()) {
                    String type = entry.getKey();
                    BigDecimal amount = entry.getValue().setScale(2, RoundingMode.HALF_UP);
                    BasicSorDetails detail = BasicSorDetails.builder()
                            .amount(amount)
                            .type(type)
                            .build();
                    basicSorDetails.add(detail);
                }
                existingStatementSorDetailsMap.get(sorId).setBasicSorDetails(basicSorDetails);
            }

        }


        // Convert the map back to a list and update the statement
        List<SorDetail> updatedSorDetailsList = new ArrayList<>(existingStatementSorDetailsMap.values());
        existingStatement.setSorDetails(updatedSorDetailsList);
        //existingStatement.setBasicSorDetails(updatedStatementPushRequest.getStatement().getBasicSorDetails());

        // Set audit details
        AuditDetails auditDetails = statementServiceUtil.getAuditDetails(statementCreateRequest.getRequestInfo().getUserInfo().getUuid(), existingStatement, false);
        existingStatement.setAuditDetails(auditDetails);
        existingStatement.setAdditionalDetails(updatedStatementPushRequest.getStatement().getAdditionalDetails());
        //Cummulative BasicSorDetails on Parent Level
          accumulateBasicSorDetails(existingStatement);

        // Update the StatementPushRequest with the updated Statement object
        updatedStatementPushRequest.setStatement(existingStatement);

        return updatedStatementPushRequest;
    }

    /**
     * This method is used to update all the SOR Details object status as In-Active
     * in existingStatement if all the SORS are removed from the estimate
     *
     * @param existingStatement
     * @return statementPushRequest
     */
    private static StatementPushRequest checkAndUpdateStatementPushRequest(Statement existingStatement) {
        log.info("SORS_REMOVED", "Sor mapped in estimate are removed");
        List<SorDetail> updatedSorDetail = new ArrayList<>();
        for (SorDetail sorDetail : existingStatement.getSorDetails()) {
            sorDetail.setIsActive(Boolean.FALSE);
            updatedSorDetail.add(sorDetail);
        }
        // Remove All Sor Details and basicSorDetails from statement
        //And set updateSorDetail
        existingStatement.getSorDetails().clear();
        existingStatement.getBasicSorDetails().clear();
        existingStatement.setSorDetails(updatedSorDetail);

        StatementPushRequest statementPushRequest = new StatementPushRequest();
        statementPushRequest.setStatement(existingStatement);

        return statementPushRequest;
    }


    /**
     * @param statement
     */
    public void accumulateBasicSorDetails(Statement statement) {
        if (statement.getSorDetails() == null || statement.getSorDetails().isEmpty()) {
            log.error("SorDetails are empty or null");
            return;
        }

        // Map to accumulate data by type
        Map<String, BasicSorDetails> cumulativeDataMap = new HashMap<>();

        // Iterate over each sorDetails
        for (SorDetail sorDetail : statement.getSorDetails()) {
            log.info("Processing SorDetail with ID: {}", sorDetail.getSorId());
            if (sorDetail.getIsActive().equals(Boolean.TRUE)) {
                if (sorDetail.getBasicSorDetails() == null || sorDetail.getBasicSorDetails().isEmpty()) {
                    log.info("BasicSorDetails are empty or null for SorDetail ID: {}", sorDetail.getSorId());
                    continue;
                }

                for (Object obj : sorDetail.getBasicSorDetails()) {
                    BasicSorDetails basicSorDetail;
                    if (obj instanceof BasicSorDetails) {
                        basicSorDetail = (BasicSorDetails) obj;
                    } else if (obj instanceof LinkedHashMap) {
                        basicSorDetail = convertMapToBasicSorDetails((LinkedHashMap<?, ?>) obj);
                    } else {
                        log.info("Unrecognized object type: {}", obj.getClass().getName());
                        continue; // Skip unrecognized types
                    }

                    String type = basicSorDetail.getType();
                    if (!cumulativeDataMap.containsKey(type)) {
                        cumulativeDataMap.put(type, new BasicSorDetails());
                    }
                    BasicSorDetails cumulativeData = cumulativeDataMap.get(type);

                    cumulativeData.setType(type);

                    if (cumulativeData.getAmount() == null) {
                        cumulativeData.setAmount(BigDecimal.ZERO);
                    }
                    if (basicSorDetail.getAmount() != null) {
                        cumulativeData.setAmount(cumulativeData.getAmount().add(basicSorDetail.getAmount()));
                    }
                }
            } else {
                log.info(" SorDetail with ID: {} is inactive", sorDetail.getSorId());
            }
        }

        // Convert the map to a list of BasicSorDetails for the statement
        List<BasicSorDetails> cumulativeList = new ArrayList<>(cumulativeDataMap.values());
        statement.setBasicSorDetails(cumulativeList);

        log.info("Accumulated BasicSorDetails:");

    }


    private StatementPushRequest createStatementPushRequest(Estimate estimate, Map<String, List<EstimateDetail>> sorIdToEstimateDetailMap, RequestInfo requestInfo,
                                                            Map<String, SorComposition> sorIdCompositionMap, Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap,
                                                            Map<String, Rates> basicSorRates, Map<String, Sor> sorDescriptionMap, List<EstimateDetail> estimateDetailList) {

        log.info("EnrichmentService::createStatementPushRequest");

        Map<String, Object> additionalDetailsMap = new HashMap<>();
        if (estimate.getRevisionNumber()!=null) {
            additionalDetailsMap.put("estimateNumber", estimate.getRevisionNumber());
        } else {
            additionalDetailsMap.put("estimateNumber", estimate.getEstimateNumber());
        }

        additionalDetailsMap.put("projectId", estimate.getProjectId());

        Statement statement = Statement.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(estimate.getTenantId())
                .targetId(estimate.getId())
                .statementType(Statement.StatementTypeEnum.ANALYSIS)
                .additionalDetails(additionalDetailsMap)
                .build();
        AuditDetails auditDetails = statementServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), statement, true);
        statement.setAuditDetails(auditDetails);

        enrichSorDetailsAndBasicSorDetails(statement, estimate, sorIdCompositionMap, basicSorRates,
                Boolean.TRUE, sorIdToEstimateDetailQuantityMap, sorDescriptionMap, estimateDetailList);
        StatementPushRequest statementPushRequest = StatementPushRequest.builder()
                .requestInfo(requestInfo)
                .statement(statement)
                .build();
        return statementPushRequest;

    }

    private void enrichSorDetailsAndBasicSorDetails(Statement statement, Estimate estimate, Map<String, SorComposition> sorIdCompositionMap,
                                                    Map<String, Rates> basicSorRates, Boolean isCreate,
                                                    Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap, Map<String, Sor> sorDescriptionMap, List<EstimateDetail> estimateDetailList) {

        List<SorDetail> sorDetails = new ArrayList<>();
        Long createdTime = estimate.getAuditDetails().getCreatedTime();
        // Extract sorId from EstimateDetail into a set
        Set<String> sorIdsInEstimateDetails = estimateDetailList.stream()
                .map(EstimateDetail::getSorId)
                .collect(Collectors.toSet());

        for (String sorId : sorIdsInEstimateDetails) {
            List<BasicSor> lineItems = new ArrayList<>();
            SorComposition sorComposition = sorIdCompositionMap.get(sorId);
            Map<String, Object> additionalDetailsMap = new HashMap<>();
            Sor sor = sorDescriptionMap.get(sorId);
            additionalDetailsMap.put("sorDetails", sor);
            if (sor.getSorType().equals(configuration.getWorksSorType()) && sorComposition != null) {
                /*if (sorComposition == null)
                    throw new CustomException("COMPOSITION_NOT_FOUND", "Sor Composition not present for sorId:" + sor.getId());*/
                BigDecimal analysisQuantity = sorComposition.getQuantity();
                List<SorCompositionBasicSorDetail> sorCompositionBasicSorDetails = sorComposition.getBasicSorDetails();

                // Track cumulative amounts for each sorType
                Map<String, BigDecimal> cumulativeAmounts = new HashMap<>();
                Map<String, BigDecimal> cumulativeQuantities = new HashMap<>();

                for (SorCompositionBasicSorDetail sorCompositionBasicSorDetail : sorCompositionBasicSorDetails) {
                    BasicSor basicSor = new BasicSor();
                    String basicSorId = sorCompositionBasicSorDetail.getSorId();
                    BigDecimal basicSorQuantity = sorCompositionBasicSorDetail.getQuantity();
                    computeLineItems(basicSor, basicSorId, basicSorQuantity, analysisQuantity, basicSorRates,
                            sorIdToEstimateDetailQuantityMap, sorId, createdTime, sorDescriptionMap);
                    lineItems.add(basicSor);

                    // Update cumulative amounts and quantities
                    for (BasicSorDetails detail : basicSor.getBasicSorDetails()) {
                        String type = detail.getType();
                        cumulativeAmounts.put(type, cumulativeAmounts.getOrDefault(type, BigDecimal.ZERO).add(detail.getAmount()));
                        cumulativeQuantities.put(type, cumulativeQuantities.getOrDefault(type, BigDecimal.ZERO).add(detail.getQuantity()));
                    }
                }

                // Create basicSorDetails from cumulative amounts
                List<BasicSorDetails> basicSorDetails = new ArrayList<>();
                for (Map.Entry<String, BigDecimal> entry : cumulativeAmounts.entrySet()) {
                    String type = entry.getKey();
                    BigDecimal amount = entry.getValue().setScale(2, RoundingMode.HALF_UP);
                    BasicSorDetails detail = BasicSorDetails.builder()
                            .id(UUID.randomUUID().toString())
                            .amount(amount)
                            .type(type)
                            .build();
                    basicSorDetails.add(detail);
                }
                //Set Additional Details in Sor Details Object
                Map<String, Object> basicSoradditionalDetailsMap = getBasicSoradditionalDetailsMap(basicSorRates, sorIdToEstimateDetailQuantityMap, sorDescriptionMap, sorId);

                // Create a SorDetail for each worksSor
                SorDetail sorDetail = SorDetail.builder()
                        .id(UUID.randomUUID().toString())
                        .sorId(sorId)
                        .tenantId(estimate.getTenantId())
                        .basicSorDetails(basicSorDetails)
                        .statementId(statement.getId())
                        .additionalDetails(basicSoradditionalDetailsMap)
                        .isActive(Boolean.TRUE)
                        .build();

                lineItems.forEach(basicSor -> {
                    basicSor.setReferenceId(sorDetail.getId());
                });
                sorDetail.setLineItems(lineItems);
                if (!isCreate) {
                    sorDetails.addAll(statement.getSorDetails());
                }
                sorDetails.add(sorDetail);
            } else {
                List<BasicSorDetails> basicSorDetails = new ArrayList<>();
                BasicSorDetails basicSorDetail = new BasicSorDetails();
                computeBasicSorDetailsForNonWorksSorInEstimate(sorId, sorIdToEstimateDetailQuantityMap, basicSorDetail, createdTime, sorDescriptionMap, basicSorRates, additionalDetailsMap);

                basicSorDetails.add(basicSorDetail);

                SorDetail sorDetail = SorDetail.builder()
                        .id(UUID.randomUUID().toString())
                        .sorId(sorId)
                        .statementId(statement.getId())
                        .tenantId(estimate.getTenantId())
                        .isActive(Boolean.TRUE)
                        .basicSorDetails(basicSorDetails)
                        .additionalDetails(additionalDetailsMap)
                        .build();
                if (!isCreate) {
                    sorDetails.addAll(statement.getSorDetails());
                }
                sorDetails.add(sorDetail);
            }

        }

        statement.setSorDetails(sorDetails);
        accumulateBasicSorDetails(statement);
    }

    /**
     * This method is used to set the additional Details on SorDetails level
     *
     * @param basicSorRates
     * @param sorIdToEstimateDetailQuantityMap
     * @param sorDescriptionMap
     * @param sorId
     * @return
     */
    private static Map<String, Object> getBasicSoradditionalDetailsMap(Map<String, Rates> basicSorRates, Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap,
                                                                       Map<String, Sor> sorDescriptionMap, String sorId) {
        log.info("EnrichmentService:: getBasicSoradditionalDetailsMap");
        Rates rates = basicSorRates.get(sorId);
        Map<String, Object> basicSoradditionalDetailsMap = new HashMap<>();
        Sor sorDescription = sorDescriptionMap.get(sorId);

        BigDecimal sorQuantity = BigDecimal.ZERO;
        if (sorIdToEstimateDetailQuantityMap.get(sorId).compareTo(BigDecimal.ZERO) < 0) {
            sorQuantity = sorIdToEstimateDetailQuantityMap.get(sorId).multiply(BigDecimal.valueOf(-1));
        } else {
            sorQuantity = sorIdToEstimateDetailQuantityMap.get(sorId);
        }
        BigDecimal labourCessAmount = BigDecimal.ZERO;
        for (AmountDetail amountDetail : rates.getAmountDetails()) {

            if (amountDetail.getHeads().contains("LC")) {
                labourCessAmount = amountDetail.getAmount().multiply(sorQuantity);
                break; // Exit the loop once found
            }
        }

        BigDecimal estimatedAmount = rates.getRate().multiply(sorQuantity).setScale(2, RoundingMode.HALF_UP);
        basicSoradditionalDetailsMap.put("sorDetails", sorDescription);
        basicSoradditionalDetailsMap.put("rateDetails", rates);
        basicSoradditionalDetailsMap.put("estimatedQuantity", sorQuantity);
        basicSoradditionalDetailsMap.put("estimatedAmount", estimatedAmount);
        basicSoradditionalDetailsMap.put("labourCessAmount", labourCessAmount);
        return basicSoradditionalDetailsMap;
    }


    private BasicSorDetails convertMapToBasicSorDetails(LinkedHashMap<?, ?> map) {
        BasicSorDetails basicSorDetails = new BasicSorDetails();
        basicSorDetails.setType((String) map.get("type"));
        Object amount = map.get("amount");
        if (amount instanceof BigDecimal) {
            basicSorDetails.setAmount((BigDecimal) amount);
        } else if (amount instanceof Number) {
            basicSorDetails.setAmount(new BigDecimal(amount.toString()));
        }
        return basicSorDetails;
    }


    private void computeLineItems(BasicSor basicSor, String basicSorId, BigDecimal basicSorQuantity,
                                  BigDecimal analysisQuantity, Map<String, Rates> basicSorRates,
                                  Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap, String worksSor,
                                  Long estimateCreatedTime, Map<String, Sor> sorDescriptionMap) {

        Rates rates = basicSorRates.get(basicSorId);
        Map<String, Object> additionalDetailsMap = new HashMap<>();

        //Rates rates  = commonUtil.getApplicatbleRate(ratesList, estimateCreatedTime);
        BigDecimal basicRate= BigDecimal.ZERO;
        if(rates!=null)
        {            basicRate = rates.getRate();
        }

        /*BigDecimal basicRate = BigDecimal.ONE;*/
        List<BasicSorDetails> basicSorDetailsList = new ArrayList<>();
        Sor sor = sorDescriptionMap.get(basicSorId);
        BigDecimal quantityDefinedInEstimate = sorIdToEstimateDetailQuantityMap.get(worksSor);

        if (quantityDefinedInEstimate.compareTo(BigDecimal.ZERO) < 0) {
            quantityDefinedInEstimate = quantityDefinedInEstimate.multiply(BigDecimal.valueOf(-1));
        }
        BigDecimal quantity = (basicSorQuantity.divide(analysisQuantity, 4, RoundingMode.HALF_UP))
                .multiply(quantityDefinedInEstimate).divide(sor.getQuantity(), 4, RoundingMode.HALF_UP);
        BigDecimal amount = quantity.multiply(basicRate).setScale(2, RoundingMode.HALF_UP);

        BasicSorDetails basicSorDetails = BasicSorDetails.builder()
                .id(UUID.randomUUID().toString())
                // .uom(sor.getUom())
                .type(sor.getSorType())
                //.rate(basicRate)
                .quantity(quantity)
                // .name(sor.getDescription())
                .amount(amount)
                .build();
        additionalDetailsMap.put("sorDetails", sor);
        additionalDetailsMap.put("rateDetails", rates);

        basicSorDetailsList.add(basicSorDetails);
        basicSor.setId(UUID.randomUUID().toString());
        basicSor.setSorId(basicSorId);
        basicSor.setSorType(sor.getSorType());
        basicSor.setBasicSorDetails(basicSorDetailsList);
        basicSor.setAdditionalDetails(additionalDetailsMap);



    }

    private void computeBasicSorDetailsForNonWorksSorInEstimate(String sorId, Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap,
                                                                BasicSorDetails basicSorDetail, Long createdTime,
                                                                Map<String, Sor> sorDescriptionMap, Map<String, Rates> basicSorRates, Map<String, Object> additionalDetailsMap) {
        log.info("EnrichmentSerivce :: computeBasicSorDetailsForNonWorksSorInEstimate");
        BigDecimal quantityDefinedInEstimate = sorIdToEstimateDetailQuantityMap.get(sorId);
        if (quantityDefinedInEstimate.compareTo(BigDecimal.ZERO) < 0) {
            quantityDefinedInEstimate = quantityDefinedInEstimate.multiply(BigDecimal.valueOf(-1));
        }
        Rates rate = basicSorRates.get(sorId);
        BigDecimal basicRate = rate.getRate();
        Sor sor = sorDescriptionMap.get(sorId);
        additionalDetailsMap.put("rateDetails", rate);
        BigDecimal amount = quantityDefinedInEstimate.multiply(basicRate).divide(sor.getQuantity());

        additionalDetailsMap.put("estimatedQuantity", quantityDefinedInEstimate);
        additionalDetailsMap.put("estimatedAmount", amount);

        basicSorDetail.setQuantity(quantityDefinedInEstimate);
        basicSorDetail.setAmount(amount);
        basicSorDetail.setType(sor.getSorType());
        basicSorDetail.setId(UUID.randomUUID().toString());
    }


    private static boolean areBasicSorDetailsOfLineItemsEqual( List<BasicSor> existingBasicSorList, List<BasicSor> newBasicSorList) {

        if (existingBasicSorList == null) {
            return false;
        }else{

        List<BasicSor> updatedBasicSorList= new ArrayList<>();
        boolean hasDifferences = false;

        // Create a map for newBasicSorList
        Map<String, BasicSor> newBasicSorMap = new HashMap<>();
        for (BasicSor basicSor : newBasicSorList) {
            newBasicSorMap.put(basicSor.getSorId(), basicSor);
        }


            for (BasicSor existingBasicSor : existingBasicSorList) {
                String sorId = existingBasicSor.getSorId();
                if (newBasicSorMap.containsKey(sorId)) {
                    BasicSor newBasicSor = newBasicSorMap.get(sorId);

                    BasicSor updatedBasicSor = getBasicSor(existingBasicSor, newBasicSor);

                    updatedBasicSorList.add(updatedBasicSor);
                    hasDifferences = true;

                }else{
                    updatedBasicSorList.add(newBasicSorMap.get(sorId));
                }
            }
            // If there were differences, update the new list with changes
            if (hasDifferences) {
                // First, create a set of sorIds from updatedDetails for quick lookup
                Set<String> updatedSorIds = updatedBasicSorList.stream()
                        .map(BasicSor::getSorId)
                        .collect(Collectors.toSet());

                // Then, iterate through existingBasicSorList and update accordingly
                List<BasicSor> finalList = existingBasicSorList.stream()
                        .map(existingBasicSor -> {
                            // Check if sorId is in updatedSorIds
                            if (!updatedSorIds.contains(existingBasicSor.getSorId())) {
                                // If not present in updatedSorIds, add existingBasicSor directly
                                return existingBasicSor;
                            } else {
                                // If present, find corresponding updated detail if available
                                Optional<BasicSor> updatedDetail = updatedBasicSorList.stream()
                                        .filter(newBasicSor -> newBasicSor.getSorId().equals(existingBasicSor.getSorId()))
                                        .findFirst();

                                // Return updatedDetail if found, otherwise existingBasicSor
                                return updatedDetail.orElse(existingBasicSor);
                            }
                        })
                        .toList();

                // Finally, update existingBasicSorList with the finalList
                existingBasicSorList.clear();
                existingBasicSorList.addAll(finalList);


            }

            return !hasDifferences;
        }


    }

    private static  BasicSor getBasicSor(BasicSor existingBasicSor, BasicSor newBasicSor) {
        List<BasicSorDetails> updatedDetails = new ArrayList<>(newBasicSor.getBasicSorDetails());
        BasicSor updatedBasicSor = new BasicSor();
        updatedBasicSor.setBasicSorDetails(updatedDetails);
        updatedBasicSor.setSorType(existingBasicSor.getSorType());
        updatedBasicSor.setId(existingBasicSor.getId());
        updatedBasicSor.setAdditionalDetails(newBasicSor.getAdditionalDetails());
        updatedBasicSor.setReferenceId(existingBasicSor.getReferenceId());
        updatedBasicSor.setSorId(existingBasicSor.getSorId());
        return updatedBasicSor;
    }

    // Helper method to compare basicSorDetails lists
    private static boolean areBasicSorDetailsEqual(List<BasicSorDetails> existingBasicSorDetailsList, List<BasicSorDetails> newBasicSorDetailsList) {
        // If sizes differ, they are not equal
        if (existingBasicSorDetailsList.size() != newBasicSorDetailsList.size()) {
            return false;
        }

        // Flag to determine if there are differences
        boolean hasDifferences = false;

        // Create a list to store updated details
        List<BasicSorDetails> updatedDetails = new ArrayList<>();

        // Compare each element in the lists
        for (int i = 0; i < existingBasicSorDetailsList.size(); i++) {
            ObjectMapper objectMapper = new ObjectMapper();
            BasicSorDetails detail1 = objectMapper.convertValue(existingBasicSorDetailsList.get(i), BasicSorDetails.class);
            BasicSorDetails detail2 = objectMapper.convertValue(newBasicSorDetailsList.get(i), BasicSorDetails.class);

            if (detail1.getType().equals(detail2.getType())) {
                if (!detail1.getAmount().equals(detail2.getAmount()) ||
                        (detail1.getQuantity() != null && detail2.getQuantity() != null &&
                                !detail1.getQuantity().equals(detail2.getQuantity()))) {
                    // If amounts differ OR quantities are both non-null and differ, update id and add to updated list
                    detail2.setId(detail1.getId());
                    updatedDetails.add(detail2);
                    hasDifferences = true;
                }
            }/* else {
                // If types differ, they are not equal
                return false;
            }*/
        }

        // If there were differences, update the new list with changes
        if (hasDifferences) {
            newBasicSorDetailsList.clear();
            newBasicSorDetailsList.addAll(updatedDetails);
        }

        // Return true if lists are equal, false otherwise
        return !hasDifferences;
    }


}
