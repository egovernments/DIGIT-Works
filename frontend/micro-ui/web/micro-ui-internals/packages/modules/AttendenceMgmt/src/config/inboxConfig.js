const musterRollStatus = [
    {
        name: 'Submitted',
        code: 'SUBMITTED',
        i18nKey: 'SUBMITTED'
    },
    {
        name: 'Approved',
        code: 'APPROVED',
        i18nKey: 'APPROVED'
    },
    {
        name: 'Rejected',
        code: 'REJECTED',
        i18nKey: 'REJECTED'
    },
    {
        name: 'Verified',
        code: 'VERIFIED',
        i18nKey: 'VERIFIED'
    }
]
const inboxConfig = () => {
    return {
        label : "ES_COMMON_INBOX",
        type : "inbox", 
        apiDetails: {
            serviceName: "/muster-roll/v1/_search",
            requestParam: {},
            requestBody: {},
            minParametersForSearchForm:1,
            masterName:"commonUiConfig",
            moduleName:"SearchAttendanceConfig",
            tableFormJsonPath:"requestParam",
            filterFormJsonPath:"rrequestParam",
            searchFormJsonPath:"requestParam",
        },
        sections : {
            search : {
                uiConfig : {
                    headerStyle : null,
                    primaryLabel: 'ACTION_TEST_SEARCH',
                    secondaryLabel: 'CLEAR_SEARCH_LINk',
                    minReqFields: 1,
                    defaultValues : {
                        attendanceRegisterName: "",
                        iaip:"",
                    },
                    fields : [
                        {
                            label:"NAME_OF_WORK",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "attendanceRegisterName",
                                validation: { minlength : 2 }
                            }
                        },
                        {
                            label: "ATM_IMPLEMENTING_AGENCY",
                            type: "dropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                              name: "iaip",
                              optionsKey: "name",
                              mdmsConfig: {
                                masterName: "Department",
                                moduleName: "common-masters",
                                localePrefix: "COMMON_MASTERS_DEPARTMENT",
                              }
                            }
                        },

                    ]
                },
                label : "",
                children : {},
                show : true
            },
            links : {
                uiConfig : {
                    links : [
                        {
                            text: "WORKS_ENROLL_WAGE_SEEKER",
                            url: `/employee/contracts/create-contract`,
                            roles: [],
                        }
                    ],
                    label : "ATM_ATTENDANCE_MANAGEMENT",
                    logoIcon : {
                        component : "BioMetricIcon",
                        customClass : "search-icon--projects"       
                    }
                },
                children : {},
                show : true 
            },
            filter : {
                uiConfig : {
                    type : 'filter',
                    headerStyle : null,
                    primaryLabel: 'ACTION_TEST_APPLY',
                    minReqFields: 0,
                    secondaryLabel: '',
                    defaultValues : {
                        musterRolldateRange:"",
                        musterRollStatus: ""
                    },
                    fields : [
                        {
                            label:"ATM_MUSTER_ROLL_DATE_RANE",
                            type: "dateRange",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "musterRolldateRange"
                            },
                        },
                        {
                            label: "CORE_COMMON_STATUS",
                            type: "dropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "musterRollStatus",
                                optionsKey: "i18nKey",
                                options: musterRollStatus
                            },
                        },
                    ]
                },
                label : "FILTERS",
                show : true
            },
            searchResult: {
                label: "",
                uiConfig: {
                    columns: [
                        {
                            label: "ATM_MUSTER_ROLL_ID",
                            jsonPath: "musterRollNumber",
                        },
                        {
                            label: "WORKS_NAME_OF_WORK",
                            jsonPath: "work",
                        },
                        {
                            label: "ATM_ATTENDANCE_WEEK",
                            jsonPath: "week",
                        },
                        {
                            label: "ATM_IA_AP",
                            jsonPath: "iaip",
                        },
                        {
                            label: "ATM_NO_OF_INDIVIDUALS",
                            jsonPath: "individualCount",
                        },
                        {
                            label: "ATM_SLA",
                            jsonPath: "slaDays",
                        }
                    ],
                    enableGlobalSearch: false,
                    enableColumnSort: true,
                    resultsJsonPath: "musterRolls",
                },
                children: {},
                show: true 
            }
        },
        additionalSections : {}
    }
}

export default inboxConfig;