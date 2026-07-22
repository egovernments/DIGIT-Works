package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.models.project.Project;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class HealthBillReportGeneratorHierarchyTest {

    private static final String STATIC_FALLBACK = "ADMIN";

    @Mock
    private ExpenseCalculatorConfiguration config;

    // real mapper needed for valueToTree on project additionalDetails
    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private HealthBillReportGenerator reportGenerator;

    private String resolve(Project project) {
        return (String) ReflectionTestUtils.invokeMethod(reportGenerator, "getHierarchyTypeFromProject", project);
    }

    @Test
    @DisplayName("returns hierarchyType from project additionalDetails when present")
    void returnsHierarchyTypeFromProject() {
        Map<String, Object> additionalDetails = new HashMap<>();
        additionalDetails.put("hierarchyType", "MICROPLAN");
        Project project = Project.builder().additionalDetails(additionalDetails).build();

        assertEquals("MICROPLAN", resolve(project));
    }

    @Test
    @DisplayName("falls back to static config when additionalDetails is null")
    void fallsBackWhenAdditionalDetailsNull() {
        lenient().when(config.getBoundaryHierarchyName()).thenReturn(STATIC_FALLBACK);
        Project project = Project.builder().additionalDetails(null).build();

        assertEquals(STATIC_FALLBACK, resolve(project));
    }

    @Test
    @DisplayName("falls back to static config when hierarchyType key is missing")
    void fallsBackWhenKeyMissing() {
        lenient().when(config.getBoundaryHierarchyName()).thenReturn(STATIC_FALLBACK);
        Map<String, Object> additionalDetails = new HashMap<>();
        additionalDetails.put("someOtherKey", "value");
        Project project = Project.builder().additionalDetails(additionalDetails).build();

        assertEquals(STATIC_FALLBACK, resolve(project));
    }

    @Test
    @DisplayName("falls back to static config when hierarchyType is blank")
    void fallsBackWhenHierarchyTypeBlank() {
        lenient().when(config.getBoundaryHierarchyName()).thenReturn(STATIC_FALLBACK);
        Map<String, Object> additionalDetails = new HashMap<>();
        additionalDetails.put("hierarchyType", "   ");
        Project project = Project.builder().additionalDetails(additionalDetails).build();

        assertEquals(STATIC_FALLBACK, resolve(project));
    }

    @Test
    @DisplayName("falls back to static config when project is null")
    void fallsBackWhenProjectNull() {
        lenient().when(config.getBoundaryHierarchyName()).thenReturn(STATIC_FALLBACK);

        assertEquals(STATIC_FALLBACK, resolve(null));
    }
}
