var Axios = require("axios").default;
var get = require("lodash/get");
var { logger } = require("../../logger");
var { throwError } = require("..");

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
      "BFF-SERVICE :: API SUCCESS :: " +
        _url +
        ":: RESPONSE CODE AS :: " +
        responseStatus
    );
    if (responseStatus === 200 || responseStatus === 201) {
      return response.data;
    }
  } catch (error) {
    var errorResponse = error.response;
    logger.error(
      "BFF-SERVICE :: API FAILURE :: " +
        _url +
        ":: RESPONSE CODE AS :: " +
        errorResponse.status +
        ":: ERROR STACK :: " +
        error.stack || error
    );

    throwError(
      "error occured while making request to " +
        _url +
        ": error response :" +
        (errorResponse ? parseInt(errorResponse.status, 10) : error.message),
      errorResponse.data.Errors[0].code,
      errorResponse.status
    );
  }
};

module.exports = { httpRequest };
