export const initialInboxState = {
    searchForm:{

    },
    filterForm:{

    },
    tableForm:{

    } 
};

const reducer = (state, action) => {
    switch (action.type) {
        case "searchForm":
            console.log('action SEARCH', action);
            return {...state, searchForm: action.state};
        case "filterForm":
            return state;
        case "tableForm":
            const updatedTableState = action.state
            return { ...state, tableForm: updatedTableState };
        default:
            return state;
    }
}

export default reducer;
