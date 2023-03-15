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
                                label:"Bill Number",
                                type: "text",
                                isMandatory: false,
                                disable: false,
                                populators: { 
                                    name: "billNumber",
                                    error: 'Enter valid bill number',
                                    validation: { pattern: /^[a-zA-Z0-9\/{ \/ .\- _$@#\'() } ]*$/i, minlength : 2 }
                                }
                            },
                            {
                                label:"Project ID",
                                type: "text",
                                isMandatory: false,
                                disable: false,
                                populators: { 
                                    name: "projectNumber",
                                    error: 'Enter valid project number',
                                    validation: { pattern: /^[a-zA-Z0-9\/{ \/ .\- _$@#\'() } ]*$/i, minlength : 2 }
                                }
                            },
                            {
                                label: "Bill type",
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
                                text: "Create Bill",
                                url: `/employee/expenditure/create-bill`,
                                roles: [],
                            },
                            {
                                text: "Search Bill",
                                url: `/employee/expenditure/search-bill`,
                                roles: [],
                            }
                        ],
                        label : "MUKTA",
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
                                            name: "BILL_INBOX_ASSIGNED_TO_ME"
                                        },
                                        {
                                            code: "ASSIGNED_TO_ALL",
                                            name: "BILL_INBOX_ASSIGNED_TO_ALL"
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
                                label: "Bill number",
                                jsonPath: "businessObject.musterRollNumber",
                                additionalCustomization:true 
                            },
                            {
                                label: "Project name",
                                jsonPath: "businessObject.additionalDetails.attendanceRegisterName",
                            },
                            {
                                label: "CBO name",
                                jsonPath: "businessObject"
                            },
                            {
                                label: "Assignee",
                                jsonPath: "businessObject.additionalDetails.orgName",
                            },
                            {
                                label: "Workflow state",
                                jsonPath: "businessObject.individualEntries"
                            },
                            {
                                label: "Amount (₹)",
                                jsonPath: "businessObject.serviceSla",
                                additionalCustomization:true
                            },
                            {
                                label: "SLA days remaining",
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