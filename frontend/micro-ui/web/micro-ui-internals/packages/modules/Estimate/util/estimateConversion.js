const convertNumberFields=(text=null)=>{
    return text?text:0;

}

/*\
input is estimatedetails array and type
output is array of object of type which is passed

*/

const fetchData = (RatesData, sorid) => {
    let currentDateInMillis = new Date().getTime(); 
        const Rates = RatesData?.MdmsRes?.["WORKS-SOR"]?.Rates?.filter((rate) => {
          // Convert validFrom and validTo to milliseconds
          let validFromInMillis = new Date(parseInt(rate?.validFrom)).getTime();
          let validToInMillis = rate?.validTo ? new Date(parseInt(rate?.validTo)).getTime() : Infinity;
          // Check if the current date is within the valid date range
          return rate.sorId === sorid
                      && validFromInMillis <= currentDateInMillis
                      && currentDateInMillis < validToInMillis;
        });
        return Rates;
      
  };

  const fetchUnitRate = (RatesData, sorId, oldUnitRate) => {
    try {
      let updatedRate = fetchData(RatesData, sorId);
  
      if (
        sorId && updatedRate?.[0]?.rate &&
        (window.location.href.includes("create") || window.location.href.includes("update")) &&
        updatedRate?.[0]?.rate !== oldUnitRate
      ) {
        return updatedRate?.[0]?.rate;
      } else {
        return oldUnitRate;
      }
    } catch (error) {
      console.error(error);
      return oldUnitRate;
    }
  };

  const fetchOriginalQtyAndAmount = (allDetailedEstimate,estimateData, type) => {
    //For old application version is coming as null for original estimates
    let lineItems = allDetailedEstimate?.estimates?.filter((ob) => ob?.versionNumber === 0 || ob?.versionNumber === null)?.[0]?.estimateDetails;
    //for new application version is coming as 1 for original estimates
    if(!lineItems)
      lineItems = allDetailedEstimate?.estimates?.filter((ob) => ob?.versionNumber === 1)?.[0]?.estimateDetails;
    lineItems?.filter(e => e.category === "NON-SOR")
    .forEach((item, index) => {
      // Check if the "sorId" is not null or undefined
      if (item.sorId !== null && item.sorId !== undefined && item?.sorId === "45") {
          // Update the "sorId" with the desired sequence
          item.sorId = (index + 1).toString();
      }
      });
    const convertedOriginalObject = lineItems?.filter(e => e.category === type).reduce((acc, curr) => {
        if (acc[curr.sorId]) {
            acc[curr.sorId].push(curr);
        } else {
            acc[curr.sorId] = [curr];
        }
        return acc;
    }, {});
    return convertedOriginalObject;
  }
  

export const transformEstimateObjects = (estimateData, type, RatesData, allDetailedEstimate) => {
  const originalConvertedObject = fetchOriginalQtyAndAmount(allDetailedEstimate,estimateData, type );
    let lineItems = estimateData?.estimateDetails ? estimateData?.estimateDetails : estimateData;
    let isEstimateCreateorUpdate = /(estimate\/create-detailed-estimate|estimate\/update-detailed-estimate|estimate\/create-revision-detailed-estimate|estimate\/update-revision-detailed-estimate)/.test(window.location.href);
    lineItems?.filter(e => e.category === "NON-SOR")
    .forEach((item, index) => {
      // Check if the "sorId" is not null or undefined
      if (item.sorId !== null && item.sorId !== undefined && item?.sorId === "45") {
          // Update the "sorId" with the desired sequence
          item.sorId = (index + 1).toString();
      }
      });
    const convertedObject = lineItems?.filter(e => e.category === type).reduce((acc, curr) => {
        if (acc[curr.sorId]) {
            acc[curr.sorId].push(curr);
        } else {
            acc[curr.sorId] = [curr];
        }
        return acc;
    }, {});
    return convertedObject && Object.keys(convertedObject).map((key, index) => {
        const measures = convertedObject[key].map((e, index) => ({
            sNo: index + 1,
            isDeduction: e?.isDeduction,
            description: e?.description?.trim(),
            amountid: e?.amountDetail?.[0]?.id || null,
            id: e?.id || null,
            previousLineItemId: e?.previousLineItemId || null,
            height: convertNumberFields(e?.height),
            width: convertNumberFields(e?.width),
            length: convertNumberFields(e?.length),
            number: convertNumberFields(e?.quantity),
            noOfunit:convertNumberFields(e?.noOfunit),
            rowAmount: (isEstimateCreateorUpdate && type === "SOR") ? fetchUnitRate( RatesData, convertedObject[key]?.[0]?.sorId,convertedObject[key]?.[0]?.unitRate) * convertNumberFields(e?.noOfunit) : convertNumberFields(e?.amountDetail[0]?.amount),
            consumedRowQuantity: 0
        }));
        return {
            amount: measures?.reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.rowAmount : acc + curr?.rowAmount, 0),
            originalAmount : originalConvertedObject?.[key]?.reduce((total, item) => total + (item.isDeduction ? -item.amountDetail[0].amount : item.amountDetail[0].amount), 0) /*|| convertedObject[key]?.[0]?.amountDetail?.[0]?.amount*/,
            originalQty : originalConvertedObject?.[key]?.reduce((total, item) => total + (item.isDeduction ? -item.noOfunit : item.noOfunit), 0) /*|| convertedObject?.[key]?.reduce((total, item) => total + (item.isDeduction ? -item.noOfunit : item.noOfunit), 0)*/,
            consumedQ : measures?.reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.consumedRowQuantity : acc + curr?.consumedRowQuantity, 0),
            sNo: index + 1,
            currentMBEntry:measures?.reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.noOfunit : acc + curr?.noOfunit, 0),
            uom: convertedObject[key]?.[0]?.uom,
            description: convertedObject[key]?.[0]?.name,
            unitRate: (isEstimateCreateorUpdate && type === "SOR") ? fetchUnitRate( RatesData, convertedObject[key]?.[0]?.sorId,convertedObject[key]?.[0]?.unitRate) : convertedObject[key]?.[0]?.unitRate,
            approvedQuantity: convertedObject[key].reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.noOfunit : acc + curr?.noOfunit, 0),
            showMeasure:false,
            sorCode : convertedObject[key]?.[0]?.sorId,
            sorType: estimateData?.additionalDetails?.sorSkillData?.filter((ob) => ob?.sorId === key)?.[0]?.sorType,
            sorSubType: estimateData?.additionalDetails?.sorSkillData?.filter((ob) => ob?.sorId === key)?.[0]?.sorSubType,
            category: convertedObject[key]?.[0]?.category,
            //need estimate id for estimate conversion
            estimateId : estimateData?.id,
            measures,
        };
    });
};



