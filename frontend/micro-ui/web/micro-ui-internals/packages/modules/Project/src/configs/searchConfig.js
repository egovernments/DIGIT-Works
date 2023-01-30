const searchConfig = () => {
    return {
        label : "Search Projects",
        type: 'search',
        apiDetails: {
            serviceName: "/pms/project/v1/_search",
            requestParam: {
                tenantId: Digit.ULBService.getCurrentTenantId(),
                limit: 5,
                offset: 0
            },
            requestBody: {
                apiOperation: "SEARCH",
                Projects: [
                    {
                        tenantId: Digit.ULBService.getCurrentTenantId()
                    }
                ]
            },
            jsonPath: `Properties[0].address`
        },
        sections : {
            search : {
                uiConfig : {
                    headerStyle : null,
                    primaryLabel: 'Search',
                    secondaryLabel: 'Clear Search',
                    defaultValues : {
                        projectNumber: "",
                        subProjectId: "",
                        projectName: "",
                        workType: "",
                        createdFromDate: "",
                        createdToDate: ""
                    },
                    fields : [
                        {
                            label:"Project ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectNumber"
                            },
                        },
                        {
                            label: "Sub Project ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "subProjectId"
                            },
                        },
                        {
                          label: "Name of the Project",
                          type: "text",
                          isMandatory: false,
                          disable: false,
                          populators: { 
                              name: "projectName"
                          }
                        },
                        {
                          label: "Type Of Work",
                          type: "dropdown",
                          isMandatory: false,
                          disable: false,
                          populators: {
                            name: "workType",
                            optionsKey: "name",
                            mdmsConfig: {
                              masterName: "TypeOfWork",
                              moduleName: "works",
                              localePrefix: "WORKS",
                            }
                          }
                        },
                        {
                          label: "Created from Date",
                          type: "date",
                          isMandatory: false,
                          disable: false,
                          populators: { 
                              name: "createdFromDate"
                          }
                        },
                        {
                            label: "Created to Date",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "createdToDate"
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
                    defaultValues: {
                        offset: 0,
                        limit: 10,
                        // sortBy: "department",
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
                            jsonPath: "gender",
                            translate:true,
                            prefix:"COMMON_MASTERS_DEPARTMENT_",
                        },
                        {
                            label: "WORKS_PROJECT_TYPE",
                            jsonPath: "projectType",
                        },
                        {
                            label: "WORKS_SUB_PROJECT_TYPE",
                            jsonPath: "projectSubTyp",
                        },
                        {
                            label: "WORKS_WORK_NATURE",
                            jsonPath: "endDate",
                        },
                        {
                            label: "WORKS_PARENT_PROJECT_ID",
                            jsonPath: "parent",
                        },
                        {
                            label: "WORKS_CREATED_BY",
                            jsonPath: "auditDetails.createdBy",
                        },
                        {
                            label: "WORKS_STATUS",
                            jsonPath: "rowVersion",
                        },
                        {
                            label: "WORKS_TOTAL_AMOUNT",
                            jsonPath: "startDate",
                        }
                    ],
                    enableGlobalSearch: false,
                    enableColumnSort: true,
                    resultsJsonPath: "Projects",
                },
                children: {},
                show: true //by default true. 
            }
        },
        additionalSections : {}
    }
}

export default searchConfig;