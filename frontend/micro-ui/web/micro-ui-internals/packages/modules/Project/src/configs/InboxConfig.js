const inboxConfig = () => {
    return {
        label : "Inbox",
        type : "inbox", 
        apiDetails: {
            serviceName: "",
            requestParam: {
                limit:10,
                offset:0,
                tenantId: Digit.ULBService.getCurrentTenantId(),
            },
            requestBody: {
                apiOperation: "FILTER",
                Projects: [
                    {
                        tenantId: Digit.ULBService.getCurrentTenantId()
                    }
                ]
            },
            jsonPathForReqBody: `requestBody.Projects[0]`,
            jsonPathForReqParam:`requestParam`,
            preProcessResponese: (data) =>  data,
            mandatoryFieldsInParam: {
                tenantId: Digit.ULBService.getCurrentTenantId(),
            },
            mandatoryFieldsInBody: {
                tenantId: Digit.ULBService.getCurrentTenantId(),
            },
            //Note -> The above mandatory fields should not be dynamic
            //If they are dynamic they should be part of the reducer state
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
                        department: "",
                        workType: ""
                    },
                    fields : [
                        {
                            label:"Project ID",
                            type: "component",
                            component : "SubProjectDetailsTable",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectId",
                            },
                        },
                        {
                            label: "Department",
                            type: "dropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                              name: "department",
                              optionsKey: "name",
                              mdmsConfig: {
                                masterName: "Department",
                                moduleName: "common-masters",
                                localePrefix: "WORKS",
                              }
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
                        }
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
                            text: "WORKS_CREATE_PROJECT",
                            url: `/employee/project/create-project`,
                            roles: [],
                        }
                    ],
                    label : "ACTION_TEST_PROJECT_INBOX",
                    logoIcon : { //Pass the name of the Icon Component as String here and map it in the InboxSearchLinks Component   
                        component : "PropertyHouse",
                        customClass : "inbox-search-icon--projects"         
                    }
                },
                children : {},
                show : true //by default true. 
            },
            filter : {
                uiConfig : {
                    type : 'filter',
                    headerStyle : null,
                    primaryLabel: 'Filter',
                    secondaryLabel: '',
                    defaultValues : {
                        projectId: "",
                        department: "",
                        workType: ""
                    },
                    fields : [
                        {
                            label:"WORKS_PROJECT_CREATED_FROM_DATE",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectFromDate"
                            },
                        },
                        {
                            label:"WORKS_PROJECT_CREATED_TO_DATE",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectToDate"
                            },
                        },
                        {
                            label: "WORKS_CREATED_BY",
                            type: "dropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                              name: "createdBy",
                              optionsKey: "name",
                              mdmsConfig: {
                                masterName: "TypeOfWork",
                                moduleName: "works",
                                localePrefix: "WORKS",
                              }
                            }
                        },
                        {
                            label: "WORKS_STATUS",
                            type: "dropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                              name: "status",
                              optionsKey: "name",
                              mdmsConfig: {
                                masterName: "TypeOfWork",
                                moduleName: "works",
                                localePrefix: "WORKS",
                              }
                            }
                        }
                    ]
                },
                label : "Filter",
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
                children : {},
                show : true //by default true. 
            }
        },
        additionalSections : {
            //Open for Extensions
            //One can create a diff Parent card and add additional fields in it.
        }
    }
}

export default inboxConfig;