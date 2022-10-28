export const updateEstimatePayload =(data, estimate)=>{
    // let estimateDetails= data?.estimateDetails.filter((item)=>item!==null)
    const tenantId = Digit.ULBService.getCurrentTenantId()
    let Zone = tenantId === "pb.jalandhar" ? "JZN1" : "Z1"

    let payload={  
        "id": estimate?.id,
        "tenantId": tenantId,
        "status": "ACTIVE",
        "estimateStatus": "CREATED",
        "subject": "Construct new schools",
        "requirementNumber": data?.requirementNumber,
        "description": "Construct new schools",
        "department": data?.department?.code,
        "location":`${tenantId}:ADMIN:${tenantId}:${Zone}:${data?.ward?.code}:${data?.location?.code}`,
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
        "estimateDetails": data?.estimateDetails,
        "additionalDetails": {
          formData:data,
          filesAttached: data?.uploads
        }
      }
      return payload;
}