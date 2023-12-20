/*
input is document array, 
output is document array containing object
*/

const processDocuments = (uploadedDocs, data) => {
  const documents = [];

  for (const docType in uploadedDocs) {
    if (uploadedDocs.hasOwnProperty(docType)) {
      const docList = uploadedDocs[docType];
      docList.forEach(([filename, fileInfo]) => {
        const document = {
          documentType: fileInfo?.file?.type,
          fileStore: fileInfo?.fileStoreId?.fileStoreId,
          documentUid: fileInfo?.file?.name,
          id: data?.documents?.length == 1 ? data?.documents?.[0]?.id : null,
          additionalDetails: {
            fileType: docType,
            tenantId: fileInfo?.fileStoreId?.tenantId,
            fileName: fileInfo?.file?.name,
          },
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
  item?.measures?.map((measure) => {
    const measurement = {
      referenceId: null,
      id: measure?.id,
      targetId: measure?.targetId,
      length: parseFloat(measure?.length),
      breadth:parseFloat(measure?.width),
      height: parseFloat(measure?.height),
      numItems: measure?.number,
      currentValue: measure?.noOfunit,
      description : measure?.description,
      cumulativeValue: 0,
      isActive: true,
      comments: null,
      additionalDetails: {
        mbAmount: measure?.rowAmount || 0,
        type: type,
      },
    };
    measurements.push(measurement);
  });
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

const measurement= {
  id: data?.id ? data?.id : null,
  measurementNumber: data?.measurementNumber ? data?.measurementNumber : null,
  tenantId: "pg.citya",
  physicalRefNumber: null,
  referenceId: data.SOR?.[0]?.contractNumber || data.NONSOR?.[0]?.contractNumber,
  entryDate: 0,
  documents: processDocuments(data.uploadedDocs, data),
  measures: [],
  isActive: true,
  additionalDetails: {
    sorAmount: data.sumSor || 0,
    nonSorAmount: data.sumNonSor || 0,
    totalAmount: (data.sumSor ? data.sumSor : 0) + (data.sumNonSor ? data.sumNonSor : 0),
    startDate: data?.period?.startDate,
    endDate: data?.period?.endDate,
    musterRollNumber: data?.musterRollNumber,
  },
  wfStatus: null,
  workflow: {
    action: data?.wfStatus === "SENT_BACK" ? "EDIT/RE-SUBMIT" : data?.workflowAction,
  },
}
  let sumSor = 0;
  let sumNonSor = 0;

  // Process SOR data
  if (data.SOR && Array.isArray(data.SOR)) {
    data.SOR.forEach((sorItem) => {
      // sumSor += sorItem.measures?.[0]?.rowAmount;
      measurement.measures.push(...getMeasurementFromMeasures(sorItem, "SOR"));
      sorItem.measures.forEach((measure) => {
        if (measure.rowAmount) {
          sumSor += measure.rowAmount;
        }
      });
    });
  }

  // Process NONSOR data
  if (data.NONSOR && Array.isArray(data.NONSOR)) {
    data.NONSOR.forEach((nonsorItem) => {
      sumNonSor += nonsorItem.measures?.[0]?.rowAmount;
      measurement.measures.push(...getMeasurementFromMeasures(nonsorItem, "NONSOR"));
      nonsorItem.measures?.forEach((measure) => {
        if (measure.rowAmount) {
          sumNonSor += measure.rowAmount;
        }
      });
    });
  }

  // update the additional details
  measurement.additionalDetails={
    ...measurement.additionalDetails,
    ...{sorAmount : sumSor, nonSorAmount : sumNonSor,totalAmount : sumSor + sumNonSor}
  }

  /* added as a temporary fix that sends entrydate */
  measurement.entryDate=new Date().getTime();
  const transformedData = {
    measurements: [
     measurement
    ],
  };

  return transformedData;
};
