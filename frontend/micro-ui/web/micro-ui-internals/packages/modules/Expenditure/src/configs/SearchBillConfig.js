export const SearchBillConfig = {
    "tenantId": "pg",
    "moduleName": "commonMuktaUiConfig",
    "SearchBillConfig":[
        {
            label : "EXP_SEARCH_BILL",
            type: 'search',
            apiDetails: {
                serviceName: "/wms/expense/_search",
                requestParam: {},
                requestBody: {
                    inbox: {
                        moduleSearchCriteria: {}
                    }
                },
                minParametersForSearchForm:1,
                masterName:"commonUiConfig",
                moduleName:"SearchBillConfig",
                tableFormJsonPath:"requestBody.inbox",
                filterFormJsonPath:"requestBody.inbox.moduleSearchCriteria",
                searchFormJsonPath:"requestBody.inbox.moduleSearchCriteria",
            },
            sections : {
                search : {
                    uiConfig : {
                        headerStyle : null,
                        primaryLabel: 'ES_COMMON_SEARCH',
                        secondaryLabel: 'ES_COMMON_CLEAR_SEARCH',
                        minReqFields: 1,
                        showFormInstruction : "BILL_SELECT_ONE_PARAM_TO_SEARCH",
                        formClassName:"custom-both-clear-search",
                        defaultValues : {
                            ward: "",
                            billType: "",
                            projectName: "",
                            billNumber: "",
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
                                    optionsKey: "i18nKey",
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
                                    validation: { pattern: "^[^\\$\"<>?\\\\~`!@$%^()+={}\\[\\]*:;“”‘’]{1,50}$", minlength : 2 }
                                }
                            },
                            {
                                label:"WORKS_BILL_NUMBER",
                                type: "text",
                                isMandatory: false,
                                disable: false,
                                populators: { 
                                    name: "billNumber",
                                    error: "ES_COMMON_BILL_PATTERN_ERR_MSG",
                                    validation: { pattern: "^[A-Za-z0-9\\/-]*$", minlength : 2 }
                                }
                            },
                            {
                                label: "CORE_COMMON_STATUS",
                                type: "apidropdown",
                                isMandatory: false,
                                disable: false,
                                populators: {
                                  optionsCustomStyle: {
                                    top: "2.3rem",
                                  },
                                  name: "status",
                                  optionsKey: "i18nKey",
                                  allowMultiSelect: false,
                                  masterName: "commonUiConfig",
                                  moduleName: "SearchBillConfig",
                                  customfn: "populateReqCriteria",
                                }
                            },
                            {
                                label: "ES_COMMON_CREATED_FROM",
                                type: "date",
                                isMandatory: false,
                                disable: false,
                                key : "createdFrom",
                                preProcess : {
                                    updateDependent : ["populators.max"]
                                },
                                populators: {
                                    name: "createdFrom",
                                    max : "currentDate"
                                },
                            },
                            {
                                label: "ES_COMMON_CREATED_TO",
                                type: "date",
                                isMandatory: false,
                                disable: false,
                                key : "createdTo",
                                preProcess : {
                                    updateDependent : ["populators.max"]
                                },
                                populators: { 
                                    name: "createdTo",
                                    error: 'DATE_VALIDATION_MSG',
                                    max : "currentDate"
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
                                jsonPath: "",
                                additionalCustomization:true 
                            },
                            {
                                label: "ES_COMMON_CBO_NAME",
                                jsonPath: "additionalDetails.orgName"
                            },
                            {
                                label: "WORKS_BILL_TYPE",
                                jsonPath: "additionalDetails.billType",
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