var express = require("express");
var router = express.Router();
var config = require("../config");
const { asyncMiddleware } = require("../utils/asyncMiddleware");
var logger = require("../logger").logger;
const { pool, search_payment_details, exec_query_eg_payments_excel, create_eg_payments_excel, reset_eg_payments_excel } = require("../api");
var producer = require("../producer").producer;

function renderError(res, errorMessage, errorCode) {
  if (errorCode == undefined) errorCode = 500;
  res.status(errorCode).send({ errorMessage });
}

router.post(
  "/_generate",
  asyncMiddleware(async function (req, res, next) {
    /**
     * TODO: integration with payment api is left, in query only payment id will be send, using payment id will fetch billids and then will trigger the request
     */
    var tenantId = req.query.tenantId;
    var requestinfo = req.body?.RequestInfo;
    var criteria = req.body?.Criteria;

    if (requestinfo == undefined) {
      return renderError(res, "Requestinfo can not be null");
    }
    if (criteria == undefined) {
      return renderError(res, "Criteria can not be null");
    }
    if (!tenantId) {
      return renderError(
        res,
        "TenantId are mandatory to generate the group bill"
      );
    }
    let paymentId = criteria?.paymentId;
    let billIds = criteria?.billIds;

    if (!paymentId) {
      return renderError(
        res,
        "paymentId is mandatory to generate the group bill"
      );
    }

    if (!requestinfo?.userInfo?.uuid) {
      return renderError(
        res,
        "uuid of useris missing."
      );
    }


    var kafkaData = {
      RequestInfo: requestinfo,
      tenantId: tenantId,
      paymentId: paymentId
    };

    try {
      let paymentRequest = {};
      paymentRequest['RequestInfo'] = requestinfo;
      paymentRequest.paymentCriteria = {
        "tenantId": tenantId,
        "ids": [paymentId]
      };
      let paymentResp = await search_payment_details(paymentRequest);
      if (!paymentResp?.payments?.length) {
        console.log(error)
        return renderError(res, `Failed to query for payment search. Check the Payment id.`);
      }
    } catch (error) {
      logger.error(error.stack || err);
      return renderError(res, `Failed to query for payment search.`);
    }


    try {
      var payloads = [];
      payloads.push({
        topic: config.KAFKA_PAYMENT_EXCEL_GEN_TOPIC,
        messages: JSON.stringify(kafkaData)
      });
      producer.send(payloads, function (err, data) {
        if (err) {
          logger.error(err.stack || err);
          errorCallback({
            message: `error while publishing to kafka: ${err.message}`
          });
        } else {
          logger.info("paymentId: " + paymentId + ": published to kafka successfully to generate excel.");
        }
      });

      try {
        const result = await exec_query_eg_payments_excel('select * from eg_payments_excel where paymentid = $1', [paymentId])
        var userId = requestinfo?.userInfo?.uuid;
        if (result.rowCount < 1) {
          await create_eg_payments_excel(paymentId, tenantId, userId);
        } else {
          await reset_eg_payments_excel(paymentId, userId);
        }
      } catch (err) {
        logger.error(err.stack || err);
      }

      res.status(201);
      res.json({
        ResponseInfo: requestinfo,
        paymentId: paymentId,
        message: `XLSX Bill creation is in process for payment ${paymentId}.`,
      });

    } catch (error) {
      logger.error(error.stack || err);
      return renderError(res, `Failed to query bill generate.`);
    }

  })
);

router.post(
  "/_search",
  async (req, res) => {
    let requestInfo;
    try {
      let tenantid = req.query.tenantid;
      let paymentId = req.query.paymentId;
      requestInfo = req.body?.RequestInfo;
      let userId = requestInfo?.userInfo?.uuid;
      if (
        (userId == undefined || userId.trim() == "") &&
        (paymentId == undefined || paymentId.trim() == "")
      ) {
        res.status(400);
        res.json({
          ResponseInfo: requestInfo,
          message: "paymentId and userId both can not be empty",
        });
      } else {
        if (paymentId) {
          if (paymentId.includes(",")) {
            paymentId = paymentId.split(",");
          } else {
            paymentId = [paymentId];
          }
        }
        delete requestInfo['authToken']
        delete requestInfo['userInfo']
        getFileStoreIds(
          paymentId,
          tenantid,
          userId,
          (responseBody) => {
            // doc successfully created
            res.status(responseBody.status);
            delete responseBody.status;
            res.json({
              ResponseInfo: requestInfo,
              ...responseBody
            });
          }
        );
      }
    } catch (error) {
      logger.error(error.stack || error);
      res.status(400);
      res.json({
        ResponseInfo: requestInfo,
        message: "some unknown error while searching: " + error.message,
      });
    }
  }
);

const getFileStoreIds = (
  paymentId,
  tenantId,
  userId,
  callback
) => {
  var searchquery = "";
  var queryparams = [];
  var next = 1;
  var paymentIdPresent = false;
  searchquery = "SELECT * FROM eg_payments_excel WHERE";

  if (paymentId != undefined && paymentId.length > 0) {
    searchquery += ` paymentId = ANY ($${next++})`;
    queryparams.push(paymentId);
    paymentIdPresent = true;
  }

  if (tenantId != undefined && tenantId.trim() !== "") {
    searchquery += ` and tenantId = ($${next++})`;
    queryparams.push(tenantId);
  }

  if (userId != undefined && userId.trim() !== "") {
    if (paymentIdPresent) searchquery += " and";
    searchquery += ` createdby = ($${next++})`;
    queryparams.push(userId);
  }
  
  pool.query(searchquery, queryparams, (error, results) => {
    if (error) {
      logger.error(error.stack || error);
      callback({
        status: 400,
        message: `error occured while searching records in DB : ${error.message}`
      });
    } else {
      if (results && results.rows.length > 0) {
        var searchresult = [];
        results.rows.map(crow => {
          searchresult.push({
            "id": crow.id,
            "paymentid": crow.paymentid,
            "tenantId": crow.tenantId,
            "status": crow.status,
            "numberofbills": crow.numberofbills,
            "numberofbeneficialy": crow.numberofbeneficialy,
            "totalamount": crow.totalamount,
            "filestoreid": crow.filestoreid,
            "createdby": crow.createdby,
            "lastmodifiedby": crow.lastmodifiedby,
            "createdtime": crow.createdtime,
            "lastmodifiedtime": crow.lastmodifiedtime,
          });
        });
        logger.info(results.rows.length + " matching records found in search");
        callback({ status: 200, message: "Success", searchresult });
      } else {
        logger.error("no result found in DB search");
        callback({ status: 404, message: "no matching result found" });
      }
    }
  });
};
module.exports = router;
