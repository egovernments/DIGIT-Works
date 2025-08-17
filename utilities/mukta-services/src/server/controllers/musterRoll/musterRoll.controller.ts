import * as express from "express";
import { TSMap } from "typescript-map";

//import * as measurementController from "MeasurementController";
// Import necessary modules and libraries

import {
  calculate_expense,
  search_contract,
  search_estimate,
  search_measurement,
  search_muster,
  searchRates,
  search_mdms
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
    contractNumber: string
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
            referenceId: contractNumber ? [contractNumber] : null,
          },
        },
        null,
        true
      ),

      searchRates(
        tenantId,
        "WORKS-SOR",
        "Rates",
        defaultRequestInfo
      ),

      search_mdms(
        tenantId.split(".")[0],
        "works",
        "MeasurementCriteria",
        defaultRequestInfo
      ),
    ];

 

    // Execute promises in parallel
    const [contract, allMeasurements, sorRates,periodResponse] =
      await Promise.all(promises);

    return {
      contract,
      allMeasurements,
      sorRates,
      periodResponse
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

  doValidations = async (
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

      const { contract, allMeasurements, sorRates, periodResponse} =
        await this.getContractandConfigs(
          tenantId,
          defaultRequestInfo,
          musterRolls?.referenceId
        );

      var contractLineItems: any;

      if (contract !== null && !contract?.notFound) {

        // Extract estimate IDs from the contract response
        const allEstimateIds = extractEstimateIds(contract);
        const estimateIds = allEstimateIds.join(",");

        var { estimate } = await this.getEstimate(
          tenantId,
          estimateIds,
          defaultRequestInfo
        );
        contractLineItems = contract.lineItems;


      }
   



      const { expenseCalculator } = await this.getExpenseCalculation(tenantId, defaultRequestInfo, [musterRolls.id]);

      // Logic for MR_MB Validation

      var estimateDetails = estimate.estimateDetails;
      let totalLabourRate = 0;
      let musterRollValidationMapList = [];
      var isMbPresent: boolean = false;
      var isConfiguredDateLesser:boolean=false
      var labourRateGreaterThanZero:boolean=false;
      let nonSorEstimateCategory : boolean=false;
      var measurementNumber;
     
      if (musterRolls !== null && !musterRolls?.notFound) {  
        var mrStartDate = musterRolls?.startDate;
        var mrEndDate = musterRolls?.endDate;
        let musterRollValidationMap = new TSMap<string, any>();
      for (var estimateDetail of estimateDetails) {
        nonSorEstimateCategory = estimateDetail?.category !== 'SOR';
        //if (estimateDetail.category == 'SOR') {
          // let sorMap = new TSMap<string, string>();

          // sorMap.set(estimateDetail.sorId, estimateDetail.id);
          const filteredSorRates: any = sorRates.filter(
            (rate: any) => rate.sorId === estimateDetail.sorId);
          const amountDetailsWithLA: { type: string; heads: string; amount: number }[] = filteredSorRates
            .flatMap((rate: any) => rate.amountDetails)
            .filter((amountDetail: any) => amountDetail.heads.includes('LA'));

          const foundContractItem = contractLineItems.find((lineItem: any) => {
            return lineItem.estimateLineItemId === estimateDetail.id;
          });
          const contractRef: string = foundContractItem.contractLineItemRef;

          const givenDateTime: Date = new Date(periodResponse?.[0].measurementBookStartDate);
          const daysToMonday: number = (givenDateTime.getDay() + 7) % 7;
          const mondayDateTime: Date = new Date(givenDateTime);
          mondayDateTime.setDate(givenDateTime.getDate() - daysToMonday);
          const mbConfigruedStartDate: number = mondayDateTime.getTime();
          // const mbConfiguredEndDate = this.getEndDate(
          //   mbConfigruedStartDate,   
          //   periodResponse?.period
          // );
          
          
          
          if(mrStartDate>=mbConfigruedStartDate ){
            isConfiguredDateLesser=true;
          for (var allMeasurement of allMeasurements) {
            let currentValue;
            var mbEndDate = allMeasurement.additionalDetails.endDate
            var mbStartDate = allMeasurement.additionalDetails.startDate
          
           
            if (allMeasurement.referenceId == musterRolls.referenceId && allMeasurement.wfStatus == 'APPROVED' &&
            ((mrStartDate >= mbStartDate && mbEndDate >= mrEndDate) || (mrStartDate == mbStartDate && mrEndDate == mbEndDate))) {             
              
              // if ((mrStartDate >= mbStartDate && mbEndDate >= mrEndDate) || (mrStartDate == mbStartDate && mrEndDate == mbEndDate)) {
                measurementNumber = allMeasurement.measurementNumber;
                const matchingMeasure = allMeasurement.measures.find(
                  (measure: any) => measure.targetId === contractRef
                );
                currentValue = (matchingMeasure?.targetId === contractRef) ? matchingMeasure.currentValue : 0;
                console.log("Current Value::", currentValue)

                totalLabourRate += !nonSorEstimateCategory?((amountDetailsWithLA!=null && amountDetailsWithLA.length > 0)?amountDetailsWithLA[0].amount:0 )* currentValue:0;
                labourRateGreaterThanZero=totalLabourRate > 0 ? true :labourRateGreaterThanZero;
                 musterRollValidationMap.set("measurementNumber", measurementNumber);
                 musterRollValidationMap.set("totalLabourRate",totalLabourRate);
                isMbPresent = true;
                break;
                
              // }
          
           
            }
          
          
             // need to refactor this check based on the configured data from MDMS.
            // If mrStartDate< the configured date then the validation should not be checked

          }
        }
      //}

      }
      if (isMbPresent === false && isConfiguredDateLesser === true) {
        musterRollValidationMap.set("message", "MB_PERIOD_IS_NOT_VALID_WRT_MR_PERIOD_OR_NO_APPROVED_MB_IS_PRESENT");
        musterRollValidationMap.set("type", "error");
      }else{
        if (expenseCalculator?.totalAmount >
          totalLabourRate ) {

          musterRollValidationMap.set("message", "MB_LABOUR_UTILIZATION_AMOUNT_IS_LESS_THAN_WAGE_BILL")
          musterRollValidationMap.set("type", "warn");
        }

      }

        musterRollValidationMapList.push(musterRollValidationMap);
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
      await search_muster(params, defaultRequestInfo, "")
    console.log(JSON.stringify(musterRolls));

    return {
      musterRolls : musterRolls?.[0]
    };
  };

  getExpenseCalculation = async (
    tenantId: string,
    defaultRequestInfo: any,
    musterRollId: any,
  ) => {
    var expenseCalculator =

      await calculate_expense(
        null,
        {
          ...defaultRequestInfo,
          criteria: {
            tenantId,
            musterRollId
          }

        }, null
      )
    return {
      expenseCalculator
    };



  };



}

// Export the MeasurementController class
export default MusterRollController;
