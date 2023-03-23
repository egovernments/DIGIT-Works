import { Loader, ProjectIcon } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useLocation } from "react-router-dom";
import CreateProjectForm from "./CreateProjectForm";
import { createProjectConfigMUKTA } from "../../../configs/createProjectConfigMUKTA";

const updateDefaultValues = ({configs, isModify, sessionFormData, setSessionFormData, findCurrentDate, ULBOptions, state, headerLocale}) => {
    let projectDetails = state?.project?.[0]; 
    console.log(projectDetails);
    if(!isModify) {
      //clear defaultValues from 'config' ( this case can come when user navigates from Create Screen to Modify Screen )
      let validDefaultValues = ["basicDetails_dateOfProposal", "noSubProject_ulb"];
      configs.defaultValues = Object.keys(configs?.defaultValues)
                            .filter(key=> validDefaultValues.includes(key))
                            .reduce((obj, key) => Object.assign(obj, {
                              [key] : configs.defaultValues[key]
                            }), {});
    }

    //update default Values
    //Only update the default values when date of proposal or ULB is not present in Session form Data.
    //If we update evertytime, we would overwrite the Session data, on every refresh
    if(!sessionFormData?.basicDetails_dateOfProposal || !sessionFormData.noSubProject_ulb) {
      if(isModify) {
        //this field is only for Modify flow
        configs.defaultValues.basicDetails_projectID = projectDetails?.projectNumber ? projectDetails?.projectNumber  : "";
      }else{
        projectDetails = {};
      }

      configs.defaultValues.basicDetails_dateOfProposal = projectDetails?.additionalDetails?.dateOfProposal ? "2020-01-01" : findCurrentDate(); //TODO:
      configs.defaultValues.noSubProject_ulb = ULBOptions[0];
      configs.defaultValues.basicDetails_projectName = projectDetails?.name ? projectDetails?.name  : "";
      configs.defaultValues.basicDetails_projectDesc = projectDetails?.description ? projectDetails?.description  : "";
      configs.defaultValues.noSubProject_letterRefNoOrReqNo = projectDetails?.referenceID ? projectDetails?.referenceID  : "";
      configs.defaultValues.noSubProject_typeOfProject = projectDetails?.projectType ? { code : projectDetails?.projectType, name : `COMMON_MASTERS_${Digit.Utils.locale.getTransformedLocale(projectDetails?.projectType)}`, projectSubType : []}  : "";
      configs.defaultValues.noSubProject_targetDemography = projectDetails?.additionalDetails?.targetDemography ? { code : projectDetails?.additionalDetails?.targetDemography, name : `COMMON_MASTERS_${Digit.Utils.locale.getTransformedLocale(projectDetails?.additionalDetails?.targetDemography)}`}  : "";
      configs.defaultValues.noSubProject_estimatedCostInRs = projectDetails?.additionalDetails?.estimatedCostInRs ? projectDetails?.additionalDetails?.estimatedCostInRs  : "";
      configs.defaultValues.noSubProject_geoLocation = projectDetails?.address?.addressLine1 ? projectDetails?.address?.addressLine1  : "";
      configs.defaultValues.noSubProject_ward = projectDetails?.additionalDetails?.ward ?  { code : projectDetails?.additionalDetails?.ward, name : projectDetails?.additionalDetails?.ward, i18nKey: `${headerLocale}_ADMIN_${projectDetails?.additionalDetails?.ward}`}  : "";
      configs.defaultValues.noSubProject_locality = projectDetails?.address?.boundary ? { code : projectDetails?.address?.boundary, name : projectDetails?.address?.boundary, i18nKey: `${headerLocale}_ADMIN_${projectDetails?.address?.boundary}`}  : "";
      configs.defaultValues.noSubProject_fund = projectDetails?.additionalDetails?.fund ? { code : projectDetails?.additionalDetails?.fund, name : `COMMON_MASTERS_FUND_${Digit.Utils.locale.getTransformedLocale(projectDetails?.additionalDetails?.fund)}`}  : "";
      configs.defaultValues.noSubProject_docs = projectDetails?.additionalDetails?.projectFiles ? projectDetails?.additionalDetails?.projectFiles : "";
    
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
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);
    let ULBOptions = []
    ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB });

    const findCurrentDate = () => {
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
      {}
    );

    const [sessionFormData, setSessionFormData, clearSessionFormData] = projectSession;

    useEffect(()=>{
      if(configs) {
        updateDefaultValues({ configs, isModify, sessionFormData, setSessionFormData, findCurrentDate, ULBOptions, state, headerLocale })
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