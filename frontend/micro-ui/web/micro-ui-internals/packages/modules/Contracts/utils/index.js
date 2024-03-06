export const stringReplaceAll = (str = "", searcher = "", replaceWith = "") => {
  if (searcher == "") return str;
  while (str && str.includes(searcher)) {
    str = str.replace(searcher, replaceWith);
  }
  return str;
};

export const ifUserRoleExists = (role) => {
  const userInfo = Digit.UserService.getUser();
  const roleCodes = userInfo?.info?.roles ? userInfo?.info?.roles.map((role) => role.code) : [];
  if (roleCodes.indexOf(role) > -1) {
    return true;
  } else return false;
};

export const convertEpochToDateDMY = (dateEpoch) => {
  if (dateEpoch == null || dateEpoch == undefined || dateEpoch == "") {
    return "NA";
  }
  const dateFromApi = new Date(dateEpoch);
  let month = dateFromApi.getMonth() + 1;
  let day = dateFromApi.getDate();
  let year = dateFromApi.getFullYear();
  month = (month > 9 ? "" : "0") + month;
  day = (day > 9 ? "" : "0") + day;
  return `${day}/${month}/${year}`;
};

export const mdmsData = async (tenantId,t) => {
  
  const result =  await Digit.MDMSService.getMultipleTypes(tenantId, "tenant", ["tenants", "citymodule"]);
  
  const filteredResult = result?.tenant.tenants.filter(e => e.code === tenantId)
  
  const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)
  const ulbGrade = filteredResult?.[0]?.city?.ulbGrade.replaceAll(" ","_")
  
  const obj =  {
    header: t(`TENANT_TENANTS_${headerLocale}`)+ ` ` + t(`ULBGRADE_${ulbGrade}`),
    subHeader:filteredResult?.[0].address,
    description:`${filteredResult?.[0]?.contactNumber} | ${filteredResult?.[0]?.domainUrl} | ${filteredResult?.[0]?.emailId}`,
  }
  return obj
}


export const isWorkEndInPreviousWeek = (workEndEpoch, mbValidationStartEpoch) => {
  const IST_OFFSET = 5.5 * 60 * 60 * 1000; // Offset for India Standard Time (IST)

  // Adjust epoch timestamps to India time zone (IST)
  const workEndDate = new Date(workEndEpoch + IST_OFFSET);
  const mbValidationStartDate = new Date(mbValidationStartEpoch + IST_OFFSET);

  // Adjust project start date to India time zone (UTC+5:30)
  mbValidationStartDate.setUTCHours(0, 0, 0, 0);

  // Find the Monday of the week before the project starting date
  const previousWeekStart = new Date(mbValidationStartDate);
  previousWeekStart.setDate(mbValidationStartDate.getDate() - mbValidationStartDate.getDay() - 6);

  // Find the Sunday of the previous week
  const previousWeekEnd = new Date(previousWeekStart);
  previousWeekEnd.setDate(previousWeekStart.getDate() + 6);

   // Set the end of the previous week to 23:59:59
   previousWeekEnd.setHours(23, 59, 59, 999);

  // Convert dates to epoch timestamps
  const previousWeekEndEpoch = previousWeekEnd.getTime();
  const workEndEpochTime = workEndDate.getTime() - IST_OFFSET;

  // Check if the work end date falls before the previous week
  return workEndEpochTime <= previousWeekEndEpoch;
}