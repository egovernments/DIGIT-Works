const searchConfig = () => {
    return {
        header : "Search Projects",
        visible: true,
        type: 'search',
        children : {
            search: {
                header : "",
                visible: true,
                type: "",
                children : {}
            },
            links: {
                header : "",
                visible: false,
                type: "",
                children : {}
            },
            filter: {
                header : "",
                visible: true,
                type: "",
                children : {}
            },
            searchResult: {
                header : "",
                visible: true,
                type: "",
                children : {}
            }
        }
    }
}

export default searchConfig;