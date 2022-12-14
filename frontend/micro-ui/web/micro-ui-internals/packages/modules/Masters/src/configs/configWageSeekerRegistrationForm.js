import { UploadFile } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";

const ConfigWageSeekerRegistrationForm =  ({selectFile, uploadedFile, setUploadedFile, error}) => {
  const { t } = useTranslation()

  const tenantId = Digit.ULBService.getCurrentTenantId();
  const ULB = Digit.Utils.locale.getCityLocale(tenantId);
  const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)

  const {data: localityOptions } = Digit.Hooks.useLocation(
          tenantId, 'Locality', 
          {
              select: (data) => {
                  return data?.TenantBoundary[0]?.boundary.map((item) => ({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}` }));
              },
          })

  const {data: wardOptions } = Digit.Hooks.useLocation(
      tenantId, 'Ward', 
      {
          select: (data) => {
              return data?.TenantBoundary[0]?.boundary.map((item) => ({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}` }));
          },
      })
  
  let ULBOptions = []
  ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })

  let districtOptions = []
  districtOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })
 

  return {
    form: [
      {
        head: "MASTERS_WAGE_SEEKER_DETAILS",
        subHead: "",
        body: [
          {
            inline: true,
            label: "MASTERS_AADHAR_NUMBER",
            isMandatory: true,
            key: "AadharNumber",
            type: "number",
            disable: false,
            populators: { name: "AadharNumber", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[0-9]{12}$/i }},
          },
          {
            inline: true,
            label: "MASTERS_NAME_OF_WAGE_SEEKER",
            isMandatory: true,
            key: "NameOfWageSeeker",
            type: "text",
            disable: false,
            populators: { name: "NameOfWageSeeker", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i } },
          },
          {
            inline: true,
            label: "MASTERS_DOB",
            isMandatory: true,
            description: "",
            type: "date",
            disable: false,
            populators: { name: "dob", error: t("WORKS_REQUIRED_ERR"), validation: { required: true } },
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
              error: t("WORKS_REQUIRED_ERR"),
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "GenderType",
                moduleName: "common-masters",
                localePrefix: "COMMON_GENDER",
              },
            },
          },
          {
            isMandatory: false,
            key: "SocialCategory",
            type: "radioordropdown",
            label: "MASTERS_SOCIAL_CATEGORY",
            disable: false,
            populators: {
              name: "SocialCategory",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "SocialCategory",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
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
                message={uploadedFile ? `1 ${t(`CS_ACTION_FILEUPLOADED`)}` : t(`CS_ACTION_NO_FILEUPLOADED`)}
                accept= "image/*, .pdf, .png, .jpeg, .jpg"
                iserror={error}
                customClass="upload-margin-bottom"
              />
            ),
          },
          {
            inline: true,
            label: "MASTERS_MOBILE_NUMBER",
            isMandatory: true,
            key: "MobileNumber",
            type: "mobileNumber",
            disable: false,
            populators: { name: "MobileNumber", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[6789][0-9]{9}$/i } },
          },
          {
            isMandatory: true,
            key: "MobileValidationStatus",
            type: "radioordropdown",
            label: "MASTERS_MOBILE_NO_VAL_STATUS",
            disable: false,
            populators: {
              name: "MobileValidationStatus",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              options : [
                {
                  name : "Yes",
                  code : "YES"
                },
                {
                  name : "No",
                  code : "NO"
                }
              ]
            },
          },
          {
            isMandatory: true,
            key: "WageSeekerSkills",
            type: "radioordropdown",
            label: "MASTERS_WAGE_SEEKER_SKILLS",
            disable: false,
            populators: {
              name: "WageSeekerSkills",
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
            key: "EngagementStatus",
            type: "radioordropdown",
            label: "MASTERS_ENGAGEMENT_STATUS",
            disable: false,
            populators: {
              name: "EngagementStatus",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              optionsCustomStyle : {
                top : "2.5rem"
              },
              options : [
                {
                  name : "Yes",
                  code : "YES"
                },
                {
                  name : "No",
                  code : "NO"
                }
              ]
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
            key: "Disability",
            type: "radio",
            label: "MASTERS_DOES_WAGE_SEEKER_HAS_DISABILITY",
            disable: false,
            additionalWrapperClass : "radio-mb-flex-column",
            populators: {
              name: "Disability",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              options : [
                {
                  name : "Yes",
                  code : "YES"
                },
                {
                  name : "No",
                  code : "NO"
                },
              ]
            },
          },
          {
            inline: true,
            label: "MASTERS_UDID_NUMBER",
            isMandatory: false,
            key: "UDID",
            type: "number",
            disable: false,
            populators: { name: "UDID", error: "", validation: {} },
          },
          {
            isMandatory: false,
            key: "UDID",
            type: "radioordropdown",
            label: "MASTERS_UDID_VALIDATION_STATUS",
            disable: false,
            populators: {
              name: "UDID",
              optionsKey: "name",
              error: "",
              optionsCustomStyle : {
                top : "2.5rem"
              },
              options : [
                {
                  name : "Yes",
                  code : "YES"
                },
                {
                  name : "No",
                  code : "NO"
                }
              ]
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
            isMandatory: true,
            key: "Address",
            type: "text",
            disable: false,
            populators: { name: "Address", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[^\$\"<>?\\\\~`!@$%^()+={}\[\]*:;“”‘’]{1,500}$/i} },
          },
          {
            isMandatory: true,
            key: "ward",
            type: "radioordropdown",
            label: "MASTERS_WARD",
            disable: false,
            populators: {
              error: "",
              name: "ward",
              optionsKey: "i18nKey",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              options: wardOptions,
              optionsCustomStyle : {
                top : "2.5rem"
              },
            },
          },
          {
            isMandatory: true,
            key: "ulb",
            type: "radioordropdown",
            label: "MASTERS_ULB",
            disable: false,
            populators: {
              error: t("WORKS_REQUIRED_ERR"),
              optionsCustomStyle : {
                top : "2.5rem"
              },
              name: "ulb",
              optionsKey: "i18nKey",
              options: ULBOptions
            },
          },
          {
            isMandatory: true,
            key: "district",
            type: "dropdown",
            label: t("PDF_STATIC_LABEL_ESTIMATE_DISTRICT"),
            disable: false,
            populators: {
                error :t("WORKS_REQUIRED_ERR"),
                optionsCustomStyle : {
                  top : "2.5rem"
                },
                name: "district",
                optionsKey: "i18nKey",
                options: districtOptions
            },
          },
          {
            isMandatory: true,
            key: "orgId",
            type: "radioordropdown",
            label: "MASTERS_COMMUNITY_ORG_ID",
            disable: false,
            populators: {
              name: "orgId",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              optionsCustomStyle : {
                top : "2.5rem"
              },
              options: [
                {
                  code: "ABC",
                  name: "ABC",
                },
                {
                  code: "XYZ",
                  name: "XYZ",
                }
              ],
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
            isMandatory: true,
            key: "AccountHolderName",
            type: "text",
            disable: false,
            populators: { name: "AccountHolderName", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i } },
          },  
          {
            isMandatory: true,
            key: "BankAccType",
            type: "radioordropdown",
            label: "MASTERS_BANK_ACCOUNT_TYPE",
            disable: false,
            populators: {
              name: "BankAccType",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "BankAccType",
                moduleName: "works",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            inline: true,
            label: "MASTERS_ACC_NO",
            isMandatory: true,
            key: "AccountNumber",
            type: "number",
            disable: false,
            populators: { name: "AccountNumber", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^\d{9,18}$/ } },
          },
          {
            isMandatory: true,
            key: "Bank",
            type: "radioordropdown",
            label: "MASTERS_BANK_NAME",
            disable: false,
            populators: {
              name: "Bank",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              optionsCustomStyle : {
                top : "2.5rem"
              },
              mdmsConfig: {
                masterName: "Bank",
                moduleName: "finance",
                localePrefix: "FINANCE",
              },
            },
          },
          {
            inline: true,
            label: "MASTERS_BANK_BRANCH",
            isMandatory: true,
            key: "Branch",
            type: "text",
            disable: false,
            populators: { name: "Branch", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i } },
          },
          {
            inline: true,
            label: "MASTERS_IFSC",
            isMandatory: true,
            key: "IFSC",
            type: "text",
            disable: false,
            populators: { name: "IFSC", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[A-Z]{4}0[A-Z0-9]{6}$/ } },
          },
        ],
      },
    ],
  };
};

export default ConfigWageSeekerRegistrationForm;
