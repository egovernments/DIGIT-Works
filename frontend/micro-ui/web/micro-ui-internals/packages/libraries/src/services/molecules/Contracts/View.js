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

const transformViewDataToApplicationDetails = async (t, data, workflowDetails, tenantId) => {

    const contract = data.contracts?.[0]
    const contractDetails = {
        title: " ",
        asSectionHeader: false,
        values: [
            { title: "COMMON_NAME_OF_CBO", value: contract?.additionalDetails?.orgName || t("NA")},
            { title: "WORKS_ORGN_ID", value: contract?.orgId || t("NA")},
            { title: "COMMON_ROLE_OF_CBO", value: contract?.executingAuthority ? t(`COMMON_MASTERS_${contract?.executingAuthority}`) : "NA"},
            { title: "COMMON_DESGN_OF_OFFICER_IN_CHARGE",  value : contract?.additionalDetails?.officerInChargeDesgn || "NA"},
            { title: "COMMON_NAME_OF_OFFICER_IN_CHARGE",value: contract?.additionalDetails?.officerInChargeName?.name || "NA"},
            { title: "COMMON_PROJECT_COMP_PERIOD_DAYS", value: contract?.completionPeriod || t("NA")},
            { title: "COMMON_WORK_ORDER_AMT_RS", value: contract?.totalContractedAmount || t("NA")},
        ]
    }
    const allDocuments = combine(contract?.documents, contract?.additionalDetails?.estimateDocs);
    const documentDetails = {
        title: "",
        asSectionHeader: true,
        additionalDetails: {
            documents: [{
                title: "WORKS_RELEVANT_DOCUMENTS",
                BS : 'Works',
                values: allDocuments?.map((document) => {
                    if(document?.fileStoreId != null){
                        return {
                            title: document?.fileType,
                            documentType: document?.fileName,
                            documentUid: document?.documentUid,
                            fileStoreId: document?.fileStoreId,
                        };
                    }
                        return {
                            title: document?.documentType,
                            documentType: document?.documentType,
                            documentUid: document?.documentUid,
                            fileStoreId: document?.fileStore,
                        };
                }),
            },
            ]
        }
    }
    const applicationDetails = { applicationDetails: [contractDetails, documentDetails] };    

  return {
    applicationDetails,
    applicationData: contract,
    processInstancesDetails: workflowDetails?.ProcessInstances,
    workflowDetails,
    isNoDataFound : data?.contracts?.length === 0 ? true : false
  }
} 

export const View = {
    fetchContractDetails: async (t, tenantId, data, searchParams) => {
    try {
        const response = await ContractService.search(tenantId, data, searchParams);
        return transformViewDataToApplicationDetails(t, response)
        } catch (error) {
            throw new Error(error?.response?.data?.Errors[0].message);
        }  
    }
}

