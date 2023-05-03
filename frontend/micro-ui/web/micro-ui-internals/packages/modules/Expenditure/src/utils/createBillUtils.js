import { convertDateToEpoch } from "../../../../libraries/src/utils/pt";

//form data input name (with cropped prefix) mapping with file category Name
const documentType = {
    "vendor_invoice" : "Vendor Invoice",
    "material_utilisation_log" : "Material Utilisation Log",
    "measurement_book" : "Measurement Book",
    "others" : "Others"
}


const createDocObject = (document, docType, otherDocFileName="Others", isActive, docConfigData) =>{
  console.log("config data :", docConfigData);
   let documentType = docConfigData?.works?.DocumentConfig?.[0]?.documents;
    //handle empty Category Name in File Type
    if((otherDocFileName.trim()).length === 0) {
      otherDocFileName = "";
    }
    let payload_modal = {};
    payload_modal.documentType = documentType?.filter(doc=>doc?.name === docType)?.[0]?.code;
    payload_modal.fileStore = document?.[1]?.['fileStoreId']?.['fileStoreId'];
    payload_modal.documentUid = document?.[1]?.['fileStoreId']?.['fileStoreId'];
    payload_modal.key = docType;
    payload_modal.additionalDetails = {
      fileName : document?.[1]?.['file']?.['name'] ? document?.[1]?.['file']?.['name'] :  documentType?.filter(doc=>doc?.name === docType)?.[0]?.code,
      otherCategoryName :  docType === "others" ? otherDocFileName : ""
    }
    return payload_modal;
}

const fetchDocuments = (documents, otherDocFileName, docConfigData) => {
    let documents_payload_list = [];
  
    //new uploaded docs
    for(let docType of Object.keys(documents)) {
      for(let document of documents[docType]) {
        if(_.isArray(document)) {
        let payload_modal = createDocObject(document, docType, otherDocFileName, "ACTIVE", docConfigData); 
        documents_payload_list.push(payload_modal);
        }
      }
    }
  
    return documents_payload_list;
  }

const fetchDeductions = (deductions, tenantId) => {

    let deductionsList = deductions?.filter(row => row && row.amount!=="0")?.map(row => {
        return {
            "tenantId": tenantId,
            "headCode": row?.name?.code,
            "amount": row?.amount,
            "type": "DEDUCTION",
            "paidAmount": 0,
            "status": "ACTIVE",
            "additionalDetails": {
                "comments": row?.comments
            }
        } 
    })
    return deductionsList
}

export const createBillPayload = (data, contract,  docConfigData) => {
    const tenantId = Digit.ULBService.getCurrentTenantId()
    let DeductionsList = fetchDeductions(data?.deductionDetails, tenantId)

    let payload = {
        bill: {
            "tenantId": tenantId,
            "invoiceDate": convertDateToEpoch(data?.invoice_date),
            "invoiceNumber": data?.invoiceDetails_invoiceNumber,
            "contractNumber": data?.basicDetails_workOrderNumber,
            "projectId": data?.basicDetails_projectID,
            "billDate": convertDateToEpoch(data?.billDetails_billDate), 
            "status": "ACTIVE",
            "billDetails": [
              { 
                "tenantId": tenantId,	
                "billId": null,	
                "netLineItemAmount": null,	
                "referenceId": data?.basicDetails_workOrderNumber,
                "paymentStatus": null,	
                "fromPeriod": convertDateToEpoch(contract?.startDate),
                "toPeriod":convertDateToEpoch(contract?.endDate),
                "payee": {
                  "tenantId": tenantId,
                  "type": "ORG", 
                  "identifier": data?.invoiceDetails_vendorId,
                  "status": "ACTIVE"
                },
                "lineItems": [
                  {
                    "tenantId": tenantId,
                    "headCode": "MC",
                    "amount": data?.invoiceDetails_materialCost,
                    "type": "PAYABLE", 
                    "paidAmount": 0,
                    "status": "ACTIVE"
                  },
                  {
                    "tenantId": tenantId,
                    "headCode": "GST",
                    "amount": data?.invoiceDetails_gst,
                    "type": "PAYABLE",
                    "paidAmount": 0,
                    "status": "ACTIVE"
                  },
                  ...DeductionsList
                ],
                "payableLineItems": [],
                "additionalDetails": {}
              }
            ],
            "additionalDetails": {
                "totalBillAmount" : String(Digit.Utils.dss.convertFormatterToNumber(data?.billDetails_billAmt))
            },
            "documents": fetchDocuments(
              data?.uploadedDocs,
                data?.uploadedDocs?.doc_others_name,
                docConfigData
                ),
          },
        workflow: {
            "action": "SUBMIT",
            "assignees": []
          }
    };
    return payload;
}