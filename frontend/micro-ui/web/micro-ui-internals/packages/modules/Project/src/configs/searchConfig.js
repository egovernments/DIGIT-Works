const searchConfig = () => {
    return {
        label : "Search Projects",
        type: 'search',
        sections : {
            search : {
                uiConfig : {
                    headerStyle : null,
                },
                label : "",
                children : {},
                show : true
            },
            links : {
                uiConfig : {},
                label : "",
                children : {},
                show : false 
            },
            filter : {
                uiConfig : {},
                label : "",
                children : {},
                show : false 
            },
            searchResult : {
                uiConfig : {},
                label : "",
                children : {},
                show : true
            }
        },
        additionalSections : {}
    }
}

export default searchConfig;