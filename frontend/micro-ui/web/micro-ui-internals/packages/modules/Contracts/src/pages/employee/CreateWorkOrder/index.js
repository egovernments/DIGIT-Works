import React, { useEffect, useMemo, useState } from "react";
import { useHistory } from "react-router-dom";
import CreateWorkOrderForm from "./CreateWorkOrderForm";
// import createWorkOrderConfigMUKTA from "../../../configs/createWorkOrderConfigMUKTA.json";
import { useTranslation } from "react-i18next";
import { Loader } from "@egovernments/digit-ui-react-components";
import { updateDefaultValues } from "../../../../utils/modifyWorkOrderUtils";

const CreateWorkOrder = () => {
    const {t} = useTranslation();
    const queryStrings = Digit.Hooks.useQueryParams();
    const isModify = queryStrings?.workOrderNumber ? true : false;
    const [estimateNumber, setEsimateNumber] = useState(queryStrings?.estimateNumber ? queryStrings?.estimateNumber : "");
    const contractNumber = queryStrings?.workOrderNumber;
    const tenantId = queryStrings?.tenantId;
    const stateTenant = Digit.ULBService.getStateId();
    const [documents, setDocuments] = useState([]);
    const [officerInCharge, setOfficerInCharge] = useState([]);
    const [nameOfCBO, setNameOfCBO] = useState([]);
    const [isFormReady, setIsFormReady] = useState(false);
    const tenant = Digit.ULBService.getStateId();

    const { isLoadin: isDocConfigLoading, data : docConfigData } = Digit.Hooks.useCustomMDMS(
        tenant,
        "works",
        [
            {
                "name": "DocumentConfig",
                "filter": `[?(@.module=='Work Order')]`
            }
        ]
    );

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

    // const configs = createWorkOrderConfigMUKTA?.CreateWorkOrderConfig[0];

    //fetching contract data -- modify
    const { isLoading: isContractLoading,data:contract } = Digit.Hooks.contracts.useContractSearch({
        tenantId,
        filters: { contractNumber, tenantId },
        config:{
            enabled: isModify,
            cacheTime : 0
        }
    })

    useEffect(()=>{
        //if session WO# is diff from queryString WO#, reset sessionFormData
        if(sessionFormData?.basicDetails_workOrdernumber !== queryStrings?.workOrderNumber) {
            clearSessionFormData();
        }
    },[])

    useEffect(()=>{
        if(!isContractLoading && isModify) {
            setEsimateNumber(contract?.additionalDetails?.estimateNumber)
        }
    },[contract])

    //fetching estimate data
    const { isLoading: isEstimateLoading,data:estimate } = Digit.Hooks.estimates.useEstimateSearch({
        tenantId,
        filters: { estimateNumber },
        config:{
            enabled: !!(estimateNumber)
        }
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

    const ContractSession = Digit.Hooks.useSessionStorage("CONTRACT_CREATE", {});
    const [sessionFormData, setSessionFormData, clearSessionFormData] = ContractSession;

    const createDocumentObject = (documents) => {
        let docs =  documents?.filter(document=>document?.fileStoreId)?.map((document) => {
            return {
                title: document?.fileType==="Others"?document?.fileName:document?.fileType,
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
            "tenantId": tenantId,
            "functions" : {
                "type" : "CBO"
            }
        }
    }


    //hrms user search
    const  { isLoading: isLoadingHrmsSearch, isError, error, data: assigneeOptions } = Digit.Hooks.hrms.useHRMSSearch({ roles: "OFFICER_IN_CHARGE", isActive: true }, tenantId, null, null, { enabled: true });

    //organisation search
    const { isLoading : isOrgSearchLoading, data : organisationOptions } = Digit.Hooks.organisation.useSearchOrg(searchOrgPayload, {
        cacheTime: 0
    });
    
    //Overheads Search
    const { isLoading : isOverHeadsMasterDataLoading, data : overHeadMasterData } = Digit.Hooks.useCustomMDMS(
        Digit.ULBService.getStateId(),
        "works",
        [{ "name": "Overheads" }]
    );

     //Contract Roles Search
     const { isLoading : isRoleOfCBOLoading, data : roleOfCBO } = Digit.Hooks.useCustomMDMS(
        Digit.ULBService.getStateId(),
        "works",
        [{ "name": "CBORoles" }]
    );

    const createOfficerInChargeObject = (assigneeOptions) => {
        return assigneeOptions?.Employees?.filter(employees=>employees?.isActive).map((employee=>( { code : employee?.code, name : employee?.user?.name, data : employee} )))
    }

    const createNameOfCBOObject = (organisationOptions) => {
        return organisationOptions?.organisations?.map(organisationOption => ( {code : organisationOption?.id, name : organisationOption?.name, applicationNumber : organisationOption?.applicationNumber, orgNumber : organisationOption?.orgNumber } ))
    }

    const handleWorkOrderAmount = ({estimate, overHeadMasterData}) => {
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
        
        return Math.round(totalAmount);
    }

    useEffect(()=>{
        if((estimate && project && createWorkOrderConfigMUKTA && !isLoadingHrmsSearch && !isOrgSearchLoading && !isOverHeadsMasterDataLoading && !isContractLoading && !isDocConfigLoading)) {
            updateDefaultValues({ createWorkOrderConfigMUKTA, isModify, sessionFormData, setSessionFormData, contract, estimate, project, handleWorkOrderAmount, overHeadMasterData, createNameOfCBOObject, organisationOptions, createOfficerInChargeObject, assigneeOptions, roleOfCBO, docConfigData});

            setDocuments(createDocumentObject(estimate?.additionalDetails?.documents));
            setOfficerInCharge(createOfficerInChargeObject(assigneeOptions));
            setNameOfCBO(createNameOfCBOObject(organisationOptions));

            setIsFormReady(true);
        }
    },[isConfigLoading, isEstimateLoading, isProjectLoading, isLoadingHrmsSearch, isOrgSearchLoading, isOverHeadsMasterDataLoading, isContractLoading, estimate, isRoleOfCBOLoading, isDocConfigLoading]);

    if(isConfigLoading) return <Loader></Loader>
    return (
        <React.Fragment>
            {
                isFormReady && <CreateWorkOrderForm createWorkOrderConfig={createWorkOrderConfigMUKTA} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData} tenantId={tenantId} estimate={estimate} project={project} preProcessData={{documents : documents, nameOfCBO : nameOfCBO, officerInCharge : officerInCharge}} isModify={isModify} contractID={contract?.id} contractNumber={contract?.contractNumber} lineItems={contract?.lineItems} contractAuditDetails={contract?.auditDetails} roleOfCBOOptions={roleOfCBO?.works?.CBORoles} docConfigData={docConfigData}></CreateWorkOrderForm>
            }
        </React.Fragment>
    )
}

export default CreateWorkOrder;