export const createEstimatePayload =(data)=>{
    let estimateDetails= data?.estimateDetails.filter((item)=>item!==null)
    const tenantId = Digit.ULBService.getCurrentTenantId()
    let payload={  
        "tenantId": tenantId,
        "status": "ACTIVE",
        "estimateStatus": "ACTIVE",
        "subject": "Construct new schools",
        "requirementNumber": data?.requirementNumber,
        "description": "Construct new schools",
        "department": data?.department?.code,
        "location":`${tenantId}:ADMIN:${tenantId}:Z1:${data?.ward?.code}:${data?.location?.code}`,
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
        "estimateDetails": estimateDetails,
        "additionalDetails": {}
      }
      return payload;
}