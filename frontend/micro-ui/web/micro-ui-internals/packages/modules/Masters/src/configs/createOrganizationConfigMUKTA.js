//Civil Works, Electrical Works, Not applicable.
const FunctionalCategory = [
    {
        code: "CIVIL_WORKS",
        name: "Civil Works"
    },
    {
        code: "ELECTRICAL_WORKS",
        name: "Electrical Works"
    },
    {
        code: "NOT_APPLICABLE",
        name: "Not Applicable"
    }
]

const Rank = [
    { code: "A", name: "A" },
    { code: "B", name: "B" },
    { code: "C", name: "C"},
    { code: "D", name: "D"},
    { code: "NOT_APPLICABLE", name: "Not Applicable"}
]

export const createOrganizationConfigMUKTA = ({defaultValues, orgType, orgSubType, ULBOptions, wards, localities}) => {
    return {
        tenantId: "pg",
        moduleName: "commonMuktaUiConfig",
        CreateOrganisationConfig: [
            {
                defaultValues: defaultValues,
                metaData: {
                    showNavs: true
                },
                form: [
                {
                    head: "",
                    subHead: "",
                    body: [
                    {
                        inline: true,
                        label: "MASTERS_NAME_OF_ORGN",
                        isMandatory: true,
                        key: "basicDetails_orgName",
                        type: "text",
                        disable: false,
                        populators: {
                            name: "basicDetails_orgName",
                            error: "MASTERS_PATTERN_ERR_MSG_ORG_DETAILS",
                            validation: {pattern: /^[a-zA-Z0-9\/{\/.\- _$@#\'}]*$/i, minlength : 2, maxlength: 128}
                        }
                    },
                    {
                        inline: true,
                        label: "MASTERS_REGISTERED_BY_DEPT",
                        isMandatory: true,
                        key: "basicDetails_regDept",
                        type: "text",
                        disable: false,
                        populators: {
                            name: "basicDetails_regDept",
                            error: "MASTERS_PATTERN_ERR_MSG_ORG_DETAILS",
                            validation: {pattern: /^[a-zA-Z0-9\/{\/.\- _$@#\'}]*$/i, minlength : 2, maxlength: 64}
                        }
                    },
                    {
                        inline: true,
                        label: "MASTERS_REGISTRATION_NUMBER",
                        isMandatory: true,
                        key: "basicDetails_regDeptNo",
                        type: "text",
                        disable: false,
                        populators: {
                            name: "basicDetails_regDeptNo",
                            error: "MASTERS_PATTERN_ERR_MSG_ORG_DETAILS",
                            validation: {pattern: /^[a-zA-Z0-9\/{\/.\-]*$/i, minlength : 2, maxlength: 64}
                        }
                    },
                    {
                        inline: true,
                        label: "MASTERS_DATE_OF_INCORPORATION",
                        isMandatory: true,
                        key: "basicDetails_dateOfIncorporation",
                        type: "date",
                        disable: false,
                        populators: { name: "basicDetails_dateOfIncorporation", error: "WORKS_REQUIRED_ERR", max : new Date().toISOString().split("T")[0] },
                    }
                    ]
                },
                {
                    head: "Functional Details",
                    subHead: "",
                    body: [
                        {
                            inline: true,
                            key: "funDetails_orgType",
                            label: "MASTERS_ORGANISATION_TYPE",
                            isMandatory: true,
                            type: "dropdown",
                            disable: false,
                            populators: {
                                name: "funDetails_orgType",
                                optionsKey: "name",
                                error: "WORKS_REQUIRED_ERR",
                                optionsCustomStyle: {
                                    top: "2.3rem",
                                },
                                options: orgType?.orgTypes
                            }
                        },
                        {
                            inline: true,
                            key: "funDetails_orgSubType",
                            label: "MASTERS_ORGANISATION_SUB_TYPE",
                            isMandatory: true,
                            type: "dropdown",
                            disable: false,
                            populators: {
                                name: "funDetails_orgSubType",
                                optionsKey: "name",
                                error: "WORKS_REQUIRED_ERR",
                                optionsCustomStyle: {
                                    top: "2.3rem",
                                },
                               options: orgSubType
                            }
                        },
                        {
                            inline: true,
                            key: "funDetails_category",
                            label: "ES_COMMON_CATEGORY",
                            isMandatory: true,
                            type: "dropdown",
                            disable: false,
                            populators: {
                                name: "funDetails_category",
                                optionsKey: "name",
                                error: "WORKS_REQUIRED_ERR",
                                optionsCustomStyle: {
                                    top: "2.3rem",
                                },
                                options: FunctionalCategory
                            }
                        },
                        {
                            inline: true,
                            key: "funDetails_classRank",
                            label: "MASTERS_CLASS_RANK",
                            isMandatory: true,
                            type: "dropdown",
                            disable: false,
                            populators: {
                                name: "funDetails_classRank",
                                optionsKey: "name",
                                error: "WORKS_REQUIRED_ERR",
                                optionsCustomStyle: {
                                    top: "2.3rem",
                                },
                                options: Rank
                            }
                        },
                        {
                            inline: true,
                            label: "ES_COMMON_VALID_FROM",
                            isMandatory: true,
                            key: "funDetails_validFrom",
                            type: "date",
                            disable: false,
                            populators: { name: "funDetails_validFrom", error: "WORKS_REQUIRED_ERR", max : new Date().toISOString().split("T")[0] },
                        },
                        {
                            inline: true,
                            label: "ES_COMMON_VALID_TO",
                            isMandatory: false,
                            key: "funDetails_validTo",
                            type: "date",
                            disable: false,
                            populators: { name: "funDetails_validTo", min : new Date().toISOString().split("T")[0] },
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
                        populators: {
                            name: "locDetails_city",
                            optionsKey: "i18nKey",
                            error: "WORKS_REQUIRED_ERR",
                            optionsCustomStyle : {
                            top : "2.3rem"
                            },
                            options: ULBOptions
                        }
                        },
                        {
                        label: "COMMON_WARD",
                        isMandatory: true,
                        key: "locDetails_ward",
                        type: "radioordropdown",
                        disable: false,
                        populators: {
                            name: "locDetails_ward",
                            optionsKey: "i18nKey",
                            error: ("WORKS_REQUIRED_ERR"),
                            optionsCustomStyle : {
                            top : "2.3rem"
                            },
                            options: wards?.wards
                        }
                        },
                        {
                        label: "COMMON_LOCALITY",
                        isMandatory: true,
                        key: "locDetails_locality",
                        type: "radioordropdown",
                        disable: false,
                        populators: {
                            name: "locDetails_locality",
                            optionsKey: "i18nKey",
                            error: ("WORKS_REQUIRED_ERR"),
                            optionsCustomStyle : {
                            top : "2.3rem"
                            },
                            options: localities
                        }
                        },
                        {
                            label: "ES_COMMON_STREET_NAME",
                            isMandatory: false,
                            key: "locDetails_streetName",
                            type: "text",
                            disable: false,
                            populators: { 
                                name: "locDetails_streetName", 
                                validation: { minlength : 2 }
                            }
                        },
                        {
                            label: "ES_COMMON_DOOR_NO",
                            isMandatory: false,
                            key: "locDetails_houseName",
                            type: "text",
                            disable: false,
                            populators: { 
                                name: "locDetails_houseName", 
                                validation: { minlength : 2 }
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
                            inline: true,
                            label: "CORE_COMMON_NAME",
                            isMandatory: true,
                            key: "contactDetails_name",
                            type: "text",
                            disable: false,
                            populators: {
                            name: "contactDetails_name",
                            error: "MASTERS_PATTERN_ERR_MSG_ORG_DETAILS",
                            validation: {pattern: /^[a-zA-Z0-9\/{ \/.\- @#\'}]*$/i, minlength : 2, maxlength: 64}
                            }
                        },
                        {
                            inline: true,
                            label: "CORE_COMMON_PROFILE_MOBILE_NUMBER",
                            isMandatory: true,
                            key: "contactDetails_mobile",
                            type: "number",
                            disable: false,
                            populators: {
                            name: "contactDetails_mobile",
                            error: "PHONE_VALIDATION",
                            validation: { min: 5999999999, max: 9999999999 }
                            }
                        },
                        {
                            inline: true,
                            label: "CORE_COMMON_PROFILE_EMAIL",
                            isMandatory: false,
                            key: "contactDetails_email",
                            type: "text",
                            disable: false,
                            populators: {
                            name: "contactDetails_email",
                            error: "EMAIL_VALIDATION",
                            validation: {pattern:  Digit?.Utils?.getPattern?.("Email"), minlength : 2}
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
                            populators: { name: "financeDetails_accountHolderName", error: "WORKS_REQUIRED_ERR", validation: { minlength : 2, maxlength: 64}}
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
                            label: "MASTERS_ACC_NO",
                            isMandatory: true,
                            key: "financeDetails_accountNumber",
                            type: "number",
                            disable: false,
                            populators: { name: "financeDetails_accountNumber", error: "WORKS_REQUIRED_ERR", validation: {pattern: Digit.Utils.getPattern('bankAccountNo')} }
                        },
                        {
                            type: "component",
                            component: "TransferCodeTable",
                            withoutLabel: true,
                            key: "transferCodes",
                        }, 
                        {
                            label: "MASTERS_BANK_NAME",
                            isMandatory: false,
                            key: "financeDetails_bankName",
                            type: "text",
                            disable: false,
                            populators: { name: "financeDetails_bankName", validation: { minlength : 2 }}
                        },
                        {
                            label: "MASTERS_BRANCH_NAME",
                            isMandatory: false,
                            key: "financeDetails_branchName",
                            type: "text",
                            disable: false,
                            populators: { name: "financeDetails_branchName", validation: { minlength : 2 }}
                        },
                        {
                            type: "component",
                            component: "TransferCodeTable",
                            withoutLabel: true,
                            key: "taxIdentifier",
                        }
                    ]
                }
                ]
            }
        ]
    }
}