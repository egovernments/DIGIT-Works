import { Loader, FormComposerV2 } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";
import { CreateConfig } from "../../configs/MeasurementCreateConfig";



const CreateMeasurement = () => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();


  // State to hold estimate data
  const [isEstimateEnabled, setIsEstimateEnabled] = useState(false);

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
    if (!isContractLoading) {
      setIsEstimateEnabled(true);
    }
  }, [isContractLoading]);



  //fetching estimate data
  const { isLoading: isEstimateLoading, data: estimate, isError: isEstimateError } = Digit.Hooks.estimates.useEstimateSearch({
    tenantId,
    filters: { ids: contract?.lineItems[0].estimateId },
    config: {
      enabled: isEstimateEnabled,
    }
  })


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




  if (isContractLoading || isEstimateLoading) {
    return <Loader />
  }

  const estimateDetails = estimate?.estimateDetails || [];
  // console.log(estimateDetails, "eeeeeeeeeeee")
  const sorCategoryArray = [];
  const nonSorCategoryArray = [];

  estimateDetails.reduce((_, currentItem) => {
    if (currentItem.category === 'SOR') {
      sorCategoryArray.push(currentItem);
    } else if (currentItem.category === 'NON-SOR') {
      nonSorCategoryArray.push(currentItem);
    }
  }, null);


  return (
    <FormComposerV2
      heading={t("Measurement Book")}
      label={t("Submit Bar")}
      description={"Description"}
      text={"Sample Text if required"}
      config={CreateConfig({ defaultValues: contract }).CreateConfig[0]?.form?.map((config) => {
        return {
          ...config,
          body: config.body.filter((a) => !a.hideInEmployee),
        };
      })}
      defaultValues={{ SOR: sorCategoryArray, NONSOR: nonSorCategoryArray }}
      onSubmit={onSubmit}
      fieldStyle={{ marginRight: 0 }}
    />
  );
};

export default CreateMeasurement;