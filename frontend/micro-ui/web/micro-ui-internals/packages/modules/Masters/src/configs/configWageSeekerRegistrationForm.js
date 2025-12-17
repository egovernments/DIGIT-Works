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
                  return data?.TenantBoundary[0]?.boundary.map((item) => ({ code: item.code, name: t(`${headerLocale}_ADMIN_${item?.code}`), i18nKey: `${headerLocale}_ADMIN_${item?.code}` }));
              },
          })

  const {data: wardOptions } = Digit.Hooks.useLocation(
      tenantId, 'Ward', 
      {
          select: (data) => {
              return data?.TenantBoundary[0]?.boundary.sort((a, b) => a.code.localeCompare(b.code)).map((item) => ({ code: item.code, name: t(`${headerLocale}_ADMIN_${item?.code}`), i18nKey: `${headerLocale}_ADMIN_${item?.code}` }));
          },
      })

  let ULBOptions = []
  ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })

  let districtOptions = []
  districtOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })


  return {
    defaultValues : {
      AadharNumber : "",
      Name : "",
      FatherGuardianName : "",
      dob : "",
      genders : "",
      SocialCategory : "",
      PhoneNumber : "",
      Skills : "",
      SubSkills : "",
      LinkToOrganisations : "",
      Address : "",
      ward : "",
      ulb : "",
      district : "",
      AccountHolderName : "",
      BankAccType : "",
      AccountNumber : "",
      Bank : "",
      Branch : "",
      IFSC : ""
    },
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
            populators: { name: "AadharNumber", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[0-9]{12}$/i, maxlength : 12}},
          },
          {
            inline: true,
            label: "MASTERS_NAME",
            isMandatory: true,
            key: "Name",
            type: "text",
            disable: false,
            populators: { name: "Name", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, maxlength : 140 }}
          },
          {
            inline: true,
            label: "MASTERS_FATHER_NAME",
            isMandatory: false,
            key: "FatherGuardianName",
            type: "text",
            disable: false,
            populators: { name: "FatherGuardianName" }
          },
          {
            inline: true,
            label: "MASTERS_DOB",
            isMandatory: false,
            key:"dob",
            description: "",
            type: "date",
            disable: false,
            populators: { name: "dob" },
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
              // optionsCustomStyle : {
              //   top : "2.5rem"
              // },
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
              // optionsCustomStyle : {
              //   top : "2.5rem"
              // },
              mdmsConfig: {
                masterName: "SocialCategory",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            type:"multiupload",
            label: t("PHOTOGRAPH"),
            populators:{
                name: "photograph",
                allowedMaxSizeInMB:2,
                maxFilesAllowed:2,
                allowedFileTypes : /(.*?)(jpeg|jpg|png|pdf|image)$/i,
                customClass : "upload-margin-bottom"
            }
          },
          {
            inline: true,
            label: "MASTERS_PHONE_NUMBER",
            isMandatory: true,
            key: "PhoneNumber",
            type: "number",
            disable: false,
            populators: { name: "PhoneNumber", error: t("WORKS_REQUIRED_ERR"), validation: { pattern: /^[6789][0-9]{9}$/i } },
          },
          {
            isMandatory: true,
            key: "Skills",
            type: "radioordropdown",
            label: "MASTERS_SKILLS",
            disable: false,
            populators: {
              name: "Skills",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              // optionsCustomStyle : {
              //   top : "2.5rem"
              // },
              mdmsConfig: {
                masterName: "WageSeekerSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            isMandatory: true,
            key: "SubSkills",
            type: "radioordropdown",
            label: "MASTERS_SUB_SKILLS",
            disable: false,
            populators: {
              name: "SubSkills",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              // optionsCustomStyle : {
              //   top : "2.5rem"
              // },
              mdmsConfig: {
                masterName: "WageSeekerSubSkills",
                moduleName: "common-masters",
                localePrefix: "MASTERS",
              },
            },
          },
          {
            isMandatory: true,
            key: "LinkToOrganisations",
            type: "radioordropdown",
            label: "MASTERS_LINK_TO_ORG",
            disable: false,
            populators: {
              name: "LinkToOrganisations",
              optionsKey: "name",
              error: t("WORKS_REQUIRED_ERR"),
              required: true,
              // optionsCustomStyle : {
              //   top : "2.5rem"
              // },
              options : [
                {
                  name : "MASTERS_ORG_1",
                  code : "MASTERS_ORG_1"
                },
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
              // optionsCustomStyle : {
              //   top : "2.5rem"
              // },
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
              // optionsCustomStyle : {
              //   top : "2.5rem"
              // },
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
                // optionsCustomStyle : {
                //   top : "2.5rem"
                // },
                name: "district",
                optionsKey: "i18nKey",
                options: districtOptions
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
              // optionsCustomStyle : {
              //   top : "2.5rem"
              // },
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
            isMandatory: false,
            key: "Bank",
            type: "radioordropdown",
            label: "MASTERS_BANK_NAME",
            disable: false,
            populators: {
              name: "Bank",
              optionsKey: "name",
              // optionsCustomStyle : {
              //   top : "2.5rem"
              // },
              mdmsConfig: {
                masterName: "Bank",
                moduleName: "finance",
                localePrefix: "COMMON",
              },
            },
          },
          {
            inline: true,
            label: "MASTERS_BANK_BRANCH",
            isMandatory: false,
            key: "Branch",
            type: "text",
            disable: false,
            populators: { name: "Branch" },
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