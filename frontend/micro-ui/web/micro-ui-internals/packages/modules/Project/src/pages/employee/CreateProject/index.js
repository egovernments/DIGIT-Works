import { Loader } from "@egovernments/digit-ui-react-components";
import React, { useEffect } from "react";
import { createProjectConfigMUKTA } from "../../../configs/createProjectConfigMUKTA";
// import { createProjectConfig } from "../../../configs/createProjectConfig";
import CreateProjectForm from "./CreateProjectForm";


const CreateProject = () => {
    let createProjectConfig = createProjectConfigMUKTA;
    const tenant = Digit.ULBService.getStateId();

    const findCurrentDate = () => {
      //return new Date().toJSON().slice(0, 10);
      var date = new Date();
      var dateString = new Date(date.getTime() - (date.getTimezoneOffset() * 60000 ))
                    .toISOString()
                    .split("T")[0];
      return dateString;
    } 

    const { isLoading, data} = Digit.Hooks.useCustomMDMS(
      tenant,
      Digit.Utils.getConfigModuleName(),
      [
          {
              "name": "CreateProjectConfig"
          }
      ],
      {
        select: (data) => {
            return data?.commonUiConfig?.CreateProjectConfig[0];
        },
      }
    );

    useEffect(()=>{
      if(Object.keys(createProjectConfig.CreateProjectConfig[0].defaultValues).includes("basicDetails_dateOfProposal")) {
        createProjectConfig.CreateProjectConfig[0].defaultValues.basicDetails_dateOfProposal = findCurrentDate();
      }
    },[createProjectConfig]) //TODO: - change this to data

    const projectSession = Digit.Hooks.useSessionStorage("NEW_PROJECT_CREATE", 
      createProjectConfig?.CreateProjectConfig?.[0]?.defaultValues  //TODO: - change this to data
    );

    const [sessionFormData, setSessionFormData, clearSessionFormData] = projectSession;

    if(isLoading) return <Loader />
    return (
      <React.Fragment>
        <CreateProjectForm sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData} createProjectConfig={createProjectConfig?.CreateProjectConfig?.[0]}></CreateProjectForm>
      </React.Fragment>
    )
}

export default CreateProject;