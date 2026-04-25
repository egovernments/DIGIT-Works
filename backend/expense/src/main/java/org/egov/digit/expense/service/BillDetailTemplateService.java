package org.egov.digit.expense.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.BillDetailExcelGenerator;
import org.egov.digit.expense.util.BillDetailExcelParser;
import org.egov.digit.expense.util.FilestoreUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.egov.digit.expense.config.Constants.*;


@Service
@Slf4j
public class BillDetailTemplateService {

    private final BillRepository billRepository;
    private final BillDetailExcelGenerator excelGenerator;
    private final BillDetailExcelParser excelParser;
    private final BillService billService;
    private final FilestoreUtil filestoreUtil;

    @Autowired
    public BillDetailTemplateService(BillRepository billRepository,
                                     BillDetailExcelGenerator excelGenerator,
                                     BillDetailExcelParser excelParser,
                                     BillService billService,
                                     FilestoreUtil filestoreUtil) {
        this.billRepository  = billRepository;
        this.excelGenerator  = excelGenerator;
        this.excelParser     = excelParser;
        this.billService     = billService;
        this.filestoreUtil   = filestoreUtil;
    }

    public byte[] generateTemplateBytes(BillTemplateRequest request) {
        RequestInfo requestInfo = request.getRequestInfo();
        Bill bill = fetchBillOrThrow(request.getBillId(), request.getTenantId(), requestInfo);
        Set<String> roles = extractRoles(requestInfo);
        log.info("BillDetailTemplateService::generateTemplateBytes billId={} roles={}", request.getBillId(), roles);
        validateRoleAndBillStatus(roles, bill, "generate template");
        return excelGenerator.generateTemplate(bill, roles, requestInfo);
    }

    public BillDetailUpdateResponse processUpload(BillTemplateUploadRequest request) {
        String billId    = request.getBillId();
        String tenantId  = request.getTenantId();
        RequestInfo requestInfo = request.getRequestInfo();

        log.info("BillDetailTemplateService::processUpload billId={} filestoreId={}", billId, request.getFilestoreId());

        Bill bill = fetchBillOrThrow(billId, tenantId, requestInfo);
        Set<String> roles = extractRoles(requestInfo);
        validateRoleAndBillStatus(roles, bill, "upload template");

        byte[] excelBytes = filestoreUtil.downloadFile(request.getFilestoreId(), tenantId);

        List<PartialBillDetail> partialDetails = excelParser.parse(excelBytes, bill, roles, requestInfo);
        if (partialDetails.isEmpty()) {
            throw new CustomException(ERR_TEMPLATE_EMPTY, MSG_TEMPLATE_EMPTY);
        }

        BillDetailUpdateRequest updateRequest = BillDetailUpdateRequest.builder()
                .requestInfo(requestInfo)
                .billId(billId)
                .tenantId(tenantId)
                .billDetails(partialDetails)
                .build();

        return billService.partialUpdateBillDetails(updateRequest);
    }

    /**
     * Denies access unless the user holds PAYMENT_REVIEWER or PAYMENT_EDITOR,
     * and the bill is in a status that role is permitted to act on:
     *   PAYMENT_EDITOR   → PENDING_VERIFICATION or PARTIALLY_VERIFIED
     *   PAYMENT_REVIEWER → UNDER_REVIEW
     */
    private void validateRoleAndBillStatus(Set<String> roles, Bill bill, String operation) {
        boolean isEditor   = roles.contains(ROLE_PAYMENT_EDITOR);
        boolean isReviewer = roles.contains(ROLE_PAYMENT_REVIEWER);

        if (!isEditor && !isReviewer) {
            throw new CustomException(ERR_UNAUTHORIZED, MSG_UNAUTHORIZED_TEMPLATE_PREFIX + operation + ".");
        }

        String billStatus = bill.getStatus() != null ? bill.getStatus().toString() : null;
        Set<String> editorAllowed   = Set.of(STATUS_PENDING_VERIFICATION, STATUS_PARTIALLY_VERIFIED);
        Set<String> reviewerAllowed = Set.of(STATUS_UNDER_REVIEW);

        boolean editorStatusOk   = isEditor   && editorAllowed.contains(billStatus);
        boolean reviewerStatusOk = isReviewer && reviewerAllowed.contains(billStatus);

        if (!editorStatusOk && !reviewerStatusOk) {
            throw new CustomException(ERR_INVALID_BILL_STATUS,
                    "Cannot " + operation + ": the bill is not available for this action at its current stage.");
        }
    }

    private Bill fetchBillOrThrow(String billId, String tenantId, RequestInfo requestInfo) {
        BillSearchRequest searchRequest = BillSearchRequest.builder()
                .requestInfo(requestInfo)
                .billCriteria(BillCriteria.builder()
                        .ids(new HashSet<>(List.of(billId)))
                        .tenantId(tenantId)
                        .build())
                .pagination(new Pagination())
                .build();
        List<Bill> bills = billRepository.search(searchRequest, true);
        if (bills.isEmpty()) {
            throw new CustomException(ERR_INVALID_BILL,
                    "Bill not found: id=" + billId + " tenantId=" + tenantId);
        }
        return bills.get(0);
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
