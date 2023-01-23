const inboxConfig = () => {
    return {
        pageHeader : "Search Projects",
        form : {
                type : "search", 
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
                    }
                },
                additionalSections : {
                    //Open for Extensions
                    //One can create a diff Parent card and add additional fields in it.
                }
        }
    }
}

export default inboxConfig;