import React, { useState, useEffect } from 'react';
import { useTranslation } from "react-i18next";
import { useHistory, useLocation } from 'react-router-dom';
import { Header, SubmitBar } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../../templates/ApplicationDetails';
import { Toast,ActionBar,Button } from '@egovernments/digit-ui-components';

const ViewWageSeeker = () => {
  const { t } = useTranslation()
  const history = useHistory()
  const location = useLocation()
  const [showDataError, setShowDataError] = useState(null)

  const wageSeekerSession = Digit.Hooks.useSessionStorage("WAGE_SEEKER_CREATE", {});
  const [sesionFormData, clearSessionFormData] = wageSeekerSession;
  const loggedInUserRoles = Digit.Utils.getLoggedInUserDetails("roles");

  // const tenantId = Digit.ULBService.getCurrentTenantId()
  const {individualId,tenantId } = Digit.Hooks.useQueryParams()

  const payload = {
    Individual: {
      individualId: [individualId]
    }
  }
  const searchParams = {
    offset: 0, limit: 100
  }
  
  const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.wageSeeker.useViewWageSeeker({tenantId, data: payload, searchParams, config: { cacheTime:0 }})

  useEffect(() => {
    if(isError) {
      setShowDataError(true)
    }
  }, [error])

  useEffect(() => {
    if (!window.location.href.includes("modify-wageseeker") && sesionFormData && Object.keys(sesionFormData) != 0) {
      clearSessionFormData();
    }
  }, [location])

  const handleModify = () => {
    history.push(`/${window.contextPath}/employee/masters/modify-wageseeker?tenantId=${tenantId}&individualId=${individualId}`);
  }
 
  return (
    <React.Fragment>
      <Header className="works-header-view">{t("MASTERS_VIEW_WAGESEEKER")}</Header>
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
          {loggedInUserRoles?.includes("VIEW_WS_UNMASKED") && loggedInUserRoles?.includes("VIEW_DED_UNMASKED") && <ActionBar
            actionFields={[<Button type={"submit"} label={t("ES_COMMON_MODIFY")} variation={"primary"} onClick={handleModify}></Button>]}
            setactionFieldsToRight={true}
            className={"new-actionbar"}
          />}
          </React.Fragment>
      }
      {
        showDataError && <Toast type={"error"} label={t("COMMON_ERROR_FETCHING_WAGE_SEEKER_DETAILS")} isDleteBtn={true} onClose={() => setShowDataError(false)} />
      }
    </React.Fragment>
  )
}

export default ViewWageSeeker