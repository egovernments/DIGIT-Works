import React from "react";
import { useTranslation } from "react-i18next";
import { Header } from "@egovernments/digit-ui-react-components";
import ApplicationDetails from "../../../../../templates/ApplicationDetails";

const ViewAttendance = () => {
  const { t } = useTranslation();
  const { tenantId, musterRollNumber } = Digit.Hooks.useQueryParams();

  //const { applicationDetails, applicationData, workflowDetails } = Digit.Hooks.attendance.useViewAttendance(tenantId, musterRollNumber);
  const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.attendance.useViewAttendance(tenantId, { musterRollNumber });
  console.log('@@Data', isLoading, data, isError, isSuccess, error);
  const dummyDate = Digit.DateUtils.ConvertEpochToDate(1669919400000)
  console.log('dummyDate', dummyDate);
  return (
    <React.Fragment>
      <Header>{t("ATM_VIEW_ATTENDENCE")}</Header>
      <ApplicationDetails
        applicationDetails={data?.applicationDetails}
        isLoading={isLoading}
        applicationData={data?.applicationData}
        moduleCode="AttendenceMgmt"
        isDataLoading={false}
        workflowDetails={data?.workflowDetails}
        showTimeLine={true}
        timelineStatusPrefix={""}
        businessService={"MUKTAWORKS"}
        forcedActionPrefix={"WORKS"}
      />
    </React.Fragment>
  );
};

export default ViewAttendance;
