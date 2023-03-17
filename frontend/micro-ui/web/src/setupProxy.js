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
    "/contract-service",
    "/fsm-calculator/v1/billingSlab/_search",
    "/muster-roll",
    "/pms/project",
    "/inbox/v2/_search",
    "/individual/v1/_search",
    "/org-services",
    "/expensebilling"
  ].forEach((location) =>
    app.use(location, createProxy)
  );
};
