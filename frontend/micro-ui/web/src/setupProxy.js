const { createProxyMiddleware } = require("http-proxy-middleware");
const createProxy = createProxyMiddleware({
  target: process.env.REACT_APP_PROXY_URL,
  changeOrigin: true,
});
module.exports = function (app) {
  [
    "/egov-mdms-service",
    "/egov-location",
    "/localization",
    "/egov-workflow-v2",
    "/pgr-services",
    "/contract-service",
    "/filestore",
    "/egov-hrms",
    "/user-otp",
    "/user",
    "/fsm",
    "/billing-service",
    "/collection-services",
    "/pdf-service",
    "/pg-service",
    "/vehicle",
    "/vendor",
    "/property-services",
    "/fsm-calculator/v1/billingSlab/_search",
    "/muster-roll",
    "/project",
    "/inbox/v2/_search",
    "/individual",
    "/org-services",
    "/wms/contract/_search",
    "/contract-service",
    "/expensebilling",
    "/bankaccount-service",
    "/expense-calculator/v1/_estimate"
  ].forEach((location) =>
    app.use(location, createProxy)
  );
};
