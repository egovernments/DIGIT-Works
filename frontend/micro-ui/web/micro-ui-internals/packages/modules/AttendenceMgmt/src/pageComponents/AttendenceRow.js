import React from 'react'

const AttendenceRow = () => {
    const sampleUsersState = [
        {
            "name": "Sample Name",
            "aadhar": "9878-9378-2827",
            "guardian": "Sample guardian",
            "state": [
                {
                    "key": 0,
                    "day": "mon",
                    "attendence": "half"
                },
                {
                    "key": 1,
                    "day": "tue",
                    "attendence": "full"
                },
                {
                    "key": 2,
                    "day": "wed",
                    "attendence": "zero"
                },
                {
                    "key": 3,
                    "day": "thu",
                    "attendence": "zero"
                },
                {
                    "key": 4,
                    "day": "fri",
                    "attendence": "half"
                },
                {
                    "key": 5,
                    "day": "sat",
                    "attendence": "full"
                },
                {
                    "key": 6,
                    "day": "sun",
                    "attendence": "full"
                }
            ]
        }
    ]

    // const markup = sampleUsersState?.map(user => {
    //     user.state.map((state, index) => {
    //         return (
    //             <div class="radio-outer-circle unselected">
    //                 <div class="radio-full-inner-circle">

    //                 </div>
    //             </div>
    //         )
    //     })
    // })
 
  return (
      <div class="modern-radio-container" style={{"height":"50vh"}}>
        {/* for every user return the row of attendence circles */}
        
        {sampleUsersState?.map(user=>{
            return user.state.map((state,index)=> { 
                return(
                <div class="radio-outer-circle unselected">
                    <div class="radio-full-inner-circle">
                    
                    </div>
                </div>
            )})
        })}
          {/* <div class="radio-outer-circle unselected"  >
              <div class="radio-full-inner-circle">

              </div>
          </div>
          <div class="radio-outer-circle unselected"  >
              <div class="radio-half-inner-circle">

              </div>
          </div>
          <div class="radio-outer-circle unselected"  >
              <div class="radio-half-inner-circle">

              </div>
          </div>
          <div class="radio-outer-circle unselected"  >
              <div class="radio-half-inner-circle">

              </div>
          </div>
          <div class="radio-outer-circle unselected"  >
              <div class="radio-half-inner-circle">

              </div>
          </div>
          <div class="radio-outer-circle unselected"  >
              <div class="radio-half-inner-circle">

              </div>
          </div>

          <div class="radio-outer-circle unselected"  >
              <div class="radio-half-inner-circle">

              </div>
          </div> */}

      </div>
  )
}

export default AttendenceRow