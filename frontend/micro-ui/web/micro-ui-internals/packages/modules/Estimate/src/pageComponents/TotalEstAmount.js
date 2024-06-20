import { CardSectionHeader } from '@egovernments/digit-ui-react-components'
import React,{ useEffect,useState,useMemo } from 'react'

const TotalEstAmount = ({formData,setValue,t,...props}) => {
    const formFieldNameNonSor = "NONSORtable"
    const formFieldNameSor = "SORtable"
    const formFieldNameOverheads = "overheadDetails"
    
    let getTotalAmount = useMemo(() => {
        let totalSor = formData?.[formFieldNameSor]?.reduce((acc,row)=>{
            let amountNonSor =  parseFloat(row?.amount) 
            amountNonSor = amountNonSor ? amountNonSor : 0
            return amountNonSor + parseFloat(acc)
        }, 0) 
        totalSor = totalSor ? totalSor : 0
        let totalNonSor = formData?.[formFieldNameNonSor]?.reduce((acc,row)=>{
            let amountNonSor =  parseFloat(row?.amount) 
            amountNonSor = amountNonSor ? amountNonSor : 0
            return amountNonSor + parseFloat(acc)
        }, 0) 
        totalNonSor = totalNonSor ? totalNonSor : 0
        let totalOverHeads = formData?.[formFieldNameOverheads]?.reduce((acc, row) => {
            let amountOverheads = parseFloat(row?.amount) 
            amountOverheads = amountOverheads ? amountOverheads : 0
            return amountOverheads + parseFloat(acc)
        }, 0)
        totalOverHeads = !(Number.isNaN(totalOverHeads))  ? totalOverHeads : 100

        return totalSor + totalNonSor + totalOverHeads
    }, [formData])

    
    useEffect(() => {
        setValue("totalEstimatedAmount", getTotalAmount)
    }, [getTotalAmount])
    
 
  return (
      <div style={{ display: "flex", justifyContent: "flex-end", marginTop: "1.5rem" }}>
          <div style={{ display: "flex", flexDirection: 'row', justifyContent: "space-between", padding: "1rem", border:"1px solid #D6D5D4",borderRadius:"5px" }}>
              <CardSectionHeader style={{ marginRight: "1rem", marginBottom: "0px", color:"#505A5F"}}>{t("TOTAL_EST_AMOUNT")}</CardSectionHeader>
              <CardSectionHeader style={{ marginBottom: "0px" }}>{`₹ ${Digit.Utils.dss.formatterWithoutRound(Math.round(getTotalAmount), 'number')}`}</CardSectionHeader>
          </div>
      </div>
  )
}

export default TotalEstAmount