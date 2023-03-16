export const SearchBillConfig = {
    "tenantId": "pg",
    "moduleName": "commonMuktaUiConfig",
    "SearchBillConfig":[
        {
            label : "EXP_SEARCH_BILL",
            type: 'search',
            apiDetails: {
                serviceName: "/muster-roll/v1/_search",
                requestParam: {},
                requestBody: {},
                minParametersForSearchForm:1,
                masterName:"commonUiConfig",
                moduleName:"SearchBillConfig",
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
                        minReqFields: 1,
                        showFormInstruction : "",
                        defaultValues : {
                            ward: "",
                            billType: {
                                name: "Work Order",
                                code: "WORK_ORDER",
                                active: true
                            },
                            projectName: "",
                            musterRollNumber: "",
                            status: "",
                            createdFrom: "",
                            createdTo: ""
                        },
                        fields : [
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
                                    allowMultiSelect: false,
                                    optionsCustomStyle : {
                                        top : "2.3rem"
                                    }
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
                            },
                            {
                                label: "WORKS_PROJECT_NAME",
                                type: "text",
                                isMandatory: false,
                                disable: false,
                                preProcess : {
                                    convertStringToRegEx : ["populators.validation.pattern"]
                                },
                                populators: { 
                                    name: "projectName",
                                    error: `PROJECT_PATTERN_ERR_MSG`,
                                    validation: { pattern: "^[A-Za-z0-9\\/-]*$", minlength : 2 }
                                }
                            },
                            {
                                label:"WORKS_BILL_NUMBER",
                                type: "text",
                                isMandatory: false,
                                disable: false,
                                populators: { 
                                    name: "musterRollNumber",
                                    error: "ES_COMMON_BILL_PATTERN_ERR_MSG",
                                    validation: { pattern: "^[A-Za-z0-9\\/-]*$", minlength : 2 }
                                }
                            },
                            {
                                label: "CORE_COMMON_STATUS",
                                type: "dropdown",
                                isMandatory: false,
                                disable: false,
                                populators: {
                                    name: "status",
                                    optionsKey: "name",
                                    optionsCustomStyle: {
                                        top: "2.3rem"
                                    },
                                    options: []
                                }
                            },
                            {
                                label: "ES_COMMON_CREATED_FROM",
                                type: "date",
                                isMandatory: false,
                                disable: false,
                                populators: {
                                    name: "createdFrom"
                                },
                            },
                            {
                                label: "ES_COMMON_CREATED_TO",
                                type: "date",
                                isMandatory: false,
                                disable: false,
                                populators: { 
                                    name: "createdTo",
                                    error: 'DATE_VALIDATION_MSG'
                                },
                                additionalValidation: {
                                    type: 'date',
                                    keys: {start: 'createdFrom', end: 'createdTo'}
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
                                label: "WORKS_BILL_NUMBER",
                                jsonPath: "musterRollNumber",
                                additionalCustomization:true 
                            },
                            {
                                label: "WORKS_PROJECT_NAME",
                                jsonPath: "additionalDetails.projectName",
                            },
                            {
                                label: "ES_COMMON_LOCATION",
                                jsonPath: "businessObject.additionalDetails.attendanceRegisterName",
                                additionalCustomization:false 
                            },
                            {
                                label: "ES_COMMON_CBO_NAME",
                                jsonPath: "additionalDetails.orgName"
                            },
                            {
                                label: "WORKS_BILL_TYPE",
                                jsonPath: "additionalDetails.contractId",
                            },
                            {
                                label: "CORE_COMMON_STATUS",
                                jsonPath: "musterRollStatus",
                                additionalCustomization:true
                            },
                            {
                                label: "EXP_BILL_AMOUNT",
                                jsonPath: "additionalDetails.amount",
                                additionalCustomization:true
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
      ]
}