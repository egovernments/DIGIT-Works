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
                    minReqFields: 1,
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
                                name: "projectId",
                                validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }
                            },
                        },
                        {
                            label: "Sub Project ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "subProjectId",
                                validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }
                            },
                        },
                        {
                          label: "Name of the Project",
                          type: "text",
                          isMandatory: false,
                          disable: false,
                          populators: { 
                              name: "projectName",
                              validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }
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
                            label: "name",
                            jsonPath: "name",
                            redirectUrl: '/works-ui/employee/project/project-inbox-item'
                        },
                        {
                            label: "age",
                            jsonPath: "age"
                        },
                        {
                            label: "gender",
                            jsonPath: "gender",
                        },
                        {
                            label: "company",
                            jsonPath: "company",
                        },
                        {
                            label: "email",
                            jsonPath: "email",
                        },
                        {
                            label: "phone",
                            jsonPath: "phone",
                        },
                        {
                            label: "balance",
                            jsonPath: "balance",
                        },
                        {
                            label: "favoriteFruit",
                            jsonPath: "favoriteFruit",
                        },
                        {
                            label: "eyeColor",
                            jsonPath: "eyeColor",
                        }
                    ],
                    enableGlobalSearch: false,
                    enableColumnSort: true,
                },
                children: {},
                show: true //by default true. 
            }
        },
        additionalSections : {}
    }
}

export default searchConfig;