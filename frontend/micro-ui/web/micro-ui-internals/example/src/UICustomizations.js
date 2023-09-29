import React from "react";
import { Link } from "react-router-dom";
import _ from "lodash";
import { Amount } from "@egovernments/digit-ui-react-components";

//create functions here based on module name set in mdms(eg->SearchProjectConfig)
//how to call these -> Digit?.Customizations?.[masterName]?.[moduleName]
// these functions will act as middlewares
var Digit = window.Digit || {};

// const businessServiceMap = {
//   estimate: "ESTIMATE",
//   contracts: "CONTRACT",
//   attendencemgmt: "MR",
//   expenditure:{
//     WAGE_BILL:"EXPENSE.WAGE",
//     PURCHASE_BILL:"EXPENSE.PURCHASE",
//     SUPERVISION_BILL:"EXPENSE.SUPERVISION"
//   }
// };

const businessServiceMap = {
  estimate: "ESTIMATE",
  contract: "CONTRACT",
  "muster roll": "MR",
  "works.wages":"EXPENSE.WAGES",
  "works.purchase":"EXPENSE.PURCHASE",
  "works.supervision":"EXPENSE.SUPERVISION",
  revisedWO:"CONTRACT-REVISION",
  measurement : "MB"
};    

const inboxModuleNameMap = {
  "mukta-estimate": "estimate-service",
  "contract-approval-mukta": "contract-service",
  "muster-roll-approval": "muster-roll-service",
};

