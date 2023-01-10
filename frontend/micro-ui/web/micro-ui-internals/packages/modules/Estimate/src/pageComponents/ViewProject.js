import { Loader, Card } from '@egovernments/digit-ui-react-components';
import React, { Fragment, useState, useEffect } from 'react'
import { useTranslation } from 'react-i18next';
import ApplicationDetails from '../../../templates/ApplicationDetails';

const ViewProject = (props) => {
    
    //will fetch project id from props and call this hook useViewProjectDetailsInEstimate to get the details
    const { t } = useTranslation()
    let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.works.useViewProjectDetailsInEstimate(t, "", "");
  return (
    <>
        <ApplicationDetails
            applicationDetails={applicationDetails}
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