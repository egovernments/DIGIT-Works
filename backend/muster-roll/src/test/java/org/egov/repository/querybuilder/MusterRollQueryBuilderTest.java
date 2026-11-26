package org.egov.repository.querybuilder;

import org.egov.util.MusterRollServiceUtil;
import org.egov.web.models.MusterRollSearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MusterRollQueryBuilderTest {

    @InjectMocks
    private MusterRollQueryBuilder queryBuilder;
    @Mock
    private MusterRollServiceUtil musterRollServiceUtil;

    @BeforeEach
    void setUp() {
        when(musterRollServiceUtil.isTenantBasedSearch(any(MusterRollSearchCriteria.class))).thenReturn(true);
    }

    private String buildQuery(String sortBy) {
        MusterRollSearchCriteria criteria = MusterRollSearchCriteria.builder()
                .tenantId("pb.amritsar").limit(10).offset(0).sortBy(sortBy).build();
        return queryBuilder.getMusterSearchQuery(criteria, new ArrayList<>(), Collections.emptyList(), false);
    }

    @Test
    void sortByIsIgnoredWhenNotASortableColumn() {
        String unsupported = "unknown_column";

        String query = buildQuery(unsupported);

        assertFalse(query.contains(unsupported));
        assertTrue(query.contains("ORDER BY createdtime DESC"));
    }

    @Test
    void sortByIsAppliedWhenItIsASortableColumn() {
        assertTrue(buildQuery("start_date").contains("ORDER BY start_date DESC"));
    }

    @Test
    void sortByIsMatchedCaseInsensitively() {
        assertTrue(buildQuery("createdTime").contains("ORDER BY createdtime DESC"));
    }

    @Test
    void sortByDefaultsToCreatedTimeWhenAbsent() {
        assertTrue(buildQuery(null).contains("ORDER BY createdtime DESC"));
    }
}
