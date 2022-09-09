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
