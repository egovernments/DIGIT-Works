import { Loader } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useLocation } from "react-router-dom";
import CreateProjectForm from "./CreateProjectForm";
import { createProjectConfigMUKTA } from "../../../configs/createProjectConfigMUKTA";

const updateDefaultValues = ({configs, isModify, sessionFormData, setSessionFormData, findCurrentDate, ULBOptions, state}) => {
    const projectDetails = state?.project?.basicDetails; //TODO:
    if(!isModify) {
      //clear defaultValues from config
      let validDefaultValues = ["basicDetails_dateOfProposal", "noSubProject_ulb"];
      configs.defaultValues = Object.keys(configs?.defaultValues)
                            .filter(key=> validDefaultValues.includes(key))
                            .reduce((obj, key) => Object.assign(obj, {
                              [key] : configs.defaultValues[key]
                            }), {});
    }
    //update default Values
    if(!sessionFormData?.basicDetails_dateOfProposal || !sessionFormData.noSubProject_ulb) {
      console.log(projectDetails);
      if(isModify) {
        configs.defaultValues.basicDetails_projectID = projectDetails?.projectID ? projectDetails?.projectID  : "";
      }
      configs.defaultValues.basicDetails_dateOfProposal = projectDetails?.projectProposalDate ? "2020-01-01" : findCurrentDate();
      configs.defaultValues.noSubProject_ulb = ULBOptions[0];
      configs.defaultValues.basicDetails_projectName = projectDetails?.projectName ? projectDetails?.projectName  : "";
      configs.defaultValues.basicDetails_projectDesc = projectDetails?.projectDesc ? projectDetails?.projectDesc  : "";
      console.log(configs);
      setSessionFormData({...configs?.defaultValues});
    }
}

const CreateProject = () => {

    const {t} = useTranslation();
    const stateTenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const ULB = Digit.Utils.locale.getCityLocale(tenantId);
    const [isFormReady, setIsFormReady] = useState(false);
    const queryStrings = Digit.Hooks.useQueryParams();
    const isModify = queryStrings?.isModify === "true";
    const {state} = useLocation();
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

    // const { isLoading, data : configs} = Digit.Hooks.useCustomMDMS( //change to data
    //   stateTenant,
    //   Digit.Utils.getConfigModuleName(),
    //   [
    //       {
    //           "name": "CreateProjectConfig"
    //       }
    //   ],
    //   {
    //     select: (data) => {
    //         return data?.[Digit.Utils.getConfigModuleName()]?.CreateProjectConfig[0];
    //     },
    //   }
    // );

    const configs = createProjectConfigMUKTA?.CreateProjectConfig[0];

    const projectSession = Digit.Hooks.useSessionStorage("NEW_PROJECT_CREATE", 
      configs?.defaultValues
    );

    const [sessionFormData, setSessionFormData, clearSessionFormData] = projectSession;

    useEffect(()=>{
      if(configs) {
        updateDefaultValues({ configs, isModify, sessionFormData, setSessionFormData, findCurrentDate, ULBOptions, state })
        setIsFormReady(true);
      }
    },[configs]);

    // if(isLoading) return <Loader />
    return (
      <React.Fragment>
        {isFormReady && 
          <CreateProjectForm t={t} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData} createProjectConfig={configs} isModify={isModify}></CreateProjectForm>
        }
        </React.Fragment>
    )
}

export default CreateProject;