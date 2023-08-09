import React, { Fragment, useState, useEffect } from "react";
import { Loader, ActionBar, SubmitBar, WorkflowModal, LabelFieldPair, CardLabel, TextInput, Toast } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import ApplicationDetails from "../../../../templates/ApplicationDetails";
import getModalConfig from "./modalConfig";
import { useHistory } from "react-router-dom";
const CreateTimeExtension = (props) => {
  const history = useHistory()
  const { applicationData: contractObject, tenantId } = props.data;
  //removing documents section
  delete props.data.applicationDetails.applicationDetails[1];

  const { t } = useTranslation();
  const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("contract");
  const [modalConfig, setModalConfig] = useState({});
  const rolesForThisAction = "WORK_ORDER_VERIFIER";
  const [approvers, setApprovers] = useState([]);
  const [selectedApprover, setSelectedApprover] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [extensionRequested, setExtensionRequested] = useState(null);
  const [reasonForExtension, setReasonForExtension] = useState(null);
  const [showToast, setShowToast] = useState(null);

  const { isLoading: approverLoading, isError, error, data: employeeDatav1 } = Digit.Hooks.hrms.useHRMSSearch(
    { roles: rolesForThisAction, isActive: true },
    Digit.ULBService.getCurrentTenantId(),
    null,
    null,
    { enabled: true }
  );

  employeeDatav1?.Employees.map((emp) => (emp.nameOfEmp = emp?.user?.name || "NA"));

  useEffect(() => {
    setApprovers(employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees.filter((emp) => emp?.nameOfEmp !== "NA") : []);
  }, [employeeDatav1]);

  useEffect(() => {
    setModalConfig(
      getModalConfig({
        t,
        approvers,
        selectedApprover,
        setSelectedApprover,
        approverLoading,
      })
    );
  }, [approvers]);

  const reqCriteriaUpdate = {
    url: `/contract/v1/_create`,
    params: {},
    body: {},
    config: {
      enabled: true,
    },
  };

  const mutation = Digit.Hooks.useCustomAPIMutationHook(reqCriteriaUpdate);

  const onModalSubmit = async (data) => {
    //create TE payload here and call the createMutation for TE and route to response page on onSuccess/onError
    
    const onError = (resp) => {
      
      history.push(`/${window.contextPath}/employee/contracts/create-time-extension-response?isSuccess=${false}`,{message:"TE_CREATION_FAILED"})
    };

    const onSuccess = (resp) => {
      
      history.push(`/${window.contextPath}/employee/contracts/create-time-extension-response?revisedWONumber=${resp.contracts[0].supplementNumber}&isSuccess=${true}`,{message:"TE_CREATION_SUCCESS",showID:true,label:"REVISED_WO_NUMBER"})
    };

    mutation.mutate(
      {
        params: {},
        body: {
          contract: { ...contractObject,
            businessService : "CONTRACT-REVISION",
            endDate:(extensionRequested * 86400000) + contractObject.endDate,
            additionalDetails:{...contractObject.additionalDetails,timeExtReason:reasonForExtension,timeExt:extensionRequested}
          },
          workflow: {
            action: "CREATE",
            assignees: selectedApprover?.uuid ? [selectedApprover.uuid] : [],
            comment:data?.comments ? data.comments : null
          },
        },
      },
      {
        onError,
        onSuccess,
      }
    );
  };

  const closeToast = () => {
    setTimeout(() => {
      setShowToast(null);
    }, 5000);
  };

  const handleSubmit = () => {
    if (!extensionRequested || !reasonForExtension || extensionRequested <= 0) {
      setShowToast({
        label: "TE_SUBMIT_VALIDATION",
        isError: true,
      });
      closeToast();
      return;
    }
    setShowModal(true);
  };

  const getTimeExtensionJSX = () => {
    return (
      <>
        <LabelFieldPair style={{ marginTop: "1rem" }}>
          <CardLabel style={{ fontSize: "16px", fontWeight: "500", lineHeight: "24px" }}>{`${t(`EXTENSION_REQ`)}*`}</CardLabel>
          <TextInput
            className={"field"}
            textInputStyle={{ width: "60%", marginLeft: "2%" }}
            onChange={(e) => setExtensionRequested(e.target.value)}
            ValidationRequired={true}
            validation={{ type: "number" }}
            min={1}
            step={1}
          />
        </LabelFieldPair>
        <LabelFieldPair>
          <CardLabel style={{ fontSize: "16px", fontWeight: "500", lineHeight: "24px" }}>{`${t(`EXTENSION_REASON`)}*`}</CardLabel>
          <TextInput
            className={"field"}
            textInputStyle={{ width: "60%", marginLeft: "2%" }}
            onChange={(e) => setReasonForExtension(e.target.value)}
          />
        </LabelFieldPair>
      </>
    );
  };

  if (props.isLoading || approverLoading) return <Loader />;

  return (
    <React.Fragment>
      <ApplicationDetails
        applicationDetails={props.data?.applicationDetails}
        isLoading={props.isLoading}
        applicationData={""}
        moduleCode="Contract"
        showTimeLine={false}
        timelineStatusPrefix={`WF_${businessService}_`}
        businessService={businessService}
        tenantId={props.tenantId}
        applicationNo={props.contractNumber}
        statusAttribute={"state"}
        timeExtensionCreate={{
          getTimeExtensionJSX,
        }}
      />
      <ActionBar>
        <SubmitBar label={t("CREATE_AND_FORWARD_TE")} onSubmit={handleSubmit} />
      </ActionBar>
      {showModal && <WorkflowModal closeModal={() => setShowModal(false)} onSubmit={onModalSubmit} config={modalConfig} />}
      {showToast && <Toast label={t(showToast?.label)} error={showToast?.isError}></Toast>}
    </React.Fragment>
  );
};

export default CreateTimeExtension;
