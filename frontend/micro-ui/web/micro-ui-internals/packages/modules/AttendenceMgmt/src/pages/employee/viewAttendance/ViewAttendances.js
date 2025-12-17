import React, {useState, useEffect} from "react";
import { useTranslation } from "react-i18next";
import { Header,WorkflowActions,Loader,ViewDetailsCard,MultiLink } from "@egovernments/digit-ui-react-components";
import ApplicationDetails from "../../../../../templates/ApplicationDetails";
import WarningPopUp from "../../../pageComponents/WarningPopUp";
import {Toast,Button} from '@egovernments/digit-ui-components'
import { useHistory } from "react-router-dom";

const ViewAttendance = () => {
  const { t } = useTranslation();
  const { tenantId, musterRollNumber } = Digit.Hooks.useQueryParams();
  const history = useHistory()
  const [showToast, setShowToast] = useState(null);
  const [showEditTitle, setshowEditTitle] = useState(false);
  const [showDataError, setShowDataError] = useState(null)
  const [showPopup, setShowPopUp] = useState(null);
  const [modify, setModify] = useState(false);
  const [cardState,setCardState] = useState([])
  const [saveAttendanceState, setSaveAttendanceState] = useState({ displaySave : false, updatePayload: []})
  const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("muster roll")

  const [isStateChanged, setStateChanged] = useState(``)

  const closeToast = () => {
      setShowToast(null);
  };

  const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.attendance.useViewAttendance(tenantId, { musterRollNumber },{},isStateChanged);

  const requestCriteria = {
    url : "/mukta-services/musterRollValidations/_validate",

    body: {
      "tenantId" : tenantId,
      "musterRollNumber": musterRollNumber,
    }

  }
  const {isLoading: isMbValidationLoading, data: mbValidationMr} = Digit.Hooks.useCustomAPIHook(requestCriteria);

  const { isLoading: approverLoading, isErrorApprover, errorApprover, data: employeeDatav1 } = Digit.Hooks.hrms.useHRMSSearch({ roles: "MUSTER_ROLL_VERIFIER", isActive: true }, Digit.ULBService.getCurrentTenantId(), null, null, { enabled:true });


  employeeDatav1?.Employees.map(emp => emp.nameOfEmp = emp?.user?.name || "NA")

  const { mutate } = Digit.Hooks.attendance.useUpdateAttendance();

  const HandleDownloadPdf = () => {
    Digit.Utils.downloadEgovPDF('musterRoll/muster-roll',{musterRollNumber,tenantId},`Muster-roll-${musterRollNumber}.pdf`)
  }

  useEffect(() => {
    if(isError) {
      setShowDataError(true)
    }
  }, [error])
 
  useEffect(() => {
      const muster = data?.applicationData
        setCardState([
            {
                title: '',
                values: [
                  { title: "ATM_MUSTER_ROLL_ID", value: muster?.musterRollNumber || t("ES_COMMON_NA") },
                  { title: "WORKS_MB_NUMBER", value: mbValidationMr?.musterRollValidation?.[0]?.measurementNumber || t("ES_COMMON_NA"), isValueLink: mbValidationMr?.musterRollValidation?.[0]?.measurementNumber ? true : false, navigateLinkHandler: () => history.push(`/${window.contextPath}/employee/measurement/view?tenantId=${tenantId}&workOrderNumber=${muster?.additionalDetails?.contractId}&mbNumber=${mbValidationMr?.musterRollValidation?.[0]?.measurementNumber}`) },
                  { title: "WORKS_ORDER_NO", value: muster?.additionalDetails?.contractId || t("ES_COMMON_NA") },
                  { title: "WORKS_PROJECT_ID", value: muster?.additionalDetails?.projectId || t("ES_COMMON_NA") },
                  { title: "PROJECTS_DESCRIPTION", value: muster?.additionalDetails?.projectDesc || t("ES_COMMON_NA")},
                  { title: "COMMON_NAME_OF_CBO", value: muster?.additionalDetails?.orgName || t("ES_COMMON_NA") },
                  { title: "COMMON_ROLE_OF_CBO", value: t(`COMMON_MASTERS_${muster?.additionalDetails?.executingAuthority}`) || t("ES_COMMON_NA") },
                  { title: "ES_COMMON_MUSTER_ROLL_PERIOD", value: `${Digit.DateUtils.ConvertTimestampToDate(muster?.startDate, 'dd/MM/yyyy')} - ${Digit.DateUtils.ConvertTimestampToDate(muster?.endDate, 'dd/MM/yyyy')}` },
                  { title: "MUSTER_ROLLS_NO_OF_WAGE_SEEKERS", value: muster?.individualEntries.length || t("ES_COMMON_NA") },
                  { title: "MUSTER_ROLLS_TOTAL_ATTENDANCE_IN_DAYS", value: muster?.individualEntries?.reduce((acc,row)=>acc + (row?.actualTotalAttendance || row?.modifiedTotalAttendance || 0),0) || "0" },
                  { title: "MUSTER_ROLLS_QUANTITY_OF_WORK_IN_DAYS", value: muster?.individualEntries?.reduce((acc,row)=>acc + ( row?.modifiedTotalAttendance || row?.actualTotalAttendance || 0),0) || "0" },
                  { title: "WORKS_TOTAL_LABOUR_UTILIZATION", value:Digit.Utils.dss.formatterWithoutRound(isNaN(mbValidationMr?.musterRollValidation?.[0]?.totalLabourRate) ? 0 : parseFloat(mbValidationMr?.musterRollValidation?.[0]?.totalLabourRate).toFixed(2), "number",undefined, true, undefined, 2) || t("ES_COMMON_NA") },
                  { title: "MUSTER_TOTAL_WAGE_AMOUNT", value:Digit.Utils.dss.formatterWithoutRound(muster?.totalAmount, "number") || t("ES_COMMON_NA") },
                ]
              }
        ])
    }, [data,mbValidationMr])

  if(isLoading || approverLoading || isMbValidationLoading) return <Loader />
  return (
    <React.Fragment>
      <div className={"employee-application-details"} style={{ marginBottom: "24px",alignItems:"center" }}>
        <Header styles={{}} className={`works-header-view ${showEditTitle ? "" : "works_attendance_view"}`}>{showEditTitle ? t("ATM_EDIT_ATTENDENCE") : t("ATM_VIEW_ATTENDENCE")}</Header>
        {/* <MultiLink
         onHeadClick={() => HandleDownloadPdf()}
         downloadBtnClassName={"employee-download-btn-className"}
         label={t("CS_COMMON_DOWNLOAD")}
        /> */}
        <Button
          label={t("CS_COMMON_DOWNLOAD")}
          onClick={() => HandleDownloadPdf()}
          className={"employee-download-btn-className"}
          variation={"teritiary"}
          type="button"
          icon={"FileDownload"}
        />
      </div>

      {data && <ViewDetailsCard cardState={cardState} t={t} />}

      {showDataError === null && (
        <ApplicationDetails
          applicationDetails={data?.applicationDetails}
          isLoading={isLoading}
          applicationData={data?.applicationData}
          moduleCode="AttendenceMgmt"
          isDataLoading={false}
          showTimeLine={true}
          timelineStatusPrefix={`WF_${businessService}_`}
          businessService={businessService}
          forcedActionPrefix={`WF_${businessService}_ACTION`}
          mutate={mutate}
          showToast={showToast}
          setShowToast={setShowToast}
          closeToast={closeToast}
          tenantId={tenantId}
          applicationNo={musterRollNumber}
          modify={modify}
          setshowEditTitle={setshowEditTitle}
          saveAttendanceState={saveAttendanceState}
          setSaveAttendanceState={setSaveAttendanceState}
          approverList={employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees.filter((emp) => emp?.nameOfEmp !== "NA") : []}
        />
      )}

      {isSuccess && !modify && (
        <WorkflowActions
          forcedActionPrefix={`WF_${businessService}_ACTION`}
          businessService={businessService}
          applicationNo={musterRollNumber}
          tenantId={tenantId}
          applicationDetails={data?.applicationData}
          url={Digit.Utils.Urls.attendencemgmt.mustorRoll.update}
          setStateChanged={setStateChanged}
          moduleCode="attendencemgmt"
          editApplicationNumber={""}
          WorflowValidation={(setShowModal) => {
            try {
              let validationFlag = false;
              for (const validation of mbValidationMr?.musterRollValidation) {
                if (validation?.type === "error") {
                  validationFlag = true;
                  setShowToast({ type: "error", label: t(validation?.message) });
                  break;
                } else if (validation?.type === "warn") {
                  validationFlag = true;
                  setShowPopUp({ setShowWfModal: setShowModal, label: t(validation?.message) });
                  break;
                }
              }
              if (!validationFlag) setShowModal(true);
            } catch (error) {
              showToast(error.message);
            }
          }}
          editCallback={() => {
            setModify(true);
            setshowEditTitle(true);
            setSaveAttendanceState((prevState) => {
              return {
                ...prevState,
                displaySave: true,
                updatePayload: data?.applicationData?.individualEntries?.map((row) => {
                  return {
                    totalAttendance: row?.modifiedTotalAttendance || row?.actualTotalAttendance,
                    id: row?.id,
                  };
                }),
              };
            });
          }}
        />
      )}
      {showPopup && <WarningPopUp setShowWfModal={showPopup?.setShowWfModal} label={showPopup?.label} setShowPopUp={setShowPopUp} t={t} />}
      {showToast && <Toast type={showToast?.type} label={showToast?.label} isDleteBtn={true} onClose={() => closeToast()} />}
      {showDataError && (
        <Toast type={"error"} label={t("COMMON_ERROR_FETCHING_MUSTER_DETAILS")} isDleteBtn={true} onClose={() => setShowDataError(false)} />
      )}
    </React.Fragment>
  );
};

export default ViewAttendance;
