export const updateEstimatePayload =(data, estimate)=>{
    // let estimateDetails= data?.estimateDetails.filter((item)=>item!==null)
    data?.estimateDetails.map((item, index) => Object.assign(item, {
      additionalDetails:estimate?.estimateDetails[index]?.additionalDetails,
      amount:item?.amount,
      estimateDetailNumber:estimate?.estimateDetails[index]?.estimateDetailNumber,
      id:estimate?.estimateDetails[index]?.id,
      name:item?.name
    }))
    const tenantId = Digit.ULBService.getCurrentTenantId()
    let Zone = tenantId === "pb.jalandhar" ? "JZN1" : "Z1"

    let payload={  
        "id": estimate?.id,
        "tenantId": tenantId,
        "estimateNumber": estimate?.estimateNumber,
        "adminSanctionNumber": null,
        "proposalDate":estimate?.proposalDate,
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
        "scheme": data?.scheme?.code || data?.scheme?.schemeCode,
        "subScheme": data?.subScheme?.code || data?.scheme?.subSchemes[0]?.code,
        "estimateDetails": data?.estimateDetails,
        "additionalDetails": {
          formData:data,
          filesAttached: data?.uploads,
          owner: Digit.UserService.getUser()?.info?.name,
        }
      }
      return payload;
}