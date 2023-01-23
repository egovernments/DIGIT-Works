import { PropertyHouse } from "@egovernments/digit-ui-react-components";

const InboxSearchComposerConfig = (t) => {
    return {
        pageHeader : "Search Projects", //change this to label
        type : "search", 
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
                            text: t("ACTION_TEST_PROJECTS"),  //transaplation on component label //make key names more intuitive
                            link: `/${window?.contextPath}/employee/contracts/create-contract`,
                            businessService: "WORKS",
                            roles: [], // handle roles inside the component
                        }
                    ],
                    logoIcon : PropertyHouse, //import this in component. 
                    headerText : "Projects",
                },
                children : {},
                show : true //by default true. 
            },
            filter : {
                uiConfig : {
                    
                },
                children : {},
                show : true //by default true. 
            },
            searchResult : {
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

export default InboxSearchComposerConfig;