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
                        projectId: "",
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
                                name: "projectId"
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
            searchResult : {
                label : "",
                uiConfig: {
                    defaultValues: {
                        offset: 0,
                        limit: 10,
                        // sortBy: "department",
                        sortOrder: "ASC",
                    },
                    columns: [
                        {
                            label: "name",
                            jsonPath: "searchResult[0].name",
                            accessor: "name",
                            isLink: true,
                            redirectUrl: '/works-ui/employee/project/project-inbox-item'
                        },
                        {
                            label: "age",
                            jsonPath: "searchResult[0].age",
                            accessor: "age",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "gender",
                            jsonPath: "searchResult[0].age",
                            accessor: "gender",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "company",
                            jsonPath: "searchResult[0].company",
                            accessor: "company",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "email",
                            jsonPath: "searchResult[0].email",
                            accessor: "email",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "phone",
                            jsonPath: "searchResult[0].phone",
                            accessor: "phone",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "balance",
                            jsonPath: "searchResult[0].balance",
                            accessor: "balance",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "favoriteFruit",
                            jsonPath: "searchResult[0].favoriteFruit",
                            accessor: "favoriteFruit",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "eyeColor",
                            jsonPath: "searchResult[0].eyeColor",
                            accessor: "eyeColor",
                            isLink: false,
                            redirectUrl: ""
                        }
                    ],
                    enableGlobalSearch: false,
                    enableColumnSort: true
                },
                children : {},
                show : true
            }
        },
        additionalSections : {}
    }
}

export default searchConfig;