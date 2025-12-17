import BillingService from "../../../elements/Bill";
import { ContractService } from "../../../elements/Contracts";
import AttendanceService from "../../../elements/Attendance";
import { WageSeekerService } from "../../../elements/WageSeeker";

const getBeneficiaryData = async (wageBillDetails, tenantId, musterRoll, t, indResponse) => {
  let tableData = {}
  const individuals = indResponse?.Individual
  if(wageBillDetails?.length > 0) {
    wageBillDetails?.forEach((item, index) => {
      let tableRow = {}
      const individual = individuals?.find(ind => ind?.id === item?.payee?.identifier)
      tableRow.id = item?.id
      tableRow.sno = index + 1
      //tableRow.registerId = individual?.individualId || t("NA")
      tableRow.registerId = {to:`/works-ui/employee/masters/view-wageseeker?tenantId=${tenantId}&individualId=${individual?.individualId}`, label:individual?.individualId, isLink:true}
      tableRow.nameOfIndividual = individual?.name?.givenName || t("NA")
      tableRow.guardianName = individual?.fatherName || t("NA")
      // tableRow.amount = item?.payableLineItems?.[0]?.amount || 0 //check if correct(add all payable here)
      tableRow.amount = item?.payableLineItems?.reduce((acc,item)=>{
        if(item?.type==="PAYABLE") return acc + item.amount
        return acc 
      },0) || 0 //check if correct(add all payable here)
      // tableRow.bankAccountDetails = {
      //   accountNo : individual?.additionalDetails?.bankDetails || t("NA"), 
      //   ifscCode : null
      // }
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
    //totalRow.bankAccountDetails = ""
            
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
  const location = {
    "ward":wageBill?.additionalDetails?.ward?t(`${headerLocale}_ADMIN_${wageBill?.additionalDetails?.ward}`):null,
    "locality":wageBill?.additionalDetails?.locality?t(`${headerLocale}_ADMIN_${wageBill?.additionalDetails?.locality}`):null,
    "city":wageBill?.tenantId ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(wageBill?.tenantId)}`) :null
  };
  const locationString = `${location.locality ? location.locality + ", " : ""}${location.ward ? location.ward + ", " : ""}${location.city ? location.city : ""}`
 // const location = t(`TENANT_TENANTS_${headerLocale}`)

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

  let IndsToSearch = musterRes?.musterRolls?.[0].individualEntries.map((ob) => ob?.individualId)

  let indPayload =IndsToSearch?.length!==0 ? {
    Individual:{
      id:IndsToSearch
    }
  } : null

  const indResponse = await WageSeekerService.search(tenantId, indPayload, {tenantId,offset:0,limit:100});
  
  const beneficiaryData = await getBeneficiaryData(wageBill?.billDetails, tenantId, musterRoll, t, indResponse)

  const billDetails = {
    title: " ",
    asSectionHeader: true,
    values: [
      { title: "WORKS_BILL_NUMBER", value: wageBill?.billNumber || t("ES_COMMON_NA")},
      { title: "WORKS_BILL_DATE", value: Digit.DateUtils.ConvertTimestampToDate(wageBill?.billDate, 'dd/MM/yyyy') || t("ES_COMMON_NA") },
      { title: "WORKS_ORDER_NO", value: workOrderNum || t("ES_COMMON_NA")},
      { title: "WORKS_PROJECT_ID", value: contract?.additionalDetails?.projectId || t("ES_COMMON_NA")},
      { title: "PROJECTS_DESCRIPTION", value: contract?.additionalDetails?.projectDesc || t("ES_COMMON_NA") },
      { title: "ES_COMMON_LOCATION", value: locationString || t("ES_COMMON_NA") }
    ]
  }

  const beneficiaryDetails = {
    title: "EXP_BENEFICIARY_DETAILS",
    asSectionHeader: true,
    values: [
        { title: "ES_COMMON_MUSTER_ROLL_ID", value: musterRollNum || t("ES_COMMON_NA"), isLink : true, to : `/works-ui/employee/attendencemgmt/view-attendance?tenantId=${tenantId}&musterRollNumber=${musterRollNum}`},
        { title: "ES_COMMON_MUSTER_ROLL_PERIOD", value: `${Digit.DateUtils.ConvertTimestampToDate(musterRoll?.startDate, 'dd/MM/yyyy')} - ${Digit.DateUtils.ConvertTimestampToDate(musterRoll?.endDate, 'dd/MM/yyyy')}` || t("ES_COMMON_NA") }
    ],
    additionalDetails : {
      table : {
        mustorRollTable : true,
        tableData: beneficiaryData
      }
    }
  }
 
  const calcDeductions = wageBill?.billDetails
    ?.map((item) => {
      return item.payableLineItems.filter((item) => item.headCode === "LC");
    })
    ?.reduce((acc, item) => {
      return (item?.[0]?.amount ? item?.[0]?.amount : 0) + acc;
    }, 0);

  const billAmount = {
    title: "EXP_BILL_DETAILS",
    asSectionHeader: true,
    values: [
        { title: "EXP_BILL_AMOUNT", value: Digit.Utils.dss.formatterWithoutRound(Math.round(wageBill?.totalAmount), "number") || t("ES_COMMON_NA")},
        { title: "WB_DEDUCTIONS", value: Digit.Utils.dss.formatterWithoutRound(calcDeductions.toFixed(2), "number") || t("ES_COMMON_NA")},
    ],
    amountStyle: {
      width:"8rem",
      textAlign:"right"
    }
  }

  const netPayable = {
    title: " ",
    asSectionHeader: true,
    Component: Digit.ComponentRegistryService.getComponent("PayableAmt"),
    value: Digit.Utils.dss.formatterWithoutRound(Math.round(wageBill?.totalAmount-calcDeductions), "number") || t("ES_COMMON_NA")
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