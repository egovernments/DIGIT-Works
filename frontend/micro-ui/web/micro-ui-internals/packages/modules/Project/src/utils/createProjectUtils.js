import { convertDateToEpoch } from "../../../../libraries/src/utils/pt";

//form data input name (with cropped prefix) mapping with file category Name
const documentType = {
  "feasibility_analysis" : "Feasiblity Analysis",
  "finalized_worklist" : "Finalized Worklist",
  "project_proposal" : "Project Proposal",
  "others" : "Other"
}

//This handler will trim all the doc keys to 'documentType' Keys -- check above object
const transformDefaultDocObject = (documentDefaultValue) => {
  if(!documentDefaultValue) {
    return false;
  }

  let trimmedObjectKeys = {};
  for(let key of Object.keys(documentDefaultValue)) {

    //only for one project -- MUKTA specific
    //for more than one project, remove associated prefix, 'withSubProject_project_doc_'
    if(key.includes("noSubProject_doc_")) {
      let croppedKey = key.replace("noSubProject_doc_","");
      trimmedObjectKeys[croppedKey] = documentDefaultValue[key];
    }
  }

  return trimmedObjectKeys;
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

const createDocumentsPayload = (documents, otherDocFileName, configs) => {
  let documents_payload_list = [];
  let documentDefaultValue = transformDefaultDocObject(configs?.defaultValues?.noSubProject_docs);

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

function createProjectList(data, selectedProjectType, parentProjectID, tenantId, modifyParams, configs) {
    
    let projects_payload = [];
    let project_details;
    let basic_details = data?.basicDetails;
    let total_projects = 1;
    if(selectedProjectType?.code === "COMMON_NO") {
      project_details = data?.noSubProject;
      total_projects = 1;
    }else if(selectedProjectType?.code === "COMMON_YES") {
      if(parentProjectID) {
        total_projects = data?.withSubProject?.subProjects.length - 1; //array has data from 1st index
      }else{
        project_details = data?.withSubProject;
      }
    }
    //iterate till all sub-projects. For noSubProject Case, this will iterate only once
    for(let index=1; index<=total_projects; index++) {
        // In case of Sub Projects having Parent ID, project_details will be each sub-project
        if(parentProjectID) {
          project_details = data?.withSubProject?.subProjects[index];
        }
        let payload =   {
          "tenantId": tenantId,
          "id" : modifyParams?.modify_projectID,
          "projectNumber" : modifyParams?.modify_projectNumber,
          "name": parentProjectID ? project_details?.projectName : basic_details?.projectName,
          "projectType": project_details?.typeOfProject?.code, 
          "projectSubType": project_details?.subTypeOfProject?.code , 
          "department": project_details?.owningDepartment?.code,
          "description":  parentProjectID ? project_details?.projectDesc : basic_details?.projectDesc,
          "referenceID": project_details?.letterRefNoOrReqNo,
          "documents": createDocumentsPayload(
            {
            feasibility_analysis : project_details?.docs?.noSubProject_doc_feasibility_analysis, 
             finalized_worklist : project_details?.docs?.noSubProject_doc_finalized_worklist, 
             others : project_details?.docs?.noSubProject_doc_others, 
             project_proposal : project_details?.docs?.noSubProject_doc_project_proposal
            },
            project_details?.docs?.noSubProject_doc_others_name,
            configs
            ),
          "natureOfWork" : project_details?.natureOfWork?.code,
          "address": {
            "id" : modifyParams?.modify_addressID,
            "tenantId": tenantId,
            "doorNo": "1", //Not being captured on UI
            "latitude": 90, //Not being captured on UI
            "longitude": 180, //Not being captured on UI
            "locationAccuracy": 10000, //Not being captured on UI
            "type": "Home", //Not being captured on UI
            "addressLine1": project_details?.geoLocation,
            "addressLine2": "Address Line 2", //Not being captured on UI
            "landmark": "Area1", //Not being captured on UI
            "city": project_details?.ulb?.code,
            "pincode": "999999", //Not being captured on UI
            "buildingName": "Test_Building", //Not being captured on UI
            "street": "Test_Street", //Not being captured on UI
            "boundary": project_details?.ward?.code, //ward code
            "boundaryType" : "Ward"
          },
          "startDate": convertDateToEpoch(project_details?.startDate), 
          "endDate": convertDateToEpoch(project_details?.endDate), 
          "isTaskEnabled": false, //Not being captured on UI //For Health Team Project
          "parent": parentProjectID || "", // In case of Single project, Parent ID is empty.
          "additionalDetails": { //These are financial details. Adding them here as they will be integrated with a different service.
            "budgetHead" : project_details?.budgetHead?.code,
            "estimatedCostInRs" : project_details?.estimatedCostInRs,
            "function" : project_details?.function?.code,
            "fund" : project_details?.fund?.code,
            "scheme" :  project_details?.scheme?.code,
            "subScheme" :  project_details?.subScheme?.code,  
            "dateOfProposal" : convertDateToEpoch(basic_details?.dateOfProposal),
            "recommendedModeOfEntrustment" : project_details?.recommendedModeOfEntrustment?.code,
            "locality" : project_details?.locality,
            "creator": Digit.UserService.getUser()?.info?.name,
            "targetDemography" : project_details?.targetDemography?.code,
          },
          "rowVersion": 0
      }
      projects_payload.push(payload);
    }
    return projects_payload;
}

const CreateProjectUtils = {
    payload : {
        create : (data, selectedProjectType, parentProjectID, tenantId, modifyParams, configs) => {
            return {
                Projects : createProjectList(data, selectedProjectType, parentProjectID, tenantId, modifyParams, configs), //if there is a Parent Project, then create list of sub-projects array, or only create one object for Parent Project.
                apiOperation : "CREATE"
            }
        },
        transform : (data) => {
          let transformedPayload = {
            basicDetails : {},
            noSubProject : {},
            withSubProject : {}
          };
          for(let key of Object.keys(data)) {
            if(key.includes("basicDetails_")) {
              let croppedKey = key.replace("basicDetails_","");
              transformedPayload.basicDetails[croppedKey] = data[key];
            }
            if(key.includes("noSubProject_")) {
              let croppedKey = key.replace("noSubProject_","");
              transformedPayload.noSubProject[croppedKey] = data[key];
            }
            if(key.includes("withSubProject_project_")) {
              let croppedKey = key.replace("withSubProject_project_","");
              transformedPayload.withSubProject[croppedKey] = data[key];
            }
          }
          return transformedPayload;
        }
    }
}

export default CreateProjectUtils;