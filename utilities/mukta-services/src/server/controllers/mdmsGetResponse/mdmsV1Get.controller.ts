import * as express from "express";
// Import necessary modules and libraries

import {
  search_mdms,
  mdmsv2_v1Search,
} from "../../api/index";
import {
  convertObjectForMdmsResponse,
  errorResponder,
  sendResponse,
} from "../../utils/index";

// Define the MdmsV1GetController class
class MdmsV1GetController {
  // Define class properties
  public path = "/mdmsV1/_get";
  public router = express.Router();

  // Constructor to initialize routes
  constructor() {
    this.intializeRoutes();
  }

  // Initialize routes for MdmsV1GetController
  public intializeRoutes() {
    this.router.post(`${this.path}/_search`, this.getMdmsResponse);
  }



  // Helper function to get mdms response and configuration data
  getMdmsResponseAndConfig = async (
    tenantId: string,
    moduleName: string,
    masterName: string,
    filter: string,
    RequestInfo: any
  ) => {

  
    // Define an array of promises for parallel execution
    const promises = [ 

      search_mdms(
        tenantId,
        "works",
        "MdmsV1GetConfig",
        RequestInfo 
      ),

      mdmsv2_v1Search(
        tenantId,
        moduleName,
        masterName,
        filter,
        RequestInfo
      
      ),
    ];



    // Execute promises in parallel
    const [ config, mdmsResponse] =
      await Promise.all(promises);

    return {
      config,
      mdmsResponse,      
    };
  };



  // This function handles the HTTP request for retrieving the response.
  getMdmsResponse = async (
    request: express.Request,
    response: express.Response
   
  ) => {
    try {
     
      const { moduleName, masterName, tenantId, filter } = request.query;
      const{RequestInfo} = request.body;

      console.log("Request Info fetched from the request",RequestInfo);
  
      const tenantIdStr = tenantId as string;
      const moduleNameStr = moduleName as string;
      const masterNameStr = masterName as string;
      const filterStr = filter as string;

      const {  config,mdmsResponse } =
        await this.getMdmsResponseAndConfig(
          tenantIdStr,
          moduleNameStr,
          masterNameStr,
          filterStr,
          RequestInfo
        );

      if (mdmsResponse !== null && !mdmsResponse?.notFound) {   


        // Prepare the payload based on the responses
        const payload = {
          MdmsRes: mdmsResponse,
        };

        // Convert the payload according to the configuration
        const finalResponse = convertObjectForMdmsResponse(payload, config);

        // Send the final response
        return sendResponse(
          response,
          { ...finalResponse },
          request
        );
      }
      // Handle the case where mdmsResponse is null
      return errorResponder({ error: mdmsResponse?.message }, request, response);
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

// Export the MdmsV1GetController class
export default MdmsV1GetController;
