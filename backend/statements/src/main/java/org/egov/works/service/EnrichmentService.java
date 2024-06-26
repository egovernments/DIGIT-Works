package org.egov.works.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.util.CommonUtil;
import org.egov.works.util.EstimateUtil;
import org.egov.works.util.MdmsUtil;
import org.egov.works.util.StatementServiceUtil;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.config.ErrorConfiguration.ESTIMATE_RESPONSE_NULL_EMPTY_CODE;
import static org.egov.works.config.ErrorConfiguration.ESTIMATE_RESPONSE_NULL_EMPTY_MSG;
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

    public StatementPushRequest enrichStatementPushRequest(StatementCreateRequest statementCreateRequest) {
        log.info("EnrichmentService::enrichStatementRequest");
        StatementPushRequest statementPushRequest;
        StatementRequest statementRequest = statementCreateRequest.getStatementRequest();
        RequestInfo requestInfo = statementCreateRequest.getRequestInfo();
        EstimateResponse estimateResponse = estimateUtil.getEstimate(statementRequest.getId(), statementRequest.getTenantId(),
                Statement.StatementTypeEnum.ANALYSIS.toString(), statementCreateRequest.getRequestInfo());
        if (estimateResponse == null || estimateResponse.getEstimates().isEmpty()) {
            throw new CustomException(ESTIMATE_RESPONSE_NULL_EMPTY_CODE, ESTIMATE_RESPONSE_NULL_EMPTY_MSG);
        }

        Map<String, List<EstimateDetail>> sorIdToEstimateDetailMap = new HashMap<>();
        Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap = new HashMap<>();

        Estimate estimate = estimateResponse.getEstimates().get(0);
        if (null != estimate && !estimate.getEstimateDetails().isEmpty()) {
            List<EstimateDetail> filteredActiveEstimateDetails = estimate.getEstimateDetails().stream()
                    .filter(ed -> ESTIMATE_DETAIL_SOR_CATEGORY.equals(ed.getCategory()) &&
                            ed.isActive())
                    .toList();

            Set<String> uniqueIdentifiers = new HashSet<>();
            Set<String> basicSorIds = new HashSet<>();
            for (EstimateDetail estimateDetail : filteredActiveEstimateDetails) {
                uniqueIdentifiers.add(estimateDetail.getSorId());
                sorIdToEstimateDetailMap.computeIfAbsent(estimateDetail.getSorId(), k -> new ArrayList<>()).add(estimateDetail);
                BigDecimal quantity = estimateDetail.getIsDeduction() ? estimateDetail.getQuantity().multiply(BigDecimal.valueOf(-1)) : estimateDetail.getQuantity();
                sorIdToEstimateDetailQuantityMap.merge(estimateDetail.getSorId(), quantity, BigDecimal::add);
            }
            Map<String, SorComposition> sorIdCompositionMap = mdmsUtil.fetchSorComposition(requestInfo, uniqueIdentifiers, statementRequest.getTenantId(), estimate.getAuditDetails().getCreatedTime());


            if (sorIdCompositionMap != null && !sorIdCompositionMap.isEmpty()) {
                for (SorComposition sorComposition : sorIdCompositionMap.values()) {
                    basicSorIds.addAll(sorComposition.getBasicSorDetails().stream().map(SorCompositionBasicSorDetail::getSorId).toList());
                }
            }

            uniqueIdentifiers.addAll(basicSorIds);
            Map<String, Sor> sorDescriptionMap = mdmsUtil.fetchSorData(requestInfo, configuration.getStateLevelTenantId(), new ArrayList<>(uniqueIdentifiers), true);
            Map<String, List<Rates>> sorRates = mdmsUtil.fetchBasicRates(requestInfo, estimate.getTenantId(), new ArrayList<>(uniqueIdentifiers));
            statementPushRequest = createStatementPushRequest(estimate, sorIdToEstimateDetailMap, requestInfo,
                    sorIdCompositionMap, sorIdToEstimateDetailQuantityMap, sorRates, sorDescriptionMap, filteredActiveEstimateDetails);


        } else {
            throw new CustomException("INVALID_ESTIMATE_DETAILS", "Estimate details is not present");
        }
        return statementPushRequest;
    }


    /**
     * @param statementCreateRequest
     * @param existingStatement
     * @return
     */
    public StatementPushRequest enrichStatementPushRequestForUpdate(StatementCreateRequest statementCreateRequest, Statement existingStatement) {
        log.info("EnrichmentService::enrichStatementPushRequestForUpdate");

        StatementPushRequest updatedStatementPushRequest = enrichStatementPushRequest(statementCreateRequest);

        // Map to hold existing SorDetails by sorId
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
                if (!areBasicSorDetailsEqual(existingSorDetail.getBasicSorDetails(), newSorDetail.getBasicSorDetails())) {
                    existingSorDetail.setBasicSorDetails(new ArrayList<>(newSorDetail.getBasicSorDetails()));
                }

                // Update line items if necessary
                List<BasicSor> existingBasicSorList = existingSorDetail.getLineItems();
                List<BasicSor> newBasicSorList = newSorDetail.getLineItems();
                if (newBasicSorList!=null &&!areBasicSorDetailsOfLineItemsEqual(existingBasicSorList, newBasicSorList)) {
                    existingSorDetail.setLineItems(new ArrayList<>(newBasicSorList));
                }
            }
        }
        // Track cumulative amounts for each sorType
        Map<String, BigDecimal> cumulativeAmounts = new HashMap<>();
        Map<String, BigDecimal> cumulativeQuantities = new HashMap<>();


        // Mark sorDetails in the existing statement as inactive if not present in the new statement
        for (String sorId : existingStatementSorDetailsMap.keySet()) {
            if (!newStatementSorIds.contains(sorId)) {
                SorDetail existingSorDetail = existingStatementSorDetailsMap.get(sorId);
                existingSorDetail.setIsActive(Boolean.FALSE);
            }
            ObjectMapper objectMapper= new ObjectMapper();
            List<BasicSorDetails> basicSorDetailsList = objectMapper.convertValue(
                    existingStatementSorDetailsMap.get(sorId).getBasicSorDetails(),
                    new TypeReference<List<BasicSorDetails>>() {}
            );
            for (BasicSorDetails detail :basicSorDetailsList) {
                String type = detail.getType();
                cumulativeAmounts.put(type, cumulativeAmounts.getOrDefault(type, BigDecimal.ZERO).add(detail.getAmount()));
                //cumulativeQuantities.put(type, cumulativeQuantities.getOrDefault(type, BigDecimal.ZERO).add(detail.getQuantity()));
            }
            // Update cumulative amounts and quantities


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
            existingStatementSorDetailsMap.get(sorId).setBasicSorDetails(basicSorDetails);

        }



        // Convert the map back to a list and update the statement
        List<SorDetail> updatedSorDetailsList = new ArrayList<>(existingStatementSorDetailsMap.values());
        existingStatement.setSorDetails(updatedSorDetailsList);

        // Set audit details
        AuditDetails auditDetails = statementServiceUtil.getAuditDetails(statementCreateRequest.getRequestInfo().getUserInfo().getUuid(), existingStatement, false);
        existingStatement.setAuditDetails(auditDetails);

        //Cummulative BasicSorDetails on Parent Level
        accumulateBasicSorDetails(existingStatement);

        // Update the StatementPushRequest with the updated Statement object
        updatedStatementPushRequest.setStatement(existingStatement);

        return updatedStatementPushRequest;
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
        }else{
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
                                                            Map<String, List<Rates>> basicSorRates, Map<String,Sor> sorDescriptionMap,List<EstimateDetail> estimateDetailList) {

        log.info("EnrichmentService::createStatementPushRequest");

        Map<String, Object> additionalDetailsMap= new HashMap<>();
        additionalDetailsMap.put("estimateNumber",estimate.getEstimateNumber());
        additionalDetailsMap.put("projectId",estimate.getProjectId());

        Statement statement = Statement.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(estimate.getTenantId())
                .targetId(estimate.getId())
                .statementType(Statement.StatementTypeEnum.ANALYSIS)
                .additionalDetails(additionalDetailsMap)
                .build();
        AuditDetails auditDetails = statementServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), statement,true);
        statement.setAuditDetails(auditDetails);

        enrichSorDetailsAndBasicSorDetails(statement, estimate, sorIdCompositionMap, basicSorRates,
                Boolean.TRUE, sorIdToEstimateDetailQuantityMap, sorDescriptionMap,estimateDetailList);
        StatementPushRequest statementPushRequest = StatementPushRequest.builder()
                .requestInfo(requestInfo)
                .statement(statement)
                .build();
        return statementPushRequest;

    }

    private void enrichSorDetailsAndBasicSorDetails(Statement statement, Estimate estimate, Map<String, SorComposition> sorIdCompositionMap,
                                                    Map<String, List<Rates>> basicSorRates, Boolean isCreate,
                                                    Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap,  Map<String,Sor> sorDescriptionMap,List<EstimateDetail> estimateDetailList) {

        List<SorDetail> sorDetails = new ArrayList<>();
        Long createdTime=estimate.getAuditDetails().getCreatedTime();
        // Extract sorId from EstimateDetail into a set
        Set<String> sorIdsInEstimateDetails = estimateDetailList.stream()
                .map(EstimateDetail::getSorId)
                .collect(Collectors.toSet());

        for (String sorId : sorIdsInEstimateDetails) {
            List<BasicSor> lineItems = new ArrayList<>();
            SorComposition sorComposition = sorIdCompositionMap.get(sorId);
            Map<String,Object> additionalDetailsMap= new HashMap<>();
            Sor sor= sorDescriptionMap.get(sorId);
            additionalDetailsMap.put("sorDetails", sor);
            if (sor.getSorType().equals(configuration.getWorksSorType()))  {
                if(sorComposition == null)
                    throw new CustomException("COMPOSITION_NOT_FOUND","Sor Composition not present for sorId:"+ sor.getId());
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

                List<Rates> ratesList = basicSorRates.get(sorId);
                Map<String,Object> basicSoradditionalDetailsMap= new HashMap<>();
                Sor sorDescription = sorDescriptionMap.get(sorId);
                Rates rates  = commonUtil.getApplicatbleRate(ratesList, createdTime);
                basicSoradditionalDetailsMap.put("sorDetails",sorDescription);
                basicSoradditionalDetailsMap.put("rateDetails",rates);

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
                if(!isCreate){
                    sorDetails.addAll(statement.getSorDetails());
                }
                sorDetails.add(sorDetail);
            }else{
                List<BasicSorDetails> basicSorDetails = new ArrayList<>();
                BasicSorDetails basicSorDetail= new BasicSorDetails();
                computeBasicSorDetailsForNonWorksSorInEstimate(sorId,sorIdToEstimateDetailQuantityMap,basicSorDetail,createdTime,sorDescriptionMap,basicSorRates,additionalDetailsMap);

                basicSorDetails.add(basicSorDetail);
                SorDetail sorDetail= SorDetail.builder()
                        .id(UUID.randomUUID().toString())
                        .sorId(sorId)
                        .statementId(statement.getId())
                        .tenantId(estimate.getTenantId())
                        .isActive(Boolean.TRUE)
                        .basicSorDetails(basicSorDetails)
                        .additionalDetails(additionalDetailsMap)
                        .build();
                if(!isCreate){
                    sorDetails.addAll(statement.getSorDetails());
                }
                sorDetails.add(sorDetail);
            }

        }

        statement.setSorDetails(sorDetails);
        accumulateBasicSorDetails(statement);
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
                                  BigDecimal analysisQuantity, Map<String, List<Rates>> basicSorRates,
                                  Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap, String worksSor,
                                  Long estimateCreatedTime,Map<String,Sor> sorDescriptionMap) {

        List<Rates> ratesList = basicSorRates.get(basicSorId);
        Map<String,Object> additionalDetailsMap= new HashMap<>();

        Rates rates  = commonUtil.getApplicatbleRate(ratesList, estimateCreatedTime);
        BigDecimal basicRate = rates.getRate();
        /*BigDecimal basicRate = BigDecimal.ONE;*/
        List<BasicSorDetails> basicSorDetailsList = new ArrayList<>();
        Sor sor= sorDescriptionMap.get(basicSorId);
        BigDecimal quantityDefinedInEstimate = sorIdToEstimateDetailQuantityMap.get(worksSor);

        BigDecimal quantity = (basicSorQuantity.divide(analysisQuantity, 4, RoundingMode.HALF_UP))
                .multiply(quantityDefinedInEstimate).divide(sor.getQuantity(),4,RoundingMode.HALF_UP);
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
        additionalDetailsMap.put("sorDetails",sor);
        additionalDetailsMap.put("rateDetails",rates);

        basicSorDetailsList.add(basicSorDetails);
        basicSor.setId(UUID.randomUUID().toString());
        basicSor.setSorId(basicSorId);
        basicSor.setSorType(sor.getSorType());
        basicSor.setBasicSorDetails(basicSorDetailsList);
        basicSor.setAdditionalDetails(additionalDetailsMap);


    }

  private void  computeBasicSorDetailsForNonWorksSorInEstimate( String sorId,Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap,
                                                                BasicSorDetails basicSorDetail,Long createdTime,
                                                                Map<String,Sor> sorDescriptionMap,Map<String, List<Rates>> basicSorRates,Map<String,Object> additionalDetailsMap){
      log.info("EnrichmentSerivce :: computeBasicSorDetailsForNonWorksSorInEstimate");
      BigDecimal quantityDefinedInEstimate = sorIdToEstimateDetailQuantityMap.get(sorId);
      Rates rate = commonUtil.getApplicatbleRate(basicSorRates.get(sorId),createdTime);
      BigDecimal basicRate = rate.getRate();
      Sor sor= sorDescriptionMap.get(sorId);
      additionalDetailsMap.put("rateDetails", rate);
      BigDecimal amount = quantityDefinedInEstimate.multiply(basicRate).divide(sor.getQuantity());

      basicSorDetail.setQuantity(quantityDefinedInEstimate);
      basicSorDetail.setAmount(amount);
      basicSorDetail.setType(sor.getSorType());
      basicSorDetail.setId(UUID.randomUUID().toString());
  }


    private static boolean areBasicSorDetailsOfLineItemsEqual(List<BasicSor> existingBasicSorList, List<BasicSor> newBasicSorList) {
        // Compare size
        if (existingBasicSorList.size() != newBasicSorList.size()) {
            return false;
        }

        boolean hasDifferences = false;

        // Compare each element
        for (int i = 0; i < existingBasicSorList.size(); i++) {
            BasicSor existingBasicSor = existingBasicSorList.get(i);
            BasicSor newBasicSor = newBasicSorList.get(i);

            if (existingBasicSor.getSorId().equals(newBasicSor.getSorId())) {
                if (!areBasicSorDetailsEqual(existingBasicSor.getBasicSorDetails(), newBasicSor.getBasicSorDetails())) {
                    // Update the basicSorDetails of existingBasicSor
                    existingBasicSor.setBasicSorDetails(newBasicSor.getBasicSorDetails());
                    hasDifferences = true;
                }
            }
        }

        return !hasDifferences;
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
            ObjectMapper objectMapper= new ObjectMapper();
            BasicSorDetails detail1 = objectMapper.convertValue(existingBasicSorDetailsList.get(i), BasicSorDetails.class);
            BasicSorDetails detail2 = objectMapper.convertValue(newBasicSorDetailsList.get(i),BasicSorDetails.class);

            if (detail1.getType().equals(detail2.getType())) {
                if (!detail1.getAmount().equals(detail2.getAmount())) {
                    // If amounts  differ, update the id and add to updated list
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
