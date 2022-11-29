import { useTranslation } from "react-i18next";

//mapping every user state to an id(aadhar for now)
export const initialTableState = {
  isLoading: false,
  rows: {
    "1111-1111-1111": {
      sno: "1",
      reg_id: "ID-1239-1312",
      aadhar: "1111-1111-1111",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    },
    "2222-2222-2222": {
      sno: "2",
      reg_id: "ID-1239-1312",
      aadhar: "2222-2222-2222",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    },
    "3333-3333-3333": {
      sno: "3",
      reg_id: "ID-1239-1312",
      aadhar: "3333-3333-3333",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    },
    "4444-4444-4444": {
      sno: "4",
      reg_id: "ID-1239-1312",
      aadhar: "4444-4444-4444",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    },
    "5555-5555-5555": {
      sno: "5",
      reg_id: "ID-1239-1312",
      aadhar: "5555-5555-5555",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    },
    "13333-3333-3333": {
      sno: "3",
      reg_id: "ID-1239-1312",
      aadhar: "13333-3333-3333",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    },
    "14444-4444-4444": {
      sno: "4",
      reg_id: "ID-1239-1312",
      aadhar: "14444-4444-4444",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    },
    "15555-5555-5555": {
      sno: "5",
      reg_id: "ID-1239-1312",
      aadhar: "15555-5555-5555",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    },
    "24444-4444-4444": {
      sno: "4",
      reg_id: "ID-1239-1312",
      aadhar: "14444-4444-4444",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    },
    "25555-5555-5555": {
      sno: "5",
      reg_id: "ID-1239-1312",
      aadhar: "15555-5555-5555",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    },
    "34444-4444-4444": {
      sno: "4",
      reg_id: "ID-1239-1312",
      aadhar: "14444-4444-4444",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    },
    "35555-5555-5555": {
      sno: "5",
      reg_id: "ID-1239-1312",
      aadhar: "15555-5555-5555",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    },
    total: {
      sno: "Total",
      type: "total",
      reg_id: "",
      aadhar: "",
      name_of_individual: "",
      guardian_name: "",
      attendence: [0, 0, 0, 0, 0, 0, 0],
    },
  },
};

const selectNextState = (state) => {
  if (state === "zero") return "half";
  else if (state === "half") return "full";
  else if (state === "full") return "zero";
};

const updateAttendenceCount = (state) => {
  let currentAttendence = [0, 0, 0, 0, 0, 0, 0];
  for (let index in currentAttendence) {
    for (let row of Object.keys(state.rows)) {
      let attendence = state.rows[row].attendence[index];
      if (attendence === "half") {
        currentAttendence[index] += 0.5;
      } else if (attendence === "full") {
        currentAttendence[index] += 1;
      }
    }
  }
  return currentAttendence;
};

const reducer = (state, action) => {
  console.log(action);
  switch (action.type) {
    case "attendence":
      const prevAttendence = action.state.row.attendence;
      prevAttendence[action.state.index] = selectNextState(action.state.row.attendence[action.state.index]);
      const obj = {
        [action.state.row.aadhar]: {
          ...action.state.row,
          attendence: prevAttendence,
        },
      };
      let updatedState = { ...state, rows: { ...state.rows, ...obj } };
      let updatedAttendenceCount = updateAttendenceCount(updatedState);
      const finalState = { ...state, rows: { ...state.rows, total: { ...state.rows.total, attendence: updatedAttendenceCount } } };
      console.log(finalState);
      return finalState;
    default:
      return state;
  }
};

export default reducer;
