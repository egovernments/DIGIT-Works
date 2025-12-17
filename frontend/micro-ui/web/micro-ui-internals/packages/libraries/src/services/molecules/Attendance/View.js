import AttendanceService from "../../elements/Attendance";
import { WageSeekerService } from "../../elements/WageSeeker";
import { WorksService } from "../../elements/Works";

const attendanceTypes = {
  0 : 'zero',
  1 : 'full',
  0.5 : 'half'
 }

const getWeekDates = (data) => {
  let weekDates = {}
  const dayTimes = {
    "mon":data?.startDate,
    "sun":data?.endDate,
    "tue":data?.startDate + (86400000*1),
    "wed":data?.startDate + (86400000*2),
    "thu":data?.startDate + (86400000*3),
    "fri":data?.startDate + (86400000*4),
    "sat":data?.startDate + (86400000*5),
    "sun":data?.endDate
  }
  const weekTimes = Object.keys(dayTimes).map(key => {
    return dayTimes[key]
  })
  if(data?.individualEntries?.length > 0) {
    // const attendanceEntry = data?.individualEntries[1]?.attendanceEntries
    weekTimes?.forEach(item => {
      weekDates[`${Digit.DateUtils.getDayfromTimeStamp(item)}`] = Digit.DateUtils.ConvertTimestampToDate(item, 'MMM d')
    })
  }
  return weekDates
}

const getWeekAttendance = (data) => {
  let weekAttendance = {}
  if(data?.length > 0) {
    data.forEach(item => {
      weekAttendance[`${Digit.DateUtils.getDayfromTimeStamp(item?.time)}`] = attendanceTypes[item.attendance]
    })
  }
  return weekAttendance
}

