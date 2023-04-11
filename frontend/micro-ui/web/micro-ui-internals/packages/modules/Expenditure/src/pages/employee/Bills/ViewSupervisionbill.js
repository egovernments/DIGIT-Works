import React, { Fragment } from "react";
import ApplicationDetails from "../../../../../templates/ApplicationDetails";
import { useTranslation } from "react-i18next";

const ViewSupervisionbill = ({ ...props }) => {
  const { t } = useTranslation();
  const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("expenditure");
  const { tenantId } = Digit.Hooks.useQueryParams();

  const { isLoading, data: applicationDetails, isError } = Digit.Hooks.bills.useSupervisionBillScreen({ t });

  return (
    <>
      <ApplicationDetails
        applicationDetails={applicationDetails}
        isLoading={isLoading}
        applicationData={applicationDetails?.applicationData}
        moduleCode="Expenditure"
        showTimeLine={false}
        timelineStatusPrefix={"WF_ESTIMATE_STATUS_"}
        businessService={businessService}
        // forcedActionPrefix={"ACTION_"}
        tenantId={tenantId}
        applicationNo={""}
        statusAttribute={"state"}
      />
    </>
  );
};

export default ViewSupervisionbill;
