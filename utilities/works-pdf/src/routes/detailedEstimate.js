const express = require("express");
const router = express.Router();
const url = require("url");
const config = require("../config");

const { search_estimateDetails, create_pdf, search_projectDetails, search_localization} = require("../api");

const { asyncMiddleware } = require("../utils/asyncMiddleware");
const { pdf } = require("../config");
const { logger } = require("../logger");
const { transformDetailedData } = require("../utils/transformDetailedData");
const { getLanguageFromRequest, getStateLocalizationModule, getCityLocalizationModule, getStateLocalizationPrefix, getCityLocalizationPrefix, getLocalizationByKey } = require("../utils/localization");


function renderError(res, errorMessage, errorCode) {
    if (errorCode == undefined) errorCode = 500;
    res.status(errorCode).send({ errorMessage })

}

async function getStateCityLocalizaitons(request, tenantId) {
    let localizationMaps = {};
    let lang = getLanguageFromRequest(request);
    let modules = [getStateLocalizationModule(tenantId),getCityLocalizationModule(tenantId)].join(",");
    let localRequest = {}
    localRequest["RequestInfo"] = request["RequestInfo"];
    let localizations = await search_localization(localRequest, lang, modules, tenantId);
    if (localizations?.data?.messages?.length) {
        localizations.data.messages.forEach(localObj => {
            localizationMaps[localObj.code] = localObj.message;
        });
    }
    return localizationMaps;
}

function updateLocalization(pdfData, localizationMaps, tenantId) {
        if (pdfData?.city) {
            pdfData.city = pdfData.city.toUpperCase();
            cityKey = "TENANT_TENANTS_" + pdfData.city.split(".").join("_");
            pdfData.city = getLocalizationByKey(cityKey, localizationMaps);
        }
        if (pdfData?.locality) {
            let localityKey = getCityLocalizationPrefix(tenantId);
            localityKey = localityKey + "_ADMIN_" + pdfData.locality;
            pdfData.locality = getLocalizationByKey(localityKey, localizationMaps);
        }
        if (pdfData?.ward) {
            let boundaryKey = getCityLocalizationPrefix(tenantId);
            boundaryKey = boundaryKey + "_ADMIN_" + pdfData.ward;
            pdfData.ward = getLocalizationByKey(boundaryKey, localizationMaps);
        }
    
    return pdfData;
}



router.post(
    "/detailed-estimate",
    asyncMiddleware(async function (req, res, next) {
        const estimateNumber = req.query.estimateNumber;
        const tenantId = req.query.tenantId;
        const requestinfo = req.body;

        if (!estimateNumber) {
            return renderError(res, "estimateNumber is mandatory to generate the receipt", 400)
        }
        if (!tenantId) {
            return renderError(res, "tenantId is mandatory to generate the receipt", 400)
        }
        if (requestinfo == undefined) {
            return renderError(res, "requestinfo can not be null", 400)
        }

        try {
            try {

                resEstimate = await search_estimateDetails(tenantId, requestinfo, estimateNumber);

            }
            catch (ex) {

                return renderError(res, "Failed to query details of the estimate", 500);

            }
            var estimate = resEstimate.data;
            const projectId = estimate.estimates[0].additionalDetails.projectNumber;

            try {

                result = await search_projectDetails(tenantId, requestinfo, projectId);

            }
            catch (ex) {

                return renderError(res, "Failed to query details of the project", 500);

            }
            estimate.projectName = result.data.Project[0].name;


            const estimates = transformDetailedData(estimate);

            // Filter values with category 'SOR'
            const sorArray = estimates.estimateDetails.filter(item => item.category === 'SOR');

            // Filter values with category 'NON-SOR'
            const nonSorArray = estimates.estimateDetails.filter(item => item.category === 'NON-SOR');

            const overHeadArray = estimates.estimateDetails.filter(item => item.category === 'OVERHEAD');

            /// Combine all three arrays
            const combinedArray = sorArray.concat(nonSorArray, overHeadArray);

            estimates.estimateDetails = combinedArray;
            
            estimate.pdfData = estimates;

            if (estimate && estimate.pdfData) {

                    var pdfResponse;
                    const pdfkey = config.pdf.detailedEstimate_template;

                    let localizationMap = await getStateCityLocalizaitons(requestinfo, tenantId);
                    estimate.pdfData = updateLocalization(estimate.pdfData, localizationMap, tenantId);

                    try {

                        pdfResponse = await create_pdf(
                            tenantId,
                            pdfkey,
                            estimate,
                            requestinfo
                        )

                    }
                    

                    catch (ex) {

                        return renderError(res, "Failed to generate PDF for detailed estimate", 500);

                    }

                    const filename = `${pdfkey}_${new Date().getTime()}`;

                    res.writeHead(200, {

                        "Content-Type": "application/pdf",
                        "Content-Disposition": `attachment; filename=${filename}.pdf`,
                    });

                    pdfResponse.data.pipe(res);

                }

                else {

                    return renderError(
                        res,
                        "There is no estimate created using this estimate number",
                        404
                    );

                }

            } 
            catch (ex) {

                return renderError(res, "Failed to query details of detailed estimate", 500);
                
            }

        })

);

module.exports = router;
