package org.egov.digit.expense.calculator.util;

public class BillReportConstraints {
    public static final String REPORT_KEY = "reportDetails";
    public static final String REPORT_STATUS_KEY = "status";
    public static final String PDF_REPORT_ID_KEY = "pdfReportId";
    public static final String EXCEL_REPORT_ID_KEY = "excelReportId";
    public static final String ERROR_ERROR_MESSAGE_KEY = "errorMessage";
    public static final String REPORT_STATUS_INITIATED = "INITIATED";
    public static final String REPORT_STATUS_COMPLETED = "COMPLETED";
    public static final String REPORT_STATUS_FAILED = "FAILED";

    public static final String FOOD_HEAD_CODE = "FOOD";
    public static final String TRANSPORT_HEAD_CODE = "TRAVEL";
    public static final String PER_DIEM_HEAD_CODE = "PER_DAY";
    public static final String REPORT_HEADER = "PDF_STATIC_LABEL_REPORT_HEADER_NAME";

    public static final String[] BILL_EXCEL_COLUMNS = {
            "PDF_STATIC_LABEL_BILL_TABLE_SERIAL_NUMBER", "PDF_STATIC_LABEL_BILL_TABLE_INDIVIDUAL_NAME", "PDF_STATIC_LABEL_BILL_TABLE_ROLE",
            "PDF_STATIC_LABEL_BILL_TABLE_BOUNDARY_NAME", "PDF_STATIC_LABEL_BILL_TABLE_ID_NUMBER", "PDF_STATIC_LABEL_BILL_TABLE_MOBILE_NUMBER",
            "PDF_STATIC_LABEL_BILL_TABLE_WAGE", "PDF_STATIC_LABEL_BILL_TABLE_FOOD", "PDF_STATIC_LABEL_BILL_TABLE_TRANSPORTATION",
            "PDF_STATIC_LABEL_BILL_TABLE_TOTAL_WAGE", "PDF_STATIC_LABEL_BILL_TABLE_NUMBER_OF_DAYS", "PDF_STATIC_LABEL_BILL_TABLE_TOTAL_AMOUNT_TO_PAY"
    };
    public static int[] BILL_EXCEL_COLUMN_WIDTH = {
            6 * 256, 20 * 256, 15 * 256, 15 * 256,
            15 * 256, 15 * 256, 15 * 256, 15 * 256,
            15 * 256, 20 * 256, 20 * 256, 20 * 256
    };

    public static final String BILL_EXCEL_CAMPAIGN_NAME_LABEL = "PDF_STATIC_LABEL_BILL_CAMPAIGN_NAME";
    public static final String BILL_EXCEL_TOTAL_AMOUNT_TO_PROCESS_LABEL = "PDF_STATIC_LABEL_BILL_TOTAL_AMOUNT_TO_PROCESS";
    public static final String BILL_EXCEL_TOTAL_NUMBER_OF_WORKERS_LABEL = "PDF_STATIC_LABEL_BILL_TOTAL_NUMBER_OF_WORKERS";
    public static final String BILL_EXCEL_FOOTER_TOTAL_AMOUNT_LABEL = "PDF_STATIC_LABEL_BILL_TABLE_TOTAL_AMOUNT";

    public static final String BILL_EXCEL_FOOTER_PYMT_ADV_PREPARED_BY = "PDF_STATIC_LABEL_PYMT_ADV_PREPARED_BY";
    public static final String BILL_EXCEL_FOOTER_PYMT_ADV_VERIFIED_BY = "PDF_STATIC_LABEL_PYMT_ADV_VERIFIED_BY";
    public static final String BILL_EXCEL_FOOTER_PYMT_ADV_APPROVED_BY = "PDF_STATIC_LABEL_PYMT_ADV_APPROVED_BY";
    public static final String BILL_EXCEL_FOOTER_REPORT_RUN_DT_TIME = "PDF_STATIC_LABEL_REPORT_RUN_DT_TIME";
    public static final String BILL_EXCEL_FOOTER_REPORT_RUN_BY = "PDF_STATIC_LABEL_REPORT_RUN_BY";


}
