var config = require("./config");
var axios = require("axios").default;
var url = require("url");
var producer = require("./producer").producer;
var logger = require("./logger").logger;
const { Pool } = require("pg");

const pool = new Pool({
  user: config.DB_USER,
  host: config.DB_HOST,
  database: config.DB_NAME,
  password: config.DB_PASSWORD,
  port: config.DB_PORT,
});

auth_token = config.auth_token;

async function search_user(uuid, tenantId, requestinfo) {
  return await axios({
    method: "post",
    url: url.resolve(config.host.user, config.paths.user_search),
    data: {
      RequestInfo: requestinfo.RequestInfo,
      uuid: [uuid],
      tenantId: tenantId,
    },
  });
}

async function search_muster(musterRollNumber, tenantId, requestinfo) {
  // currently single property pdfs supported
  if (musterRollNumber) {
    musterRollNumber = musterRollNumber.trim();
  }
  var params = {
    tenantId: tenantId,
    musterRollNumber: musterRollNumber,
  };
  // if (
  //   checkIfCitizen(requestinfo) &&
  //   allowCitizenTOSearchOthersRecords != true
  // ) {
  //   var mobileNumber = requestinfo.RequestInfo.userInfo.mobileNumber;
  //   var userName = requestinfo.RequestInfo.userInfo.userName;
  //   params["mobileNumber"] = mobileNumber || userName;
  // }
  return await axios({
    method: "post",
    url: url.resolve(config.host.muster, config.paths.mus_search),
    data: requestinfo,
    params,
  });
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
  console.log(requestinfo, "requestinfo ind");
  return await axios({
    method: "post",
    url: url.resolve(config.host.individual, config.paths.ind_search),
    data: requestinfo,
    params,
  });
}

async function search_workflow(applicationNumber, tenantId, requestinfo) {
  var params = {
    tenantId: tenantId,
    businessIds: applicationNumber,
  };
  return await axios({
    method: "post",
    url: url.resolve(config.host.workflow, config.paths.workflow_search),
    data: requestinfo,
    params,
  });
}

async function search_mdms(tenantId, module, master, requestinfo) {
  return await axios({
    method: "post",
    url: url.resolve(config.host.mdms, config.paths.mdms_search),
    data: requestinfo,
    params: {
      tenantId: tenantId,
      ids: uuid,
    },
  });
}

async function create_pdf(tenantId, key, data, requestinfo) {
  return await axios({
    responseType: "stream",
    method: "post",
    url: url.resolve(config.host.pdf, config.paths.pdf_create),
    data: Object.assign(requestinfo, data),
    params: {
      tenantId: tenantId,
      key: key,
    },
  });
}

async function create_pdf_and_upload(tenantId, key, data, requestinfo) {
  return await axios({
    //responseType: "stream",
    method: "post",
    url: url.resolve(config.host.pdf, config.paths.pdf_create_upload),
    data: Object.assign(requestinfo, data),
    params: {
      tenantId: tenantId,
      key: key,
    },
  });
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
};
