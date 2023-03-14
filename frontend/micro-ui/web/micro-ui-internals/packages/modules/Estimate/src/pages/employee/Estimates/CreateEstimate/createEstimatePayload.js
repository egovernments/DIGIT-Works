
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

    const obj = Object.keys(docs).map(key=>{
        return {
            fileName: docs?.[key]?.[0]?.[0],
            fileStoreId: docs?.[key]?.[0]?.[1]?.fileStoreId?.fileStoreId,
            documentUid: docs?.[key]?.[0]?.[1]?.fileStoreId?.fileStoreId,
            tenantId: docs?.[key]?.[0]?.[1]?.fileStoreId?.tenantId,
            fileType: `${key}`
        }
        
    })
    return obj
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
            "executingDepartment": "DEPT_11",//hardcoded since we are not capturing it anymore and it is required at BE side
            // "executingDepartment": filteredFormData?.selectedDept?.code,
            // "projectId":"7c941228-6149-4adc-bdb9-8b77f6c3757d",//static for now
            "address": {
                ...projectData?.projectDetails?.searchedProject?.basicDetails?.address,
                tenantId//here added because in address tenantId is mandatory from BE side
            },//get from project search
            "estimateDetails": fetchEstimateDetails(filteredFormData),
            "additionalDetails": {
                "documents": fetchDocuments(data?.uploadedDocs) ,
                "labourMaterialAnalysis":{...filteredFormData?.analysis},
                "creator": Digit.UserService.getUser()?.info?.name,
                "location":{
                    locality: projectData?.projectDetails?.searchedProject?.basicDetails?.address?.boundary,
                    ward: projectData?.projectDetails?.searchedProject?.basicDetails?.ward,
                    city: projectData?.projectDetails?.searchedProject?.basicDetails?.address?.city
                },
                "totalEstimatedAmount": data?.totalEstimateAmount
            }
        },
        workflow:{
            "action": "SUBMIT",
            "comment": filteredFormData?.comments,
            "assignees": [
                filteredFormData?.selectedApprover?.uuid ? filteredFormData?.selectedApprover?.uuid: undefined 
            ]
        }
    }
    if(!payload.workflow.assignees?.[0])
        delete payload.workflow.assignees
    return payload;
}