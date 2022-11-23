import React,{Fragment,useState,useMemo,useRef} from 'react'
import cloneDeep from "lodash/cloneDeep";
const AttendenceTable = ({ initialUserState }) => {
    
    const [userState,setUserState] = useState(initialUserState)
    console.log(userState);
    const nextState = (state) => {
        switch (state) {
            case "half":
                return "full"
                
            case "full":
                return "zero"
            
            case "zero":
                return "half"

            default:
                return "zero";
        }
    }
    const updateState = (idxOut,idxIn) => {
        console.log(idxOut,idxIn);
        
        //const updatedStateObj = userState
        const updatedStateObj = cloneDeep(userState)

        updatedStateObj[idxOut].state[idxIn].attendence = nextState(updatedStateObj[idxOut].state[idxIn].attendence)
        setUserState(updatedStateObj)
    }
    
    const classSelector = (state) => {
        
        switch (state) {
            case "half":
                return ["radio-outer-circle selected","radio-half-inner-circle"]
            case "full":
                return ["radio-outer-circle selected","radio-full-inner-circle"]

            default:
                return ["radio-outer-circle unselected",""]
        }
    }
    

    const tableMarkup = useMemo(() => <div className="modern-radio-container" style={{ "height": "50vh" }}>
        {userState.map((user, idxOut) => user.state.map((state, idxIn) => {
            return (
                <div className={`${classSelector(state.attendence)?.[0]}`} onClick={() => updateState(idxOut, idxIn)}>
                    <div className={`${classSelector(state.attendence)?.[1]}`}>

                    </div>
                </div>
            )
        }))}
    </div>, [userState])


    {/* for every user return the row of attendence circles */ }
    // const tableMarkup = <div className="modern-radio-container" style={{ "height": "50vh" }}>
    //     {userState.map((user,idxOut) => user.state.map((state, idxIn) => {
    //         return (
    //             <div className={`${classSelector(state.attendence)?.[0]}`} onClick={() => updateState(idxOut, idxIn)}>
    //                 <div className={`${classSelector(state.attendence)?.[1]}`}>

    //                 </div>
    //             </div>
    //         )
    //     })) }
    // </div>
        
        
                

  return (
    <>
        {tableMarkup}
    </>
  )
}

export default AttendenceTable