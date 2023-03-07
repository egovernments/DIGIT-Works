import { Link } from "react-router-dom";
import React from "react";

//create functions here based on module name set in mdms(eg->SearchProjectConfig)
//how to call these -> Digit?.Customizations?.[masterName]?.[moduleName]
// these functions will act as middlewares 
var Digit = window.Digit || {};

export const UICustomizations = {
    EstimateInboxConfig:{
        preProcess:(data) => {
            console.log(data);
            
            return data
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
            debugger;
            let currentProject = searchResult?.filter(result=>result?.id === row?.id)[0];
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
    
          if (column.label === "PROJECT_ESTIMATED_COST") {
            if(value) {
              return (
                <p>{`₹ ${value}`}</p>
              );
            }
            return <p>{"NA"}</p>
          }
    
          if (column.label === "ES_COMMON_LOCATION") {
            let currentProject = searchResult?.filter(result=>result?.id === row?.id)[0];
            if(currentProject) {
              let locality = currentProject?.address?.boundary ? t(`${headerLocale}_ADMIN_${currentProject?.address?.boundary}`) : "";
              let ward = currentProject?.additionalDetails?.ward ? t(`${headerLocale}_ADMIN_${currentProject?.additionalDetails?.ward}`) : "";
              let city = currentProject?.address?.city ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(currentProject?.address?.city)}`) : "";
              return (
                <p>{`${locality ? locality +', ' : ''}${ward ? ward + ', ' : ''}${city}`}</p>
              )
            } 
            return <p>{"NA"}</p>
          }
        },
        additionalValidations: (type, data, keys) => {
          if (type === "date") {
            return data[keys.start] && data[keys.end] ? () => new Date(data[keys.start]).getTime() < new Date(data[keys.end]).getTime() : true;
          }
        },
      },
}
