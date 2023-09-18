import { Card } from "@egovernments/digit-ui-react-components";
import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import ApplicationDetails from "../../../../templates/ApplicationDetails";
import MeasurementHistory from "../../components/MBHistoryTable";

const ViewMeasurement = () => {
  const { t } = useTranslation();
  const history = useHistory();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  console.log(tenantId);
  const contractNumber = { contractNumber: "WO/2023-24/000783" };
  const subEstimateNumber = "EP/2022-23/09/000094/000070";

  const payload = {
    contractNumber: "WO/2023-24/000783",
    tenantId: tenantId,
  };
  let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.measurement.useViewMeasurement(
    tenantId,
    payload
  );

  console.log(applicationDetails);

  return (
    <React.Fragment>
      <Card>
        View
        <ApplicationDetails
          applicationDetails={applicationDetails?.applicationDetails}
          isLoading={isLoading}
          applicationData={applicationDetails?.applicationData}
          moduleCode="contracts"
          isDataLoading={false}
          workflowDetails={applicationDetails?.workflowDetails}
          mutate={() => {}}
          tenantId={tenantId}
          showT
        />
         <MeasurementHistory contractNumber={"WO/2023-24/000784"}/>
      </Card>
    </React.Fragment>
  );
};

export default ViewMeasurement;
