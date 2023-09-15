import { Loader, FormComposerV2 } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";
import { CreateConfig } from "../../configs/MeasurementCreateConfig";
import _ from "lodash";



const CreateMeasurement = () => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();
  const MeasurementSession = Digit.Hooks.useSessionStorage("MEASUREMENT_CREATE", {})

  const [sessionFormData, setSessionFormData, clearSessionFormData] = MeasurementSession;

  const [createState, setState] = useState(sessionFormData || {});


  // State to hold estimate data
  const [isEstimateEnabled, setIsEstimateEnabled] = useState(false);

  // get contractNumber from the url
  const searchparams = new URLSearchParams(location.search);
  const contractNumber = searchparams.get("workOrderNumber");
  console.log(contractNumber, "ccccccccccccc")

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
    if (!isContractLoading) {
      setIsEstimateEnabled(true);
    }
  }, [isContractLoading]);



  useEffect(() => {
    if (!_.isEqual(sessionFormData, createState)) {
      setSessionFormData({ ...createState });
    }
  }, [createState]);

  //fetching estimate data
  const { isLoading: isEstimateLoading, data: estimate, isError: isEstimateError } = Digit.Hooks.estimates.useEstimateSearch({
    tenantId,
    filters: { ids: contract?.lineItems[0].estimateId },
    config: {
      enabled: isEstimateEnabled,
    }
  })

  useEffect(() => {
    if (estimate && !createState?.SOR) {
      // get estimateDetails from the estimate data and get SOR and NONSOR data seperate
      const estimateDetails = estimate?.estimateDetails || [];
      // console.log(estimateDetails, "eeeeeeeeeeee")
      const sorCategoryArray = [];
      const nonSorCategoryArray = [];

      estimateDetails.reduce((_, currentItem) => {
        if (currentItem.category === 'SOR') {
          sorCategoryArray.push(currentItem);
        } else if (currentItem.category === 'NONSOR') {
          nonSorCategoryArray.push(currentItem);
        }
      }, null);
      setState({ SOR: sorCategoryArray, NONSOR: nonSorCategoryArray, isLocalStateUpadted: true });
    }
  }, [estimate]);
  ////// In progress



  // Define the request criteria for creating a measurement
  const reqCriteriaCreate = {
    url: `/measurementservice/v1/_create`,
    params: {},
    body: {},
    config: {
      enabled: true,
    },
  };

  // Handle form submission
  const onSubmit = async (data) => {

    try {
      const result = await Digit.Hooks.useCustomAPIMutationHook(reqCriteriaCreate);
      console.log("result:", result);

    } catch (error) {
      console.error("Error:", error);
    }
  };


  const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
    // setSessionFormData({ ...sessionFormData, ...formData });

    if (!_.isEqual(formData, createState)) {
      setState({ ...formData })
    }
    console.log(formData, "formData")
    console.log(createState, "createStateData")
    // console.log(sessionFormData, "sessionFormData")
  }

  if (isContractLoading || isEstimateLoading) {
    return <Loader />
  }


  console.log(createState,
    "createStatecreateState");

  return (
    <FormComposerV2
      // heading={t("Measurement Book")}
      // label={t("Submit Bar")}
      // description={"Description"}
      // text={"Sample Text if required"}
      config={CreateConfig({ defaultValues: contract }).CreateConfig[0]?.form?.map((config) => {
        console.log(config, "cccccccccccccc")
        return {
          ...config,
          body: config.body.filter((a) => !a.hideInEmployee),
        };
      })}
      defaultValues={{ ...createState }}
      onSubmit={onSubmit}
      fieldStyle={{ marginRight: 0 }}
      onFormValueChange={onFormValueChange}
    />
  );
};

export default CreateMeasurement;
