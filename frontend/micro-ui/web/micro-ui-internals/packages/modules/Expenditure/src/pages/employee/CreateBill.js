import React from 'react'
import { Header } from '@egovernments/digit-ui-react-components'
import { useTranslation } from 'react-i18next'
import AddVendorBill from '../../components/CreateBill/AddVendorBill'
import MustorRollDetails from '../../components/CreateBill/MustorRollDetails'
import CommissionDetails from '../../components/CreateBill/CommissionDetails'

const CreateBill = () => {
  const { t } = useTranslation()
  
  return (
    <React.Fragment>
      <Header>{t("EXP_CREATE_BILL")}</Header>
      <MustorRollDetails/>
      <AddVendorBill/>
      <CommissionDetails/>
    </React.Fragment>
  )
}

export default CreateBill;