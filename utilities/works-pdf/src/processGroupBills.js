var { search_expense_bill, search_bank_account_details, upload_file_using_filestore, pool } = require("./api");
const XLSX = require('xlsx');
var logger = require("./logger").logger;
async function processGroupBill(requestData) {
    try {
        let paymentId = requestData.paymentId;
        let tenantId = requestData.tenantId;
        let userId = requestData?.RequestInfo?.userInfo?.uuid;
        let bills = await getBillDetails(requestData);
        let billsForExcel = [];
        if (bills) {
            bills.forEach((bill, idx) => {
                let nBills = getBillsForExcel(bill);
                if (nBills.length)
                billsForExcel = billsForExcel.concat(nBills);
            })
        }
        
        let accountIdMap = {};
        let beneficiaryIds = []
        billsForExcel.forEach((bill) => {
            if (bill?.beneficiaryId && !accountIdMap[bill.beneficiaryId]) {
                accountIdMap[bill.beneficiaryId] = {}
                beneficiaryIds.push(bill.beneficiaryId)
            }
        })
        let bankAccounts = await getBankAccountDetails(requestData, beneficiaryIds);
        bankAccounts.forEach((bankAccount) => { accountIdMap[bankAccount.id] = bankAccount });
        billsForExcel = billsForExcel.map((billDetails) => {
            if (accountIdMap[billDetails.beneficiaryId]) {
                let accountDetails = accountIdMap[billDetails.beneficiaryId];
                let bankAccountDetails = accountDetails?.bankAccountDetails[0] || {};
                billDetails['bankAccountNumber'] = bankAccountDetails?.accountNumber || "";
                billDetails['accountType'] = bankAccountDetails?.accountType || "";
                billDetails['beneficiaryName'] = bankAccountDetails?.accountHolderName || "";
                billDetails['ifsc'] = bankAccountDetails?.bankBranchIdentifier?.code || "";
            }
            return billDetails;
        })
        let filestoreId = await createXlsxFromBills(billsForExcel, paymentId, tenantId);
        let billsLength = bills.length;
        let numberofbeneficialy = billsForExcel.length;
        let totalAmount = 0;
        bills.forEach((bill) => {
            if (bill?.totalAmount) {
                totalAmount = totalAmount + bill?.totalAmount;
            }
        })
        await updateForJobCompletion(paymentId, filestoreId, userId, billsLength, numberofbeneficialy, totalAmount);
        return filestoreId;
    } catch (error) {
        console.log("err", error)
        return null
    }
}

const getBillDetails = async (requestData) => {
    let bills = [];
    try {
        let request = {}
        request['requestInfo'] = requestData['RequestInfo']
        request['billCriteria'] = {
            tenantId: requestData.tenantId,
            ids: requestData.billIds
        }
        // Get how many expenses are there
        // let expenseResponse = await search_expense_bill(request, 1, 0)
        // let pagination = expenseResponse?.pagination;
        // pagination.totalCount = 11;
        // let total = pagination.totalCount;
        let total = requestData.billIds.length;
        if (total) {
            let limit = 100;
            let rounds = total / limit;
            let promises = [];
            for (let idx = 0; idx < rounds; idx++) {
                let offset = limit * idx;
                promises.push(fetchBillDetailsByRequest(deepClone(request), limit, offset));
            }
            let billResponse = await Promise.all(promises)
            for (let idx = 0; idx < billResponse.length; idx++) {
                bills = bills.concat(billResponse[idx]);
            }
        }
    } catch (error) {
        console.log('err', error)
    }
    return bills;
}

const getBankAccountDetails = async (requestData, beneficiaryIds) => {
    let bankAccounts = []
    let defaultRequest = {}
    defaultRequest['requestInfo'] = requestData['RequestInfo'];
    defaultRequest["bankAccountDetails"] = {};
    defaultRequest["bankAccountDetails"]["tenantId"] = requestData?.tenantId;
    defaultRequest["bankAccountDetails"]["ids"] = beneficiaryIds;
    let total = beneficiaryIds.length;
    if (total) {
        let limit = total;
        let rounds = total / limit;
        let requests = [];
        for (let idx = 0; idx < rounds; idx++) {
            let nRequest = {...defaultRequest}
            nRequest['pagination'] = {
                limit: limit,
                offset: idx * limit
            }
            requests.push(nRequest);
        }
        let promises = [];
        requests.forEach((request) => {
            promises.push(fetchBankAccountDetailsByRequest(deepClone(request)));
        })
        let bankAccountResponse = await Promise.all(promises)
        for (let idx = 0; idx < bankAccountResponse.length; idx++) {
            bankAccounts = bankAccounts.concat(bankAccountResponse[idx]);
        }
    }
    return bankAccounts;
}

const fetchBillDetailsByRequest = (request, limit, offset) => {
    return new Promise((resolve, reject) => {
        try {
            search_expense_bill(deepClone(request), limit, offset).then((data) => {
                if (data?.bills?.length) {
                    resolve(data.bills)
                } else {
                    resolve([])
                }
            }).catch(err => reject(err))
        } catch (error) {
            reject(error)
            console.log(error)
        }
    })
}

const fetchBankAccountDetailsByRequest = (request) => {
    return new Promise((resolve, reject) => {
        try {
            search_bank_account_details(deepClone(request)).then((data) => {
                if (data?.bankAccounts?.length) {
                    resolve(data.bankAccounts)
                } else {
                    resolve([])
                }
            }).catch(err => reject(err))
        } catch (error) {
            reject(error)
            console.log(error)
        }
    })
}


