import React,{ Fragment,useState } from 'react'
import { WorkflowTimeline,WorkflowActions } from '@egovernments/digit-ui-react-components'

// Action
// Date and Time
// Name,Designation
// phone
//comments
//attachments
const WorkflowCompTest = (props) => {
    const [displayMenu,setDisplayMenu] = useState(false)
  return (
      <>
          <WorkflowTimeline businessService={"muster-roll-approval"} applicationNo={"MR/2022-23/02/20/000464"} tenantId={"pg.citya"} />
          <WorkflowActions 
              displayMenu={displayMenu}
              setDisplayMenu={setDisplayMenu}
              forcedActionPrefix={"ACTIONS"}
              ActionBarStyle={{}}
              MenuStyle={{}}
              businessService={"muster-roll-approval"} 
              applicationNo={"MR/2022-23/02/20/000464"} 
              tenantId={"pg.citya"}
              saveAttendanceState={{ displaySave: false, updatePayload: [] }}
              onActionSelect={(action)=>console.log(action)}
          />
      </>
  )
}
//MR/2022-23/02/20/000457
//MR/2022-23/02/20/000460

//MR/2022-23/02/20/000461
export default WorkflowCompTest