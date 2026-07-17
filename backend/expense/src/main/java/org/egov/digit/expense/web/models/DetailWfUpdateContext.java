package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

/**
 * Context stored in JSONB for {@code DETAIL_WF_UPDATE} scheduler jobs.
 * One job is created per eligible BillDetail per phase; referenceId on the job is the billDetailId.
 *
 * <p>Phases: IGNORE_ERRORS, SEND_FOR_REVIEW, SEND_FOR_APPROVAL, PAYMENT_INITIATION.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailWfUpdateContext {

    /** Bill that owns the detail being transitioned. */
    private String billId;

    /** The specific BillDetail this job is responsible for. */
    private String billDetailId;

    /** Workflow phase driving this detail transition. */
    private String phase;

    /** Original actor's RequestInfo — used for WF transitions and audit. */
    private RequestInfo requestInfo;
}
