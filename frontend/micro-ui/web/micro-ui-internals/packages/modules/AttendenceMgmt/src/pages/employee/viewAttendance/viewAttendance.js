import { Header } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";
import ApplicationDetails from "../../../../../templates/ApplicationDetails";

const ViewAttendance = () => {
  const { t } = useTranslation();
  const { applicationDetails, applicationData, workflowDetails } = Digit.Hooks.attendance.useViewAttendance({}); //pass required inputs when backend service is ready.

  return (
    <React.Fragment>
      <div className={"employee-main-application-details"}>
        <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
          <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("ATM_VIEW_ATTENDENCE")}</Header>
        </div>
        <ApplicationDetails
          applicationDetails={applicationDetails}
          isLoading={false} //will come from backend
          applicationData={applicationData}
          moduleCode="AttendenceMgmt"
          isDataLoading={false}
          workflowDetails={workflowDetails}
          showTimeLine={true}
          timelineStatusPrefix={""}
          businessService={""}
          forcedActionPrefix={"WORKS"}
        />
      </div>
    </React.Fragment>
  );
};

export default ViewAttendance;
