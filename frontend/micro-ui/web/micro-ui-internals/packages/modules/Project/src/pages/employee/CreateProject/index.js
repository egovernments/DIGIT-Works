import { FormComposer, Header, Toast } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { createProjectSectionConfig } from "../../../configs/createProjectConfig";
import _ from "lodash";
import CreateProjectUtils from "../../../utils/createProjectUtils";

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

const CreateProject = () => {
    const {t} = useTranslation();
    const [selectedProjectType, setSelectedProjectType] = useState({name : "COMMON_YES", code : "COMMON_YES"});
    const [navTypeConfig, setNavTypeConfig] = useState(whenHasProjectsHorizontalNavConfig);
    const [showNavs, setShowNavs] = useState(false);
    const [subTypeOfWorkOptions, setSubTypeOfWorkOptions] = useState([]);
    const [subSchemaOptions, setSubSchemaOptions] = useState([]);
    const [selectedWard, setSelectedWard] = useState('');
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const orgSession = Digit.Hooks.useSessionStorage("NEW_PROJECT_CREATE", {});
    const [sessionFormData, setSessionFormData, clearSessionFormData] = orgSession;
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);
    const [currentFormCategory, setCurrentFormCategory] = useState("project");
    const [showInfoLabel, setShowInfoLabel] = useState(false);
    const [toast, setToast] = useState({show : false, label : "", error : false});

    //clear session data on first init
    useEffect(()=>{
      clearSessionFormData();
    },[]);

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
      })
  
    const filteredLocalities = wardsAndLocalities?.localities[selectedWard];

    const onFormValueChange = (setValue, formData, formState, reset) => {
        if (!_.isEqual(sessionFormData, formData)) {
          const difference = _.pickBy(sessionFormData, (v, k) => !_.isEqual(formData[k], v));
          if(formData?.basicDetails_hasSubProjects) {
            setSelectedProjectType(formData?.basicDetails_hasSubProjects);
          }
          if(formData.noSubProject_ward) {
              setSelectedWard(formData?.noSubProject_ward?.code)
          }
          if (difference?.noSubProject_ward) {
              setValue("noSubProject_locality", '');
          }
          if(formData?.noSubProject_typeOfWork) {
            setSubTypeOfWorkOptions(formData?.noSubProject_typeOfWork?.subTypes);
          } 
          if (difference?.noSubProject_typeOfWork) {
            setValue("noSubProject_subTypeOfWork", '');
          }
          if(formData?.noSubProject_scheme) {
            setSubSchemaOptions(formData?.noSubProject_scheme?.subSchemes);
          } 
          if (difference?.noSubProject_scheme) {
            setValue("noSubProject_subScheme", '');
          } 
          if(formData?.withSubProject_project_scheme) {
            setSubSchemaOptions(formData?.withSubProject_project_scheme?.subSchemes);
          } 
          if (difference?.withSubProject_project_scheme) {
            setValue("withSubProject_project_subScheme", '');
          } 
          setSessionFormData({ ...sessionFormData, ...formData });
        }
    }
    const createProjectSectionFormConfig = createProjectSectionConfig(subTypeOfWorkOptions, subSchemaOptions, wardsAndLocalities, filteredLocalities, showInfoLabel);

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

    const { mutate: CreateProjectMutation } = Digit.Hooks.works.useCreateProject();

    const onSubmit = async(data) => {
      const transformedPayload = CreateProjectUtils.payload.transform(data);
      let payload = CreateProjectUtils.payload.create(transformedPayload, selectedProjectType, "", tenantId);

      await CreateProjectMutation(payload, {
        onError: async (error, variables) => {
            console.log("Error",error);
            debugger;
            setToast(()=>({show : true, label : "Failed to Create Project.", error : true}));
        },
        onSuccess: async (responseData, variables) => {
          console.log("Success",responseData);
          debugger;
          if(responseData?.ResponseInfo?.Errors) {
            setToast(()=>({show : true, label : variables?.ResponseInfo?.Errors[0]['message'], error : true}));
          }else if(responseData?.ResponseInfo?.status){
            setToast(()=>({show : true, label : variables?.ResponseInfo?.status, error : false}));
          }else{
            setToast(()=>({show : true, label : "Something Went Wrong.", error : true}));
          }
        },
    });
    }

    const handleToastClose = () => {
      setToast({show : false, label : "", error : false});
    }

    return (
        <React.Fragment>
        <div className={"employee-main-application-details"}>
          <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
            <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_CREATE_PROJECT")}</Header>
          </div>
          {
            createProjectSectionFormConfig?.form && (
              <FormComposer
                label={"WORKS_CREATE_PROJECT"}
                config={createProjectSectionFormConfig?.form.map((config) => {
                  return {
                    ...config,
                    body: config?.body.filter((a) => !a.hideInEmployee),
                  };
                })}
                onSubmit={onSubmit}
                submitInForm={false}
                fieldStyle={{ marginRight: 0 }}
                inline={false}
                className="card-no-margin"
                defaultValues={createProjectSectionFormConfig?.defaultValues}
                showWrapperContainers={false}
                isDescriptionBold={false}
                noBreakLine={true}
                showNavs={showNavs}
                showMultipleCardsWithoutNavs={false}
                showMultipleCardsInNavs={false}
                horizontalNavConfig={navTypeConfig}
                onFormValueChange={onFormValueChange}
                currentFormCategory={currentFormCategory}
            />
           )}
           {toast?.show && <Toast label={toast?.label} error={toast?.error} isDleteBtn={'true'} onClose={handleToastClose}></Toast>}
        </div>
      </React.Fragment>
    )
}

export default CreateProject;