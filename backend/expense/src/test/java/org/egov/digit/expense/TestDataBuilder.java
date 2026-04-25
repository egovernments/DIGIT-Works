package org.egov.digit.expense;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.common.contract.request.User;
import org.egov.common.contract.workflow.State;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.*;

import java.math.BigDecimal;
import java.util.*;

public class TestDataBuilder {

    public static final String TENANT_ID    = "ng.test";
    public static final String BILL_ID      = "bill-001";
    public static final String DETAIL_ID_1  = "detail-001";
    public static final String DETAIL_ID_2  = "detail-002";
    public static final String DETAIL_ID_3  = "detail-003";
    public static final String BILL_NUMBER  = "WB/2025-26/0001";
    public static final String BUSINESS_SVC = "PAYMENTS.BILL";
    public static final String USER_UUID    = "user-uuid-001";
    public static final String TASK_ID      = "task-001";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static RequestInfo buildRequestInfo(String... roles) {
        User user = User.builder()
                .uuid(USER_UUID)
                .tenantId(TENANT_ID)
                .roles(buildRoles(roles))
                .build();
        return RequestInfo.builder().userInfo(user).build();
    }

    public static List<Role> buildRoles(String... names) {
        List<Role> list = new ArrayList<>();
        for (String name : names) {
            list.add(Role.builder().name(name).code(name).tenantId(TENANT_ID).build());
        }
        return list;
    }

    public static State buildWfState(String applicationStatus) {
        State state = new State();
        state.setApplicationStatus(applicationStatus);
        return state;
    }

    public static Bill buildBill(Status billStatus, Status detailStatus, int detailCount) {
        List<BillDetail> details = new ArrayList<>();
        for (int i = 0; i < detailCount; i++) {
            details.add(buildDetail("detail-" + (i + 1), detailStatus));
        }
        return buildBillWithDetails(billStatus, details);
    }

    public static Bill buildBillWithMixedDetails(Status billStatus, Status... detailStatuses) {
        List<BillDetail> details = new ArrayList<>();
        for (int i = 0; i < detailStatuses.length; i++) {
            details.add(buildDetail("detail-" + (i + 1), detailStatuses[i]));
        }
        return buildBillWithDetails(billStatus, details);
    }

    public static Bill buildBillWithDetails(Status billStatus, List<BillDetail> details) {
        return Bill.builder()
                .id(BILL_ID)
                .tenantId(TENANT_ID)
                .businessService(BUSINESS_SVC)
                .billNumber(BILL_NUMBER)
                .billDate(System.currentTimeMillis())
                .dueDate(System.currentTimeMillis() + 86400000L)
                .totalAmount(BigDecimal.valueOf(1000))
                .totalWageAmount(BigDecimal.valueOf(1000))
                .status(billStatus)
                .referenceId("register-001")
                .billDetails(details)
                .auditDetails(buildAuditDetails())
                .build();
    }

    public static BillDetail buildDetail(String id, Status status) {
        return BillDetail.builder()
                .id(id)
                .tenantId(TENANT_ID)
                .billId(BILL_ID)
                .status(status)
                .totalAmount(BigDecimal.valueOf(200))
                .payee(buildParty("MTN"))
                .auditDetails(buildAuditDetails())
                .build();
    }

    public static BillDetail buildDetailWithPayee(String id, Status status, String phone, String provider) {
        return BillDetail.builder()
                .id(id)
                .tenantId(TENANT_ID)
                .billId(BILL_ID)
                .status(status)
                .totalAmount(BigDecimal.valueOf(200))
                .payee(Party.builder()
                        .id("payee-" + id)
                        .tenantId(TENANT_ID)
                        .paymentProvider(provider)
                        .payeePhoneNumber(phone)
                        .build())
                .auditDetails(buildAuditDetails())
                .build();
    }

    public static Party buildParty(String provider) {
        return Party.builder()
                .id("payee-001")
                .tenantId(TENANT_ID)
                .paymentProvider(provider)
                .payeePhoneNumber("0770000001")
                .build();
    }

    public static AuditDetails buildAuditDetails() {
        return AuditDetails.builder()
                .createdBy(USER_UUID)
                .lastModifiedBy(USER_UUID)
                .createdTime(System.currentTimeMillis())
                .lastModifiedTime(System.currentTimeMillis())
                .build();
    }

    public static SchedulerJob buildDetailVerifyJob(String billId, String detailId) {
        DetailVerifyContext ctx = DetailVerifyContext.builder()
                .billId(billId).billDetailId(detailId)
                .requestInfo(buildRequestInfo("PAYMENT_EDITOR"))
                .build();
        return SchedulerJob.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(TENANT_ID)
                .jobType(SchedulerJobType.DETAIL_VERIFY)
                .referenceId(detailId)
                .schedulerStatus(SchedulerJobStatus.PENDING)
                .attemptCount(0).maxAttempts(200)
                .context(MAPPER.convertValue(ctx, Object.class))
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .build();
    }

