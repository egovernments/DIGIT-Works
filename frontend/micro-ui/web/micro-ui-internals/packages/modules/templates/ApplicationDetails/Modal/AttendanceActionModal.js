import { Loader, Modal, FormComposer } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
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

  let { loiNumber, estimateNumber } = Digit.Hooks.useQueryParams();
   const [config, setConfig] = useState({});
   const [defaultValues, setDefaultValues] = useState({});
   const [approvers, setApprovers] = useState([]);
   const [selectedApprover, setSelectedApprover] = useState({});
   
   const [department, setDepartment] = useState([]);
   const [selectedDept,setSelectedDept] = useState({})

   const [rejectionReason, setRejectionReason] = useState([]);
   const [selectedReason,setSelectedReason] = useState([])

   const [designation, setDesignation] = useState([]);
   const [selectedDesignation,setSelectedDesignation] = useState({})

  const { isLoading: mdmsLoading, data: mdmsData,isSuccess:mdmsSuccess } = Digit.Hooks.useCustomMDMS(
    Digit.ULBService.getCurrentTenantId(),
    "common-masters",
    [
      // {
      //   "name": "Designation"
      // },
      // {
      //   "name": "Department"
      // },
      {
        "name": "RejectReasons"
      }
    ]
  );
    debugger;
  mdmsData?.["common-masters"]?.Designation?.map(designation => {
    designation.i18nKey = `ES_COMMON_DESIGNATION_${designation?.name}`
  })

  mdmsData?.["common-masters"]?.Department?.map(department => {
    department.i18nKey = `ES_COMMON_${department?.code}`
  })

  mdmsData?.["common-masters"]?.RejectReasons?.map(rejectReasons => {
    rejectReasons.i18nKey = `ES_ATM_${rejectReasons?.code}`
  })


  useEffect(() => {
    setDepartment(mdmsData?.["common-masters"]?.Department)
    setDesignation(mdmsData?.["common-masters"]?.Designation)
    setRejectionReason(mdmsData?.["common-masters"]?.RejectReasons)
  }, [mdmsData]);


  
  const { isLoading: approverLoading, isError, error, data: employeeDatav1 } = Digit.Hooks.hrms.useHRMSSearch({ designations: selectedDesignation?.code, departments: selectedDept?.code, roles: action?.assigneeRoles?.toString(), isActive: true }, Digit.ULBService.getCurrentTenantId(), null, null, { enabled: action?.action === "CHECK" || action?.action === "TECHNICALSANCATION"});


  employeeDatav1?.Employees.map(emp => emp.nameOfEmp = emp?.user?.name || "NA")
  
  useEffect(() => {
    setApprovers(employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees.filter(emp => emp?.nameOfEmp !== "NA") : [])
  }, [employeeDatav1])
  
  useEffect(() => {
    
    if(action?.action?.includes("CHECK") || action?.action?.includes("TECHNICALSANCATION")){
      setConfig(
        configAttendanceCheckModal({
          t,
          action,
          businessService,
          approvers,
          selectedApprover,
          setSelectedApprover,
          designation,
          selectedDesignation,
          setSelectedDesignation,
          department,
          selectedDept,
          setSelectedDept,
          approverLoading
        })
      )
    }else if(action?.action?.includes("APPROVE") || action?.action?.includes("ADMINSANCTION")){
      setConfig(
        configAttendanceApproveModal({
          t,
          action
        })
      )
    }
    else if(action?.action?.includes("REJECT")){
      setConfig(
        configAttendanceRejectModal({
          t,
          action,
          rejectionReason,
          selectedReason,
          setSelectedReason,
          loiNumber,
          department,
          estimateNumber
        })
      )
    }
  }, [approvers,designation,department]);

  
  function submit (_data) {
    const workflow = {
      action: action?.action,
      comment: _data?.comments,
      assignees: selectedApprover?.uuid ? [selectedApprover?.uuid] : undefined
    }

    if(action?.action.includes("REJECT")) {
      workflow.assignee = [applicationData?.auditDetails?.createdBy]
    }

    Object.keys(workflow).forEach(key => {
      if (workflow[key] === undefined) {
        delete workflow[key];
      }
    });
    {estimateNumber ? submitAction({estimate:applicationData,workflow}) :
    submitAction({letterOfIndent:applicationData,workflow})}
    
  }

  const cardStyle = () => {
    if(config.label.heading === "Processing Details") {
      return {
        "padding" : "0px"
      }
    }
    return {}
  }

  return action && config?.form  ? (
    <Modal
      headerBarMain={<Heading label={t(config.label.heading)} className="header-left-margin" />}
      headerBarEnd={<CloseBtn onClick={closeModal} />}
      actionCancelLabel={t(config.label.cancel)}
      actionCancelOnSubmit={closeModal}
      actionSaveLabel={t(config.label.submit)}
      actionSaveOnSubmit={() => { }}
      formId="modal-action"
    >
      {mdmsLoading ? (
        <Loader />
      ) : (
        <FormComposer
          config={config.form}
          noBoxShadow
          inline
          childrenAtTheBottom
          onSubmit={submit}
          defaultValues={config?.defaultValues}
          formId="modal-action"
          cardStyle = {cardStyle()}
        />
      )}
    </Modal>
  ) : (
    <Loader />
  );
}

export default AttendanceActionModal