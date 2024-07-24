package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.*;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.kafka.ExpenseCalculatorProducer;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.IndividualEntry;
import org.egov.digit.expense.calculator.web.models.MusterRoll;
import org.egov.digit.expense.calculator.web.models.MusterRollRequest;
import org.egov.digit.expense.calculator.web.models.MusterRollResponse;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final JdbcTemplate jdbcTemplate;
    private static final String MIGRATE_SOR_SEARCH_QUERY = " SELECT * FROM eg_sor_migration WHERE id = {} ";

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
    @Value("${individual.update.context.path}")
    private String individualUpdateContextPath;
    @Value("${individual.update.topic}")
    private String individualUpdateTopic;
    @Value("${is.testing}")
    private boolean isTesting;


    public SorMigrationUtil(ServiceRequestRepository restRepo, ExpenseCalculatorConfiguration configs, ObjectMapper mapper, MdmsUtils mdmsUtils, CommonUtil commonUtil, ExpenseCalculatorProducer producer, JdbcTemplate jdbcTemplate) {
        this.restRepo = restRepo;
        this.configs = configs;
        this.mapper = mapper;
        this.mdmsUtils = mdmsUtils;
        this.commonUtil = commonUtil;
        this.producer = producer;
        this.jdbcTemplate = jdbcTemplate;
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
            //TODO remove this when running in prod
//            if (tenantId.equalsIgnoreCase("od"))
//                continue;
            if (isTesting && !tenantId.equalsIgnoreCase("od.testing"))
                continue;
            log.info("Migrating sor for tenantId {}", tenantId);
            switch (key) {
                case "musterRoll":
                    migrateMuster(requestInfo, tenantId, sorMigrationMapping);
                    break;
                case "individual":
                    migrateIndividual(requestInfo, tenantId, sorMigrationMapping);
                    break;
                case "individualAgain":
                    migrateIndividualAgain(requestInfo, tenantId, sorMigrationMapping);
                    break;
                case "encryptionIndividual":
                    migrateIndividualAgainAndAgain(requestInfo, tenantId);
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
                try {
//                    String isMigratedQuery = "SELECT is_migration_successful FROM eg_sor_migration WHERE id = '" + musterRoll.getId() + "'";
//                    List<Map<String, Object>> isMigrated = jdbcTemplate.queryForList(isMigratedQuery);
//                    if (!CollectionUtils.isEmpty(isMigrated) && isMigrated.get(0).get("is_migration_successful").equals(true)) {
//                        log.info("Sor already migrated for id :: " + musterRoll.getId());
//                        continue;
//                    }
                    for (IndividualEntry individualEntry : musterRoll.getIndividualEntries()) {
                        Map<String, Object> additionalDetailsMap;
                        try {
                            String addDetails = mapper.writeValueAsString(individualEntry.getAdditionalDetails());
                            additionalDetailsMap = mapper.readValue(addDetails, new TypeReference<Map<String, Object>>() {
                            });

                        if(additionalDetailsMap.get("skillCode")==null){
                            continue;
                        }
                            
                        if (sorMapping.containsKey(additionalDetailsMap.get("skillCode").toString())) {
                            additionalDetailsMap.put("skillCode", sorMapping.get(additionalDetailsMap.get("skillCode").toString()));
                        } else {
                            log.error("Skill code {} not found in sor mapping", additionalDetailsMap.get("skillCode"));
                            throw new CustomException("SKILL_CODE_NOT_FOUND", "Skill code not found in sor mapping");
                        }
                        } catch (Exception e) {
                            continue;
                        }
                        individualEntry.setAdditionalDetails(mapper.convertValue(additionalDetailsMap, Object.class));

                    }
                    musterRoll.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                    musterRoll.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                    MusterRollRequest musterRollRequest = MusterRollRequest.builder().musterRoll(musterRoll).requestInfo(requestInfo).build();
                    producer.push(musterRollUpdateTopic, musterRollRequest);
                    String insertQuery = "INSERT INTO eg_sor_migration(id, is_migration_successful) VALUES ('" + musterRoll.getId() + "', true);";
                    jdbcTemplate.update(insertQuery);
                    log.info("Migrated muster roll for muster roll id {}", musterRoll.getId());
                } catch (Exception e) {
                    log.error("Error migrating muster roll for id {}", musterRoll.getId(), e);
                    String insertQuery = "INSERT INTO eg_sor_migration(id, is_migration_successful) VALUES ('" + musterRoll.getId() + "', false);";
                    jdbcTemplate.update(insertQuery);
                }


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
               /* String MIGRATE_SEARCH_QUERY = "SELECT is_migration_successful FROM eg_sor_migration WHERE id = '" + individual.getId() + "'";
                List<Map<String, Object>> isMigrated = jdbcTemplate.queryForList(MIGRATE_SEARCH_QUERY);
                if (!CollectionUtils.isEmpty(isMigrated) && isMigrated.get(0).get("is_migration_successful").equals(true)) {
                    log.info("Individual already migrated for id :: " + individual.getId());
                    continue;
                }*/
                try {
                     for (Skill skill : individual.getSkills()) {
                        if (!skill.getLevel().startsWith("SOR_0") || !skill.getLevel().contains("SOR_0")){
                            String labourCode = skill.getLevel() + "." + skill.getType();
                            if (sorMapping.containsKey(labourCode)) {
                                skill.setType(sorMapping.get(labourCode));
                                skill.setLevel(sorMapping.get(labourCode));
                            } else {
                                log.error("Labour code {} not found in sor mapping", labourCode);
                               // throw new CustomException("SKILL_CODE_NOT_FOUND", "Labour code not found in sor mapping");
                            }
                        }else{
                            log.info("Already Migrated");
                        }

                    }
                    individual.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                    individual.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                    List<Individual> individuals = Collections.singletonList(individual);
                    producer.push(individualUpdateTopic, individuals);
                    String insertQuery = "INSERT INTO eg_sor_migration(id, is_migration_successful) VALUES ('" + individual.getId() + "', true);";
                    jdbcTemplate.update(insertQuery);
                    log.info("Migrated individual for individual id {}", individual.getId());
                } catch (Exception e) {
                    log.error("Error migrating individual for id {}", individual.getId(), e);
                    String insertQuery = "INSERT INTO eg_sor_migration(id, is_migration_successful) VALUES ('" + individual.getId() + "', false);";
                    jdbcTemplate.update(insertQuery);
                }

            }
            offset = offset + limit;
        } while (individualResponse.getIndividual().size() > 0) ;

    }

    private void migrateIndividualAgain(RequestInfo requestInfo, String tenantId, Map<String, String> sorMapping) {

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
//                String MIGRATE_SEARCH_QUERY = "SELECT is_migration_successful FROM eg_sor_migration WHERE id = '" + individual.getId() + "'";
//                List<Map<String, Object>> isMigrated = jdbcTemplate.queryForList(MIGRATE_SEARCH_QUERY);
//                if (!CollectionUtils.isEmpty(isMigrated) && isMigrated.get(0).get("is_migration_successful").equals(true)) {
//                    log.info("Individual already migrated for id :: " + individual.getId());
//                    continue;
//                }
                try {
                    for (Skill skill : individual.getSkills()) {
//                        String labourCode = skill.getLevel() + "." + skill.getType();
                        if (skill.getType().equalsIgnoreCase("SOR")) {
                            skill.setType(skill.getLevel());
//                            skill.setLevel(sorMapping.get(labourCode));
                        } else {
                            log.error("Unable to update individual again");
                            //throw new CustomException("SKILL_CODE_NOT_FOUND", "Labour code not found in sor mapping");
                        }
                    }
                    individual.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                    individual.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                    List<Individual> individuals = Collections.singletonList(individual);
                    producer.push(individualUpdateTopic, individuals);
//                    String insertQuery = "INSERT INTO eg_sor_migration(id, is_migration_successful) VALUES ('" + individual.getId() + "', true);";
//                    jdbcTemplate.update(insertQuery);
                    log.info("Migrated individual for individual id {}", individual.getId());
                } catch (Exception e) {
                    log.error("Error migrating individual for id {}", individual.getId(), e);
//                    String insertQuery = "INSERT INTO eg_sor_migration(id, is_migration_successful) VALUES ('" + individual.getId() + "', false);";
//                    jdbcTemplate.update(insertQuery);
                }

            }
            offset = offset + limit;
        } while (individualResponse.getIndividual().size() > 0) ;

    }
    private void migrateIndividualAgainAndAgain(RequestInfo requestInfo, String tenantId) {
        IndividualBulkResponse individualResponse;
        int offset = configs.getDefaultOffset();
        int limit = configs.getDefaultLimit();
        StringBuilder updateUrl = new StringBuilder(individualHost).append(individualUpdateContextPath);
        do {
            StringBuilder searchUrl = new StringBuilder(individualHost).append(individualContextPath)
                    .append("?tenantId=").append(tenantId).append("&offset=").append(offset).append("&limit=").append(limit);
            IndividualSearchRequest search = IndividualSearchRequest.builder().requestInfo(requestInfo).individual(IndividualSearch.builder().build()).build();
            Object individualObject;
            individualResponse = IndividualBulkResponse.builder().individual(new ArrayList<>()).build();
            try {
                individualObject = restRepo.fetchResult(searchUrl, search);
            } catch (Exception e) {
                log.error("Error fetching individual for offset {}", offset);
                continue;
            }
            individualResponse = mapper.convertValue(individualObject, IndividualBulkResponse.class);
            if (individualResponse.getIndividual().size() == 0 && offset == 0) {
                log.error("No individuals found for tenantId {}", tenantId);
            }
            for (Individual individual : individualResponse.getIndividual()) {
                individual.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                individual.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                IndividualRequest individualRequest = IndividualRequest.builder().individual(individual).requestInfo(requestInfo).build();
                for (Identifier identifier : individualRequest.getIndividual().getIdentifiers()){
                    if (identifier.getIdentifierType().equalsIgnoreCase("AADHAAR")) {
                        identifier.setIdentifierId(RandomStringUtils.randomNumeric(12));
                    } else {
                        identifier.setIdentifierId(individual.getId());
                    }
                    identifier.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
                    identifier.getAuditDetails().setLastModifiedBy(requestInfo.getUserInfo().getUuid());
                }
                try {
                    Object response = restRepo.fetchResult(updateUrl, individualRequest);
                    log.info("Migrated individual for individual id {}", individual.getId());
                } catch (Exception e) {
                    log.error("Error migrating individual for id {}", individual.getId(), e);
                }
            }
            offset = offset + limit;
        } while (!individualResponse.getIndividual().isEmpty()) ;
    }
}
