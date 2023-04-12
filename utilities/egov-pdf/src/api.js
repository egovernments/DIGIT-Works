var config = require("./config");
var axios = require("axios").default;
var url = require("url");
var producer = require("./producer").producer;
var logger = require("./logger").logger;
const { Pool } = require('pg');

const pool = new Pool({
  user: config.DB_USER,
  host: config.DB_HOST,
  database: config.DB_NAME,
  password: config.DB_PASSWORD,
  port: config.DB_PORT,
});

auth_token = config.auth_token;

async function search_projectDetails(tenantId, requestinfo, projectId) {
  var params = {
    tenantId: tenantId,
    limit: 1,
    offset: 0
  };

  var searchEndpoint = config.paths.projectDetails_search;
  var data = {
    "Projects": [{
      "tenantId": tenantId,
      "projectNumber": projectId
    }]
  }
  return await axios({
    method: "post",
    url: url.resolve(config.host.projectDetails, searchEndpoint),
    data: Object.assign(requestinfo, data),
    params,
  });
}
async function search_musterRoll(tenantId, requestinfo, musterRollNumber) {
  var params = {
    tenantId: tenantId,
    musterRollNumber: musterRollNumber
  };

  var searchEndpoint = config.paths.musterRoll_search;

  return await axios({
    method: "post",
    url: url.resolve(config.host.musterRoll, searchEndpoint),
    data: Object.assign(requestinfo),
    params,
  });
}
async function search_contract(tenantId, requestinfo, contractId) {
  var params = {
    tenantId: tenantId,
    contractNumber: contractId
  };

  var searchEndpoint = config.paths.contract_search;

  return await axios({
    method: "post",
    url: url.resolve(config.host.contract, searchEndpoint),
    data: Object.assign(requestinfo, params),

  });
}
async function search_mdmsWageSeekerSkills(tenantId, requestinfo) {
  var params = {
    tenantId: tenantId.split(".")[0],
    moduleName: "common-masters",
    masterName: "WageSeekerSkills",
  };

  var searchEndpoint = config.paths.mdmsWageSeekerSkills_search;

  return await axios({
    method: "post",
    url: url.resolve(config.host.mdms, searchEndpoint),
    data: Object.assign(requestinfo),
    params

  });
}


async function search_estimateDetails(tenantId, requestinfo, estimateNumber) {
  var params = {
    tenantId: tenantId,
    estimateNumber: estimateNumber,
    limit: 1,
    _offset: 0,
    get offset() {
      return this._offset;
    },
    set offset(value) {
      this._offset = value;
    },
  };

  var searchEndpoint = config.paths.estimate_search;
  return await axios({
    method: "post",
    url: url.resolve(config.host.estimates, searchEndpoint),
    data: Object.assign(requestinfo),
    params,
  });
}

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
  var oj = Object.assign(requestinfo, data);
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


/**
 * It generates bill of property tax and merge into single PDF file
 * @param {*} kafkaData - Data pushed in kafka topic
 */
async function create_bulk_pdf_pt(kafkaData) {
  var propertyBills;
  var consolidatedResult = { Bill: [] };

  let {
    tenantId,
    locality,
    bussinessService,
    isConsolidated,
    consumerCode,
    requestinfo,
    jobid
  } = kafkaData;

  try {

    try {
      var searchCriteria = { locality, tenantId, bussinessService };
      propertyBills = await search_bill(
        null,
        null,
        {
          RequestInfo: requestinfo.RequestInfo,
          searchCriteria
        }
      );

      propertyBills = propertyBills.data.Bills;

      if (propertyBills.length > 0) {
        for (let propertyBill of propertyBills) {
          if (propertyBill.status === 'EXPIRED') {
            var billresponse = await fetch_bill(
              tenantId, propertyBill.consumerCode, propertyBill.businessService, { RequestInfo: requestinfo.RequestInfo }
            );
            if (billresponse?.data?.Bill?.[0]) consolidatedResult.Bill.push(billresponse.data.Bill[0]);
          }
          else {
            if (propertyBill.status === 'ACTIVE')
              consolidatedResult.Bill.push(propertyBill);
          }
        }
      }
    }
    catch (ex) {
      if (ex.response && ex.response.data) logger.error(ex.response.data);
      throw new Error("Failed to query details of property ");
    }

    if (consolidatedResult?.Bill?.length > 0) {
      var pdfResponse;
      var pdfkey = config.pdf.ptbill_pdf_template;
      try {
        var batchSize = config.PDF_BATCH_SIZE;
        var size = consolidatedResult.Bill.length;
        var numberOfFiles = (size % batchSize) == 0 ? (size / batchSize) : (~~(size / batchSize) + 1);
        for (var i = 0; i < size; i += batchSize) {
          var payloads = [];
          var billData = consolidatedResult.Bill.slice(i, i + batchSize);
          var billArray = {
            Bill: billData,
            isBulkPdf: true,
            pdfJobId: jobid,
            pdfKey: pdfkey,
            totalPdfRecords: size,
            currentPdfRecords: billData.length,
            tenantId: tenantId,
            numberOfFiles: numberOfFiles,
            locality: locality,
            service: bussinessService,
            isConsolidated: isConsolidated,
            consumerCode: consumerCode
          };
          var pdfData = Object.assign({ RequestInfo: requestinfo.RequestInfo }, billArray)
          payloads.push({
            topic: config.KAFKA_RECEIVE_CREATE_JOB_TOPIC,
            messages: JSON.stringify(pdfData)
          });
          producer.send(payloads, function (err, data) {
            if (err) {
              logger.error(err.stack || err);
              errorCallback({
                message: `error while publishing to kafka: ${err.message}`
              });
            } else {
              logger.info("jobid: " + jobid + ": published to kafka successfully");
            }
          });

        }

        try {
          const result = await pool.query('select * from egov_bulk_pdf_info where jobid = $1', [jobid]);
          if (result.rowCount >= 1) {
            const updateQuery = 'UPDATE egov_bulk_pdf_info SET totalrecords = $1 WHERE jobid = $2';
            await pool.query(updateQuery, [size, jobid]);
          }
        } catch (err) {
          logger.error(err.stack || err);
        }
      } catch (ex) {
        let errorMessage = "Failed to generate PDF";
        if (ex.response && ex.response.data) logger.error(ex.response.data);
        throw new Error(errorMessage);
      }
    } else {
      throw new Error("There is no billfound for the criteria");
    }

  } catch (ex) {
    throw new Error("Failed to query bill for property application");
  }

}

module.exports = {
  create_pdf,
  create_pdf_and_upload,
  search_mdms,
  search_user,
  search_workflow,
  search_projectDetails,
  search_estimateDetails,
  search_musterRoll,
  search_contract,
  search_mdmsWageSeekerSkills
};
