package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

/**
 * Context stored in JSONB for {@code BILL_STATUS_POLL} scheduler jobs.
 * Carries the polling phase and the original actor's RequestInfo for auth.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillPollContext {

    /** Polling phase: VERIFICATION, IGNORE_ERRORS, SEND_FOR_REVIEW, REVIEW */
    private String phase;

    /** Original actor's RequestInfo — used for service-triggered workflow transitions. */
    private RequestInfo requestInfo;

    /**
     * Batch identifier shared across all BILL_STATUS_POLL jobs created by a single bulk request.
     * Null for single-bill transitions — BillStatusPollHandler fires email directly.
     * When set, BillStatusPollHandler skips email; the BILL_BATCH_EMAIL coordinator owns it.
     */
    private String batchId;
}
