


export const initialTableState = {
    searchForm:{

    },
    filterForm:{

    },
    tableForm:{

    } 
};
/*
click on search -> come to reducer with latest state -> call API -> set table data based on response
*/

const reducer = async (state, action) => {
    switch (action.type) {
        case "searchForm":
            console.log('action SEARCH', action);
            return state;
        case "filterForm":
            return state;
        case "tableForm":
            const updatedTableState = action.state
            console.log('updatedTableState', updatedTableState);
            return { ...state, tableForm: updatedTableState };
        default:
            return state;
    }
}

export default reducer;
