import * as express from "express";
// Import necessary modules and libraries

import {
  search_contract,
  search_estimate,
  search_mdms,
  search_measurement,
  search_muster,
} from "../../api/index";
import {
  convertObjectForMeasurment,
  errorResponder,
  extractEstimateIds,
  sendResponse,
} from "../../utils/index";

// Define the MeasurementController class
class MeasurementController {
  // Define class properties
  public path = "/measurement";
  public router = express.Router();
  public dayInMilliSecond = 86400000;

  // Constructor to initialize routes
  constructor() {
    this.intializeRoutes();
  }

  // Initialize routes for MeasurementController
  public intializeRoutes() {
    this.router.post(`${this.path}/_search`, this.getAllMeasurements);
  }

  // Helper function to calculate end date based on start date and days
  public getEndDate = (startDate: number, days: number) => {
    return startDate + this.dayInMilliSecond * days - 1;
  };

  // Helper function to determine the measurement period
  public getPeriod = (
    periodResponse: any = {},
    contractResponse: any = {},
    measurementResponse: any = [],
    key: string="",
  ) => {
    // Check contract status
    const {
      contractStatus,
      period,
      measurementWorkflowStatus,
      enableMeasurementAfterContractEndDate,
      measurementBookStartDate
    } = periodResponse || {};

    if (
      (contractStatus && contractResponse?.status == contractStatus) ||
      !contractStatus
    ) {
      /* only active contract eligible for contract creation*/

      if (measurementResponse?.length > 0 && key !== "View") {
        /* many measurements are present */

        // Logic to be added to get the latest measurement
        const latestMeasurement = measurementResponse?.[0] || {};
        const lastMeasurementEndDate =
          latestMeasurement?.additionalDetails?.endDate;
        const newStartDate = lastMeasurementEndDate + 1;
        const newEndDate = this.getEndDate(newStartDate, period);

        if (
          (measurementWorkflowStatus &&
            latestMeasurement?.wfStatus == measurementWorkflowStatus) ||
          !measurementWorkflowStatus 
        ) {
          if (newStartDate < contractResponse?.endDate) {
            return {
              startDate: newStartDate,
              endDate: newEndDate
            };
          }
          if (enableMeasurementAfterContractEndDate) {
            return {
              startDate: newStartDate,
              endDate: newEndDate,
              message: "RECEIVED_MEASUREMENTS_TILL_CONTRACT_END_DATE",
              type: "warn",
            };
          }
          return {
            startDate: null,
            endDate: null,
            message: "RECEIVED_MEASUREMENTS_TILL_CONTRACT_END_DATE",
            type: "error",
          };
        }
        if(latestMeasurement?.wfStatus === "REJECTED"){
          return {
            startDate: latestMeasurement?.additionalDetails?.startDate,
            endDate: latestMeasurement?.additionalDetails?.endDate,
          };
        }
        return {
          startDate: null,
          endDate: null,
          message: "LAST_CREATED_MEASUREMENT_STILL_IN_WORKFLOW",
          type: "error",
        };
      } else if (measurementResponse?.length == 0 || measurementResponse?.code === "NO_MEASUREMENT_ROLL_FOUND" || key === "View") {
        /* no measurements are present */

        // Need to implement a check with the mdms configured date with the contractResponse?.startDate 
        //if configured date is greater than contractResponse?.startDate then we will take 
        //the period as a configured date period
        //otherwise it will go in the flow as is.
        //Under piece of code is used to get the same week monday epoch according to the contract startdate
        // const givenEpochTime: number = contractResponse?.startDate;
        // const givenDateTime: Date = new Date(givenEpochTime);
        // const daysToMonday: number = (givenDateTime.getDay() + 7) % 7;
        // const mondayDateTime: Date = new Date(givenDateTime);
        // mondayDateTime.setDate(givenDateTime.getDate() - daysToMonday);

       const mbStartDate:Date= this.setStartDateForMeasurementBook(periodResponse,contractResponse,measurementBookStartDate)

        // Get the Monday epoch datetime in milliseconds
        const mondayEpochTimeMillis: number = mbStartDate.getTime();

        const newEndDate = this.getEndDate(
          mondayEpochTimeMillis,   
          periodResponse?.period
        );

        return {
          startDate: mondayEpochTimeMillis, 
          endDate: newEndDate 
        };
      }
    }

    return {
      startDate: null,
      endDate: null,
      message: "CONTRACT_IS_NOT_ACTIVE",
      type: "error",
    };
  };

