package org.egov.works.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.util.EstimateUtil;
import org.egov.works.util.MdmsUtil;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static org.egov.works.config.ErrorConfiguration.ESTIMATE_RESPONSE_NULL_EMPTY_CODE;
import static org.egov.works.config.ErrorConfiguration.ESTIMATE_RESPONSE_NULL_EMPTY_MSG;
import static org.egov.works.config.ServiceConstants.*;

@Service
@Slf4j
public class EnrichmentService {

    @Autowired
    private EstimateUtil estimateUtil;


    @Autowired
    private MdmsUtil mdmsUtil;

    @Autowired
    StatementConfiguration configuration;

    private static final String OR_ADDITIONAL_FILTER = " || ";
    public static final String MDMS_SOR_MASTER_NAME = "SOR";
    private static final String SOR_FILTER_CODE = "@.id=='%s'";
    private static final String FILTER_START = "[?(";
    private static final String FILTER_END = ")]";

    public StatementPushRequest  enrichStatementPushRequest(StatementCreateRequest statementCreateRequest) {
        log.info("EnrichmentService::enrichStatementRequest");
        StatementPushRequest statementPushRequest= new StatementPushRequest();
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

            Set<String> worksSorId = new HashSet<>();
            Set<String> uniqueIdentifiers = new HashSet<>();
            for (EstimateDetail estimateDetail : estimateDetailList) {

                uniqueIdentifiers.add(estimateDetail.getSorId());
               // if (estimateDetail.getCategory().equals(ESTIMATE_DETAIL_SOR_CATEGORY)) {
                    sorIdToEstimateDetailMap.computeIfAbsent(estimateDetail.getSorId(), k -> new ArrayList<>()).add(estimateDetail);
                    BigDecimal quantity = estimateDetail.getIsDeduction() ? estimateDetail.getQuantity().multiply(BigDecimal.valueOf(-1)) : estimateDetail.getQuantity();
                    sorIdToEstimateDetailQuantityMap.merge(estimateDetail.getSorId(), quantity, BigDecimal::add);
              //  }

            }
            List<Mdms> mdmsList = getSorsForSorIds(uniqueIdentifiers, statementCreateRequest.getRequestInfo(), configuration.getStateLevelTenantId());
            Map<String, Map<String, String>> basicSorDescriptionMap = new HashMap<>();
            mapSorWithSorDetailsAndDescriptions(basicSorDescriptionMap, mdmsList, worksSorId);


            Map<String, SorComposition> sorIdCompositionMap = mdmsUtil.fetchSorComposition(requestInfo, worksSorId, statementRequest.getTenantId());

           statementPushRequest = createStatementPushRequest(estimate, sorIdToEstimateDetailMap, worksSorId, requestInfo,
                                                      sorIdCompositionMap, sorIdToEstimateDetailQuantityMap,basicSorDescriptionMap);


        }
        return statementPushRequest;
    }

    private StatementPushRequest createStatementPushRequest(Estimate estimate, Map<String, List<EstimateDetail>> sorIdToEstimateDetailMap, Set<String> worksSorId, RequestInfo requestInfo,
                                                            Map<String, SorComposition> sorIdCompositionMap, Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap,Map<String, Map<String, String>> basicSorDescriptionMap) {
        List<Statement> statementList = new ArrayList<>();
        Statement statement = Statement.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(estimate.getTenantId())
                .targetId(estimate.getId())
                .statementType(Statement.StatementTypeEnum.ANALYSIS)
                .build();
        Map<String, List<Rates>> basicSorRates = mdmsUtil.fetchBasicRates(requestInfo, statement.getTenantId(), sorIdCompositionMap);
        enrichSorDetailsAndBasicSorDetails(requestInfo,statement, estimate, worksSorId, sorIdCompositionMap, basicSorRates,sorIdToEstimateDetailMap, sorIdToEstimateDetailQuantityMap,basicSorDescriptionMap);
        statementList.add(statement);
        StatementPushRequest statementPushRequest = StatementPushRequest.builder()
                .requestInfo(requestInfo)
                .statement(statementList)
                .build();
        return statementPushRequest;

    }

    private void enrichSorDetailsAndBasicSorDetails(RequestInfo requestInfo, Statement statement, Estimate estimate, Set<String> worksSorId,
                                                    Map<String, SorComposition> sorIdCompositionMap, Map<String, List<Rates>> basicSorRates,Map<String, List<EstimateDetail>> sorIdToEstimateDetailMap,
                                                    Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap,Map<String, Map<String, String>> basicSorDescriptionMap) {

        List<SorDetail> sorDetails = new ArrayList<>();

        for (String worksSor : worksSorId) {
            List<BasicSor> lineItems = new ArrayList<>();
            SorComposition sorComposition = sorIdCompositionMap.get(worksSor);
            BigDecimal analysisQuantity = sorComposition.getQuantity();
            List<SorCompositionBasicSorDetail> sorCompositionBasicSorDetails = sorComposition.getBasicSorDetails();

            // Track cumulative amounts for each sorType
            Map<String, BigDecimal> cumulativeAmounts = new HashMap<>();
            Map<String, BigDecimal> cumulativeQuantities = new HashMap<>();

            for (SorCompositionBasicSorDetail sorCompositionBasicSorDetail : sorCompositionBasicSorDetails) {
                BasicSor basicSor = new BasicSor();
                String basicSorId = sorCompositionBasicSorDetail.getSorId();
               List<Mdms> mdmsList = getSorsForSorIds(Set.of(basicSorId), requestInfo, configuration.getStateLevelTenantId());
                mapSorWithSorDetailsAndDescriptions(basicSorDescriptionMap, mdmsList, worksSorId);
                BigDecimal basicSorQuantity = sorCompositionBasicSorDetail.getQuantity();
                computeLineItems(basicSor, basicSorId, basicSorQuantity, analysisQuantity, basicSorRates, sorIdToEstimateDetailQuantityMap, worksSor, basicSorDescriptionMap);
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
                    .sorId(worksSor)
                    //.lineItems(lineItems)
                    .basicSorDetails(basicSorDetails)
                    .statementId(statement.getId())
                    .build();

            lineItems.forEach(basicSor -> {
                basicSor.setReferenceId(sorDetail.getId());
            });
            sorDetail.setLineItems(lineItems);

            sorDetails.add(sorDetail);
        }
        for(EstimateDetail estimateDetail: estimate.getEstimateDetails()){
            if(!worksSorId.contains(estimateDetail.getSorId()) && estimateDetail.getCategory().equals(ESTIMATE_DETAIL_SOR_CATEGORY) ){
                List<BasicSorDetails> basicSorDetails = new ArrayList<>();
                BasicSorDetails basicSorDetail= new BasicSorDetails();

                computeBasicSorDetailsForNonWorksSorInEstimate(basicSorDescriptionMap,estimateDetail.getSorId(),sorIdToEstimateDetailQuantityMap,basicSorDetail);
                basicSorDetails.add(basicSorDetail);
                SorDetail sorDetail= SorDetail.builder()
                        .id(UUID.randomUUID().toString())
                        .sorId(estimateDetail.getSorId())
                        .statementId(statement.getId())
                        .basicSorDetails(basicSorDetails)
                        .build();
                sorDetails.add(sorDetail);

            }

        }


        statement.setSorDetails(sorDetails);
    }

    private void mapSorWithSorDetailsAndDescriptions(Map<String, Map<String, String>> basicSorDescriptionMap, List<Mdms> mdmsList, Set<String> worksSorId) {
        for (Mdms mdms : mdmsList) {
            JsonNode data = mdms.getData();
            String sorType = data.get(SOR_TYPE_JSON_KEY).asText();
            String sorCode = data.get(SOR_CODE_JSON_KEY).asText();
            String uom = data.get("uom").asText();
            String description = data.get("description").asText();
            String quantity = data.get("quantity").asText();
            Map<String, String> internalSorDescMap = new HashMap<>();
            internalSorDescMap.put("sorType", sorType);
            internalSorDescMap.put("uom", uom);
            internalSorDescMap.put("description", description);
            internalSorDescMap.put("quantity", quantity);
            if (sorType.equals(configuration.getWorksSorType())) {
                worksSorId.add(sorCode);
            }

            basicSorDescriptionMap.put(sorCode, internalSorDescMap);
        }
    }

    private void computeLineItems(BasicSor basicSor, String basicSorId, BigDecimal basicSorQuantity,
                                  BigDecimal analysisQuantity, Map<String, List<Rates>> basicSorRates,
                                  Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap, String worksSor, Map<String, Map<String, String>> sorDetailsMap) {

        List<Rates> ratesList = basicSorRates.get(basicSorId);
        Rates rates = ratesList.get(0);
        BigDecimal basicRate = rates.getRate();
        List<BasicSorDetails> amountDetails = new ArrayList<>();

        BigDecimal quantityDefinedInEstimate = sorIdToEstimateDetailQuantityMap.get(worksSor);
        BigDecimal quantity = (basicSorQuantity.divide(analysisQuantity)).multiply(quantityDefinedInEstimate);
        BigDecimal amount = quantity.multiply(basicRate);
        BasicSorDetails basicSorDetails = BasicSorDetails.builder()
                .id(UUID.randomUUID().toString())
                .uom(sorDetailsMap.get(basicSorId).get("uom"))
                .type(sorDetailsMap.get(basicSorId).get("sorType"))
                .rate(basicRate)
                .quantity(quantity)
                .name(sorDetailsMap.get(basicSorId).get("description"))
                .amount(amount)
                .build();
        amountDetails.add(basicSorDetails);
        basicSor.setId(UUID.randomUUID().toString());
        basicSor.setSorId(basicSorId);
        basicSor.setSorType(sorDetailsMap.get(basicSorId).get("sorType"));
        basicSor.setAmountDetails(amountDetails);
    }

  private void  computeBasicSorDetailsForNonWorksSorInEstimate(Map<String, Map<String, String>> basicSorDescriptionMap, String sorId,
                                                               Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap,BasicSorDetails basicSorDetail){

      BigDecimal quantityDefinedInEstimate = sorIdToEstimateDetailQuantityMap.get(sorId);

      basicSorDetail.setQuantity(quantityDefinedInEstimate);
      basicSorDetail.setUom(basicSorDescriptionMap.get(sorId).get("uom"));
      basicSorDetail.setType(basicSorDescriptionMap.get(sorId).get("sorType"));

  }





    private List<Mdms> getSorsForSorIds(Set<String> sorIdSet, RequestInfo requestInfo, String tenantId) {
        int limit = 800;
        int offset = 0;
        List<Mdms> mdmsList = new ArrayList<>();
        StringBuilder sorStringBuilder = new StringBuilder();
        Iterator<String> sorIterator = sorIdSet.iterator();


        while (sorIterator.hasNext()) {
            String currentUnifqueIdentifier=sorIterator.next();
            MdmsCriteriaV2 mdmsCriteria = MdmsCriteriaV2.builder()
                    .tenantId(tenantId)
                    .schemaCode(WORKS_SOR_SCHEMA_CODE)
                    .offset(offset)
                    .limit(limit)
                    .build();
            if (sorIdSet != null && !sorIdSet.isEmpty()) {
                mdmsCriteria.setUniqueIdentifier(currentUnifqueIdentifier);
           }

            MdmsSearchCriteriaV2 mdmsSearchCriteria = MdmsSearchCriteriaV2.builder()
                    .requestInfo(requestInfo)
                    .mdmsCriteria(mdmsCriteria)
                    .build();
            MdmsResponseV2 mdmsResponse = mdmsUtil.fetchSorsFromMdms(mdmsSearchCriteria);
            if (mdmsResponse.getMdms() != null && !mdmsResponse.getMdms().isEmpty()) {
                for(Mdms mdms:mdmsResponse.getMdms()){
                    if (mdms.getUniqueIdentifier().equals(currentUnifqueIdentifier)) {

                        mdmsList.add(mdms);
                    }
                }


            } else {
                break;
            }
        }
        return mdmsList;
    }


}
