import React, { useState }from 'react';
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';
import { Header, ActionBar, SubmitBar } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../templates/ApplicationDetails';


const ViewContractDetails = () => {
    const { t } = useTranslation();
    //const history = useHistory();
    const [showToast, setShowToast] = useState(null);
    const queryStrings = Digit.Hooks.useQueryParams();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    // contractNumber = "WO/2022-23/000329"

    const payload = {
        tenantId : queryStrings?.tenantId || tenantId,
        contractNumber : queryStrings?.workOrderNumber
    }

    const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.contracts.useViewContractDetails(payload?.tenantId, payload, {})
    console.log('@@Data', data);
  


    /*
    let workflowDetails = Digit.Hooks.useWorkflowDetails(
      {
          tenantId: searchParams?.tenantId,
          id: searchParams?.contractNumber,
          moduleCode: data?.processInstancesDetails?.[0]?.businessService,
          config: {
              enabled:data?.processInstancesDetails?.[0]?.businessService ? true : false,
              cacheTime:0
          }
      }
    );

    console.log("workflow :", workflowDetails);
    */
   
    return (
      <React.Fragment>
        <Header>{t("WORKS_VIEW_WORK_ORDER")}</Header>
        <ApplicationDetails
          applicationDetails={data?.applicationDetails}
          isLoading={isLoading}
          applicationData={data?.applicationData}
          moduleCode="contracts"
          isDataLoading={false}
          workflowDetails={{}}
          businessService={"contracts"} 
          showTimeLine={true}
          mutate={()=>{}} //mutate={mutate}
          tenantId={tenantId}
          showToast={showToast}
          setShowToast={setShowToast}
          applicationNo={payload?.contractNumber}
          //setshowEditTitle={setshowEditTitle}
          //timelineStatusPrefix={"ATM_"}
          //forcedActionPrefix={"ATM"}
        />
        <ActionBar>
            <SubmitBar label={t("WORKS_ACTIONS")} onSubmit={() => {}} />
        </ActionBar>
      </React.Fragment>
    )
}

export default ViewContractDetails