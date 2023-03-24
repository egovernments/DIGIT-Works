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
        showNavs : false,
        currentFormCategory : false,
      },
      "form" : [
        {
          head: "",
          subHead: "",
          body: [
            {
              label: "Wage seeker ID",
              isMandatory: false,
              key: "basicDetails_wageSeekerId",
              type: "text",
              disable: true,
              populators: { name: "basicDetails_wageSeekerId" },
            },
            {
              label: "Aadhar",
              isMandatory: false,
              key: "basicDetails_aadhar",
              type: "text",
              disable: true,
              populators: { name: "basicDetails_aadhar" }
            },
            {
              label: "Name of wage seeker",
              isMandatory: true,
              key: "basicDetails_wageSeekerName",
              type: "text",
              disable: false,
              populators: { name: "basicDetails_wageSeekerName", validation: { minlength : 2 }}
            },
            {
              label: "Father's/ Husband's name",
              isMandatory: true,
              key: "basicDetails_fatherHusbandName",
              type: "text",
              disable: false,
              populators: { name: "basicDetails_fatherHusbandName", validation: { minlength : 2 }}
            },
            {
              label: "Relationship",
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
              label: "Date of birth",
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
              label: "Gender",
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
              label: "Mobile number",
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
              label: "Social Category",
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
              label: "Photograph",
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
          head: "Skill Details",
          body: [
            {
              label: "Skill Category",
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
              label: "Skill",
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
          head: "Location Details",
          body: [
            {
              label: "City",
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
              label: "Ward",
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
              label: "Locality",
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
              label: "Street name",
              isMandatory: false,
              key: "locDetails_streetName",
              type: "text",
              disable: false,
              populators: { name: "locDetails_streetName", validation: { minlength : 2 }}
            },
            {
              label: "Door / House number",
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
          head: "Financial Details",
          body: [
            {
              label: "Account holder's name",
              isMandatory: true,
              key: "financeDetails_accountHolderName",
              type: "text",
              disable: false,
              populators: { name: "financeDetails_accountHolderName", validation: { minlength : 2 }}
            },
            {
              label: "Account number",
              isMandatory: true,
              key: "financeDetails_accountNumber",
              type: "number",
              disable: false,
              populators: { name: "financeDetails_accountNumber" }
            },
            {
              label: "Account Type",
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
              label:"IFSC",
              isMandatory: true,
              key: "financeDetails_ifsc",
              type: "text",
              disable: false,
              populators: { name: "financeDetails_ifsc", error: "WORKS_REQUIRED_ERR", validation: {minlength : 2} },
            },
            {
              label: "Branch name",
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