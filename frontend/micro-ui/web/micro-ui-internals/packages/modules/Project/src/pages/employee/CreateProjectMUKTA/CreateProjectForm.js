import { FormComposer, Header, Toast } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import { createProjectSectionConfig } from "../../../configs/createProjectConfig";
import _ from "lodash";
import CreateProjectUtils from "../../../utils/createProjectUtils";
import { useHistory } from "react-router-dom";
import { createProjectConfigMUKTA } from "../../../configs/createProjectConfigMUKTA";

const whenHasProjectsHorizontalNavConfig =  [
  {
      name:"Project_Details",
      code:"WORKS_PROJECT_DETAILS",
  },
  {
      name:"Financial_Details",
      code:"WORKS_FINANCIAL_DETAILS",
  }
];

const whenHasSubProjectsHorizontalNavConfig =  [
  {
    name:"Project_Details_In_Sub_Project",
    code:"WORKS_PROJECT_DETAILS",
  },
  {
      name:"Financial_Details_In_Sub_Project",
      code:"WORKS_FINANCIAL_DETAILS",
  },
  {
      name: "Sub_Project_Details_In_Sub_Project",
      code:"WORKS_SUB_PROJECT_DETAILS",
  }
];

const CreateProjectForm = ({sessionFormData, setSessionFormData, clearSessionFormData}) => {
    const {t} = useTranslation();
    const [selectedProjectType, setSelectedProjectType] = useState({name : "COMMON_YES", code : "COMMON_YES"});
    const [navTypeConfig, setNavTypeConfig] = useState(whenHasProjectsHorizontalNavConfig);
    const [showNavs, setShowNavs] = useState(false);
    const [subTypeOfProjectOptions, setsubTypeOfProjectOptions] = useState([]);
    const [withSubProjectSubSchemeOptions, setWithSubProjectSubSchemeOptions] = useState([]);
    const [noSubProjectSubSchemeOptions, setNoSubProjectSubSchemeOptions] = useState([]);
    const [selectedWard, setSelectedWard] = useState('');
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);
    const [currentFormCategory, setCurrentFormCategory] = useState("project");
    const [showInfoLabel, setShowInfoLabel] = useState(false);
    const [toast, setToast] = useState({show : false, label : "", error : false});
    const history = useHistory();
    const [isEndDateValid, setIsEndDateValid] = useState(true);
    const ULB = Digit.Utils.locale.getCityLocale(tenantId);
    let ULBOptions = []
    ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB });
    const { isLoading, data : wardsAndLocalities } = Digit.Hooks.useLocation(
      tenantId, 'Ward',
      {
          select: (data) => {
              const wards = []
              const localities = {}
              data?.TenantBoundary[0]?.boundary.forEach((item) => {
                  localities[item?.code] = item?.children.map(item => ({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}` }))
                  wards.push({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}` })
              });
             return {
                  wards, localities
             }
          }
      });
    const filteredLocalities = wardsAndLocalities?.localities[selectedWard];
    
    const config =  useMemo(
      () => Digit.Utils.preProcessMDMSConfig(t, createProjectConfigMUKTA, {
        updateDependent : [
          {
            key : 'withSubProject_project_subScheme',
            value : withSubProjectSubSchemeOptions
          },
          {
            key : 'noSubProject_subScheme',
            value : noSubProjectSubSchemeOptions
          },
          {
            key : 'noSubProject_subTypeOfProject',
            value : subTypeOfProjectOptions
          },
          {
            key : 'noSubProject_ulb',
            value : ULBOptions
          },
          {
            key : 'noSubProject_ward',
            value : wardsAndLocalities?.wards
          },
          {
            key : 'noSubProject_locality',
            value : filteredLocalities
          },
          {
            key : "citizenInfoLabel",
            value : showInfoLabel ? 'project-banner' : 'project-banner display-none'
          },
          {
            key : "noSubProject_endDate",
            value : !isEndDateValid ? (() => isEndDateValid) : (()=>{})
          }
        ]
      }),
      [withSubProjectSubSchemeOptions, noSubProjectSubSchemeOptions, subTypeOfProjectOptions, ULBOptions, wardsAndLocalities, filteredLocalities, showInfoLabel, isEndDateValid]);

      const createSubTypesMDMSObject = (subTypesData) => {
      let mdmsData = [];
      for(let subType of subTypesData?.projectSubType) {
        mdmsData.push({code : subType, name : `COMMON_MASTERS_${subType}`});
      }
      return mdmsData;
    }
  
    // this validation is handled using useform's setError and custom validation type is added. 
    // passing a name which is not associalted to any input will persist the error on submit
    // later on while rendering error, this custom is removed from the name to target the target input element
    const handleErrorForEndDateInSubProjects = (formData, setError, clearErrors, setValue) => {
      if(formData?.withSubProject_project_subProjects) {
        let totalProjects = formData?.withSubProject_project_subProjects.length;
        let subProjects = formData?.withSubProject_project_subProjects;
        for(let index=1; index<=totalProjects; index++) {
          //handle date validation for sub-projects
          if((new Date(subProjects?.[index]?.startDate).getTime()) > (new Date(subProjects?.[index]?.endDate))) {
            setError(`withSubProject_project_subProjects.${index}.endDate_custom`,{ type: "custom" }, { shouldFocus: true });
          }else {
            clearErrors(`withSubProject_project_subProjects.${index}.endDate_custom`);
          }
        }
      }
    }

    const sumTotalEstimatedCost = (sessionFormData, setValue, getValues) => {
      let totalEstimatedCost = 0;
      if(sessionFormData?.withSubProject_project_subProjects) {
        let totalProjects = sessionFormData?.withSubProject_project_subProjects.length;
        let subProjects = sessionFormData?.withSubProject_project_subProjects;
        for(let index=1; index<=totalProjects; index++) {
          if(subProjects[index]?.estimatedCostInRs){
            totalEstimatedCost += Number(getValues(`withSubProject_project_subProjects.${index}.estimatedCostInRs`));
          }
        }
      }
      setValue('withSubProject_project_estimatedCostInRs', totalEstimatedCost);
    }

    const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
      if (!_.isEqual(sessionFormData, formData)) {
        const difference = _.pickBy(sessionFormData, (v, k) => !_.isEqual(formData[k], v));

        //date validation for sub project table
        handleErrorForEndDateInSubProjects(formData, setError, clearErrors, setValue);

        //handle sum of estimated amounts
        sumTotalEstimatedCost(sessionFormData, setValue, getValues);

        if(formData?.basicDetails_hasSubProjects) {
          setSelectedProjectType(formData?.basicDetails_hasSubProjects);
        }
        if(formData.noSubProject_ward) {
            setSelectedWard(formData?.noSubProject_ward?.code)
        }
        if (difference?.noSubProject_ward) {
            setValue("noSubProject_locality", '');
        }
        if(formData?.noSubProject_typeOfProject) {
          let subTypeData = createSubTypesMDMSObject(formData?.noSubProject_typeOfProject);
          setsubTypeOfProjectOptions(subTypeData); 
        } 
        if (difference?.noSubProject_typeOfProject) {
          setValue("noSubProject_subTypeOfProject", '');
        }
        if(formData?.noSubProject_scheme) {
          let subSchemes = formData?.noSubProject_scheme?.subSchemes;
          subSchemes = subSchemes ? subSchemes.map(subScheme => ({ code : `COMMON_MASTERS_${subScheme?.code}`, name : subScheme?.name})) : [];
          setNoSubProjectSubSchemeOptions(subSchemes);
        } 
        if (difference?.noSubProject_scheme) {
          setValue("noSubProject_subScheme", '');
        } 
        if(formData?.withSubProject_project_scheme) {
          let subSchemes = formData?.withSubProject_project_scheme?.subSchemes;
          subSchemes = subSchemes ? subSchemes.map(subScheme => ({ code : `COMMON_MASTERS_${subScheme?.code}`, name : subScheme?.name})) : [];
          setWithSubProjectSubSchemeOptions(subSchemes);
        } 
        if (difference?.withSubProject_project_scheme) {
          setValue("withSubProject_project_subScheme", '');
        }
        setSessionFormData({ ...sessionFormData, ...formData });

        //date validation for project table
        if (difference?.noSubProject_startDate) {
          trigger("noSubProject_endDate", {shouldFocus : true});
        }
        if (difference?.noSubProject_endDate){
          setIsEndDateValid(()=>(new Date(sessionFormData?.noSubProject_startDate).getTime()) < (new Date(formData?.noSubProject_endDate).getTime()));
        }
      }
    }

    const { mutate: CreateProjectMutation } = Digit.Hooks.works.useCreateProject();

    const onSubmit = async(data) => {
      //Transforming Payload to categories of Basic Details, Projects and Sub-Projects
      const transformedPayload = CreateProjectUtils.payload.transform(data);
      //Final Payload
      let payload = CreateProjectUtils.payload.create(transformedPayload, selectedProjectType, "", tenantId);

      await CreateProjectMutation(payload, {
        onError: async (error, variables) => {
          if(error?.response?.data?.Errors?.[0]?.code === "INVALID_DATE") {
            setToast(()=>({show : true, label : t("COMMON_END_DATE_SHOULD_BE_GREATER_THAN_START_DATE"), error : true}));
          }else {
            setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_PROJECTS"), error : true}));
          }
        },
        onSuccess: async (responseData, variables) => {
          //for parent with sub-projects send another call for sub-projects array. Add the Parent ID in each sub-project.
          if(selectedProjectType?.code === "COMMON_YES") {
            payload = CreateProjectUtils.payload.create(transformedPayload, selectedProjectType, responseData?.Projects[0]?.id, tenantId);
            let parentProjectNumber = responseData?.Projects[0]?.projectNumber;
            await CreateProjectMutation(payload, {
              onError :  async (error, variables) => {
                if(error?.response?.data?.Errors?.[0]?.code === "INVALID_DATE") {
                  setToast(()=>({show : true, label : t("COMMON_END_DATE_SHOULD_BE_GREATER_THAN_START_DATE"), error : true}));
                }else {
                  setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_PROJECTS"), error : true}));
                }
              },
              onSuccess: async (responseData, variables) => {
                if(responseData?.ResponseInfo?.Errors) {
                  setToast(()=>({show : true, label : responseData?.ResponseInfo?.Errors?.[0]?.message, error : true}));
                }else if(responseData?.ResponseInfo?.status){
                  sendDataToResponsePage(parentProjectNumber, responseData, true);
                  clearSessionFormData();
                }else{
                  setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_PROJECTS"), error : true}));
                }
              }
            })
          }else{
            if(responseData?.ResponseInfo?.Errors) {
              setToast(()=>({show : true, label : responseData?.ResponseInfo?.Errors?.[0]?.message, error : true}));
            }else if(responseData?.ResponseInfo?.status){
              sendDataToResponsePage("", responseData, true);
              clearSessionFormData();
            }else{
              setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_PROJECTS"), error : true}));
            }
          }
        },
    });
    }

    const sendDataToResponsePage = (parentProjectNumber, responseData, isSuccess) => {
      let queryString = parentProjectNumber ? `${parentProjectNumber},` : "";
      responseData?.Projects?.forEach((project, index ) => {
        if(index === responseData?.Projects.length - 1){
          queryString = queryString+project?.projectNumber;
        }
        else {
          queryString = queryString+project?.projectNumber+",";
        }
      });
      history.push({
        pathname: `/${window?.contextPath}/employee/project/create-project-response`,
        search: `?projectIDs=${queryString}&isSuccess=${isSuccess}`,
      }); 
    }

    const handleToastClose = () => {
      setToast({show : false, label : "", error : false});
    }

    //remove Toast after 3s
    useEffect(()=>{
      if(toast?.show) {
        setTimeout(()=>{
          handleToastClose();
        },3000);
      }
    },[toast?.show]);

    useEffect(()=>{
        if(selectedProjectType?.code === "COMMON_YES") {
          setNavTypeConfig(whenHasSubProjectsHorizontalNavConfig);
          setCurrentFormCategory("withSubProject");
          setShowInfoLabel(true);
          setShowNavs(true);
        }else if(selectedProjectType?.code === "COMMON_NO") {
          setNavTypeConfig(whenHasProjectsHorizontalNavConfig);
          setCurrentFormCategory("noSubProject");
          setShowInfoLabel(false);
          setShowNavs(true);
        }
    },[selectedProjectType]);


    return (
        <React.Fragment>
            <Header styles={{fontSize: "32px"}}>{t("WORKS_CREATE_PROJECT")}</Header>
          {
            createProjectConfigMUKTA && (
              <FormComposer
                label={"WORKS_CREATE_PROJECT"}
                config={config?.CreateProjectConfig?.form.map((config) => {
                  return {
                    ...config,
                    body: config?.body.filter((a) => !a.hideInEmployee),
                  };
                })}
                onSubmit={onSubmit}
                submitInForm={false}
                fieldStyle={{ marginRight: 0 }}
                inline={false}
                className="form-no-margin"
                defaultValues={sessionFormData}
                showWrapperContainers={false}
                isDescriptionBold={false}
                noBreakLine={true}
                showNavs={showNavs}
                showMultipleCardsWithoutNavs={false}
                showMultipleCardsInNavs={false}
                horizontalNavConfig={navTypeConfig}
                currentFormCategory={currentFormCategory}
                onFormValueChange={onFormValueChange}
            />
           )}
      </React.Fragment>
    )
}

export default CreateProjectForm;