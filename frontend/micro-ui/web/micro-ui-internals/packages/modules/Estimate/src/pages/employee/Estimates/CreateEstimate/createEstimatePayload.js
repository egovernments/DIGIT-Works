
const fetchEstimateDetails = (data) => {
    
    let sornonSORData = data?.nonSORTablev1?.filter(row=> row && row.estimatedAmount!=="0")?.map(row => {
        
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
    let overHeadsData = data?.overheadDetails?.filter(row => row && row.amount!=="0")?.map(row => {
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

const fetchEstimateDetailsEdit = (data,estimate) => {
    
    let sornonSORData = data?.nonSORTablev1?.filter(row=> row && row.estimatedAmount!=="0")?.map(row => {
        
        return {
            "sorId": 45,
            "category": "NON-SOR",
            "name": row?.description,
            "description": row?.description,
            "unitRate": row?.rate,
            "noOfunit": row?.estimatedQuantity,
            "uom": row?.uom?.code,
            "isActive":true,
            // "uomValue": 10, //not sure what is this field//try removing this field
            "amountDetail": [
                {
                    "type": "EstimatedAmount",
                    "amount": row?.estimatedAmount,
                    "additionalDetails":{},
                    "isActive":true,
                }
            ],

        }
    })
    let overHeadsData = data?.overheadDetails?.filter(row => row && row.amount!=="0")?.map(row => {
        return {
            "category": "OVERHEAD",
            "name": row?.name?.code,
            "description": row?.name?.description,
            "isActive":true,
            "amountDetail": [
                {
                    "type": row?.name?.code,
                    "amount": row?.amount,
                    "isActive":true,
                }
            ],
            "additionalDetails": {
                row
            },

        }
    })
    
    //updating existing lineItems
    estimate?.estimateDetails?.forEach(lineItem =>{
        lineItem.isActive = false,
        lineItem.amountDetail[0].isActive = false
    })
    

    return [...sornonSORData,...overHeadsData,...estimate.estimateDetails]
}

const fetchDocuments = (docs) => {

    const obj = Object.keys(docs).map(key=>{
        return {
            fileName: key?.includes("OTHERS") ? docs?.["ESTIMATE_DOC_OTHERS_name"]: docs?.[key]?.[0]?.[0] ,
            fileStoreId: docs?.[key]?.[0]?.[1]?.fileStoreId?.fileStoreId,
            documentUid: docs?.[key]?.[0]?.[1]?.fileStoreId?.fileStoreId,
            tenantId: docs?.[key]?.[0]?.[1]?.fileStoreId?.tenantId,
            fileType: key?.includes("OTHERS") ? "Others" :`${key}`
        }
        
    })
    
    return obj
}

export const createEstimatePayload = (data,projectData,isEdit,estimate) => {
    
    if(isEdit){
        //here make the payload of edit estimate rather than create estimate
        
        let filteredFormData = Object.fromEntries(Object.entries(data).filter(([_, v]) => v != null));
        const tenantId = Digit.ULBService.getCurrentTenantId()
        let payload = {
            estimate:{
                "id":estimate.id,
                "estimateNumber":estimate.estimateNumber,
                "tenantId": tenantId,
                "projectId": projectData?.projectDetails?.searchedProject?.basicDetails?.uuid,
                "status": "ACTIVE",
                "wfStatus": "CREATED",
                "name": projectData?.projectDetails?.searchedProject?.basicDetails?.projectName,
                "description": projectData?.projectDetails?.searchedProject?.basicDetails?.projectDesc,
                "executingDepartment": "WRK",//hardcoded since we are not capturing it anymore and it is required at BE side
                // "executingDepartment": filteredFormData?.selectedDept?.code,
                // "projectId":"7c941228-6149-4adc-bdb9-8b77f6c3757d",//static for now
                "address": {
                    ...projectData?.projectDetails?.searchedProject?.basicDetails?.address,
                    tenantId//here added because in address tenantId is mandatory from BE side
                },//get from project search
                "estimateDetails": fetchEstimateDetailsEdit(filteredFormData,estimate),
                "additionalDetails": {
                    "documents": fetchDocuments(data?.uploadedDocs) ,
                    "labourMaterialAnalysis":{...filteredFormData?.analysis},
                    "creator": Digit.UserService.getUser()?.info?.name,
                    "location":{
                        locality: projectData?.projectDetails?.searchedProject?.basicDetails?.address?.boundary,
                        ward: projectData?.projectDetails?.searchedProject?.basicDetails?.ward,
                        city: projectData?.projectDetails?.searchedProject?.basicDetails?.address?.city
                    },
                    "projectNumber": projectData?.projectDetails?.searchedProject?.basicDetails?.projectID,
                    "totalEstimatedAmount": data?.totalEstimateAmount,
                    "tenantId": tenantId,
                    "ward":projectData?.projectDetails?.searchedProject?.basicDetails?.ward,
                    "locality":projectData?.projectDetails?.searchedProject?.basicDetails?.locality,
                }
            },
            workflow:{
                "action":"RE-SUBMIT" ,
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
    else{
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
                "executingDepartment": "WRK",//hardcoded since we are not capturing it anymore and it is required at BE side
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
                    "ward":projectData?.projectDetails?.searchedProject?.basicDetails?.ward,
                    "locality":projectData?.projectDetails?.searchedProject?.basicDetails?.locality,
                    "projectNumber": projectData?.projectDetails?.searchedProject?.basicDetails?.projectID,
                    "totalEstimatedAmount": data?.totalEstimateAmount,
                    "tenantId": tenantId,
                    "projectName":projectData?.projectDetails?.searchedProject?.basicDetails?.projectName
                }
            },
            workflow:{
                "action":"SUBMIT",
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
}