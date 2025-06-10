const express = require("express");
const router = express.Router();
const config = require("../config");


const {asyncMiddleware} = require("../utils/asyncMiddleware");
const {transformStatementData} = require("../utils/transformStatementData");
const {search_rateAnalysisUtilizationDetails, search_localization} = require("../api");
const {search_projectDetails_by_ID} = require("../api");
const {create_pdf} = require("../api");
const get = require("lodash.get");
const {getLocalizationByKey, getCityLocalizationPrefix, getStateLocalizationModule, getCityLocalizationModule} = require("../utils/localization");

function renderError(res, errorMessage, errorCode) {
    if (errorCode == undefined) errorCode = 500;
    res.status(errorCode).send({ errorMessage })

}

const desiredOrder = ["Material", "Labour", "Machinery"];

function sortData(data, order) {
    return data.sort((a, b) => {
        const indexA = order.indexOf(a.sorType);
        const indexB = order.indexOf(b.sorType);
        return indexA - indexB;
    });
}


function updateLocalization(pdfData, localizationMaps, tenantId) {
    if (pdfData.city) {
        pdfData.city = pdfData.city.toUpperCase();
        cityKey = "TENANT_TENANTS_" + pdfData.city.split(".").join("_");
        pdfData.city = getLocalizationByKey(cityKey, localizationMaps);
    }
    if (pdfData.locality) {
        let localityKey = getCityLocalizationPrefix(tenantId);
        localityKey = localityKey + "_ADMIN_" + pdfData.locality;
        pdfData.locality = getLocalizationByKey(localityKey, localizationMaps);
    }
    if (pdfData.ward) {
        let boundaryKey = getCityLocalizationPrefix(tenantId);
        boundaryKey = boundaryKey + "_ADMIN_" + pdfData.ward;
        pdfData.ward = getLocalizationByKey(boundaryKey, localizationMaps);
    }

    return pdfData;
}

router.post("/utilization-statement", asyncMiddleware(async function (req, res, next) {
    const requestInfo = req.body;
    const tenantId = req.query.tenantId;
    const referenceId = req.query.referenceId;
    if(requestInfo == undefined) {
        return renderError(res, "requestinfo can not be null", 400);
    }
    if(!tenantId){
        return renderError(res, "tenantId is mandatory to generate the receipt", 400);
    }
    if(!referenceId){
        return renderError(res, "referenceId is mandatory to generate the receipt", 400);
    }
    let projectData;
    try {
        try {
            analysisStatement = await search_rateAnalysisUtilizationDetails(tenantId, requestInfo, referenceId);
        } catch (ex) {
            return renderError(res, "Failed to query details of the rate analysis statement", 500);
        }
        var statementData = analysisStatement.data.statement;
        if (statementData.length <= 0) {
            return renderError(res, "Statement Not Found", 500);
        }
        try {
            const projectId = statementData[0].additionalDetails.projectId;
            project = await search_projectDetails_by_ID(tenantId, requestInfo, projectId);
        } catch (ex) {
            return renderError(res, "Failed to get project details", 500);
        }
        projectData = project.data.Project[0];
        const AnalysisStatement = transformStatementData(statementData, projectData);
        if (AnalysisStatement) {
            let lang = "en_IN";
            let localizationMap = {};
            try {
                let localizationReq = {};
                localizationReq['RequestInfo'] = requestInfo.RequestInfo;
                let msgId = requestInfo.RequestInfo.msgId;
                if (msgId) {
                    msgId = msgId.split("|")
                    lang = msgId.length == 2 ? msgId[1] : lang;
                }
                let stateLocalizationModule = getStateLocalizationModule(tenantId);
                let cityLocalizationModule = getCityLocalizationModule(tenantId);

                let module = ["rainmaker-statement", stateLocalizationModule, cityLocalizationModule].join(",");
                let localizations = await search_localization(localizationReq, lang, module,tenantId );
                localizations.data.messages.forEach(localObj => {
                    localizationMap[localObj.code] = localObj.message;
                });

                AnalysisStatement.data.forEach(data => {
                    data = updateLocalization(data, localizationMap, tenantId);
                    if(localizationMap[data.sorType]){
                        data.sorType = localizationMap[data.sorType];
                    }
                })

                AnalysisStatement.data = sortData(AnalysisStatement.data, desiredOrder);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
            }
            var pdfResponse;
            const pdfKey = config.pdf.rateAnalysisUtilization_template;
            try {

                pdfResponse = await create_pdf(
                    tenantId,
                    pdfKey,
                    AnalysisStatement,
                    requestInfo
                )
                const filename = `${pdfKey}_${new Date().getTime()}`;

                res.writeHead(200, {

                    "Content-Type": "application/pdf",
                    "Content-Disposition": `attachment; filename=${filename}.pdf`,
                });

                pdfResponse.data.pipe(res);

            } catch (ex) {

                return renderError(res, "Failed to generate PDF for utilization statement", 500);

            }
        } else {

            return renderError(
                res,
                "There is no utilization created using this estimate number",
                404
            );

        }
    } catch (ex) {
        return renderError(res, "Failed to transform analysis statement", 500);
    }
}))

module.exports = router;
