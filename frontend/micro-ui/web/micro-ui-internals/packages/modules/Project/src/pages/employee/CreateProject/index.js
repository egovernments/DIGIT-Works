import { Loader } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import CreateProjectForm from "./CreateProjectForm";

const CreateProject = () => {
    const {t} = useTranslation();
    const stateTenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const ULB = Digit.Utils.locale.getCityLocale(tenantId);
    const [isFormReady, setIsFormReady] = useState(false);
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

    const { isLoading, data : configs} = Digit.Hooks.useCustomMDMS( //change to data
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

    const projectSession = Digit.Hooks.useSessionStorage("NEW_PROJECT_CREATE", 
      configs?.defaultValues
    );

    const [sessionFormData, setSessionFormData, clearSessionFormData] = projectSession;

    useEffect(()=>{
      if(configs) {
        if(Object.keys(configs?.defaultValues).includes("basicDetails_dateOfProposal")) {
          configs.defaultValues.basicDetails_dateOfProposal = findCurrentDate();
        }
        if(Object.keys(configs?.defaultValues).includes("noSubProject_ulb")) {
          configs.defaultValues.noSubProject_ulb = ULBOptions[0];
        }
        setSessionFormData(configs?.defaultValues);
        setIsFormReady(true);
      }
    },[configs]);

    if(isLoading) return <Loader />
    return (
      <React.Fragment>
        {isFormReady && 
          <CreateProjectForm t={t} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData} createProjectConfig={configs}></CreateProjectForm>
        }
        </React.Fragment>
    )
}

export default CreateProject;