package org.egov.digit.expense.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillApprovalRepository;
import org.egov.digit.expense.util.FilestoreUtil;
import org.egov.digit.expense.web.models.ApprovalSignature;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillApproval;
import org.egov.digit.expense.web.models.BillApprovalRequest;
import org.egov.digit.expense.web.models.BillApprovalSearchCriteria;
import org.egov.digit.expense.web.models.enums.ApprovalStatus;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.egov.digit.expense.config.Constants.*;

/**
 * Validates and persists the printed name + signature captured from a PAYMENT_APPROVER
 * at the point a bill is approved (PAYMENT_INITIATION action), and enriches searched
 * bills with their approval history for display.
 */
@Service
@Slf4j
public class BillApprovalService {

    // Magic-byte signatures, checked against actual file content.
    private static final byte[] PNG_MAGIC = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
    private static final byte[] JPEG_MAGIC = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};

    private final BillApprovalRepository billApprovalRepository;
    private final FilestoreUtil filestoreUtil;
    private final ExpenseProducer expenseProducer;
    private final Configuration config;

    @Autowired
    public BillApprovalService(BillApprovalRepository billApprovalRepository,
                                FilestoreUtil filestoreUtil,
                                ExpenseProducer expenseProducer,
                                Configuration config) {
        this.billApprovalRepository = billApprovalRepository;
        this.filestoreUtil = filestoreUtil;
        this.expenseProducer = expenseProducer;
        this.config = config;
    }

    /**
     * Validates the raw signature image: non-empty, within the configured size limit, and
     * actually a PNG or JPEG.
     *
     * Format is decided by magic bytes rather than a filename extension or a client-supplied
     * Content-Type, neither of which is trustworthy — the image is uploaded straight to
     * filestore by the client (the DIGIT convention), so the only point at which the server
     * can vouch for it is here, once it holds the bytes itself.
     */
    void validateSignatureBytes(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            throw new CustomException(ERR_SIGNATURE_FILE_EMPTY, MSG_SIGNATURE_FILE_EMPTY);
        }

        long maxSize = config.getSignatureMaxSizeBytes();
        if (bytes.length > maxSize) {
            throw new CustomException(ERR_SIGNATURE_FILE_TOO_LARGE,
                    MSG_SIGNATURE_FILE_TOO_LARGE_PREFIX + (maxSize / (1024 * 1024)) + "MB");
        }

        if (!startsWith(bytes, PNG_MAGIC) && !startsWith(bytes, JPEG_MAGIC)) {
            throw new CustomException(ERR_SIGNATURE_FILE_INVALID_FORMAT, MSG_SIGNATURE_FILE_INVALID_FORMAT);
        }
    }

    private boolean startsWith(byte[] bytes, byte[] magic) {
        if (bytes.length < magic.length) return false;
        for (int i = 0; i < magic.length; i++) {
            if (bytes[i] != magic[i]) return false;
        }
        return true;
    }

    /**
     * Validates the printed name + signature submitted with a PAYMENT_INITIATION approval and
     * confirms the caller holds PAYMENT_APPROVER, building (but not yet persisting) the immutable
     * BillApproval record. Must be called BEFORE the bill is transitioned — a validation failure
     * blocks approval. The returned record is only actually recorded via {@link #recordApproval}
     * once payment initiation has succeeded, so a failed initiation never leaves behind a false
     * "approved" audit row.
     */
    public BillApproval validateApproval(Bill bill, Bill billFromSearch, ApprovalSignature signature,
                                          RequestInfo requestInfo) {
        if (signature == null) {
            throw new CustomException(ERR_APPROVAL_SIGNATURE_REQUIRED, MSG_APPROVAL_SIGNATURE_REQUIRED);
        }
        if (!StringUtils.hasText(signature.getPrintedName()) || signature.getPrintedName().isBlank()) {
            throw new CustomException(ERR_APPROVAL_PRINTED_NAME_REQUIRED, MSG_APPROVAL_PRINTED_NAME_REQUIRED);
        }
        if (signature.getSignatureMethod() == null || !StringUtils.hasText(signature.getSignatureFileStoreId())) {
            throw new CustomException(ERR_APPROVAL_SIGNATURE_FILE_REQUIRED, MSG_APPROVAL_SIGNATURE_FILE_REQUIRED);
        }

        // Authorise before touching the filestore, so an unauthorised caller cannot make the
        // service fetch arbitrary files on their behalf.
        Set<String> roles = extractRoles(requestInfo);
        if (!roles.contains(ROLE_PAYMENT_APPROVER)) {
            throw new CustomException(ERR_APPROVAL_UNAUTHORIZED, MSG_APPROVAL_UNAUTHORIZED);
        }

        // Fetch the image and validate it here — this is the only place the signature is
        // checked. Confirming it resolves for this tenant also stops a caller submitting an
        // arbitrary or foreign fileStoreId they never uploaded.
        byte[] signatureBytes;
        try {
            signatureBytes = filestoreUtil.downloadFile(signature.getSignatureFileStoreId(), bill.getTenantId());
        } catch (Exception e) {
            throw new CustomException(ERR_APPROVAL_SIGNATURE_FILE_NOT_FOUND, MSG_APPROVAL_SIGNATURE_FILE_NOT_FOUND);
        }
        validateSignatureBytes(signatureBytes);

        String approverUuid = requestInfo.getUserInfo() != null ? requestInfo.getUserInfo().getUuid() : null;
        long now = System.currentTimeMillis();

        AuditDetails auditDetails = AuditDetails.builder()
                .createdBy(approverUuid)
                .createdTime(now)
                .lastModifiedBy(approverUuid)
                .lastModifiedTime(now)
                .build();

        return BillApproval.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(bill.getTenantId())
                .billId(bill.getId())
                .billNumber(billFromSearch.getBillNumber())
                .businessService(billFromSearch.getBusinessService())
                .approverUuid(approverUuid)
                .approverName(signature.getPrintedName().trim())
                .role(ROLE_PAYMENT_APPROVER)
                .signatureMethod(signature.getSignatureMethod())
                .signatureFileStoreId(signature.getSignatureFileStoreId())
                .approvalStatus(ApprovalStatus.APPROVED)
                .approvedTime(now)
                .auditDetails(auditDetails)
                .build();
    }

    /**
     * Persists a previously-validated BillApproval record. Must only be called AFTER payment
     * initiation has completed successfully, so the audit trail never records an approval that
     * didn't actually take effect.
     */
    public void recordApproval(BillApproval approval, RequestInfo requestInfo) {
        BillApprovalRequest approvalRequest = BillApprovalRequest.builder()
                .requestInfo(requestInfo)
                .billApproval(approval)
                .build();
        expenseProducer.push(approval.getTenantId(), config.getBillApprovalCreateTopic(), approvalRequest);
        log.info("Recorded bill approval signature for bill={} approver={}", approval.getBillId(), approval.getApproverUuid());
    }

    /**
     * Fetches approval history for the given bills and sets it on each Bill.approvals.
     */
    public void enrichBillsWithApprovals(List<Bill> bills, String tenantId) {
        if (bills == null || bills.isEmpty()) return;

        List<String> billIds = bills.stream().map(Bill::getId).toList();
        BillApprovalSearchCriteria criteria = BillApprovalSearchCriteria.builder()
                .billIds(billIds)
                .tenantId(tenantId)
                .build();

        List<BillApproval> approvals = billApprovalRepository.search(criteria);
        if (approvals.isEmpty()) return;

        Map<String, List<BillApproval>> byBillId = approvals.stream()
                .collect(Collectors.groupingBy(BillApproval::getBillId));

        for (Bill bill : bills) {
            bill.setApprovals(byBillId.getOrDefault(bill.getId(), Collections.emptyList()));
        }
    }

    private Set<String> extractRoles(RequestInfo requestInfo) {
        List<Role> rawRoles = requestInfo.getUserInfo() != null
                ? requestInfo.getUserInfo().getRoles() : null;
        if (rawRoles == null) return Collections.emptySet();
        return rawRoles.stream()
                .filter(r -> r != null && r.getCode() != null)
                .map(Role::getCode)
                .collect(Collectors.toSet());
    }
}
