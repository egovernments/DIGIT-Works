import { Link } from "react-router-dom";

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
            data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), includeAncestors:true }
            data.body.Projects[0] = { ...data.body.Projects[0], tenantId: Digit.ULBService.getCurrentTenantId(), startDate, endDate, projectType }

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
                    <Link to={`/works-ui/employee/project/project-details?tenantId=${Digit.ULBService.getCurrentTenantId() }&projectNumber=${value}`}>{String(value ? column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value : t("ES_COMMON_NA"))}</Link>
                </span>
            }

            if (column.label === "WORKS_PARENT_PROJECT_ID") {
                return value ?
                    <span className="link">
                        <Link to={`/works-ui/employee/project/project-details?tenantId=${Digit.ULBService.getCurrentTenantId()}&projectNumber=${value}`}>{String(value ? column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value : t("ES_COMMON_NA"))}</Link>
                    </span>
                    : t("ES_COMMON_NA")
            }
            
            if (column.label === "WORKS_PROJECT_NAME") {
                return (
                <div class="tooltip">
                    <span class="textoverflow" style={{'--max-width': `${column.maxLength}ch`}}>{String(t(value))}</span>
                    <span class="tooltiptext" style={{whiteSpace: "nowrap"}}>{String(t(value))}</span>
                </div>
                );
            }

          },
        additionalValidations: (type, data, keys) => {
            if(type === 'date') {
                return (data[keys.start] && data[keys.end]) ? () => new Date(data[keys.start]).getTime() < new Date(data[keys.end]).getTime() : true
            }
        },
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

        },
        additionalValidations: (type, data, keys) => {
            if(type === 'date') {
                return (data[keys.start] && data[keys.end]) ? () => new Date(data[keys.start]).getTime() < new Date(data[keys.end]).getTime() : true
            }
        },
    },
    SearchAttendanceConfig: {
        preProcess: (data) => {
          const fromDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.fromDate);
          const toDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.toDate);
          const musterRollStatus = data?.params?.musterRollStatus?.code;
          const status = data?.params?.status?.code;
          data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), fromDate, toDate, musterRollStatus, status };
          return data;
        },
        additionalCustomizations: (row, key, columnConfig, value, t, searchResult) => {
          if (key === "ATM_MUSTER_ROLL_NUMBER") {
            return (
              <span className="link">
                <Link
                  to={`/${window.contextPath}/employee/attendencemgmt/view-attendance?tenantId=${row?.tenantId}&musterRollNumber=${value}`}
                >
                  {String(value ? value : t("ES_COMMON_NA"))}
                </Link>
              </span>
            );
          }
          if (key === "ATM_NO_OF_INDIVIDUALS") {
            return <div>{value?.length}</div>;
          }
        },
        MobileDetailsOnClick: (row, tenantId) => {
          let link;
          Object.keys(row).map((key) => {
            if (key === "ATM_MUSTER_ROLL_NUMBER")
              link = `/${window.contextPath}/employee/attendencemgmt/view-attendance?tenantId=${tenantId}&musterRollNumber=${row[key]}`;
          });
          return link;
        },
    },
    AttendanceInboxConfig: {
        preProcess: (data) => {
          const convertedStartDate = Digit.DateUtils.ConvertTimestampToDate(
            data.body.inbox?.moduleSearchCriteria?.musterRolldateRange?.range?.startDate,
            "yyyy-MM-dd"
          );
          const convertedEndDate = Digit.DateUtils.ConvertTimestampToDate(
            data.body.inbox?.moduleSearchCriteria?.musterRolldateRange?.range?.endDate,
            "yyyy-MM-dd"
          );
          const startDate = Digit.Utils.pt.convertDateToEpoch(convertedStartDate, "dayStart");
          const endDate = Digit.Utils.pt.convertDateToEpoch(convertedEndDate, "dayStart");
          const attendanceRegisterName = data.body.inbox?.moduleSearchCriteria?.attendanceRegisterName?.trim();
          const musterRollStatus = data.body.inbox?.moduleSearchCriteria?.musterRollStatus?.code;
          const musterRollNumber = data.body.inbox?.moduleSearchCriteria?.musterRollNumber;
          data.body.inbox = {
            ...data.body.inbox,
            tenantId: Digit.ULBService.getCurrentTenantId(),
            processSearchCriteria: { ...data.body.inbox.processSearchCriteria, tenantId: Digit.ULBService.getCurrentTenantId() },
            moduleSearchCriteria: {
              tenantId: Digit.ULBService.getCurrentTenantId(),
              startDate,
              endDate,
              musterRollStatus,
              attendanceRegisterName,
              musterRollNumber,
            },
          };
          return data;
        },
        postProcess: (responseArray, uiConfig) => {
          const statusOptions = responseArray?.statusMap?.filter((item) => item.applicationstatus)?.map((item) => ({ code: item.applicationstatus, i18nKey: `COMMON_MASTERS_${item.applicationstatus}` }));
          if (uiConfig?.type === "filter") {
            let fieldConfig = uiConfig?.fields?.filter((item) => item.type === "dropdown" && item.populators.name === "musterRollStatus");
            if (fieldConfig.length) {
              fieldConfig[0].populators.options = statusOptions;
            }
          }
        },
        additionalCustomizations: (row, key, columnConfig, value, t, searchResult) => {
          if (key === "ATM_MUSTER_ROLL_ID") {
            return (
              <span className="link">
                <Link
                  to={`/works-ui/employee/attendencemgmt/view-attendance?tenantId=${row?.tenantId}&musterRollNumber=${value}`}
                >
                  {String(value ? value : t("ES_COMMON_NA"))}
                </Link>
              </span>
            );
          }
          if (key === "ATM_ATTENDANCE_WEEK") {
            const week = `${Digit.DateUtils.ConvertTimestampToDate(value?.startDate, "dd/MM/yyyy")}-${Digit.DateUtils.ConvertTimestampToDate(
              value?.endDate,
              "dd/MM/yyyy"
            )}`;
            return <div>{week}</div>;
          }
          if (key === "ATM_NO_OF_INDIVIDUALS") {
            return <div>{value?.length}</div>;
          }
          if (key === "ATM_SLA") {
            return parseInt(value) > 0 ? (
              <span className="sla-cell-success">{t(value) || ""}</span>
            ) : (
              <span className="sla-cell-error">{t(value) || ""}</span>
            );
          }
        },
        MobileDetailsOnClick: (row, tenantId) => {
          let link;
          Object.keys(row).map((key) => {
            if (key === "ATM_MUSTER_ROLL_ID")
              link = `/${window.contextPath}/employee/attendencemgmt/view-attendance?tenantId=${tenantId}&musterRollNumber=${row[key]}`;
          });
          return link;
        },
    },
    ProjectInboxConfig: {
        preProcess: (data) => {
          const createdFrom = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdFrom);
          const createdTo = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdTo);
          const projectType = data.body.Projects[0]?.projectType?.code;
          data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), includeAncestors: true, createdFrom, createdTo };
          let name = data.body.Projects[0]?.name;
          name = name?.trim();
          delete data.body.Projects[0]?.createdFrom;
          delete data.body.Projects[0]?.createdTo;
          delete data.body.Projects[0]?.department;
          delete data.body.Projects[0]?.createdBy;
          delete data.body.Projects[0]?.status;
          data.body.Projects[0] = { ...data.body.Projects[0], tenantId: Digit.ULBService.getCurrentTenantId(), projectType, name };
    
          return data;
        },
        postProcess: (responseArray) => {
          const listOfUuids = responseArray?.map((row) => row.auditDetails.createdBy);
          const uniqueUuids = listOfUuids?.filter(function (item, i, ar) { return ar.indexOf(item) === i; });
          const tenantId = Digit.ULBService.getCurrentTenantId();
          const reqCriteria = {
            url: "/user/_search",
            params: {},
            body: { tenantId, pageSize: 100, uuid: [...uniqueUuids] },
            config: {
              enabled: responseArray?.length > 0 ? true : false,
              select: (data) => {
                const usersResponse = data?.user;
                responseArray?.forEach((row) => {
                  const uuid = row?.auditDetails?.createdBy;
                  const user = usersResponse?.filter((user) => user.uuid === uuid);
                  row.createdBy = user?.[0].name;
                });
                return responseArray;
              },
            },
          };
          const { isLoading: isPostProcessLoading, data: combinedResponse, isFetching: isPostProcessFetching } = Digit.Hooks.useCustomAPIHook(reqCriteria);
          return {
            isPostProcessFetching,
            isPostProcessLoading,
            combinedResponse,
          };
        },
        additionalCustomizations: (row, key, column, value, t, searchResult) => {
          if (key === "WORKS_PRJ_SUB_ID") {
            return (
              <span className="link">
                <Link to={`/${window.contextPath}/employee/project/project-details?tenantId=${row?.tenantId}&projectNumber=${value}`}>
                {String(value ? value : t("ES_COMMON_NA"))}
                </Link>
              </span>
            );
          }
    
          if (key === "WORKS_PARENT_PROJECT_ID") {
            return value ? (
              <span className="link">
                <Link to={`/${window.contextPath}/employee/project/project-details?tenantId=${row?.tenantId}&projectNumber=${value}`}>
                {String(value ? value : t("ES_COMMON_NA"))}
                </Link>
              </span>
            ) : (
              t("ES_COMMON_NA")
            );
          }
        },
        MobileDetailsOnClick: (row, tenantId) => {
          let link;
          Object.keys(row).map((key) => {
            if (key === "WORKS_PRJ_SUB_ID")
              link = `/${window.contextPath}/employee/project/project-details?tenantId=${tenantId}&projectNumber=${row[key]}`;
          });
          return link;
        },
        additionalValidations: (type, data, keys) => {
          if (type === "date") {
            return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() < new Date(data[keys.end]).getTime() : true;
          }
        },
    },
    BillInboxConfig: {
        preProcess: (data) => {
          const musterRollNumber = data.body.inbox?.moduleSearchCriteria?.billNumber
          data.body.inbox = {
            ...data.body.inbox,
            tenantId: Digit.ULBService.getCurrentTenantId(),
            processSearchCriteria: { ...data.body.inbox.processSearchCriteria, tenantId: Digit.ULBService.getCurrentTenantId() },
            moduleSearchCriteria: { tenantId: Digit.ULBService.getCurrentTenantId(), musterRollNumber },
          };
          return data;
        },
        MobileDetailsOnClick: (row, tenantId) => {
          let link;
          Object.keys(row).map((key) => {
            if (key === "WORKS_BILL_NUMBER")
              link = `/${window.contextPath}/employee/expenditure/view-bill?tenantId=${tenantId}&billNumber=${row[key]}`;
          });
          return link;
        },
        additionalCustomizations: (row, key, column, value, t, searchResult) => {
          if (key === "WORKS_BILL_NUMBER") {
            return (
              <span className="link">
                <Link to={`/works-ui/employee/expenditure/view-bill?tenantId=${row?.tenantId}&billNumber=${value}`}>
                {String(value ? value : t("ES_COMMON_NA"))}
                </Link>
              </span>
            );
          }
    
          if (key === "ES_COMMON_AMOUNT") {
            return value ? Digit.Utils.dss.formatterWithoutRound(value, "number") : t("ES_COMMON_NA");
          }
    
          if (key === "COMMON_SLA_DAYS") {
            return value ? (
              parseInt(value) > 0 ? (
                <span className="sla-cell-success">{t(value) || ""}</span>
              ) : (
                <span className="sla-cell-error">{t(value) || ""}</span>
              )
            ) : (
              t("ES_COMMON_NA")
            );
          }
    
          if (key === "COMMON_WORKFLOW_STATES") {
            return value ? t(`BILL_STATUS_${value}`) : t("ES_COMMON_NA");
          }
        },
    },
    SearchBillConfig: {
        preProcess: (data) => {
          const fromDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.fromDate);
          const toDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.toDate);
          const musterRollStatus = data?.params?.musterRollStatus?.code;
          delete data.params.billType;
          data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), fromDate, toDate, musterRollStatus };
          return data;
        },
        additionalCustomizations: (row, key, column, value, t, searchResult) => {
          if (key === "WORKS_BILL_NUMBER") {
            return (
              <span className="link">
                <Link
                  to={`/${
                    window.contextPath
                  }/employee/expenditure/view-bill?tenantId=${row?.tenantId}&billNumber=${value}`}
                >
                  {String(value ? value : t("ES_COMMON_NA"))}
                </Link>
              </span>
            );
          }
          if (key === "EXP_BILL_AMOUNT") {
            return value ? Digit.Utils.dss.formatterWithoutRound(value, 'number') : t("ES_COMMON_NA")
          }
          if(key === "CORE_COMMON_STATUS") {
            return value ? t(`BILL_STATUS_${value}`) : t("ES_COMMON_NA")
          }
          if(key === "ES_COMMON_LOCATION") {
            const headerLocale = Digit.Utils.locale.getTransformedLocale(row?.tenantId)
            return t(`TENANT_TENANTS_${headerLocale}`)
          }
        },
        MobileDetailsOnClick: (row, tenantId) => {
          let link;
          Object.keys(row).map((key) => {
            if (key === "WORKS_BILL_NUMBER")
              link = `/${window.contextPath}/employee/expenditure/view-bill?tenantId=${tenantId}&billNumber=${row[key]}`;
          });
          return link;
        },
        additionalValidations: (type, data, keys) => {
          if (type === "date") {
            return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() < new Date(data[keys.end]).getTime() : true;
          }
        }
    }
}
