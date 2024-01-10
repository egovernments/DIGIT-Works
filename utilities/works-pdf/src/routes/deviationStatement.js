const express = require("express");
const router = express.Router();
const url = require("url");
const config = require("../config");

const { search_estimateDetails, search_revisedEstimateDetails, create_pdf, search_projectDetails } = require("../api");

const { asyncMiddleware } = require("../utils/asyncMiddleware");
const { pdf } = require("../config");
const { logger } = require("../logger");
const { transformDeviationData } = require("../utils/transformDeviationData");

function renderError(res, errorMessage, errorCode) {
    if (errorCode == undefined) errorCode = 500;
    res.status(errorCode).send({ errorMessage })

}
router.post(
    "/deviation-statement",
    asyncMiddleware(async function (req, res, next) {
        const tenantId = req.query.tenantId;
        const revisionNumber = req.query.revisionNumber;
        const requestinfo = req.body;
        if (requestinfo == undefined) {
            return renderError(res, "requestinfo can not be null", 400)
        }
        if (!tenantId) {
            return renderError(res, "tenantId is mandatory to generate the receipt", 400)
        }
        if (!revisionNumber) {
            return renderError(res, "revisionNumber is mandatory to generate the receipt", 400)
        }
        try {
            try {
                var resRevisedEstimate = await search_revisedEstimateDetails(tenantId, requestinfo, revisionNumber);
            }
            catch (ex) {
                return renderError(res, "Failed to query details of the revisedEstimate", 500);
            }
            const estimate = resRevisedEstimate.data;

            const estimateNumber = estimate.estimates[0].estimateNumber;

            try {
                var resEstimate = await search_estimateDetails(tenantId, requestinfo, estimateNumber);
            }
            catch (ex) {
                return renderError(res, "Failed to query details of the estimate", 500);
            }
            const originalEstimateLength = resEstimate.data.estimates.length;
            estimate.estimates.push(resEstimate.data.estimates[originalEstimateLength - 1]);


            const projectId = estimate.estimates[0].additionalDetails.projectNumber;

            result = await search_projectDetails(tenantId, requestinfo, projectId);
            estimate.projectName = result.data.Project[0].name;

            var estimates;
            if (estimate && estimate.estimates.length > 1) {
                estimates = transformDeviationData(estimate);
            }

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
                    const pdfkey = config.pdf.deviationStatement_template;
                    try {
                        pdfResponse = await create_pdf(
                            tenantId,
                            pdfkey,
                            estimate,
                            requestinfo
                        )
                    }
                    
                    catch (ex) {
                        return renderError(res, "Failed to generate PDF for estimates", 500);
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
            } catch (ex) {
                return renderError(res, "Failed to query details of the estimate", 500);
            }

        })

);

module.exports = router;
