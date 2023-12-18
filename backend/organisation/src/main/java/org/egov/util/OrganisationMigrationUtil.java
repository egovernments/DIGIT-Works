package org.egov.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.kafka.OrganizationProducer;
import org.egov.repository.OrganisationRepository;
import org.egov.service.EncryptionService;
import org.egov.web.models.OrgRequest;
import org.egov.web.models.OrgSearchCriteria;
import org.egov.web.models.OrgSearchRequest;
import org.egov.web.models.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class OrganisationMigrationUtil {

    public static final String ORGANISATION_ENCRYPT_KEY = "Organisation";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private Configuration config;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private OrganizationProducer organizationProducer;
    // Look up data from eg_org table and encrypted the values of name, mobile number and emailid and push the data to kafka topic to be persisted.
    public void migrate2(RequestInfo requestInfo) {

        String orgIdQuery = "SELECT id, tenant_id FROM eg_org;";
        List<Map<String, Object>> orgIds = jdbcTemplate.queryForList(orgIdQuery);
        for (Map<String, Object> orgId : orgIds) {
            String id = (String) orgId.get("id");
            String tenantId = (String) orgId.get("tenant_id");

            //Search the encryption migration table for id
            String isMigratedQuery = "SELECT is_migrated FROM encryption_migration WHERE uuid = '" + id + "'";
            List<Map<String, Object>> isMigrated = jdbcTemplate.queryForList(isMigratedQuery);
            if (!CollectionUtils.isEmpty(isMigrated) && isMigrated.get(0).get("is_migrated").equals(true)) {
                log.info("Organisation already migrated for id :: " + id);
                continue;
            }

            // Search database for organisation with id
            OrgSearchRequest orgSearchRequest = OrgSearchRequest.builder().requestInfo(requestInfo)
                    .searchCriteria(OrgSearchCriteria.builder().id(Collections.singletonList(id)).tenantId(tenantId).build())
                    .build();
            List<Organisation> organisationsFromDB = organisationRepository.getOrganisations(orgSearchRequest);
            if (CollectionUtils.isEmpty(organisationsFromDB)) {
                log.info("Organisation not found for id :: " + id);
                continue;
            }
            if (CollectionUtils.isEmpty(organisationsFromDB.get(0).getContactDetails())) {
                log.info("Organisation contact details not found for id :: " + id);
                // Create row in isMigrated table with id and isMigrated = false
                String insertQuery = "INSERT INTO encryption_migration(uuid, is_migrated) VALUES ('" + id + "', false);";
                jdbcTemplate.update(insertQuery);
                continue;
            }
            OrgRequest orgRequest = OrgRequest.builder().requestInfo(requestInfo).organisations(Collections.singletonList(organisationsFromDB.get(0))).build();
            log.info("Calling for encryption");
            try {
                encryptionService.encryptDetails(orgRequest,ORGANISATION_ENCRYPT_KEY);
            } catch (Exception e) {
                log.error("Error while encrypting details", e);
                String insertQuery = "INSERT INTO encryption_migration(uuid, is_migrated) VALUES ('" + id + "', false);";
                jdbcTemplate.update(insertQuery);
                continue;
            }

            organizationProducer.push(config.getOrgKafkaUpdateTopic(), orgRequest);
            // Insert isMigrated table with id and isMigrated true
            String insertQuery = "INSERT INTO encryption_migration(uuid, is_migrated) VALUES ('" + id + "', true);";
            jdbcTemplate.update(insertQuery);
        }
    }
}

