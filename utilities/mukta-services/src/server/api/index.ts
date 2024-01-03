import config, { getErrorCodes } from "../config";

var url = require("url");
import { httpRequest } from "./request";


const search_user = async (uuid: string, tenantId: string, requestinfo: any) => {
  return await httpRequest(
    url.resolve(config.host.user, config.paths.user_search),
    {
      RequestInfo: requestinfo.RequestInfo,
      uuid: [uuid],
      tenantId: tenantId,
    }
  );
}

/*
  This asynchronous function searches for muster rolls based on the provided parameters.
*/
const search_muster = async (params: any, requestinfo: any, key: string) => {
  // Send an HTTP request to the muster search endpoint using the provided parameters and request information.
  const musterResponse = await httpRequest(
    url.resolve(config.host.muster, config.paths.mus_search),
    requestinfo,
    params
  );

  // Check if there are muster rolls in the response.
  if (musterResponse?.musterRolls?.length > 0) {
    // If muster rolls are found, return them.
    return key === "View" ? musterResponse?.musterRolls : musterResponse?.musterRolls?.[0];
  }

  // If no muster rolls are found, return an error code.
  return getErrorCodes("WORKS", "NO_MUSTER_ROLL_FOUND");
}



const search_individual = async (individualIds: Array<string>, tenantId: string, requestinfo: any) => {
  // currently single property pdfs supported
  // if (individualIds) {
  //   individualId = individualId.trim();
  // }
  var params = {
    tenantId: tenantId,
    limit: 100,
    offset: 0,
  };
  requestinfo.Individual = {
    id: individualIds,
  };

  return await httpRequest(
    url.resolve(config.host.individual, config.paths.ind_search),
    requestinfo,
    params
  );
}

const search_workflow = async (applicationNumber: string, tenantId: string, requestinfo: any) => {
  var params = {
    tenantId: tenantId,
    businessIds: applicationNumber,
  };
  return await httpRequest(
    url.resolve(config.host.workflow, config.paths.workflow_search),
    requestinfo,
    params
  );
}
const search_localization = async (tenantId: string, module: string = "rainmaker-common", locale: string = "en_IN", requestinfo: any) => {
  return await httpRequest(
    url.resolve(config.host.localization, config.paths.localization_search),
    requestinfo,
    {
      tenantId: tenantId,
      module: module,
      locale: locale
    }
  );
}

const search_mdms = async (tenantId: string, module: string, master: string, requestinfo: any) => {
  const requestBody = {
    RequestInfo: requestinfo.RequestInfo,
    MdmsCriteria: {
      tenantId: tenantId,
      moduleDetails: [
        {
          moduleName: module,
          masterDetails: [
            {
              name: master,
            },
          ],
        },
      ],
    },
  };
  return await httpRequest(
    url.resolve(config.host.mdms, config.paths.mdms_search),
    requestBody,
    null,
    "post",
    "",
    { cachekey: `${tenantId}-${module}-${master}` }
  ).then((response: { MdmsRes: any; })=>response.MdmsRes[module][master]);
}

const create_pdf = async (tenantId: string, key: string, data: string, requestinfo: any) => {
  return await httpRequest(
    url.resolve(config.host.pdf, config.paths.pdf_create),
    Object.assign(requestinfo, data),
    {
      tenantId: tenantId,
      key: key,
    },
    "post",
    "stream"
  );
}

const create_pdf_and_upload = async (tenantId: string, key: string, data: any, requestinfo: any) => {
  return await httpRequest(
    url.resolve(config.host.pdf, config.paths.pdf_create),
    Object.assign(requestinfo, data),
    {
      tenantId: tenantId,
      key: key,
    }
  );
}


/*
  This asynchronous function searches for contracts based on the provided parameters.
*/
const search_contract = async (params: any, requestinfo: any, cachekey: any) => {
  // Send an HTTP request to the contract search endpoint using the provided parameters and request information.
  const contractResponse = await httpRequest(
    url.resolve(config.host.contract, config.paths.contract_search),
    requestinfo,
    params,
    "post",
    "",
    { cachekey }
  );

  // Check if there are contracts in the response.
  if (contractResponse?.contracts?.length > 0) {
    // If contracts are found, return the first one.
    return contractResponse?.contracts?.[0];
  }

  // If no contracts are found, return an error code.
  return getErrorCodes("WORKS", "NO_CONTRACT_FOUND");
}

/*
  This asynchronous function searches for estimates based on the provided parameters.
*/
const search_estimate = async (params: any, requestinfo: any, cachekey: any) => {
  // Send an HTTP request to the estimate search endpoint using the provided parameters and request information.
  const estimateResponse = await httpRequest(
    url.resolve(config.host.estimate, config.paths.estimate_search),
    requestinfo,
    params,
    "post",
    "",
    { cachekey }
  );
  
  // Check if there are estimates in the response.
  if (estimateResponse?.estimates?.length > 0) {
    // If estimates are found, return the first one.
    return estimateResponse?.estimates?.[0];
  }

  // If no estimates are found, return an error code.
  return getErrorCodes("WORKS", "NO_ESTIMATE_FOUND");
}

/*
  This asynchronous function searches for measurements based on the provided parameters.
*/
const search_measurement = async (requestinfo: any, cachekey: any, allResponse: boolean = false) => {
  // Send an HTTP request to the measurement search endpoint using the provided parameters and request information.
  const musterResponse = await httpRequest(
    url.resolve(config.host.measurement, config.paths.measurement_search),
    requestinfo,
    null,
    "post",
    "",
    { cachekey }
  );
  
  // Check if there are measurements in the response.
  if (musterResponse?.measurements?.length > 0) {
    // If measurements are found, return either all of them or just the first one based on the 'allResponse' parameter.
    return allResponse ? musterResponse?.measurements : musterResponse?.measurements?.[0];
  }

  // If no measurements are found, return an error code.
  return getErrorCodes("WORKS", "NO_MEASUREMENT_ROLL_FOUND");
}

export {
  create_pdf,
  create_pdf_and_upload,
  search_mdms,
  search_user,
  search_workflow,
  search_muster,
  search_individual,
  search_localization,
  search_contract,
  search_estimate,
  search_measurement
};