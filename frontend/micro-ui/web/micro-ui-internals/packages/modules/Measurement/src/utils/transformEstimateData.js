/*\
input is estimatedetails array, contract object , type and measurement object 
output is array of object of type which is passed

*/

import { Link } from "react-router-dom/cjs/react-router-dom.min";
import React from "react";

export const transformEstimateData = (lineItems, contract, type, measurement = {}, allMeasurements = [], showM=false) => {
  /* logic to be updated according to business usecase*/
  //let isCreateorUpdate = window.location.href.includes("create") || window.location.href.includes("update");
  const lastMeasuredObject = allMeasurements?.filter?.((e) => e?.isActive)?.[0] || {};
  const transformedContract = transformContractObject(contract);
  const isMeasurement = measurement && Object.keys(measurement)?.length > 0;
  let isMeasurementCreate = window.location.href.includes("measurement/create")

  
  lineItems?.filter(e => e.category === "NON-SOR")
    .forEach((item, index) => {
      // Check if the "sorId" is not null or undefined
      if (item.sorId !== null && item.sorId !== undefined && item?.sorId === "45") {
          // Update the "sorId" with the desired sequence
          item.sorId = (index + 1).toString();
      }
      });
      
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
  const lastApprovedMeasurementObject = transformMeasureObject(allMeasurements?.filter?.((e) => e?.isActive && e?.wfStatus === "APPROVED")?.find((ob) => ob?.auditDetails?.lastModifiedTime < measurement?.auditDetails?.lastModifiedTime)) || {};
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
      additionalDetails: {...measuredObject?.additionalDetails, measureLineItems : measuredObject?.additionalDetails?.measureLineItems?.length > 0 && !(window.location.href.includes("measurement/create")) ? measuredObject?.additionalDetails?.measureLineItems : [{number:0,width:0,length:0,height:0, quantity:0,measureSummary:"", measurelineitemNo:0}]},
      consumedRowQuantity: window.location.href.includes("/measurement/update") || (window.location.href.includes("/measurement/view"))? lastApprovedMeasurementObject?.lineItemsObject?.[transformedContract?.lineItemsObject?.[estimate?.id]?.contractLineItemId]?.cumulativeValue  : transformMeasurementData?.lineItemsObject?.[transformedContract?.lineItemsObject?.[estimate?.id]?.contractLineItemId]?.cumulativeValue || 0,
    })
  });
    return {
      amount:  window.location.href.includes("measurement/create") ? 0 : (measures?.reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.rowAmount : acc + curr?.rowAmount, 0) || 0),
      consumedQ: (measurement?.wfStatus === "APPROVED" || (allMeasurements.length > 0 && allMeasurements?.filter((ob) => ob?.wfStatus === "APPROVED")?.length > 0)) ? measures?.reduce((acc, curr) => curr.isDeduction == true ? acc - (curr?.consumedRowQuantity ? curr?.consumedRowQuantity : 0) : acc + (curr?.consumedRowQuantity ? curr?.consumedRowQuantity : 0), 0) : 0,
      sNo: index + 1,
      currentMBEntry: window.location.href.includes("measurement/create") ? 0 : ( measures?.reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.noOfunit : acc + curr?.noOfunit, 0) || 0),
      uom: transformedEstimateObject[key]?.[0]?.uom,
      description: transformedEstimateObject[key]?.[0]?.name,
      unitRate: transformedEstimateObject[key]?.[0]?.unitRate,
      contractNumber: transformedContract?.contractNumber,
      targetId: transformedContract?.lineItemsObject[transformedEstimateObject[key][0].id]?.contractLineItemId,
      approvedQuantity: transformedEstimateObject[key].reduce((acc, curr) => curr.isDeduction == true ? acc - curr?.noOfunit : acc + curr.noOfunit, 0),
      showMeasure: showM,
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

