import React, { useState, useEffect } from 'react';
import { useTranslation } from "react-i18next";
import { useHistory, useLocation } from 'react-router-dom';
import { Header, ViewDetailsCard, Loader, SubmitBar } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../../templates/ApplicationDetails';
import { Toast,ActionBar,Button ,Tab} from "@egovernments/digit-ui-components";

const ViewOrganisation = () => {
  const { t } = useTranslation()
  const history = useHistory()
  const location = useLocation()
  const [showDataError, setShowDataError] = useState(null)

  const { orgId, tenantId } = Digit.Hooks.useQueryParams()

  const [activeLink, setActiveLink] = useState("Location_Details");
  const orgSession = Digit.Hooks.useSessionStorage("ORG_CREATE", {});
  const [sessionFormData, clearSessionFormData] = orgSession;
  const loggedInUserRoles = Digit.Utils.getLoggedInUserDetails("roles");

  const configNavItems = [
    {
        "name": "Location_Details",
        "code": "COMMON_LOCATION_DETAILS",
        "active": true
    },
    {
        "name": "Contact_Details",
        "code": "ES_COMMON_CONTACT_DETAILS",
        "active": true
    },
    {
      "name": "Financial_Details",
      "code": "MASTERS_FINANCIAL_DETAILS",
      "active": true
  }
]
  const payload = {
    SearchCriteria: {
      orgNumber: orgId,
      tenantId
    }
  }

  const {isLoading, data: organisation, isError, isSuccess, error} = Digit.Hooks.organisation.useViewOrganisation({ tenantId, data: payload, config: { cacheTime:0 } })

  useEffect(() => {
    if(isError) {
      setShowDataError(true)
    }
  }, [error])

  useEffect(()=>{
    if (!window.location.href.includes("create-organization") && sessionFormData && Object.keys(sessionFormData) != 0) {
      clearSessionFormData();
    }
},[location]);

  const handleModify = () => {
    history.push(`/${window.contextPath}/employee/masters/create-organization?tenantId=${tenantId}&orgId=${orgId}`);
  }

  if(isLoading) return <Loader />

  return (
    <React.Fragment>
      <Header className="works-header-view">{t("MASTERS_VIEW_VENDOR_ORG")}</Header>
      {showDataError === null && (
        <React.Fragment>
          {organisation && <ViewDetailsCard cardState={organisation?.applicationDetails?.orgDetails} t={t} />}
          {organisation && (
            <Tab
              showNav={true}
              configNavItems={configNavItems}
              activeLink={activeLink}
              setActiveLink={setActiveLink}
              inFormComposer={false}
              configItemKey="name"
              configDisplayKey={"code"}
              itemStyle={{width:"unset !important"}}
              navStyles={{}}
              style={{}}
            >
              {activeLink === "Location_Details" && (
                <ApplicationDetails
                  applicationDetails={{ applicationDetails: [organisation?.applicationDetails?.locationDetails] }}
                  isLoading={isLoading}
                  applicationData={organisation?.applicationData}
                  moduleCode="Masters"
                  isDataLoading={false}
                  workflowDetails={organisation?.workflowDetails}
                  showTimeLine={false}
                  mutate={() => {}}
                  tenantId={tenantId}
                />
              )}
              {activeLink === "Contact_Details" && (
                <ApplicationDetails
                  applicationDetails={{ applicationDetails: [organisation?.applicationDetails?.contactDetails] }}
                  isLoading={isLoading}
                  applicationData={organisation?.applicationData}
                  moduleCode="Masters"
                  isDataLoading={false}
                  workflowDetails={organisation?.workflowDetails}
                  showTimeLine={false}
                  mutate={() => {}}
                  tenantId={tenantId}
                />
              )}
              {activeLink === "Financial_Details" && (
                <ApplicationDetails
                  applicationDetails={{ applicationDetails: organisation?.applicationDetails?.financialDetails }}
                  isLoading={isLoading}
                  applicationData={organisation?.applicationData}
                  moduleCode="Masters"
                  isDataLoading={false}
                  workflowDetails={organisation?.workflowDetails}
                  showTimeLine={false}
                  mutate={() => {}}
                  tenantId={tenantId}
                />
              )}
            </Tab>
          )}
          {loggedInUserRoles?.includes("VIEW_ORG_UNMASKED") && loggedInUserRoles?.includes("VIEW_DED_UNMASKED") && <ActionBar
            actionFields={[<Button type={"submit"} label={t("ES_COMMON_MODIFY")} variation={"primary"} onClick={handleModify}></Button>]}
            setactionFieldsToRight={true}
            className={"new-actionbar"}
          />}
        </React.Fragment>
      )}
      {showDataError && (
        <Toast type={"error"} label={t("COMMON_ERROR_FETCHING_ORG_DETAILS")} isDleteBtn={true} onClose={() => setShowDataError(false)} />
      )}
    </React.Fragment>
  );
}

export default ViewOrganisation