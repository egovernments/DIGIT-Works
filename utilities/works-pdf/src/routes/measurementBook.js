const express = require("express");
const router = express.Router();
const url = require("url");
const config = require("../config");

const { search_measurementBookDetails, create_pdf, search_localization} = require("../api");
const { asyncMiddleware } = require("../utils/asyncMiddleware");
const { pdf } = require("../config");
const { logger } = require("../logger");

const { transformEstimateData } = require("../utils/transformEstimateData");
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

function updateLocalization(estimate, localizationMaps, tenantId) {
        if (estimate?.city) {
            estimate.city = estimate.city.toUpperCase();
            cityKey = "TENANT_TENANTS_" + estimate.city.split(".").join("_");
            estimate.city = getLocalizationByKey(cityKey, localizationMaps);
        }
        if (estimate?.locality) {
            let localityKey = getCityLocalizationPrefix(tenantId);
            localityKey = localityKey + "_ADMIN_" + estimate.locality;
            estimate.locality = getLocalizationByKey(localityKey, localizationMaps);
        }
        if (estimate?.ward) {
            let boundaryKey = getCityLocalizationPrefix(tenantId);
            boundaryKey = boundaryKey + "_ADMIN_" + estimate.ward;
            estimate.ward = getLocalizationByKey(boundaryKey, localizationMaps);
        }
    
    return estimate;
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
        const key = "View";
        try {
            try {
                var resMeasurement = await search_measurementBookDetails(tenantId, requestinfo, contractNumber, measurementNumber, key);
            }
            catch (ex) {
                if (ex.response && ex.response.data)
                return renderError(res, "Failed to query details of the measurement", 500);
            }
            const measurementBookDetails = resMeasurement.data;

            const contract = resMeasurement.data?.contract;
            const lineItems = resMeasurement.data?.contract?.lineItems;
            const measurement = resMeasurement.data?.measurement;
            const allMeasurements = resMeasurement.data?.allMeasurements;
            const estimateDetails = resMeasurement.data?.estimate?.estimateDetails;
            var estimate = resMeasurement.data?.estimate;
            estimate.locality = estimate.additionalDetails.locality;
            estimate.ward = estimate.additionalDetails.location.ward ;
            estimate.city = estimate.additionalDetails.location.city ;

            let localizationMap = await getStateCityLocalizaitons(requestinfo, tenantId);
            estimate = updateLocalization(estimate, localizationMap, tenantId);

            // convert startDateTime and endDateTime into dd/mm/yyyy format and show only date in a variable named measurement period
            const startDate = new Date(measurement.additionalDetails.startDate);
            const endDate = new Date(measurement.additionalDetails.endDate);
            // Format dates in IST
            const options = {
                day: 'numeric',
                month: 'numeric',
                year: 'numeric',
                timeZone: 'Asia/Kolkata' // Use the time zone for Indian Standard Time (IST)
            };
            const measurementPeriod = startDate.toLocaleString('en-IN', options) + ' - ' + endDate.toLocaleString('en-IN', options);

            // make a new variable in measurement named measurementPeriod and assign measurementPeriod to it
            measurementBookDetails.measurement.measurementPeriod = measurementPeriod;

            var transformedData;
            if(measurementBookDetails){
                transformedData = transformEstimateData(lineItems, contract, measurement, allMeasurements, estimateDetails, measurementNumber);
            }

            // make an array of all the values from the transformedData without keys
            const transformedDataValues = Object.values(transformedData);

            // Filter values with category 'SOR'
            const sorArray = transformedDataValues.filter(item => item.category === 'SOR');

            // Filter values with category 'NON-SOR'
            const nonSorArray = transformedDataValues.filter(item => item.category === 'NON-SOR');

            // Concatenate the two arrays
            const combinedArray = sorArray.concat(nonSorArray);

            measurementBookDetails.tableData = combinedArray;

            const Nformatter = new Intl.NumberFormat("en-IN", { maximumFractionDigits: 2 });
            // Function to format a number with commas
            function formatNumberWithCommas(value) {
                return Nformatter.format(value);
            }

            const Nformatter1 = new Intl.NumberFormat("en-IN", { maximumFractionDigits: 0 });
            // Function to format a number with commas
            function formatNumberWithCommas1(value) {
                return Nformatter1.format(value);
            }

            var totalSum = 0;
            for(let i = 0;i<measurementBookDetails.tableData.length;i++){
                totalSum += measurementBookDetails.tableData[i].mbAmount;
                measurementBookDetails.tableData[i].mbAmount = formatNumberWithCommas(measurementBookDetails.tableData[i].mbAmount);
                // measurementBookDetails.tableData[i].consumedQuantity = parseFloat(measurementBookDetails.tableData[i].consumedQuantity).toFixed(4);
                measurementBookDetails.tableData[i].currentQuantity = parseFloat(measurementBookDetails.tableData[i].currentQuantity).toFixed(4);
                // measurementBookDetails.tableData[i].estimatedQuantity = parseFloat(measurementBookDetails.tableData[i].estimatedQuantity).toFixed(4);
            }
            measurementBookDetails.measurement.totalSum = formatNumberWithCommas1(totalSum);

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
