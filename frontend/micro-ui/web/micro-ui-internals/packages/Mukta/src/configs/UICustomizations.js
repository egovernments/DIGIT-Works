import { Link } from "react-router-dom";
import _ from "lodash";
import React from "react";
import { Amount, LinkLabel } from "@egovernments/digit-ui-react-components";


//create functions here based on module name set in mdms(eg->SearchProjectConfig)
//how to call these -> Digit?.Customizations?.[masterName]?.[moduleName]
// these functions will act as middlewares
var Digit = window.Digit || {};

const getBillType = (businessService) => {
  switch(businessService) {
    case "EXPENSE.WAGES":
      return 'wage'
    case "EXPENSE.PURCHASE":
      return 'purchase'
    case "EXPENSE.SUPERVISION":
      return 'supervision'
    default:
      return 'wage';
  }
}
const PAYMENT_UPDATE_STATUS="SUCCESSFUL";


const getCreatePaymentPayload = (data) => {
  let payment = {}
  payment.tenantId = Digit.ULBService.getCurrentTenantId()
  payment.netPayableAmount = 0
  payment.netPaidAmount = 0
  payment.additionalDetails = {}
  //payment.status = 'INITIATED'

  payment.bills = []
  
  data?.forEach(item => {
    const bill = item
    let billObj = {}
    billObj.billId = bill?.id
    billObj.tenantId = bill?.tenantId
    billObj.totalAmount = bill?.totalAmount
    /* temp fix for now  before jit integration*/
    billObj.totalPaidAmount = bill?.totalAmount
    //billObj.status = 'INITIATED'
    payment.netPayableAmount=payment.netPayableAmount+bill?.totalAmount;
    billObj.billDetails = []
    if(bill?.billDetails?.length > 0) {
      bill?.billDetails?.forEach(detail => {
        let billDetailObj = {}
        billDetailObj.billDetailId = detail?.id //billId
        billDetailObj.totalAmount = detail?.totalAmount
    /* temp fix for now  before jit integration*/

        billDetailObj.totalPaidAmount = detail?.totalAmount
        //billDetailObj.status = 'INITIATED'
        billDetailObj.payableLineItems = detail?.payableLineItems?.filter((row => row.status==="ACTIVE"))?.map(item => (
          {
            lineItemId: item?.id,
            tenantId: item?.tenantId,
            paidAmount: item?.amount,
            //status: 'INITIATED'
          }
        ))
        billDetailObj.additionalDetails = {}
        billObj.billDetails.push(billDetailObj)
      })
    }
    payment.bills.push(billObj)
  })
  payment.netPaidAmount=payment.netPayableAmount;
  let payload = {payment}
  
  return payload
}
const getUpdatePaymentPayload = (payment={}) =>{
  if(payment?.status){
    payment.status=PAYMENT_UPDATE_STATUS;
  }
  if(payment?.bills){
    payment.bills=payment?.bills?.map(bill=>{
      if(bill?.status){
        bill.status=PAYMENT_UPDATE_STATUS;
      }
      bill.billDetails= bill.billDetails?.map(billDetail=>{
        if(billDetail?.status){
          billDetail.status=PAYMENT_UPDATE_STATUS;
        }
        return {...billDetail}
      })
      return {...bill}
    })
  }
  return {payment};
}

const RetryComponent = ({row,t})=> <LinkLabel
  onClick={async () => {
    try {
      const pdfRegenerateResponse =
        row?.paymentId &&
        (await Digit.ExpenseService.regeneratePDF(
          {
            Criteria: {
              paymentId: row?.paymentId,
            },
          },
          row?.tenantId
        ));
      console.info(pdfRegenerateResponse);
    } catch (error) {
      console.error(error, "downloaderror");
    }
  }}
>
  {t("CS_COMMON_RETRY")}
</LinkLabel>;

