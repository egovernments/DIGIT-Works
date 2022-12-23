import React from 'react'
import { useTranslation } from 'react-i18next'
import { CardSectionHeader, Card} from "@egovernments/digit-ui-react-components";
import ApplicationDetails from "../../../../templates/ApplicationDetails";

const CommissionDetails = ({ wrapInCard }) => {
  const { t } = useTranslation()
  let data = {
    applicationDetails: [
      {
          title: t("EXP_COMMISSION_DETAILS"),
          asSectionHeader: true,
          values: [
          { title: `${t("EXP_NAME_OF_SHG")}`, value: 'Maa Bhagawati SHG' },
          { title: `${t("EXP_COMMISSION_PERCENTAGE")}`, value: '7.5%' },
          { title: `${t("EXP_COMMISSION_AMOUNT")}`, value: 'Rs. 48949'}
          ],
    }]
  }; 

  return (
    <Card noCardStyle={!wrapInCard} style={{marginTop: '-16px'}}>
      <ApplicationDetails
        isLoading={false}
        applicationDetails={data}
        isDataLoading={false}
        applicationData={data?.certData}
        moduleCode="Expenditure"
        showTimeLine={false}
        noBoxShadow
        sectionHeadStyle={{marginBottom: '1rem'}}
      />
      <div style={{margin: "16px", display: "flex", justifyContent:"space-between"}}>
          <CardSectionHeader style={{marginBottom: 0}}>{t("EXP_TOTAL_COMMISSION_AMOUNT")}</CardSectionHeader>
          <CardSectionHeader style={{marginBottom: 0}}>{"₹ 1,20,000"}</CardSectionHeader>
      </div>
    </Card>
  )
}

export default CommissionDetails