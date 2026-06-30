package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

/**
 * Context stored in JSONB for {@code BILL_DETAIL_WF_UPDATE} scheduler jobs.
 * Carries the phase and the original actor's RequestInfo for WF auth.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailWfUpdateContext {

    /** Phase: IGNORE_ERRORS, SEND_FOR_REVIEW, SEND_FOR_APPROVAL, PAYMENT_INITIATION */
    private String phase;

    /** Original actor's RequestInfo — used for WF transitions. */
    private RequestInfo requestInfo;
}