export const UICustomizations = {
  EstimateInboxConfig: {
    preProcess: (data) => {
      //set tenantId
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.processSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();

      const estimateNumber = data?.body?.inbox?.moduleSearchCriteria?.estimateNumber?.trim()
      if(estimateNumber) data.body.inbox.moduleSearchCriteria.estimateNumber = estimateNumber

      const projectId = data?.body?.inbox?.moduleSearchCriteria?.projectId?.trim()
      if(projectId) data.body.inbox.moduleSearchCriteria.projectId = projectId
      // deleting them for now(assignee-> need clarity from pintu,ward-> static for now,not implemented BE side)

      const assignee = _.clone(data.body.inbox.moduleSearchCriteria.assignee);
      delete data.body.inbox.moduleSearchCriteria.assignee;
      if (assignee?.code === "ASSIGNED_TO_ME") {
        data.body.inbox.moduleSearchCriteria.assignee = Digit.UserService.getUser().info.uuid;
      }

      
      let ward = _.clone(data.body.inbox.moduleSearchCriteria.ward ? data.body.inbox.moduleSearchCriteria.ward : []);
      delete data.body.inbox.moduleSearchCriteria.ward;
      ward = ward?.map((row) => row?.code);
      if (ward.length > 0) data.body.inbox.moduleSearchCriteria.ward = ward;

      //cloning locality and workflow states to format them
      let locality = _.clone(data.body.inbox.moduleSearchCriteria.locality ? data.body.inbox.moduleSearchCriteria.locality : []);
      let states = _.clone(data.body.inbox.moduleSearchCriteria.state ? data.body.inbox.moduleSearchCriteria.state : []);
      delete data.body.inbox.moduleSearchCriteria.locality;
      delete data.body.inbox.moduleSearchCriteria.state;
      locality = locality?.map((row) => row?.code);
      states = Object.keys(states)?.filter((key) => states[key]);

      //adding formatted data to these keys
      if (locality.length > 0) data.body.inbox.moduleSearchCriteria.locality = locality;
      if (states.length > 0) data.body.inbox.moduleSearchCriteria.status = states;

      const projectType = _.clone(data.body.inbox.moduleSearchCriteria.projectType ? data.body.inbox.moduleSearchCriteria.projectType : {});
      if (projectType?.code) data.body.inbox.moduleSearchCriteria.projectType = projectType.code;

      //adding tenantId to moduleSearchCriteria
      data.body.inbox.moduleSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();

      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      switch(key){
         case "ESTIMATE_ESTIMATE_NO":
          return (
           <span className="link">
            <Link to={`/${window.contextPath}/employee/estimate/estimate-details?tenantId=${row.ProcessInstance.tenantId}&estimateNumber=${value}`}>
              {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
            </Link>
           </span>
          );
         case "COMMON_ASSIGNEE":
          return value? <span>{value?.[0]?.name}</span> : <span>{t("NA")}</span>;

         case "COMMON_WORKFLOW_STATES":
          return <span>{t(`WF_EST_${value}`)}</span>;

         case "WORKS_ESTIMATED_AMOUNT":
          return <Amount customStyle={{ textAlign: 'right'}} value={Math.round(value)} t={t}></Amount>

         case "COMMON_SLA_DAYS":
          return value > 0 ? <span className="sla-cell-success">{value}</span> : <span className="sla-cell-error">{value}</span>;
        
         default:
          return t("ES_COMMON_NA");
      }
     },
     additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    },
    MobileDetailsOnClick: (row, tenantId) => {
      let link;
      Object.keys(row).map((key) => {
        if (key === "ESTIMATE_ESTIMATE_NO")
          link = `/${window.contextPath}/employee/estimate/estimate-details?tenantId=${tenantId}&estimateNumber=${
            row[key]
          }`;
      });
      return link;
    },
  },
  AttendanceInboxConfig: {
    preProcess: (data) => {
      
      //set tenantId
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.processSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();

      const musterRollNumber = data?.body?.inbox?.moduleSearchCriteria?.musterRollNumber?.trim();
      if(musterRollNumber) data.body.inbox.moduleSearchCriteria.musterRollNumber = musterRollNumber

      const attendanceRegisterName = data?.body?.inbox?.moduleSearchCriteria?.attendanceRegisterName?.trim();
      if(attendanceRegisterName) data.body.inbox.moduleSearchCriteria.attendanceRegisterName = attendanceRegisterName

      // deleting them for now(assignee-> need clarity from pintu,ward-> static for now,not implemented BE side)
      const assignee = _.clone(data.body.inbox.moduleSearchCriteria.assignee);
      delete data.body.inbox.moduleSearchCriteria.assignee;
      if (assignee?.code === "ASSIGNED_TO_ME") {
        data.body.inbox.moduleSearchCriteria.assignee = Digit.UserService.getUser().info.uuid;
      }

      //cloning locality and workflow states to format them
      // let locality = _.clone(data.body.inbox.moduleSearchCriteria.locality ? data.body.inbox.moduleSearchCriteria.locality : []);
      
      let selectedOrg =  _.clone(data.body.inbox.moduleSearchCriteria.orgId ? data.body.inbox.moduleSearchCriteria.orgId : null);
      delete data.body.inbox.moduleSearchCriteria.orgId;
      if(selectedOrg) {
         data.body.inbox.moduleSearchCriteria.orgId = selectedOrg?.[0]?.applicationNumber;
      }

      // let selectedWard =  _.clone(data.body.inbox.moduleSearchCriteria.ward ? data.body.inbox.moduleSearchCriteria.ward : null);
      // delete data.body.inbox.moduleSearchCriteria.ward;
      // if(selectedWard) {
      //    data.body.inbox.moduleSearchCriteria.ward = selectedWard?.[0]?.code;
      // }

      let states = _.clone(data.body.inbox.moduleSearchCriteria.state ? data.body.inbox.moduleSearchCriteria.state : []);
      let ward = _.clone(data.body.inbox.moduleSearchCriteria.ward ? data.body.inbox.moduleSearchCriteria.ward : []);
      // delete data.body.inbox.moduleSearchCriteria.locality;
      delete data.body.inbox.moduleSearchCriteria.state;
      delete data.body.inbox.moduleSearchCriteria.ward;

      // locality = locality?.map((row) => row?.code);
      states = Object.keys(states)?.filter((key) => states[key]);
      ward = ward?.map((row) => row?.code);
      
      
      // //adding formatted data to these keys
      // if (locality.length > 0) data.body.inbox.moduleSearchCriteria.locality = locality;
      if (states.length > 0) data.body.inbox.moduleSearchCriteria.status = states;  
      if (ward.length > 0) data.body.inbox.moduleSearchCriteria.ward = ward;
      const projectType = _.clone(data.body.inbox.moduleSearchCriteria.projectType ? data.body.inbox.moduleSearchCriteria.projectType : {});
      if (projectType?.code) data.body.inbox.moduleSearchCriteria.projectType = projectType.code;

      //adding tenantId to moduleSearchCriteria
      data.body.inbox.moduleSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();

      //setting limit and offset becoz somehow they are not getting set in muster inbox 
      data.body.inbox .limit = data.state.tableForm.limit
      data.body.inbox.offset = data.state.tableForm.offset
      delete data.state
      return data;
    },
    postProcess: (responseArray, uiConfig) => {
      const statusOptions = responseArray?.statusMap
        ?.filter((item) => item.applicationstatus)
        ?.map((item) => ({ code: item.applicationstatus, i18nKey: `COMMON_MASTERS_${item.applicationstatus}` }));
      if (uiConfig?.type === "filter") {
        let fieldConfig = uiConfig?.fields?.filter((item) => item.type === "dropdown" && item.populators.name === "musterRollStatus");
        if (fieldConfig.length) {
          fieldConfig[0].populators.options = statusOptions;
        }
      }
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      if (key === "ATM_MUSTER_ROLL_ID") {
        return (
          <span className="link">
            <Link
              to={`/${window.contextPath}/employee/attendencemgmt/view-attendance?tenantId=${Digit.ULBService.getCurrentTenantId()}&musterRollNumber=${value}`}
            >
              {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
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

      if(key === "ATM_AMOUNT_IN_RS"){
        return <span>{value ? Digit.Utils.dss.formatterWithoutRound(value, "number") : t("ES_COMMON_NA")}</span>;
      }
      if (key === "ATM_SLA") {
        return parseInt(value) > 0 ? (
          <span className="sla-cell-success">{t(value) || ""}</span>
        ) : (
          <span className="sla-cell-error">{t(value) || ""}</span>
        );
      }
      if (key === "COMMON_WORKFLOW_STATES") {
        return <span>{t(`WF_MUSTOR_${value}`)}</span>
      }

      //added this in case we change the key and not updated here , it'll throw that nothing was returned from cell error if that case is not handled here. To prevent that error putting this default
      return <span>{t(`CASE_NOT_HANDLED`)}</span>
    },
    MobileDetailsOnClick: (row, tenantId) => {
      let link;
      Object.keys(row).map((key) => {
        if (key === "ATM_MUSTER_ROLL_ID")
          link = `/${window.contextPath}/employee/attendencemgmt/view-attendance?tenantId=${tenantId}&musterRollNumber=${row[key]}`;
      });
      return link;
    },
    populateReqCriteria: () => {
      
      const tenantId = Digit.ULBService.getCurrentTenantId();

      return {
        url: "/org-services/organisation/v1/_search",
        params: { limit: 50, offset: 0 },
        body: {
          SearchCriteria: {
            tenantId: tenantId,
            functions : {
              type : "CBO"
            }
          },
        },
        config: {
          enabled: true,
          select: (data) => {
            return data?.organisations;
          },
        },
      };
    },
  },
  SearchEstimateConfig: {
    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { fromProposalDate, toProposalDate } = data;
      if ((fromProposalDate === "" && toProposalDate !== "") || (fromProposalDate !== "" && toProposalDate === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    preProcess: (data,defaultValues) => {
      //get data to set in api
      const fromProposalDate = Digit.Utils.pt.convertDateToEpoch(data?.body?.inbox?.moduleSearchCriteria?.fromProposalDate,"daystart");
      if(fromProposalDate) data.body.inbox.moduleSearchCriteria.fromProposalDate = fromProposalDate
      const toProposalDate = Digit.Utils.pt.convertDateToEpoch(data?.body?.inbox?.moduleSearchCriteria?.toProposalDate);
      if(toProposalDate) data.body.inbox.moduleSearchCriteria.toProposalDate = toProposalDate
      
      const status = data?.body?.inbox?.moduleSearchCriteria?.status?.[0]?.wfStatus
      delete data?.body?.inbox?.moduleSearchCriteria?.status
      if(status){
        data.body.inbox.moduleSearchCriteria.status = status
      }

      const projectType = data?.body?.inbox?.moduleSearchCriteria?.typeOfWork?.code;
      delete data.body.inbox.moduleSearchCriteria.typeOfWork
      if(projectType) data.body.inbox.moduleSearchCriteria.typeOfWork = projectType

      const ward = data?.body?.inbox?.moduleSearchCriteria?.ward?.[0]?.code
      delete data.body.inbox.moduleSearchCriteria.ward
      if(ward) data.body.inbox.moduleSearchCriteria.ward = ward

      const estimateId = data?.body?.inbox?.moduleSearchCriteria?.estimateId?.trim()
      if(estimateId) data.body.inbox.moduleSearchCriteria.estimateId = estimateId
    
      const projectName = data?.body?.inbox?.moduleSearchCriteria?.projectName?.trim()
      if(projectName) data.body.inbox.moduleSearchCriteria.projectName = projectName

      //set tenantId 
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.moduleSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();

      //here iterate over defaultValues and set from presets in the api
      
      const presets  = Digit.Hooks.useQueryParams();
      if(Object.keys(presets).length > 0 ) {
        Object.keys(presets).forEach(preset => {
          //if present in defaultValues object then only set it
          if(Object.keys(defaultValues).some(key => key===preset)){
            data.body.inbox.moduleSearchCriteria[preset] = presets[preset]
          }
        })
      }

      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result

      const getAmount = (item) => {
        return item.amountDetail.reduce((acc, row) => acc + row.amount, 0);
      };
      if (key === "ESTIMATE_ESTIMATE_NO") {
        return (
          <span className="link">
            <Link
              to={`/${
                window.contextPath
              }/employee/estimate/estimate-details?tenantId=${Digit.ULBService.getCurrentTenantId()}&estimateNumber=${value}`}
            >
              {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      }
      if (key === "ES_COMMON_PROJECT_NAME") {
        return (
          <div class="tooltip">
            <span class="textoverflow" style={{ "--max-width": `${column.maxLength}ch` }}>
              {String(value ? value : t("ES_COMMON_NA"))}
            </span>
            {/* check condtion - if length greater than 20 */}
            <span class="tooltiptext" style={{ whiteSpace: "nowrap" }}>
              {row?.businessObject?.project?.description || t("ES_COMMON_NA")}
            </span>
          </div>
        );
      }
      if (key === "WORKS_ESTIMATED_AMOUNT") {
        return <Amount customStyle={{ textAlign: 'right'}} value={Math.round(value)} t={t}></Amount>
      }
      if(key === "CORE_COMMON_STATUS"){
        return t(`WF_ESTIMATE_STATUS_${value}`)
      }
      if (key === "ES_COMMON_LOCATION") {
        const location = value;
        const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())
        if (location) {
          let locality = location?.locality ? t(`${headerLocale}_ADMIN_${location?.locality}`) : "";
          let ward = location?.ward ? t(`${headerLocale}_ADMIN_${location?.ward}`) : "";
          let city = location?.city ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(location?.city)}`) : "";
          return <p>{`${ward ? ward + ", " : ""}${city}`}</p>;
        }
        return <p>{"NA"}</p>;
      }
    },
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    },
    MobileDetailsOnClick: (row, tenantId) => {
      let link;
      Object.keys(row).map((key) => {
        if (key === "ESTIMATE_ESTIMATE_NO")
          link = `/${window.contextPath}/employee/estimate/estimate-details?tenantId=${tenantId}&estimateNumber=${row[key]}`;
      });
      return link;
    },
    populateReqCriteria: () => {
      
      const tenantId = Digit.ULBService.getCurrentTenantId();

      return {
        url: "/egov-workflow-v2/egov-wf/businessservice/_search",
        params: { tenantId, businessServices: Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("estimate") },
        body: {
         
        },
        config: {
          enabled: true,
          select: (data) => {
            const states =  data?.BusinessServices?.[0]?.states?.filter(state=> state.state)?.map(state=> {
              return {
                "i18nKey":`WF_${Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("estimate")}_STATUS_${state?.state}`,
                "wfStatus":state?.state
              }
            })
            return states  
          },
        },
      };
    },
  },
  SearchProjectConfig: {
    preProcess: (data) => {
      const createdFrom = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdFrom, "daystart");
      const createdTo = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdTo);
      const projectType = data.body.Projects[0]?.projectType?.code;
      const ward = data.body.Projects[0]?.ward?.[0]?.code;
      data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), includeAncestors: true, createdFrom, createdTo };
      let name = data.body.Projects[0]?.name?.trim();
      let projectNumber = data.body.Projects[0]?.projectNumber?.trim()
      delete data.body.Projects[0]?.createdFrom;
      delete data.body.Projects[0]?.createdTo;
      data.body.Projects[0] = { ...data.body.Projects[0], tenantId: Digit.ULBService.getCurrentTenantId(),projectNumber, projectType, name, address : { boundary : ward}  };

      return data;
    },
    postProcess: (responseArray) => {
      const listOfUuids = responseArray?.map((row) => row.auditDetails.createdBy);
      const uniqueUuids = listOfUuids?.filter(function (item, i, ar) {
        return ar.indexOf(item) === i;
      });
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
      const { isLoading: isPostProcessLoading, data: combinedResponse, isFetching: isPostProcessFetching } = Digit.Hooks.useCustomAPIHook(
        reqCriteria
      );

      return {
        isPostProcessFetching,
        isPostProcessLoading,
        combinedResponse,
      };
    },
    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { createdFrom, createdTo } = data;
      if (createdTo !== "" && createdFrom === "")
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };
      else if (createdTo === "" && createdFrom !== "")
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result
     switch(key){
        case "WORKS_PROJECT_ID":
         return (
          <span className="link">
            <Link to={`/${window.contextPath}/employee/project/project-details?tenantId=${row?.tenantId}&projectNumber=${value}`}>
            {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
            </Link>
          </span>
         );

       case "WORKS_PARENT_PROJECT_ID":
         return value ? (
          <span className="link">
            <Link to={`/${window.contextPath}/employee/project/project-details?tenantId=${row?.tenantId}&projectNumber=${value}`}>
              {String(value ? value : t("ES_COMMON_NA"))}
            </Link>
          </span>
         ) : (
          t("ES_COMMON_NA")
         );

      case "WORKS_PROJECT_NAME": 
       { let currentProject = searchResult?.filter((result) => result?.id === row?.id)[0];
        return (
          <div class="tooltip">
            <span class="textoverflow" style={{ "--max-width": `${column?.maxLength}ch` }}>         
              {String(t(value))}
            </span>
            {/* check condtion - if length greater than 20 */}
            <span class="tooltiptext" style={{ whiteSpace: "nowrap" }}>
              {currentProject?.description}
            </span>
          </div>
         ); }

      case "PROJECT_ESTIMATED_COST_IN_RS":
        return <Amount customStyle={{ textAlign: 'right'}} value={value} t={t}></Amount>

      case "ES_COMMON_LOCATION":    
      { let currentProject = searchResult?.filter((result) => result?.id === row?.id)[0];
        const headerLocale = Digit.Utils.locale.getTransformedLocale(row?.tenantId)
        if (currentProject) {
          let locality = currentProject?.address?.boundary ? t(`${headerLocale}_ADMIN_${currentProject?.address?.boundary}`) : "";
          let ward = currentProject?.additionalDetails?.ward ? t(`${headerLocale}_ADMIN_${currentProject?.additionalDetails?.ward}`) : "";
          let city = currentProject?.address?.city
            ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(currentProject?.address?.city)}`)
            : "";
          return <p>{`${locality ? locality + ", " : ""}${ward ? ward + ", " : ""}${city}`}</p>;
        }
        return <p>{"NA"}</p>
      }

      default:
        return t("ES_COMMON_NA");
      }
    },
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    },
    MobileDetailsOnClick: (row, tenantId) => {
      let link;
      Object.keys(row).map((key) => {
        if (key === "WORKS_PROJECT_ID")
          link = `/${window.contextPath}/employee/project/project-details?tenantId=${tenantId}&projectNumber=${row[key]}`;
      });
      return link;
    },
  },
  SearchWMSProjectConfig: {
    preProcess: (data) => {
     const createdFrom = Digit.Utils.pt.convertDateToEpoch(data.body.inbox.moduleSearchCriteria?.createdFrom, "daystart");
      const createdTo = Digit.Utils.pt.convertDateToEpoch(data.body.inbox.moduleSearchCriteria?.createdTo);
      const projectType = data.body.inbox.moduleSearchCriteria?.projectType?.code;
      const boundary = data.body.inbox.moduleSearchCriteria?.boundary?.[0]?.code;
      data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), includeAncestors: true };
      let projectName = data.body.inbox.moduleSearchCriteria?.projectName?.trim();
      let projectNumber = data.body.inbox.moduleSearchCriteria?.projectNumber?.trim()
      delete data.body.inbox.moduleSearchCriteria?.createdFrom;
      delete data.body.inbox.moduleSearchCriteria?.ward;
      delete data.body.inbox.moduleSearchCriteria?.createdTo;
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.moduleSearchCriteria = { ...data.body.inbox.moduleSearchCriteria, tenantId: Digit.ULBService.getCurrentTenantId(),projectNumber, projectType, projectName, boundary, createdFrom, createdTo};

      return data;
    },
    postProcess: (responseArray) => {
      const listOfUuids = responseArray?.map((row) => row.auditDetails.createdBy);
      const uniqueUuids = listOfUuids?.filter(function (item, i, ar) {
        return ar.indexOf(item) === i;
      });
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
      const { isLoading: isPostProcessLoading, data: combinedResponse, isFetching: isPostProcessFetching } = Digit.Hooks.useCustomAPIHook(
        reqCriteria
      );

      return {
        isPostProcessFetching,
        isPostProcessLoading,
        combinedResponse,
      };
    },
    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { createdFrom, createdTo } = data;
      if (createdTo !== "" && createdFrom === "")
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };
      else if (createdTo === "" && createdFrom !== "")
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result
     switch(key){
        case "WORKS_PROJECT_ID":
         return (
          <span className="link">
            <Link to={`/${window.contextPath}/employee/project/project-details?tenantId=${row?.businessObject?.tenantId}&projectNumber=${value}`}>
            {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
            </Link>
          </span>
         );

       case "WORKS_PARENT_PROJECT_ID":
         return value ? (
          <span className="link">
            <Link to={`/${window.contextPath}/employee/project/project-details?tenantId=${row?.businessObject?.tenantId}&projectNumber=${value}`}>
              {String(value ? value : t("ES_COMMON_NA"))}
            </Link>
          </span>
         ) : (
          t("ES_COMMON_NA")
         );

      case "WORKS_PROJECT_NAME": 
       { let currentProject = searchResult?.filter((result) => result?.businessObject?.id === row?.businessObject?.id)[0];
        return (
          <div class="tooltip">
            <span class="textoverflow" style={{ "--max-width": `${column?.maxLength}ch` }}>         
              {String(t(value))}
            </span>
            {/* check condtion - if length greater than 20 */}
            <span class="tooltiptext" style={{ whiteSpace: "nowrap" }}>
              {currentProject?.businessObject?.description}
            </span>
          </div>
         ); }

      case "PROJECT_ESTIMATED_COST_IN_RS":
        return <Amount customStyle={{ textAlign: 'right'}} value={value} t={t}></Amount>

      case "ES_COMMON_LOCATION":    
      { let currentProject = searchResult?.filter((result) => result?.businessObject.id === row?.businessObject.id)[0].businessObject;
        const headerLocale = Digit.Utils.locale.getTransformedLocale(row?.businessObject.tenantId)
        if (currentProject) {
          let locality = currentProject?.address?.boundary ? t(`${headerLocale}_ADMIN_${currentProject?.address?.boundary}`) : "";
          let ward = currentProject?.additionalDetails?.ward ? t(`${headerLocale}_ADMIN_${currentProject?.additionalDetails?.ward}`) : "";
          let city = currentProject?.address?.city
            ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(currentProject?.address?.city)}`)
            : "";
          return <p>{`${locality ? locality + ", " : ""}${ward ? ward + ", " : ""}${city}`}</p>;
        }
        return <p>{"NA"}</p>
      }

      default:
        return t("ES_COMMON_NA");
      }
    },
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    },
    MobileDetailsOnClick: (row, tenantId) => {
      let link;
      Object.keys(row).map((key) => {
        if (key === "WORKS_PROJECT_ID")
          link = `/${window.contextPath}/employee/project/project-details?tenantId=${tenantId}&projectNumber=${row[key]}`;
      });
      return link;
    },
  },
  SearchAttendanceConfig: {
    preProcess: (data) => {
      
      //get data to set in api
      const startDate = Digit.Utils.pt.convertDateToEpoch(data?.body?.inbox?.moduleSearchCriteria?.startDate,"daystart");
      if(startDate) data.body.inbox.moduleSearchCriteria.startDate = startDate
      const endDate = Digit.Utils.pt.convertDateToEpoch(data?.body?.inbox?.moduleSearchCriteria?.endDate);
      if(endDate) data.body.inbox.moduleSearchCriteria.endDate = endDate
      
      const projectType = data?.body?.inbox?.moduleSearchCriteria?.projectType?.code;
      delete data.body.inbox.moduleSearchCriteria.projectType
      if(projectType) data.body.inbox.moduleSearchCriteria.projectType = projectType

      const ward = data?.body?.inbox?.moduleSearchCriteria?.ward?.[0]?.code
      delete data.body.inbox.moduleSearchCriteria.ward
      if(ward) data.body.inbox.moduleSearchCriteria.ward = ward
    
      const status = data?.body?.inbox?.moduleSearchCriteria?.musterRollStatus?.[0]?.wfStatus
      delete data?.body?.inbox?.moduleSearchCriteria?.musterRollStatus
      if(status){
        data.body.inbox.moduleSearchCriteria.musterRollStatus = status
      }

      const musterRollNumber = data?.body?.inbox?.moduleSearchCriteria?.musterRollNumber?.trim();
      if(musterRollNumber) data.body.inbox.moduleSearchCriteria.musterRollNumber = musterRollNumber

      const attendanceRegisterName = data?.body?.inbox?.moduleSearchCriteria?.attendanceRegisterName?.trim();
      if(attendanceRegisterName) data.body.inbox.moduleSearchCriteria.attendanceRegisterName = attendanceRegisterName
      //set tenantId 
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.moduleSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();
      return data;
    },
    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { startDate, endDate } = data;
      if ((startDate === "" && endDate !== "") || (startDate !== "" && endDate === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      if (key === "ES_COMMON_MUSTER_ROLL_ID") {
        return (
          <span className="link">
            <Link
              to={`/${
                window.contextPath
              }/employee/attendencemgmt/view-attendance?tenantId=${Digit.ULBService.getCurrentTenantId()}&musterRollNumber=${value}`}
            >
              {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      }
      if(key === "ES_COMMON_LOCATION"){
        return value ? (
          <span style={{ whiteSpace: "nowrap" }}>
            <p>{`${value?.locality ? value?.locality + ", " : ""}${value?.ward ? value?.ward + ", " : ""}${t(Digit.Utils.locale.getCityLocale(row?.businessObject?.tenantId))}`}</p>
          </span>
        ) : (
          t("ES_COMMON_NA")
        );
      }

      if (key === "ES_COMMON_PROJECT_NAME") {
        return (
          <div class="tooltip">
            <span class="textoverflow" style={{ "--max-width": `${column.maxLength}ch` }}>
              {String(t(value))}
            </span>
            {/* check condtion - if length greater than 20 */}
            <span class="tooltiptext" style={{ whiteSpace: "nowrap" }}>
              {row?.businessObject?.additionalDetails?.projectDesc || t("ES_COMMON_NA")}
            </span>
          </div>
        );
      }


      if (key === "CORE_COMMON_STATUS") {
        return <span>{t(`WF_MUSTOR_${value}`)}</span>
      }

      if(key === "MUSTER_WAGE_AMOUNT") {
         return <span>{value ? Digit.Utils.dss.formatterWithoutRound(value, "number") : t("ES_COMMON_NA")}</span>;
      }
      
    },
    MobileDetailsOnClick: (row, tenantId) => {
      let link;
      Object.keys(row).map((key) => {
        if (key === "ES_COMMON_MUSTER_ROLL_ID")
          link = `/${window.contextPath}/employee/attendencemgmt/view-attendance?tenantId=${tenantId}&musterRollNumber=${row[key]}`;
      });
      return link;
    },
    populateReqCriteria: () => {
      
      const tenantId = Digit.ULBService.getCurrentTenantId();

      return {
        url: "/egov-workflow-v2/egov-wf/businessservice/_search",
        params: { tenantId, businessServices: Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("muster roll") },
        body: {
         
        },
        config: {
          enabled: true,
          select: (data) => {
            const states =  data?.BusinessServices?.[0]?.states?.filter(state=> state.state)?.map(state=> {
              return {
                "i18nKey":`WF_${Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("muster roll")}_STATUS_${state?.state}`,
                "wfStatus":state?.state
              }
            })
            return states  
          },
        },
      };
    },
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    }
  },
  ContractsInboxConfig: {
    preProcess: (data) => {
      //set tenantId(inbox,moduleSearch,ProcessSearch)
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.processSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.moduleSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();

      const workOrderNumber = data?.body?.inbox?.moduleSearchCriteria?.workOrderNumber?.trim()
      if(workOrderNumber) data.body.inbox.moduleSearchCriteria.workOrderNumber = workOrderNumber

      const projectId = data?.body?.inbox?.moduleSearchCriteria?.projectId?.trim()
      if(projectId) data.body.inbox.moduleSearchCriteria.projectId = projectId

      const assignee = _.clone(data.body.inbox.moduleSearchCriteria.assignee);
      delete data.body.inbox.moduleSearchCriteria.assignee;
      if (assignee?.code === "ASSIGNED_TO_ME") {
        data.body.inbox.moduleSearchCriteria.assignee = Digit.UserService.getUser().info.uuid;
      }

      delete data.body.inbox.moduleSearchCriteria.ward;

      //cloning locality and workflow states to format them
      let locality = _.clone(data.body.inbox.moduleSearchCriteria.locality ? data.body.inbox.moduleSearchCriteria.locality : []);
      let states = _.clone(data.body.inbox.moduleSearchCriteria.state ? data.body.inbox.moduleSearchCriteria.state : []);
      delete data.body.inbox.moduleSearchCriteria.locality;
      delete data.body.inbox.moduleSearchCriteria.state;
      locality = locality?.map((row) => row?.code);
      states = Object.keys(states)?.filter((key) => states[key]);

      //adding formatted data to these keys
      if (locality.length > 0) data.body.inbox.moduleSearchCriteria.locality = locality;
      if (states.length > 0) data.body.inbox.moduleSearchCriteria.status = states;

      const projectType = _.clone(data.body.inbox.moduleSearchCriteria.projectType ? data.body.inbox.moduleSearchCriteria.projectType : {});
      if (projectType?.code) data.body.inbox.moduleSearchCriteria.projectType = projectType.code;

      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      switch(key){
        case "WORKS_ORDER_NO": 
          return (
           <span className="link">
            <Link to={`/${window.contextPath}/employee/contracts/contract-details?tenantId=${row?.ProcessInstance.tenantId}&workOrderNumber=${value}`}>
              {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
            </Link>
           </span>
          );

        case "COMMON_ASSIGNEE":
          return value? <span>{value?.[0]?.name}</span> : <span>{t("NA")}</span>;

        case "COMMON_WORKFLOW_STATES":
          return <span>{t(`WF_WO_${value}`)}</span>;

        case "ES_COMMON_AMOUNT":
          return <Amount customStyle={{ textAlign: 'right'}} value={value} t={t}></Amount>

        case "COMMON_SLA_DAYS":
          return value > 0 ? <span className="sla-cell-success">{value}</span> : <span className="sla-cell-error">{value}</span>;

        default:
          return t("ES_COMMON_NA");
      }
    },
    MobileDetailsOnClick: (row, tenantId) => {
      let link;
      Object.keys(row).map((key) => {
        if (key === "WORKS_ORDER_NO")
          link = `/${window.contextPath}/employee/contracts/contract-details?tenantId=${tenantId}&workOrderNumber=${row[key]}`;
      });
      return link;
    },
  },
  SearchContractConfig: {
    preProcess: (data,defaultValues) => {
      const startDate = Digit.Utils.pt.convertDateToEpoch(data.body.inbox?.moduleSearchCriteria?.createdFrom,'daystart');
      const endDate = Digit.Utils.pt.convertDateToEpoch(data.body.inbox?.moduleSearchCriteria?.createdTo,'dayend');
      const workOrderNumber = data.body.inbox?.moduleSearchCriteria?.workOrderNumber?.trim();
      const status = data?.body?.inbox?.moduleSearchCriteria?.status?.[0]?.wfStatus
      const projectType = data.body.inbox?.moduleSearchCriteria?.projectType?.code;
      const projectName = data.body.inbox?.moduleSearchCriteria?.projectName?.trim();
      const ward = data.body.inbox?.moduleSearchCriteria?.ward?.[0]?.code;
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.moduleSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox = {
        ...data.body.inbox,
        tenantId: Digit.ULBService.getCurrentTenantId(),
        moduleSearchCriteria: {
          tenantId: Digit.ULBService.getCurrentTenantId(),
          ward,
          workOrderNumber,
          projectType,
          projectName,
          startDate,
          endDate,
          status
        },
      };

      const presets  = Digit.Hooks.useQueryParams();
      if(Object.keys(presets).length > 0 ) {
        Object.keys(presets).forEach(preset => {
          //if present in defaultValues object then only set it
          if(Object.keys(defaultValues).some(key => key===preset)){
            data.body.inbox.moduleSearchCriteria[preset] = presets[preset]
          }
        })
      }
      
      return data;
    },
    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { createdFrom, createdTo } = data;
      if ((createdFrom === "" && createdTo !== "") || (createdFrom !== "" && createdTo === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result
      switch (key) {
      case "WORKS_ORDER_ID": 
        return (
          <span className="link">
            <Link
              to={`/${window.contextPath}/employee/contracts/contract-details?tenantId=${row?.businessObject?.tenantId}&workOrderNumber=${value}`}>
                {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      
      case "WORKS_PROJECT_NAME":
          return (
            <div class="tooltip">
              <span class="textoverflow" style={{ "--max-width": `${column.maxLength}ch` }}>
                {String(t(value))}
              </span>
              {/* check condtion - if length greater than 20 */}
              <span class="tooltiptext" style={{ whiteSpace: "nowrap" }}>
                {row?.businessObject?.additionalDetails?.projectDesc || t("ES_COMMON_NA")}
              </span>
            </div>
          );
        
      case "ES_COMMON_AMOUNT":
        return <Amount customStyle={{ textAlign: 'right'}} value={value} t={t}></Amount>
      case "COMMON_ROLE_OF_CBO": 
        return <span>{t(`COMMON_MASTERS_${value}`)}</span>;

      case "ES_COMMON_LOCATION": 
        return value ? (
          <span style={{ whiteSpace: "break-spaces" }}>
            {String(
              `${t(Digit.Utils.locale.getCityLocale(row?.businessObject?.tenantId))}, ${t(
                Digit.Utils.locale.getMohallaLocale(value, row?.businessObject?.tenantId)
              )}`
            )}
          </span>
        ) : (
          t("ES_COMMON_NA")
        );
      case "ES_COMMON_STATUS":
        return value ? (
          <span style={{ whiteSpace: "break-spaces" }}>
            {t(`WF_${Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("contract")}_STATUS_${value}`)}
          </span>
        ) : (
          t("ES_COMMON_NA")
        );
      default:
        return t("ES_COMMON_NA");
      }
    },
    MobileDetailsOnClick: (row, tenantId) => {
      let link;
      Object.keys(row).map((key) => {
        if (key === "WORKS_ORDER_ID")
          link = `/${window.contextPath}/employee/contracts/contract-details?tenantId=${tenantId}&workOrderNumber=${row[key]}`;
      });
      return link;
    },
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    },
    populateReqCriteria: () => {
      
      const tenantId = Digit.ULBService.getCurrentTenantId();

      return {
        url: "/egov-workflow-v2/egov-wf/businessservice/_search",
        params: { tenantId, businessServices: Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("contract") },
        body: {
         
        },
        config: {
          enabled: true,
          select: (data) => {
            const states =  data?.BusinessServices?.[0]?.states?.filter(state=> state.state)?.map(state=> {
              return {
                "i18nKey":`WF_${Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("contract")}_STATUS_${state?.state}`,
                "wfStatus":state?.state
              }
            })
            return states  
          },
        },
      };
    },
  },
  SearchWageSeekerConfig: {
    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { createdFrom, createdTo } = data;
      if ((createdFrom === "" && createdTo !== "") || (createdFrom !== "" && createdTo === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    preProcess: (data) => {
      data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId() };

      let requestBody = { ...data.body.Individual };
      const pathConfig = {
        name: "name.givenName",
      };
      const dateConfig = {
        createdFrom: "daystart",
        createdTo: "dayend",
      };
      const selectConfig = {
        wardCode: "wardCode[0].code",
        socialCategory: "socialCategory.code",
      };
      const textConfig = ["name", "individualId"]
      let Individual = Object.keys(requestBody)
        .map((key) => {
          if (selectConfig[key]) {
            requestBody[key] = _.get(requestBody, selectConfig[key], null);
          } else if (typeof requestBody[key] == "object") {
            requestBody[key] = requestBody[key]?.code;
          } else if (textConfig?.includes(key)) {
            requestBody[key] = requestBody[key]?.trim()
          }
          return key;
        })
        .filter((key) => requestBody[key])
        .reduce((acc, curr) => {
          if (pathConfig[curr]) {
            _.set(acc, pathConfig[curr], requestBody[curr]);
          } else if (dateConfig[curr] && dateConfig[curr]?.includes("day")) {
            _.set(acc, curr, Digit.Utils.date.convertDateToEpoch(requestBody[curr], dateConfig[curr]));
          } else {
            _.set(acc, curr, requestBody[curr]);
          }
          return acc;
        }, {});

      data.body.Individual = { ...Individual };
      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result
      switch (key) {
        case "MASTERS_WAGESEEKER_ID":
          return (
            <span className="link">
              <Link to={`/${window.contextPath}/employee/masters/view-wageseeker?tenantId=${row?.tenantId}&individualId=${value}`}>
                 {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
              </Link>
            </span>
          );

        case "MASTERS_SOCIAL_CATEGORY":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(`MASTERS_${value}`))}</span> : t("ES_COMMON_NA");

        case "CORE_COMMON_PROFILE_CITY":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(Digit.Utils.locale.getCityLocale(value)))}</span> : t("ES_COMMON_NA");

        case "MASTERS_WARD":
          return value ? (
            <span style={{ whiteSpace: "nowrap" }}>{String(t(Digit.Utils.locale.getMohallaLocale(value, row?.tenantId)))}</span>
          ) : (
            t("ES_COMMON_NA")
          );

        case "MASTERS_LOCALITY":
          return value ? (
            <span style={{ whiteSpace: "break-spaces" }}>{String(t(Digit.Utils.locale.getMohallaLocale(value, row?.tenantId)))}</span>
          ) : (
            t("ES_COMMON_NA")
          );
        default:
          return t("ES_COMMON_NA");
      }
    },
    MobileDetailsOnClick: (row, tenantId) => {
      let link;
      Object.keys(row).map((key) => {
        if (key === "MASTERS_WAGESEEKER_ID")
          link = `/${window.contextPath}/employee/masters/view-wageseeker?tenantId=${tenantId}&wageseekerId=${row[key]}`;
      });
      return link;
    },
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    }
  },
  SearchWMSWageSeekerConfig: {
    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { createdFrom, createdTo } = data;
      if ((createdFrom === "" && createdTo !== "") || (createdFrom !== "" && createdTo === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    preProcess: (data) => {
      data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId() };

      let requestBody = { ...data.body.inbox.moduleSearchCriteria };
      const pathConfig = {
      };
      const dateConfig = {
        createdFrom: "daystart",
        createdTo: "dayend",
      };
      const selectConfig = {
        ward: "ward[0].code",
        name: "name.givenName",
        socialCategoryValue: "socialCategoryValue.code",
      };
      const textConfig = ["name", "individualId"]
      
      let Individual = Object.keys(requestBody)
        .map((key) => {
          if (selectConfig[key]) {
            requestBody[key] = _.get(requestBody, selectConfig[key], null);
          } else if (typeof requestBody[key] == "object") {
            requestBody[key] = requestBody[key]?.code;
          } else if (textConfig?.includes(key)) {
            requestBody[key] = requestBody[key]?.trim()
          }
          return key;
        })
        .filter((key) => requestBody[key])
        .reduce((acc, curr) => {
          if (pathConfig[curr]) {
            _.set(acc, pathConfig[curr], requestBody[curr]);
          } else if (dateConfig[curr] && dateConfig[curr]?.includes("day")) {
            _.set(acc, curr, Digit.Utils.date.convertDateToEpoch(requestBody[curr], dateConfig[curr]));
          } else {
            _.set(acc, curr, requestBody[curr]);
          }
          return acc;
        }, {});

      data.body.inbox.moduleSearchCriteria = { ...data.body.inbox.moduleSearchCriteria ,...Individual, tenantId:Digit.ULBService.getCurrentTenantId()};
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId()
      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result
      switch (key) {
        case "MASTERS_WAGESEEKER_ID":
          return (
            <span className="link">
              <Link to={`/${window.contextPath}/employee/masters/view-wageseeker?tenantId=${row?.businessObject?.tenantId}&individualId=${value}`}>
                 {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
              </Link>
            </span>
          );

        case "MASTERS_SOCIAL_CATEGORY":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(`MASTERS_${value}`))}</span> : t("ES_COMMON_NA");

        case "CORE_COMMON_PROFILE_CITY":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(Digit.Utils.locale.getCityLocale(value)))}</span> : t("ES_COMMON_NA");

        case "MASTERS_WARD":
          return value ? (
            <span style={{ whiteSpace: "nowrap" }}>{String(t(Digit.Utils.locale.getMohallaLocale(value, row?.businessObject.tenantId)))}</span>
          ) : (
            t("ES_COMMON_NA")
          );

        case "MASTERS_LOCALITY":
          return value ? (
            <span style={{ whiteSpace: "break-spaces" }}>{String(t(Digit.Utils.locale.getMohallaLocale(value, row?.businessObject.tenantId)))}</span>
          ) : (
            t("ES_COMMON_NA")
          );
        default:
          return t("ES_COMMON_NA");
      }
    },
    MobileDetailsOnClick: (row, tenantId) => {
      let link;
      Object.keys(row).map((key) => {
        if (key === "MASTERS_WAGESEEKER_ID")
          link = `/${window.contextPath}/employee/masters/view-wageseeker?tenantId=${tenantId}&wageseekerId=${row[key]}`;
      });
      return link;
    },
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    }
  },
  SearchOrganisationConfig: {
    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { createdFrom, createdTo } = data;
      if ((createdFrom === "" && createdTo !== "") || (createdFrom !== "" && createdTo === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    preProcess: (data) => {  
        let requestBody = { ...data.body.SearchCriteria };
        const pathConfig = {
          type: "functions.type",
        };
        const dateConfig = {
          createdFrom: "daystart",
          createdTo: "dayend",
        };
        const selectConfig = {
          boundaryCode: "boundaryCode[0].code",
          type:"type.code",
          applicationStatus: "applicationStatus.code",
        };
        const textConfig = ["name", "orgNumber"]

        let SearchCriteria = Object.keys(requestBody)
          .map((key) => {
            if (selectConfig[key]) {
              requestBody[key] = _.get(requestBody, selectConfig[key], null);
            } else if (typeof requestBody[key] == "object") {
              requestBody[key] = requestBody[key]?.code;
            } else if (textConfig?.includes(key)) {
              requestBody[key] = requestBody[key]?.trim()
            }
            return key;
          })
          .filter((key) => requestBody[key])
          .reduce((acc, curr) => {
            if (pathConfig[curr]) {
              _.set(acc, pathConfig[curr], requestBody[curr]);
            } else if (dateConfig[curr] && dateConfig[curr]?.includes("day")) {
              _.set(acc, curr, Digit.Utils.date.convertDateToEpoch(requestBody[curr], dateConfig[curr]));
            } else {
              _.set(acc, curr, requestBody[curr]);
            }
            return acc;
          }, {});
        data.body.SearchCriteria = { ...SearchCriteria,tenantId:Digit.ULBService.getCurrentTenantId()  };
        return data;
      },
      
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result
      switch (key) {
        case "MASTERS_ORGANISATION_ID":
          return (
            <span className="link">
              <Link to={`/${window.contextPath}/employee/masters/view-organization?tenantId=${row?.tenantId}&orgId=${value}`}>
                 {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
              </Link>
            </span>
          );
        case "MASTERS_ADDRESS":
          return value ? (
            <span style={{ whiteSpace: "break-spaces" }}>
              {String(`${t(Digit.Utils.locale.getCityLocale(row?.tenantId))} ${t(Digit.Utils.locale.getMohallaLocale(value, row?.tenantId))}`)}
            </span>
          ) : (
            t("ES_COMMON_NA")
          );
        case "CORE_COMMON_STATUS":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(`MASTERS_ORG_STATUS_${value}`))}</span> : t("ES_COMMON_NA");

        case "MASTERS_ORGANISATION_TYPE":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(`COMMON_MASTERS_ORG_${value?.split?.('.')?.[0]}`))}</span> : t("ES_COMMON_NA");

        case "MASTERS_ORGANISATION_SUB_TYPE":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(`COMMON_MASTERS_SUBORG_${row?.functions?.[0]?.type?.split?.('.')?.[1]}`))}</span> : t("ES_COMMON_NA");
        default:
          return t("ES_COMMON_NA");
      }
    },
    MobileDetailsOnClick: (row, tenantId) => {
      let link;
      Object.keys(row).map((key) => {
        if (key === "MASTERS_ORGANISATION_ID")
          link = `/${window.contextPath}/employee/masters/view-organization?tenantId=${tenantId}&orgId=${row[key]}`;
      });
      return link;
    },
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    }
  },
  SearchBillWMSConfig: {
    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { createdFrom, createdTo } = data;
      if ((createdFrom === "" && createdTo !== "") || (createdFrom !== "" && createdTo === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    preProcess: (data,defaultValues) => {
      
      
      let requestBody = { ...data.body.inbox.moduleSearchCriteria };
      const dateConfig = {
        createdFrom: "daystart",
        createdTo: "dayend",
      };
      const selectConfig = {
        billType: "billType.code",
        ward: "ward[0].code",
        status: "status[0].code",
      };
      const textConfig = ["projectName", "billNumber"]

      let SearchCriteria = Object.keys(requestBody)
      .map((key) => {
        if (selectConfig[key]) {
          requestBody[key] = _.get(requestBody, selectConfig[key], null);
        } else if (typeof requestBody[key] == "object") {
          requestBody[key] = requestBody[key]?.code;
        } else if (textConfig?.includes(key)) {
          requestBody[key] = requestBody[key]?.trim()
        }
        return key;
      })
      .filter((key) => requestBody[key])
      .reduce((acc, curr) => {
        if (dateConfig[curr] && dateConfig[curr]?.includes("day")) {
          _.set(acc, curr, Digit.Utils.date.convertDateToEpoch(requestBody[curr], dateConfig[curr]));
        } else {
          _.set(acc, curr, requestBody[curr]);
        }
        return acc;
      }, {});
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.moduleSearchCriteria = { ...SearchCriteria,tenantId:Digit.ULBService.getCurrentTenantId()  };

      const presets  = Digit.Hooks.useQueryParams();
      if(Object.keys(presets).length > 0 ) {
        Object.keys(presets).forEach(preset => {
          //if present in defaultValues object then only set it
          if(Object.keys(defaultValues).some(key => key===preset)){
            data.body.inbox.moduleSearchCriteria[preset] = presets[preset]
          }
        })
      }

      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      let tenantId = Digit.ULBService.getCurrentTenantId()
      if (key === "WORKS_BILL_NUMBER") {
        let billType = ""
        const bsPurchaseBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase");
        const bsSupervisionBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.supervision");
        const bsWageBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.wages");
        if(row?.ProcessInstance?.businessService === bsPurchaseBill ){
          billType = "purchase"
        }
        if(row?.ProcessInstance?.businessService === bsSupervisionBill ){
          billType = "supervision"
        }
        if(row?.ProcessInstance?.businessService === bsWageBill ){
          billType = "wage"
        }
        return (
          <span className="link">
            <Link
              to={`/${
                window.contextPath
              }/employee/expenditure/${billType}-bill-details?tenantId=${tenantId}&billNumber=${value}`}
            >
              {String(value ? value : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      }
      if (key === "EXP_BILL_AMOUNT") {
        return <Amount customStyle={{ textAlign: 'right'}} value={value} t={t}></Amount>
      }

      if (key === "WORKS_PROJECT_NAME") {
        return (
          <div class="tooltip">
            <span class="textoverflow" style={{ "--max-width": `${column.maxLength}ch` }}>
              {String(value ? value : t("ES_COMMON_NA"))}
            </span>
            {/* check condtion - if length greater than 20 */}
            <span class="tooltiptext" style={{ whiteSpace: "nowrap" }}>
              {row?.businessObject?.additionalDetails?.projectDesc || t("ES_COMMON_NA")}
            </span>
          </div>
        );
      }

      if(key === "CORE_COMMON_STATUS") {
        return value ? t(`BILL_STATUS_PAYMENT_INITIATED`) : t("ES_COMMON_NA")
      }
      if(key === "ES_COMMON_LOCATION") {
        const location = {
          "ward":value?.ward,
          "locality":value?.locality,
          "city":row?.businessObject?.tenantId
        };
        const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())
        if (location) {
          let locality = location?.locality ? t(`${headerLocale}_ADMIN_${location?.locality}`) : "";
          let ward = location?.ward ? t(`${headerLocale}_ADMIN_${location?.ward}`) : "";
          let city = location?.city ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(location?.city)}`) : "";
          return <p>{`${locality ? locality + ", " : ""}${ward ? ward + ", " : ""}${city}`}</p>;
        }
        return <p>{"NA"}</p>;
      }
      if(key === "WORKS_BILL_TYPE") {
        const headerLocale = Digit.Utils.locale.getTransformedLocale(value)
        return value ? t(`COMMON_MASTERS_BILL_TYPE_${headerLocale}`) : t("ES_COMMON_NA")
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
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    },
    populateReqCriteria: () => {
      const tenantId = Digit.ULBService.getCurrentTenantId();

      return {
        url: "/egov-workflow-v2/egov-wf/businessservice/_search",
        params: { tenantId, businessServices:Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase") },
        body: {},
        config: {
          enabled: true,
          select: (data) => {
            const states =  data?.BusinessServices?.[0]?.states?.filter(state=> state.state)?.map(state=> {
              return {
                "code": state?.state,
                "i18nKey":`WF_${Digit.Utils.locale.getTransformedLocale(Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase"))}_STATUS_${state?.state}`,
                "wfStatus":state?.state
              }
            })
            return states  
          },
        },
      };
    },
    selectionHandler: async (selectedRows,t) => {

      /// here do expense calc search and get the response and send the list of bills to getCreatePaymentPayload
        const ids = selectedRows?.map(row=> row?.original?.businessObject?.id)
        
        const result = await Digit.WorksService.searchBill({
          "billCriteria": {
            "tenantId": Digit.ULBService.getCurrentTenantId(),
            ids,
            // "businessService":[bsPurchaseBill,bsWageBill,bsSupervisionBill]
            // "businessService":bsPurchaseBill
          },
           "pagination": {
            "limit": 50,
            "offSet": 0,
            "sortBy": "ASC",
            "order": "ASC"
          }
        })
  
        const payload = getCreatePaymentPayload(result.bills);
        let responseToReturn = { isSuccess: true, label: "BILL_STATUS_PAYMENT_INITIATED_TOAST"}
        try {
          const response = await Digit.ExpenseService.createPayment(payload);
          responseToReturn.label=`${t(responseToReturn?.label)} ${response?.payments?.[0]?.paymentNumber}`
          return responseToReturn
        } catch (error) {
          responseToReturn.isSuccess = false
          responseToReturn.label = t("BILL_STATUS_PAYMENT_FAILED")
          return responseToReturn
        }
      }
  },
  CreatePAWMSConfig:{
    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { createdFrom, createdTo } = data;
      if ((createdFrom === "" && createdTo !== "") || (createdFrom !== "" && createdTo === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    preProcess: (data,defaultValues) => {
      let requestBody = { ...data.body.inbox.moduleSearchCriteria };
      const dateConfig = {
        createdFrom: "daystart",
        createdTo: "dayend",
      };
      const selectConfig = {
        billType: "billType.code",
        ward: "ward[0].code",
        status: "status[0].code",
      };
      const textConfig = ["projectName", "billNumber"]

      let SearchCriteria = Object.keys(requestBody)
      .map((key) => {
        if (selectConfig[key]) {
          requestBody[key] = _.get(requestBody, selectConfig[key], null);
        } else if (typeof requestBody[key] == "object") {
          requestBody[key] = requestBody[key]?.code;
        } else if (textConfig?.includes(key)) {
          requestBody[key] = requestBody[key]?.trim()
        }
        return key;
      })
      .filter((key) => requestBody[key])
      .reduce((acc, curr) => {
        if (dateConfig[curr] && dateConfig[curr]?.includes("day")) {
          _.set(acc, curr, Digit.Utils.date.convertDateToEpoch(requestBody[curr], dateConfig[curr]));
        } else {
          _.set(acc, curr, requestBody[curr]);
        }
        return acc;
      }, {});
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.moduleSearchCriteria = { ...SearchCriteria,tenantId:Digit.ULBService.getCurrentTenantId()  };

      const presets  = Digit.Hooks.useQueryParams();
      if(Object.keys(presets).length > 0 ) {
        Object.keys(presets).forEach(preset => {
          //if present in defaultValues object then only set it
          if(Object.keys(defaultValues).some(key => key===preset)){
            data.body.inbox.moduleSearchCriteria[preset] = presets[preset]
          }
        })
      }

      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      let tenantId = Digit.ULBService.getCurrentTenantId()
      if (key === "WORKS_BILL_NUMBER") {
        let billType = ""
        const bsPurchaseBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase");
        const bsSupervisionBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.supervision");
        const bsWageBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.wages");
        if(row?.ProcessInstance?.businessService === bsPurchaseBill ){
          billType = "purchase"
        }
        if(row?.ProcessInstance?.businessService === bsSupervisionBill ){
          billType = "supervision"
        }
        if(row?.ProcessInstance?.businessService === bsWageBill ){
          billType = "wage"
        }
        return (
          <span className="link">
            <Link
              to={`/${
                window.contextPath
              }/employee/expenditure/${billType}-bill-details?tenantId=${tenantId}&billNumber=${value}`}
            >
              {String(value ? value : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      }
      if (key === "EXP_BILL_AMOUNT") {
        return <Amount customStyle={{ textAlign: 'right'}} value={value} t={t}></Amount>
      }

      if (key === "WORKS_PROJECT_NAME") {
        return (
          <div class="tooltip">
            <span class="textoverflow" style={{ "--max-width": `${column.maxLength}ch` }}>
              {String(value ? value : t("ES_COMMON_NA"))}
            </span>
            {/* check condtion - if length greater than 20 */}
            <span class="tooltiptext" style={{ whiteSpace: "nowrap" }}>
              {row?.businessObject?.additionalDetails?.projectDesc || t("ES_COMMON_NA")}
            </span>
          </div>
        );
      }

      if(key === "CORE_COMMON_STATUS") {
        return value ? t(`BILL_STATUS_PAYMENT_INITIATED`) : t("ES_COMMON_NA")
      }
      if(key === "ES_COMMON_LOCATION") {
        const location = {
          "ward":value?.ward,
          "locality":value?.locality,
          "city":row?.businessObject?.tenantId
        };
        const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())
        if (location) {
          let locality = location?.locality ? t(`${headerLocale}_ADMIN_${location?.locality}`) : "";
          let ward = location?.ward ? t(`${headerLocale}_ADMIN_${location?.ward}`) : "";
          let city = location?.city ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(location?.city)}`) : "";
          return <p>{`${locality ? locality + ", " : ""}${ward ? ward + ", " : ""}${city}`}</p>;
        }
        return <p>{"NA"}</p>;
      }
      if(key === "WORKS_BILL_TYPE") {
        const headerLocale = Digit.Utils.locale.getTransformedLocale(value)
        return value ? t(`COMMON_MASTERS_BILL_TYPE_${headerLocale}`) : t("ES_COMMON_NA")
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
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    },
    populateReqCriteria: () => {
      const tenantId = Digit.ULBService.getCurrentTenantId();

      return {
        url: "/egov-workflow-v2/egov-wf/businessservice/_search",
        params: { tenantId, businessServices:Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase") },
        body: {},
        config: {
          enabled: true,
          select: (data) => {
            const states =  data?.BusinessServices?.[0]?.states?.filter(state=> state.state)?.map(state=> {
              return {
                "code": state?.state,
                "i18nKey":`WF_${Digit.Utils.locale.getTransformedLocale(Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase"))}_STATUS_${state?.state}`,
                "wfStatus":state?.state
              }
            })
            return states  
          },
        },
      };
    },
    selectionHandler: async (selectedRows,t) => {

      /// here do expense calc search and get the response and send the list of bills to getCreatePaymentPayload
        const ids = selectedRows?.map(row=> row?.original?.businessObject?.id)
        
        const result = await Digit.WorksService.searchBill({
          "billCriteria": {
            "tenantId": Digit.ULBService.getCurrentTenantId(),
            ids,
            // "businessService":[bsPurchaseBill,bsWageBill,bsSupervisionBill]
            // "businessService":bsPurchaseBill
          },
           "pagination": {
            "limit": 50,
            "offSet": 0,
            "sortBy": "ASC",
            "order": "ASC"
          }
        })
        
        // const payload = getCreatePaymentPayload(result.bills);
        //Updated this code to call create n number of times(one for every bill)
        let responseToReturn = { isSuccess: true, label: "BILL_STATUS_PAYMENT_INITIATED_TOAST"}
        let statuses = []
        for(let i=0;i<result.bills.length;i++){
          try {
            const payload = getCreatePaymentPayload([result.bills?.[i]]);
            const response = await Digit.ExpenseService.createPayment(payload);
            // responseToReturn.label=`${t(responseToReturn?.label)} ${response?.payments?.[0]?.paymentNumber}`
            statuses.push([result.bills?.[i]?.billNumber,"success",response?.payments?.[0]?.paymentNumber])
            // return responseToReturn
          } catch (error) {
            // responseToReturn.isSuccess = false
            // responseToReturn.label = t("BILL_STATUS_PAYMENT_FAILED")
            statuses.push([result.bills?.[i]?.billNumber,"failed"])
            // return responseToReturn
          }
        }
        
        let atleastOnePaymentSuccess = statuses?.some(status => status?.[1]==="success")
        responseToReturn.isSuccess = atleastOnePaymentSuccess ? true : false
        let initiatedBills = ""
        let failedBills = ""
        statuses?.forEach(status => {
          if(status[1]==="success") initiatedBills += `${status[0]}, `
          else failedBills += `${status[0]}, `
        })
        const returnLabel = `${t("BILL_STATUS_PAYMENT_INITIATED")}:${initiatedBills} ${t("BILL_STATUS_PAYMENT_FAILED")}:${failedBills}`
        responseToReturn.label = returnLabel
        return responseToReturn
      },
  }
  ,
  SearchPIWMS:{
    customValidationCheck: (data) => {
      //checking both to and from date are present
      
      const { createdFrom, createdTo } = data;
      if ((createdFrom === "" && createdTo !== "") || (createdFrom !== "" && createdTo === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    preProcess: (data,defaultValues,nav) => {
      
      let requestBody = { ...data.body.inbox.moduleSearchCriteria };
      const dateConfig = {
        createdFrom: "daystart",
        createdTo: "dayend",
      };
      const selectConfig = {
        // billType: "billType.code",
        // ward: "ward[0].code",
        status: "status.code",
        piType:"piType.code"
      };
      const textConfig = ["billNumber","jitBillNo"]

      let SearchCriteria = Object.keys(requestBody)
      .map((key) => {
        if (selectConfig[key]) {
          requestBody[key] = _.get(requestBody, selectConfig[key], null);
        } else if (typeof requestBody[key] == "object") {
          requestBody[key] = requestBody[key]?.code;
        } else if (textConfig?.includes(key)) {
          requestBody[key] = requestBody[key]?.trim()
        }
        return key;
      })
      .filter((key) => requestBody[key])
      .reduce((acc, curr) => {
        if (dateConfig[curr] && dateConfig[curr]?.includes("day")) {
          _.set(acc, curr, Digit.Utils.date.convertDateToEpoch(requestBody[curr], dateConfig[curr]));
        } else {
          _.set(acc, curr, requestBody[curr]);
        }
        return acc;
      }, {});
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.moduleSearchCriteria = { ...SearchCriteria,tenantId:Digit.ULBService.getCurrentTenantId()  };

      const presets  = Digit.Hooks.useQueryParams();
      if(Object.keys(presets).length > 0 ) {
        Object.keys(presets).forEach(preset => {
          //if present in defaultValues object then only set it
          if(Object.keys(defaultValues).some(key => key===preset)){
            data.body.inbox.moduleSearchCriteria[preset] = presets[preset]
          }
        })
      }

      
      if(nav === "Pending for action"){
        data.body.inbox.moduleSearchCriteria.status = ["PARTIAL","FAILED"]
      }

      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      let tenantId = Digit.ULBService.getCurrentTenantId()
      let numSuccess = 0
      let numFailed = 0
      row?.businessObject?.beneficiaryDetails?.forEach(bene => {
        if(bene?.paymentStatus === "Payment Successful"){
          numSuccess +=1
        }else if(bene?.paymentStatus === "Payment Failed"){
          numFailed += 1
        }
      })
      if (key === "EXP_PI_ID") {
        return (
          <span className="link">
            <Link
              // to={`/${
              //   window.contextPath
              // }/employee/expenditure/view-payment-instruction?tenantId=${tenantId}&piNumber=${value}`}
              to={`/${
                window.contextPath
              }/employee/expenditure/view-payment?tenantId=${tenantId}&paymentNumber=${row?.businessObject?.muktaReferenceId}`}
            >
              {String(value ? value : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      }
      if (key === "EXP_PI_DATE") {
        return Digit.DateUtils.ConvertEpochToDate(value)
        // return <Amount customStyle={{ textAlign: 'right'}} value={value} t={t}></Amount>
      }

      // if (key === "WORKS_PROJECT_NAME") {
      //   return (
      //     <div class="tooltip">
      //       <span class="textoverflow" style={{ "--max-width": `${column.maxLength}ch` }}>
      //         {String(value ? value : t("ES_COMMON_NA"))}
      //       </span>
      //       {/* check condtion - if length greater than 20 */}
      //       <span class="tooltiptext" style={{ whiteSpace: "nowrap" }}>
      //         {row?.businessObject?.additionalDetails?.projectDesc || t("ES_COMMON_NA")}
      //       </span>
      //     </div>
      //   );
      // }

      if(key === "EXP_NO_SUCC_PAYMENTS") {
        return numSuccess
      }
      if(key === "EXP_NO_FAIL_PAYMENTS") {
        return numFailed
      }

      if(key === "CORE_COMMON_STATUS") {
        return t(Digit.Utils.locale.getTransformedLocale(`EXP_PI_STATUS_${value}`))
      }
      
      if(key === "ES_COMMON_TOTAL_AMOUNT") {
       return <Amount customStyle={{ textAlign: 'right'}} value={value} t={t}></Amount>
      }
      else{
        return t("ES_COMMON_NA")

      }
    },
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    },
  },
  SearchPaymentInstruction: {
    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { createdFrom, createdTo } = data;
      if ((createdFrom === "" && createdTo !== "") || (createdFrom !== "" && createdTo === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    preProcess: (data,defaultValues) => {
      
      let requestBody = { ...data.body.paymentCriteria };
      const dateConfig = {
        createdFrom: "daystart",
        createdTo: "dayend",
      };
      const selectConfig = {
        billType: "billType.code",
        ward: "ward[0].code",
        status: "status[0].code",
      };
      const textConfig = ["projectName", "billNumber"]

      let SearchCriteria = Object.keys(requestBody)
      .map((key) => {
        if (selectConfig[key]) {
          requestBody[key] = _.get(requestBody, selectConfig[key], null);
        } else if (typeof requestBody[key] == "object") {
          requestBody[key] = requestBody[key]?.code;
        } else if (textConfig?.includes(key)) {
          requestBody[key] = requestBody[key]?.trim()
        }
        return key;
      })
      .filter((key) => requestBody[key])
      .reduce((acc, curr) => {
        if (dateConfig[curr] && dateConfig[curr]?.includes("day")) {
          _.set(acc, curr, Digit.Utils.date.convertDateToEpoch(requestBody[curr], dateConfig[curr]));
        } else {
          _.set(acc, curr, requestBody[curr]);
        }
        return acc;
      }, {});
      data.body.paymentCriteria.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.paymentCriteria = { ...SearchCriteria,tenantId:Digit.ULBService.getCurrentTenantId()  };
      
      //added for testing(to have some results)
      data.body.paymentCriteria.status = "INITIATED"

      const presets  = Digit.Hooks.useQueryParams();
      if(Object.keys(presets).length > 0 ) {
        Object.keys(presets).forEach(preset => {
          //if present in defaultValues object then only set it
          if(Object.keys(defaultValues).some(key => key===preset)){
            data.body.paymentCriteria[preset] = presets[preset]
          }
        })
      }

      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      let tenantId = Digit.ULBService.getCurrentTenantId()
      if (key === "WORKS_BILL_NUMBER") {
        let billType = ""
        const bsPurchaseBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase");
        const bsSupervisionBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.supervision");
        const bsWageBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.wages");
        if(row?.ProcessInstance?.businessService === bsPurchaseBill ){
          billType = "purchase"
        }
        if(row?.ProcessInstance?.businessService === bsSupervisionBill ){
          billType = "supervision"
        }
        if(row?.ProcessInstance?.businessService === bsWageBill ){
          billType = "wage"
        }
        return (
          <span className="link">
            <Link
              to={`/${
                window.contextPath
              }/employee/expenditure/${billType}-bill-details?tenantId=${tenantId}&billNumber=${value}`}
            >
              {String(value ? value : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      }
      if (key === "EXP_BILL_AMOUNT") {
        return <Amount customStyle={{ textAlign: 'right'}} value={value} t={t}></Amount>
      }

      if (key === "WORKS_PROJECT_NAME") {
        return (
          <div class="tooltip">
            <span class="textoverflow" style={{ "--max-width": `${column.maxLength}ch` }}>
              {String(value ? value : t("ES_COMMON_NA"))}
            </span>
            {/* check condtion - if length greater than 20 */}
            <span class="tooltiptext" style={{ whiteSpace: "nowrap" }}>
              {row?.businessObject?.additionalDetails?.projectDesc || t("ES_COMMON_NA")}
            </span>
          </div>
        );
      }

      if(key === "CORE_COMMON_STATUS") {
        return value ? t(`BILL_STATUS_PAYMENT_INITIATED`) : t("ES_COMMON_NA")
      }
      if(key === "ES_COMMON_LOCATION") {
        const location = {
          "ward":value?.ward,
          "locality":value?.locality,
          "city":row?.businessObject?.tenantId
        };
        const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())
        if (location) {
          let locality = location?.locality ? t(`${headerLocale}_ADMIN_${location?.locality}`) : "";
          let ward = location?.ward ? t(`${headerLocale}_ADMIN_${location?.ward}`) : "";
          let city = location?.city ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(location?.city)}`) : "";
          return <p>{`${locality ? locality + ", " : ""}${ward ? ward + ", " : ""}${city}`}</p>;
        }
        return <p>{"NA"}</p>;
      }
      if(key === "WORKS_BILL_TYPE") {
        const headerLocale = Digit.Utils.locale.getTransformedLocale(value)
        return value ? t(`COMMON_MASTERS_BILL_TYPE_${headerLocale}`) : t("ES_COMMON_NA")
      }
    },
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    },

  },
  SearchBillConfig: {
    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { createdFrom, createdTo } = data;
      if ((createdFrom === "" && createdTo !== "") || (createdFrom !== "" && createdTo === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    preProcess: (data) => {
      
      const createdFromDate = Digit.Utils.pt.convertDateToEpoch(data?.body?.searchCriteria?.createdFrom,"daystart");
      delete data?.body?.searchCriteria?.createdFrom
      // if(createdFromDate) data.body.searchCriteria.createdFrom = createdFromDate
      const createdToDate = Digit.Utils.pt.convertDateToEpoch(data?.body?.searchCriteria?.createdTo);
      delete data?.body?.searchCriteria?.createdTo

      // if(createdToDate) data.body.searchCriteria.createdTo = createdToDate
      
      const status = data?.body?.searchCriteria?.status?.[0]?.code
      delete data?.body?.searchCriteria?.status
      // if(status){
      //   data.body.searchCriteria.status = status
      // }

      const billType = data?.body?.searchCriteria?.billType?.code;
      delete data?.body?.searchCriteria?.billType
      delete data?.body?.searchCriteria?.billTypes
      if(billType) data.body.searchCriteria.billTypes = [billType]

      // const ward =  data?.body?.searchCriteria?.ward?.[0]?.code
      delete data?.body?.searchCriteria?.ward
      // if(ward) data.body.searchCriteria.ward = ward

      const billNumber = data?.body?.searchCriteria?.billNumber?.trim()
      delete data?.body?.searchCriteria?.billNumber
      delete data?.body?.searchCriteria?.billNumbers
      if(billNumber) data.body.searchCriteria.billNumbers = [billNumber]
    
      const projectNumber = data?.body?.searchCriteria?.projectNumber?.trim()
      delete data?.body?.searchCriteria?.projectNumber
      if(projectNumber) data.body.searchCriteria.projectNumbers = [projectNumber]

      data.body.searchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();
      delete data.body.searchCriteria.ward
      
      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      let tenantId = Digit.ULBService.getCurrentTenantId()
      if (key === "WORKS_BILL_NUMBER") {
        let billType = ""
        const bsPurchaseBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase");
        const bsSupervisionBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.supervision");
        const bsWageBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.wages");
        if(row?.bill?.businessService === bsPurchaseBill ){
          billType = "purchase"
        }
        if(row?.bill?.businessService === bsSupervisionBill ){
          billType = "supervision"
        }
        if(row?.bill?.businessService === bsWageBill ){
          billType = "wage"
        }
        return (
          <span className="link">
            <Link
              to={`/${
                window.contextPath
              }/employee/expenditure/${billType}-bill-details?tenantId=${tenantId}&billNumber=${value}`}
            >
              {String(value ? value : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      }
      if (key === "WORKS_BILL_TYPE") {
        return value ? t(`COMMON_MASTERS_BILL_TYPE_${Digit.Utils.locale.getTransformedLocale(value)}`) : t("ES_COMMON_NA")
      }
      if (key === "EXP_BILL_AMOUNT") {
        return <Amount customStyle={{ textAlign: 'right'}} value={value} t={t}></Amount>
      }
      if(key === "CORE_COMMON_STATUS") {
        return value ? t(Digit.Utils.locale.getTransformedLocale(`WF_${row?.bill?.businessService}_STATUS_${value}`)) : t("ES_COMMON_NA")
      }
      if(key === "ES_COMMON_LOCATION") {
        const location = value;
        const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)
        return location ? t(`TENANT_TENANTS_${headerLocale}`) : t("ES_COMMON_NA")
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
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    },
    populateReqCriteria: () => {
      const tenantId = Digit.ULBService.getCurrentTenantId();

      return {
        url: "/egov-workflow-v2/egov-wf/businessservice/_search",
        params: { tenantId, businessServices:Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase") },
        body: {},
        config: {
          enabled: true,
          select: (data) => {
            const states =  data?.BusinessServices?.[0]?.states?.filter(state=> state.state)?.map(state=> {
              return {
                "code": state?.state,
                "i18nKey":`WF_${Digit.Utils.locale.getTransformedLocale(Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase"))}_STATUS_${state?.state}`,
                "wfStatus":state?.state
              }
            })
            return states  
          },
        },
      };
    },
    selectionHandler: async (selectedRows,t) => {
      const payload = getCreatePaymentPayload(selectedRows,t);
      let responseToReturn = { isSuccess: true, label: "BILL_STATUS_PAYMENT_SUCCESS"}
      try {
        const response = await Digit.PaymentService.createPayment(payload);
        responseToReturn.label=`${t(responseToReturn?.label)}  : ${response?.payments?.[0]?.paymentNumber}`
        return responseToReturn
      } catch (error) {
        responseToReturn.isSuccess = false
        responseToReturn.label = t("BILL_STATUS_PAYMENT_FAILED")
        return responseToReturn
      }
    }
  },
  SearchExpenseBillConfig: {
    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { createdFrom, createdTo } = data;
      if ((createdFrom === "" && createdTo !== "") || (createdFrom !== "" && createdTo === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    preProcess: (data) => {
      const createdFromDate = Digit.Utils.pt.convertDateToEpoch(data?.body?.billCriteria?.createdFrom,"daystart");
      if(createdFromDate) data.body.billCriteria.createdFrom = createdFromDate
      const createdToDate = Digit.Utils.pt.convertDateToEpoch(data?.body?.billCriteria?.createdTo);
      if(createdToDate) data.body.billCriteria.createdTo = createdToDate

      const status = data?.body?.billCriteria?.status?.[0]?.code
      delete data?.body?.billCriteria?.status
      if(status){
        data.body.billCriteria.status = status
      }

      const billType = data?.body?.billCriteria?.billType?.code;
      delete data?.body?.billCriteria?.billType
      if(billType) data.body.billCriteria.businessService = billType

      const ward =  data?.body?.billCriteria?.ward?.[0]?.code
      delete data?.body?.billCriteria?.ward
      if(ward) data.body.billCriteria.ward = ward

      const billNumber = data?.body?.billCriteria?.billNumber?.trim()
      delete data?.body?.billCriteria?.billNumber
      delete data?.body?.billCriteria?.billNumbers
      if(billNumber) data.body.billCriteria.billNumbers = [billNumber]
      
      data.body.billCriteria.tenantId = Digit.ULBService.getCurrentTenantId();
      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      if (key === "WORKS_BILL_NUMBER") {
        const billType = getBillType(row?.businessService)
        return (
          <span className="link">
            <Link
              to={`/${
                window.contextPath
              }/employee/expenditure/${billType}-bill-details?tenantId=${row?.tenantId}&billNumber=${value}`}
            >
              {String(value ? value : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      }
      if (key === "WORKS_BILL_TYPE") {
        return value ? `COMMON_MASTERS_BILL_TYPE_${Digit.Utils.locale.getTransformedLocale(value)}` : t("ES_COMMON_NA")
      }
      if (key === "EXP_BILL_AMOUNT") {
        return <Amount customStyle={{ textAlign: 'right'}} value={value} t={t}></Amount>
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
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
    },
    populateReqCriteria: () => {
      const tenantId = Digit.ULBService.getCurrentTenantId();

      return {
        url: "/egov-workflow-v2/egov-wf/businessservice/_search",
        params: { tenantId, businessServices:Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase") },
        body: {},
        config: {
          enabled: true,
          select: (data) => {
            const states =  data?.BusinessServices?.[0]?.states?.filter(state=> state.state)?.map(state=> {
              return {
                "code": state?.state,
                "i18nKey":`WF_${Digit.Utils.locale.getTransformedLocale(Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase"))}_STATUS_${state?.state}`,
                "wfStatus":state?.state
              }
            })
            return states  
          },
        },
      };
    },
    selectionHandler: async (selectedRows) => {
      const payload = getCreatePaymentPayload(selectedRows);
      let responseToReturn = { isSuccess: true, label: "BILL_STATUS_PAYMENT_SUCCESS"}
      try {
        const response = await Digit.PaymentService.createPayment(payload);
        return responseToReturn
      } catch (error) {
        responseToReturn.isSuccess = false
        responseToReturn.label = t("BILL_STATUS_PAYMENT_FAILED")
        return responseToReturn
      }
    }
  },
  DownloadBillConfig: {
    preProcess: (data) => {
      data.params.tenantId = Digit.ULBService.getCurrentTenantId();
      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      if(key === "WORKS_SNO"){
        return <span>{searchResult?.findIndex(e=>e?.id==row?.id)+1}</span>
      }
      if (key === "ES_COMMON_TOTAL_AMOUNT") {
        return <Amount customStyle={{ textAlign: 'right'}} value={value} t={t}></Amount>
      }
      if(key === "CORE_COMMON_STATUS") {
        return t("BILL_STATUS_PAYMENT_INITIATED")
      }
      if(key === "ES_COMMON_DATE") {
        return value ? Digit.DateUtils.ConvertTimestampToDate(parseInt(value), "dd/MM/yyyy") : t("ES_COMMON_NA")
      }
      if(key === "CS_COMMON_ACTION") {
        switch (row?.status) {
          case "COMPLETED":
            return value ? (
              <LinkLabel
                onClick={async () => {
                  let photo = "";
                  try {
                    photo = value && (await Digit.UploadServices.Filefetch([value], Digit.ULBService.getCurrentTenantId()));
                    const imageLink = photo?.data?.fileStoreIds?.[0]?.url;
                    downloadPdf(imageLink);
                    const paySearchResponse =
                      row?.paymentNumber &&
                      (await Digit.ExpenseService.searchPA({
                        paymentCriteria: {
                          tenantId: row?.tenantId,
                          paymentNumbers: [row?.paymentNumber],
                        },
                      }));
                    if (paySearchResponse && paySearchResponse?.payments?.[0]) {
                      const payUpdateResponse = await Digit.ExpenseService.updatePayment(getUpdatePaymentPayload(paySearchResponse?.payments?.[0]));
                    }
                  } catch (error) {
                    console.error(error, "downloaderror");
                  }
                }}
              >
                {t("CS_COMMON_DOWNLOAD")}
              </LinkLabel>
            ) : <RetryComponent row={row} t={t}/>;
          case "INPROGRESS":
            return t("CS_COMMON_NA");
          case "FAILED":
            return <RetryComponent row={row} t={t}/>
          default:
            return t("CS_COMMON_NA");
        }
       
        
      }
    }
  },
  BillInboxConfig:{
    preProcess: (data) => {
      
      //set tenantId
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.processSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();

      const billNumber = data?.body?.inbox?.moduleSearchCriteria?.billNumber?.trim()
      if(billNumber) data.body.inbox.moduleSearchCriteria.billNumber = billNumber

      const projectId = data?.body?.inbox?.moduleSearchCriteria?.projectId?.trim()
      if(projectId) data.body.inbox.moduleSearchCriteria.projectId = projectId

      const referenceId = data?.body?.inbox?.moduleSearchCriteria?.referenceId?.trim()
      if(referenceId) data.body.inbox.moduleSearchCriteria.referenceId = referenceId;
      // deleting them for now(assignee-> need clarity from pintu,ward-> static for now,not implemented BE side)

      const assignee = _.clone(data.body.inbox.moduleSearchCriteria.assignee);
      delete data.body.inbox.moduleSearchCriteria.assignee;
      if (assignee?.code === "ASSIGNED_TO_ME") {
        data.body.inbox.moduleSearchCriteria.assignee = Digit.UserService.getUser().info.uuid;
      }

      
      let ward = _.clone(data.body.inbox.moduleSearchCriteria.ward ? data.body.inbox.moduleSearchCriteria.ward : []);
      delete data.body.inbox.moduleSearchCriteria.ward;
      ward = ward?.map((row) => row?.code)?.filter(row=>row);
      if (ward.length > 0) data.body.inbox.moduleSearchCriteria.ward = ward;

      //cloning locality and workflow states to format them
      let locality = _.clone(data.body.inbox.moduleSearchCriteria.locality ? data.body.inbox.moduleSearchCriteria.locality : []);
      
      let states = _.clone(data.body.inbox.moduleSearchCriteria.state ? data.body.inbox.moduleSearchCriteria.state : []);
      delete data.body.inbox.moduleSearchCriteria.locality;
      delete data.body.inbox.moduleSearchCriteria.state;
      delete data.body.inbox.moduleSearchCriteria.status;
      locality = locality?.map((row) => row?.code)?.filter(row=>row);
      states = Object.keys(states)?.filter((key) => states[key]);

      //adding formatted data to these keys
      if (locality.length > 0) data.body.inbox.moduleSearchCriteria.locality = locality;
      if (states.length > 0) data.body.inbox.moduleSearchCriteria.status = states;

      const billType = _.clone(data.body.inbox.moduleSearchCriteria.billType ? data.body.inbox.moduleSearchCriteria.billType : {});
      delete data.body.inbox.moduleSearchCriteria.billType
      const bsPurchaseBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase");
      const bsWageBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.wages");
      const bsSupervisionBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.supervision");
      
      if (billType?.code) data.body.inbox.processSearchCriteria.businessService = [billType.code];
      else data.body.inbox.processSearchCriteria.businessService = [
        bsPurchaseBill,
        bsSupervisionBill,
        bsWageBill
      ]
      //adding tenantId to moduleSearchCriteria
      data.body.inbox.moduleSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();

      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      
      switch(key){
         case "WORKS_BILL_NUMBER":
          let billType = ""
          const bsPurchaseBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase");
          const bsSupervisionBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.supervision");
          const bsWageBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.wages");
          if(row.ProcessInstance.businessService === bsPurchaseBill ){
            billType = "purchase"
          }
          if(row.ProcessInstance.businessService === bsSupervisionBill ){
            billType = "supervision"
          }
          if(row.ProcessInstance.businessService === bsWageBill ){
            billType = "wage"
          }
          return (
            <span className="link">
              <Link
                to={`/${
                  window.contextPath
                }/employee/expenditure/${billType}-bill-details?tenantId=${row.businessObject.tenantId}&billNumber=${value}`}
              >
                {String(value ? value : t("ES_COMMON_NA"))}
              </Link>
            </span>
          );
          
         case "COMMON_WORKFLOW_STATES":
          return <span>{t(Digit.Utils.locale.getTransformedLocale(`WF_${row.ProcessInstance.businessService}_STATE_${value}`))}</span>;

         case "ES_COMMON_AMOUNT":
          return <Amount customStyle={{ textAlign: 'right'}} value={value} t={t}></Amount>

         case "COMMON_SLA_DAYS":
          return value > 0 ? <span className="sla-cell-success">{value}</span> : <span className="sla-cell-error">{value}</span>;
        
         default:
          return t("ES_COMMON_NA");
      }
     },
    MobileDetailsOnClick: (row, tenantId) => {
      let link;
      Object.keys(row).map((key) => {
        if (key === "ESTIMATE_ESTIMATE_NO")
          link = `/${window.contextPath}/employee/estimate/estimate-details?tenantId=${tenantId}&estimateNumber=${
            row[key]
          }`;
      });
      return link;
    },
  }
};

const downloadPdf = (link, openIn = "_blank") => {
  var win = window.open(link, openIn);
  if (win) {
    win.focus();
  }
};