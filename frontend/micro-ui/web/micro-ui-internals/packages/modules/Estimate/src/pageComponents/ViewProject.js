import { Loader, Card } from '@egovernments/digit-ui-react-components';
import React, { Fragment, useState, useEffect } from 'react'
import { useTranslation } from 'react-i18next';
import ApplicationDetails from '../../../templates/ApplicationDetails';

const ViewProject = ({searchParams, filters}) => {
  const tenantId =  Digit.ULBService.getCurrentTenantId();
  const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);    
  const { t } = useTranslation()
  const { data, isLoading } = Digit.Hooks.works.useViewProjectDetailsInEstimate(t, tenantId, searchParams, filters, headerLocale);

  return (
    <>
        <ApplicationDetails
            applicationDetails={data?.projectDetails?.searchedProject?.details?.projectDetails}
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

export default ViewProject