import { Loader, FormComposerV2 } from "@egovernments/digit-ui-react-components";
import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";
import { CreateConfig } from "../../configs/MeasurementCreateConfig";



const CreateMeasurement = () => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();


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

  console.log("Contract Data:", contract);

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


  /* use newConfig instead of commonFields for local development in case needed */

  const configs = CreateConfig
if(isContractLoading) return <Loader />;

  return (
    <FormComposerV2
      heading={t("Application Heading")}
      label={t("Submit Bar")}
      description={"Description"}
      text={"Sample Text if required"}
      config={configs.map((config) => {
        return {
          ...config,
          body: config.body.filter((a) => !a.hideInEmployee),
        };
      })}
      defaultValues={{SOR:contract,NONSOR:contract}}
      onSubmit={onSubmit}
      fieldStyle={{ marginRight: 0 }}
    />
  );
};

export default CreateMeasurement;