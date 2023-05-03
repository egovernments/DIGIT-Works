import BillingService from "../../../elements/Bill";
import { ContractService } from "../../../elements/Contracts";
import AttendanceService from "../../../elements/Attendance";

const getBeneficiaryData = async (wageBillDetails, tenantId, musterRoll, t) => {
  let tableData = {}
  const individuals = musterRoll?.individualEntries
  if(wageBillDetails?.length > 0) {
    wageBillDetails?.forEach((item, index) => {
      let tableRow = {}
      const individual = individuals?.find(ind => ind?.individualId === item?.payee?.identifier)
      tableRow.id = item?.id
      tableRow.sno = index + 1
      tableRow.registerId = individual?.additionalDetails?.userId || t("NA")
      tableRow.nameOfIndividual = individual?.additionalDetails?.userName || t("NA")
      tableRow.guardianName = individual?.additionalDetails?.fatherName || t("NA")
      tableRow.amount = item?.payableLineItems?.[0]?.amount || 0 //check if correct
      tableRow.bankAccountDetails = {
        accountNo : individual?.additionalDetails?.bankDetails || t("NA"), 
        ifscCode : null
      }
      //update this id
      tableData[item.id] = tableRow
    });

    //Add row to show Total data
    let totalRow = {}
    totalRow.type = "total"
    totalRow.sno = "RT_TOTAL"
    totalRow.registerId = "DNR"
    totalRow.nameOfIndividual = "DNR"
    totalRow.guardianName = "DNR"
    totalRow.amount = 0
    totalRow.bankAccountDetails = ""
            
    tableData['total'] = totalRow
  }
  return tableData
}

const transformViewDataToApplicationDetails = async (t, data, tenantId) => {
  if(data.bills.length === 0) throw new Error('No data found');
  
  const wageBill = data.bills[0]
  const referenceIds = wageBill?.referenceId?.split('_')
  const workOrderNum = referenceIds?.[0]
  const musterRollNum = referenceIds?.[1]

  const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())
  const location = t(`TENANT_TENANTS_${headerLocale}`)

  //get contract details
  const contractPayload = {
    tenantId,
    contractNumber: workOrderNum
  }
  const contractRes = await ContractService.search(tenantId, contractPayload, {});
  const contract = contractRes?.contracts?.[0]

  //get muster details
  const musterRes = await AttendanceService.search(tenantId, { musterRollNumber: musterRollNum });
  const musterRoll = musterRes?.musterRolls?.[0]
  
  const beneficiaryData = await getBeneficiaryData(wageBill?.billDetails, tenantId, musterRoll, t)

  const billDetails = {
    title: " ",
    asSectionHeader: true,
    values: [
      { title: "WORKS_BILL_NUMBER", value: wageBill?.billNumber || t("ES_COMMON_NA")},
      { title: "WORKS_BILL_DATE", value: Digit.DateUtils.ConvertTimestampToDate(wageBill?.billDate, 'dd/MM/yyyy') || t("ES_COMMON_NA") },
      { title: "WORKS_ORDER_NO", value: workOrderNum || t("ES_COMMON_NA")},
      { title: "WORKS_PROJECT_ID", value: contract?.additionalDetails?.projectId || t("ES_COMMON_NA")},
      { title: "PROJECTS_DESCRIPTION", value: contract?.additionalDetails?.projectDesc || t("ES_COMMON_NA") },
      { title: "ES_COMMON_LOCATION", value: location || t("ES_COMMON_NA") }
    ]
  }

  const beneficiaryDetails = {
    title: "EXP_BENEFICIARY_DETAILS",
    asSectionHeader: true,
    values: [
        { title: "ES_COMMON_MUSTER_ROLL_ID", value: musterRollNum || t("ES_COMMON_NA")},
        { title: "ES_COMMON_MUSTER_ROLL_PERIOD", value: `${Digit.DateUtils.ConvertTimestampToDate(musterRoll?.startDate, 'dd/MM/yyyy')} - ${Digit.DateUtils.ConvertTimestampToDate(musterRoll?.endDate, 'dd/MM/yyyy')}` || t("ES_COMMON_NA") }
    ],
    additionalDetails : {
      table : {
        mustorRollTable : true,
        tableData: beneficiaryData
      }
    }
  }

  const billAmount = {
    title: "EXP_BILL_DETAILS",
    asSectionHeader: true,
    values: [
        { title: "EXP_BILL_AMOUNT", value: Digit.Utils.dss.formatterWithoutRound(wageBill?.netPayableAmount, "number") || t("ES_COMMON_NA")},
    ]
  }

  const netPayable = {
    title: " ",
    asSectionHeader: true,
    Component: Digit.ComponentRegistryService.getComponent("PayableAmt"),
    value: Digit.Utils.dss.formatterWithoutRound(wageBill?.netPayableAmount, "number") || t("ES_COMMON_NA")
}

  const applicationDetails = { applicationDetails: [billDetails, beneficiaryDetails, billAmount, netPayable] };

  return {
    applicationDetails,
    applicationData: wageBill,
    processInstancesDetails: {},
    workflowDetails: {}
  }
}

export const View = {
    fetchBillDetails: async (t, tenantId, data) => {
      try {
        const response = await BillingService.search(data);
        return transformViewDataToApplicationDetails(t, response, tenantId)
      } catch (error) {
          throw new Error(error?.response?.data?.Errors[0].message);
      }
    }
}