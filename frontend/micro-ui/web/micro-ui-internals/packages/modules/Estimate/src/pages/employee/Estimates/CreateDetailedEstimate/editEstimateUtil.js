import { transformEstimateObjects } from "../../../../../util/estimateConversion"

export const editEstimateUtil = (estimate,uom,overheads,RatesData, allEstimates, sessionFormData) => {
    let SORtable = transformEstimateObjects(estimate, "SOR",RatesData, allEstimates)
    let NONSORtable = transformEstimateObjects(estimate, "NON-SOR",RatesData, allEstimates)

    if(!estimate || !uom || !overheads){
        return {}
    }

    const formData = {}
    //pre populating the relevant formData
    //total estimate amount
    formData.totalEstimatedAmount = estimate?.additionalDetails?.totalEstimatedAmount;
    //change has been done so that I will get estimate id for analysis statement
    formData.estimateId= estimate?.id;

    //labour and material analysis
    formData.labourMaterialAnalysis = estimate?.additionalDetails?.labourMaterialAnalysis

    formData.SOR = SORtable;
    formData.NONSOR = NONSORtable;
    formData.SORtable = SORtable;
    formData.NONSORtable = NONSORtable;


    formData.accessors = {
        SOR : SORtable
    }
    formData.isEdit = true;
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
    //formData["NONSORtable"] = LineItems
    //Overheads table
    const overHeadItems = [null]
    estimate?.estimateDetails?.filter(row=>row?.category==="OVERHEAD")?.forEach(row=>{
        let corresOverhead = overheads.filter(overheadRow=>overheadRow?.code === row?.amountDetail?.[0]?.type)?.[0]
        overHeadItems.push({
            "id": row?.id || undefined,
            "previousLineItemId" : row?.previousLineItemId || undefined,
            "percentage": corresOverhead?.type==="percentage" ? `${corresOverhead?.value}` : "Lumpsum",
            "amount": row?.amountDetail?.[0]?.amount,
            "name":{
                ...overheads.filter(overheadRow=>overheadRow?.code === row?.amountDetail?.[0]?.type)?.[0],
                "name": `ES_COMMON_OVERHEADS_${row?.amountDetail?.[0]?.type}`
            },
            "isActive":row?.isActive,
        })
    })
    formData["editOverheadDetailes"] = overHeadItems
    formData["overheadDetails"] = overHeadItems
    const savedFormData = sessionStorage.getItem("Digit.NEW_ESTIMATE_CREATE");
    if (savedFormData) {
        let parsedData = JSON.parse(savedFormData);
        if(parsedData?.value) parsedData = parsedData?.value;
        if(parsedData?.SORtable && parsedData?.SORtable?.length > 0) formData.SORtable = parsedData?.SORtable;
        if(parsedData?.NONSORtable && parsedData?.NONSORtable?.length > 0) formData.NONSORtable = parsedData?.NONSORtable;
    }
    return formData
}