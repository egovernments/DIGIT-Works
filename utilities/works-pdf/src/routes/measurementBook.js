const express = require("express");
const router = express.Router();
const url = require("url");
const config = require("../config");

const { search_measurementBookDetails, create_pdf } = require("../api");
const { asyncMiddleware } = require("../utils/asyncMiddleware");
const { pdf } = require("../config");
const { logger } = require("../logger");

const { transformEstimateData } = require("../utils/transformEstimateData");

function renderError(res, errorMessage, errorCode) {
    if (errorCode == undefined) errorCode = 500;
    res.status(errorCode).send({ errorMessage })

}
router.post(
    "/measurement-book",
    asyncMiddleware(async function (req, res, next) {
        const tenantId = req.query.tenantId;
        const contractNumber = req.query.contractNumber;
        const measurementNumber = req.query.measurementNumber;
        const requestinfo = req.body;
        if (requestinfo == undefined) {
            return renderError(res, "requestinfo can not be null", 400)
        }
        if (!tenantId) {
            return renderError(res, "tenantId is mandatory to generate the receipt", 400)
        }
        if (!measurementNumber) {
            return renderError(res, "measurementNumber is mandatory to generate the receipt", 400)
        }
        if (!contractNumber) {
            return renderError(res, "contractNumber is mandatory to generate the receipt", 400)
        }

        console.log("tenantId", tenantId);
        console.log("contractNumber", contractNumber);
        console.log("measurementNumber", measurementNumber);

        try {
            try {
                var resMeasurement = await search_measurementBookDetails(tenantId, requestinfo, contractNumber, measurementNumber);
            }
            catch (ex) {
                if (ex.response && ex.response.data)
                return renderError(res, "Failed to query details of the measurement", 500);
            }

            // print all the values of resMeasurement
            console.log("resMeasurement", resMeasurement.data);

            var measurementBookDetails = resMeasurement.data;
            var estimateDetails = resMeasurement.data?.estimate?.estimateDetails;

            //print all the values of measurementBookDetails
            console.log("measurementBookDetails", measurementBookDetails);


            var transformedData;
            if(measurementBookDetails){
                
                // transformedData = transformEstimateData(lineItems, contract, measurement, allMeasurements, estimateDetails);
                try{
                    transformedData = await transformEstimateData(estimateDetails);
                }
                catch (ex) {
                    if (ex.response && ex.response.data)
                    return renderError(res, "Failed to transform data", 500);
                }
            }

            // make an array of all the values from the transformedData without keys
            const transformedDataValues = Object.values(transformedData);
            measurementBookDetails.tableData = transformedDataValues;

            if(measurementBookDetails){
                    var pdfResponse;
                    var pdfkey = config.pdf.measurement_template;
                    try {
                        pdfResponse = await create_pdf(
                            tenantId,
                            pdfkey,
                            measurementBookDetails,
                            requestinfo
                        )
                    }   
                    catch (ex) {
                        if (ex.response && ex.response.data)
                        return renderError(res, "Failed to generate PDF for measurement", 500);
                    }

                    const filename = `${pdfkey}_${new Date().getTime()}`;

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