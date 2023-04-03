//form data input name (with cropped prefix) mapping with file category Name
const documentType = {
    "doc_boq" : "BOQ",
    "doc_others" : "Others",
    "doc_terms_and_conditions" : "Terms And Conditions",
  }
  
  //This handler will return the payload for doc according to API spec. 
  //This object will be later pushed to an array
  const createDocObject = (document, docType, otherDocFileName="Others", isActive) =>{
   
    //handle empty Category Name in File Type
    if((otherDocFileName.trim()).length === 0) {
      otherDocFileName = "";
    }
  
    let payload_modal = {};
    payload_modal.documentType = documentType?.[docType];
    payload_modal.fileStore = document?.[1]?.['fileStoreId']?.['fileStoreId'];
    payload_modal.documentUid = "";
    payload_modal.status = isActive;
    payload_modal.id = document?.[1]?.['file']?.['id'];
    payload_modal.key = docType;
    payload_modal.additionalDetails = {
      fileName : document?.[1]?.['file']?.['name'],
      otherCategoryName : otherDocFileName
    }
    return payload_modal;
  }
  
  //documents - all uploaded docs in the current screen ( create / modify )
  //configs - previously uploaded docs -- defaultValues
  //otherDocFileName - filename filled in the text field for files -- Others
  const createDocumentsPayload = (documents, otherDocFileName, configs) => {
    let documents_payload_list = [];
    let documentDefaultValue = configs?.defaultValues?.documents;
   
    //new uploaded docs
    for(let docType of Object.keys(documents)) {
      for(let document of documents[docType]) {
        let payload_modal = createDocObject(document, docType, otherDocFileName, "ACTIVE"); 
        documents_payload_list.push(payload_modal);
      }
    }

    // compare with existing docs
    // if existing docs exists
    if(documentDefaultValue) {
      for(let defaultDocKey of Object.keys(documentDefaultValue)) {
        let isExist = false;
        for(let uploadedDocObject of documents_payload_list) {
          if(defaultDocKey === uploadedDocObject?.key && defaultDocKey !== "others_name") {
  
            //new file being uploaded, if ID is undefined ( Update Case )
            if(!uploadedDocObject?.id) {
              //if old file exists, make it inactive
              let payload_modal = createDocObject(documentDefaultValue[defaultDocKey][0], defaultDocKey, otherDocFileName, "INACTIVE"); 
              documents_payload_list.push(payload_modal);
            }
            isExist = true;
          }
        }
        //if previous file does not exist in new formData ( Delete Case ), mark it as InActive
        if(!isExist && defaultDocKey !== "others_name") {
          let payload_modal = createDocObject(documentDefaultValue[defaultDocKey][0], defaultDocKey, otherDocFileName, "INACTIVE"); 
          documents_payload_list.push(payload_modal);
        }
      }
    }
  
    return documents_payload_list;
}

export const createWorkOrderUtils = ({tenantId, estimate, project, inputFormdata, selectedApprover, modalData, createWorkOrderConfig, modifyParams}) => {
    return {
        contract : {
            "id" : modifyParams?.contractID,
            "contractNumber" : modifyParams?.contractNumber,
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
                    "id" : modifyParams?.lineItems?.[0]?.id,
                    "estimateId": estimate?.id,
                    "tenantId": tenantId,
                    "status" : "ACTIVE",
                    "additionalDetails": {
                    },
                    "auditDetails" : modifyParams?.lineItems?.[0]?.auditDetails
                }
            ],
            "documents": createDocumentsPayload({
                doc_boq : inputFormdata?.documents?.doc_boq,
                doc_others : inputFormdata?.documents?.doc_others,
                doc_terms_and_conditions : inputFormdata?.documents?.doc_terms_and_conditions
            },
                inputFormdata?.documents?.doc_others_name,
                createWorkOrderConfig
            ),
            "auditDetails" : modifyParams?.contractAuditDetails,
            "processInstance" : null,
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
            "action": modifyParams?.updateAction ? modifyParams?.updateAction : "CREATE",
            "comment": modalData?.comments,
            "assignees": [
                selectedApprover?.user?.uuid
            ]
        }
    }
}
