package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillDetailWfUpdateContext;
import org.egov.digit.expense.web.models.SchedulerJob;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

/**
 * Handles {@link SchedulerJobType#BILL_DETAIL_WF_UPDATE} jobs.
 *
 * <p>Asynchronously transitions bill details through their workflow for phases that do not
 * involve external calls (IGNORE_ERRORS, SEND_FOR_REVIEW, SEND_FOR_APPROVAL, PAYMENT_INITIATION).
 * Each detail is transitioned independently — failures are logged and retried on the next cycle.
 *
 * <p>Context JSONB: serialised {@link BillDetailWfUpdateContext}.
 */
@Component
@Slf4j
public class BillDetailWfUpdateHandler implements SchedulerJobHandler {

    private final PaymentWorkflowService paymentWorkflowService;
    private final ObjectMapper objectMapper;

    @Autowired
    public BillDetailWfUpdateHandler(PaymentWorkflowService paymentWorkflowService,
                                     ObjectMapper objectMapper) {
        this.paymentWorkflowService = paymentWorkflowService;
        this.objectMapper = objectMapper;
    }

    @Override
    public SchedulerJobType getJobType() {
        return SchedulerJobType.BILL_DETAIL_WF_UPDATE;
    }

    @Override
    public SchedulerJobResult handle(SchedulerJob job) {
        String billId = job.getReferenceId();
        String tenantId = job.getTenantId();

        try {
            BillDetailWfUpdateContext ctx = objectMapper.convertValue(job.getContext(), BillDetailWfUpdateContext.class);
            RequestInfo requestInfo = ctx.getRequestInfo();
            String phase = ctx.getPhase();

            Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId, requestInfo);
            if (bill == null) {
                log.warn("Bill {} not found for tenant {} — marking FAILED", billId, tenantId);
                return SchedulerJobResult.FAILED;
            }

            return switch (phase) {
                case "IGNORE_ERRORS" -> transitionDetails(bill, requestInfo,
                        d -> d.getStatus() == Status.VERIFICATION_FAILED,
                        Actions.IGNORE_ERRORS_AND_VERIFY, phase);
                case "SEND_FOR_REVIEW" -> transitionDetails(bill, requestInfo,
                        d -> d.getStatus() == Status.VERIFIED,
                        Actions.SEND_FOR_REVIEW, phase);
                case "SEND_FOR_APPROVAL" -> transitionDetails(bill, requestInfo,
                        d -> d.getStatus() == Status.UNDER_REVIEW,
                        Actions.SEND_FOR_APPROVAL, phase);
                case "PAYMENT_INITIATION" -> transitionDetails(bill, requestInfo,
                        d -> d.getStatus() == Status.REVIEWED || d.getStatus() == Status.PAYMENT_FAILED,
                        Actions.PAYMENT_INITIATION, phase);
                default -> {
                    log.error("Unknown phase '{}' for BILL_DETAIL_WF_UPDATE bill={}", phase, billId);
                    yield SchedulerJobResult.FAILED;
                }
            };

        } catch (Exception e) {
            log.error("Error in BILL_DETAIL_WF_UPDATE for billId={}, tenantId={}", billId, tenantId, e);
            return SchedulerJobResult.RETRY;
        }
    }

    private SchedulerJobResult transitionDetails(Bill bill, RequestInfo requestInfo,
                                                  Predicate<BillDetail> filter,
                                                  Actions action, String phase) {
        boolean anyFailed = false;
        int transitioned = 0;

        for (BillDetail detail : bill.getBillDetails()) {
            if (!filter.test(detail)) continue;
            try {
                paymentWorkflowService.transitionBillDetail(detail, action, requestInfo);
                transitioned++;
            } catch (Exception e) {
                log.error("BILL_DETAIL_WF_UPDATE phase={} — failed to transition billDetail {} via {}: {}",
                        phase, detail.getId(), action, e.getMessage());
                anyFailed = true;
            }
        }

        if (transitioned > 0) {
            paymentWorkflowService.pushBillUpdate(bill, requestInfo);
        }

        if (anyFailed) {
            log.warn("BILL_DETAIL_WF_UPDATE phase={} bill={} — {} detail(s) transitioned, retrying failed ones",
                    phase, bill.getId(), transitioned);
            return SchedulerJobResult.RETRY;
        }

        log.info("BILL_DETAIL_WF_UPDATE phase={} bill={} — all {} eligible detail(s) transitioned",
                phase, bill.getId(), transitioned);

        // After all details reach PAYMENT_IN_PROGRESS, trigger the Transfer task so MTN
        // disbursement starts automatically without a separate frontend API call.
        if ("PAYMENT_INITIATION".equals(phase)) {
            paymentWorkflowService.createTransferTask(bill, requestInfo);
        }

        return SchedulerJobResult.DONE;
    }
}
