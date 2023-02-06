export const initialInboxState = {
    searchForm:{

    },
    filterForm:{

    },
    tableForm:{
        limit: 10,
        offset: 0,
    } 
};

const reducer = (state, action) => {
    switch (action.type) {
        case "searchForm":
            const {state:updatedSearchState} = action 
            return {...state, searchForm: {...state.searchForm,...updatedSearchState}};
        case "filterForm":
            return state;
        case "tableForm":
            const updatedTableState = action.state
            return { ...state, tableForm: {...state.tableForm,...updatedTableState} };
        case "clearSearchForm":
            return {...state,searchForm:action.state}
        default:
            return state;
    }
}

export default reducer;
