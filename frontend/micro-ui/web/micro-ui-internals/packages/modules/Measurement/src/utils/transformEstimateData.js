/*\
input is estimatedetails array, contract object , type and measurement object 
output is array of object of type which is passed

*/

export const transformEstimateData = (lineItems, contract, type, measurement = {}, allMeasurements = []) => {
  /* logic to be updated according to business usecase*/
  //let isCreateorUpdate = window.location.href.includes("create") || window.location.href.includes("update");
  const lastMeasuredObject = allMeasurements?.filter?.((e) => e?.isActive)?.[0] || {};
  const transformedContract = transformContractObject(contract);
  const isMeasurement = measurement && Object.keys(measurement)?.length > 0;
  let isMeasurementCreate = window.location.href.includes("/create")
  const transformedEstimateObject = lineItems
    .filter((e) => e.category === type)
    .reduce((acc, curr) => {
      if (acc[curr.sorId]) {
        acc[curr.sorId].push(curr);
      } else {
        acc[curr.sorId] = [curr];
      }
      return acc;
    }, {});
  const transformMeasurementData = isMeasurement ? transformMeasureObject(measurement) : transformMeasureObject(lastMeasuredObject);
  return Object.keys(transformedEstimateObject).map((key, index) => {
    const measures = transformedEstimateObject[key].map((estimate, index) =>{
     const measuredObject= transformMeasurementData?.lineItemsObject ? transformMeasurementData?.lineItemsObject[transformedContract?.lineItemsObject[estimate?.id]?.contractLineItemId] : {};
    return ({
      sNo: index + 1,
      targetId: transformedContract?.lineItemsObject[estimate.id]?.contractLineItemId,
      isDeduction: estimate?.isDeduction,
      description: estimate?.description,
      id: measuredObject?.id || null,
      height: isMeasurementCreate ? 0 : (measuredObject?.height || 0),
      width: isMeasurementCreate ? 0 : (measuredObject?.breadth || 0),
      length: isMeasurementCreate ? 0 : (measuredObject?.length || 0),
      number: isMeasurementCreate ? 0 : (measuredObject?.numItems || 0),
      noOfunit:  isMeasurementCreate ? 0 : (measuredObject?.currentValue || 0),
      rowAmount: isMeasurementCreate ? 0 : (measuredObject?.additionalDetails?.mbAmount || 0),
      consumedRowQuantity: transformMeasurementData?.lineItemsObject?.[transformedContract?.lineItemsObject?.[estimate?.id]?.contractLineItemId]?.cumulativeValue || 0,
    })
  });
    return {
      amount:  window.location.href.includes("measurement/create") ? 0 : (measures?.reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.rowAmount : acc + curr?.rowAmount, 0) || 0),
      consumedQ: (measurement?.wfStatus === "APPROVED" || (allMeasurements.length > 0 && allMeasurements?.filter((ob) => ob?.wfStatus === "APPROVED")?.length > 0)) ? measures?.reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.consumedRowQuantity : acc + curr?.consumedRowQuantity, 0) : 0,
      sNo: index + 1,
      currentMBEntry: window.location.href.includes("measurement/create") ? 0 : ( measures?.reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.noOfunit : acc + curr?.noOfunit, 0) || 0),
      uom: transformedEstimateObject[key]?.[0]?.uom,
      description: transformedEstimateObject[key]?.[0]?.name,
      unitRate: transformedEstimateObject[key]?.[0]?.unitRate,
      contractNumber: transformedContract?.contractNumber,
      targetId: transformedContract?.lineItemsObject[transformedEstimateObject[key][0].id]?.contractLineItemId,
      approvedQuantity: transformedEstimateObject[key].reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.noOfunit : acc + curr.noOfunit, 0),
      showMeasure: false,
      sorId: key,
      measures,
    };
  });
};

export const transformContractObject = (contract = {}) => {
  return {
    contractNumber: contract?.contractNumber,
    lineItems: contract?.lineItems,
    estimateId: contract?.lineItems?.[0]?.estimateId,
    lineItemsObject: contract?.lineItems.reduce((acc, curr) => {
      //measuees
      acc[curr?.estimateLineItemId] = {
        //accc targetid
        estimateLineItemId: curr?.estimateLineItemId,
        contractLineItemId: curr?.contractLineItemRef,
        unitRate: curr?.unitRate,
      };

      return acc;
    }, {}),
  };
};

export const transformMeasureObject = (measurement = {}) => {
  return {
    lineItemsObject: measurement?.measures?.reduce((acc, curr) => {
      acc[curr?.targetId] = curr;
      return acc;
    }, {}),
  };
};

export const getDefaultValues = (data, t) => {
  const { contract, estimate, allMeasurements, measurement, musterRollNumber, period } = data;

  const SOR = transformEstimateData(estimate?.estimateDetails, contract, "SOR", measurement, allMeasurements);
  const NONSOR = transformEstimateData(estimate?.estimateDetails, contract, "NON-SOR", measurement, allMeasurements);

  // extract details from contract
  const {
    contractNumber,
    issueDate,
    additionalDetails: { projectId: projectID, projectName: projectName, projectDesc: projectDesc, locality: projectLoc, ward: projectWard },
  } = contract;
  const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId());
  const Pward = projectWard ? t(`${headerLocale}_ADMIN_${projectWard}`) : "";
  // const city = projectLoc ? t(`${Digit.Utils.locale.getTransformedLocale(projectLoc)}`) : "";
  const city = projectLoc ? t(`${headerLocale}_ADMIN_${projectLoc}`) : "";

  const projectLocation = `${Pward ? Pward + ", " : ""}${city}`;
  const measurementPeriod = `${Digit.DateUtils.ConvertEpochToDate(period?.startDate)} - ${Digit.DateUtils.ConvertEpochToDate(period?.endDate)}`;
  const musterRoll = typeof musterRollNumber == "string" ? musterRollNumber : "NA";

  const uploadedDocs = {}
  allMeasurements?.[0]?.documents.forEach((doc,index)=>{
        if(doc?.fileStore){
            uploadedDocs[`${doc?.additionalDetails?.fileType}`] = [
              ...(uploadedDocs[`${doc?.additionalDetails?.fileType}`] ? uploadedDocs[`${doc?.additionalDetails?.fileType}`] : []),
                [
                    doc?.additionalDetails?.fileName,
                    {
                        file:{
                          name: doc?.additionalDetails?.fileName,
                        },
                        fileStoreId:{
                            fileStoreId:doc.fileStore,
                            tenantId:doc?.additionalDetails?.tenantId
                        }
                    },
                    doc?.id
                ]
            ]
        }
    })
  const contractDetails = {
    contractNumber,
    projectID,
    projectName,
    projectDesc,
    projectLocation,
    sanctionDate: Digit.DateUtils.ConvertEpochToDate(issueDate),
    musterRollNo: musterRoll,
    measurementPeriod: measurementPeriod,
  };

  return { SOR, NONSOR, contractDetails, uploadedDocs, documents:measurement?.documents || allMeasurements?.[0]?.documents };
};
