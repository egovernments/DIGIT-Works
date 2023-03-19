var logger = require("../logger").logger;

/* 
Send The Error Response back to client with proper response code 
*/
export const throwError = (
  message = "",
  code = "INTERNAL_SERVER_ERROR",
  status = 500
) => {
  let error = new Error(message);
  error.status = status;
  error.code = code;
  throw error;
};

/* 
Error Object
*/
export const getErrorResponse = (code = "", message = "") => ({
  ResponseInfo: null,
  Errors: [
    {
      code: code,
      message: message,
      description: null,
      params: null,
    },
  ],
});

/* 
Send The Response back to client with proper response code and response info
*/
export const sendResponse = (res, response, code = 200) => {
  res.status(code).send({
    ...getResponseInfo(),
    ...response,
  });
};

/* 
Response Object
*/
export const getResponseInfo = () => ({
  ResponseInfo: {
    apiId: "bff-0.0.1",
    ver: "1",
    ts: new Date().getTime(),
    status: "successful",
  },
});

/* 
Fallback Middleware function for returning 404 error for undefined paths
*/
export const invalidPathHandler = (request, response, next) => {
  response.status(404);
  response.send(getErrorResponse("INVALID_PATH", "invalid path"));
};

/*
Error handling Middleware function for logging the error message
*/
export const errorLogger = (error, request, response, next) => {
  logger.error(`error ${error.message}`);
  next(error); // calling next middleware
};

/*
Error handling Middleware function reads the error message and sends back a response in JSON format
*/
export const errorResponder = (error, request, response, next) => {
  response.header("Content-Type", "application/json");
  const status = error.status || 500;
  response.status(status).send(getErrorResponse(error.code, error.message));
};