export const UICustomizations = {
  businessServiceMap,
  updatePayload: (applicationDetails, data, action, businessService) => {
    
    if (businessService === businessServiceMap.estimate) {
      const workflow = {
        comment: data.comments,
        documents: data?.documents?.map((document) => {
          return {
            documentType: action?.action + " DOC",
            fileName: document?.[1]?.file?.name,
            fileStoreId: document?.[1]?.fileStoreId?.fileStoreId,
            documentUid: document?.[1]?.fileStoreId?.fileStoreId,
            tenantId: document?.[1]?.fileStoreId?.tenantId,
          };
        }),
        assignees: data?.assignees?.uuid ? [data?.assignees?.uuid] : null,
        action: action.action,
      };
      //filtering out the data
      Object.keys(workflow).forEach((key, index) => {
        if (!workflow[key] || workflow[key]?.length === 0) delete workflow[key];
      });

      return {
        estimate: applicationDetails,
        workflow,
      };
    }
    if (businessService === businessServiceMap.contract || businessService === businessServiceMap.revisedWO ) {
      const workflow = {
        comment: data?.comments,
        documents: data?.documents?.map((document) => {
          return {
            documentType: action?.action + " DOC",
            fileName: document?.[1]?.file?.name,
            fileStoreId: document?.[1]?.fileStoreId?.fileStoreId,
            documentUid: document?.[1]?.fileStoreId?.fileStoreId,
            tenantId: document?.[1]?.fileStoreId?.tenantId,
          };
        }),
        assignees: data?.assignees?.uuid ? [data?.assignees?.uuid] : null,
        action: action.action,
      };
      //filtering out the data
      Object.keys(workflow).forEach((key, index) => {
        if (!workflow[key] || workflow[key]?.length === 0) delete workflow[key];
      });

      return {
        contract: applicationDetails,
        workflow,
      };
    }
    if (businessService === businessServiceMap?.["muster roll"]) {
      const workflow = {
        comment: data?.comments,
        documents: data?.documents?.map((document) => {
          return {
            documentType: action?.action + " DOC",
            fileName: document?.[1]?.file?.name,
            fileStoreId: document?.[1]?.fileStoreId?.fileStoreId,
            documentUid: document?.[1]?.fileStoreId?.fileStoreId,
            tenantId: document?.[1]?.fileStoreId?.tenantId,
          };
        }),
        assignees: data?.assignees?.uuid ? [data?.assignees?.uuid] : null,
        action: action.action,
      };
      //filtering out the data
      Object.keys(workflow).forEach((key, index) => {
        if (!workflow[key] || workflow[key]?.length === 0) delete workflow[key];
      });

      return {
        musterRoll: applicationDetails,
        workflow,
      };
    }
    if(businessService === businessServiceMap?.["works.purchase"]){
      const workflow = {
        comment: data.comments,
        documents: data?.documents?.map((document) => {
          return {
            documentType: action?.action + " DOC",
            fileName: document?.[1]?.file?.name,
            fileStoreId: document?.[1]?.fileStoreId?.fileStoreId,
            documentUid: document?.[1]?.fileStoreId?.fileStoreId,
            tenantId: document?.[1]?.fileStoreId?.tenantId,
          };
        }),
        assignees: data?.assignees?.uuid ? [data?.assignees?.uuid] : null,
        action: action.action,
      };
      //filtering out the data
      Object.keys(workflow).forEach((key, index) => {
        if (!workflow[key] || workflow[key]?.length === 0) delete workflow[key];
      });

      const additionalFieldsToSet = {
        projectId:applicationDetails.additionalDetails.projectId,
        invoiceDate:applicationDetails.billDate,
        invoiceNumber:applicationDetails.referenceId.split('_')?.[1],
        contractNumber:applicationDetails.referenceId.split('_')?.[0],
        documents:applicationDetails.additionalDetails.documents
      }
      return {
        bill: {...applicationDetails,...additionalFieldsToSet},
        workflow,
      };
    }
    if (businessService === businessServiceMap?.measurement) {
      const workflow = {
        comment: data.comments,
        documents: data?.documents?.map((document) => {
          return {
            documentType: action?.action + " DOC",
            fileName: document?.[1]?.file?.name,
            fileStoreId: document?.[1]?.fileStoreId?.fileStoreId,
            documentUid: document?.[1]?.fileStoreId?.fileStoreId,
            tenantId: document?.[1]?.fileStoreId?.tenantId,
          };
        }),
        assignees: data?.assignees?.uuid ? [data?.assignees?.uuid] : null,
        action: action.action,
      };
      //filtering out the data
      Object.keys(workflow).forEach((key, index) => {
        if (!workflow[key] || workflow[key]?.length === 0) delete workflow[key];
      });
      // ap[0] = {...ap[0]wor:{}}
      applicationDetails[0] = {...applicationDetails[0],"workflow" : workflow}
      return {
        measurements: applicationDetails
      };
    }
  },
  enableModalSubmit:(businessService,action,setModalSubmit,data)=>{
    if(businessService === businessServiceMap?.["muster roll"] && action.action==="APPROVE"){
      setModalSubmit(data?.acceptTerms)
    }
  },
  enableHrmsSearch: (businessService, action) => {
    if (businessService === businessServiceMap.estimate) {
      return action.action.includes("TECHNICALSANCTION") || action.action.includes("VERIFYANDFORWARD");
    }
    if (businessService === businessServiceMap.contract) {
      return action.action.includes("VERIFY_AND_FORWARD");
    }
     if (businessService === businessServiceMap?.["muster roll"]) {
      return action.action.includes("VERIFY");
    }
    if(businessService === businessServiceMap?.["works.purchase"]){
      return action.action.includes("VERIFY_AND_FORWARD")
    }
    if(businessService === businessServiceMap?.["revisedWO"]){
      return action.action.includes("VERIFY_AND_FORWARD")
    }

    return false;
  },
  getBusinessService: (moduleCode) => {
    if (moduleCode?.includes("estimate")) {
      return businessServiceMap?.estimate;
    } else if (moduleCode?.includes("contract")) {
      return businessServiceMap?.contract;
    } else if (moduleCode?.includes("muster roll")) {
      return businessServiceMap?.["muster roll"];
    }
    else if (moduleCode?.includes("works.purchase")) {
      return businessServiceMap?.["works.purchase"];
    }
    else if (moduleCode?.includes("works.wages")) {
      return businessServiceMap?.["works.wages"];
    }
    else if (moduleCode?.includes("works.supervision")) {
      return businessServiceMap?.["works.supervision"];
    }
    else if (moduleCode?.includes("revisedWO")) {
      return businessServiceMap?.["revisedWO"];
    }
    else if (moduleCode?.includes("measurement")) {
      return businessServiceMap?.measurement;
    }
    else {
      return businessServiceMap;
    }
  },
  getInboxModuleName: (moduleCode) => {
    if (moduleCode?.includes("estimate")) {
      return inboxModuleNameMap?.estimate;
    } else if (moduleCode?.includes("contract")) {
      return inboxModuleNameMap?.contracts;
    } else if (moduleCode?.includes("attendence")) {
      return inboxModuleNameMap?.attendencemgmt;
    } else {
      return inboxModuleNameMap;
    }
  },
  SearchProjectConfig: {
    preProcess: (data) => {
      const createdFrom = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdFrom);
      const createdTo = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdTo);
      const projectType = data.body.Projects[0]?.projectType?.code;
      data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), includeAncestors: true, createdFrom, createdTo };
      let name = data.body.Projects[0]?.name;
      name = name?.trim();
      delete data.body.Projects[0]?.createdFrom;
      delete data.body.Projects[0]?.createdTo;
      data.body.Projects[0] = { ...data.body.Projects[0], tenantId: Digit.ULBService.getCurrentTenantId(), projectType, name };

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
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result
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
            <Link to={`/${window.contextPath}/employee/project/project-details?tenantId=${row.tenantId}&projectNumber=${value}`}>
            {String(value ? value : t("ES_COMMON_NA"))}
            </Link>
          </span>
        ) : (
          t("ES_COMMON_NA")
        );
      }

      if (key === "WORKS_PROJECT_NAME") {
        return (
          <div class="tooltip">
            <span class="textoverflow" style={{ "--max-width": `${column?.maxlength}ch` }}>
              {String(t(value))}
            </span>
            {/* check condtion - if length greater than 20 */}
            <span class="tooltiptext" style={{ whiteSpace: "nowrap" }}>
              {String(t(value))}
            </span>
          </div>
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
              to={`/works-ui/employee/attendencemgmt/view-attendance?tenantId=${row?.ProcessInstance.tenantId}&musterRollNumber=${value}`}
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
  SearchEstimateConfig: {
    preProcess: (data) => {
      const fromProposalDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.fromProposalDate);
      const toProposalDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.toProposalDate);
      const department = data?.params?.department?.code;
      data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), fromProposalDate, toProposalDate, department };
      return data;
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result

      const getAmount = (item) => {
        return item.amountDetail.reduce((acc, row) => acc + row.amount, 0);
      };
      if (key === "WORKS_ESTIMATE_ID") {
        return (
          <span className="link">
            <Link
              to={`/${
                window.contextPath
              }/employee/estimate/estimate-details?tenantId=${row?.tenantId}&estimateNumber=${value}`}
            >
               {String(value ? value : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      }
      if (key === "WORKS_ESTIMATED_AMOUNT") {
        return row?.estimateDetails?.reduce((totalAmount, item) => totalAmount + getAmount(item), 0);
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
        if (key === "WORKS_ESTIMATE_ID")
          link = `/${window.contextPath}/employee/estimate/estimate-details?tenantId=${tenantId}&estimateNumber=${row[key]}`;
      });
      return link;
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
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
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
  SearchWageSeekerConfig: {
    preProcess: (data) => {
      // const createdFrom = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdFrom);
      // const createdTo = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdTo);
      data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId() };
      // data.body.Individual = { ...data.body.Individual, tenantId: Digit.ULBService.getCurrentTenantId() };
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
                {value ? value : t("ES_COMMON_NA")}
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
            <span style={{ whiteSpace: "nowrap" }}>{String(t(Digit.Utils.locale.getMohallaLocale(value, row?.tenantId)))}</span>
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
    },
  },




  SearchMeasurementConfig: {
    preProcess: (data) => {

      // console.log(data);
    const mbNumber=data?.body?.Individual?.MBNumber || null;
    const refId= data?.body?.Individual?.MBReference || null;

      data.body.criteria = {
        "tenantId": Digit.ULBService.getCurrentTenantId() ,
        "measurementNumber": mbNumber,
        
      };
      data.body.pagination = {
        "limit": 10,
    "offSet": 0,
    "sortBy": "string",
    "order": "DESC"
      };
      data.body.params = {};
    
      return data;
      
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      // console.log(key,value);
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result
      switch (key) {
        case "MB_REFERENCE_NUMBER":
          return (
            <span className="link">
              <Link to={`/${window.contextPath}/employee/measurement/view?tenantId=${row?.tenantId}&referenceNumber=${value}`}>
                {value ? value : t("ES_COMMON_NA")}
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
            <span style={{ whiteSpace: "nowrap" }}>{String(t(Digit.Utils.locale.getMohallaLocale(value, row?.tenantId)))}</span>
          ) : (
            t("ES_COMMON_NA")
          );
        default:
          return t("NA");
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
    },
  },

  InboxMeasurementConfig: {
    preProcess: (data) => {
      let moduleSearchCriteria = data.body.inbox.moduleSearchCriteria;

      const statusValues = Object.keys(moduleSearchCriteria?.status || {}).filter(key => moduleSearchCriteria.status[key]);
      moduleSearchCriteria = {
        ...(moduleSearchCriteria?.measurementNumber && { measurementNumber: moduleSearchCriteria?.measurementNumber?.trim() }),
        ...(moduleSearchCriteria?.projectType?.code && { projectType: moduleSearchCriteria?.projectType?.code }),
        ...(moduleSearchCriteria?.projectId && { projectId: moduleSearchCriteria?.projectId?.trim() }),
        ...(moduleSearchCriteria?.assignee?.code === "ASSIGNED TO ME" && { assignee: Digit.UserService.getUser().info.uuid }),
        ...(moduleSearchCriteria?.ward?.length > 0 && { ward: moduleSearchCriteria.ward?.map(e => e?.code) }),
        ...(statusValues.length > 0 && { status: statusValues })
      }
        data.body.inbox.moduleSearchCriteria = { ...moduleSearchCriteria };
        data.body.inbox.moduleSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();
        return data;
      },
        additionalCustomizations: (row, key, column, value, t, searchResult) => {

          const tenantId = searchResult[0]?.ProcessInstance?.tenantId;
         
          switch (key) {
            case "MB_REFERENCE_NUMBER":
              const state = row?.ProcessInstance?.state?.state;
              const contractNumber = row?.businessObject?.referenceId
              return (
                <span className="link">
                  {/* {Digit.Utils.statusBasedNavigation(state, contractNumber, value, tenantId, value)} */}
                </span>
              );
            case "MB_ASSIGNEE":
              return value ? <span>{value?.[0]?.name}</span> : <span>{t("NA")}</span>;
            case "MB_WORKFLOW_STATE":
              return <span>{t(value)}</span>;
            case "MB_AMOUNT":
              return <Amount customStyle={{ textAlign: 'right' }} value={Math.round(value)} t={t}></Amount>
            case "MB_SLA_DAYS_REMAINING":
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
                  link = `/${window.contextPath}/employee/estimate/estimate-details?tenantId=${tenantId}&estimateNumber=${row[key]
                    }`;
              });
              return link;
            },
  },   
 
  WMSSearchMeasurementConfig: {

    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { createdFrom, createdTo } = data;
      if ((createdFrom === "" && createdTo !== "") || ( createdFrom!== "" && createdTo === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },

    preProcess: (data) => {
    const mbNumber=data?.body?.inbox?.measurementNumber || null;
    const refId= data?.body?.Individual?.referenceId || null;

  
      return data;
      
    },
    additionalCustomizations: (row, key, column, value, t, searchResult) => {
      // console.log(key,value);
      // console.log(row,"qwertyuiop");
    
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result

      const tenantId = searchResult[0]?.ProcessInstance?.tenantId;

      switch (key) {
        case "MB_NUMBER":
          const state = row?.ProcessInstance?.state?.state;
          const contractNumber = row?.businessObject?.referenceId
          return (
            <span className="link">
              {Digit.Utils.statusBasedNavigation(state , contractNumber, value, tenantId, value)}
            </span>
          );
            case "MB_AMOUNT":
              return value ? <span style={{ whiteSpace: "nowrap" }}>{value}</span> : t("ES_COMMON_NA");
              case "MB_STATUS":
                return <span>{t(value)}</span>;
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
            <span style={{ whiteSpace: "nowrap" }}>{String(t(Digit.Utils.locale.getMohallaLocale(value, row?.tenantId)))}</span>
          ) : (
            t("ES_COMMON_NA")
          );
        default:
          return t("NA");
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
    },
    populateReqCriteria: () => {
      
      const tenantId = Digit.ULBService.getCurrentTenantId();
     

      return {
        url: "/egov-workflow-v2/egov-wf/businessservice/_search",
        params: { tenantId, businessServices: Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("measurement") },
        body: {
         
        },
        config: {
          enabled: true,
          select: (data) => {
            const states =  data?.BusinessServices?.[0]?.states?.filter(state=> state.state)?.map(state=> {
              return {
                // "i18nKey":`${Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("MB")}${state?.state}`,
                "i18nKey":state?.state,

                "wfStatus":state?.state
              }
            })
            return states  
          },
        },
      };
    },
  },





  SearchOrganisationConfig: {
    preProcess: (data) => {
      // const createdFrom = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdFrom);
      // const createdTo = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdTo);
      // data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId() };
      data.body.SearchCriteria = { ...data.body.SearchCriteria, tenantId: Digit.ULBService.getCurrentTenantId() };
      
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
                {value ? value : t("ES_COMMON_NA")}
              </Link>
            </span>
          );
        case "MASTERS_LOCATION":
          return value ? (
            <span style={{ whiteSpace: "break-spaces" }}>
              {String(`${t(Digit.Utils.locale.getCityLocale(row?.tenantId))} ${t(Digit.Utils.locale.getMohallaLocale(value, row?.tenantId))}`)}
            </span>
          ) : (
            t("ES_COMMON_NA")
          );

        case "CORE_COMMON_STATUS":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(`MASTERS_${value}`))}</span> : t("ES_COMMON_NA");

        case "MASTERS_ORGANISATION_TYPE":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(`MASTERS_${value}`))}</span> : t("ES_COMMON_NA");

        case "MASTERS_ORGANISATION_SUB_TYPE":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(`MASTERS_${value}`))}</span> : t("ES_COMMON_NA");
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
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() < new Date(data[keys.end]).getTime() : true;
      }
    },
  },
  EstimateInboxConfig: {},
  BillInboxConfig: {
    preProcess: (data) => {
      const musterRollNumber = data.body.inbox?.moduleSearchCriteria?.billNumber;
      let states = _.clone(data.body.inbox.moduleSearchCriteria.state ? data.body.inbox.moduleSearchCriteria.state : []);
      delete data.body.inbox.moduleSearchCriteria.state;
      states = Object.keys(states)?.filter((key) => states[key]);

      let status;
      if (states.length > 0) status = states;

      data.body.inbox = {
        ...data.body.inbox,
        tenantId: Digit.ULBService.getCurrentTenantId(),
        processSearchCriteria: { ...data.body.inbox.processSearchCriteria, tenantId: Digit.ULBService.getCurrentTenantId() },
        moduleSearchCriteria: { tenantId: Digit.ULBService.getCurrentTenantId(), musterRollNumber, status },
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
            <Link to={`/works-ui/employee/expenditure/view-bill?tenantId=${row?.ProcessInstance.tenantId}&billNumber=${value}`}>
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
};
