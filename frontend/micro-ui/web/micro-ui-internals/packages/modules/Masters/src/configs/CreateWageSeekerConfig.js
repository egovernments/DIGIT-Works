export const CreateWageSeekerConfig = {
  "tenantId" : "od",
  "moduleName" : "commonUiConfig",
  "CreateWageSeekerConfig" : [
    {
      "defaultValues" : {},
      "metaData" : {
        showNavs : false
      },
      "form" : [
        {
          head: "",
          subHead: "",
          body: [
            {
              label: "MASTERS_WAGESEEKER_ID",
              isMandatory: false,
              key: "basicDetails_wageSeekerId",
              type: "text",
              disable: true,
              preProcess : {
                updateDependent : ["populators.customStyle.display"]
              },
              populators: { 
                name: "basicDetails_wageSeekerId", 
                customStyle : {
                  display : "none"
                },
                customClass : "field-value-no-border"
              }
            },
            {
              label: "ES_COMMON_AADHAR",
              isMandatory: true,
              key: "basicDetails_aadhar",
              type: "text",
              disable: false,
              preProcess : {
                updateDependent : ["populators.customStyle.pointerEvents", "populators.customClass"]
              },
              populators: { 
                name: "basicDetails_aadhar",
                error: "WORKS_REQUIRED_ERR",
                customStyle : {
                  pointerEvents : "auto"
                },
                customClass : "field-value-no-border" 
              }
            },
            {
              label: "MASTERS_NAME_OF_WAGE_SEEKER",
              isMandatory: true,
              key: "basicDetails_wageSeekerName",
              type: "text",
              disable: false,
              preProcess : {
                convertStringToRegEx : ["populators.validation.pattern"]
              },
              populators: { 
                name: "basicDetails_wageSeekerName", 
                error: "MASTERS_PATTERN_ERR_MSG_WS_DETAILS",
                validation: { pattern: "^[a-zA-Z0-9 .\\-_@\\']*$", minlength : 2, maxlength: 128 }
              }
            },
            {
              label: "MASTERS_FATHER_HUSBAND_NAME",
              isMandatory: true,
              key: "basicDetails_fatherHusbandName",
              type: "text",
              disable: false,
              preProcess : {
                convertStringToRegEx : ["populators.validation.pattern"]
              },
              populators: { 
                name: "basicDetails_fatherHusbandName", 
                error: "MASTERS_PATTERN_ERR_MSG_WS_DETAILS",
                validation: { pattern: "^[a-zA-Z0-9 .\\-_@\\']*$", minlength : 2, maxlength: 128 }
              }
            },
            {
              label: "ES_COMMON_RELATIONSHIP",
              isMandatory: true,
              key: "basicDetails_relationShip",
              type: "radioordropdown",
              disable: false,
              populators: {
                name: "basicDetails_relationShip",
                optionsKey: "name",
                error: "WORKS_REQUIRED_ERR",
                // optionsCustomStyle : {
                //   top : "2.3rem"
                // },
                mdmsConfig: {
                  masterName: "Relationship",
                  moduleName: "common-masters",
                  localePrefix: "COMMON_MASTERS_RELATIONSHIP",
                }
              }
            },
            {
              label: "ES_COMMON_BIRTHDATE",
              isMandatory: true,
              key: "basicDetails_dateOfBirth",
              type: "date",
              disable: false,
              preProcess : {
                updateDependent : ["populators.max", "populators.validation.customValidation"]
              },
              populators: { 
                  name: "basicDetails_dateOfBirth",
                  error: "ES_COMMON_AGE_GREATER_THAN_18_ERROR",
                  max: "currentDate",
                  validation : {
                    customValidation : true
                  }
              }
            },
            {
              label: "CORE_COMMON_GENDER",
              isMandatory: true,
              key: "basicDetails_gender",
              type: "radioordropdown",
              disable: false,
              populators: {
                name: "basicDetails_gender",
                optionsKey: "name",
                error: "WORKS_REQUIRED_ERR",
                // optionsCustomStyle : {
                //   top : "2.3rem"
                // },
                mdmsConfig: {
                  masterName: "GenderType",
                  moduleName: "common-masters",
                  localePrefix: "COMMON_MASTERS_GENDER",
                }
              }
            },
            {
              label: "CORE_COMMON_MOBILE_NUMBER",
              isMandatory: true,
              key: "basicDetails_mobileNumber",
              type: "mobileNumber",
              disable: false,
              populators: { 
                name: "basicDetails_mobileNumber", 
                error: "PHONE_VALIDATION", 
                validation: { min: 5999999999, max: 9999999999 }
              }
            },
            {
              label: "COMMON_SOCIAL_CATEGORY",
              isMandatory: false,
              key: "basicDetails_socialCategory",
              type: "radioordropdown",
              disable: false,
              populators: {
                name: "basicDetails_socialCategory",
                optionsKey: "name",
                // optionsCustomStyle : {
                //   top : "2.3rem"
                // },
                mdmsConfig: {
                  masterName: "SocialCategory",
                  moduleName: "common-masters",
                  localePrefix: "COMMON_MASTERS_SOCIAL",
                }
              }
            },
            {
              "label": "ES_COMMON_PHOTOGRAPH",
              "isMandatory": false,
              "key": "basicDetails_photograph",
              "type": "multiupload",
              "preProcess" : {
                "convertStringToRegEx" : ["populators.allowedFileTypes"]
              },
              "populators": {
                "name": "basicDetails_photograph",
                "allowedMaxSizeInMB": 2,
                "maxFilesAllowed": 1,
                "allowedFileTypes": "(.*?)(jpg|jpeg|png)$",
              }
            }
          ]
        },
        {
          navLink:"Wage_Seeker_Details",
          head: "ATM_SKILLS_DETAILS",
          body: [
            {
              label: "ES_COMMON_SKILL",
              isMandatory: true,
              key: "skillDetails_skill",
              type: "multiselectdropdown",
              disable: false,
              preProcess : {
                updateDependent : ["populators.options"]
              },
              populators: {
                name: "skillDetails_skill",
                optionsKey: "name",
                error: "WORKS_REQUIRED_ERR",
                defaultText: 'COMMON_SELECT_SKILL',
                selectedText: "COMMON_SELECTED",
                allowMultiSelect: true,
                options: [],
                isDropdownWithChip: true
              }
            }
          ]
        },
        {
          navLink:"Wage_Seeker_Details",
          head: "ES_COMMON_LOCATION_DETAILS",
          body: [
            {
              label: "CORE_COMMON_CITY",
              isMandatory: true,
              key: "locDetails_city",
              type: "radioordropdown",
              disable: true,
              preProcess : {
                updateDependent : ["populators.options"]
              },
              populators: {
                name: "locDetails_city",
                optionsKey: "i18nKey",
                error: "WORKS_REQUIRED_ERR",
                // optionsCustomStyle : {
                //   top : "2.3rem"
                // },
                options: []
              }
            },
            {
              label: "CORE_COMMON_WARD",
              isMandatory: true,
              key: "locDetails_ward",
              type: "radioordropdown",
              disable: false,
              preProcess : {
                updateDependent : ["populators.options"]
              },
              populators: {
                name: "locDetails_ward",
                optionsKey: "i18nKey",
                error: "WORKS_REQUIRED_ERR",
                // optionsCustomStyle : {
                //   top : "2.3rem"
                // },
                options: []
              }
            },
            {
              label: "COMMON_LOCALITY",
              isMandatory: true,
              key: "locDetails_locality",
              type: "radioordropdown",
              disable: false,
              preProcess : {
                updateDependent : ["populators.options"]
              },
              populators: {
                name: "locDetails_locality",
                optionsKey: "i18nKey",
                error: "WORKS_REQUIRED_ERR",
                // optionsCustomStyle : {
                //   top : "2.3rem"
                // },
                options: []
              }
            },
            {
              label: "ES_COMMON_STREET_NAME",
              isMandatory: false,
              key: "locDetails_streetName",
              type: "text",
              disable: false,
              preProcess : {
                convertStringToRegEx : ["populators.validation.pattern"]
              },
              populators: { 
                name: "locDetails_streetName", 
                error: "MASTERS_PATTERN_ERR_MSG_WS_DETAILS",
                validation: { pattern: "^[a-zA-Z0-9 .,\\/\\-_@#\\']*$", minlength : 2, maxlength: 128 }
              }
            },
            {
              label: "ES_COMMON_DOOR_NO",
              isMandatory: false,
              key: "locDetails_houseName",
              type: "text",
              disable: false,
              preProcess : {
                convertStringToRegEx : ["populators.validation.pattern"]
              },
              populators: { 
                name: "locDetails_houseName", 
                error: "MASTERS_PATTERN_ERR_MSG_WS_DETAILS",
                validation: { pattern: "^[a-zA-Z0-9 .,\\/\\-_@#\\']*$", minlength : 2, maxlength: 8 }
              }
            }
          ]
        },
        {
          navLink:"Wage_Seeker_Details",
          head: "WORKS_FINANCIAL_DETAILS",
          body: [
            {
              label: "ES_COMMON_ACCOUNT_HOLDER_NAME",
              isMandatory: true,
              key: "financeDetails_accountHolderName",
              type: "text",
              disable: false,
              preProcess : {
                convertStringToRegEx : ["populators.validation.pattern"]
              },
              populators: { 
                name: "financeDetails_accountHolderName", 
                error: "WORKS_REQUIRED_ERR",
                validation: { pattern: "^[a-zA-Z0-9 .\\-_@\\']*$", minlength : 2, maxlength: 64 }
              }
            },
            {
              label: "CORE_COMMON_ACCOUNT_NO",
              isMandatory: true,
              key: "financeDetails_accountNumber",
              type: "number",
              disable: false,
              preProcess : {
                convertStringToRegEx : ["populators.validation.pattern"]
              },
              populators: { 
                name: "financeDetails_accountNumber",
                error: "BANK_ACCOUNT_VALIDATION",
                validation: {pattern: "^[0-9]{9,18}$", minlength : 9, maxlength: 18} 
              }
            },
            {
              label: "CORE_COMMON_ACCOUNT_TYPE",
              isMandatory: true,
              key: "financeDetails_accountType",
              type: "radioordropdown",
              populators: {
                name: "financeDetails_accountType",
                optionsKey: "name",
                error: "WORKS_REQUIRED_ERR",
                // optionsCustomStyle : {
                //   top : "2.3rem"
                // },
                mdmsConfig: {
                  masterName: "BankAccType",
                  moduleName: "works",
                  localePrefix: "MASTERS",
                }
              }
            },
            {
              label:"COMMON_IFSC_CODE",
              isMandatory: true,
              key: "financeDetails_ifsc",
              type: "text",
              disable: false,
              preProcess : {
                convertStringToRegEx : ["populators.validation.pattern"]
              },
              populators: { 
                name: "financeDetails_ifsc", 
                error: "ES_COMMON_IFSC_CODE_ERROR", 
                validation: { pattern: "^[A-Z]{4}0[A-Z0-9]{6}$" }
              }
            },
            {
              label: "MASTERS_BRANCH_NAME",
              isMandatory: false,
              key: "financeDetails_branchName",
              type: "text",
              disable: false,
              populators: { name: "financeDetails_branchName", validation: { minlength : 2 }}
            }
          ]
        }
      ]
    }
  ]
}