  setStartDateForMeasurementBook= (periodResponse:any,contractResponse: any,measurementBookStartDate:number) =>{
    let givenEpochTime: number;
    if(measurementBookStartDate<contractResponse?.startDate){
      givenEpochTime = contractResponse?.startDate;
    }else{
      givenEpochTime=measurementBookStartDate;
    }
    const givenDateTime: Date = new Date(givenEpochTime);
    const daysToMonday: number = (givenDateTime.getDay() + 7) % 7;
    const mondayDateTime: Date = new Date(givenDateTime);
    mondayDateTime.setDate(givenDateTime.getDate() - daysToMonday);
    return mondayDateTime;

  }

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
            referenceId: contractNumber ? [contractNumber] : null,
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

    if (measurementNumber) {
      // Add measurement search promise if measurementNumber is provided
      promises.push(
        search_measurement(
          {
            ...defaultRequestInfo,
            criteria: {
              tenantId,
              measurementNumber,
              referenceId: contractNumber ? [contractNumber] : null,
            },
          },
          null
        )
      );
    }

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
  getEstimateandMuster = async (
    tenantId: string,
    ids: string,
    defaultRequestInfo: any,
    period: any,
    contractNumber: string,
    key: string
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

    if (period?.startDate) {
      nextPromises.push(
        search_muster(
          {
            tenantId,
            fromDate: period?.startDate,
            referenceId: contractNumber,
          },
          defaultRequestInfo,
          ""
        )
      );
    }

    //if(key === "View")
    //{
      nextPromises.push(
        search_muster(
          {
            tenantId,
            referenceId: contractNumber,
          },
          defaultRequestInfo,
          key
        )
      );
    //}

    let [estimate, muster, musterRolls] = await Promise.all(nextPromises);
    if(!(key === "View")) muster = muster?.[0]; 
    return { estimate, muster, musterRolls };
  };

  // This function handles the HTTP request for retrieving all measurements.
  getAllMeasurements = async (
    request: express.Request,
    response: express.Response
  ) => {
    try {
      const { tenantId, RequestInfo, contractNumber, measurementNumber, key } =
        request.body;
      const defaultRequestInfo = { RequestInfo };

      const { contract, measurement, config, periodResponse, allMeasurements } =
        await this.getContractandConfigs(
          tenantId,
          defaultRequestInfo,
          contractNumber,
          measurementNumber
        );

      if (contract !== null && !contract?.notFound) {
        // Calculate the period based on the responses
        const period = this.getPeriod(
          periodResponse?.[0],
          contract,
          allMeasurements,
          key
        );

        // Extract estimate IDs from the contract response
        const allEstimateIds = extractEstimateIds(contract);
        const estimateIds = allEstimateIds.join(",");

        const { estimate, muster, musterRolls } = await this.getEstimateandMuster(
          tenantId,
          estimateIds,
          defaultRequestInfo,
          period,
          contractNumber,
          key
        );

        // Prepare the payload based on the responses
        const payload = {
          contract: contract,
          estimate: estimate,
          allMeasurements: allMeasurements,
          measurement: measurement || {},
          musterRoll: muster,
          musterRollsArray : musterRolls,
          period: period,
        };

        // Convert the payload according to the configuration
        const finalResponse = convertObjectForMeasurment(payload, config);

        // Send the final response
        return sendResponse(
          response,
          { ...finalResponse },
          request
        );
      }
      // Handle the case where contractResponse is null
      return errorResponder({ error: contract?.message }, request, response);
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
}

// Export the MeasurementController class
export default MeasurementController;
