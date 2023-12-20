import * as express from "express";
import { TSMap } from "typescript-map";

//import * as measurementController from "MeasurementController";
// Import necessary modules and libraries

import {
  calculate_expense,
  search_contract,
  search_estimate,
  search_mdms,
  search_measurement,
  search_muster,
} from "../../api/index";
import {
 // convertObjectForMeasurment,
  errorResponder,
  extractEstimateIds,
  sendResponse,
} from "../../utils/index";

// Define the MusterRollController class
class MusterRollController {
  // Define class properties
  public path = "/musterRollValidations";
  public router = express.Router();
  public dayInMilliSecond = 86400000;


  //Muster Roll Search API  call
  // Call the measurement service with the Contract id which we will fetch from the MR
  //Now check the MB's which are in approved status and the period of those MB's matches the Muster Roll period.

  // Constructor to initialize routes
  constructor() {
    this.intializeRoutes();
  }

  

  // Initialize routes for MeasurementController
  public intializeRoutes() {
    this.router.post(`${this.path}/_validate`, this.doValidations);
  }
 

  // Helper function to calculate end date based on start date and days
  public getEndDate = (startDate: number, days: number) => {
    return startDate + this.dayInMilliSecond * days - 1;
  };


  // Helper function to get contract and configuration data
  getContractandConfigs = async (
    tenantId: string,
    defaultRequestInfo: any,
    contractNumber: string,
    measurementNumber: any = null
  ) => {
    // Define an array of promises for parallel execution
    const promises = [
      search_contract(
        {
          tenantId,
        },
        { ...defaultRequestInfo, contractNumber, tenantId },
        contractNumber
      ),

      search_measurement(
        {
          ...defaultRequestInfo,
          criteria: {
            tenantId,
            workOrderNumber: contractNumber ? [contractNumber] : null,
          },
        },
        null,
        true
      ),

      search_mdms(
        tenantId.split(".")[0],
        "works",
        "MeasurementBFFConfig",
        defaultRequestInfo
      ),

      search_mdms(
        tenantId.split(".")[0],
        "works",
        "MeasurementCriteria",
        defaultRequestInfo
      ),
    ];

   // if (measurementNumber) {
      // Add measurement search promise if measurementNumber is provided
      promises.push(
        search_measurement(
          {
            ...defaultRequestInfo,
            criteria: {
              tenantId,
             // measurementNumber,
             workOrderNumber: contractNumber ? [contractNumber] : null,
            },
          },
          null
        )
      );
    //}

    // Execute promises in parallel
    const [contract, allMeasurements, config, periodResponse, measurement] =
      await Promise.all(promises);

    return {
      contract,
      measurement,
      config,
      periodResponse,
      allMeasurements,
    };
  };

  // Helper function to get estimate and muster data
  getEstimate = async (
    tenantId: string,
    ids: string,
    defaultRequestInfo: any
  ) => {
    const nextPromises = [
      search_estimate(
        {
          tenantId,
          ids,
        },
        defaultRequestInfo,
        ids
      ),
    ];

  

    const [estimate] = await Promise.all(nextPromises);

    return { estimate };
  };

