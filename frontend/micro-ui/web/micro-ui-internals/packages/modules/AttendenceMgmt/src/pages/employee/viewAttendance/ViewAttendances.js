import React, {useState, useEffect} from "react";
import { useTranslation } from "react-i18next";
import { Header, Toast } from "@egovernments/digit-ui-react-components";
import ApplicationDetails from "../../../../../templates/ApplicationDetails";

const ViewAttendance = () => {
  const { t } = useTranslation();
  const { tenantId, musterRollNumber } = Digit.Hooks.useQueryParams();
  const [showToast, setShowToast] = useState(null);
  const [showEditTitle, setshowEditTitle] = useState(false);
  const [showDataError, setShowDataError] = useState(null)
  const [modify, setModify] = useState(false);

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

  useEffect(() => {
    if(isError) {
      setShowDataError(true)
    }
  }, [error])
 
  return (
    <React.Fragment>
      <Header>{showEditTitle ? t('ATM_EDIT_ATTENDENCE') : t("ATM_VIEW_ATTENDENCE")}</Header>
      {
        showDataError === null && <ApplicationDetails
          applicationDetails={data?.applicationDetails}
          isLoading={isLoading}
          applicationData={data?.applicationData}
          moduleCode="AttendenceMgmt"
          isDataLoading={false}
          workflowDetails={workflowDetails}
          showTimeLine={true}
          timelineStatusPrefix={"ATM_"}
          businessService={"muster-roll-approval"}
          forcedActionPrefix={"ATM"}
          mutate={mutate}
          showToast={showToast}
          setShowToast={setShowToast}
          closeToast={closeToast}
          tenantId={tenantId}
          applicationNo={musterRollNumber}
          setshowEditTitle={setshowEditTitle}
          modify={modify}
          setModify={setModify}
      />}
      {
        showDataError && <Toast error={true} label={t("COMMON_ERROR_FETCHING_MUSTER_DETAILS")} isDleteBtn={true} onClose={() => setShowDataError(false)} />
      }
    </React.Fragment>
    );
};

export default ViewAttendance;
