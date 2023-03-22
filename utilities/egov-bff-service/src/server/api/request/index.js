var Axios = require("axios").default;
var get = require("lodash/get");
var { logger } = require("../../logger");
const { throwError } = require("../../utils");

Axios.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err && !err.response) {
      err.response = {
        status: 400,
      };
    }
    if (err && err.response && !err.response.data) {
      err.response.data = {
        Errors: [{ code: err.message }],
      };
    }
    throw err;
  }
);

const defaultheader = {
  "content-type": "application/json;charset=UTF-8",
  accept: "application/json, text/plain, */*",
};

const getServiceName = (url = "") => url && url.slice && url.slice(url.lastIndexOf(url.split("/")[3]));

/*
 
Used to Make API call through axios library

  @author jagankumar-egov

 * @param {string} _url
 * @param {Object} _requestBody
 * @param {Object} _params
 * @returns {string} _method default to post
 * @returns {string} responseType
 * @param {Object} headers

*/
const httpRequest = async (
  _url,
  _requestBody,
  _params,
  _method = "post",
  responseType = "",
  headers = defaultheader
) => {
  try {
    const response = await Axios({
      method: _method,
      url: _url,
      data: _requestBody,
      params: _params,
      headers,
      responseType,
    });
    const responseStatus = parseInt(get(response, "status"), 10);
    logger.info(
      "INTER-SERVICE :: SUCCESS :: " +
        getServiceName(_url) +
        ":: CODE :: " +
        responseStatus
    );
    if (responseStatus === 200 || responseStatus === 201) {
      return response.data;
    }
  } catch (error) {
    var errorResponse = error.response;
    logger.error(
      "INTER-SERVICE :: FAILURE :: " +
        getServiceName(_url) +
        ":: CODE :: " +
        errorResponse.status +
        ":: ERROR :: " +
        errorResponse.data.Errors[0].code || error
    );
    logger.error(":: ERROR STACK :: " + error.stack || error);
    throwError(
      "error occured while making request to " +
        getServiceName(_url) +
        ": error response :" +
        (errorResponse ? parseInt(errorResponse.status, 10) : error.message),
      errorResponse.data.Errors[0].code,
      errorResponse.status
    );
  }
};

module.exports = { httpRequest };
