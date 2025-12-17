export const createOrganizationConfigMUKTA = {
    tenantId: "od",
    moduleName: "commonMuktaUiConfig",
    CreateOrganizationConfig: [
        {
            defaultValues: {},
            metaData: {
                showNavs: true
            },
            form: [
            {
                head: "",
                subHead: "",
                body: [
                    {
                        label: "MASTERS_ORGANISATION_ID",
                        isMandatory: false,
                        key: "basicDetails_orgId",
                        type: "text",
                        disable: true,
                        preProcess : {
                          updateDependent : ["populators.customStyle.display"]
                        },
                        populators: { 
                          name: "basicDetails_orgId", 
                          customStyle : {
                            display : "none"
                          },
                          customClass : "field-value-no-border"
                        }
                    },
                    {
                        label: "MASTERS_NAME_OF_ORGN",
                        isMandatory: true,
                        key: "basicDetails_orgName",
                        type: "text",
                        disable: false,
                        preProcess : {
                            convertStringToRegEx : ["populators.validation.pattern"]
                        },
                        populators: {
                            name: "basicDetails_orgName",
                            error: "MASTERS_PATTERN_ERR_MSG_ORG_DETAILS",
                            validation: {pattern: "^[a-zA-Z0-9 .\\-_@\\']*$", minlength : 2, maxlength: 128}
                        }
                    },
                    {
                        label: "MASTERS_REGISTERED_BY_DEPT",
                        isMandatory: false,
                        key: "basicDetails_regDept",
                        type: "text",
                        disable: false,
                        preProcess : {
                            convertStringToRegEx : ["populators.validation.pattern"]
                        },
                        populators: {
                            name: "basicDetails_regDept",
                            error: "WORKS_REQUIRED_ERR",
                            validation: {pattern: "^[a-zA-Z0-9 .\\-_@\\']*$", minlength : 2, maxlength: 64}
                        }
                    },
                    {
                        label: "MASTERS_REGISTRATION_NUMBER",
                        isMandatory: false,
                        key: "basicDetails_regDeptNo",
                        type: "text",
                        disable: false,
                        preProcess : {
                            convertStringToRegEx : ["populators.validation.pattern"]
                        },
                        populators: {
                            name: "basicDetails_regDeptNo",
                            error: "MASTERS_PATTERN_ERR_MSG_ORG_DETAILS",
                            validation: {pattern: "^[a-zA-Z0-9 .\\-_@\\']*$", minlength : 2, maxlength: 64}
                        }
                    },
                    {
                        label: "MASTERS_DATE_OF_INCORPORATION",
                        isMandatory: true,
                        key: "basicDetails_dateOfIncorporation",
                        type: "date",
                        disable: false,
                        preProcess : {
                            updateDependent : ["populators.max"]
                        },
                        populators: { 
                            name: "basicDetails_dateOfIncorporation", 
                            error: "WORKS_REQUIRED_ERR", 
                            max: "currentDate"
                        }
                    }
                ]
            },
            {
                head: "MASTERS_FUNCTIONAL_DETAILS",
                subHead: "",
                body: [
                    {
                        key: "funDetails_orgType",
                        label: "MASTERS_ORGANISATION_TYPE",
                        isMandatory: true,
                        type: "dropdown",
                        disable: false,
                        preProcess : {
                            updateDependent : ["populators.options"]
                        },
                        populators: {
                            name: "funDetails_orgType",
                            optionsKey: "name",
                            error: "WORKS_REQUIRED_ERR",
                            // optionsCustomStyle: {
                            //     top: "2.3rem",
                            // },
                            options: []
                        }
                    },
                    {
                        key: "funDetails_orgSubType",
                        label: "MASTERS_ORGANISATION_SUB_TYPE",
                        isMandatory: true,
                        type: "dropdown",
                        disable: false,
                        preProcess : {
                            updateDependent : ["populators.options"]
                        },
                        populators: {
                            name: "funDetails_orgSubType",
                            optionsKey: "name",
                            error: "WORKS_REQUIRED_ERR",
                            // optionsCustomStyle: {
                            //     top: "2.3rem",
                            // },
                            options: []
                        }
                    },
                    {
                        key: "funDetails_category",
                        label: "ES_COMMON_CATEGORY",
                        isMandatory: true,
                        type: "dropdown",
                        disable: false,
                        preProcess : {
                            updateDependent : ["populators.options"]
                        },
                        populators: {
                            name: "funDetails_category",
                            optionsKey: "name",
                            error: "WORKS_REQUIRED_ERR",
                            // optionsCustomStyle: {
                            //     top: "2.3rem",
                            // },
                            options: []
                        }
                    },
                    {
                        key: "funDetails_classRank",
                        label: "MASTERS_CLASS_RANK",
                        isMandatory: true,
                        type: "dropdown",
                        disable: false,
                        populators: {
                            name: "funDetails_classRank",
                            optionsKey: "name",
                            error: "WORKS_REQUIRED_ERR",
                            // optionsCustomStyle: {
                            //     top: "2.3rem",
                            // },
                            mdmsConfig: {
                                masterName: "OrgFunctionClass",
                                moduleName: "common-masters",
                                localePrefix: "COMMON_MASTERS_CLASS",
                            }
                        }
                    },
                    {
                        label: "ES_COMMON_VALID_FROM",
                        isMandatory: true,
                        key: "funDetails_validFrom",
                        type: "date",
                        disable: false,
                        preProcess : {
                            updateDependent : ["populators.max"]
                        },
                        populators: { 
                            name: "funDetails_validFrom", 
                            error: "WORKS_REQUIRED_ERR", 
                            max: "currentDate"
                        }
                    },
                    {
                        label: "ES_COMMON_VALID_TO",
                        isMandatory: false,
                        key: "funDetails_validTo",
                        type: "date",
                        disable: false,
                        preProcess : {
                            updateDependent : ["populators.min"]
                        },
                        populators: { 
                            name: "funDetails_validTo", 
                            min : "currentDate"
                        }
                    }
                ]
            },
            {
                navLink: "location_details",
                head: "",
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
                            //     top : "2.3rem"
                            // },
                            options: []
                        }
                    },
                    {
                        label: "COMMON_WARD",
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
                            // optionsCustomStyle : {
                            // top : "2.3rem"
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
                            error: "WORKS_PATTERN_ERR",
                            validation: { pattern: "^[a-zA-Z0-9 .,\\/\\-_@#\\']*$", minlength : 2, maxlength: 64 }
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
                            error: "WORKS_PATTERN_ERR",
                            validation: { pattern: "^[a-zA-Z0-9 .,\\/\\-_@#\\']*$", minlength : 2, maxlength: 8 }
                        }
                    }
                ]
            },
            {
                navLink: "contact_Details",
                sectionFormCategory: "contactDetails",
                head: "",
                body: [
                    {
                        label: "CORE_COMMON_NAME",
                        isMandatory: true,
                        key: "contactDetails_name",
                        type: "text",
                        disable: false,
                        preProcess : {
                            convertStringToRegEx : ["populators.validation.pattern"]
                        },
                        populators: {
                            name: "contactDetails_name",
                            error: "MASTERS_PATTERN_ERR_MSG_ORG_DETAILS",
                            validation: {pattern: "^[a-zA-Z0-9 .\\-_@\\']*$", minlength : 1, maxlength: 50}
                        }
                    },
                    {
                        label: "CORE_COMMON_PROFILE_MOBILE_NUMBER",
                        isMandatory: true,
                        key: "contactDetails_mobile",
                        type: "mobileNumber",
                        disable: false,
                        populators: {
                            name: "contactDetails_mobile",
                            error: "PHONE_VALIDATION",
                            validation: { min: 5999999999, max: 9999999999 }
                        }
                    },
                    {
                        label: "CORE_COMMON_PROFILE_EMAIL",
                        isMandatory: false,
                        key: "contactDetails_email",
                        type: "text",
                        disable: false,
                        preProcess : {
                            convertStringToRegEx : ["populators.validation.pattern"]
                        },
                        populators: {
                            name: "contactDetails_email",
                            error: "EMAIL_VALIDATION",
                            validation: {pattern: "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$", minlength : 2, maxlength: 64}                         
                        }
                    }
                ]
            },
            {
                navLink: "financial_Details",
                sectionFormCategory: "termsAndConditions",
                head: "",
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
                        label: "MASTERS_BANK_ACCOUNT_TYPE",
                        isMandatory: true,
                        key: "financeDetails_accountType",
                        type: "radioordropdown",
                        populators: {
                            name: "financeDetails_accountType",
                            optionsKey: "name",
                            error: "WORKS_REQUIRED_ERR",
                            // optionsCustomStyle : {
                            //     top : "2.3rem"
                            // },
                            mdmsConfig: {
                                masterName: "BankAccType",
                                moduleName: "works",
                                localePrefix: "MASTERS",
                            }
                        }
                    },
                    {
                        label: "MASTERS_ACC_NO",
                        isMandatory: true,
                        key: "financeDetails_accountNumber",
                        type: "text",
                        disable: false,
                        preProcess : {
                            convertStringToRegEx : ["populators.validation.pattern"]
                        },
                        populators: { 
                            name: "financeDetails_accountNumber", 
                            error: "BANK_ACCOUNT_VALIDATION", 
                            validation: { pattern: "^[0-9]{9,18}$", minlength : 9, maxlength: 18 } }
                    },
                    {
                        type: "component",
                        component: "TransferCodeTable",
                        withoutLabel: true,
                        key: "transferCodes",
                        customProps : {
                            isMandatory: true
                        }
                    }, 
                    {
                        label: "MASTERS_BANK_NAME",
                        isMandatory: false,
                        key: "financeDetails_bankName",
                        type: "text",
                        disable: true,
                        populators: { name: "financeDetails_bankName", validation: { minlength : 2 }}
                    },
                    {
                        label: "MASTERS_BRANCH_NAME",
                        isMandatory: false,
                        key: "financeDetails_branchName",
                        type: "text",
                        disable: true,
                        populators: { name: "financeDetails_branchName", validation: { minlength : 2 }}
                    },
                    {
                        type: "component",
                        component: "TransferCodeTable",
                        withoutLabel: true,
                        key: "taxIdentifier",
                        customProps : {
                            isMandatory: false
                        }
                    }
                ]
            }
            ]
        }
    ]
}