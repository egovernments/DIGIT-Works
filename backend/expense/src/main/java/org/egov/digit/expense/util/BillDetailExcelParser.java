package org.egov.digit.expense.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.LineItemType;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.digit.expense.util.BillDetailExcelGenerator.*;

@Component
@Slf4j
public class BillDetailExcelParser {

    /**
     * Parses an uploaded bill detail template Excel file and converts each data row
     * into a PartialBillDetail. Uses workerId (col 0) to identify the matching BillDetail.
     * Only fields editable by the user's role are populated; everything else is left null
     * so that enrichment preserves the DB value.
     */
    public List<PartialBillDetail> parse(byte[] excelBytes, Bill bill, Set<String> userRoles) {
        log.info("BillDetailExcelParser::parse billId={}", bill.getId());

        List<String> headCodes = collectPayableHeadCodes(bill);
        Map<String, BillDetail> workerToBillDetail = buildWorkerMap(bill);

        boolean isEditor   = userRoles.contains("PAYMENT_EDITOR");
        boolean isReviewer = userRoles.contains("PAYMENT_REVIEWER");

        List<PartialBillDetail> result = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelBytes))) {
            Sheet sheet = workbook.getSheetAt(0);
            int lastRow = sheet.getLastRowNum();

            for (int rowIdx = 1; rowIdx <= lastRow; rowIdx++) {
                Row row = sheet.getRow(rowIdx);
                if (row == null) continue;

                String workerId = readString(row, 0);
                if (workerId == null || workerId.isBlank()) {
                    log.warn("Row {} has no workerId — skipping", rowIdx);
                    continue;
                }

                BillDetail source = workerToBillDetail.get(workerId);
                if (source == null) {
                    throw new CustomException("EG_EXPENSE_TEMPLATE_INVALID_ROW",
                            "No BillDetail found for workerId=" + workerId + " in bill " + bill.getId());
                }

                PartialBillDetail.PartialBillDetailBuilder builder = PartialBillDetail.builder()
                        .id(source.getId());

                if (isEditor) {
                    Party updatedPayee = buildPayeeFromRow(row, source.getPayee());
                    builder.payee(updatedPayee);
                }

                if (isReviewer) {
                    BigDecimal totalAttendance = readBigDecimal(row, STATIC_COL_COUNT + headCodes.size());
                    if (totalAttendance == null || totalAttendance.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new CustomException("EG_EXPENSE_TEMPLATE_INVALID_ATTENDANCE",
                                "totalAttendance must be > 0 for workerId=" + workerId);
                    }
                    builder.totalAttendance(totalAttendance);

                    List<LineItem> updatedLineItems = buildLineItems(row, headCodes, source, totalAttendance);
                    builder.lineItems(updatedLineItems);

                    // Also populate payableLineItems with the PAYABLE subset so that
                    // EnrichmentUtil updates whichever field the DB record uses.
                    // mergeLineItems treats null as "preserve DB value", so we must explicitly
                    // pass the updated list — not null — to get amounts persisted.
                    List<LineItem> updatedPayableItems = updatedLineItems.stream()
                            .filter(li -> li.getType() == LineItemType.PAYABLE)
                            .collect(Collectors.toList());
                    builder.payableLineItems(updatedPayableItems);

                    BigDecimal totalAmount = computeTotalAmount(updatedLineItems);
                    builder.totalAmount(totalAmount);
                }

                result.add(builder.build());
            }
        } catch (CustomException e) {
            throw e;
        } catch (IOException e) {
            throw new CustomException("EG_EXPENSE_TEMPLATE_PARSE_ERROR", "Failed to read Excel file: " + e.getMessage());
        } catch (Exception e) {
            throw new CustomException("EG_EXPENSE_TEMPLATE_PARSE_ERROR", "Unexpected error parsing template: " + e.getMessage());
        }

        return result;
    }

    private Party buildPayeeFromRow(Row row, Party source) {
        Party.PartyBuilder builder = Party.builder();
        // Preserve immutable identity fields from the DB
        if (source != null) {
            builder.id(source.getId())
                   .parentId(source.getParentId())
                   .tenantId(source.getTenantId())
                   .type(source.getType())
                   .identifier(source.getIdentifier())
                   .paymentProvider(source.getPaymentProvider())
                   .status(source.getStatus())
                   .auditDetails(source.getAuditDetails())
                   .additionalDetails(source.getAdditionalDetails());
        }
        // Read editable payee fields
        builder.payeeName(readString(row, 3))
               .payeePhoneNumber(readString(row, 5))
               .bankAccount(readString(row, 6))
               .bankCode(readString(row, 7))
               .beneficiaryCode(readString(row, 8));
        return builder.build();
    }

    private List<LineItem> buildLineItems(Row row, List<String> headCodes,
                                          BillDetail source, BigDecimal totalAttendance) {
        // Build map from PAYABLE items (same source logic as generator)
        Map<String, LineItem> existingByHeadCode = new HashMap<>();
        for (LineItem item : getPayableItems(source)) {
            existingByHeadCode.put(item.getHeadCode(), item);
        }

        List<LineItem> result = new ArrayList<>();

        // Recalculate PAYABLE amounts from per-day wage × attendance
        for (int i = 0; i < headCodes.size(); i++) {
            String headCode = headCodes.get(i);
            BigDecimal perDayWage = readBigDecimal(row, STATIC_COL_COUNT + i);
            if (perDayWage == null) perDayWage = BigDecimal.ZERO;

            BigDecimal newAmount = perDayWage.multiply(totalAttendance).setScale(2, RoundingMode.HALF_UP);

            LineItem source_item = existingByHeadCode.get(headCode);
            if (source_item != null) {
                // Clone and update only amount
                result.add(LineItem.builder()
                        .id(source_item.getId())
                        .billDetailId(source_item.getBillDetailId())
                        .tenantId(source_item.getTenantId())
                        .headCode(source_item.getHeadCode())
                        .type(source_item.getType())
                        .amount(newAmount)
                        .paidAmount(source_item.getPaidAmount())
                        .status(source_item.getStatus())
                        .paymentStatus(source_item.getPaymentStatus())
                        .additionalDetails(source_item.getAdditionalDetails())
                        .auditDetails(source_item.getAuditDetails())
                        .build());
            }
        }

        // Preserve DEDUCTION line items unchanged (only from lineItems, not payableLineItems)
        if (source.getLineItems() != null) {
            source.getLineItems().stream()
                    .filter(li -> li.getType() == LineItemType.DEDUCTION)
                    .forEach(result::add);
        }

        return result;
    }

    private BigDecimal computeTotalAmount(List<LineItem> lineItems) {
        BigDecimal payable   = lineItems.stream()
                .filter(li -> li.getStatus() != Status.INACTIVE && li.getType() == LineItemType.PAYABLE)
                .map(LineItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal deduction = lineItems.stream()
                .filter(li -> li.getStatus() != Status.INACTIVE && li.getType() == LineItemType.DEDUCTION)
                .map(LineItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return payable.subtract(deduction);
    }

    private Map<String, BillDetail> buildWorkerMap(Bill bill) {
        if (bill.getBillDetails() == null) return Collections.emptyMap();
        return bill.getBillDetails().stream()
                .filter(d -> d.getWorkerId() != null)
                .collect(Collectors.toMap(BillDetail::getWorkerId, d -> d, (a, b) -> a));
    }

    private String readString(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING  -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default      -> null;
        };
    }

    private BigDecimal readBigDecimal(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null) return null;
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return BigDecimal.valueOf(cell.getNumericCellValue());
            }
            if (cell.getCellType() == CellType.STRING) {
                String val = cell.getStringCellValue().trim();
                return val.isEmpty() ? null : new BigDecimal(val);
            }
        } catch (Exception e) {
            log.warn("Could not parse numeric value at row={} col={}: {}", row.getRowNum(), col, e.getMessage());
        }
        return null;
    }
}
