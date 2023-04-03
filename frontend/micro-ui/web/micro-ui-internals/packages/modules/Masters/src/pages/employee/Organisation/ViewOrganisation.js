import React, { useState, useEffect } from 'react';
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';
import { Header, ViewDetailsCard, HorizontalNav, Loader, ActionBar, SubmitBar, Toast } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../../templates/ApplicationDetails';

const ViewOrganisation = () => {
  const { t } = useTranslation()
  const history = useHistory()
  const [showDataError, setShowDataError] = useState(null)

  const { organizationId, tenantId } = Digit.Hooks.useQueryParams()

  const [activeLink, setActiveLink] = useState("Location_Details");

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
      orgNumber: organizationId
    }
  }

  const {isLoading, data: organisation, isError, isSuccess, error} = Digit.Hooks.organisation.useViewOrganisation({ tenantId, data: payload })

  useEffect(() => {
    if(isError) {
      setShowDataError(true)
    }
  }, [error])

  const handleModify = () => {
    history.push(`/${window.contextPath}/employee/masters/create-organization?tenantId=${tenantId}&organizationId=${organizationId}`);
  }

  if(isLoading) return <Loader />

  return (
    <React.Fragment>
      <Header>{t("MASTERS_VIEW_VENDOR_ORG")}</Header>
      {
        showDataError === null && ( <React.Fragment>
          {
            organisation && <ViewDetailsCard cardState={organisation?.applicationDetails?.orgDetails} t={t} />
          }
          {
            organisation && 
              <HorizontalNav showNav={true} configNavItems={configNavItems} activeLink={activeLink} setActiveLink={setActiveLink} inFormComposer={false}>
                { activeLink === "Location_Details" && (
                  <ApplicationDetails
                    applicationDetails={{ applicationDetails : [organisation?.applicationDetails?.locationDetails] }}
                    isLoading={isLoading}
                    applicationData={organisation?.applicationData}
                    moduleCode="Masters"
                    isDataLoading={false}
                    workflowDetails={organisation?.workflowDetails}
                    showTimeLine={false}
                    mutate={()=>{}}
                    tenantId={tenantId}
                  />) 
                }
                { activeLink === "Contact_Details" &&  (
                  <ApplicationDetails
                    applicationDetails={{ applicationDetails : [organisation?.applicationDetails?.contactDetails] }}
                    isLoading={isLoading}
                    applicationData={organisation?.applicationData}
                    moduleCode="Masters"
                    isDataLoading={false}
                    workflowDetails={organisation?.workflowDetails}
                    showTimeLine={false}
                    mutate={()=>{}}
                    tenantId={tenantId}
                  />) 
                }
                { activeLink === "Financial_Details" &&  (
                  <ApplicationDetails
                    applicationDetails={{ applicationDetails : organisation?.applicationDetails?.financialDetails }}
                    isLoading={isLoading}
                    applicationData={organisation?.applicationData}
                    moduleCode="Masters"
                    isDataLoading={false}
                    workflowDetails={organisation?.workflowDetails}
                    showTimeLine={false}
                    mutate={()=>{}}
                    tenantId={tenantId}
                  />) 
                } 
              </HorizontalNav>
          }
          <ActionBar>
            <SubmitBar label={t("ES_COMMON_MODIFY")} onSubmit={handleModify} />
          </ActionBar>
      </React.Fragment> )
      }
      {
        showDataError && <Toast error={true} label={t("COMMON_ERROR_FETCHING_ORG_DETAILS")} isDleteBtn={true} onClose={() => setShowDataError(false)} />
      }
  </React.Fragment>
  )
}

export default ViewOrganisation