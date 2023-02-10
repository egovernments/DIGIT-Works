import { Loader, Card } from '@egovernments/digit-ui-react-components';
import React, { Fragment, useState, useEffect } from 'react'
import { useTranslation } from 'react-i18next';
import ApplicationDetails from '../../../templates/ApplicationDetails';

const ViewFinancialDetails = (props) => {
  const tenantId =  Digit.ULBService.getCurrentTenantId();
  const queryStrings = Digit.Hooks.useQueryParams();
  const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);

  const searchParams = {
    Projects : [
        {
            tenantId : queryStrings?.tenantId,
            projectNumber : queryStrings?.projectNumber
        }
    ]
  } 
  const filters = {
      limit : 10,
      offset : 0
  }
    
  const { t } = useTranslation()
  const { data, isLoading } = Digit.Hooks.works.useViewProjectDetailsInEstimate(t, tenantId, searchParams, filters, headerLocale);
  return (
    <>
        <ApplicationDetails
            applicationDetails={data?.projectDetails?.searchedProject?.details?.financialDetails}
            isLoading={isLoading} 
            applicationData={{}}
            moduleCode="works"
            isDataLoading={isLoading}
            workflowDetails={{}}
            showTimeLine={false}
            timelineStatusPrefix={""}
            businessService={""}
            forcedActionPrefix={"WORKS"}
            noBoxShadow={true}
        />
    </>
  )
}

export default ViewFinancialDetails;