const { pdf } = require("../config");

const transformEstimateData = (lineItems, contract, measurement, allMeasurements, estimateDetails) => {

  const idEstimateDetailsMap = {};
  for (let i = 0; i < estimateDetails.length; i++) {

    // make two new field in estimateDetails name as estimatedQuantity and mbAmount and assign them value 0
    estimateDetails[i].mbAmount = 0;
    estimateDetails[i].estimatedQuantity = 0;
    estimateDetails[i].consumedQuantity = 0;
    estimateDetails[i].currentQuantity = 0;

    if (estimateDetails[i].category === "OVERHEAD") {
        continue;
    }

    const sorId = estimateDetails[i].sorId || "null";
    const updatedArray = idEstimateDetailsMap[sorId] || [];

    updatedArray.push(estimateDetails[i]);
    idEstimateDetailsMap[sorId] = updatedArray;
    
  }
  const sorIdMeasuresMap = {};
  // iterate over idEstimateDetailsMap and from idEstimateDetailsMap[sorId] we will get array of estimateDetails and then get id of each estimateDetails and then match that id with estimateLineItemId of lineItems and get contractLineItemId and then match that contractLineItemId with targetId of measurement and get measures
  for (let i = 0; i < Object.keys(idEstimateDetailsMap).length; i++) {
    const sorId = Object.keys(idEstimateDetailsMap)[i];
    const estimateDetailsArray = idEstimateDetailsMap[sorId];

    // iterate over values of idEstimateDetailsMap and if isDeduction is true then subtract amount from mbAmount and if isDeduction is false then add amount to mbAmount
    for (let j = 0; j < estimateDetailsArray.length; j++) {
      if (estimateDetailsArray[j].isDeduction) {
        estimateDetailsArray[0].mbAmount -= estimateDetailsArray[j].amountDetail[0].amount;
      } else {
        estimateDetailsArray[0].mbAmount += estimateDetailsArray[j].amountDetail[0].amount;
      }

      // if isDeduction is true then subtract quantity from estimatedQuantity and if isDeduction is false then add quantity to estimatedQuantity
      if (estimateDetailsArray[j].isDeduction) {
        estimateDetailsArray[0].estimatedQuantity -= estimateDetailsArray[j].quantity;
      } else {
        estimateDetailsArray[0].estimatedQuantity += estimateDetailsArray[j].quantity;
      }

      // if isDeduction is true then subtract noOfunit from consumedQuantity and if isDeduction is false then add noOfunit to consumedQuantity
      if (estimateDetailsArray[j].isDeduction) {
        estimateDetailsArray[0].consumedQuantity -= estimateDetailsArray[j].noOfunit;
      } else {
        estimateDetailsArray[0].consumedQuantity += estimateDetailsArray[j].noOfunit;
      }
    }

    // if there is any square bracket in name of estimateDetail then replace it with round bracket because square bracket in the end of string is not supported in pdf
    if(estimateDetailsArray[0].name.includes("[")){
      estimateDetailsArray[0].name=estimateDetailsArray[0].name.replace("[","(")
      estimateDetailsArray[0].name=estimateDetailsArray[0].name.replace("]",")")
    }
    
    const {
      name: description,
      uom,
      unitRate,
      estimatedQuantity,
      consumedQuantity,
      currentQuantity,
      mbAmount,
      quantity,
      // amountDetail: [{ amount: mbAmount }],
    } = estimateDetailsArray[0];
  
    var sorIdMeasuresMapKey = {
      sorId,
      description,
      uom,
      unitRate,
      quantity,
      estimatedQuantity,
      consumedQuantity,
      currentQuantity,
      mbAmount,
    };
    sorIdMeasuresMap[sorId] = { ...sorIdMeasuresMapKey };
  }
  return sorIdMeasuresMap;
};

module.exports = {
  transformEstimateData
  };