var express = require("express");
var router = express.Router();
var url = require("url");
var config = require("../config");

var {
  search_muster,
  search_individual,
} = require("../api");

const { asyncMiddleware } = require("../utils/asyncMiddleware");

function renderError(res, errorMessage, errorCode) {
  if (errorCode == undefined) errorCode = 500;
  res.status(errorCode).send({ errorMessage });
}

router.post(
  "/getMusterDetails",
  asyncMiddleware(async function (req, res, next) {
    var tenantId = req.query.tenantId;
    var musterRollNumber = req.query.musterRollNumber;
    var requestinfo = req.body;
    var resProperty;
    if (requestinfo == undefined) {
      return renderError(res, "requestinfo can not be null", 400);
    }
    if (!tenantId || !musterRollNumber) {
      return renderError(
        res,
        "tenantId and musterRollNumber are mandatory to get the muster details",
        400
      );
    }
    try {
      try {
        console.log(musterRollNumber, tenantId, requestinfo);
        resProperty = await search_muster(
          musterRollNumber,
          tenantId,
          requestinfo
        );
      } catch (ex) {
        if (ex.response && ex.response.data) console.log(ex.response.data);
        return renderError(res, "Failed to query details of the muster", 500);
      }
      var musterRolls = resProperty.data.musterRolls;
      if (musterRolls && musterRolls.length > 0) {
        var musterObj = musterRolls[0] || {};
        var individualIds = musterObj.individualEntries.map(
          (ind) => ind.individualId
        );
        var individualId = musterObj.individualEntries[0].individualId;
        var paymentresponse;
        try {
          paymentresponse = await search_individual(
            individualIds,
            tenantId,
            requestinfo
          );
        } catch (ex) {
          if (ex.response && ex.response.data) console.log(ex.response.data);
          return renderError(res, `Failed to query payment for property`, 500);
        }
        var payments = paymentresponse.data;
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
          res.status(200).send({
            ResponseInfo: {
              apiId: requestinfo?.apiId,
              ver: "1",
              ts: new Date().getTime(),
              resMsgId: "JAGAN",
              status: "successful",
            },
            musterRolls,
            individual: Individual,
          });

          //pdfData = pdfResponse.data.read();
          // res.writeHead(200, {
          //   "Content-Type": "application/json"          });
        } else {
          return renderError(res, "There is no payment for this id", 404);
        }
      } else {
        return renderError(
          res,
          "There is no property for you for this id",
          404
        );
      }
    } catch (ex) {
      return renderError(
        res,
        "Failed to query receipt details of the property",
        500
      );
    }
  })
);

module.exports = router;
