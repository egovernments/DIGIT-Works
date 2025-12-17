const e = require("express");
const { logger } = require("../logger");

const transformDetailedData = (data) => {

    const lastIndex = data.estimates.length - 1;
    //iterate over estimate make an array of object which contains estimateNumber, projectNumber, projectName and description and an array of estimateDetails
    const estimates = {};
    estimates["estimateNumber"] = data.estimates[lastIndex].estimateNumber;
    estimates["projectNumber"] = data.estimates[lastIndex].additionalDetails.projectNumber;
    estimates["projectName"] = data.estimates[lastIndex].additionalDetails.projectName;
    estimates["description"] = data.estimates[lastIndex].description;
    estimates["tenantId"] = data.estimates[lastIndex].tenantId;
    estimates["projectName"] = data.projectName;
    estimates["locality"] = data.estimates[lastIndex].additionalDetails.locality;
    estimates["ward"] = data.estimates[lastIndex].additionalDetails.location.ward;
    estimates["city"] = data.estimates[lastIndex].additionalDetails.location.city;

    const Nformatter = new Intl.NumberFormat("en-IN", { maximumFractionDigits: 0 });
    // Function to format a number with commas
    function formatNumberWithCommas(value) {
        return Nformatter.format(value);
    }

    const Nformatter1 = new Intl.NumberFormat("en-IN", { maximumFractionDigits: 2 });
    // Function to format a number with commas
    function formatNumberWithCommas1(value) {
        return Nformatter1.format(value);
    }
    
    // Format totalEstimatedAmount with commas
    var totalEstimatedAmount = parseFloat(data.estimates[lastIndex].additionalDetails.totalEstimatedAmount).toFixed(2);

    estimates["totalEstimatedAmount"] = formatNumberWithCommas(totalEstimatedAmount);

    const sorIdMap = {};

    var count = -1;
    var count1 = 100;
    for (const estimateDetail of data.estimates[lastIndex].estimateDetails) {

        if(estimateDetail.category === 'NON-SOR' && estimateDetail.sorId === '45'){
            estimateDetail.sorId = count1;
            count1++;
        }

        if(estimateDetail.category === 'NON-SOR' && estimateDetail.sorId === null){
            estimateDetail.sorId = '0';
        }

        if(estimateDetail.category === 'OVERHEAD' && estimateDetail.sorId === null){
            estimateDetail.sorId = count;
            count--;
        }
        
        // if in the end of name there is a square bracket then convert that square bracket into round bracket
        if(estimateDetail.name.includes('[')){
            estimateDetail.name = estimateDetail.name.replace('[', '(');
            estimateDetail.name = estimateDetail.name.replace(']', ')');
        }

        if (estimateDetail.name.includes("\"")){
            estimateDetail.name = estimateDetail.name.replace(/"/g, "'");
        }

        if(estimateDetail.length == null)estimateDetail.length=1;
        if(estimateDetail.width == null)estimateDetail.width=1;
        if(estimateDetail.height == null)estimateDetail.height=1;
        if(estimateDetail.quantity == null)estimateDetail.quantity=1;

        var estQ = estimateDetail.noOfunit;
        estimateDetail.estimatedQuantity = estQ;

        estimateDetail.unitRate = formatNumberWithCommas1(estimateDetail.unitRate);

        const { sorId, category, isDeduction, name, description, uom, unitRate, estimatedQuantity, amountDetail, additionalDetails } = estimateDetail;
    
        if (sorIdMap[sorId] === undefined) {
            const amount = isDeduction ? -amountDetail[0].amount : amountDetail[0].amount;
            // Apply condition on estimatedQuantity according to isDeduction
            const adjustedEstimatedQuantity = isDeduction ? -estimatedQuantity : estimatedQuantity;
    
            sorIdMap[sorId] = {
                sorId,
                category,
                name,
                description,
                uom,
                unitRate,
                estimatedQuantity: adjustedEstimatedQuantity,
                amount,
                additionalDetails
            };
        } else {
            const sorIdEntry = sorIdMap[sorId];
            const amountChange = isDeduction ? -amountDetail[0].amount : amountDetail[0].amount;
    
            sorIdEntry.amount += amountChange;
            sorIdEntry.estimatedQuantity += isDeduction ? -estimatedQuantity : estimatedQuantity;
        }
    }

    const estimateDetails = [];
    for(const key in sorIdMap){
        sorIdMap[key].amount = sorIdMap[key].amount.toFixed(2);
        sorIdMap[key].estimatedQuantity = sorIdMap[key].estimatedQuantity.toFixed(4);
        estimateDetails.push(sorIdMap[key]);
    }
    estimates["estimateDetails"] = estimateDetails;



    return estimates;

    
};

module.exports = {
    transformDetailedData
    };
