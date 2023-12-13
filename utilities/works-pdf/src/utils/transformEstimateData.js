const { pdf } = require("../config");

const transformEstimateData = (lineItems, contract, measurement, allMeasurements, estimateDetails) => {

  var idEstimateDetailsMap = {};
  for (let i = 0; i < estimateDetails.length; i++) {
    if(estimateDetails[i].category=="OVERHEAD"){
      continue;
    }
    if(estimateDetails[i].sorId==null){
      if(idEstimateDetailsMap["null"]){
        var updatedArray=idEstimateDetailsMap["null"]
        updatedArray.push(estimateDetails[i])
        idEstimateDetailsMap["null"]=updatedArray
      }
      else{
        idEstimateDetailsMap["null"]=[estimateDetails[i]]
      }
    }
    else{
      if(idEstimateDetailsMap[estimateDetails[i].sorId]){
        const updatedArray=idEstimateDetailsMap[estimateDetails[i].sorId]
        updatedArray.push(estimateDetails[i])
        idEstimateDetailsMap[estimateDetails[i].sorId]=updatedArray
      }
      else{
        idEstimateDetailsMap[estimateDetails[i].sorId]=[estimateDetails[i]]
      }
    }
  }
  const sorIdMeasuresMap = {};
  // iterate over idEstimateDetailsMap and from idEstimateDetailsMap[sorId] we will get array of estimateDetails and then get id of each estimateDetails and then match that id with estimateLineItemId of lineItems and get contractLineItemId and then match that contractLineItemId with targetId of measurement and get measures
  for (let i = 0; i < Object.keys(idEstimateDetailsMap).length; i++) {
    const sorId = Object.keys(idEstimateDetailsMap)[i];
    const estimateDetailsArray = idEstimateDetailsMap[sorId];

    // if there is any square bracket in name then replace it with round bracket
    if(estimateDetailsArray[0].name.includes("[")){
      estimateDetailsArray[0].name=estimateDetailsArray[0].name.replace("[","(")
      estimateDetailsArray[0].name=estimateDetailsArray[0].name.replace("]",")")
    }

    var description = estimateDetailsArray[0].name;
    var uom = estimateDetailsArray[0].uom;
    var unitRate = estimateDetailsArray[0].unitRate;
    var quantity = estimateDetailsArray[0].quantity;
    var mbAmount = estimateDetailsArray[0].amountDetail[0].amount;
    
  

    var sorIdMeasuresMapKey = {
      sorId: sorId,
      description: description,
      uom: uom,
      unitRate: unitRate,
      quantity: quantity,
      mbAmount: mbAmount
    };    
    sorIdMeasuresMapKey = {
      ...sorIdMeasuresMapKey
    }


    sorIdMeasuresMap[sorId] = sorIdMeasuresMapKey;
  }
  return sorIdMeasuresMap;
};

module.exports = {
  transformEstimateData
  };