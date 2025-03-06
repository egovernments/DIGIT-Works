package org.egov.digit.expense.calculator.service;


import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.web.models.boundary.BoundaryHierarchyResult;
import org.egov.digit.expense.calculator.util.ServiceRequestClient;
import org.egov.digit.expense.calculator.web.models.boundary.BoundaryRelationshipRequest;
import org.egov.digit.expense.calculator.web.models.boundary.BoundarySearchResponse;
import org.egov.digit.expense.calculator.web.models.boundary.EnrichedBoundary;
import org.springframework.stereotype.Component;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.egov.tracer.model.CustomException;


import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.*;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;
@Component
@Slf4j
public class BoundaryService {


    private final ServiceRequestClient serviceRequestClient;

    private static Map<String, String> boundaryCodeVsLocalizedName = new ConcurrentHashMap<>();
    private final ExpenseCalculatorConfiguration config;

    private static List<EnrichedBoundary> cachedEnrichedBoundaries = null;

    public BoundaryService( ServiceRequestClient serviceRequestClient , ExpenseCalculatorConfiguration config) {
        this.serviceRequestClient = serviceRequestClient;
        this.config = config;
    }

    public BoundaryHierarchyResult getBoundaryHierarchyWithLocalityCode(String localityCode, String tenantId) {
        if (localityCode == null) {
            return null;
        }
        // Fetch both localized and non-localized boundary data
        BoundaryHierarchyResult boundaryResult = getBoundaryCodeToNameMap(localityCode, tenantId);

        return applyTransformerElasticIndexLabels(boundaryResult, tenantId);
    }


    public BoundaryHierarchyResult getBoundaryCodeToNameMap(String locationCode, String tenantId) {
        RequestInfo requestInfo = RequestInfo.builder()
                .authToken(config.getBoundaryV2AuthToken())
                .build();

        // Fetch boundaries
        List<EnrichedBoundary> boundaries = fetchBoundaryData(locationCode, tenantId);

        // Create and return BoundaryHierarchyResult
        return createBoundaryHierarchyResult(boundaries, tenantId, requestInfo);
    }

    public BoundaryHierarchyResult createBoundaryHierarchyResult(List<EnrichedBoundary> boundaries, String tenantId, RequestInfo requestInfo) {
        BoundaryHierarchyResult boundaryHierarchyResult = new BoundaryHierarchyResult();
        Map<String, String> boundaryMapToLocalizedNameMap = getBoundaryCodeToLocalizedNameMap(boundaries, requestInfo, tenantId);

        Map<String, String> boundaryCodeToLocalizationCodeMap = boundaries.stream()
                .collect(Collectors.toMap(
                        EnrichedBoundary::getBoundaryType,
                        EnrichedBoundary::getCode
                ));
        boundaryHierarchyResult.setBoundaryHierarchy(boundaryMapToLocalizedNameMap);
        boundaryHierarchyResult.setBoundaryHierarchyCode(boundaryCodeToLocalizationCodeMap);
        return boundaryHierarchyResult;
    }

    private List<EnrichedBoundary> getEnrichedBoundaryPath(List<EnrichedBoundary> enrichedBoundaries, String locationCode) {
        for (EnrichedBoundary enrichedBoundary : enrichedBoundaries) {
            List<EnrichedBoundary> path = getEnrichedBoundaryPath(enrichedBoundary, locationCode);
            if (path != null) {
                return path;
            }
        }
        return Collections.emptyList();
    }

