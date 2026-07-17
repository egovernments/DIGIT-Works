-- Rename report type values to descriptive names
UPDATE eg_expense_bill_report SET type = 'TRANSACTION_REPORT_EXCEL' WHERE type = 'EXCEL';
UPDATE eg_expense_bill_report SET type = 'TRANSACTION_REPORT_PDF'   WHERE type = 'PDF';
UPDATE eg_expense_bill_report SET type = 'PAYMENT_ADVISORY_EXCEL'   WHERE type = 'PAYMENT_ADVISORY';
