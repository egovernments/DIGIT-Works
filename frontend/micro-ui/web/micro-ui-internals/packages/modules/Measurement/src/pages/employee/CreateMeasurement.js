import { Loader, FormComposerV2, Header, Toast } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";
import { CreateConfig } from "../../configs/MeasurementCreateConfig";
import ContractDetailsCard from "../../components/ContractCardDetails";
import { transformEstimateData } from "../../utils/transformEstimateData";
import { transformData } from "../../utils/transformData";
import _ from "lodash";
const CreateMeasurement = () => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();
  const MeasurementSession = Digit.Hooks.useSessionStorage("MEASUREMENT_CREATE", {})
  const [sessionFormData, setSessionFormData, clearSessionFormData] = MeasurementSession;
  const [createState, setState] = useState(sessionFormData || {});
  const [creatStateSet, setCreateState] = useState(false)
  const [isEstimateEnabled, setIsEstimateEnabled] = useState(false);
  const [showErrorToast, setShowErrorToast] = useState(false);
  const [errorMessage, setErrorMessage] = useState("")

  // get contractNumber from the url
  const searchparams = new URLSearchParams(location.search);
  const contractNumber = searchparams.get("workOrderNumber");
  //fetching contract data
  const { isLoading: isContractLoading, data: contract } = Digit.Hooks.contracts.useContractSearch({
    tenantId,
    filters: { contractNumber, tenantId },
    config: {
      enabled: true,
      cacheTime: 0
    }
  })

  // When contract data is available, enable estimate search
  useEffect(() => {
    if (contract) {
      setIsEstimateEnabled(true);
    }
  }, [contract]);
  //fetching estimate data
  const { isLoading: isEstimateLoading, data: estimate, isError: isEstimateError } = Digit.Hooks.estimates.useEstimateSearch({
    tenantId,
    filters: { ids: contract?.lineItems[0].estimateId },
    config: {
      enabled: isEstimateEnabled,
    }
  })
  // Define the request criteria for creating a measurement
  const reqCriteriaUpdate = {
    url: `/measurement-service/v1/_create`,
    params: {},
    body: {},
    config: {
      enabled: false,
    },
  };
  const mutation = Digit.Hooks.useCustomAPIMutationHook(reqCriteriaUpdate);

  // Handle form submission
  const handleCreateMeasurement = async (data) => {

    // Create the measurement payload with transformed data
    const measurements = transformData(data);
    //call the createMutation for MB and route to response page on onSuccess or console error
    const onError = (resp) => {
      setErrorMessage(resp?.response?.data?.Errors?.[0]?.message);
      setShowErrorToast(true);

    };
    const onSuccess = (resp) => {
      history.push(`/${window.contextPath}/employee/measurement/response?mbreference=${resp.measurements[0].measurementNumber}`)
    };
    mutation.mutate(
      {
        params: {},
        body: { ...measurements },
        config: {
          enabled: true,
        }
      },
      {
        onError,
        onSuccess,
      }
    );
  };


  const closeToast = () => {
    setShowErrorToast(false);
  }
  //remove Toast from 3s
  useEffect(() => {
    if (showErrorToast) {
      setTimeout(() => {
        closeToast();
      }, 3000)
    }
  }, [showErrorToast])

  useEffect(() => {
    if (!_.isEqual(sessionFormData, createState)) {
      setSessionFormData({ ...createState });
    }
  }, [createState]);
  const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
    if (!_.isEqual(formData, createState)) {
      setState({ ...formData })
    }
  }
  // after fetching the estimate data get sor and nonsor details
  useEffect(() => {
    if (estimate) {
      // get estimateDetails from the estimate data and get SOR and NONSOR data seperate
      const estimateDetails = estimate?.estimateDetails || [];
      const sorCategoryArray = [];
      const nonSorCategoryArray = [];
      sorCategoryArray.push(...transformEstimateData(estimateDetails, contract, "SOR"));
      nonSorCategoryArray.push(...transformEstimateData(estimateDetails, contract, "NON-SOR"));
      if (sorCategoryArray && nonSorCategoryArray) {
        setState({ SOR: sorCategoryArray, NONSOR: nonSorCategoryArray });
        setCreateState(true)
      }

    }
  }, [estimate]);

  // if data is still loading return loader
  if (isContractLoading || isEstimateLoading || !contract || !estimate || !creatStateSet) {
    return <Loader />
  }
  // else render form and data
  return (
    <div>
      <Header className="works-header-view" style={{}}>Measurement Book</Header>
      <ContractDetailsCard contract={contract} /> {/* Display contract details */}
      <FormComposerV2
        label={t("MB_SUBMIT_BAR")}
        config={CreateConfig({ defaultValue: contract }).CreateConfig[0]?.form?.map((config) => {
          return {
            ...config,
            body: config.body.filter((a) => !a.hideInEmployee),
          };
        })}
        defaultValues={{ ...createState }}
        onSubmit={handleCreateMeasurement}
        fieldStyle={{ marginRight: 0 }}
        onFormValueChange={onFormValueChange}
      />
      {showErrorToast && <Toast error={true} label={errorMessage} isDleteBtn={true} onClose={closeToast} />}

    </div>
  );
};
export default CreateMeasurement;