package org.egov.digit.expense.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.web.models.enums.WorkflowNotificationType;

import java.util.List;

/**
 * Context stored in JSONB for {@code BILL_BATCH_EMAIL} coordinator jobs.
 * Created once per bulk status-update request that triggers email notifications.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillBatchEmailContext {

    /** Shared batch identifier — used to query all sibling BILL_STATUS_POLL jobs. */
    private String batchId;

    /** POLL_PHASE_SEND_FOR_REVIEW or POLL_PHASE_SEND_FOR_APPROVAL — kept for backward compatibility. */
    private String phase;

    /** Notification type determined at job creation time (REVIEW or APPROVAL). */
    private WorkflowNotificationType notificationType;

    /**
     * Bill IDs that successfully reached the job-creation step.
     * Used to fetch bills for project-staff recipient resolution.
     */
    private List<String> billIds;

    /** Original actor's RequestInfo — used for downstream service calls (localization, individual search). */
    private RequestInfo requestInfo;
}
