import { Loader, Card } from '@egovernments/digit-ui-react-components';
import React, { Fragment, useState, useEffect } from 'react'
import { useTranslation } from 'react-i18next';
import ApplicationDetails from '../../../templates/ApplicationDetails';

const ViewProject = ({fromUrl=true,...props}) => {
  let { tenantId, projectNumber,id } = Digit.Hooks.useQueryParams();
  if(!fromUrl){
    tenantId = props?.tenantId
    projectNumber = props?.projectNumber,
    id = props?.projectId
  }
  
  const searchParams = {
    Projects: [
      {
        tenantId,
        projectNumber: projectNumber,
        id
      }
    ]
  }

  Object.keys(searchParams.Projects[0]).forEach(key=>{
    if (!searchParams.Projects[0][key]) delete searchParams.Projects[0][key]
  })

  const filters = {
    limit: 11,
    offset: 0,
    includeAncestors: true,
    includeDescendants: true
  }
  
  
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