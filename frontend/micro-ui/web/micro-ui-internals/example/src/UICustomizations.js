import { Link } from "react-router-dom";

//create functions here based on module name set in mdms(eg->SearchProjectConfig)
//how to call these -> Digit?.Customizations?.[masterName]?.[moduleName]
// these functions will act as middlewares 
var Digit = window.Digit || {};

export const UICustomizations = {
    SearchProjectConfig: {
        preProcess: (data) => {
            
            const createdFrom = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdFrom)
            const createdTo = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdTo)
            const projectType = data.body.Projects[0]?.projectType?.code
            data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), includeAncestors:true,createdFrom,createdTo }
            let name = data.body.Projects[0]?.name
            name = name?.trim()
            delete data.body.Projects[0]?.createdFrom
            delete data.body.Projects[0]?.createdTo
            data.body.Projects[0] = { ...data.body.Projects[0], tenantId: Digit.ULBService.getCurrentTenantId(), projectType,name}

            return data
        },
        postProcess: (responseArray) => {
            
            const listOfUuids = responseArray?.map(row => row.auditDetails.createdBy)
            const uniqueUuids = listOfUuids?.filter(function (item, i, ar) { return ar.indexOf(item) === i; });
            const tenantId = Digit.ULBService.getCurrentTenantId()
            const reqCriteria = {
                url:"/user/_search",
                params:{},
                body: { tenantId, pageSize: 100, uuid: [...uniqueUuids] },
                config:{
                    enabled:responseArray?.length > 0 ? true : false,
                    select: (data) => {
                        const usersResponse = data?.user
                        responseArray?.forEach((row)=> {
                            const uuid = row?.auditDetails?.createdBy
                            const user = usersResponse?.filter(user => user.uuid === uuid)
                            row.createdBy = user?.[0].name
                        } )
                        return responseArray
                    }
                }

            }
            const { isLoading: isPostProcessLoading, data: combinedResponse, isFetching: isPostProcessFetching } = Digit.Hooks.useCustomAPIHook(reqCriteria);

            return {
                isPostProcessFetching,
                isPostProcessLoading,
                combinedResponse
            }
        },
        additionalCustomizations: (row,column,columnConfig,value,t) => {
            
            //here we can add multiple conditions
            //like if a cell is link then we return link
            //first we can identify which column it belongs to then we can return relevant result
            
            if (column.label ==="WORKS_PRJ_SUB_ID")
            {
                return <span className="link">
                    <Link to={`/${window.contextPath}/employee/project/project-details?tenantId=${row.tenantId }&projectNumber=${value}`}>{String(value ? column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value : t("ES_COMMON_NA"))}</Link>
                </span>
            }

            if (column.label === "WORKS_PARENT_PROJECT_ID") {
               return value ?
                 <span className="link">
                    <Link to={`/${window.contextPath}/employee/project/project-details?tenantId=${row.tenantId}&projectNumber=${value}`}>{String(value ? column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value : t("ES_COMMON_NA"))}</Link>
                </span>
                :  t("ES_COMMON_NA")
            }

            if (column.label === "WORKS_PROJECT_NAME") {
                return (
                <div class="tooltip">
                    <span class="textoverflow" style={{'--max-width': `${column.maxLength}ch`}}>{String(t(value))}</span>
                    {/* check condtion - if length greater than 20 */}
                    <span class="tooltiptext" style={{whiteSpace: "nowrap"}}>{String(t(value))}</span>
                </div>
                );
            }
        },
        additionalValidations: (type, data, keys) => {
            if(type === 'date') {
                return (data[keys.start] && data[keys.end]) ? () => new Date(data[keys.start]).getTime() < new Date(data[keys.end]).getTime() : true
            }
        }
    },
    SearchEstimateConfig: {
        preProcess: (data) => {
            const fromProposalDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.fromProposalDate)
            const toProposalDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.toProposalDate)
            const department = data?.params?.department?.code
            data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), fromProposalDate, toProposalDate, department }
            return data
        },
        additionalCustomizations: (row, column, columnConfig, value, t) => {
            //here we can add multiple conditions
            //like if a cell is link then we return link
            //first we can identify which column it belongs to then we can return relevant result

            const getAmount = (item) => {
                return item.amountDetail.reduce((acc,row)=> acc + row.amount, 0 )
            }
            if (column.label === "WORKS_ESTIMATE_ID") {
                return <span className="link">
                    <Link to={`/${window.contextPath}/employee/estimate/estimate-details?tenantId=${Digit.ULBService.getCurrentTenantId()}&estimateNumber=${value}`}>{String(value ? column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value : t("ES_COMMON_NA"))}</Link>
                </span>
            }
            if (column.label === "WORKS_ESTIMATED_AMOUNT"){
                
               return row?.estimateDetails?.reduce((totalAmount,item)=>totalAmount + getAmount(item),0)
                
            }

        }
    },
    SearchAttendanceConfig: {
        preProcess: (data) => {
            const fromDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.fromDate)
            const toDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.toDate)
            const musterRollStatus = data?.params?.musterRollStatus?.code
            const status = data?.params?.status?.code
            data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), fromDate, toDate, musterRollStatus, status }
            return data
        },
        additionalCustomizations: (row,column,columnConfig,value,t) => {
            if (column.label === "ATM_MUSTER_ROLL_NUMBER") {
                return <span className="link">
                    <Link to={`/${window.contextPath}/employee/attendencemgmt/view-attendance?tenantId=${Digit.ULBService.getCurrentTenantId() }&musterRollNumber=${value}`}>{String(value ? column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value : t("ES_COMMON_NA"))}</Link>
                </span>
            }
            if (column.label === "ATM_NO_OF_INDIVIDUALS") {
                return <div>{value?.length}</div>
            }
        }
    },
    AttendanceInboxConfig: {
        preProcess: (data) => {
            const convertedStartDate = Digit.DateUtils.ConvertTimestampToDate(data.body.inbox?.moduleSearchCriteria?.musterRolldateRange?.range?.startDate, 'yyyy-MM-dd')
            const convertedEndDate = Digit.DateUtils.ConvertTimestampToDate(data.body.inbox?.moduleSearchCriteria?.musterRolldateRange?.range?.endDate, 'yyyy-MM-dd')
            const startDate = Digit.Utils.pt.convertDateToEpoch(convertedStartDate, 'dayStart')
            const endDate = Digit.Utils.pt.convertDateToEpoch(convertedEndDate, 'dayStart')
            const attendanceRegisterName = data.body.inbox?.moduleSearchCriteria?.attendanceRegisterName?.trim()
            const musterRollStatus = data.body.inbox?.moduleSearchCriteria?.musterRollStatus?.code
            data.body.inbox = { 
                ...data.body.inbox, 
                tenantId: Digit.ULBService.getCurrentTenantId(), 
                processSearchCriteria: { ...data.body.inbox.processSearchCriteria, tenantId: Digit.ULBService.getCurrentTenantId() },
                moduleSearchCriteria: {tenantId: Digit.ULBService.getCurrentTenantId(), startDate, endDate, musterRollStatus, attendanceRegisterName}
            }
            return data
        },
        postProcess: (responseArray, uiConfig) => {
            const statusOptions = responseArray?.statusMap?.filter(item => item.applicationstatus)?.map(item => ({ code: item.applicationstatus, i18nKey: `COMMON_MASTERS_${item.applicationstatus}`}))
            if(uiConfig?.type === 'filter') {
             let fieldConfig = uiConfig?.fields?.filter(item => item.type === 'dropdown' && item.populators.name === 'musterRollStatus')
             if(fieldConfig.length) { 
               fieldConfig[0].populators.options = statusOptions
             }
           }
        },
        additionalCustomizations: (row,column,columnConfig,value,t) => {
            if (column.label === "ATM_MUSTER_ROLL_ID") {
                return <span className="link">
                    <Link to={`/works-ui/employee/attendencemgmt/view-attendance?tenantId=${Digit.ULBService.getCurrentTenantId() }&musterRollNumber=${value}`}>{String(value ? column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value : t("ES_COMMON_NA"))}</Link>
                </span>
            }
            if( column.label === "ATM_ATTENDANCE_WEEK") {
                const week = `${Digit.DateUtils.ConvertTimestampToDate(value?.startDate, 'dd/MM/yyyy')} - ${Digit.DateUtils.ConvertTimestampToDate(value?.endDate, 'dd/MM/yyyy')}`
                return <div>{week}</div>
            }
            if (column.label === "ATM_NO_OF_INDIVIDUALS") {
                return <div>{value?.length}</div>
            }
            if (column.label === "ATM_SLA") {
                return (
                    parseInt(value) > 0 ? <span className="sla-cell-success">{ t(value) || ""}</span> : <span className="sla-cell-error">{ t(value )|| ""}</span>
                );
            }
        }
    },
    ProjectInboxConfig: {
        preProcess: (data) => {
            const createdFrom = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdFrom)
            const createdTo = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdTo)
            const projectType = "MP-CWS"
            data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), includeAncestors:true,createdFrom,createdTo }
            let name = data.body.Projects[0]?.name
            name = name?.trim()
            delete data.body.Projects[0]?.createdFrom
            delete data.body.Projects[0]?.createdTo
            delete data.body.Projects[0]?.department
            delete data.body.Projects[0]?.createdBy
            delete data.body.Projects[0]?.status
            data.body.Projects[0] = { ...data.body.Projects[0], tenantId: Digit.ULBService.getCurrentTenantId(), projectType,name}

            return data
        },
        postProcess: (responseArray) => {
            const listOfUuids = responseArray?.map(row => row.auditDetails.createdBy)
            const uniqueUuids = listOfUuids?.filter(function (item, i, ar) { return ar.indexOf(item) === i; });
            const tenantId = Digit.ULBService.getCurrentTenantId()
            const reqCriteria = {
                url:"/user/_search",
                params:{},
                body: { tenantId, pageSize: 100, uuid: [...uniqueUuids] },
                config:{
                    enabled:responseArray?.length > 0 ? true : false,
                    select: (data) => {
                        const usersResponse = data?.user
                        responseArray?.forEach((row)=> {
                            const uuid = row?.auditDetails?.createdBy
                            const user = usersResponse?.filter(user => user.uuid === uuid)
                            row.createdBy = user?.[0].name
                        } )
                        return responseArray
                    }
                }

            }
            const { isLoading: isPostProcessLoading, data: combinedResponse, isFetching: isPostProcessFetching } = Digit.Hooks.useCustomAPIHook(reqCriteria);
            return {
                isPostProcessFetching,
                isPostProcessLoading,
                combinedResponse
            }
        },
        additionalCustomizations: (row,column,columnConfig,value,t) => {
            if (column.label ==="WORKS_PRJ_SUB_ID")
            {
                return <span className="link">
                    <Link to={`/${window.contextPath}/employee/project/project-details?tenantId=${row.tenantId }&projectNumber=${value}`}>{String(value ? column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value : t("ES_COMMON_NA"))}</Link>
                </span>
            }

            if (column.label === "WORKS_PARENT_PROJECT_ID") {
               return value ?
                 <span className="link">
                    <Link to={`/${window.contextPath}/employee/project/project-details?tenantId=${row.tenantId}&projectNumber=${value}`}>{String(value ? column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value : t("ES_COMMON_NA"))}</Link>
                </span>
                :  t("ES_COMMON_NA")
            }
        },
        additionalValidations: (type, data, keys) => {
            if(type === 'date') {
                return (data[keys.start] && data[keys.end]) ? () => new Date(data[keys.start]).getTime() < new Date(data[keys.end]).getTime() : true
            }
        }
    }
}

