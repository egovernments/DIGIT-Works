import React from "react";
import CreateProjectForm from "./CreateProjectForm";

const CreateProject = () => {

    const findCurrentDate = () => {
      return new Date().toJSON().slice(0, 10);
    } 
    const orgSession = Digit.Hooks.useSessionStorage("NEW_PROJECT_CREATE", 
    {
      basicDetails_dateOfProposal : findCurrentDate(),
      basicDetails_hasSubProjects : {name : "COMMON_YES", code : "COMMON_YES"},
    });
    const [sessionFormData, setSessionFormData, clearSessionFormData] = orgSession;

    return (
      <React.Fragment>
        <CreateProjectForm sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData}></CreateProjectForm>
      </React.Fragment>
    )
}

export default CreateProject;