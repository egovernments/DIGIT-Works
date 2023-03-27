export const CreateWageSeekerConfig = {
  "tenantId" : "pg",
  "moduleName" : "commonUiConfig",
  "CreateWageSeekerConfig" : [
    {
      "defaultValues" : {
        basicDetails_wageSeekerId: "123",
        basicDetails_aadhar: "9099-1234-1234",
        basicDetails_wageSeekerName: "Shivdayal Kishor",
        basicDetails_fatherHusbandName: "Ramkripal Kishor",
        basicDetails_relationShip: {
          "code": "FATHER",
          "active": true,
          "name": "COMMON_MASTERS_RELATIONSHIP_FATHER"
        },
        basicDetails_dateOfBirth: "2023-03-09",
        basicDetails_gender: {
          "code": "MALE",
          "active": true,
          "name": "COMMON_MASTERS_GENDER_MALE"
        },
        basicDetails_mobileNumber: "9898989898",
        basicDetails_socialCategory: {
          "name": "COMMON_MASTERS_SOCIAL_SC",
          "code": "SC",
          "active": true
        },
        basicDetails_photograph: [[ 
          "photo.jepg",
          {
            "file": {},
            "fileStoreId": {
              "fileStoreId": "859303bc-d889-4775-be36-1f2c23c88301",
              "tenantId": "pg.citya"
            }
          }]],
        skillDetails_skillCategory: {
          "name": "COMMON_MASTERS_SKILL_LEVEL_UNSKILLED",
          "code": "UNSKILLED",
        },
        skillDetails_skill: {
          "name": "COMMON_MASTERS_SKILL_TYPE_MALE_MULIA",
          "code": "MALE_MULIA",
        },
        locDetails_city: {code: "pg.citya", name: "TENANT_TENANTS_PG_CITYA",  i18nKey: "TENANT_TENANTS_PG_CITYA" },
        financeDetails_accountHolderName: 'Asha Devi',
        financeDetails_accountNumber: '10000213400421',
        financeDetails_accountType: {
          "name": "MASTERS_SAVINGS",
          "code": "SAVINGS",
          "active": true
        },
        financeDetails_ifsc: 'SBIN007123',
        financeDetails_branchName: 'Block1, Kormangala, Bangalore'
      },
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
              populators: { name: "basicDetails_wageSeekerId" },
            },
            {
              label: "ES_COMMON_AADHAR",
              isMandatory: false,
              key: "basicDetails_aadhar",
              type: "text",
              disable: true,
              populators: { name: "basicDetails_aadhar" }
            },
            {
              label: "MASTERS_NAME_OF_WAGE_SEEKER",
              isMandatory: true,
              key: "basicDetails_wageSeekerName",
              type: "text",
              disable: false,
              populators: { name: "basicDetails_wageSeekerName", validation: { minlength : 2 }}
            },
            {
              label: "MASTERS_FATHER_HUSBAND_NAME",
              isMandatory: true,
              key: "basicDetails_fatherHusbandName",
              type: "text",
              disable: false,
              populators: { name: "basicDetails_fatherHusbandName", validation: { minlength : 2 }}
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
                error: ("WORKS_REQUIRED_ERR"),
                optionsCustomStyle : {
                  top : "2.3rem"
                },
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
                updateDependent : ["populators.max"]
              },
              populators: { 
                  name: "basicDetails_dateOfBirth",
                  max: "currentDate"
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
                error: ("WORKS_REQUIRED_ERR"),
                optionsCustomStyle : {
                  top : "2.3rem"
                },
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
              type: "number",
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
                error: ("WORKS_REQUIRED_ERR"),
                optionsCustomStyle : {
                  top : "2.3rem"
                },
                mdmsConfig: {
                  masterName: "SocialCategory",
                  moduleName: "common-masters",
                  localePrefix: "COMMON_MASTERS_SOCIAL",
                }
              }
            },
            {
              label: "ES_COMMON_PHOTOGRAPH",
              isMandatory: false,
              key: "basicDetails_photograph",
              type:"multiupload",
              populators:{
                  name: "basicDetails_photograph",
                  allowedMaxSizeInMB:2,
                  maxFilesAllowed:2,
                  allowedFileTypes : /(.*?)(jpeg|jpg|png|pdf|image)$/i,
              }
            }
          ]
        },
        {
          navLink:"Wage_Seeker_Details",
          head: "ATM_SKILLS_DETAILS",
          body: [
            {
              label: "ES_COMMON_SKILL_CATEGORY",
              isMandatory: true,
              key: "skillDetails_skillCategory",
              type: "radioordropdown",
              disable: false,
              preProcess : {
                updateDependent : ["populators.options"]
              },
              populators: {
                name: "skillDetails_skillCategory",
                optionsKey: "name",
                error: ("WORKS_REQUIRED_ERR"),
                optionsCustomStyle : {
                  top : "2.3rem"
                },
                options: []
              }
            },
            {
              label: "ES_COMMON_SKILL",
              isMandatory: true,
              key: "skillDetails_skill",
              type: "radioordropdown",
              disable: false,
              preProcess : {
                updateDependent : ["populators.options"]
              },
              populators: {
                name: "skillDetails_skill",
                optionsKey: "name",
                error: ("WORKS_REQUIRED_ERR"),
                optionsCustomStyle : {
                  top : "2.3rem"
                },
                options: []
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
                optionsCustomStyle : {
                  top : "2.3rem"
                },
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
                error: ("WORKS_REQUIRED_ERR"),
                optionsCustomStyle : {
                  top : "2.3rem"
                },
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
                error: ("WORKS_REQUIRED_ERR"),
                optionsCustomStyle : {
                  top : "2.3rem"
                },
                options: []
              }
            },
            {
              label: "ES_COMMON_STREET_NAME",
              isMandatory: false,
              key: "locDetails_streetName",
              type: "text",
              disable: false,
              populators: { name: "locDetails_streetName", validation: { minlength : 2 }}
            },
            {
              label: "ES_COMMON_DOOR_NO",
              isMandatory: false,
              key: "locDetails_houseName",
              type: "text",
              disable: false,
              populators: { name: "locDetails_houseName", validation: { minlength : 2 }}
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
              populators: { name: "financeDetails_accountHolderName", validation: { minlength : 2 }}
            },
            {
              label: "CORE_COMMON_ACCOUNT_NO",
              isMandatory: true,
              key: "financeDetails_accountNumber",
              type: "number",
              disable: false,
              populators: { name: "financeDetails_accountNumber" }
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
                optionsCustomStyle : {
                  top : "2.3rem"
                },
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
              populators: { name: "financeDetails_ifsc", error: "WORKS_REQUIRED_ERR", validation: {minlength : 2} },
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