    private List<EnrichedBoundary> getEnrichedBoundaryPath(EnrichedBoundary enrichedBoundary, String locationCode) {
        if (locationCode.equals(enrichedBoundary.getCode())) {
            EnrichedBoundary matchedNode = new EnrichedBoundary();
            matchedNode.setCode(enrichedBoundary.getCode());
            matchedNode.setBoundaryType(enrichedBoundary.getBoundaryType());
            matchedNode.setChildren(Collections.emptyList());
            return Collections.singletonList(matchedNode);
        }

        if (enrichedBoundary.getChildren() != null && !enrichedBoundary.getChildren().isEmpty()) {
            for (EnrichedBoundary child : enrichedBoundary.getChildren()) {
                List<EnrichedBoundary> childPath = getEnrichedBoundaryPath(child, locationCode);
                if (childPath != null) {
                    EnrichedBoundary currentNode = new EnrichedBoundary();
                    currentNode.setCode(enrichedBoundary.getCode());
                    currentNode.setBoundaryType(enrichedBoundary.getBoundaryType());
                    currentNode.setChildren(Collections.emptyList());

                    List<EnrichedBoundary> fullPath = new ArrayList<>();
                    fullPath.add(currentNode);
                    fullPath.addAll(childPath);
                    return fullPath;
                }
            }
        }

        return null;
    }


    public List<EnrichedBoundary> fetchBoundaryData(String locationCode, String tenantId) {
        List<EnrichedBoundary> finalEnrichedBoundary;
        if (cachedEnrichedBoundaries != null && !cachedEnrichedBoundaries.isEmpty()) {
            log.info("Fetching boundary info from cached boundary for code: {}", locationCode);
            finalEnrichedBoundary = getEnrichedBoundaryPath(cachedEnrichedBoundaries, locationCode);
            if (finalEnrichedBoundary != null && !finalEnrichedBoundary.isEmpty()) {
                return finalEnrichedBoundary;
            }
        }

        log.info("Could not fetch boundary info from cached tree. Fetching from service for locationCode: {}", locationCode);


        List<EnrichedBoundary> boundaries;
        RequestInfo requestInfo = RequestInfo.builder()
                .authToken(config.getBoundaryV2AuthToken())
                .build();
        BoundaryRelationshipRequest boundaryRequest = BoundaryRelationshipRequest.builder()
                .requestInfo(requestInfo).build();
        StringBuilder uri = new StringBuilder(config.getBoundaryServiceHost()
                + config.getBoundaryRelationshipSearchUrl()
                + "?includeParents=true&includeChildren=true&tenantId=" + tenantId
                + "&hierarchyType=" + config.getBoundaryHierarchyName()
                + "&code=" + locationCode);
        log.info("URI: {}, \n, requestBody: {}", uri, requestInfo);
        try {
            // Fetch boundary details from the service
            log.debug("Fetching boundary relation details for tenantId: {}, boundary: {}", tenantId, locationCode);
            BoundarySearchResponse boundarySearchResponse = serviceRequestClient.fetchResult(
                    uri,
                    boundaryRequest,
                    BoundarySearchResponse.class
            );
            log.debug("Boundary Relationship details fetched successfully for tenantId: {}", tenantId);

            List<EnrichedBoundary> enrichedBoundaries = boundarySearchResponse.getTenantBoundary().stream()
                    .filter(hierarchyRelation -> !CollectionUtils.isEmpty(hierarchyRelation.getBoundary()))
                    .flatMap(hierarchyRelation -> hierarchyRelation.getBoundary().stream())
                    .collect(Collectors.toList());
            cachedEnrichedBoundaries = enrichedBoundaries;
            log.info("Cached boundary object");
            boundaries = getEnrichedBoundaryPath(enrichedBoundaries, locationCode);
//            getAllBoundaryCodes(enrichedBoundaries, boundaries);

        } catch (Exception e) {
            log.error("Exception while searching boundaries for tenantId: {}, {}", tenantId, ExceptionUtils.getStackTrace(e));
            // Throw a custom exception if an error occurs during boundary search
            throw new CustomException("BOUNDARY_SEARCH_ERROR", e.getMessage());
        }

        return boundaries;
    }

    private Map<String, String> getBoundaryCodeToLocalizedNameMap(
            List<EnrichedBoundary> boundaries, RequestInfo requestInfo, String tenantId) {

        Map<String, String> boundaryMap = new HashMap<>();

        for (EnrichedBoundary boundary : boundaries) {
            String boundaryCode = boundary.getCode();
            String boundaryName = getLocalizedBoundaryName(boundaryCode, requestInfo, tenantId);

            boundaryMap.put(boundary.getBoundaryType(), boundaryName);
        }

        return boundaryMap;
    }

