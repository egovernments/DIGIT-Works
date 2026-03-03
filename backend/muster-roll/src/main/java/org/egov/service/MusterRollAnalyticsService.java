package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.Individual;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.util.MdmsUtil;
import org.egov.web.models.ElasticsearchQueryConfig;
import org.egov.web.models.IndividualEntry;
import org.egov.web.models.MusterRoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MusterRollAnalyticsService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final MdmsUtil mdmsUtil;
    private final MusterRollServiceConfiguration config;

    @Autowired
    public MusterRollAnalyticsService(ObjectMapper objectMapper, RestTemplate restTemplate,
                                      MdmsUtil mdmsUtil, MusterRollServiceConfiguration config) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.mdmsUtil = mdmsUtil;
        this.config = config;
    }

    /**
     * Fetches total registrations and interventions from Elasticsearch for each individual
     * and sets them on the corresponding IndividualEntry.
     */
    public void enrichIndividualMetrics(MusterRoll musterRoll, List<Individual> individuals, RequestInfo requestInfo) {
        if (musterRoll == null || CollectionUtils.isEmpty(musterRoll.getIndividualEntries()) || CollectionUtils.isEmpty(individuals)) {
            log.info("MusterRollAnalyticsService::enrichIndividualMetrics::Skipping - no individual entries or individuals");
            return;
        }

        // Build individualId <-> userUuid mappings
        Map<String, String> individualIdToUserUuid = new HashMap<>();
        Map<String, String> userUuidToIndividualId = new HashMap<>();
        for (Individual individual : individuals) {
            if (individual.getId() != null && individual.getUserUuid() != null) {
                individualIdToUserUuid.put(individual.getId(), individual.getUserUuid());
                userUuidToIndividualId.put(individual.getUserUuid(), individual.getId());
            }
        }

        List<String> userUuids = musterRoll.getIndividualEntries().stream()
                .map(IndividualEntry::getIndividualId)
                .map(individualIdToUserUuid::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (userUuids.isEmpty()) {
            log.info("MusterRollAnalyticsService::enrichIndividualMetrics::No userUuids found for individuals");
            return;
        }

        String tenantId = musterRoll.getTenantId();
        Long startDate = musterRoll.getStartDate().longValue();
        Long endDate = musterRoll.getEndDate().longValue();
        String projectId = musterRoll.getReferenceId();

        // Fetch ES query configs from MDMS
        List<ElasticsearchQueryConfig> esQueryConfigs = fetchEsQueryConfigs(requestInfo, tenantId);

        ElasticsearchQueryConfig registrationsConfig = esQueryConfigs.stream()
                .filter(c -> "REGISTRATIONS".equalsIgnoreCase(c.getCode()))
                .findFirst().orElse(null);

        ElasticsearchQueryConfig interventionsConfig = esQueryConfigs.stream()
                .filter(c -> "INTERVENTIONS".equalsIgnoreCase(c.getCode()))
                .findFirst().orElse(null);

        // Fetch and map registrations
        Map<String, Long> registrationsByIndividualId = fetchMetricFromEs(
                registrationsConfig, userUuids, startDate, endDate, tenantId,
                userUuidToIndividualId, projectId, "total_registrations");

        // Fetch and map interventions
        Map<String, Long> interventionsByIndividualId = fetchMetricFromEs(
                interventionsConfig, userUuids, startDate, endDate, tenantId,
                userUuidToIndividualId, projectId, "total_interventions");

        // Set metrics on individual entries
        for (IndividualEntry entry : musterRoll.getIndividualEntries()) {
            String individualId = entry.getIndividualId();
            entry.setTotalRegistrations(registrationsByIndividualId.getOrDefault(individualId, 0L));
            entry.setTotalInterventions(interventionsByIndividualId.getOrDefault(individualId, 0L));
        }

        log.info("MusterRollAnalyticsService::enrichIndividualMetrics::Enriched {} individual entries with metrics",
                musterRoll.getIndividualEntries().size());
    }

    /**
     * Fetches ES query configs from MDMS and converts to ElasticsearchQueryConfig models.
     */
    private List<ElasticsearchQueryConfig> fetchEsQueryConfigs(RequestInfo requestInfo, String tenantId) {
        List<ElasticsearchQueryConfig> configs = new ArrayList<>();
        try {
            Object mdmsData = mdmsUtil.mDMSCallEsQueries(requestInfo, tenantId);
            String jsonPath = "$.MdmsRes." + config.getEsQueriesMdmsModule() + "." + config.getEsQueriesMdmsMaster() + ".*";
            List<LinkedHashMap<String, Object>> queryList = JsonPath.read(mdmsData, jsonPath);

            if (queryList != null) {
                for (LinkedHashMap<String, Object> entry : queryList) {
                    configs.add(objectMapper.convertValue(entry, ElasticsearchQueryConfig.class));
                }
            }
        } catch (Exception e) {
            log.error("MusterRollAnalyticsService::fetchEsQueryConfigs::Error parsing MDMS response", e);
        }
        log.info("MusterRollAnalyticsService::fetchEsQueryConfigs::Fetched {} query configs", configs.size());
        return configs;
    }

    /**
     * Executes an ES query template against Elasticsearch, replacing placeholders,
     * and returns a map of individualId -> metric value.
     *
     * Placeholders in query: {{userUuids}}, {{startDate}}, {{endDate}}, {{tenantId}}
     */
    private Map<String, Long> fetchMetricFromEs(ElasticsearchQueryConfig queryConfig, List<String> userUuids,
                                                 Long startDate, Long endDate, String tenantId,
                                                 Map<String, String> userUuidToIndividualId,
                                                 String projectId, String metricField) {
        Map<String, Long> result = new HashMap<>();
        if (queryConfig == null || queryConfig.getQuery() == null || queryConfig.getIndexName() == null) {
            log.warn("MusterRollAnalyticsService::fetchMetricFromEs::No query config for metric: {}", metricField);
            return result;
        }

        try {
            // Replace placeholders in the query template
            String userUuidsJson = objectMapper.writeValueAsString(userUuids);
            String query = queryConfig.getQuery()
                    .replace("{{userUuidsPlaceholder}}", userUuidsJson)
                    .replace("{{projectIdPlaceholder}}", projectId)
                    .replace("{{startDatePlaceholder}}", String.valueOf(startDate))
                    .replace("{{endDatePlaceholder}}", String.valueOf(endDate))
                    .replace("{{tenantIdPlaceholder}}", tenantId);

            log.debug("MusterRollAnalyticsService::fetchMetricFromEs::Executing ES query for metric: {} on index: {}",
                    metricField, queryConfig.getIndexName());

            String esUrl = config.getElasticSearchHost() + "/" + queryConfig.getIndexName() + "/_search";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, getESEncodedCredentials());
            HttpEntity<String> requestEntity = new HttpEntity<>(query, headers);

            String responseStr = restTemplate.postForObject(esUrl, requestEntity, String.class);
            JsonNode responseJson = objectMapper.readTree(responseStr);

            // Parse aggregation buckets: aggregations.Users.buckets[]
            JsonNode buckets = responseJson.path("aggregations").path("Users").path("buckets");
            if (buckets.isArray()) {
                for (JsonNode bucket : buckets) {
                    String userUuid = bucket.path("key").asText();
                    String individualId = userUuidToIndividualId.get(userUuid);
                    if (individualId == null) continue;

                    // Sum values from nested date buckets if present
                    long total = 0;
                    JsonNode metricBuckets = bucket.path(metricField).path("buckets");
                    if (metricBuckets.isArray() && !metricBuckets.isEmpty()) {
                        for (JsonNode dateBucket : metricBuckets) {
                            total += dateBucket.path(metricField).path("value").asLong(0);
                        }
                    } else {
                        // Direct value or doc_count fallback
                        total = bucket.path(metricField).path("value").asLong(
                                bucket.path("doc_count").asLong(0));
                    }

                    result.put(individualId, total);
                }
            }

            log.info("MusterRollAnalyticsService::fetchMetricFromEs::Parsed {} user metrics for {}", result.size(), metricField);
        } catch (Exception e) {
            log.error("MusterRollAnalyticsService::fetchMetricFromEs::Error fetching metric {} from ES", metricField, e);
        }

        return result;
    }

    public String getESEncodedCredentials() {
        String credentials = config.getElasticSearchUsername() + ":" + config.getElasticSearchPassword();
        byte[] credentialsBytes = credentials.getBytes();
        byte[] base64CredentialsBytes = Base64.getEncoder().encode(credentialsBytes);
        return "Basic " + new String(base64CredentialsBytes);
    }
}
