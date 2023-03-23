



  var express = require("express");
var router = express.Router();
var url = require("url");
var config = require("../config");
var fs  = require("fs");
var { logger } = require( "../logger");
import get from "lodash.get";
import { getValue } from "./utils/commons";
import { handlelogic } from "./utils/searcherUtils";
const { asyncMiddleware } = require("../middlewares/asyncMiddleware");
const { throwError, sendResponse } = require("../utils");
var jp = require("jsonpath");

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
            // logger.error(
            //   "error when reading file for dataconfig: file:///" + item
            // );
            // logger.error(err.stack);
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
            // logger.info("loaded dataconfig: file:///" + item);
            return dataConfigMap;
          }
        } catch (error) {
          // logger.error("error in loading dataconfig: file:///" + item);
          // logger.error(error.stack);
        }
      });
    } else {
      (async () => {
        try {
          var response = await axios.get(item);
          dataConfigMap[response.data.key] = response.data;
          // logger.info("loaded dataconfig: " + item);
        } catch (error) {
          // logger.error("error in loading dataconfig: " + item);
          // logger.error(error.stack);
        }
      })();
    }
  });
}
initialise();



const prepareBulk = async (
  key,
  dataconfig,
  // formatconfig,
  req,
  baseKeyPath,
  requestInfo,
  returnFileInResponse,
  entityIdPath
) => {
  console.log('peee');
  let isCommonTableBorderRequired = get(
    dataconfig,
    "DataConfigs.isCommonTableBorderRequired"
  );
  let formatObjectArrayObject = [];
  let formatConfigByFile = [];
  let totalobjectcount = 0;
  let entityIds = [];
  let countOfObjectsInCurrentFile = 0;
  let moduleObjectsArray = getValue(
    jp.query(req.body || req, baseKeyPath),
    [],
    baseKeyPath
  );
  if (Array.isArray(moduleObjectsArray) && moduleObjectsArray.length > 0) {
    totalobjectcount = moduleObjectsArray.length;
    for (var i = 0, len = moduleObjectsArray.length; i < len; i++) {
      let moduleObject = moduleObjectsArray[i];
      let entityKey = getValue(

        jp.query(moduleObject, entityIdPath),
        [null],
        entityIdPath
      );
      entityIds.push(entityKey[0]);

      // let formatObject = JSON.parse(JSON.stringify(formatconfig));

      // // Multipage pdf, each pdf from new page
      // if (
      //   formatObjectArrayObject.length != 0 &&
      //   formatObject["content"][0] !== undefined
      // ) {
      //   formatObject["content"][0]["pageBreak"] = "before";
      // }
      /////////////////////////////
      let  formatObject={};
      formatObject = await handlelogic(
        key,
        formatObject,
        moduleObject,
        dataconfig,
        isCommonTableBorderRequired,
        requestInfo
      );
      console.log(formatObject,'formatObject');

      formatObjectArrayObject.push(formatObject["content"]);
      countOfObjectsInCurrentFile++;
      if (
        (!returnFileInResponse &&
          countOfObjectsInCurrentFile == maxPagesAllowed) ||
        i + 1 == len
      ) {
        let formatconfigCopy = JSON.parse(JSON.stringify(formatconfig));
        
        let locale = requestInfo.msgId.split('|')[1];
        if(!locale)
          locale = envVariables.DEFAULT_LOCALISATION_LOCALE;

        if(defaultFontMapping[locale] != 'default')
         formatconfigCopy.defaultStyle.font = defaultFontMapping[locale];

        formatconfigCopy["content"] = formatObjectArrayObject;
        formatConfigByFile.push(formatconfigCopy);
        formatObjectArrayObject = [];
        countOfObjectsInCurrentFile = 0;
      }
    }
    return [formatConfigByFile, totalobjectcount, entityIds];
  } else {
    logger.error(
      `could not find property of type array in request body with name ${baseKeyPath}`
    );
    throw {
      message: `could not find property of type array in request body with name ${baseKeyPath}`,
    };
  }
};
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


    //  console.log(dataconfig,dataConfigMap,dataConfigUrls,'dataconfig');

     return await prepareBulk(
        key,
        dataconfig,
        req,
        baseKeyPath,
         req.body.RequestInfo,
         true,
         entityIdPath
      );
        sendResponse(res,{success:"sssss",config},req,200);
    } catch (ex) {
      throwError(ex.message, ex.code, ex.status);
    }
  })
);

module.exports = router;
