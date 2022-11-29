import { useTranslation } from "react-i18next";

//mapping every user state to an id(aadhar for now)
export const initialTableState = {
    isLoading: false,
    rows: {
        "1111-1111-1111": {
            name: "Piyush HarjitPal",
            guardian: "Harijitpal",
            aadhar: "1111-1111-1111",
            skill: "Unskilled",
            attendence: ['zero', 'zero', 'zero', 'zero', 'zero', 'zero', 'zero',]
        },
        "2222-2222-2222": {
            name: "Nipun Arora",
            guardian: "Harijitpal",
            aadhar: "2222-2222-2222",
            skill: "Skill 1",
            attendence: ['zero', 'zero', 'zero', 'zero', 'zero', 'zero', 'zero',]
        },
        "3333-3333-3333": {
            name: "Chandra",
            guardian: "Harijitpal",
            aadhar: "3333-3333-3333",
            skill: "Skill 2",
            attendence: ['zero', 'zero', 'zero', 'zero', 'zero', 'zero', 'zero',]
        },
        "4444-4444-4444": {
            name: "Kishore",
            guardian: "Harijitpal",
            aadhar: "4444-4444-4444",
            skill: "Skill 3",
            attendence: ['zero', 'zero', 'zero', 'zero', 'zero', 'zero', 'zero',]
        },
        "5555-5555-5555": {
            name: "Kumar",
            guardian: "Harijitpal",
            aadhar: "5555-5555-5555",
            skill: "Skill 2",
            attendence: ['zero', 'zero', 'zero', 'zero', 'zero', 'zero', 'zero',]
        }
    }
};

const selectNextState = (state) => {
    if (state==="zero") return "half"
    else if(state==="half") return "full"
    else if(state==="full") return "zero"
}
const reducer = (state, action) => {
    switch (action.type) {
        case "attendence":
            const prevAttendence = action.state.row.attendence;
            prevAttendence[action.state.index] = selectNextState(action.state.row.attendence[action.state.index])
            const obj ={
               [action.state.row.aadhar]:{
                ...action.state.row,
                attendence: prevAttendence
               }
            }
            const finalState = { ...state, rows: { ...state.rows, ...obj } } 
            return finalState;
        case "skill":
            const newState = {
                [action.row.aadhar]: {
                    ...action.row,
                    skill:action.selectedSkill
                }
            }
            const updatedState = { ...state, rows: { ...state.rows, ...newState } }
            return updatedState
        default:
            return state;
    }
}

export default reducer;
