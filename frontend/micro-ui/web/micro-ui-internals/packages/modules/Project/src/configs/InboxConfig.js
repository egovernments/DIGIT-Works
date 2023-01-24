const InboxConfig = (t) => {
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

export default InboxConfig;