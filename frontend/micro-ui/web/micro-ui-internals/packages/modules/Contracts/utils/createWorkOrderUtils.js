    //This handler will return the payload for doc according to API spec. 

import { isArray } from "lodash";

  //This object will be later pushed to an array
  const createDocObject = (document, docType, otherDocFileName="Others", isActive, docConfigData) =>{
    let documentType = docConfigData?.works?.DocumentConfig?.[0]?.documents;

    //handle empty Category Name in File Type
    if((otherDocFileName.trim()).length === 0) {
      otherDocFileName = "";
    }
  
    let payload_modal = {};
    payload_modal.documentType = documentType?.filter(doc=>doc?.name === docType)?.[0]?.code;
    payload_modal.fileStore = document?.[1]?.['fileStoreId']?.['fileStoreId'];
    payload_modal.documentUid = "";
    payload_modal.status = isActive;
    payload_modal.id = document?.[1]?.['file']?.['id'];
    payload_modal.key = docType;
    payload_modal.additionalDetails = {
      fileName : document?.[1]?.['file']?.['name'] ? document?.[1]?.['file']?.['name'] :  documentType?.filter(doc=>doc?.name === docType)?.[0]?.code,
      otherCategoryName : otherDocFileName
    }
    return payload_modal;
  }
  
  //documents - all uploaded docs in the current screen ( create / modify )
  //configs - previously uploaded docs -- defaultValues
  //otherDocFileName - filename filled in the text field for files -- Others
  const createDocumentsPayload = (documents, otherDocFileName, configs, docConfigData) => {

    let documents_payload_list = [];
    let documentDefaultValue = configs?.defaultValues?.documents;
    //new uploaded docs
    for(let docType of Object.keys(documents)) {
        for(let document of documents[docType]) {
          if(_.isArray(document)) {
            let payload_modal = createDocObject(document, docType, otherDocFileName, "ACTIVE", docConfigData); 
            documents_payload_list.push(payload_modal);
          }
        }
    }


    // compare with existing docs
    // if existing docs exists
    if(documentDefaultValue) {
      for(let defaultDocKey of Object.keys(documentDefaultValue)) {
        let isExist = false;
        for(let uploadedDocObject of documents_payload_list) {
          //checks if default and new file exist for same file category
          if(defaultDocKey === uploadedDocObject?.key && defaultDocKey !== "doc_others_name") {
  
            //new file being uploaded, if ID is undefined ( Update Case )
            if(!uploadedDocObject?.id) {
              //if old file exists, make old file as inactive
              let payload_modal = createDocObject(documentDefaultValue[defaultDocKey][0], defaultDocKey, otherDocFileName, "INACTIVE", docConfigData); 
              documents_payload_list.push(payload_modal);
            }
            isExist = true;
          }
        }
        //if previous file does not exist in new formData ( Delete Case ), mark it as InActive
        if(!isExist && defaultDocKey !== "doc_others_name") {
          let payload_modal = createDocObject(documentDefaultValue[defaultDocKey][0], defaultDocKey, otherDocFileName, "INACTIVE", docConfigData); 
          documents_payload_list.push(payload_modal);
        }
      }
    }
    return documents_payload_list;
}

const handleTermsAndConditions = (data) => {
  data.WOTermsAndConditions = data?.WOTermsAndConditions?.filter(WOTermsAndCondition=>WOTermsAndCondition);
  return data.WOTermsAndConditions;
}

export const createWorkOrderUtils = ({tenantId, estimate, project, inputFormdata, selectedApprover, modalData, createWorkOrderConfig, modifyParams, docConfigData, isModify}) => {
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
            "lineItems": isModify ? modifyParams?.lineItems : [
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
            "documents": createDocumentsPayload(
                inputFormdata?.documents,
                inputFormdata?.documents?.doc_others_name,
                createWorkOrderConfig,
                docConfigData
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
                "termsAndConditions" : handleTermsAndConditions(inputFormdata),
                "locality" : project?.additionalDetails?.locality,
                "estimateNumber" : estimate?.estimateNumber,
                "officerInChargeDesgn" : inputFormdata?.designationOfOfficerInCharge,
                "officerInChargeName" : {code : inputFormdata?.nameOfOfficerInCharge?.code, name : inputFormdata?.nameOfOfficerInCharge?.name},
                "projectDesc" : project?.description,
                "cboOrgNumber" : inputFormdata?.nameOfCBO?.orgNumber
            }
        },
        workflow : {
            "action": modifyParams?.updateAction ? modifyParams?.updateAction : "CREATE",
            "comment": modalData?.comments,
            "assignees": selectedApprover?.user?.uuid ? [selectedApprover?.user?.uuid] : []
        }
    }
}
