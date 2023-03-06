import { Loader } from "@egovernments/digit-ui-react-components";
import React, { useEffect } from "react";
import { useTranslation } from "react-i18next";
import { createProjectConfigMUKTA } from "../../../configs/createProjectConfigMUKTA";
// import { createProjectConfig } from "../../../configs/createProjectConfig";
import CreateProjectForm from "./CreateProjectForm";

const CreateProject = () => {
    const {t} = useTranslation();
    let createProjectConfig = createProjectConfigMUKTA;
    const stateTenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const ULB = Digit.Utils.locale.getCityLocale(tenantId);
    let ULBOptions = []
    ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB });

    const findCurrentDate = () => {
      //return new Date().toJSON().slice(0, 10);
      var date = new Date();
      var dateString = new Date(date.getTime() - (date.getTimezoneOffset() * 60000 ))
                    .toISOString()
                    .split("T")[0];
      return dateString;
    } 

    const { isLoading, data} = Digit.Hooks.useCustomMDMS(
      stateTenant,
      Digit.Utils.getConfigModuleName(),
      [
          {
              "name": "CreateProjectConfig"
          }
      ],
      {
        select: (data) => {
            return data?.[Digit.Utils.getConfigModuleName()]?.CreateProjectConfig[0];
        },
      }
    );

    useEffect(()=>{
      if(Object.keys(createProjectConfig.CreateProjectConfig[0].defaultValues).includes("basicDetails_dateOfProposal")) {
        createProjectConfig.CreateProjectConfig[0].defaultValues.basicDetails_dateOfProposal = findCurrentDate();
      }
      if(Object.keys(createProjectConfig.CreateProjectConfig[0].defaultValues).includes("noSubProject_ulb")) {
        createProjectConfig.CreateProjectConfig[0].defaultValues.noSubProject_ulb = ULBOptions[0];
      }
    },[createProjectConfig]) //TODO: - change this to data

    const projectSession = Digit.Hooks.useSessionStorage("NEW_PROJECT_CREATE", 
      createProjectConfig?.CreateProjectConfig?.[0]?.defaultValues  //TODO: - change this to data
    );

    const [sessionFormData, setSessionFormData, clearSessionFormData] = projectSession;

    if(isLoading) return <Loader />
    return (
      <React.Fragment>
        <CreateProjectForm t={t} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData} createProjectConfig={createProjectConfig?.CreateProjectConfig?.[0]}></CreateProjectForm>
      </React.Fragment>
    )
}

export default CreateProject;