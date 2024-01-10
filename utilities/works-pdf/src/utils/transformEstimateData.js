const { pdf } = require("../config");

const transformEstimateData = (lineItems, contract, measurement, allMeasurements, estimateDetails) => {

  const idEstimateDetailsMap = {};
  for (let i = 0; i < estimateDetails.length; i++) {

    // make new field in estimateDetails 
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

      const numItems = estimateDetailsArray[j].noOfunit ?? 1;
      const length = estimateDetailsArray[j].length ?? 1;
      const width = estimateDetailsArray[j].width ?? 1;
      const height = estimateDetailsArray[j].height ?? 1;
      const estQ = numItems*length*width*height;
    
      // if isDeduction is true then subtract quantity from estimatedQuantity and if isDeduction is false then add quantity to estimatedQuantity
      if (estimateDetailsArray[j].isDeduction) {
        estimateDetailsArray[0].estimatedQuantity -= estQ;
      } else {
        estimateDetailsArray[0].estimatedQuantity += estQ;
      }
    }

    // if there is any square bracket in name of estimateDetail then replace it with round bracket because square bracket in the end of string is not supported in pdf
    if(estimateDetailsArray[0].name.includes("[")){
      estimateDetailsArray[0].name=estimateDetailsArray[0].name.replace("[","(")
      estimateDetailsArray[0].name=estimateDetailsArray[0].name.replace("]",")")
    }
    
    const {
      id,
      name: description,
      uom,
      unitRate,
      category,
      estimatedQuantity,
      consumedQuantity,
      currentQuantity,
      mbAmount,
      isDeduction,
      quantity,
    } = estimateDetailsArray[0];
  
    var sorIdMeasuresMapKey = {
      id,
      sorId,
      description,
      uom,
      unitRate,
      category,
      quantity,
      isDeduction,
      estimatedQuantity,
      consumedQuantity,
      currentQuantity,
      mbAmount,
    };
    sorIdMeasuresMap[sorId] = { ...sorIdMeasuresMapKey };
  }

  // iterate over sorIdMeasuresMap
  for (const sorId of Object.keys(sorIdMeasuresMap)) {
    const id = sorIdMeasuresMap[sorId].id;

    // Find the line item with matching estimateLineItemId
    const matchingLineItem = lineItems.find(item => item.estimateLineItemId === id);

    if (matchingLineItem) {
        const contractLineItemId = matchingLineItem.contractLineItemRef;

        // Find the measure with matching targetId
        const matchingMeasure = measurement.measures.find(measure => measure.targetId === contractLineItemId);

        if (matchingMeasure) {

          const numItems = matchingMeasure.numItems ?? 1;
          const length = matchingMeasure.length ?? 1;
          const width = matchingMeasure.breadth ?? 1;
          const height = matchingMeasure.height ?? 1;
          const q = (numItems*length*width*height).toFixed(2);

          if(sorIdMeasuresMap[sorId].isDeduction){
            sorIdMeasuresMap[sorId].currentQuantity -= q;
          }
          else{
            sorIdMeasuresMap[sorId].currentQuantity += q;
          }

          // Update consumedQuantity in sorIdMeasuresMap
          sorIdMeasuresMap[sorId].consumedQuantity = matchingMeasure.cumulativeValue;
        }
    }
}

  return sorIdMeasuresMap;
};

module.exports = {
  transformEstimateData
  };