import { Link } from "react-router-dom";
import _ from "lodash";
import React from "react";

//create functions here based on module name set in mdms(eg->SearchProjectConfig)
//how to call these -> Digit?.Customizations?.[masterName]?.[moduleName]
// these functions will act as middlewares
var Digit = window.Digit || {};

export const UICustomizations = {
  EstimateInboxConfig: {
    preProcess: (data) => {
      //set tenantId
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.processSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();

      // deleting them for now(assignee-> need clarity from pintu,ward-> static for now,not implemented BE side)

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

      //adding tenantId to moduleSearchCriteria
      data.body.inbox.moduleSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();

      return data;
    },
    additionalCustomizations: (row, column, columnConfig, value, t) => {
      if (column.key === "estimateNumber") {
        return (
          <span className="link">
            <Link to={`/${window.contextPath}/employee/estimate/estimate-details?tenantId=${row.ProcessInstance.tenantId}&estimateNumber=${value}`}>
              {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      } else if (column.key === "assignee") {
        if (value) {
          return <span>{value?.[0]?.name}</span>;
        } else {
          return <span></span>;
        }
      } else if (column.key === "state") {
        return <span>{t(`WF_EST_${value}`)}</span>;
      } else if (column.key === "estimatedAmount") {
        return <span>{value ? Digit.Utils.dss.formatterWithoutRound(value, "number") : t("ES_COMMON_NA")}</span>;
      } else if (column.key === "sla") {
        return value > 0 ? <span className="sla-cell-success">{value}</span> : <span className="sla-cell-error">{value}</span>;
      } else {
        return <div>{t("NA")}</div>;
      }
    },
  },
  AttendanceInboxConfig: {
    preProcess: (data) => {
      
      //set tenantId

      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.processSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();

      // deleting them for now(assignee-> need clarity from pintu,ward-> static for now,not implemented BE side)

      const assignee = _.clone(data.body.inbox.moduleSearchCriteria.assignee);
      delete data.body.inbox.moduleSearchCriteria.assignee;
      if (assignee?.code === "ASSIGNED_TO_ME") {
        data.body.inbox.moduleSearchCriteria.assignee = Digit.UserService.getUser().info.uuid;
      }

      delete data.body.inbox.moduleSearchCriteria.ward;

      //cloning locality and workflow states to format them
      // let locality = _.clone(data.body.inbox.moduleSearchCriteria.locality ? data.body.inbox.moduleSearchCriteria.locality : []);
      
      let selectedOrg =  _.clone(data.body.inbox.moduleSearchCriteria.orgId ? data.body.inbox.moduleSearchCriteria.orgId : null);
      delete data.body.inbox.moduleSearchCriteria.orgId;
      if(selectedOrg) {
         data.body.inbox.moduleSearchCriteria.orgId = selectedOrg?.[0]?.orgNumber;
      }
      let states = _.clone(data.body.inbox.moduleSearchCriteria.state ? data.body.inbox.moduleSearchCriteria.state : []);
      // delete data.body.inbox.moduleSearchCriteria.locality;
      delete data.body.inbox.moduleSearchCriteria.state;
      // locality = locality?.map((row) => row?.code);
      states = Object.keys(states)?.filter((key) => states[key]);

      // //adding formatted data to these keys
      // if (locality.length > 0) data.body.inbox.moduleSearchCriteria.locality = locality;
      if (states.length > 0) data.body.inbox.moduleSearchCriteria.status = states;

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
    additionalCustomizations: (row, column, columnConfig, value, t) => {
      if (column.label === "ATM_MUSTER_ROLL_ID") {
        return (
          <span className="link">
            <Link
              to={`/works-ui/employee/attendencemgmt/view-attendance?tenantId=${Digit.ULBService.getCurrentTenantId()}&musterRollNumber=${value}`}
            >
              {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      }
      if (column.label === "ATM_ATTENDANCE_WEEK") {
        const week = `${Digit.DateUtils.ConvertTimestampToDate(value?.startDate, "dd/MM/yyyy")}-${Digit.DateUtils.ConvertTimestampToDate(
          value?.endDate,
          "dd/MM/yyyy"
        )}`;
        return <div>{week}</div>;
      }
      if (column.label === "ATM_NO_OF_INDIVIDUALS") {
        return <div>{value?.length}</div>;
      }
      if (column.label === "ATM_SLA") {
        return parseInt(value) > 0 ? (
          <span className="sla-cell-success">{t(value) || ""}</span>
        ) : (
          <span className="sla-cell-error">{t(value) || ""}</span>
        );
      }
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
    preProcess: (data) => {
      const fromProposalDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.fromProposalDate);
      const toProposalDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.toProposalDate);
      const projectType = data?.params?.projectType?.code;
      data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), fromProposalDate, toProposalDate, projectType };
      //deleting ward data since this is a static field for now
      delete data?.params?.ward;
      return data;
    },
    additionalCustomizations: (row, column, columnConfig, value, t, searchResult, headerLocale) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result

      const getAmount = (item) => {
        return item.amountDetail.reduce((acc, row) => acc + row.amount, 0);
      };
      if (column.label === "ESTIMATE_ESTIMATE_NO") {
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
      if (column.label === "WORKS_ESTIMATED_AMOUNT") {
        const amt = row?.estimateDetails?.reduce((totalAmount, item) => totalAmount + getAmount(item), 0);
        return amt ? Digit.Utils.dss.formatterWithoutRound(amt, "number") : t("ES_COMMON_NA");
      }
      if (column.label === "ES_COMMON_LOCATION") {
        const location = searchResult?.[0].additionalDetails?.location;
        if (location) {
          let locality = location?.locality ? t(`${headerLocale}_ADMIN_${location?.locality}`) : "";
          let ward = location?.ward ? t(`${headerLocale}_ADMIN_${location?.ward}`) : "";
          let city = location?.city ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(location?.city)}`) : "";
          return <p>{`${locality ? locality + ", " : ""}${ward ? ward + ", " : ""}${city}`}</p>;
        }
        return <p>{"NA"}</p>;
      }
    },
  },
  SearchProjectConfig: {
    preProcess: (data) => {
      const createdFrom = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdFrom);
      const createdTo = Digit.Utils.pt.convertDateToEpoch(data.body.Projects[0]?.createdTo);
      const projectType = data.body.Projects[0]?.projectType?.code;
      const ward = data.body.Projects[0]?.ward?.[0]?.code;
      data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), includeAncestors: true, createdFrom, createdTo };
      let name = data.body.Projects[0]?.name;
      name = name?.trim();
      delete data.body.Projects[0]?.createdFrom;
      delete data.body.Projects[0]?.createdTo;
      data.body.Projects[0] = { ...data.body.Projects[0], tenantId: Digit.ULBService.getCurrentTenantId(), projectType, name, address : { boundary : ward}  };

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
    additionalCustomizations: (row, column, columnConfig, value, t, searchResult, headerLocale) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result

      if (column.label === "WORKS_PROJECT_ID") {
        return (
          <span className="link">
            <Link to={`/${window.contextPath}/employee/project/project-details?tenantId=${row.tenantId}&projectNumber=${value}`}>
              {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      }

      if (column.label === "WORKS_PARENT_PROJECT_ID") {
        return value ? (
          <span className="link">
            <Link to={`/${window.contextPath}/employee/project/project-details?tenantId=${row.tenantId}&projectNumber=${value}`}>
              {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
            </Link>
          </span>
        ) : (
          t("ES_COMMON_NA")
        );
      }

      if (column.label === "WORKS_PROJECT_NAME") {
        let currentProject = searchResult?.filter((result) => result?.id === row?.id)[0];
        return (
          <div class="tooltip">
            <span class="textoverflow" style={{ "--max-width": `${column.maxLength}ch` }}>
              {String(t(value))}
            </span>
            {/* check condtion - if length greater than 20 */}
            <span class="tooltiptext" style={{ whiteSpace: "nowrap" }}>
              {currentProject?.description}
            </span>
          </div>
        );
      }

      if (column.label === "PROJECT_ESTIMATED_COST_IN_RS") {
        if (value) {
          return <p>{`${Digit.Utils.dss.formatterWithoutRound(value, "number")}`}</p>;
        }
        return <p>{"NA"}</p>;
      }

      if (column.label === "ES_COMMON_LOCATION") {
        let currentProject = searchResult?.filter((result) => result?.id === row?.id)[0];
        if (currentProject) {
          let locality = currentProject?.address?.boundary ? t(`${headerLocale}_ADMIN_${currentProject?.address?.boundary}`) : "";
          let ward = currentProject?.additionalDetails?.ward ? t(`${headerLocale}_ADMIN_${currentProject?.additionalDetails?.ward}`) : "";
          let city = currentProject?.address?.city
            ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(currentProject?.address?.city)}`)
            : "";
          return <p>{`${locality ? locality + ", " : ""}${ward ? ward + ", " : ""}${city}`}</p>;
        }
        return <p>{"NA"}</p>;
      }
    },
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() < new Date(data[keys.end]).getTime() : true;
      }
    },
  },
  ContractsInboxConfig: {
    preProcess: (data) => {
      //set tenantId(inbox,moduleSearch,ProcessSearch)
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.processSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.moduleSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();

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
    additionalCustomizations: (row, column, columnConfig, value, t) => {
      if (column.key === "workOrderNumber") {
        return (
          <span className="link">
            <Link to={`/${window.contextPath}/employee/contracts/contract-details?tenantId=${row.ProcessInstance.tenantId}&workOrderNumber=${value}`}>
              {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      } else if (column.key === "assignee") {
        if (value) {
          return <span>{value?.[0]?.name}</span>;
        } else {
          return <span>{t("NA")}</span>;
        }
      } else if (column.key === "state") {
        return <span>{t(`WF_WO_${value}`)}</span>;
      } else if (column.key === "estimatedAmount") {
        return value ? Digit.Utils.dss.formatterWithoutRound(value, "number") : t("ES_COMMON_NA");
      } else if (column.key === "sla") {
        return value > 0 ? <span className="sla-cell-success">{value}</span> : <span className="sla-cell-error">{value}</span>;
      } else {
        return <div>{t("NA")}</div>;
      }
    },
  },
  SearchContractConfig: {
    preProcess: (data) => {
      const startDate = Digit.Utils.pt.convertDateToEpoch(data.body.inbox?.moduleSearchCriteria?.createdFrom);
      const endDate = Digit.Utils.pt.convertDateToEpoch(data.body.inbox?.moduleSearchCriteria?.createdTo);
      const workOrderNumber = data.body.inbox?.moduleSearchCriteria?.workOrderNumber;
      const status = data.body.inbox?.moduleSearchCriteria?.contractStatus?.code;
      const projectType = data.body.inbox?.moduleSearchCriteria?.projectType?.code;
      const projectName = data.body.inbox?.moduleSearchCriteria?.projectName;
      const ward = data.body.inbox?.moduleSearchCriteria?.ward;
      data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox.moduleSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId();
      data.body.inbox = {
        ...data.body.inbox,
        tenantId: Digit.ULBService.getCurrentTenantId(),
        moduleSearchCriteria: {
          tenantId: Digit.ULBService.getCurrentTenantId(),
          ward,
          workOrderNumber,
          status,
          projectType,
          projectName,
          startDate,
          endDate,
        },
      };
      return data;
    },

    customValidationCheck: (data) => {
      //checking both to and from date are present
      const { createdFrom, createdTo } = data;
      if ((createdFrom === "" && createdTo !== "") || (createdFrom !== "" && createdTo === ""))
        return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" };

      return false;
    },
    additionalCustomizations: (row, column, columnConfig, value, t, searchResult, headerLocale) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result

      if (column.label === "WORKS_ORDER_ID") {
        return (
          <span className="link">
            <Link
              to={`/${
                window.contextPath
              }/employee/contracts/contract-details?tenantId=${Digit.ULBService.getCurrentTenantId()}&workOrderNumber=${value}`}
            >
              {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
            </Link>
          </span>
        );
      }
      if (column.label === "ES_COMMON_AMOUNT") {
        return Digit.Utils.dss.formatterWithoutRound(value, "number");
      }
      if (column.label === "COMMON_ROLE_OF_CBO") {
        return <span>{t(`COMMON_MASTERS_${value}`)}</span>;
      }
      if (column.label === "ES_COMMON_LOCATION") {
        return value ? (
          <span style={{ whiteSpace: "nowrap" }}>
            {String(
              `${t(Digit.Utils.locale.getCityLocale(row?.businessObject?.tenantId))}, ${t(
                Digit.Utils.locale.getMohallaLocale(value, row?.businessObject?.tenantId)
              )}`
            )}
          </span>
        ) : (
          t("ES_COMMON_NA")
        );
      }
    },
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() < new Date(data[keys.end]).getTime() : true;
      }
    },
  },
  SearchWageSeekerConfig: {
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
      let Individual = Object.keys(requestBody)
        .map((key) => {
          if (selectConfig[key]) {
            requestBody[key] = _.get(requestBody, selectConfig[key], null);
          } else if (typeof requestBody[key] == "object") {
            requestBody[key] = requestBody[key]?.code;
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
    additionalCustomizations: (row, column, columnConfig, value, t) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result
      switch (column.label) {
        case "MASTERS_WAGESEEKER_ID":
          return (
            <span className="link">
              <Link to={`/${window.contextPath}/employee/masters/view-wageseeker?tenantId=${row.tenantId}&individualId=${value}`}>
                {value ? value : t("ES_COMMON_NA")}
              </Link>
            </span>
          );

        case "MASTERS_SOCIAL_CATEGORY":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(`MASTERS_${value}`))}</span> : t("ES_COMMON_NA");

        case "MASTERS_ULB":
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
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() <= new Date(data[keys.end]).getTime() : true;
      }
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
    additionalCustomizations: (row, column, columnConfig, value, t) => {
      //here we can add multiple conditions
      //like if a cell is link then we return link
      //first we can identify which column it belongs to then we can return relevant result
      switch (column.label) {
        case "MASTERS_ORGANISATION_ID":
          return (
            <span className="link">
              <Link to={`/${window.contextPath}/employee/masters/view-organisation?tenantId=${row.tenantId}&orgId=${value}`}>
                {value ? value : t("ES_COMMON_NA")}
              </Link>
            </span>
          );
        case "MASTERS_LOCATION":
          return value ? (
            <span style={{ whiteSpace: "nowrap" }}>
              {String(`${t(Digit.Utils.locale.getCityLocale(row?.tenantId))} ${t(Digit.Utils.locale.getMohallaLocale(value, row?.tenantId))}`)}
            </span>
          ) : (
            t("ES_COMMON_NA")
          );
        case "MASTERS_STATUS":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(`MASTERS_${value}`))}</span> : t("ES_COMMON_NA");

        case "MASTERS_ORGANISATION_TYPE":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(`MASTERS_${value}`))}</span> : t("ES_COMMON_NA");

        case "MASTERS_ORGANISATION_SUB_TYPE":
          return value ? <span style={{ whiteSpace: "nowrap" }}>{String(t(`MASTERS_${value}`))}</span> : t("ES_COMMON_NA");
        default:
          return t("ES_COMMON_NA");
      }
    },
    additionalValidations: (type, data, keys) => {
      if (type === "date") {
        return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() < new Date(data[keys.end]).getTime() : true;
      }
    },
  },
};
