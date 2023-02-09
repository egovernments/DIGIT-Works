import { Link } from "react-router-dom";
import { useQuery, useQueryClient } from "react-query";

//create functions here based on module name set in mdms(eg->SearchProjectConfig)
//how to call these -> Digit?.Customizations?.[masterName]?.[moduleName]
// these functions will act as middlewares 
var Digit = window.Digit || {};

export const UICustomizations = {
    SearchProjectConfig: {
        preProcess: (data) => {
            const startDate = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.startDate)
            const endDate = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.endDate)
            const projectType = data.body.Projects[0]?.projectType?.code
            data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId() }
            data.body.Projects[0] = { ...data.body.Projects[0], tenantId: Digit.ULBService.getCurrentTenantId(), startDate, endDate, projectType }

            return data
        },
        // postProcess: ( responseArray, filters, options = {}) => {
            
        //     if(responseArray?.length===0) return []
        //     const listOfUuids = responseArray?.map(row => row.auditDetails.createdBy)
        //     const data = { uuid: listOfUuids }
        //     const tenantId = Digit.ULBService.getCurrentTenantId()
        //     const client = useQueryClient();
        //     const queryData = useQuery(["USER_SEARCH", filters, data], () => Digit.UserService.userSearch(null, data, {}), options);
        // },
        additionalCustomizations: (row,column,columnConfig,value,t) => {
            //here we can add multiple conditions
            //like if a cell is link then we return link
            //first we can identify which column it belongs to then we can return relevant result
            
            if (column.label ==="WORKS_PRJ_SUB_ID")
            {
                return <span className="link">
                    <Link to={`/works-ui/employee/project/project-details?tenantId=${Digit.ULBService.getCurrentTenantId() }&projectNumber=${value}`}>{String(value ? column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value : t("ES_COMMON_NA"))}</Link>
                </span>
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
                    <Link to={`/works-ui/employee/estimate/estimate-details?tenantId=${Digit.ULBService.getCurrentTenantId()}&estimateNumber=${value}`}>{String(value ? column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value : t("ES_COMMON_NA"))}</Link>
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
                    <Link to={`/works-ui/employee/attendencemgmt/view-attendance?tenantId=${Digit.ULBService.getCurrentTenantId() }&musterRollNumber=${value}`}>{String(value ? column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value : t("ES_COMMON_NA"))}</Link>
                </span>
            }
            if (column.label === "ATM_NO_OF_INDIVIDUALS") {
                return <div>{value?.length}</div>
            }
        }
    }
}

