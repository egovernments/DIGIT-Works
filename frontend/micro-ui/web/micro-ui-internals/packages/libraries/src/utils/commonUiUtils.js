
import { ULBService } from "../services/molecules/Ulb";
const convertDateToEpoch = (dateString, dayStartOrEnd = "dayend") => {
    //example input format : "2018-10-02"
    try {
        const parts = dateString.match(/(\d{4})-(\d{1,2})-(\d{1,2})/);
        const DateObj = new Date(Date.UTC(parts[1], parts[2] - 1, parts[3]));
        DateObj.setMinutes(DateObj.getMinutes() + DateObj.getTimezoneOffset());
        if (dayStartOrEnd === "dayend") {
            DateObj.setHours(DateObj.getHours() + 24);
            DateObj.setSeconds(DateObj.getSeconds() - 1);
        }
        return DateObj.getTime();
    } catch (e) {
        return dateString;
    }
};

const getCode = (obj) => {
    return obj?.code
}

const fetchTenantId = () => {
    return ULBService.getCurrentTenantId()
}
export default { convertDateToEpoch,getCode,fetchTenantId }