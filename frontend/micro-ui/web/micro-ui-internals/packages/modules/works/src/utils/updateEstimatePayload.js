export const updateEstimatePayload =(data, estimate)=>{
    // let estimateDetails= data?.estimateDetails.filter((item)=>item!==null)
    const tenantId = Digit.ULBService.getCurrentTenantId()
    let payload={  
        "id": estimate?.id,
        "tenantId": tenantId,
        "status": "ACTIVE",
        "estimateStatus": "CREATED",
        "subject": "Construct new schools",
        "requirementNumber": data?.requirementNumber,
        "description": "Construct new schools",
        "department": data?.department?.code,
        "workCategory": "Engineering",
        "beneficiaryType":  data?.beneficiaryType.code,
        "natureOfWork": data?.natureOfWork.code,
        "typeOfWork": data?.typeOfWork?.code,
        "subTypeOfWork": data?.subTypeOfWork?.code,
        "entrustmentMode": data?.entrustmentMode?.code,
        "fund": data?.fund?.code,
        "function": data?.function?.code,
        "budgetHead": data?.budgetHead?.code,
        "scheme": data?.scheme?.code,
        "subScheme": data?.subScheme?.code,
        "location": "pb.amritsar:ADMIN:pb.amritsar:Z1:B1:SUN04",
        "additionalDetails": {},
        "estimateDetails": data?.estimateDetails
      }
      return payload;
}