var config = require("../config");
var url = require("url");
const { Pool } = require("pg");
const { httpRequest } = require("./request");

const pool = new Pool({
  user: config.DB_USER,
  host: config.DB_HOST,
  database: config.DB_NAME,
  password: config.DB_PASSWORD,
  port: config.DB_PORT,
});

var auth_token = config.auth_token;

async function search_user(uuid, tenantId, requestinfo) {
  return await httpRequest(
    url.resolve(config.host.user, config.paths.user_search),
    {
      RequestInfo: requestinfo.RequestInfo,
      uuid: [uuid],
      tenantId: tenantId,
    }
  );
}

async function search_muster(musterRollNumber, tenantId, requestinfo) {
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

async function search_individual(individualIds, tenantId, requestinfo) {
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

async function search_workflow(applicationNumber, tenantId, requestinfo) {
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
async function search_localization(tenantId="", module="rainmaker-common", locale="en_IN", requestinfo) {
  return await httpRequest(
    url.resolve(config.host.localization, config.paths.localization_search),
    requestinfo,
    {
      tenantId: tenantId,
      module: module,
      locale:locale
    }
  );
}

async function search_mdms(tenantId, module, master, requestinfo) {
  return await httpRequest(
    url.resolve(config.host.mdms, config.paths.mdms_search),
    requestinfo,
    {
      tenantId: tenantId,
      ids: uuid,
    }
  );
}

async function create_pdf(tenantId, key, data, requestinfo) {
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

async function create_pdf_and_upload(tenantId, key, data, requestinfo) {
  return await httpRequest(
    url.resolve(config.host.pdf, config.paths.pdf_create_upload),
    Object.assign(requestinfo, data),
    {
      tenantId: tenantId,
      key: key,
    }
  );
}

function checkIfCitizen(requestinfo) {
  if (requestinfo.RequestInfo.userInfo.type == "CITIZEN") {
    return true;
  } else {
    return false;
  }
}

module.exports = {
  create_pdf,
  create_pdf_and_upload,
  search_mdms,
  search_user,
  search_workflow,
  search_muster,
  search_individual,
  search_localization
};
