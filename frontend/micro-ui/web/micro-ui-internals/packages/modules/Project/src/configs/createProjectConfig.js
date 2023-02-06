import { CitizenInfoLabel } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";

export const createProjectSectionConfig = (subTypeOfProjectOptions, subSchemaOptions, wardsAndLocalities, filteredLocalities, showInfoLabel=false) => {
  const { t } = useTranslation()

  const tenantId = Digit.ULBService.getCurrentTenantId();
  const ULB = Digit.Utils.locale.getCityLocale(tenantId);
  
  let ULBOptions = []
  ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })

  return {
    defaultValues : {
      basicDetails_dateOfProposal : "01-01-2020",
      basicDetails_hasSubProjects : {name : "COMMON_YES", code : "COMMON_YES"},
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
            key: "basicDetails_dateOfProposal",
            type: "date",
            disable: true,
            populators: { name: "basicDetails_dateOfProposal" },
          },
          {
            inline: true,
            label: "PDF_STATIC_LABEL_ESTIMATE_PROJECT_NAME",
            isMandatory: true,
            key: "basicDetails_projectName",
            type: "text",
            disable: false,
            populators: { name: "basicDetails_projectName", error: t("PROJECT_PATTERN_ERR_MSG_PROJECT_NAME"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }}
          },
          {
            inline: true,
            label: "PROJECT_DESC",
            isMandatory: false,
            key: "basicDetails_projectDesc",
            type: "text",
            disable: false,
            populators: { name: "basicDetails_projectDesc", error: t("PROJECT_PATTERN_ERR_MSG_PROJECT_DESC"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }}
          },
          {
            isMandatory: false,
            key: "basicDetails_hasSubProjects",
            type: "radio",
            label: "WORKS_HAS_SUB_PROJECT_LABEL",
            disable: false,
            populators: {
              name: "basicDetails_hasSubProjects",
              optionsKey: "name",
              error: "Required",
              required: false,
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
        sectionFormCategory : "noSubProject",
        head: t(""),
        body: [
          {
            isMandatory: true,
            key: "noSubProject_owningDepartment",
            type: "radioordropdown",
            label: "PROJECT_OWNING_DEPT",
            disable: false,
            populators: {
              name: "noSubProject_owningDepartment",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "Department",
                moduleName: "common-masters",
                localePrefix: "COMMON_MASTERS_DEPARTMENT",
              },
            },
          },
          {
            isMandatory: false,
            key: "noSubProject_targetDemocracy",
            type: "radioordropdown",
            label: t("PROJECT_TARGET_DEMOGRAPHY"),
            disable: false,
            populators: {
              name: "noSubProject_targetDemocracy",
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
            label: t("WORKS_LOR"),
            isMandatory: false,
            key: "noSubProject_letterRefNoOrReqNo",
            type: "text",
            disable: false,
            populators: { name: "noSubProject_letterRefNoOrReqNo", error: t("PROJECT_PATTERN_ERR_MSG_PROJECT_LOR"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }}
          },
          {
            inline: true,
            label: t("PROJECT_ESTIMATED_COST_IN_RS"),
            isMandatory: false,
            key: "noSubProject_estimatedCostInRs",
            type: "number",
            disable: false,
            populators: { name: "noSubProject_estimatedCostInRs" }
          },
        ]
      },
      {
        navLink:"Project_Details",
        sectionFormCategory : "noSubProject",
        head: t("WORKS_WORK_DETAILS"),
        body: [
          {
            isMandatory: true,
            key: "noSubProject_typeOfProject",
            type: "radioordropdown",
            label: "WORKS_PROJECT_TYPE",
            disable: false,
            populators: {
              name: "noSubProject_typeOfProject",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "ProjectType",
                moduleName: "works",
                localePrefix: "ES_COMMON",
              },
            },
          },
          {
            isMandatory: false,
            key: "noSubProject_subTypeOfProject",
            type: "radioordropdown",
            label: "WORKS_SUB_PROJECT_TYPE",
            disable: false,
            populators: {
              name: "noSubProject_subTypeOfProject",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: false,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              options : subTypeOfProjectOptions //TODO:
            },
          },
          {
            isMandatory: false,
            key: "noSubProject_natureOfWork",
            type: "radioordropdown",
            label: "WORKS_WORK_NATURE",
            disable: false,
            populators: {
              name: "noSubProject_natureOfWork",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: false,
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
            key:"noSubProject_startDate",
            description: "",
            type: "date",
            disable: false,
            populators: { name: "noSubProject_startDate" },
          },
          {
            inline: true,
            label: "PROJECT_PLANNED_END_DATE",
            isMandatory: false,
            key:"noSubProject_endDate",
            description: "",
            type: "date",
            disable: false,
            populators: { name: "noSubProject_endDate" },
          },
          {
            isMandatory: false,
            key: "noSubProject_recommendedModeOfEntrustment",
            type: "radioordropdown",
            label: "PDF_STATIC_LABEL_ESTIMATE_ENTRUSTMENT",
            disable: false,
            populators: {
              name: "noSubProject_recommendedModeOfEntrustment",
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
        sectionFormCategory : "noSubProject",
        head: t("PDF_STATIC_LABEL_ESTIMATE_LOC_DETAILS"),
        body: [
          {
            inline: true,
            label: "WORKS_GEO_LOCATION",
            isMandatory: true,
            key: "noSubProject_geoLocation",
            type: "text",
            disable: false,
            populators: { name: "noSubProject_geoLocation",  error: t("WORKS_REQUIRED_ERR") }
          },
          {
            isMandatory: true,
            key: "noSubProject_ulb",
            type: "radioordropdown",
            label: t("ES_COMMON_ULB"),
            disable: false,
            populators: {
              name: "noSubProject_ulb",
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
            isMandatory: false,
            key: "noSubProject_ward",
            type: "radioordropdown",
            label: "PDF_STATIC_LABEL_ESTIMATE_WARD",
            disable: false,
            populators: {
              name: "noSubProject_ward",
              optionsKey: "i18nKey",
              error: t("WORKS_REQUIRED_ERR"),
              required: false,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              options: wardsAndLocalities?.wards
            },
          },
          {
            isMandatory: false,
            key: "noSubProject_locality",
            type: "radioordropdown",
            label: "WORKS_LOCALITY",
            disable: false,
            populators: {
              name: "noSubProject_locality",
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
        sectionFormCategory : "noSubProject",
        head: t("WORKS_RELEVANT_DOCS"),
        body: [
          {
            type:"multiupload",
            label: t("WORKS_UPLOAD_FILES"),
            populators:{
                name: "noSubProject_uploadedFiles",
                allowedMaxSizeInMB:2,
                maxFilesAllowed:2,
                allowedFileTypes : /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i,
                customClass : "upload-margin-bottom",
                errorMessage : t("WORKS_FILE_UPLOAD_CUSTOM_ERROR_MSG")
            }
          }
        ]
      },
      {
        navLink:"Financial_Details",
        sectionFormCategory : "noSubProject",
        head: t(""),
        body: [
          {
            isMandatory: true,
            key: "noSubProject_fund",
            type: "radioordropdown",
            label: "WORKS_FUND",
            disable: false,
            populators: {
              name: "noSubProject_fund",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "Fund",
                moduleName: "finance",
                localePrefix: "ES_COMMON_FIN",
              },
            },
          },
          {
            isMandatory: true,
            key: "noSubProject_function",
            type: "radioordropdown",
            label: "WORKS_FUNCTION",
            disable: false,
            populators: {
              name: "noSubProject_function",
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
            key: "noSubProject_budgetHead",
            type: "radioordropdown",
            label: "WORKS_BUDGET_HEAD",
            disable: false,
            populators: {
              name: "noSubProject_budgetHead",
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
            key: "noSubProject_scheme",
            type: "radioordropdown",
            label: "WORKS_SCHEME",
            disable: false,
            populators: {
              name: "noSubProject_scheme",
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
            key: "noSubProject_subScheme",
            type: "radioordropdown",
            label: "WORKS_SUB_SCHEME",
            disable: false,
            populators: {
              name: "noSubProject_subScheme",
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
        sectionFormCategory : "withSubProject",
        head: "",
        body: [
          {
            isMandatory: false,
            key: "withSubProject_project_owningDepartment",
            type: "radioordropdown",
            label: "PROJECT_OWNING_DEPT",
            disable: false,
            populators: {
              name: "withSubProject_project_owningDepartment",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: false,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "Department",
                moduleName: "common-masters",
                localePrefix: "COMMON_MASTERS_DEPARTMENT",
              },
            },
          },
          {
            isMandatory: false,
            key: "withSubProject_project_executingDepartment",
            type: "radioordropdown",
            label: "WORKS_EXECUTING_DEPT",
            disable: false,
            populators: {
              name: "withSubProject_project_executingDepartment",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: false,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "Department",
                moduleName: "common-masters",
                localePrefix: "COMMON_MASTERS_DEPARTMENT",
              },
            },
          },
          {
            isMandatory: false,
            key: "withSubProject_project_beneficiary",
            type: "radioordropdown",
            label: "WORKS_BENEFICIARY",
            disable: false,
            populators: {
              name: "withSubProject_project_beneficiary",
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
            key: "withSubProject_project_LetterRefNoOrReqNo",
            type: "text",
            disable: false,
            populators: { name: "withSubProject_project_letterRefNoOrReqNo", error: t("PROJECT_PATTERN_ERR_MSG_PROJECT_LOR"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }}
          },
          {
            inline: true,
            label: "PROJECT_TOTAL_ESTIMATED_COST_IN_RS",
            isMandatory: false,
            key: "withSubProject_project_estimatedCostInRs",
            type: "number",
            disable: false,
            populators: { name: "withSubProject_project_estimatedCostInRs" }
          },
        ]
      },
      {
        navLink:"Financial_Details_In_Sub_Project",
        sectionFormCategory : "withSubProject",
        head: t(""),
        body: [
          {
            isMandatory: true,
            key: "withSubProject_project_fund",
            type: "radioordropdown",
            label: "WORKS_FUND",
            disable: false,
            populators: {
              name: "withSubProject_project_fund",
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
            key: "withSubProject_project_function",
            type: "radioordropdown",
            label: "WORKS_FUNCTION",
            disable: false,
            populators: {
              name: "withSubProject_project_function",
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
            key: "withSubProject_project_budgetHead",
            type: "radioordropdown",
            label: "WORKS_BUDGET_HEAD",
            disable: false,
            populators: {
              name: "withSubProject_project_budgetHead",
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
            key: "withSubProject_project_scheme",
            type: "radioordropdown",
            label: "WORKS_SCHEME",
            disable: false,
            populators: {
              name: "withSubProject_project_scheme",
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
            key: "withSubProject_project_subScheme",
            type: "radioordropdown",
            label: "WORKS_SUB_SCHEME",
            disable: false,
            populators: {
              name: "withSubProject_project_subScheme",
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
        navLink:"Sub_Project_Details_In_Sub_Project",
        sectionFormCategory : "withSubProject",
        head: t(""),
        body: [
          {
            key: "withSubProject_subProjects_DetailsComponent",
            type: "component",
            component: "SubProjectDetailsTable",
            withoutLabel: true,
          },
        ]
      },
    ]
    
  };
};
