var express = require("express");
var router = express.Router();
var config = require("../config");
const { asyncMiddleware } = require("../utils/asyncMiddleware");
var logger = require("../logger").logger;
const uuidv4 = require("uuid/v4");
var producer = require("../producer").producer ;


function renderError(res, errorMessage, errorCode) {
    if (errorCode == undefined) errorCode = 500;
    res.status(errorCode).send({ errorMessage });
}

router.post(
    "/_create",
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

      var id = uuidv4();
      var jobid = `${config.pdf.group_bill}-${new Date().getTime()}-${id}`;

      var kafkaData = {
        RequestInfo: requestinfo,
        tenantId: tenantId,
        jobid: jobid,
        Criteria: criteria
      };

      try {
        var payloads = [];
        payloads.push({
          topic: config.KAFKA_BULK_PDF_TOPIC,
          messages: JSON.stringify(kafkaData)
        });
        producer.send(payloads, function(err, data) {
          if (err) {
            logger.error(err.stack || err);
            errorCallback({
              message: `error while publishing to kafka: ${err.message}`
            });
          } else {
            logger.info("jobid: " + jobid + ": published to kafka successfully");
          }
        });

        // try {
        //   const result = await pool.query('select * from egov_bulk_pdf_info where jobid = $1', [jobid]);
        //   if(result.rowCount<1){
        //     var userid = requestinfo.RequestInfo.userInfo.uuid;
        //     const insertQuery = 'INSERT INTO egov_bulk_pdf_info(jobid, uuid, recordscompleted, totalrecords, createdtime, filestoreid, lastmodifiedby, lastmodifiedtime, tenantid, locality, businessservice, consumercode, isconsolidated, status) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14)';
        //     const curentTimeStamp = new Date().getTime();
        //     const status = 'INPROGRESS';
        //     await pool.query(insertQuery,[jobid, userid, 0, 0, curentTimeStamp, null, userid, curentTimeStamp, tenantId, locality, bussinessService, consumerCode, isConsolidated, status]);
        //   }
        // } catch (err) {
        //   logger.error(err.stack || err);
        // } 

        res.status(201);
        res.json({
          ResponseInfo: requestinfo,
          jobId:jobid,
          message: "Bulk bill creation is in process",
        });
        
      } catch (error) {
        console.log(error)
        return renderError(res, `Failed to query bill for water and sewerage application`);
      }

    })
  );
  
module.exports = router;
