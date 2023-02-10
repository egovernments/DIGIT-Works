import { Loader, Card } from '@egovernments/digit-ui-react-components';
import React, { Fragment, useState, useEffect } from 'react'
import { useTranslation } from 'react-i18next';
import ApplicationDetails from '../../../templates/ApplicationDetails';

const ViewProject = (props) => {
  const tenantId =  Digit.ULBService.getCurrentTenantId();
  const queryStrings = Digit.Hooks.useQueryParams();
  const searchParams = {
    Projects : [
        {   //will update this once integration for view is completed
            tenantId : queryStrings?.tenantId || "pb.amritsar" ,
            projectNumber : queryStrings?.projectNumber || "PR/2022-23/02/000725"
        }
    ]
  } 
  const filters = {
      limit : 10,
      offset : 0,
      includeAncestors : true
  }
    
  const { t } = useTranslation()
  const { data, isLoading } = Digit.Hooks.works.useViewProjectDetailsInEstimate(t, tenantId, searchParams, filters);
  console.log(data);
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