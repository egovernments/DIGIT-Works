package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.*;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.kafka.ExpenseCalculatorProducer;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.IndividualEntry;
import org.egov.digit.expense.calculator.web.models.MusterRoll;
import org.egov.digit.expense.calculator.web.models.MusterRollResponse;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.JSON_PATH_FOR_TENANTS_VERIFICATION;

@Component
@Slf4j
public class SorMigrationUtil {

    private final ServiceRequestRepository restRepo;
    private final ExpenseCalculatorConfiguration configs;
    private final ObjectMapper mapper;
    private final MdmsUtils mdmsUtils;
    private final CommonUtil commonUtil;
    private final ExpenseCalculatorProducer producer;

    @Value("${expense.sor.migration.mapping}")
    private String sorMigrationMappingString;
    @Value("${state.level.tenant.id}")
    private String stateLevelTenantId;
    @Value("${muster.roll.update.topic}")
    private String musterRollUpdateTopic;
    @Value("${individual.host}")
    private String individualHost;
    @Value("${individual.context.path}")
    private String individualContextPath;
    @Value("${individual.update.topic}")
    private String individualUpdateTopic;


    public SorMigrationUtil(ServiceRequestRepository restRepo, ExpenseCalculatorConfiguration configs, ObjectMapper mapper, MdmsUtils mdmsUtils, CommonUtil commonUtil, ExpenseCalculatorProducer producer) {
        this.restRepo = restRepo;
        this.configs = configs;
        this.mapper = mapper;
        this.mdmsUtils = mdmsUtils;
        this.commonUtil = commonUtil;
        this.producer = producer;
    }

    public String migrateSor(RequestInfo requestInfo, String key) {
        log.info("Migrating sor for key {}", key);
        Map<String, String> sorMigrationMapping = null;
        try {
            sorMigrationMapping = mapper.readValue(sorMigrationMappingString, new TypeReference<Map<String, String>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

//        MdmsCriteriaReq mdmsCriteriaReq = mdmsUtils.getMDMSValidationRequest(requestInfoWrapper.getRequestInfo(), "pg");
        Object mdmsData = mdmsUtils.fetchMDMSForValidation(requestInfo, stateLevelTenantId);
        log.info("MDMS Response for tenantID:" + mdmsData.toString());
        List<Object> tenantRes = commonUtil.readJSONPathValue(mdmsData, JSON_PATH_FOR_TENANTS_VERIFICATION);
        for (Object tenantIdRes : tenantRes) {
            String tenantId = tenantIdRes.toString();
            log.info("Migrating sor for tenantId {}", tenantId);
            switch (key) {
                case "musterRoll":
                    migrateMuster(requestInfo, tenantId, sorMigrationMapping);
                    break;
                case "individual":
                    migrateIndividual(requestInfo, tenantId, sorMigrationMapping);
                    break;
                default:
                    throw new CustomException("INVALID_SOR_TYPE", "Invalid sor type");
            }

        }
        return key;
    }

    private void migrateMuster(RequestInfo requestInfo, String tenantId, Map<String, String> sorMapping) {
        MusterRollResponse musterRollResponse;
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        int offset = configs.getDefaultOffset();
        int limit = configs.getDefaultLimit();
        do {
            StringBuilder url = new StringBuilder(configs.getMusterRollHost()).append(configs.getMusterRollEndPoint())
                    .append("?tenantId=").append(tenantId).append("&offset=").append(offset).append("&limit=").append(limit);

            Object response = restRepo.fetchResult(url, requestInfoWrapper);
            musterRollResponse = mapper.convertValue(response, MusterRollResponse.class);
            if (musterRollResponse.getMusterRolls().size() == 0 && offset == 0) {
                log.error("No muster rolls found for tenantId {}", tenantId);
            }

            for (MusterRoll musterRoll : musterRollResponse.getMusterRolls()) {
                for (IndividualEntry individualEntry : musterRoll.getIndividualEntries()) {
                    Map<String, Object> additionalDetailsMap;
                    try {
                        String addDetails = mapper.writeValueAsString(individualEntry.getAdditionalDetails());
                        additionalDetailsMap = mapper.readValue(addDetails, new TypeReference<Map<String, Object>>() {
                        });
                    } catch (JsonProcessingException e) {
                        throw new CustomException("PARSING_ERROR", "error while parsing additionalDetails" + e);
                    }
                    if (sorMapping.containsKey(additionalDetailsMap.get("skillCode").toString())) {
                        additionalDetailsMap.put("skillCode", sorMapping.get(additionalDetailsMap.get("skillCode").toString()));
                    } else {
                        log.error("Skill code {} not found in sor mapping", additionalDetailsMap.get("skillCode"));
                    }
                    individualEntry.setAdditionalDetails(mapper.convertValue(additionalDetailsMap, Object.class));
                }
                musterRoll.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                musterRoll.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                producer.push(musterRollUpdateTopic, musterRoll);
            }
            offset = offset + limit;
        } while (musterRollResponse.getMusterRolls().size() > 0);
    }

    private void migrateIndividual(RequestInfo requestInfo, String tenantId, Map<String, String> sorMapping) {

        IndividualBulkResponse individualResponse;
        int offset = configs.getDefaultOffset();
        int limit = configs.getDefaultLimit();

        do {
            StringBuilder url = new StringBuilder(individualHost).append(individualContextPath)
                    .append("?tenantId=").append(tenantId).append("&offset=").append(offset).append("&limit=").append(limit);
            IndividualSearchRequest search = IndividualSearchRequest.builder().requestInfo(requestInfo).individual(IndividualSearch.builder().build()).build();
            Object individualObject = restRepo.fetchResult(url, search);
            individualResponse = mapper.convertValue(individualObject, IndividualBulkResponse.class);
            if (individualResponse.getIndividual().size() == 0 && offset == 0) {
                log.error("No individuals found for tenantId {}", tenantId);
            }
            for (Individual individual : individualResponse.getIndividual()) {
                for (Skill skill : individual.getSkills()) {
                    String labourCode = skill.getLevel() + "." + skill.getType();
                    if (sorMapping.containsKey(labourCode)) {
                        skill.setExperience(sorMapping.get(labourCode));
                    }
                }
                individual.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                individual.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                producer.push(individualUpdateTopic, individual);
            }
            offset = offset + limit;
        } while (individualResponse.getIndividual().size() > 0) ;

    }
}