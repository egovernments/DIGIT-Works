import { UploadFile } from "@egovernments/digit-ui-react-components";
import React from "react";

const ConfigWageSeekerRegistrationForm =  ({selectFile, uploadedFile, setUploadedFile, error}) => {
  return {
    form: [
      {
        head: "MASTERS_WAGE_SEEKER_DETAILS",
        subHead: "",
        body: [
          {
            inline: true,
            label: "MASTERS_AADHAR_NUMBER",
            isMandatory: false,
            key: "AadharNumber",
            type: "text",
            disable: false,
            populators: { name: "AadharNumber", error: "Required", validation: { pattern: /^[A-Za-z]+$/i } },
          },
          {
            inline: true,
            label: "MASTERS_NAME_OF_WAGE_SEEKER",
            isMandatory: false,
            key: "NameOfWageSeeker",
            type: "text",
            disable: false,
            populators: { name: "NameOfWageSeeker", error: "Required", validation: { pattern: /^[A-Za-z]+$/i } },
          },
          {
            inline: true,
            label: "MASTERS_DOB",
            isMandatory: true,
            description: "",
            type: "date",
            disable: false,
            populators: { name: "dob", error: "Required", validation: { required: true } },
          },
          {
            isMandatory: true,
            key: "genders",
            type: "radioordropdown",
            label: "MASTERS_GENDER",
            disable: false,
            populators: {
              name: "genders",
              optionsKey: "name",
              error: "",
              required: true,
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
          {
            isMandatory: true,
            key: "genders",
            type: "radioordropdown",
            label: "MASTERS_SOCIAL_CATEGORY",
            disable: false,
            populators: {
              name: "genders",
              optionsKey: "name",
              error: "",
              required: true,
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
          {   
            label: ("PHOTOGRAPH"),
            populators: (
              <UploadFile
                id={"upload_photo"}
                onUpload={selectFile}
                onDelete={() => {
                    setUploadedFile(null);
                }}
                showHint={true}
                message={uploadedFile ? `1 ${(`CS_ACTION_FILEUPLOADED`)}` : (`CS_ACTION_NO_FILEUPLOADED`)}
                accept= "image/*, .pdf, .png, .jpeg, .jpg"
                iserror={error}
              />
            ),
          },
          {
            inline: true,
            label: "MASTERS_MOBILE_NUMBER",
            isMandatory: false,
            key: "MobileNumber",
            type: "mobileNumber",
            disable: false,
            populators: { name: "MobileNumber", error: "Required", validation: { pattern: /^[A-Za-z]+$/i } },
          },
          {
            isMandatory: true,
            key: "genders",
            type: "radioordropdown",
            label: "MASTERS_MOBILE_NO_VAL_STATUS",
            disable: false,
            populators: {
              name: "genders",
              optionsKey: "name",
              error: "",
              required: true,
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
          {
            isMandatory: true,
            key: "genders",
            type: "radioordropdown",
            label: "MASTERS_WAGE_SEEKER_SKILLS",
            disable: false,
            populators: {
              name: "genders",
              optionsKey: "name",
              error: "",
              required: true,
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
          {
            isMandatory: true,
            key: "genders",
            type: "radioordropdown",
            label: "MASTERS_ENGAGEMENT_STATUS",
            disable: false,
            populators: {
              name: "genders",
              optionsKey: "name",
              error: "",
              required: true,
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
        ],
      },
      {
        head: "MASTERS_DISABILITY_DETAILS_IF_ANY",
        subHead: "",
        body: [
          {
            isMandatory: true,
            key: "genders",
            type: "radio",
            label: "MASTERS_DOES_WAGE_SEEKER_HAS_DISABILITY",
            disable: false,
            populators: {
              name: "genders",
              optionsKey: "name",
              error: "",
              required: true,
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
          {
            inline: true,
            label: "MASTERS_UDID_NUMBER",
            isMandatory: false,
            key: "AadharNumber",
            type: "number",
            disable: false,
            populators: { name: "AadharNumber", error: "Required", validation: { pattern: /^[A-Za-z]+$/i } },
          },
          {
            isMandatory: true,
            key: "genders",
            type: "radioordropdown",
            label: "MASTERS_UDID_VALIDATION_STATUS",
            disable: false,
            populators: {
              name: "genders",
              optionsKey: "name",
              error: "",
              required: true,
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
        ],
      },
      {
        head: "MASTERS_LOCATION_DETAILS",
        subHead: "",
        body: [
          {
            inline: true,
            label: "MASTERS_ADDRESS",
            isMandatory: false,
            key: "AadharNumber",
            type: "text",
            disable: false,
            populators: { name: "AadharNumber", error: "Required", validation: { pattern: /^[A-Za-z]+$/i } },
          },
          {
            isMandatory: true,
            key: "genders",
            type: "radioordropdown",
            label: "MASTERS_WARD",
            disable: false,
            populators: {
              name: "genders",
              optionsKey: "name",
              error: "",
              required: true,
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
          {
            isMandatory: true,
            key: "genders",
            type: "radioordropdown",
            label: "MASTERS_ULB",
            disable: false,
            populators: {
              name: "genders",
              optionsKey: "name",
              error: "",
              required: true,
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
          {
            isMandatory: true,
            key: "genders",
            type: "radioordropdown",
            label: "MASTERS_DISTRICT",
            disable: false,
            populators: {
              name: "genders",
              optionsKey: "name",
              error: "",
              required: true,
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
          {
            isMandatory: true,
            key: "genders",
            type: "radioordropdown",
            label: "MASTERS_COMMUNITY_ORG_ID",
            disable: false,
            populators: {
              name: "genders",
              optionsKey: "name",
              error: "",
              required: true,
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
        ],
      },
      {
        head: "MASTERS_FINANCIAL_DETAILS",
        subHead: "",
        body: [
          {
            inline: true,
            label: "MASTERS_BANK_ACC_HOLDER_NAME",
            isMandatory: false,
            key: "AadharNumber",
            type: "text",
            disable: false,
            populators: { name: "AadharNumber", error: "Required", validation: { pattern: /^[A-Za-z]+$/i } },
          },
          {
            isMandatory: true,
            key: "genders",
            type: "radioordropdown",
            label: "MASTERS_BANK_ACCOUNT_TYPE",
            disable: false,
            populators: {
              name: "genders",
              optionsKey: "name",
              error: "",
              required: true,
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
          {
            inline: true,
            label: "MASTERS_ACC_NO",
            isMandatory: false,
            key: "AadharNumber",
            type: "text",
            disable: false,
            populators: { name: "AadharNumber", error: "Required", validation: { pattern: /^[A-Za-z]+$/i } },
          },
          {
            isMandatory: true,
            key: "genders",
            type: "radioordropdown",
            label: "MASTERS_BANK_NAME",
            disable: false,
            populators: {
              name: "genders",
              optionsKey: "name",
              error: "",
              required: true,
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
          {
            inline: true,
            label: "MASTERS_BANK_BRANCH",
            isMandatory: false,
            key: "AadharNumber",
            type: "text",
            disable: false,
            populators: { name: "AadharNumber", error: "Required", validation: { pattern: /^[A-Za-z]+$/i } },
          },
          {
            inline: true,
            label: "MASTERS_IFSC",
            isMandatory: false,
            key: "AadharNumber",
            type: "text",
            disable: false,
            populators: { name: "AadharNumber", error: "Required", validation: { pattern: /^[A-Za-z]+$/i } },
          },
        ],
      },
    ],
  };
};

export default ConfigWageSeekerRegistrationForm;