    public static SchedulerJob buildDetailWfUpdateJob(String billId, String detailId, String phase) {
        DetailWfUpdateContext ctx = DetailWfUpdateContext.builder()
                .billId(billId).billDetailId(detailId).phase(phase)
                .requestInfo(buildRequestInfo("PAYMENT_EDITOR"))
                .build();
        return SchedulerJob.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(TENANT_ID)
                .jobType(SchedulerJobType.DETAIL_WF_UPDATE)
                .referenceId(detailId)
                .schedulerStatus(SchedulerJobStatus.PENDING)
                .attemptCount(0).maxAttempts(200)
                .context(MAPPER.convertValue(ctx, Object.class))
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .build();
    }

    public static SchedulerJob buildBillStatusPollJob(String billId, String phase) {
        BillPollContext ctx = BillPollContext.builder()
                .phase(phase)
                .requestInfo(buildRequestInfo("PAYMENT_EDITOR"))
                .build();
        return SchedulerJob.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(TENANT_ID)
                .jobType(SchedulerJobType.BILL_STATUS_POLL)
                .referenceId(billId)
                .schedulerStatus(SchedulerJobStatus.PENDING)
                .attemptCount(0).maxAttempts(200)
                .context(MAPPER.convertValue(ctx, Object.class))
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .build();
    }

    public static BillRequest buildBillRequest(Bill bill) {
        return BillRequest.builder()
                .bill(bill)
                .requestInfo(buildRequestInfo("PAYMENT_EDITOR"))
                .build();
    }

    public static TaskRequest buildWfUpdateTaskRequest(Bill bill, BillDetail detail, String phase) {
        Task task = Task.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(TENANT_ID)
                .billId(bill.getId())
                .billDetailId(detail.getId())
                .type(Task.Type.WfUpdate)
                .status(Status.IN_PROGRESS)
                .additionalDetails(Map.of("phase", phase))
                .auditDetails(buildAuditDetails())
                .build();
        Bill singleDetailBill = Bill.builder()
                .id(bill.getId())
                .tenantId(bill.getTenantId())
                .businessService(bill.getBusinessService())
                .billNumber(bill.getBillNumber())
                .billDate(bill.getBillDate())
                .dueDate(bill.getDueDate())
                .totalAmount(bill.getTotalAmount())
                .totalWageAmount(bill.getTotalWageAmount())
                .status(bill.getStatus())
                .referenceId(bill.getReferenceId())
                .billDetails(Collections.singletonList(detail))
                .auditDetails(bill.getAuditDetails())
                .build();
        return TaskRequest.builder()
                .task(task)
                .bill(singleDetailBill)
                .requestInfo(buildRequestInfo("PAYMENT_EDITOR"))
                .build();
    }

    public static TaskRequest buildVerifyTaskRequest(Bill bill, BillDetail detail) {
        Task task = Task.builder()
                .id(TASK_ID)
                .tenantId(TENANT_ID)
                .billId(bill.getId())
                .billDetailId(detail.getId())
                .type(Task.Type.Verify)
                .status(Status.IN_PROGRESS)
                .auditDetails(buildAuditDetails())
                .build();
        Bill singleDetailBill = Bill.builder()
                .id(bill.getId())
                .tenantId(bill.getTenantId())
                .businessService(bill.getBusinessService())
                .billNumber(bill.getBillNumber())
                .billDate(bill.getBillDate())
                .dueDate(bill.getDueDate())
                .totalAmount(bill.getTotalAmount())
                .totalWageAmount(bill.getTotalWageAmount())
                .status(bill.getStatus())
                .referenceId(bill.getReferenceId())
                .billDetails(Collections.singletonList(detail))
                .auditDetails(bill.getAuditDetails())
                .build();
        return TaskRequest.builder()
                .task(task)
                .bill(singleDetailBill)
                .requestInfo(buildRequestInfo("PAYMENT_EDITOR"))
                .build();
    }

    public static BulkBillStatusUpdateRequest buildBulkRequest(String status, String action, String... billIds) {
        return BulkBillStatusUpdateRequest.builder()
                .tenantId(TENANT_ID)
                .billIds(Arrays.asList(billIds))
                .status(status)
                .workflow(org.egov.common.contract.models.Workflow.builder().action(action).build())
                .requestInfo(buildRequestInfo("PAYMENT_EDITOR"))
                .build();
    }

    public static boolean hasPhase(SchedulerJob job, String phase) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> ctx = new ObjectMapper().convertValue(job.getContext(), Map.class);
            return phase.equals(ctx.get("phase"));
        } catch (Exception e) {
            return false;
        }
    }
}
