import { Header, MultiLink, Card, StatusTable, Row, CardSubHeader, Loader,ViewImages} from '@egovernments/digit-ui-react-components'
import React, { Fragment, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { Link, useLocation } from "react-router-dom";
import ApplicationDetailsContent from '../../../../../templates/ApplicationDetails/components/ApplicationDetailsContent';
import ApplicationDetails from "../../../../../templates/ApplicationDetails";


const ProjectClosureDetails = ({ activeLink, isMdmsLoading, checklistData }) => {
   // const { ClosureChecklist, KickoffChecklist } = checklistData?.works
    const { t } = useTranslation()
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const tenant = Digit.ULBService.getStateId();
    //estimation
    let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.works.useViewProjectClosureDetails("pb.amritsar");

    //contracts
    let { isLoading: isContractLoading, isError: isContractError, data: applicationDetailsContract, error: errorContract } = Digit.Hooks.contracts.useViewContractDetailsClosureScreen(t, "tenantId", "contractId", "subEstimateNumber", { enabled:true });


    const { data: applicationDetailsBills ,isLoading:isLoadingBills } = Digit.Hooks.works.useViewProjectClosureDetailsBills('pb.amrisar'); //pass required inputs when backend service is ready.
    
    const { data: applicationDetailsKickoffChecklist, isLoading: isLoadingKickoffChecklist } = Digit.Hooks.works.useViewProjectClosureDetailsKickoffChecklist('pb.amrisar', checklistData?.works?.KickoffChecklist, { enabled: isMdmsLoading ? false : true},t); //pass required inputs when backend service is ready.

    const { data: applicationDetailsClosureChecklist, isLoading: isLoadingClosureChecklist } = Digit.Hooks.works.useViewProjectClosureDetailsClosureChecklist('pb.amrisar', checklistData?.works?.ClosureChecklist, { enabled: isMdmsLoading ? false : true },t); //pass required inputs when backend service is ready.

    let workflowDetails = Digit.Hooks.useWorkflowDetails(
        {
            tenantId: "pb.amritsar",
            id: applicationDetails?.applicationData?.estimateNumber,
            moduleCode: applicationDetails?.processInstancesDetails?.[0]?.businessService,
            config: {
                enabled: applicationDetails?.processInstancesDetails?.[0]?.businessService ? true : false,
                cacheTime: 0
            }
        },
    );
  return (
    <Fragment>
          {activeLink === "Estimation" ? isLoading ? <Loader /> : <Card className={"employeeCard-override"}>
             <ApplicationDetailsContent
              applicationDetails={applicationDetails}
              workflowDetails={workflowDetails}
              isDataLoading={isLoading}
              applicationData={applicationDetails?.applicationData}
              //businessService={businessService}
              timelineStatusPrefix={""}
              statusAttribute={"status"}
              //paymentsList={paymentsList}
              showTimeLine={true}
              //oldValue={oldValue}
              isInfoLabel={false}
              noBoxShadow={true}

          />
          </Card>:null}
          {activeLink === "Contracts" ? isContractLoading ? <Loader /> : <Card className={"employeeCard-override"}> 
              <ApplicationDetailsContent
                  applicationDetails={applicationDetailsContract}
                  isDataLoading={isContractLoading}
                  applicationData={applicationDetailsContract?.applicationData}
                  workflowDetails={applicationDetailsContract?.workflowDetails}
                  statusAttribute={"status"}
                  moduleCode="contracts"
                  showTimeLine={true}
                  timelineStatusPrefix=""
                  isInfoLabel={false}
                  noBoxShadow={true}
              />
              <ApplicationDetailsContent
                  applicationDetails={applicationDetailsContract}
                  isDataLoading={isContractLoading}
                  applicationData={applicationDetailsContract?.applicationData}
                  workflowDetails={applicationDetailsContract?.workflowDetails}
                  statusAttribute={"status"}
                  moduleCode="contracts"
                  showTimeLine={true}
                  timelineStatusPrefix=""
                  isInfoLabel={false}
                  noBoxShadow={true}
              />
              <ApplicationDetailsContent
                  applicationDetails={applicationDetailsContract}
                  isDataLoading={isContractLoading}
                  applicationData={applicationDetailsContract?.applicationData}
                  workflowDetails={applicationDetailsContract?.workflowDetails}
                  statusAttribute={"status"}
                  moduleCode="contracts"
                  showTimeLine={true}
                  timelineStatusPrefix=""
                  isInfoLabel={false}
                  noBoxShadow={true}
              />
              <ApplicationDetailsContent
                  applicationDetails={applicationDetailsContract}
                  isDataLoading={isContractLoading}
                  applicationData={applicationDetailsContract?.applicationData}
                  workflowDetails={applicationDetailsContract?.workflowDetails}
                  statusAttribute={"status"}
                  moduleCode="contracts"
                  showTimeLine={true}
                  timelineStatusPrefix=""
                  isInfoLabel={false}
                  noBoxShadow={true}
              />
          </Card> :null}
          {activeLink === "FieldSurvey" ? isMdmsLoading || isLoadingKickoffChecklist ? <Loader /> : <Card className={"employeeCard-override"}>
              <ApplicationDetailsContent
                  applicationDetails={applicationDetailsKickoffChecklist}
                  workflowDetails={{}}
                  isDataLoading={isLoadingKickoffChecklist}
                  applicationData={{}}
                  //businessService={businessService}
                  timelineStatusPrefix={""}
                  statusAttribute={"status"}
                  //paymentsList={paymentsList}
                  showTimeLine={false}
                  //oldValue={oldValue}
                  isInfoLabel={false}
                  noBoxShadow={true}

              />
          </Card> : null}
          {activeLink === "Billing" ? <Card className={"employeeCard-override"}>
              <ApplicationDetails
                  applicationDetails={applicationDetailsBills}
                  isLoading={false} //will come from backend
                  applicationData={applicationDetailsBills}
                  moduleCode="Expenditure"
                  isDataLoading={false}
                  workflowDetails={[]}
                  showTimeLine={false}
                  timelineStatusPrefix={""}
                  businessService={""}
                  forcedActionPrefix={"EXP"}
                  noBoxShadow={true}
              />
              <ApplicationDetails
                  applicationDetails={applicationDetailsBills}
                  isLoading={false} //will come from backend
                  applicationData={applicationDetailsBills}
                  moduleCode="Expenditure"
                  isDataLoading={false}
                  workflowDetails={[]}
                  showTimeLine={false}
                  timelineStatusPrefix={""}
                  businessService={""}
                  forcedActionPrefix={"EXP"}
                  noBoxShadow={true}
              />
              <ApplicationDetails
                  applicationDetails={applicationDetailsBills}
                  isLoading={false} //will come from backend
                  applicationData={applicationDetailsBills}
                  moduleCode="Expenditure"
                  isDataLoading={false}
                  workflowDetails={[]}
                  showTimeLine={false}
                  timelineStatusPrefix={""}
                  businessService={""}
                  forcedActionPrefix={"EXP"}
                  noBoxShadow={true}
              />
              <ApplicationDetails
                  applicationDetails={applicationDetailsBills}
                  isLoading={false} //will come from backend
                  applicationData={applicationDetailsBills}
                  moduleCode="Expenditure"
                  isDataLoading={false}
                  workflowDetails={[]}
                  showTimeLine={false}
                  timelineStatusPrefix={""}
                  businessService={""}
                  forcedActionPrefix={"EXP"}
                  noBoxShadow={true}
              />
          </Card> : null}
          {activeLink === "Closure Checklist" ? isMdmsLoading || isLoadingKickoffChecklist ? <Loader /> : <Card className={"employeeCard-override"}>
              <ApplicationDetailsContent
                  applicationDetails={applicationDetailsClosureChecklist}
                  workflowDetails={{}}
                  isDataLoading={isLoadingClosureChecklist}
                  applicationData={{}}
                  //businessService={businessService}
                  timelineStatusPrefix={""}
                  statusAttribute={"status"}
                  //paymentsList={paymentsList}
                  showTimeLine={false}
                  //oldValue={oldValue}
                  isInfoLabel={false}
                  noBoxShadow={true}

              />
          </Card> : null}

    </Fragment>
  )
}

export default ProjectClosureDetails