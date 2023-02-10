import React from "react";
import CreateProjectForm from "./CreateProjectForm";

const CreateProject = () => {

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

    return (
      <React.Fragment>
        <CreateProjectForm sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData}></CreateProjectForm>
      </React.Fragment>
    )
}

export default CreateProject;