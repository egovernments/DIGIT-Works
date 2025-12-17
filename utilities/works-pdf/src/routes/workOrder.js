var express = require("express");
var router = express.Router();
var url = require("url");
var config = require("../config");

var { search_organisation, search_contract, create_pdf, search_workflow, search_localization, search_hrms, search_mdms_terms_and_conditions } = require("../api");
const { asyncMiddleware } = require("../utils/asyncMiddleware");
const get = require("lodash.get");
const { getStateLocalizationModule, getLanguageFromRequest, getCityLocalizationModule, getLocalizationByKey } = require("../utils/localization");

function renderError(res, errorMessage, errorCode) {
    if (errorCode == undefined) errorCode = 500;
    res.status(errorCode).send({ errorMessage })

}

async function getLocalizaitons(request, tenantId) {
    let localizationMaps = {};
    let lang = getLanguageFromRequest(request);
    let modules = [getStateLocalizationModule(tenantId), getCityLocalizationModule(tenantId)].join(",");
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

router.post(
    "/work-order",
    asyncMiddleware(async function (req, res, next) {
        var tenantId = req.query.tenantId;
        var contractId = req.query.contractId;
        var requestinfo = req.body;
        if (requestinfo == undefined) {
            return renderError(res, "requestinfo can not be null", 400)
        }
        if (!tenantId) {
            return renderError(res, "tenantId is mandatory to generate the receipt", 400)
        }
        if (!contractId) {
            return renderError(res, "contractId is mandatory to generate the receipt", 400)
        }
        try {

            try {
                resContract = await search_contract(tenantId, requestinfo, contractId);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the contract", 500);
            }
            try {
                resOrg = await search_organisation(tenantId, requestinfo, resContract.data.contracts[0].orgId);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the organisation", 500);
            }
            try {
                let workflowRequest = {};
                workflowRequest["RequestInfo"] = requestinfo["RequestInfo"];
                resWorkFlow = await search_workflow(contractId, tenantId, workflowRequest,true);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query details of the workflow", 500);
            }
            try {
                resHrms = await search_hrms(tenantId, requestinfo);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to fetch the result from HRMS", 500);
            }
            try {
                resMdmsTermsAndConditions = await search_mdms_terms_and_conditions(tenantId, requestinfo);
            }
            catch (ex) {
                if (ex.response && ex.response.data) console.log(ex.response.data);
                return renderError(res, "Failed to query for terms and conditions", 500);
            }


            // Get localizations as map
            let localizationMaps = await getLocalizaitons(requestinfo, tenantId);


            var contract = resContract.data;
            var organisation = resOrg.data;
            var hrms = resHrms.data;
            
            var mdmsData = get(resMdmsTermsAndConditions, 'data.MdmsRes.tenant.tenants', []);
            let worflow = resWorkFlow?.data || {};
            if (contract && contract.contracts && contract.contracts.length > 0 && organisation && organisation.organisations && organisation.organisations.length > 0 && worflow?.ProcessInstances?.length) {
                var pdfResponse;
                const locale = requestinfo?.RequestInfo?.msgId?.split("|")[1];
                var pdfkey;

                switch (locale) {
                    case "hi_IN":
                        pdfkey = config.pdf.work_order_template_hindi;
                        break;
                    case "or_IN":
                        if (contract.contracts[0].executingAuthority == "IA") {
                            pdfkey = config.pdf.work_order_template_odiya_ia;
                        } else {
                            pdfkey = config.pdf.work_order_template_odiya_ip;
                        }
                        break;
                    default:
                        if (contract.contracts[0].executingAuthority == "IA") {
                            pdfkey = config.pdf.work_order_ia_template;
                        } else {
                            pdfkey = config.pdf.work_order_ip_template;
                        }

                }

                contract.contracts[0].contactName = organisation.organisations[0].contactDetails[0].contactName
                contract.contracts[0].nameOfCbo = organisation.organisations[0].name
                contract.contracts[0].city = organisation.organisations[0].orgAddress[0].city
                contract.contracts[0].doorNo = organisation.organisations[0].orgAddress[0].doorNo
                contract.contracts[0].street = organisation.organisations[0].orgAddress[0].street
                contract.contracts[0].orgWard = organisation.organisations[0].orgAddress[0].boundaryCode
                contract.contracts[0].orgLocality = organisation.organisations[0].additionalDetails.locality
                contract.contracts[0].orgDistrict = organisation.organisations[0].orgAddress[0].district
                var approversName;

                for(var workflow in worflow.ProcessInstances){
                    var obj = worflow.ProcessInstances[workflow];
                    if(obj.action=="APPROVE"){
                        approversName=obj.assigner.name;
                        break;
                    }
                }
                contract.contracts[0].approverName=approversName;

                let address = [];
                let tenantIdForTnC = '';
                if (contract.contracts[0].street) {
                    address.push(contract.contracts[0].street)
                }
                if (contract.contracts[0].doorNo) {
                    address.push(contract.contracts[0].doorNo)
                }
                if (contract.contracts[0].city) {
                    let city = contract.contracts[0].city.toUpperCase();
                    let cityKey = "TENANT_TENANTS_" + city.split(".").join("_");
                    city = getLocalizationByKey(cityKey, localizationMaps);
                    address.push(city)
                    tenantIdForTnC = city;
                }
                if (address.length) {
                    address = address.join(", ");
                    contract.contracts[0].pdfCboAddress = address;
                }

                // Get issuer name 
                contract.contracts[0].pdfNameOfIssuedTo = contract.contracts[0].totalContractedAmount <= 1500000 ? organisation.organisations[0].name : get(contract.contracts[0], 'additionalDetails.officerInChargeName.name', null);

                // Get days left to accept the work order
                let sla = get(worflow, "ProcessInstances[0].businesssServiceSla", 0);
                let todaysDateTime = new Date().getTime();
                let workflowAuditDetails = get(worflow, "ProcessInstances[0].auditDetails", {});
                // Get today's date time and subtract with accpted date and add the remaining sla to find the total number of days
                sla = sla + (todaysDateTime - workflowAuditDetails.createdTime);
                let slaDays = parseInt(sla) / (24 * 60 * 60 * 1000);
                contract.contracts[0].pdfWorkOrdAcceptanceDays = parseInt(slaDays.toFixed());
                var slaDate = workflowAuditDetails.createdTime + sla;
                contract.contracts[0].pdfSlaDate = slaDate;
                contract.contracts[0].pdfAcceptedDate = workflowAuditDetails.createdTime;

                let officerInChargeDetails = hrms.Employees.filter(emp =>
                    emp.code == contract.contracts[0].additionalDetails.officerInChargeId);

                contract.contracts[0].officerInChargeNumber = officerInChargeDetails[0].user.mobileNumber;
                contract.contracts[0].officerInChargeName = get(contract.contracts[0], 'additionalDetails.officerInChargeName.name', null);



                var accountantDetails = new Map();
                var accountantRoles = ["WORK_ORDER_VIEWER", "BILL_ACCOUNTANT", "EMPLOYEE_COMMON", "BILL_VIEWER", "ORG_VIEWER"];
                for (var emp in hrms.Employees) {
                    var obj = hrms.Employees[emp];
                    var roles = obj.user.roles;
                    let accountantRoleChecker = (accountantRoles, roles) => accountantRoles.every(v => roles.includes(v));
                    if (roles.length == accountantRoles.length && accountantRoleChecker) {
                        accountantDetails.set('name', obj.user.name);
                        accountantDetails.set('mobileNumber', obj.user.mobileNumber);
                        accountantDetails.set('designation', obj.assignments[0].designation);

                    }

                }
                contract.contracts[0].accountantDetailsName = accountantDetails.get('name');
                contract.contracts[0].accountantDetailsNumber = accountantDetails.get('mobileNumber');
                contract.contracts[0].accountantDetailsDesignation = accountantDetails.get('designation');


                let termAndConditions = [];
                if (contract.contracts[0].executingAuthority == 'IA') {
                    let iaItem = mdmsData.find(item => item.code === 'IA');
                    if(iaItem){
                        let tnClength = iaItem.termsAndConditions.length;
                        for (i = 0; i < tnClength; i++) {
                            var termsKey = iaItem.termsAndConditions[i].disclaimer;
                            var tnc = getLocalizationByKey(termsKey, localizationMaps);
                            termAndConditions.push(tnc.replace('{nameoftheulb}', tenantIdForTnC));
                        }
                    }
                }

                if (contract.contracts[0].executingAuthority == 'IP') {
                    let ipItem = mdmsData.find(item => item.code === 'IP');
                    if(ipItem){
                        let tnClength = ipItem.termsAndConditions.length;
                        for (i = 0; i < tnClength; i++) {
                            var termsKey = ipItem.termsAndConditions[i].disclaimer;
                            var tnc = getLocalizationByKey(termsKey, localizationMaps);
                            termAndConditions.push(tnc.replace('{nameoftheulb}', tenantIdForTnC));
                        }
                    }
                }
                let newTC = [];
                for (let idx = 0; idx < termAndConditions.length; idx++) {
                    newTC.push({ "terms": termAndConditions[idx] })
                }
                contract.contracts[0].termsAndConditionList = newTC;
                var issueDate;

                var fiscalyear = "";
                for(var con in contract.contracts){
                    var date=contract.contracts[con].issueDate;
                    if(date!=null){
                        issueDate=contract.contracts[con].issueDate;
                        break;
                    }

                }
                
                var date = new Date(issueDate);

                if ((date.getMonth() + 1) <= 3) {
                  fiscalyear = (date.getFullYear() - 1) + "-" + date.getFullYear()
                } else {
                  fiscalyear = date.getFullYear() + "-" + (date.getFullYear() + 1)
                }

                contract.contracts[0].financialYear= fiscalyear;
                contract.contracts[0].issueDate=date;

                try {
                    pdfResponse = await create_pdf(
                        tenantId,
                        pdfkey,
                        contract,
                        requestinfo
                    )
                }
                catch (ex) {
                    if (ex.response && ex.response.data) console.log(ex.response.data);
                    return renderError(res, "Failed to generate PDF for work order", 500);
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
                    "There is no contract created using this contractId",
                    404
                );
            }
        } catch (ex) {
            return renderError(res, "Failed to query details of the contract", 500);
        }

    })

);

module.exports = router;