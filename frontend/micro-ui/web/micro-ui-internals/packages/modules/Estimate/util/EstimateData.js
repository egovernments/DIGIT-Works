/*\
input is estimatedetails array, contract object , type and measurement object 
output is array of object of type which is passed

*/

export const transformEstimateData = (category = "NON-SOR") => {
  return {
    amount: 0,
    consumedQ: 0,
    sNo: 1, // You can set this to any specific value you want
    uom: null,
    description: null,
    unitRate: 0,
    category: category,
    targetId: null,
    approvedQuantity: 0,
    measures: [
      {
        sNo: 1,
        targetId: null,
        isDeduction: false,
        description: null,
        id: 0,
        height: 0,
        width: 0,
        length: 0,
        number: 0,
        noOfunit: 0,
        rowAmount: 0,
        consumedRowQuantity: 0,
      },
    ],
  };
};

//methods is used to calculate the labour, material, machinery charges
export const getLabourMaterialAnalysisCost = (formData, categories) => {
  let SORAmount = formData?.SORtable?.reduce((tot, ob) => {
    let amount = ob?.amountDetails?.reduce(
      (total, item) => total + (categories.some((category) => item?.heads?.includes(category)) ? item?.amount : 0),
      0
    );
    return tot + amount * ob?.currentMBEntry;
  }, 0);
  SORAmount = SORAmount ? SORAmount : 0;
  return SORAmount.toFixed(2);
};

export const transformStatementData = (data,screenType) => {
  const nestedData = [];

  const { sorDetails } = data;

  sorDetails.filter((ob) => ob?.isActive === true).forEach((sorDetail, sorIndex) => {
    const { lineItems } = sorDetail;

    // Main SOR data
    const mainSORRow = {
      sNo: nestedData.length + 1,
      sortype: `${sorDetail?.additionalDetails?.sorDetails?.sorType}`,
      sorSubType : sorDetail?.additionalDetails?.sorDetails?.sorSubType,
      code: sorDetail.sorId,
      description: sorDetail?.additionalDetails?.sorDetails?.description,
      uom: sorDetail?.additionalDetails?.sorDetails?.uom,
      rate: parseFloat(sorDetail?.additionalDetails?.rateDetails?.rate).toFixed(2),
      type: sorDetail?.additionalDetails?.sorDetails?.sorType ,
      estimatedQuantity: {
        M: screenType==="ANALYSIS"?sorDetail?.additionalDetails?.estimatedQuantity:sorDetail?.additionalDetails?.consumedQuantity,
        L:
         screenType==="ANALYSIS"?sorDetail?.additionalDetails?.estimatedQuantity:sorDetail?.additionalDetails?.consumedQuantity,
        E:
         screenType==="ANALYSIS"?sorDetail?.additionalDetails?.estimatedQuantity:sorDetail?.additionalDetails?.consumedQuantity,
      }, // Hardcoded

      // TODO:[previous code]
      // estimatedAmount:
      //   sorDetail.lineItems.length > 0
      //     ? sorDetail.lineItems.reduce((sum, detail) => sum + (detail.amountDetails?.[0]?.amount || 0), 0)
      //     : sorDetail?.basicSorDetails?.[0]?.amount, // Sum of amounts in basicSorDetails

      // end of the comment

      estimatedAmount: {
        M:
       screenType==="ANALYSIS"? sorDetail?.additionalDetails?.estimatedAmount:sorDetail?.additionalDetails?.consumedAmount,
        L:
       screenType==="ANALYSIS"? sorDetail?.additionalDetails?.estimatedAmount:sorDetail?.additionalDetails?.consumedAmount,
        E:
        screenType==="ANALYSIS"? sorDetail?.additionalDetails?.estimatedAmount:sorDetail?.additionalDetails?.consumedAmount,
      },

      subrows: [], // Initialize subrows array
    };

    sorDetail.lineItems !== null && sorDetail?.additionalDetails?.sorDetails?.sorType === "W"?
    // Sub-table rows (line items)
    lineItems.forEach((lineItem,index) => {
      const { amountDetails } = lineItem?.additionalDetails?.rateDetails;

        const subrow = {
          sNo: index + 1,
          code: lineItem?.sorId,
          name: lineItem?.additionalDetails?.sorDetails?.description,
          unit: lineItem?.additionalDetails?.sorDetails?.uom,
          rate: lineItem?.additionalDetails?.rateDetails?.rate,
          quantity: parseFloat(lineItem?.basicSorDetails[0]?.quantity),
          amount: lineItem?.basicSorDetails[0]?.amount,
          type: lineItem?.additionalDetails?.sorDetails?.sorType,
        };
        mainSORRow.subrows.push(subrow);
    }):[];

    nestedData.push(mainSORRow);
  });

  return nestedData;
};

export const sortSorsBasedonType = (statement,screenType) => {
  // Function to create the array of objects based on the requirement

  let resultArray = [];
   let data=[statement[0]];
  data.forEach((item) => {
    item.sorDetails.filter((ob) => ob?.isActive === true).forEach((detail) => {
      let sorId = (detail.lineItems!==null&& detail.lineItems.length > 0) ? detail.lineItems[0].sorId : detail.sorId;
      let description = null;
      let type = null;
      let amount = null;
      let quantity = null;
      let uom = null;
      let rate = null;

      // If lineItems exist, use them; otherwise, use basicSorDetails
      if ( detail.lineItems !==null && detail.lineItems.length > 0) {
        detail.lineItems.forEach((line) => {
          sorId=line?.sorId,
            description = line?.additionalDetails?.sorDetails?.description;
            type = line?.additionalDetails?.sorDetails?.sorType;
            amount = line?.basicSorDetails?.[0]?.amount;
            quantity = line?.basicSorDetails[0]?.quantity;
            uom = line?.additionalDetails?.sorDetails?.uom;
            rate = line.additionalDetails?.rateDetails?.rate;

            resultArray.push({
              sorId,
              description,
              type,
              amount,
              quantity,
              uom,
              rate,
            });
          });
        
      } else {
        detail?.basicSorDetails.forEach((basic) => {
          description = detail?.additionalDetails?.sorDetails?.description;
          type = basic.type;
          amount = screenType==="ANALYSIS"? detail?.additionalDetails?.estimatedAmount:detail?.additionalDetails?.consumedAmount;
          quantity = screenType==="ANALYSIS"?detail?.additionalDetails?.estimatedQuantity:detail?.additionalDetails?.consumedQuantity;
          uom = detail?.additionalDetails?.sorDetails?.uom;
          rate = parseFloat(detail?.additionalDetails?.rateDetails?.rate).toFixed(2),
          resultArray.push({
            sorId,
            description,
            type,
            amount,
            quantity,
            uom,
            rate,
          });
        });
      }
    });
  });
 
  return resultArray;

  // Create the array of objects based on the provided statement data
};