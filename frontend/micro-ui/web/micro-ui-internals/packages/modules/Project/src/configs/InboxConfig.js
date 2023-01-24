const InboxSearchComposerConfig = () => {
    return {
        pageHeader : "Search Projects",
        form : {
                type : "inbox", 
                //use classname as 'search' for search view
                //use classname as 'inobx' for inbox view
                //inbox view is by default
                sections : {
                    search : {
                        fields : []
                    },
                    links : {
                        fields : []
                    },
                    filter : {
                        fields : []
                    },
                    searchResult : {
                        fields : []
                        //need a list of columns and their jsonPaths
                        //config for calling the api
                        //if it's an inbox by default we need to show some data, if it's a search screen
                        //then by default an empty card should be displayed
                    }
                },
                additionalSections : {
                    //Open for Extensions
                    //One can create a diff Parent card and add additional fields in it.
                }
        }
    }
}

export default InboxSearchComposerConfig;