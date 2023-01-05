import React from "react";
import { useTranslation } from "react-i18next";

export const createProjectSectionConfig = (hasSubProjectOptions, handleHasSubProjectOptions) => {
  const { t } = useTranslation();

  return {
    defaultValues : {

    },
    form: [
      { 
        head: "",
        subHead: "",
        body: [
          {
            inline: true,
            label: "PROJECT_DATE_OF_PROPOSAL",
            isMandatory: false,
            key: "dateOfProposal",
            type: "date",
            disable: true,
            populators: { name: "dateOfProposal" },
          },
          {
            inline: true,
            label: "PROJECT_NAME",
            isMandatory: true,
            key: "projectName",
            type: "text",
            disable: false,
            populators: { name: "projectName" }
          },
          {
            inline: true,
            label: "PROJECT_DESC",
            isMandatory: false,
            key: "projectDesc",
            type: "text",
            disable: false,
            populators: { name: "projectDesc" }
          },
          {
            isMandatory: false,
            key: "hasSubProjects",
            type: "goToDefaultCase",
            label: "PROJECT_SUB_PROJECT",
            disable: false,
            populators: <div className="radio-wrap flex-row">
                        {
                            hasSubProjectOptions?.options?.map((option)=>(
                                <div key={option?.key} className="mg-sm">
                                    <span className="radio-btn-wrap">
                                        <input
                                            className="radio-btn"
                                            type="radio"
                                            value={option?.value}
                                            checked={hasSubProjectOptions?.options[0]?.value}
                                            onChange={() => handleHasSubProjectOptions(option)}
                                            name="hasSubProjects"   
                                        />
                                        <span className="radio-btn-checkmark"></span>
                                    </span>
                                    <label>{t(option?.code)}</label>
                            </div>
                            ))
                        }
                    </div>
        }
      ]
      },
      {
        navLink:"Project_Details",
        head: t("PROJECT DETAILS"),
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
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            isMandatory: true,
            key: "targetDemocracy",
            type: "radioordropdown",
            label: "PROJECT_TARGET_DEMOCRACY",
            disable: false,
            populators: {
              name: "targetDemocracy",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            isMandatory: true,
            key: "letterRefNoOrReqNo",
            type: "radioordropdown",
            label: "PROJECT_LETTER_REF_OR_REQ_NO",
            disable: false,
            populators: {
              name: "letterRefNoOrReqNo",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            isMandatory: true,
            key: "estimatedCostInRs",
            type: "radioordropdown",
            label: "PROJECT_ESTIMATED_COST_IN_RS",
            disable: false,
            populators: {
              name: "estimatedCostInRs",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
        ]
      },
      {
        navLink:"Project_Details",
        head: t("WORK DETAILS"),
        body: [
          {
            isMandatory: true,
            key: "typeOfWork",
            type: "radioordropdown",
            label: "PROJECT_TYPE_OF_WORK",
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
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            isMandatory: true,
            key: "subTypeOfWork",
            type: "radioordropdown",
            label: "PROJECT_SUB_TYPE_WORK",
            disable: false,
            populators: {
              name: "subTypeOfWork",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            isMandatory: true,
            key: "natureOfWork",
            type: "radioordropdown",
            label: "PROJECT_NATURE_OF_WORK",
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
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
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
            isMandatory: true,
            key: "recommendedModeOfEntrustment",
            type: "radioordropdown",
            label: "PROJECT_RECOMMENDED_MODE_OF_ENTRUSTMENT",
            disable: false,
            populators: {
              name: "recommendedModeOfEntrustment",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
        ]
      },  
      {
        navLink:"Project_Details",
        head: t("LOCATION DETAILS"),
        body: [
          {
            inline: true,
            label: "MASTERS_GEOLOCATION",
            isMandatory: true,
            key: "Name",
            type: "text",
            disable: false,
            populators: { name: "Name" }
          },
          {
            isMandatory: true,
            key: "ulb",
            type: "radioordropdown",
            label: "PROJECT_ULB",
            disable: false,
            populators: {
              name: "ulb",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            isMandatory: true,
            key: "ward",
            type: "radioordropdown",
            label: "PROJECT_WARD",
            disable: false,
            populators: {
              name: "ward",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            isMandatory: true,
            key: "locality",
            type: "radioordropdown",
            label: "PROJECT_LOCALITY",
            disable: false,
            populators: {
              name: "locality",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
        ]
      },
      {
        navLink:"Project_Details",
        head: t("UPLOAD FILES"),
        body: [
          {
            type:"multiupload",
            label: t("PROJECT_UPLOAD_FILES"),
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
        head: t("FIN_DETAILS"),
        body: [
          {
            isMandatory: true,
            key: "fund",
            type: "radioordropdown",
            label: "PROJECT_FUND",
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
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            isMandatory: true,
            key: "function",
            type: "radioordropdown",
            label: "PROJECT_FUNCTION",
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
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            isMandatory: true,
            key: "budgetHead",
            type: "radioordropdown",
            label: "PROJECT_BUDGET_HEAD",
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
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            isMandatory: true,
            key: "schema",
            type: "radioordropdown",
            label: "PROJECT_SCHEMA",
            disable: false,
            populators: {
              name: "schema",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            isMandatory: true,
            key: "subSchema",
            type: "radioordropdown",
            label: "PROJECT_SUB_SCHEMA",
            disable: false,
            populators: {
              name: "subSchema",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
        ]
      }
    ]
    
  };
};
