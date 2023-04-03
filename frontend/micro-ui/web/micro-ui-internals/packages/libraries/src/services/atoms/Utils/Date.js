import { format, toDate } from "date-fns";

export const ConvertTimestampToDate = (timestamp, dateFormat = "d-MMM-yyyy") => {
  return timestamp ? format(toDate(timestamp), dateFormat) : null;
};

export const ConvertEpochToDate = (dateEpoch, format="dd/mm/yyyy") => {
  if (dateEpoch == null || dateEpoch == undefined || dateEpoch == "") {
    return "NA";
  }
  const dateFromApi = new Date(dateEpoch);
  let month = dateFromApi.getMonth() + 1;
  let day = dateFromApi.getDate();
  let year = dateFromApi.getFullYear();
  month = (month > 9 ? "" : "0") + month;
  day = (day > 9 ? "" : "0") + day;

  switch(format) {
    case "yyyy-mm-dd" : {
      return `${year}-${month}-${day}`;
    }
    case "dd/mm/yyyy" : {
      return `${day}/${month}/${year}`;
    }
    default : {
      return `${day}/${month}/${year}`;
    }
  }
};

export const ConvertEpochToTimeInHours = (dateEpoch) => {
  if (dateEpoch == null || dateEpoch == undefined || dateEpoch == "") {
    return "NA";
  }
  const dateFromApi = new Date(dateEpoch);
  let hour = dateFromApi.getHours();
  let min = dateFromApi.getMinutes();
  let period = hour > 12 ? "PM" : "AM";
  hour = hour > 12 ? hour - 12 : hour;
  hour = (hour > 9 ? "" : "0") + hour;
  min = (min > 9 ? "" : "0") + min;
  return `${hour}:${min} ${period}`;
};

export const getDayfromTimeStamp = (timestamp) => {
  var a = new Date(timestamp);
  var days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
  var dayOfWeek = days[a.getDay()]
  return dayOfWeek
}

export const getYearDifference = (date) => {
  const timeDiff = Date.now() - new Date(date).getTime()
  const ageDate = new Date(timeDiff)
  return Math.abs(ageDate.getUTCFullYear() - 1970);
}