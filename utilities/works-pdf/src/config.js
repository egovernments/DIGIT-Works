// config.js
// const env = process.env.NODE_ENV; // 'dev' or 'test'

HOST = process.env.EGOV_HOST || "localhost";


if (!HOST) {
  console.log("You need to set the HOST variable");
  process.exit(1);
}

module.exports = {
  auth_token: process.env.AUTH_TOKEN,
  KAFKA_BROKER_HOST: process.env.KAFKA_BROKER_HOST || "localhost:9092",
  KAFKA_RECEIVE_CREATE_JOB_TOPIC: process.env.KAFKA_RECEIVE_CREATE_JOB_TOPIC || "PDF_GEN_RECEIVE",
  KAFKA_BULK_PDF_TOPIC: process.env.KAFKA_BULK_PDF_TOPIC || "BULK_PDF_GEN",
  KAFKA_PAYMENT_EXCEL_GEN_TOPIC: process.env.KAFKA_PAYMENT_EXCEL_GEN_TOPIC || "PAYMENT_EXCEL_GEN",
  KAFKA_EXPENSE_PAYMENT_CREATE_TOPIC: process.env.KAFKA_EXPENSE_PAYMENT_CREATE_TOPIC || "expense-payment-create",
  PDF_BATCH_SIZE: process.env.PDF_BATCH_SIZE || 40,
  DB_USER: process.env.DB_USER || "postgres",
  DB_PASSWORD: process.env.DB_PASSWORD || "postgres",
  DB_HOST: process.env.DB_HOST || "localhost",
  DB_NAME: process.env.DB_NAME || "digit-works",
  DB_PORT: process.env.DB_PORT || 5432,
  pdf: {
    project_details_template:
      process.env.PROJECT_DETAILS_TEMPLATE || "project-detail",
    estimate_template: process.env.ESTIMATE_TEMPLATE || "estimate",
    nominal_muster_roll_template:
      process.env.NOMINAL_MUSTER_ROLL_TEMPLATE || "nominal-muster-roll",
    work_order_ip_template:
      process.env.WORK_ORDER_TEMPLATE || "work-order_ip",
    work_order_ia_template:
      process.env.WORK_ORDER_TEMPLATE || "work-order_ia",  
    work_order_template_hindi:
      process.env.WORK_ORDER_TEMPLATE_HINDI || "work-order-hindi",
    work_order_template_odiya_ia:
      process.env.WORK_ORDER_TEMPLATE_ODIYA_IA || "work-order-odiya_ia",
    work_order_template_odiya_ip:
      process.env.WORK_ORDER_TEMPLATE_ODIYA_IP || "work-order-odiya_ip",  
    deviationStatement_template: process.env.MEASUREMENT_TEMPLATE || "deviation-statement",
    measurement_template: process.env.MEASUREMENT_TEMPLATE || "measurement-book",
    detailedEstimate_template: process.env.DETAILED_ESTIMATE_TEMPLATE || "detailed-estimate",
    paymentTracker_template: process.env.PAYMENT_TRACKER_TEMPLATE || "payment-tracker",

    rateAnalysisStatement_template: process.env.RATE_ANALYSIS_TEMPLATE || "analysis-statement",
    rateAnalysisUtilization_template: process.env.RATE_ANALYSIS_TEMPLATE || "utilization-statement",

  },
  app: {
    port: parseInt(process.env.APP_PORT || 8098) ,
    host: HOST,
    contextPath: process.env.CONTEXT_PATH || "/egov-pdf",
  },
  host: {
    mdms: process.env.EGOV_MDMS_HOST || 'http://localhost:8083',
    pdf: process.env.EGOV_PDF_HOST || 'http://localhost:8091',
    user: process.env.EGOV_USER_HOST || HOST,
    workflow: process.env.EGOV_WORKFLOW_HOST || 'http://localhost:8094',
    projectDetails: process.env.EGOV_PROJECT_HOST || 'http://localhost:8082/',
    estimates: process.env.EGOV_ESTIMATE_HOST || 'http://localhost:8084/',
    musterRoll: process.env.EGOV_MUSTER_ROLL_HOST || 'http://localhost:8085',
    contract: process.env.EGOV_CONTRACT_HOST || 'http://localhost:8086',
    organisation: process.env.EGOV_ORGANISATION_HOST || 'http://localhost:8090',
    localization: process.env.EGOV_LOCALIZATION_HOST || 'http://localhost:8081',
    expense: process.env.EXPENSE_SERVICE_HOST || 'http://localhost:8087',
    bankaccount: process.env.BANKACCOUNT_SERVICE_HOST || 'http://localhost:8091',
    filestore: process.env.EGOV_FILESTORE_SERVICE_HOST || 'http://localhost:8092',
    expense_calculator: process.env.EXPENSE_CALCULATOR_SERVICE_HOST || 'http://localhost:8093',
    hrms: process.env.EGOV_HRMS_HOST || 'http://localhost:8095',
    measurements: process.env.EGOV_MEASUREMENT_HOST || 'http://localhost:8099',
    mdmsV2: process.env.EGOV_MDMS_V2_HOST || 'http://localhost:8088',
    statements: process.env.RATE_ANALYSIS_STATEMENTS_HOST || 'http://localhost:8089',
    paymentTracker: process.env.EGOV_WMS_HOST || 'http://localhost:8096',
    mukta_service: process.env.MUKTA_SERVICES_HOST || 'http://localhost:8097'
  },
  paths: {
    pdf_create: "/pdf-service/v1/_createnosave",
    user_search: "/user/_search",
    mdms_search: "/egov-mdms-service/v1/_search",
    workflow_search: "/egov-workflow-v2/egov-wf/process/_search",
    projectDetails_search: "/project/v1/_search",
    estimate_search: "/estimate/v1/_search",
    musterRoll_search: "/muster-roll/v1/_search",
    contract_search: "/contract/v1/_search",
    mdms_get: "/egov-mdms-service/v1/_get",
    orgnisation_search: "/org-services/organisation/v1/_search",
    expense_bill_search: "/expense/bill/v1/_search",
    expense_payment_search: "/expense/payment/v1/_search",
    bankaccount_search: "/bankaccount-service/bankaccount/v1/_search",
    expense_calculator_estimate: "/expense-calculator/v1/_estimate",
    expense_calculator_search: "/expense-calculator/v1/_search",
    localization_search: "/localization/messages/v1/_search",
    hrms_search: "/egov-hrms/employees/_search",
    deviationStatement_search: "/estimate/v1/_search",
    measurement_book_search: "/mukta-services/measurement/_search",
    mdmsV2_search: "/mdms-v2/v1/_search",
    analysis_statement_search: "/statements/v1/analysis/_search",
    analysis_utilization_search: "/statements/v1/utilization/_search",
    payment_instruction_search: "/wms/mukta-pi/_search",
    report_paymentTracker_search: "/wms/report/payment_tracker",
    mukta_service_get: "/mukta-services/mdmsV1/_get/_search",
    masked_ind_search: "/mukta-services/individual/v1/_search",
  },
  constraints: {
    "beneficiaryIdByHeadCode": "Deduction_{tanentId}_{headcode}"
  }
};
