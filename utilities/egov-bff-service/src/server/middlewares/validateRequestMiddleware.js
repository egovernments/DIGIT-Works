const { object, string } = require("yup");
const { errorResponder } = require("../utils");

const requestSchema = object({
  apiId: string().nullable(),
  action: string().nullable(),
  msgId: string().required(),
  authToken: string().nullable(),
  userInfo: object().nonNullable()
});

module.exports = (req, res, next) => {
  try {
    requestSchema.validateSync(req.body.RequestInfo);
    next();
  } catch (error) {
    error.status = 400;
    error.code = "MISSING_PARAMETERS_IN_REQUESTINFO";
    errorResponder(error, req, res);
  }
};
