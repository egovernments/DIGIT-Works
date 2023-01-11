//mapping every user state to an id(aadhar for now)
export const initialTableState = {
  isLoading: false,
  rows: {
    "1111-1111-1111": {
      sno: "1",
      reg_id: "ID-1239-1312",
      aadhar: "1111-1111-1111",
      name_of_individual: "Rashmi",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
      actualWorkingDays : 4,
      amountInRs : 3000,
      modifiedWorkingDays : 5,
      skill : 'Carpenter',
      modifiedAmountInRs : 4000,
      bankAccountDetails : {
        accountNo : '1232323432423',
        ifscCode : 'SBIN123322333'
      },
      aadharNumber : '12213213'
    },
    "2222-2222-2222": {
      sno: "2",
      reg_id: "ID-1239-1312",
      aadhar: "2222-2222-2222",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
      actualWorkingDays : 4,
      amountInRs : 3000,
      modifiedWorkingDays : 5,
      skill : 'Mason',
      modifiedAmountInRs : 4000,
      bankAccountDetails : {
        accountNo : '1232323432423',
        ifscCode : 'SBIN123322333'
      },
      aadharNumber : '12213213'
    },
    "3333-3333-3333": {
      sno: "3",
      reg_id: "ID-1239-1312",
      aadhar: "3333-3333-3333",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
      actualWorkingDays : 4,
      skill : 'Carpenter',
      amountInRs : 3000,
      modifiedWorkingDays : 5,
      modifiedAmountInRs : 4000,
      bankAccountDetails : {
        accountNo : '1232323432423',
        ifscCode : 'SBIN123322333'
      },
      aadharNumber : '12213213'
    },
    "4444-4444-4444": {
      sno: "4",
      reg_id: "ID-1239-1312",
      aadhar: "4444-4444-4444",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
      actualWorkingDays : 4,
      skill : 'Electrician',
      amountInRs : 3000,
      modifiedWorkingDays : 5,
      modifiedAmountInRs : 4000,
      bankAccountDetails : {
        accountNo : '1232323432423',
        ifscCode : 'SBIN123322333'
      },
      aadharNumber : '12213213'
    },
    "5555-5555-5555": {
      sno: "5",
      reg_id: "ID-1239-1312",
      aadhar: "5555-5555-5555",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
      actualWorkingDays : 4,
      amountInRs : 3000,
      skill : 'Mason',
      modifiedWorkingDays : 5,
      modifiedAmountInRs : 4000,
      bankAccountDetails : {
        accountNo : '1232323432423',
        ifscCode : 'SBIN123322333'
      },
      aadharNumber : '12213213'
    },
    "13333-3333-3333": {
      sno: "6",
      reg_id: "ID-1239-1312",
      aadhar: "13333-3333-3333",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
      actualWorkingDays : 4,
      skill : 'Labour',
      amountInRs : 3000,
      modifiedWorkingDays : 5,
      modifiedAmountInRs : 4000,
      bankAccountDetails : {
        accountNo : '1232323432423',
        ifscCode : 'SBIN123322333'
      },
      aadharNumber : '12213213'
    },
    "14444-4444-4444": {
      sno: "7",
      reg_id: "ID-1239-1312",
      aadhar: "14444-4444-4444",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
      actualWorkingDays : 4,
      skill : 'Carpenter',
      amountInRs : 3000,
      modifiedWorkingDays : 5,
      modifiedAmountInRs : 4000,
      bankAccountDetails : {
        accountNo : '1232323432423',
        ifscCode : 'SBIN123322333'
      },
      aadharNumber : '12213213'
    },
    "15555-5555-5555": {
      sno: "8",
      reg_id: "ID-1239-1312",
      aadhar: "15555-5555-5555",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
      actualWorkingDays : 4,
      skill : 'Mason',
      amountInRs : 3000,
      modifiedWorkingDays : 5,
      modifiedAmountInRs : 4000,
      bankAccountDetails : {
        accountNo : '1232323432423',
        ifscCode : 'SBIN123322333'
      },
      aadharNumber : '12213213'
    },
    "24444-4444-4444": {
      sno: "9",
      reg_id: "ID-1239-1312",
      aadhar: "24444-4444-4444",
      name_of_individual: "Rashmi Ranjan",
      guardian_name: "Rashmi Ranjan",
      attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
      actualWorkingDays : 4,
      skill : 'Mason',
      amountInRs : 3000,
      modifiedWorkingDays : 5,
      modifiedAmountInRs : 4000,
      bankAccountDetails : {
        accountNo : '1232323432423',
        ifscCode : 'SBIN123322333'
      },
      aadharNumber : '12213213'
    },
    // "25555-5555-5555": {
    //   sno: "10",
    //   reg_id: "ID-1239-1312",
    //   aadhar: "25555-5555-5555",
    //   name_of_individual: "Rashmi Ranjan",
    //   guardian_name: "Rashmi Ranjan",
    //   attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    //   actualWorkingDays : 4,
    //   amountInRs : 3000,
    //   modifiedWorkingDays : 5,
    //   modifiedAmountInRs : 4000,
    //   bankAccountDetails : {
    //     accountNo : '12323',
    //     ifscCode : '123213'
    //   },
    //   aadharNumber : '12213213'
    // },
    // "34444-4444-4444": {
    //   sno: "11",
    //   reg_id: "ID-1239-1312",
    //   aadhar: "34444-4444-4444",
    //   name_of_individual: "Rashmi Ranjan",
    //   guardian_name: "Rashmi Ranjan",
    //   attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    //   actualWorkingDays : 4,
    //   amountInRs : 3000,
    //   modifiedWorkingDays : 5,
    //   modifiedAmountInRs : 4000,
    //   bankAccountDetails : {
    //     accountNo : '12323',
    //     ifscCode : '123213'
    //   },
    //   aadharNumber : '12213213'
    // },
    // "35555-5555-5555": {
    //   sno: "12",
    //   reg_id: "ID-1239-1312",
    //   aadhar: "35555-5555-5555",
    //   name_of_individual: "Rashmi Ranjan",
    //   guardian_name: "Rashmi Ranjan",
    //   attendence: ["zero", "zero", "zero", "zero", "zero", "zero", "zero"],
    //   actualWorkingDays : 4,
    //   amountInRs : 3000,
    //   modifiedWorkingDays : 5,
    //   modifiedAmountInRs : 4000,
    //   bankAccountDetails : {
    //     accountNo : '12323',
    //     ifscCode : '123213'
    //   },
    //   aadharNumber : '12213213'
    // },
    total: {
      sno: "ATM_TOTAL",
      type: "total",
      reg_id: "DNR", //do not render
      aadhar: "DNR", //do not render
      name_of_individual: "DNR", //do not render
      guardian_name: "DNR", //do not render
      attendence: [0, 0, 0, 0, 0, 0, 0],
      actualWorkingDays : "36",
      skill : "",
      amountInRs : "27000",
      modifiedWorkingDays : "",
      modifiedAmountInRs : "36000",
      bankAccountDetails : "",
      aadharNumber : ""
    },
  },
};

