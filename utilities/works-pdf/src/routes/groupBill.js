var express = require("express");
var router = express.Router();
var config = require("../config");
const { asyncMiddleware } = require("../utils/asyncMiddleware");
var logger = require("../logger").logger;
const uuidv4 = require("uuid/v4");
var producer = require("../producer").producer;
const { Pool } = require('pg');
const pool = new Pool({
  user: config.DB_USER,
  host: config.DB_HOST,
  database: config.DB_NAME,
  password: config.DB_PASSWORD,
  port: config.DB_PORT,
});

function renderError(res, errorMessage, errorCode) {
  if (errorCode == undefined) errorCode = 500;
  res.status(errorCode).send({ errorMessage });
}

router.post(
  "/_generate",
  asyncMiddleware(async function (req, res, next) {
    var tenantId = req.query.tenantId;
    var bussinessService = req.query.bussinessService;
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

    if (!requestinfo?.userInfo?.uuid) {
      return renderError(
        res,
        "uuid of useris missing."
      );
    }

    var id = uuidv4();
    var jobId = `${config.pdf.group_bill}-${new Date().getTime()}-${id}`;

    var kafkaData = {
      RequestInfo: requestinfo,
      tenantId: tenantId,
      jobId: jobId,
      Criteria: criteria
    };

    try {
      var payloads = [];
      payloads.push({
        topic: config.KAFKA_BULK_PDF_TOPIC,
        messages: JSON.stringify(kafkaData)
      });
      producer.send(payloads, function (err, data) {
        if (err) {
          logger.error(err.stack || err);
          errorCallback({
            message: `error while publishing to kafka: ${err.message}`
          });
        } else {
          logger.info("jobId: " + jobId + ": published to kafka successfully");
        }
      });

      try {
        const result = await pool.query('select * from eg_works_bill_gen where jobId = $1', [jobId]);
        if (result.rowCount < 1) {
          var userId = requestinfo?.userInfo?.uuid;
          const insertQuery = 'INSERT INTO eg_works_bill_gen(id, jobId, tenantId, userId, status, numberOfBills, numberOfBeneficialy, totalAmount, filestoreId, criteria, createdtime, lastmodifiedtime, endtime) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13)';
          const curentTimeStamp = new Date().getTime();
          const status = 'INPROGRESS';
          await pool.query(insertQuery, [id, jobId, tenantId, userId, status, 0, 0, 0, null, criteria, curentTimeStamp, curentTimeStamp, null]);
        }
      } catch (err) {
        logger.error(err.stack || err);
      }

      res.status(201);
      res.json({
        ResponseInfo: requestinfo,
        jobId: jobId,
        message: "Bulk bill creation is in process",
      });

    } catch (error) {
      console.log(error)
      return renderError(res, `Failed to query bill for water and sewerage application`);
    }

  })
);

router.post(
  "/_search",
  async (req, res) => {
    let requestInfo;
    try {
      let tenantid = req.query.tenantid;
      let jobId = req.query.jobId;
      requestInfo = req.body?.RequestInfo;
      let userId = requestInfo?.userInfo?.uuid;
      if (
        (userId == undefined || userId.trim() == "") &&
        (jobId == undefined || jobId.trim() == "")
      ) {
        res.status(400);
        res.json({
          ResponseInfo: requestInfo,
          message: "jobId and userId both can not be empty",
        });
      } else {
        if (jobId) {
          if (jobId.includes(",")) {
            jobId = jobId.split(",");
          } else {
            jobId = [jobId];
          }
        }

        getFileStoreIds(
          jobId,
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
  jobId,
  tenantId,
  userId,
  callback
) => {
  var searchquery = "";
  var queryparams = [];
  var next = 1;
  var jobIdPresent = false;
  searchquery = "SELECT * FROM eg_works_bill_gen WHERE";

  if (jobId != undefined && jobId.length > 0) {
    searchquery += ` jobId = ANY ($${next++})`;
    queryparams.push(jobId);
    jobIdPresent = true;
  }

  if (tenantId != undefined && tenantId.trim() !== "") {
    searchquery += ` and tenantId = ($${next++})`;
    queryparams.push(tenantId);
  }

  if (userId != undefined && userId.trim() !== "") {
    if (jobIdPresent) searchquery += " and";
    searchquery += ` userId = ($${next++})`;
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
            filestoreId: crow.filestoreid,
            jobId: crow.jobid,
            tenantId: crow.tenantid,
            createdtime: crow.createdtime,
            endtime: crow.endtime,
            totalcount: crow.totalcount,
            key: crow.key,
            documentType: crow.documenttype,
            moduleName: crow.modulename
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
