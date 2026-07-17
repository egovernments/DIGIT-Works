package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.repository.SchedulerJobRepository;
import org.egov.digit.expense.service.WorkflowEmailNotificationService;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.SchedulerJobStatus;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.WorkflowNotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.egov.digit.expense.config.Constants.POLL_PHASE_SEND_FOR_REVIEW;

/**
 * Coordinator job that fires ONE email after all BILL_STATUS_POLL jobs in a bulk request settle.
 *
 * <p>Retries until all sibling BILL_STATUS_POLL jobs (same batchId) are DONE or FAILED,
 * then fetches the batch bills, filters to those in the expected workflow status
 * (UNDER_REVIEW for REVIEW notifications, REVIEWED for APPROVAL notifications),
 * and sends one email with the eligible bill count.
 */
@Component
@Slf4j
public class BillBatchEmailHandler implements SchedulerJobHandler {

    private final ObjectMapper objectMapper;
    private final SchedulerJobRepository schedulerJobRepository;
    private final BillRepository billRepository;
    private final WorkflowEmailNotificationService workflowEmailNotificationService;

    @Autowired
    public BillBatchEmailHandler(ObjectMapper objectMapper,
                                  SchedulerJobRepository schedulerJobRepository,
                                  BillRepository billRepository,
                                  WorkflowEmailNotificationService workflowEmailNotificationService) {
        this.objectMapper = objectMapper;
        this.schedulerJobRepository = schedulerJobRepository;
        this.billRepository = billRepository;
        this.workflowEmailNotificationService = workflowEmailNotificationService;
    }

    @Override
    public SchedulerJobType getJobType() {
        return SchedulerJobType.BILL_BATCH_EMAIL;
    }

    @Override
    public SchedulerJobResult handle(SchedulerJob job) {
        String tenantId = job.getTenantId();

        try {
            BillBatchEmailContext ctx = objectMapper.convertValue(job.getContext(), BillBatchEmailContext.class);
            String batchId = ctx.getBatchId();
            RequestInfo requestInfo = ctx.getRequestInfo();

            List<SchedulerJob> batchJobs = schedulerJobRepository.findByBatchId(tenantId, batchId);

            if (batchJobs.isEmpty()) {
                log.warn("BILL_BATCH_EMAIL batchId={}: no BILL_STATUS_POLL jobs found — marking DONE (nothing to email)", batchId);
                return SchedulerJobResult.DONE;
            }

            boolean anyPending = batchJobs.stream()
                    .anyMatch(j -> j.getSchedulerStatus() == SchedulerJobStatus.PENDING
                               || j.getSchedulerStatus() == SchedulerJobStatus.PROCESSING);
            if (anyPending) {
                log.debug("BILL_BATCH_EMAIL batchId={}: {} jobs still in flight — retrying", batchId, batchJobs.size());
                return SchedulerJobResult.RETRY;
            }

            long doneCount = batchJobs.stream()
                    .filter(j -> j.getSchedulerStatus() == SchedulerJobStatus.DONE)
                    .count();

            if (doneCount == 0) {
                log.warn("BILL_BATCH_EMAIL batchId={}: all {} jobs FAILED — no email sent", batchId, batchJobs.size());
                return SchedulerJobResult.DONE;
            }

            // Prefer notificationType set at creation time; fall back to phase for pre-existing rows
            WorkflowNotificationType type = ctx.getNotificationType() != null
                    ? ctx.getNotificationType()
                    : (POLL_PHASE_SEND_FOR_REVIEW.equals(ctx.getPhase())
                            ? WorkflowNotificationType.REVIEW
                            : WorkflowNotificationType.APPROVAL);

            List<Bill> bills = fetchBills(ctx.getBillIds(), tenantId, requestInfo);
            if (bills.isEmpty()) {
                log.warn("BILL_BATCH_EMAIL batchId={}: could not fetch bills — no email sent", batchId);
                return SchedulerJobResult.DONE;
            }

            // Only notify for bills that actually reached the expected status
            String expectedStatus = (type == WorkflowNotificationType.REVIEW) ? "UNDER_REVIEW" : "REVIEWED";
            List<Bill> eligibleBills = bills.stream()
                    .filter(b -> b.getStatus() != null && expectedStatus.equals(b.getStatus().toString()))
                    .collect(Collectors.toList());

            if (eligibleBills.isEmpty()) {
                log.warn("BILL_BATCH_EMAIL batchId={} type={}: no bills in status {} — no email sent",
                        batchId, type, expectedStatus);
                return SchedulerJobResult.DONE;
            }

            workflowEmailNotificationService.notifyBatch(eligibleBills, tenantId, requestInfo, type, eligibleBills.size());
            log.info("BILL_BATCH_EMAIL batchId={}: email sent type={} eligibleBills={} totalJobs={}",
                    batchId, type, eligibleBills.size(), batchJobs.size());

            return SchedulerJobResult.DONE;

        } catch (Exception e) {
            log.error("BILL_BATCH_EMAIL job={} tenantId={}: unexpected error — retrying", job.getId(), tenantId, e);
            return SchedulerJobResult.RETRY;
        }
    }

    @Override
    public void onMaxAttemptsExceeded(SchedulerJob job) {
        log.error("BILL_BATCH_EMAIL job={} batchId={} tenantId={}: max attempts exceeded — batch email NEVER SENT",
                job.getId(), job.getReferenceId(), job.getTenantId());
    }

    private List<Bill> fetchBills(List<String> billIds, String tenantId, RequestInfo requestInfo) {
        try {
            BillCriteria criteria = BillCriteria.builder()
                    .ids(new HashSet<>(billIds))
                    .tenantId(tenantId)
                    .build();
            BillSearchRequest searchRequest = BillSearchRequest.builder()
                    .requestInfo(requestInfo)
                    .billCriteria(criteria)
                    .build();
            return billRepository.search(searchRequest, false);
        } catch (Exception e) {
            log.error("BILL_BATCH_EMAIL: failed to fetch bills for billIds={}: {}", billIds, e.getMessage());
            return List.of();
        }
    }
}