    private String getLocalizedBoundaryName(String boundaryCode, RequestInfo requestInfo, String tenantId) {
        String cachedName = boundaryCodeVsLocalizedName.get(boundaryCode);

        if (cachedName != null) {
            log.info("Fetched localization for code: {} from cache", boundaryCode);
            return cachedName;
        }

        String fetchedName = getBoundaryNameFromLocalisationService(boundaryCode, requestInfo, tenantId);
        if (fetchedName == null) {
            fetchedName = boundaryCode.substring(boundaryCode.lastIndexOf('_') + 1);
        } else {
            boundaryCodeVsLocalizedName.put(boundaryCode, fetchedName);
            log.info("Fetched localization from service for code: {}, value: {}. Cached result.", boundaryCode, fetchedName);
        }

        return fetchedName;
    }

    private String getBoundaryNameFromLocalisationService(String boundaryCode, RequestInfo requestInfo, String tenantId) {
        StringBuilder uri = new StringBuilder();
        RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
        requestInfoWrapper.setRequestInfo(requestInfo);
        uri.append(config.getLocalizationServiceHost()).append(config.getLocalizationServiceContextPath())
                .append(config.getLocalizationServiceEndpoint())
                .append("?tenantId=" + tenantId)
                .append("&module=" + config.getLocalizationModuleName())
                .append("&locale=" + config.getLocalizationLocaleCode())
                .append("&codes=" + boundaryCode);
        List<String> codes = null;
        List<String> messages = null;
        Object result = null;
        try {
            result = serviceRequestClient.fetchResult(uri, requestInfoWrapper, Map.class);
            codes = JsonPath.read(result, LOCALIZATION_CODES_JSONPATH);
            messages = JsonPath.read(result, LOCALIZATION_MSGS_JSONPATH);
        } catch (Exception e) {
            log.error("Exception while fetching from localization: {}", ExceptionUtils.getStackTrace(e));
        }
        return CollectionUtils.isEmpty(messages) ? null : messages.get(0);
    }

    private void getAllBoundaryCodes(List<EnrichedBoundary> enrichedBoundaries, List<EnrichedBoundary> boundaries) {
        if (enrichedBoundaries == null || enrichedBoundaries.isEmpty()) {
            return;
        }

        for (EnrichedBoundary root : enrichedBoundaries) {
            if (root != null) {
                Deque<EnrichedBoundary> stack = new ArrayDeque<>();
                stack.push(root);

                while (!stack.isEmpty()) {
                    EnrichedBoundary current = stack.pop();
                    if (current != null) {
                        boundaries.add(current);
                        if (current.getChildren() != null) {
                            stack.addAll(current.getChildren());
                        }
                    }
                }
            }
        }
    }

    public BoundaryHierarchyResult applyTransformerElasticIndexLabels(BoundaryHierarchyResult boundaryResult, String tenantId) {
        Map<String, String> localizedBoundaryHierarchy = new HashMap<>();
        Map<String, String> nonLocalizedBoundaryHierarchyCode = new HashMap<>();

        boundaryResult.getBoundaryHierarchy().forEach((boundaryType, localizedName) -> {
            // Generate elastic index label
//            String label = mdmsService.getMDMSTransformerElasticIndexLabels(boundaryType, tenantId);
             String label =boundaryType;
            // Populate localized and non-localized maps
            localizedBoundaryHierarchy.put(label, localizedName);
            nonLocalizedBoundaryHierarchyCode.put(label, boundaryResult.getBoundaryHierarchyCode().get(boundaryType));
        });

        // Return the result as a BoundaryHierarchyResult
        return BoundaryHierarchyResult.builder()
                .boundaryHierarchy(localizedBoundaryHierarchy)
                .boundaryHierarchyCode(nonLocalizedBoundaryHierarchyCode)
                .build();
    }


}
