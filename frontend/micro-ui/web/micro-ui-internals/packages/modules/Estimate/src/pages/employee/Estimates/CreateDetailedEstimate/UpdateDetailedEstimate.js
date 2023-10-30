import React, { useState, useEffect } from "react";
import CreateEstimate from "./CreateEstimate";
import _ from "lodash";
const UpdateDetailedEstimate = () => {
   
    // get MBNumber from the url
     const searchparams = new URLSearchParams(location.search);
     const projectNumber = searchparams.get("projectNumber");
     const tenantId = searchparams.get("tenantId");
     const isEdit = searchparams.get("isEdit");
     const estimateNumber = searchparams.get("estimateNumber");

    
    const propsToSend = {
        IsEdit: true,
        ProjectNumber : projectNumber,
        estimateNumber : estimateNumber,
        TenantId : tenantId
    }
    return (
        <CreateEstimate props={propsToSend}></CreateEstimate>
    );
};
export default UpdateDetailedEstimate;