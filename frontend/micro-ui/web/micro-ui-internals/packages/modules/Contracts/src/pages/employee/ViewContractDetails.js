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

    const searchParams = {
              tenantId : queryStrings?.tenantId || tenantId,
              contractNumber : queryStrings?.contractNumber
          }

    const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.contracts.useViewContractDetails(searchParams?.tenantId, {}, searchParams)
    console.log('@@Data', data);
    console.log("tenantid :",searchParams?.tenantId);
    console.log("contractNumber :",searchParams?.contractNumber);


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
        
   
    return (
      <React.Fragment>
        <Header>{t("WORKS_VIEW_WORK_ORDER")}</Header>
        <ApplicationDetails
          applicationDetails={data?.applicationDetails}
          isLoading={isLoading}
          applicationData={data?.applicationData}
          moduleCode="contracts"
          isDataLoading={false}
          workflowDetails={workflowDetails}
          businessService={"contracts"} 
          showTimeLine={true}
          mutate={()=>{}} //mutate={mutate}
          tenantId={tenantId}
          showToast={showToast}
          setShowToast={setShowToast}
          applicationNo={searchParams?.contractNumber}
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