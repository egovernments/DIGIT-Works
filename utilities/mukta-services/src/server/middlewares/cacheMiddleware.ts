import  { errorResponder, sendResponse, appCache } from "../utils/index";
import { NextFunction, Request,Response } from "express";

const cacheEnabled=false;

const cacheMiddleware= (req:Request, res:Response, next:NextFunction) => {
  try {    
    const cacheData = appCache.get(req.originalUrl);
    if (cacheData && cacheEnabled) {
      sendResponse(res, cacheData, req, 304);
    } else {
      next();
    }
  } catch (error) {
    // error.status = 400;
    // error.code = "MISSING_PARAMETERS_IN_REQUESTINFO";
    errorResponder(error, req, res , next);
  }
};


export default cacheMiddleware;