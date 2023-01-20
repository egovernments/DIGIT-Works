
const fetchEstimateDetails = (data) => {
    
    let sornonSORData = data?.nonSORTablev1?.filter(row=> row)?.map(row => {
        return {
            "sorId": 45,
            "category": "SOR/Non SOR",
            "name": row?.description,
            "description": row?.description,
            "unitRate": row?.rate,
            "noOfunit": row?.estimatedQuantity,
            "uom": row?.uom,
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
            "name": row?.name,
            "description": row?.name,
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

export const createEstimatePayload = (data) => {
    console.log(data);
    let filteredFormData = Object.fromEntries(Object.entries(data).filter(([_, v]) => v != null));
    const tenantId = Digit.ULBService.getCurrentTenantId()
    //let Zone = tenantId === "pb.jalandhar" ? "JZN1" : "Z1"
    let payload = {
        estimate:{
            "tenantId": tenantId,
            "projectId": "uuuid-oi123467-Qastry",
            "status": "ACTIVE",
            "wfStatus": "CREATED",
            "name": "Construct new schools",
            "referenceNumber": "File-18430283",
            "description": "Construct new schools",
            "executingDepartment": filteredFormData?.selectedDept?.code,
            "address": {
                "tenantId": "pb.jalandhar",
                "latitude": 0,
                "longitude": 0,
                "addressNumber": "kormangla",
                "addressLine1": "forum 1",
                "addressLine2": "string",
                "landmark": "circle",
                "city": "bangalore",
                "pincode": "560150",
                "detail": "string"
            },
            // "estimateDetails": [
            //     {
            //         "category": "Overhead, SOR, non-SOR",
            //         "sorId": "251c51eb-e970-4e01-a99a-70136c47a934",
            //         "name": "string",
            //         "description": "string",
            //         "unitRate": 0,
            //         "noOfunit": 0,
            //         "uom": "string",
            //         "uomValue": 0,
            //         "amountDetail": [
            //             {
            //                 "type": "Gst, cess, charge",
            //                 "amount": 34567,
            //                 "additionalDetails": {}
            //             }
            //         ],
            //         "additionalDetails": {}
            //     }
            // ],
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
    // let payload = {
    //     "tenantId": tenantId,
    //     "status": "ACTIVE",
    //     "estimateStatus": "ACTIVE",
    //     "subject": "Construct new schools",
    //     "requirementNumber": data?.requirementNumber,
    //     "description": "Construct new schools",
    //     "department": data?.department?.code,
    //     "location": `${tenantId}:ADMIN:${tenantId}:${data?.ward?.code}:${data?.location?.code}`,
    //     "workCategory": "Engineering",
    //     "beneficiaryType": data?.beneficiaryType.code,
    //     "natureOfWork": data?.natureOfWork.code,
    //     "typeOfWork": data?.typeOfWork?.code,
    //     "subTypeOfWork": data?.subTypeOfWork?.code,
    //     "entrustmentMode": data?.entrustmentMode?.code,
    //     "fund": data?.fund?.code,
    //     "function": data?.function?.code,
    //     "budgetHead": data?.budgetHead?.code,
    //     "scheme": data?.scheme?.schemeCode,
    //     "subScheme": data?.scheme?.subSchemes[0]?.code,
    //     "estimateDetails": estimateDetails,
    //     "additionalDetails": {
    //         formData: data,
    //         createdBy: Digit.UserService.getUser()?.info?.name,
    //         owner: Digit.UserService.getUser()?.info?.name,
    //         filesAttached: data?.uploads
    //     }
    // }
    return payload;
}