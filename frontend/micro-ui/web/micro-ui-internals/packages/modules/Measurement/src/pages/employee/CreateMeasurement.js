import { Loader, FormComposerV2, Header } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";
import { CreateConfig } from "../../configs/MeasurementCreateConfig";
import ContractDetailsCard from "../../components/ContractCardDetails";
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


  //function to create measure object of transform data
  const createMeasurement = (item, type) => {
    return {
      referenceId: null,
      targetId: item.amountDetail[0].id || "",
      length: 200,
      breadth: 200,
      height: 200,
      numItems: 1,
      currentValue: 0,
      cumulativeValue: 0,
      isActive: true,
      comments: null,
      additionalDetails: {
        mbAmount: item.amountDetail[0]?.amount || 0,
        type: type,
      },
    };
  };

  // transform the data according requestbody
  const transformData = (data) => {
    const transformedData = {
      measurements: [
        {
          tenantId: "pg.citya",
          physicalRefNumber: null,
          referenceId: contractNumber || "",
          entryDate: 0,
          documents: [{
            "documentType": "string",
            "fileStore": "be14ceb8-01ba-485b-a6e2-489e5474a576",
            "documentUid": "string",
            "additionalDetails": {}
          }],
          measures: [],
          isActive: true,
          additionalDetails: {
            sorAmount: data.sumSor || 0,
            nonSorAmount: data.sumNonSor || 0,
            totalAmount: (data.sumSor ? data.sumSor : 0) + (data.sumNonSor ? data.sumNonSor : 0),
          },
        },
      ],
    };

    // Process SOR data
    if (data.SOR && Array.isArray(data.SOR)) {
      data.SOR.forEach((sorItem) => {
        transformedData.measurements[0].measures.push(createMeasurement(sorItem, "SOR"));
      });
    }

    // Process NONSOR data
    if (data.NONSOR && Array.isArray(data.NONSOR)) {
      data.NONSOR.forEach((nonsorItem) => {
        transformedData.measurements[0].measures.push(createMeasurement(nonsorItem, "NONSOR"));
      });
    }

    return transformedData;
  };

  // Define the request criteria for creating a measurement

  const reqCriteriaUpdate = {
    url: `/measurement-service/measurement/v1/_create`,
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
      console.log(resp);
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

  useEffect(() => {
    if (!_.isEqual(sessionFormData, createState)) {
      setSessionFormData({ ...createState });
    }
  }, [createState]);


  const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
    if (!_.isEqual(formData, createState)) {
      setState({ ...formData })
    }
    console.log(formData, "formData")
  }

  // after fetching the estimate data get sor and nonsor details
  useEffect(() => {
    if (estimate) {
      // get estimateDetails from the estimate data and get SOR and NONSOR data seperate
      const estimateDetails = estimate?.estimateDetails || [];
      const sorCategoryArray = [];
      const nonSorCategoryArray = [];
      estimateDetails.reduce((_, currentItem) => {
        if (currentItem.category === 'SOR') {
          sorCategoryArray.push(currentItem);
        } else if (currentItem.category === 'NON-SOR') {
          nonSorCategoryArray.push(currentItem);
        }
      }, null);

      setState({ SOR: sorCategoryArray, NONSOR: nonSorCategoryArray });
      setCreateState(true)
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
        // heading={t("Measurement Book")}
        label={t("Submit Bar")}
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

      // showWrapperContainers={true}
      // isDescriptionBold={true}
      // noBreakLine={false}
      // showMultipleCards={true}

      />
    </div>
  );
};
export default CreateMeasurement;


