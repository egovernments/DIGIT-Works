import React, { useState, useEffect } from "react";
import { transformEstimateObjects } from "../../../Estimate/util/estimateConversion";
import { transformStatementData, sortSorsBasedonType } from "../../../Estimate/util/EstimateData";
import { sortedFIlteredData } from "../utils/view_utilization";

export const data = (statementDetails, rawData, oldData) => {
  const [viewData, setViewData] = useState({ SOR: [], NONSOR: [], sorted: [] });
  const [sorted, setSorted] = useState([]);

  const headerLocale = Digit.Utils.locale.getTransformedLocale(statementDetails?.tenantId);

  useEffect(() => {
    const processArrays = () => {
      if (statementDetails && !viewData?.nestedData) {
        //Transforming the estimate search response according to formdata
        setViewData({
          nestedData: transformStatementData(statementDetails, "UTILIZATION"),
          sorted: sortSorsBasedonType(rawData, "UTILIZATION"),
          //NONSOR: transformEstimateObjects(estimateDetails, "NON-SOR", {}, allDetailedEstimate),
        });
      }
    };
    processArrays();
  }, []);

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
                    : parseFloat(oldData?.Material || 0).toFixed(2)
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
                value: oldData
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
                // amountStyle: { maxWidth: "12%", textAlign: "end", marginLeft:"-15rem" },
                rowContainerStyle: {justifyContent : "revert"}
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
            cardHeader: { value: "WORKS_SORS_WISE_MATERIAL_MB", inlineStyles: {} },
            component: "ViewStatement",
            props: {
              config: {
                key: "SOR",
                mode: "VIEWES",
                screenType: "UTILIZATION",
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
            cardHeader: { value: "WORKS_SORS_WISE_LABOUR_MB", inlineStyles: {} },
            component: "ViewStatement",
            props: {
              config: {
                key: "SOR",
                mode: "VIEWES",
                screenType: "UTILIZATION",
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
            cardHeader: { value: "WORKS_SORS_WISE_MACHINERY_MB", inlineStyles: {} },
            component: "ViewStatement",
            props: {
              config: {
                key: "SOR",
                mode: "VIEWES",
                screenType: "UTILIZATION",
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
