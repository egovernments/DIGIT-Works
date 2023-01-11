import { Loader, Card } from '@egovernments/digit-ui-react-components';
import React, { Fragment, useState, useEffect } from 'react'
import { useTranslation } from 'react-i18next';
import ApplicationDetails from '../../../templates/ApplicationDetails';

const ViewFinancialDetails = (props) => {
    
    const { t } = useTranslation()
    const { isLoading, data : fetchedData } = Digit.Hooks.project.useViewFinancialDetails(t, ""); 
  
  return (
    <>
        <ApplicationDetails
            applicationDetails={fetchedData?.applicationDetails}
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