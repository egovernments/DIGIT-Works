const searchConfig = () => {
    return {
        label : "Search Projects",
        type: 'search',
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
            jsonPathForReqBody: `requestBody.Projects[0]`,
            jsonPathForReqParam:`requestParam`,
            mandatoryFieldsInParam: {
                tenantId: "fetchTenantId"
            },
            mandatoryFieldsInBody: {
                tenantId: "fetchTenantId",
            },
            tableFormJsonPath:"",
            filterFormJsonPath:"",
            searchFormJsonPath:"",
            // queryNameJsonPath:"Projects"
        },
        sections : {
            search : {
                uiConfig : {
                    headerStyle : null,
                    primaryLabel: 'Search',
                    secondaryLabel: 'Clear Search',
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
                            label:"Project ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectNumber",
                            },
                        },
                        {
                            label: "Sub Project ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectNumber",
                            },
                        },
                        {
                          label: "Name of the Project",
                          type: "text",
                          isMandatory: false,
                          disable: false,
                          populators: { 
                              name: "name",
                          }
                        },
                        {
                          label: "Type Of Work",
                          type: "dropdown",
                          isMandatory: false,
                          disable: false,
                          populators: {
                            name: "projectType",
                            optionsKey: "code",
                            mdmsConfig: {
                              masterName: "ProjectType",
                              moduleName: "works",
                            }
                          },
                          preProcessfn: "getCode"
                        },
                        {
                          label: "Created from Date",
                          type: "date",
                          isMandatory: false,
                          disable: false,
                          populators: { 
                              name: "startDate"
                          },
                          preProcessfn: "convertDateToEpoch"
                        },
                        {
                            label: "Created to Date",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "endDate"
                            },
                            preProcessfn: "convertDateToEpoch"
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
                    defaultValues: {
                        offset: 0,
                        limit: 10,
                        sortOrder: "ASC",
                    },
                    columns: [
                        {
                            label: "WORKS_PRJ_SUB_ID",
                            jsonPath: "projectNumber",
                            redirectUrl: '/works-ui/employee/project/project-inbox-item'
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
                            jsonPath: "endDate",
                        },
                        {
                            label: "WORKS_PARENT_PROJECT_ID",
                            jsonPath: "parentId",
                        },
                        {
                            label: "WORKS_CREATED_BY",
                            jsonPath: "auditDetails.createdBy",
                        },
                        {
                            label: "WORKS_STATUS",
                            jsonPath: "status",
                        },
                        {
                            label: "WORKS_TOTAL_AMOUNT",
                            jsonPath: "totalAmount",
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