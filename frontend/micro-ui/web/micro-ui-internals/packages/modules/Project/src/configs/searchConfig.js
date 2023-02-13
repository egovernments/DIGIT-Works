const searchConfig = () => {
    return {
        label : "WORKS_SEARCH_PROJECT",
        type: 'search',
        postProcessResult:true,
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
                    primaryLabel: 'ACTION_TEST_SEARCH',
                    secondaryLabel: 'CLEAR_SEARCH_LINk',
                    minReqFields: 1,
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
                            label:"WORKS_PROJECT_ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectNumber",
                                error: `PROJECT_PATTERN_ERR_MSG`,
                                validation: { pattern: /^[a-z0-9\/-]*$/i, minlength : 2 }
                            }
                        },
                        {
                            label: "PROJECT_PRJ_SUB_ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectNumber",
                                error: `PROJECT_PATTERN_ERR_MSG`,
                                validation: { pattern: /^[a-z0-9\/-]*$/i, minlength : 2 }
                            }
                        },
                        {
                          label: "PROJECT_NAME",
                          type: "text",
                          isMandatory: false,
                          disable: false,
                          populators: { 
                              name: "name",
                              error: `PROJECT_PATTERN_ERR_MSG`,
                              validation: { pattern: /^[a-z0-9\/-@#]*$/i, minlength : 2 }
                          }
                        },
                        {
                          label: "WORKS_PROJECT_TYPE",
                          type: "dropdown",
                          isMandatory: false,
                          disable: false,
                          populators: {
                            name: "projectType",
                            optionsKey: "code",
                            mdmsConfig: {
                              masterName: "ProjectType",
                              moduleName: "works",
                              localePrefix: "ES_COMMON"
                            }
                          },
                        },
                        {
                          label: "CREATED_FROM_DATE",
                          type: "date",
                          isMandatory: false,
                          disable: false,
                          populators: { 
                              name: "startDate",
                          }
                        },
                        {
                            label: "CREATED_TO_DATE",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "endDate",
                                error: 'DATE_VALIDATION_MSG'
                            },
                            additionalValidation: {
                                type: 'date',
                                keys: {start: 'startDate', end: 'endDate'}
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
                            label: "WORKS_PRJ_SUB_ID",
                            jsonPath: "projectNumber",
                            additionalCustomization:true
                        },
                        {
                            label: "WORKS_PROJECT_NAME",
                            jsonPath: "name"
                        },
                        {
                            label: "PROJECT_OWNING_DEPT",
                            jsonPath: "department",
                            translate:true,
                            prefix:"COMMON_MASTERS_DEPARTMENT_",
                        },
                        {
                            label: "WORKS_PROJECT_TYPE",
                            jsonPath: "projectType",
                        },
                        {
                            label: "WORKS_SUB_PROJECT_TYPE",
                            jsonPath: "projectSubType",
                        },
                        {
                            label: "WORKS_WORK_NATURE",
                            jsonPath: "natureOfWork",
                        },
                        {
                            label: "WORKS_PARENT_PROJECT_ID",
                            jsonPath: "ancestors[0].projectNumber",
                        },
                        {
                            label: "WORKS_CREATED_BY",
                            jsonPath: "createdBy",
                        },
                        {
                            label: "WORKS_STATUS",
                            jsonPath: "status",
                        },
                        {
                            label: "WORKS_TOTAL_AMOUNT",
                            jsonPath: "additionalDetails.estimatedCostInRs",
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
}

export default searchConfig;