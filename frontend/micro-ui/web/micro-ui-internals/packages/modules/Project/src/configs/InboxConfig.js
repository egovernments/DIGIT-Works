const inboxConfig = (t) => {
    return {
        label : "Project Inbox",
        type : "inbox", 
        //use classname as 'search' for search view
        //use classname as 'inbox' for inbox view
        //inbox view is by default
        sections : {
            search : {
                label : "",
                uiConfig : { //UIConfig structure can vary based on components.

                },
                children : {},
                show : true //by default true. 
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