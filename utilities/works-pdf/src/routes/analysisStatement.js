const express = require("express");
const router = express.Router();

const data = require("./statementRes.json");
const {asyncMiddleware} = require("../utils/asyncMiddleware");
const {transformStatementData} = require("../utils/transformStatementData");

router.post("/rate-analysis-statement", asyncMiddleware(async function (req, res, next) {
    transformStatementData(data);
    console.log(statement)
}))

module.exports = router;
