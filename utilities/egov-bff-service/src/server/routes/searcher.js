



  var express = require("express");
var router = express.Router();
var url = require("url");
var config = require("../config");
var fs  = require("fs");
var { logger } = require( "../logger");


const { asyncMiddleware } = require("../utils/asyncMiddleware");
const { throwError, sendResponse } = require("../utils");

let dataConfigUrls = config.configs.DATA_CONFIG_URLS || [];
let dataConfigMap = {};



const initialise=async ()=>{
    var i = 0;
    
dataConfigUrls &&
dataConfigUrls.split&&
  dataConfigUrls.split(",").map((item) => {
    item = item.trim();
    if (item.includes("file://")) {
      item = item.replace("file://", "");
      fs.readFile(item, "utf8", function (err, data) {
        try {
          if (err) {
            logger.error(
              "error when reading file for dataconfig: file:///" + item
            );
            logger.error(err.stack);
          } else {
            data = JSON.parse(data);
            dataConfigMap[data.key] = data;
            /*if (data.fromTopic != null) {
              topicKeyMap[data.fromTopic] = data.key;
              topic.push(data.fromTopic);
            }*/
            i++;
            // if (i == datafileLength) {
            //   topic.push(envVariables.KAFKA_RECEIVE_CREATE_JOB_TOPIC)
            //   listenConsumer(topic);
            // }
            logger.info("loaded dataconfig: file:///" + item);
            return dataConfigMap;
          }
        } catch (error) {
          logger.error("error in loading dataconfig: file:///" + item);
          logger.error(error.stack);
        }
      });
    } else {
      (async () => {
        try {
          var response = await axios.get(item);
          dataConfigMap[response.data.key] = response.data;
          logger.info("loaded dataconfig: " + item);
        } catch (error) {
          logger.error("error in loading dataconfig: " + item);
          logger.error(error.stack);
        }
      })();
    }
  });
}
initialise();
router.post(
  "/getMusterDetails",
  asyncMiddleware(async function (req, res, next) {
     
    var tenantId = req.query.tenantId;
    var musterRollNumber = req.query.musterRollNumber;
    var requestinfo = req.body;
    var resProperty;
    if (requestinfo == undefined || Object.keys(requestinfo).length == 0) {
      throwError(`Requestinfo can not be null`, "REQUEST_INFO_NOT_NULL", 400);
    }
    if (!tenantId || !musterRollNumber) {
      throwError(
        "tenantId and musterRollNumber are mandatory to get the muster details",
        "MISSING_MANDATORY_DETAILS",
        400
      );
    }
    try {
        var key=req.query.key||"consolidatedreceipt";
        var dataconfig = dataConfigMap[key];
        var baseKeyPath = get(dataconfig, "DataConfigs.baseKeyPath");
        var entityIdPath = get(dataconfig, "DataConfigs.entityIdPath");
        if (baseKeyPath == null) {
          logger.error("baseKeyPath is absent in config");
          throw {
            message: `baseKeyPath is absent in config`
          };
        }


     console.log(dataconfig,dataConfigMap,dataConfigUrls,'dataconfig');

     return await prepareBulk(
        key,
        dataconfig,
        req,
        baseKeyPath,
        requestInfo
      );
        sendResponse(res,{success:"sssss",config},req,200);
    } catch (ex) {
      throwError(ex.message, ex.code, ex.status);
    }
  })
);

module.exports = router;
