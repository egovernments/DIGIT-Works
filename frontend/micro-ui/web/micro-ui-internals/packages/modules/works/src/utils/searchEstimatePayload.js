export const searchEstimatePayload =(data)=>{
    const tenantId = Digit.ULBService.getCurrentTenantId();
    let payload={  
      "tenantId":tenantId,
      "estimateDetailNumber":"",
      "adminSanctionNumber":data.adminSanctionNumber,
      "estimateNumber":data.estimateNumber,
      "estimateStatus":"",
      "fromProposalDate":data.fromProposalDate,
      "toProposalDate":data.toProposalDate,
      "department":data.department,
      "typeOfWork":"",
      "sortBy":data.sortBy,
      "sortOrder":data.sortOrder,
      "limit":data.limit,
      "offset":data.offset
    }
    
    return payload;
}