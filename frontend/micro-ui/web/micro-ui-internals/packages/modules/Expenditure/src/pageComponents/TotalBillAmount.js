import { CardSectionHeader } from '@egovernments/digit-ui-react-components'
import React,{ useEffect,useState,useMemo } from 'react'

const TotalBillAmount = ({formData,setValue,t,...props}) => {
    const formFieldNameDeductions = "deductionDetails"
    
    let getTotalAmount = useMemo(() => {
        let totalDeductions = formData?.[formFieldNameDeductions]?.reduce((acc, row) => {
            let amountDeductions = parseFloat(row?.amount) 
            amountDeductions = amountDeductions ? amountDeductions : 0
            return amountDeductions + parseFloat(acc)
        }, 0)
        totalDeductions = totalDeductions ? totalDeductions : 0
        return totalDeductions
    }, [formData])
    
    useEffect(() => {
        setValue("totalBillAmount", getTotalAmount)
    }, [getTotalAmount])
    
 
  return (
      <div style={{ display: "flex", justifyContent: "flex-end", marginTop: "2rem" }}>
          <div style={{ display: "flex", flexDirection: 'row', justifyContent: "space-between", padding: "1rem", border:"1px solid #D6D5D4",borderRadius:"5px" }}>
              <CardSectionHeader style={{ marginRight: "1rem", marginBottom: "0px", color:"#505A5F"}}>{t("NET_PAYABLE")}</CardSectionHeader>
              <CardSectionHeader style={{ marginBottom: "0px" }}>{`₹ ${Digit.Utils.dss.formatterWithoutRound(getTotalAmount, 'number')}`}</CardSectionHeader>
          </div>
      </div>
  )
}

export default TotalBillAmount