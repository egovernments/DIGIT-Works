package org.egov.wms.config;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final List<String> PAYMENT_REPORT_HEADERS = Arrays.asList("Project ID", "Estimated amount (₹)","Wage payment successful (₹)", "Wage payment failed (₹)", "Purchase payment successful (₹)", "Purchase payment failed (₹)", "Supervision payment successful (₹)", "Supervision payment failed (₹)");
    public static final String EXPENSE_PURCHASE = "EXPENSE.PURCHASE";
    public static final String EXPENSE_WAGE = "EXPENSE.WAGES";
    public static final String EXPENSE_SUPERVISION = "EXPENSE.SUPERVISION";
}
