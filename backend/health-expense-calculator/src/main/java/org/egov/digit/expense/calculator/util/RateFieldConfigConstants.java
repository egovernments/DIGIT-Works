package org.egov.digit.expense.calculator.util;

import org.egov.digit.expense.calculator.web.models.RateFieldConfig;

import java.util.List;

public class RateFieldConfigConstants {

    // valueType identifiers
    public static final String VALUE_TYPE_FLAT       = "FLAT";
    public static final String VALUE_TYPE_PERCENTAGE = "PERCENTAGE";

    // paymentType identifiers
    public static final String PAYMENT_TYPE_PER_DAY   = "PER_DAY";
    public static final String PAYMENT_TYPE_ONE_TIME   = "ONE_TIME";
    public static final String PAYMENT_TYPE_PER_PERIOD = "PER_PERIOD";

    // Standard rateBreakup field keys
    public static final String FIELD_KEY_FOOD    = "FOOD";
    public static final String FIELD_KEY_TRAVEL  = "TRAVEL";
    public static final String FIELD_KEY_PER_DAY = "PER_DAY";

    // Bill / ReportBill JSON keys for standard fields
    public static final String BILL_AMOUNT_KEY_FOOD      = "totalFoodAmount";
    public static final String BILL_AMOUNT_KEY_TRANSPORT = "totalTransportAmount";
    public static final String BILL_AMOUNT_KEY_WAGE      = "totalWageAmount";

    // ReportBillDetail JSON keys for standard fields
    public static final String DETAIL_KEY_FOOD      = "foodAmount";
    public static final String DETAIL_KEY_TRANSPORT = "transportAmount";
    public static final String DETAIL_KEY_WAGE      = "wageAmount";

    // Excel column header localization keys for standard fields (per-day and total columns)
    public static final String COLUMN_LABEL_KEY_WAGE      = "PDF_STATIC_LABEL_BILL_TABLE_WAGE";
    public static final String COLUMN_LABEL_KEY_FOOD      = "PDF_STATIC_LABEL_BILL_TABLE_FOOD";
    public static final String COLUMN_LABEL_KEY_TRANSPORT = "PDF_STATIC_LABEL_BILL_TABLE_TRANSPORTATION";
    public static final String TOTAL_COLUMN_LABEL_KEY_WAGE      = "PDF_STATIC_LABEL_TOTAL_BILL_TABLE_TOTAL_WAGE";
    public static final String TOTAL_COLUMN_LABEL_KEY_FOOD      = "PDF_STATIC_TOTAL_LABEL_BILL_TABLE_FOOD";
    public static final String TOTAL_COLUMN_LABEL_KEY_TRANSPORT = "PDF_STATIC_LABEL_TOTAL_BILL_TABLE_TRANSPORTATION";

    // MDMS snapshot key stored in BillingPeriod.additionalDetails
    public static final String WORKER_RATES_SNAPSHOT_KEY = "workerRatesSnapshot";

    /**
     * Default fieldConfig used when WorkerMdms has no fieldConfig (legacy MDMS data).
     * Produces identical line items and JSON keys as the original hardcoded behavior.
     */
    public static final List<RateFieldConfig> DEFAULT_FIELD_CONFIGS = List.of(
            RateFieldConfig.builder()
                    .fieldKey(FIELD_KEY_FOOD).valueType(VALUE_TYPE_FLAT).paymentType(PAYMENT_TYPE_PER_DAY)
                    .billAmountKey(BILL_AMOUNT_KEY_FOOD).reportDetailKey(DETAIL_KEY_FOOD)
                    .columnLabelKey(COLUMN_LABEL_KEY_FOOD).totalColumnLabelKey(TOTAL_COLUMN_LABEL_KEY_FOOD)
                    .isPayable(true).order(1).build(),
            RateFieldConfig.builder()
                    .fieldKey(FIELD_KEY_TRAVEL).valueType(VALUE_TYPE_FLAT).paymentType(PAYMENT_TYPE_PER_DAY)
                    .billAmountKey(BILL_AMOUNT_KEY_TRANSPORT).reportDetailKey(DETAIL_KEY_TRANSPORT)
                    .columnLabelKey(COLUMN_LABEL_KEY_TRANSPORT).totalColumnLabelKey(TOTAL_COLUMN_LABEL_KEY_TRANSPORT)
                    .isPayable(true).order(2).build(),
            RateFieldConfig.builder()
                    .fieldKey(FIELD_KEY_PER_DAY).valueType(VALUE_TYPE_FLAT).paymentType(PAYMENT_TYPE_PER_DAY)
                    .billAmountKey(BILL_AMOUNT_KEY_WAGE).reportDetailKey(DETAIL_KEY_WAGE)
                    .columnLabelKey(COLUMN_LABEL_KEY_WAGE).totalColumnLabelKey(TOTAL_COLUMN_LABEL_KEY_WAGE)
                    .isPayable(true).order(3).build()
    );
}
