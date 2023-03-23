var express = require("express");
var router = express.Router();
var url = require("url");
var config = require("../config");

var { search_muster, search_individual, search_localization } = require("../api");

const { asyncMiddleware } = require("../middlewares/asyncMiddleware");
const { throwError, sendResponse } = require("../utils");

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
      resProperty = await search_muster(
        musterRollNumber,
        tenantId,
        requestinfo
      );
      var musterRolls = resProperty.musterRolls;
      if (musterRolls && musterRolls.length > 0) {
        var musterObj = musterRolls[0] || {};
        var individualIds = musterObj.individualEntries.map(
          (ind) => ind.individualId
        );
        var individualId = musterObj.individualEntries[0].individualId;
        var paymentresponse;
        paymentresponse = await search_individual(
          individualIds,
          tenantId,
          requestinfo
        );
        var payments = paymentresponse;
        if (payments && payments.Individual && payments.Individual.length > 0) {
          var Individual = {};
          payments.Individual.map((ind) => {
            Individual[ind.id] = {
              individualId: ind.individualId,
              name: ind.name.givenName,
              fatherName: ind.fatherName,
              identifierId: ind.identifiers[0].identifierId,
              skills: ind.skills.map((skill) => `${skill.level}.${skill.type}`),
            };
          });

          sendResponse(res, { musterRolls, individual: Individual },req);
        } else {
          throwError("individuals not exists", "INDIVIDUAL_NOT_FOUND", 400);
        }
      } else {
        throwError("muster roll not found", "MUSTERROLL_NOT_FOUND", 400);
      }
    } catch (ex) {
      throwError(ex.message, ex.code, ex.status);
    }
  })
);

router.post(
  "/getLocalization",
  asyncMiddleware(async function (req, res, next) {
    var tenantId = req.query.tenantId;
    var module = req.query.module;
    var locale = req.query.locale;
    const locResponse = await search_localization(
      tenantId,
      module,
      locale,
      req.body
    );
    if(!locResponse|| !locResponse?.messages || locResponse?.messages?.length==0){
      throwError("LOCALISATION NOT FOUND","LOCALISATION NOT FOUND",400)
    }

    // let messages={}
    // locResponse.messages.map(message=>{messages[message.code]=message.message})
  
    let messages=locResponse.messages.map(message=>({code:message.code,message:message.message}))
    sendResponse(res,{messages},req);
  })
)

module.exports = router;
