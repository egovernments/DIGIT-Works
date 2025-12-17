import React, { Fragment, useState, useEffect } from "react";
import { Loader, SubmitBar, WorkflowModal, CardLabel} from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import ApplicationDetails from "../../../../templates/ApplicationDetails";
import getModalConfig from "./modalConfig";
import { useHistory } from "react-router-dom";
import {Toast,ActionBar,Button,TextInput} from '@egovernments/digit-ui-components'
const CreateTimeExtension = ({isEdit,revisedWONumber,...props}) => {
  
  const history = useHistory()
  const { applicationData: contractObject, tenantId } = props.data;
  
  //removing documents section
  delete props.data.applicationDetails.applicationDetails[1];
  

  useEffect(() => {
    if(isEdit){
      props.data.applicationDetails.applicationDetails[0].values = props?.data?.applicationDetails?.applicationDetails[0]?.values?.slice(0,-2) 
    }
  }, [])
  

  const { t } = useTranslation();
  const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("contract");
  const [modalConfig, setModalConfig] = useState({});
  const rolesForThisAction = "WORK_ORDER_VERIFIER";
  const [approvers, setApprovers] = useState([]);
  const [selectedApprover, setSelectedApprover] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [extensionRequested, setExtensionRequested] = useState(isEdit ? contractObject.additionalDetails.timeExt : null);
  const [reasonForExtension, setReasonForExtension] = useState(isEdit ? contractObject.additionalDetails.timeExtReason : null);
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
        isEdit,
      })
    );
  }, [approvers]);

  const reqCriteriaUpdate = {
    url: isEdit ? `/contract/v1/_update` : `/contract/v1/_create`,
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
      
      history.push(`/${window.contextPath}/employee/contracts/create-time-extension-response?revisedWONumber=${resp.contracts[0].supplementNumber}&isSuccess=${true}`,{message:isEdit ? "TE_EDIT_SUCCESS":"TE_CREATION_SUCCESS",showID:true,label:"REVISED_WO_NUMBER"})
    };

    mutation.mutate(
      {
        params: {},
        body: {
          contract: { ...contractObject,
            businessService : "CONTRACT-REVISION",
            endDate:isEdit ? ((extensionRequested-contractObject.additionalDetails.timeExt) * 86400000) + contractObject.endDate : (extensionRequested * 86400000) + contractObject.endDate,
            additionalDetails:{...contractObject.additionalDetails,timeExtReason:reasonForExtension,timeExt:extensionRequested}
          },
          workflow: {
            action: isEdit ? "EDIT" : "CREATE",
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
    if (!extensionRequested || !reasonForExtension || extensionRequested <= 0 || extensionRequested > 365) {
      setShowToast({
        label: "TE_SUBMIT_VALIDATION",
        type: "error",
      });
      closeToast();
      return;
    }
    setShowModal(true);
  };

  const getTimeExtensionJSX = () => {
    return (
      <>
        <div style={{ lineHeight: "19px", maxWidth: "950px", minWidth: "280px",marginTop:"1rem" }}>
          <div className={"employee-data-table"} >
            <div className={"row border-none"} style={{alignItems:"center"}}>
              <CardLabel className={""} style={{ fontSize: "16px", fontWeight: "500", lineHeight: "24px" }}>{`${t(`EXTENSION_REQ`)}*`}</CardLabel>
              <TextInput
                className={"value"}
                // textInputStyle={{ width: "60%", marginLeft: "2%" }}
                onChange={(e) => setExtensionRequested(e.target.value)}
                ValidationRequired={true}
                validation={{ type: "number" }}
                maxlength={3}
                min={1}
                step={1}
                defaultValue={isEdit ? contractObject?.additionalDetails?.timeExt : null}
              />
            </div>
            <div className={"row border-none"} style={{alignItems:"center"}}>
              <CardLabel className={""} style={{ fontSize: "16px", fontWeight: "500", lineHeight: "24px"}}>{`${t(`EXTENSION_REASON`)}*`}</CardLabel>
              <TextInput
                className={"value"}
                // textInputStyle={{ width: "60%", marginLeft: "2%" }}
                onChange={(e) => setReasonForExtension(e.target.value)}
                defaultValue={isEdit ? contractObject?.additionalDetails?.timeExtReason : null}
              />
            </div>
          </div>
        </div>
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
      <ActionBar
                actionFields={[
                  <Button
                    type={"submit"}
                    label={t("CREATE_AND_FORWARD_TE")}
                    variation={"primary"}
                    onClick={handleSubmit}
                  ></Button>
                ]}
                setactionFieldsToRight={true}
                className={"new-actionbar"}
              />
      {showModal && <WorkflowModal closeModal={() => setShowModal(false)} onSubmit={onModalSubmit} config={modalConfig} />}
      {showToast && <Toast label={t(showToast?.label)} type={showToast?.type}></Toast>}
    </React.Fragment>
  );
};

export default CreateTimeExtension;
