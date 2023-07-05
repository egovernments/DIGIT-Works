import BillingService from "../../../elements/Bill";
import { ContractService } from "../../../elements/Contracts";
import AttendanceService from "../../../elements/Attendance";
import { ExpenseService } from "../../../elements/Expense";

const getBeneficiaryData = async (beneficiaries, tenantId, t) => {
  let tableData = {}
  // const individuals = musterRoll?.individualEntries
  if(beneficiaries?.length > 0) {
    beneficiaries?.forEach((item, index) => {
      let tableRow = {}
      const individual = beneficiaries?.find(ind => ind?.individualId === item?.payee?.identifier)
      tableRow.id = item?.id
      tableRow.sno = index + 1
      tableRow.beneficiaryId = individual?.additionalDetails?.userId || t("NA")
      tableRow.nameOfIndividual = individual?.additionalDetails?.userName || t("NA")
      tableRow.amount = item?.payableLineItems?.reduce((acc,item)=>{
        if(item?.type==="PAYABLE") return acc + item.amount
        return acc 
      },0) || 0 //check if correct(add all payable here)
      tableRow.accountNo = individual?.additionalDetails?.bankDetails?.accountNo || t("NA"), 
      tableRow.ifscCode = individual?.additionalDetails?.bankDetails?.ifscCode || t("NA"), 
      tableRow.status = individual?.paymentStatus,
      tableData[item.id] = tableRow
    });

    //Add row to show Total data
    let totalRow = {}
    totalRow.sno = "RT_TOTAL"
    totalRow.beneficiaryId = "DNR"
    totalRow.nameOfIndividual = "DNR"
    tableRow.accountNo = "DNR"
    tableRow.ifscCode = "DNR"
    tableRow.status = "DNR"
    totalRow.amount = 0
  }
  return tableData
}

const transformViewDataToApplicationDetails = async (t, data, tenantId) => {
  if(data.bills.length === 0) throw new Error('No data found');
  
  const paymentInstruction = data.payments[0]


  const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())
  const location = {
    "ward":paymentInstruction?.additionalDetails?.ward?t(`${headerLocale}_ADMIN_${paymentInstruction?.additionalDetails?.ward}`):null,
    "locality":paymentInstruction?.additionalDetails?.locality?t(`${headerLocale}_ADMIN_${paymentInstruction?.additionalDetails?.locality}`):null,
    "city":paymentInstruction?.tenantId ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(paymentInstruction?.tenantId)}`) :null
  };
  const locationString = `${location.locality ? location.locality + ", " : ""}${location.ward ? location.ward + ", " : ""}${location.city ? location.city : ""}`
 // const location = t(`TENANT_TENANTS_${headerLocale}`)

  //get contract details
  const contractPayload = {
    tenantId,
    contractNumber: workOrderNum
  }
  
  const beneficiaryData = await getBeneficiaryData(paymentInstruction?.billDetails, tenantId, t)

  const billDetails = {
    title: " ",
    asSectionHeader: true,
    values: [
      { title: "WORKS_BILL_NUMBER", value: paymentInstruction?.paymentNumber || t("ES_COMMON_NA")},
      { title: "WORKS_PAYMENT_INSTRUCT_TYPE", value: paymentInstruction?.type || t("ES_COMMON_NA")},
      { title: "WORKS_PARENT_PAYMENT_INSTRUCT_ID", value: paymentInstruction?.parentID || t("ES_COMMON_NA")},
      { title: "WORKS_PAYMENT_INSTRUCT_ID", value: paymentInstruction?.id || t("ES_COMMON_NA")},
      { title: "WORKS_PARENT_PAYMENT_INSTRUCT_DATE", value: Digit.DateUtils.ConvertTimestampToDate(paymentInstruction?.paymentDate, 'dd/MM/yyyy') || t("ES_COMMON_NA") },
      { title: "WORKS_HEAD_OF_ACCOUNTS", value: paymentInstruction?.headOfAccount || t("ES_COMMON_NA")},
      { title: "WORKS_PARENT_PAYMENT_INSTRUCT_MASTER_ALLOT_ID", value: paymentInstruction?.masterAllotId || t("ES_COMMON_NA")},
      { title: "WORKS_PAYMENT_INSTRUCT_GROSS_AMT", value: paymentInstruction?.grossAmount || t("ES_COMMON_NA") },
      { title: "WORKS_PAYMENT_INSTRUCT_NET_AMT", value: paymentInstruction?.netAmount || t("ES_COMMON_NA") }
    ]
  }

  const beneficiaryDetails = {
    title: "EXP_BENEFICIARY_DETAILS",
    asSectionHeader: true,
    values: [],
    additionalDetails : {
      table : {
        mustorRollTable : true,
        tableData: beneficiaryData
      }
    }
  }


  const billAmount = {
    title: "EXP_PAYMENT_ADVISE_DETAILS",
    asSectionHeader: true,
    values: [
        { title: "EXP_PAYMENT_ADV_ID", value: paymentInstruction?.paymentAdvise?.id || t("ES_COMMON_NA")},
        { title: "EXP_PAYMENT_ADV_DATE", value: Digit.DateUtils.ConvertTimestampToDate(paymentInstruction?.paymentAdvise?.date, 'dd/MM/yyyy') || t("ES_COMMON_NA") },
        { title: "EXP_PAYMENT_ADV_TOKEN_NO", value: paymentInstruction?.paymentAdvise?.tokenNo || t("ES_COMMON_NA")},
        { title: "EXP_PAYMENT_ADV_TOKEN_DATE", value: Digit.DateUtils.ConvertTimestampToDate(paymentInstruction?.paymentAdvise?.tokenDate, 'dd/MM/yyyy') || t("ES_COMMON_NA") },
        { title: "EXP_PAYMENT_ADV_ONLINE_BILL_NO", value: paymentInstruction?.paymentAdvise?.billNo || t("ES_COMMON_NA")},
    ],
    amountStyle: {
      width:"8rem",
      textAlign:"right"
    }
  }


  const applicationDetails = { applicationDetails: [billDetails, billAmount, beneficiaryDetails] };

  return {
    applicationDetails,
    applicationData: paymentInstruction,
    processInstancesDetails: {},
    workflowDetails: {}
  }
}

export const ViewPaymentInstruction = {
    fetchPaymentInstruction: async (t, tenantId, data) => {
      try {
        const response = await ExpenseService.searchPayment(data);
        return transformViewDataToApplicationDetails(t, response, tenantId)
      } catch (error) {
          throw new Error(error?.response?.data?.Errors[0].message);
      }
    }
}