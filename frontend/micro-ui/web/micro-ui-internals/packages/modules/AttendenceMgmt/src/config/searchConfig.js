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

const searchConfig = () => {
    return {
        label : "ATM_SEARCH_ATTENDANCE",
        type: 'search',
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
                    primaryLabel: 'ES_COMMON_SEARCH',
                    secondaryLabel: 'ES_COMMON_CLEAR_SEARCH',
                    minReqFields: 0,
                    defaultValues : {
                        musterRollNumber: "",
                        registerId: "",
                        status: "",
                        musterRollStatus: "",
                        fromDate: "",
                        toDate: ""
                    },
                    fields : [
                        {
                            label:"ATM_MUSTER_ROLL_NUMBER",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "musterRollNumber",
                                error: `Enter valid muster roll number`,
                                validation: { pattern: /^[a-z0-9\/-]*$/i, minlength : 2 }
                            }
                        },
                        {
                            label: "ATM_REGISTER_ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "registerId",
                                error: `Enter valid register id`,
                                validation: { pattern: /^[a-z0-9\/-]*$/i, minlength : 2 }
                            }
                        },
                        {
                            label: "ES_COMMON_STATUS",
                            type: "dropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "musterRollStatus",
                                optionsKey: "i18nKey",
                                options: musterRollStatus,
                                optionsCustomStyle : {
                                    top : "2.3rem"
                                }
                            },
                        },
                        {
                            label: "CREATED_FROM_DATE",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "fromDate",
                            }
                        },
                        {
                            label: "CREATED_TO_DATE",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "toDate",
                                error: 'DATE_VALIDATION_MSG',
                            }
                        }
                    ]
                },
                label : "",
                children : {},
                show : true
            },
            searchResult: {
                label: "",
                uiConfig: {
                    columns: [
                        {
                            label: "ATM_MUSTER_ROLL_NUMBER",
                            jsonPath: "musterRollNumber",
                            additionalCustomization:true
                        },
                        {
                            label: "ATM_NAME_OF_WORK",
                            jsonPath: "additionalDetails.attendanceRegisterName"
                        },
                        {
                            label: "ATM_IMPLEMENTING_AGENCY",
                            jsonPath: "additionalDetails.orgName"
                        },
                        {
                            label: "ATM_NO_OF_INDIVIDUALS",
                            jsonPath: "individualEntries",
                            additionalCustomization:true
                        },
                        {
                            label: "ES_COMMON_STATUS",
                            jsonPath: "musterRollStatus",
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

export default searchConfig;