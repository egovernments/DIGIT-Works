/*
input is document array, 
output is document array containing object
*/

const processDocuments = (uploadedDocs) => {
    const documents = [];

    for (const docType in uploadedDocs) {
        if (uploadedDocs.hasOwnProperty(docType)) {
            const docList = uploadedDocs[docType];
            docList.forEach(([filename, fileInfo]) => {
                const document = {
                    documentType: fileInfo?.file?.type,
                    fileStore: fileInfo?.fileStoreId?.fileStoreId,
                    documentUid: fileInfo?.file?.name,
                    additionalDetails: {},
                };
                documents.push(document);
            });
        }
    }

    return documents;
};



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
    parent measurement details
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
                documents: processDocuments(data.uploadedDocs),
                measures: [],
                isActive: true,
                additionalDetails: {
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


    let sumSor = 0;
    let sumNonSor = 0;

    // Process SOR data
    if (data.SOR && Array.isArray(data.SOR)) {
        data.SOR.forEach((sorItem) => {
            sumSor += sorItem.amount;
            transformedData.measurements[0].measures.push(...getMeasurementFromMeasures(sorItem, "SOR"));
        });
    }

    // Process NONSOR data
    if (data.NONSOR && Array.isArray(data.NONSOR)) {
        data.NONSOR.forEach((nonsorItem) => {
            sumNonSor += nonsorItem.amount;
            transformedData.measurements[0].measures.push(...getMeasurementFromMeasures(nonsorItem, "NONSOR"));
        });
    }

    // update the additional details
    transformedData.measurements[0].additionalDetails.sorAmount = sumSor;
    transformedData.measurements[0].additionalDetails.nonSorAmount = sumNonSor;
    transformedData.measurements[0].additionalDetails.totalAmount = sumSor + sumNonSor;

    return transformedData;
};
