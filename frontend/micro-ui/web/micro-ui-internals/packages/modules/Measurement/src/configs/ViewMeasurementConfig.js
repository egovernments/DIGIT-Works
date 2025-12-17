import React, { useState, useEffect } from "react";
import { transformEstimateData, findMusterRollNumber } from "../utils/transformEstimateData";

export const data = (contract, estimateDetails, measurement, allMeasurements, thumbnails, projectLocation, period, musterRollNumber, musterRolls) => {
  const [viewData, setViewData] = useState({ SOR: [], NONSOR: [] });
  
  useEffect(() => {
    const processArrays = () => {
      if (estimateDetails) {
        setViewData({
          SOR: transformEstimateData(estimateDetails?.estimateDetails, contract, "SOR", measurement, allMeasurements),
          NONSOR: transformEstimateData(estimateDetails?.estimateDetails, contract, "NON-SOR", measurement, allMeasurements),
        });
      }
    };
    processArrays();
  }, [estimateDetails]);

  
  let musterrollNumber = (findMusterRollNumber(musterRolls,measurement?.measurementNumber, measurement?.additionalDetails?.startDate, measurement?.additionalDetails?.endDate)); 

  return {
    cards: [
      {
        sections: [
          {
            type: "DATA",
            values: [
              {
                key: "MB_NUMBER",
                value: measurement?.measurementNumber,
              },
              {
                key: "MB_WORK_ORDER_NUMBER",
                value: contract?.contractNumber,
              },
              {
                key: "MB_PROJECT_ID",
                value: contract?.additionalDetails?.projectId,
              },
              {
                key: "MB_MUSTER_ROLL_ID",
                isLink : musterrollNumber ? true : false,
                to : `/works-ui/employee/attendencemgmt/view-attendance?tenantId=${measurement?.tenantId}&musterRollNumber=${musterrollNumber}`,
                value: (findMusterRollNumber(musterRolls,measurement?.measurementNumber, measurement?.additionalDetails?.startDate, measurement?.additionalDetails?.endDate)) || "NA",
              },
              {
                key: "MB_PROJECT_DATE",
                value: contract?.issueDate ? Digit.DateUtils.ConvertEpochToDate(contract?.issueDate) : Digit.DateUtils.ConvertEpochToDate(estimateDetails?.proposalDate),
              },
              {
                key: "MB_PROJECT_NAME",
                value: contract?.additionalDetails?.projectName,
              },
              {
                key: "MB_PROJECT_DESC",
                value: contract?.additionalDetails?.projectDesc,
              },
              {
                key: "MB_LOCATION",
                value: projectLocation,
              },
              {
                key: "MB_MEASUREMENT_PERIOD",
                value: `${Digit.DateUtils.ConvertEpochToDate(measurement?.additionalDetails?.startDate)} - ${Digit.DateUtils.ConvertEpochToDate(measurement?.additionalDetails?.endDate)}` || "NA",
              },
            ],
          },
        ],
      },
      {
        sections : [
            {
                type : "COMPONENT",
                component : "MeasurementHistory",
                customComponnetWrapperClssName:"mbhistory-wrapper",
                props : {
                    contractNumber : contract?.contractNumber,
                    measurementNumber : measurement?.measurementNumber
                }
            }
        ]
      },
      {
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "MB_SORS", inlineStyles: {} },
            component: "MeasureTable",
            sectionClassName:"table-included-section",
            props: {
              config: {
                key: "SOR",
                mode: "VIEW",
              },
              arrayProps: {
                fields: viewData?.SOR,
              },
              register: () => {},
              setValue: (key, value) => setViewData((old) => ({ ...old, SOR: value })),
            },
          }
        ],
      },
      {
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "MB_NONSOR", inlineStyles: {} },
            component: "MeasureTable",
            props: {
              config: {
                key: "NONSOR",
                mode: "VIEW",
              },
              arrayProps: {
                fields: viewData?.NONSOR,
              },
              register: () => {},
              setValue: (key, value) => setViewData((old) => ({ ...old, NONSOR: value })),
            },
          }
        ],
      },
      {
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "", inlineStyles: {} },
            component: "ViewAnalysisStatement",
            props: {formData : {...estimateDetails,
             Measurement:measurement,
             SORtable:  estimateDetails ? transformEstimateData(estimateDetails?.estimateDetails, contract, "SOR", measurement, allMeasurements) : []}}
          },
          {
            type: "COMPONENT",
            cardHeader: { value: "", inlineStyles: {} },
            component: "ViewTotalEstAmount",
            props: {mode: "VIEWES", detail : {...estimateDetails, value:measurement?.additionalDetails?.totalAmount} }
          }
        ],
        sectionClassName:"viewstatement-viewamount-wrapper"
      },
      {
        sections : [
            {
                type: "IMAGE",
                fullImage: true,
                cardHeader: { value: "MB_WORKSITE_PHOTOS", inlineStyles: {} },
                photo : { 
                    thumbnailsToShow : thumbnails
                }
            }
        ]
      },
      {
        sections: [
          {
            type: "WFHISTORY",
            businessService: "MB",
            applicationNo: measurement?.measurementNumber,
            tenantId: measurement?.tenantId,
            timelineStatusPrefix: "WF_MB_",
            breakLineRequired: false,
            config : {
              select: (data) => {
                return {...data, timeline: data?.timeline.filter((ob) => ob?.performedAction !== "SAVE_AS_DRAFT")}
              },
            }
          },
          {
            type: "WFACTIONS",
            forcedActionPrefix: "WF_MB_ACTION",
            businessService: "MB",
            applicationNo: measurement?.measurementNumber,
            tenantId: measurement?.tenantId,
            applicationDetails: [measurement],
            url: Digit.Utils.Urls.measurement.update,
            moduleCode: "MB",
            editApplicationNumber: undefined,
          },
        ],
      },
    ],
    apiResponse: {},
    additionalDetails: {},
    horizontalNav: {
      showNav: false,
      configNavItems: [],
      activeByDefault: "",
    },
  };
};
