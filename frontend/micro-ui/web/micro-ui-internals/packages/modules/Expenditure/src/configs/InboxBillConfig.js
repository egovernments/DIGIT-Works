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
                                    validation: { minlength : 2 }
                                }
                            },
                            {
                                label:"Project ID",
                                type: "text",
                                isMandatory: false,
                                disable: false,
                                populators: { 
                                    name: "projectNumber",
                                    validation: { minlength : 2 }
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
                                label:"ATM_MUSTER_ROLL_DATE_RANGE",
                                type: "dateRange",
                                isMandatory: false,
                                disable: false,
                                populators: { 
                                    name: "musterRolldateRange"
                                },
                            },
                            {
                                label: "ES_COMMON_STATUS",
                                type: "dropdown",
                                isMandatory: false,
                                disable: false,
                                populators: {
                                    name: "musterRollStatus",
                                    optionsKey: "i18nKey",
                                    options: [],
                                    optionsCustomStyle : {
                                        top : "2.3rem"
                                    }
                                },
                            },
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