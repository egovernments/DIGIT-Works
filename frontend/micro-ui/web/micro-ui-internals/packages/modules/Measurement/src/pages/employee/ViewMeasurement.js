
import { Header, Card, Loader, Button } from "@egovernments/digit-ui-react-components";

import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import ApplicationDetails from "../../../../templates/ApplicationDetails";
import MeasurementHistory from "../../components/MBHistoryTable";
import MeasureTable from "../../components/MeasureTable";

const ViewMeasurement = () => {
  const { t } = useTranslation();
  const history = useHistory();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const searchparams = new URLSearchParams(location.search);
  const workOrderNumber = searchparams.get("workOrderNumber");
  const mbNumber = searchparams.get("mbNumber")

  const criteria = {
    "criteria" : {
      tenantId : tenantId,
      referenceId : [workOrderNumber],
      measurementNumber : mbNumber
    }
  }

  let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.measurement.useViewMeasurement(tenantId, criteria);

  const projectDetails = {"applicationDetails" : [applicationDetails?.applicationDetails?.applicationDetails[0]]};
  const imageDetails = {"applicationDetails" : [applicationDetails?.applicationDetails?.applicationDetails[2]]};


  const sorEstimates = applicationDetails?.applicationData?.estimate[0]?.estimateDetails.filter(item => item.category === 'SOR');
  const nonSorEstimates = applicationDetails?.applicationData?.estimate[0]?.estimateDetails.filter(item => item.category === 'NONSOR');

  const measures = applicationDetails?.applicationData?.measurements[0].measures
  console.log(measures, "measure")

  console.log(sorEstimates, "estimate");

  const tableData = {
    "data" : {
      "SOR" : sorEstimates,
      "NONSOR" : nonSorEstimates
    },
    "config" : {
      "key" : "SOR"
    }
  }

  if (isLoading) {
    return <Loader/>
  }

  return (
    <React.Fragment>
      <Header className="works-header-view">{t("MB_VIEW_MEASUREMENT_BOOK")}</Header>
      <ApplicationDetails
        applicationDetails={projectDetails}
        isLoading={isLoading}
        applicationData={applicationDetails?.applicationData}
        moduleCode="contracts"
        workflowDetails={applicationDetails?.workflowDetails}
        mutate={() => {}}
        tenantId={tenantId}
        showTimeLine={false}
      />
      <MeasurementHistory contractNumber={workOrderNumber} />
      <Card className="override-card view-utilization-card">
        <Button 
        className={"view-utilization-btn"} 
        label={"View Utilisation Statement"}>
        </Button>
        <Card>Total amount</Card>
      </Card>
      <MeasureTable {...tableData} isView = {true} measureData = {measures}/>
      <ApplicationDetails
        applicationDetails={imageDetails}
        isLoading={isLoading}
        applicationData={applicationDetails?.applicationData}
        moduleCode="contracts"
        workflowDetails={applicationDetails?.workflowDetails}
        mutate={() => {}}
        tenantId={tenantId}
        showTimeLine={false}
      />
      <ApplicationDetails
        applicationData={applicationDetails?.applicationData}
        moduleCode="contracts"
        workflowDetails={applicationDetails?.workflowDetails}
        mutate={() => {}}
        tenantId={tenantId}
        showTimeLine={true}
      />
    </React.Fragment>
  );
};

export default ViewMeasurement;
