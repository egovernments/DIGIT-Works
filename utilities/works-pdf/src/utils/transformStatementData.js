const e = require("express");
const { logger } = require("../logger");

function formatInLakh(totalAmount) {
    // Ensure totalAmount is a number and format it to two decimal places
    let amount = Number(totalAmount).toFixed(2);
    let parts = amount.split("."); // Split the amount into whole and decimal parts
    let wholePart = parts[0];
    let decimalPart = parts.length > 1 ? "." + parts[1] : "";

    // Regular expression to insert commas correctly for Indian numbering system
    wholePart = wholePart.replace(/\B(?=(\d{3})+(?!\d))/g, ","); // Apply comma for thousands
    wholePart = wholePart.replace(/(\d+)(?=(\d{2})+(?!\d))/g, "$1,"); // Apply comma for lakhs

    return wholePart + decimalPart;
}

const transformStatementData = (data, project) => {
    try {
        const sorMap = new Map();
        statement = data[0];
        const tenantId = statement['tenantId'];
        const estimateNumber = statement.additionalDetails.estimateNumber;
        const measurementBookNumber = statement.additionalDetails.measurementNumber;
        const ProjectID  = project.projectNumber;
        const ProjectName = project.name;
        const locality =  project.additionalDetails && project.additionalDetails.locality != null ? project.additionalDetails.locality : project.address.locality;
        const  ward = project.address.boundary;
        const city = project.address.city;
        const ProjectDescription = project.description;

            statement["sorDetails"].forEach(sorDetail => {
                if(sorDetail["lineItems"] != null && sorDetail["lineItems"].length > 0){
                    sorDetail["lineItems"].forEach(lineItem => {
                        const amountDetail = lineItem["basicSorDetails"][0];
                        const sorDetailInLineItem = lineItem["additionalDetails"]["sorDetails"];
                        const rateDetails = lineItem["additionalDetails"]["rateDetails"];

                        if(sorMap.has(lineItem["sorId"])){
                            const amountDetailDup = sorMap.get(lineItem["sorId"]);
                            amountDetailDup.amount += amountDetail.amount;
                            amountDetailDup.quantity += amountDetail.quantity;
                            sorMap.set(lineItem["sorId"], amountDetailDup);
                        }else{
                            amountDetail.description = "Code: "+ sorDetailInLineItem.id+ " \n \n" + sorDetailInLineItem.description;
                            amountDetail.sorType = lineItem.sorType;
                            amountDetail.unit = sorDetailInLineItem.uom;
                            amountDetail.rate = rateDetails.rate;
                            sorMap.set(lineItem["sorId"], amountDetail);
                        }
                    });
                }else{
                    const sorId = sorDetail["sorId"];
                    sorDetail["basicSorDetails"].forEach(basicSorDetail => {
                        const sorDetailInLineItem = sorDetail["additionalDetails"]["sorDetails"];
                        const rateDetails = sorDetail["additionalDetails"]["rateDetails"];
                        if(sorMap.has(sorId)){
                            const amountDetail = sorMap.get(sorId);
                            amountDetail.amount += basicSorDetail.amount;
                            amountDetail.quantity += basicSorDetail.quantity;
                            sorMap.set(sorId, amountDetail);
                        }else{
                            basicSorDetail.description = "Code: "+ sorDetailInLineItem.id+ " \n \n" + sorDetailInLineItem.description;
                            basicSorDetail.sorType = sorDetailInLineItem.sorType;
                            basicSorDetail.unit = sorDetailInLineItem.uom;
                            basicSorDetail.rate = rateDetails.rate;
                            sorMap.set(sorId,basicSorDetail);
                        }
                    })
                }
            });
            const sorTypeToSorMap = new Map();
            for(let value of sorMap.values()){
                if(!sorTypeToSorMap.has(value.type)){
                    sorTypeToSorMap.set(value.type, []);
                }
                value.quantity = value.quantity.toFixed(4);
                value.Sno = sorTypeToSorMap.get(value.type).length + 1;
                sorTypeToSorMap.get(value.type).push(value);
            }
        const sorTypeMap = {
            "W": "WRK_WORKS",
            "M": "WRK_MATERIALS",
            "E": "WRK_MACHINERY",
            "L": "WRK_LABOUR"
        };
            const resultArray = [...sorTypeToSorMap.entries()].map(([sorType, amountDetails], index) => ({
                sorType : sorTypeMap[sorType],
                amountDetails,
                measurementBookNumber,
                ProjectID,
                ProjectName,
                ProjectDescription,
                locality,
                ward,
                city,
                estimateNumber,
                tenantId
            }));
            resultArray.forEach(result => {
                var totalAmount = 0;
                result.amountDetails.forEach(amountDetail => {
                    totalAmount += parseFloat(amountDetail.amount);
                    amountDetail.amount = formatInLakh(amountDetail.amount);
                    amountDetail.rate = formatInLakh(amountDetail.rate);
                })
                result.totalEstimatedAmount = formatInLakh(totalAmount);
            })
            const AnalysisStatement = {
                "data": resultArray
            };
            return AnalysisStatement;
        }catch (ex){
            console.log(ex);
        }
};

module.exports = {
    transformStatementData
}