
const useEstimateSearchValidation=({detailedestimates, tenantId, t})=>{

    let validation = {}

    //To get all the contracts asocciated with this estimate
    let allEstimateIds = detailedestimates?.estimates?.map((ob) => ob?.id);
    let { isLoading: isLoadingContracts, data: contracts } = Digit.Hooks.contracts.useContractSearch({
        tenantId,
        filters: { tenantId, estimateIds: allEstimateIds },
        config: {
          enabled: detailedestimates?.estimates?.length > 0,
          cacheTime: 0,
        },
      });

    //To check if any contract in workflow it will add validation
    let inWorkflowContract = contracts && contracts?.filter((ob) => ob?.status === "INWORKFLOW");
    if(inWorkflowContract?.length > 0)
    {
        validation = {
            type : ["CREATE_REVISION_ESTIMATE"],
            error : true,
            label : t("WORKS_CONTRACT_IN_WORKFLOW"),
            applicationNo : inWorkflowContract?.[0]?.supplementNumber || inWorkflowContract?.[0]?.contractNumber
        }
    }

    //To get all the measurements associated with contracts
    const requestCriteriaForMeasurement = {
        url : "/measurement-service/v1/_search",
    
        body: {
          criteria : {
            "referenceId" : [contracts?.[0]?.contractNumber],
            "tenantId" : tenantId
         }
        },
        config: {
            enabled : contracts && contracts?.[0]?.contractNumber ? true : false
        }
    
      }
      const {isLoading: isMeasurementLoading, data: measurementData} = Digit.Hooks.useCustomAPIHook(requestCriteriaForMeasurement);

      //To check if any running measurements is present or not and add validation
      let inworflowmeasurement = measurementData?.measurements && measurementData?.measurements?.filter((ob) => ob?.wfStatus !== "APPROVED" && ob?.wfStatus !== "REJECTED");
      if(inworflowmeasurement?.length > 0)
      {
        validation = {
            type : ["CREATE_REVISION_ESTIMATE"],
            error : true,
            label : t("WORKS_MEASUREMENT_IN_WORKFLOW"),
            applicationNo : inworflowmeasurement?.[0]?.measurementNumber
        }
      }

      //To check if any RE is running so the contract creation can be restricted
      let inworkflowEstimates = detailedestimates?.estimates?.filter((ob) => ob?.status === "INWORKFLOW");
      if(inworkflowEstimates?.length > 0)
      {
        validation = {
            type : ["CREATE_CONTRACT"],
            error : true,
            label : t("WORKS_REVISION_ESTIMATE_IN_WORKFLOW"),
            applicationNo : inworkflowEstimates?.[0]?.revisionNumber || inworkflowEstimates?.[0]?.estimateNumber 
        }
      }
      return validation;
}

export default useEstimateSearchValidation;