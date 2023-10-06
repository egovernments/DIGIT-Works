import * as express from "express";
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

class MeasurementController {
  public path = "/measurement";
  public router = express.Router();
  public dayInMilliSecond = 86400000;

  constructor() {
    this.intializeRoutes();
  }

  public intializeRoutes() {
    this.router.post(`${this.path}/_search`, this.getAllMeasurements);
  }
  public getEndDate = (startDate: number, days: number) => {
    return startDate + this.dayInMilliSecond * days - 1;
  };
  public getPeriod = (
    periodResponse: any = {},
    contractResponse: any = {},
    measurementResponse: any = []
  ) => {
    if (contractResponse?.status == "ACTIVE") {
      /* only active contract eligible for contract creation*/

      if (measurementResponse?.length > 0) {
        /* many measurements are present */

        //logic to be added to get the latest measurement
        const latestMeasurement = measurementResponse?.[0] || {};
        const lastMeasurementEndDate =
          latestMeasurement?.additionalDetails?.endDate;
        const newStartDate = lastMeasurementEndDate + 1;
        const newEndDate = this.getEndDate(
          newStartDate,
          periodResponse?.period
        );
        if (latestMeasurement?.wfStatus == "APPROVED") {
          if (newStartDate < contractResponse?.endDate) {
            return {
              startDate: newStartDate,
              endDate:
                newEndDate < contractResponse?.endDate
                  ? newEndDate
                  : contractResponse?.endDate,
            };
          }
          return {
            startDate: null,
            endDate: null,
            message: "RECEIVED_MEASUREMENTS_TILL_CONTRACT_END_DATE",
            type: "error",
          };
        }
        return {
          startDate: null,
          endDate: null,
          message: "LAST_CREATED_MEASUREMENT_STILL_IN_WORKFLOW",
          type: "error",
        };
      } else if (measurementResponse?.length == 0) {
        /* no measurements are present */

        const newEndDate = this.getEndDate(
          contractResponse?.startDate,
          periodResponse?.period
        );

        return {
          startDate: contractResponse?.startDate,
          endDate:
            newEndDate < contractResponse?.endDate
              ? newEndDate
              : contractResponse?.endDate,
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

  // This function handles the HTTP request for retrieving all measurements.
  getAllMeasurements = async (
    request: express.Request,
    response: express.Response
  ) => {
    try {
      const { tenantId, RequestInfo, contractNumber, measurementNumber } =
        request.body;

      // Define the request body for MDMS
      const mdmsRequestBody = {
        RequestInfo: RequestInfo,
      };

      const defaultRequestInfo = { RequestInfo };

      // Search for MDMS data
    //   const config = await search_mdms(
    //     tenantId.split(".")[0],
    //     "works",
    //     "MeasurementBFFConfig",
    //     mdmsRequestBody
    //   );

    //   const periodResponse = await search_mdms(
    //     tenantId.split(".")[0],
    //     "works",
    //     "MeasurementCriteria",
    //     mdmsRequestBody
    //   );

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
            mdmsRequestBody
          ),
          search_mdms(
            tenantId.split(".")[0],
            "works",
            "MeasurementCriteria",
            mdmsRequestBody
          )
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
      const [contractResponse, measurementResponse, config,periodResponse, uniqueMeasurementResponse] =
        await Promise.all(promises);

      if (contractResponse !== null && !contractResponse?.notFound) {
        // Calculate the period based on the responses
        const period = this.getPeriod(
          periodResponse?.[0],
          contractResponse,
          measurementResponse
        );

        // Extract estimate IDs from the contract response
        const allEstimateIds = extractEstimateIds(contractResponse);
        const estimateIds = allEstimateIds.join(",");
        const nextPromises = [
            search_estimate(
                {
                  tenantId,
                  ids: estimateIds,
                },
                defaultRequestInfo,
                estimateIds
              )]
              if(true){
                nextPromises.push(search_muster(
                    {
                      tenantId,
                      fromDate: period?.startDate,
                      referenceId: contractNumber,
                    },
                    mdmsRequestBody
                  ));
              }


                const [estimateResponse, musterResponse] = await Promise.all(nextPromises);
  
        // Prepare the payload based on the responses
        const payload = {
          contract: contractResponse,
          estimate: estimateResponse,
          allMeasurements: measurementResponse,
          measurement: uniqueMeasurementResponse || [],
          musterRoll: musterResponse,
          period:periodResponse
        };

        // Convert the payload according to the configuration
        const finalResponse = convertObjectForMeasurment(payload, config);

        // Send the final response
        return sendResponse(response,{ ...finalResponse },request);
      } else {
        // Handle the case where contractResponse is null
        return errorResponder(
          { error: contractResponse?.message },
          request,
          response
        );
      }
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

export default MeasurementController;