  doValidations= async (
    request: express.Request,
    response: express.Response
  ) => {
    try {
      const { tenantId, RequestInfo, musterRollNumber } =
        request.body;
        console.log(request.body);
      const defaultRequestInfo = { RequestInfo };
     

      const { musterRolls } =
        await this.getMusterRoll(
          tenantId,
          defaultRequestInfo,
          musterRollNumber
        );
        

        const { contract,  allMeasurements } =
        await this.getContractandConfigs(
          tenantId,
          defaultRequestInfo,
          musterRolls?.referenceId,
          null
        );

        if (contract !== null && !contract?.notFound) {
  
          // Extract estimate IDs from the contract response
          const allEstimateIds = extractEstimateIds(contract);
          const estimateIds = allEstimateIds.join(",");
  
          var { estimate } = await this.getEstimate(
            tenantId,
            estimateIds,
            defaultRequestInfo
            );

          
        }
        
        const {expenseCalculator }=await this.getExpenseCalculation(tenantId,defaultRequestInfo,[musterRolls.id]);
        let musterRollValidationMapList = []; 
     var isMbPresent:boolean=false;
     //var labourCode=["US","SS","SD","HS"];

      if (musterRolls !== null && !musterRolls?.notFound) {
        // Calculate the period based on the responses
        var mrStartDate= musterRolls?.startDate;
       var  mrEndDate= musterRolls?.endDate;

      
     for(var allMeasurement of allMeasurements){
      var mbEndDate=allMeasurement.additionalDetails.endDate
      var mbStartDate=allMeasurement.additionalDetails.startDate
      if((mrStartDate>mbStartDate && mrEndDate>mbEndDate) ||(mrStartDate==mbStartDate && mrEndDate==mbEndDate) ){
        isMbPresent=!isMbPresent;
      
      }
     }
     if(isMbPresent === false){
      let musterRollValidationMap = new TSMap<string, string>();
      musterRollValidationMap.set("message","MB_PERIOD_IS_NOT_VALID_WRT_MR_PERIOD");
      musterRollValidationMap.set("type","error");
      musterRollValidationMapList.push(musterRollValidationMap)
     }else{
      // if(estimate.additionalDetails.sorSkillData?.[0].sorType=="L" 
      // || labourCode.includes(estimate.additionalDetails.sorSkillData?.[0].sorSubType)){
        
   
        if(estimate.additionalDetails.labourMaterialAnalysis.labour == 0){
          let musterRollValidationMap = new TSMap<string, string>();
   
          musterRollValidationMap.set("message","MB_LABOUR_UTILIZATION_AMOUNT_IS_ZERO")
          musterRollValidationMap.set("type","error")
              musterRollValidationMapList.push(musterRollValidationMap);
   
   
        }
   
        if(estimate.additionalDetails.labourMaterialAnalysis.labour != 0 &&
          expenseCalculator.estimates?.[0].netPayableAmount>
          estimate.additionalDetails.labourMaterialAnalysis.labour){
            let musterRollValidationMap= new TSMap<string, string>();
            musterRollValidationMap.set("message","MB_LABOUR_UTILIZATION_AMOUNT_IS_LESS_THAN_WAGE_BILL")
            musterRollValidationMap.set("type","warn")
            musterRollValidationMapList.push(musterRollValidationMap);
        }
        
      }

    // }

     
        
    

     const jsonString = JSON.stringify(musterRollValidationMapList);
     const musterRollValidation = JSON.parse(jsonString);
  
  

        // Send the final response
        return sendResponse(
          response,
          { musterRollValidation },
          request
        );
      }
      // Handle the case where contractResponse is null
      return errorResponder({ error: musterRolls?.message }, request, response);
    } catch (e) {
      // Handle errors
      console.error(e);
      return errorResponder(
        { error: "Internal Server Error" },
        request,
        response
      );
    }
    
  };

   // Helper function to get contract and configuration data
   getMusterRoll = async (
    tenantId: string,
    defaultRequestInfo: any,
    musterRollNumber: string,
    //measurementNumber: any = null
  ) => {
   
    const params = {
      tenantId: tenantId,
      //fromDate: null,
      musterRollNumber: musterRollNumber
    };

    // Execute promises in parallel
    var musterRolls =
      await search_muster(params,defaultRequestInfo)
      console.log(JSON.stringify(musterRolls));

    return {
      musterRolls
    };
  };

  getExpenseCalculation =async(
    tenantId: string,
    defaultRequestInfo: any,
    musterRollId: any,
  ) =>{
    var expenseCalculator=

   await calculate_expense(
      null,
      {
        ...defaultRequestInfo,
        criteria:{
          tenantId,
          musterRollId
        }

      },null
    )
    return {
      expenseCalculator
    };
  

    
  };
}

// Export the MeasurementController class
export default MusterRollController;
