import config from "../config";

var url = require("url");
const { httpRequest } = require("./request");



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

const search_muster = async (musterRollNumber: string, tenantId: string, requestinfo: any) => {
  if (musterRollNumber) {
    musterRollNumber = musterRollNumber.trim();
  }
  var params = {
    tenantId: tenantId,
    musterRollNumber: musterRollNumber,
  };
  return await httpRequest(
    url.resolve(config.host.muster, config.paths.mus_search),
    requestinfo,
    params
  );
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
  checkIfCitizen(requestinfo);
  return await httpRequest(
    url.resolve(config.host.mdms, config.paths.mdms_search),
    requestinfo,
    {
      tenantId: tenantId,
      ids: "",
    }
  );
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

const checkIfCitizen = async (requestinfo: any) => {
  if (requestinfo.RequestInfo.userInfo.type == "CITIZEN") {
    return true;
  } else {
    return false;
  }
}

const search_contract = async (tenantId: string, requestBody: any, cacheKey: any) => {
  return await httpRequest(
    url.resolve(config.host.contract, config.paths.contract_search),
    requestBody,
    {
      tenantId: tenantId,
    },
    "post",
    "",
    { cachekey: cacheKey }
  );
}

const search_estimate = async (tenantId: string, ids: string, requestBody: any, cacheKey: string) => {
  return await httpRequest(
    url.resolve(config.host.estimate, config.paths.estimate_search),
    requestBody,
    {
      tenantId: tenantId,
      ids: ids
    },
    "post",
    "",
    { cachekey: cacheKey }
  );
}

const search_measurement = async (requestBody: any) => {
  return await httpRequest(
    url.resolve(config.host.measurement, config.paths.measurement_search),
    requestBody,
  );
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