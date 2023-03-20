import { Loader, Card, Toast } from '@egovernments/digit-ui-react-components';
import React, { Fragment, useState, useEffect } from 'react'
import { useTranslation } from 'react-i18next';
import ApplicationDetails from '../../../templates/ApplicationDetails';

const ViewProject = ({fromUrl=true,...props}) => {
  let { tenantId, projectNumber,id } = Digit.Hooks.useQueryParams();
  const [toast, setToast] = useState({show : false, label : "", error : false});
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

  const handleToastClose = () => {
    setToast({show : false, label : "", error : false});
  }
  
  const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);    
  const { t } = useTranslation()
  const { data, isLoading, isError } = Digit.Hooks.works.useViewProjectDetails(t, tenantId, searchParams, filters, headerLocale);

  useEffect(()=>{
    if( isError || (!isError && data?.isNoDataFound)) {
      setToast({show : true, label : "COMMON_PROJECT_NOT_FOUND", error : true});
    }
  },[isError, data]);

  if(isLoading) return <Loader></Loader>
  return (
    <>
        {
          !data?.isNoDataFound && 
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
              customClass="status-table-custom-class"
            />
        }
        {toast?.show && <Toast label={toast?.label} error={toast?.error} isDleteBtn={true} onClose={handleToastClose}></Toast>}
    </>
  )
}

export default ViewProject