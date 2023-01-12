import { CitizenInfoLabel } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";

export const createProjectSectionConfig = (subTypeOfWorkOptions, subSchemaOptions, wardsAndLocalities, filteredLocalities, showInfoLabel=false) => {
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
          {
            isMandatory: false,
            key: "",
            type: "goToDefaultCase",
            label: "",
            disable: false,
            populators : showInfoLabel && <CitizenInfoLabel info={t("WORKS_INFO")} text={t("WORKS_SUB_PROJECT_INFO_MSG")} className="project-banner" fill="#CC7B2F"></CitizenInfoLabel>
          }
      ]
      },
      {
        navLink:"Project_Details",
        sectionFormCategory : "projects",
        head: t(""),
        body: [
          {
            isMandatory: true,
            key: "owningDepartment",
            type: "radioordropdown",
            label: "PROJECT_OWNING_DEPT",
            disable: false,
            populators: {
              name: "owningDepartment",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
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
            label: "PROJECT_ESTIMATED_COST_IN_RS",
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
        sectionFormCategory : "projects",
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
        sectionFormCategory : "projects",
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
        sectionFormCategory : "projects",
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
        sectionFormCategory : "projects",
        head: t(""),
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
      },
      {
        navLink:"Project_Details_In_Sub_Project",
        sectionFormCategory : "subProjects",
        head: "",
        body: [
          {
            isMandatory: false,
            key: "subProjectOwningDepartment",
            type: "radioordropdown",
            label: "PROJECT_OWNING_DEPT",
            disable: false,
            populators: {
              name: "subProjectOwningDepartment",
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
            key: "subProjectExecutingDepartment",
            type: "radioordropdown",
            label: "WORKS_EXECUTING_DEPT",
            disable: false,
            populators: {
              name: "subProjectExecutingDepartment",
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
            key: "subProjectBeneficiary",
            type: "radioordropdown",
            label: "WORKS_BENEFICIARY",
            disable: false,
            populators: {
              name: "subProjectBeneficiary",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: false,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "BeneficiaryType",
                moduleName: "works",
                localePrefix: "ES_COMMON",
              },
            },
          },
          {
            inline: true,
            label: "WORKS_LOR",
            isMandatory: false,
            key: "subProjectLetterRefNoOrReqNo",
            type: "text",
            disable: false,
            populators: { name: "letterRefNoOrReqNo", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }}
          },
          {
            inline: true,
            label: "PROJECT_TOTAL_ESTIMATED_COST_IN_RS",
            isMandatory: false,
            key: "subProjectTotalEstimatedCostInRs",
            type: "number",
            disable: true,
            populators: { name: "totalEstimatedCostInRs" }
          },
        ]
      },
      {
        navLink:"Financial_Details_In_Sub_Project",
        sectionFormCategory : "subProjects",
        head: t(""),
        body: [
          {
            isMandatory: true,
            key: "subProjectsFund",
            type: "radioordropdown",
            label: "WORKS_FUND",
            disable: false,
            populators: {
              name: "subProjectsFund",
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
            key: "subProjectsFunction",
            type: "radioordropdown",
            label: "WORKS_FUNCTION",
            disable: false,
            populators: {
              name: "subProjectsFunction",
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
            key: "subProjectsBudgetHead",
            type: "radioordropdown",
            label: "WORKS_BUDGET_HEAD",
            disable: false,
            populators: {
              name: "subProjectsBudgetHead",
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
            key: "subProjectsScheme",
            type: "radioordropdown",
            label: "WORKS_SCHEME",
            disable: false,
            populators: {
              name: "subProjectsScheme",
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
            key: "subProjectsSubScheme",
            type: "radioordropdown",
            label: "WORKS_SUB_SCHEME",
            disable: false,
            populators: {
              name: "subProjectsSubScheme",
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
      },
    ]
    
  };
};
