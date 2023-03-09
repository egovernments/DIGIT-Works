import { Link } from "react-router-dom";
import _ from "lodash";
import React from "react";

//create functions here based on module name set in mdms(eg->SearchProjectConfig)
//how to call these -> Digit?.Customizations?.[masterName]?.[moduleName]
// these functions will act as middlewares 
var Digit = window.Digit || {};

export const UICustomizations = {
    EstimateInboxConfig:{
        preProcess:(data) => {
            //set tenantId
            data.body.inbox.tenantId = Digit.ULBService.getCurrentTenantId()
            data.body.inbox.processSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId()
            
            // deleting them for now(assignee-> need clarity from pintu,ward-> static for now,not implemented BE side)
            delete data.body.inbox.moduleSearchCriteria.assignee
            delete data.body.inbox.moduleSearchCriteria.ward
            
            //cloning locality and workflow states to format them
            let locality = _.clone(data.body.inbox.moduleSearchCriteria.locality ? data.body.inbox.moduleSearchCriteria.locality : [])
            let states = _.clone(data.body.inbox.moduleSearchCriteria.state ? data.body.inbox.moduleSearchCriteria.state:[])
            delete data.body.inbox.moduleSearchCriteria.locality
            delete data.body.inbox.moduleSearchCriteria.state
            locality = locality?.map(row=>row?.code)
            states = Object.keys(states)?.filter(key=>states[key])

            //adding formatted data to these keys 
            if(locality.length>0)
            data.body.inbox.moduleSearchCriteria.locality = locality
            if(states.length>0)
            data.body.inbox.moduleSearchCriteria.state = states
            
            const projectType = _.clone(data.body.inbox.moduleSearchCriteria.projectType ? data.body.inbox.moduleSearchCriteria.projectType:{})
            if (projectType?.code) data.body.inbox.moduleSearchCriteria.projectType = projectType.code
            
            //adding tenantId to moduleSearchCriteria
            data.body.inbox.moduleSearchCriteria.tenantId = Digit.ULBService.getCurrentTenantId()
            
            return data
        }
    },
    SearchEstimateConfig: {
        customValidationCheck:(data)=> {
            
            //checking both to and from date are present
            const { fromProposalDate, toProposalDate } = data
            if ((fromProposalDate === "" && toProposalDate !== "") || (fromProposalDate !== "" && toProposalDate === "") )
                return { warning: true, label: "ES_COMMON_ENTER_DATE_RANGE" }
            
               
            return false
        },
        preProcess: (data) => {
            const fromProposalDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.fromProposalDate);
            const toProposalDate = Digit.Utils.pt.convertDateToEpoch(data?.params?.toProposalDate);
            const projectType = data?.params?.projectType?.code;
            data.params = { ...data.params, tenantId: Digit.ULBService.getCurrentTenantId(), fromProposalDate, toProposalDate,projectType };
            //deleting ward data since this is a static field for now
            delete data?.params?.ward
            return data;
        },
        additionalCustomizations: (row, column, columnConfig, value, t) => {
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
                            to={`/${window.contextPath
                                }/employee/estimate/estimate-details?tenantId=${Digit.ULBService.getCurrentTenantId()}&estimateNumber=${value}`}
                        >
                            {String(value ? (column.translate ? t(column.prefix ? `${column.prefix}${value}` : value) : value) : t("ES_COMMON_NA"))}
                        </Link>
                    </span>
                );
            }
            if (column.label === "WORKS_ESTIMATED_AMOUNT") {
                return row?.estimateDetails?.reduce((totalAmount, item) => totalAmount + getAmount(item), 0);
            }
            if (column.label === "ES_COMMON_LOCATION") {
                // let currentProject = searchResult?.filter(result => result?.id === row?.id)[0];
                // if (currentProject) {
                //     let locality = currentProject?.address?.boundary ? t(`${headerLocale}_ADMIN_${currentProject?.address?.boundary}`) : "";
                //     let ward = currentProject?.additionalDetails?.ward ? t(`${headerLocale}_ADMIN_${currentProject?.additionalDetails?.ward}`) : "";
                //     let city = currentProject?.address?.city ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(currentProject?.address?.city)}`) : "";
                //     return (
                //         <p>{`${locality ? locality + ', ' : ''}${ward ? ward + ', ' : ''}${city}`}</p>
                //     )
                // }
                // return <p>{"NA"}</p>
                return <p>{"Location Data"}</p>
            }
        },
    },
}
