import React, { useState, useEffect } from "react";
import _ from "lodash";
import { Loader, Modal, FormComposer } from "@egovernments/digit-ui-react-components";
import { configAttendanceApproveModal, configAttendanceRejectModal, configAttendanceCheckModal } from "../config";


const Heading = (props) => {
  return <h1 className={props.className ? `heading-m ${props.className}` : "heading-m"}>{props.label}</h1>;
};

const Close = () => (
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#FFFFFF">
    <path d="M0 0h24v24H0V0z" fill="none" />
    <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12 19 6.41z" />
  </svg>
);

const CloseBtn = (props) => {
  return (
    <div className="icon-bg-secondary" onClick={props.onClick}>
      <Close />
    </div>
  );
};

const AttendanceActionModal = ({ t, action, tenantId, state, id, closeModal, submitAction, actionData, applicationData, businessService, moduleCode,applicationDetails,workflowDetails }) => {
  
  let { loiNumber, estimateNumber, musterRollNumber } = Digit.Hooks.useQueryParams();
   const [config, setConfig] = useState({});

  const userUuid = Digit.UserService.getUser()?.info.uuid;
  const { isLoading, data:employeeData } = Digit.Hooks.hrms.useHRMSSearch(
    { uuids : userUuid }, tenantId
  );

  const empData =  employeeData?.Employees[0]
  const empDepartment = empData?.assignments?.[0].department
  const empDesignation = empData?.assignments?.[0].designation
  const empName = empData?.user?.name

  useEffect(() => {
    const selectedAction = action?.action
    switch(selectedAction) {
      case "VERIFY":
        //Check what to show on Verify
        break;
      case "REJECT":
        setConfig(
          configAttendanceRejectModal({
            t,
            action,
            empDepartment,
            empDesignation,
            empName
          })
        )
        break;
      case "APPROVE":
        setConfig(
          configAttendanceApproveModal({
            t,
            action
          })
        )
      default:
        break
    }
  }, [employeeData]);

  
  function onSubmit (data) {
    console.log('FormData', data, applicationDetails);
    const musterRoll = { tenantId, id: applicationDetails?.applicationDetails?.[0]?.applicationData?.id}
    const workflow = { action: action?.action, comments: data?.comments}
    const dataTobeSubmitted = {musterRoll, workflow}
    console.log('dataTobeSubmitted', dataTobeSubmitted);
    //PR: api to update muster roll with actions
    // submitAction({letterOfIndent:applicationData,workflow})}
  }

  const cardStyle = () => {
    if(config.label.heading === "Processing Details") {
      return {
        "padding" : "0px"
      }
    }
    return {}
  }

  return action && config?.form ? (
    <Modal
      headerBarMain={<Heading label={t(config.label.heading)} className="header-left-margin" />}
      headerBarEnd={<CloseBtn onClick={closeModal} />}
      actionCancelLabel={t(config.label.cancel)}
      actionCancelOnSubmit={closeModal}
      actionSaveLabel={t(config.label.submit)}
      actionSaveOnSubmit={() => {}}
      formId="modal-action"
    >
      <FormComposer
        config={config.form}
        noBoxShadow
        inline
        childrenAtTheBottom
        onSubmit={onSubmit}
        formId="modal-action"
        cardStyle = {cardStyle()}
      />
    </Modal>
  ) : (
    <Loader />
  );
}

export default AttendanceActionModal