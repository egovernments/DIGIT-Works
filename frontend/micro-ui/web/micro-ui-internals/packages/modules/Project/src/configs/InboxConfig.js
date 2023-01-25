const inboxConfig = (t) => {
    return {
        label : "Inbox",
        type : "inbox", 
        //use classname as 'search' for search view
        //use classname as 'inbox' for inbox view
        //inbox view is by default
        sections : {
            search : {
                uiConfig : {
                    headerStyle : null,
                    primaryLabel: 'Search',
                    secondaryLabel: 'Clear Search',
                    defaultValues : {
                        projectId: "",
                        department: "",
                        workType: ""
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
                            text: "ACTION_TEST_PROJECTS",
                            url: `/employee/contracts/create-contract`,
                            businessService: "WORKS",
                            roles: [],
                        },
                        {
                            text: "ACTION_TEST_PROJECTS",
                            url: `/employee/contracts/create-contract`,
                            businessService: "WORKS",
                            roles: [],
                        },
                        {
                            text: "ACTION_TEST_PROJECTS",
                            url: `/employee/contracts/create-contract`,
                            businessService: "WORKS",
                            roles: [],
                        },
                        {
                            text: "ACTION_TEST_PROJECTS",
                            url: `/employee/contracts/create-contract`,
                            businessService: "WORKS",
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
                    headerStyle : null,
                    buttonLabel: 'Filter',
                    linkLabel: 'Clear Search'
                },
                label : "Filter",
                children : {
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
                        }
                    ]
                },
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