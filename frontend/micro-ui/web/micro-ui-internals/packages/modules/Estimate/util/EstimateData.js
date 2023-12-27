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
export const getLabourMaterialAnalysisCost = (formData,category) => {

      let SORAmount = formData?.SORtable?.reduce((tot,ob) => {
        let amount = ob?.amountDetails?.reduce((total,item) => item?.heads?.includes(category) ? (item?.amount + total) : total,0);
        return (tot + amount * ob?.currentMBEntry)
    },0);
    SORAmount = SORAmount ? SORAmount : 0;
    return (SORAmount).toFixed(2);
}


