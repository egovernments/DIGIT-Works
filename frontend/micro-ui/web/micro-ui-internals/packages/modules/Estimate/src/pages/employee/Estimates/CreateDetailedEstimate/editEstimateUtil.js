export const editEstimateUtil = (estimate,uom,overheads) => {
    
    if(!estimate || !uom || !overheads){
        return {}
    }

    const formData = {}
    //pre populating the relevant formData
    //total estimate amount
    formData.totalEstimatedAmount = estimate?.additionalDetails?.totalEstimatedAmount

    //labour and material analysis
    formData.analysis = estimate?.additionalDetails?.labourMaterialAnalysis

    //uploaded docs
    const uploadedDocs = {}
    estimate?.additionalDetails?.documents.forEach(doc=>{
        if(doc?.fileStoreId){
            uploadedDocs[doc.fileType==="Others" ? "ESTIMATE_DOC_OTHERS" :doc.fileType] = [
                [
                    doc?.fileName,
                    {
                        file:{},
                        fileStoreId:{
                            fileStoreId:doc.fileStoreId,
                            tenantId:doc.tenantId
                        }
                    }
                ]
            ]
        }
        if((doc?.fileStoreId) && doc?.fileType==="Others"){
            uploadedDocs["ESTIMATE_DOC_OTHERS_name"]=doc?.fileName
        }
    })
    
    formData.uploadedDocs = uploadedDocs

    //Line Items table
    const LineItems = [null]
    estimate?.estimateDetails?.filter(row=>row?.category==="NON-SOR")?.forEach(row=>{
        LineItems.push({
            "description": row?.description,
            "rate": `${row?.unitRate}`,
            "estimatedQuantity": `${row?.noOfunit}`,
            "estimatedAmount": `${row?.amountDetail?.[0].amount}`,
            "uom":{
                ...uom.filter(uomRow=>uomRow?.code === row?.uom)?.[0],
                "name": `ES_COMMON_UOM_${row?.uom}`
            },
            "isActive":row?.isActive,
        })
    })
    formData["nonSORTablev1"] = LineItems
    //Overheads table
    const overHeadItems = [null]
    estimate?.estimateDetails?.filter(row=>row?.category==="OVERHEAD")?.forEach(row=>{
        let corresOverhead = overheads.filter(overheadRow=>overheadRow?.code === row?.amountDetail?.[0]?.type)?.[0]
        overHeadItems.push({
            "percentage": corresOverhead?.type==="percentage" ? `${corresOverhead?.value}` : "Lumpsum",
            "amount": `${row?.amountDetail?.[0]?.amount}`,
            "name":{
                ...overheads.filter(overheadRow=>overheadRow?.code === row?.amountDetail?.[0]?.type)?.[0],
                "name": `ES_COMMON_OVERHEADS_${row?.amountDetail?.[0]?.type}`
            },
            "isActive":row?.isActive,
        })
    })
    formData["overheadDetails"] = overHeadItems

    
    
    return formData
}