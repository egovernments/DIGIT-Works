import React from "react";
import { useTranslation } from "react-i18next";

export const createProjectSectionConfig = (subTypeOfWorkOptions, subSchemaOptions, wardsAndLocalities, filteredLocalities) => {
  const { t } = useTranslation()

  const tenantId = Digit.ULBService.getCurrentTenantId();
  const ULB = Digit.Utils.locale.getCityLocale(tenantId);
  
  let ULBOptions = []
  ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })

  return {
    defaultValues : {
      dateOfProposal : "01-01-2020"
    },
    form: [
      { 
        head: "",
        subHead: "",
        body: [
          {
            inline: true,
            label: "PDF_STATIC_LABEL_ESTIMATE_PROPOSAL_DATE",
            isMandatory: false,
            key: "dateOfProposal",
            type: "date",
            disable: true,
            populators: { name: "dateOfProposal" },
          },
          {
            inline: true,
            label: "PDF_STATIC_LABEL_ESTIMATE_PROJECT_NAME",
            isMandatory: true,
            key: "projectName",
            type: "text",
            disable: false,
            populators: { name: "projectName", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }}
          },
          {
            inline: true,
            label: "PROJECT_DESC",
            isMandatory: false,
            key: "projectDesc",
            type: "text",
            disable: false,
            populators: { name: "projectDesc", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }}
          },
          {
            isMandatory: false,
            key: "hasSubProjects",
            type: "radio",
            label: "WORKS_HAS_SUB_PROJECT_LABEL",
            disable: false,
            populators: {
              name: "hasSubProjects",
              optionsKey: "name",
              error: "Required",
              required: false,
              defaultValue : "YES",
              options: [
                {
                  code: "COMMON_YES",
                  name: "COMMON_YES",
                },
                {
                  code: "COMMON_NO",
                  name: "COMMON_NO",
                }
              ],
            },
          },
      ]
      },
      {
        navLink:"Project_Details",
        head: t("WORKS_PROJECT_DETAILS"),
        body: [
          {
            isMandatory: false,
            key: "owningDepartment",
            type: "radioordropdown",
            label: "PROJECT_OWNING_DEPT",
            disable: false,
            populators: {
              name: "owningDepartment",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: false,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "Department",
                moduleName: "works",
                localePrefix: "ES_COMMON",
              },
            },
          },
          {
            isMandatory: false,
            key: "targetDemocracy",
            type: "radioordropdown",
            label: "PROJECT_TARGET_DEMOGRAPHY",
            disable: false,
            populators: {
              name: "targetDemocracy",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: false,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "SocialCategory",
                moduleName: "common-masters",
                localePrefix: "ES_COMMON",
              },
            },
          },
          {
            inline: true,
            label: "WORKS_LOR",
            isMandatory: false,
            key: "letterRefNoOrReqNo",
            type: "text",
            disable: false,
            populators: { name: "letterRefNoOrReqNo", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }}
          },
          {
            inline: true,
            label: "PROJECT_ESTIMATED_COST",
            isMandatory: false,
            key: "estimatedCostInRs",
            type: "number",
            disable: false,
            populators: { name: "estimatedCostInRs" }
          },
        ]
      },
      {
        navLink:"Project_Details",
        head: t("WORKS_WORK_DETAILS"),
        body: [
          {
            isMandatory: true,
            key: "typeOfWork",
            type: "radioordropdown",
            label: "WORKS_WORK_TYPE",
            disable: false,
            populators: {
              name: "typeOfWork",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "TypeOfWork",
                moduleName: "works",
                localePrefix: "ES_COMMON",
              },
            },
          },
          {
            isMandatory: false,
            key: "subTypeOfWork",
            type: "radioordropdown",
            label: "PDF_STATIC_LABEL_ESTIMATE_SUB_TYPE_OF_WORK",
            disable: false,
            populators: {
              name: "subTypeOfWork",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: false,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              options : subTypeOfWorkOptions
            },
          },
          {
            isMandatory: true,
            key: "natureOfWork",
            type: "radioordropdown",
            label: "WORKS_WORK_NATURE",
            disable: false,
            populators: {
              name: "natureOfWork",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "NatureOfWork",
                moduleName: "works",
                localePrefix: "ES_COMMON",
              },
            },
          },
          {
            inline: true,
            label: "PROJECT_PLANNED_START_DATE",
            isMandatory: false,
            key:"dob",
            description: "",
            type: "date",
            disable: false,
            populators: { name: "dob" },
          },
          {
            inline: true,
            label: "PROJECT_PLANNED_END_DATE",
            isMandatory: false,
            key:"dob",
            description: "",
            type: "date",
            disable: false,
            populators: { name: "dob" },
          },
          {
            isMandatory: false,
            key: "recommendedModeOfEntrustment",
            type: "radioordropdown",
            label: "PDF_STATIC_LABEL_ESTIMATE_ENTRUSTMENT",
            disable: false,
            populators: {
              name: "recommendedModeOfEntrustment",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: false,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "EntrustmentMode",
                moduleName: "works",
                localePrefix: "ES_COMMON",
              },
            },
          },
        ]
      },  
      {
        navLink:"Project_Details",
        head: t("PDF_STATIC_LABEL_ESTIMATE_LOC_DETAILS"),
        body: [
          {
            inline: true,
            label: "WORKS_GEO_LOCATION",
            isMandatory: true,
            key: "geoLocation",
            type: "text",
            disable: false,
            populators: { name: "geoLocation",  error: t("WORKS_REQUIRED_ERR") }
          },
          {
            isMandatory: true,
            key: "ulb",
            type: "radioordropdown",
            label: t("ES_COMMON_ULB"),
            disable: false,
            populators: {
              name: "ulb",
              optionsKey: "i18nKey",
              options: ULBOptions,
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              }
            },
          },
          {
            isMandatory: true,
            key: "ward",
            type: "radioordropdown",
            label: "PDF_STATIC_LABEL_ESTIMATE_WARD",
            disable: false,
            populators: {
              name: "ward",
              optionsKey: "i18nKey",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              options: wardsAndLocalities?.wards
            },
          },
          {
            isMandatory: true,
            key: "locality",
            type: "radioordropdown",
            label: "WORKS_LOCALITY",
            disable: false,
            populators: {
              name: "locality",
              optionsKey: "i18nKey",
              error: t("WORKS_REQUIRED_ERR"),
              required: false,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              options: filteredLocalities
            },
          },
        ]
      },
      {
        navLink:"Project_Details",
        head: t("WORKS_UPLOAD_FILES"),
        body: [
          {
            type:"multiupload",
            label: t("WORKS_UPLOAD_FILES"),
            populators:{
                name: "photograph",
                allowedMaxSizeInMB:2,
                maxFilesAllowed:2,
                allowedFileTypes : /(.*?)(jpeg|jpg|png|pdf|image)$/i,
                customClass : "upload-margin-bottom"
            }
          }
        ]
      },
      {
        navLink:"Financial_Details",
        head: t("PDF_STATIC_LABEL_ESTIMATE_FINANCIAL_DETAILS"),
        body: [
          {
            isMandatory: true,
            key: "fund",
            type: "radioordropdown",
            label: "WORKS_FUND",
            disable: false,
            populators: {
              name: "fund",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "Fund",
                moduleName: "finance",
                localePrefix: "ES_COMMON",
              },
            },
          },
          {
            isMandatory: true,
            key: "function",
            type: "radioordropdown",
            label: "WORKS_FUNCTION",
            disable: false,
            populators: {
              name: "function",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "Functions",
                moduleName: "finance",
                localePrefix: "ES_COMMON",
              },
            },
          },
          {
            isMandatory: true,
            key: "budgetHead",
            type: "radioordropdown",
            label: "WORKS_BUDGET_HEAD",
            disable: false,
            populators: {
              name: "budgetHead",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "BudgetHead",
                moduleName: "finance",
                localePrefix: "ES_COMMON",
              },
            },
          },
          {
            isMandatory: true,
            key: "scheme",
            type: "radioordropdown",
            label: "WORKS_SCHEME",
            disable: false,
            populators: {
              name: "scheme",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "Scheme",
                moduleName: "finance",
                localePrefix: "ES_COMMON",
              },
            },
          },
          {
            isMandatory: true,
            key: "subScheme",
            type: "radioordropdown",
            label: "WORKS_SUB_SCHEME",
            disable: false,
            populators: {
              name: "subScheme",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              options : subSchemaOptions
            },
          },
        ]
      }
    ]
    
  };
};
