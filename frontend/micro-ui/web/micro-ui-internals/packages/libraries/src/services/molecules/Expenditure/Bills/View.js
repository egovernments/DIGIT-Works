import BillingService from "../../../elements/Bill";

const getBeneficiaryData = (wageBillDetails, tenantId, t) => {
  let tableData = {}
  if(wageBillDetails?.lineItems?.length > 0) {
    const payload = {
      Individual: {
        id : [] //array of individual Ids
      }
    }
    const searchParams = { offset: 0, limit: 100}

    //const data = await WageSeekerService.search(tenantId, payload, searchParams);
    //const individualData = data?.Individual

    const individualData = [] //remove this

    wageBillDetails?.lineItems.forEach((item, index) => {
      let tableRow = {}
      const individual = individualData?.find(ind => ind?.id === item?.id)
      tableRow.id = item?.id
      tableRow.sno = index + 1
      tableRow.registerId = individual?.individualId || t("NA")
      tableRow.nameOfIndividual = individual?.name?.givenName || t("NA")
      tableRow.guardianName = individual?.fatherName  || t("NA")
      tableRow.amount = item?.amount || 0
      tableRow.bankAccountDetails = {
        accountNo : item?.additionalDetails?.bankDetails || t("NA"), //check where to get these details
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
  const wageBillDetails = wageBill?.billDetails?.[0]
  const beneficiaryData = getBeneficiaryData(wageBillDetails, tenantId, t)
  const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())
  const location = t(`TENANT_TENANTS_${headerLocale}`)

  const billDetails = {
    title: " ",
    asSectionHeader: true,
    values: [
      { title: "WORKS_BILL_NUMBER", value: wageBillDetails?.billId || t("ES_COMMON_NA")},
      { title: "WORKS_BILL_DATE", value: Digit.DateUtils.ConvertTimestampToDate(wageBill?.billDate, 'dd/MM/yyyy') || t("ES_COMMON_NA") },
      { title: "WORKS_ORDER_NO", value: 'WO/2022-23/000052' || t("ES_COMMON_NA")},
      { title: "WORKS_PROJECT_ID", value: 'PJ/2022-23/000051' || t("ES_COMMON_NA")},
      { title: "PROJECTS_DESCRIPTION", value: 'RWHS Scheme at Ward 2' || t("ES_COMMON_NA") },
      { title: "ES_COMMON_LOCATION", value: location || t("ES_COMMON_NA") }
    ]
  }

  const beneficiaryDetails = {
    title: "EXP_BENEFICIARY_DETAILS",
    asSectionHeader: true,
    values: [
        { title: "ES_COMMON_MUSTER_ROLL_ID", value: 'NMR-0000520021' || t("ES_COMMON_NA")},
        { title: "ES_COMMON_MUSTER_ROLL_PERIOD", value: '06/03/2023 - 12/03/2023' || t("ES_COMMON_NA") }
        // { title: "ES_COMMON_MUSTER_ROLL_PERIOD", value: `${Digit.DateUtils.ConvertTimestampToDate(musterRoll?.startDate, 'dd/MM/yyyy')} - ${Digit.DateUtils.ConvertTimestampToDate(musterRoll?.endDate, 'dd/MM/yyyy')}` || t("ES_COMMON_NA") }
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
          console.log('error', error);
          throw new Error(error?.response?.data?.Errors[0].message);
      }
    }
}