import React, { useState, useEffect } from "react";
import { transformEstimateObjects } from "../../util/estimateConversion";
import { transformStatementData, sortSorsBasedonType } from "../../util/EstimateData";
import { sortedFIlteredData } from "../../../Measurement/src/utils/view_utilization";

export const data = (statementDetails, rawData, oldData) => {
  const [viewData, setViewData] = useState({ SOR: [], NONSOR: [], sorted: [] });
  const [sorted, setSorted] = useState([]);

  const headerLocale = Digit.Utils.locale.getTransformedLocale(statementDetails?.tenantId);
  //const geoLocationValue = estimateDetails?.address?.latitude && estimateDetails?.address?.longitude ? `${estimateDetails?.address?.latitude}, ${estimateDetails?.address?.longitude}` : "NA";
  // let data=sortSorsBasedonType(rawData);

  useEffect(() => {
    const processArrays = () => {
      if (statementDetails && !viewData?.nestedData) {
        //Transforming the estimate search response according to formdata
        setViewData({
          nestedData: transformStatementData(statementDetails, "ANALYSIS"),
          sorted: sortSorsBasedonType(rawData, "ANALYSIS"),
          //NONSOR: transformEstimateObjects(estimateDetails, "NON-SOR", {}, allDetailedEstimate),
        });
      }
    };
    processArrays();
  }, [statementDetails, sorted]);

  return {
    cards: [
      {
        sections: [
          {
            type: "DATA",

            values: [
              {
                key: "STATEMENT_MATERIAL",
                value: oldData
                  ? oldData?.Material.includes(",")
                    ? oldData?.Material
                    : parseFloat(oldData?.Material).toFixed(2)
                  : statementDetails
                  ? statementDetails?.basicSorDetails.filter((ob) => ob?.type === "M").length != 0
                    ? Digit.Utils.dss.formatterWithoutRound(
                        statementDetails?.basicSorDetails.filter((ob) => ob?.type === "M")[0]?.amount.toFixed(2),
                        "number",
                        undefined,
                        true,
                        undefined,
                        2
                      )
                    : parseFloat(0).toFixed(2)
                  : parseFloat(0).toFixed(2),
                // amountStyle: { maxWidth: "12%", textAlign: "end", marginLeft:"-15rem" },
                rowContainerStyle: {justifyContent : "revert"}
              },
              {
                key: "STATEMENT_LABOUR",
                value: oldData
                  ? oldData?.Labour?.includes(",")
                    ? oldData?.Labour
                    : parseFloat(oldData?.Labour).toFixed(2)
                  : statementDetails
                  ? statementDetails?.basicSorDetails.filter((ob) => ob?.type === "L").length != 0
                    ? Digit.Utils.dss.formatterWithoutRound(
                        statementDetails?.basicSorDetails.filter((ob) => ob?.type === "L")[0]?.amount.toFixed(2),
                        "number",
                        undefined,
                        true,
                        undefined,
                        2
                      )
                    : parseFloat(0).toFixed(2)
                  : parseFloat(0).toFixed(2),
                // amountStyle: { maxWidth: "12%", textAlign: "end", marginLeft:"-15rem" },
                rowContainerStyle: {justifyContent : "revert"}
              },

              {
                key: "STATEMENT_MACHINERY",
                value: oldData
                  ? oldData?.Machinery?.includes(",")
                    ? oldData?.Machinery
                    : parseFloat(oldData?.Machinery).toFixed(2)
                  : statementDetails
                  ? statementDetails?.basicSorDetails.filter((ob) => ob?.type === "E").length != 0
                    ? Digit.Utils.dss.formatterWithoutRound(
                        statementDetails?.basicSorDetails.filter((ob) => ob?.type === "E")[0]?.amount.toFixed(2),
                        "number",
                        undefined,
                        true,
                        undefined,
                        2
                      )
                    : parseFloat(0).toFixed(2)
                  : parseFloat(0).toFixed(2),
                // amountStyle: { maxWidth: "12%", textAlign: "end", marginLeft:"-15rem" },
                rowContainerStyle: {justifyContent : "revert"}
              },
              {
                key: "STATEMENT_LABOUR_CESS",
                value:oldData
                ? oldData?.LabourCessCost?.includes(",")
                  ? oldData?.LabourCessCost
                  : parseFloat(oldData?.LabourCessCost).toFixed(2)
                : statementDetails?
                 Digit.Utils.dss.formatterWithoutRound(
                  parseFloat(
                    statementDetails?.sorDetails.reduce((acc, ob) => {
                      return acc + (ob?.additionalDetails?.labourCessAmount || 0);
                    }, 0) || 0
                  ).toFixed(2),
                  "number",
                  undefined,
                  true,
                  undefined,
                  2
                ):parseFloat(0).toFixed(2),
                // amountStyle: { maxWidth: "12%", textAlign: "end" },
              },
            ],
          },
        ],
      },
      {
        navigationKey: "card1",
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "WORKS_SORS_WISE_MATERIAL", inlineStyles: {} },
            component: "ViewStatement",
            props: {
              config: {
                key: "SOR",
                mode: "VIEWES",
              },
              arrayProps: {
                fields: viewData?.nestedData,
                type: "M",
              },
              register: () => {},
              setValue: (key, value) => setViewData((old) => ({ ...old, nestedData: value })),
            },
          },
        ],
      },
      {
        navigationKey: "card2",
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "WORKS_SORS_WISE_LABOUR", inlineStyles: {} },
            component: "ViewStatement",
            props: {
              config: {
                key: "SOR",
                mode: "VIEWES",
              },
              arrayProps: {
                fields: viewData?.nestedData,
                type: "L",
              },
              register: () => {},
              setValue: (key, value) => setViewData((old) => ({ ...old, nestedData: value })),
            },
          },
        ],
      },
      {
        navigationKey: "card3",
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "WORKS_SORS_WISE_MACHINERY", inlineStyles: {} },
            component: "ViewStatement",
            props: {
              config: {
                key: "SOR",
                mode: "VIEWES",
              },
              arrayProps: {
                fields: viewData?.nestedData,
                type: "E",
              },
              register: () => {},
              setValue: (key, value) => setViewData((old) => ({ ...old, nestedData: value })),
            },
          },
        ],
      },
      {
        navigationKey: "card1",
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "WORKS_SORS_WISE_MATERIAL_CONSOLIDATION", inlineStyles: {} },
            component: "GroupedTable",

            props: {
              emptyTableMsg: "NO_MATERIAL_CONSOLIDATION",
              config: {
                key: "SOR",
                mode: "VIEWES",
              },
              arrayProps: {
                fields: sortedFIlteredData(viewData?.sorted, "M"),
                type: "M",
              },
              register: () => {},
              setValue: (key, value) => setViewData(),
            },
          },
        ],
      },
      {
        navigationKey: "card2",
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "WORKS_SORS_WISE_LABOUR_CONSOLIDATION", inlineStyles: {} },
            component: "GroupedTable",
            props: {
              emptyTableMsg: "NO_LABOUR_CONSOLIDATION",
              config: {
                key: "SOR",
                mode: "VIEWES",
              },
              arrayProps: {
                fields: sortedFIlteredData(viewData?.sorted, "L"),
                type: "L",
              },
              register: () => {},
              setValue: (key, value) => setViewData(),
            },
          },
        ],
      },
      {
        navigationKey: "card3",
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "WORKS_SORS_WISE_MACHINERY_CONSOLIDATION", inlineStyles: {} },
            component: "GroupedTable",
            props: {
              emptyTableMsg: "NO_MACHINERY_CONSOLIDATION",
              config: {
                key: "SOR",
                mode: "VIEWES",
              },
              arrayProps: {
                fields: sortedFIlteredData(viewData?.sorted, "E"),
                type: "E",
              },
              register: () => {},
              setValue: (key, value) => setViewData(),
            },
          },
        ],
      },
      // {
      //   navigationKey: "card1",
      //   sections: [
      //     {
      //       type: "COMPONENT",
      //       cardHeader: { value: "WORKS_SORS_WISE_LABOUR", inlineStyles: {} },
      //       component: "GroupedTable",
      //       props: {
      //         config: {
      //           key: "SOR",
      //           mode: "VIEWES",
      //         },
      //         arrayProps: {
      //           fields: sorted.filter(item => item.type === 'M'),
      //           type: "M"
      //         },
      //         register: () => {},
      //         setValue: (key, value) => setViewData((old) => ({ ...old, nestedData: value })),
      //       },
      //     }
      //   ],
      // },

      //   {
      //     navigationKey: "card1",
      //     sections: [
      //       {
      //         type: "COMPONENT",
      //         cardHeader: { value: "MB_NONSOR", inlineStyles: {} },
      //         component: "EstimateMeasureTableWrapper",
      //         props: {
      //           config: {
      //             key: "NONSOR",
      //             mode: "VIEWES",
      //           },
      //           arrayProps: {
      //             fields: viewData?.NONSOR,
      //           },
      //           register: () => {},
      //           setValue: (key, value) => setViewData((old) => ({ ...old, NONSOR: value })),
      //         },
      //       }
      //     ],
      //   },
      //   {
      //     navigationKey: "card1",
      //     sections: [
      //       {
      //         type: "COMPONENT",
      //         cardHeader: { value: "ES_OTHER_CHARGES", inlineStyles: {} },
      //         component: "OverheadDetailsTable",
      //         props: {data : overheadDetails}
      //       }
      //     ],
      //   },
      //   {
      //     navigationKey: "card1",
      //     sections: [
      //       {
      //         type: "COMPONENT",
      //         cardHeader: { value: "", inlineStyles: {} },
      //         component: "ViewAnalysisStatement",
      //         props: {formData : {...estimateDetails, SORtable:  estimateDetails ? transformEstimateObjects(estimateDetails, "SOR",{}, allDetailedEstimate) : []}}
      //       },
      //       {
      //         type: "COMPONENT",
      //         cardHeader: { value: "", inlineStyles: {} },
      //         component: "ViewTotalEstAmount",
      //         props: {mode: "VIEWES", detail : {...estimateDetails, value:estimateDetails?.additionalDetails?.totalEstimatedAmount, showTitle:"TOTAL_ESTIMATE_AMOUNT"} }
      //       }
      //     ],
      //   },
      //   {
      //     navigationKey: "card1",
      //     sections: [
      //       {
      //         type: "DOCUMENTS",
      //         documents: [
      //           {
      //             title: "ES_WORKS_RELEVANT_DOCUMENTS",
      //             BS: "Works",
      //             values: documents,
      //           },
      //         ],
      //         inlineStyles: {
      //           marginTop: "1rem",
      //         },
      //       }
      //     ],
      //   },
      //   {
      //     navigationKey: "card1",
      //     sections: [
      //       {
      //         type: "WFHISTORY",
      //         businessService: "ESTIMATE",
      //         applicationNo: revisionNumber ? revisionNumber : estimateDetails?.estimateNumber,
      //         tenantId: estimateDetails?.tenantId,
      //         timelineStatusPrefix: "WF_ESTIMATE_",
      //         breakLineRequired: false,
      //         config : {
      //           select: (data) => {
      //             return {...data, timeline: data?.timeline.filter((ob) => ob?.performedAction !== "DRAFT")}
      //           },
      //         }
      //       },
      //       {
      //         type: "WFACTIONS",
      //         forcedActionPrefix: "WF_ESTIMATE_ACTION",
      //         businessService: "ESTIMATE",
      //         applicationNo: revisionNumber ? revisionNumber : estimateDetails?.estimateNumber,
      //         tenantId: estimateDetails?.tenantId,
      //         applicationDetails: estimateDetails,
      //         url: "/estimate/v1/_update",
      //         moduleCode: "Estimate",
      //         editApplicationNumber: undefined,
      //         editCallback : getRedirectionCallback
      //       },
      //     ],
      //   },
      //   {
      //     navigationKey: "card2",
      //     sections: [
      //       {
      //         type: "DATA",
      //         sectionHeader: { value: "WORKS_PROJECT_DETAILS", inlineStyles: {marginBottom : "16px", marginTop:"32px", fontSize: "24px"} },
      //         values: [
      //           {
      //             key: "PROJECT_LOR",
      //             value: projectDetails?.referenceNumber,
      //           },
      //           {
      //             key: "WORKS_PROJECT_TYPE",
      //             value: projectDetails?.projectType,
      //           },
      //           {
      //             key: "PROJECT_TARGET_DEMOGRAPHY",
      //             value: projectDetails?.targets,
      //           },
      //           {
      //             key: "PROJECT_ESTIMATED_COST",
      //             value: projectDetails?.additionalDetails?.estimatedCostInRs || "NA",
      //           },
      //         ],
      //       },
      //       {
      //         type: "DATA",
      //         sectionHeader: { value: "WORKS_LOCATION_DETAILS", inlineStyles: {marginBottom : "16px", marginTop:"32px", fontSize: "24px"} },
      //         values: [
      //           {
      //             key: "WORKS_GEO_LOCATION",
      //             value: geoLocationValue,
      //           },
      //           {
      //             key: "WORKS_CITY",
      //             value: projectDetails?.address?.city,
      //           },
      //           {
      //             key: "WORKS_WARD",
      //             value: `${headerLocale}_ADMIN_${projectDetails?.address?.boundary}`,
      //           },
      //           {
      //             key: "WORKS_LOCALITY",
      //             value: `${headerLocale}_ADMIN_${projectDetails?.additionalDetails?.locality}`,
      //           },
      //         ],
      //       },
      //       {
      //         type: "DOCUMENTS",
      //         documents: [
      //           {
      //             title: "ES_PROJECT_WORKS_RELEVANT_DOCUMENTS",
      //             BS: "Works",
      //             values: Projectdocuments,
      //           },
      //         ],
      //         inlineStyles: {
      //           marginTop: "1rem",
      //         },
      //         headerStyle: {
      //           marginTop: "32px",
      //           marginBottom: "8px"
      //         }
      //       },
      //     ],
      //   },
    ],
    apiResponse: {},
    additionalDetails: {},
    horizontalNav: {
      showNav: true,
      configNavItems: [
        {
          name: "card1",
          active: true,
          code: "Material",
        },
        {
          name: "card2",
          active: true,
          code: "Labour",
        },
        {
          name: "card3",
          active: true,
          code: "Machinery",
        },
      ],
      activeByDefault: "card1",
    },
  };
};