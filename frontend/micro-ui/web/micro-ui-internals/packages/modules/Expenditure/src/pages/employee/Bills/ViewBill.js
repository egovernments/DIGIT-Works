import React from 'react'
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';
import { Header, ActionBar, SubmitBar } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../../templates/ApplicationDetails';

const ViewBill = () => {
  const { t } = useTranslation();
  //const { tenantId, billNumber } = Digit.Hooks.useQueryParams();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const billNumber = 123

  const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.bills.useViewBill(tenantId, {}, {})
  console.log('@@Data', data);

  return (
    <React.Fragment>
      <Header>{"View Wage Bill"}</Header>
      <ApplicationDetails
        applicationDetails={data?.applicationDetails}
        isLoading={isLoading}
        applicationData={data?.applicationData}
        moduleCode="Masters"
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