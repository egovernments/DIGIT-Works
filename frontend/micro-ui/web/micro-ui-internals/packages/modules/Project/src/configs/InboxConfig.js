const inboxConfig = (t) => {
    return {
        label : "Search Projects",
        type : "inbox", 
        //use classname as 'search' for search view
        //use classname as 'inbox' for inbox view
        //inbox view is by default
        sections : {
            search : {
                uiConfig : {
                    headerStyle : null,
                    buttonLabel: 'Search',
                    linkLabel: 'Clear Search'
                },
                label : "",
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
                show : true
            },
            links : {
                uiConfig : {
                    links : [
                        {
                            label: "ACTION_TEST_PROJECTS",
                            link: `/employee/contracts/create-contract`,
                            businessService: "WORKS",
                        }
                    ],            
                },
                label : "Projects",
                children : {},
                show : true //by default true. 
            },
            filter : {
                label : "",
                uiConfig : {
                    
                },
                children : {},
                show : true //by default true. 
            },
            searchResult : {
                label : "",
                uiConfig : {
                    
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