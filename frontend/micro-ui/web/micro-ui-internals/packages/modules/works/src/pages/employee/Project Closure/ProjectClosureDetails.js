import { Header, MultiLink, Card, StatusTable, Row, CardSubHeader, Loader } from '@egovernments/digit-ui-react-components'
import React, { Fragment, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { Link, useLocation } from "react-router-dom";
import ApplicationDetailsContent from '../../../../../templates/ApplicationDetails/components/ApplicationDetailsContent';


const ProjectClosureDetails = ({activeLink}) => {
    const { t } = useTranslation()
    //estimation
    let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.works.useViewProjectClosureDetails("pb.amritsar");

    //contracts

    let { isLoading: isContractLoading, isError: isContractError, data: applicationDetailsContract, error: errorContract } = Digit.Hooks.contracts.useViewContractDetailsClosureScreen(t, "tenantId", "contractId", "subEstimateNumber", { enabled:true });

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
          {activeLink==="Estimation"? isLoading ? <Loader /> : <ApplicationDetailsContent
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
          />:null}
          {activeLink === "Contracts" ? isContractLoading ? <Loader /> : <ApplicationDetailsContent
              applicationDetails={applicationDetailsContract}
              isDataLoading={isContractLoading}
              applicationData={applicationDetailsContract?.applicationData}
              workflowDetails={applicationDetailsContract?.workflowDetails}
              statusAttribute={"status"}
              moduleCode="contracts"
              showTimeLine={true}
              timelineStatusPrefix=""
              isInfoLabel={false}
          /> :null}
          {activeLink === "FieldSurvey" ? <div>{activeLink}</div> : null}
          {activeLink === "Billing" ? <div>{activeLink}</div> : null}
          {activeLink === "Closure Checklist" ? <div>{activeLink}</div> : null}

    </Fragment>
  )
}

export default ProjectClosureDetails