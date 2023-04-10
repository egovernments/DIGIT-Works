const documentType = {
    "doc_invoice" : "Invoice",
  }

const createDocumentsPayload = (documents, otherDocFileName) => {
    let documents_payload_list = [];

    for(let docType of Object.keys(documents)) {
      for(let document of documents[docType]) {
        let payload_modal = {};
        payload_modal.documentType = (docType === "others" && otherDocFileName) ? otherDocFileName : documentType[docType];
        payload_modal.fileStore = document[1]['fileStoreId']['fileStoreId'];
        payload_modal.documentUid = "";
        payload_modal.status = "ACTIVE";
        payload_modal.additionalDetails = {
          fileName :  docType === "others" ? otherDocFileName : document[1]['file']['name']
        }
        documents_payload_list.push(payload_modal);
      }
    }
    return documents_payload_list;
}

export const createPurchaseBillUtils = ({tenantId, contract, project, inputFormdata, selectedApprover, modalData}) => {
    return {
        purchase_bill : {
            "tenantId": tenantId,
            "wfStatus": "string",
            "billType": "CON-01",
            "totalBillAmount": inputFormdata?.billAmountRs,
            "securityDeposit": 0,
            "agreementDate": 0,
            "defectLiabilityPeriod": 0, 
            "startDate": 0,
            "endDate": 0,
            "status" : "ACTIVE",
            "completionPeriod": inputFormdata?.projectCompletionPeriodInDays,
            "lineItems": [
                {
                    "contractId": contract?.id,
                    "tenantId": tenantId,
                    "status" : "ACTIVE",
                    "additionalDetails": {
                    }
                }
            ],
            "documents": createDocumentsPayload({
                doc_invoice : inputFormdata?.documents?.doc_invoice
            },
                inputFormdata?.documents?.doc_others_name
            ),
            "additionalDetails": {
                "projectType": project?.projectType,
                "ward": project?.address?.boundary,
                "projectName": project?.name,
                "projectId": project?.projectNumber,
                "contractDocs" : contract?.additionalDetails?.documents,
                "totalContractAmount" : contract?.additionalDetails?.totalContractAmount,
                "termsAndConditions" : inputFormdata?.WOTermsAndConditions,
                "locality" : project?.additionalDetails?.locality?.code,
                "contractNumber" : contract?.contractNumber,
                "projectDesc" : project?.description
            }
        },
        workflow : {
            "action": "CREATE",
            "comment": modalData?.comments,
            "assignees": selectedApprover?.user?.uuid ? [selectedApprover?.user?.uuid] : []
        }
    }
}
