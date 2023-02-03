import { convertDateToEpoch } from "../../../../libraries/src/utils/pt";

const createDocumentsPayload = (documents) => {
  let documents_payload_list = [];
  for(let index in documents) {
    let payload_modal = {};
    payload_modal.id = index;
    payload_modal.documentType = documents[index][1]['file']['type'];
    payload_modal.fileStore = documents[index][1]['fileStoreId']['fileStoreId'];
    payload_modal.documentUid = "";
    documents_payload_list.push(payload_modal);
  }
  return documents_payload_list;
}

function createProjectList(data, selectedProjectType, parentProjectID, tenantId) {
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
          "name": parentProjectID ? project_details?.projectName : basic_details?.projectName,
          "projectType": project_details?.typeOfProject?.code || "MP-CWS" , //BE throwing null pointer Exception if send null and giving error on  empty string. But for Sub-projects's Parent, this field is not captured on UI. -- Need to check with CHetan.
          "projectSubType": project_details?.subTypeOfProject?.code , 
          "department": project_details?.owningDepartment?.code,
          "description":  parentProjectID ? project_details?.projectDesc : basic_details?.projectDesc,
          "referenceID": project_details?.letterRefNoOrReqNo,
          "documents": createDocumentsPayload(project_details?.uploadedFiles),
          "address": {
            "tenantId": tenantId,
            "doorNo": "1", //Not being captured on UI
            "latitude": 90, //Not being captured on UI
            "longitude": 180, //Not being captured on UI
            "locationAccuracy": 10000, //Not being captured on UI
            "type": "Home", //Not being captured on UI
            "addressLine1": project_details?.geoLocation,
            "addressLine2": "Address Line 2", //Not being captured on UI
            "landmark": "Area1", //Not being captured on UI
            "city": "City1", //Not being captured on UI
            "pincode": "999999", //Not being captured on UI
            "buildingName": "Test_Building", //Not being captured on UI
            "street": "Test_Street", //Not being captured on UI
            "locality": project_details?.locality?.code
          },
          "startDate": convertDateToEpoch(project_details?.startDate), 
          "endDate": convertDateToEpoch(project_details?.endDate), 
          "isTaskEnabled": false, //Not being captured on UI //For Health Team Project
          "parent": parentProjectID || "", // In case of Single project, Parent ID is empty.
          "targets": [ //Not being captured on UI //For Health Team Project
            {
              "beneficiaryType": "Slum",
              "totalNo": 0,
              "targetNo": 0
            }
          ],
          "additionalDetails": { //These are financial details. Adding them here as they will be integrated with a different service.
            "budgetHead" : project_details?.budgetHead?.code,
            "estimatedCostInRs" : project_details?.estimatedCostInRs,
            "function" : project_details?.function,
            "fund" : project_details?.fund,
            "scheme" :  project_details?.scheme?.code,
            "subScheme" :  project_details?.subScheme?.code,
          },
          "rowVersion": 0
      }
      projects_payload.push(payload);
    }
    return projects_payload;
}

const CreateProjectUtils = {
    payload : {
        create : (data, selectedProjectType, parentProjectID, tenantId) => {
            return {
                Projects : createProjectList(data, selectedProjectType, parentProjectID, tenantId), //if there is a Parent Project, then create list of sub-projects array, or only create one object for Parent Project.
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