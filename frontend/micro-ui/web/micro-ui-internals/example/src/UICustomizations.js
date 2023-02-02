
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
    return Digit.ULBService.getCurrentTenantId()
}


//create functions here based on module name set in mdms(eg->SearchProjectConfig)
//how to call these -> Digit?.Customizations?.[masterName]?.[moduleName]
// these functions will act as middlewares 
export const UICustomizations = {
    SearchProjectConfig: (data, form) => {
        //add tenantId to body and param here
        
        const startDate = convertDateToEpoch(data.body.Projects[0]?.startDate)
        const endDate = convertDateToEpoch(data.body.Projects[0]?.endDate)
        const projectType = getCode(data.body.Projects[0]?.projectType)
        data.params = { ...data.params, tenantId:fetchTenantId()}
        data.body.Projects[0] = { ...data.body.Projects[0], tenantId: fetchTenantId(),startDate,endDate,projectType }

        return data
    }
}
