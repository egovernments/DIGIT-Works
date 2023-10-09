/*\
input is estimatedetails array, contract object , type and measurement object 
output is array of object of type which is passed

*/

export const transformEstimateData = (lineItems, contract, type, measurement = {}, allMeasurements = []) => {
  /* logic to be updated according to business usecase*/
  const lastMeasuredObject = allMeasurements?.filter?.((e) => e?.isActive)?.[0] || {};
  const transformedContract = transformContractObject(contract);
  const isMeasurement = measurement && Object.keys(measurement)?.length > 0;
  const convertedObject = lineItems
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
  return Object.keys(convertedObject).map((key, index) => {
    const measures = convertedObject[key].map((e, index) => ({
      sNo: index + 1,
      targetId: transformedContract?.lineItemsObject[e.id]?.contractLineItemId,
      isDeduction: e?.additionalDetails?.isDeduction,
      description: e?.description,
      id: isMeasurement ? transformMeasurementData?.lineItemsObject[transformedContract?.lineItemsObject[e.id]?.contractLineItemId]?.id : null,
      height: isMeasurement ? transformMeasurementData?.lineItemsObject[transformedContract?.lineItemsObject[e.id]?.contractLineItemId]?.height : 0,
      width: isMeasurement ? transformMeasurementData?.lineItemsObject[transformedContract?.lineItemsObject[e.id]?.contractLineItemId]?.breadth : 0,
      length: isMeasurement ? transformMeasurementData?.lineItemsObject[transformedContract?.lineItemsObject[e.id]?.contractLineItemId]?.length : 0,
      number: isMeasurement ? transformMeasurementData?.lineItemsObject[transformedContract?.lineItemsObject[e.id]?.contractLineItemId]?.numItems : 0,
      noOfunit: isMeasurement
        ? transformMeasurementData?.lineItemsObject[transformedContract?.lineItemsObject[e.id]?.contractLineItemId]?.currentValue
        : 0,
      rowAmount: isMeasurement
        ? transformMeasurementData?.lineItemsObject[transformedContract?.lineItemsObject[e.id]?.contractLineItemId]?.additionalDetails?.mbAmount
        : 0,
      consumedRowQuantity:
        transformMeasurementData?.lineItemsObject?.[transformedContract?.lineItemsObject?.[e?.id]?.contractLineItemId]?.cumulativeValue || 0,
    }));
    return {
      amount: isMeasurement ? measures?.reduce((acc, curr) => acc + curr?.rowAmount, 0) : 0,
      consumedQ: isMeasurement ? measures?.reduce((acc, curr) => acc + curr?.consumedRowQuantity, 0) : 0,
      sNo: index + 1,
      currentMBEntry: isMeasurement ? measures?.reduce((acc, curr) => acc + curr?.noOfunit, 0) : 0,
      uom: convertedObject[key]?.[0]?.uom,
      description: convertedObject[key]?.[0]?.name,
      unitRate: convertedObject[key]?.[0]?.unitRate,
      contractNumber: transformedContract?.contractNumber,
      targetId: transformedContract?.lineItemsObject[convertedObject[key][0].id]?.contractLineItemId,
      approvedQuantity: convertedObject[key].reduce((acc, curr) => acc + curr.noOfunit, 0),
      showMeasure: false,
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
        contractLineItemId: curr?.id,
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

  return { SOR, NONSOR, contractDetails };
};
