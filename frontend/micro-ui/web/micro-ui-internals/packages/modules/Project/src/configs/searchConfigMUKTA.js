const searchConfigMUKTA = {
    "tenantId" : "pg",
    "moduleName" : "commonUiConfig",
    "SearchProjectConfig" : [
        {
            label : "WORKS_SEARCH_PROJECTS",
            type: 'search',
            "actionLabel": "WORKS_CREATE_NEW_PROJECT",
            "actionRole": "EMPLOYEE",
            "actionLink": "project/create-project",
            apiDetails: {
                serviceName: "/pms/project/v1/_search",
                requestParam: {
                    
                },
                requestBody: {
                    apiOperation: "SEARCH",
                    Projects: [
                        {
                            
                        }
                    ]
                },
                minParametersForSearchForm:1,
                masterName:"commonUiConfig",
                moduleName:"SearchProjectConfig",
                tableFormJsonPath:"requestParam",
                filterFormJsonPath:"requestBody.Projects[0]",
                searchFormJsonPath:"requestBody.Projects[0]",
            },
            sections : {
                search : {
                    uiConfig : {
                        headerStyle : null,
                        primaryLabel: 'ES_COMMON_SEARCH',
                        secondaryLabel: 'ES_COMMON_CLEAR_SEARCH',
                        minReqFields: 1,
                        showFormInstruction : "PROJECT_SELECT_ONE_PARAM_TO_SEARCH",
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
                                label: "PDF_STATIC_LABEL_ESTIMATE_WARD",
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
                                label: "WORKS_PROJECT_TYPE",
                                type: "dropdown",
                                isMandatory: false,
                                disable: false,
                                populators: {
                                name: "projectType",
                                optionsKey: "name",
                                optionsCustomStyle : {
                                    top : "2.3rem"
                                },
                                mdmsConfig: {
                                    masterName: "ProjectType",
                                    moduleName: "works",
                                    localePrefix: "COMMON_MASTERS"
                                }
                                },
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
                                    name: "name",
                                    error: `PROJECT_PATTERN_ERR_MSG`,
                                    validation: { pattern: /^[^\$\"<>?\\\\~`!@$%^()+={}\[\]*:;“”‘’]{1,50}$/i, minlength : 2 }
                                }
                            },
                            {
                                label:"WORKS_PROJECT_ID",
                                type: "text",
                                isMandatory: false,
                                disable: false,
                                populators: { 
                                    name: "projectNumber",
                                    error: `PROJECT_PATTERN_ERR_MSG`,
                                    validation: { pattern: /PR\/[0-9]+-[0-9]+\/[0-9]+\/[0-9]+/i, minlength : 2 }
                                }
                            },
                            {
                            label: "ES_COMMON_CREATED_FROM",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "createdFrom",
                            }
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
                                label: "WORKS_PROJECT_ID",
                                jsonPath: "projectNumber",
                                additionalCustomization:true
                            },
                            {
                                label: "WORKS_PROJECT_NAME",
                                jsonPath: "name",
                                additionalCustomization:true
                            },
                            {
                                label: "ES_COMMON_LOCATION",
                                jsonPath: "",
                                additionalCustomization:true
                            },
                            {
                                label: "PROJECT_ESTIMATED_COST_IN_RS",
                                jsonPath: "additionalDetails.estimatedCostInRs",
                                additionalCustomization:true
                            }
                        ],
                        enableGlobalSearch: false,
                        enableColumnSort: true,
                        resultsJsonPath: "Projects",
                    },
                    children: {},
                    show: true 
                }
            },
            additionalSections : {}
        }
    ]
}

export default searchConfigMUKTA;