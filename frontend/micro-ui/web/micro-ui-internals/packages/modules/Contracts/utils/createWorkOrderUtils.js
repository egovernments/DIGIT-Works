const documentType = {
    "doc_boq" : "BOQ",
    "doc_others" : "Others",
    "doc_terms_and_conditions" : "Terms And Conditions",
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

export const createWorkOrderUtils = ({tenantId, estimate, project, data}) => {
    debugger;
    return {
        contract : {
            "tenantId": tenantId,
            "wfStatus": "string",
            "executingAuthority": data?.roleOfCBO?.code,
            "contractType": "CON-01",
            "totalContractedAmount": estimate?.additionalDetails?.totalEstimatedAmount,
            "securityDeposit": 0,
            "agreementDate": 0,
            "defectLiabilityPeriod": 0, 
            "orgId": data?.cboID,
            "startDate": 0,
            "endDate": 0,
            "status" : "ACTIVE",
            "completionPeriod": data?.projectCompletionPeriodInDays,
            "lineItems": [
                {
                    "estimateId": estimate?.id,
                    "tenantId": tenantId,
                    "status" : "ACTIVE",
                    "additionalDetails": {
                    }
                }
            ],
            "documents": createDocumentsPayload({
                doc_boq : data?.documents?.doc_boq,
                doc_others : data?.documents?.doc_others,
                doc_terms_and_conditions : data?.documents?.doc_terms_and_conditions
            },
                data?.documents?.doc_others_name
            ),
            "additionalDetails": {
                "officerInChargeId": data?.nameOfOfficerInCharge?.code,
                "projectType": project?.projectType,
                "ward": project?.additionalDetails?.ward,
                "projectName": project?.name,
                "orgName": data?.nameOfCBO?.name,
                "projectId": project?.projectNumber,
                "estimateDocs" : estimate?.additionalDetails?.documents,
                "cboName" : data?.nameOfCBO?.name,
                "cboCode" : data?.nameOfCBO?.code,
                "totalEstimatedAmount" : estimate?.additionalDetails?.totalEstimatedAmount,
                "termsAndConditions" : data?.WOTermsAndConditions,
                "locality" : project?.address?.boundary,
                "estimateNumber" : estimate?.estimateNumber,
                "officerInChargeDesgn" : data?.designationOfOfficerInCharge,
                "officerInChargeName" : data?.nameOfOfficerInCharge,
            }
        },
        workflow : {
            "action": "CREATE",
            "comment": "CREATE WO",
            "assignees": [
            ]
        }
    }
}