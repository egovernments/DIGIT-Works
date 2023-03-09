
const fetchEstimateDetails = (data) => {
    
    let sornonSORData = data?.nonSORTablev1?.filter(row=> row)?.map(row => {
        
        return {
            "sorId": 45,
            "category": "NON-SOR",
            "name": row?.description,
            "description": row?.description,
            "unitRate": row?.rate,
            "noOfunit": row?.estimatedQuantity,
            "uom": row?.uom?.code,
            // "uomValue": 10, //not sure what is this field//try removing this field
            "amountDetail": [
                {
                    "type": "EstimatedAmount",
                    "amount": row?.estimatedAmount,
                    "additionalDetails":{}
                }
            ],

        }
    })
    let overHeadsData = data?.overheadDetails?.filter(row => row)?.map(row => {
        return {
            "category": "OVERHEAD",
            "name": row?.name?.code,
            "description": row?.name?.description,
            "amountDetail": [
                {
                    "type": row?.name?.code,
                    "amount": row?.amount
                }
            ],
            "additionalDetails": {
                row
            },

        }
    })
    
    

    return [...sornonSORData,...overHeadsData]
}

const fetchDocuments = (docs) => {
    return docs.map(doc=>{
        return {
            fileName: doc?.[1]?.file?.name,
            fileStoreId: doc?.[1]?.fileStoreId?.fileStoreId,
            documentUid: doc?.[1]?.fileStoreId?.fileStoreId,
            tenantId: doc?.[1]?.fileStoreId?.tenantId,
            fileType: doc?.[1]?.file?.type
        }
    })
}

export const createEstimatePayload = (data,projectData) => {
    
    let filteredFormData = Object.fromEntries(Object.entries(data).filter(([_, v]) => v != null));
    const tenantId = Digit.ULBService.getCurrentTenantId()
    
    let payload = {
        estimate:{
            "tenantId": tenantId,
            "projectId": projectData?.projectDetails?.searchedProject?.basicDetails?.uuid,
            "status": "ACTIVE",
            "wfStatus": "CREATED",
            "name": projectData?.projectDetails?.searchedProject?.basicDetails?.projectName,
            "description": projectData?.projectDetails?.searchedProject?.basicDetails?.projectDesc,
            "executingDepartment": filteredFormData?.selectedDept?.code,
            // "projectId":"7c941228-6149-4adc-bdb9-8b77f6c3757d",//static for now
            "address": {
                ...projectData?.projectDetails?.searchedProject?.basicDetails?.address,
            },//get from project search
            "estimateDetails": fetchEstimateDetails(filteredFormData),
            "additionalDetails": {
                "documents": data?.uploads?.length > 0 ? fetchDocuments(data?.uploads) : [],
                "labourMaterialAnalysis":{...filteredFormData?.analysis}
            }
        },
        workflow:{
            "action": "CREATE",
            "comment": filteredFormData?.comments,
            "assignees": [
                filteredFormData?.selectedApprover?.uuid
            ]
        }
    }
    
    return payload;
}