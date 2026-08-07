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
import org.springframework.web.multipart.MultipartFile;

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

    private static final Set<String> ALLOWED_SIGNATURE_CONTENT_TYPES =
            Set.of("image/png", "image/jpeg", "image/jpg");

    // Magic-byte signatures, checked against actual file content — the client-supplied
    // Content-Type header (checked above) can be spoofed, so it alone is not trustworthy.
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
     * Validates an uploaded/drawn signature image (format + size) and stores it in the
     * filestore, returning the fileStoreId to be echoed back in the approval request.
     */
    public String uploadSignature(MultipartFile file, String tenantId) {
        if (file == null || file.isEmpty()) {
            throw new CustomException(ERR_SIGNATURE_FILE_EMPTY, MSG_SIGNATURE_FILE_EMPTY);
        }

        long maxSize = config.getSignatureMaxSizeBytes();
        if (file.getSize() > maxSize) {
            throw new CustomException(ERR_SIGNATURE_FILE_TOO_LARGE,
                    MSG_SIGNATURE_FILE_TOO_LARGE_PREFIX + (maxSize / (1024 * 1024)) + "MB");
        }

        String contentType = file.getContentType();
        if (!StringUtils.hasText(contentType) || !ALLOWED_SIGNATURE_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new CustomException(ERR_SIGNATURE_FILE_INVALID_FORMAT, MSG_SIGNATURE_FILE_INVALID_FORMAT);
        }

        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (Exception e) {
            throw new CustomException(ERR_SIGNATURE_FILE_EMPTY, MSG_SIGNATURE_FILE_EMPTY);
        }

        if (!startsWith(bytes, PNG_MAGIC) && !startsWith(bytes, JPEG_MAGIC)) {
            throw new CustomException(ERR_SIGNATURE_FILE_CONTENT_MISMATCH, MSG_SIGNATURE_FILE_CONTENT_MISMATCH);
        }

        String fileName = StringUtils.hasText(file.getOriginalFilename())
                ? file.getOriginalFilename() : "signature.png";
        return filestoreUtil.upload(bytes, tenantId, fileName);
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

        // Confirm the fileStoreId actually belongs to this tenant before accepting it as the
        // approver's signature — without this, any caller could submit an arbitrary/foreign
        // fileStoreId that was never uploaded through /signature/_upload.
        try {
            filestoreUtil.downloadFile(signature.getSignatureFileStoreId(), bill.getTenantId());
        } catch (Exception e) {
            throw new CustomException(ERR_APPROVAL_SIGNATURE_FILE_NOT_FOUND, MSG_APPROVAL_SIGNATURE_FILE_NOT_FOUND);
        }

        Set<String> roles = extractRoles(requestInfo);
        if (!roles.contains(ROLE_PAYMENT_APPROVER)) {
            throw new CustomException(ERR_APPROVAL_UNAUTHORIZED, MSG_APPROVAL_UNAUTHORIZED);
        }

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
