import React from 'react'
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';
import { Header, ActionBar, SubmitBar } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../../templates/ApplicationDetails';

const ViewBill = () => {
  const { t } = useTranslation();
  const { tenantId, billNumber } = Digit.Hooks.useQueryParams();

  const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.bills.useViewBill(tenantId, {}, { musterRollNumber: billNumber })

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

export default ViewBill;