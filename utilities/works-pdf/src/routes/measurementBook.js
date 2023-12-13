var express = require("express");
var router = express.Router();
var url = require("url");
var config = require("../config");

var { search_measurementBookDetails, create_pdf } = require("../api");
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
        var tenantId = req.query.tenantId;
        var contractNumber = req.query.contractNumber;
        var measurementNumber = req.query.measurementNumber;
        var requestinfo = req.body;
        if (requestinfo == undefined) {
            return renderError(res, "requestinfo can not be null", 400)
        }
        if (!tenantId) {
            return renderError(res, "tenantId is mandatory to generate the receipt", 400)
        }
        if (!measurementNumber) {
            return renderError(res, "measurementNumber is mandatory to generate the receipt", 400)
        }
        try {
            try {
                resMeasurement = await search_measurementBookDetails(tenantId, requestinfo, contractNumber, measurementNumber);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the measurement", 500);
            }
            var measurementBookDetails = resMeasurement.data;
            logger.info("measurementBookDetails", measurementBookDetails);

            var contract = resMeasurement.data?.contract;
            var lineItems = resMeasurement.data?.contract?.lineItems;
            var measurement = resMeasurement.data?.measurement;
            var allMeasurements = resMeasurement.data?.allMeasurements;
            var estimateDetails = resMeasurement.data?.estimate?.estimateDetails;

            var transformedData;
            if(measurementBookDetails){
                transformedData = transformEstimateData(lineItems, contract, measurement, allMeasurements, estimateDetails);
            }

            logger.info("transformedData", transformedData);

            // make an array of all the values from the transformedData without keys
            var transformedDataValues = Object.values(transformedData);
            logger.info("transformedDataValues", transformedDataValues);
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
                        logger.info(ex);
                        logger.error(ex);
                        if (ex.response && ex.response.data) console.log(ex.response.data);
                        return renderError(res, "Failed to generate PDF for measurement", 500);
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
                        "There is no estimate created using this estimate number",
                        404
                    );
                }
            } catch (ex) {
                logger.info(ex);
                logger.error(ex);
                return renderError(res, "Failed to query details of the estimate", 500);
            }

        })

);

module.exports = router;