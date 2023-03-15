export const InboxBillConfig = {
    "tenantId": "pg",
    "moduleName": "commonMuktaUiConfig",
    "InboxBillConfig":[
        {
            label : "ES_COMMON_INBOX",
            postProcessResult:false,
            type : "inbox", 
            apiDetails: {
                serviceName: "/inbox/v2/_search",
                requestParam: {},
                requestBody: {},
                minParametersForSearchForm:1,
                masterName:"commonUiConfig",
                moduleName:"BillInboxConfig",
                tableFormJsonPath:"requestBody",
                filterFormJsonPath:"requestBody",
                searchFormJsonPath:"requestBody",
            },
            sections : {
                search : {
                    uiConfig : {
                        headerStyle : null,
                        primaryLabel: 'ES_COMMON_SEARCH',
                        secondaryLabel: 'ES_COMMON_CLEAR_SEARCH',
                        minReqFields: 1,
                        defaultValues : {
                            billNumber: "",
                            projectNumber:"",
                            billType: ""
                        },
                        fields : [
                            {
                                label:"WORKS_BILL_NUMBER",
                                type: "text",
                                isMandatory: false,
                                disable: false,
                                preProcess: {
                                    convertStringToRegEx: [
                                        "populators.validation.pattern"
                                    ]
                                },
                                populators: { 
                                    name: "billNumber",
                                    error: 'Enter valid bill number',
                                    validation: { pattern: "^[A-Za-z0-9\\/-]*$", minlength : 2 }
                                }
                            },
                            {
                                label:"WORKS_PROJECT_ID",
                                type: "text",
                                isMandatory: false,
                                disable: false,
                                populators: { 
                                    name: "projectNumber",
                                    error: 'Enter valid project number',
                                    validation: { pattern: "^[A-Za-z0-9\\/-]*$", minlength : 2 }
                                }
                            },
                            {
                                label: "WORKS_BILL_TYPE",
                                type: "dropdown",
                                isMandatory: false,
                                disable: false,
                                populators: {
                                  name: "billType",
                                  optionsKey: "name",
                                  optionsCustomStyle : {
                                    top : "2.3rem"
                                  },
                                  mdmsConfig: {
                                    masterName: "BillType",
                                    moduleName: "common-masters",
                                    localePrefix: "COMMON_MASTERS_BILL",
                                  }
                                }
                            }
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
                                text: "EXP_CREATE_BILL",
                                url: `/employee/expenditure/create-bill`,
                                roles: [],
                            },
                            {
                                text: "EXP_SEARCH_BILL",
                                url: `/employee/expenditure/search-bill`,
                                roles: [],
                            }
                        ],
                        label : "MUKTA",
                        logoIcon : {
                            component : "PropertyHouse",
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
                        primaryLabel: 'ES_COMMON_APPLY',
                        minReqFields: 0,
                        secondaryLabel: '',
                        defaultValues : {
                            musterRolldateRange:"",
                            musterRollStatus: ""
                        },
                        fields : [
                            {
                                label: "",
                                type: "radio",
                                isMandatory: false,
                                disable: false,
                                populators: {
                                    name: "assignee",
                                    options: [
                                        {
                                            code: "ASSIGNED_TO_ME",
                                            name: "COMMON_INBOX_ASSIGNED_TO_ME"
                                        },
                                        {
                                            code: "ASSIGNED_TO_ALL",
                                            name: "COMMON_INBOX_ASSIGNED_TO_ALL"
                                        }
                                    ],
                                    optionsKey: "name",
                                    styles: {
                                        gap: "1rem",
                                        flexDirection: "column"
                                    },
                                    innerStyles: {
                                        display: "flex"
                                    }
                                }
                            },
                            {
                                label: "COMMON_WARD",
                                type: "locationdropdown",
                                isMandatory: false,
                                disable: false,
                                populators: {
                                    name: "ward",
                                    type: "ward",
                                    optionsKey: "name",
                                    defaultText: "COMMON_SELECT_WARD",
                                    selectedText: "COMMON_SELECTED",
                                    allowMultiSelect: true
                                }
                            },
                            {
                                label: "COMMON_LOCALITY",
                                type: "locationdropdown",
                                isMandatory: false,
                                disable: false,
                                populators: {
                                    name: "locality",
                                    type: "locality",
                                    optionsKey: "name",
                                    defaultText: "COMMON_SELECT_LOCALITY",
                                    selectedText: "COMMON_SELECTED",
                                    allowMultiSelect: true
                                }
                            },
                            {
                                label: "COMMON_WORKFLOW_STATES",
                                type: "workflowstatesfilter",
                                isMandatory: false,
                                disable: false,
                                populators: {
                                    name: "state",
                                    labelPrefix: "WF_EST_",
                                    businessService: "estimate-approval-5"
                                }
                            }
                        ]
                    },
                    label : "ES_COMMON_FILTERS",
                    show : true
                },
                searchResult: {
                    label: "",
                    uiConfig: {
                        columns: [
                            {
                                label: "WORKS_BILL_NUMBER",
                                jsonPath: "businessObject.musterRollNumber",
                                additionalCustomization:true 
                            },
                            {
                                label: "ES_COMMON_PROJECT_NAME",
                                jsonPath: "businessObject.additionalDetails.attendanceRegisterName",
                            },
                            {
                                label: "ES_COMMON_CBO_NAME",
                                jsonPath: "businessObject"
                            },
                            {
                                label: "COMMON_ASSIGNEE",
                                jsonPath: "businessObject.additionalDetails.orgName",
                            },
                            {
                                label: "COMMON_WORKFLOW_STATES",
                                jsonPath: "businessObject.individualEntries"
                            },
                            {
                                label: "ES_COMMON_AMOUNT",
                                jsonPath: "businessObject.serviceSla",
                                additionalCustomization:true
                            },
                            {
                                label: "COMMON_SLA_DAYS",
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
      ]
}