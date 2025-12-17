import React, { useState, useEffect } from "react";
import { getRefactoredTableRows, grossTotalAmountCalculation, getRefactoreExtraChargesTableRows, formatDate } from "../utils/transformData";

export const viewRateAnalysisdataconfig = (data, rateAnalysis, sorId, t,infoCard) => {
  
  const [viewData, setViewData] = useState(null);
  const categorizeData = () => {
    // Initialize categories with empty objects
    const categories = {
      Labour: [],
      MachineryBasicRate: [],
      Conveyance: [],
      Royality: [],
      LabourCess: [],
      ExplorationMineralfund: [],
      DistrictMineralfund: [],
      AdditionalCharges: [],
      MaterialBasicRate:[],
      //ExtraCharges: [],
    };

    // Prefix mapping to determine categories
    const prefixMapping = {
      LA: "Labour",
      MA: "MaterialBasicRate",
      CA: "Conveyance",
      RA: "Royality",
      LC: "LabourCess",
      EMF: "ExplorationMineralfund",
      DMF: "DistrictMineralfund",
      ADC: "AdditionalCharges",
      MHA: "MachineryBasicRate",
      //
      L: "Labour",
      M: "MaterialBasicRate",
      E: "MachineryBasicRate",
    };

    // Check if data is not null and is an object
    if (data && typeof data === "object") {
      // Iterate over each entry in the data object
      Object.entries(data).forEach(([key, value]) => {
        // Extract the prefix from the key
        const prefix = key.split(".")[0];
        let category = "";

        if (prefix === "L") {
          category = "Labour";
        } else if (prefix === "M") {
          category = "MaterialBasicRate";
        } else if (prefix === "E") {
          category = "MachineryBasicRate";
        } else {
          category = prefixMapping[prefix];
        }

        // If a valid category is found, add the data to that category

        if (category in categories) {
          categories[category] = [...categories[category], ...value];
        } else {
          categories["ExtraCharges"] = value;
        }
      });
    } else {
      // Handle the case where data is null or not an object
      console.error("Data is null or not an object");
    }

    // Set the categorized data
    setViewData(categories);
  };

  useEffect(() => {
    // Call the function to categorize data
    categorizeData();
  }, [data, rateAnalysis]);

 
  return {
    cards: [
      {
        sections: [
          {
            type: "DATA",
            // localization code to be replaced for key
            values: [
              {
                key: "RA_SOR_CODE",
                value: rateAnalysis?.sorCode,
              },
              {
                key: "RA_SOR_TYPE",
                value: rateAnalysis?.sorType ? t(`WORKS_SOR_TYPE_${rateAnalysis?.sorType}`) : "NA",
              },
              {
                key: "RA_SOR_SUBTYPE",
                value: rateAnalysis?.sorSubType ? t(`WORKS_SOR_SUBTYPE_${rateAnalysis?.sorSubType}`) : "NA",
              },
              {
                key: "RA_SOR_VARIENT",
                value: rateAnalysis?.sorVariant ? rateAnalysis?.sorVariant != "NA" ? t(`WORKS_SOR_VARIANT_${rateAnalysis?.sorVariant}`) :t(`WORKS_SOR_VARIANT_NA`):"NA",
              },

              {
                key: "RA_UOM",
                value: rateAnalysis?.uom ? t(`COMMON_MASTERS_UOM_${rateAnalysis?.uom}`) : "NA",
              },
              {
                key: "RA_RATE_DEFINED",
                value: rateAnalysis?.quantity?parseFloat(rateAnalysis?.quantity).toFixed(4):"NA",
              },
              { isTranslate:false,
                key: "RA_DESCRIPTION",
                value: rateAnalysis?.description,
              },
              {
                key: "RA_STATUS",
                value: rateAnalysis?.status,
              },
            ],
          },
        ],
      },

      {
        sections: [
          {
            type: "COMPONENT",
            // cardHeader: { value: "Rate Ana", inlineStyles: {} },
            component: "RateCardWithRightButton",
            props: {
              sections: {

                type: "DATA",

                cardHeader: { value: "RA_RATE_ANALYSIS_LABEL", inlineStyles: {
                marginBottom : "10px", fontSize: "24px",lineHeight:"32px"
                } },
                infoCard:infoCard,
                values: [
                  {
                    key: "RA_DATE",
                    value: rateAnalysis?.effectiveFrom ? formatDate(rateAnalysis?.effectiveFrom) : "NA",
                  },
                  {
                    key: "RA_ANALYSIS_QUANTITY_DEFINED",
                    value: parseFloat(rateAnalysis?.analysisQuantity).toFixed(4),
                  },
                ],
              },
            },
          },
        ],
      },

      {
        sections: [
          {
            type: "COMPONENT",
            // cardHeader: { value: "Rate Ana", inlineStyles: {} },
            component: "SORDetailsTemplate",
            props: {
              emptyTableMsg: "NO_MATERIAL_FOUND",
              pageType: "VIEW",
              config: {
                sorType: "VIEW_MATERIAL",
              },

              arrayData: getRefactoredTableRows(viewData?.MaterialBasicRate, "VIEW_MATERIAL"),
              watch: () => {},
              setValue: () => {},
              register: () => {},
            },
          },
        ],
      },

      // end of material table

      // // labour table

      {
        sections: [
          {
            type: "COMPONENT",

            component: "SORDetailsTemplate",
            props: {
              emptyTableMsg: "NO_LABOUR_FOUND",
              pageType: "VIEW",
              config: {
                sorType: "VIEW_LABOUR",
              },

              arrayData: getRefactoredTableRows(viewData?.Labour, "VIEW_LABOUR"),
              watch: () => {},
              setValue: () => {},
              register: () => {},
            },
          },
        ],
      },

      // // end of labour table

      // // Machinery table

      {
        sections: [
          {
            type: "COMPONENT",

            component: "SORDetailsTemplate",
            props: {
              emptyTableMsg: "NO_MACHINERY_FOUND",
              pageType: "VIEW",
              config: {
                sorType: "VIEW_MACHINERY",
              },
              arrayData: getRefactoredTableRows(viewData?.MachineryBasicRate, "VIEW_MACHINERY"),
              watch: () => {},
              setValue: () => {},
              register: () => {},
            },
          },
        ],
      },

      // // end of Machinery table
      // // Conveyance table

      {
        sections: [
          {
            type: "COMPONENT",
            // cardHeader: { value: "Rate Ana", inlineStyles: {} },
            component: "SORDetailsTemplate",
            props: {
              emptyTableMsg: "NO_CONVEYANCE_FOUND",
              pageType: "VIEW",
              config: {
                sorType: "CONVEYANCE",
              },
              arrayData: getRefactoredTableRows(viewData?.Conveyance, "CONVEYANCE"),
              watch: () => {},
              setValue: () => {},
              register: () => {},
            },
          },
        ],
      },

      // // end of Conveyance table
      // // Royality on Mineral table

      {
        sections: [
          {
            type: "COMPONENT",
            // cardHeader: { value: "Rate Ana", inlineStyles: {} },
            component: "SORDetailsTemplate",
            props: {
              emptyTableMsg: "NO_ROYALITY_FOUND",
              pageType: "VIEW",
              config: {
                sorType: "ROYALITY_ON_MINERAL",
              },
              arrayData: getRefactoredTableRows(viewData?.Royality, "ROYALITY_ON_MINERAL"),
              watch: () => {},
              setValue: () => {},
              register: () => {},
            },
          },
        ],
      },

      // // end of Royality on Mineral table

      // // Environment management fund table

      {
        sections: [
          {
            type: "COMPONENT",
            // cardHeader: { value: "Rate Ana", inlineStyles: {} },
            component: "SORDetailsTemplate",
            props: {
              emptyTableMsg: "NO_EMF_FOUND",
              pageType: "VIEW",
              config: {
                sorType: "ENVIRONMENT_MANAGEMENT_FUND",
              },
              arrayData: getRefactoredTableRows(viewData?.ExplorationMineralfund, "ENVIRONMENT_MANAGEMENT_FUND"),
              watch: () => {},
              setValue: () => {},
              register: () => {},
            },
          },
        ],
      },

      // // end of Environment management fund table

      // // District Mineral Fund table

      {
        sections: [
          {
            type: "COMPONENT",
            // cardHeader: { value: "Rate Ana", inlineStyles: {} },
            component: "SORDetailsTemplate",
            props: {
              emptyTableMsg: "NO_DMF_FOUND",
              pageType: "VIEW",
              config: {
                sorType: "DISTRICT_MINERAL_FUND",
              },
              arrayData: getRefactoredTableRows(viewData?.DistrictMineralfund, "DISTRICT_MINERAL_FUND"),
              watch: () => {},
              setValue: () => {},
              register: () => {},
            },
          },
        ],
      },

      // // end of District Mineral Fund table

      // // Additional Charges table

      {
        sections: [
          {
            type: "COMPONENT",
            // cardHeader: { value: "Rate Ana", inlineStyles: {} },
            component: "SORDetailsTemplate",
            props: {
              emptyTableMsg: "NO_ADDTIONAL_CHARGES_FOUND",
              pageType: "VIEW",
              config: {
                sorType: "ADDITIONAL_CHARGES",
              },
              arrayData: getRefactoredTableRows(viewData?.AdditionalCharges, "ADDITIONAL_CHARGES"),
              watch: () => {},
              setValue: () => {},
              register: () => {},
            },
          },
        ],
      },

      // // end of Additional Charges table

      // // Extra Charges table

      {
        sections: [
          {
            type: "COMPONENT",
            // cardHeader: { value: "Rate Ana", inlineStyles: {} },
            component: "ExtraChargesViewTable",
            props: {
              emptyTableMsg: "NO_EXTRA_CHARGES_FOUND",
              pageType: "VIEW",
              config: {
                sorType: "EXTRA_CHARGES",
              },
              arrayData: getRefactoreExtraChargesTableRows(viewData?.ExtraCharges, "EXTRA_CHARGES"),
            },
          },
        ],
      },

      // // end of Extra Charges table

      // // no heade table

      {
        sections: [
          {
            type: "COMPONENT",

            component: "TableWithOutHead",

            props: {
              qty: rateAnalysis?.analysisQuantity,
              uom: rateAnalysis?.uom,

              arrayData: grossTotalAmountCalculation(viewData),
            },
          },
        ],
      },

      // //

      {
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "", inlineStyles: {} },
            component: "RateAmountGroup",
            props: {
              mode: "VIEWES",
              detail: {
                newValue: grossTotalAmountCalculation(viewData)[2]?.amount / (rateAnalysis?.analysisQuantity ? rateAnalysis?.analysisQuantity : 1.0),
                sorId: sorId,
                uom: rateAnalysis?.uom,
              },
            },
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