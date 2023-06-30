const get = require("lodash.get");
const { create_audit_details } = require("../api");

async function createEgPaymentsExcelLog(requestData, values) {
    await createAuditLogs(requestData, "CREATE", {
        paymentId, 
        paymentNumber, 
        "tenantId": requestData.payment.tenantId, 
        "status": "INPROGRESS",
        "lastmodifiedby": userid,
        "lastmodifiedtime": new Date().getTime()
    })
}

async function createAuditLogs(requestData, operation, values){
    let RequestInfo = requestData.RequestInfo;
    let AuditLogs = []
    let keyValueMap = {}
    Object.keys(values).forEach(function(key) {
        keyValueMap[key] = values[key]
    });
    let AuditLogObj = {
        "userUUID" : RequestInfo.userInfo.uuid,
        "module": "PA",
        "tenantId": RequestInfo.userInfo.tenantId,
        "transactionCode": "PA."+operation,
        "changeDate": new Date().getTime(),
        "entityName": "eg_payments_excel",
        "objectId": values.paymentId,
        keyValueMap,
        "operationType": operation
    }
    AuditLogs.push(AuditLogObj);
    let request = {RequestInfo, AuditLogs}
    await create_audit_details(request);
}


module.exports = { createAuditLogs };