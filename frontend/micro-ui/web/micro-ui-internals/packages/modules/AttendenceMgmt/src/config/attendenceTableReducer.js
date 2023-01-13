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
