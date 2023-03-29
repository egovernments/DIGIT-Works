import React, { useEffect, useMemo, useState } from "react";
import { useHistory } from "react-router-dom";
import CreateWorkOrderForm from "./CreateWorkOrderForm";
// import createWorkOrderConfigMUKTA from "../../../configs/createWorkOrderConfigMUKTA.json";
import { useTranslation } from "react-i18next";
import { Loader } from "@egovernments/digit-ui-react-components";

const CreateWorkOrder = () => {
    const {t} = useTranslation();
    const queryStrings = Digit.Hooks.useQueryParams();
    const estimateNumber = queryStrings?.estimateNumber;
    const tenantId = queryStrings?.tenantId;
    const stateTenant = Digit.ULBService.getStateId();
    const [documents, setDocuments] = useState([]);
    const [officerInCharge, setOfficerInCharge] = useState([]);
    const [nameOfCBO, setNameOfCBO] = useState([]);
    const [isFormReady, setIsFormReady] = useState(false);

    const { isLoading : isConfigLoading, data : createWorkOrderConfigMUKTA} = Digit.Hooks.useCustomMDMS( //change to data
    stateTenant,
    Digit.Utils.getConfigModuleName(),
    [
        {
            "name": "CreateWorkOrderConfig"
        }
    ],
    {
      select: (data) => {
          return data?.[Digit.Utils.getConfigModuleName()]?.CreateWorkOrderConfig[0];
      },
    }
    );

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
    
    //Overheads Search
    const { isLoading : isOverHeadsMasterDataLoading, data : overHeadMasterData } = Digit.Hooks.useCustomMDMS(
        Digit.ULBService.getStateId(),
        "works",
        [{ "name": "Overheads" }]
    );

    const createOfficerInChargeObject = () => {
        return assigneeOptions?.Employees?.filter(employees=>employees?.isActive).map((employee=>( { code : employee?.code, name : employee?.user?.name, data : employee} )))
    }

    const createNameOfCBOObject = () => {
        return organisationOptions?.organisations?.map(organisationOption => ( {code : organisationOption?.id, name : organisationOption?.name, applicationNumber : organisationOption?.applicationNumber } ))
    }

    const handleWorkOrderAmount = (estimate, overHeadMasterData) => {
        overHeadMasterData = overHeadMasterData?.works?.Overheads;
        let totalAmount = estimate?.additionalDetails?.totalEstimatedAmount;

        //loop through the estimate Details and filter with OVERHEAD
        estimate?.estimateDetails?.forEach((estimateDetail)=>{
            if(estimateDetail?.category !== "OVERHEAD") return;
            let amountDetails = estimateDetail?.amountDetail?.[0];

            let overHeadCode = amountDetails?.type;
            let shouldSubtract = !((overHeadMasterData?.filter(overHead=>overHead?.code === overHeadCode)?.[0])?.isWorkOrderValue);

            if(shouldSubtract) {
                totalAmount -= amountDetails?.amount;
            }
        })
        return totalAmount;
    }

    useEffect(()=>{
        if((!isEstimateLoading && !isProjectLoading && !isLoadingHrmsSearch && !isOrgSearchLoading && !isOverHeadsMasterDataLoading)) {
            //set default values
            let defaultValues = {
                basicDetails_projectID :  project?.projectNumber,
                basicDetails_dateOfProposal : Digit.DateUtils.ConvertEpochToDate(project?.additionalDetails?.dateOfProposal),
                basicDetails_projectName : project?.name,
                basicDetails_projectDesc : project?.description,
                workOrderAmountRs : handleWorkOrderAmount(estimate, overHeadMasterData)
            };

            //set document object
            setDocuments(createDocumentObject(estimate?.additionalDetails?.documents));
            setOfficerInCharge(createOfficerInChargeObject());
            setNameOfCBO(createNameOfCBOObject());
            setSessionFormData({...sessionFormData, ...defaultValues});
            setIsFormReady(true);
        }
    },[isEstimateLoading, isProjectLoading, isLoadingHrmsSearch, isOrgSearchLoading, isOverHeadsMasterDataLoading]);

    if(isConfigLoading) return <Loader></Loader>
    return (
        <React.Fragment>
            {
                isFormReady && <CreateWorkOrderForm createWorkOrderConfig={createWorkOrderConfigMUKTA} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData} tenantId={tenantId} estimate={estimate} project={project} preProcessData={{documents : documents, nameOfCBO : nameOfCBO, officerInCharge : officerInCharge}}></CreateWorkOrderForm>
            }
        </React.Fragment>
    )
}

export default CreateWorkOrder;