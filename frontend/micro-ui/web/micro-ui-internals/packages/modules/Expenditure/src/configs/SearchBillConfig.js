export const SearchBillConfig = {
    "tenantId": "pg",
    "moduleName": "commonMuktaUiConfig",
    "SearchBillConfig":[
        {
            label : "Search Bill",
            type: 'search',
            apiDetails: {
                serviceName: "/pms/project/v1/_search",
                requestParam: {},
                requestBody: {},
                minParametersForSearchForm:1,
                masterName:"commonUiConfig",
                moduleName:"SearchBillConfig",
                tableFormJsonPath:"requestParam",
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
                        showFormInstruction : "",
                        defaultValues : {
                            projectNumber: "",
                            subProjectId: "",
                            name: "",
                            projectType: "",
                            startDate: "",
                            endDate: ""
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
                            {
                                label: "Project Name",
                                type: "text",
                                isMandatory: false,
                                disable: false,
                                preProcess : {
                                    convertStringToRegEx : ["populators.validation.pattern"]
                                },
                                populators: { 
                                    name: "name",
                                    error: `PROJECT_PATTERN_ERR_MSG`,
                                    validation: { pattern: /^[a-zA-Z0-9\/{ \/ .\- _$@#\'() } ]*$/i, minlength : 2 }
                                }
                            },
                            {
                                label:"Bill number",
                                type: "text",
                                isMandatory: false,
                                disable: false,
                                populators: { 
                                    name: "projectNumber",
                                    error: `Please enter valid bill number`,
                                    validation: { pattern: /^[a-zA-Z0-9\/{ \/ .\- _$@#\'() } ]*$/i, minlength : 2 }
                                }
                            },
                            {
                                label: "Status",
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
                                label: "Created From",
                                type: "date",
                                isMandatory: false,
                                disable: false,
                                populators: {
                                    name: "createdFrom"
                                },
                            },
                            {
                                label: "Created To",
                                type: "date",
                                isMandatory: false,
                                disable: false,
                                populators: { 
                                    name: "createdTo",
                                    error: 'DATE_VALIDATION_MSG'
                                },
                                // additionalValidation: {
                                //     type: 'date',
                                //     keys: {start: 'createdFrom', end: 'createdTo'}
                                // }
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
                                label: "Bill number",
                                jsonPath: "businessObject.musterRollNumber",
                                additionalCustomization:true 
                            },
                            {
                                label: "Project name",
                                jsonPath: "businessObject.additionalDetails.attendanceRegisterName",
                            },
                            {
                                label: "Location",
                                jsonPath: "businessObject.additionalDetails.attendanceRegisterName",
                                additionalCustomization:true 
                            },
                            {
                                label: "CBO name",
                                jsonPath: "businessObject"
                            },
                            {
                                label: "Bill Type",
                                jsonPath: "businessObject.additionalDetails.orgName",
                            },
                            {
                                label: "Status",
                                jsonPath: "businessObject.individualEntries"
                            },
                            {
                                label: "Bill amount (₹)",
                                jsonPath: "businessObject.serviceSla",
                                additionalCustomization:true
                            }
                        ],
                        enableGlobalSearch: false,
                        enableColumnSort: true,
                        resultsJsonPath: "Bills",
                    },
                    children: {},
                    show: true 
                }
            },
            additionalSections : {}
        }
      ]
}