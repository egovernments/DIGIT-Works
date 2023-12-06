const { logger } = require("../logger");

const transformDeviationData = (estimate) => {

    //iterate over estimate make an array of object which contains estimateNumber, projectNumber, projectName and description and an array of estimateDetails
    var estimates = {};
    estimates["estimateNumber"] = estimate.estimates[0].estimateNumber;
    estimates["projectNumber"] = estimate.estimates[0].additionalDetails.projectNumber;
    estimates["projectName"] = estimate.estimates[0].additionalDetails.projectName;
    estimates["description"] = estimate.estimates[0].description;
    //set revision number
    estimates["revisionNumber"] = estimate.estimates[0].revisionNumber;
    var estimateDetails = [];
    for(let i=0;i<estimate.estimates[0].estimateDetails.length;i++){
        if(estimate.estimates[0].estimateDetails[i].category=="OVERHEAD"){
            continue;
        }
        else{
            estimateDetails.push(estimate.estimates[0].estimateDetails[i]);
        }
    }
    estimates["estimateDetails"] = estimateDetails;

    var lastEstimateDetails= estimate.estimates[estimate.estimates.length-1].estimateDetails;
    // now iterate over estimates["estimateDetails"] and match sorId with lastEstimateDetails and if it matches then take the quantity and amount from that lastEstimateDetails and add it to the object of estimates["estimateDetails"] corresponding to that sorId
    for(let i=0;i<estimates["estimateDetails"].length;i++){
        var sorId = estimates["estimateDetails"][i].sorId;
        for(let j=0;j<lastEstimateDetails.length;j++){
            if(sorId==lastEstimateDetails[j].sorId){
                estimates["estimateDetails"][i].originalQuantity = lastEstimateDetails[j].quantity;
                estimates["estimateDetails"][i].originalAmount = lastEstimateDetails[j].amountDetail[0].amount;
                // create a new field named "deviation" and check if originalAmount is greater than amount then deviation is negative else positive
                if(estimates["estimateDetails"][i].originalAmount>estimates["estimateDetails"][i].amountDetail[0].amount){
                    estimates["estimateDetails"][i].deviation = "Less";
                }
                else if(estimates["estimateDetails"][i].originalAmount==estimates["estimateDetails"][i].amountDetail[0].amount){
                    estimates["estimateDetails"][i].deviation = "No";
                }
                else{
                    estimates["estimateDetails"][i].deviation = "Excess";
                }
            }
        }
    }

    return estimates;

    
};

module.exports = {
    transformDeviationData
    };