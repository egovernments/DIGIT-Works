import { Loader, FormComposerV2, Header, ActionBar, Menu, SubmitBar, WorkflowModal, AlertPopUp } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect, useCallback } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";
import { CreateConfig } from "../../configs/RateAnalysisCreateConfig";
import { getDefaultValues, transformRequestBody } from "../../utils/transformData";
import getModalConfig from "../../../../Measurement/src/pages/employee/config";
import { deepCompare } from "../../utils/transformData";
import { Toast } from "@egovernments/digit-ui-components";

const CreateRateAnalysis = ({ props }) => {
  
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();
  const rolesForThisAction = "MUKTA_STATE_ADMIN"; 
  //const MeasurementSession = Digit.Hooks.useSessionStorage("MEASUREMENT_CREATE", {});
  const queryStrings = Digit.Hooks.useQueryParams();
  // const [sessionFormData, setSessionFormData, clearSessionFormData] = MeasurementSession;
  const [createState, setState] = useState({ SORDetails:[], extraCharges:[], accessors: undefined, period: {} });
  const [isButtonDisabled, setIsButtonDisabled] = useState(false)
  const [defaultState, setDefaultState] = useState({ SORDetails:[], extraCharges:[] });
  const [showToast, setShowToast] = useState({display: false, type:""});
  const [errorMessage, setErrorMessage] = useState("");
  const [config, setConfig] = useState({});
  const [approvers, setApprovers] = useState([]);
  const [selectedApprover, setSelectedApprover] = useState({});
  const [ isPopupOpen, setIsPopupOpen] = useState(false);
  let isUpdate = window.location.href.includes("update") || props?.isUpdate


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
    url: isUpdate? `/mdms-v2/v2/_update/WORKS-SOR.Composition` : `/mdms-v2/v2/_create/WORKS-SOR.Composition`,
    params: {},
    body: {},
    config: {
      enabled: false,
    },
  };

  const mutation = Digit.Hooks.useCustomAPIMutationHook(reqCriteria);

  // for MDMS service for SOR 
  const requestCriteria = {
    url: "/mdms-v2/v2/_search",
    body: {
    MdmsCriteria: {
        tenantId: tenantId,
        schemaCode: "WORKS-SOR.SOR",
        uniqueIdentifiers : [`${queryStrings?.sorid}`],
    },
    },
    changeQueryName:"sorRates"
};

const { isLoading, data : data} = Digit.Hooks.useCustomAPIHook(requestCriteria);

  // for MDMS service for SOR Composition
  const requestCriteriaMDMS = {
    url: "/mdms-v2/v2/_search",
    body: {
    MdmsCriteria: {
        tenantId: tenantId.split(".")?.[0],
        schemaCode: "WORKS-SOR.Composition",
        uniqueIdentifiers : [`${queryStrings?.compositionid}`],
    },
    },
    changeQueryName:"sorComposition"
};

const { isLoading : isCompositionLoading, data : compositionData} = Digit.Hooks.useCustomAPIHook(requestCriteriaMDMS);

//for MDMS service to call SOR data for BasicSORDetails
const requestCriteriaSOR = {
  url: "/mdms-v2/v2/_search",
  body: {
  MdmsCriteria: {
      tenantId: tenantId,
      limit:100,
      schemaCode: "WORKS-SOR.SOR",
      uniqueIdentifiers : compositionData?.mdms?.[0]?.data?.basicSorDetails?.map((ob) => ob?.sorId),
  },
  },
  config:{
    enabled:compositionData?.mdms?.length > 0 ? true : false
  },
  changeQueryName:"allSORdata"
};

const { isLoading: isSORLoading, data :allSORData} = Digit.Hooks.useCustomAPIHook(requestCriteriaSOR);

//for MDMS service to call Overhead data for extracharges
const requestCriteriaOverhead = {
  url: "/mdms-v2/v2/_search",
  body: {
  MdmsCriteria: {
      tenantId: tenantId,
      limit:100,
      schemaCode: "WORKS-SOR.Overhead",
      //uniqueIdentifiers : compositionData?.mdms?.[0]?.data?.additionalCharges?.map((ob) => ob?.applicableOn),
  },
  },
  config:{
    //enabled:compositionData?.mdms?.length > 0 ? true : false
  },
  changeQueryName:"allOverheadData"
};

const { isLoading: isOverheadLoading, data :allOverheadData} = Digit.Hooks.useCustomAPIHook(requestCriteriaOverhead);

