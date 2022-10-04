import { Loader, Modal, FormComposer } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { configApproveModal, configRejectModal, configCheckModal } from "../config";

import cloneDeep from "lodash/cloneDeep";


const Heading = (props) => {
  return <h1 className="heading-m">{props.label}</h1>;
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

const convertDateToEpochNew = (dateString, dayStartOrEnd = "dayend") => {
  //example input format : "2018-10-02"
  try {
    const parts = dateString.match(/(\d{4})-(\d{1,2})-(\d{1,2})/);
    const DateObj = new Date(Date.UTC(parts[1], parts[3] - 1, parts[2]));

    DateObj.setMinutes(DateObj.getMinutes() + DateObj.getTimezoneOffset());
    if (dayStartOrEnd === "dayend") {
      DateObj.setHours(DateObj.getHours() + 24);
      DateObj.setSeconds(DateObj.getSeconds() - 1);
    }
    return DateObj.getTime();
  } catch (e) {
    return dateString;
  }
};


const WorksActionModal = ({ t, action, tenantId, state, id, closeModal, submitAction, actionData, applicationData, businessService, moduleCode,applicationDetails,workflowDetails }) => {
  
  //here according to the action selected render appropriate modal

  // const { data: approverData, isLoading: PTALoading } = Digit.Hooks.useEmployeeSearch(
  //   tenantId,
  //   {
  //     roles: action?.assigneeRoles?.map?.((e) => ({ code: e })),
  //     isActive: true,
  //   },
  //   { enabled: !action?.isTerminateState }
  // );

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

  //get approverDept,designation,approver(hrms),rejectionReason

  const rejectReasons = [
    {
      name: "Estimate Details are incorrect"
    },
    {
      name: "Financial Details are incorrect"
    },
    {
      name: "Agreement Details are incorrect"
    },
    {
      name: "Vendor Details are incorrect"
    },
    {
      name: "Attachments provided are wrong"
    },
    {
      name: "Others"
    },
  ]

  const { isLoading: mdmsLoading, data: mdmsData,isSuccess:mdmsSuccess } = Digit.Hooks.useCustomMDMS(
    Digit.ULBService.getCurrentTenantId(),
    "common-masters",
    [
      {
        "name": "Designation"
      },
      {
        "name": "Department"
      }
    ]
  );

  const { data: approverData, isLoading: approverLoading } = Digit.Hooks.useEmployeeSearch(
    tenantId,
    {
      roles: action?.assigneeRoles?.map?.((e) => ({ code: e })),
      isActive: true,
    },
    { enabled: !action?.isTerminateState }
  );
    
  
  // const { isLoading: approverLoading, isError,isSuccess:approverSuccess, error, data: employeeDatav1 } = Digit.Hooks.hrms.useHRMSSearch({ Designation: selectedDesignation?.code, Department: selectedDept?.code }, Digit.ULBService.getCurrentTenantId(), null, null, { enabled: !!(selectedDept?.code && selectedDesignation?.code) });
  // employeeDatav1?.Employees.map(emp => emp.nameOfEmp = emp.user.name)

  
  useEffect(() => {
    
    setApprovers(approverData?.Employees?.map((employee) => ({ uuid: employee?.uuid, name: employee?.user?.name })));
  }, [approverData]);

  useEffect(() => {
    
    //setApprovers(approverData?.Employees?.map((employee) => ({ uuid: employee?.uuid, name: employee?.user?.name })));
    //setApprovers(employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees : [])
    setDepartment(mdmsData?.["common-masters"]?.Department)
    setDesignation(mdmsData?.["common-masters"]?.Designation)
    setRejectionReason(rejectReasons)
  }, [mdmsData]);


  

  useEffect(() => {
    
    if(action?.action?.includes("CHECK")){
      setConfig(
        configCheckModal({
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
          setSelectedDept
        })
      )
    }else if(action?.action?.includes("APPROVE")){
      setConfig(
        configApproveModal({
          t,
          action
        })
      )
    }
    else if(action?.action?.includes("REJECT")){
      setConfig(
        configRejectModal({
          t,
          action,
          rejectReasons,
          loiId,
          department
        })
      )
    }
  }, [approvers,designation,department]);

  
  const submit = (_data) => {

  }

  // if(mdmsLoading || approverLoading ) {
  //   return <Loader />
  // } 

  

  return action && config?.form  ? (
    <Modal
      headerBarMain={<Heading label={t(config.label.heading)} />}
      headerBarEnd={<CloseBtn onClick={closeModal} />}
      actionCancelLabel={t(config.label.cancel)}
      actionCancelOnSubmit={closeModal}
      actionSaveLabel={t(config.label.submit)}
      actionSaveOnSubmit={() => { }}
      formId="modal-action"
    >
      {approverLoading ? (
        <Loader />
      ) : (
        <FormComposer
          config={config.form}
          noBoxShadow
          inline
          childrenAtTheBottom
          onSubmit={submit}
          //defaultValues={defaultValues}
          formId="modal-action"
        />
      )}
    </Modal>
  ) : (
    <Loader />
  );
}

export default WorksActionModal