
const useTEorMBCreateValidation=({estimateNumber, tenantId, t})=>{

  let validation = {}

  //To get all the Estimates associated with the contract
  const requestrevisionCriteria = {
      url: "/estimate/v1/_search",
      params : {tenantId : tenantId , estimateNumber : estimateNumber},
      config : {
        cacheTime : 0,
        enabled : estimateNumber ? true : false
      },
      changeQueryName: "allDetailedEstimate1"
    };
  
    const {isLoading: isDetailedEstimatesLoading, data: allDetailedEstimate} = Digit.Hooks.useCustomAPIHook(requestrevisionCriteria);

    //To check if any Revised estimate is in workflow in order to restrict Time Estension and measurement
    let inworkflowEstimates = allDetailedEstimate?.estimates?.filter((ob) => ob?.status === "INWORKFLOW");
    
    if(inworkflowEstimates?.length > 0)
    {
      validation = {
                  type : ["TIME_EXTENSTION","CREATE_MEASUREMENT"],
                  error : true,
                  label : `WORKS_REVISION_ESTIMATE_IN_WORKFLOW`,
                  applicationNo : inworkflowEstimates?.[0]?.revisionNumber
              }
    }

    return validation;
}

export default useTEorMBCreateValidation;