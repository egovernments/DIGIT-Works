import React, { useState, useEffect } from 'react';
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';
import { Header, ActionBar, SubmitBar, Toast } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../templates/ApplicationDetails';

const ViewWageSeeker = () => {
  const { t } = useTranslation();
  const history = useHistory();
  const [showDataError, setShowDataError] = useState(null)

  //const { tenantId, individualId } = Digit.Hooks.useQueryParams();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const individualId = "IND-2023-03-17-001342"

  const payload = {
    Individual: {
      individualId
    }
  }
  const searchParams = {
    offset: 0, limit: 100
  }
  
  const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.wageSeeker.useViewWageSeeker(tenantId, payload, searchParams)

  useEffect(() => {
    if(isError) {
      setShowDataError(true)
    }
  }, [error])

  const handleModify = () => {
    history.push(`/${window.contextPath}/employee/masters/modify-wageseeker?tenantId=${tenantId}&individualId=${individualId}`);
  }
 
  return (
    <React.Fragment>
      <Header>{t("MASTERS_VIEW_WAGESEEKER")}</Header>
      {
        showDataError === null && <React.Fragment>
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
      }
      {
        showDataError && <Toast error={true} label={t("COMMON_ERROR_FETCHING_WAGE_SEEKER_DETAILS")} isDleteBtn={true} onClose={() => setShowDataError(false)} />
      }
    </React.Fragment>
  )
}

export default ViewWageSeeker