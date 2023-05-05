import AttendanceService from "../../elements/Attendance";
import { WorksService } from "../../elements/Works";

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

const getAttendanceTableData = async(data, skills, t, expenseCalculations) => {
  let tableData = {}

  if(data?.individualEntries?.length > 0) {
    data?.individualEntries.forEach((item, index) => {
      let tableRow = {}
      tableRow.id = item.id
      tableRow.sno = index + 1
      tableRow.registerId = item?.additionalDetails?.userId || t("NA")
      tableRow.actualWorkingDays = item?.actualTotalAttendance || 0
      tableRow.nameOfIndividual = item?.additionalDetails?.userName || t("NA")
      tableRow.guardianName = item?.additionalDetails?.fatherName  || t("NA")
      tableRow.skill = skills[item?.additionalDetails?.skillCode]?.name || t("NA")
      tableRow.amount = skills[item?.additionalDetails?.skillCode]?.amount * item?.actualTotalAttendance || 0
      tableRow.modifiedAmount = expenseCalculations?.filter(data=>data?.referenceId === item?.individualId)?.[0]?.lineItems?.[0]?.amount || 0;
      tableRow.modifiedWorkingDays = item?.modifiedTotalAttendance ? item?.modifiedTotalAttendance : item?.actualTotalAttendance
      tableRow.bankAccountDetails = {
        accountNo : item?.additionalDetails?.bankDetails || t("NA"),
        ifscCode : null
      }
      tableRow.aadharNumber = item?.additionalDetails?.aadharNumber || t("NA")
      tableRow.attendence = getWeekAttendance(item?.attendanceEntries)
      tableRow.perDayWage = skills[item?.additionalDetails?.skillCode]?.amount
      tableData[item.id] = tableRow
    });

    //Add row to show Total data
    let totalRow = {}
    totalRow.type = "total"
    totalRow.sno = "RT_TOTAL"
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

const transformViewDataToApplicationDetails = async (t, data, skills) => {

  const expenseCalculatorPayload = {
    criteria : {
      "tenantId": "pg.citya",
      "musterRollId": [ data?.musterRolls?.[0]?.id ]
    }
  }

  let expenseCalculationsResponse  =  await WorksService.fetchEstimateExpenseCalculator(expenseCalculatorPayload);
  let expenseCalculations = expenseCalculationsResponse?.calculation?.estimates?.[0]?.calcDetails;

  if(data?.musterRolls?.length === 0) throw new Error('No data found');
  
  const musterRoll = data.musterRolls[0]
  const attendanceTableData = await getAttendanceTableData(musterRoll, skills, t, expenseCalculations)
  
  const totalAmount = expenseCalculationsResponse?.calculation?.totalAmount;
  const weekDates = getWeekDates(musterRoll)
  const registrationDetails = {
    applicationData: musterRoll,
    additionalDetails: {
      table: {
        weekTable: {
          tableHeader: "ATM_ATTENDANCE_DETAILS",
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
    applicationData: {totalAmount,...musterRoll},
  }
};

const workflowDataDetails = async (tenantId, businessIds) => {
    const response = await Digit.WorkflowService.getByBusinessId(tenantId, businessIds);
    return response
}

const getWageSeekerSkills = async () => {
  const skills = {}
  const skillResponse = await Digit.MDMSService.getMultipleTypesWithFilter(Digit.ULBService.getStateId(), "common-masters", [{"name": "WageSeekerSkills"}])
  const labourChangesResponse = await Digit.MDMSService.getMultipleTypesWithFilter(Digit.ULBService.getStateId(), "expense", [{"name": "LabourCharges"}])
  skillResponse?.['common-masters']?.WageSeekerSkills.forEach(item => {
    let amount = labourChangesResponse?.["expense"]?.LabourCharges?.find(charge => charge?.code === item?.code)?.amount
    let skillWithAmount = {...item, amount}
    skills[item.code] = skillWithAmount
  })
  return skills
}

export const fetchAttendanceDetails = async (t, tenantId, searchParams) => {
  try {
    const response = await AttendanceService.search(tenantId, searchParams);
    // const workflowDetails = await workflowDataDetails(tenantId, searchParams.musterRollNumber);
    const skills = await getWageSeekerSkills()
    
    return transformViewDataToApplicationDetails(t, response, skills)
  } catch (error) {
      throw new Error(error?.response?.data?.Errors[0].message);
  }
};