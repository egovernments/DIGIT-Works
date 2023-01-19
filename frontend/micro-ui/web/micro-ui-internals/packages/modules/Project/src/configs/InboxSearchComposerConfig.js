const InboxSearchComposerConfig = () => {
    return {
        pageHeader : "Search Projects",
        form : {
                type : "inbox",
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

export default InboxSearchComposerConfig;