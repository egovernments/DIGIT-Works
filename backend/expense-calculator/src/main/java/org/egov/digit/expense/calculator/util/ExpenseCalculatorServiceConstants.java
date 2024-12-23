package org.egov.digit.expense.calculator.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExpenseCalculatorServiceConstants {
    public static final String MASTER_TENANTS = "tenants";
    public static final String FILTER_CODE = "$.*.code";
    public static final String MDMS_TENANT_MODULE_NAME = "tenant";
    public static final String MDMS_RESP_CONSTANT = "$.MdmsRes.";
    public static final String JSON_PATH_FOR_TENANTS_VERIFICATION = MDMS_RESP_CONSTANT + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";
    public static final String MUSTER_ROLL_ID_JSON_PATH = "$.musterRolls.*.id";
    public static final String WAGE_SEEKER_SKILLS = "WageSeekerSkills";
    public static final String PAYER_MASTER = "PayerList";
    public static final String EXPENSE_MODULE = "expense";
    public static final String MDMS_HEAD_CODES = "HeadCodes";
    public static final String MDMS_APPLICABLE_CHARGES = "ApplicableCharges";
    public static final String MDMS_BUSINESS_SERVICE = "BusinessService";
    public static final String JSON_PATH_FOR_MDMS_BUSINESS_SERVICE =  "$.*.businessService";
    public static final String MDMS_LABOUR_CHARGES = "LabourCharges";
    public static final String MDMS_PAYER_LIST = "PayerList";
    public static final String MDMS_COMMON_ACTIVE_FILTER ="$.*.[?(@.active==true)]";
    public static final String MDMS_COMMON_MASTERS ="common-masters";
    public static final String JSON_PATH_FOR_PAYER_LIST = MDMS_RESP_CONSTANT+EXPENSE_MODULE + "." + PAYER_MASTER;
    public static final List<String> SUPERVISION_MASTER_NAMES = Collections
			.unmodifiableList(Arrays.asList(MDMS_HEAD_CODES, MDMS_BUSINESS_SERVICE, MDMS_APPLICABLE_CHARGES));
    public static final String BUSINESS_SERVICE_SUPERVISION = "works.supervision";
    public static final String BUSINESS_SERVICE_PURCHASE = "works.purchase";
    public static final String MDMS_EXPENSE_MASTERS ="expense";

    public static final String JSON_PATH_FOR_PAYER = MDMS_RESP_CONSTANT + MDMS_EXPENSE_MASTERS + "." + MDMS_PAYER_LIST + ".*";
    public static final String JSON_PATH_FOR_BUSINESS_SERVICE_VERIFICATION = MDMS_RESP_CONSTANT + MDMS_EXPENSE_MASTERS + "." + MDMS_BUSINESS_SERVICE + ".*";
    public static final String JSON_PATH_FOR_LABOUR_CHARGES = MDMS_RESP_CONSTANT+MDMS_EXPENSE_MASTERS + "." + MDMS_LABOUR_CHARGES;
    public static final String JSON_PATH_FOR_HEAD_CODES = MDMS_RESP_CONSTANT+MDMS_EXPENSE_MASTERS + "." + MDMS_HEAD_CODES;

    public static final String JSON_PATH_FOR_APPLICABLE_CHARGES = MDMS_RESP_CONSTANT+MDMS_EXPENSE_MASTERS + "." + MDMS_APPLICABLE_CHARGES;

    public static final String BILL_TYPE_WAGE = "WAGE";
    public static final String PAYEE_TYPE_SUPERVISIONBILL = "ORGANIZATION";
    public static final String CBO_IMPLEMENTATION_AGENCY = "IA";
    public static final String CBO_IMPLEMENTATION_PARTNER = "IP";
    public static final String HEAD_CODE_SUPERVISION = "SC";
    public static final String HEAD_CODE_LABOR_CESS = "LC";
    public static final String SUCCESSFUL_CONSTANT = "successful";
    public static final String SKILL_CODE_CONSTANT = "skillCode";
    public static final String PROJECT_ID_CONSTANT = "projectId";
    public static final String CONTRACT_ID_CONSTANT = "contractId";
    public static final String PROJECT_ID_OF_CONSTANT = "PROJECT_ID_OF_";
    public static final String CONCAT_CHAR_CONSTANT = "_";
    public static final String ORG_ID_CONSTANT = "ORG_ID";
    public static final String WF_SUBMIT_ACTION_CONSTANT = "SUBMIT";
    public static final String DEDUCTION_CONSTANT = "deduction";
    public static final String PERCENTAGE_CONSTANT = "percentage";
    public static final String LUMPSUM_CONSTANT = "lumpsum";
    public static final String EXPENSE_CONSTANT = "expense";
    public static final String DOCUMENTS_CONSTANT = "documents";
    public static final String LINEITEM_STATUS_ACTIVE = "ACTIVE";
    public static final String LINEITEM_STATUS_INACTIVE = "INACTIVE";
    public static final String FILTER_START = "[?(";
    public static final String FILTER_END = "')]";
    public static final String ID_SEARCH_CONSTANT = "@.id=='";
    public static final String ID_SEARCH_CONSTANT_RATE = "@.sorId=='";
    public static final String FILTER_OR_CONSTANT = "'||";
    public static final String WORKS_SOR_CONSTANT = "WORKS-SOR";
    public static final String SOR_CONSTANT = "SOR";
    public static final String RATES_CONSTANT = "Rates";
    public static final String JSON_PATH_FOR_SOR = MDMS_RESP_CONSTANT+WORKS_SOR_CONSTANT + "." + SOR_CONSTANT;
    public static final String JSON_PATH_FOR_RATES = MDMS_RESP_CONSTANT+WORKS_SOR_CONSTANT + "." + RATES_CONSTANT;
}