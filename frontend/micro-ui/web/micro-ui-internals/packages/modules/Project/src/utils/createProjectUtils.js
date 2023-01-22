function createProjectList(data, parentProjectID) {
    console.log(data, parentProjectID);
    let project_payload = [];
    if(!parentProjectID) {
        //Project that has no sub-projects
        let parent_project_with_no_subprojects =   {
            "tenantId": "pb.amritsar",
            "name": "project101",
            "projectType": "MP-CWS",
            "projectSubType": "",
            "department": "DEPT_11",
            "description": "DEPT_11 description",
            "referenceID": "d9d8ffee-68a2-485d-bc50-14160477ea4c",
            "documents": [
                {
                  "id": "",
                  "documentType": "Document1",
                  "fileStore": "99dcde84-aae2-4836-9ff9-f711fb82bb8b",
                  "documentUid": "QWERTY123"
                }
            ],
            "address": {
              "tenantId": "pb.amritsar",
              "doorNo": "1",
              "latitude": 90,
              "longitude": 180,
              "locationAccuracy": 10000,
              "type": "Home",
              "addressLine1": "Address Line 1",
              "addressLine2": "Address Line 2",
              "landmark": "Area1",
              "city": "City1",
              "pincode": "999999",
              "buildingName": "Test_Building",
              "street": "Test_Street",
              "locality": "SUN04"
            },
            "startDate": 0,
            "endDate": 0, 
            "isTaskEnabled": false,
            "parent": "",
            "targets": [
              {
                "beneficiaryType": "Slum",
                "totalNo": 0,
                "targetNo": 0
              }
            ],
            "additionalDetails": {},
            "rowVersion": 0
        }
        project_payload.push(parent_project_with_no_subprojects);
    }
    return project_payload;
}

const CreateProjectUtils = {
    payload : {
        create : (data, parentProjectID) => {
            return {
                Projects : createProjectList(data, parentProjectID), //if there is a Parent Project, then create list of sub-projects array, or only create one object for Parent Project.
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