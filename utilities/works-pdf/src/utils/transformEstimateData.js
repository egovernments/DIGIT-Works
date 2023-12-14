const { pdf } = require("../config");

const transformEstimateData = (lineItems, contract, measurement, allMeasurements, estimateDetails) => {

  const idEstimateDetailsMap = {};
  for (let i = 0; i < estimateDetails.length; i++) {
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

    // if there is any square bracket in name of estimateDetail then replace it with round bracket because square bracket in the end of string is not supported in pdf
    if(estimateDetailsArray[0].name.includes("[")){
      estimateDetailsArray[0].name=estimateDetailsArray[0].name.replace("[","(")
      estimateDetailsArray[0].name=estimateDetailsArray[0].name.replace("]",")")
    }
    
    const {
      name: description,
      uom,
      unitRate,
      quantity,
      amountDetail: [{ amount: mbAmount }],
    } = estimateDetailsArray[0];
  
    var sorIdMeasuresMapKey = {
      sorId,
      description,
      uom,
      unitRate,
      quantity,
      mbAmount,
    };
    sorIdMeasuresMap[sorId] = { ...sorIdMeasuresMapKey };
  }
  return sorIdMeasuresMap;
};

module.exports = {
  transformEstimateData
  };