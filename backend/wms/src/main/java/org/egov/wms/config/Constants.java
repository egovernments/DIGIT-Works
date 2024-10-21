package org.egov.wms.config;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final List<String> PAYMENT_REPORT_HEADERS = Arrays.asList("Project Id", "Estimated amount","Wage amount paid","Purchase amount paid", "Supervision amount paid", "Total amount paid");
    public static final String EXPENSE_PURCHASE = "EXPENSE.PURCHASE";
    public static final String EXPENSE_WAGE = "EXPENSE.WAGE";
    public static final String EXPENSE_SUPERVISION = "EXPENSE.SUPERVISION";
}
