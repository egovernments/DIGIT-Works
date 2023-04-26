import { convertDateToEpoch } from "../../../../libraries/src/utils/pt";

//form data input name (with cropped prefix) mapping with file category Name
const documentType = {
    "vendor_invoice" : "Vendor Invoice",
    "material_utilisation_log" : "Material Utilisation Log",
    "measurement_book" : "Measurement Book",
    "others" : "Others"
}


const createDocObject = (document, docType, otherDocFileName="Others", isActive) =>{
 
    //handle empty Category Name in File Type
    if((otherDocFileName.trim()).length === 0) {
      otherDocFileName = "";
    }
    let payload_modal = {};
    payload_modal.documentType = documentType?.[docType];
    payload_modal.fileStore = document?.[1]?.['fileStoreId']?.['fileStoreId'];
    payload_modal.documentUid = document?.[1]?.['fileStoreId']?.['fileStoreId'];
    payload_modal.tenantId = document?.[1]?.['fileStoreId']?.['tenantId'];
    payload_modal.key = docType;
    payload_modal.additionalDetails = {
      fileName : document?.[1]?.['file']?.['name'] ? document?.[1]?.['file']?.['name'] : documentType?.[docType],
      otherCategoryName :  docType === "others" ? otherDocFileName : ""
    }
    return payload_modal;
}

const fetchDocuments = (documents, otherDocFileName) => {
    let documents_payload_list = [];
  
    //new uploaded docs
    for(let docType of Object.keys(documents)) {
      for(let document of documents[docType]) {
        let payload_modal = createDocObject(document, docType, otherDocFileName, "ACTIVE"); 
        documents_payload_list.push(payload_modal);
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
            "type": "deduction",
            "additionalDetails": {
                "comments": row?.comments
            }
        } 
    })
    return deductionsList
}

export const createBillPayload = (data, contract) => {
    const tenantId = Digit.ULBService.getCurrentTenantId()
    let DeductionsList = fetchDeductions(data?.deductionDetails, tenantId)

    let payload = {
        bill: {
            "tenantId": tenantId,
            "invoiceDate": data?.invoice_date,
            "invoiceNumber": data?.invoiceDetails_invoiceNumber,
            "contractNumber": data?.basicDetails_workOrderNumber,
            "projectId": data?.basicDetails_projectID,
            "fromPeriod":convertDateToEpoch(contract?.startDate),
            "toPeriod": convertDateToEpoch(contract?.endDate), 
            "billDate": convertDateToEpoch(data?.billDetails_billDate),
            "billDetails": [
              {
                "fromPeriod": convertDateToEpoch(contract?.startDate),
                "toPeriod":convertDateToEpoch(contract?.endDate),
                "payee": {
                  "type": "ORG", 
                  "identifier": data?.invoiceDetails_vendorId
                },
                "lineItems": [
                  {
                    "tenantId": tenantId,
                    "headCode": "MC",
                    "amount": data?.invoiceDetails_materialCost,
                    "type": "expense"
                  },
                  {
                    "tenantId": tenantId,
                    "headCode": "GST",
                    "amount": data?.invoiceDetails_gst,
                    "type": "expense"
                  },
                  ...DeductionsList
                ],
                "payableLineItems": [],
                "additionalDetails": {}
              }
            ],
            "additionalFields": {
                "totalBillAmount" : String(Digit.Utils.dss.convertFormatterToNumber(data?.billDetails_billAmt))
            },
            "documents": fetchDocuments(
                {
                 vendor_invoice : data?.uploadedDocs?.doc_vendor_invoice, 
                 material_utilisation_log : data?.uploadedDocs?.doc_material_utilisation_log, 
                 measurement_book : data?.uploadedDocs?.doc_measurement_book, 
                 others : data?.uploadedDocs?.doc_others
                },
                data?.uploadedDocs?.doc_others_name
                ),
          },
        workflow: {
            "action": "create", 
            "assignees": []
          }
    };
    return payload;
}