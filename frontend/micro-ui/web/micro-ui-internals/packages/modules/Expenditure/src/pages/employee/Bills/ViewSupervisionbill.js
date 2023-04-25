import React, { Fragment } from "react";
import ApplicationDetails from "../../../../../templates/ApplicationDetails";
import { useTranslation } from "react-i18next";
import { Header } from "@egovernments/digit-ui-react-components";

const ViewSupervisionbill = ({ ...props }) => {
  const { t } = useTranslation();
  const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessServiceV1("expenditure");
  const { tenantId } = Digit.Hooks.useQueryParams();

  const { isLoading, data: applicationDetails, isError } = Digit.Hooks.bills.useSupervisionBillScreen({ t });

  return (
    <>
      <Header>{t("BILLS_SUPERVISION")}</Header>
      <ApplicationDetails
        applicationDetails={applicationDetails?.applicationDetailsCardOne}
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

      <ApplicationDetails
        applicationDetails={applicationDetails?.applicationDetailsCardTwo}
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
