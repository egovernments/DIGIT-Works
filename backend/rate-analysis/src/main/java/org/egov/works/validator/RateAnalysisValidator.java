package org.egov.works.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.util.MdmsUtil;
import org.egov.works.web.models.AnalysisRequest;
import org.egov.works.web.models.Rates;
import org.egov.works.web.models.SorComposition;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static org.egov.works.config.ErrorConfiguration.TENANT_ID_NOT_FOUND_CODE;
import static org.egov.works.config.ErrorConfiguration.TENANT_ID_NOT_FOUND_MSG;
import static org.egov.works.config.ServiceConstants.MDMS_TENANTS_MASTER_NAME;
import static org.egov.works.config.ServiceConstants.MDMS_TENANT_MODULE_NAME;

@Component
@Slf4j
public class RateAnalysisValidator {

    private final ObjectMapper mapper;
    private final MdmsUtil mdmsUtil;

    public RateAnalysisValidator(ObjectMapper mapper, MdmsUtil mdmsUtil) {
        this.mapper = mapper;
        this.mdmsUtil = mdmsUtil;
    }

    public void validateTenantId( String tenantId, RequestInfo requestInfo) {
        log.info("StatementValidator::validateTenantId");
        Set<String> validTenantSet = new HashSet<>();
        List<String> masterList = Collections.singletonList(MDMS_TENANTS_MASTER_NAME);
        Map<String, Map<String, JSONArray>> response = mdmsUtil.fetchMdmsData(requestInfo, tenantId, MDMS_TENANT_MODULE_NAME, masterList, null);
        String node = response.get(MDMS_TENANT_MODULE_NAME).get(MDMS_TENANTS_MASTER_NAME).toString();
        try {
            JsonNode currNode = mapper.readTree(node);
            for (JsonNode tenantNode : currNode) {
                // Assuming each item in the array has a "code" field
                String tenantIdFromMdms = tenantNode.get("code").asText();
                validTenantSet.add(tenantIdFromMdms);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        if (!validTenantSet.contains(tenantId)) {
            throw new CustomException(TENANT_ID_NOT_FOUND_CODE, tenantId + TENANT_ID_NOT_FOUND_MSG);
        }
    }

    public void validateRevisionOfRates(AnalysisRequest analysisRequest, Map<String, SorComposition> sorIdCompositionMap,
                                        Map<String, List<Rates>> basicRatesMap, Map<String, JsonNode> sorMap) {
        Map<String, String> errorMap = new HashMap<>();
//        for (String sorId : sorMap.keySet()) {
//            if (sorMap.get(sorId).get("sorType").asText().equals("W") ) {
//                errorMap.put("BASIC_SOR_ONLY", sorId + " is not basic Sor");
//            }
//        }

        if (!CollectionUtils.isEmpty(errorMap))
            throw new CustomException(errorMap);

    }
    public void validateNewRates(Map<String, Rates> oldRatesMap, List<Rates> newRates) {
        Map<String, String> errorMap = new HashMap<>();
        for (Rates rates : newRates) {
            if (!oldRatesMap.containsKey(rates.getSorId()) || oldRatesMap.get(rates.getSorId()) == null) {
                log.info("Previous rates not found for sorId " + rates.getSorId());
            } else {
                Rates oldRate = oldRatesMap.get(rates.getSorId());
                if (oldRate.getValidFrom().compareTo(rates.getValidFrom()) > 0) {
                    log.error("Effective from date cannot be less than previous effective from date");
                    newRates.remove(rates);
                    errorMap.put("EFFECTIVE_FROM_ERROR", "Effective from date cannot be less than previous effective from date for sorId :: " + rates.getSorId());
                }
                if (oldRate.getRate().equals(rates.getRate())) {
                    log.error("Previous rate same as new rate");
                    newRates.remove(rates);
                    errorMap.put("RATE_SAME", "Previous rate same as new rate for sorId :: " + rates.getSorId());
                }
            }
        }
        if (!CollectionUtils.isEmpty(errorMap))
            throw new CustomException(errorMap);
    }



}