const getAttendanceTableData = async(data, skills, t, expenseCalculations, indResponse) => {
  let individuals = indResponse?.Individual;
  let tableData = {}
  if(data?.individualEntries?.length > 0) {
    data?.individualEntries.filter((ob) => ob?.attendanceEntries !== null)?.forEach((item, index) => {
      let tableRow = {}
      let individualdata = individuals?.filter((ob) => ob?.individualId === item?.additionalDetails?.userId)?.[0]
      tableRow.id = item.id
      tableRow.sno = index + 1
      tableRow.registerId = {isLink:true, label:individualdata?.individualId, to:`/works-ui/employee/masters/view-wageseeker?tenantId=${individualdata?.tenantId}&individualId=${individualdata?.individualId}`} || t("NA")
      tableRow.actualWorkingDays = item?.actualTotalAttendance || 0
      tableRow.nameOfIndividual = individualdata?.name?.givenName || t("NA")
      tableRow.guardianName = individualdata?.fatherName  || t("NA")
      const skill = skills[item?.additionalDetails?.skillCode]
      tableRow.skill = skill ? `${t(skill.sorSubType)} - ${skill.description}` : t("NA");
      tableRow.amount = skills[item?.additionalDetails?.skillCode]?.amount * item?.actualTotalAttendance || 0
      tableRow.modifiedAmount = expenseCalculations?.filter(data=>data?.payee?.identifier === item?.individualId)?.[0]?.lineItems?.[0]?.amount || 0;
      tableRow.modifiedWorkingDays = item?.modifiedTotalAttendance ? item?.modifiedTotalAttendance : item?.actualTotalAttendance
      // tableRow.bankAccountDetails = {
      //   accountNo : item?.additionalDetails?.bankDetails || t("NA"),
      //   ifscCode : null
      // }
      // tableRow.aadharNumber = item?.additionalDetails?.aadharNumber || t("NA")
      tableRow.attendence = getWeekAttendance(item?.attendanceEntries)
      tableRow.perDayWage = skills[item?.additionalDetails?.skillCode]?.rates?.rate || 0
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

const transformViewDataToApplicationDetails = async (t, data, skills, indResponse) => {

  const expenseCalculatorPayload = {
    criteria : {
      "tenantId": data?.musterRolls?.[0]?.tenantId,
      "musterRollId": [ data?.musterRolls?.[0]?.id ]
    }
  }

  let expenseCalculationsResponse  =  await WorksService.fetchEstimateExpenseCalculator(expenseCalculatorPayload);
  let expenseCalculations = expenseCalculationsResponse?.calculation?.estimates?.[0]?.calcDetails;

  if(data?.musterRolls?.length === 0) throw new Error('No data found');
  
  const musterRoll = data.musterRolls[0]
  const attendanceTableData = await getAttendanceTableData(musterRoll, skills, t, expenseCalculations, indResponse)
  
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

const getWageSeekerSkills = async (data) => {
  // const tenantId = Digit.ULBService.getStateId();
  const tenantId = data.musterRolls[0].tenantId
  const skills = {};

  const requestCriteria = {
    url: "/mdms-v2/v1/_search",
    body: {
      MdmsCriteria: {
        tenantId: tenantId,
        moduleDetails: [
          {
            moduleName: "WORKS-SOR",
            masterDetails: [
              {
                name: "SOR",
                filter: "[?(@.sorType=='L')]"
              },
              {
                name: "Rates"
              }
            ],
          },
        ],
      },
    },
  };
 let skillResponse = await Digit.CustomService.getResponse(requestCriteria)

 if (skillResponse?.MdmsRes?.['WORKS-SOR']) {
  const sorData = skillResponse.MdmsRes['WORKS-SOR'].SOR;
    const ratesData = skillResponse.MdmsRes['WORKS-SOR'].Rates;

    // Create a mapping for Rates based on sorId
    const ratesMapping = {};
    ratesData.forEach(rate => {
      if(parseInt(rate?.validFrom) < data?.musterRolls?.[0]?.auditDetails?.createdTime && ( rate?.validTo == null || parseInt(rate?.validTo) > data?.musterRolls?.[0]?.auditDetails?.createdTime)){
      ratesMapping[rate.sorId] = rate;
      }
    });

    // Iterate over the SOR data and add corresponding Rates data to each skill
    sorData.forEach(skill => {
      skills[skill.id] = {
        description: skill.description,
        uom: skill.uom,
        sorType: skill.sorType,
        quantity: skill.quantity,
        sorSubType: skill.sorSubType,
        sorVariant: skill.sorVariant,
        rates: ratesMapping[skill.id] || null,  // Add corresponding Rates data or null if not found
      };
    });
 }
  return skills;
};

// const getWageSeekerSkills = async (data) => {
//   const skills = {}
//   const skillResponse = await Digit.MDMSService.getMultipleTypesWithFilter(Digit.ULBService.getStateId(), "common-masters", [{"name": "WageSeekerSkills"}])
//   const labourChangesResponse = await Digit.MDMSService.getMultipleTypesWithFilter(Digit.ULBService.getStateId(), "expense", [{"name": "LabourCharges"}])
//   skillResponse?.['common-masters']?.WageSeekerSkills.forEach(item => {
//     let amount = labourChangesResponse?.["expense"]?.LabourCharges?.find(charge => charge?.code === item?.code && charge?.effectiveFrom < data?.musterRolls?.[0]?.auditDetails?.createdTime && (charge?.effectiveTo == null || charge?.effectiveTo > data?.musterRolls?.[0]?.auditDetails?.createdTime))?.amount
//     let skillWithAmount = {...item, amount}
//     skills[item.code] = skillWithAmount
//   })
//   return skills
// }

export const fetchAttendanceDetails = async (t, tenantId, searchParams) => {
  try {
    const response = await AttendanceService.search(tenantId, searchParams);
    // const workflowDetails = await workflowDataDetails(tenantId, searchParams.musterRollNumber);
    let IndsToSearch = response?.musterRolls?.[0].individualEntries.map((ob) => ob?.individualId)

    let indPayload =IndsToSearch?.length!==0 ? {
      Individual:{
        id:IndsToSearch
      }
    } : null

    const indResponse = await WageSeekerService.search(tenantId, indPayload, {tenantId,offset:0,limit:100});

    const skills = await getWageSeekerSkills(response)
    
    return transformViewDataToApplicationDetails(t, response, skills, indResponse)
  } catch (error) {
      throw new Error(error?.response?.data?.Errors[0].message);
  }
};