const { all } = require("axios");
const { pdf } = require("../config");

const transformEstimateData = (lineItems, contract, measurement, allMeasurements, estimateDetails, measurementNumber) => {

  var count = 100;
  // apply loop on lineItems and check if category is NON-SOR and sorId is 45 then change it to count++
  for (let i = 0; i < estimateDetails.length; i++) {
    count++;
    if (estimateDetails[i].category === "NON-SOR" && estimateDetails[i].sorId === "45") {
      estimateDetails[i].sorId = count;
    }
  }

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

      const quantity = estimateDetailsArray[j].quantity ?? 1;
      const numItems = estimateDetailsArray[j].noOfunit ?? 1;
      const length = estimateDetailsArray[j].length ?? 1;
      const width = estimateDetailsArray[j].width ?? 1;
      const height = estimateDetailsArray[j].height ?? 1;
      const estQ = quantity * length * width * height;

      // if isDeduction is true then subtract quantity from estimatedQuantity and if isDeduction is false then add quantity to estimatedQuantity
      if (estimateDetailsArray[j].isDeduction) {
        estimateDetailsArray[0].estimatedQuantity -= estQ;
      } else {
        estimateDetailsArray[0].estimatedQuantity += estQ;
      }

      const id = estimateDetailsArray[j].id;
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
          const q = numItems * length * width * height;

          if (estimateDetailsArray[j].isDeduction) {
            estimateDetailsArray[0].currentQuantity -= q;
          }
          else {
            estimateDetailsArray[0].currentQuantity += q;
          }
        }
      }

      const matchingLineItem1 = lineItems.find(item => item.estimateLineItemId === id);

      if (matchingLineItem1) {
        const contractLineItemId = matchingLineItem1.contractLineItemRef;
        var ismatch = false;
        for (let i = 0; i < allMeasurements.length; i++) {
          if (measurementNumber == allMeasurements[i].measurementNumber) {
            ismatch = true;
            continue;
          }
          if (allMeasurements[i].wfStatus == 'APPROVED' && ismatch) {
            // Find the measure with matching targetId
            const matchingMeasure = allMeasurements[i].measures.find(measure => measure.targetId === contractLineItemId);
            if (matchingMeasure) {
              // Update consumedQuantity in sorIdMeasuresMap
              if (estimateDetailsArray[j].isDeduction) {
                estimateDetailsArray[0].consumedQuantity -= matchingMeasure.cumulativeValue;
              }
              else {
                estimateDetailsArray[0].consumedQuantity += matchingMeasure.cumulativeValue;
              }
            }
            break;
          }
        }
      }

    }

    // if there is any square bracket in name of estimateDetail then replace it with round bracket because square bracket in the end of string is not supported in pdf
    if (estimateDetailsArray[0].name.includes("[")) {
      estimateDetailsArray[0].name = estimateDetailsArray[0].name.replace("[", "(")
      estimateDetailsArray[0].name = estimateDetailsArray[0].name.replace("]", ")")
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
        const mbAmount = sorIdMeasuresMap[sorId].unitRate * sorIdMeasuresMap[sorId].currentQuantity;
        sorIdMeasuresMap[sorId].mbAmount = mbAmount;
  }

  return sorIdMeasuresMap;
};

module.exports = {
  transformEstimateData
};