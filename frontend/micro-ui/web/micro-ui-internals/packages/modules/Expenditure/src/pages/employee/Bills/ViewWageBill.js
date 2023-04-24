import React from 'react'
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';
import { Header, ActionBar, SubmitBar } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../../templates/ApplicationDetails';

const ViewWageBill = () => {
  const { t } = useTranslation();
  const { tenantId, billNumber } = Digit.Hooks.useQueryParams();

  const payload = {
    billCriteria: {
      tenantId,
      ids: [],
      businessService: "works.wages",
      referenceIds: []
    },
    pagination: { limit: 10, offSet: 0, sortBy: "ASC", order: "ASC"}
  }
  const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.bills.useViewWageBill({tenantId, data: payload, config: { cacheTime:0 }})

  return (
    <React.Fragment>
      <Header>{t("EXP_VIEW_BILL")}</Header>
      <ApplicationDetails
        applicationDetails={data?.applicationDetails}
        isLoading={isLoading}
        applicationData={data?.applicationData}
        moduleCode="AttendenceMgmt"
        isDataLoading={false}
        workflowDetails={data?.workflowDetails}
        showTimeLine={false}
        mutate={()=>{}}
        tenantId={tenantId}
      />
    </React.Fragment>
  )
}

export default ViewWageBill;