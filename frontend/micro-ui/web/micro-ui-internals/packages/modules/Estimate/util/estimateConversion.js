const convertNumberFields=(text=null)=>{
    return text?text:0;

}

/*\
input is estimatedetails array and type
output is array of object of type which is passed

*/

export const transformEstimateObjects = (estimateData, type) => {
    let lineItems = estimateData?.estimateDetails ? estimateData?.estimateDetails : estimateData;
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
            description: e.description,
            amountid: e?.amountDetail?.[0]?.id || null,
            id: e?.id || null,
            height: convertNumberFields(e?.height),
            width: convertNumberFields(e?.width),
            length: convertNumberFields(e?.length),
            number: convertNumberFields(e?.quantity),
            noOfunit:convertNumberFields(e?.noOfunit),
            rowAmount: convertNumberFields(e?.amountDetail[0]?.amount),
            consumedRowQuantity: 0
        }));
        return {
            amount: measures?.reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.rowAmount : acc + curr?.rowAmount, 0),
            consumedQ : measures?.reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.consumedRowQuantity : acc + curr?.consumedRowQuantity, 0),
            sNo: index + 1,
            currentMBEntry:measures?.reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.noOfunit : acc + curr?.noOfunit, 0),
            uom: convertedObject[key]?.[0]?.uom,
            description: convertedObject[key]?.[0]?.name,
            unitRate: convertedObject[key]?.[0]?.unitRate,
            approvedQuantity: convertedObject[key].reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.noOfunit : acc + curr?.noOfunit, 0),
            showMeasure:false,
            sorCode : convertedObject[key]?.[0]?.sorId,
            sorType: estimateData?.additionalDetails?.sorSkillData?.filter((ob) => ob?.sorId === key)?.[0]?.sorType,
            sorSubType: estimateData?.additionalDetails?.sorSkillData?.filter((ob) => ob?.sorId === key)?.[0]?.sorSubType,
            category: convertedObject[key]?.[0]?.category,
            measures,
        };
    });
};



