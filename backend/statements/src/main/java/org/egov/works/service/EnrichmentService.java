package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
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

    public StatementPushRequest  enrichStatementPushRequest(StatementCreateRequest statementCreateRequest) {
        log.info("EnrichmentService::enrichStatementRequest");
        StatementPushRequest statementPushRequest;
        StatementRequest statementRequest = statementCreateRequest.getStatementRequest();
        RequestInfo requestInfo = statementCreateRequest.getRequestInfo();
        EstimateResponse estimateResponse = estimateUtil.getEstimate(statementCreateRequest.getStatementRequest(), statementCreateRequest.getRequestInfo());
        if (estimateResponse == null && estimateResponse.getEstimates().isEmpty()) {
            throw new CustomException(ESTIMATE_RESPONSE_NULL_EMPTY_CODE, ESTIMATE_RESPONSE_NULL_EMPTY_MSG);
        }

        Map<String, List<EstimateDetail>> sorIdToEstimateDetailMap = new HashMap<>();
        Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap = new HashMap<>();

        Estimate estimate = estimateResponse.getEstimates().get(0);
        if (null != estimate && !estimate.getEstimateDetails().isEmpty()) {
            List<EstimateDetail> estimateDetailList = estimate.getEstimateDetails().stream().
                    filter(estimateDetail -> estimateDetail.getCategory().equals(ESTIMATE_DETAIL_SOR_CATEGORY)).toList();

            Set<String> uniqueIdentifiers = new HashSet<>();
            Set<String> basicSorIds = new HashSet<>();
            for (EstimateDetail estimateDetail : estimateDetailList) {
                uniqueIdentifiers.add(estimateDetail.getSorId());
                sorIdToEstimateDetailMap.computeIfAbsent(estimateDetail.getSorId(), k -> new ArrayList<>()).add(estimateDetail);
                BigDecimal quantity = estimateDetail.getIsDeduction() ? estimateDetail.getQuantity().multiply(BigDecimal.valueOf(-1)) : estimateDetail.getQuantity();
                sorIdToEstimateDetailQuantityMap.merge(estimateDetail.getSorId(), quantity, BigDecimal::add);
            }
            Map<String, SorComposition> sorIdCompositionMap = mdmsUtil.fetchSorComposition(requestInfo, uniqueIdentifiers, statementRequest.getTenantId(),estimate.getAuditDetails().getCreatedTime());


            if( sorIdCompositionMap!=null && !sorIdCompositionMap.isEmpty() ){
                for (SorComposition sorComposition : sorIdCompositionMap.values()) {
                    basicSorIds.addAll(sorComposition.getBasicSorDetails().stream().map(SorCompositionBasicSorDetail::getSorId).toList());
                }
            }

            uniqueIdentifiers.addAll(basicSorIds);
            Map<String,Sor> sorDescriptionMap = mdmsUtil.fetchSorData(requestInfo,configuration.getStateLevelTenantId(),uniqueIdentifiers,true);
            Map<String, List<Rates>> sorRates = mdmsUtil.fetchBasicRates(requestInfo, estimate.getTenantId(), uniqueIdentifiers);
           statementPushRequest = createStatementPushRequest(estimate, sorIdToEstimateDetailMap, requestInfo,
                                                      sorIdCompositionMap, sorIdToEstimateDetailQuantityMap,sorRates,sorDescriptionMap,estimateDetailList);


        }else {
            throw new CustomException("INVALID_ESTIMATE_DETAILS","Estimate is null or estimate details is empty");
        }
        return statementPushRequest;
    }

    public StatementPushRequest enrichStatementPushRequestForUpdate(StatementCreateRequest statementCreateRequest,Statement statement){
        log.info("EnrichmentService::enrichStatementPushRequestForUpdate");
        StatementRequest statementRequest = statementCreateRequest.getStatementRequest();
        RequestInfo requestInfo = statementCreateRequest.getRequestInfo();
        EstimateResponse estimateResponse = estimateUtil.getEstimate(statementCreateRequest.getStatementRequest(), statementCreateRequest.getRequestInfo());
        if (estimateResponse == null && estimateResponse.getEstimates().isEmpty()) {
            throw new CustomException(ESTIMATE_RESPONSE_NULL_EMPTY_CODE, ESTIMATE_RESPONSE_NULL_EMPTY_MSG);
        }

        List<SorDetail> sorDetailList = statement.getSorDetails();

        Estimate estimate = estimateResponse.getEstimates().get(0);
        if (null != estimate && !estimate.getEstimateDetails().isEmpty()) {

            // Extract sorId from sorDetailList into a set
            Set<String> sorIdsInSorDetails = sorDetailList.stream()
                    .map(SorDetail::getSorId)
                    .collect(Collectors.toSet());

            // Filter and collect EstimateDetails that match category and sorId criteria
            List<EstimateDetail> filteredEstimateDetails = estimate.getEstimateDetails().stream()
                    .filter(ed -> ESTIMATE_DETAIL_SOR_CATEGORY.equals(ed.getCategory()) &&
                            !sorIdsInSorDetails.contains(ed.getSorId()))
                    .toList();


            digit.models.coremodels.AuditDetails auditDetails = statementServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), statement,false);
            statement.setAuditDetails(auditDetails);

            Map<String, List<EstimateDetail>> sorIdToEstimateDetailMap = new HashMap<>();
            Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap = new HashMap<>();
            Set<String> uniqueIdentifiers = new HashSet<>();
            Set<String> basicSorIds = new HashSet<>();
            for (EstimateDetail estimateDetail : filteredEstimateDetails) {
                uniqueIdentifiers.add(estimateDetail.getSorId());
                sorIdToEstimateDetailMap.computeIfAbsent(estimateDetail.getSorId(), k -> new ArrayList<>()).add(estimateDetail);
                BigDecimal quantity = estimateDetail.getIsDeduction() ? estimateDetail.getQuantity().multiply(BigDecimal.valueOf(-1)) : estimateDetail.getQuantity();
                sorIdToEstimateDetailQuantityMap.merge(estimateDetail.getSorId(), quantity, BigDecimal::add);
            }
            Map<String, SorComposition> sorIdCompositionMap = mdmsUtil.fetchSorComposition(requestInfo, uniqueIdentifiers, statementRequest.getTenantId(),estimate.getAuditDetails().getCreatedTime());


            if( sorIdCompositionMap!=null && !sorIdCompositionMap.isEmpty() ){
                for (SorComposition sorComposition : sorIdCompositionMap.values()) {
                    basicSorIds.addAll(sorComposition.getBasicSorDetails().stream().map(SorCompositionBasicSorDetail::getSorId).toList());
                }
            }

            uniqueIdentifiers.addAll(basicSorIds);
            Map<String,Sor> sorDescriptionMap = mdmsUtil.fetchSorData(requestInfo,configuration.getStateLevelTenantId(),uniqueIdentifiers,true);
            Map<String, List<Rates>> sorRates = mdmsUtil.fetchBasicRates(requestInfo, estimate.getTenantId(), uniqueIdentifiers);
            enrichSorDetailsAndBasicSorDetails(statement, estimate, sorIdCompositionMap, sorRates,
                    Boolean.FALSE, sorIdToEstimateDetailQuantityMap, sorDescriptionMap,filteredEstimateDetails);
            StatementPushRequest statementPushRequest = StatementPushRequest.builder()
                    .requestInfo(requestInfo)
                    .statement(statement)
                    .build();
            return statementPushRequest;
        }else {
            throw new CustomException("INVALID_ESTIMATE_DETAILS","Estimate is null or estimate details is empty");
        }



    }

    private StatementPushRequest createStatementPushRequest(Estimate estimate, Map<String, List<EstimateDetail>> sorIdToEstimateDetailMap, RequestInfo requestInfo,
                                                            Map<String, SorComposition> sorIdCompositionMap, Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap,
                                                            Map<String, List<Rates>> basicSorRates, Map<String,Sor> sorDescriptionMap,List<EstimateDetail> estimateDetailList) {

        log.info("EnrichmentService::createStatementPushRequest");

        Statement statement = Statement.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(estimate.getTenantId())
                .targetId(estimate.getId())
                .statementType(Statement.StatementTypeEnum.ANALYSIS)
                .build();
        digit.models.coremodels.AuditDetails auditDetails = statementServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), statement,true);
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

        for (EstimateDetail estimateDetail : estimateDetailList) {
            List<BasicSor> lineItems = new ArrayList<>();
                String sorId=estimateDetail.getSorId();
                SorComposition sorComposition = sorIdCompositionMap.get(sorId);
                if (sorComposition != null) {
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
                        for (BasicSorDetails detail : basicSor.getAmountDetails()) {
                            String type = detail.getType();
                            cumulativeAmounts.put(type, cumulativeAmounts.getOrDefault(type, BigDecimal.ZERO).add(detail.getAmount()));
                            cumulativeQuantities.put(type, cumulativeQuantities.getOrDefault(type, BigDecimal.ZERO).add(detail.getQuantity()));
                        }
                    }

                    // Create basicSorDetails from cumulative amounts
                    List<BasicSorDetails> basicSorDetails = new ArrayList<>();
                    for (Map.Entry<String, BigDecimal> entry : cumulativeAmounts.entrySet()) {
                        String type = entry.getKey();
                        BigDecimal amount = entry.getValue();
                        BasicSorDetails detail = BasicSorDetails.builder()
                                .id(UUID.randomUUID().toString())
                                .amount(amount)
                                .type(type)
                                .build();
                        basicSorDetails.add(detail);
                    }


                    // Create a SorDetail for each worksSor
                    SorDetail sorDetail = SorDetail.builder()
                            .id(UUID.randomUUID().toString())
                            .sorId(sorId)
                            .tenantId(estimate.getTenantId())
                            .basicSorDetails(basicSorDetails)
                            .statementId(statement.getId())
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

                    computeBasicSorDetailsForNonWorksSorInEstimate(sorId,sorIdToEstimateDetailQuantityMap,basicSorDetail,createdTime,sorDescriptionMap,basicSorRates);
                    basicSorDetails.add(basicSorDetail);
                    SorDetail sorDetail= SorDetail.builder()
                            .id(UUID.randomUUID().toString())
                            .sorId(sorId)
                            .statementId(statement.getId())
                            .tenantId(estimate.getTenantId())
                            .lineItems(new ArrayList<>())
                            .basicSorDetails(basicSorDetails)
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

 /*   public void accumulateBasicSorDetails(Statement statement) {
        if (statement.getSorDetails() == null || statement.getSorDetails().isEmpty()) {
            return;
        }

        // Map to accumulate data by type
        Map<String, BasicSorDetails> cumulativeDataMap = new HashMap<>();

        // Iterate over each sorDetails
        for (SorDetail sorDetail : statement.getSorDetails()) {
            if (sorDetail.getBasicSorDetails() == null) {
                continue;
            }

            for (BasicSorDetails basicSorDetail : sorDetail.getBasicSorDetails()) {
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
        }

        // Convert the map to a list of BasicSorDetails for the statement
        List<BasicSorDetails> cumulativeList = new ArrayList<>(cumulativeDataMap.values());
        statement.setBasicSorDetails(cumulativeList);
    }*/

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
        }

        // Convert the map to a list of BasicSorDetails for the statement
        List<BasicSorDetails> cumulativeList = new ArrayList<>(cumulativeDataMap.values());
        statement.setBasicSorDetails(cumulativeList);

        log.info("Accumulated BasicSorDetails:");

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

        /*Rates rates  = commonUtil.getApplicatbleRate(ratesList, estimateCreatedTime);
        BigDecimal basicRate = rates.getRate();*/
        BigDecimal basicRate = BigDecimal.ONE;
        List<BasicSorDetails> amountDetails = new ArrayList<>();

        BigDecimal quantityDefinedInEstimate = sorIdToEstimateDetailQuantityMap.get(worksSor);
        BigDecimal quantity = (basicSorQuantity.divide(analysisQuantity)).multiply(quantityDefinedInEstimate);
        BigDecimal amount = quantity.multiply(basicRate);
        Sor sor= sorDescriptionMap.get(basicSorId);
        BasicSorDetails basicSorDetails = BasicSorDetails.builder()
                .id(UUID.randomUUID().toString())
                .uom(sor.getUom())
                .type(sor.getSorType())
                .rate(basicRate)
                .quantity(quantity)
                .name(sor.getDescription())
                .amount(amount)
                .build();
        amountDetails.add(basicSorDetails);
        basicSor.setId(UUID.randomUUID().toString());
        basicSor.setSorId(basicSorId);
        basicSor.setSorType(sor.getSorType());
        basicSor.setAmountDetails(amountDetails);
    }

  private void  computeBasicSorDetailsForNonWorksSorInEstimate( String sorId,Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap,
                                                                BasicSorDetails basicSorDetail,Long createdTime,
                                                                Map<String,Sor> sorDescriptionMap,Map<String, List<Rates>> basicSorRates){
      BigDecimal quantityDefinedInEstimate = sorIdToEstimateDetailQuantityMap.get(sorId);
      Rates rate = commonUtil.getApplicatbleRate(basicSorRates.get(sorId),createdTime);
      Sor sor= sorDescriptionMap.get(sorId);
      basicSorDetail.setQuantity(quantityDefinedInEstimate);
      basicSorDetail.setUom(sor.getUom());
      basicSorDetail.setType(sor.getSorType());
      basicSorDetail.setName(sor.getDescription());
  }

}
