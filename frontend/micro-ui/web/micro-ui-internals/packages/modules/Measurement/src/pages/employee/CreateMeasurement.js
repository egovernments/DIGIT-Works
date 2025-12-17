import { Loader, FormComposerV2, Header, Menu, SubmitBar, WorkflowModal } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect, useCallback } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";
import { CreateConfig } from "../../configs/MeasurementCreateConfig";
import { getDefaultValues } from "../../utils/transformEstimateData";
import { transformData } from "../../utils/transformData";
import getModalConfig from "./config";
import { Toast,ActionBar,Button } from '@egovernments/digit-ui-components';
import _ from "lodash";

const updateData = (data, formState, tenantId) => {
  const SOR = data?.SORtable || formState?.SOR;
  const NONSOR = data?.NONSORtable || formState?.NONSOR;
  return { ...formState, ...data, SOR, NONSOR, tenantId };
};

const CreateMeasurement = ({ props }) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();
  const rolesForThisAction = "MB_VERIFIER"; 
  const MeasurementSession = Digit.Hooks.useSessionStorage("MEASUREMENT_CREATE", {});
  // const [sessionFormData, setSessionFormData, clearSessionFormData] = MeasurementSession;
  const [createState, setState] = useState({ SOR: [], NONSOR: [], accessors: undefined, period: {} });
  const [defaultState, setDefaultState] = useState({ SOR: [], NONSOR: [] });
  const [showToast, setShowToast] = useState({display: false, type: ""});
  const [errorMessage, setErrorMessage] = useState("");
  const [isButtonDisabled, setIsButtonDisabled] = useState(false)
  const [displayMenu, setDisplayMenu] = useState(false);
  const [config, setConfig] = useState({});
  const [approvers, setApprovers] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedApprover, setSelectedApprover] = useState({});

  const getFormAccessors = useCallback((accessors) => {
    if (!createState?.accessors) {
      setState((old) => ({ ...old, accessors }));
    }
  }, []);
  // get contractNumber from the url
  const searchparams = new URLSearchParams(location.search);
  const contractNumber = searchparams.get("workOrderNumber");
  const mbNumber = searchparams.get("mbNumber");

  // use this for call create or update
  const reqCriteria = {
    url: props?.isUpdate ? `/measurement-service/v1/_update` : `/measurement-service/v1/_create`,
    params: {},
    body: {},
    config: {
      enabled: false,
    },
  };

  const mutation = Digit.Hooks.useCustomAPIMutationHook(reqCriteria);

  // for BFF service
  const requestCriteria = {
    url: "/mukta-services/measurement/_search",
    body: {
      contractNumber: contractNumber,
      tenantId: tenantId,
      key: props?.isUpdate ? "View" : ""
    },
  };

  const { isLoading, data } = Digit.Hooks.useCustomAPIHook(requestCriteria);

  const { isLoading: approverLoading, isError, error, data: employeeDatav1 } = Digit.Hooks.hrms.useHRMSSearch(
    { roles: rolesForThisAction, isActive: true },
    Digit.ULBService.getCurrentTenantId(),
    null,
    null,
    { enabled: true }
  );

  employeeDatav1?.Employees.map((emp) => (emp.nameOfEmp = emp?.user?.name || "NA"));

  // fetch the required data........
  useEffect(() => {
    const fetchRequiredData = () => {
      if (data) {
        const defaultValues = getDefaultValues(data, t, mbNumber);
        setState({
          SOR: defaultValues?.SOR,
          NONSOR: defaultValues?.NONSOR,
          SORtable : defaultValues?.SOR,
          NONSORtable: defaultValues?.NONSOR,
          ...defaultValues?.contractDetails,
          period: data?.period,
          musterRollNumber: data?.musterRollNumber,
          uploadedDocs: defaultValues?.uploadedDocs,
          documents : defaultValues?.documents,
        });
        setDefaultState({
          SOR: defaultValues?.SOR,
          NONSOR: defaultValues?.NONSOR,
          SORtable : defaultValues?.SOR,
          NONSORtable: defaultValues?.NONSOR,
          contract: data?.contract,
          estimate: data?.estimate,
          contractDetails: defaultValues?.contractDetails,
          uploadedDocs: defaultValues?.uploadedDocs,
          documents : defaultValues?.documents,
        });
        createState?.accessors?.setValue?.("SOR", defaultValues?.SOR);
        createState?.accessors?.setValue?.("NONSOR", defaultValues?.NONSOR);
        createState?.accessors?.setValue?.("contract", data?.contract);
        if (data?.period?.type == "error") {
          setErrorMessage(t(data?.period?.message));
          setShowToast({display:true, error:true});
        }
      }
    };
    fetchRequiredData();
  }, [data]);

  useEffect(() => {
    setApprovers(employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees.filter((emp) => emp?.nameOfEmp !== "NA") : []);
  }, [employeeDatav1]);
  useEffect(() => {
    setConfig(
      getModalConfig({
        t,
        approvers,
        selectedApprover,
        setSelectedApprover,
        approverLoading,
        isEdit : props?.isUpdate,
      })
    );
  }, [approvers]);

  // action to be performed....
  let actionMB = [
    {
      name: "SUBMIT",
      displayName:"WF_SUBMIT"
    },
    {
      name:"SAVE_AS_DRAFT",
      displayName: "WF_SAVE_AS_DRAFT"
    },
  ];

  function onActionSelect(action =     {
    name: "SUBMIT",
    displayName:"WF_SUBMIT"
  }) {
    if (createState?.period?.type == "error") {
      setErrorMessage(t(createState?.period?.message));
      setShowToast({display:true, type:"error"});
      return null;
    }
    if (action?.name === "SUBMIT") {
      createState.workflowAction = "SUBMIT";
      setShowModal(true);
      //handleCreateMeasurement(createState, action);
    }
    if (action?.name === "SAVE_AS_DRAFT") {
      createState.workflowAction = "SAVE_AS_DRAFT";
      handleCreateMeasurement(createState, action);
    }
  }

  // Handle form submission
  const handleCreateMeasurement = async (data, action) => {
    setShowModal(false);
    setIsButtonDisabled(true);
    if (props?.isUpdate) {
      data.id = props?.data?.[0].id;
      data.measurementNumber = props?.data?.[0].measurementNumber;
      data.wfStatus = props?.data?.[0]?.wfStatus;
    }

    if(selectedApprover)
      data.selectedApprover = selectedApprover;
    // Create the measurement payload with transformed data
    const measurements = transformData(updateData(data, createState, tenantId));
    //call the createMutation for MB and route to response page on onSuccess or show error
    const onError = (resp) => {
      setIsButtonDisabled(false);
      setErrorMessage(t(resp?.response?.data?.Errors?.[0]?.message));
      setShowToast({display:true, type:"error"});
    };
    const onSuccess = (resp) => {
      if(action?.name === "SAVE_AS_DRAFT")
      {
        setErrorMessage(t("MB_APPLICATION_IS_SUCCESSFULLY_DRAFTED"));
        setShowToast({display:true});
        setTimeout(() => {history.push(`/${window.contextPath}/employee/measurement/update?tenantId=${resp.measurements[0].tenantId}&workOrderNumber=${contractNumber}&mbNumber=${resp.measurements[0].measurementNumber}`)}, 3000);;
      }
      else
        history.push(`/${window.contextPath}/employee/measurement/response?mbreference=${resp.measurements[0].measurementNumber}`);
    };
    mutation.mutate(
      {
        params: {},
        body: { ...measurements },
        config: {
          enabled: true,
        },
      },
      {
        onError,
        onSuccess,
      }
    );
  };

  const closeToast = () => {
    setShowToast({display:false});
  };
  //remove Toast after 3s
  useEffect(() => {
    if (showToast && showToast?.display === true) {
      setTimeout(() => {
        closeToast();
      }, 3000);
    }
  }, [showToast]);

  // useEffect(() => {
  //   if (!_.isEqual(sessionFormData, createState)) {
  //     // setSessionFormData({ ...createState });
  //   }
  //   console.log(createState,"formdata",sessionFormData)
  // }, [createState]);

  const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
    if (!_.isEqual(formData?.uploadedDocs, createState?.uploadedDocs)) {
      setState({ ...createState, ...formData })
    }
    // console.log(formData, "---formData-", createState);
  };
  actionMB = actionMB && (props?.isUpdate) && props?.data && props?.data?.[0]?.wfStatus==="SENT_BACK" ? actionMB?.filter((ob) => ob?.name !== "SAVE_AS_DRAFT") : actionMB;

  // if data is still loading return loader
  if (isLoading || !defaultState?.contract || approverLoading) {
    return <Loader />;
  }

  // else render form and data
  return (
    <div>
      {showModal && (
        <WorkflowModal
          closeModal={() => setShowModal(false)}
          onSubmit={(_data) => handleCreateMeasurement({ ..._data, ...createState }, "SUBMIT")}
          config={config}
          isDisabled={isButtonDisabled}
        />
      )}
      <Header className="works-header-view modify-header">{t("MB_MEASUREMENT_BOOK")}</Header>
      <FormComposerV2
        label={t("MB_SUBMIT_BAR")}
        config={CreateConfig({ defaultValue: defaultState?.contract, measurement: props?.data[0], mbnumber: mbNumber })
          .CreateConfig[0]?.form?.filter((a) => !a.hasOwnProperty("forOnlyUpdate") || props?.isUpdate)
          .map((config) => {
            return {
              ...config,
              body: config.body.filter((a) => !a.hideInEmployee),
            };
          })}
        getFormAccessors={getFormAccessors}
        defaultValues={{ ...createState }}
        onSubmit={onActionSelect}
        fieldStyle={{ marginRight: 0 }}
        showMultipleCardsWithoutNavs={true}
        onFormValueChange={onFormValueChange}
        noBreakLine={true}
        fieldPairNoMargin={true}
      />
      {showToast?.display && <Toast type={showToast?.type} label={errorMessage} isDleteBtn={true} onClose={closeToast} />}
      <ActionBar
        actionFields={[
          <Button
            isDisabled={isButtonDisabled}
            type={"actionButton"}
            options={actionMB}
            label={t("ACTIONS")}
            variation={"primary"}
            optionsKey={"displayName"}
            isSearchable={false}
            onOptionSelect={(option) => {
              onActionSelect(option);
            }}
          ></Button>
        ]}
        setactionFieldsToRight={true}
        className={"new-actionbar"}
      />
    </div>
  );
};
export default CreateMeasurement;
