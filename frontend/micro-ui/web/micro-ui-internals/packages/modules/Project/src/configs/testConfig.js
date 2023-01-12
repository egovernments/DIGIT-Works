import React from "react";
import { useTranslation } from "react-i18next";

export const testConfig = (subTypeOfWorkOptions, subSchemaOptions, wardsAndLocalities, filteredLocalities) => {
  const { t } = useTranslation()

  const tenantId = Digit.ULBService.getCurrentTenantId();
  const ULB = Digit.Utils.locale.getCityLocale(tenantId);
  
  let ULBOptions = []
  ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })

  return {
    defaultValues : {
      dateOfProposal : "2020-01-20"
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
        sectionFormCategory : "projects",
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
            inline: true,
            label: "WORKS_LOR",
            isMandatory: false,
            key: "letterRefNoOrReqNo",
            type: "text",
            disable: false,
            populators: { name: "letterRefNoOrReqNo", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }}
          },
        ]
      },
      {
        navLink:"Project_Details",
        sectionFormCategory : "projects",
        head: t("WORKS_WORK_DETAILS"),
        body: [
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
        ]
      },
      {
        navLink:"Financial_Details",
        sectionFormCategory : "projects",
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
        navLink:"Project_Details_Sub_Project",
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
        ]
      },
      {
        navLink:"Financial_Details_Sub_Project",
        sectionFormCategory : "subProjects",
        head: t("PDF_STATIC_LABEL_ESTIMATE_FINANCIAL_DETAILS"),
        body: [
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
            isMandatory: true,
            key: "subProjectFund",
            type: "radioordropdown",
            label: "WORKS_FUND",
            disable: false,
            populators: {
              name: "subProjectFund",
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
            key: "subProjectScheme",
            type: "radioordropdown",
            label: "WORKS_SCHEME",
            disable: false,
            populators: {
              name: "subProjectScheme",
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
            key: "subProjectSubScheme",
            type: "radioordropdown",
            label: "WORKS_SUB_SCHEME",
            disable: false,
            populators: {
              name: "subProjectSubScheme",
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
