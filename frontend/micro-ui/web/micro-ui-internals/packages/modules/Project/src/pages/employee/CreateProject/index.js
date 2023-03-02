import { Loader } from "@egovernments/digit-ui-react-components";
import React from "react";
import CreateProjectForm from "./CreateProjectForm";

const CreateProject = () => {
    const tenant = Digit.ULBService.getStateId();

    const findCurrentDate = () => {
      //return new Date().toJSON().slice(0, 10);
      var date = new Date();
      var dateString = new Date(date.getTime() - (date.getTimezoneOffset() * 60000 ))
                    .toISOString()
                    .split("T")[0];
      return dateString;
    } 

    const projectSession = Digit.Hooks.useSessionStorage("NEW_PROJECT_CREATE", 
    {
      basicDetails_dateOfProposal : findCurrentDate(),
      basicDetails_hasSubProjects : {name : "COMMON_YES", code : "COMMON_YES"},
      withSubProject_project_estimatedCostInRs : 0
    });

    const [sessionFormData, setSessionFormData, clearSessionFormData] = projectSession;

    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
      tenant,
      "commonUiConfig",
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
    if(isLoading) return <Loader />
    return (
      <React.Fragment>
        <CreateProjectForm sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData} createProjectConfig={data}></CreateProjectForm>
      </React.Fragment>
    )
}

export default CreateProject;