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

                },
                children : {},
                show : true //by default true. 
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