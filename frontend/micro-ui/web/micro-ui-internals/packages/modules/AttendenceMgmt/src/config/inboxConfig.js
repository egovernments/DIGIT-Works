const inboxConfig = () => {
    return {
        label : "ES_COMMON_INBOX",
        type : "inbox", 
        apiDetails: {
            serviceName: "/inbox/v2/_search",
            requestParam: {},
            requestBody: {
                inbox: {
                    processSearchCriteria: {
                        businessService: [
                            "muster-roll-approval"
                        ],
                        moduleName: "muster-roll-service"
                    },
                    moduleSearchCriteria: {}
                }
            },
            minParametersForSearchForm:1,
            masterName:"commonUiConfig",
            moduleName:"AttendanceInboxConfig",
            tableFormJsonPath:"requestBody.inbox",
            filterFormJsonPath:"requestBody.inbox.moduleSearchCriteria",
            searchFormJsonPath:"requestBody.inbox.moduleSearchCriteria",
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
                                options: []
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
                            jsonPath: "businessObject.musterRollNumber",
                            additionalCustomization:true 
                        },
                        {
                            label: "WORKS_NAME_OF_WORK",
                            jsonPath: "businessObject.additionalDetails.attendanceRegisterName",
                        },
                        {
                            label: "ATM_ATTENDANCE_WEEK",
                            jsonPath: "businessObject",
                            additionalCustomization:true
                        },
                        {
                            label: "ATM_IA_AP",
                            jsonPath: "businessObject.additionalDetails.orgName",
                        },
                        {
                            label: "ATM_NO_OF_INDIVIDUALS",
                            jsonPath: "businessObject.individualEntries",
                            additionalCustomization:true 
                        },
                        {
                            label: "ATM_SLA",
                            jsonPath: "businessObject.serviceSla",
                            additionalCustomization:true
                        }
                    ],
                    enableGlobalSearch: false,
                    enableColumnSort: true,
                    resultsJsonPath: "items",
                },
                children: {},
                show: true 
            }
        },
        additionalSections : {}
    }
}

export default inboxConfig;