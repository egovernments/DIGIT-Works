// config.js
// const env = process.env.NODE_ENV; // 'dev' or 'test'

HOST = process.env.EGOV_HOST;


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
  PDF_BATCH_SIZE: process.env.PDF_BATCH_SIZE || 40,
  DB_USER: process.env.DB_USER || "postgres",
  DB_PASSWORD: process.env.DB_PASSWORD || "postgres",
  DB_HOST: process.env.DB_HOST || "localhost",
  DB_NAME: process.env.DB_NAME || "digit-works",
  DB_PORT: process.env.DB_PORT || 5432,
  pdf: {
    epass_pdf_template: process.env.EPASS_TEMPLATE || "tlcertificate",
    project_details_template:
      process.env.PROJECT_DETAILS_TEMPLATE || "project-detail",
    estimate_template: process.env.ESTIMATE_TEMPLATE || "estimate",
    nominal_muster_roll_template:
      process.env.NOMINAL_MUSTER_ROLL_TEMPLATE || "nominal-muster-roll",
    work_order_template:
      process.env.WORK_ORDER_TEMPLATE || "work-order",
    work_order_template_hindi:
      process.env.WORK_ORDER_TEMPLATE_HINDI || "work-order-hindi",
  },
  app: {
    port: parseInt(process.env.APP_PORT) || 8080,
    host: HOST,
    contextPath: process.env.CONTEXT_PATH || "/egov-pdf",
  },
  host: {
    mdms: process.env.EGOV_MDMS_HOST || 'http://localhost:8083',
    epass: process.env.EGOV_TLSERVICES_HOST || HOST,
    pdf: process.env.EGOV_PDF_HOST || 'http://localhost:8082',
    user: process.env.EGOV_USER_HOST || HOST,
    workflow: process.env.EGOV_WORKFLOW_HOST || HOST,
    projectDetails: process.env.EGOV_PROJECT_HOST || 'http://localhost:8081/',
    estimates: process.env.EGOV_ESTIMATE_HOST || 'http://localhost:8084/',
    musterRoll: process.env.EGOV_MUSTER_ROLL_HOST || 'http://localhost:8085',
    contract: process.env.EGOV_CONTRACT_HOST || 'http://localhost:8086',
    organisation: process.env.EGOV_ORGANISATION_HOST || 'http://localhost:8087',
    expense: process.env.EXPENSE_SERVICE_HOST || 'http://localhost:8090',
    bankaccount: process.env.BANKACCOUNT_SERVICE_HOST || 'http://localhost:8091',
    filestore: process.env.EGOV_FILESTORE_SERVICE_HOST || 'http://localhost:8092',
  },
  paths: {
    pdf_create: "/pdf-service/v1/_createnosave",
    epass_search: "/tl-services/v1/_search",
    user_search: "/user/_search",
    mdms_search: "/egov-mdms-service/v1/_search",
    download_url: "/download/epass",
    workflow_search: "/egov-workflow-v2/egov-wf/process/_search",
    projectDetails_search: "/pms/project/v1/_search",
    estimate_search: "/estimate-service/estimate/v1/_search",
    musterRoll_search: "/muster-roll/v1/_search",
    contract_search: "/contract-service/contract/v1/_search",
    mdmsWageSeekerSkills_search: "/egov-mdms-service/v1/_get",
    orgnisation_search: "/org-services/organisation/v1/_search",
    expense_bill_search: "/expense/bill/v1/_search",
    bankaccount_search: "/bankaccount-service/bankaccount/v1/_search"
  },
};
