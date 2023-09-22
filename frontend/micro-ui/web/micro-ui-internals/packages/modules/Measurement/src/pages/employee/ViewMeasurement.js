
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
  console.log(tenantId);
  const contractNumber = { contractNumber: "WO/2023-24/000351" };
  const subEstimateNumber = "EP/2022-23/09/000094/000070";
  const searchparams = new URLSearchParams(location.search);
  const workOrderNumber = searchparams.get("workOrderNumber")

  const payload = {
    contractNumber: workOrderNumber,
    tenantId: tenantId,
  };
  let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.measurement.useViewMeasurement(tenantId, payload);

  const projectDetails = {"applicationDetails" : [applicationDetails?.applicationDetails?.applicationDetails[0]]};
  const imageDetails = {"applicationDetails" : [applicationDetails?.applicationDetails?.applicationDetails[2]]};




  return (
    <React.Fragment>
      <Header className="works-header-view">{t("MB_VIEW_MEASUREMENT_BOOK")}</Header>
      <ApplicationDetails
        applicationDetails={projectDetails}
        isLoading={isLoading}
        applicationData={applicationDetails?.applicationData}
        moduleCode="contracts"
        isDataLoading={false}
        workflowDetails={applicationDetails?.workflowDetails}
        mutate={() => {}}
        tenantId={tenantId}
        showTimeLine={false}
      />
      <MeasurementHistory contractNumber={"WO/2023-24/000784"} />
      <Card className="override-card">
        <Button className={"jk-digit-secondary-btn"} label={"View Utilisation Details"}></Button>
      </Card>
      <ApplicationDetails
        applicationDetails={imageDetails}
        isLoading={isLoading}
        applicationData={applicationDetails?.applicationData}
        moduleCode="contracts"
        isDataLoading={false}
        workflowDetails={applicationDetails?.workflowDetails}
        mutate={() => {}}
        tenantId={tenantId}
        showTimeLine={false}
      />
    </React.Fragment>
  );
};

export default ViewMeasurement;
