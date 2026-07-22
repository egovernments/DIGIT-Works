package org.egov.digit.expense.calculator.service;

import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.ServiceRequestClient;
import org.egov.digit.expense.calculator.web.models.boundary.BoundarySearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoundaryServiceTest {

    @Mock
    private ServiceRequestClient serviceRequestClient;

    @Mock
    private ExpenseCalculatorConfiguration config;

    @InjectMocks
    private BoundaryService boundaryService;

    @BeforeEach
    void resetStaticCache() {
        // static cache would otherwise short-circuit the service call across tests
        ReflectionTestUtils.setField(BoundaryService.class, "cachedEnrichedBoundaries", new java.util.concurrent.ConcurrentHashMap<>());
    }

    @Test
    @DisplayName("sends the passed hierarchyType (not the static config) in the boundary search URL")
    void usesPassedHierarchyTypeInUrl() throws Exception {
        lenient().when(config.getBoundaryV2AuthToken()).thenReturn("token");
        lenient().when(config.getBoundaryServiceHost()).thenReturn("http://boundary/");
        lenient().when(config.getBoundaryRelationshipSearchUrl()).thenReturn("boundary-relationships/_search");
        when(serviceRequestClient.fetchResult(any(StringBuilder.class), any(), eq(BoundarySearchResponse.class)))
                .thenReturn(BoundarySearchResponse.builder().tenantBoundary(Collections.emptyList()).build());

        boundaryService.fetchBoundaryData("MICROPLAN_MO_01", "mz", "MICROPLAN");

        ArgumentCaptor<StringBuilder> uriCaptor = ArgumentCaptor.forClass(StringBuilder.class);
        verify(serviceRequestClient).fetchResult(uriCaptor.capture(), any(), eq(BoundarySearchResponse.class));

        String uri = uriCaptor.getValue().toString();
        assertTrue(uri.contains("&hierarchyType=MICROPLAN"),
                "boundary search URL should carry the passed hierarchyType; was: " + uri);
        // the static hierarchy config must not be consulted anymore
        verify(config, never()).getBoundaryHierarchyName();
    }
}
