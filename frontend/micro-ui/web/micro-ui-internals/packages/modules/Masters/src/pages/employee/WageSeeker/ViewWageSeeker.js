import React from 'react';
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';
import { Header, ActionBar, SubmitBar } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../../templates/ApplicationDetails';

const ViewWageSeeker = () => {
  const { t } = useTranslation();
  const history = useHistory();
  //const { tenantId, individualId } = Digit.Hooks.useQueryParams();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const individualId = 123

  const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.wageSeeker.useViewWageSeeker(tenantId, {}, {})
  console.log('@@Data', data);

  const handleModify = () => {
    history.push(`/${window.contextPath}/employee/masters/modify-wageseeker?tenantId=${tenantId}&individualId=${individualId}`);
  }
 
  return (
    <React.Fragment>
      <Header>{t("MASTERS_VIEW_WAGESEEKER")}</Header>
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
      <ActionBar>
          <SubmitBar label={t("ES_COMMON_MODIFY")} onSubmit={handleModify} />
      </ActionBar>
    </React.Fragment>
  )
}

export default ViewWageSeeker