import { ContractService } from "../../elements/Contracts";

const combine = (docs, estimateDocs) => {
    let allDocuments = [];
    for(let i=0; i<docs?.length; i++) {
        if(docs[i]?.fileStoreId !== undefined||  docs[i]?.fileStore !== undefined)
         {     allDocuments.push(docs[i]); }
    }
    for(let i=0; i<estimateDocs?.length; i++) {
        if(estimateDocs?.[i]?.fileStoreId !== undefined||  estimateDocs?.[i]?.fileStore !== undefined)
         {     allDocuments.push(estimateDocs[i]); }
    }
    return allDocuments;
}

const transformViewDataToApplicationDetails = async (t, data, workflowDetails, revisedWONumber) => {
    //if revisedWONumber is defined then it's a time extension screen(use TE object here)
    const isTimeExtAlreadyInWorkflow = data.contracts.some(element => element.businessService===
        Digit?.Customizations?.["commonUiConfig"]?.businessServiceMap?.revisedWO && element.status==="INWORKFLOW")
    
    let contract
    
    // let contract = data.contracts.filter(element => element.supplementNumber=== null)?.[0]
    if(data.contracts.length === 1) contract = data.contracts[0]
    else {
        //this condition is added because to show work order when there are multiple object in response array
        // if(revisedWONumber)
        contract = data.contracts.filter(element => element.supplementNumber && (element.status==="ACTIVE" || element.status==="INWORKFLOW"))?.[0]
        contract = contract ? {...contract, wfStatus:"ACCEPTED"} : {}
        // else
        // contract = data.contracts.filter(element => (element.status==="ACTIVE" || element.status==="INWORKFLOW"))?.[0]   
    }
    
    if(revisedWONumber){
        contract = data?.contracts?.filter(row => row?.supplementNumber===revisedWONumber)?.[0]
    }
    //Added this condition if from the response array not able to find contract then takes the original contract (usecase : of rejected TE)
    if(Object.keys(contract)?.length <= 0)
    {   
        contract = data?.contracts?.filter((row) => row?.supplementNumber == null && row?.status === "ACTIVE")?.[0];
    }

    const contractDetails = {
        title: " ",
        asSectionHeader: false,
        values: [
            { title: "COMMON_NAME_OF_CBO", value: contract?.additionalDetails?.orgName || t("NA")},
            { title: "WORKS_ORGN_ID", value: contract?.additionalDetails?.cboOrgNumber || t("NA")},
            { title: "COMMON_ROLE_OF_CBO", value: contract?.executingAuthority ? t(`COMMON_MASTERS_${contract?.executingAuthority}`) : "NA"},
            { title: "COMMON_DESGN_OF_OFFICER_IN_CHARGE",  value : contract?.additionalDetails?.officerInChargeDesgn || "NA"},
            { title: "COMMON_NAME_OF_OFFICER_IN_CHARGE",value: contract?.additionalDetails?.officerInChargeName?.name || "NA"},
            { title: "COMMON_PROJECT_COMP_PERIOD_DAYS", value: contract?.completionPeriod || t("NA")},
            { title: "COMMON_WORK_ORDER_AMT_RS", value: `₹ ${Digit.Utils.dss.formatterWithoutRound(contract?.totalContractedAmount, 'number')}` || t("NA")},
        ]
    }
    if(contract.startDate){
        contractDetails.values.push({ title: "WORKS_START_DATE", value: Digit.DateUtils.ConvertEpochToDate(contract.startDate)  || t("NA")})
    }
    if(contract.endDate && contract?.additionalDetails?.timeExt && revisedWONumber){
        contractDetails.values.push({ title: "WORKS_ORIGINAL_END_DATE", value: Digit.DateUtils.ConvertEpochToDate(contract.endDate - (Number(contract?.additionalDetails?.timeExt)*24*60*60*1000))  || t("NA")})
    }
    if(contract.endDate){
        contractDetails.values.push({ title: contract?.additionalDetails?.timeExt && revisedWONumber ? "WORKS_EXTENDED_END_DATE":"WORKS_ORIGINAL_END_DATE", value: Digit.DateUtils.ConvertEpochToDate(contract.endDate)  || t("NA")})
    }
    if(contract.additionalDetails.timeExt && revisedWONumber){
        contractDetails.values.push({ title: "EXTENSION_REQ", value: contract?.additionalDetails?.timeExt  || t("NA")})
    }
    if(contract.additionalDetails.timeExtReason && revisedWONumber){
        contractDetails.values.push({ title: "EXTENSION_REASON", value: contract?.additionalDetails?.timeExtReason  || t("NA")})
    }

    const allDocuments = combine(contract?.documents, contract?.additionalDetails?.estimateDocs);
    let documentDetails = {
        title: "",
        asSectionHeader: true,
        additionalDetails: {
            documents: [{
                title: "WORKS_RELEVANT_DOCUMENTS",
                BS : 'Works',
                values: allDocuments?.map((document) => {
                    if(!document.fileStoreId && !document.fileStore)
                    return null
                   if(document?.status !== "INACTIVE") {
                    if(document?.fileStoreId){
                            //ESTIMATE FILES
                            return {
                                title: document?.fileType==="Others"?document?.fileName:document?.fileType,
                                documentType: document?.fileName,
                                documentUid: document?.documentUid,
                                fileStoreId: document?.fileStoreId,
                            };
                        }
                        //WO FILES
                        return {
                            title: document?.documentType === "OTHERS" ? document?.additionalDetails?.otherCategoryName : t(`CONTRACT_${document?.documentType}`),
                            documentType: document?.documentType,
                            documentUid: document?.fileStore,
                            fileStoreId: document?.fileStore,
                        };
                   }
                   return {};
                }),
            },
            ]
        }
    }

    //filter any empty object
    documentDetails.additionalDetails.documents[0].values =documentDetails?.additionalDetails?.documents?.[0]?.values?.filter(value=>{
        if(value?.title){
            return value;
        }
    });

    const applicationDetails = revisedWONumber ? { applicationDetails: [contractDetails] } : { applicationDetails: [contractDetails, documentDetails] };    

  return {
    applicationDetails,
    applicationData: contract,
    processInstancesDetails: workflowDetails?.ProcessInstances,
    workflowDetails,
    isNoDataFound : data?.contracts?.length === 0 ? true : false,
    additionalDetails:{
        isTimeExtAlreadyInWorkflow
    }
  }
} 

export const View = {
    fetchContractDetails: async (t, tenantId, data, searchParams,revisedWONumber) => {
    try {
        const response = await ContractService.search(tenantId, data, searchParams);
        return transformViewDataToApplicationDetails(t, response,undefined,revisedWONumber)
        } catch (error) {
            throw new Error(error?.response?.data?.Errors[0].message);
        }  
    }
}

