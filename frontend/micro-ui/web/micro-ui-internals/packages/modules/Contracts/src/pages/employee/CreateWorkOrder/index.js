import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import createWorkOrderConfigMUKTA from "../../../configs/createWorkOrderConfigMUKTA";
import CreateWorkOrderForm from "./CreateWorkOrderForm";

const CreateWorkOrder = () => {

    const queryStrings = Digit.Hooks.useQueryParams();
    const estimateNumber = queryStrings?.estimateNumber;
    const tenantId = queryStrings?.tenantId;
    const [config, setConfig] = useState({});

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

    const ContractSession = Digit.Hooks.useSessionStorage("CONTRACT_CREATE", {
        basicDetails_projectID : "",
        basicDetails_dateOfProposal : "",
        basicDetails_projectName : "",
        basicDetails_projectDesc : "",
        cboID : "",
        designationOfOfficerInCharge : "",
    });
    const [sessionFormData, setSessionFormData, clearSessionFormData] = ContractSession;

    const createDocumentObject = (documents) => {
        let docs =  documents?.filter(document=>document?.fileStoreId)?.map((document) => {
            return {
                title: document?.fileType,
                documentType: document?.fileType,
                documentUid: document?.documentUid,
                fileStoreId: document?.fileStoreId,
            };
        })

        return [
            {
                title: "",
                BS : 'Works',
                values: docs,
            }
        ];
    }

    const searchOrgPayload = {
        "SearchCriteria": {
            "tenantId": "pg.citya"
        }
    }


    //hrms user search
    const  { isLoading: isLoadingHrmsSearch, isError, error, data: assigneeOptions } = Digit.Hooks.hrms.useHRMSSearch({ roles: "OFFICER_IN_CHARGE", isActive: true }, tenantId, null, null, { enabled: true });

    //organisation search
    const { isLoading : isOrgSearchLoading, data : organisationOptions } = Digit.Hooks.organisation.useSearchOrg(searchOrgPayload);
    
    const createOfficerInChargeObject = () => {
        return assigneeOptions?.Employees?.filter(employees=>employees?.isActive).map((employee=>( { code : employee?.code, name : employee?.user?.name, data : employee} )))
    }

    const createNameOfCBOObject = () => {
        return organisationOptions?.organisations?.map(organisationOption => ( {code : organisationOption?.id, name : organisationOption?.name, applicationNumber : organisationOption?.applicationNumber } ))
    }

    useEffect(()=>{
        if((!isEstimateLoading && !isProjectLoading && !isLoadingHrmsSearch && !isOrgSearchLoading)) {
            //set default values
            let defaultValues = {
                basicDetails_projectID :  project?.projectNumber,
                basicDetails_dateOfProposal : Digit.DateUtils.ConvertEpochToDate(project?.additionalDetails?.dateOfProposal),
                basicDetails_projectName : project?.name,
                basicDetails_projectDesc : project?.description,
                workOrderAmountRs : estimate?.additionalDetails?.totalEstimatedAmount
            };

            //set document object
            let documents =  createDocumentObject(estimate?.additionalDetails?.documents);
            let officerInCharge = createOfficerInChargeObject();
            let nameOfCBO = createNameOfCBOObject();
            setSessionFormData({...sessionFormData, ...defaultValues});
            setConfig(createWorkOrderConfigMUKTA({defaultValues, documents, officerInCharge, nameOfCBO}));
        }
    },[isEstimateLoading, isProjectLoading, isLoadingHrmsSearch, isOrgSearchLoading])

    return (
        <React.Fragment>
            {
                config && <CreateWorkOrderForm createWorkOrderConfig={config?.CreateWorkOrderConfig?.[0]} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData} tenantId={tenantId} estimate={estimate} project={project}></CreateWorkOrderForm>
            }
        </React.Fragment>
    )
}

export default CreateWorkOrder;