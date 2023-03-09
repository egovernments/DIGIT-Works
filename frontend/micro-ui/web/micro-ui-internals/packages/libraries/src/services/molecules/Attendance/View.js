import AttendanceService from "../../elements/Attendance";

const attendanceTypes = {
  0 : 'zero',
  1 : 'full',
  0.5 : 'half'
 }

const getWeekDates = (data) => {
  let weekDates = {}
  if(data?.individualEntries?.length > 0) {
    const attendanceEntry = data?.individualEntries[0]?.attendanceEntries
    attendanceEntry.forEach(item => {
      weekDates[`${Digit.DateUtils.getDayfromTimeStamp(item?.time)}`] = Digit.DateUtils.ConvertTimestampToDate(item?.time, 'MMM d')
    })
  }
  return weekDates
}

const getWeekAttendance = (data) => {
  let weekAttendance = {}
  if(data.length > 0) {
    data.forEach(item => {
      weekAttendance[`${Digit.DateUtils.getDayfromTimeStamp(item?.time)}`] = attendanceTypes[item.attendance]
    })
  }
  return weekAttendance
}

const getAttendanceTableData = (data, skills, t) => {
  let tableData = {}
  if(data?.individualEntries?.length > 0) {
    data?.individualEntries.forEach((item, index) => {
      let tableRow = {}
      tableRow.id = item.id
      tableRow.sno = index + 1
      tableRow.registerId = t("NA") || data?.registerId 
      tableRow.actualWorkingDays = item?.actualTotalAttendance
      tableRow.nameOfIndividual = item?.additionalDetails?.userName || "Piyush HarjitPal"
      tableRow.guardianName = item?.additionalDetails?.fatherName  || "Harijitpal"
      tableRow.skill = skills[item?.additionalDetails?.skillCode]?.name || t("NA")
      tableRow.amount = skills[item?.additionalDetails?.skillCode]?.amount * item?.actualTotalAttendance || 0
      tableRow.modifiedAmount = (item?.modifiedTotalAttendance ? (skills[item?.additionalDetails?.skillCode]?.amount * item?.modifiedTotalAttendance) : tableRow?.amount) || 0
      tableRow.modifiedWorkingDays = item?.modifiedTotalAttendance ? item?.modifiedTotalAttendance : item?.actualTotalAttendance
      tableRow.bankAccountDetails = {
        accountNo : item?.additionalDetails?.bankDetails || t("NA"),
        ifscCode : null
      }
      tableRow.aadharNumber = item?.additionalDetails?.aadharNumber || t("NA")
      tableRow.attendence = getWeekAttendance(item?.attendanceEntries)
      tableData[item.id] = tableRow
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
    totalRow.aadharNumber = ""
    totalRow.attendence = { Sun: 0, Sat: 0, Fri: 0, Thu: 0, Wed: 0, Tue: 0, Mon: 0 }
            
    tableData['total'] = totalRow
  }
  return tableData
}

const transformViewDataToApplicationDetails = (t, data, workflowDetails, skills) => {
  if(data.musterRolls.length === 0) return;
  
  const musterRoll = data.musterRolls[0]
  const attendanceTableData = getAttendanceTableData(musterRoll, skills, t)
  const weekDates = getWeekDates(musterRoll)
  const registrationDetails = {
    title: "ATM_REGISTRATION_DETAILS",
    applicationData: musterRoll,
    asSectionHeader: true,
    values: [
      { title: "ES_COMMON_ORG_NAME", value: musterRoll?.additionalDetails?.orgName || t("NA") },
      { title: "ATM_REGISTER_ID", value: musterRoll?.additionalDetails?.attendanceRegisterNo || t("NA")},
      { title: "ATM_REGISTER_NAME", value: musterRoll?.additionalDetails?.attendanceRegisterName || t("NA") },
      { title: "ATM_ATTENDENCE_FOR_WEEK", value: `${Digit.DateUtils.ConvertTimestampToDate(musterRoll?.startDate, 'dd/MM/yyyy')} - ${Digit.DateUtils.ConvertTimestampToDate(musterRoll?.endDate, 'dd/MM/yyyy')}` || t("NA") },
    ],
    additionalDetails: {
      table: {
        weekTable: {
          tableHeader: "ATM_ENROLLED_USERS",
          renderTable: true,
          tableData: attendanceTableData,
          weekDates: weekDates
        },
      }
    },
  };
  const applicationDetails = { applicationDetails: [registrationDetails] };

  return {
    applicationDetails,
    applicationData: musterRoll,
    processInstancesDetails: workflowDetails?.ProcessInstances,
    workflowDetails
  }
};

const workflowDataDetails = async (tenantId, businessIds) => {
    const response = await Digit.WorkflowService.getByBusinessId(tenantId, businessIds);
    return response
}

const getWageSeekerSkills = async () => {
  const skills = {}
  const response = await Digit.MDMSService.getMultipleTypesWithFilter(Digit.ULBService.getStateId(), "common-masters", [{"name": "WageSeekerSkills"}])
  response?.['common-masters']?.WageSeekerSkills.forEach(item => (skills[item.code] = item))
  return skills
}

export const fetchAttendanceDetails = async (t, tenantId, searchParams) => {
  try {
    const response = await AttendanceService.search(tenantId, searchParams);
    const workflowDetails = await workflowDataDetails(tenantId, searchParams.musterRollNumber);
    const skills = await getWageSeekerSkills()
    return transformViewDataToApplicationDetails(t, response, workflowDetails, skills)
  } catch (error) {
      console.log('error', error);
      throw new Error(error?.response?.data?.Errors[0].message);
  }
};