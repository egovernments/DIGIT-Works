const { logger } = require("../logger");

const transformDeviationData = (data) => {

    //iterate over estimate make an array of object which contains estimateNumber, projectNumber, projectName and description and an array of estimateDetails
    const estimates = {};
    estimates["estimateNumber"] = data.estimates[0].estimateNumber;
    estimates["projectNumber"] = data.estimates[0].additionalDetails.projectNumber;
    estimates["description"] = data.estimates[0].description;
    estimates["revisionNumber"] = data.estimates[0].revisionNumber;
    estimates["tenantId"] = data.estimates[0].tenantId;
    estimates["projectName"] = data.projectName;
    const sorIdMap = {};

    const Nformatter = new Intl.NumberFormat("en-IN", { maximumFractionDigits: 2 });
    // Function to format a number with commas
    function formatNumberWithCommas(value) {
        return Nformatter.format(value);
    }

    for (const estimateDetail of data.estimates[0].estimateDetails) {
        if (estimateDetail.category === "OVERHEAD") {
            continue;
        }
        // if in the end of name there is a square bracket then convert that square bracket into round bracket
        if(estimateDetail.name.includes('[')){
            estimateDetail.name = estimateDetail.name.replace('[', '(');
            estimateDetail.name = estimateDetail.name.replace(']', ')');
        }

        if(estimateDetail.length == null)estimateDetail.length=1;
        if(estimateDetail.width == null)estimateDetail.width=1;
        if(estimateDetail.height == null)estimateDetail.height=1;
        if(estimateDetail.quantity == null)estimateDetail.quantity=1;

        var estQ = estimateDetail.length*estimateDetail.width*estimateDetail.height*estimateDetail.quantity;
        estimateDetail.estimatedQuantity = estQ;
    
        const { sorId, isDeduction, name, description, uom, unitRate, estimatedQuantity, amountDetail } = estimateDetail;
    
        if (sorIdMap[sorId] === undefined) {
            const amount = isDeduction ? -amountDetail[0].amount : amountDetail[0].amount;
    
            sorIdMap[sorId] = {
                sorId,
                name,
                description,
                uom,
                unitRate,
                estimatedQuantity,
                amount,
                deviation: "Excess",
                originalQuantity: 0,
                originalAmount: 0
            };
        } else {
            const sorIdEntry = sorIdMap[sorId];
            const amountChange = isDeduction ? -amountDetail[0].amount : amountDetail[0].amount;
    
            sorIdEntry.amount += amountChange;
            sorIdEntry.estimatedQuantity += isDeduction ? -estimatedQuantity : estimatedQuantity;
        }
    }

    // iterate over estimateDetails of last estimate and check if sorId is present in sorIdMap then check if isDeduction is true then subtract the amount from originalAmount and quantity from originalQuantity and if isDeduction is false then add the amount to originalAmount and quantity to originalQuantity

    const lastIndex = data.estimates.length - 1;
const originalEstimateDetails = data.estimates[lastIndex].estimateDetails;


for (const estimateDetail of originalEstimateDetails) {
    if (estimateDetail.category === "OVERHEAD") {
        continue;
    }

    if(estimateDetail.length == null)estimateDetail.length=1;
    if(estimateDetail.width == null)estimateDetail.width=1;
    if(estimateDetail.height == null)estimateDetail.height=1;
    if(estimateDetail.quantity == null)estimateDetail.quantity=1;

    var estQ = estimateDetail.length*estimateDetail.width*estimateDetail.height*estimateDetail.quantity;
    estimateDetail.estimatedQuantity = estQ;

    const { sorId, isDeduction, amountDetail, estimatedQuantity } = estimateDetail;
    const sorIdEntry = sorIdMap[sorId];

    if (sorIdEntry !== undefined) {
        const amountChange = isDeduction ? -amountDetail[0].amount : amountDetail[0].amount;

        sorIdEntry.originalAmount += isDeduction ? -amountChange : amountChange;
        sorIdEntry.originalQuantity += isDeduction ? -estimatedQuantity : estimatedQuantity;

        // Set deviation based on originalAmount and amount
        if (sorIdEntry.originalAmount === sorIdEntry.amount) {
            sorIdEntry.deviation = "No";
        } else if (sorIdEntry.originalAmount < sorIdEntry.amount) {
            sorIdEntry.deviation = "Excess";
        } else {
            sorIdEntry.deviation = "Less";
        }
    }
}

    var totalSum = 0;    

    const estimateDetails = [];
    for(const key in sorIdMap){
        totalSum += sorIdMap[key].amount;
        sorIdMap[key].unitRate = formatNumberWithCommas(sorIdMap[key].unitRate);
        sorIdMap[key].amount = formatNumberWithCommas(sorIdMap[key].amount);
        sorIdMap[key].originalAmount = formatNumberWithCommas(sorIdMap[key].originalAmount);
        estimateDetails.push(sorIdMap[key]);
    }
    estimates.totalSum = formatNumberWithCommas(totalSum);
    estimates["estimateDetails"] = estimateDetails;



    return estimates;

    
};

module.exports = {
    transformDeviationData
    };
