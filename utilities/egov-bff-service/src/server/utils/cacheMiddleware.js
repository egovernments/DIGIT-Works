const { sendResponse, appCache } = require(".");

module.exports = (req, res, next) => {
  try {
    const cacheData = appCache.get(req.originalUrl+"3");
    if (cacheData) {
      sendResponse(res, cacheData, req, 304);
    } else {
      next();
    }
  } catch (error) {
    error.status = 400;
    error.code = "MISSING_PARAMETERS_IN_REQUESTINFO";
    errorResponder(error, req, res);
  }
};
