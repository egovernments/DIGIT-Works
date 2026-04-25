package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

/**
 * Context stored in JSONB for {@code DETAIL_VERIFY} scheduler jobs.
 * One job is created per BillDetail; referenceId on the job is the billDetailId.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailVerifyContext {

    /** Bill that owns the detail being verified. */
    private String billId;

    /** The specific BillDetail this job is responsible for. */
    private String billDetailId;

    /** Original actor's RequestInfo — used for WF transitions and audit. */
    private RequestInfo requestInfo;
}
