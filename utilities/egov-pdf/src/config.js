// config.js
// const env = process.env.NODE_ENV; // 'dev' or 'test'

HOST = process.env.EGOV_HOST || 'https://works-dev.digit.org/';

if (!HOST) {
  console.log("You need to set the HOST variable");
  process.exit(1);
}

module.exports = {
  auth_token: process.env.AUTH_TOKEN,
  KAFKA_BROKER_HOST: process.env.KAFKA_BROKER_HOST || "kafka-v2.kafka-cluster:9092",
  KAFKA_RECEIVE_CREATE_JOB_TOPIC: process.env.KAFKA_RECEIVE_CREATE_JOB_TOPIC || "PDF_GEN_RECEIVE",
  KAFKA_BULK_PDF_TOPIC: process.env.KAFKA_BULK_PDF_TOPIC || "BULK_PDF_GEN",
  PDF_BATCH_SIZE: process.env.PDF_BATCH_SIZE || 40,
  DB_USER: process.env.DB_USER || "postgres",
  DB_PASSWORD: process.env.DB_PASSWORD || "postgres",
  DB_HOST: process.env.DB_HOST || "localhost",
  DB_NAME: process.env.DB_NAME || "postgres",
  DB_PORT: process.env.DB_PORT || 5432,
  pdf: {
    epass_pdf_template: process.env.EPASS_TEMPLATE || "tlcertificate",
    project_details_template:
    process.env.PROJECT_DETAILS || "project-detail" 
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
    projectDetails: process.env.EGOV_PROJECT_HOST || 'http://localhost:8081/'
  },
  paths: {
    pdf_create: "/pdf-service/v1/_createnosave",
    epass_search: "/tl-services/v1/_search",
    user_search: "/user/_search",
    mdms_search: "/egov-mdms-service/v1/_search",
    download_url: "/download/epass",
    workflow_search: "/egov-workflow-v2/egov-wf/process/_search",
    projectDetails_search:"/pms/project/v1/_search"
  },
};