const requestCriteriaSORComposition = {
  url: "/mdms-v2/v2/_search",
  body: {
  MdmsCriteria: {
      tenantId: tenantId.split(".")?.[0],
      schemaCode: "WORKS-SOR.Composition",
      filters: {
        sorId: queryStrings?.sorid
    },
  },
  },
  changeQueryName:"allsorComposition"
};

const { isLoading : isallCompositionLoading, data : allcompositionData} = Digit.Hooks.useCustomAPIHook(requestCriteriaSORComposition);

  // fetch the required data........
  useEffect(() => {
    const fetchRequiredData = () => {
      if (data) {
        const defaultValues =  getDefaultValues(data?.mdms?.[0], t, mbNumber, compositionData?.mdms?.[0],allSORData,allOverheadData,isUpdate || props?.isUpdate) ;
        setState({
          currentDate : [new Date().toISOString().split("T")[0]],
          SORDetails : defaultValues?.SORDetails || [],
          ...defaultValues?.SORData,
          extraCharges:defaultValues?.extraCharges,
        });
        setDefaultState({
          currentDate : new Date(Date.now() + 86400000).toISOString().split("T")[0],
          SORDetails : defaultValues?.SORDetails || [],
          sordata: data?.mdms?.[0],
          extraCharges:defaultValues?.extraCharges,
        });
        createState?.accessors?.setValue?.("SORDetails", defaultValues?.SORDetails || []);
        createState?.accessors?.setValue?.("extraCharges", defaultValues?.extraCharges || []);
        createState?.accessors?.setValue?.("sordata", data?.mdms?.[0]);
        if (data?.period?.type == "error") {
          setErrorMessage(data?.period?.message);
          setShowToast({display:true, type:"error"});
        }
      }
    };
    fetchRequiredData();
  }, [data,allSORData,compositionData,isOverheadLoading,allOverheadData]);

  useEffect(() => {
    setConfig(
      getModalConfig({
        t,
        approvers,
        selectedApprover,
        setSelectedApprover,
        //approverLoading,
        isEdit : props?.isUpdate,
      })
    );
  }, [approvers]);

  // Handle form submission
  const handleCreateRateAnalysis = async (data, action) => {
    setIsButtonDisabled(true);
    if(createState?.SORType !== "Works")
    {
      setErrorMessage(t("RA_ONLY_FOR_WORKS"));
      setShowToast({display:true, type:"error"});
      setIsPopupOpen(false);
      setIsButtonDisabled(false);
      return;
    }
    if(createState?.SORDetails?.length <= 0)
    {
      setErrorMessage(t("RA_SOR_DETAILS_MANDATORY"));
      setShowToast({display:true, type:"error"});
      setIsPopupOpen(false);
      setIsButtonDisabled(false);
      return;
    }
    if(createState?.SORDetails?.filter((ob) => ob?.quantity === null || ob?.quantity === "")?.length > 0)
    {
      setErrorMessage(t("RA_SOR_DETAILS_QUANTITY_MANDATORY"));
      setShowToast({display:true, type:"error"});
      setIsPopupOpen(false);
      setIsButtonDisabled(false);
      return;
    }

    if(!isUpdate && allcompositionData?.mdms?.length > 0 && Digit.Utils.date.convertDateToEpoch(createState?.effective_from_date) <= allcompositionData?.mdms?.sort((a, b) => b.data.effectiveFrom - a.data.effectiveFrom)[0].data.effectiveFrom)
    {
      setErrorMessage(t("RA_NOT_ADDED_SAME_RECORD_EXIST"));
      setShowToast({display:true,type:"error"});
      setIsButtonDisabled(false);
      return;
    }

    if(isUpdate)
    {
      data.extraCharges = data?.extraCharges?.filter((ob) => ob?.applicableOn && ob?.calculationType && ob?.figure && ob?.description )
    }

    if(selectedApprover)
      data.selectedApprover = selectedApprover;

    // Create the rateanalysis payload with transformRequestBody data
    const rateComposition = await transformRequestBody(data, createState, tenantId, compositionData, isUpdate);

    //call the createMutation for Rate Analysis and route to view page on onSuccess or show error
    const onError = (resp) => {
      setIsButtonDisabled(false);
      setErrorMessage(resp?.response?.data?.Errors?.[0]?.message);
      setShowToast({display:true, type:"error"});
    };
    const onSuccess = (resp) => {
      setIsButtonDisabled(false);
        // if(isUpdate) setErrorMessage(`${t("RA_SUCCESS_UPDATE_MEESAGE_1")} ${resp?.mdms[0]?.data?.sorId} ${t("RA_SUCCESS_UPDATE_MESSAGE_2")} ${resp?.mdms?.[0]?.data?.effectiveFrom}`);
        // else setErrorMessage(`${t("RA_SUCCESS_MEESAGE_1")} ${resp?.mdms[0]?.data?.sorId} ${t("RA_SUCCESS_MESSAGE_2")} ${resp?.mdms?.[0]?.data?.effectiveFrom}`);
        // setShowToast({display:true, type:"error"});
        // setTimeout(() => {history.push(`/${window.contextPath}/employee/rateAnalysis/view-rate-analysis?sorId=${resp?.mdms[0]?.data?.sorId}&fromeffective=${resp?.mdms?.[0]?.data?.effectiveFrom}`)}, 3000);;
        history.push(`/${window.contextPath}/employee/rateAnalysis/response?sorId=${resp?.mdms[0]?.data?.sorId}&fromeffective=${parseInt(resp?.mdms?.[0]?.data?.effectiveFrom)}&compositionId=${resp?.mdms[0]?.uniqueIdentifier}&isUpdate=${isUpdate}`)
    };
    mutation.mutate(
      {
        params: {},
        body: { ...rateComposition },
        config: {
          enabled: rateComposition? true : false,
        },
      },
      {
        onError,
        onSuccess,
      }
    );
  };

  const closeToast = () => {
    setShowToast({display:false, type:""});;
  };
  //remove Toast after 3s
  useEffect(() => {
    if (showToast?.display) {
      setTimeout(() => {
        closeToast();
      }, 3000);
    }
  }, [showToast]);

  const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
    if (deepCompare(formData,createState)) {
      setState({ ...createState, ...formData })
    }
    else if((!formData?.extraCharges || formData?.extraCharges?.length == 0) && createState?.extraCharges?.length > 0 && isUpdate)
    {
      setState({ ...createState, extraCharges: [] })
    }
  };

  const validateRateAnalysis =() => {
    //validate if no data has been changed for edit
    if(!(deepCompare(createState?.SORDetails,compositionData?.mdms?.[0]?.data.basicSorDetails)))
    {
      setErrorMessage(t("RA_NO_CHANGE_IN_SOR"));
      setShowToast({display:true, type:""});
    }
    else
    {
      setIsPopupOpen(true)
    }
  }

  // if data is still loading return loader
  if (isLoading || !defaultState?.sordata || isCompositionLoading || isSORLoading || isOverheadLoading || isallCompositionLoading) {
    return <Loader />;
  }

  // else render form and data
  return (
    <div>
      {isPopupOpen && <AlertPopUp t={t} label={"Existing rate analysis is edited.Do you want to update existing rate analysis? Please confirm to complete the action."} setIsPopupOpen={setIsPopupOpen} onButtonClickConfirm={(_data) => handleCreateRateAnalysis({..._data,...createState},"SUBMIT")} onButtonClickCancel={() => { setIsPopupOpen(false)}}/>}
      <Header className="works-header-view modify-header">{isUpdate ? t("RA_UPDATE_RATE_ANALYSIS") : t("RA_CREATE_RATE_ANALYSIS")}</Header>
      <FormComposerV2
        label={t("RA_SUBMIT_BAR")}
        config={CreateConfig({t, defaultValue: defaultState, isUpdate, measurement : props?.data[0] }).CreateConfig[0]?.form?.filter((a) => (!a.hasOwnProperty('forOnlyUpdate') || props?.isUpdate)).map((config) => {
          return {
            ...config,
            body: config.body.filter((a) => !a.hideInEmployee),
          };
        })}
        getFormAccessors={getFormAccessors}
        defaultValues={{ ...createState }}
        onSubmit={(_data) => isUpdate && isUpdate !== undefined? validateRateAnalysis() : handleCreateRateAnalysis({..._data,...createState},"SUBMIT")}
        isDisabled={isButtonDisabled}
        fieldStyle={{ marginRight: 0 }}
        showMultipleCardsWithoutNavs={true}
        onFormValueChange={onFormValueChange}
        noBreakLine={true}
      />
      {showToast?.display && <Toast type={showToast?.type} label={errorMessage} isDleteBtn={true} onClose={closeToast} />}
    </div>
  );
};
export default CreateRateAnalysis;
