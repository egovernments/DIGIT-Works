import { Loader, FormComposerV2, Header, Toast, ActionBar, Menu, SubmitBar } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect, useCallback } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";
import { CreateConfig } from "../../configs/MeasurementCreateConfig";
import { getDefaultValues } from "../../utils/transformEstimateData";
import { transformData } from "../../utils/transformData";
import _ from "lodash";

const updateData = (data, formState) => {
  const SOR = data?.SORtable || formState?.SOR;
  const NONSOR = data?.NONSORtable || formState?.NONSOR;
  return { ...formState, ...data, SOR, NONSOR };
};

const CreateMeasurement = ({ props }) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();
  const MeasurementSession = Digit.Hooks.useSessionStorage("MEASUREMENT_CREATE", {});
  // const [sessionFormData, setSessionFormData, clearSessionFormData] = MeasurementSession;
  const [createState, setState] = useState({ SOR: [], NONSOR: [], accessors: undefined, period: {} });
  const [defaultState, setDefaultState] = useState({ SOR: [], NONSOR: [] });
  const [showToast, setShowToast] = useState({display: false, error: false});
  const [errorMessage, setErrorMessage] = useState("");
  const [displayMenu, setDisplayMenu] = useState(false);

  const getFormAccessors = useCallback((accessors) => {
    if (!createState?.accessors) {
      setState((old) => ({ ...old, accessors }));
    }
  }, []);
  // get contractNumber from the url
  const searchparams = new URLSearchParams(location.search);
  const contractNumber = searchparams.get("workOrderNumber");

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
    },
  };

  const { isLoading, data } = Digit.Hooks.useCustomAPIHook(requestCriteria);

  // fetch the required data........
  useEffect(() => {
    const fetchRequiredData = () => {
      if (data) {
        const defaultValues = getDefaultValues(data, t);
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
          setErrorMessage(data?.period?.message);
          setShowToast({display:true, error:true});
        }
      }
    };
    fetchRequiredData();
  }, [data]);

  // action to be performed....
  let actionMB = [
    {
      name: "SUBMIT",
    },
    {
      name: "SAVE_AS_DRAFT",
    },
  ];

  function onActionSelect(action) {
    if (createState?.period?.type == "error") {
      setErrorMessage(createState?.period?.message);
      setShowToast({display:true, error:true});
      return null;
    }
    if (action?.name === "SUBMIT") {
      createState.workflowAction = "SUBMIT";
      handleCreateMeasurement(createState, action);
    }
    if (action?.name === "SAVE_AS_DRAFT") {
      createState.workflowAction = "SAVE_AS_DRAFT";
      handleCreateMeasurement(createState, action);
    }
  }

  // Handle form submission
  const handleCreateMeasurement = async (data, action) => {
    if (props?.isUpdate) {
      data.id = props?.data?.[0].id;
      data.measurementNumber = props?.data?.[0].measurementNumber;
      data.wfStatus = props?.data?.[0]?.wfStatus;
    }

    // Create the measurement payload with transformed data
    const measurements = transformData(updateData(data, createState));
    //call the createMutation for MB and route to response page on onSuccess or show error
    const onError = (resp) => {
      setErrorMessage(resp?.response?.data?.Errors?.[0]?.message);
      setShowToast({display:true, error:true});
    };
    const onSuccess = (resp) => {
      if(action?.name === "SAVE_AS_DRAFT")
      {
        setErrorMessage(t("MB_APPLICATION_IS_SUCCESSFULLY_DRAFTED"));
        setShowToast({display:true, error:false});
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
    setShowToast({display:false, error:false});;
  };
  //remove Toast after 3s
  useEffect(() => {
    if (showToast) {
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
  if (isLoading || !defaultState?.contract) {
    return <Loader />;
  }

  // else render form and data
  return (
    <div>
      <Header className="works-header-view modify-header">{t("MB_MEASUREMENT_BOOK")}</Header>
      <FormComposerV2
        label={t("MB_SUBMIT_BAR")}
        config={CreateConfig({ defaultValue: defaultState?.contract, measurement : props?.data[0] }).CreateConfig[0]?.form?.filter((a) => (!a.hasOwnProperty('forOnlyUpdate') || props?.isUpdate)).map((config) => {
          return {
            ...config,
            body: config.body.filter((a) => !a.hideInEmployee),
          };
        })}
        getFormAccessors={getFormAccessors}
        defaultValues={{ ...createState }}
        onSubmit={handleCreateMeasurement}
        fieldStyle={{ marginRight: 0 }}
        showMultipleCardsWithoutNavs={true}
        onFormValueChange={onFormValueChange}
        noBreakLine={true}
      />
      {showToast?.display && <Toast error={showToast?.error} label={errorMessage} isDleteBtn={true} onClose={closeToast} />}
      <ActionBar>
        {displayMenu ? <Menu localeKeyPrefix={"WF"} options={actionMB} optionKey={"name"} t={t} onSelect={onActionSelect} /> : null}
        <SubmitBar label={t("ACTIONS")} onSubmit={() => setDisplayMenu(!displayMenu)} />
      </ActionBar>
    </div>
  );
};
export default CreateMeasurement;
