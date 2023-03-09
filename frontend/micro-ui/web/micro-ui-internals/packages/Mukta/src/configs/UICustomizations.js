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
    }
}
