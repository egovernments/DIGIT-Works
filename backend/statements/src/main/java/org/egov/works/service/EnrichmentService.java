package org.egov.works.service;

import com.fasterxml.jackson.databind.JsonNode;
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

            Set<String> worksSorId = new HashSet<>();
            Set<String> uniqueIdentifiers = new HashSet<>();
            Set<String> basicSorIds = new HashSet<>();
            List<String> sorsList= new ArrayList<>();
            Map<String, Map<String, String>> basicSorDescriptionMap = new HashMap<>();
            for (EstimateDetail estimateDetail : estimateDetailList) {

                uniqueIdentifiers.add(estimateDetail.getSorId());
               // if (estimateDetail.getCategory().equals(ESTIMATE_DETAIL_SOR_CATEGORY)) {
                    sorIdToEstimateDetailMap.computeIfAbsent(estimateDetail.getSorId(), k -> new ArrayList<>()).add(estimateDetail);
                    BigDecimal quantity = estimateDetail.getIsDeduction() ? estimateDetail.getQuantity().multiply(BigDecimal.valueOf(-1)) : estimateDetail.getQuantity();
                    sorIdToEstimateDetailQuantityMap.merge(estimateDetail.getSorId(), quantity, BigDecimal::add);
              //  }

            }
            List<Mdms> mdmsList = getSorsForSorIds(uniqueIdentifiers, statementCreateRequest.getRequestInfo(), configuration.getStateLevelTenantId());
            mapSorWithSorDetailsAndDescriptions(basicSorDescriptionMap, mdmsList, worksSorId);


            Map<String, SorComposition> sorIdCompositionMap = mdmsUtil.fetchSorComposition(requestInfo, worksSorId, statementRequest.getTenantId(),estimate.getAuditDetails().getCreatedTime());


            for (SorComposition sorComposition : sorIdCompositionMap.values()) {
                basicSorIds.addAll(sorComposition.getBasicSorDetails().stream().map(SorCompositionBasicSorDetail::getSorId).toList());
            }
            sorsList.addAll(basicSorIds);
            sorsList.addAll(uniqueIdentifiers);
            Map<String,Sor> sorDescriptionMap = mdmsUtil.fetchSorData(requestInfo,configuration.getStateLevelTenantId(),sorsList,true);
            Map<String, List<Rates>> sorRates = mdmsUtil.fetchBasicRates(requestInfo, estimate.getTenantId(), sorsList);
           statementPushRequest = createStatementPushRequest(estimate, sorIdToEstimateDetailMap, worksSorId, requestInfo,
                                                      sorIdCompositionMap, sorIdToEstimateDetailQuantityMap,basicSorDescriptionMap,sorRates,sorDescriptionMap);


        }else {
            throw new CustomException("INVALID_ESTIMATE_DETAILS","Estimate is null or estimate details is empty");
        }
        return statementPushRequest;
    }

    public StatementPushRequest enrichStatementPushRequestForUpdate(StatementCreateRequest statementCreateRequest,List<Statement> statementList){
        for(Statement statement :statementList){

        }

        return null;
    }

    private StatementPushRequest createStatementPushRequest(Estimate estimate, Map<String, List<EstimateDetail>> sorIdToEstimateDetailMap, Set<String> worksSorId, RequestInfo requestInfo,
                                                            Map<String, SorComposition> sorIdCompositionMap, Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap,
                                                            Map<String, Map<String, String>> basicSorDescriptionMap, Map<String, List<Rates>> basicSorRates, Map<String,Sor> sorDescriptionMap) {


        Statement statement = Statement.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(estimate.getTenantId())
                .targetId(estimate.getId())
                .statementType(Statement.StatementTypeEnum.ANALYSIS)
                .build();
        digit.models.coremodels.AuditDetails auditDetails = statementServiceUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), statement,true);
        statement.setAuditDetails(auditDetails);

        enrichSorDetailsAndBasicSorDetails(requestInfo,statement, estimate, worksSorId, sorIdCompositionMap, basicSorRates,
                sorIdToEstimateDetailMap, sorIdToEstimateDetailQuantityMap,basicSorDescriptionMap, sorDescriptionMap);
        StatementPushRequest statementPushRequest = StatementPushRequest.builder()
                .requestInfo(requestInfo)
                .statement(statement)
                .build();
        return statementPushRequest;

    }

    private void enrichSorDetailsAndBasicSorDetails(RequestInfo requestInfo, Statement statement, Estimate estimate, Set<String> worksSorId,
                                                    Map<String, SorComposition> sorIdCompositionMap, Map<String, List<Rates>> basicSorRates,Map<String, List<EstimateDetail>> sorIdToEstimateDetailMap,
                                                    Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap,Map<String, Map<String, String>> basicSorDescriptionMap,
                                                    Map<String,Sor> sorDescriptionMap) {

        List<SorDetail> sorDetails = new ArrayList<>();
        Long createdTime=estimate.getAuditDetails().getCreatedTime();

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
                computeLineItems(basicSor, basicSorId, basicSorQuantity, analysisQuantity, basicSorRates, sorIdToEstimateDetailQuantityMap, worksSor, basicSorDescriptionMap,createdTime);
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
                    .tenantId(estimate.getTenantId())
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

                computeBasicSorDetailsForNonWorksSorInEstimate(basicSorDescriptionMap,estimateDetail,sorIdToEstimateDetailQuantityMap,basicSorDetail,requestInfo,estimate.getTenantId(),createdTime);
                basicSorDetails.add(basicSorDetail);
                SorDetail sorDetail= SorDetail.builder()
                        .id(UUID.randomUUID().toString())
                        .sorId(estimateDetail.getSorId())
                        .statementId(statement.getId())
                        .tenantId(estimate.getTenantId())
                        .lineItems(new ArrayList<>())
                        .basicSorDetails(basicSorDetails)
                        .build();
                sorDetails.add(sorDetail);

            }

        }


        statement.setSorDetails(sorDetails);
        accumulateBasicSorDetails(statement);
    }

    public void accumulateBasicSorDetails(Statement statement) {
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



                // You can add additional fields to be accumulated if needed
            }
        }

        // Convert the map to a list of BasicSorDetails for the statement
        List<BasicSorDetails> cumulativeList = new ArrayList<>(cumulativeDataMap.values());
        statement.setBasicSorDetails(cumulativeList);
    }
    private void mapSorWithSorDetailsAndDescriptions(Map<String, Map<String, String>> basicSorDescriptionMap, List<Mdms> mdmsList, Set<String> worksSorId) {
        if(!mdmsList.isEmpty()){
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
        }else{
            throw new CustomException("SOR_DETAILS_NOT_FOUND","Sor Details not found in MDMS");
        }

    }

    private void computeLineItems(BasicSor basicSor, String basicSorId, BigDecimal basicSorQuantity,
                                  BigDecimal analysisQuantity, Map<String, List<Rates>> basicSorRates,
                                  Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap, String worksSor,
                                  Map<String, Map<String, String>> sorDetailsMap,Long estimateCreatedTime) {

        List<Rates> ratesList = basicSorRates.get(basicSorId);

        Rates rates  = commonUtil.getApplicatbleRate(ratesList, estimateCreatedTime);
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

  private void  computeBasicSorDetailsForNonWorksSorInEstimate(Map<String, Map<String, String>> basicSorDescriptionMap, EstimateDetail estimateDetail,
                                                               Map<String, BigDecimal> sorIdToEstimateDetailQuantityMap,BasicSorDetails basicSorDetail,RequestInfo requestInfo,String tenantId,Long createdTime){
        String sorId=estimateDetail.getSorId();
      BigDecimal quantityDefinedInEstimate = sorIdToEstimateDetailQuantityMap.get(sorId);
      Map<String, List<Rates>> basicSorRates = mdmsUtil.fetchRateForNonWorksSor(sorId,requestInfo,tenantId);
      //Rate rate = commonUtil.getApplicatbleRate(basicSorRates.get(sorId),createdTime);
      basicSorDetail.setQuantity(quantityDefinedInEstimate);
      basicSorDetail.setUom(basicSorDescriptionMap.get(sorId).get("uom"));
      basicSorDetail.setType(basicSorDescriptionMap.get(sorId).get("sorType"));
      basicSorDetail.setName(basicSorDescriptionMap.get(sorId).get("description"));

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
