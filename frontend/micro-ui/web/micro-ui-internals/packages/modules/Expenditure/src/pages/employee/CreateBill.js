import React from 'react'
import { useTranslation } from 'react-i18next'
import { Header, Card, BreakLine, CardSectionHeader } from '@egovernments/digit-ui-react-components'
import AddVendorBill from '../../components/CreateBill/AddVendorBill'
import MustorRollDetails from '../../components/CreateBill/MustorRollDetails'
import CommissionDetails from '../../components/CreateBill/CommissionDetails'

/*
create option in contract details button - Create Bill, redirect to current page
Based on type of contract - SHG_WORK_ORDER, DEPT_WORK_ORDER, DEPT_PURCHASE_ORDER, pass type from screen and create switch case 
  Organisation (SHG)
  department - work order
  department - purchase order
On click of proceed, show success bar
add in collapsible component
*/
const CreateBill = () => {
  const { t } = useTranslation()
  
  return (
    <React.Fragment>
      <Header>{t("EXP_CREATE_BILL")}</Header>
      <Card>
        <MustorRollDetails/>
        <BreakLine />
        {/* <AddVendorBill noBreakLine/>
        <BreakLine /> */}
        <CommissionDetails/>
        <BreakLine/>
        <div style={{margin: "32px 16px", display: "flex", justifyContent:"space-between"}}>
          <CardSectionHeader style={{marginBottom: 0}}>{t("EXP_TOTAL_BILL_AMT")}</CardSectionHeader>
          <CardSectionHeader style={{marginBottom: 0}}>{"₹ 3,60,000"}</CardSectionHeader>
        </div>
      </Card>
    </React.Fragment>
  )
}

export default CreateBill;