const selectNextState = (state) => {
  if (state === "zero") return "half";
  else if (state === "half") return "full";
  else if (state === "full") return "zero";
};

const updateAttendenceCount = (state) => {
  let currentAttendence = { Sun: 0, Sat: 0, Fri: 0, Thu: 0, Wed: 0, Tue: 0, Mon: 0 }
  for (let day of Object.keys(currentAttendence)) {
    for (let row of Object.keys(state)) {
      let attendence = state[row].attendence[day];
      if (attendence === "half") {
        currentAttendence[day] += 0.5;
      } else if (attendence === "full") {
        currentAttendence[day] += 1;
      }
    }
  }
  return currentAttendence;
};

const updateAmtAndWorkingDays = (state) => {
  let totalAmount = 0
  let totalActualWorkingDays = 0
  for (let row of Object.keys(state)) {
    if(row !== 'total') {
      totalAmount += state[row].amount
      totalActualWorkingDays += state[row].actualWorkingDays
    }    
  }
  return {totalAmount, totalActualWorkingDays};
};

const reducer = (state, action) => {
  switch (action.type) {
    case "attendanceTotal":
      const prevAttendence = action.state.row.attendence;
      prevAttendence[action.state.index] = selectNextState(action.state.row.attendence[action.state.index]);
      const obj = {
        [action.state.row.id]: {
          ...action.state.row,
          attendence: prevAttendence,
        },
      };
      let updatedState = { ...state, ...obj };;
      let attendanceTotalCount = updateAttendenceCount(updatedState);
      return { ...state, total: { ...state.total, attendence: attendanceTotalCount }  };
    
    case "initialTotal":
      console.log('initialTotal');
      const initialTotalCount = updateAttendenceCount(state);
      const { totalAmount, totalActualWorkingDays } = updateAmtAndWorkingDays(state)
      return { ...state, total: { ...state.total, attendence: initialTotalCount, amount: totalAmount, actualWorkingDays: totalActualWorkingDays}  };

    default:
      return state;
  }
};

export default reducer;
