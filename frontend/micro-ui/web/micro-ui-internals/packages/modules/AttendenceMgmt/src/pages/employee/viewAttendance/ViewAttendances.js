import React, {useState} from "react";
import { useTranslation } from "react-i18next";
import { Header } from "@egovernments/digit-ui-react-components";
import ApplicationDetails from "../../../../../templates/ApplicationDetails";

const ViewAttendance = () => {
  const { t } = useTranslation();
  const { tenantId, musterRollNumber } = Digit.Hooks.useQueryParams();
  const [showToast, setShowToast] = useState(null);
  const [showEditTitle, setshowEditTitle] = useState(false);

  const closeToast = () => {
      setShowToast(null);
  };

  const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.attendance.useViewAttendance(tenantId, { musterRollNumber });
  
  let workflowDetails = Digit.Hooks.useWorkflowDetails(
    {
        tenantId: tenantId,
        id: musterRollNumber,
        moduleCode: data?.processInstancesDetails?.[0]?.businessService,
        config: {
            enabled:data?.processInstancesDetails?.[0]?.businessService ? true : false,
            cacheTime:0
        }
    }
  );

  const { mutate } = Digit.Hooks.attendance.useUpdateAttendance();

  return (
    <React.Fragment>
      <Header>{showEditTitle ? t('ATM_EDIT_ATTENDENCE') : t("ATM_VIEW_ATTENDENCE")}</Header>
      <ApplicationDetails
        applicationDetails={data?.applicationDetails}
        isLoading={isLoading}
        applicationData={data?.applicationData}
        moduleCode="AttendenceMgmt"
        isDataLoading={false}
        workflowDetails={workflowDetails}
        showTimeLine={true}
        timelineStatusPrefix={""}
        businessService={"muster-roll-approval"}
        forcedActionPrefix={"ATM"}
        mutate={mutate}
        showToast={showToast}
        setShowToast={setShowToast}
        closeToast={closeToast}
        tenantId={data?.applicationData?.tenantId}
        applicationNo={data?.applicationData?.musterRollNumber}
        setshowEditTitle={setshowEditTitle}
      />
    </React.Fragment>
    );
};

export default ViewAttendance;
