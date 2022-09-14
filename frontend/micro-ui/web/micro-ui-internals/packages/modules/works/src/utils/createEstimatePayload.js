export const createEstimatePayload =(data)=>{
    let payload={  
        "tenantId": "pb.amritsar",
        "status": "ACTIVE",
        "estimateStatus": "CREATED",
        "subject": "Construct new schools",
        "requirementNumber": data?.requirementNumber,
        "description": "Construct new schools",
        "department": data?.department?.code,
        "location": data?.location.code,
        "workCategory": "Engineering",
        "beneficiaryType": data?.beneficiaryType.code,
        "natureOfWork": data?.natureOfWork.code,
        "typeOfWork": data?.typeOfWork?.code,
        "subTypeOfWork": data?.subTypeOfWork?.code,
        "entrustmentMode": data?.entrustmentMode?.code, 
        "fund": data?.fund?.code,
        "function": data?.function?.code,
        "budgetHead": data?.budgetHead?.code,
        "scheme": data?.scheme?.code,
        "subScheme": data?.scheme?.subSchemes[0]?.code,
        "estimateDetails": data?.estimateDetails,
        "additionalDetails": {}
      }
      return payload;
}