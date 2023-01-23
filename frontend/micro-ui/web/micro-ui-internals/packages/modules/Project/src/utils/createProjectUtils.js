import { convertDateToEpoch } from "../../../../libraries/src/utils/pt";

const createDocumentsPayload = (documents) => {
  let documents_payload_list = [];
  let payload_modal =  {
    "id": "",
    "documentType": "",
    "fileStore": "",
    "documentUid": "" //Check with Nipun
  }
  for(let index in documents) {
    payload_modal.id = index;
    payload_modal.documentType = documents[index][1]['file']['type'];
    payload_modal.fileStore = documents[index][1]['fileStoreId']['fileStoreId'];
    payload_modal.documentUid = "";
    documents_payload_list.push(payload_modal);
  }
  return payload_modal;
}

function createProjectList(data, selectedProjectType, tenantId, parentProjectID) {
    console.log("CREATING PAYLOAD-->",data, selectedProjectType, tenantId, parentProjectID);
    let projects_payload = [];
    let project_details;
    let basic_details = data?.basicDetails;
    let total_projects = 1;
    if(selectedProjectType?.code === "COMMON_NO") {
      project_details = data?.noSubProject;
      total_projects = 1;
    }else {
      project_details = data?.withSubProject;
      total_projects = project_details?.subProjects.length - 1; //array has data from 1st index
    }
    //iterate till all sub-projects. For noSubProject Case, this will iterate only once
    for(let i=0; i<total_projects; i++) {
        let payload =   {
          "tenantId": tenantId,
          "name": basic_details?.projectName,
          "projectType": "MP-CWS", //Check with Chetan
          "projectSubType": "",  //Check with Chetan
          "department": project_details?.owningDepartment?.code,
          "description": basic_details?.projectDesc,
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
            "locality": project_details?.locality
          },
          "startDate": convertDateToEpoch(project_details?.startDate), 
          "endDate": convertDateToEpoch(project_details?.endDate), 
          "isTaskEnabled": false, //Not being captured on UI //For Health Team Project
          "parent": "",
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
    }
    project_payloads.push(parent_project_with_no_subprojects);
    return projects_payload;
}

const CreateProjectUtils = {
    payload : {
        create : (data, selectedProjectType, tenantId, parentProjectID) => {
            return {
                Projects : createProjectList(data, selectedProjectType, tenantId, parentProjectID), //if there is a Parent Project, then create list of sub-projects array, or only create one object for Parent Project.
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
            console.log(key, data[key]);
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