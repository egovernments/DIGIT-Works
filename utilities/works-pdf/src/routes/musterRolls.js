var express = require("express");
var router = express.Router();
var url = require("url");
var config = require("../config");

var { search_musterRoll, create_pdf, search_localization } = require("../api");
var {searchEstimateFormusterRoll,create_pdf  }= require("../api");
var { search_contract, create_pdf } = require("../api");
var { search_mdmsV2, create_pdf } = require("../api");
var { search_individual, create_pdf } = require("../api");
const { asyncMiddleware } = require("../utils/asyncMiddleware");
const { calculateAttendenceDetails, calculateAttendenceTotal, getDateMonth } = require("../utils/calculateMusterData");
const get = require("lodash.get");

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
        var musterRollId = req.query.musterRollId;
        
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
                var contractId = resMuster.data.musterRolls[0].referenceId
                resContract = await search_contract(tenantId, requestinfo, contractId);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the contract service", 500);
            }
            try {

                resMdms = await search_mdmsV2(tenantId);

            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the mdms v2 service", 500);
            }
            try {
                individualIds = resMuster.data.musterRolls[0].individualEntries.map(ind => ind.additionalDetails.userId);
                resIndividuals = await search_individual(individualIds, tenantId, requestinfo);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the individual service", 500);
            }
            var muster = resMuster.data;
            muster.musterRolls[0] = enrichIndividualEntries(muster.musterRolls[0], resIndividuals.data.Individual);
            muster.musterRolls[0] = filterIndividualEntries(muster.musterRolls[0]);

            function enrichIndividualEntries(muster_roll, individuals) {
                if (Array.isArray(muster_roll.individualEntries)) {
                  muster_roll.individualEntries = muster_roll.individualEntries.map(entry => {
                    const individual = individuals.find(i => i.individualId === entry.additionalDetails.userId);
                    const additionalDetails = {
                        fatherName: individual.fatherName,
                        userName: individual.name.givenName,
                        gender: individual.gender,
                        mobileNumber: individual.mobileNumber,
                        userId: individual.individualId,
                        skillCode: entry.additionalDetails.skillCode,
                    }
                    return { ...entry, additionalDetails };
                  })
                }
                return muster_roll;
            }

            function filterIndividualEntries(muster_roll) {
                if (Array.isArray(muster_roll.individualEntries)) {
                  muster_roll.individualEntries = muster_roll.individualEntries.filter(entry => {
                    const additionalDetails = get(entry, 'additionalDetails', {});
                    return additionalDetails.hasOwnProperty('skillCode') && !additionalDetails.hasOwnProperty('skillValue');
                  });
                }
                return muster_roll;
            }
            var contract = resContract.data;
            var mdms = get(resMdms, 'data.MdmsRes.WORKS-SOR.Rates', []);
            
            // Get estimate using expense calculator
            let estimateCalc = {}
            try {
                var esrequestinfo = {}
                var musterRollId = muster.musterRolls[0].id;
                let criteria = {
                    "tenantId": tenantId,
                    "musterRollId": [musterRollId]
                }
                esrequestinfo['criteria'] = criteria
                esrequestinfo['RequestInfo'] = requestinfo["RequestInfo"]
                let calculateEstResp = await searchEstimateFormusterRoll(esrequestinfo);
                estimateCalc = calculateEstResp?.data?.calculation || {};
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the calculate estimate.", 500);
            }
            let lang = "en_IN";
            let localizationMap = {};
            try {
                let localizationReq = {};
                localizationReq['RequestInfo'] = requestinfo["RequestInfo"];
                let msgId = get(requestinfo, "RequestInfo.msgId", null)
                if (msgId) {
                    msgId = msgId.split("|")
                    lang = msgId.length == 2 ? msgId[1] : lang;
                }
                let module = "rainmaker-common,rainmaker-common-masters";
                let localizations = await search_localization(localizationReq, lang, module,tenantId );
                if (localizations?.data?.messages?.length) {
                    localizations.data.messages.forEach(localObj => {
                        localizationMap[localObj.code] = localObj.message;
                    });
                }
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
            }
            
            if (muster && muster.musterRolls && muster.musterRolls.length > 0 && contract && contract.contracts && mdms && mdms.length > 0) {

                var pdfResponse;
                var pdfkey = config.pdf.nominal_muster_roll_template;
                if (contract.contracts.length > 0) {
                    muster.musterRolls[0].rollOfCbo = contract.contracts[0].executingAuthority;
                    muster.musterRolls[0].projectDesc = contract.contracts[0].additionalDetails.projectDesc;
                    muster.musterRolls[0].cboName = contract.contracts[0].additionalDetails.cboName;
                    muster.musterRolls[0].projectId = contract.contracts[0].additionalDetails.projectId;
                    muster.musterRolls[0].totalWageAmount = estimateCalc?.totalAmount;
                }
                else {
                    muster.musterRolls[0].rollOfCbo = 'NA';
                    muster.musterRolls[0].projectDesc = 'NA';
                    muster.musterRolls[0].cboName = 'NA';
                    muster.musterRolls[0].projectId = 'NA'; 
                    muster.musterRolls[0].totalWageAmount= 'NA';                  
                }
                const createdTime = muster.musterRolls[0].auditDetails.createdTime;
                const filteredMdmsLabourCharges = mdms.filter(item => createdTime >= item.validFrom && (createdTime <= item.validTo || item.validTo == null));

                var labourCharges = filteredMdmsLabourCharges.reduce((modified, actual) => {
                    modified[actual.sorId] = actual.rate;
                    return modified;
                }, {})
                
                if (get(muster, "musterRolls[0].individualEntries[0].attendanceEntries")) {
                    // Sort by descending order
                    muster.musterRolls[0].individualEntries[0].attendanceEntries = muster.musterRolls[0].individualEntries[0].attendanceEntries.sort((a, b) => parseFloat(b.time) - parseFloat(a.time));
                    muster.musterRolls[0].individualEntries[0].attendanceEntries = muster.musterRolls[0].individualEntries[0].attendanceEntries.map((attendence => {
                        if (attendence?.time) {
                            attendence["dateMonth"] = getDateMonth(attendence.time)
                        }
                        return attendence;
                    }))
                }

                muster.musterRolls[0].attendanceDetails = calculateAttendenceDetails(muster.musterRolls[0].individualEntries, estimateCalc, labourCharges, localizationMap)
                muster.musterRolls[0].attendanceTotal = calculateAttendenceTotal(muster.musterRolls[0].individualEntries, estimateCalc)

                try {
                    pdfResponse = await create_pdf(
                        tenantId,
                        pdfkey,
                        muster,
                        requestinfo
                    )
                }
                catch (ex) {
                    if (ex.response && ex.response.data);

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