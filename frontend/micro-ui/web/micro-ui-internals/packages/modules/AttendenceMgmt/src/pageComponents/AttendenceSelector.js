import React from 'react'

const AttendenceSelector = (state) => {
    const classSelector = (state) => {
        switch (state) {
            case "half":
                return ["radio-outer-circle selected", "radio-half-inner-circle"]
            case "full":
                return ["radio-outer-circle selected", "radio-full-inner-circle"]

            default:
                return ["radio-outer-circle unselected", ""]
        }
    }

  return (
      <div className="modern-radio-container">
          <div className={`${classSelector(state.attendence)?.[0]}`}>
              <div className={`${classSelector(state.attendence)?.[1]}`}>
              </div>
          </div>
      </div>
  )
}

export default AttendenceSelector