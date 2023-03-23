import React from 'react'
import Card from './Card'
import {StatusTable,Row} from './StatusTable'
const ViewDetailsCard = ({cardState,t,...props}) => {
  return (
    <Card className={"employeeCard-override"} >
        <StatusTable customClass="view-header">
            {Object.keys(cardState)?.map((key,idx)=>{
                return (<Row className="border-none" label={`${t(key)}:`} text={cardState?.[key]} textStyle={{ whiteSpace: "pre" }} />)
            })}
        </StatusTable>
    </Card>
  )
}

export default ViewDetailsCard