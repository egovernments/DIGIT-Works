package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

import java.util.List;

/**
 * Context stored in JSONB for {@code BILL_STARTED_CHECK} scheduler jobs.
 * Coordinates the transition from bill-level action to per-detail in-progress state.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillStartedCheckContext {

    private String billId;
    private String tenantId;

    /** VERIFY_STARTED or PAYMENT_STARTED — determines which statuses to check. */
    private String phase;

    /** IDs of the details in the original request (for scoped eligibility checks). */
    private List<String> requestDetailIds;

    private RequestInfo requestInfo;
}
