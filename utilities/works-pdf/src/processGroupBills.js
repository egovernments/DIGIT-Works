var { search_expense_bill } = require("./api");
const XLSX = require('xlsx');

async function processGroupBill(requestData) {
    try {
        let bills = await getBillDetails(requestData);
        let billsForExcel = [];
        if (bills) {
            bills.forEach((bill, idx) => {
                let nBills = getBillsForExcel(bill);
                console.log(idx + ' get bill called result ' + nBills.length)
                if (nBills.length)
                    billsForExcel = billsForExcel.concat(nBills);
            })
        }
        createXlsxFromBills(billsForExcel)
        return billsForExcel;
    } catch (error) {
        console.log("err", error)
        return null
    }
}

const getBillDetails = async (requestData) => {
    let bills = [];
    try {
        console.log('getBillDetails called !!')
        let request = {}
        request['requestInfo'] = requestData['RequestInfo']
        request['billCriteria'] = requestData['Criteria']
        // Get how many expenses are there
        let expenseResponse = await search_expense_bill(request, 1, 0)
        let pagination = expenseResponse?.pagination;
        pagination.totalCount = 11;
        let total = pagination.totalCount;
        if (total) {
            let limit = 5;
            let rounds = total / limit;
            let promises = [];
            for (let idx = 0; idx < rounds; idx++) {
                let offset = limit * idx;
                promises.push(fetchBillDetailsByRequest(JSON.parse(JSON.stringify(request)), limit, offset));
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

const fetchBillDetailsByRequest = (request, limit, offset) => {
    return new Promise((resolve, reject) => {
        try {
            search_expense_bill(JSON.parse(JSON.stringify(request)), limit, offset).then((data) => {
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
        newBill['beneficiaryId'] = billDetail?.payee?.identifier;
        newBill['beneficiaryType'] = billDetail?.payee?.type;
        newBill['grossAmount'] = billDetail?.netLineItemAmount || 0;
        newBill['payableAmount'] = billDetail?.netLineItemAmount || 0;
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
    bills.push(newBill)
    return bills;
}

let createXlsxFromBills = (billsData) => {
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
        obj.payableAmount
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
    ];
    const worksheet = XLSX.utils.aoa_to_sheet([headers, ...data]);

    // Create a new workbook and add the worksheet to it
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Payment');

    // Write the workbook to a file
    XLSX.writeFile(workbook, 'output.xlsx');
}

let deepClone = (data) => { return JSON.parse(JSON.stringify(data)) };

module.exports = { processGroupBill };