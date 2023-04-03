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

export const createWorkOrderUtils = ({tenantId, estimate, project, inputFormdata, selectedApprover, modalData}) => {
    return {
        contract : {
            "tenantId": tenantId,
            "wfStatus": "string",
            "executingAuthority": inputFormdata?.roleOfCBO?.code,
            "contractType": "CON-01",
            "totalContractedAmount": inputFormdata?.workOrderAmountRs,
            "securityDeposit": 0,
            "agreementDate": 0,
            "defectLiabilityPeriod": 0, 
            "orgId": inputFormdata?.nameOfCBO?.code,
            "startDate": 0,
            "endDate": 0,
            "status" : "ACTIVE",
            "completionPeriod": inputFormdata?.projectCompletionPeriodInDays,
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
                doc_boq : inputFormdata?.documents?.doc_boq,
                doc_others : inputFormdata?.documents?.doc_others,
                doc_terms_and_conditions : inputFormdata?.documents?.doc_terms_and_conditions
            },
                inputFormdata?.documents?.doc_others_name
            ),
            "additionalDetails": {
                "officerInChargeId": inputFormdata?.nameOfOfficerInCharge?.code,
                "projectType": project?.projectType,
                "ward": project?.address?.boundary,
                "projectName": project?.name,
                "orgName": inputFormdata?.nameOfCBO?.name,
                "projectId": project?.projectNumber,
                "estimateDocs" : estimate?.additionalDetails?.documents,
                "cboName" : inputFormdata?.nameOfCBO?.name,
                "cboCode" : inputFormdata?.nameOfCBO?.code,
                "totalEstimatedAmount" : estimate?.additionalDetails?.totalEstimatedAmount,
                "termsAndConditions" : inputFormdata?.WOTermsAndConditions,
                "locality" : project?.additionalDetails?.locality?.code,
                "estimateNumber" : estimate?.estimateNumber,
                "officerInChargeDesgn" : inputFormdata?.designationOfOfficerInCharge,
                "officerInChargeName" : inputFormdata?.nameOfOfficerInCharge,
                "projectDesc" : project?.description
            }
        },
        workflow : {
            "action": "CREATE",
            "comment": modalData?.comments,
            "assignees": [
                selectedApprover?.user?.uuid
            ]
        }
    }
}