export const getDefaultValues = (data, t, mbNumber) => {
  const { contract, estimate, allMeasurements, measurement, musterRollNumber, period } = data;

  const SOR = transformEstimateData(estimate?.estimateDetails, contract, "SOR", allMeasurements?.length > 0 ? allMeasurements?.filter((ob) => ob?.measurementNumber === mbNumber)?.[0] : measurement, allMeasurements);
  const NONSOR = transformEstimateData(estimate?.estimateDetails, contract, "NON-SOR", allMeasurements?.length > 0 ? allMeasurements?.filter((ob) => ob?.measurementNumber === mbNumber)?.[0] : measurement, allMeasurements);

  // extract details from contract
  const {
    contractNumber,
    supplementNumber,
    issueDate,
    additionalDetails: { projectId: projectID, projectName: projectName, projectDesc: projectDesc, locality: projectLoc, ward: projectWard },
  } = contract;
  const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId());
  const Pward = projectWard ? t(`${headerLocale}_ADMIN_${projectWard}`) : "";
  // const city = projectLoc ? t(`${Digit.Utils.locale.getTransformedLocale(projectLoc)}`) : "";
  const city = projectLoc ? t(`${headerLocale}_ADMIN_${projectLoc}`) : "";
  const entryDate = data?.allMeasurements?.length > 0 ? data?.allMeasurements?.filter(ob => ob?.measurementNumber === mbNumber)?.[0]?.entryDate : null;

  const projectLocation = `${Pward ? Pward + ", " : ""}${city}`;
  let CurrentStartDate = period?.startDate;
  let CurrentEndDate = period?.endDate;
  let measurementPeriod = `${Digit.DateUtils.ConvertEpochToDate(period?.startDate)} - ${Digit.DateUtils.ConvertEpochToDate(period?.endDate)}`;
  if(window?.location.href.includes("measurement/update") && data?.allMeasurements  && data?.allMeasurements?.length > 0 && data?.allMeasurements?.code !== "NO_MEASUREMENT_ROLL_FOUND" && data?.allMeasurements?.filter(ob => ob?.measurementNumber === mbNumber)?.length > 0)
  {
     measurementPeriod = `${Digit.DateUtils.ConvertEpochToDate(data?.allMeasurements?.filter(ob => ob?.measurementNumber === mbNumber)?.[0]?.additionalDetails?.startDate)} - ${Digit.DateUtils.ConvertEpochToDate(data?.allMeasurements?.filter(ob => ob?.measurementNumber === mbNumber)?.[0]?.additionalDetails?.endDate)}`;
     CurrentStartDate = data?.allMeasurements?.filter(ob => ob?.measurementNumber === mbNumber)?.[0]?.additionalDetails?.startDate;
     CurrentEndDate = data?.allMeasurements?.filter(ob => ob?.measurementNumber === mbNumber)?.[0]?.additionalDetails?.endDate;
  }
  //const musterRoll = typeof musterRollNumber == "string" ? musterRollNumber : (allMeasurements?.filter((ob) => ob?.measurementNumber === mbNumber)?.[0]?.additionalDetails?.musterRollNumber?.[0] || "NA")
  const musterRoll = findMusterRollNumber(data?.musterRolls,'',data?.allMeasurements?.length > 0  ? data?.allMeasurements?.filter(ob => ob?.measurementNumber === mbNumber)?.[0]?.additionalDetails?.startDate : period?.startDate,data?.allMeasurements?.length > 0  ? data?.allMeasurements?.filter(ob => ob?.measurementNumber === mbNumber)?.[0]?.additionalDetails?.endDate :  period?.endDate)

  let uploadedDocs = {}
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
   uploadedDocs = window.location.href.includes("measurement/create") ?  {} : uploadedDocs;
  const contractDetails = {
    contractNumber,
    supplementNumber,
    projectID,
    projectName,
    projectDesc,
    projectLocation,
    sanctionDate:issueDate ?  Digit.DateUtils.ConvertEpochToDate(issueDate) : Digit.DateUtils.ConvertEpochToDate(estimate?.proposalDate),
    musterRollNo: musterRoll ? <Link style={{ color: "#C84C0E" }} to={`/works-ui/employee/attendencemgmt/view-attendance?tenantId=${data?.allMeasurements?.[0]?.tenantId || Digit.ULBService.getCurrentTenantId()}&musterRollNumber=${musterRoll}`}>{musterRoll}</Link> : "NA",
    measurementPeriod: measurementPeriod,
    CurrentStartDate,
    CurrentEndDate,
    mbNumber,
    entryDate
  };

  return { SOR, NONSOR, contractDetails, uploadedDocs, documents:measurement?.documents || allMeasurements?.[0]?.documents };
};

export function findMusterRollNumber(musterRolls, measurementNumber, startDate, endDate) {
  if(musterRolls && musterRolls?.length > 0)
  for (const musterRoll of musterRolls) {
    if (
      musterRoll.startDate >= startDate &&
      musterRoll.endDate <= endDate
    ) {
      // Match found, now find corresponding muster roll number
      return musterRoll?.musterRollNumber;
    }
  }
  // If no match found
  return null;
}

