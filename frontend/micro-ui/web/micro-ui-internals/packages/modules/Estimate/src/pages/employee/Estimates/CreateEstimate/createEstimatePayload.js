
const fetchEstimateDetails = (data) => {
    
    let sornonSORData = data?.nonSORTablev1?.filter(row=> row)?.map(row => {
        
        return {
            "sorId": 45,
            "category": "SOR/Non SOR",
            "name": row?.description,
            "description": row?.description,
            "unitRate": row?.rate,
            "noOfunit": row?.estimatedQuantity,
            "uom": row?.uom?.code,
            "uomValue": 10, //not sure what is this field
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
            "category": "Overheads",
            "name": row?.name?.name,
            "description": row?.name?.description,
            "amountDetail": [
                {
                    "additionalDetails":{},
                    "type": "GST",
                    "amount": row?.amount
                }
            ],

        }
    })
    
    

    return [...sornonSORData,...overHeadsData]
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
            // "referenceNumber": "File-18430283",
            "description": projectData?.projectDetails?.searchedProject?.basicDetails?.projectDesc,
            "executingDepartment": filteredFormData?.selectedDept?.code,
            // "projectId":"7c941228-6149-4adc-bdb9-8b77f6c3757d",//static for now
            "address": {
                ...projectData?.projectDetails?.searchedProject?.basicDetails?.address
            },//get from project search
            "estimateDetails": fetchEstimateDetails(filteredFormData),
            "additionalDetails": {
                "uploads":data?.uploads?.length > 0 ? data?.uploads : []
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