const getBillsForExcel = (bill) => {
    let bills = []
    let billObj = {
        "projectId" : "NA",
        "workOrderId" : bill?.referenceId,
        "billNumber" : bill?.billNumber,
        "billType" :  bill?.businessService,
        "grossAmount" : 0,
        "beneficiaryType" : "",
        "beneficiaryName" : "",
        "beneficiaryId" : "",
        "accountType" : "", // call bank account service with paye.identifire id
        "bankAccountNumber" : "",
        "ifsc" : "",
        "payableAmount" : 0,
        "headCode": ""
    }
    if (!(bill.billDetails && bill.billDetails.length)) { 
        return [billObj];
    }
    let businessService = bill['businessService'];
    console.log('businessService ' + businessService)
    if (businessService == 'works.wages') {
        let wagesBills = getWagesBill(bill, billObj);
        bills = bills.concat(wagesBills);
    } else if (businessService == 'works.purchase') {
        let purchaseBills = getPurchaseBill(bill, billObj);
        bills = bills.concat(purchaseBills);
    } else if (businessService == 'works.supervision') {
        let supervisionBills = getSupervisionBill(bill, billObj);
        bills = bills.concat(supervisionBills);
    } else {
        bills = [billObj];
    }
    return bills;
}

let getWagesBill = (bill, billObj) => {
    let bills = [];
    bill?.billDetails.forEach(billDetail => {
        let newBill = deepClone(billObj);
        newBill['beneficiaryId'] = billDetail?.payee?.identifier || "";
        newBill['beneficiaryType'] = billDetail?.payee?.type || "";
        newBill['grossAmount'] = billDetail?.netLineItemAmount || 0;
        newBill['payableAmount'] = billDetail?.netLineItemAmount || 0;
        newBill['headCode'] = billDetail?.payableLineItems[0]?.headCode || "";
        bills.push(newBill)
    });
    return bills;
}

let getPurchaseBill = (bill, billObj) => {
    let bills = [];
    bill?.billDetails.forEach(billDetail => {
        if (billDetail?.payableLineItems?.length) {
            billDetail?.payableLineItems.forEach((payableLineItem) => {
                let newBill = deepClone(billObj);
                newBill['beneficiaryId'] = billDetail?.payee?.identifier || "";
                newBill['beneficiaryType'] = billDetail?.payee?.type || "";
                newBill['grossAmount'] = payableLineItem?.amount || 0;
                newBill['payableAmount'] = payableLineItem?.amount || 0;
                newBill['headCode'] = payableLineItem?.headCode || "";
                bills.push(newBill)
            })
        }
    });
    return bills;
}

let getSupervisionBill = (bill, billObj) => {
    let bills = [];
    let newBill = deepClone(billObj);
    newBill['beneficiaryId'] = bill?.billDetails[0]?.payee?.identifier || "";
    newBill['beneficiaryType'] = bill?.billDetails[0]?.payee?.type || "";
    newBill['grossAmount'] = bill?.billDetails[0]?.netLineItemAmount || 0;
    newBill['payableAmount'] = bill?.billDetails[0]?.netLineItemAmount || 0;
    newBill['headCode'] = bill?.billDetails[0]?.payableLineItems[0]?.headCode || "";
    bills.push(newBill)
    return bills;
}

let createXlsxFromBills = async (billsData, paymentId, tenantId) => {
    const data = billsData.map((obj, idx) => [
        idx+1,
        obj.projectId,
        obj.workOrderId,
        obj.billNumber,
        obj.billType,
        obj.grossAmount,
        obj.beneficiaryType,
        obj.beneficiaryName,
        obj.beneficiaryId,
        obj.accountType,
        obj.bankAccountNumber,
        obj.ifsc,
        obj.payableAmount,
        obj.headCode
    ]);
    const headers = [
        "Sr. No.",
        "Project ID",
        "Work Order ID",
        "Bill Number",
        "Bill Type",
        "Gross Amount",
        "Beneficiary Type",
        "Beneficiary Name",
        "Beneficiary ID",
        "Account Type",
        "Bank Account Number",
        "IFSC",
        "Payable Amount",
        "Head Code"
    ];
    const worksheet = XLSX.utils.aoa_to_sheet([headers, ...data]);

    // Create a new workbook and add the worksheet to it
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Payment');
    const buffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'buffer' });
    try {
        let filename = `${paymentId} - ${new Date().getTime()}.xlsx`;
        let filestoreId = await upload_file_using_filestore(filename, tenantId, buffer);
        return filestoreId;
    } catch (error) {
        console.log('Error : ', error)
        throw(error)
    }
}

async function updateForJobCompletion (paymentId, filestoreId, userId, billsLength, numberofbeneficialy, totalAmount) {
    try {
        const updateQuery = 'UPDATE eg_payments_excel SET filestoreid =  $1, lastmodifiedby = $2, lastmodifiedtime = $3, status = $4, numberofbills = $5, numberofbeneficialy = $6, totalamount = $7 WHERE paymentid = $8';
        const curentTimeStamp = new Date().getTime();
        let status = "COMPLETED";
        await pool.query(updateQuery, [filestoreId, userId, curentTimeStamp, status, billsLength, numberofbeneficialy, totalAmount, paymentId]);
    } catch (error) {
        logger.error("Error occured while updating the eg_payments_excel table.");
        logger.error(error.stack || error);
    }
}

let deepClone = (data) => { return JSON.parse(JSON.stringify(data)) };

module.exports = { processGroupBill };