import React, { useEffect, useState } from "react";
import CreateWorkOrderForm from "./CreateWorkOrderForm";
import createWorkOrderConfigMUKTA from "../../../configs/createWorkOrderConfigMUKTA.json";

const CreateWorkOrder = () => {

    const queryStrings = Digit.Hooks.useQueryParams();
    const estimateNumber = queryStrings?.estimateNumber || "ES/2022-23/000758";
    const tenantId = queryStrings?.tenantId || "pg.citya";
    const [isConfigReady, setIsConfigReady] = useState(false);
    let config = createWorkOrderConfigMUKTA?.CreateWorkOrderConfig?.[0];

     //fetching estimate data
     const { isLoading: isEstimateLoading,data:estimate } = Digit.Hooks.estimates.useEstimateSearch({
        tenantId,
        filters: { estimateNumber }
    })
    //fetching project data
    const { isLoading: isProjectLoading, data: project } = Digit.Hooks.project.useProjectSearch({
        tenantId,
        searchParams: {
            Projects: [
                {
                    tenantId,
                    id:estimate?.projectId
                }
            ]
        },
        config:{
            enabled: !!(estimate?.projectId) 
        }
    })

    if(!isEstimateLoading) {
        console.log(estimate);
        if(!isProjectLoading) {
            console.log(project);
        }
    }

    const ContractSession = Digit.Hooks.useSessionStorage("CONTRACT_CREATE", {
        basicDetails_projectID : "",
        basicDetails_dateOfProposal : "",
        basicDetails_projectName : "",
        basicDetails_projectDesc : "",
        cboID : "",
        designationOfOfficerInCharge : "",
    });
    const [sessionFormData, setSessionFormData, clearSessionFormData] = ContractSession;

    const updateDefaultValues = (estimate, project, config, setSessionFormData) => {
        config.defaultValues.basicDetails_projectID = project?.projectNumber;
        config.defaultValues.basicDetails_dateOfProposal = Digit.DateUtils.ConvertEpochToDate(project?.additionalDetails?.dateOfProposal);
        config.defaultValues.basicDetails_projectName = project?.name;
        config.defaultValues.basicDetails_projectDesc = project?.description;

        setSessionFormData({...config?.defaultValues});
    }

    useEffect(()=>{
        if(!isEstimateLoading && !isProjectLoading) {
            config = updateDefaultValues(estimate, project, config, setSessionFormData);
            setIsConfigReady(true);
        }
    },[config, isEstimateLoading, isProjectLoading])

    return (
        <React.Fragment>
            {
                isConfigReady && <CreateWorkOrderForm createWorkOrderConfig={config} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData}></CreateWorkOrderForm>
            }
        </React.Fragment>
    )
}

export default CreateWorkOrder;