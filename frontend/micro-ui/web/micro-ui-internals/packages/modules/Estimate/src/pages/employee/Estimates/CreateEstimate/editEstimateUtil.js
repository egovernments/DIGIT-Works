export const editEstimateUtil = (estimate,uom,overheads) => {
    
    if(!estimate || !uom || !overheads){
        return {}
    }

    const formData = {}
    //pre populating the relevant formData
    //total estimate amount
    formData.totalEstimateAmount = estimate?.additionalDetails?.totalEstimatedAmount

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
        if(!(doc?.fileStoreId) && doc?.fileName==="Others"){
            uploadedDocs["ESTIMATE_DOC_OTHERS_name"]="Others"
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

    //this util fn converts the search response to formData that we pass as default values to the create estimate form to pre populate the existing values
//   return {
//     "nonSORTablev1": [
//         null,
//         {
//             "description": "proj one",
//             "rate": "10",
//             "estimatedQuantity": "100",
//             "estimatedAmount": "1000.0",
//             "uom": {
//                 "code": "KG",
//                 "description": "Kilogram",
//                 "active": true,
//                 "effectiveFrom": 1677044852,
//                 "effectiveTo": null,
//                 "name": "ES_COMMON_UOM_KG"
//             }
//         },
//         {
//             "description": "proj two",
//             "rate": "10",
//             "estimatedQuantity": "200",
//             "estimatedAmount": "2000.0",
//             "uom": {
//                 "code": "SQM",
//                 "description": "Square Meter",
//                 "active": true,
//                 "effectiveFrom": 1677044852,
//                 "effectiveTo": null,
//                 "name": "ES_COMMON_UOM_SQM"
//             }
//         },
//         {
//             "description": "proj three",
//             "rate": "10",
//             "estimatedQuantity": "300",
//             "estimatedAmount": "3000.0",
//             "uom": {
//                 "code": "RMT",
//                 "description": "Running Meter",
//                 "active": true,
//                 "effectiveFrom": 1677044852,
//                 "effectiveTo": null,
//                 "name": "ES_COMMON_UOM_RMT"
//             }
//         },
//         {
//             "description": "proj four",
//             "rate": "10",
//             "estimatedQuantity": "400",
//             "estimatedAmount": "4000.0",
//             "uom": {
//                 "code": "CUM",
//                 "description": "Cubic Meter",
//                 "active": true,
//                 "effectiveFrom": 1677044852,
//                 "effectiveTo": null,
//                 "name": "ES_COMMON_UOM_CUM"
//             }
//         }
//     ],
//     "overheadDetails": [
//         null,
//         {
//             "percentage": "12 %",
//             "amount": "1200.0",
//             "name": {
//                 "code": "GST",
//                 "description": "Goods and Service Tax",
//                 "active": true,
//                 "isAutoCalculated": true,
//                 "type": "percentage",
//                 "value": "12",
//                 "isWorkOrderValue": true,
//                 "effectiveFrom": 1677044852,
//                 "effectiveTo": null,
//                 "name": "ES_COMMON_OVERHEADS_GST"
//             }
//         },
//         {
//             "percentage": "Lumpsum",
//             "amount": "500",
//             "name": {
//                 "code": "REW",
//                 "description": "Royalty on Earth Work",
//                 "active": true,
//                 "isAutoCalculated": false,
//                 "type": "lumpsum",
//                 "isWorkOrderValue": false,
//                 "effectiveFrom": 1677044852,
//                 "effectiveTo": null,
//                 "name": "ES_COMMON_OVERHEADS_REW"
//             }
//         },
//         {
//             "percentage": "7.5 %",
//             "amount": "750.0",
//             "name": {
//                 "code": "SC",
//                 "description": "Supervision Charge",
//                 "active": true,
//                 "isAutoCalculated": true,
//                 "type": "percentage",
//                 "value": "7.5",
//                 "isWorkOrderValue": true,
//                 "effectiveFrom": 1677044852,
//                 "effectiveTo": null,
//                 "name": "ES_COMMON_OVERHEADS_SC"
//             }
//         }
//     ],
//     "analysis": {
//         "labour": "5000",
//         "material": "5000"
//     },
//     "uploadedDocs": {
//         "ESTIMATE_DOC_OTHERS_name": "Others",
//         "ESTIMATE_DOC_DETAILED_ESTIMATE": [
//             [
//                 "abc.pdf",
//                 {
//                     "file": {},
//                     "fileStoreId": {
//                         "fileStoreId": "bd429e63-42b3-48fa-a0ee-d3c6cb18ab4c",
//                         "tenantId": "pg.citya"
//                     }
//                 }
//             ]
//         ],
//         "ESTIMATE_DOC_LABOUR_ANALYSIS": [
//             [
//                 "consumerCode-PB-CH-2022-07-27-001010.pdf",
//                 {
//                     "file": {},
//                     "fileStoreId": {
//                         "fileStoreId": "d78847bd-e6f8-4949-803b-370c1c618e92",
//                         "tenantId": "pg.citya"
//                     }
//                 }
//             ]
//         ],
//         "ESTIMATE_DOC_MATERIAL_ANALYSIS": [
//             [
//                 "consumerCode-PB-CH-2022-07-27-001010.pdf",
//                 {
//                     "file": {},
//                     "fileStoreId": {
//                         "fileStoreId": "fffde16c-7777-467f-8c0e-99cf10a97844",
//                         "tenantId": "pg.citya"
//                     }
//                 }
//             ]
//         ],
//         "ESTIMATE_DOC_DESIGN_DOCUMENT": [
//             [
//                 "consumer-PB-CH-2022-07-27-001010.pdf",
//                 {
//                     "file": {},
//                     "fileStoreId": {
//                         "fileStoreId": "78c8d701-87f3-4cbd-b462-ddc58620146b",
//                         "tenantId": "pg.citya"
//                     }
//                 }
//             ]
//         ],
//         "ESTIMATE_DOC_OTHERS": [
//             [
//                 "consumer-PB-CH-2022-07-27-001010.pdf",
//                 {
//                     "file": {},
//                     "fileStoreId": {
//                         "fileStoreId": "9412ced7-16ac-4b56-b165-19e249fa039f",
//                         "tenantId": "pg.citya"
//                     }
//                 }
//             ]
//         ]
//     },
//     "totalEstimateAmount": 12450
//     }

}