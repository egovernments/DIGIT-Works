/*\
input is estimatedetails array, contract object , type and measurement object 
add comments

*/

export const transformEstimateData = (lineItems, contract, type, measurement = {}) => {
    const transformedContract = transformContractObject(contract)
    const isMeasurement = measurement && Object.keys(measurement)?.length > 0
    const convertedObject = lineItems.filter(e => e.category === type).reduce((acc, curr) => {
        if (acc[curr.sorId]) {
            acc[curr.sorId].push(curr);
        } else {
            acc[curr.sorId] = [curr]
        }
        return acc;
    }, {});
    return Object.keys(convertedObject).map((key, index) => {
        return {

            amount: 0,
            consumedQ: 0,
            sNo: index + 1,
            currentMBEntry: 0,
            uom: convertedObject[key]?.[0]?.uom,
            description: convertedObject[key]?.[0]?.name,
            unitRate: convertedObject[key]?.[0]?.unitRate,
            contractNumber: transformedContract?.contractNumber,
            targetId: transformedContract?.lineItemsObject[convertedObject[key][0].id]?.contractLineItemId,
            approvedQuantity: convertedObject[key].reduce((acc, curr) => acc + curr.noOfunit, 0),
            measures: convertedObject[key].map((e, index) => ({
                sNo: index + 1,
                targetId: transformedContract?.lineItemsObject[e.id]?.contractLineItemId,
                isDeduction: e.additionalDetails.isDeduction,
                description: e.description,
                height: isMeasurement ? e.additionalDetails.height : 0,
                width: isMeasurement ? e.additionalDetails.width : 0,
                length: isMeasurement ? e.additionalDetails.length : 0,
                number: isMeasurement ? e.uomValue : 0,
                noOfunit: isMeasurement ? e.noOfunit : 0,
                rowAmount: isMeasurement ? e.amountDetail[0].amount : 0,
            }))
        }
    })

};




export const transformContractObject = (contract = {}) => {
    return {
        contractNumber: contract?.contractNumber,
        lineItems: contract?.lineItems,
        estimateId: contract?.lineItems?.[0]?.estimateId,
        lineItemsObject: contract?.lineItems.reduce((acc, curr) => {
            acc[curr?.estimateLineItemId] = {
                estimateLineItemId: curr?.estimateLineItemId,
                contractLineItemId: curr?.id,
                unitRate: curr?.unitRate
            }

            return acc;
        }, {})

    }

}
