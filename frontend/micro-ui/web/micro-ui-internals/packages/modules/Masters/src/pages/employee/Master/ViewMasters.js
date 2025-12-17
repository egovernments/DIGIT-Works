import { Header } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";
import ApplicationDetails from "../../../../../templates/ApplicationDetails";

const ViewMasters = () => {
  const { t } = useTranslation();
  const { applicationDetails, applicationData, workflowDetails } = Digit.Hooks.masters.useViewOrg({}); //pass required inputs when backend service is ready.

  return (
    <React.Fragment>
      <div className={"employee-main-application-details"}>
        <div className={"employee-application-details"} style={{ marginBottom: "24px" }}>
          <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("MASTERS_VIEW_COMMUNITY_ORG")}</Header>
        </div>
        <ApplicationDetails
          applicationDetails={applicationDetails}
          isLoading={false} //will come from backend
          applicationData={applicationData}s
          moduleCode="Masters"
          isDataLoading={false}
          workflowDetails={workflowDetails}
          showTimeLine={true}
          timelineStatusPrefix={""}
          businessService={""}
          forcedActionPrefix={"MASTERS"}
        />
      </div>
    </React.Fragment>
  );
}

export default ViewMasters;