var express = require("express");
var router = express.Router();
var url = require("url");
var config = require("../config");

var { search_projectDetails, create_pdf, search_localization, search_payment_instruction, search_report_paymentTracker} = require("../api");
const { asyncMiddleware } = require("../utils/asyncMiddleware");
const { getLanguageFromRequest, getStateLocalizationModule, getCityLocalizationModule, getStateLocalizationPrefix, getCityLocalizationPrefix, getLocalizationByKey } = require("../utils/localization");

function renderError(res, errorMessage, errorCode) {
    if (errorCode == undefined) errorCode = 500;
    res.status(errorCode).send({ errorMessage })

}

function getCurrentDate() {
    const today = new Date();
  
    // Get the components of the date
    const day = String(today.getDate()).padStart(2, '0');
    const month = String(today.getMonth() + 1).padStart(2, '0'); // Months are zero-based
    const year = today.getFullYear();
  
    // Format the date as "dd-mm-yyyy"
    const formattedDate = `${day}/${month}/${year}`;
  
    return formattedDate;
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

function updateLocalization(projects, localizationMaps, tenantId) {
    projects = projects.map((project) => {
        if (project?.address?.latitude != null && project?.address?.latitude != 0 && project?.address?.longitude != null  && project?.address?.longitude != 0) {
            project.address.pdfLatlong = `${project.address.latitude}, ${project.address.longitude}`;
        } else {
            project.address.pdfLatlong = null;
        }
        if (project?.address?.city) {
            project.address.city = project.address.city.toUpperCase();
            cityKey = "TENANT_TENANTS_" + project.address.city.split(".").join("_");
            project.address.city = getLocalizationByKey(cityKey, localizationMaps);
        }
        if (project?.additionalDetails?.locality) {
            let localityKey = getCityLocalizationPrefix(tenantId);
            localityKey = localityKey + "_ADMIN_" + project.additionalDetails.locality;
            project.additionalDetails.locality = getLocalizationByKey(localityKey, localizationMaps);
        }
        if (project?.address?.boundary) {
            let boundaryKey = getCityLocalizationPrefix(tenantId);
            boundaryKey = boundaryKey + "_ADMIN_" + project.address.boundary;
            project.address.boundary = getLocalizationByKey(boundaryKey, localizationMaps);
        }
        return project;
    })
    return projects;
}

router.post(
    "/payment-tracker",
    asyncMiddleware(async function (req, res, next) {
        var tenantId = req.query.tenantId;
        var projectNum = req.query.projectId;
        var requestinfo = req.body;
        if (requestinfo == undefined) {
            return renderError(res, "requestinfo can not be null", 400)
        }
        if (!tenantId) {
            return renderError(res, "tenantId is mandatory to generate the receipt", 400)
        }
        if (!projectNum) {
            return renderError(res, "projectId is mandatory to generate the receipt", 400)
        }
        try {
            try {
                resProject = await search_projectDetails(tenantId, requestinfo, projectNum);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the project", 500);
            }

            RequestInfo = requestinfo.RequestInfo;

            var estimatedAmount = 0;
            var wageAmountPaid = 0;
            var purchaseAmountPaid = 0;
            var supervisionAmountPaid = 0;
            var failedPaymentAmount = 0;
            var total = 0;

            try {
                resHeadWiseData = await search_report_paymentTracker(tenantId, RequestInfo, projectNum);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the report", 500);
            }
            var headWiseData = resHeadWiseData.data;
            var projects = headWiseData.aggsResponse.projects[0];

            if (projects) {
                if(projects.estimatedAmount != null){
                    estimatedAmount = projects.estimatedAmount;
                }

                if(projects.paymentDetails && projects.paymentDetails.length > 0){
                    for (var i = 0; i < projects.paymentDetails.length; i++) {
                        billType = projects.paymentDetails[i].billType;
                        paidAmount = projects.paymentDetails[i].paidAmount;
                        remainingAmount = projects.paymentDetails[i].remainingAmount;
                        if (billType == "EXPENSE.WAGES") {
                            wageAmountPaid = paidAmount;
                        }
                        if (billType == "EXPENSE.PURCHASE") {
                            purchaseAmountPaid = paidAmount;
                        }
                        if (billType == "EXPENSE.SUPERVISION") {
                            supervisionAmountPaid = paidAmount;
                        }
                        failedPaymentAmount += remainingAmount;
                    }
                    
                }
            }

            try {
                resPaymentInstruction = await search_payment_instruction(tenantId, RequestInfo, projectNum);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the payment instruction", 500);
            }

            function convertEpochToIST(epochTime) {
                // Convert epoch time (in milliseconds) to a JavaScript Date object
                const date = new Date(epochTime);
            
                // Format the date in dd/mm/yyyy and adjust to IST timezone
                const options = { timeZone: 'Asia/Kolkata', day: '2-digit', month: '2-digit', year: 'numeric' };
                return date.toLocaleDateString('en-GB', options);
            }

            var paymentInstruction = resPaymentInstruction.data;
            var bills = paymentInstruction.items;
            if (bills && bills.length > 0) {
                for (var i = 0; i < bills.length; i++) {
                    bills[i].businessObject["failedAmount"] = 0;
                    bills[i].businessObject["successAmount"] = 0;
                    const formattedDate = convertEpochToIST(bills[i].businessObject.auditDetails.lastModifiedTime);
                    bills[i].businessObject["paymentDate"] = formattedDate;
                    if(bills[i].businessObject.piStatus == "FAILED"){
                        bills[i].businessObject["failedAmount"] = bills[i].businessObject.netAmount;
                    }else if(bills[i].businessObject.piStatus == "SUCCESSFUL"){
                        bills[i].businessObject["successAmount"] = bills[i].businessObject.netAmount;
                    }else if(bills[i].businessObject.piStatus == "PARTIAL"){
                        if(bills[i].businessObject.beneficiaryDetails && bills[i].businessObject.beneficiaryDetails.length > 0){
                            for(var j = 0; j < bills[i].businessObject.beneficiaryDetails.length; j++){
                                if(bills[i].businessObject.beneficiaryDetails[j].paymentStatus == "Payment Successful"){
                                    bills[i].businessObject["successAmount"] += bills[i].businessObject.beneficiaryDetails[j].amount;
                                }
                                if(bills[i].businessObject.beneficiaryDetails[j].paymentStatus == "Payment Failed"){
                                    bills[i].businessObject["failedAmount"] += bills[i].businessObject.beneficiaryDetails[j].amount;
                                }
                            }
                        }
                    }else if(bills[i].businessObject.piStatus == "COMPLETED"){
                        for(var j = 0; j < bills[i].businessObject.beneficiaryDetails.length; j++){
                            if(bills[i].businessObject.beneficiaryDetails[j].paymentStatus == "Payment Successful"){
                                bills[i].businessObject["successAmount"] += bills[i].businessObject.beneficiaryDetails[j].amount;
                            }
                            if(bills[i].businessObject.beneficiaryDetails[j].paymentStatus == "Payment Failed"){
                                bills[i].businessObject["failedAmount"] += bills[i].businessObject.beneficiaryDetails[j].amount;
                            }
                        }
                    }
                    total += bills[i].businessObject.successAmount;
                }
            }

            for (var i = 0; i < bills.length; i++) {
                var piStatus = bills[i].businessObject.piStatus.toLowerCase();
                piStatus = piStatus.charAt(0).toUpperCase() + piStatus.slice(1); 
                bills[i].businessObject.piStatus = piStatus;
            }


            var project = resProject.data;
            if (project && project.Project && project.Project.length > 0) {
                    var pdfResponse;
                    var pdfkey = config.pdf.paymentTracker_template;
                    
                    let localizationMap = await getStateCityLocalizaitons(requestinfo, tenantId);
                    project.Project = updateLocalization(project.Project, localizationMap, tenantId);
                    // Adding project as Projects because it's updating on create_pdf
                    project["Projects"] = project.Project;
                    project["Projects"][0]["date"] = getCurrentDate(); 
                    project["Projects"][0]["bills"] = bills;
                    project["Projects"][0]["estimatedAmount"] = estimatedAmount.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                    project["Projects"][0]["wageAmountPaid"] = wageAmountPaid.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                    project["Projects"][0]["purchaseAmountPaid"] = purchaseAmountPaid.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                    project["Projects"][0]["supervisionAmountPaid"] = supervisionAmountPaid.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                    project["Projects"][0]["failedPaymentAmount"] = failedPaymentAmount.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",");

                    project["Projects"][0]["total"] = total.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",");

                    try {
                        pdfResponse = await create_pdf(
                            tenantId,
                            pdfkey,
                            project,
                            requestinfo
                        )
                    }
                    catch (ex) {
                        if (ex.response && ex.response.data) console.log(ex.response.data);
                        return renderError(res, "Failed to generate PDF for project details", 500);
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
                        "There is no project created using this project number",
                        404
                    );
                }
            } catch (ex) {
                return renderError(res, "Failed to query details of the Parent", 500);
            }

        })

);

module.exports = router;