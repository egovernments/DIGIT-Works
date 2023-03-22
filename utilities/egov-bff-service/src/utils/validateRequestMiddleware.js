const { errorResponder } = require(".");
const { object, string } = require("yup");

const requestSchema = object({
  apiId: string().nullable(),
  action: string().nullable(),
  msgId: string().required(),
  authToken: string().required(),
});

module.exports = (req, res, next) => {
  try {
    console.log(req,"req req");
    requestSchema.validateSync(req.body.RequestInfo);
    next();
  } catch (error) {
    error.status = 400;
    error.code = "MISSING_PARAMETERS_IN_REQUESTINFO";
    errorResponder(error, req, res);
  }
};
