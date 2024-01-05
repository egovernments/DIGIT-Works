import React, { useState, useEffect } from "react";
import CreateEstimate from "./CreateEstimate";
import _ from "lodash";
import { Loader } from "@egovernments/digit-ui-react-components";
const UpdateRevisionDetailedEstimate = () => {
   
     const searchparams = new URLSearchParams(location.search);
     const projectNumber = searchparams.get("projectNumber");
     const tenantId = searchparams.get("tenantId");
     const isEdit = searchparams.get("isEdit");
     const estimateNumber = searchparams.get("estimateNumber");

    //calling in main Create Estimate Screen
    //  const requestCriteria = {
    //     url: "/mdms-v2/v1/_search",
    //     body: {
    //     MdmsCriteria: {
    //         tenantId: tenantId,
    //         moduleDetails: [
    //         {
    //             moduleName: "WORKS-SOR",
    //             masterDetails: [
    //             {
    //                 name: "Rates",
    //                 //filter: `[?(@.sorId=='${sorid}')]`,
    //             },
    //             ],
    //         },
    //         ],
    //     },
    //     },
    //     changeQueryName:"ratesQuery"
    // };

    // const { isLoading, data : RatesData} = Digit.Hooks.useCustomAPIHook(requestCriteria);

    // if(isLoading)
    // return <Loader />

    
    const propsToSend = {
        IsEditRevisionEstimate: true,
        ProjectNumber : projectNumber,
        estimateNumber : estimateNumber,
        TenantId : tenantId,
        //RatesData : RatesData,
    }
    return (
        <CreateEstimate props={propsToSend}></CreateEstimate>
    );
};
export default UpdateRevisionDetailedEstimate;