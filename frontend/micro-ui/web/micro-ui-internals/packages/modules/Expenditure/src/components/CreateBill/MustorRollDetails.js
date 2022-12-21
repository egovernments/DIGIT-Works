import React from 'react'
import { useTranslation } from 'react-i18next'
import { Card, CardSectionHeader } from '@egovernments/digit-ui-react-components'
import MustorRollDetailsTable from './MustorRollDetailsTable'

const MustorRollDetails = ({ wrapInCard }) => {
  const { t } = useTranslation()

  return (
    <React.Fragment>
       <Card noCardStyle={!wrapInCard} style={{margin: '0px 16px'}}>
          <CardSectionHeader style={{marginBottom: '1rem'}}>{t("EXP_MUSTOR_ROLL_DETAILS")}</CardSectionHeader>
          <MustorRollDetailsTable/>
          <div style={{margin: "32px 0px", display: "flex", justifyContent:"space-between"}}>
              <CardSectionHeader style={{marginBottom: 0}}>{t("EXP_TOTAL_LABOUR_BILL")}</CardSectionHeader>
              <CardSectionHeader style={{marginBottom: 0}}>{"₹ 1,20,000"}</CardSectionHeader>
          </div>
       </Card>
    </React.Fragment>
  )
}

export default MustorRollDetails