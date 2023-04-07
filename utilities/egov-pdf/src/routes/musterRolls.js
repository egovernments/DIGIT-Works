var express = require("express");
var router = express.Router();
var url = require("url");
var config = require("../config");

var { search_musterRoll, create_pdf } = require("../api");
var { search_contract, create_pdf } = require("../api");
var { search_mdmsWageSeekerSkills, create_pdf } = require("../api");
const { asyncMiddleware } = require("../utils/asyncMiddleware");

function renderError(res, errorMessage, errorCode) {
    if (errorCode == undefined) errorCode = 500;
    res.status(errorCode).send({ errorMessage })

}
router.post(
    "/muster-roll",
    asyncMiddleware(async function (req, res, next) {

        var tenantId = req.query.tenantId;
        var musterRollNumber = req.query.musterRollNumber;
        var requestinfo = req.body;
        if (requestinfo == undefined) {
            return renderError(res, "requestinfo can not be null", 400)
        }
        if (!tenantId) {
            return renderError(res, "tenantId is mandatory to generate the receipt", 400)
        }
        if (!musterRollNumber) {
            return renderError(res, "musterRollNumber is mandatory to generate the receipt", 400)
        }
        try {
            try {
                resMuster = await search_musterRoll(tenantId, requestinfo, musterRollNumber);


            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the muster roll", 500);
            }
            try {
                var contractId = resMuster.data.musterRolls[0].additionalDetails.contractId
                resContract = await search_contract(tenantId, requestinfo, contractId);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the contract service", 500);
            }
            try {

                resMdms = await search_mdmsWageSeekerSkills(tenantId, requestinfo);


            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the mdms service", 500);
            }
            var muster = resMuster.data;
            var contract = resContract.data;
            var mdms = resMdms.data.MdmsRes['common-masters'].WageSeekerSkills
            if (muster && muster.musterRolls && muster.musterRolls.length > 0 && contract && contract.contracts && contract.contracts.length > 0 && mdms && mdms.length > 0) {

                var pdfResponse;
                var pdfkey = config.pdf.nominal_muster_roll_template;
                muster.musterRolls[0].additionalDetails['rollOfCbo'] = contract.contracts[0].executingAuthority;
                muster.musterRolls[0].additionalDetails['projectDesc'] = contract.contracts[0].additionalDetails.projectDesc;
                muster.musterRolls[0].additionalDetails["cboName"] = contract.contracts[0].additionalDetails.cboName;
                muster.musterRolls[0].additionalDetails["projectId"] = contract.contracts[0].additionalDetails.projectId;
                var mdms = mdms.reduce((modified, actual) => {
                    modified[actual.code] = actual.amount;
                    return modified;
                }, {})
                muster.musterRolls[0].individualEntries = muster.musterRolls[0].individualEntries.map(individualEntrie => ({
                    ...individualEntrie, perDayWage: mdms[individualEntrie.additionalDetails.skillCode],
                    totalWage: individualEntrie.actualTotalAttendance * mdms[individualEntrie.additionalDetails.skillCode]
                }))
                try {

                    pdfResponse = await create_pdf(
                        tenantId,
                        pdfkey,
                        muster,
                        requestinfo
                    )
                }
                catch (ex) {
                    if (ex.response && ex.response.data) console.log(ex.response.data);

                    return renderError(res, "Failed to generate PDF for muster roll", 500);
                }

                var filename = `${pdfkey}_${new Date().getTime()}`;

                //pdfData = pdfResponse.data.read();
                res.writeHead(200, {
                    "Content-Type": "application/pdf",
                    "Content-Disposition": `attachment; filename=${filename}.pdf`,
                });
                pdfResponse.data.pipe(res);
            }
            else {
                return renderError(
                    res,
                    "There is no data found using this muster roll number",
                    404
                );
            }
        } catch (ex) {
            return renderError(res, "Failed to query details of the muster", 500);
        }

    })

);

module.exports = router;