/*\
input is estimatedetails array, contract object , type and measurement object 
output is array of object of type which is passed

*/

const measures = [
    {
        "id": "91925a44-ee04-4599-97de-6289674af7b9",
        "referenceId": "4e6bf7f2-8b44-4aff-8351-6a023244c44c",
        "targetId": "cd0631db-8718-4aa8-80c1-9ddc71f61dde",
        "length": 1,
        "breadth": 1,
        "height": 2,
        "numItems": 2,
        "currentValue": 4,
        "cumulativeValue": 146,
        "isActive": true,
        "comments": null,
        "auditDetails": {
            "createdBy": "40e3b45a-0f64-4e8c-8768-aab82c095b2d",
            "lastModifiedBy": null,
            "createdTime": 1695563037793,
            "lastModifiedTime": 1695563037793
        },
        "additionalDetails": null
    },
    {
        "id": "04f94cc2-eb9c-412a-b8d7-52e07380c8e0",
        "referenceId": "4e6bf7f2-8b44-4aff-8351-6a023244c44c",
        "targetId": "30b604ca-90c1-4999-98bc-69a29599af5c",
        "length": 2,
        "breadth": 1,
        "height": 1,
        "numItems": 3,
        "currentValue": 6,
        "cumulativeValue": 82,
        "isActive": true,
        "comments": null,
        "auditDetails": {
            "createdBy": "40e3b45a-0f64-4e8c-8768-aab82c095b2d",
            "lastModifiedBy": null,
            "createdTime": 1695563037793,
            "lastModifiedTime": 1695563037793
        },
        "additionalDetails": null
    },
    {
        "id": "9d7735b1-c590-42e5-8d0b-92392eeeb71f",
        "referenceId": "4e6bf7f2-8b44-4aff-8351-6a023244c44c",
        "targetId": "42bdc619-251b-402a-864c-edc0a9f37306",
        "length": 2,
        "breadth": 1,
        "height": 2,
        "numItems": 1,
        "currentValue": 4,
        "cumulativeValue": 2048,
        "isActive": true,
        "comments": null,
        "auditDetails": {
            "createdBy": "40e3b45a-0f64-4e8c-8768-aab82c095b2d",
            "lastModifiedBy": null,
            "createdTime": 1695563037793,
            "lastModifiedTime": 1695563037793
        },
        "additionalDetails": null
    }
]

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


