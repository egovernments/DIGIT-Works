const e = require("express");
const { logger } = require("../logger");

const transformStatementData = (data) => {
    try {
        const sorMap = new Map();
        statement = data["statement"][0];

        statement["sorDetails"].forEach(sorDetail => {
            if(sorDetail["lineItems"].length > 0){
                sorDetail["lineItems"].forEach(lineItem => {
                    const amountDetail = lineItem["basicSorDetails"][0];
                    const sorDetail = lineItem["additionalDetails"]["sorDetails"];

                    if(sorMap.has(lineItem["sorId"])){
                        const amountDetailDup = sorMap.get(lineItem["sorId"]);
                        amountDetail.amount += amountDetailDup.amount;
                        amountDetail.quantity += amountDetailDup.quantity;
                    }else{
                        amountDetail.description = "Code: "+ sorDetail.id+ " \n \n" + sorDetail.description;
                        amountDetail.sorType = lineItem.sorType;
                    }
                    sorMap.set(lineItem["sorId"], amountDetail);
                });
            }else{
                const sorId = sorDetail["sorId"];
                sorDetail["basicSorDetails"].forEach(basicSorDetail => {
                    const sorDetail = lineItem["additionalDetails"]["sorDetails"];
                    if(sorMap.has(sorId)){
                        const amountDetail = sorMap.get(sorId);
                        amountDetail.amount += basicSorDetail.amount;
                        amountDetail.quantity += basicSorDetail.quantity;
                    }else{
                        amountDetail.description = "Code: "+ sorDetail.id+ " \n \n" + sorDetail.description;
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
            value.Sno = sorTypeToSorMap.get(value.type).length + 1;
            sorTypeToSorMap.get(value.type).push(value);
        }
        const ProjectID  = "PJ/2024-25/002428"
        const ProjectName = "Test Project"
        const ProjectLocation =  "GAJPATI NAGAR, Ward 1, Testing"
        const ProjectDescription = "Test Project"
        const tenantId = "od.jatni";
        const resultArray = [...sorTypeToSorMap.entries()].map(([sorType, amountDetails], index) => ({
            sorType,
            amountDetails,
            ProjectID,
            ProjectName,
            ProjectDescription,
            ProjectLocation,
            tenantId
        }));
        resultArray.forEach(result => {
            var totalAmount = 0;
            result.amountDetails.forEach(amountDetail => {
                totalAmount += amountDetail.amount;
            })
            result.totalEstimatedAmount = totalAmount;
        })
        const AnalysisStatement = resultArray;
        return AnalysisStatement;
    }catch (ex){
        console.log(ex);
    }
};

module.exports = {
    transformStatementData
}