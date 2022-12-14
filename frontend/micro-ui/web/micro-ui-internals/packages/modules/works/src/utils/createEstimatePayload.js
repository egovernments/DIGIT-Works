export const createEstimatePayload =(data)=>{
    let estimateDetails= data?.estimateDetails.filter((item)=>item!==null)
    const tenantId = Digit.ULBService.getCurrentTenantId()
    //let Zone = tenantId === "pb.jalandhar" ? "JZN1" : "Z1"
    let payload={  
        "tenantId": tenantId,
        "status": "ACTIVE",
        "estimateStatus": "ACTIVE",
        "subject": "Construct new schools",
        "requirementNumber": data?.requirementNumber,
        "description": "Construct new schools",
        "department": data?.department?.code,
        "location":`${tenantId}:ADMIN:${tenantId}:${data?.ward?.code}:${data?.location?.code}`,
        "workCategory": "Engineering",
        "beneficiaryType": data?.beneficiaryType.code,
        "natureOfWork": data?.natureOfWork.code,
        "typeOfWork": data?.typeOfWork?.code,
        "subTypeOfWork": data?.subTypeOfWork?.code,
        "entrustmentMode": data?.entrustmentMode?.code, 
        "fund": data?.fund?.code,
        "function": data?.function?.code,
        "budgetHead": data?.budgetHead?.code,
        "scheme": data?.scheme?.schemeCode,
        "subScheme": data?.scheme?.subSchemes[0]?.code,
        "estimateDetails": estimateDetails,
        "additionalDetails": {
          formData:data,
          createdBy: Digit.UserService.getUser()?.info?.name,
          owner: Digit.UserService.getUser()?.info?.name,
          filesAttached: data?.uploads
        }
      }
      return payload;
}