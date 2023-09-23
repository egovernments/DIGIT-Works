
/*
input is lineitem, 
output is aray of measurements for them
*/
const getMeasurementFromMeasures = (item, type) => {


    const measurements = [];
    item?.measures?.map(measure => {
        const measurement = {
            referenceId: null,
            targetId: measure?.targetId,
            length: measure?.length,
            width: measure?.width,
            height: measure?.height,
            numItems: measure?.number,
            currentValue: measure?.noOfunit,
            cumulativeValue: 0,
            isActive: true,
            comments: null,
            additionalDetails: {
                mbAmount: measure?.rowAmount || 0,
                type: type,
            },
        }
        measurements.push(measurement);
    })
    return measurements;
};





/*
input is formdata, 
output is measurements[{
    //parent measurement details
    and measures array
}]

*/




export const transformData = (data) => {

    const transformedData = {
        measurements: [
            {
                tenantId: "pg.citya",
                physicalRefNumber: null,
                referenceId: data.SOR?.[0]?.contractNumber || data.NONSOR?.[0]?.contractNumber,
                entryDate: 0,
                //data?.uploadedDocs?.img_measurement_book iterate in this array map ==>  {}
                documents: [
                    {
                        "documentType": data?.uploadedDocs?.img_measurement_book?.[0]?.[1]?.file?.type,
                        "fileStore": data?.uploadedDocs?.img_measurement_book?.[0]?.[1]?.fileStoreId?.fileStoreId,
                        "documentUid": data?.uploadedDocs?.img_measurement_book?.[0]?.[0],
                        "additionalDetails": {},
                    },
                ],
                measures: [],
                isActive: true,
                additionalDetails: {
                    /// add logi c to calculate sum pof sor amt and nonsor amt
                    sorAmount: data.sumSor || 0,
                    nonSorAmount: data.sumNonSor || 0,
                    totalAmount: (data.sumSor ? data.sumSor : 0) + (data.sumNonSor ? data.sumNonSor : 0),
                },
                "wfStatus": "DRAFTED",
                "workflow": {
                    "action": "SAVE_AS_DRAFT",
                    "assignes": [],
                    "comments": "string",
                    "verificationDocuments": [
                        {
                            "documentType": "string",
                            "fileStore": "be14ceb8-01ba-485b-a6e2-489e5474a576",
                            "documentUid": "string",
                            "additionalDetails": {},
                        },
                    ],
                },
            },
        ],
    };
    // Process SOR data
    if (data.SOR && Array.isArray(data.SOR)) {
        data.SOR.forEach((sorItem) => {
            transformedData.measurements[0].measures.push(...getMeasurementFromMeasures(sorItem, "SOR"));
        });
    }
    // Process NONSOR data
    if (data.NONSOR && Array.isArray(data.NONSOR)) {
        data.NONSOR.forEach((nonsorItem) => {
            transformedData.measurements[0].measures.push(...getMeasurementFromMeasures(nonsorItem, "NONSOR"));
        });
    }
    return transformedData; // Return the transformed data object, not the function itself
};
