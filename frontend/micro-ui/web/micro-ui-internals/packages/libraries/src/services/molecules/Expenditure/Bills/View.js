import AttendanceService from "../../../elements/Attendance";

const getAttendanceTableData = (data, skills, t) => {
  let tableData = {}
  if(data?.individualEntries?.length > 0) {
    data?.individualEntries.forEach((item, index) => {
      let tableRow = {}
      tableRow.id = item.id
      tableRow.sno = index + 1
      tableRow.registerId = item?.additionalDetails?.userId || t("NA")
      tableRow.nameOfIndividual = item?.additionalDetails?.userName || t("NA")
      tableRow.guardianName = item?.additionalDetails?.fatherName  || t("NA")
      tableRow.skill = t(`COMMON_MASTERS_SKILLS_${item?.additionalDetails?.skillCode}`) || t("NA")
      tableRow.actualWorkingDays = item?.actualTotalAttendance
      tableRow.modifiedWorkingDays = item?.modifiedTotalAttendance ? item?.modifiedTotalAttendance : item?.actualTotalAttendance
      tableRow.amount = skills[item?.additionalDetails?.skillCode]?.amount || 0
      tableRow.modifiedAmount = (item?.modifiedTotalAttendance ? (tableRow?.amount * item?.modifiedTotalAttendance) : (tableRow?.amount * item?.actualTotalAttendance)) || 0
      tableRow.bankAccountDetails = {
        accountNo : item?.additionalDetails?.bankDetails || t("NA"),
        ifscCode : null
      }
      tableRow.paymentStatus = 'PAYMENT_PENDING'
      //tableRow.aadharNumber = item?.additionalDetails?.aadharNumber || t("NA")
      tableData[item.individualId] = tableRow
    });

    //Add row to show Total data
    let totalRow = {}
    totalRow.type = "total"
    totalRow.sno = "ES_COMMON_TOTAL_AMOUNT"
    totalRow.registerId = "DNR"
    totalRow.nameOfIndividual = "DNR"
    totalRow.guardianName = "DNR"
    totalRow.skill = ""
    totalRow.amount = 0
    totalRow.modifiedAmount = 0
    totalRow.actualWorkingDays = 0
    totalRow.modifiedWorkingDays = 0
    totalRow.bankAccountDetails = ""
    totalRow.paymentStatus = ""
    //totalRow.aadharNumber = ""
            
    tableData['total'] = totalRow
  }
  return tableData
}

const transformViewDataToApplicationDetails = async (t, data, skills) => {
  if(data.musterRolls.length === 0) return;
  
  const musterRoll = data.musterRolls[0]
  const musterTableData = getAttendanceTableData(musterRoll, skills, t)
  const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())
  const location = t(`TENANT_TENANTS_${headerLocale}`)

  const BillDetails = {
    title: " ",
    asSectionHeader: true,
    values: [
      { title: "WORKS_BILL_NUMBER", value: musterRoll?.musterRollNumber || t("ES_COMMON_NA")},
      { title: "WORKS_ORDER_NO", value: musterRoll?.additionalDetails?.contractId || t("ES_COMMON_NA")},
      { title: "WORKS_PROJECT_ID", value: musterRoll?.additionalDetails?.projectId || t("ES_COMMON_NA")},
      { title: "PROJECTS_DESCRIPTION", value: musterRoll?.additionalDetails?.projectName || t("ES_COMMON_NA") },
      { title: "ES_COMMON_LOCATION", value: location || t("ES_COMMON_NA") },
      { title: "EXP_BILL_CLASSIFICATION", value: musterRoll?.additionalDetails?.billType || t("ES_COMMON_NA") },
      { title: "WORKS_BILL_DATE", value: Digit.DateUtils.ConvertTimestampToDate(musterRoll?.auditDetails?.lastModifiedTime, 'dd/MM/yyyy') || t("ES_COMMON_NA") },
      { title: "ES_COMMON_STATUS", value: `BILL_STATUS_${musterRoll?.musterRollStatus}`|| t("ES_COMMON_NA") }
    ]
  }

  const beneficiaryDetails = {
    title: "EXP_BENEFICIARY_DETAILS",
    asSectionHeader: true,
    values: [
        { title: "ES_COMMON_MUSTER_ROLL_ID", value: musterRoll?.musterRollNumber || t("ES_COMMON_NA")},
        { title: "ES_COMMON_MUSTER_ROLL_PERIOD", value: `${Digit.DateUtils.ConvertTimestampToDate(musterRoll?.startDate, 'dd/MM/yyyy')} - ${Digit.DateUtils.ConvertTimestampToDate(musterRoll?.endDate, 'dd/MM/yyyy')}` || t("ES_COMMON_NA") }
    ],
    additionalDetails : {
      table : {
        mustorRollTable : true,
        tableData: musterTableData
      }
    }
  }

  const applicationDetails = { applicationDetails: [BillDetails, beneficiaryDetails] };

  return {
    applicationDetails,
    applicationData: musterRoll,
    processInstancesDetails: {},
    workflowDetails: {}
  }
}

const getWageSeekerSkills = async () => {
  const skills = {}
  const response = await Digit.MDMSService.getMultipleTypesWithFilter(Digit.ULBService.getStateId(), "common-masters", [{"name": "WageSeekerSkills"}])
  response?.['common-masters']?.WageSeekerSkills.forEach(item => (skills[item.code] = item))
  return skills
}

export const View = {
    fetchBillDetails: async (t, tenantId, data, searchParams) => {
      try {
        const response = await AttendanceService.search(tenantId, searchParams);
        const skills = await getWageSeekerSkills()
        return transformViewDataToApplicationDetails(t, response, skills)
      } catch (error) {
          console.log('error', error);
          throw new Error(error?.response?.data?.Errors[0].message);
      }
    }
}