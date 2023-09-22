import { NextFunction, Request, Response } from "express";

import { logger } from "../logger";
const NodeCache = require("node-cache");
const jp = require('jsonpath');

/*
  stdTTL: (default: 0) the standard ttl as number in seconds for every generated
   cache element. 0 = unlimited

  checkperiod: (default: 600) The period in seconds, as a number, used for the automatic
   delete check interval. 0 = no periodic check.

*/
const appCache = new NodeCache({ stdTTL: 100, checkperiod: 120 });

/* 
Send The Error Response back to client with proper response code 
*/
const throwError = (
  message = "Internal Server Error",
  code = "INTERNAL_SERVER_ERROR",
  status = 500
) => {
  let error = new Error(message);
  //   error.status = status;
  //   error.code = code;
  console.log(error, 'error');

  throw error;
};

/* 
Error Object
*/
const getErrorResponse = (
  code = "INTERNAL_SERVER_ERROR",
  message = "Some Error Occured!!"
) => ({
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
const sendResponse = (res: Response, response: Response, req: Request, code: number) => {
  if (code != 304) {
    appCache.set(req.originalUrl, { ...response });
  } else {
    logger.info("CACHED RESPONSE FOR :: " + req.originalUrl);
  }
  res.status(200).send({
    ...getResponseInfo(code),
    ...response,
  });
};

/* 
Response Object
*/
const getResponseInfo = (code: Number) => ({
  ResponseInfo: {
    apiId: "bff-0.0.1",
    ver: "1",
    ts: new Date().getTime(),
    status: "successful",
    desc: code == 304 ? "cached-response" : "new-response",
  },
});

/* 
Fallback Middleware function for returning 404 error for undefined paths
*/
const invalidPathHandler = (request: any, response: any, next: NextFunction) => {
  response.status(404);
  response.send(getErrorResponse("INVALID_PATH", "invalid path"));
};

/*
Error handling Middleware function for logging the error message
*/
const errorLogger = (error: Error, request: any, response: any, next: NextFunction) => {
  logger.error(error.stack);
  logger.error(`error ${error.message}`);
  next(error); // calling next middleware
};

/*
Error handling Middleware function reads the error message and sends back a response in JSON format
*/
const errorResponder = (error: any, request: any, response: Response, next: NextFunction) => {
  response.header("Content-Type", "application/json");
  const status = 500;
  response.status(status).send(getErrorResponse("INTERNAL_SERVER_ERROR", error.message));
};

const convertObjectForMeasurment = (obj: any, config: any) => {
  const resultBody: Record<string, any> = {};
  config.forEach((configObj: any) => {
    const { path, jsonPath } = configObj;
    const jsonPathValue = jp.query(obj, jsonPath);

    // Assign jsonPathValue to the corresponding property in resultBody
    resultBody[path] = jsonPathValue;
  });
  return resultBody;
}

// Extract estimateIds from all contracts
const extractEstimateIds = (contractResponse: any): any[] => {
  const allEstimateIds = [];
  for (const contract of contractResponse.contracts) {
    const contractEstimateIds = contract.lineItems.map((item: { estimateId: any; }) => item.estimateId);
    allEstimateIds.push(...contractEstimateIds);
  }
  return allEstimateIds;
}

export {
  errorResponder,
  errorLogger,
  invalidPathHandler,
  getResponseInfo,
  throwError,
  sendResponse,
  appCache,
  convertObjectForMeasurment,
  extractEstimateIds
};